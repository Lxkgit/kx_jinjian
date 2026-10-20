<template>
  <div class="navigation-bar-wrapper" ref="wrapper">
    <Swiper class="navigation-list" :options="swiperOption" ref="mySwiper">
      <SwiperSlide class="navigation-slide" v-for="item in curNavList" :key="item.id">
        <div class="navigation-item" @click="clickHandle(item)">
          <div class="item-logo" :style="{'--background': randomColor()}">
            <img :src="item.src" alt="">
          </div>
          <span class="item-name">{{ item.label }}</span>
        </div>
      </SwiperSlide>
      <div class="swiper-pagination" slot="pagination"></div>
    </Swiper>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
import 'swiper/css/swiper.css'
export default ({
  components: {
    SwiperSlide,
    Swiper,
  },
  data() {
    return {
      colors: ['#5E81F4', '#3E4A95', '#6F86FF', '#44A5FF', '#4ED3B2'],
      navList: [
        { label: '网格体系管理', path: 'SystemManage', permi: 'gridManagement:systemManage:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-1.png') },
        { label: '辖区管理', path: 'AreaDistribute', permi: 'gridManagement:areaDistribute:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-2.png') },
        { label: '网格员管理', path: 'GriderDistribute', permi: 'gridManagement:griderDistribute:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-3.png') },
        { label: '标签管理', path: 'LabelManage', permi: 'groupManagement:labelManage:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-6.png') },
        { label: '客群管理', path: 'GroupManage', permi: 'groupManagement:groupManage:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-7.png') },
        { label: '待锚点', path: 'TobeAnchorPoint', permi: 'anchorManagement:tobeAnchorPoint:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-4.png') },
        { label: '已锚点', path: 'AnchoredPoint', permi: 'anchorManagement:anchoredPoint:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-5.png') },
        { label: '楼宇锚点', path: 'BuildAnchorPoint', permi: 'anchorManagement:buildAnchorPoint:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-9.png') },
        { label: '变更记录', path: 'ChangeRecord', permi: 'gridManagement:changeRecord:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-10.png') },
        { label: '日程管理', path: 'ScheduleManagement', permi: 'gridManagement:scheduleManagement:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-8.png') },
        { label: '对私公共池', path: 'CustomerBank', permi: 'customerManagement:customerBank:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-11.png') },
        { label: '对私行内客户', path: 'CustomerInside', permi: 'customerManagement:customerInside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-11.png') },
        { label: '对私行外客户', path: 'CustomerOutside', permi: 'customerManagement:customerOutside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-11.png') },
        { label: '商户公共池', path: 'MerchantBank', permi: 'merchantManagement:merchantBank:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-13.png') },
        { label: '商户行内客户', path: 'MerchantInside', permi: 'merchantManagement:merchantInside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-13.png') },
        { label: '商户行外客户', path: 'MerchantOutside', permi: 'merchantManagement:merchantOutside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-13.png') },
        { label: '对公公共池', path: 'CompanyBank', permi: 'companyManagement:companyBank:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-12.png') },
        { label: '对公行内客户', path: 'CompanyInside', permi: 'companyManagement:companyInside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-12.png') },
        { label: '对公行外客户', path: 'CompanyOutside', permi: 'companyManagement:companyOutside:view', src: require('@/modules/module-gridManagement/views/workbench/assets/navIcon/nav-12.png') },
      ],
    }
  },
  computed: {
    curNavList() {
      const showNav = []
      for (let item of this.navList) {
        let permi = this.$auth.hasPermi(item.permi)
        if (permi) {
          showNav.push(item)
        }
      }
      return showNav
    },
    swiperOption() {
      return {
        slidesPerView: 'auto',
        slideToClickedSlide: true,
        touchRatio: 0.5,
        pagination: {
          el: '.swiper-pagination',
          clickable: true,
        },
      }
    }
  },
  mounted() {

  },
  methods: {
    randomColor() {
      return this.colors[Math.floor(Math.random() * 100) % 5]
    },
    clickHandle({ path }) {
      this.$router.push({ name: path })
    },
  },
})
</script>

<style lang="scss" scoped>
.navigation-bar-wrapper {
  position: relative;
  .navigation-list {
    box-sizing: border-box;
    width: calc(100% - 40px);
    margin-left: auto;
    .navigation-slide {
      width: 130px !important;

      .navigation-item {
        cursor: pointer;
        text-align: center;
        margin-bottom: 25px;
        .item-logo {
          margin: auto;
          width: 65px;
          height: 65px;
          background: var(--background);
          box-shadow: 0px 0px 5px 1px rgba(109, 135, 189, 0.08);
          border-radius: 20px;
          > img {
            width: 100%;
            height: 100%;
          }
        }
        .item-name {
          white-space: nowrap;
          font-size: 16px;
          color: #222222;
          line-height: 2;
        }
      }
    }
  }
}
</style>