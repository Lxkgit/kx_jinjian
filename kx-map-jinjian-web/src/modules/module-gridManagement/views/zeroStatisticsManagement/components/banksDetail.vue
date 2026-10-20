<template>
  <div class="bank-detail">
    <orgTitle title="" goBack></orgTitle>
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
    <table-title title="数据列表" class="table-title"></table-title>
    <div class="i-table">
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe border
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum" :maxHeight="490"
               :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </div>
  </div>
</template>

<script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  components: {
    orgTitle, tableTitle
  },
  data() {
    return {
      searchColumns: [
        { label: '客户名称', key: 'name', type: 'text' }
      ],
      tableBtn: [
        {
          cname: "查看详情",
          ename: "viewHandle",
        },
      ],
      columns: [
        { label: "客户名称", prop: "servePointName", showOverFlowTooltip: true, },
        { label: "零钞卷别", prop: "11211", showOverFlowTooltip: true, },
        { label: "预约数量", prop: "112", showOverFlowTooltip: true, },
        { label: "是否预约兑换“零钱包”", prop: "234", showOverFlowTooltip: true, },
        { label: "是否选择“大篷车小钱箱”现场兑换", prop: "21321312", showOverFlowTooltip: true, },
        { label: "是否上门配送", prop: "213213", showOverFlowTooltip: true, },
        { label: "操作", slot: true },
      ],
      tableData: [
        {
          servePointName: "客户名称",
          11211: "零钞卷别",
          112: "预约数量",
          234: "是否预约兑换“零钱包”",
          21321312: "是否选择“大篷车小钱箱”现场兑换",
          213213: "是否上门配送",
        }
      ],
    }
  },
  methods: {
    fetchList() { },
    viewHandle(data) {
      this.$router.push({
        name: "CitySystem",
      });
    },
  },
}
</script>

<style lang="scss" scoped>
.filterBar-box {
  margin-top: 12px;
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
  height: calc(100vh - 64px - 56px - 120px - 120px - 12px);
  background: #fff;
  padding: 0 16px;
}
</style>