<template>
  <div>
    <div v-show="cardList && cardList.length > 0">
      <ul class="cardUl">
        <li v-for="item in cardList" :key="item" :class="{active:item == cardNumber}" @click="toggleCard(item)">卡号: <span>{{ item }}</span></li>
      </ul>
      <div class="form">
        <span>创建时间</span>
        <el-date-picker v-model="transactionRecordTime" :picker-options="pickerOptions" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期" size="mini" />
        <el-button icon="el-icon-search" type="primary" size="mini" @click="custViewProdBankCardDetail">查询</el-button>
      </div>
      <el-table v-if="cardList.length > 0" :data="tableData" fit size="small" highlight-current-row stripe height="calc(100vh - 340px)" style="width: 100%;">
        <el-table-column v-for="item in recordsColumns" :key="item.prop" :prop="item.prop" :label="item.label" header-align="center" align="center" />
      </el-table>
    </div>
  </div>
</template>

<script>
import { timeFormat } from "../utils";;
import popTitle from "./popTitle";

export default {
  // 组件名称
  name: "transactionRecords",
  // 组件参数 接收来自父组件的数据
  // 局部注册的组件
  components: {
    popTitle
  },
  props: {
    cardList: {
      type: Array, default: () => []
    },
    tableData: {
      type: Array, default: () => []
    },
  },
  // 组件状态值
  data() {
    return {
      recordsColumns: [
        { label: "交易时间", prop: "dealTime" },
        { label: "交易金额", prop: "amnt" },
        { label: "交易机构", prop: "tradeOrg" },
        { label: "摘要", prop: "desc" },
        { label: "对方户名", prop: "toAccount" }
      ],
      pickerOptions: {
        disabledDate: val => {
          const now = Date.now();
          const time = timeFormat(now, "yyyy-MM-dd");
          const arr = time.split("-");
          const lastYear = +new Date(`${+arr[0] - 1}-${arr[1]}-${arr[2]}`);
          if (this.selectData !== "") {
            const selectTime = timeFormat(this.selectData, "yyyy-MM-dd").split(
              "-"
            );
            let minTimeMonth = +selectTime[1] - 3;
            let minTimeYear = selectTime[0];
            if (selectTime[1] <= 3) {
              minTimeMonth = +selectTime[1] + 12 - 3;
              minTimeYear = +selectTime[0] - 1;
            }
            const minTime = +new Date(
              `${minTimeYear}-${minTimeMonth}-${selectTime[2]}`
            );
            let maxTimeMonth = +selectTime[1] + 3;
            let maxTimeYear = selectTime[0];
            if (selectTime[1] > 9) {
              maxTimeMonth = (+selectTime[1] + 3) % 12;
              maxTimeYear = +selectTime[0] + 1;
            }
            const maxTime = +new Date(
              `${maxTimeYear}-${maxTimeMonth}-${selectTime[2]}`
            );
            const D1 = Math.min(
              this.selectData - lastYear,
              this.selectData - minTime
            );
            const D2 = Math.min(
              maxTime - this.selectData,
              now - this.selectData
            );
            return (
              val.getTime() > this.selectData + D2 ||
              val.getTime() < this.selectData - D1
            );
          }
          return val.getTime() > now || val.getTime() < lastYear;
        },
        onPick: ({ maxDate, minDate }) => {
          this.selectData = maxDate ? "" : minDate.getTime();
        }
      },
      selectData: "",
      transactionRecordTime: [],
      cardNumber: "",
    };
  },
  // 计算属性
  computed: {},
  // 侦听器
  watch: {},
  created() {
    this.cardNumber = '';

  },
  mounted() { },
  // 组件方法
  methods: {
    toggleCard(val) {
      this.cardNumber = val;
    },
    custViewProdBankCardDetail() {
      this.$emit('handleQuery', { cardNumber: this.cardNumber, transactionRecordTime: this.transactionRecordTime })
    },
  }
};
</script>

<style scoped  lang="scss">
.cardUl {
  display: flex;
  flex-wrap: wrap;
  li {
    list-style: none;
    background-color: rgba(238, 238, 243, 0.37);
    color: #999;
    border: 1px solid #999;
    border-radius: 4px;
    padding: 6px 15px;
    margin: 0 10px 10px 0;
    cursor: pointer;
    &.active {
      background: #fff9f0;
      border-color: #ea931c;
      color: #e9921c;
    }
  }
}
.form {
  margin-bottom: 10px;
}
</style>