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
      <add-bank-point-dialog
        v-model="showDetail"
        :defaultValue="defaultValue"
        :status="status"
        @fetchList="fetchList"
      ></add-bank-point-dialog>
    </PageContent>
  </div>
</template>
    
<script>
import { mapState, mapGetters, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addBankPointDialog from "../components/addBankPointDialog.vue";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  components: { addBankPointDialog },
  dicts: ["merchant_true_false"],
  data() {
    return {
      searchColumns: [
        {
          label: "被巡查银行网点名称",
          key: "bankPointName",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "新增巡查台账",
          ename: "addBankCashRecord",
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
          label: "被巡查银行网点名称",
          prop: "bankName",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "网点是否有小面额人民币预约方式/投诉电话等信息",
          prop: "hadPhoneMsg",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ hadPhoneMsg }) => {
            return this.dict.label.merchant_true_false[hadPhoneMsg];
          },
        },
        {
          label: "网点是否有零钞兑换绿色通道",
          prop: "hadCashExchangeRoad",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ hadCashExchangeRoad }) => {
            return this.dict.label.merchant_true_false[hadCashExchangeRoad];
          },
        },
        {
          label: "是否能实现零钱快速兑换",
          prop: "canCashExchangeQuick",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ canCashExchangeQuick }) => {
            return this.dict.label.merchant_true_false[canCashExchangeQuick];
          },
        },
        {
          label: "零钱包兑换台账是否完整",
          prop: "isMoneyChangeFull",
          showOverFlowTooltip: true,
          width: 130,
          formatter: ({ isMoneyChangeFull }) => {
            return this.dict.label.merchant_true_false[isMoneyChangeFull];
          },
        },
        {
          label: "是否为网格责任银行网点",
          prop: "isGridBankPoint",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ isGridBankPoint }) => {
            return this.dict.label.merchant_true_false[isGridBankPoint];
          },
        },
        {
          label: "网格化摸排台账是否完整，符合常态化摸排要求",
          prop: "isGridRecordFull",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ isGridRecordFull }) => {
            return this.dict.label.merchant_true_false[isGridRecordFull];
          },
        },
        {
          label: "是否有自助机具开通小面额取款功能",
          prop: "hadRobotWithDrawal",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ hadRobotWithDrawal }) => {
            return this.dict.label.merchant_true_false[hadRobotWithDrawal];
          },
        },
        {
          label: "网点对外付出人民币现金整洁度是否符合要求",
          prop: "hadMoneyClean",
          showOverFlowTooltip: true,
          width: 200,
          formatter: ({ hadMoneyClean }) => {
            return this.dict.label.merchant_true_false[hadMoneyClean];
          },
        },
        {
          label: "机具小面额取款功能是否正常",
          prop: "canGetSmallMoney",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ canGetSmallMoney }) => {
            return this.dict.label.merchant_true_false[canGetSmallMoney];
          },
        },
        {
          label: "巡查人",
          prop: "inspector",
          showOverFlowTooltip: true,
          width: 120,
        },
        {
          label: "巡查日期",
          prop: "inspectionTime",
          showOverFlowTooltip: true,
          width: 100,
        },
        {
          label: "记录时间",
          prop: "recordTime",
          showOverFlowTooltip: true,
          width: 180,
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
    ...mapGetters("accountBook", ["bankCashRecordList"]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateBankCashRecordList"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.tableData = this.bankCashRecordList;
      });
    },
    export() {},
    addBankCashRecord() {
      this.status = "add";
      this.showDetail = true;
    },
    delete(row) {
      let arr = [];
      arr = this.bankCashRecordList.filter((item) => !(item.id == row.id));
      this.updateBankCashRecordList([...arr]);
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