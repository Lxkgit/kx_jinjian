<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <span class="operate-title">标签目录</span>
          <el-tree
            v-clickoutside="closeMenu"
            class="tree-content"
            ref="tree"
            node-key="id"
            :data="treeData"
            :props="props"
            @node-contextmenu="openMenu"
            @node-click="nodeClickHandle"
            highlight-current
            default-expand-all
            :expand-on-click-node="false"
          >
          </el-tree>
          <!-- 右键 -->
          <ul
            v-show="visible"
            :style="{ left: left + 'px', top: top + 'px' }"
            class="contextmenu"
          >
            <li
              v-hasPermi="['labelManage:labelGroup:add']"
              v-if="checkAdd(selectLeaf)"
              @click="addHandle"
            >
              新增
            </li>
            <li
              v-hasPermi="['labelManage:labelGroup:edit']"
              v-if="checkDelete(selectLeaf)"
              @click="updateHandle"
            >
              编辑
            </li>
            <li
              v-hasPermi="['labelManage:labelGroup:delete']"
              v-if="checkDelete(selectLeaf)"
              @click="deleteHandle"
            >
              删除
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
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        selection
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
    <labelDirectoryEdit
      v-model="showEditDir"
      :status="status"
      :editInfo="editInfo"
    >
    </labelDirectoryEdit>
    <labelEdit v-model="showEditLabel" :status="status" :editInfo="editInfo">
    </labelEdit>
  </div>
</template>

<script>
import service from "../api";
import pageMixin from "@/mixins/pageMixin";
import labelDirectoryEdit from "../components/labelDirectoryEdit";
import labelEdit from "../components/labelEdit";
export default {
  name: "LabelManage",
  mixins: [pageMixin],
  components: {
    labelDirectoryEdit,
    labelEdit,
  },
  data() {
    return {
      treeData: [],
      props: {
        value: "id",
        label: "labelGroupName",
        children: "child",
      },
      searchColumns: [
        {
          label: "标签内容",
          key: "labelName",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "新增标签类型",
          ename: "addHandle",
          type: "primary",
          permi: "labelManage:labelGroup:add",
          disabled: () => this.labelGroup && !this.labelGroup.disabled,
        },
        {
          cname: "新增标签内容",
          ename: "addLabelHandle",
          type: "primary",
          permi: "labelManage:label:add",
          disabled: () => this.labelGroup && this.labelGroup.disabled,
        },
        {
          cname: "批量删除",
          ename: "batchDelete",
          type: "primary",
          permi: "labelManage:label:delete",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "editLabelHandle",
          permi: "labelManage:label:edit",
        },
        {
          cname: "删除",
          ename: "deleteLabelHandle",
          permi: "labelManage:label:delete",
        },
      ],
      columns: [
        { label: "标签名称", prop: "labelGroupName" },
        { label: "标签内容", prop: "labelName" },
        { label: "创建人", prop: "createBy" },
        {
          label: "创建时间",
          prop: "createTime",
          formatter: ({ createTime }) => {
            return this.parseTime(createTime, "{y}-{m}-{d}");
          },
        },
        { label: "操作", slot: true },
      ],
      left: 0,
      top: 0,
      visible: false,
      selectLeaf: {},
      labelGroup: {
        id: 2,
      },
      showEditDir: false,
      showEditLabel: false,
      editInfo: null,
      status: "add",
    };
  },
  mounted() {
    this.iterator = this.initFun();
    this.iterator.next();
  },
  methods: {
    *initFun() {
      yield this.labelDirectory();
      yield this.fetchList();
    },
    labelDirectory() {
      service.labelDirectory().then((res) => {
        let { data } = res;
        // data[1].labelGroupName = "客户标签";
        // data = [data[1]];
        this.treeData = data.map((item) => {
          item.disabled = true;
          return item;
        });
        for (let item of data) {
          if (item.child.length > 0) {
            this.labelGroup = item.child[0];
            this.$nextTick(() => {
              this.$refs.tree.setCurrentKey(item.child[0].id);
            });
            this.iterator.next();
            return;
          } else {
            this.labelGroup = null;
            this.iterator.next();
          }
        }
      });
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
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          labelGroupId: this.labelGroup.id,
          ...formInfo,
        };
        this.loading = true;
        service
          .labelList(param)
          .then((res) => {
            const { total, rows } = res;
            console.log("rows", rows);
            this.tableData = rows;
            // this.tableData.forEach((i) => {
            //   i.labelGroupName = i.labelGroupName.replace(
            //     /^([^/]+)/,
            //     "客户标签"
            //   );
            // });
            this.total = total;
            this.loading = false;
            this.multiSelected = [];
          })
          .catch((error) => {
            if (error) {
              this.tableData = [];
              this.total = 0;
            }
            this.multiSelected = [];
            this.loading = false;
          });
      });
    },
    nodeClickHandle(data, node) {
      this.closeMenu();
      this.selectLeaf = data;
      this.labelGroup = data;
      this.resetHandle();
    },
    /* 标签目录编辑 */
    addHandle() {
      this.editInfo = this.selectLeaf;
      this.status = "add";
      this.showEditDir = true;
    },
    updateHandle() {
      this.editInfo = this.selectLeaf;
      this.status = "update";
      this.showEditDir = true;
    },
    deleteHandle() {
      const { id } = this.selectLeaf;
      this.$modal.confirm("该操作将永久删除该标签目录, 是否确认?").then(() => {
        service
          .labelDirectoryDelete({ ids: [id] })
          .then((res) => {
            this.$modal.msgSuccess("删除成功!");
            this.labelDirectory();
          })
          .catch(() => {
            // this.$modal.msgWarning('删除失败!')
          });
      });
    },
    /* 标签编辑 */
    addLabelHandle() {
      this.editInfo = this.labelGroup;
      this.status = "add";
      this.showEditLabel = true;
    },
    editLabelHandle(data) {
      this.editInfo = {
        ...this.labelGroup,
        labelName: data.labelName,
        labelId: data.id,
      };
      this.status = "update";
      this.showEditLabel = true;
    },
    batchDelete() {
      const ids = this.multiSelected.map((item) => item.id);
      this.$modal.confirm("该操作将永久删除所选标签, 是否确认?").then(() => {
        service
          .labelDeleteByIds({ ids })
          .then((res) => {
            this.$modal.msgSuccess("删除成功!");
            this.fetchList();
          })
          .catch(() => {
            // this.$modal.msgWarning('删除失败!')
          });
      });
    },
    deleteLabelHandle(data) {
      const { id } = data;
      this.$modal.confirm("该操作将永久删除该标签, 是否确认?").then(() => {
        service
          .labelDeleteByIds({ ids: [id] })
          .then((res) => {
            this.$modal.msgSuccess("删除成功!");
            this.fetchList();
          })
          .catch(() => {
            // this.$modal.msgWarning('删除失败!')
          });
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
  width: 100px !important;
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
.operate-select {
  width: 100%;
  margin-bottom: 10px;
}

.tree-wrapper {
  overflow: hidden;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  .tree-content {
    width: 100%;
    height: calc(100% - 46px);
    overflow: auto;
    ::v-deep .el-tree-node > .el-tree-node_children {
      overflow: visible;
    }
  }
}
</style>