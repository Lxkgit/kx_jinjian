<template>
  <div style="position: relative" :style="{ width, height }">
    <div :id="id" :style="{ width, height }"></div>
    <div class="bacImg" :style="{ width, height }"></div>
  </div>
</template>

<script>
export default {
  name: "preferenceProportion",
  props: {
    id: {
      type: String,
      default: ""
    },
    width: {
      type: String,
      default: "136px"
    },
    height: {
      type: String,
      default: "136px"
    },
    radius: {
      type: Array,
      default: () => {
        return [5, 55];
      }
    },
    dataValue: {
      type: Array,
      default: () => []
    }
  },
  watch: {
    dataValue: {
      handler(newVal, oldVal) {
        if (newVal && newVal.length > 0) {
          this.getData();
          this.updataDataValue(this.dataValue);
          if (this.myChart) {
            this.myChart.setOption(this.option);
          }
          // this.myChart.setOption(this.option);
        }

        // this.showPie()
      },
      deep: true,
      immediate: true
    }
  },
  data() {
    return {
      myChart: null,
      option: null,
      colorArr: [
        "#66D4E6",
        "#14E7A0",
        "#15C3E8",
        "#14E8BF",
        "#DAB54C",
        "#FC924C"
      ]
    };
  },
  created() {
    this.getData();
    this.updataDataValue(this.dataValue);
  },
  mounted() {
    this.myChart = this.$echarts.init(document.getElementById(`${this.id}`));
    this.showPie();
  },
  methods: {
    // 修改数据
    getData() {
      this.option = {
        tooltip: {
          trigger: "item"
        },
        series: [
          {
            name: "储蓄占比",
            type: "pie",
            radius: this.radius,
            center: ["50%", "50%"],
            roseType: "area",

            label: {
              show: false
            },

            labelLine: {
              show: false
            },
            data: this.dataValue
          }
        ]
      };
      if (this.dataValue && this.dataValue.length > 0) {
        this.dataValue.forEach((item, index) => {
          item.name = item.label;
          item.value = item.amount;
          item.itemStyle = {
            color: ""
          };
          item.itemStyle.color = this.colorArr[index];
        });
      }
    },
    updataDataValue(data) {
      this.option.series[0].data = data;
    },
    // 渲染数据
    showPie() {
      this.myChart.setOption(this.option);
      this.myChart.on("click", e => {
        if (e.data.children && e.data.children.length > 0) {
          this.$emit("children", e.data.children);
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.bacImg {
  z-index: -100;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-size: 100% 100%;
  pointer-events: auto;
  background-image: url("../assets/compImg/preferenceProportion.png");
}
</style>