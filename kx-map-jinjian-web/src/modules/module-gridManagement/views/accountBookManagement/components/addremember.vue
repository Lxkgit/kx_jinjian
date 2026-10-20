<template>
  <i-dialog
    :title="status == 'add' ? '新增' : '编辑'"
    v-model="showDialog"
    width="30%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="this.status != 'view'"
  >
    <i-form
      ref="storeForm"
      :columns="columns"
      labelWidth="130px"
      labelPosition="right"
    >
      <template #upload>
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/"
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :disabled="status == 'view'"
        >
          <i class="el-icon-plus"></i>
        </el-upload>
      </template>
    </i-form>
  </i-dialog>
</template>
    
    <script>
import { mapState, mapMutations } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
import dayjs from "dayjs";
export default {
  name: "addStoreDialog",
  mixins: [dialogMixin],
  dicts: ["sys_user_sex"],
  props: {
    defaultValue: {
      type: Object,
    },
    status: {
      type: String,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "网点名称",
          key: "orgName",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入网点名称",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "巡查人员",
          key: "inspector",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入巡查人员",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "巡查时间",
          key: "inspectionTime",
          type: "date",
          required: true,
          format: "yyyy-MM-dd",
          value: "",
          rules: [
            {
              required: true,
              message: "请选择巡查时间",
              trigger: ["change"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "巡查内容",
          key: "inspectionContent",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入巡查内容",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "发现问题",
          key: "findProblem",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入发现问题",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "上传图片",
          key: "orgName",
          type: "slot",
          slotName: "upload",
        },
      ],
      dialogImageUrl: "",
      dialogVisible: false,
    };
  },
  computed: {
    ...mapState("accountBook", ["rememberList"]),
  },
  methods: {
    ...mapMutations("accountBook", ["updateRememberList"]),
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    initData() {
      if (this.status == "edit" || this.status == "view") {
        this.$nextTick(() => {
          console.log("defaultValue", this.defaultValue);
          this.$refs.storeForm.setForm(this.defaultValue);
        });
      }
    },
    handleConfirm() {
      this.$refs.storeForm.getForm((formInfo) => {
        let arr = this.rememberList;
        let obj = {};
        if (this.status == "add") {
          obj = {
            id: new Date().getTime(),
            recordTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
          };
        } else if (this.status == "edit") {
          let {
            orgName,
            inspector,
            inspectionTime,
            id,
            inspectionContent,
            findProblem,
          } = this.defaultValue;
          arr = arr.filter((item) => !(item.id == id));
          obj = {
            orgName,
            inspector,
            inspectionTime,
            id,
            inspectionContent,
            findProblem,
          };
        }
        this.updateRememberList([{ ...obj, ...formInfo }, ...arr]);
        this.$message.success("新增成功!");
        this.$parent.$parent.fetchList();
        this.handleClose();
      });
    },
    handleClose() {
      this.$refs.storeForm.reset();
      this.showDialog = false;
    },
  },
};
</script>
    
    <style>
</style>