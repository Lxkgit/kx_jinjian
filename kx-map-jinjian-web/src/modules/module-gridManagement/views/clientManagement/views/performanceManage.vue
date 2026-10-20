<template>
  <div style="height: 100%;">
    <PageContent :hasLeftbg="false" style="height: 100%;">
      <!-- 过滤器 -->
      <div class="filterBar-box">
        <i-filter-bar
          ref="filterBar"
          slot="filter"
          class="filterBar"
          :columns="searchColumns"
          :gridColumns="4"
          @eventFun="eventFun"
        >
        </i-filter-bar>
        <div>
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </div>
      <!-- 按钮-->
    <table-title title="网点列表" class="table-title">
        <template #left>
          <div class="title-left">
            <svg-icon icon-class="branch" class="title-icon"></svg-icon>
            <div class="title-text">网点总数：{{ total }}</div>
          </div>
        </template>
        <template #right>
          <i-button-bar
          style="margin-bottom: 0;"
          :btn-lists="btnLists"
          @click="clickHandle"
          slot="btn"
          ref="btnBar"
        >
      </i-button-bar>
        </template>
    </table-title>

    <!-- 列表 -->
    <div class="i-table">
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
        :maxHeight="maxHeight"
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
    </div>

    </PageContent>
    <saveGroup
      v-model="showSave"
      :defaultValue="defaultValue"
      :type="2"
      @fetchList="fetchList"
    >
    </saveGroup>
    <NetworkDetail v-model="showNetwork"></NetworkDetail>
    <importDetail v-model="showImport"></importDetail>
  </div>
</template>

<script>
import services from "../api";
import pageMixin from "@/mixins/pageMixin";
import optionMixin from "@/mixins/optionMixin";
import saveMixins from "../mixins/saveMixins";
import customerMixin from "@/modules/module-gridManagement/mixins/customerMixin.vue";
import { download } from "@/utils/ruoyi";
import { getToken } from "@/utils/auth";
import importDetail from "../components/importDetail.vue";
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
    "dot_type",
    "new_grid_type"
  ],
  components: {
    importDetail,
  },
  data() {
    return {
      props: {
        label: "name",
        children: "children",
        isLeaf: (data) => {
          return !data.isLeaf;
        },
      },
      searchColumns: [
        {
          label: "网点名称",
          key: "branchName",
          type: "text",
        },
        // {
        //   label: "网点负责人",
        //   key: "customerIdentity",
        //   type: "text",
        // },
        {
          label: "所在地区",
          key: "gridCode",
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
          events: {
            change: true,
          },
        },
        {
          label: "网点地址",
          key: "branchAddr",
          type: "text",
        },
        {
          label: "网点功能",
          key: "branchFunctionList",
          value: "",
          type: "cascader",
          filterable: true,
          collapseTags: true,
          showAllLevels: false,
          props: {
            label: "dictLabel",
            value: "dictValue",
            children: "children",
            emitPath: false,
            multiple: true,
          },
          clearable: true,
          options: this.getTree,
        },
        {
          label: "网点类型",
          key: "branchTypeList",
          type: "select",
          multiple: true,
          collapseTags: true,
          options: () => this.dict.type.dot_type,
        },
        {
          label: "网点所在地类型",
          key: "branchAddrType",
          type: "select",
          options: () => this.dict.type.new_grid_type,
        },
        {
          label: "所属银行机构",
          key: "parentOrgNum",
          type: "select",
          options: () => [],
        },
      ],
      btnLists: [
        {
          cname: "网点保存",
          ename: "saveGroupHandle",
          type: "primary",
        },
        {
          cname: "网点导入",
          ename: "importHandle",
          // slot: true,
        },
        {
          cname: "导出",
          ename: "exportHandle",
        },
        // {
        //   cname: "模版下载",
        //   ename: "dowlonadHandle",
        // },
      ],
      maxHeight: window.innerHeight - 400,
      tableBtn: [
        {
          cname: "详情",
          ename: "networkClick",
        },
        {
          cname: "编辑",
          ename: "editClick",
        },
      ],
      columns: [
        {
          label: "网点名称",
          prop: "branchName",
          width: 320,
          // showOverFlowTooltip: true,
        },
        {
          label: "所属银行机构",
          prop: "orgName",
          showOverFlowTooltip: true,
          width: 180,
        },
        {
          label: "银行机构类型",
          prop: "orgType",
        },
        {
          label: "网点负责人",
          prop: "branchChargePerson",
          width: 100,
        },
        {
          label: "负责人联系电话",
          prop: "chargeMobilePhone",
          width: "130",
        },
        {
          label: "网点类型",
          prop: "branchType",
          showOverFlowTooltip: true,
          width: 180,
          formatter: ({ branchType }) => {
            let str = "";
            let arr = [];
            if (branchType && branchType.includes(",")) {
              arr = branchType.split(",");
              arr.forEach((item, idx) => {
                if (idx == "0") {
                  str = this.dict.label.dot_type[item];
                } else str = str + "," + this.dict.label.dot_type[item];
              });
            } else {
              str = this.dict.label.dot_type[branchType];
            }

            return str;
          },
        },
        {
          label: "网点地址",
          prop: "branchAddr",
          // showOverFlowTooltip: true,
          width: 180,
        },
        {
          label: "所属网格",
          prop: "gridName",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "网点所在地类型",
          prop: "branchAddrType",
          showOverFlowTooltip: true,
          width: 150,
          formatter: ({ branchAddrType }) => {
            return this.dict.label.new_grid_type[branchAddrType];
          },
        },
        { label: "操作", slot: true ,fixed:'right'},
      ],
      upload: {
        action:
          process.env.VUE_APP_BASE_API +
          "/system/performance/customer/importBatch",
        headers: { Authorization: "Bearer " + getToken() },
      },
      fileList: [],
      showSave: false,
      showNetwork: false,
      defaultValue: {},
      treeData: [],
      activeIndex: null,
      bankAgencyName: null,
      showImport: false,
    };
  },
  mounted() {
    this.clientType = "2";
    this.fetchList();
  },
  methods: {
    exportHandle() {
      this.makeSure('确认导出吗', () => {
        this.$refs.filterBar.getForm((formInfo) => {
          // const param = {
          //   ...formInfo,
          //   bankAgencyName: this.bankAgencyName,
          // }
          services.anchorExport({}).then(res => {
            const blob = new Blob([res], { type: "application/vnd.ms-excel" });
            const fileName = "网点数据列表.xlsx"; //下载文件名称
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink);
          })
        })
      })
    },
    importHandle() {
      this.showImport = true;
    },
    successHandle() {
      this.$modal.msgSuccess("上传成功");
      this.fetchList();
    },
    // 下载模版
    dowlonadHandle() {
      services.modelDownload().then((res) => {
        const { msg } = res;
        this.download("/system/common/download", { fileName: msg }, msg);
      });
    },
    clickTree(item, index) {
      if (this.activeIndex == index) {
        this.activeIndex = null;
        this.bankAgencyName = null;
      } else {
        this.activeIndex = index;
        this.bankAgencyName = item.bankAgencyName;
      }
      this.fetchList();
    },
    eventFun(val) {
      console.log(val);
    },
    searchHandle() {
      console.log(this.$refs.filterBar);
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.fetchList();
    },
    nodeClickHandle(data) {
      this.arrCode = data.code;
      this.resetHandle();
    },
    async getTree() {
      const { data } = await this.getDicts("service_type");
      // const arr = [
      //   { dictLabel: "现金服务", dictValue: 1, children: [] },
      //   { dictLabel: "人民币", dictValue: 2, children: [] },
      // ];
      // data.forEach((item) => {
      //   if (item.dictValue >= 1 && item.dictValue <= 6) {
      //     arr[0].children.push(item);
      //   } else {
      //     arr[1].children.push(item);
      //   }
      // });
      return data;
    },
    networkClick(row) {
      console.log('row<<<<<',row);

      const { branchName, branchType, id } = row;
      const param = {
        customerName: branchName,
        dotType: branchType,
        id,
      };
      this.$store.commit("gridManagement/SET_CUSTOMER_PARAM", param);
      // this.showNetwork = true;
      this.$router.push({
        name: "BankDetailInfo",
        query: {
          type: "view",
          branchInfo:row,
          id
        },
      });
    },
    editClick(row) {
      const { branchName, branchType, id } = row;
      const param = {
        customerName: branchName,
        dotType: branchType,
        id,
        type: "edit",
      };
      this.$store.commit("gridManagement/SET_CUSTOMER_PARAM", param);
      // this.showNetwork = true;
      this.$router.push({
        name: "BankDetailInfo",
        query: {
          type: "edit",
          branchInfo:row,
          id
        },
      });
    },
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        this.loading = true;
        let data = {
          ...this.formInline,
          ...formInfo,
        };

        services
          .performanceList(data)
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

};
</script>

<style lang="scss" scoped>
.filterBar-box {
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  display: flex;
  height: 120px;
  justify-content: space-between;
  .filterBar {
    width: 85%;
  }
}
.table-title {
  padding: 16px;
  height: 68px;
}
.title-left {
  display: flex;
  align-items: center;
  margin-left: 8px;
  .title-text {
    font-size: 14px;
    color: #5E81F4;
    margin-left: 4px;
  }
  .title-icon {
    width: 20px;
    height: 20px;
  }
}
.i-table {
  height: calc(100vh - 64px - 56px - 120px - 68px - 24px);
  background: #fff;
  border: 1px solid #e7e6e6;
}
.btn-list {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.posiTital{
  position: absolute;
  right: 0;
  top: 100px;
}
</style>
