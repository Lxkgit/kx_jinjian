<template>
  <div>
    <pop-title class="margin-top-total" text="走访登记信息" fontSize="15px" color="#4ccbcc">
      <div class="date" v-if="tableData.length > 0">({{ createTime }})</div>
    </pop-title>
    <!-- 详细信息 -->
    <info v-if="isShowInfo" :baseInfo="baseInfo" />
    <div style="padding: 10px 0px 10px 40px">历史走访记录:</div>
    <el-table :data="tableData" stripe style="width: 100%; padding: 0 0 20px 40px" :header-cell-style="tableHeaderStyle">
      <el-table-column align="center" prop="createTime" label="走访时间" width="180">
      </el-table-column>
      <el-table-column align="center" prop="createUserName" label="走访人员" width="180">
      </el-table-column>
      <el-table-column align="center" prop="intentionalProduct" label="意向产品">
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="{row,$index}">
          <span class="see" @click="seeInfo($index)">查看详情</span>
        </template>
      </el-table-column>
    </el-table>
    <br>
    <info v-if="isShow" :baseInfo="baseInfoNew" />
  </div>
</template>

<script>
// import {
//   detailStoreVisitRecord, //  走访记录 详情
//   listByStorevisitHistory, // 商户历史走访记录分页
// } from "@/api/meshMap/meshIndex.js";
import popTitle from "./popTitle"; // 楼宇锚点
import info from "./info.vue";
import visitInfo from "../mock/visitInfo.json";
export default {
  name: "interView",
  components: {
    info,
    popTitle,
  },
  props: {
    institutionId: {
      type: [Number, String],
      // default:'395210'
      default: "",
    },
  },
  data() {
    return {
      isShowInfo: true,
      isShow: false,
      tableData: [],
      baseInfo: {},
      baseInfoNew: {},
      isBaseNew: false,
    };
  },
  computed: {
    createTime() {
      const dateString = this.tableData[0].createTime;
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      return year + "-" + month + "-" + day;
    },
  },
  mounted() {
    this.getlistByStorevisitHistory();
  },
  methods: {
    // 获取走访记录
    getlistByStorevisitHistory() {
      this.tableData = visitInfo;
      this.getdetailStoreVisitRecord(0);

    },
    // 走访记录 详情
    getdetailStoreVisitRecord(id) {
      let data = { ...visitInfo[id] }
      data.normalOperate = data.normalOperate === "1" ? "是" : "否";
      data.licenseValid = data.licenseValid === "1" ? "是" : "否";
      data.addressType = data.addressType === "1" ? "自有" : "租赁";
      data.creditDemand = data.creditDemand === "1" ? "有" : "无";
      data.otherBankProduct =
        data.otherBankProduct === "1" ? "是" : "否";
      if (this.isShow) {
        this.baseInfoNew = data;
      } else {
        this.baseInfo = data;
      }
      this.isShowInfo = true;
    },
    seeInfo(id) { 
      this.isShow = true;
      this.getdetailStoreVisitRecord(id);
    },
    // 表格表头颜色更改
    tableHeaderStyle({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "background:#e6f4f6;";
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.margin-top-total {
  margin: 10px;
}
:fullscreen .margin-top-total {
  margin: 20px 0 30px 10px !important;
}

:fullscreen .margin-top-num {
  margin: 60px 0 30px 10px !important;
}
.date {
  color: #1b7a7b;
  font-size: 16px;
  margin-left: 10px;
  font-weight: 500;
}
.see {
  display: inline-block;
  color: #5dadef;
  line-height: 22px;
}
.see:hover {
  border-bottom: 1px solid #00b5fe;
  cursor: pointer;
}
</style>