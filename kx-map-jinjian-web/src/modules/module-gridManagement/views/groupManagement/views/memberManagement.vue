<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <ul>
            <h2>银行机构</h2>
            <li
              v-for="(item, index) in treeData"
              :key="item.bankAgencyName"
              :class="[index === activeIndex ? 'active' : '']"
              @click="clickTree(item, index)"
            >
              <span :title="item.bankAgencyName">{{
                item.bankAgencyName
              }}</span>
              <span>{{ item.count || 0 }}</span>
            </li>
          </ul>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
        @eventFun="eventFun"
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
        :selection="true"
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
    </SplitPageContent>
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
import services from "@/modules/module-gridManagement/views/clientManagement/api";
import pageMixin from "@/mixins/pageMixin";
import optionMixin from "@/mixins/optionMixin";
import saveMixins from "@/modules/module-gridManagement/views/clientManagement/mixins/saveMixins";
import customerMixin from "@/modules/module-gridManagement/mixins/customerMixin.vue";
import service from "../api";
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
          key: "customerName",
          type: "text",
        },
        // {
        //   label: "网点负责人",
        //   key: "customerIdentity",
        //   type: "text",
        // },
        {
          label: "网点地址",
          key: "customerAddress",
          type: "text",
        },
        {
          label: "所在县域",
          key: "belongGridCodeList",
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
          label: "网点服务",
          key: "serviceTypeList",
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
      ],
      btnLists: [
        {
          cname: "删除",
          ename: "deleteList",
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
          prop: "dutyName",
          showOverFlowTooltip: true,
          // formatter: ({ customerIdentity }) => {
          //   return this.$identityMasking(customerIdentity);
          // },
        },
        {
          label: "负责人联系电话",
          prop: "dutyPhone",
          showOverFlowTooltip: true,
          // formatter: ({ customerPhone }) => {
          //   return this.$phoneMasking(customerPhone);
          // },
        },
        {
          label: "网点地址",
          prop: "customerAddress",
          showOverFlowTooltip: true,
          // formatter: ({ customerType }) => {
          //   return this.dict.label.custom_type_three[customerType];
          // },
        },
        {
          label: "所属网格",
          prop: "belongGridName",
          showOverFlowTooltip: true,
        },
        {
          label: "网格长",
          prop: "gridMainManagerName",
          showOverFlowTooltip: true,
        },
        {
          label: "所属银行机构",
          prop: "bankAgencyName",
          showOverFlowTooltip: true,
        },
        { label: "操作", slot: true },
      ],
      showSave: false,
      showNetwork: false,
      defaultValue: {},
      treeData: [],
      activeIndex: null,
      bankAgencyName: null,
    };
  },
  computed: {
    groupInfo() {
      return this.$store.getters["gridManagement/groupInfo"];
    },
  },
  methods: {
    deleteList() {
      this.handleDel(this.multiSelected);
    },
    handleDel(rows) {
      if (rows.length == 0) {
        return this.$modal.msgWarning("请选择要删除的数据");
      }
      let ids = rows.map((item) => item.id);
      const param = {
        groupId: this.groupInfo.id,
        ids,
      };
      this.$modal.confirm("该操作将永久移除所选数据, 是否确认?").then(() => {
        service.removeMember(param).then((res) => {
          if (res.code == "200") {
            this.$message({
              type: "success",
              message: "已删除",
            });
            this.fetchList();
            this.getLeftTree();
          }
        });
      });
    },
    getLeftTree() {
      const param = {
        groupId: this.groupInfo.id,
      };
      services.listByBank(param).then((res) => {
        this.treeData = res.data;
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
      const { customerName, customerNm, customerPhone } = row;
      const param = {
        customerName,
        customerNm,
        phone:customerPhone
      };
      this.$store.commit("gridManagement/SET_CUSTOMER_PARAM", param);
      this.showNetwork = true;
    },
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        this.loading = true;
        const data = {
          ...this.formInline,
          ...formInfo,
          bankAgencyName: this.bankAgencyName,
          groupId: this.groupInfo.id,
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
  mounted() {
    this.clientType = "2";
    this.getLeftTree();
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped>
.tree-wrapper {
  overflow: auto;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  &::-webkit-scrollbar {
    width: 3px;
  }
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  &::-webkit-scrollbar-thumb {
    background: #409eff;
    border-radius: 2px;
  }
  // .tree-content {
  //   width: 100%;
  //   height: calc(100% - 46px);
  //   overflow: auto;
  //   ::v-deep .el-tree-node > .el-tree-node_children {
  //     overflow: visible;
  //   }
  // }
  ul {
    h2 {
      text-align: center;
      font-size: 20px;
      margin-bottom: 10px;
    }
    li {
      font-size: 16px;
      padding: 8px;
      color: #333;
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      span:nth-of-type(1) {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    .active {
      color: #fff;
      background: #409eff;
      border-radius: 4px;
    }
  }
}
</style>
