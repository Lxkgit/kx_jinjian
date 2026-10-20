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
          label: "被巡查银行网点名称",
          key: "bankName",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入被巡查银行网点名称",
              trigger: ["change", "blur"],
            },
          ],
          disabled: () => this.status == "view",
        },
        {
          label: "网点是否有小面额人民币预约方式/投诉电话等信息",
          key: "hadPhoneMsg",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择网点是否有小面额人民币预约方式/投诉电话等信息",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网点是否有零钞兑换绿色通道",
          key: "hadCashExchangeRoad",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择网点是否有零钞兑换绿色通道",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否能实现零钱快速兑换",
          key: "canCashExchangeQuick",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否能实现零钱快速兑换",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "零钱包兑换台账是否完整",
          key: "isMoneyChangeFull",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择零钱包兑换台账是否完整",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否为网格责任银行网点",
          key: "isGridBankPoint",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否为网格责任银行网点",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网格化摸排台账是否完整，符合常态化摸排要求",
          key: "isGridRecordFull",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择网格化摸排台账是否完整，符合常态化摸排要求",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "是否有自助机具开通小面额取款功能",
          key: "hadRobotWithDrawal",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择是否有自助机具开通小面额取款功能",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网点对外付出人民币现金整洁度是否符合要求",
          key: "hadMoneyClean",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择网点对外付出人民币现金整洁度是否符合要求",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "机具小面额取款功能是否正常",
          key: "canGetSmallMoney",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择机具小面额取款功能是否正常",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "所在地理位置",
          key: "location",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入所在地理位置",
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
      ],
      dialogVisible: false,
    };
  },
  computed: {
    ...mapState("accountBook", ["bankCashRecordList"]),
  },
  mounted() {
    console.log("111111", this.$parent);
  },
  methods: {
    ...mapMutations("accountBook", ["updateBankCashRecordList"]),
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
        let arr = this.bankCashRecordList;
        let obj = {};
        if (this.status == "add") {
          obj = { id: new Date().getTime() };
        } else if (this.status == "edit") {
          let { id } = this.defaultValue;
          arr = arr.filter((item) => !(item.id == id));
          obj = { id };
        }
        (obj.recordTime = dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss")),
          this.updateBankCashRecordList([{ ...obj, ...formInfo }, ...arr]);
        this.$message.success(this.status == "edit" ? "编辑成功!" : "新增成功");
        this.$emit("fetchList");
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