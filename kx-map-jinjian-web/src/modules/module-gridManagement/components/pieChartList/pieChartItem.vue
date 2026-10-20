<template>
    <div class="productDistributeItem" :style="{'--width':width}">
      <div :id="'productChatDom'+index" class="circleChat" ref="chart"
           :style="{'--circleWidth':circleWidth,'--circleHeight':circleHeight}"></div>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  export default {
    props: {
      data: {
        type: Object,
        default: () => { }
      },
      styleClass: {
        type: Object,
        default: () => { }
      },
      index: {
        type: [Number, String],
        default: 0
      },
      width: {
        type: String,
        default: '60px'
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
        myChart: null,
      }
    },
    mounted() {
      this.initChart()
    },
    beforeDestroy() {
      if(this.myChart) {
        this.myChart.dispose()
        this.myChart = null
      }
    },
    watch:{
      data:{
        handler(newValue,old) {
          this.updateChart()
        },
        immediate:true,
        deep:true
      },
    },
    methods: {
      initChart() {
        let emotionChart = this.$refs.chart
        this.myChart = echarts.getInstanceByDom(emotionChart) || echarts.init(emotionChart)
        this.updateChart()
      },
      updateChart() {
        if (this.myChart) {
          let option = {
            graphic: [
              {
                type: 'text',
                left: 'center',
                top: 'center',
                z: 9,
                style: {
                  text: `${this.data.centerText ? this.data.centerText : this.data.rate}${this.data.centerText ? '' : '%'}`,
                  textAlign: 'center',
                  fontSize: `${this.styleClass.circleTextSize}`,
                  fill: `${this.styleClass.circleTextColor}`,
                  fontWeight: 400,
                  fontFamily: `${this.styleClass.circleFontFamily}`,
                }
              }
            ],
            series: [
              {
                type: "pie",
                radius: ["85%", "100%"],
                avoidLabelOverlap: true,
                emphasis: false,
                labelLine: {
                  show:false
                },
                data: [
                  {
                    value: this.data.rate,
                    itemStyle: {
                      color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [
                          {
                            offset: 0.25,
                            color: `${this.styleClass.rightCircleColorFront}` // 40% 处的颜色  
                          },
                          {
                            offset: 1,
                            color: `${this.styleClass.rightlCircleColorBack}` // 100% 处的颜色
                          }
                        ],
                        global: false // 缺省为 false
                      }
                    },
                  },
                  {
                    value: 100 - this.data.rate,
                    itemStyle: {
                      color: {
                        type: 'linear',
                        x: 0,
                        y: 0,
                        x2: 0,
                        y2: 1,
                        colorStops: [
                          {
                            offset: 0,
                            color:'#ECEEF3' // 0% 处的颜色  
                          },
                          {
                            offset: 1,
                            color:'#ECEEF3' // 100% 处的颜色
                          }
                        ],
                        global: false // 缺省为 false
                      }
                    },
                  },
                ],
              },
              {
                name: '1',
                type: 'pie',
                radius: ['0%', '75%'],
                center: ['50%', '50%'],
                emphasis: false,
                data: [{
                  name: '',
                  value: 1,
                }],
                itemStyle: {
                  color: '#F6F7FA',
                  borderColor: '#E0E2EC',
                  borderWidth: 0.5,
                }
              },
            ]
          };
          this.myChart.clear()
          this.myChart.setOption(option);
        }
      }
    }
  }
  </script>
  
  <style lang="scss" scoped>
  .productDistributeItem {
    width: var(--width);
    display: flex;
    flex-direction: column;
    align-items: center;
    .circleChat {
      width: var(--circleWidth);
      height: var(--circleHeight);
    }
  }
  </style>
  