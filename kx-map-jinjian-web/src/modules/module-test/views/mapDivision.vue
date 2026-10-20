<template>
  <div class="map-wrapper">
    <div id="olMap"></div>
    <div class="operate-wrapper">
      <el-button type="primary" @click="startDraw">分割</el-button>
      <el-button type="primary" @click="endDraw">结束分割</el-button>
      <el-form :model="gridForm" label-width="80px">

        <el-form-item v-for="(item,index) in gridForm.gridList" :key="index" label="区域名称"
                      :prop="`gridList.${index}.name`">
          <el-input v-model="item.name" @focus="focusHandle(index)" @blur="blurHandle()"
                    @change="changeHandle(index, item)">
          </el-input>
        </el-form-item>

      </el-form>
    </div>
  </div>
</template>

<script>
import kxMap from '@/utils/kxMap';
import * as turf from '@turf/turf';
export default ({
  data() {
    return {
      gridForm: {
        gridList: [
          { name: '' }
        ],
      }
    }
  },
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      this.olMap = new kxMap.KMap('olMap')

      this.currentLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 4 })
      this.temp = new kxMap.DrawLayer(this.olMap, { zIndex: 4 })

      this.control = new this.olMap.BaseViewControl(this.olMap)

      this.initPolygon()
    },
    initPolygon() {
      this.fatherPolygon = turf.randomPolygon(1,
        {
          bbox: [120.707643, 27.758743, 120.941778, 27.997378],
          max_radial_length: 0.1,
          num_vertices: 6,
        })
      this.currentLayer.addGeoFeature(this.fatherPolygon)
      const extent = turf.bbox(this.fatherPolygon)
      this.control.setView(extent)
    },
    changeHandle(index, { name }) {
      turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.name = name
        }
      });
      this.currentLayer.clear();
      this.currentLayer.addGeoFeature(this.fatherPolygon)
    },
    focusHandle(index) {
      turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
        if (featureIndex === index) {
          currentProperties.fillColor = [255, 0, 0, 0.2]
        } else {
          currentProperties.fillColor = [0, 0, 0, 0.2]
        }
      });
      this.currentLayer.clear();
      this.currentLayer.addGeoFeature(this.fatherPolygon)
    },
    blurHandle() {
      turf.propEach(this.fatherPolygon, function (currentProperties) {
        currentProperties.fillColor = [0, 0, 0, 0.2]
      });
      this.currentLayer.clear();
      this.currentLayer.addGeoFeature(this.fatherPolygon)
    },
    startDraw() {
      this.currentLayer.setDrawType('Polygon')
      this.currentLayer.startDraw()
    },
    endDraw() {
      this.getDivisionGeo()
      this.currentLayer.addGeoFeature(this.fatherPolygon)
    },
    getDivisionGeo() {
      const features = this.currentLayer.getDataCoordinates();
      const _ = this
      let base = null
      let rst = [];
      // 计算重合部分
      features.forEach((v1, index) => {
        if (index < this.fatherPolygon.features.length) {
          return;
        }
        let poly1 = turf.polygon(v1.coordinates);
        turf.featureEach(this.fatherPolygon, function (currentFeature) {
          if (turf.getType(currentFeature) === "MultiPolygon") {
            let flatten = turf.flatten(currentFeature);
            let uArr = []
            flatten.features.forEach((v2) => {
              let i = turf.intersect(poly1, v2);
              i && uArr.push(i)

            });
            rst.push(_.unionBoundary(uArr));
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
        this.gridForm.gridList = []
        turf.propEach(this.fatherPolygon, function (currentProperties, featureIndex) {
          currentProperties.name = currentProperties.name || `网格${featureIndex + 1}`
          let obj = {
            name: currentProperties.name || `网格${featureIndex + 1}`
          }
          _.gridForm.gridList.push(obj)
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
}
</style>