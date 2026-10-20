<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="边界调整"></operateTitle>
    </template>
    <template #operate>
      <subTitle title="网格信息"></subTitle>
      <el-form ref="gridForm" :model="subForm" label-width="100px" disabled>
        <div class="sub-item" v-for="(item,index) in subForm.gridList" :key="index">
          <mapFormItem preName="gridList." :index="`${index}.`" v-model="subForm.gridList[index]"
                       :orgOptions="orgOptions">
          </mapFormItem>
        </div>
      </el-form>
    </template>
    <template #footer>
      <i-button-bar size="small" :plain="false" :btn-lists="btnLists" @click="clickHandle"
                    ref="btnBar" justifyContent="space-around">
      </i-button-bar>
    </template>
  </mapContent>
</template>

<script>
import mapContent from '@/modules/module-gridManagement/components/mapContent';
import operateTitle from '@/modules/module-gridManagement/components/operateTitle';
import subTitle from '@/modules/module-gridManagement/components/subTitle';
import mapFormItem from '@/modules/module-gridManagement/components/mapFormItem';
import kxMap from '@/utils/kxMap';
import * as turf from '@turf/turf';
import { judgeContains, judgeDisjoint } from '@/utils/kxMap/mapUtils';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
import mapMixin from '@/mixins/mapMixin.vue';

export default ({
  components: {
    mapContent,
    operateTitle,
    subTitle,
    mapFormItem
  },
  mixins: [mapMixin],
  props: {
    mapData: {
      type: Object
    },
    orgOptions: {
      type: Array,
    }
  },
  data() {
    return {
      subForm: {
        gridList: [],
      },
      adjustList: [],
      btnLists: [
        {
          cname: '确定',
          ename: 'confirmHandle',
          type: 'primary',
          disabled: () => this.disabled
        },
        {
          cname: '取消',
          ename: 'cancelHandle',
        },
      ],
      disabled: false,
      viewOptions: {
        padding: [30, 330, 30, 30],
        duration: 300
      },
    }
  },
  watch: {
    mapData: {
      handler(value, o) {
        this.$nextTick(() => {
          this.initPolygon()
        })
      },
      deep: true,
      immediate: true,
    }
  },
  mounted() {
    this.initMap()
  },
  methods: {
    async initMap() {
      await this.initMapBase()

      this.fatherLayer = new kxMap.PolygonLayer(this.olMap, { zIndex: 2 }, 'father')
      this.adjustLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 })

      this.adjustLayer.setDrawStyle({ strokeColor: '#18A8F5', lineDash: [] })
      this.adjustLayer.addSelect()
      this.adjustLayer.addModify()

      this.fatherLayer.addSnap()
      this.adjustLayer.addSnap()

      this.control = new this.olMap.BaseViewControl(this.olMap)
      this.adjustLayer.modifyEndCallBack = (e) => {
        const features = e.features.array_
        for (let item of features) {
          const { code } = item.getProperties()
          !this.adjustList.includes(code) && this.adjustList.push(code)
        }
      }



    },
    initPolygon() {
      const { parentCode, typeCode } = this.mapData
      const param = {
        areaCode: parentCode,
        typeCode,
        jurisdiction: 2
      }
      const infoParam = {
        typeCode,
        parentCode,
      }
      service.getCoordinatesByCode(param).then(res => {
        const { data } = res
        let fatherPolygon = []
        let borderPolygon = []
        turf.featureEach(data, function (currentFeature, featureIndex) {
          if (featureIndex === 0) {
            fatherPolygon.push(currentFeature)
          } else {
            borderPolygon.push(currentFeature)
          }
        });
        this.fatherPolygon = turf.featureCollection(fatherPolygon)
        this.borderPolygon = turf.featureCollection(borderPolygon)
        const extent = turf.bbox(this.fatherPolygon)

        this.fatherLayer.clear()
        this.adjustLayer.clear()
        this.fatherLayer.addShadyFeature(this.fatherPolygon)
        this.adjustLayer.addGeoFeature(this.borderPolygon)

        this.control.setView(extent, this.viewOptions)
      })

      service.getPaAddrInfoTypeList(infoParam).then(res => {
        const { data } = res
        this.adjustList = []
        this.subForm.gridList = data
      })
    },
    changeHandle(info, index) {
      const { name } = info
      turf.propEach(this.borderPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.name = name
        }
      });
      this.adjustLayer.clear();
      this.adjustLayer.addGeoFeature(this.borderPolygon)
    },
    focusHandle(info, index) {
      turf.propEach(this.borderPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.fillColor = [255, 0, 0, 0.2]
        } else {
          currentProperties.fillColor = [0, 0, 0, 0.2]
        }
      });
      this.adjustLayer.clear();
      this.adjustLayer.addGeoFeature(this.borderPolygon)
    },
    blurHandle() {
      turf.propEach(this.borderPolygon, function (currentProperties) {
        currentProperties.fillColor = [0, 0, 0, 0.2]
      });
      this.adjustLayer.clear();
      this.adjustLayer.addGeoFeature(this.borderPolygon)
    },
    confirmHandle() {
      const curFeatures = this.adjustLayer.getDataCoordinates()
      const curProperties = this.adjustLayer.getDataProperties()
      const collection = turf.featureCollection(curFeatures.map((item, index) => {
        return turf.feature(item)
      }))
      for (let item of curFeatures) {
        const feature = turf.feature(item)
        if (!judgeDisjoint(feature, collection)) {
          this.$message.error(`多边形存在相交!`)
          return
        } else if (!judgeContains(feature, this.fatherPolygon)) {
          this.$message.error(`所绘制的多边形不在所在区域!`);
          return
        }
      }

      let param = []
      this.subForm.gridList.forEach((item, index) => {
        curProperties.forEach((el, i) => {
          if (el.name === item.name) {
            const coordinates = curFeatures[i].type === 'Polygon' ? [curFeatures[i].coordinates] : curFeatures[i].coordinates
            let obj = {
              ...item,
              coordinates: JSON.stringify(coordinates),
              isMaintained: this.adjustList.includes(item.code) ? 1 : 0
            }
            param.push(obj)
          }
        })
      })
      this.disabled = true
      service.updateBatch(param).then(() => {
        this.$modal.msgSuccess('边界调整成功')
        this.disabled = false
        this.$emit('clearTree', true)
      }).catch(() => {
        this.disabled = false
      })

    },
    cancelHandle() {
      this.$emit('cancelHandle', this.mapData)
    },
    clickHandle(type) {
      this[type].call()
    }
  },
})
</script>

<style lang="scss" scoped>
#olMap {
  width: 100%;
  height: 100%;
}
</style>