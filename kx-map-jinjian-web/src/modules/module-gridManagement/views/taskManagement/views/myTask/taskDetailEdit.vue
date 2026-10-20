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
import conversionMixin from "../../mixins/conversionMixin";
import services from '../../api'
export default {
  name: "taskDetailEdit",
  mixins: [dialogMixin, conversionMixin],
  props: {
    status: { type: String, default: "add" },
    defaultValue: Object,
  },
  data() {
    return {
      columns: [],
      taskInfo: {}
    };
  },
  computed: {
    title() {
      return this.status === "add" ? "任务反馈" : "任务详情";
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
      const { contents, taskObjectId, id, taskId,clientId,customerNm } = this.defaultValue;
      this.$nextTick(() => {
        this.taskInfo = {id, taskId, clientId,customerNm}
        this.modelType = taskObjectId + "";
        this.columns = this.dataConversion(contents);
      });
    },
    handleClose() {
      this.taskInfo={}
      this.columns=[]
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
      if(this.columns.length == 0) {
        const data = {
          contents: [],
          ...this.taskInfo
        }
        services.editTaskDetail(data).then(res=>{
          if (res.code == 200) {
            this.showDialog = false
          }
        })
      }
      this.$refs.myForm.getForm((formInfo) => {
        const { contents } = this.defaultValue
        contents.forEach((item, index)=>{
          const key = 'formItem' + index
          const value = formInfo[key]
          item.detailContent = Array.isArray(value) ? JSON.stringify(value) : value
        })
        const data = {
          contents,
          ...this.taskInfo
        }
        services.editTaskDetail(data).then(res=>{
          if (res.code == 200) {
            this.taskInfo = {}
            this.columns=[]
            this.showDialog = false
          }
        })
      });
    },
  },
};
</script>

<style scoped lang="scss"></style>