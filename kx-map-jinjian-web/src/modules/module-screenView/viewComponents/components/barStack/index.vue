<template>
  <div class="grid-box">
    <div class="left">
      <ul>
        <li v-for="item in sumList" :key="item.name">
          <span class="f14"><i>{{ item.value }}</i> 个</span>
          <span>{{ item.name }}</span>
        </li>
      </ul>
    </div>
    <div class="right">
      <div ref="echart" class="echarts"></div>

    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          xData: ['杭州', '宁波', '温州', '绍兴', '湖州', '嘉兴', '金华', '衢州', '舟山', '台州', '丽水'],
          yDate: [1782, 1807, 287, 600, 700, 300, 740, 200, 300, 200, 100],
          yDate1: [200, 444, 500, 1000, 290, 300, 400, 600, 134, 222, 333],
          yDate2: [444, 500, 1000, 290, 300, 400, 600, 700, 444, 555, 222],
          yDate3: [290, 300, 400, 600, 700, 300, 740, 200, 300, 740, 200],
          yDate4: [444, 500, 1000, 600, 700, 300, 740, 200, 300, 740, 200],
          yDate5: [444, 500, 1000, 290, 300, 400, 740, 200, 300, 740, 200],
          yDate6: [444, 300, 400, 600, 700, 300, 740, 200, 300, 740, 200],
          yDate7: [444, 500, 1000, 500, 700, 300, 740, 200, 500, 700, 300],
          sumList: [
            {
              name: '人工现金柜台',
              value: 3200
            },
            {
              name: '本外部现金存取',
              value: 5300
            },
            {
              name: '小面额兑换支取',
              value: 7400
            },
            {
              name: '数字人民币服务',
              value: 3590
            },
            {
              name: '假币鉴定',
              value: 2340
            },
            {
              name: '真伪授权鉴定',
              value: 5290
            },
            {
              name: '特殊残损人民币兑换',
              value: 3330
            },
            {
              name: '停止流通人民币兑换',
              value: 3430
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
      let colorList = [
        '#8886FF',
        '#6B93FF',
        '#53ECFF',
        '#53FFB1',
        '#FFCB5B',
        '#FFA055',
        '#FF7373',
        '#FF8FCE'
      ]
      var barWidth = 10
      const xData = this.info.xData
      this.option = {
        color: colorList,
        grid: {
          left: '2%',
          right: '1%',
          top: '3%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: xData,
            axisLabel: {
              interval: 0,

              textStyle: {
                fontSize: 11,
                color: "#fff"
              }
            },
            axisLine: {
              show: true,
              lineStyle: {
                color: "#fff",
              },
            },
            axisTick: {
              show: false,
              alignWithLabel: true
            },
            splitLine: {
              //修改背景线条样式
              show: false,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)"
              }
            },
          }
        ],
        yAxis: [
          {
            type: 'value',
            axisLine: {
              show: false,
              lineStyle: {
                color: "#fff",
              },
            },
            axisTick: {
              show: false,
              alignWithLabel: true
            },
            splitLine: {
              //修改背景线条样式
              show: true,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)"
              }
            },
          }
        ],
        series: [
          {
            barWidth: barWidth,
            name: xData[0],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate
          },
          {
            name: xData[1],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate1
          },
          {
            name: xData[2],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate2
          },
          {
            name: xData[3],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate3
          },
          {
            name: xData[4],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate4
          },
          {
            name: xData[5],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate5
          },
          {
            name: xData[6],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate6
          },
          {
            name: xData[7],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate7
          },
          {
            name: xData[8],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate8
          },
          {
            name: xData[9],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate9
          },
          {
            name: xData[10],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate10
          },
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
  width: 430px;
  height: 340px;
  display: flex;
  align-items: center;

  .left {
    width: 120px;
    height: 100%;

    ul {
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      // margin-top: 0px;

      li {
        display: flex;
        width: 123px;
        height: 33px;
        background: url('../../assets/btnbg1.png') no-repeat;
        background-size: 100%;
        margin-bottom: 8px;
        font-size: 14px;
        color: #fff;
        flex-direction: column;
        align-items: center;
        justify-content: center;

        &:nth-child(1) {
          i {
            color: #8886FF;
          }
        }

        &:nth-child(2) {
          background: url('../../assets/btnbg2.png') no-repeat;
          background-size: 100%;

          i {
            color: #6B93FF;
          }
        }

        &:nth-child(3) {
          background: url('../../assets/btnbg3.png') no-repeat;
          background-size: 100%;

          i {
            color: #53ECFF;
          }
        }

        &:nth-child(4) {
          background: url('../../assets/btnbg4.png') no-repeat;
          background-size: 100%;

          i {
            color: #53FFB1;
          }
        }

        &:nth-child(5) {
          background: url('../../assets/btnbg5.png') no-repeat;
          background-size: 100%;

          i {
            color: #FFCB5B;
          }
        }

        &:nth-child(6) {
          background: url('../../assets/btnbg6.png') no-repeat;
          background-size: 100%;

          i {
            color: #FFA055;
          }
        }

        &:nth-child(7) {
          background: url('../../assets/btnbg7.png') no-repeat;
          background-size: 100%;

          i {
            color: #FF7373;
          }
        }



        &:nth-child(8) {
          background: url('../../assets/btnbg8.png') no-repeat;
          background-size: 100%;

          i {
            color: #FF8FCE;
          }
        }

        &:last-child {
          margin-bottom: 0;
        }

        span {
          font-size: 12px;
          display: block;
          text-align: center;

          &.f14 {
            font-size: 14px;
            line-height: 16px;
            color: rgba(255, 255, 255, 0.6);
          }
        }
      }
    }
  }

  .right {
    width: 325px;
    height: 100%;

    .echarts {
      width: 100%;
      height: 100%;
    }
  }


}
</style>