<template>
  <PageContent>
    <div class="top">
      <h2>现金服务宣传统计（人民银行金华市分行-2025年）</h2>
      <div class="top-box">
        <div
          class="top-item"
          v-for="item in reservationList"
          :key="item.label"
          :style="{
            background: `linear-gradient(to bottom,${item.topColor},${item.bottomColor})`,
          }"
        >
          <div class="icon">
            <i :class="`iconfont ${item.icon}`"></i>
          </div>
          <div class="text">
            <div class="label">{{ item.label }}</div>
            <section>
              <span class="value">{{ item.value }}</span>
              <span class="unit">{{ item.unit }}</span>
            </section>
          </div>
        </div>
      </div>
    </div>
    <div class="bottom">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      >
        <template #action="scope">
          <!-- <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns> -->
        </template>
      </i-table>
    </div>
  </PageContent>
</template>
  
  <script>
import pieChart from "@/components/MerchantDetail/components/pieChart.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  components: {
    pieChart,
  },
  data() {
    return {
      reservationList: [
        {
          label: "服务宣传次数",
          value: "2567",
          unit: "次",
          icon: "icon-meitixuanchuan",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "服务宣传受众人数",
          value: "61347",
          unit: "人",
          icon: "icon-renqun",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "不宜流通人民币兑换与回收服务次数",
          value: "35722",
          unit: "次",
          icon: "icon-tongjifenxi-changguitongji",
          topColor: "#63C4F7",
          bottomColor: "#54ABEB",
        },
        {
          label: "人民币知识宣传次数",
          value: "61347",
          unit: "次",
          icon: "icon-xitongcanshuguanli",
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
        },
        {
          label: "服务宣传次数",
          prop: "servePointAddr",
          showOverFlowTooltip: true,
        },
        {
          label: "服务宣传覆盖网格数",
          prop: "dutyName",
          showOverFlowTooltip: true,
        },
        { label: "服务宣传受众人数", prop: "dutyPhone" },
        {
          label: "不宜流通人民币兑换与回收服务次数",
          prop: "gridName",
          width: 200,
        },
        { label: "人民币知识宣传次数", prop: "gridName" },
        { label: "优秀服务宣传案例数", prop: "gridName" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "查看详情",
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
      maxHeight: "500px",
    };
  },
  computed: {},
  methods: {
    viewHandle() {
      this.$router.push({
        name: "CityData",
      });
    },
  },
};
</script>
  
  <style scoped lang="scss">
.top {
  border-bottom: 1px solid #ccc;
  margin-bottom: 20px;
  h2 {
    font-size: 18px;
  }
  &-box {
    display: grid;
    grid-template: 1fr / repeat(4, 1fr);
    gap: 20px;
    padding: 20px 0;
    .top-item {
      padding: 20px;
      border-radius: 10px;
      font-size: 16px;
      color: #fff;
      display: flex;
      align-items: center;
      .icon {
        margin-right: 20px;
        i {
          font-size: 60px;
        }
      }
      .label {
        line-height: 22px;
      }
      section {
        margin-top: 5px;
        .value {
          font-size: 32px;
          font-weight: 700;
          color: #2269f7;
        }
        .unit {
          margin-left: 5px;
        }
      }
    }
  }
}
.bottom {
}
.zhibiaoRight::v-deep .el-progress-circle {
  width: 150px !important;
  height: 150px !important;
}
.zhibiaoRight ::v-deep .el-progress__text {
  top: 40%;
  font-weight: 700;
  font-size: 24px !important;
}
</style>