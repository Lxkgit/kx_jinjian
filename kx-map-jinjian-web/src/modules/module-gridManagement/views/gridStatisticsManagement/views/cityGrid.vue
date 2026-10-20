<template>
  <PageContent>
    <div class="top">
      <!-- 机构标题 -->
      <orgTitleSelect title="人民银行浙江省分行-2025年">
        <template #right>
          <el-button type="primary" @click="showChange = true">条件切换</el-button>
        </template>
      </orgTitleSelect>
      <!-- 数据统计 -->
      <div class="top-data">
        <tableTitle title="数据统计" class="top-title"></tableTitle>
        <statisticsItem
          height="92"
          :gridColumns="6"
          gridRows="1fr"
        ></statisticsItem>
      </div>
    </div>
    <!-- 过滤器 -->
    <div class="filterBar-box">
        <i-filter-bar
          ref="filterBar"
          slot="filter"
          class="filterBar"
          :columns="searchColumns"
          :gridColumns="2"
        >
        </i-filter-bar>
        <div>
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
    </div>
    </div>
    <!-- 列表 -->
    <table-title title="客户档案列表" class="table-title">
        <template #right>
         <el-button>导出</el-button>
        </template>
    </table-title>
    <div class="i-table">
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        :showPagination="false"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
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
    </div>
    <placeLevelChange v-model="showChange"></placeLevelChange>
  </PageContent>
</template>
  
  <script>
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
import orgTitleSelect from '@/modules/module-gridManagement/components/orgTitleSelect.vue'
import placeLevelChange from '@/modules/module-gridManagement/views/gridManagement/components/placeLevelChange.vue'
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  components: {
    statisticsItem,orgTitleSelect,placeLevelChange
  },
  data() {
    return {
      reservationList: [
        {
          label: "社区网格数量",
          value: "2567",
          unit: "个",
          icon: "icon-fuwushang",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "行政村网格数量",
          value: "61347",
          unit: "个",
          icon: "icon-hangzhengcun",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格责任区数量",
          value: "35722",
          unit: "个",
          icon: "icon-zerenqu",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格责任银行网点数量",
          value: "61347",
          unit: "个",
          icon: "icon-icon-health",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "已对接基层社区（行政村）数量",
          value: "35722",
          unit: "元",
          icon: "icon-shequ",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格专兼职工作人员数量",
          value: "35722",
          unit: "元",
          icon: "icon-haoyou",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "线索摸排次数",
          value: "35722",
          unit: "元",
          icon: "icon-tongjichaxun",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格内开展大篷车、小钱箱等现金服务次数",
          value: "35722",
          unit: "元",
          icon: "icon-qianxiang",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格内宣传次数",
          value: "35722",
          unit: "元",
          icon: "icon-meitixuanchuan",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "宣传受众人数",
          value: "35722",
          unit: "元",
          icon: "icon-renqun",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "发现拒收现金线索数量",
          value: "35722",
          unit: "元",
          icon: "icon-mianshouxufei",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网格协助核查拒收现金线索",
          value: "35722",
          unit: "元",
          icon: "icon-zshuaka",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
      ],
      searchColumns: [
        {
          label: "银行机构名称",
          key: "servePointName",
          type: "text",
        },
      ],
      columns: [
        {
          label: "银行机构名称",
          prop: "servePointName",
          showOverFlowTooltip: true,
          width: 300,
        },
        {
          label: "网格总数",
          prop: "1",
          showOverFlowTooltip: true,
          slot:true
        },
        {
          label: "社区网格数量",
          prop: "servePointAddr",
          showOverFlowTooltip: true,
          slot:true
        },
        {
          label: "行政村网格数量",
          prop: "2",
          showOverFlowTooltip: true,
          slot:true
        },
        {
          label: "责任区网格数量",
          prop: "dutyName",
          showOverFlowTooltip: true,
          width: 150,
        },
        { label: "网格责任银行网点数量", prop: "dutyPhone", width: 150 },
        { label: "已对接基层社区（行政村）数量", prop: "gridName1", width: 150 },
        { label: "网格责任网点数量", prop: "gridName2323", width: 150 },
        { label: "网格专兼职工作人员数量", prop: "gridName11", width: 150 },
        { label: "线索摸排次数", prop: "gridName111", width: 150 },
        { label: "网格内开展现金服务次数", prop: "gridName", width: 200 },
        { label: "网格内小面额兑出数量", prop: "gridName23", width: 150 },
        {
          label: "网格内小面额兑出金额",
          prop: "gridName222",
          width: 150,
        },
        { label: "网格内残损币收兑数量", prop: "gridName4", width: 150 },
        { label: "网格内残损币收兑金额", prop: "gridName421", width: 150 },
        { label: "网格内宣传次数", prop: "gridName5", width: 150 },
        { label: "宣传受众人数", prop: "gridName6", width: 150 },
        { label: "发现拒收现金线索数量", prop: "gridName7", width: 150 },
        { label: "网格协助核查拒收现金线索", prop: "gridName8", width: 150 },
        { label: "操作", slot: true,fixed: "right" },
      ],
      tableBtn: [
        {
          cname: "明细",
          ename: "viewHandle",
          permi: "gridManagement:systemManage:view",
        },
      ],
      tableData: [
        {
          servePointName: "建设银行金华分行",
          servePointAddr: "-",
          dutyPhone: "-",
          gridName: "-",
          dutyName: "-",
        },
        {
          servePointName: "工商银行杭州分行",
          servePointAddr: "-",
          dutyPhone: "-",
          gridName: "-",
          dutyName: "-",
        },
      ],
      maxHeight: 270,
      showChange:false,
    };
  },
  computed: {},
  methods: {
    viewHandle() {
      this.$router.push({
        name: "GridList",
      });
    },
  },
};
</script>
  
  <style scoped lang="scss">
.top {
  &-data {
    margin-top: 12px;
    padding: 12px 16px;
    background: #fff;
  }
  .top-title {
    position: relative;
    margin-bottom: 24px;
  }
  .top-title ::after {
    content:'';
    position: absolute;
    bottom:-8px;
    left: -16px;
    width: calc(100% + 32px);
    height: 1px;
    background:#E7EAF6;
  }
}
.filterBar-box {
  margin: 12px 0;
  background: #fff;
  padding: 16px;
  display: flex;
  height: 120px;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 60px;
}
.i-table {
  height: calc(100vh - 64px - 56px - 64px - 256px - 68px - 60px - 24px - 24px);
  background: #fff;
  padding: 0 16px;
}
</style>