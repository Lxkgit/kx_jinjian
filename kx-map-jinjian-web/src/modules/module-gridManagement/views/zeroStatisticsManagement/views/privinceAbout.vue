<template>
  <PageContent>
    <div class="top">
       <!-- 机构标题 -->
       <orgTitleSelect title="人民银行浙江省分行-2025年">
          <template #right>
            <el-button type="primary" @click="showDetail = true">条件切换</el-button>
          </template>
      </orgTitleSelect>
      <div class="top-data">
        <tableTitle title="数据统计" class="top-title"></tableTitle>
        <highLightCard :dataArr="cardData"></highLightCard>
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
    <table-title title="数据明细" class="table-title">
        <template #right>
         <el-button>导出</el-button>
        </template>
    </table-title>
    <div class="bottom">
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe border :allColumns="columns"
        :total="total" :pageNum="formInline.pageNum" :maxHeight="500" :pageSize="formInline.pageSize"
        @currentChange="currentChange" @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)" :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </div>
    <filterDetail v-model="showDetail" @handleConfirm="handleConfirm"></filterDetail>
  </PageContent>
</template>

<script>
import pieChart from "../components/pieChart.vue";
import pageMixin from "@/mixins/pageMixin";
import filterDetail from "../../merStatisticsManagement/components/filterDetail.vue";
import orgTitleSelect from '@/modules/module-gridManagement/components/orgTitleSelect.vue'
import highLightCard from '@/modules/module-gridManagement/components/highLightCard'
export default {
  mixins: [pageMixin],
  components: {
    pieChart,
    filterDetail,orgTitleSelect,highLightCard
  },
  data() {
    return {
      cardData:[
        { title: '零钞预约金额', value: '1423432432423', unit: '元'},
        { title: '客户主动取消预约笔数', value: '12345', unit: '笔'},
        { title: '受理后客户未兑换笔数', value: '213213', unit: '笔'},
        { title: '实际已兑换金额', value: '1423432432423', unit: '元'},
        { title: '超期未受理笔数', value: '1423432432423', unit: '笔'},
        { title: '已完成笔数', value: '22222222', unit: '笔'},
      ],
      searchColumns: [
        {
          label: "地市分行名称",
          key: "servePointName",
          type: "text",
        },
      ],
      columns: [
        { label: "银行机构名称",prop: "servePointName",showOverFlowTooltip: true },
        { label: "银行网点数量",prop: "12",showOverFlowTooltip: true },
        { label: "预约笔数", prop: "servePointAddr",showOverFlowTooltip: true,  },
        { label: "预约金额", prop: "dutyName",showOverFlowTooltip: true,},
        { label: "已完成笔数", prop: "dutyPhone" },
        { label: "实际已兑换金额", prop: "gridName" },
        { label: "待受理笔数", prop: "gridName1", width: 150 },
        { label: "银行网点超期未受理笔数", prop: "gridName2", width: 150 },
        { label: "银行网点受理后客户未兑换笔数", prop: "gridName3", width: 150 },
        { label: "客户主动取消预约笔数", prop: "gridName4", width: 150 },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "明细",
          ename: "viewHandle",
        },
      ],
      tableData: [
        {
          servePointName:'杭州市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'宁波市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'温州市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'绍兴市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'湖州市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'嘉兴市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'金华市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'衢州市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'舟山市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'台州市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
        {
          servePointName:'丽水市',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        },
      ],
      showDetail: false,
    };
  },
  computed: {},
  methods: {
    viewHandle() {
      this.$router.push({
        name: "CityAbout",
      });
    },
    viewDetail() {
      this.showDetail = true;
    },
    handleConfirm(value) {
      console.log(value);
    },
  },
};
</script>

<style scoped lang="scss">
.top {
  margin-bottom: 13px;
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
  margin-bottom:12px;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 60px;
}
.bottom {
  padding:0 16px 16px 16px;
  background: #fff;
}
</style>