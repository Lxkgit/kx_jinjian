<template>
  <div class="table-box">
    <table-title title="任务类型配置列表" class="table-title">
      <template #right>
        <el-button type="primary" @click="clickHandle('addHandle')"
          >新建任务类型</el-button
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
      maxHeight="630"
      :pageSize="formInline.pageSize"
      @currentChange="currentChange"
      @sizeChange="sizeChange"
      :showIndex="true"
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
    </div>

    <IDialog
      :title="dialogTitle"
      v-model="showDialog"
      @handleClose="handleClose"
      @handleConfirm="handleConfirm"
      :loading="loading"
    >
      <i-form
        ref="myForm"
        :columns="formColumns"
        labelWidth="120px"
        labelPosition="right"
      />
    </IDialog>
  </div>
</template>
<script>
import pageMixin from "@/mixins/pageMixin";
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  mixins: [pageMixin, dialogMixin],
  components: {
    tableTitle,
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
          label: "创建人",
          prop: "dutyName",
        },
        { label: "创建时间", prop: "dutyPhone" },
        { label: "操作", slot: true, width: 150 },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "删除",
          ename: "delHandle",
          danger:'danger',
        },
      ],
      formColumns: [
        {
          label: "任务类型名称",
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
        {
          label: "说明",
          key: "groupDesc",
          value: "",
          type: "textarea",
          maxlength: 200,
          showWordLimit: true,
        },
      ],
      dialogTitle:'',
    };
  },
  methods: {
    clickHandle(type, row) {
      console.log("--type", type, row);
      if (type == "delHandle") {
        this.$confirmDialog({
          title: "删除类型",
          message: "删除类型后，将无法选择引用任务类型，无法恢复，请谨慎操作！",
          type: "danger",
          confirmBtnText: "确定",
          confirmCallBack: () => {
            console.log(111);
          },
          cancelCallBack: () => {},
        });
      } else if (type == "editHandle") {
        this.dialogTitle='编辑任务类型'
        this.$nextTick(()=>{
            this.$refs.myForm.setForm(row)
        })
        this.showDialog = true;
      } else {
        this.dialogTitle='新建任务类型'
        this.showDialog = true;
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
.table-box {
  width: 100%;
  padding: 16px;
  height: 100%;
  background: #ffffff;
  .i-table {
    height: calc(100% - 68px);
  }
}
.table-title {
  padding: 16px;
  padding-top:0;
  height: 68px;
}
</style>
