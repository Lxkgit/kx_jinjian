<template>
  <div class="template-box">
    <!-- 顶部tab切换 -->
    <div class="task-period">
        <div v-for="(item,index) in tabList" :key="index" 
         :class="taskActive == item.label ? 'period-box period-active' : 'period-box'" 
         @click="changeTaskType(item)">
            {{ item.label }}
        </div>
     </div>
    <div class="table-box">
      <table-title title="任务类型配置列表" class="table-title">
        <template #right>
          <el-button type="primary" @click="clickHandle('addHandle')"
            >创建模版</el-button
          >
        </template>
      </table-title>
      <div class="i-table">
        <i-table
        ref="table"
        :loading="tableLoading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        maxHeight="565"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        :showIndex="true"
        >
          <template #action="scope">
            <i-table-btns
              :count="4"
              :btn-lists="tableBtn"
              @click="(type) => clickHandle(type, scope.row)"
              :data="scope.row"
            >
            </i-table-btns>
          </template>
        </i-table>
      </div>

      <!-- 创建模板弹窗 -->
      <IDialog
        :title="dialogTitle"
        v-model="showDialog"
        @handleClose="handleClose"
        @handleConfirm="handleConfirm"
        :loading="loading"
        width="1100px"
      >
        <table-title title="基础信息"></table-title>
        <i-form
          ref="myForm"
          :columns="formColumns"
          labelWidth="120px"
          labelPosition="right"
          :gridColumns="3"
        />
        <table-title title="模板内容"></table-title>
        <dynamic-field-config :fields="fields"></dynamic-field-config>
      </IDialog>

      <!-- 预览模板弹窗 -->
      <taskDetail v-model="showViewDialog" title="任务模板预览"></taskDetail>

    </div>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import dialogMixin from "@/mixins/dialogMixin.vue";
import dynamicFieldConfig from "./components/dynamicFieldConfig.vue";
import taskDetail from "./components/taskDetail.vue";
export default {
  mixins: [pageMixin, dialogMixin],
  components: {
    tableTitle,
    dynamicFieldConfig,
    taskDetail
  },
  data() {
    return {
      loading: false,
      tableLoading: false,
      tableData: [{ servePointAddr: "测试" }],
      columns: [
        {
          label: "任务类型名称",
          prop: "servePointAddr",
        },
        {
          label: "模板引用次数",
          prop: "dutyName",
        },
        {
          label: "创建机构",
          prop: "dutyNam1e",
        },
        { label: "创建时间", prop: "dutyPhone" },
        { label: "操作", slot: true, width: 150 },
      ],
      tableBtn: [
        {
          cname: "预览",
          ename: "viewHandle",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "删除",
          ename: "delHandle",
          danger:'danger'
        },
      ],
      formColumns: [
        {
          label: "模板名称",
          key: "servePointAddr",
          value: "",
          type: "text",
          rules: [
            {
              required: true,
              message: "请输入任务类型名称",
              trigger: ["change", "blur"],
            },
          ],
        },
      ],
      dialogTitle: "",
      taskActive:'全部',
      tabList: [
        { label: "全部", value: "1" },
        { label: "我创建的", value: "2" },
        { label: "已使用的", value: "3" },
        { label: "未使用的", value: "4" },
      ],
      active: "1",
      showViewDialog:false,
      fields:[{},{},{},{}],
    };
  },
  watch: {
    active: {
      handler(val) {
        if (val == 1) {
        } else if (val == 2) {
        } else if (val == 3) {
        }
      },
    },
  },
  methods: {
    changeTaskType(item) {
      this.taskActive = item.label;
    },
    clickHandle(type, row) {
      console.log("--type", type, row);
      if (type == "delHandle") {
        this.$confirmDialog({
          title: "删除模板",
          message: "除模板后，将无法引用此模板，请谨慎操作！",
          type: "danger",
          confirmBtnText: "确定",
          confirmCallBack: () => {
            console.log(111);
          },
          cancelCallBack: () => {},
        });
      } else if (type == "editHandle") {
        this.dialogTitle = "编辑模板";
        this.$nextTick(() => {
          this.$refs.myForm.setForm(row);
        });
        this.showDialog = true;
      } else if (type == "addHandle") {
        this.dialogTitle = "创建模板";
        this.showDialog = true;
      } else if (type == "viewHandle") {
        this.showViewDialog=true
      }
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {});
    },
    handleClose() {
      this.$refs.myForm.reset();
    },
  },
};
</script>
<style lang="scss" scoped>
.task-period {
    width: 100%;
    height: 52px;
    background: #fff;
    padding: 16px 16px 0 16px;
    border-bottom: 1px solid #5E81F4;
    display: flex;
    align-items: center;
    .period-box {
      width:120px;
      height: 35px;
      background: #F6F7FA;
      border-radius: 4px 4px 0px 0px;
      border: 1px solid #EBEEF5;
      cursor:pointer;
      color: #323548;
      font-size: 14px;
      text-align: center;
      line-height: 36px;
    }
    .period-active {
      border: 1px solid #5E81F4;
      background: linear-gradient(180deg, #5E81F4 0%, #425AAA 100%);
      color: #FFFFFF;
    }
    .period-box:not(:first-child) {
      margin-left: 20px;
    }
}
.template-box {
  height: 100%;
  width: 100%;
}
.template-header {
  height: 52px;
  width: 100%;
  background: #ffffff;
  margin-bottom: 12px;
  padding-left: 16px;
}
.table-box {
  margin-top: 12px;
  width: 100%;
  padding: 16px;
  height: calc(100% - 52px - 12px);
  background: #ffffff;
  .table-title {
    padding: 16px;
    padding-top:0;
    height: 68px;
  }
}
</style>
