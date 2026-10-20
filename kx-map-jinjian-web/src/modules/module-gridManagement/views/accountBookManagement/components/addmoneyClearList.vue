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
    ></i-form>
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
          label: "记录人",
          key: "records",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入记录人",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "摸排日期",
          key: "feelDate",
          type: "date",
          required: true,
          format: "yyyy-MM-dd",
          value: "",
          rules: [
            {
              required: true,
              message: "请选择摸排日期",
              trigger: ["change"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "记录内容",
          key: "recordContent",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入记录内容",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", ["amountClearList"]),
  },
  methods: {
    ...mapMutations("accountBook", ["updateAmountClearList"]),
    initData() {
      console.log(this.status);
      if (this.status == "edit" || this.status == "view") {
        this.$nextTick(() => {
          console.log("defaultValue", this.defaultValue);
          this.$refs.storeForm.setForm(this.defaultValue);
        });
      }
    },
    handleConfirm() {
      this.$refs.storeForm.getForm((formInfo) => {
        // 新增商户
        let arr = this.amountClearList;
        let obj = {};
        if (this.status == "add") {
          obj = {
            id: new Date().getTime(),
            recordTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
          };
        } else if (this.status == "edit") {
          let { records, feelDate, recordContent, id, recordTime } =
            this.defaultValue;
          arr = arr.filter((item) => !(item.id == id));
          obj = { records, feelDate, recordContent, id, recordTime };
        }
        this.updateAmountClearList([{ ...obj, ...formInfo }, ...arr]);
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