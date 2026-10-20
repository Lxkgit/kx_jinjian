<template>
  <div ref="barChart" class="bar-charts" :style="{'--width':width,'--height':height}"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  props:{
    width:{type:String,default:'auto'},
    height:{type:String,default:'346px'},
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
  mounted() {
    this.initChart();
  },
  beforeDestroy() { 
    this.disposeChart();
  },
  methods: {
    initChart() {
      if (this.chart) {
        this.chart.dispose();
      }
      this.chart = echarts.init(this.$refs.barChart);
      const option = {
        grid: {
          left: 210,
          right: 40,
          top: 20,
          bottom: 40
        },
        xAxis: {
          type: 'value',
          min: 0,
          max: 100,
          interval: 25, // 每25为一个刻度
          splitNumber: 4, // 0-100分4段
          splitLine: {
            show: true,
            lineStyle: {
              color: '#fff'
            }
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#D8D8D8',
              width: 1
            }
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            formatter: '{value}%',
            color: '#494D68',
            align: 'center',
            fontSize: 14
          }
        },
        yAxis: {
          type: 'category',
          data: [
            '需要小面额现金上门兑换服务',
            '曾发生无法找零情况',
            '了解兑换小面额人民币现金方式',
            '了解拒收人民币现金是违法行为',
            '备付人民币现金整洁',
            '零钱备付充足(超过300元)',
            '张贴“支持现金支付”标志',
            '具备人民币现金收付条件',
          ],
          axisLabel: {
            color: '#323548',
            fontSize: 14,
            width: 220,
            overflow: 'truncate', // 超出省略号，不换行
            align: 'right',
            margin: 10
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#D8D8D8',
              width: 1
            }
          },
          axisTick: {
            show: false
          }
        },
        series: [
          {
            type: 'bar',
            data: [95, 80, 50, 60, 40, 85, 100, 100],
            barWidth: 24,
            itemStyle: {
              color: '#5b8ff9',
              // borderRadius: [8, 8, 8, 8]
            },
            showBackground: true,
            backgroundStyle: {
              color: '#f0f2f5',
              // borderRadius: [8, 8, 8, 8]
            },
            label: {
              show: false
            }
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

<style scoped>
.bar-charts {
  width: var(--width);
  height: var(--height);
}
</style>
