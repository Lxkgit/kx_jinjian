<template>
  <i-dialog
    :title="status == 'add' ? '新增' : '编辑'"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="this.status != 'view'"
  >
    <i-form
      ref="storeForm"
      :columns="columns"
      labelWidth="350px"
      labelPosition="right"
    >
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
  dicts: ["merchant_true_false"],
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
          label: "出租车公司名称",
          key: "name",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入出租车公司名称",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "出粗车车牌号",
          key: "number",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入出粗车车牌号",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "上车点",
          key: "point",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入上车点",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "是否收取现金",
          key: "isCash",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否收取现金",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "零钱备付是否充足",
          key: "smallCashCz",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择零钱备付是否充足",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否有银行上门开展零钱包兑换服务",
          key: "exchange",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否有银行上门开展零钱包兑换服务",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否有了解整治拒收人民币现金等政策",
          key: "cleanUp",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否有了解整治拒收人民币现金等政策",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否知晓附近的零钱包兑换银行",
          key: "isKonw",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否知晓附近的零钱包兑换银行",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "巡查人",
          key: "inspector",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入巡查人",
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
          label: "记录时间",
          key: "recordTime",
          type: "date",
          required: true,
          format: "yyyy-MM-dd",
          value: "",
          rules: [
            {
              required: true,
              message: "请选择记录时间",
              trigger: ["change"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "所在网格",
          key: "girdName",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入所在网格",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
      ],
      dialogVisible: false,
    };
  },
  computed: {
    ...mapState("accountBook", ["taxiList"]),
  },
  mounted() {
    console.log("111111", this.$parent);
  },
  methods: {
    ...mapMutations("accountBook", ["updateTaxiList"]),
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
        let arr = this.taxiList;
        let obj = {};
        if (this.status == "add") {
          obj = { id: new Date().getTime() };
        } else if (this.status == "edit") {
          let { id } = this.defaultValue;
          arr = arr.filter((item) => !(item.id == id));
          obj = { id };
        }
        (obj.recordTime = dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss")),
          this.updateTaxiList([{ ...obj, ...formInfo }, ...arr]);
        this.$message.success(this.status == "edit" ? "编辑成功!" : "新增成功");
        this.$parent.fetchList();
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