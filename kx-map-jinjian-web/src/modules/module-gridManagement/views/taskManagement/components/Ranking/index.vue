<template>
  <div class="simple-tb" :style="{ width: width }">
    <div class="header">
      <div class="dot"></div>
      <div class="header-title">{{ headerTitle }}</div>
    </div>
    <div class="simple-box" :style="{ height: height }">
      <div class="hd">
        <span
          class="hd-item"
          v-for="(item, index) in title"
          :key="index"
          :style="[{ flex: flexList[index] }]"
        >
          {{ item }}
        </span>
      </div>
      <div class="content" v-if="list.length > 0">
        <div class="row" v-for="(item, index) in list" :key="index">
          <div
            class="row-item"
            v-for="(obj, idx) in config"
            :key="idx"
            :style="[{ flex: flexList[idx] }]"
          >
            <div
              v-if="obj.type == 'top' || obj.type == 'last'"
              class="text"
              :class="rankClass(index, obj)"
              :title="item[obj.key]"
            >
              {{ item[obj.key] }}
            </div>
            <div
              v-else
              :class="textClass(obj)"
              class="text"
              :title="item[obj.key]"
            >
              {{ item[obj.key] }}
            </div>
          </div>
        </div>
      </div>
      <div class="noInfo" v-else>暂无数据</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ranking",
  props: {
    width: {
      type: String,
      default: "22.5vw",
    },
    height: {
      type: String,
      default: "30vh",
    },
    // 排行榜标题
    headerTitle: {
      type: String,
      default: "默认标题",
    },
    // 表格字段
    title: {
      type: Array,
      default: () => [],
    },
    // 每列flex值
    flexList: {
      type: Array,
      default: () => [],
    },
    // 表格字段的配置信息
    config: {
      type: Array,
      default: () => [],
    },
    // 表格列表数据
    list: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {};
  },
  computed: {},
  methods: {
    rankClass(index, obj) {
      return {
        one: obj.type == "top" && index == 0,
        two: obj.type == "top" && index == 1,
        three: obj.type == "top" && index == 2,
        rank: obj.type == "last" || (obj.type == "top" && index > 2),
        round: true,
        // org: obj.type == "org",
      };
    },
    textClass(obj) {
      return {
        org: obj.type == "org",
      };
    },
    sliceStr(str) {
      // if (str.length > 5) {
      //   str = str.slice(0, 4) + "...";
      // }
      return str;
    },
  },
};
</script>

<style lang="scss" scoped>
.simple-tb {
  .header {
    display: flex;
    align-items: center;
    .dot {
      background-color: #3c71f2;
      width: 15px;
      height: 15px;
      border-radius: 50%;
    }
    .header-title {
      margin-left: 10px;
      font-size: 16px;
    }
  }
  .simple-box {
    // height: 30vh;
    overflow: scroll;
    margin-top: 10px;
    .hd {
      min-width: 540px;
      display: flex;
      justify-content: space-around;
      align-items: center;
      text-align: center;
      background: #e7eaf6;
      border-radius: 6px;
      padding: 10px;
    }
    .content {
      min-width: 540px;
      display: flex;
      flex-direction: column;
      .row {
        display: flex;
        align-items: center;
        padding: 10px;
        border-bottom: 1px solid #ddd;
        .row-item {
          display: flex;
          justify-content: space-around;
          align-items: center;
          color: #333;
          .text {
            text-align: center;
            display: -webkit-box;
            -webkit-line-clamp: 1; /* 显示的行数 */
            -webkit-box-orient: vertical;
            overflow: hidden;
          }
          .round {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 30px;
            width: 30px;
            line-height: 30px;
            border-radius: 50%;
          }
          .one {
            background: #f9545b;
            color: #fff;
          }
          .two {
            background: #f29661;
            color: #fff;
          }
          .three {
            background: #fbb669;
            color: #fff;
          }
          .rank {
            background: #999;
            color: #333;
          }
          .org {
            color: #5871ff;
          }
        }
      }
    }
    .noInfo {
      height: 100%;
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      font-size: 20px;
      color: #333;
    }
  }
  .simple-box::-webkit-scrollbar {
    width: 10px;
    height: 10px;
  }
}
</style>
