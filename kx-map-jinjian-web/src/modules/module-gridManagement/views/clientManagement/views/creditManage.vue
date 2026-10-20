<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
      >
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
    <saveGroup
      v-model="showSave"
      :defaultValue="defaultValue"
      :type="2"
      @fetchList="fetchList"
    >
    </saveGroup>
    <NetworkDetail v-model="showNetwork"></NetworkDetail>
  </div>
</template>

<script>
import services from "../api";
import pageMixin from "@/mixins/pageMixin";
import optionMixin from "@/mixins/optionMixin";
import saveMixins from "../mixins/saveMixins";
import customerMixin from "@/modules/module-gridManagement/mixins/customerMixin.vue";
export default {
  name: "CreditManage",
  mixins: [pageMixin, optionMixin, saveMixins, customerMixin],
  dicts: [
    "custom_type_three",
    "guarantee_method",
    "use_type",
    "five_level",
    "is_product",
    "product_label",
    "offline_attract",
    "credit_promotion",
    "exchange_method",
    "other_label",
    "in_arrears",
  ],
  data() {
    return {
      searchColumns: [
        {
          label: "网点名称",
          key: "customerName",
          type: "text",
        },
        {
          label: "网点负责人",
          key: "customerIdentity",
          type: "text",
        },
        {
          label: "网点地址",
          key: "customerIdentity1",
          type: "text",
        },
        {
          label: "所属网格",
          key: "belongGridCodes",
          value: "",
          type: "cascader",
          clearable: true,
          filterable: true,
          collapseTags: true,
          showAllLevels: false,
          props: {
            value: "code",
            label: "name",
            emitPath: false,
            multiple: true,
            lazy: true,
            lazyLoad: this.lazyLoad,
            checkStrictly: true,
          },
        },
        {
          label: "网点服务",
          key: "branchBanks",
          value: "",
          type: "cascader",
          filterable: true,
          collapseTags: true,
          showAllLevels: false,
          props: {
            label: "label",
            value: "id",
            children: "children",
            emitPath: false,
            multiple: true,
          },
          clearable: true,
          options: this.deptTreeSelect,
        },
      ],
      btnLists: [
        {
          cname: "保存客群",
          ename: "saveGroupHandle",
          type: "primary",
        },
      ],
      maxHeight: window.innerHeight - 410,
      tableBtn: [
        {
          cname: "详情",
          ename: "networkClick",
        },
      ],
      columns: [
        { label: "网点名称", prop: "customerName", showOverFlowTooltip: true },
        {
          label: "网点负责人",
          prop: "customerIdentity",
          showOverFlowTooltip: true,
          formatter: ({ customerIdentity }) => {
            return this.$identityMasking(customerIdentity);
          },
        },
        {
          label: "负责人联系电话",
          prop: "customerPhone",
          showOverFlowTooltip: true,
          formatter: ({ customerPhone }) => {
            return this.$phoneMasking(customerPhone);
          },
        },
        {
          label: "网点地址",
          prop: "customerType",
          showOverFlowTooltip: true,
          formatter: ({ customerType }) => {
            return this.dict.label.custom_type_three[customerType];
          },
        },
        {
          label: "所属网格",
          prop: "belongGridName",
          showOverFlowTooltip: true,
        },
        {
          label: "网格长",
          prop: "gridManager",
          showOverFlowTooltip: true,
        },
        { label: "所属银行机构", prop: "branchBank", showOverFlowTooltip: true },
        { label: "操作", slot: true },
      ],
      showSave: false,
      showNetwork:false,
      defaultValue: {},
    };
  },
  methods: {
    networkClick() {
      this.showNetwork = true
    },
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        this.loading = true;
        const data = {
          ...this.formInline,
          ...formInfo,
        };
        services
          .creditList(data)
          .then((res) => {
            this.tableData = res.rows;
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
          });
      });
    },
    saveGroupHandle() {
      this.$refs.filterBar.getForm((formInfo) => {
        let flag = false;
        Object.keys(formInfo).forEach((key) => {
          if (Array.isArray(formInfo[key])) {
            flag =
              flag ||
              formInfo[key].reduce((f, cur) => {
                return f || !!cur || cur === 0;
              }, false);
          } else {
            flag = flag || !!formInfo[key];
          }
        });
        if (!flag) {
          this.$modal.msgWarning("请至少填写一项");
          return;
        }
        this.showSave = true;
        this.defaultValue = {
          ...formInfo,
        };
      });
    },
  },
  mounted() {
    this.clientType = "2";
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped></style>
