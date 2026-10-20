<template>
  <i-dialog
    :title="title"
    v-model="showDialog"
    width="80%"
    @handleConfirm="handleConfirm"
    @handleClose="handleClose"
    :showConfirm="!isDisabled"
  >
    <i-form
      v-if="columns.length > 0"
      ref="myForm"
      :columns="columns"
      label-position="right"
      label-width="140px"
    >
    </i-form>
  </i-dialog>
</template>
  
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import conversionMixin from "@/modules/module-gridManagement/views/taskManagement/mixins/conversionMixin";
import services from "../api";
export default {
  name: "taskDetailEdit",
  mixins: [dialogMixin, conversionMixin],
  props: {
    status: { type: String, default: "add" },
    defaultValue: Object,
    taskTypeId: Number,
  },
  data() {
    return {
      columns: [],
      taskInfo: {},
      formList: [],
    };
  },
  computed: {
    title() {
      return this.status === "add" ? "填写问卷" : "填写详情";
    },
    isDisabled() {
      if (this.status == "detail") {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    initData() {
      if (this.status == "add") {
        services.taskTypeDetail({ id: this.taskTypeId }).then((res) => {
          if (res.data.list) {
            let time = +new Date();
            let list = res.data.list.map((item) => {
              item.content = item.content !== "" ? item.content : "";
              if (item.fillingForm == "9" && item.isRequired == "1") {
                item.content = item.content.split(",");
              }
              item.frontKey = time;
              time += 1;
              return item;
            });
            this.$nextTick(() => {
              this.formList = list;
              this.columns = this.dataConversion(this.formList);
            });
            console.log(this.columns);
          }
        });
      } else if (this.status == "edit" || this.status == "detail") {
        this.formList = this.defaultValue.details;
        this.columns = this.dataConversion(this.defaultValue.details);
      }

      //   const { contents, taskObjectId, id, taskId, clientId, customerNm } =
      //     this.defaultValue;
      //   this.$nextTick(() => {
      //     this.taskInfo = { id, taskId, clientId, customerNm };
      //     this.modelType = taskObjectId + "";
      //     this.columns = this.dataConversion(contents);
      //   });
    },
    handleClose() {
      this.taskInfo = {};
      this.columns = [];
    },
    handleConfirm() {
      // console.log(this.columns);
      // let flag = false
      // for (const item of this.columns) {
      //   if (item.type == "checkin" && item.isMust == '1') {
      //     flag = true
      //   }
      // }
      // if (flag) {
      //   this.$message.error("请前往移动端进行打卡")
      //   return
      // }
      //   if (this.columns.length == 0) {
      //     const data = {
      //       contents: [],
      //       ...this.taskInfo,
      //     };
      //     services.editTaskDetail(data).then((res) => {
      //       if (res.code == 200) {
      //         this.showDialog = false;
      //       }
      //     });
      //   }
      this.$refs.myForm.getForm((formInfo) => {
        // const { contents } = this.defaultValue;
        this.formList.forEach((item, index) => {
          const key = "formItem" + index;
          const value = formInfo[key];
          item.detailContent = Array.isArray(value)
            ? JSON.stringify(value)
            : value;
        });

        if (this.status == "add") {
          const data = {
            details: this.formList,
            typeId: this.taskTypeId,
          };
          services.questionRecordinsert(data).then((res) => {
            if (res.code == 200) {
              this.columns = [];
              this.showDialog = false;
              this.$parent.fetchList();
              this.$modal.msgSuccess("新增成功");
            }
          });
        } else {
          const data = {
            details: this.formList,
            id: this.defaultValue.id,
          };
          services.questionRecordupdate(data).then((res) => {
            if (res.code == 200) {
              this.columns = [];
              this.showDialog = false;
              this.$parent.fetchList();
              this.$modal.msgSuccess("编辑成功");
            }
          });
        }
      });
    },
  },
};
</script>
  
  <style scoped lang="scss"></style>