<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <div v-show="searchColumns.length > 0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <saveGroup v-model="showSave" :defaultValue="defaultValue" :type="2" @fetchList="fetchList">
    </saveGroup>
  </div>
</template>

<script>
import services from "../api";
import pageMixin from "@/mixins/pageMixin";
import saveMixins from "../mixins/saveMixins";
import customerMixin from '@/modules/module-gridManagement/mixins/customerMixin.vue';
export default {
  mixins: [pageMixin, saveMixins, customerMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "客户编号",
          key: "customNm",
          type: "text",
        },
        {
          label: "客户名称",
          key: "customName",
          type: "text",
        },
        {
          label: "商户简称",
          key: "customShortName",
          type: "text",
        },
        {
          label: "营业执照号码",
          key: "businessLicenseNum",
          type: "text",
        },
        {
          label: "法人姓名",
          key: "legalPersonName",
          type: "text",
        },
        {
          label: "法人身份证号码",
          key: "legalPersonIdentityNum",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "保存客群",
          ename: "saveGroupHandle",
          type: "primary",
        },
      ],
      maxHeight: window.innerHeight - 360,
      tableBtn: [
        {
          cname: "详情",
          ename: "customerViewHandle",
        },
      ],
      columns: [
        { label: "商户编号", prop: "customNm" },
        { label: "商户名称", prop: "customName" },
        { label: "营业执照号码", prop: "businessLicenseNum" },
        { label: "法人名称", prop: "legalPersonName" },
        {
          label: "法人身份证号",
          prop: "legalPersonIdentityNum",
          formatter: ({ legalPersonIdentityNum }) => {
            return legalPersonIdentityNum.replace(/^(.{14})(?:\d+)$/, "$1****");
          },
        },
        { label: "结算账户账号", prop: "settlementAccountNum" },
        { label: "操作", slot: true },
      ],
      showSave: false,
    };
  },
  methods: {
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        const data = {
          ...this.formInline,
          ...formInfo,
        };
        services.acquiringList(data).then((res) => {
          this.tableData = res.rows;
          this.total = res.total;
        });
      });
    },
    saveGroupHandle() {
      this.$refs.filterBar.getForm((formInfo) => {
        this.showSave = true;
        this.defaultValue = {
          ...formInfo,
        };
      });
    },
  },
  mounted() {
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped></style>
