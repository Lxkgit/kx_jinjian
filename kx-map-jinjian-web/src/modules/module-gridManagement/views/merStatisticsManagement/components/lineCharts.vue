<template>
  <div ref="lineChart" style="width: 100%; height: 300px;"></div>
</template>

<script>
import * as echarts from 'echarts';
import redLine from '@/modules/module-gridManagement/assets/images/red-line.png'
import yellowLine from '@/modules/module-gridManagement/assets/images/yellow-line.png'
import greenLine from '@/modules/module-gridManagement/assets/images/green-line.png'
export default {
  name: 'LineCharts',
  mounted() {
    this.initChart();
  },
  beforeDestroy() {
    this.disposeChart();
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
  methods: {
    initChart() {
      if (this.chart) {
        this.chart.dispose();
      }
      this.chart = echarts.init(this.$refs.lineChart);
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: [
            { name: '红牌', icon: `image://${redLine}` },
            { name: '黄牌', icon: `image://${yellowLine}` },
            { name: '绿牌', icon: `image://${greenLine}` }
          ],
          bottom: 0,
          itemWidth: 24,
          itemHeight: 8,
        },
        grid: {
          left: 40,
          right: 40,
          top: 30,
          bottom: 50
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: Array.from({ length: 15 }, (_, i) => (i + 1).toString()),
          axisLine: { lineStyle: { color: '#000000' } },
          axisLabel: { color: '#888' },
          axisTick: {
            show: false
          },
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: 150,
          splitLine: {
            show: true,
            lineStyle: { color: '#f0f2f5', type: 'dashed' }
          },
          axisLabel: { color: '#888' }
        },
        series: [
          {
            name: '红牌',
            type: 'line',
            data: [20, 60, 50, 55, 60, 80, 100, 130, 120, 130, 130, 110, 100, 120, 110],
            symbol: 'circle',
            symbolSize: 8,
            lineStyle: { width: 2 },
            itemStyle: { color: '#DC5564' }
          },
          {
            name: '黄牌',
            type: 'line',
            data: [15, 65, 40, 45, 50, 70, 90, 120, 110, 100, 90, 70, 80, 90, 80],
            symbol: 'circle',
            symbolSize: 8,
            lineStyle: { width: 2 },
            itemStyle: { color: '#FFC327' }
          },
          {
            name: '绿牌',
            type: 'line',
            data: [10, 30, 20, 25, 30, 50, 70, 100, 110, 120, 110, 80, 90, 80, 70],
            symbol: 'circle',
            symbolSize: 8,
            lineStyle: { width: 2 },
            itemStyle: { color: '#37C38C' }
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
/* 可根据需要自定义样式 */
</style>
