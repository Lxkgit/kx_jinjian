<template>
  <div>
    <PageContent>
      <div class="top">
        <el-button type="text">评分标准查看</el-button>
        <el-button @click="$router.go(-1)">返回</el-button>
      </div>
      <div class="one" v-for="(item, idx) in tableColumns1" :key="idx">
        <div class="one-left">
          <div class="title">{{ item.title }}</div>
          <div class="desc">{{ item.leftDesc }}</div>
        </div>
        <div class="one-middleContent border-l-t">
          <div
            class="one-middleContent-item middleLine border-r-b"
            v-for="(i, index) in item.middleList"
            :key="index"
          >
            <div class="text">{{ i.text }}</div>
            <div class="value">{{ i.value }}</div>
          </div>
        </div>
        <div class="one-right border-l-t">
          <div
            class="one-right-item middleLine border-r-b"
            v-for="(i, j) in item.rightList"
            :key="j"
          >
            <div class="text">{{ i.text }}</div>
            <div class="value">{{ i.value }}</div>
          </div>
        </div>
      </div>
      <div class="two" :style="{ height: contentHeight + 'px' }">
        <div class="two-left">
          <div class="two-left-top">
            <div>金融机构零钞服务质量</div>
            <div>(25分)</div>
          </div>
          <div class="bottom-box">
            <div>总计</div>
            <div>(100分)</div>
          </div>
        </div>
        <div class="two-right">
          <div class="two-right-top border-l-t">
            <div class="box1">
              <div class="box1-rows">
                <div
                  class="box1-rows-item border-r-b"
                  v-for="(column, m) in contentColumns"
                  :key="m"
                >
                  {{ column.text }}
                </div>
              </div>
              <div
                class="box1-rows"
                v-for="(point, i) in bankPointList"
                :key="i"
              >
                <div
                  class="box1-rows-item border-r-b"
                  v-for="(column, m) in contentColumns"
                  :key="m"
                >
                  {{ point[column.key] }}
                </div>
              </div>
            </div>
            <div class="evaluation">
              <div class="evaluation-top border-r-b">评价得分</div>
              <div class="evaluation-bottom">
                <div class="evaluation-bottom-left">
                  <div
                    class="evaluation-bottom-left-item border-r-b"
                    v-for="(item, idx) in bankPointList"
                    :key="idx"
                  >
                    21
                  </div>
                </div>
                <div class="evaluation-bottom-right border-r-b">21</div>
              </div>
            </div>
          </div>
          <div class="bottom-box">
            <div>最终得分</div>
            <div>(90分)</div>
          </div>
        </div>
      </div>
    </PageContent>
  </div>
</template>
    
<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {
      tableColumns1: [
        {
          title: "整治拒收现金宣传情况",
          leftDesc: "(25分)",
          middleList: [
            { text: "知晓宣传信息的商户", value: "39" },
            { text: "抽查的商户总数", value: "50" },
            { text: "宣传触达率", value: "89%" },
          ],
          rightList: [
            { text: "扣分", value: "-3" },
            { text: "评价得分", value: "22" },
          ],
        },
        {
          title: "收取现金和找零情况",
          leftDesc: "(25分)",
          middleList: [
            { text: "备付零钞的商户数量", value: "39" },
            { text: "抽查的商户总数", value: "50" },
            { text: "零钞备付率", value: "89%" },
          ],
          rightList: [
            { text: "扣分", value: "-3" },
            { text: "评价得分", value: "22" },
          ],
        },
        {
          title: "网格化管理覆盖情况",
          leftDesc: "(25分)",
          middleList: [
            { text: "责任网点到访的商户数量", value: "39" },
            { text: "抽查的商户总数", value: "50" },
            { text: "网格化管理覆盖率", value: "89%" },
          ],
          rightList: [
            { text: "扣分", value: "-3" },
            { text: "评价得分", value: "22" },
          ],
        },
      ],
      contentColumns: [
        { text: "网点名称", key: "bankPointName", value: "" },
        { text: "零钱包兑换排队时间超过15分钟", key: "key1", value: "" },
        {
          text: "现场不能兑换较小数量零钞，且不主动说明预约方式的",
          key: "key2",
          value: "",
        },
        { text: "对外支付现金中残缺亏损人民币", key: "key3", value: "" },
        {
          text: "客户未预约无法进行小面额人民币取现、兑换",
          key: "key4",
          value: "",
        },
        { text: "外拓零钞服务需要网点员工垫款", key: "key5", value: "" },
        { text: "未为老年客户提供便利化现金服务", key: "key6", value: "" },
      ],
      bankPointList: [],
      columnNum: 19,
    };
  },
  computed: {
    ...mapState("accountBook", ["nowCashUseCheckInfo"]),
    contentHeight() {
      return (this.bankPointList.length + 1) * 70 + 80;
    },
  },
  mounted() {
    this.initData();
  },
  methods: {
    initData() {
      let obj = {
        key1: "/",
        key2: "/",
        key3: "/",
        key4: "/",
        key5: "/",
        key6: "/",
      };
      let pointList = this.nowCashUseCheckInfo.checkPointList;
      pointList.forEach((i) => {
        this.bankPointList.push({ bankPointName: i.pointName, ...obj });
      });
    },
  },
};
</script>
    
<style lang="scss" scoped>
.top {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.one {
  display: flex;
  align-items: center;
  height: 80px;
  margin-bottom: 20px;
  width: 100%;
  &-left {
    border: 1px solid #999;
    width: 200px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-right: 20px;
  }
  &-middleContent {
    flex: 1;
    display: flex;
    align-items: center;
    height: 100%;
    &-item {
      flex: 1;
      height: 100%;
      // border: 1px solid #999;
      display: flex;
      justify-content: space-around;
      align-items: center;
      flex-direction: column;
    }
  }
  &-right {
    height: 100%;
    display: flex;
    align-items: center;
    margin-left: 20px;
    &-item {
      height: 100%;
      display: flex;
      justify-content: space-around;
      align-items: center;
      flex-direction: column;
      &:first-child {
        width: 80px;
      }
      &:last-child {
        width: 150px;
      }
    }
  }
}
.middleLine {
  position: relative;
  &::after {
    position: absolute;
    content: "";
    width: 100%;
    height: 1px;
    top: 50%;
    left: 0;
    background: #999;
  }
}
.two {
  display: flex;
  align-items: center;
  &-left {
    height: 100%;
    width: 200px;
    margin-right: 20px;
    &-top {
      border: 1px solid #999;
      display: flex;
      justify-content: space-around;
      align-items: center;
      flex-direction: column;
      height: calc(100% - 80px);
    }
  }
  &-right {
    height: 100%;
    flex: 1;
    &-top {
      display: flex;
      justify-content: space-around;
      align-items: center;
      .box1 {
        flex: 1;
        height: 100%;
        &-rows {
          display: flex;
          align-items: center;
          &-item {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            height: 70px;
          }
        }
      }
      .evaluation {
        width: 150px;
        height: 100%;
        display: flex;
        flex-direction: column;
        &-top {
          height: 70px;
          display: flex;
          justify-content: center;
          align-items: center;
        }
        &-bottom {
          flex: 1;
          height: 100%;
          display: flex;
          align-items: center;
          &-left {
            flex: 1;
            height: 100%;
            display: flex;
            align-items: center;
            flex-direction: column;
            &-item {
              flex: 1;
              width: 100%;
              display: flex;
              justify-content: center;
              align-items: center;
            }
          }
          &-right {
            flex: 1;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
          }
        }
      }
      height: calc(100% - 80px);
    }
  }
}
.border-l-t {
  border-left: 1px solid #999;
  border-top: 1px solid #999;
}
.border-r-b {
  border-right: 1px solid #999;
  border-bottom: 1px solid #999;
}
.bottom-box {
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
  border: 1px solid #999;
  height: 60px;
  margin-top: 20px;
}
.flex-center {
  display: flex;
  justify-content: center;
  align-items: center;
}
.flex-center-v {
  display: flex;
  align-items: center;
}
</style>