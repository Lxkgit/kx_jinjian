
<template>
  <div>
    <div :style="{ width }">
      <div class="scorllTitle flex">
        <div class="scorllTitle-item scorllTitle-item1 flex">排名</div>
        <div class="scorllTitle-item scorllTitle-item2 flex">名称</div>
        <div class="scorllTitle-item scorllTitle-item3 flex">完成率</div>
        <div class="scorllTitle-item scorllTitle-item4 flex">进行中</div>
        <div class="scorllTitle-item scorllTitle-item5 flex-column">
          <div>正常</div>
          <div>完成</div>
        </div>
        <div class="scorllTitle-item scorllTitle-item6 flex-column">
          <div>超期</div>
          <div>完成</div>
        </div>
        <div class="scorllTitle-item scorllTitle-item7 flex-column">
          <div>超期</div>
          <div>未完成</div>
        </div>
      </div>
      <div class="warp">
        <div class="warp-box">
          <div
            class="warp-box-item"
            v-for="(item, index) in listData"
            :key="index"
          >
            <span class="ranking">
              <div
                class="trophy"
                :class="[
                  { trophyT1: item.ranking == 1 },
                  { trophyT2: item.ranking == 2 },
                  { trophyT3: item.ranking == 3 },
                ]"
              ></div>
              <div v-text="item.ranking" class="num"></div>
            </span>
            <span class="name flex">
              <div class="nameOver" v-text="item.name"></div>
            </span>
            <span class="completionRate flex" v-text="item.A"></span>
            <span class="inHand flex">
              <div class="" v-text="item.B"></div>
            </span>
            <span class="normalCompletion flex">
              <div class="" v-text="item.C"></div>
            </span>
            <span class="overdueCompletion flex">
              <div class="" v-text="item.D"></div>
            </span>
            <span class="overdueNotCompletion flex">
              <div class="" v-text="item.E"></div>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import vueSeamlessScroll from "vue-seamless-scroll";

export default {
  name: "taskRanking",
  props: {
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "350px",
    },
    listData: {
      type: Array,
      default: () => [
        {
          ranking: "1",
          name: "沙家浜支行",
          A: "89.32",
          B: "133",
          C: "14",
          D: "12",
          E: "4",
        },
        {
          ranking: "2",
          name: "董浜支行",
          A: "87.32",
          B: "123",
          C: "12",
          D: "4",
          E: "4",
        },
        {
          ranking: "3",
          name: "梅里支行",
          A: "80.32",
          B: "233",
          C: "12",
          D: "5",
          E: "8",
        },
        {
          ranking: "4",
          name: "莫城支行",
          A: "77.32",
          B: "232",
          C: "14",
          D: "4",
          E: "8",
        },
        {
          ranking: "5",
          name: "古里支行",
          A: "71.32",
          B: "145",
          C: "33",
          D: "12",
          E: "9",
        },
      ],
    },
  },
  components: {
    vueSeamlessScroll,
  },
  data() {
    return {
      classOption: {
        step: 0.6,
      },
      titleText: ["排名", "名称", "指数", "管理员", "机构"],
    };
  },
};
</script>

<style lang="scss" scoped>
.wrapContent {
  overflow: hidden;
}

.flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.flex-column {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.warp {
  margin-top: 12px;
}

.scorllTitle {
  width: 100%;
  height: 40px;
  background-color: #125e6a;

  &-item {
    font-size: 14px;
    height: 30px;
    color: #a8e3f2;
  }
  .scorllTitle-item1 {
    flex: 1;
  }
  .scorllTitle-item2 {
    flex: 2;
  }
  .scorllTitle-item3 {
    flex: 1.6;
  }
  .scorllTitle-item4 {
    flex: 1.6;
  }
  .scorllTitle-item5 {
    flex: 1.2;
  }
  .scorllTitle-item6 {
    flex: 1.2;
  }
  .scorllTitle-item7 {
    flex: 1.2;
  }
}

.warp {
  overflow: hidden;

  &-box {
    // display: flex;
    .warp-box-item:nth-child(2n - 1) {
      background-color: rgba(150, 252, 253, 0.1);
    }

    &-item {
      color: white;
      height: 30px;
      display: flex;

      // .allOver {
      //   width: 95.3px;
      //   overflow: hidden;
      //   text-overflow: ellipsis;
      //   white-space: nowrap;
      // }

      .ranking {
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 64px;

        .trophy {
          margin-right: 5px;
          width: 12px;
          height: 14px;
          background-size: 100% 100%;
        }

        .trophyT1 {
          background-image: url("./img/t1.png");
        }

        .trophyT2 {
          background-image: url("./img/t2.png");
        }

        .trophyT3 {
          background-image: url("./img/t3.png");
        }
      }

      .name {
        flex: 2;
        .nameOver {
          width: 70px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
      .completionRate {
        flex: 1.6;
        font-family: screenSpecial;
      }
      .inHand {
        flex: 1.6;
      }
      .normalCompletion {
        flex: 1.2;
      }

      .overdueCompletion {
        flex: 1.2;
      }
      .overdueNotCompletion {
        flex: 1.2;
      }
    }
  }
}
</style>