<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree">
          <div class="title">问卷内容</div>
          <ul>
            <li
              v-for="(item, index) in treeData"
              :key="item.bankAgencyName"
              :class="[index === activeIndex ? 'active' : '']"
              @click="clickTree(item, index)"
            >
              <span :title="item.taskTypeName">{{ item.taskTypeName }}</span>
            </li>
          </ul>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="3"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetData">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        size="mini"
        slot="btn"
        ref="btnBar"
        class="i-btn-bar"
        @click="handleClick"
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
    <fillInQuestion
      v-model="showDialog"
      :status="status"
      :taskTypeId="taskTypeId"
      :defaultValue="defaultValue"
      @fetchList="fetchList()"
    ></fillInQuestion>
  </div>
</template>
  
  <script>
import pageMixin from "@/mixins/pageMixin";
import fillInQuestion from "../components/fillInQuestion.vue";
import commonService from "@/modules/module-gridManagement/api";
import services from "../api";
export default {
  name: "TaskList",
  mixins: [pageMixin],
  components: {
    fillInQuestion,
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
      taskTypeId: null, // 任务类型

      // 过滤器数据
      searchColumns: [
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
          cname: "填写问卷",
          ename: "handleAdd",
          type: "primary",
          permi: "taskManagement:taskList:add",
        },
      ],
      // table表格的字段数据
      columns: [
        { label: "创建人", prop: "createBy", showOverFlowTooltip: true },
        { label: "创建时间", prop: "createTime", showOverFlowTooltip: true },
        { label: "操作", slot: true, width: 150 },
      ],
      // table表格的操作按钮数据
      tableBtn: [
        {
          cname: "详情",
          ename: "handleDetail",
          type: "text",
          permi: "taskManagement:taskList:view",
        },
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
          permi: "taskManagement:taskList:edit",
        },
        {
          cname: "删除",
          ename: "handleDelete",
          type: "text",
          permi: "taskManagement:taskList:delete",
        },
      ],
      status: "add",
      defaultValue: {},
      showDialog: false,
      maxHeight: window.innerHeight - 370,
      treeData: [],
      activeIndex: null,
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  mounted() {
    this.iterator = this.initData();
    this.iterator.next();
  },
  methods: {
    *initData() {
      yield this.getNetworkOptions();
      yield this.fetchList();
    },
    getNetworkOptions() {
      const params = {
        pageNum: 100,
        pageSize: 100,
      };
      services.taskTypePage(params).then((res) => {
        this.treeData = res.rows;
        this.activeIndex = 0;
        this.taskTypeId = res.rows[0].id;
        this.iterator.next();
      });
    },
    // 重置
    resetData() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
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
          typeId: this.taskTypeId ? this.taskTypeId : undefined,
        };
        services
          .questionRecordList(param)
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
      }
    },
    clickTree(item, index) {
      this.activeIndex = index;
      this.taskTypeId = item.id;
      //   if (this.activeIndex == index) {
      //     this.activeIndex = null;
      //     this.bankAgencyName = null;
      //     this.taskTypeId = null;
      //   } else {
      //     this.activeIndex = index;
      //     this.bankAgencyName = item.bankAgencyName;
      //     this.taskTypeId = item.id;
      //   }
      this.fetchList();
    },
    // 编辑
    handleEdit(row) {
      this.status = "edit";
      this.showDialog = true;
      this.defaultValue = row;
    },
    // 查看
    handleDetail(row) {
      this.status = "detail";
      this.showDialog = true;
      this.defaultValue = row;
    },
    // 删除
    handleDelete(row) {
      this.makeSure("确认删除该问卷吗?", () => {
        this.delData(row);
      });
    },
    // 发送删除请求
    delData(row) {
      services.questionRecordDelete({ id: row.id }).then((res) => {
        this.$message({
          type: "success",
          message: "删除成功",
        });
        this.fetchList();
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
</style>
  