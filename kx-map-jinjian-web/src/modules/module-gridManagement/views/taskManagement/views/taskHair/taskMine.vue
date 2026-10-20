<template>
  <div>
    <PageContent>
      <el-tabs v-model="activeName">
        <el-tab-pane
          v-for="item in tabList"
          :key="item.value"
          :label="item.label"
          :name="item.value"
        ></el-tab-pane>
      </el-tabs>
      <!-- 过滤器 -->
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 数据列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight"
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
      <!-- 编辑弹窗 -->
      <add-task
        v-model="showDialog"
        :status="status"
        :defaultValue="defaultValue"
        @fetchList="fetchList()"
      ></add-task>
    </PageContent>
    <reportOnDetail v-model="showReport" :status="status"></reportOnDetail>
  </div>
</template>
    
<script>
import addTask from "./components/addTask.vue";
import reportOnDetail from "./components/reportOnDetail.vue";
import addForm from "../taskType/addForm.vue";
import services from "../../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  name: "TaskType",
  components: { addTask, reportOnDetail },
  mixins: [pageMixin],
  dicts: ["custom_kind"],
  data() {
    return {
      searchColumns: [
        {
          label: "任务名称",
          key: "taskTypeName",
          type: "text",
          placeholder: "请输入任务名称",
        },
        {
          label: "任务状态",
          key: "taskObjectId",
          type: "select",
          placeholder: "请选择任务状态",
          options: () => [
            {
              label: "进行中",
              value: "进行中",
            },
            {
              label: "已结束",
              value: "已结束",
            },
            {
              label: "未开始",
              value: "未开始",
            },
            {
              label: "已失效",
              value: "已失效",
            },
          ],
        },
        {
          label: "客户类型",
          key: "taskObjectId1",
          type: "select",
          placeholder: "请选择客户类型",
          options: () => [
            {
              label: "日常任务",
              value: "日常任务",
            },
            {
              label: "周期任务",
              value: "周期任务",
            },
          ],
        },
      ],
      searchBtn: [
        {
          icon: "el-icon-search",
          cname: "搜索",
          ename: "search",
          type: "primary",
        },
        {
          icon: "el-icon-refresh",
          cname: "重置",
          ename: "reset",
          // type: "info",
        },
      ],
      columns: [],
      tableBtn: [
        {
          cname: "任务上报",
          ename: "handlereport",
          type: "text",
          isShow: (data) =>
            this.activeName == "0" && data.feedStatus == "未反馈",
        },
        {
          cname: "反馈详情",
          ename: "handleFeedback",
          type: "text",
          isShow: (data) =>
            this.activeName == "0" && data.feedStatus != "未反馈",
        },
        {
          cname: "任务详情",
          ename: "handleView",
          type: "text",
          //   permi: "taskManagement:taskType:edit",
        },
        {
          cname: "任务转派",
          ename: "handlerective",
          type: "text",
          isShow: (data) =>
            this.activeName == "0" && data.feedStatus == "未反馈",
        },
        {
          cname: "进度明细",
          ename: "handleProgress",
          type: "text",
          isShow: (data) => this.activeName == "1",
        },
        {
          cname: "失效",
          ename: "handleBack",
          type: "text",
          isShow: (data) =>
            this.activeName == "1" && data.taskStatus != "已失效",
        },
        {
          cname: "删除",
          ename: "handleDel",
          type: "text",
          isShow: (data) => this.activeName == "1",
        },
      ],
      tableData: [],
      searchVal: {
        //查询表单
        taskTypeName: "",
      },
      defaultValue: {},
      status: "add",
      showDialog: false,
      activeName: "0",
      tabList: [
        { label: "我下发的", value: "0" },
        { label: "我转派的", value: "1" },
      ],
      showReport: false,
    };
  },
  computed: {
    taskReceiveList() {
      return this.$store.getters["gridManagement/taskReceiveList"];
    },
    taskTurnList() {
      return this.$store.getters["gridManagement/taskTurnList"];
    },
  },
  watch: {
    activeName: {
      handler(val) {
        if (val == 1) {
          this.columns = [
            { label: "任务名称", prop: "taskName", showOverFlowTooltip: true },
            { label: "任务类型", prop: "taskType" },
            { label: "任务状态", prop: "taskStatus" },
            { label: "任务开始时间", prop: "startTime" },
            { label: "任务结束时间", prop: "endTime" },
            { label: "完成进度", prop: "progress" },
            { label: "派发时间", prop: "hairTime" },
            { label: "派发机构", prop: "hairOrg" },
            { label: "操作", slot: true },
          ];
          this.tableData = this.taskTurnList;
        } else if (val == 0) {
          this.columns = [
            { label: "任务名称", prop: "taskName", showOverFlowTooltip: true },
            { label: "任务类型", prop: "taskType" },
            { label: "反馈状态", prop: "feedStatus" },
            { label: "派发机构", prop: "hairOrg" },
            { label: "任务开始时间", prop: "startTime" },
            { label: "任务结束时间", prop: "endTime" },
            { label: "操作", slot: true },
          ];
          this.tableData = this.taskReceiveList;
        }
      },
      immediate: true,
      deep: true,
    },
  },
  mounted() {},
  methods: {
    fetchList() { },
    handlereport() {
      this.status = 'add'
      this.showReport = true;
    },
    handleFeedback() {
      this.status = 'view'
      this.showReport = true;
    },
    handleBack() {
      this.$modal.msgSuccess("操作成功");
    },
    handleProgress(row) {
      var type = null;
      if (row.taskType == "周期任务") {
        type = 1;
      } else {
        type = 2;
      }
      this.$router.push({
        path: "/gridManagement/taskManagement/taskProgress",
        query: {
          type,
        },
      });
    },
    handleView(row) {
      this.status = "view";
      this.defaultValue = row;
      this.showDialog = true;
    },
    handlerective(row) {
      this.status = "turn";
      this.defaultValue = row;
      this.showDialog = true;
    },
    handleDel(row) {
      var active = this.activeName;
      this.makeSure("确认删除吗?", () => {
        var arr = [...this.taskTurnList];
        arr = arr.filter((item) => item.id != row.id);
        this.$store.commit("gridManagement/SET_TASK_TURN_LIST", [...arr]);
        this.$modal.msgSuccess("删除成功");
        this.activeName = active
      });
    },
  },
};
</script>
    
    <style lang="scss" scoped></style>
    