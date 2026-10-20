<template>
  <PageContent>
    <div class="top">
      <div class="top-title">
        <h2>抽查商户主体清单</h2>
        <el-radio-group v-model="radio">
          <el-radio-button label="0"
            >全部（{{ this.tableList.length }}）</el-radio-button
          >
          <el-radio-button label="1"
            >未完成（{{
              this.tableList.filter((item) => item.checkStatus == "1").length
            }}）</el-radio-button
          >
          <el-radio-button label="2"
            >已完成（{{
              this.tableList.filter((item) => item.checkStatus == "2").length
            }}）</el-radio-button
          >
        </el-radio-group>
      </div>
      <el-button class="goback" size="mini" @click="$router.go(-1)"
        >返回</el-button
      >
      <i-table
        :allColumns="tableColumns"
        align="center"
        :data="tableData"
        :maxHeight="maxHeight"
        :total="tableData.length"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
      >
        <template #action="scope">
          <el-button type="text" @click="jlclick(scope.row)">记录</el-button>
        </template>
      </i-table>
    </div>
    <div class="bottom">
      <div class="top-title">
        <h2>抽查银行网点清单</h2>
        <el-radio-group v-model="radio1">
          <el-radio-button label="0"
            >全部（{{ this.tableList2.length }}）</el-radio-button
          >
          <el-radio-button label="1"
            >未完成（{{
              this.tableList2.filter((item) => item.checkStatus == "1").length
            }}）</el-radio-button
          >
          <el-radio-button label="2"
            >已完成（{{
              this.tableList2.filter((item) => item.checkStatus == "2").length
            }}）</el-radio-button
          >
        </el-radio-group>
      </div>

      <i-table
        :allColumns="tableColumns2"
        align="center"
        :data="tableData2"
        :maxHeight="maxHeight"
        :total="tableData2.length"
        :pageNum="formInline2.pageNum"
        :pageSize="formInline2.pageSize"
      >
        <template #action="scope">
          <el-button type="text" @click="networkclick(scope.row)"
            >记录</el-button
          >
        </template>
      </i-table>
    </div>
    <spotDetail v-model="showDetail" :defaultValue="defaultValue"></spotDetail>
    <networkSpotDetailVue
      v-model="shownetWorkDetail"
      :defaultValue="defaultValue"
    ></networkSpotDetailVue>
  </PageContent>
</template>
  
<script>
import { mapState, mapMutations } from "vuex";
import spotDetail from "../components/meechantSpotDetail.vue";
import networkSpotDetailVue from "../components/networkSpotDetail.vue";

export default {
  components: {
    spotDetail,
    networkSpotDetailVue,
  },
  data() {
    return {
      checkStatusOption: {
        1: "未完成",
        2: "已完成",
      },
      tableColumns: [
        { label: "商户主体名称", prop: "storeName" },
        { label: "商户主体行业", prop: "storeIndustry" },
        { label: "商户主体地址", prop: "storeAddress" },
        { label: "所在网格", prop: "belongGrid" },
        { label: "商户主体联系人", prop: "storePerson" },
        { label: "联系电话", prop: "storePhone" },
        {
          label: "抽查状态",
          prop: "checkStatus",
          formatter: ({ checkStatus }) => {
            return this.checkStatusOption[checkStatus];
          },
        },
        { label: "抽查时间", prop: "checkTime" },
        { label: "抽查人员", prop: "checkPerson" },
        { label: "操作", slot: true },
      ],
      tableList: [],
      tableList2: [],
      tableColumns2: [
        { label: "网点名称", prop: "pointName" },
        { label: "网点地址", prop: "pointAddress" },
        { label: "所在网格", prop: "belongGrid" },
        { label: "网点负责人", prop: "dutyPerson" },
        { label: "负责人电话", prop: "dutyPersonPhone" },
        {
          label: "抽查状态",
          prop: "checkStatus",
          formatter: ({ checkStatus }) => {
            return this.checkStatusOption[checkStatus];
          },
        },
        { label: "抽查时间", prop: "checkTime" },
        { label: "抽查人员", prop: "checkPerson" },
        { label: "操作", slot: true },
      ],
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      formInline2: {
        pageNum: 1,
        pageSize: 10,
      },
      maxHeight: "300px",
      showDetail: false,
      shownetWorkDetail: false,
      radio: "0",
      radio1: "0",
      defaultValue: {},
    };
  },
  computed: {
    ...mapState("accountBook", [
      "cashUseEnvCheckList",
      "cashUseEditStatus",
      "nowCashUseCheckInfo",
    ]),
    tableData() {
      if (this.radio == 0) {
        return this.tableList;
      } else if (this.radio == 1) {
        return this.tableList.filter((item) => item.checkStatus == "1");
      } else if (this.radio == 2) {
        return this.tableList.filter((item) => item.checkStatus == "2");
      }
    },
    tableData2() {
      if (this.radio1 == 0) {
        return this.tableList2;
      } else if (this.radio1 == 1) {
        return this.tableList2.filter((item) => item.checkStatus == "1");
      } else if (this.radio1 == 2) {
        return this.tableList2.filter((item) => item.checkStatus == "2");
      }
    },
  },
  watch: {},
  mounted() {
    this.initData();
  },
  methods: {
    ...mapMutations("accountBook", [
      "updateCashUseEnvCheckList",
      "updateCashEditStatus",
      "updateNowCashUseCheckInfo",
    ]),
    initData() {
      this.tableList = this.nowCashUseCheckInfo.checkStoreList;
      this.tableList2 = this.nowCashUseCheckInfo.checkPointList;
    },
    jlclick(item) {
      this.defaultValue = item;
      this.showDetail = true;
    },
    networkclick(item) {
      this.defaultValue = item;
      this.shownetWorkDetail = true;
    },
  },
};
</script>
  
<style scoped lang="scss">
.top {
  width: 100%;
  padding: 15px;
  border: 1px solid #ddd;
  box-sizing: border-box;
  position: relative;
  .goback {
    position: absolute;
    top: 15px;
    right: 15px;
  }
}
.bottom {
  width: 100%;
  border: 1px solid #ddd;
  padding: 15px;
  margin-top: 10px;
}
h2 {
  font-size: 18px;
  font-weight: 700;
}
.top-title {
  h2 {
    margin-right: 10px;
  }
  height: 40px;
  display: flex;
  align-items: center;
}
</style>