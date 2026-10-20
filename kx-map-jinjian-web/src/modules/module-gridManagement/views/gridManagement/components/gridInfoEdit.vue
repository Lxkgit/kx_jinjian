<template>
  <IDialog
    title="网格信息维护"
    width="40%"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form ref="myForm" :columns="columns" label-width="100px" />
  </IDialog>
</template>

<script>
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  mixins: [dialogMixin],
  props: {
    infoId: {
      type: Number,
    },
    arrCode: {
      type: String,
    },
    gridInfo: {
      type: Object,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "网格客户总数",
          key: "gridCustomerTotal",
          type: "text",
          placeholder: "请输入网格客户总数",
          rules: [
            // { validator: checkNumber, message: "请填写非负整数" },
            // { validator: checkMax, message: "不得大于999999999" },
          ],
        },
        {
          label: "同业数量",
          key: "peersNum",
          type: "text",
          placeholder: "请输入同业数量",
          rules: [],
        },
        {
          label: "小区数量",
          key: "communityNum",
          type: "text",
          placeholder: "请输入小区数量",
          rules: [],
        },
        {
          label: "社区数量",
          key: "districtNum",
          type: "text",
          placeholder: "请输入社区数量",
          rules: [],
        },
        {
          label: "优质单位数量",
          key: "hiqUnitsNum",
          type: "text",
          placeholder: "请输入优质单位数量",
          rules: [],
        },
        {
          label: "优质单位情况",
          key: "hiqUnitsSituation",
          type: "text",
          placeholder: "请输入优质单位情况",
          rules: [],
        },
      ],
    };
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.$refs.myForm.setForm(this.gridInfo);
      });
    },
    handleClose() {
      this.$refs.myForm.reset();
      this.showDialog = false;
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        const param = {
          id: this.infoId,
          gridCode: this.arrCode,
          ...formInfo,
        };
        console.log("param", param);
        service
          .gridMessageSave(param)
          .then((res) => {
            if (res.code == 200) {
              this.$modal.msgSuccess(`修改网格信息成功`);
              this.$parent.fetchList();
              this.handleClose();
            }
          })
          .catch((err) => {
            this.$modal.msgError("网格信息编辑失败！");
            console.log("err", err);
          });
        // console.log("formInfo", formInfo);
      });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>