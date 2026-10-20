<template>
  <div>
    <div class="chart" :id="id"></div>
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
          let chartDom = document.getElementById(this.id);
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
    let chartDom = document.getElementById(this.id);
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
        // title: {
        //   text: this.title,
        //   bottom: 0,
        //   left: "center",
        //   textStyle: {
        //     color: "#6E6E6E",
        //     fontSize: 14,
        //     marginTop:10
        //   }
        // },
        tooltip: {
          trigger: "item",
        },
        // 图表图例
        legend: {
          orient: "vertical",
          right: "10%",
          top: "center",
          // itemHeight: 10,
          itemGap:10,
          // itemWidth: 15,
          data: this.source, // 绑定到 this.list
          formatter: (name) => {
            const item = this.source.find((item) => item.name === name);
            return `${name} | ${item.value}`;
          },
        },
        series: [
          {
            type: "pie",
            center: ["25%", "50%"],
            radius: ["35%", "55%"],
            // avoidLabelOverlap: false,
            label: {
              show: false,
              position: "center",
            },
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            // emphasis: {
            //   label: {
            //     show: false,
            //     fontSize: 30,
            //     fontWeight: "bold",
            //   },
            // },
            // labelLine: {
            //   show: false,
            // },
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