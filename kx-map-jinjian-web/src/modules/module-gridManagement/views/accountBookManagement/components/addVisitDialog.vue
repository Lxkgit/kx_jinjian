<template>
  <i-dialog
    :title="status == 'add' ? '新增走访' : '走访详情'"
    v-model="showDialog"
    width="50%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="status == 'add'"
    :appendToBody="true"
  >
    <i-form
      ref="visitForm"
      :columns="columns"
      labelWidth="310px"
      labelPosition="right"
    ></i-form>
  </i-dialog>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
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
    storeInfo: {
      type: Object,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "走访时间",
          key: "visitTime",
          type: "datetime",
          disabled: () => this.status == "view",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
          required: true,
          rules: [
            {
              required: true,
              message: "请选择走访时间",
              trigger: ["blur"],
            },
          ],
        },
        {
          label: "走访人",
          key: "visitPerson",
          type: "text",
          disabled: () => this.status == "view",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入走访人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否具备人民币现金收付条件",
          key: "hadMoneyCondition",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否具备人民币现金收付条件",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否张贴“支持现金支付”标志",
          key: "isBusiness",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否张贴“支持现金支付”标志",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体零钞备付是否充足（超过300元）",
          key: "enoughLooseChange",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体零钞备付是否充足（超过300元）",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体备付人民币现金是否整洁",
          key: "isCashClean",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体备付人民币现金是否整洁",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否了解拒收人民币现金是违法行为",
          key: "knowRefuseIllegal",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否了解拒收人民币现金是违法行为",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否了解兑换小面额人民币现金方式",
          key: "knowChangeCash",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否了解兑换小面额人民币现金方式",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否曾发生无法找零情况",
          key: "hadCantChangeMoney",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否曾发生无法找零情况",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "经营主体是否需要小面额现金上门兑换服务",
          key: "giveToDoorChange",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择经营主体是否需要小面额现金上门兑换服务",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "零钱包兑换个数",
          key: "conversionNum",
          type: "text",
          required: true,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请输入零钱包兑换个数",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "零钱包兑换金额",
          key: "conversionMoney",
          type: "text",
          required: true,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请输入零钱包兑换金额",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "备注",
          key: "remark",
          type: "textarea",
          disabled: () => this.status == "view",
        },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", [
      "bankList",
      "gridPointList",
      "nowGridInfo",
      "storeList",
    ]),
  },
  methods: {
    ...mapMutations("accountBook", ["updeteStoreList"]),
    initData() {
      console.log("defaultValue", this.defaultValue);
      if (this.status == "view") {
        this.$nextTick(() => {
          this.$refs.visitForm.setForm(this.defaultValue);
        });
      }
    },
    handleConfirm() {
      this.$refs.visitForm.getForm((formInfo) => {
        console.log("formInfo", JSON.stringify(formInfo));
        let timeStamp = new Date().getTime();
        let arr = this.storeList;
        let storeClassify = "3";
        if (
          formInfo.enoughLooseChange == "2" ||
          formInfo.knowChangeCash == "2"
        ) {
          storeClassify = "1";
        } else if (formInfo.giveToDoorChange == "1") {
          storeClassify = "2";
        }
        let idx = arr.findIndex((item) => item.id == this.storeInfo.id);
        arr[idx].storeClassify = storeClassify;
        arr[idx].visitList.unshift({ ...formInfo, visitId: timeStamp });
        arr[idx].visitNum = arr[idx].visitList.length;
        arr[idx].lastVisitTime = formInfo.visitTime;
        this.updeteStoreList([...arr]);
        this.$message.success("新增走访成功!");
        this.$emit("fetchList");
        this.handleClose();
      });
    },
    handleClose() {
      this.$refs.visitForm.reset();
      this.showDialog = false;
    },
  },
};
</script>

<style>
</style>