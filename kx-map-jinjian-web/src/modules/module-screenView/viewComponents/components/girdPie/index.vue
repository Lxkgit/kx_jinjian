<template>
  <div class="grid-box">
    <div class="left">
      <div class="card">
        <div class="card-title">{{ info.num1 }}</div>
        <div class="card-content">行政村</div>
      </div>
      <div class="card cardBlue">
        <div class="card-title">{{ info.num2 }}</div>
        <div class="card-content">社区</div>
      </div>
      <div class="arrow">
        <div class="icon"></div>
      </div>
    </div>
    <div class="right">
      <div ref="echart" class="echarts"></div>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
import { computed } from 'vue';
export default {
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          num1: 1,
          num2: 2,
          pieData: [
            { value: 23, name: "杭州市" },
            { value: 17, name: "宁波市" },
            { value: 52, name: "温州市" },
            { value: 26, name: "绍兴市" },
            { value: 13, name: "湖州市" },
            { value: 13, name: "嘉兴市" },
            { value: 2, name: "金华市" },
            { value: 3, name: "衢州市" },
            { value: 2, name: "舟山市" },
            { value: 2, name: "台州市" },
            { value: 1, name: "丽水市" },
          ],
        };
      },
    },
  },
  data () {
    return {
      option: {},
    };
  },
  mounted () {
    this.initEchart();
  },
  computed: {
    layers () {
      return this.$store.getters["screenView/layers"];
    },
    pieData () {
      return this.info.pieData.slice(0, 15)
    }
  },
  methods: {
    initEchart () {
      const that = this;
      const colorList = [
        "#8886FF",
        "#3254DD",
        "#2B8EF3",
        "#BEE5FB",
        "#3CD495",
        "#6B93FF",
        "#53ECFF",
        "#FFCB5B",
        "#FFA055",
        "#FF7373",
        "#FF8FCE",
      ];
      const myChart = echarts.init(this.$refs.echart);

      // 预计算数据总和
      const total = this.pieData.reduce(
        (sum, item) => sum + item.value,
        0
      );

      this.option = {
        legend: {
          bottom: 30,
          color: "#fff",
          itemHeight: 6,
          itemWidth: 6,
          formatter: function (name) {
            const item = that.option.series[0].data.find(
              (d) => d.name === name
            );
            const p = Math.round((item.value / total) * 100); // 占比
            const index = that.option.series[0].data.findIndex(
              (d) => d.name === name
            );
            return `${name}    {${String.fromCharCode(97 + index)}|${p} %}`; //String.fromCharCode(97) 代表字母a  依次类推
          },
          textStyle: {
            color: "#fff",
            rich: Object.fromEntries(
              colorList.map((color, index) => [
                String.fromCharCode(97 + index),
                { color },
              ])
            ),
          },
        },
        series: [
          {
            type: "pie",
            center: ["70%", "35%"],
            radius: ["30%", "48%"],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: "center",
              formatter: function (params) {
                const { data } = params;
                return [`{a|${total}}`, "{b|网格数(个)}"].join(
                  "\n"
                );
              },
              rich: {
                a: {
                  fontSize: 16,
                  lineHeight: 20,
                  color: "#fff",
                },
                b: {
                  fontSize: 16,
                  color: "#fff",
                  lineHeight: 20,
                },
              },
            },
            emphasis: {
              label: {
                show: true,
                position: "inside",
                formatter: function (params) {
                  const { data } = params;
                  return [`{a|${data.value}}`, `{b|${data.name}}`].join("\n");
                },
                rich: {
                  a: {
                    fontSize: 16,
                    lineHeight: 20,
                    color: "#fff",
                  },
                  b: {
                    fontSize: 16,
                    color: "#fff",
                    lineHeight: 20,
                  },
                },
              },
            },
            data: this.pieData,
            itemStyle: {
              color: (params) => colorList[params.dataIndex % colorList.length],
            },
          },
        ],
      };
      myChart.setOption(this.option);
    },
  },
  watch: {
    info (newVal) {
      this.initEchart();
    },
  },
};
</script>

<style scoped lang="scss">
.grid-box {
  width: 430px;
  height: 320px;
  position: relative;

  .left {
    position: absolute;
    left: 20px;
    top: 80px;
    width: 164px;
    display: flex;
    flex-direction: row;
    gap: 0 24px;

    .card {
      width: 70px;
      height: 100px;
      background: url("../../assets/card.png") center/100% no-repeat;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;

      &.cardBlue {
        background: url("../../assets/cardBlue.png") center/100% no-repeat;
      }

      .card-title {
        font-size: 16px;
        color: #fff;
        margin-bottom: 6px;
      }

      .card-content {
        font-size: 12px;
        color: #fff;
      }
    }

    .arrow {
      height: 20px;
      height: 100px;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-left: 6px;

      .icon {
        width: 12px;
        height: 12px;
        background: url("../../assets/arrow.png") no-repeat center;
        background-size: 100% 100%;
      }
    }
  }

  .right {
    width: 100%;
    height: 100%;

    .echarts {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
