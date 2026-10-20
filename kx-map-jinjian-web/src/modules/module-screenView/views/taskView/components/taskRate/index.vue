<template>
  <div class="task-rate">
    <!-- 头部文字 -->
    <div class="rate-text">
      <div class="txt" v-for="(item, index) in textNameValue" :key="index">
        <div class="txt-name">{{ item.name }}</div>
        <div class="txt-value">{{ item.value }}{{ index == 0?'%':'' }}</div>
      </div>
    </div>
    <div class="rate-img">
      <!-- 左侧图 -->
      <div class="rate-percent">
        <div
          class="rate-percent-img"
          :class="[imgType == 1 ? 'mainImg' : 'sonImg']"
        >
          <div class="text">{{ textNameValue[0].value }}</div>
        </div>
      </div>
      <div class="rate-line">
        <div class="line"></div>
      </div>
      <!-- 右侧图 -->
      <div class="echart">
        <div class="echart-circle">{{ textNameValue[1].value }}</div>
        <app-chart
          class="echart-item"
          width="100%"
          height="100px"
          :option="option"
          :id="id"
        ></app-chart>
      </div>
    </div>
  </div>
</template>

<script>
import appChart from "../appChart.vue";
export default {
  name: "taskRate",
  props: {
    id: {
      type: String,
      required: true,
    },
    textNameValue: {
      type: Array,
      default: () => [
        {
          name: "主任务完成率",
          value: 62.8,
        },
        {
          name: "主任务总量",
          value: "102",
        },
      ],
    },
    imgType: {
      type: Number,
      default: 1, // 主任务的图    子任务的图
    },
    optionData: {
      type: Array,
      default: () => [
        { value: 1048, name: "正常完成" },
        { value: 735, name: "延期完成" },
        { value: 580, name: "正常进行中" },
        { value: 484, name: "延期进行中" },
      ],
    },
  },
  components: {
    appChart,
  },
  data() {
    return {
      option: null,
      colorList: ["#51FFC3", "#FFAB4C", "#15E1E7", "#FEFF8D"],
    };
  },
  computed: {},
  watch: {},
  created() {
    this.optionData.forEach((v, i) => {
      this.$set(v, "itemStyle", {});
      this.$set(v.itemStyle, "color", "");
      v.itemStyle.color = this.colorList[i];
    });
    this.getData();
  },
  mounted() {},
  methods: {
    getData() {
      this.option = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "vertical",
          right: 0,
          top: 10,
          bottom: 20,
          itemGap: 0,
          itemWidth: 5,
          itemHeight: 5,
          itemGap: 10,
          data: [
            {
              name: this.optionData[0].name,
              itemStyle: {
                color: this.colorList[0],
              },
            },
            {
              name: this.optionData[1].name,
              itemStyle: {
                color: this.colorList[1],
              },
            },
            {
              name: this.optionData[2].name,
              itemStyle: {
                color: this.colorList[2],
              },
            },
            {
              name: this.optionData[3].name,
              itemStyle: {
                color: this.colorList[3],
              },
            },
          ],
          textStyle: {
            fontSize: 10,
            color: "#E1EBFD",
          },
        },
        series: [
          {
            name: "任务分布",
            type: "pie",
            center: ["30%", "50%"],
            radius: ["80%", "95%"],
            itemStyle: {
              borderRadius: 1,
              borderWidth: 5,
              // borderColor: "#fff",
            },
            label: {
              show: false,
            },
            labelLine: {
              show: false,
            },
            data: this.optionData,
          },
        ],
      };
    },
  },
};
</script> 

<style scoped lang='scss'>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.task-rate {
  margin: 20px;
  .rate-text {
    display: flex;
    justify-content: space-between;
    .txt {
      display: flex;
      font-size: 16px;
      &-name {
        color: #00f6ff;
        margin-left: 10px;
      }
      &-value {
        color: white;
        margin-left: 10px;
        font-family: screenSpecial;
      }
    }
  }
  .rate-img {
    display: flex;
    margin-top: 10px;
    .rate-percent {

      width: 25%;
      @include flex;
      ::v-deep .el-progress-circle {
        width: 80px !important;
        height: 80px !important;
      }
      ::v-deep  .el-progress__text {
        color: white;
        font-family: screenSpecial;
      }
      .rate-percent-img {
        width: 100px;
        height: 100px;
        color: white;
        font-family: screenSpecial;
        background-size: 100% 100%;
        position: relative;
        display: flex;
        justify-content: center;
        align-items: center;
      }
      .mainImg {
        background-image: url("./imgs/taskRateMainImg.png");
      }
      .sonImg {
        background-image: url("./imgs/taskRateSonImg.png");
      }
    }
    .rate-line {
      width: 25%;
      @include flex;
      .line {
        width: 100%;
        height: 5px;
        background-image: url("./imgs/line.png");
        background-size: 100% 100%;
      }
    }
    .echart {
      position: relative;
      width: 50%;
      height: 100px;
      &-circle {
        font-family: screenSpecial;
        position: absolute;
        top: 19%;
        left: 13%;
        width: 62px;
        height: 62px;
        background-image: url("./imgs/circle.png");
        background-size: 100% 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        color: white;
      }
    }
  }
}
</style>
