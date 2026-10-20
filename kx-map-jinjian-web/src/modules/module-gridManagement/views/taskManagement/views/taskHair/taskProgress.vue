<template>
  <div>
    <PageContent>
      <div style="font-size: 18px">2024.01 商户摸排任务</div>
      <div style="font-size: 18px; margin-top: 10px">汇总统计</div>

      <el-radio-group v-if="$route.query.type =='1'" v-model="active" style="margin: 10px 0">
        <el-radio-button
          v-for="item in tabList"
          :label="item.value"
          :key="item.value"
          >{{ item.label }}</el-radio-button
        >
      </el-radio-group>
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
      <reportDetail v-model="showDialog"></reportDetail>
    </PageContent>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import reportDetail from "./components/reportDetail.vue";
export default {
  mixins: [pageMixin],
  dicts: ["self_evaluate_status", "final_evaluate_status"],
  components: { reportDetail },
  data() {
    return {
      statisticData: [
        [
          {
            label: "总执行单位数",
            value: "89",
          },
        ],
        [
          {
            label: "已完成单位数",
            value: "89",
          },
          {
            label: "未完成单位数",
            value: "24",
          },
          {
            label: "任务完成率",
            value: "70",
          },
        ],
      ],
      searchColumns: [
        {
          label: "执行单位",
          key: "name",
          type: "text",
        },
        {
          label: "完成状态",
          key: "status",
          type: "select",
          options: () => [
            { label: "未完成", value: "0" },
            { label: "已完成", value: "1" },
          ],
        },
      ],
      maxHeight: window.innerHeight,
      tableBtn: [
        {
          cname: "上报详情",
          ename: "selfEvaluateDetail",
          isShow: (data) => data.status == "1",
        },
      ],
      columns: [
        { label: "执行单位", prop: "executeDot", showOverFlowTooltip: true },
        {
          label: "完成人员",
          prop: "finalEvaluateName",
          showOverFlowTooltip: true,
        },
        {
          label: "完成状态",
          prop: "selfEvaluateStatus",
          showOverFlowTooltip: true,
          formatter: ({ status }) => {
            return ["未完成", "已完成"][status];
          },
        },
        {
          label: "完成时间",
          prop: "finalEvaluateTime",
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
          status: "1",
        },
        {
          executeDot: "上海分行",
          selfEvaluateName: "—",
          selfEvaluateStatus: "1",
          selfEvaluateResult: "—",
          selfEvaluateTime: "—",
          finalEvaluateName: "李花",
          finalEvaluateStatus: "1",
          finalEvaluateResult: "—",
          finalEvaluateTime: "2024-08-19 14:00",
          status: "0",
        },
      ],
      showDialog: false,
      tabList: [
        { label: "2024年3月", value: "1" },
        { label: "2024年2月", value: "2" },
        { label: "2024年1月", value: "3" },
      ],
      active: "1",
    };
  },
  watch: {
    active: {
      handler(val) {
        if (val == 1) {
          this.statisticData = [
            [
              {
                label: "总执行单位数",
                value: "89",
              },
            ],
            [
              {
                label: "已完成单位数",
                value: "89",
              },
              {
                label: "未完成单位数",
                value: "24",
              },
              {
                label: "任务完成率",
                value: "70",
              },
            ],
          ];
        } else if(val == 2){
          this.statisticData = [
            [
              {
                label: "总执行单位数",
                value: "120",
              },
            ],
            [
              {
                label: "已完成单位数",
                value: "102",
              },
              {
                label: "未完成单位数",
                value: "18",
              },
              {
                label: "任务完成率",
                value: "80",
              },
            ],
          ];
        }else if(val == 3){
          this.statisticData = [
            [
              {
                label: "总执行单位数",
                value: "90",
              },
            ],
            [
              {
                label: "已完成单位数",
                value: "75",
              },
              {
                label: "未完成单位数",
                value: "15",
              },
              {
                label: "任务完成率",
                value: "80",
              },
            ],
          ];
        }
      },
    },
  },
  methods: {
    selfEvaluateDetail() {
      this.showDialog = true;
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