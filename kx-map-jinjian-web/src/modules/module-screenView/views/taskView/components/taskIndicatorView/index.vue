<template>
  <div class="task-indicator-view">
    <div class="masking-out"></div>
    <task-close
      class="task-close"
      text="任务指标视图"
      @close="close"
    ></task-close>
    <div class="indicator-view-title" style="margin-top: 30px">各指标详情</div>

    <div class="indicator-detail">
      <div
        class="indicator-item"
        v-for="(item, index) in this[`indicatorData${taskIndex}`]"
        :key="index"
      >
        <div class="indicator-item-left" v-if="!item.type">
          <div
            class="left-img"
            :style="{ backgroundImage: `url(${item.img})` }"
          ></div>
          <div class="left-text">{{ item.text }}</div>
        </div>
        <div class="indicator-item-left" v-else>
          <div
            class="left-img"
            :style="{ backgroundImage: `url(${item.img})` }"
          ></div>
          <div class="left-special-text">
            <div class="special-text">{{ item.text1 }}</div>
            <div class="special-text">{{ item.text2 }}</div>
          </div>
        </div>
        <div class="indicator-item-right">
          <div class="right1 right-box">
            <div class="box-value value1">{{ item.A }}</div>
            <div class="box-title">当前值</div>
          </div>
          <div class="right-line"></div>
          <div class="right1 right-box">
            <div class="box-value">{{ item.B }}</div>
            <div class="box-title">目标值</div>
          </div>
          <div class="right-line"></div>
          <div class="right1 right-box">
            <div class="box-value">{{ item.C }}</div>
            <div class="box-title">基期值</div>
          </div>
        </div>
      </div>
    </div>
    <div class="indicator-view-title" style="margin-top: 50px">
      各指标完成率走势
    </div>
    <app-chart
      height="400px"
      width="380px"
      :option="option"
      id="indicator"
    ></app-chart>
  </div>
</template>

<script>
import appChart from "../appChart";
import taskClose from "../taskClose";
import * as echarts from "echarts";
export default {
  name: "taskIndicatorView",
  props: {
    masking: {
      type: Boolean,
      default: true,
    },
    taskIndex: Number,
  },
  components: {
    taskClose,
    appChart,
  },
  data() {
    return {
      option: null,
      indicatorData1: [
        {
          img: require("./imgs/v11.png"),
          A: "56.34%",
          B: "65%",
          C: "50.39%",
          text: "万元户率",
        },
        {
          img: require("./imgs/v12.png"),
          A: "78.11%",
          B: "80%",
          C: "70.78%",
          type: "second",
          text1: "授信",
          text2: "覆盖率",
        },
        {
          img: require("./imgs/v13.png"),
          A: "34.63%",
          B: "50%",
          C: "28.40%",
          type: "second",
          text1: "用信",
          text2: "覆盖率",
        },
        {
          img: require("./imgs/v14.png"),
          A: "68.63%",
          B: "85%",
          C: "60.42%",
          type: "second",
          text1: "产品",
          text2: "覆盖率",
        },
      ],
      indicatorData2: [
        {
          img: require("./imgs/v21.png"),
          A: "67.34%",
          B: "80%",
          C: "66.39%",
          type: "second",
          text1: "三代社保",
          text2: "卡覆盖率",
        },
        {
          img: require("./imgs/v22.png"),
          A: "50.11%",
          B: "75%",
          C: "43.78%",
          type: "second",
          text1: "手机银行",
          text2: "覆盖率",
        },
        {
          img: require("./imgs/v23.png"),
          A: "42.63%",
          B: "50%",
          C: "38.40%",
          type: "second",
          text1: "代发代扣",
          text2: "覆盖率",
        },
        {
          img: require("./imgs/v24.png"),
          A: "30.63%",
          B: "50%",
          C: "32.42%",
          type: "second",
          text1: "支付业务",
          text2: "绑卡率",
        },
      ],
      indicatorData3: [
        {
          img: require("./imgs/v31.png"),
          A: "40.34%",
          B: "100%",
          C: "/",
          type: "second",
          text1: "走访",
          text2: "客户率",
        },
        {
          img: require("./imgs/v32.png"),
          A: "68.11%",
          B: "80%",
          C: "66.78%",
          type: "second",
          text1: "贷款客户",
          text2: "覆盖率",
        },
        {
          img: require("./imgs/v33.png"),
          A: "32444",
          B: "503910",
          C: "29767",
          type: "second",
          text1: "对私",
          text2: "存款户数",
        },
        {
          img: require("./imgs/v34.png"),
          A: "89026",
          B: "100000",
          C: "69772",
          type: "second",
          text1: "对公",
          text2: "贷款户数",
        },
      ],
      colorList: ["#6DE1E8", "#61C9FA", "#FF9745"],
      nameList: ["指标1", "指标2", "指标3"],
      valueObject: [
        [10, 20, 30, 40, 50, 60],
        [20, 30, 40, 50, 60, 70],
        [30, 40, 50, 20, 10, 10],
      ],
      axiosLabel: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
    };
  },
  created() {
    this.getLineOption();
  },
  methods: {
    getLineOption() {
      this.option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985",
            },
          },
        },
        legend: {
          data: [
            {
              name: "指标1",
              icon: "circle",
              itemStyle: {
                color: "#6DE1E8",
              },
            },
            {
              name: "指标2",
              icon: "circle",
              itemStyle: {
                color: "#61C9FA",
              },
            },
            {
              name: "指标3",
              icon: "circle",
              itemStyle: {
                color: "#FF9745",
              },
            },
          ],
          textStyle: {
            color: "#E1EBFD",
          },
          top: 20,
          right: "0%",
        },
        data: this.nameList,
        grid: {
          left: "3%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: true,
            data: this.axiosLabel,
            axisLabel: {
              color: "#23F7FF",
              fontWeight: 200,
            },
            axisTick: {
              show: false,
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            axisLabel: {
              formatter: "{value} %",
              color: "rgba(255,255,255,.49)",
              fontWeight: 200,
            },
            splitLine: {
              lineStyle: {
                color: "rgba(255,255,255,.15)",
              },
            },
          },
        ],
        series: [
          {
            name: this.nameList[0],
            type: "line",
            stack: this.nameList[0],
            lineStyle: {
              color: "#6DE1E8",
            },
            areaStyle: {
              opacity: 0.8,
              color: echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgb(109,225,232,.8)",
                },
                {
                  offset: 1,
                  color: "rgb(109,225,232,.2)",
                },
              ]),
            },
            emphasis: {
              focus: "series",
            },
            data: this.valueObject[0],
          },
          {
            name: this.nameList[1],
            type: "line",
            lineStyle: {
              color: "#61C9FA",
            },
            stack: this.nameList[1],
            areaStyle: {
              opacity: 0.8,
              color: echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgb(97,201,250,.8)",
                },
                {
                  offset: 1,
                  color: "rgb(97,201,250,.2)",
                },
              ]),
            },
            emphasis: {
              focus: "series",
            },
            data: this.valueObject[1],
          },
          {
            name: this.nameList[2],
            type: "line",
            stack: this.nameList[2],
            lineStyle: {
              color: "#FF9745",
            },
            areaStyle: {
              opacity: 0.8,
              color: echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgb(255,151,69,.8)",
                },
                {
                  offset: 1,
                  color: "rgb(255,151,69,.2)",
                },
              ]),
            },
            emphasis: {
              focus: "series",
            },
            data: this.valueObject[2],
          },
        ],
      };
    },
    close(e) {
      this.$emit("close", e);
    },
  },
};
</script>

<style scoped lang="scss">
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
@mixin flex-column {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.task-indicator-view {
  .task-close {
    padding-top: 15px;
  }
  position: relative;
  padding: 0 20px;
  overflow: scroll;
  background-size: 100% 100%;
  background-image: url("./imgs/managerBac.png");

  .indicator-view-title {
    color: #00e8f9;
    margin: 15px 0 15px 10px;
  }
  .indicator-detail {
    .indicator-item {
      display: flex;
      align-items: center;
      margin-bottom: 20px;
      .indicator-item-left {
        width: 80px;
        display: flex;
        flex-direction: column;
        align-items: center;
        .left-img {
          margin-bottom: 10px;
          width: 60px;
          height: 60px;
          background-size: 100% 100%;
        }
        .left-text {
          font-size: 18px;
          color: #78effc;
        }
        .left-special-text {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;
          .special-text {
            font-size: 18px;
            color: #78effc;
          }
        }
      }
      .indicator-item-right {
        flex: 1;
        display: flex;
        .right-box {
          @include flex-column;
          flex: 1;
          .box-value {
            color: #ffffff;
            font-size: 24px;
          }
          .value1 {
            color: #d1f8fa;
          }
          .box-title {
            font-size: 16px;
            color: #a8e3f2;
          }
        }
        .right-line {
          width: 1px;
          background-color: #15e1e7;
        }
      }
    }
  }
  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
