<template>
  <div ref="chart" class="circle-charts" :style="{'--width':width,'--height':height}"></div>
</template>

<script>
import * as echarts from 'echarts';
export default {
  props:{
    width:{type:String,default:'631px'},
    height:{type:String,default:'346px'},
    data:{
      type: Object,
      default: () => ({
        'red':150,
        'yellow':300,
        'green':550
      })
    },
  },
  data() {
    return {
      chart: null
    };
  },
  mounted() {
    this.initChart();
  },
  //监听侧边栏是否收缩
  watch:{
    '$store.state.app.sidebar.opened':{
       handler(val) {
        //确保动画结束 重绘
        setTimeout(()=>{
          this.resizeChart();
        },500)
      },
      deep:true,
    }
  },
  beforeDestroy() { 
    this.disposeChart();
  },
  methods: {
    initChart() {
      if (this.chart) {
        this.chart.dispose();
      }
      this.chart = echarts.init(this.$refs.chart);
      const colorArr = ['#ea6b7a', '#ffc107', '#4ef0b6'];
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c}家 ({d}%)'
        },
        legend: {
          show: false // 不显示默认图例
        },
        series: [
          {
            name: '商户数',
            type: 'pie',
            radius: '70%',
            center: ['50%', '55%'],
            // color: colorArr,
            data: [
            {
              value: `${this.data['red']}`,
              name: '红牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#DC5564' },
                    { offset: 1, color: '#FFAFB8 ' }
                  ]
                }
              }
            },
            {
              value: `${this.data['yellow']}`,
              name: '黄牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#FF9F24' },
                    { offset: 1, color: '#FFE224' }
                  ]
                }
              }
            },
            {
              value: `${this.data['green']}`,
              name: '绿牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#37C38C'},
                    { offset: 1, color: '#76FFCF'}
                  ]
                }
              }
            },
            ],
            label: {
              show: true,
              position: 'outside',
              formatter: function(params) {
                return `{legend${params.dataIndex}|} {name|${params.name}}\n{value|${params.value}家}`;
              },
              //label旁的图例样式配置
              rich: {
                legend0: {
                  width: 12,
                  height: 12,
                  borderRadius: 2,
                  backgroundColor: colorArr[0],
                  align: 'center',
                  padding: [0, 4, 2, 0]
                },
                legend1: {
                  width: 12,
                  height: 12,
                  borderRadius: 2,
                  backgroundColor: colorArr[1],
                  align: 'center',
                  padding: [0, 4, 2, 0]
                },
                legend2: {
                  width: 12,
                  height: 12,
                  borderRadius: 2,
                  backgroundColor: colorArr[2],
                  align: 'center',
                  padding: [0, 4, 2, 0]
                },
                name: {
                  fontSize: 14,
                  color: '#333',
                  padding: [0, 0, 0, 0]
                },
                value: {
                  fontSize: 14,
                  align: 'right',
                  color: '#333'
                }
              },
              fontSize: 16
            },
            labelLine: {
              length: 20,
              length2: 30
            },
            labelLayout: {
              hideOverlap: true
            },
            emphasis: {
              itemStyle: {
                borderColor: '#2f88ff',
                borderWidth: 3
              }
            }
          },
          // 内部百分比
          {
            name: '商户数',
            type: 'pie',
            radius: '70%',
            center: ['50%', '55%'],
            color: colorArr,
            data: [
            {
              value: 150,
              name: '红牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#DC5564' },
                    { offset: 1, color: '#FFAFB8 ' }
                  ]
                }
              }
            },
            {
              value: 300,
              name: '黄牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#FF9F24' },
                    { offset: 1, color: '#FFE224' }
                  ]
                }
              }
            },
            {
              value: 550,
              name: '绿牌商户数',
              //设置渐变背景色
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 1,
                  y2: 1,
                  colorStops: [
                    { offset: 0, color: '#37C38C'},
                    { offset: 1, color: '#76FFCF'}
                  ]
                }
              }
            },
            ],
            label: {
              show: true,
              position: 'inside',
              formatter: '{d}%',
              fontSize: 18,
              color: '#fff',
            },
            labelLine: {
              show: false
            },
            tooltip: {
              show: false
            },
            silent: true
          }
        ]
      };
      this.chart.setOption(option);
      window.addEventListener('resize', this.resizeChart);
    },
    resizeChart() {
      if (this.chart) {
        this.chart.resize();
      }
    },
    disposeChart() {
      window.removeEventListener('resize', this.resizeChart);
      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }
    }
  }
};
</script>

<style scoped lang="scss">
.circle-charts {
  width: var(--width);
  height: var(--height);
}
</style>
