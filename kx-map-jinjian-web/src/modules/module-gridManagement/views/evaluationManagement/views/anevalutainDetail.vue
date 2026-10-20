<template>
  <div>
    <PageContent>
      <div style="font-size: 18px">
        <span style="margin-right: 20px; font-weight: 900">{{
          bankInfo.name
        }}</span>
        <span>综合评价得分：100</span>
      </div>
      <div class="statistic-box">
        <div>
          <span class="label">评价年度:</span>
          <span class="value">{{ bankInfo.evaluationYear }}年度</span>
        </div>
        <div>
          <span class="label">评价时间:</span>
          <span class="value">2024.02.01 —— 2024.02.31</span>
        </div>
      </div>
      <el-divider></el-divider>
      <div style="font-size: 18px; margin-bottom: 24px">评价得分：100</div>
      <!-- 列表 -->
      <i-table
        class="table"
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        :spanMethod="spanMethod"
        :stripe="false"
        :border="true"
        :showPagination="false"
      >
        <template #score="{ row }">
          <span v-if="bankInfo.status == 'view'">{{ row.score }}</span>
          <el-input v-model="row.score" v-else placeholder="请输入"></el-input>
        </template>
      </i-table>
      <div style="margin-top: 20px; text-align: center">
        <el-button @click="goBack">取消</el-button>
        <el-button
          type="primary"
          style="margin-left: 20px"
          @click="submitHandle"
          v-if="bankInfo.status == 'edit'"
          >确认</el-button
        >
      </div>
    </PageContent>
  </div>
</template>
  
<script>
import pageMixin from "@/mixins/pageMixin";
import { mapGetters, mapMutations } from "vuex";
import { param } from "../../../../../utils";
export default {
  mixins: [pageMixin],
  dicts: ["self_evaluate_status", "final_evaluate_status"],
  data() {
    return {
      maxHeight: window.innerHeight,
      columns: [
        { label: "评价内容", prop: "content" },
        {
          label: "评价项目",
          prop: "project",
          width: "160",
        },
        {
          label: "评价标准",
          prop: "standards",
        },
        {
          label: "得分标准",
          prop: "scoreStandards",
        },
        {
          label: "得分",
          prop: "score",
          slot: true,
        },
      ],
      tableData: [
        {
          content: "整治拒收现金宣传情况（25分）",
          project: "宣传触达率",
          standards: "宣传触达率=知晓宣传信息的商户/抽查的商户总数。",
          scoreStandards:
            "96%至100%（含）的，得25分；92%至96%（含）的，得24分；88%至92%（含）的，得23分。以此类推。",
          score: "25",
          status: "1",
        },
        {
          content: "收取现金和找零情况（25分）",
          project: "零钞备付率",
          standards: "零钞备付率=备付零钞的商户数量/抽查的商户总数。",
          scoreStandards:
            "96%至100%（含）的，得25分；92%至96%（含）的，得24分；88%至92%（含）的，得23分。以此类推。",
          score: "25",
          status: "0",
        },
        {
          content: "网格化管理覆盖情况（25分）",
          project: "网格化管理覆盖率",
          standards: "网格化管理覆盖率=责任网点到访的商户数量/抽查的商户总数。",
          scoreStandards:
            "96%至100%（含）的，得25分；92%至96%（含）的，得24分；88%至92%（含）的，得23分。以此类推。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "“零钱包”兑换排队时间超过10分钟。",
          scoreStandards: "扣3分。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards:
            "现场不能兑换较小数量零钞（各分支机构确定具体数量），且不主动说明预约方式的（电话预约、现场预约、APP/小程序预约）。",
          scoreStandards: "扣3分。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "对外支付现金中残缺污损人民币。",
          scoreStandards:
            "对外支付现金中残缺污损人民币占1%-5%（含）扣3分；5%以上扣6分。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "客户未预约无法进行小面额人民币取现、兑换。",
          scoreStandards: "扣3分。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "外拓零钞服务需要网点员工垫款。",
          scoreStandards: "扣3分。",
          score: "25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "未为老年客户提供便利化现金服务。",
          scoreStandards: "扣3分。",
          score: "25",
          status: "0",
        },
      ],
      mergeMap: {},
    };
  },
  computed: {
    ...mapGetters("accountBook", ["bankInfo", "bankEvaluationList"]),
  },
  created() {
    this.generateMergeMap();
  },
  methods: {
    ...mapMutations("accountBook", ["updatebankEvaluationList"]),
    addContent() {},
    generateMergeMap() {
      const map = {};
      let start = 0;

      while (start < this.tableData.length) {
        const currentProject = this.tableData[start].project;
        let end = start;

        // 找到相同 project 的结束行
        while (
          end < this.tableData.length &&
          this.tableData[end].project === currentProject
        ) {
          end++;
        }

        // 记录合并信息
        map[start] = end - start; // 从 start 行开始，合并 end - start 行
        start = end;
      }

      this.mergeMap = map;
    },
    selfEvaluateDetail() {
      this.$router.push({ name: "SelfEvaluate" });
    },
    superiorEvaluateDetail() {
      this.$router.push({ name: "SuperiorEvaluate" });
    },
    spanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 1 || columnIndex === 4) {
        // 只合并第一列
        for (const start in this.mergeMap) {
          if (rowIndex === Number(start)) {
            return {
              rowspan: this.mergeMap[start], // 合并的行数
              colspan: 1,
            };
          } else if (
            rowIndex > Number(start) &&
            rowIndex < Number(start) + this.mergeMap[start]
          ) {
            return {
              rowspan: 0, // 被合并的行，不显示
              colspan: 0,
            };
          }
        }
      }

      // 默认不合并
      return { rowspan: 1, colspan: 1 };
    },
    goBack() {
      this.$router.go(-1);
    },
    submitHandle() {
      this.$modal.msgSuccess("提交成功");
      this.$router.go(-1);
    },
  },
};
</script>
  
  <style scoped lang="scss">
.top {
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  font-size: 20px;
}
.statistic-box {
  display: flex;
  align-items: center;
  margin-top: 20px;
  padding: 0 20px;
  font-size: 16px;
  div:nth-of-type(1) {
    margin-right: 40px;
  }
  .label {
    margin-right: 10px;
  }
}
.table ::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
  border: 0.5px solid #ebeef5;
}
// .table ::v-deep.el-table td.el-table__cell,
// .el-table th.el-table__cell.is-leaf {
//   border: 0.5px solid #ebeef5;
// }
</style>