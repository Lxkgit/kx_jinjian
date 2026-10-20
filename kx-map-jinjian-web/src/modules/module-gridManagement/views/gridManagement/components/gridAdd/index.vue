<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="网格新增"></operateTitle>
    </template>
    <template #operate>
      <subTitle title="绘制操作"></subTitle>
      <i-button-bar size="small" :plain="false" :btn-lists="drawBtnLists" @click="clickHandle"
                    ref="btnBar">
      </i-button-bar>
      <subTitle title="下级网格"></subTitle>
      <el-form ref="gridForm" :model="subForm" label-width="100px">
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
    <div class="overlay-wrapper">
      <div id="overlay" class="menu-list">
        <div class="menu-item" v-for="item in menuList" :key="item.value"
             @click="clickOverlay(item.value)">
          <span>{{ item.label }}</span>
        </div>
      </div>
    </div>
  </mapContent>
</template>

<script>
import mapContent from '@/modules/module-gridManagement/components/mapContent';
import operateTitle from '@/modules/module-gridManagement/components/operateTitle';
import subTitle from '@/modules/module-gridManagement/components/subTitle';
import mapFormItem from '@/modules/module-gridManagement/components/mapFormItem';
import kxMap from '@/utils/kxMap';
import { judgeContains, judgeDisjoint } from '@/utils/kxMap/mapUtils';
import * as turf from '@turf/turf';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
import mapMixin from '@/mixins/mapMixin.vue';

const MENU = {
  delete: '删除',
}

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
      curFeature: null,
      drawBtnLists: [
        {
          cname: '新增',
          ename: 'addDraw',
        },
        {
          cname: '编辑',
          ename: 'editDraw',
        },
      ],
      menuList: [],
      btnLists: [
        {
          cname: '确定',
          ename: 'confirm',
          type: 'primary',
          disabled: () => this.disabled
        },
        {
          cname: '取消',
          ename: 'cancel',
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
      this.brotherLayer = new kxMap.PolygonLayer(this.olMap, { zIndex: 3 }, 'brother')
      this.currentLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 }, false, false)

      this.currentLayer.setDrawStyle({ strokeColor: '#18A8F5', lineDash: [] })
      this.currentLayer.addSelect()
      this.currentLayer.addModify()
      this.initDrawEnd()

      this.control = new this.olMap.BaseViewControl(this.olMap)
      this.overlay = new kxMap.KOverlay(this.olMap, 'overlay')

      this.initContentmenuEvent()
      this.initSingleclickEvent()

    },
    // 右键事件
    initContentmenuEvent() {
      this.contentmenu = new kxMap.ContentmenuEvent(this.olMap)
      this.contentmenu.addEvent((e, coordinate, features) => {
        e.preventDefault();
        this.menuList = []
        this.currentLayer.setDrawActive(false)
        if (features.length > 0) {
          this.curFeature = features[0]
        } else {
          this.curFeature = null
        }
        Object.keys(MENU).forEach(key => {
          let obj = {
            label: MENU[key],
            value: key,
          }
          if ((this.curFeature && key === 'delete') || (!this.ifDraw && key === 'add') || (this.ifDraw && key === 'edit')) {
            this.menuList.push(obj)
          }
        })
        this.overlay.setPosition(coordinate)
      }, ['draw'])
    },
    // 点击事件
    initSingleclickEvent() {
      this.singleclick = new kxMap.SingleclickEvent(this.olMap)
      this.singleclick.addEvent([], () => {
        this.currentLayer.setDrawActive(true)
        this.overlay.setPosition(undefined)
      })
    },
    // 绘制完结事件
    initDrawEnd() {
      this.currentLayer.drawEndCallBack = (e) => {
        const { feature } = e
        const coordinates = feature.getGeometry().getCoordinates()
        const geoData = turf.polygon(coordinates)
        if (!judgeContains(geoData, this.fatherPolygon)) {
          this.$message.error(`所绘制的多边形超出边界区域!`);
        } else if (!judgeDisjoint(geoData, this.borderPolygon)) {
          this.$message.error(`所绘制的多边形与现有区域重叠!`)
        } else {
          if (this.subForm.gridList.length === 0) {
            const { code, addrLevel } = this.mapData
            let obj = {
              ...this.mapData,
              name: '新增网格',
              parentCode: code,
              addrLevel: addrLevel + 1,
            }
            this.subForm.gridList.push(obj)
          }
        }
      }
    },
    initPolygon() {
      const { code, typeCode } = this.mapData
      const param = {
        areaCode: code,
        typeCode,
        jurisdiction: 2
      }

      service.getCoordinatesByCode(param).then(res => {
        const { data } = res
        let fatherPolygon = []
        let borderPolygon = []
        turf.featureEach(data, function (currentFeature, featureIndex) {
          if (currentFeature.properties.code === code) {
            fatherPolygon.push(currentFeature)
          } else {
            borderPolygon.push(currentFeature)
          }
        });
        this.fatherPolygon = turf.featureCollection(fatherPolygon)
        this.borderPolygon = turf.featureCollection(borderPolygon)
        const extent = turf.bbox(this.fatherPolygon)

        this.fatherLayer.clear()
        this.brotherLayer.clear()
        this.currentLayer.clear()
        this.ifDraw = false
        this.fatherLayer.addShadyFeature(this.fatherPolygon)
        this.brotherLayer.addGeoFeature(this.borderPolygon)
        this.control.setView(extent, this.viewOptions)
      })

    },
    addDraw() {
      this.ifDraw = true
      this.currentLayer.setDrawType('Polygon')
      this.currentLayer.startDraw()
      this.refreshSnap()
    },
    editDraw() {
      this.ifDraw = false
      this.currentLayer.setDrawType('None')
      this.currentLayer.startDraw()
      this.refreshSnap()
    },
    clickOverlay(type) {
      switch (type) {
        case 'delete':
          this.currentLayer.removeFeature(this.curFeature)
          if (this.currentLayer.getDataCoordinates().length === 0) {
            this.subForm.gridList = []
          }
          break
        case 'add':
          this.ifDraw = true
          this.currentLayer.setDrawType('Polygon')
          this.currentLayer.startDraw()
          this.refreshSnap()
          break
        case 'edit':
          this.ifDraw = false
          this.currentLayer.setDrawType('None')
          this.currentLayer.startDraw()
          this.refreshSnap()
          break
        default:
          break
      }
      this.overlay.setPosition(undefined)
    },
    confirm() {
      const curFeatures = this.currentLayer.getDataCoordinates()
      const collection = turf.featureCollection(curFeatures.map(item => {
        return turf.feature(item)
      }))
      for (let item of curFeatures) {
        const feature = turf.feature(item)
        if (!judgeDisjoint(feature, this.borderPolygon)) {
          this.$message.error(`所绘制的多边形存在与其他区域相交!`)
          return
        } else if (!judgeDisjoint(feature, collection)) {
          this.$message.error(`所绘制的多边形存在相交!`)
          return
        } else if (!judgeContains(feature, this.fatherPolygon)) {
          this.$message.error(`所绘制的多边形不在所在区域!`);
          return
        }
      }
      const coordinates = curFeatures.map(item => {
        return item.coordinates
      })

      this.$refs.gridForm.validate((v) => {
        if (v) {
          const param = {
            ...this.subForm.gridList[0],
            coordinates: JSON.stringify(coordinates)
          }
          this.disabled = true
          service.addChild(param).then(res => {
            this.$modal.msgSuccess('新增成功')
            this.disabled = false
            this.$emit('clearTree', true)
          }).catch(() => {
            this.disabled = false
          })
        }
      })

    },
    cancel() {
      this.$emit('cancelHandle', this.mapData)
    },
    refreshSnap() {
      this.fatherLayer.removeSnap()
      this.brotherLayer.removeSnap()
      this.fatherLayer.addSnap()
      this.brotherLayer.addSnap()
    },
    setExtent(layer) {
      layer.setExtent()
      this.control.setView(layer.extent, this.viewOptions)
    },
    clickHandle(type) {
      this[type].call()
    },
  },
})
</script>

<style lang="scss" scoped>
#olMap {
  width: 100%;
  height: 100%;
}
.overlay-wrapper {
  display: none;
}
.menu-list {
  width: 120px;

  .menu-item {
    cursor: pointer;
    text-align: center;
    font-size: 16px;
    line-height: 2;
    border-radius: 4px;
    background: #fff;
    padding: 4px 0;
    &:hover {
      margin: 0 4px;
      box-sizing: content-box;
      background: #1890ff;
      color: #fff;
    }
  }
}
</style>