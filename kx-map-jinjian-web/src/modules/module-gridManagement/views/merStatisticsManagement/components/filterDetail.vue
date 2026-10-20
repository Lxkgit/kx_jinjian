<template>
  <IDialog
    title="条件切换"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form ref="myForm" :columns="columns" label-width="120px"> </i-form>
  </IDialog>
</template>
  
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import { getToken } from "@/utils/auth";
//   import service from '../api'
export default {
  mixins: [dialogMixin],
  data() {
    return {
      columns: [
        {
          label: "年度选择",
          key: "createBy",
          value: "",
          type: "select",
          options: [
            { label: "2025年度", value: "1" },
            { label: "2024年度", value: "2" },
            { label: "2023年度", value: "3" },
            { label: "2022年度", value: "4" },
            { label: "2021年度", value: "5" },
          ],
        },
        {
          label: "银行机构类型",
          key: "createTime",
          value: "",
          type: "select",
          options: [
            { label: "城市商业银行", value: "1" },
            { label: "农村商业银行", value: "2" },
            { label: "农村合作银行", value: "3" },
            { label: "农村信用联社", value: "4" },
            { label: "外资金融机构", value: "5" },
            { label: "其他金融机构", value: "6" },
          ],
        },
        {
          label: "银行机构",
          key: "fileName",
          value: "",
          type: "select",
          options: [
            { label: "中国工商银行", value: "1" },
            { label: "中国农业银行", value: "2" },
            { label: "中国银行", value: "3" },
            { label: "中国建设银行", value: "4" },
          ],
        },
      ],
    };
  },
  computed: {
    userName() {
      return this.$store.getters["userInfo"].userName;
    },
  },
  methods: {
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        this.$emit("handleConfirm", formInfo);
      });
    },
  },
};
</script>
  
  <style lang="scss" scoped>
</style>