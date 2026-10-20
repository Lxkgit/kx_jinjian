<template>
  <div>
    <PageContent>
      <div
        class="top"
      >
        <h2>基础设置</h2>
        <div style="width: 1100px; margin: 10px 0 0 20px">
          <i-form
            ref="filterBar"
            :columns="baseColumns"
            label-position="right"
            label-width="150px"
            :gridColumns="2"
          >
          </i-form>
        </div>
      </div>
      <!-- 按钮 -->
      <!-- <div
        style="margin-bottom: 10px"
      >
        <el-button type="primary" @click="addContent"
          >新增现场评价内容</el-button
        >
      </div> -->
      <!-- 列表 -->
      <i-table
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
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
      <div
        style="margin-top: 20px; text-align: center"
      >
        <el-button>取消</el-button>
        <el-button
          type="primary"
          style="margin-left: 20px"
          @click="submitHandle"
          >确认</el-button
        >
      </div>
      <addAnevalutionDetail v-model="showDialog"></addAnevalutionDetail>
    </PageContent>
  </div>
</template>
  
<script>
import pageMixin from "@/mixins/pageMixin";
import { mapGetters, mapMutations } from "vuex";
import addAnevalutionDetail from "../components/addAnevalutionDetail.vue";
export default {
  mixins: [pageMixin],
  dicts: ["self_evaluate_status", "final_evaluate_status"],
  components: {
    addAnevalutionDetail
  },
  data() {
    return {
      baseColumns: [
        {
          label: "名称",
          key: "name",
          type: "text",
          labelWidth: "80px",
          rules: [{ required: true, trigger: "blur", message: "名称不能为空" }],
        },
        {
          label: "评价年度",
          key: "evaluationYear",
          type: "select",
          labelWidth: "80px",
          options: [
            { label: "2025年", value: "2025" },
            { label: "2024年", value: "2024" },
            { label: "2023年", value: "2023" },
            { label: "2022年", value: "2022" },
          ],
          rules: [
            { required: true, trigger: "change", message: "评价年度不能为空" },
          ],
        },
        {
          label: "备注",
          key: "remark",
          type: "textarea",
          labelWidth: "80px",
          value: [],
          span: 24,
          rules: [{ required: true, trigger: "blur", message: "备注不能为空" }],
        },
      ],
      btnLists: [
        {
          cname: "新增巡查台账",
          ename: "addBankCashRecord",
          type: "primary",
        },
      ],
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
            value: "89",
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
            { label: "未上报", value: "0" },
            { label: "已上报", value: "1" },
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
          ename: "selfEvaluateDetail",
          isShow: (data) => data.status == "1",
        },
        {
          cname: "催办",
          ename: "selfEvaluateDetail",
          isShow: (data) => data.status == "0",
        },
      ],
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
        // {
        //   label: "得分",
        //   prop: "score",
        // },
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
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards:
            "现场不能兑换较小数量零钞（各分支机构确定具体数量），且不主动说明预约方式的（电话预约、现场预约、APP/小程序预约）。",
          scoreStandards: "扣3分。",
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "对外支付现金中残缺污损人民币。",
          scoreStandards:
            "对外支付现金中残缺污损人民币占1%-5%（含）扣3分；5%以上扣6分。",
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "客户未预约无法进行小面额人民币取现、兑换。",
          scoreStandards: "扣3分。",
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "外拓零钞服务需要网点员工垫款。",
          scoreStandards: "扣3分。",
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
        {
          content: "金融机构零钞服务质量（25分）",
          project: "网点零钞现金服务质量",
          standards: "未为老年客户提供便利化现金服务。",
          scoreStandards: "扣3分。",
          score:
            "该项得分=25-抽查网点“零钞现金服务质量”扣分的算数平均数-“金融机构‘零钱包’网点覆盖情况”扣分得分：25",
          status: "0",
        },
      ],
      mergeMap: {},
      showDialog:false
    };
  },
  computed: {
    ...mapGetters("accountBook", ["bankEvaluationList","addBankInfo"]),
  },
  created() {
    this.generateMergeMap();
  },
  mounted() {
    if (this.addBankInfo.status == "edit") {
      this.$nextTick(() => {
        this.$refs.filterBar.setForm(this.addBankInfo);
      })
    }
  },
  methods: {
    ...mapMutations("accountBook", ["updatebankEvaluationList"]),
    addContent() {
      this.showDialog = true;
    },
    submitHandle() {
      this.$refs.filterBar.getForm((formInline) => {
        if (this.addBankInfo.status == "edit") {
          var _ = this
          const updatedList = [...this.bankEvaluationList];
          const index = updatedList.findIndex(item => item.id == this.addBankInfo.id);
          if (index !== -1) {
            updatedList[index] = {
              ...formInline,
              id: this.addBankInfo.id,
              progress: this.addBankInfo.progress,
              creatTime:this.addBankInfo.creatTime
            }; 
            console.log(this,'this')
            this.updatebankEvaluationList([...updatedList]); 
          }
        } else {
          const param = {
            ...formInline,
            id: new Date().getTime(),
            progress: "0",
            creatTime: this.parseTime(new Date(), "{y}-{m}-{d}"),
          };
          this.updatebankEvaluationList([
            { ...param },
            ...this.bankEvaluationList,
          ]);
        }
        this.$modal.msgSuccess("操作成功");
        this.$router.go(-1);
      });
    },
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
</style>