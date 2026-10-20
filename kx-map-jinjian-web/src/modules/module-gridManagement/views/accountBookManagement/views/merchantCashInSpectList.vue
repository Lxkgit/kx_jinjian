<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
        labelWidth="90px"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
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
        :maxHeight="maxHeight"
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
      <add-merchant-inspect-dialog
        v-model="showDetail"
        :defaultValue="defaultValue"
        :status="status"
        @fetchList="fetchList"
      ></add-merchant-inspect-dialog>
    </PageContent>
  </div>
</template>
    
<script>
import { mapState, mapGetters, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addMerchantInspectDialog from "../components/addMerchantInspectDialog.vue";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  components: { addMerchantInspectDialog },
  dicts: ["merchant_true_false", "merchant_type"],
  data() {
    return {
      searchColumns: [
        {
          label: "商户名称",
          key: "merchantName",
          type: "text",
        },
        {
          label: "巡查时间",
          key: "spectTime",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "新增巡查",
          ename: "addSpect",
          type: "primary",
        },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "view",
        },
        {
          cname: "编辑",
          ename: "edit",
        },
        {
          cname: "删除",
          ename: "delete",
        },
      ],
      columns: [
        {
          label: "商户名称",
          prop: "label",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "商户地址",
          prop: "merchantAddress",
          showOverFlowTooltip: true,
          width: 240,
        },
        {
          label: "商户类型",
          prop: "merchantType",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ merchantType }) => {
            return this.dict.label.merchant_type[merchantType];
          },
        },
        {
          label: "商户联系人",
          prop: "merchantLinkMan",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "商户是否具备人民币现金收付条件",
          prop: "hadCashGetPay",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ hadCashGetPay }) => {
            return this.dict.label.merchant_true_false[hadCashGetPay];
          },
        },
        {
          label: "商户零钞备付是否充足（超过300元）",
          prop: "hadEnoughCash",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ hadEnoughCash }) => {
            return this.dict.label.merchant_true_false[hadEnoughCash];
          },
        },
        {
          label: "商户是否了解拒收人民币现金是违法行为",
          prop: "knowRefuseIllegal",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ knowRefuseIllegal }) => {
            return this.dict.label.merchant_true_false[knowRefuseIllegal];
          },
        },
        {
          label: "商户是否了解兑换小面额人民币现金方式",
          prop: "knowSmallCashChangeType",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ knowSmallCashChangeType }) => {
            return this.dict.label.merchant_true_false[knowSmallCashChangeType];
          },
        },
        {
          label: "商户是否曾发生无法找零情况",
          prop: "hadNoLooseChange",
          showOverFlowTooltip: true,
          width: 120,
          formatter: ({ hadNoLooseChange }) => {
            return this.dict.label.merchant_true_false[hadNoLooseChange];
          },
        },
        {
          label:
            "是否有银行上门提供金融服务（现金知识宣传、介绍和兑换零钱包等举措）",
          prop: "hadBankVisit",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ hadBankVisit }) => {
            return this.dict.label.merchant_true_false[hadBankVisit];
          },
        },
        {
          label: "巡查时间",
          prop: "inspectTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "巡查人",
          prop: "inspector",
          showOverFlowTooltip: true,
          width: 120,
        },
        {
          label: "巡查机构",
          prop: "inspectDept",
          showOverFlowTooltip: true,
          width: 100,
        },
        { label: "操作", slot: true, width: 120 },
      ],
      tableData: [],
      showDetail: false,
      status: "add",
      defaultValue: {},
    };
  },
  computed: {
    ...mapGetters("accountBook", [
      "merchantInspectList",
      "nowMerchantCashGridInfo",
    ]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateMerchantInspectList"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        let arr = this.merchantInspectList;
        this.tableData = arr.filter(
          (item) => item.gridId == this.nowMerchantCashGridInfo.gridId
        );
      });
    },
    export() {},
    addSpect() {
      this.status = "add";
      this.showDetail = true;
    },
    delete(row) {
      let arr = [];
      arr = this.merchantInspectList.filter((item) => !(item.id == row.id));
      this.updateMerchantInspectList([...arr]);
      this.fetchList();
    },
    view(row) {
      this.status = "view";
      this.defaultValue = row;
      this.showDetail = true;
    },
    edit(row) {
      this.status = "edit";
      this.defaultValue = row;
      this.showDetail = true;
    },
  },
};
</script>
    
    <style lang="scss" scoped></style>