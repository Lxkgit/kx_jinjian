<template>
  <div>
    <div class="chart" id="chart"></div>
  </div>
</template>
 
<script>
export default {
  name: "Chart",
  data() {
    return {
      chart: null,
    };
  },
  props: {
    id: String,
    source: {
      type: Array,
      default: () => [],
    },
    title: String,
    // color: {
    //   type: Array,
    //   default: () => ["#6666cc", "#99cc66", "#ffcc66", "#cc6666", "#99cccc"]
    // }
  },
  watch: {
    source: {
      handler(val) {
        if (!this.charts) {
          let chartDom = document.getElementById('chart');
          this.chart = this.$echarts.init(chartDom);
        }
        this.changeChart(val);
      },
    },
  },
  computed: {
    list() {
      return this.source.map((item) => item.name);
    },
    colorList() {
      return this.source.map((item) => item.color);
    },
  },
  mounted() {
    let chartDom = document.getElementById('chart');
    console.log(this.echarts, "echarts");
    this.chart = this.$echarts.init(chartDom); // 初始化echart
    this.changeChart(this.source);
    // 当窗口大小改变 echarts重置大小
    window.addEventListener("resize", () => {
      this.chart.resize();
    });
  },
  methods: {
    changeChart(source) {
      const option = {
        color: this.colorList,
        tooltip: {
          trigger: "item",
        },
        // 图表图例
        legend: {
          orient: "vertical", // 保持图例垂直排列
          left: "60%", // 调整图例的位置，可以根据需要调整
          top: "center", // 垂直居中
          icon: "circle", // 使用圆形图例
          itemGap: 20, // 增加图例项之间的间距
          itemWidth: 15, // 设置图例项的宽度
          itemHeight: 15, // 设置图例项的高度
        },
        series: [
          {
            type: "pie",
            center: ["30%", "50%"],
            radius: ["35%", "55%"],
            label: {
              show: false,
              position: "center",
            },
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            emphasis: {
              label: {
                show: false,
                fontSize: 30,
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: source,
          },
        ],
      };
      this.chart.setOption(option);

    },
  },
};
</script>
 
<style lang="scss" scoped>
.chart {
  width: 100%;
  height: 300px;
}
</style>