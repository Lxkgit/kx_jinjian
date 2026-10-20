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
      inspectMerchantList: [
        {
          value: "1",
          label: "美食之家",
          merchantAddress: "浙江省金华市婺江区江南街道12号",
          merchantType: "1",
          merchantLinkMan: "张三",
        },
        {
          value: "2",
          label: "时尚衣橱",
          merchantAddress: "浙江省金华市金东区多湖街道67号",
          merchantType: "2",
          merchantLinkMan: "李四",
        },
        {
          value: "3",
          label: "书香茶舍",
          merchantAddress: "浙江省金华市义乌市北苑街道127号",
          merchantType: "1",
          merchantLinkMan: "王五",
        },
        {
          value: "4",
          label: "欢乐玩具城",
          merchantAddress: "浙江省义金华市义乌市江东街道48号",
          merchantType: "2",
          merchantLinkMan: "赵六",
        },
        {
          value: "5",
          label: "鲜花小铺",
          merchantAddress: "浙江省金华市武义县白洋街道62号",
          merchantType: "1",
          merchantLinkMan: "钱七",
        },
      ],
      columns: [
        {
          label: "巡查商户",
          key: "merchantValue",
          type: "select",
          options: () => this.inspectMerchantList,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择巡查商户",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户是否具备人民币现金收付条件",
          key: "hadCashGetPay",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择商户是否具备人民币现金收付条件",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户零钞备付是否充足（超过300元）",
          key: "hadEnoughCash",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择商户零钞备付是否充足（超过300元）",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户是否了解拒收人民币现金是违法行为",
          key: "knowRefuseIllegal",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择商户是否了解拒收人民币现金是违法行为",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户是否了解兑换小面额人民币现金方式",
          key: "knowSmallCashChangeType",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择商户是否了解兑换小面额人民币现金方式",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户是否曾发生无法找零情况",
          key: "hadNoLooseChange",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择商户是否曾发生无法找零情况",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label:
            "是否有银行上门提供金融服务（现金知识宣传、介绍和兑换零钱包等举措）",
          key: "hadBankVisit",
          type: "select",
          options: () => this.dict.type.merchant_true_false,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message:
                "请选择是否有银行上门提供金融服务（现金知识宣传、介绍和兑换零钱包等举措）",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "巡查时间",
          key: "inspectTime",
          type: "datetime",
          required: true,
          format: "yyyy-MM-dd HH:mm",
          valueFormat: "yyyy-MM-dd HH:mm",
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
          label: "巡查人",
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
          label: "巡查机构",
          key: "inspectDept",
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
      ],
      dialogVisible: false,
    };
  },
  computed: {
    ...mapState("accountBook", [
      "merchantInspectList",
      "nowMerchantCashGridInfo",
    ]),
  },
  methods: {
    ...mapMutations("accountBook", ["updateMerchantInspectList"]),
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
        let arr = this.merchantInspectList;
        let obj = {};
        if (this.status == "add") {
          obj = { id: new Date().getTime() };
        } else if (this.status == "edit") {
          let { id } = this.defaultValue;
          arr = arr.filter((item) => !(item.id == id));
          obj = { id };
        }
        this.updateMerchantInspectList([
          {
            ...obj,
            ...formInfo,
            gridId: this.nowMerchantCashGridInfo.gridId,
            recordTime: dayjs(new Date()).format("YYYY-MM-DD HH:mm:ss"),
            ...this.inspectMerchantList.find(
              (item) => item.value == formInfo.merchantValue
            ),
          },
          ...arr,
        ]);
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