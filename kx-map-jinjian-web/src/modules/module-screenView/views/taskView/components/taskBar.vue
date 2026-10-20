<template>
  <div>
    <app-chart
      :option="option"
      :id="id"
      :height="height"
      :width="width"
    ></app-chart>
  </div>
</template>

<script>
import appChart from "./appChart.vue";

export default {
  name: "",
  props: {
    id: {
      type: String,
      default: "taskBar",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "100%",
    },
    dataObj: {
      type: Object,
      default: () => {
        return {
          legendNameList: ["进行中", "正常完成", "延期完成"],
          axisLabelList: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
          seriesList: [
            [5, 42, 180, 334, 390, 360, 210],
            [10, 62, 220, 324, 380, 330, 200],
            [17, 52, 190, 364, 370, 430, 170],
          ],
        };
      },
    },
  },
  components: {
    appChart,
  },
  data() {
    return {
      option: null,
      colorList: ['#5BFCF4', '#5BC9FC','#FBCA37']
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getData();
  },
  mounted() {},
  methods: {
    getData() {
      this.option = {
        title: {
          text: "任务完成情况", 
          textStyle: {
            color: "white",
          },
          top: "5%",
          left: "5%",
        },
        legend: {
          top: 50,
          right: "0%",
          data: [
            {
              name: this.dataObj.legendNameList[0],
              itemStyle: {
                color: this.colorList[0]
              }
            },
            {
              name: this.dataObj.legendNameList[1],
              itemStyle: {
                ccolor: this.colorList[1]
              }
            },
            {
              name: this.dataObj.legendNameList[2],
              itemStyle: {
                ccolor: this.colorList[2]
              }
            } 
          ],
          textStyle: {
            color: "#E1EBFD",
          },
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          top: "40%",
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: this.axisLabelList,
            axisTick: {
              show: false,
            },
            axisLabel: {
              color: "#E1EBFD",
              fontWeight: 200,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisLabel: {
              color: "#E1EBFD",
              fontWeight: 200,
            },
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.15)",
              },
            },
            name: "万元",
            nameTextStyle: {
              color: "#E1EBFD",
            },
          },
        ],
        series: [
          {
            name: this.dataObj.legendNameList[0],
            type: "bar",
            barWidth: "15%",
            data: this.dataObj.seriesList[0],
            itemStyle: {
              color: this.colorList[0]
            }
          },
          {
            name: this.dataObj.legendNameList[1],
            type: "bar",
            barWidth: "15%",
            data: this.dataObj.seriesList[1],
            itemStyle: {
              color: this.colorList[1]
            }
          },
          {
            name: this.dataObj.legendNameList[2],
            type: "bar",
            barWidth: "15%",
            data: this.dataObj.seriesList[2],
            itemStyle: {
              color: this.colorList[2]
            },
            barGap: "55%", // 除柱体宽度外的其他宽度
          },
        ],
      };
    },
  },
};
</script> 

<style scoped lang='scss'>
</style>./taskBar.vue
