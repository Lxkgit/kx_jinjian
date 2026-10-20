<template>
  <div class="grid-box">
    <div ref="echart" class="echarts"></div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
import 'echarts-wordcloud';
export default {
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          wordCloudData: [
            {
              name: '人民币现金存取',
              value: 320
            },
            {
              name: '手机点单',
              value: 530
            },
            {
              name: '零钱兑换',
              value: 640
            },
            {
              name: '数字人民币',
              value: 350
            },
            {
              name: '假币鉴定',
              value: 264
            },
            {
              name: '真伪授权鉴定',
              value: 529
            },
            {
              name: '柜台服务',
              value: 333
            },
            {
              name: '拒收人民币',
              value: 343
            },
            {
              name: '现金',
              value: 243
            },
          ]

        }
      }
    }
  },
  data() {
    return {
      option: {},
      sumList: []
    }
  },
  mounted() {
    this.sumList = this.info.sumList
    this.initEchart();
  },
  methods: {
    initEchart() {
      const myChart = echarts.init(this.$refs.echart);

      this.option = {
        series: [
          {
            type: 'wordCloud',
            shape: 'circle',
            keepAspect: false,
            // maskImage: maskImage,
            left: 'center',
            top: 'center',
            width: '100%',
            height: '100%',
            right: null,
            bottom: null,
            sizeRange: [10, 22],
            rotationRange: [0, 0],
            rotationStep: 45,
            gridSize: 30,
            drawOutOfBound: false,
            layoutAnimation: true,
            // shrinkToFit: true,
            cursor: 'none',
            textStyle: {
              fontFamily: 'PingFangSC-Medium',
              fontWeight: 500,
              color: "#fff",
              backgroundColor: '#0A3C57',
              borderColor: 'rgba(56, 119, 242, 1)',
              padding: 5,
              borderWidth: 1,
              shadowBlur: 10,
              shadowColor: 'rgba(0, 201, 255, 0.6)',
              shadowOffsetX: 2
            },
            // data: this.wordCloudData
            data: this.info.wordCloudData
          }
        ]
      };
      myChart.setOption(this.option);
    }
  },

  watch: {
    info(newVal) {
      this.initEchart();
    }
  }
};
</script>

<style scoped lang="scss">
.grid-box {
  width: 410px;
  height: 230px;
  display: flex;
  align-items: center;
  background: url("../../assets/worldCloudbg.png") no-repeat;
  background-size: 100%;
  margin: 10px 10px;

  .echarts {
    width: 100%;
    height: 100%;
  }


}
</style>