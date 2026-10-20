<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree">
          <div class="title">任务类型</div>
          <el-input v-model="filterText" placeholder="请输入任务类型名称" clearable size="mini"
                    prefix-icon="el-icon-search" style="margin-bottom: 15px" />
          <el-tree :data="networkOptions" :props="defaultProps" :expand-on-click-node="false"
                   :filter-node-method="filterNode" ref="tree" node-key="id" highlight-current
                   default-expand-all @node-click="handleNodeClick"> <span slot-scope="{ node }"
                  class="hide-text">
              <span :title="node.label">{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="3">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetData">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" size="mini" slot="btn" ref="btnBar" class="i-btn-bar"
                    @click="handleClick">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange" selection @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
    <add-tabulation v-model="showDialog" :status="status" :defaultValue="defaultValue"
                    @fetchList="fetchList()"></add-tabulation>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import addTabulation from "./addTabulation.vue";
import commonService from "@/modules/module-gridManagement/api";
import services from "../../api";
export default {
  name: 'TaskList',
  mixins: [pageMixin],
  components: {
    addTabulation,
  },
  dicts: ["master_task_status", "task_level"],
  data() {
    return {
      // 左边列表
      filterText: "",
      networkOptions: [],
      defaultProps: {
        id: "id",
        label: "label",
      },
      taskTypeId: -1, // 任务类型

      // 过滤器数据
      searchColumns: [
        {
          label: "责任机构",
          key: "deptIds",
          type: "cascader",
          props: {
            label: "label",
            value: "id",
            children: "children",
            emitPath: false,
            multiple: true,
          },
          filterable: true,
          clearable: true,
          collapseTags: true,
          options: this.deptTreeSelect,
        },
        {
          label: "任务状态",
          key: "taskStatus",
          type: "select",
          placeholder: "请选择任务状态",
          clearable: true,
          options: () => this.dict.type.master_task_status,
        },
        {
          label: "任务名称",
          key: "taskName",
          type: "text",
          placeholder: "请输入任务名称",
          clearable: true,
        },
        {
          label: "创建人",
          key: "createBy",
          type: "text",
          placeholder: "请输入创建人",
          clearable: true,
        },
      ],
      // 按钮数据
      btnLists: [
        {
          cname: "新建任务",
          ename: "handleAdd",
          type: "primary",
          permi: 'taskManagement:taskList:add'
        },
        {
          cname: "批量删除",
          ename: "handleDelete",
          type: "info",
          disabled: () => this.multiSelected.length === 0,
          permi: 'taskManagement:taskList:delete'
        },
      ],
      // table表格的字段数据
      columns: [
        { label: "任务名称", prop: "taskName", showOverFlowTooltip: true },
        { label: "任务类型", prop: "taskTypeName", showOverFlowTooltip: true },
        { label: "创建人", prop: "createBy", showOverFlowTooltip: true },
        { label: "责任机构", prop: "deptName", showOverFlowTooltip: true },
        {
          label: "任务状态",
          prop: "taskStatus",
          showOverFlowTooltip: true,
          formatter: ({ taskStatus }) => {
            return this.dict.label.master_task_status[taskStatus];
          },
        },
        {
          label: "任务优先级",
          prop: "taskLevel",
          width: 120,
          showOverFlowTooltip: true,
          formatter: ({ taskLevel }) => {
            return this.dict.label.task_level[taskLevel];
          },
        },
        {
          label: "任务开始时间",
          prop: "startTime",
          showOverFlowTooltip: true,
          width: 160,
        },
        {
          label: "任务结束时间",
          prop: "endTime",
          showOverFlowTooltip: true,
          width: 160,
        },
        { label: "完成率", prop: "completeProcess", showOverFlowTooltip: true },
        { label: "操作", slot: true, width: 150 },
      ],
      // table表格的操作按钮数据
      tableBtn: [
        {
          cname: "详情",
          ename: "handleDetail",
          type: "text",
          permi: 'taskManagement:taskList:view'
        },
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
          isShow: ({ taskStatus }) => taskStatus == 0,
          permi: 'taskManagement:taskList:edit'
        },
        {
          cname: "任务明细",
          ename: "handleDefinite",
          type: "text",
          isShow: ({ taskStatus }) => taskStatus != 0,
          permi: 'taskManagement:taskList:detail'
        },
        {
          cname: "删除",
          ename: "handleDelete",
          type: "text",
          permi: 'taskManagement:taskList:delete'
        },
      ],
      status: "add",
      defaultValue: {},
      showDialog: false,
      maxHeight: window.innerHeight - 370,
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  mounted() {
    this.getNetworkOptions();
    this.fetchList();
  },
  // activated() {
  //   this.getNetworkOptions();
  //   this.fetchList();
  // },
  methods: {
    // 获取networkOptions数据
    getNetworkOptions() {
      services.getListTaskType().then((res) => {
        this.networkOptions = res.data.map((item) => {
          return {
            id: item.id,
            label: item.taskTypeName,
          };
        });
      });
    },
    // 左边列表树筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 左边列表树节点单击事件
    handleNodeClick(data) {
      this.formInline.pageNum = 1;
      this.taskTypeId = data.id;
      this.fetchList();
    },
    // 获取责任机构
    async deptTreeSelect() {
      const { data } = await commonService.deptTree();
      return data;
    },
    // 重置
    resetData() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.taskTypeId = -1;
      this.$refs.tree.setCurrentKey(null);
      this.$refs.filterBar.reset();
      this.fetchList();
    },
    // 获取表格数据
    fetchList() {
      this.loading = true;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          ...this.formInline,
          ...formInfo,
          taskType: this.taskTypeId !== -1 ? this.taskTypeId : undefined,
        };
        services
          .taskListPage(param)
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
    // 按钮列表的点击处理
    handleClick(ename) {
      // 新建任务
      if (ename === "handleAdd") {
        this.status = "add";
        this.showDialog = true;
        this.defaultValue = {};
      }

      // 批量删除
      if (ename === "handleDelete") {
        this.makeSure("确认删除多个任务吗?", () => {
          this.delData(this.multiSelected);
        });
      }
    },
    // 编辑
    handleEdit(row) {
      this.status = "edit";
      this.showDialog = true;
      this.defaultValue = {
        ...row,
      };
    },
    // 查看
    handleDetail(row) {
      this.status = "detail";
      this.showDialog = true;
      this.defaultValue = {
        ...row,
      };
    },
    // 删除
    handleDelete(row) {
      this.makeSure("确认删除该任务吗?", () => {
        this.delData([row]);
      });
    },
    // 发送删除请求
    delData(row) {
      let ids = row.map((item) => item.id);
      services.deleteTaskList({ ids }).then((res) => {
        this.$message({
          type: "success",
          message: "删除成功",
        });
        this.fetchList();
      });
    },
    // 任务明细
    handleDefinite(row) {
      this.$router.push({
        path: "/gridManagement/taskManagement/taskDetail",
        query: { taskName: row.taskName, taskId: row.id },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.tree {
  overflow-y: auto;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  ::v-deep .el-tree-node__content {
    display: inline-block;
    max-width: 200px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .title {
    text-align: center;
    font-size: 16px;
    margin-bottom: 20px;
  }
}
.i-btn-bar {
  margin-bottom: 5px;
}
</style>
