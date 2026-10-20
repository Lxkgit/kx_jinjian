<template>
  <div class="map-wrapper">
    <div id="olMap"></div>
    <div class="operate-wrapper">
      <el-button type="primary" @click="submit">提交</el-button>
      <el-button type="primary" @click="initPolygon">刷新</el-button>
    </div>
    <div class="overlay-wrapper">
      <div id="overlay" class="menu-list">
        <div class="menu-item" v-for="item in menuList" :key="item.value"
             @click="clickOverlay(item.value)">
          <span>{{ item.label }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import kxMap from '@/utils/kxMap';
import { judgeContains, judgeDisjoint } from '@/utils/kxMap/mapUtils';
import * as turf from '@turf/turf';
const MENU = {
  delete: '删除',
  add: '新增',
  edit: '编辑',
}
export default ({
  data() {
    return {
      ifDraw: false,
      feature: null,
      menuList: []
    }
  },
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      this.olMap = new kxMap.KMap('olMap')

      this.fatherLayer = new kxMap.PolygonLayer(this.olMap, { zIndex: 2 }, 'father')
      this.brotherLayer = new kxMap.PolygonLayer(this.olMap, { zIndex: 3 }, 'brother')
      this.currentLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 })

      this.currentLayer.addSelect()
      this.currentLayer.addModify()
      this.initDrawEnd()

      this.control = new this.olMap.BaseViewControl(this.olMap)
      this.overlay = new kxMap.KOverlay(this.olMap, 'overlay')

      this.initContentmenuEvent()
      this.initSingleclickEvent()

      this.initPolygon()
    },
    // 右键事件
    initContentmenuEvent() {
      this.contentmenu = new kxMap.ContentmenuEvent(this.olMap)
      this.contentmenu.addEvent((e, coordinate, features) => {
        e.preventDefault();
        this.menuList = []
        this.currentLayer.setDrawActive(false)
        if (features.length > 0) {
          this.feature = features[0]
        } else {
          this.feature = null
        }
        Object.keys(MENU).forEach(key => {
          let obj = {
            label: MENU[key],
            value: key,
          }
          if ((this.feature && key === 'delete') || (!this.ifDraw && key === 'add') || (this.ifDraw && key === 'edit')) {
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
        if (!judgeContains(coordinates, this.fatherPolygon)) {
          this.$message.error(`所绘制的多边形不在所在区域!`);
        }
      }
    },
    initPolygon() {
      this.fatherLayer.clear()
      this.brotherLayer.clear()
      // 绘制父多边形
      this.fatherPolygon = turf.randomPolygon(1,
        {
          bbox: [120.707643, 27.758743, 120.941778, 27.997378],
          max_radial_length: 0.1,
          num_vertices: 6,
        })
      this.fatherLayer.addShadyFeature(this.fatherPolygon)
      const extent = turf.bbox(this.fatherPolygon)
      // 绘制兄弟多边形
      this.borderPolygon = turf.randomPolygon(3, { bbox: extent, max_radial_length: 0.01 })
      this.brotherLayer.addGeoFeature(this.borderPolygon)
      this.control.setView(extent)

    },
    clickOverlay(type) {
      switch (type) {
        case 'delete':
          this.currentLayer.removeFeature(this.feature)
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
    submit() {
      const curFeatures = this.currentLayer.getDataCoordinates()
      for (let item of curFeatures) {
        const feature = turf.feature(item)
        if (!judgeDisjoint(feature, this.borderPolygon)) {
          this.$message.error(`所绘制的多边形存在与其他区域相交!`)
        }
      }
    },
    refreshSnap() {
      this.fatherLayer.removeSnap()
      this.brotherLayer.removeSnap()
      this.fatherLayer.addSnap()
      this.brotherLayer.addSnap()
    },
    setExtent(layer) {
      layer.setExtent()
      this.control.setView(layer.extent)
    }
  },
})
</script>

<style lang="scss" scoped>
.map-wrapper {
  width: 100%;
  height: 100%;
  position: relative;
  #olMap {
    width: calc(100% - 300px);
    height: 100%;
  }
  .operate-wrapper {
    position: absolute;
    width: 300px;
    height: 100%;
    top: 0;
    right: 0;
    background: #fff;
  }
  .overlay-wrapper {
    display: none;
  }
  .menu-list {
    width: 120px;
    background: #fff;
    border-radius: 4px;
    padding: 4px 0;
    .menu-item {
      cursor: pointer;
      text-align: center;
      font-size: 16px;
      line-height: 2;
      border-radius: 4px;
      &:hover {
        margin: 0 4px;
        box-sizing: content-box;
        background: #1890ff;
        color: #fff;
      }
    }
  }
}
</style>