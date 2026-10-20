<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" class="filterBar"
                    :gridColumns="3">
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <!-- 列表 -->
    <table-title title="数据列表" class="table-title">
      <template #right>
        <el-button @click="clickHandle('exportHandle')">导出</el-button>
      </template>
    </table-title>
    <div class="i-table">
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe border
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum" :maxHeight="570"
               :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </div>
  </PageContent>
</template>
    
    <script>
import pageMixin from "@/mixins/pageMixin";
import optionMixin from "@/mixins/optionMixin";
export default {
  mixins: [pageMixin, optionMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "年度",
          key: "year",
          type: "select",
          options: [
            { label: '2021年', value: '2021' },
            { label: '2022年', value: '2022' },
            { label: '2023年', value: '2023' },
            { label: '2024年', value: '2024' },
            { label: '2025年', value: '2025' }
          ]
        },
        {
          label: "银行网点名称",
          key: "servePointName",
          type: "text",
        },
      ],
      tableBtn: [
        {
          cname: "查看详情",
          ename: "viewHandle",
        },
      ],
      columns: [
        { label: "银行网点名称", prop: "servePointName", showOverFlowTooltip: true,},
        { label: "预约笔数", prop: "112", showOverFlowTooltip: true,},
        { label: "预约金额", prop: "11211", showOverFlowTooltip: true,},
        { label: "已完成笔数", prop: "234", showOverFlowTooltip: true,},
        { label: "实际已兑换金额", prop: "21321312", showOverFlowTooltip: true,},
        { label: "待受理笔数", prop: "2132131221", showOverFlowTooltip: true,},
        { label: "银行网点超期未受理笔数", prop: "dutyPhone" },
        { label: "银行网点受理后客户未兑换笔数", prop: "dutyPhone11" },
        { label: "客户主动取消预约笔数", prop: "gridName1" },
        { label: "操作", slot: true },
      ],
      tableData: [{
        servePointName: '-',
        servePointAddr: '-',
        dutyPhone: '-',
        gridName: '-',
        dutyName: '-',
      }],
      info: {},
      showDetail: false,
    };
  },
  mounted() {
    //   this.fetchList();
  },
  methods: {
    fetchList() { },
    viewHandle(data) {
      this.$router.push({
        name: "BanksDetail",

      });
    },
    exportHandle() {

    },
  },
};
</script>
    
    <style lang="scss" scoped>
.filterBar-box {
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
  margin-bottom: 12px;
}
.table-title {
  padding: 16px;
  height: 68px;
}
.i-table {
  height: calc(100vh - 64px - 56px - 120px - 12px);
  background: #fff;
  padding: 0 16px;
}
</style>