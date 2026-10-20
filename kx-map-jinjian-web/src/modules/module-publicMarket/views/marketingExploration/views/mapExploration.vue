<template>
  <div class="map-exploration-wrapper">
    <div id="myMap" />
    <div class="exploration-wrapper">
      <el-input placeholder="请输入内容" v-model="searchWord" class="search-input">
        <el-button slot="append" icon="el-icon-search" @click="searchHandle"></el-button>
      </el-input>
      <expandBox v-show="(!isDetail)&&showExpand">
        <indexView :indexData="indexData" v-model="activeIndex" v-if="(!isDetail)&&showExpand"
                   @clickHandle="indexClick">
        </indexView>
        <pageList v-show="(!isDetail)&&showList" :tableData="tableData"
                  :pageSize="formPage.pageSize" :pageNum="formPage.pageNum" :total="total"
                  @handleSizeChange="handleSizeChange" @handleCurrentChange="handleCurrentChange">
          <template #default="{row}">
            <item-company :info="row" @itemCick="handleItemClick(row)" />
          </template>
        </pageList>
      </expandBox>
      <!-- 企业详情 -->
      <expandBox v-if="isDetail&&showExpand">
        <div class="sticky">
          <div class="back" @click="isDetail=false;changeDataFun()">
            <span class="el-icon-back"></span>
            <span class="title">返回查看范围内所有企业</span>
          </div>
          <headerCompany :info="currentCompany" @handleClick="companyDetail"></headerCompany>

        </div>
        <pageList :tableData="opportunityList" :pageSize="formPage.pageSize"
                  :pageNum="formPage.pageNum" :total="total">
          <template #default="{row}">
            <item-opportunity :info="row" />
          </template>
        </pageList>
      </expandBox>
    </div>
  </div>
</template>

<script>
import kxMap from '@/utils/kxMap';
import axios from 'axios'
import expandBox from '../components/expandBox'
import indexView from '../components/indexView'
import pageList from '../components/pageList'
import itemCompany from '../components/itemCompany.vue'
import itemOpportunity from '../components/itemOpportunity.vue'
import headerCompany from '../components/headerCompany.vue'
import { getToolTip } from '../utils/tooltip'
import * as turf from '@turf/turf';
import {gcj02towgs84} from '@/utils/Helper';
export default ({
  data() {
    return {
      searchWord: '',
      mockKey: '',
      formPage: {
        pageSize: 10,
        pageNum: 1,
      },
      total: 0,
      tableData: [],
      opportunityList: [],
      isDetail: false,
      status: 1,
      showExpand: false,
      showList: false,
      currentCompany: {},
      activeIndex: null,
      indexData: [
        { label: '企业', value: '', unit: '', key: 'companyCount', color: '#00749D' },
        { label: '新商机', value: '', unit: '', key: 'businessClueCount', color: '#2258FE' },
        { label: '存客商机', value: '', unit: '', key: 'businessClueUserCount', color: '#7D4DFF' },
        { label: '新增企业', value: '', unit: '', key: 'newEnterpriseCount', color: '#DB5E13' },
        // { label: '园区', value: '', unit: '', key: 'parkSearchCount', color: '#936600' },
        // { label: '协会', value: '', unit: '', key: 'associationCount', color: '#145A1E' },
      ]
    }
  },
  components: {
    expandBox,
    indexView,
    pageList,
    itemCompany,
    itemOpportunity,
    headerCompany
  },
  mounted() {
    this.activeIndex = null
    this.initMap()
  },
  methods: {
    async initMap() {
      const { data: config } = await axios.get('/map.json')
      this.olMap = new kxMap.KMap('myMap', {
        center: [120.7, 31.65],
        zoom: 8,
        minZoom: 3,
        maxZoom: 20,
      }, {
        // url:config.gd_normal
        url: (x, y, z) =>
          config.bd_normal.replace('{x}', x).replace('{y}', y).replace('{z}', z),
        tileType: '2'
      })

      this.centerLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 4 }, 'center')
      this.pointLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 3 }, 'other')
      this.indexLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 3 }, 'index')
      this.circleLayer = new kxMap.CircleLayer(this.olMap, { zIndex: 2 })

      this.control = new this.olMap.BaseViewControl(this.olMap)

      // 中心点样式
      const centerStyle = [
        {
          text: {
            fill: {
              color: "#fff",
            },
            backgroundFill: {
              color: '#393A51'
            },
            padding: [3, 17, 3, 17],
            text: (p) => `当前位置: ${wrap(p.name, 13)}`,
            overflow: 'reject-overlapping',
            offsetY: (p) => 35 + 12 * Math.floor(p.name.length / 13),
          },
          zIndex: 2,
        },
        {
          icon: {
            scale: 0.2,
            src: require('@/modules/module-publicMarket/views/marketingExploration/assets/center.png'),
          },
          zIndex: 1,
        },
      ]
      this.centerLayer.setStyle(centerStyle)
      // 范围样式
      const circleStyle = {
        fill: {
          color: "#337CFF22",
        },
        stroke: {
          color: "#337CFF",
          width: 1,
        },
      }
      this.circleLayer.setStyle(circleStyle)
      // 信息样式
      const indexStyle = [
        {
          text: {
            fill: {
              color: "#fff",
            },
            text: (p) => p.label,
            offsetY: 8,
          },
          zIndex: 2,
        },
        {
          text: {
            fill: {
              color: "#fff",
            },
            text: (p) => {
              return p.number.toString()
            },
            offsetY: -8,
          },
          zIndex: 2,
        },
        {
          circle: {
            fill: {
              color: (p) => `${p.color}`,
            },
            radius: 30,
          },
        },
      ]
      this.indexLayer.setStyle(indexStyle)
      function wrap(data, maxLength) {
        let res = ''
        if (data.length > maxLength) {
          let number = Math.fround(data.length / maxLength)
          let arr = []
          for (let i = 0; i < number; i++) {
            arr.push(data.substring(i * maxLength, (i + 1) * maxLength))
          }
          res = arr.join('\n')
        } else {
          res = data
        }
        return res
      }
      this.initSingleclick()
    },
    //点击事件
    initSingleclick(){
      const singleclick = new kxMap.SingleclickEvent(this.olMap)
      singleclick.addEvent(['index','other'], (e, features) => {

        if (features.length > 0) {
          const [feature] = features
          const properties = feature.getProperties()
          if (properties.vectorType) {
            switch (properties.vectorType) {
              case 'index':
                this.activeIndex = properties.index
                this.indexClick(properties.index)
              break
              case 'other':
                this.handleItemClick(properties)
              break
              default:
              break
            }
          } else {
            // 聚合  本例中没有
          }
        }
      })
    },
    dataFun() {
      this.iterator = this.initData()
      this.iterator.next()
    },
    changeDataFun() {
      this.creditIter = this.changeData()
      this.creditIter.next()
    },
    * initData() {
      yield this.getIndexData()
      yield this.initCenter()
    },
    * changeData() {
      yield this.getIndexData()
      yield this.initCenter()
      yield this.getPointData()
    },
    initCenter() {
      require(`../mock/${this.mockKey}/position`).default().then(res => {
        const { data } = res
        // const coordinate = [data.lon, data.lat]
        const coordinate = gcj02towgs84(data.lon,data.lat)
        const pointer = {
          coordinate,
          name: data.name
        }
        this.centerLayer.clear()
        this.centerLayer.addFeature(pointer)


        let distance = 2000;
        let n = this.indexData.length
        let p = 360 / n
        let bearings = [];
        for (let i = 0; i < n; i++) {
          bearings.push(p * i)
        }
        let options = { units: 'meters' };
        let indexPoints = this.indexData.map((item, i) => {
          return {
            ...item,
            index:i+1,
            coordinate: turf.getCoord(turf.destination(turf.point(coordinate), distance, bearings[i], options))
          }
        })

        this.indexLayer.clear()
        this.indexLayer.addFeature(indexPoints)

        const option = { units: 'meters' }
        const circle = turf.circle(coordinate, data.radius, option)
        this.circleLayer.clear()
        this.circleLayer.addGeoFeature(circle)
        this.circleLayer.setExtent()
        this.control.setView(this.circleLayer.extent)

        this.creditIter && this.creditIter.next()
      })
    },
    getIndexData() {
      require(`../mock/${this.mockKey}/getVeidaoCount`).default().then(res => {
        const { data } = res
        this.indexData.forEach(item => {
          if (data[item.key] > 9999) {
            item.value = Math.floor((data[item.key] / 10000))
            item.unit = '万'
          } else {
            item.value = data[item.key]
            item.unit = ''
          }
          item.number = data[item.key]
        })

        this.iterator && this.iterator.next()
        this.creditIter && this.creditIter.next()
      })
    },
    getPointData() {
      let pointerStyle
      let points
      let start = (this.formPage.pageNum - 1) * this.formPage.pageSize
      let end = this.formPage.pageNum * this.formPage.pageSize
      this.indexLayer.clear()
      switch (this.activeIndex) {
        case 1:
          require(`../mock/${this.mockKey}/commonSearch`).default().then(res => {
            const { data: partOneData } = res
            points = partOneData.items.slice(start, end).map(item => {
              return {
                ...item,
                // coordinate: [item.longitude, item.latitude],
                coordinate: gcj02towgs84(item.longitude,item.latitude),
                name: `${limit(item.name, 10)}${item.boCount && `(${item.boCount}条商机)`}`,
              }
            })
            this.total = partOneData.total
            this.tableData = partOneData.items.slice(start, end)
            pointerStyle = {
              icon: {
                img: (p) => getToolTip(p.name, 14, "#fff", "#1890FF").canvas,
                imgSize: (p) => [
                  getToolTip(p.name, 14, "#fff", "#1890FF").w,
                  getToolTip(p.name, 14, "#fff", "#1890FF").h,
                ],
              }
            }
            this.pointLayer.setStyle(pointerStyle)

            this.pointLayer.clear()
            this.pointLayer.addFeature(points)
          })

          break
        case 2:
          require(`../mock/${this.mockKey}/getLocationMarketBusiness`).default().then(res => {
            const { data: partTwoData } = res
            points = partTwoData.items.slice(start, end).map(item => {
              return {
                ...item,
                // coordinate: [item.gdLocation[0].lon, item.gdLocation[0].lat],
                coordinate: gcj02towgs84(item.gdLocation[0].lon,item.gdLocation[0].lat),
                name: `${limit(item.name, 10)}${item.count && `(${item.count}条商机)`}`,
              }
            })
            this.total = partTwoData.total
            this.tableData = partTwoData.items.slice(start, end).map(item => {
              return {
                ...item,
                boCount: item.count,
                regCapiDesc: item.regCapi,
                status: item.newStatus,
                type: 2,
                pcdName: item.province + item.city
              }
            })
            pointerStyle = {
              icon: {
                img: (p) => getToolTip(p.name, 14, "#fff", "#1890FF").canvas,
                imgSize: (p) => [
                  getToolTip(p.name, 14, "#fff", "#1890FF").w,
                  getToolTip(p.name, 14, "#fff", "#1890FF").h,
                ],
              }
            }
            this.pointLayer.setStyle(pointerStyle)
            this.pointLayer.clear()
            this.pointLayer.addFeature(points)
          })

          break
        case 3:
          require(`../mock/${this.mockKey}/currentMarket`).default().then(res => {
            const { data: partThreeData } = res
            points = partThreeData.items.slice(start, end).map(item => {
              return {
                ...item,
                // coordinate: [item.gdLocation[0].lon, item.gdLocation[0].lat],
                coordinate: gcj02towgs84(item.gdLocation[0].lon,item.gdLocation[0].lat),
                name: `${limit(item.name, 10)}${item.count && `(${item.count}条商机)`}`,
              }
            })
            this.total = partThreeData.total
            this.tableData = partThreeData.items.slice(start, end).map(item => {
              return {
                ...item,
                boCount: item.count,
                regCapiDesc: item.regCapi,
                status: item.newStatus,
                type: 2,
                pcdName: item.province + item.city
              }
            })
            pointerStyle = {
              icon: {
                img: (p) => getToolTip(p.name, 14, "#fff", "#7D4DFF").canvas,
                imgSize: (p) => [
                  getToolTip(p.name, 14, "#fff", "#7D4DFF").w,
                  getToolTip(p.name, 14, "#fff", "#7D4DFF").h,
                ],
              }
            }
            this.pointLayer.setStyle(pointerStyle)
            this.pointLayer.clear()
            this.pointLayer.addFeature(points)
          })
          break
        case 4:
          require(`../mock/${this.mockKey}/noNeedCheckSearch`).default().then(res => {
            const { data: partFourData } = res
            points = partFourData.items.slice(start, end).map(item => {
              return {
                ...item,
                // coordinate: [item.gdLocation[0].lon, item.gdLocation[0].lat],
                coordinate: gcj02towgs84(item.gdLocation[0].lon,item.gdLocation[0].lat),
                name: `${limit(item.name, 10)}${item.boCount && `(${item.boCount}条商机)`}`,
              }
            })
            this.total = partFourData.total
            this.tableData = partFourData.items.slice(start, end)
            pointerStyle = {
              icon: {
                img: (p) => getToolTip(p.name, 14, "#fff", "#1890FF").canvas,
                imgSize: (p) => [
                  getToolTip(p.name, 14, "#fff", "#1890FF").w,
                  getToolTip(p.name, 14, "#fff", "#1890FF").h,
                ],
              }
            }
            this.pointLayer.setStyle(pointerStyle)
            this.pointLayer.clear()
            this.pointLayer.addFeature(points)
          })
          break
        default:
          break
      }

      function limit(name, maxLength) {
        let res
        if (name.length > maxLength) {
          res = name.substring(0, maxLength) + '...'
        } else {
          res = name
        }
        return res
      }
    },
    getBusinessData(item) {
      const point = {
        ...item,
        // coordinate: [item.gdLocation[0].lon, item.gdLocation[0].lat],
        coordinate: gcj02towgs84(item.gdLocation[0].lon,item.gdLocation[0].lat),
        name: `${limit(item.name, 10)}${item.boCount && `(${item.boCount}条商机)`}`,
      }
      const pointerStyle = {
        icon: {
          img: (p) => getToolTip(p.name, 14, "#fff", "#F5573E").canvas,
          imgSize: (p) => [
            getToolTip(p.name, 14, "#fff", "#F5573E").w,
            getToolTip(p.name, 14, "#fff", "#F5573E").h,
          ],
        }
      }
      this.pointLayer.setStyle(pointerStyle)
      this.pointLayer.clear()
      this.pointLayer.addFeature(point)

      function limit(name, maxLength) {
        let res
        if (name.length > maxLength) {
          res = name.substring(0, maxLength) + '...'
        } else {
          res = name
        }
        return res
      }
    },
    searchHandle() {
      // 1. 广西壮族自治区桂林市临桂区公园北路8号  ——桂林金融大厦
      // 2. 广西壮族自治区桂林市临桂区西城南路5号麓湖国际社区B区6幢1-2层05号 ——临桂麓湖国际小微支行
      // 3. 广西壮族自治区桂林市临桂区西城中路69号——桂林市政府
      // const targetKey = ['康旭', '8号', '5号', '69号']
      const targetKey = ['康旭', '金融', '小微', '桂林']
      targetKey.forEach(item => {
        if (this.searchWord.includes(item)) {
          this.mockKey = item
          this.showExpand = true
          this.activeIndex =  null
          this.indexClick(null)
          this.isDetail=false;
          this.dataFun()
        }
      })
    },
    handleSizeChange(val) {
      this.formPage.pageSize = val
      this.changeDataFun()
    },
    handleCurrentChange(val) {
      this.formPage.pageNum = val
      this.changeDataFun()
    },
    indexClick(index) {
      if (!!index) {
        this.showList = true
        this.formPage.pageNum = 1
        this.formPage.pageSize = 10
        this.$nextTick(() => {
          this.changeDataFun()
        })
      } else {
        this.showList = false
        this.pointLayer.clear()
        this.tableData = []
        this.dataFun()
      }

    },
    // 企业列表项点击
    handleItemClick(v) {
      this.isDetail = true
      this.total = +v.boCount || +v.count || 0
      if (!this.total) {
        this.opportunityList = []
      } else {
        let tempD = []
        let page = this.total > 50 ? 50 : this.total
        for (let i = 0; i < page; i++) {
          tempD.push({
            id: i,
            type: 2,
            star: Math.ceil(Math.random() * 10),
            date: v.startDate,
            clueTypeCode: v.clueTypeCode,
            clueType: v.clueType,
            desc: v.description
          })
        }
        this.opportunityList = tempD
      }
      this.currentCompany = v
      this.getBusinessData(v)
    },
    // 企业名称点击  法任名称点击
    companyDetail({ type, data }) {
      switch (type) {
        case 'name':
          this.$router.push(`/Marketing/outLink/detail?eid=${data.eid}`)
          break;
        case 'operName':
          console.log(data.operName)

          break;

      }
    }
  },
})
</script>

<style lang="scss" scoped>
.map-exploration-wrapper {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  #myMap {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    z-index: 1;
  }
  .exploration-wrapper {
    position: absolute;
    width: 30%;
    top: 20px;
    left: 20px;
    z-index: 9;
  }
  .sticky {
    position: sticky;
    top: 0;
    background-color: #fff;
  }
  .back {
    font-size: 14px;
    padding: 10px 0 5px 5px;
    cursor: pointer;
    .el-icon-back {
      color: #1a53ff;
      margin-right: 5px;
    }
    .title {
      font-weight: 600;
    }
  }
}
</style>