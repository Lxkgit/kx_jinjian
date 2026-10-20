<template>
  <div class="chart" style="width:300px; height:150px;" :id="id" />
</template>

<script>
import * as echarts from "echarts" 
export default {
  name: 'itemEchart',
  props: {
    id: String,
    // listData: Array,
    title: String,
    color: String,
    list: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      charts: null,
      // color: "#5fbfec",
      // title: "234"
    };
  },
  watch: {
    list: {
      handler(val) {
        if (!this.charts) {
          let chartDom = document.getElementById(this.id);
          console.log(echarts, 2);
          this.chart = echarts.init(chartDom);
        }
        this.changeChart(val);
      }
    }
  },
  computed: {
    monthList() {
      const list2 = this.list && this.list.map((item, i) => {
        return i + 1 + '月'
      })
      return list2
    }
  },
  mounted() {
    let chartDom = document.getElementById(this.id);
    console.log(echarts, 1);
    this.charts = echarts.init(chartDom);
    this.changeChart(this.list);
    // 当窗口大小改变 echarts重置大小
    window.addEventListener("resize", () => {
      this.charts.resize();
    });
  },
  methods: {
    changeChart(list) {
      let option = {
        title: {
          text: this.title,
          bottom: 0,
          left: "center",
          textStyle: {
            color: "#6E6E6E",
            fontSize: 14
          }
        },
        tooltip: {
          trigger: "axis",
          backgroundColor: "#000",
          extraCssText: "color: #fff;",
          textStyle: {
            color: "#000"
          },
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "line", // 默认为直线，可选为：'line' | 'shadow'
            lineStyle: {
              color: "#000"
            }
          }
        },
        xAxis: {
          type: "category",
          axisTick: {
            show: false //是否显示刻度
          },
          axisLabel: {
            show: true,
            interval: 0,
            rotate: -40
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "#999999",
              opacity: "0.2"
            }
          },
          splitLine: {
            show: false
          },
          data: this.monthList
        },
        yAxis: {
          type: "value",
          axisTick: {
            show: false //是否显示刻度
          },
          axisLabel: {
            margin: 10,
            formatter: function (value, index) {
              if (value >= 10000 && value < 10000000) {
                value = value / 10000 + "万";
              } else if (value >= 10000000) {
                value = value / 10000000 + "千万";
              }
              return value;
            }
          },
          axisLine: {
            show: false,
            lineStyle: {
              color: "#999999", // y坐标轴的轴线颜色
              width: 1 //这里是坐标轴的宽度,可以去掉
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: "#000",
              width: 0.1
            }
          }
        },
        grid: {
          top: "5%",
          left: "5%",
          right: "5%",
          bottom: "12%",
          containLabel: true
        },
        series: [
          {
            data: list,
            type: "line",
            symbol: "circle", //拐点样式
            symbolColor: "#1bb9b3",
            symbolSize: 4,
            itemStyle: {
              normal: {
                color: this.color,
                borderColor: this.color,
                lineStyle: {
                  color: this.color
                }
              }
            },
            areaStyle: {
              color: {
                type: "linear",
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  {
                    offset: 0,
                    color: this.color // 0% 处的颜色
                  },
                  {
                    offset: 0.6,
                    color: "#f0f2f5" // 100% 处的颜色
                  }
                ],
                global: false // 缺省为 false
              }
            }
          }
        ]
      };
      option && this.charts.setOption(option);
    }
  }
};
</script>

<style scoped lang="scss">
.chart {
  margin-left: -20px;
}
</style>