<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="网格平分"></operateTitle>
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
                       :orgOptions="orgOptions" @changeHandle="(info)=>changeHandle(info,index)"
                       @focusHandle="(info)=>focusHandle(info,index)" @blurHandle="blurHandle">
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
      drawBtnLists: [
        {
          cname: '分割',
          ename: 'startDraw',
        },
        {
          cname: '编辑',
          ename: 'editDraw',
        },
        {
          cname: '结束分割',
          ename: 'endDraw',
        },
      ],
      btnLists: [
        {
          cname: '确定',
          ename: 'confirmHandle',
          type: 'primary',
          disabled: () => this.disabled || this.subForm.gridList.length == 0
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
      menuList: [],
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

      this.currentLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 })
      this.currentLayer.setDrawStyle({ strokeColor: '#18A8F5', lineDash: [] })
      this.currentLayer.addSelect()
      this.currentLayer.addModify()
      this.control = new this.olMap.BaseViewControl(this.olMap)

      this.overlay = new kxMap.KOverlay(this.olMap, 'overlay')

      this.initContentmenuEvent()
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

    clickOverlay(type) {
      switch (type) {
        case 'delete':
          this.currentLayer.removeFeature(this.curFeature)
          break
        default:
          break
      }
      this.overlay.setPosition(undefined)
    },
    initPolygon() {

      const { code, typeCode } = this.mapData
      const param = {
        typeCode,
        areaCode: code,
        jurisdiction: 0
      }
      service.getCoordinatesByCode(param).then(res => {
        const { data } = res
        this.fatherPolygon = data

        this.subForm.gridList = []
        this.fatherLayer.clear()
        this.currentLayer.clear()
        this.fatherLayer.addGeoFeature(this.fatherPolygon)
        const extent = turf.bbox(this.fatherPolygon)

        this.control.setView(extent, this.viewOptions)
      })
    },
    changeHandle(info, index) {
      const { name } = info
      turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.name = name
        }
      });
      this.fatherLayer.clear();
      this.fatherLayer.addGeoFeature(this.fatherPolygon)
    },
    focusHandle(info, index) {
      turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.fillColor = [255, 0, 0, 0.2]
        } else {
          currentProperties.fillColor = [0, 0, 0, 0.2]
        }
      });
      this.fatherLayer.clear();
      this.fatherLayer.addGeoFeature(this.fatherPolygon)
    },
    blurHandle() {
      turf.propEach(this.fatherPolygon, function (currentProperties) {
        currentProperties.fillColor = [0, 0, 0, 0.2]
      });
      this.fatherLayer.clear();
      this.fatherLayer.addGeoFeature(this.fatherPolygon)
    },
    // 绘制操作
    startDraw() {
      this.currentLayer.setDrawType('Polygon')
      this.currentLayer.startDraw()
    },
    endDraw() {
      this.getDivisionGeo()
      this.fatherLayer.clear()
      this.currentLayer.clear()
      this.fatherLayer.addGeoFeature(this.fatherPolygon)
    },
    // 提交操作
    confirmHandle() {

      this.$refs.gridForm.validate((v) => {
        if (v) {
          const geoData = this.fatherLayer.getDataCoordinates()
          let param = []
          this.subForm.gridList.forEach((item, index) => {
            const coordinates = geoData[index].type === 'Polygon' ? [geoData[index].coordinates] : geoData[index].coordinates
            let obj = {
              ...item,
              coordinates: JSON.stringify(coordinates)
            }
            param.push(obj)
          })
          this.disabled = true
          service.splitGrid(param).then(res => {
            this.$modal.msgSuccess('平分成功')
            this.disabled = false
            this.$emit('clearTree', true)
          }).catch(() => {
            this.disabled = false
          })
        }
      })
    },
    cancelHandle() {
      this.$emit('cancelHandle', this.mapData)
    },
    clickHandle(type) {
      this[type].call()
    },
    editDraw() {
      this.ifDraw = false
      this.currentLayer.setDrawType('None')
      this.currentLayer.startDraw()
      // this.refreshSnap()
    },
    getDivisionGeo() {
      const features = this.currentLayer.getDataCoordinates();
      const _ = this
      let base = null
      let rst = [];
      // 计算重合部分
      features.forEach((v1, index) => {
        let poly1 = turf.feature(v1);
        turf.featureEach(this.fatherPolygon, function (currentFeature) {
          if (turf.getType(currentFeature) === "MultiPolygon") {
            let flatten = turf.flatten(currentFeature);
            let uArr = []
            turf.featureEach(flatten, (cf) => {
              let i = turf.intersect(poly1, cf)
              i && uArr.push(i)
            })
            if (uArr.length > 0) {
              rst.push(_.unionBoundary(uArr))
            }
          } else {
            let intersection = turf.intersect(poly1, currentFeature);
            intersection && rst.push(intersection);
          }
        });
        base = this.fatherPolygon.features
      });
      // 合并
      let union = this.unionBoundary(rst);
      this.currentLayer.doneDraw();
      this.currentLayer.clear();
      if (union) {
        let difference = base.map(item => {
          return turf.difference(item, union)
        });

        this.fatherPolygon = turf.featureCollection([...difference, ...rst])
        this.subForm.gridList = []
        const { addrLevel, code } = this.mapData
        turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
          currentProperties.name = `网格${featureIndex + 1}`
          let obj = {
            ..._.mapData,
            parentCode: code,
            addrLevel: addrLevel,
            name: `网格${featureIndex + 1}`
          }
          _.subForm.gridList.push(obj)
        });
      }
    },
    unionBoundary(polygons) {
      const unionGeo = polygons.reduce((pre, cur) => {
        const unionPol = turf.union(pre, cur);
        return unionPol;
      }, polygons[0]);
      return unionGeo;
    }
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