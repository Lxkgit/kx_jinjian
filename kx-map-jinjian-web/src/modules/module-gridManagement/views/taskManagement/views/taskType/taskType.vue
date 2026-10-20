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
        :selection="true"
        @selectionChange="selectionChange"
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
      <add-form
        v-model="showDialog"
        :status="status"
        :defaultValue="defaultValue"
        @fetchList="fetchList()"
      ></add-form>
    </PageContent>
  </div>
</template>

<script>
import addForm from "./addForm.vue";
import services from "../../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  name: "TaskType",
  components: { addForm },
  mixins: [pageMixin],
  dicts: ["custom_kind"],
  data() {
    return {
      searchColumns: [
        {
          label: "任务类型",
          key: "taskTypeName",
          type: "text",
          placeholder: "请输入任务类型",
        },
        {
          label: "客户类型",
          key: "taskObjectId",
          type: "select",
          placeholder: "请选择客户类型",
          options: () => this.dict.type.custom_kind,
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
        {
          icon: "el-icon-delete",
          cname: "删除",
          ename: "delete",
          type: "info",
          permi: "taskManagement:taskType:delete",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      columns: [
        { label: "任务类型", prop: "taskTypeName", showOverFlowTooltip: true },
        {
          label: "客户类型",
          prop: "taskObjectId",
          formatter: ({ taskObjectId }) => {
            return this.dict.label.custom_kind[taskObjectId];
          },
        },
        { label: "创建人", prop: "createBy" },
        { label: "创建时间", prop: "createDate" },
        { label: "更新时间", prop: "updateDate" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
          permi: "taskManagement:taskType:edit",
        },
        {
          cname: "删除",
          ename: "handleDel",
          type: "text",
          permi: "taskManagement:taskType:delete",
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
      // size: "mini",
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
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
      this.showDialog = true;
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
      this.makeSure("确认删除该类型?", () => {
        this.delData([row]);
      });
    },
    // 点击新增或者删除按钮
    handleClick(click) {
      // 新建任务
      if (click === "add") {
        this.status = "add";
        this.showDialog = true;
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
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        services
          .taskTypePage(param)
          .then((res) => {
            // console.log(res);
            this.tableData = res.rows;
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            if (err) {
              this.detailData = [];
              this.total = 0;
            }
            this.loading = false;
          });
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
