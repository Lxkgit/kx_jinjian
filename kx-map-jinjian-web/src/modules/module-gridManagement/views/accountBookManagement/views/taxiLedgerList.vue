<template>
  <div>
    <PageContent>
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
    </PageContent>
    <addTaxiDetail
      v-model="showDetail"
      :status="status"
      :defaultValue="defaultValue"
    ></addTaxiDetail>
  </div>
</template>
  
  <script>
import { mapState, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addTaxiDetail from "../components/addTaxiDetail.vue";
const isArr = ["否", "是"];
export default {
  name: "surveyRegisteList",
  components: {
    addTaxiDetail,
  },
  mixins: [pageMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "所在网格",
          key: "gridName",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "新增巡查台账",
          ename: "addList",
          type: "primary",
        },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "删除",
          ename: "deleteHandle",
        },
      ],
      columns: [
        {
          label: "出租车公司名称",
          prop: "name",
          showOverFlowTooltip: true,
        },
        {
          label: "出租车车牌号",
          prop: "number",
          showOverFlowTooltip: true,
        },
        {
          label: "上车点",
          prop: "point",
          showOverFlowTooltip: true,
        },
        {
          label: "是否收取现金",
          prop: "isCash",
          showOverFlowTooltip: true,
          formatter: ({ isCash }) => {
            return isArr[isCash];
          },
        },
        {
          label: "零钱备付是否充足",
          prop: "smallCashCz",
          showOverFlowTooltip: true,
          formatter: ({ smallCashCz }) => {
            return isArr[smallCashCz];
          },
        },
        {
          label: "是否有银行上门开展零钱包兑换服务",
          prop: "exchange",
          showOverFlowTooltip: true,
          formatter: ({ exchange }) => {
            return isArr[exchange];
          },
        },
        {
          label: "是否有了解整治拒收人民币现金等政策",
          prop: "cleanUp",
          formatter: ({ cleanUp }) => {
            return isArr[cleanUp];
          },
        },
        {
          label: "是否知晓附近的零钱包兑换银行",
          prop: "isKonw",
          formatter: ({ isKonw }) => {
            return isArr[isKonw];
          },
        },
        {
          label: "巡查人",
          prop: "inspector",
        },
        {
          label: "巡查时间",
          prop: "inspectionTime",
        },
        {
          label: "记录时间",
          prop: "recordTime",
        },
        {
          label: "所在网格",
          prop: "girdName",
        },
        { label: "操作", slot: true },
      ],
      tableData: [],
      showDetail: false,
      status: "add",
      defaultValue: {},
    };
  },
  computed: {
    ...mapState("accountBook", ["taxiList"]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateTaxiList"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        this.total = this.taxiList.length;
        this.tableData = [...this.taxiList];
      });
    },
    addList() {
      this.status = "add";
      this.showDetail = true;
    },
    viewHandle(row) {
      this.defaultValue = row;
      this.status = "view";
      this.showDetail = true;
    },
    editHandle(row) {
      this.defaultValue = row;
      this.status = "edit";
      this.showDetail = true;
    },
    deleteHandle(row) {
      this.makeSure("确认删除吗", () => {
        let arr = [];
        arr = this.taxiList.filter((item) => !(item.id == row.id));
        this.updateTaxiList([...arr]);
        this.$message.success("删除成功");
        this.fetchList();
      });
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