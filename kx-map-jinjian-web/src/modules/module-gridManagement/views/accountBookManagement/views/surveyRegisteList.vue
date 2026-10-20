<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <ul>
            <h2>银行机构</h2>
            <li
              v-for="(item, index) in bankList"
              :key="item.bankName"
              :class="[index === activeBankIndex ? 'active' : '']"
              @click="clickTree(item, index)"
            >
              <span :title="item.bankName">{{ item.bankName }}</span>
            </li>
          </ul>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
        labelWidth="90px"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
      >
      </i-button-bar>
      <!-- 列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :maxHeight="maxHeight"
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        @selectionChange="selectionChange"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  dicts: ["had_survey_need"],
  data() {
    return {
      searchColumns: [
        {
          label: "网格名称",
          key: "gridName",
          type: "text",
        },
        {
          label: "责任网点",
          key: "dutyBranch",
          type: "text",
        },
        {
          label: "是否落实全量摸排需求",
          key: "isAllSurveyNeed",
          type: "select",
          options: () => this.dict.type.had_survey_need,
        },
      ],
      btnLists: [
        // {
        //   icon: "el-icon-s-promotion",
        //   cname: "导出",
        //   ename: "export",
        //   type: "info",
        //   disabled: () => this.multiSelected.length === 0,
        // },
      ],
      tableBtn: [
        {
          cname: "商户摸排详情",
          ename: "storeDetailHandle",
        },
      ],
      columns: [
        { label: "网格名称", prop: "gridName", showOverFlowTooltip: true },
        { label: "责任网点", prop: "dutyBranch", showOverFlowTooltip: true },
        { label: "银行机构", prop: "bankBranch", showOverFlowTooltip: true },
        { label: "商户数量(个)", prop: "storeNum", showOverFlowTooltip: true },
        {
          label: "是否落实全量摸牌需求",
          prop: "isAllSurveyNeed",
          showOverFlowTooltip: true,
          formatter: ({ isAllSurveyNeed }) => {
            return this.dict.label.had_survey_need[isAllSurveyNeed];
          },
        },
        {
          label: "红牌商户数",
          prop: "redStoreNum",
        },
        { label: "操作", slot: true },
      ],
      tableData: [],
      activeBankIndex: 0,
      bankName: "",
    };
  },
  computed: {
    ...mapState("accountBook", [
      "bankList",
      "gridPointList",
      "nowGridInfo",
      "storeList",
    ]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateNowGridInfo"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        let arr = this.gridPointList.filter(
          (item) => item.bankId == this.bankList[this.activeBankIndex].bankId
        );
        arr.forEach((i) => {
          i.redStoreNum = this.storeList.filter(
            (j) => j.gridId == i.id && j.storeClassify == "1"
          ).length;
        });
        this.total = arr.length;
        this.tableData = [...arr];
        // this.loading = true;
        // service
        //   .anchorPointList(param)
        //   .then((res) => {
        //     const { total, rows } = res;
        //     this.tableData = rows;
        //     this.total = total;
        //     this.loading = false;
        //     this.multiSelected = [];
        //   })
        //   .catch((error) => {
        //     if (error) {
        //       this.tableData = [];
        //       this.total = 0;
        //     }
        //     this.multiSelected = [];
        //     this.loading = false;
        //   });
      });
    },
    export() {
      // console.log(this.multiSelected);
    },
    storeDetailHandle(row) {
      this.updateNowGridInfo(row);
      // console.log("nowGridInfo", this.nowGridInfo);
      this.$router.push({ name: "StoreSurveyDetailList" });
    },
    clickTree(item, index) {
      this.activeBankIndex = index;
      this.bankName = item.bankName;
      this.fetchList();
    },
  },
};
</script>

<style lang="scss" scoped>
.tree-wrapper {
  overflow: auto;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  &::-webkit-scrollbar {
    width: 3px;
  }
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  &::-webkit-scrollbar-thumb {
    background: #409eff;
    border-radius: 2px;
  }
  // .tree-content {
  //   width: 100%;
  //   height: calc(100% - 46px);
  //   overflow: auto;
  //   ::v-deep .el-tree-node > .el-tree-node_children {
  //     overflow: visible;
  //   }
  // }
  ul {
    h2 {
      text-align: center;
      font-size: 20px;
      margin-bottom: 10px;
    }
    li {
      font-size: 16px;
      padding: 8px;
      color: #333;
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      span:nth-of-type(1) {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    .active {
      color: #fff;
      background: #409eff;
      border-radius: 4px;
    }
  }
}
</style>