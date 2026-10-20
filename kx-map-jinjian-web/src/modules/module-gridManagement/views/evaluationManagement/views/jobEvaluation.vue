<template>
  <div>
    <PageContent>
      <div style="font-size: 18px">2024</div>
      <div style="font-size: 18px">汇总统计</div>
      <div class="statistic-box">
        <div
          class="statistic-box-list"
          v-for="(list, idx) in statisticData"
          :key="idx"
          :style="{ flex: idx == 0 ? 1 : 2 }"
        >
          <div
            class="statistic-box-list-item"
            v-for="item in list"
            :key="item.label + item.value"
          >
            <div class="value">{{ item.value }}</div>
            <div class="label">{{ item.label }}</div>
          </div>
        </div>
      </div>
      <el-divider></el-divider>
      <div style="font-size: 18px; margin-bottom: 24px">明细清单</div>
      <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="4">
        <div v-show="searchColumns.length > 0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
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
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
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
    </PageContent>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import { mapGetters, mapMutations } from "vuex";
export default {
  mixins: [pageMixin],
  dicts: ["self_evaluate_status", "final_evaluate_status"],
  data() {
    return {
      statisticData: [
        [
          {
            label: "总机构数",
            value: "89",
          },
        ],
        [
          {
            label: "已上报机构数",
            value: "65",
          },
          {
            label: "未上报机构数",
            value: "24",
          },
        ],
        // [
        //   {
        //     label: "已终评网点数",
        //     value: "89",
        //   },
        //   {
        //     label: "未终评网点数",
        //     value: "24",
        //   },
        // ],
      ],
      searchColumns: [
        {
          label: "上报机构",
          key: "name",
          type: "text",
        },
        {
          label: "上报状态",
          key: "status",
          type: "select",
          options: () => [
            {label:'未上报',value:'0'},
            {label:'已上报',value:'1'},
          ],
        },
        // {
        //   label: "终评状态",
        //   key: "selfEvaluation",
        //   type: "select",
        //   options: () => this.dict.type.final_evaluate_status,
        // },
      ],
      maxHeight: window.innerHeight,
      tableBtn: [
        {
          cname: "上报详情",
          ename: "selfEvaluateDetail",
          isShow: (data) => data.status == "1",
        },
        {
          cname: "退回",
          ename: "returnHandle",
          isShow: (data) => data.status == "1",
        },
        {
          cname: "催办",
          ename: "fastHandle",
          isShow: (data) => data.status == "0",
        },
      ],
      columns: [
        { label: "上报机构", prop: "executeDot", showOverFlowTooltip: true },
        {
          label: "上报状态",
          prop: "selfEvaluateStatus",
          showOverFlowTooltip: true,
          formatter: ({ status }) => {
            return ['未上报', '已上报'][status];
          },
        },
        {
          label: "上报时间",
          prop: "finalEvaluateTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "上报人",
          prop: "finalEvaluateName",
          showOverFlowTooltip: true,
        },
        { label: "操作", slot: true, width: 150 },
      ],
      tableData: [
        {
          executeDot: "农业银行金华江北支行",
          selfEvaluateName: "张明",
          selfEvaluateStatus: "2",
          selfEvaluateResult: "86",
          selfEvaluateTime: "2024-08-19 14:00",
          finalEvaluateName: "张明",
          finalEvaluateStatus: "2",
          finalEvaluateResult: "86",
          finalEvaluateTime: "2024-08-19 14:00",
          status:'1',
        },
        {
          executeDot: "上海分行",
          selfEvaluateName: "—",
          selfEvaluateStatus: "1",
          selfEvaluateResult: "—",
          selfEvaluateTime: "—",
          finalEvaluateName: "—",
          finalEvaluateStatus: "1",
          finalEvaluateResult: "—",
          finalEvaluateTime: "—",
          status:'0',
        },
      ],
    };
  },
  methods: {
    ...mapMutations("accountBook", ["updateReportInfo"]),
    selfEvaluateDetail() {
      this.updateReportInfo({
        status:'view'
      })
      this.$router.push({ name: "SelfEvaluate" });
    },
    returnHandle() {
      this.$modal.msgSuccess("退回成功");
      this.$router.go(-1)
    },
    fastHandle() {
      this.$modal.msgSuccess("催办成功");
      this.$router.go(-1)
    },
  },
};
</script>

<style scoped lang="scss">
.statistic-box {
  display: flex;
  justify-content: space-between;
  align-items: center;
  &-list {
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    position: relative;
    &-item {
      border: 2.5px solid var(--current-color);
      border-radius: 10px;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      align-items: center;
      flex: 1;
      margin: 10px;
      .value {
        margin: 5px 0;
        font-size: 18px;
      }
      .label {
        margin-bottom: 5px;
      }
    }
    &:nth-child(1)::after {
      position: absolute;
      top: 10%;
      left: 100%;
      transform: translateX(-50%);
      content: "";
      background-color: var(--current-color);
      width: 1px;
      height: 80%;
      border-radius: 50%;
    }
  }
}
</style>