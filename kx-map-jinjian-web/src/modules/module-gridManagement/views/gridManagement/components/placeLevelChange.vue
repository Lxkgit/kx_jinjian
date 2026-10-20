<template>
    <IDialog
      title="条件切换"
      width="40%"
      v-model="showDialog"
      @handleClose="handleClose"
      @handleConfirm="handleConfirm"
    >
      <i-form ref="myForm" :columns="columns" label-width="100px" />
    </IDialog>
  </template>
  
  <script>
  import dialogMixin from "@/mixins/dialogMixin.vue";
  export default {
    mixins: [dialogMixin],
    props: {

    },
    data() {
      return {
        columns:[
            { label: "年度选择", key: "year",type: "select", placeholder: "请选择年度", },
            { label: "银行机构类型", key: "bankType",type: "select", placeholder: "请选择机构类型", },
            { label: "银行机构", key: "bank",type: "select", placeholder: "请选择银行机构", },
        ]
      };
    },
    methods: {
        initData() {

        },
        handleClose() {
            this.$refs.myForm.reset();
            this.showDialog = false;
        },
        handleConfirm() {
            this.$refs.myForm.getForm((formInfo) => {
                // const param = {
                //     id: this.infoId,
                //     gridCode: this.arrCode,
                //     ...formInfo,
                // };
                // service
                // .gridMessageSave(param)
                // .then((res) => {
                //     if (res.code == 200) {
                //     this.$modal.msgSuccess(`修改网格信息成功`);
                //     this.$parent.fetchList();
                //     this.handleClose();
                //     }
                // })
                // .catch((err) => {
                //     this.$modal.msgError("网格信息编辑失败！");
                //     console.log("err", err);
                // });
                // console.log("formInfo", formInfo);
            });
        },
    },
  };
  </script>
  
  <style lang="scss" scoped>
  </style>