<template>
  <PageContent>
    <div class="top">
      <!-- 机构标题 -->
      <!-- <orgTitleSelect title="人民银行浙江省分行-2025年">
        <template #right>
          <el-button type="primary" @click="showDetail = true"
            >条件切换</el-button
          >
        </template>
      </orgTitleSelect> -->
      <!-- 数据统计 -->
      <div class="top-data">
        <tableTitle title="数据统计" class="top-title"></tableTitle>
        <statisticsItem
          height="92"
          :gridColumns="5"
          :imgList="imgList"
          :dataList="dataList"
          gridRows="1fr"
        ></statisticsItem>
        <!-- <pieChartList style="margin-top: 12px" gridColumns="6"></pieChartList> -->
      </div>
    </div>

    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        :columns="searchColumns"
        :gridColumns="3"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <div class="bottom">
      <div class="export">
        <el-button type="primary" @click="exportEvent">导出报表</el-button>
      </div>

      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="10"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :default-sort="{ prop: 'servePointAddr', order: 'descending' }"
        :pageSize="formInline.pageSize"
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
    <filterDetail
      v-model="showDetail"
      @handleConfirm="handleConfirm"
    ></filterDetail>
  </PageContent>
</template>

  <script>
import pageMixin from "@/mixins/pageMixin";
import pieChartList from "@/modules/module-gridManagement/components/pieChartList";
import filterDetail from "../../merStatisticsManagement/components/filterDetail.vue";
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
import orgTitleSelect from "@/modules/module-gridManagement/components/orgTitleSelect.vue";
import { options } from "runjs";
const iconList = [
  {
    name: "排查次数",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch1.png"),
    key: "key1",
  },
  {
    name: "排查商户数",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch2.png"),
    key: "key2",
  },
  {
    name: "排查小微企业数",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch3.png"),
    key: "key3",
  },
  {
    name: "排查办公室数",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch4.png"),
    key: "key4",
  },
  {
    name: "参与人次",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch5.png"),
    key: "key5",
  },
  {
    name: "排查人次",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch3.png"),
    key: "key6",
  },
];
const dataInfo = {
  key1: "3728",
  key2: "1732",
  key3: "872",
  key4: "1124",
  key5: "1421",
  key6: "12328",
};
export default {
  mixins: [pageMixin],
  components: {
    filterDetail,
    pieChartList,
    statisticsItem,
    orgTitleSelect,
  },
  data() {
    return {
      imgList: [],
      dataList: {},
      bottomList: [
        {
          label: "网点零钱包兑换数量",
          value: "61347",
          unit: "",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "网点零钱包兑换金额",
          value: "35722",
          unit: "",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
      ],
      searchColumns: [
        {
          label: "数据维度",
          key: "servePointName",
          type: "select",
          options: [
            {
              label: "月度",
              value: "1",
            },
            {
              label: "季度",
              value: "2",
            },
          ],
        },
        {
          label: "统计时间",
          key: "clueSubmitTime",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
        },
        {
          label: "生成时间",
          key: "servePointName",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
        },
      ],
      columns: [
        {
          label: "数据维度",
          prop: "dataDimension",
          showOverFlowTooltip: true,
          width: 120,
        },
        {
          label: "统计时间范围",
          prop: "statTimeRange",
          showOverFlowTooltip: true,
          width: 200,
        },
        {
          label: "排查次数",
          prop: "checkCount",
          showOverFlowTooltip: true,
          width: 150,
          sortable: true,
        },
        {
          label: "商户数量",
          prop: "merchantCount",
          showOverFlowTooltip: true,
          width: 150,
          sortable: true,
        },
        {
          label: "小微企业数量",
          prop: "microEnterpriseCount",
          showOverFlowTooltip: true,
          width: 200,
          sortable: true,
        },
        {
          label: "办公室数量",
          prop: "officeCount",
          showOverFlowTooltip: true,
          width: 150,
          sortable: true,
        },
        {
          label: "参与人次",
          prop: "participantCount",
          showOverFlowTooltip: true,
          width: 150,
          sortable: true,
        },
        {
          label: "排查人次",
          prop: "checkPersonCount",
          showOverFlowTooltip: true,
          width: 150,
          sortable: true,
        },
        {
          label: "数据生成时间",
          prop: "dataCreateTime",
          showOverFlowTooltip: true,
        },
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
          dataDimension: "月度",
          statTimeRange: "2026-01",
          checkCount: 128,
          merchantCount: 856,
          microEnterpriseCount: 428,
          officeCount: 156,
          participantCount: 245,
          checkPersonCount: 312,
          dataCreateTime: "2026-02-01 10:15:30",
        },
        {
          dataDimension: "月度",
          statTimeRange: "2026-02",
          checkCount: 156,
          merchantCount: 924,
          microEnterpriseCount: 485,
          officeCount: 182,
          participantCount: 286,
          checkPersonCount: 368,
          dataCreateTime: "2026-03-01 09:28:15",
        },
        {
          dataDimension: "月度",
          statTimeRange: "2026-03",
          checkCount: 189,
          merchantCount: 1058,
          microEnterpriseCount: 526,
          officeCount: 205,
          participantCount: 312,
          checkPersonCount: 405,
          dataCreateTime: "2026-04-01 11:05:42",
        },
        {
          dataDimension: "季度",
          statTimeRange: "2026-Q1",
          checkCount: 473,
          merchantCount: 2838,
          microEnterpriseCount: 1439,
          officeCount: 543,
          participantCount: 843,
          checkPersonCount: 1085,
          dataCreateTime: "2026-04-10 14:30:28",
        },
        {
          dataDimension: "月度",
          statTimeRange: "2025-10",
          checkCount: 98,
          merchantCount: 756,
          microEnterpriseCount: 385,
          officeCount: 142,
          participantCount: 215,
          checkPersonCount: 278,
          dataCreateTime: "2025-11-01 10:12:18",
        },
        {
          dataDimension: "月度",
          statTimeRange: "2025-11",
          checkCount: 115,
          merchantCount: 812,
          microEnterpriseCount: 402,
          officeCount: 158,
          participantCount: 236,
          checkPersonCount: 305,
          dataCreateTime: "2025-12-01 09:45:36",
        },
        {
          dataDimension: "月度",
          statTimeRange: "2025-12",
          checkCount: 132,
          merchantCount: 885,
          microEnterpriseCount: 435,
          officeCount: 168,
          participantCount: 258,
          checkPersonCount: 328,
          dataCreateTime: "2026-01-01 10:20:45",
        },
        {
          dataDimension: "季度",
          statTimeRange: "2025-Q4",
          checkCount: 345,
          merchantCount: 2453,
          microEnterpriseCount: 1222,
          officeCount: 468,
          participantCount: 709,
          checkPersonCount: 911,
          dataCreateTime: "2026-01-10 15:18:22",
        },
        {
          dataDimension: "半年度",
          statTimeRange: "2025-H2",
          checkCount: 689,
          merchantCount: 4895,
          microEnterpriseCount: 2450,
          officeCount: 926,
          participantCount: 1418,
          checkPersonCount: 1822,
          dataCreateTime: "2026-01-15 11:30:15",
        },
        {
          dataDimension: "年度",
          statTimeRange: "2025",
          checkCount: 1286,
          merchantCount: 9258,
          microEnterpriseCount: 4685,
          officeCount: 1785,
          participantCount: 2685,
          checkPersonCount: 3458,
          dataCreateTime: "2026-01-20 16:45:30",
        },
      ],
      maxHeight: 600,
      showDetail: false,
    };
  },
  computed: {},
  mounted() {
    this.imgList = iconList;
    this.dataList = dataInfo;
  },
  methods: {
    exportEvent() {
      this.$message.success("导出成功！");
    },
    viewHandle() {
      this.$router.push({
        name: "CityData",
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
    content: "";
    position: absolute;
    bottom: -8px;
    left: -16px;
    width: calc(100% + 32px);
    height: 1px;
    background: #e7eaf6;
  }
}
.filterBar-box {
  margin-bottom: 12px;
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
  padding: 0 16px;
  background: #fff;
}
.center {
  display: flex;
  align-items: center;
  .center-item {
    flex: 1;
    margin: 5px 15px;
    padding: 15px 15px;
    border-radius: 10px;
    font-size: 16px;
    color: #fff;
    section {
      margin-top: 10px;
      .value {
        font-size: 36px;
        font-weight: 800;
      }
      .unit {
        margin-left: 5px;
      }
    }
  }
}
.export {
  padding: 10px 0;
}
</style>
