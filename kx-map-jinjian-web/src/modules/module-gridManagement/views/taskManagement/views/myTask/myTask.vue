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
                   default-expand-all @node-click="handleNodeClick">
            <span slot-scope="{ node }" class="hide-text">
              <span :title="node.label">{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns"
                    :defaultValue="defaultValue" :gridColumns="3">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange" @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
    <task-detail-edit v-model="showTaskDetail" :status="status" :defaultValue="detailDefaultValue"
                      @fetchList="fetchList" />
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import taskDetail from "./taskDetail.vue";
import taskDetailEdit from './taskDetailEdit.vue';
import commonService from "@/modules/module-gridManagement/api";
import services from "../../api";
import { formatDate } from "../../../../../../utils";
export default {
  name: 'MyTask',
  components: { taskDetail, taskDetailEdit },
  mixins: [pageMixin],
  dicts: ["master_task_status", "child_task_status", "task_level"],
  data() {
    return {
      // 左边列表
      filterText: "",
      networkOptions: [],
      defaultProps: {
        id: "id",
        label: "label",
        children: "children",
      },
      //任务类型
      taskTypeId: -1,
      // 过滤器数据
      searchColumns: [
        {
          label: "主任务状态",
          key: "masterTaskStatus",
          type: "select",
          placeholder: "请选择主任务状态",
          clearable: true,
          options: () => this.dict.type.master_task_status,
        },
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
          collapseTags: true,
          clearable: true,
          options: this.deptTreeSelect,
        },
        {
          label: "执行人",
          key: "businessManager",
          type: "select",
          placeholder: "请输入执行人的名称或工号",
          clearable: true,
          filterable: true,
          remote: true,
          loading: false,
          remoteMethod: this.remoteMethod,
          options: [],
        },
        {
          label: "任务对象",
          key: "customerName",
          type: "text",
          placeholder: "请输入任务对象",
          clearable: true,
        },
        {
          label: "子任务状态",
          key: "childTaskStatus",
          type: "select",
          placeholder: "请选择子任务状态",
          clearable: true,
          options: () => this.dict.type.child_task_status,
        },
        {
          label: "任务名称",
          key: "taskName",
          type: "text",
          placeholder: "请输入任务名称",
          clearable: true,
          value: "",
        },
      ],
      // table表格的字段数据
      columns: [
        { label: "任务对象", prop: "customerName", width: 150 },
        {
          label: "证件号",
          prop: "customerIdentity",
          showOverFlowTooltip: true,
          width: 100,
        },
        {
          label: "任务类型",
          prop: "taskTypeName",
          showOverFlowTooltip: true,
          width: 100,
        },
        {
          label: "任务名称",
          prop: "taskName",
          showOverFlowTooltip: true,
          width: 100,
        },
        {
          label: "主任务状态",
          prop: "masterTaskStatus",
          showOverFlowTooltip: true,
          formatter: ({ masterTaskStatus }) => {
            return this.dict.label.master_task_status[masterTaskStatus];
          },
          width: 115,
        },
        {
          label: "任务优先级",
          prop: "taskLevel",
          showOverFlowTooltip: true,
          formatter: ({ taskLevel }) => {
            return this.dict.label.task_level[taskLevel];
          },
          width: 115,
        },
        {
          label: "子任务状态",
          prop: "childTaskStatus",
          showOverFlowTooltip: true,
          formatter: ({ childTaskStatus }) => {
            return this.dict.label.child_task_status[childTaskStatus];
          },
          width: 115,
        },
        {
          label: "责任机构",
          prop: "deptName",
          width: 100,
          showOverFlowTooltip: true,
        },
        {
          label: "执行人",
          prop: "managerName",
          width: 100,
          showOverFlowTooltip: true,
        },
        {
          label: "任务时间",
          prop: "time",
          showOverFlowTooltip: true,
          width: 220,
        },
        {
          label: "信息完整度",
          prop: "integrity",
          showOverFlowTooltip: true,
          width: 100,
        },
        { label: "操作", slot: true, width: 140 },
      ],
      // table表格的操作按钮数据
      tableBtn: [
        {
          cname: "详情",
          ename: "handleDetail",
          type: "text",
          permi: 'taskManagement:myTask:view',
        },
        {
          cname: "反馈",
          ename: "handleFeedback",
          type: "text",
          permi: 'taskManagement:myTask:edit',
          isShow: ({ masterTaskStatus }) => masterTaskStatus == 1,
        },
      ],
      // 任务详情弹窗
      status: "detail",
      showTaskDetail: false,
      detailDefaultValue: {},
      defaultValue: {},
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
    this.defaultValue = {
      taskName: this.$route.query.taskName,
      taskId: this.$route.query.taskId,
    };
    this.$nextTick(() => {
      this.fetchList();
    });
  },
  methods: {
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
    // 获取networkOptions数据
    getNetworkOptions() {
      services.taskListOwnType().then((res) => {
        this.networkOptions = res.data.map((item) => {
          return {
            id: item.id,
            label: item.taskTypeName,
          };
        });
      });
    },
    // 获取责任机构
    async deptTreeSelect() {
      const { data } = await commonService.deptTree();
      return data;
    },
    // 远程搜索方法
    remoteMethod(managerName) {
      if (managerName) {
        this.searchColumns[2].loading = true;
        this.$refs.filterBar.getForm((formInfo) => {
          const data = {
            deptIdList: formInfo.deptIds,
            managerName
          }
          services
            .queryBusinessManage(data)
            .then((res) => {
              this.searchColumns[2].options = res.data.map((i) => {
                return { label: i.businessManageUser, value: i.managerNum };
              });
              this.searchColumns[2].loading = false;
            });
        });
      } else {
        this.searchColumns[2].options = [];
      }
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
          .myTaskPage(param)
          .then((res) => {
            this.tableData = res.rows;
            this.tableData.forEach((i) => {
              i.time =
                formatDate(i.startTime, false) +
                " — " +
                formatDate(i.endTime, false);
            });
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
          });
      });
    },
    // 重置
    resetHandle() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.taskTypeId = -1;
      this.$refs.filterBar.reset();
      this.$refs.tree.setCurrentKey(null);
      this.searchColumns[2].options = [];
      this.fetchList();
    },
    // 详情
    handleDetail(row) {

      services.taskDetailContentList({ taskDetailId: row.id }).then((res) => {
        this.detailDefaultValue = {
          ...row,
          contents: res.data,
        };
        this.status = "detail";
        this.showTaskDetail = true;
      });
    },
    handleFeedback(row) {
      services.taskDetailContentList({ taskDetailId: row.id }).then((res) => {
        this.detailDefaultValue = {
          ...row,
          contents: res.data,
        };
        this.status = "feedback";
        this.showTaskDetail = true;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.tree {
  overflow-y: auto;
  height: calc(100vh - 215px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  ::v-deep .el-tree-node__content {
    display: inline-block;
    max-width: 180px;
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
