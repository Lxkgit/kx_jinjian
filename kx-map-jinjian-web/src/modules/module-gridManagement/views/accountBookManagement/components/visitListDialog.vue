<template>
  <i-dialog
    title="走访登记"
    v-model="showDialog"
    width="70%"
    :showFooter="false"
    :appendToBody="true"
    @handleClose="handleClose"
  >
    <div class="top">
      <div class="lastVisitTitle">最近一次走访</div>
      <el-button type="primary" @click="addVisit">新增走访</el-button>
    </div>
    <div class="last-visit-box">
      <div
        class="last-visit-box-item"
        v-for="item in columnArr"
        :key="item.key"
        :style="{ gridColumnStart: `span ${item.span || 1}` }"
      >
        <div class="label">{{ item.label }}:</div>
        <div class="value">{{ lastVisitInfo[item.key] }}</div>
      </div>
    </div>
    <div class="table">
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :maxHeight="350"
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        @selectionChange="selectionChange"
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
      <add-visit-dialog
        v-model="showVisitDialog"
        :defaultValue="visitDefaultValue"
        :status="status"
        :storeInfo="defaultValue"
        @fetchList="fetchList"
      ></add-visit-dialog>
    </div>
  </i-dialog>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import dialogMixin from "@/mixins/dialogMixin.vue";
import addVisitDialog from "./addVisitDialog.vue";
export default {
  name: "visitListDialog",
  mixins: [dialogMixin, pageMixin],
  dicts: ["merchant_true_false"],
  components: {
    addVisitDialog,
  },
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      keyArr: [
        "hadMoneyCondition",
        "enoughLooseChange",
        "isCashClean",
        "knowRefuseIllegal",
        "knowChangeCash",
        "hadCantChangeMoney",
        "giveToDoorChange",
        'isBusiness'
      ],
      columnArr: [
        { label: "走访时间", key: "visitTime", span: 1 },
        { label: "走访人", key: "visitPerson", span: 1 },
        {
          label: "经营主体是否具备人民币现金收付条件",
          key: "hadMoneyCondition",
          span: 1,
        },
        {
          label: "经营主体是否张贴“支持现金支付”标志",
          key: "isBusiness",
          span: 1,
        },
        {
          label: "经营主体零钞备付是否充足（超过300元）",
          key: "enoughLooseChange",
          span: 1,
        },
        {
          label: "经营主体备付人民币现金是否整洁",
          key: "isCashClean",
          span: 1,
        },
        {
          label: "经营主体是否了解拒收人民币现金是违法行为",
          key: "knowRefuseIllegal",
          span: 1,
        },
        {
          label: "经营主体是否了解兑换小面额人民币现金方式",
          key: "knowChangeCash",
          span: 1,
        },
        {
          label: "经营主体是否曾发生无法找零情况",
          key: "hadCantChangeMoney",
          span: 1,
        },
        {
          label: "经营主体是否需要小面额现金上门兑换服务",
          key: "giveToDoorChange",
          span: 2,
        },
        { label: "零钱包兑换个数", key: "conversionNum", span: 1 },
        { label: "零钱包兑换金额", key: "conversionMoney", span: 1 },
        { label: "备注", key: "remark", span: 2 },
      ],
      tableBtn: [
        {
          cname: "查看",
          ename: "viewVisitDetail",
        },
      ],
      columns: [
        { label: "走访时间", prop: "visitTime", showOverFlowTooltip: true },
        { label: "走访人", prop: "visitPerson", showOverFlowTooltip: true },
        {
          label: "经营主体是否具备人民币现金收付条件",
          prop: "hadMoneyCondition",
          showOverFlowTooltip: true,
          formatter: ({ hadMoneyCondition }) => {
            return this.dict.label.merchant_true_false[hadMoneyCondition];
          },
        },
        {
          label: "经营主体零钞备付是否充足（超过300元）",
          prop: "enoughLooseChange",
          showOverFlowTooltip: true,
          formatter: ({ enoughLooseChange }) => {
            return this.dict.label.merchant_true_false[enoughLooseChange];
          },
        },
        {
          label: "经营主体备付人民币现金是否整洁",
          prop: "isCashClean",
          showOverFlowTooltip: true,
          formatter: ({ isCashClean }) => {
            return this.dict.label.merchant_true_false[isCashClean];
          },
        },
        {
          label: "经营主体是否了解拒收人民币现金是违法行为",
          prop: "knowRefuseIllegal",
          showOverFlowTooltip: true,
          formatter: ({ knowRefuseIllegal }) => {
            return this.dict.label.merchant_true_false[knowRefuseIllegal];
          },
        },
        {
          label: "经营主体是否了解兑换小面额人民币现金方式",
          prop: "knowChangeCash",
          showOverFlowTooltip: true,
          formatter: ({ knowChangeCash }) => {
            return this.dict.label.merchant_true_false[knowChangeCash];
          },
        },
        {
          label: "经营主体是否曾发生无法找零情况",
          prop: "hadCantChangeMoney",
          showOverFlowTooltip: true,
          formatter: ({ hadCantChangeMoney }) => {
            return this.dict.label.merchant_true_false[hadCantChangeMoney];
          },
        },
        {
          label: "经营主体是否需要小面额现金上门兑换服务",
          prop: "giveToDoorChange",
          showOverFlowTooltip: true,
          formatter: ({ giveToDoorChange }) => {
            return this.dict.label.merchant_true_false[giveToDoorChange];
          },
        },
        {
          label: "零钱包兑换个数",
          prop: "conversionNum",
          showOverFlowTooltip: true,
        },
        {
          label: "零钱包兑换金额",
          prop: "conversionMoney",
          showOverFlowTooltip: true,
        },
        { label: "操作", slot: true },
      ],
      tableData: [],
      lastVisitInfo: {},
      showVisitDialog: false,
      status: "add",
      visitDefaultValue: {},
    };
  },
  computed: {
    ...mapState("accountBook", [
      "bankList",
      "gridPointList",
      "nowGridInfo",
      "storeList",
    ]),
  },
  methods: {
    initData() {
      this.fetchList();
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      const param = {
        pageSize,
        pageNum,
        ...this.defaultValue,
      };
      this.tableData = this.storeList.find(
        (item) => this.defaultValue.id == item.id
      ).visitList;
      this.total = this.tableData.length;
      if (this.tableData.length > 0) {
        let visit = { ...this.tableData[0] };
        this.keyArr.forEach((key) => {
          visit[`${key}`] =
            this.dict.label.merchant_true_false[visit[`${key}`]];
        });
        this.$nextTick(() => {
          this.lastVisitInfo = visit;
        });
      }
    },
    addVisit() {
      this.visitDefaultValue = {};
      this.status = "add";
      this.showVisitDialog = true;
    },
    viewVisitDetail(row) {
      this.visitDefaultValue = row;
      this.status = "view";
      this.showVisitDialog = true;
    },
    handleClose() {
      this.lastVisitInfo = {};
      this.$emit("fetchList");
      this.showDialog = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.last-visit-box {
  display: grid;
  gap: 10px;
  grid-template-columns: repeat(2, 1fr);
  margin: 10px 0;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
  &-item {
    display: flex;
    align-items: center;
    .label {
      font-weight: bold;
    }
    .value {
    }
  }
}
.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
  .lastVisitTitle {
    color: #409eff;
  }
}
.table {
  height: 400px;
}
</style>