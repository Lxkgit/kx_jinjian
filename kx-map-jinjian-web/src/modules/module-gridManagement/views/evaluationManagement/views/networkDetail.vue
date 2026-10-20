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
      <div style="font-size: 18px; margin-bottom: 24px">现场评价得分：100</div>
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
      >
        <template #score="{ row }">
          <el-input v-model="row.score" placeholder="请输入"></el-input>
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
          label: "评价标准",
          prop: "fullMarks",
        },
        {
          label: "评价项目",
          prop: "project",
          width: "160",
        },

        {
          label: "扣分标准",
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
          content: "网格划分情况",
          fullMarks: "25",
          project: "是否以一社区（行政村）一网格划分网格",
          scoreStandards:
            "与网格化管理平台地图中社区、行政村数进行比对，未按要求进行网格划分的扣5分",
          score: "25",
        },
        {
          content: "网格化管理覆盖情况",
          fullMarks: "25",
          project: "是否以银行机构全覆盖、任务与体量相适配的原则分配网格",
          scoreStandards: "未做到当地银行机构全覆盖的扣5分",
          score: "25",
        },
        {
          content: "网格责任网点信息更新情况",
          fullMarks: "25",
          project: "网格信息是否及时更新",
          scoreStandards:
            "1.银行网点因迁移、变更、撤并等不适合继续承担网格责任网点职责，却仍承担网格职责的，1个网点扣5分，直至该项分数扣完；2.网格长、网格员的姓名、联系方式等信息为及时更新，1人扣0.5分，直至该项分数扣完。",
          score: "25",
        },
        {
          content: "工作报告情况",
          fullMarks: "25",
          project: "银行机构是否每半年向当地人民银行报告网格化工作开展情况",
          scoreStandards: "1家银行机构未报送工作报告扣2分，直至该项分数扣完。",
          score: "25",
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
      if (columnIndex === 0 || columnIndex === 1) {
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