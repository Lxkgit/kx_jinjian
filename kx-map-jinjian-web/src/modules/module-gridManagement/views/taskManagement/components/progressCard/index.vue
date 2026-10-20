<template>
  <div class="progress-card" :style="{ width: cardWidth, height: cardHeight }">
    <div class="card-top" :style="{ backgroundColor: color }">{{ title }}</div>
    <div class="card-bottom" v-if="paogressData.length > 0">
      <div class="card-item" v-for="(item, index) in paogressData" :key="index">
        <div class="item-top">
          <div class="item-title">
            <div class="title-head"></div>
            <div class="title-text title-text::before" :title="item.taskName">
              {{ item.taskName }}
            </div>
          </div>
          <div
            class="sub-title"
            :style="{ color: color }"
            :title="item.taskTypeName"
          >
            {{ item.taskTypeName }}
          </div>
        </div>
        <div class="item-row">
          <div class="head">
            <i class="el-icon-video-play head-icon"></i>开始时间:
          </div>
          <div class="item-time">{{ item.startTime }}</div>
        </div>
        <div class="item-row">
          <div class="head">
            <i class="el-icon-video-pause head-icon"></i>结束时间:
          </div>
          <div class="item-time">{{ item.endTime }}</div>
        </div>
        <div class="item-row" v-if="item.completeProcess">
          <div class="head">
            <i class="el-icon-edit-outline head-icon"></i>任务总进度:
          </div>
          <el-progress
            class="progress-line"
            text-color="black"
            :text-inside="true"
            :color="color"
            :percentage="parseInt(item.completeProcess)"
            :stroke-width="16"
          ></el-progress>
        </div>
      </div>
    </div>
    <div class="no-data" v-else>空</div>
  </div>
</template>

<script>
export default {
  name: "ProgressCard",
  props: {
    paogressData: {
      type: Array,
      default: () => [],
    },
    // 主题颜色
    color: {
      type: String,
      default: () => "#7391f5",
    },
    // 宽度
    cardWidth: {
      type: String,
      default: () => "400px",
    },
    // 高度
    cardHeight: {
      type: String,
      default: () => "270px",
    },
    // title文字
    title: {
      type: String,
      default: () => "顶部文字",
    },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.progress-card {
  border: 1px solid #ddd;
  margin: 10px;
  border-radius: 10px;
  overflow: hidden;
  .card-top {
    width: 100%;
    height: 3vw;
    font-size: 1.4vw;
    line-height: 3vw;
    text-align: center;
    font-weight: bold;
    color: white;
    // background-color: #7491f5;
    // border-radius: 10px 10px 0 0;
  }
  /* 隐藏右侧的滚动条 */
  .card-bottom::-webkit-scrollbar {
    display: none;
  }
  .card-bottom {
    width: 100%;
    height: calc(100% - 30px);
    overflow: scroll;
    box-sizing: border-box;
    padding: 5px 10px 40px 10px;
    // -ms-overflow-style: none; /* IE and Edge */
    // scrollbar-width: none; /* Firefox */
    .card-item {
      width: 100%;
      height: 9vw;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      background-color: #f6f6f6;
      box-sizing: border-box;
      padding: 5px 10px 5px 10px;
      margin-bottom: 10px;
      .item-top {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        // font-size: 16px;
        .item-title {
          width: 60%;
          font-weight: bold;
          display: flex;
          align-items: center;
          position: relative;
          color: #666666;
          .title-head {
            width: 0.75vw;
            height: 0.75vw;
            border-radius: 50%;
            background-color: #7491f5;
            margin-right: 5px;
          }
        }
        .sub-title {
          color: #7491f5;
          font-weight: 500;
        }
        .title-text,
        .sub-title {
          font-size: 1.1vw;
          overflow: hidden; //超出的文本隐藏
          text-overflow: ellipsis; //溢出用省略号显示
          white-space: nowrap; //溢出不换行
        }
      }
      .item-row {
        width: 100%;
        display: inline-flex;
        align-items: center;
        color: #666;
        .head-icon {
          margin-right: 5px;
        }
        .head {
          min-width: 7.5vw;
          font-size: 1.1vw;
          font-weight: 400;
        }
        .item-time {
          font-size: 1vw;
          color: #212121;
          font-weight: 400;
        }
        .progress-line {
          width: 12vw;
          display: flex;
          align-items: center;
          ::v-deep .el-progress-bar_outer {
            height: 2vw;
          }
        }
      }
    }
  }
  .no-data {
    width: 100%;
    height: calc(100% - 30px);
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 2vw;
    font-weight: bold;
  }
}
</style>