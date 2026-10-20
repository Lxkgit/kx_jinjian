<template>
  <div>
    <SplitPageContent>
      <template #operate>
        <span class="operate-title">客群分类</span>
        <el-tree ref="tree" node-key="id" :data="treeData" :props="props"
                 @node-contextmenu="openMenu" @node-click="nodeClickHandle" highlight-current
                 default-expand-all :expand-on-click-node="false">
        </el-tree>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetData">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
      <!-- 右键 -->
      <ul v-show="visible" :style="{ left: left + 'px', top: top + 'px' }" class="contextmenu">
        <li v-if="checkAdd(selectLeaf)" @click="addHandle">新增</li>
        <li @click="updateHandle">编辑</li>
        <li v-if="checkDelete(selectLeaf)" @click="deleteHandle">删除</li>
      </ul>
    </SplitPageContent>
    <!-- groupEdit -->
    <groupEdit v-model="showGroup" :status="status" :editInfo="editInfo" :groupType="groupType">
    </groupEdit>
    <categoryEdit v-model="showCategory" :status="status" :editInfo="editInfo"></categoryEdit>
  </div>
</template>

<script>
import service from "../api";
import groupEdit from "../components/groupEdit";
import categoryEdit from "../components/categoryEdit";
import pageMixin from "@/mixins/pageMixin";
import commonService from "@/modules/module-gridManagement/api";
export default {
  mixins: [pageMixin],
  components: {
    groupEdit,
    categoryEdit,
  },
  data() {
    return {
      treeData: [],
      props: {
        value: "id",
        label: "categoryName",
        children: "child",
      },
      customerGroup: null,
      searchColumns: [
        {
          label: "客群名称",
          key: "customGroupName",
          type: "text",
        },
        {
          label: "责任机构",
          key: "dutyDeptId",
          value: "",
          type: "cascader",
          props: {
            label: "label",
            value: "id",
            children: "children",
            emitPath: false,
          },
          options: this.deptTreeSelect,
        },
      ],
      btnLists: [
        {
          cname: "新增客群",
          ename: "addGroupHandle",
          type: "primary",
          disabled: () => this.customerGroup && this.customerGroup.disabled,
        },
        {
          cname: "导入客户",
          ename: "importHandle",
          type: "primary",
          disabled: () => this.customerGroup && this.customerGroup.disabled,
        },
      ],
      tableBtn: [
        {
          cname: "成员管理",
          ename: "memberManage",
        },
        {
          cname: "编辑",
          ename: "editGroupHandle",
        },
        {
          cname: "删除",
          ename: "deleteGroupHandle",
        },
      ],
      columns: [
        { label: "客群名称", prop: "customGroupName" },
        { label: "客户数量", prop: "customNum" },
        { label: "客群来源", prop: "customGroupSource" },
        { label: "说明", prop: "groupDesc" },
        { label: "状态", prop: "groupStatusDesc" },
        { label: "创建人", prop: "createBy" },
        {
          label: "创建时间",
          prop: "createTime",
          formatter: ({ createTime }) => {
            return this.parseTime(createTime, "{y}-{m}-{d} {h}:{i}:{s}");
          },
        },
        { label: "操作", slot: true },
      ],
      left: 0,
      top: 0,
      visible: false,
      selectLeaf: {},
      showGroup: false,
      showCategory: false,
      status: "add",
      editInfo: null,
      groupType: "0",
    };
  },
  watch: {
    visible(value) {
      if (value) {
        document.body.addEventListener("click", this.closeMenu);
      } else {
        document.body.removeEventListener("click", this.closeMenu);
      }
    },
  },
  mounted() {
    this.iterator = this.initFun();
    this.iterator.next();
  },
  methods: {
    *initFun() {
      yield this.customerGroupList();
      yield this.fetchList();
    },
    customerGroupList() {
      service.categoryList().then((res) => {
        console.log(res);
        const { data } = res;
        this.treeData = data.map((item) => {
          item.disabled = true;
          return item;
        });
        this.customerGroup = null;
        this.iterator.next();
      });
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        console.log(this.customerGroup);
        const param = {
          pageSize,
          pageNum,
          categoryId: this.customerGroup ? this.customerGroup.id : undefined,
          parentId: this.customerGroup
            ? this.customerGroup.parentId
            : undefined,
          ...formInfo,
        };
        this.loading = true;
        service
          .customerGroupList(param)
          .then((res) => {
            const { total, rows } = res;
            this.tableData = rows;
            this.total = total;
            this.loading = false;
            this.multiSelected = [];
          })
          .catch((error) => {
            if (error) {
              this.tableData = []
              this.total = 0
            }
            this.multiSelected = [];
            this.loading = false;
          });
      });
    },
    async deptTreeSelect() {
      const { data } = await commonService.deptTree();
      return data;
    },
    openMenu(e, data) {
      this.left = e.clientX;
      this.top = e.clientY;
      this.visible = true;
      this.selectLeaf = data;
    },
    closeMenu() {
      this.visible = false;
    },
    /* 客群分类 */
    nodeClickHandle(data) {
      this.customerGroup = data;
      this.resetHandle();
    },
    resetData() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.$refs.filterBar.reset();
      this.$refs.tree.setCurrentKey(null);
      this.customerGroup = null;
      this.fetchList();
    },
    addHandle() {
      this.editInfo = this.selectLeaf;
      this.status = "add";
      this.showCategory = true;
    },
    updateHandle() {
      this.editInfo = this.selectLeaf;
      this.status = "update";
      this.showCategory = true;
    },
    deleteHandle() {
      const { id } = this.selectLeaf;
      this.$modal
        .confirm("该操作将永久删除该客群分类, 是否确认?")
        .then(() => {
          service.delCategory({ id }).then((res) => {
            this.$modal.msgSuccess("删除成功!");
            this.customerGroupList();
          });
        })
        .catch(() => {
          this.$modal.msgWarning("删除失败!");
        });
    },
    /* 客群 */
    addGroupHandle() {
      this.editInfo = this.customerGroup;
      this.status = "add";
      this.showGroup = true;
      this.groupType = "0";
    },
    memberManage(data) {
      this.$store.commit("gridManagement/SET_GROUP_INFO", data);
      this.$router.push({ name: "GroupCustomer" });
    },
    editGroupHandle(data) {
      console.log(this.customerGroup, this.data);
      this.editInfo = {
        ...this.customerGroup,
        ...data,
      };
      this.status = "update";
      this.showGroup = true;
    },
    importHandle() {
      this.editInfo = this.customerGroup;
      this.status = "add";
      this.showGroup = true;
      this.groupType = "1";
    },
    deleteGroupHandle(data) {
      const { id } = data;
      this.$modal
        .confirm("该操作将永久删除该客群, 是否确认?")
        .then(() => {
          service.delGroup({ id }).then((res) => {
            this.$modal.msgSuccess("删除成功!");
            this.resetHandle();
          });
        })
        .catch(() => {
          this.$modal.msgWarning("删除失败!");
        });
    },
    checkAdd(data) {
      if (data) {
        return data.parentId == 0;
      }
    },
    checkDelete(data) {
      if (data) {
        return data.parentId != 0;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.operate-title {
  display: block;
  width: calc(100% + 10px);
  position: relative;
  top: -10px;
  left: -5px;
  line-height: 46px;
  text-align: center;
  background: #f6f6f6;
}
.contextmenu {
  position: fixed;
  margin: 0;
  background: #fff;
  z-index: 99999;
  list-style-type: none;
  padding: 5px 0;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 400;
  color: #333;
  box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
  li {
    margin: 0;
    padding: 7px 16px;
    cursor: pointer;
    &:hover {
      background: #eee;
    }
  }
}
.custom-tree-node {
  position: relative;
}
.disabled {
  cursor: not-allowed;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 2;
}
</style>
