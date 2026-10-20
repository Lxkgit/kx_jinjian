<template>
  <div>
    <PageContent>
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
      <!-- 新增与删除按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        slot="btn"
        ref="btnBar"
        size="mini"
        class="i-btn-bar"
        @click="handleClick"
      ></i-button-bar>
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
        v-model="showDialog2"
        :status="status"
        :defaultValue="defaultValue"
        @fetchList="fetchList()"
      ></add-task>
    </PageContent>
  </div>
</template>
  
<script>
import addTask from "./components/addTask.vue";
import services from "../../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  name: "TaskType",
  components: { addTask },
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
          key: "taskObjectId2",
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
      btnLists: [
        {
          icon: "el-icon-plus",
          cname: "新增",
          ename: "add",
          type: "primary",
          permi: "taskManagement:taskType:add",
        },
        // {
        //   icon: "el-icon-delete",
        //   cname: "删除",
        //   ename: "delete",
        //   type: "info",
        //   permi: "taskManagement:taskType:delete",
        //   disabled: () => this.multiSelected.length === 0,
        // },
      ],
      columns: [
        { label: "任务名称", prop: "taskName", showOverFlowTooltip: true },
        { label: "任务类型", prop: "taskType" },
        { label: "任务状态", prop: "taskStatus" },
        { label: "任务开始时间", prop: "startTime" },
        { label: "任务结束时间", prop: "endTime" },
        { label: "完成进度", prop: "progress" },
        { label: "派发时间", prop: "hairTime" },
        { label: "派发机构", prop: "hairOrg" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "任务详情",
          ename: "handleView",
          type: "text",
          //   permi: "taskManagement:taskType:edit",
        },
        {
          cname: "进度明细",
          ename: "handleProgress",
          type: "text",
        },
        {
          cname: "失效",
          ename: "handleBack",
          type: "text",
        },
        {
          cname: "删除",
          ename: "handleDel",
          type: "text",
        },
      ],
      tableData: [],
      searchVal: {
        //查询表单
        taskTypeName: "",
      },
      defaultValue: {},
      status: "add",
      showDialog2: false,
      // size: "mini",
    };
  },
  computed: {
    taskHairList() {
      return this.$store.getters["gridManagement/taskHairList"];
    },
  },
  watch: {
    taskHairList: {
      handler(val) {
        this.tableData = val;
      },
      immediate: true,
      deep: true,
    },
  },
  mounted() {
    // this.fetchList();
  },
  methods: {
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
    // 发送删除请求
    delData(row) {
      let ids = row
        .map((item) => {
          return item.id;
        })
        .toString();
      // console.log(ids);
      services.deleteTaskType({ ids }).then((res) => {
        if (res.code == "200") {
          this.$message({
            type: "success",
            message: "已删除",
          });
        }
        this.fetchList();
      });
    },
    // 编辑
    handleEdit(row) {
      this.status = "edit";
      this.showDialog2 = true;
      services.taskTypeDetail({ id: row.id }).then((res) => {
        if (res.data.list) {
          let time = +new Date();
          let list = res.data.list.map((item) => {
            item.content = item.content !== "" ? item.content : "";
            if (item.fillingForm == "9" && item.isRequired == "1") {
              item.content = item.content.split(",");
            }
            item.frontKey = time;
            time += 1;
            return item;
          });
          this.defaultValue = { ...res.data, list };
        } else {
          this.defaultValue = { ...res.data };
        }
      });
    },
    // 表格右侧点击删除
    handleDel(row) {
      this.makeSure("确认删除吗?", () => {
        var arr = [...this.taskHairList];
        arr = arr.filter((item) => item.id != row.id);
        this.$store.commit("gridManagement/SET_TASK_HAIR_LIST", [...arr]);
        this.$modal.msgSuccess("删除成功");
      });
    },
    handleView(row) {
      this.status = "view";
      this.defaultValue = row;
      this.showDialog2 = true;
    },
    // 点击新增或者删除按钮
    handleClick(click) {
      // 新建任务
      if (click === "add") {
        this.status = "add";
        this.showDialog2 = true;
        this.defaultValue = {};
      }
      // 删除多个任务
      if (click === "delete") {
        this.makeSure("确认删除多个任务吗?", () => {
          this.delData(this.multiSelected);
        });
      }
    },
    // 获取列表数据  /  或进行搜索
    fetchList() {
      //   this.tableData = []
    },
  },
};
</script>
  
  <style lang="scss" scoped></style>
  