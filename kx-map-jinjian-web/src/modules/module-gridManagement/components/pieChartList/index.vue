<template>
  <div class="pie-chart-list" :style="{ '--gridColumns': `repeat(${gridColumns},1fr)`,'--gridRows': `${gridRows}` }">
    <div class="pie-chart-item" :style="{'--height':itemHeight}" v-for="(item,index) in data" :key="index">
      <div class="pie"><pieChartItem :data="data[index]" :styleClass="styleClass[index]" :index="index" :circleHeight="circleHeight" :circleWidth="circleWidth"></pieChartItem></div>
      <div class="content">
        <div class="title">{{ item.text }}</div>
        <div class="num">{{ formatNumber(item.value) }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import pieChartItem from './pieChartItem.vue';
export default {
  components: { pieChartItem },
  props:{
    itemHeight:{ type:String, default: '92px' },
    dataList:{ type:Array, default: () => [] },
    gridColumns: { type: String,default: '5',},
    gridRows: { type: String,default: '1fr',},
    data: {
      type: Array,
      default: () => ([
      {
        text: '存款',
        rate: 60,
        value:'112213'
      },
      {
        text: '贷款',
        rate: 75,
        value:'22'
      },
      {
        text: '信用卡',
        rate: 30,
        value:'213'
      },
      {
        text: '保险',
        rate: 10,
        value:'21321313'
      },
      {
        text: '财富',
        rate: 100,
        value:'21212'
      },
      {
        text: '保险',
        rate: 10,
        value:'1111'
      },
      ])
    },
    styleClass: {
      type: Array,
      default: () => ([
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#9799FF',//右边边25%渐变色
          rightlCircleColorBack: '#5928D4',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#97FFE9',//右边边25%渐变色
          rightlCircleColorBack: '#28D4AF',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#97D4FF',//右边边25%渐变色
          rightlCircleColorBack: '#23C1FF',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#FFEA97',//右边边25%渐变色
          rightlCircleColorBack: '#F0C000',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#97B6FF',//右边边25%渐变色
          rightlCircleColorBack: '#3284FF',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#FFC997',//右边边25%渐变色
          rightlCircleColorBack: '#FF8934',//右边边100%渐变色
        },
      ])
    },
    circleWidth: {
        type: String,
        default: '60px'
    },
    circleHeight: {
      type: String,
      default: '60px'
    }
  },
  data() {
      return {

      }
  },
  methods: {
    formatNumber(num) {
      if (num < 1000) return num.toString();
      return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },

}
</script>

<style lang="scss" scoped>
%flex-items-center {
  display: flex;
  align-items: center;
}
.pie-chart-list {
  width: 100%;
  display: grid;
  grid-template-columns: var(--gridColumns);
  grid-template-rows: var(--gridRows);
  gap: 12px;
  .pie-chart-item {
    @extend %flex-items-center;
    height: var(--height);
    padding: 16px;
    border: 1px solid #EBEEF5;
    border-radius: 8px;
    background: #fff;
    .content {
      height: calc(100% - 5px);
      margin-left: 12px;
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      .title {
        font-size: 14px;
        color: #666666;
      }
      .num {
        font-size: 22px;
        color: #333333;
        font-weight: 500;
      }
    }
  }
}
</style>