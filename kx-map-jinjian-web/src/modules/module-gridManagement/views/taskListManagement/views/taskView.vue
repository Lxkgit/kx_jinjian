<template>
  <div class="view-box">
    <div class="view-header">
      <div class="header-left">
        <svg-icon class="icon" icon-class="wangdian" />
        <span class="text">任务详情</span>
      </div>
      <el-button @click="backHandle">返回</el-button> 
    </div>

    <div class="view-main">
      <table-title title="任务基础信息"></table-title>
      <i-form
        ref="msgForm1"
        :gridColumns="3"
        :columns="columns1"
        labelWidth="100px"
        labelPosition="right"
      >
      </i-form>
      <table-title title="任务表单"></table-title>
        <dynamic-field-config></dynamic-field-config>
      <table-title title="任务执行名单"></table-title>
      <div class="label-box">
        <div class="label-item" v-for="(item, index) in labelList" :key="index">
          <span class="text">{{ item.name }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import dynamicFieldConfig from "./components/dynamicFieldConfig.vue";
export default {
  components: {
    tableTitle,
    dynamicFieldConfig
  },
  data() {
    return {
      status: "view",
      columns1: [
        {
          label: "任务名称",
          key: "complaintPersonSource",
          type: "text",
          disabled: () => this.status == "view",
        },
        {
          label: "任务类型",
          key: "complaintTime",
          type: "select",
          disabled: () => this.status == "view",
        },
        {
          label: "任务优先级",
          key: "complaintPerson",
          type: "select",
          disabled: () => this.status == "view",
        },
        {
          label: "任务频次",
          key: "complaintPersonPhone",
          type: "select",
          disabled: () => this.status == "view",
        },
        {
          label: "任务起止时间",
          key: "complaintPersonPhone1",
          type: "date",
          disabled: () => this.status == "view",
        },
        {
          label: "执行周期",
          key: "leadSource",
          type: "select",
          disabled: () => this.status == "view",
        },
        {
          label: "具体事由",
          key: "specificCause",
          type: "textarea",
          disabled: () => this.status == "view",
          span: 3,
        },
      ],
      labelList: [
        { name: "浙江东阳农村商业银行股份有限公司南市支行南岘分理处" },
        { name: "建设银行金华分行" },
        { name: "工商银行金华分行" },
      ],
    };
  },
  mounted() {
    this.status = this.$route.query.type;
  },
  methods: {
    backHandle() {
      window.history.back();
    },
  },
};
</script>
<style lang="scss" scoped>
.view-box {
  width: 100%;
  height: 100%;
}
.view-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  height: 64px;
  background: #ffffff;
  box-shadow: 1px 0px 6px 0px #ebeef5;
  margin-bottom: 12px;
  .header-left {
    height: 24px;
    display: flex;
    .icon {
      width: 24px;
      height: 24px;
      margin-right: 8px;
    }
    .text {
      font-size: 20px;
      line-height: 24px;
      height: 24px;
      color: #323548;
    }
  }
}

.view-main {
  width: 100%;
  min-height:calc(100vh - 64px - 56px - 64px - 32px);
  background: #ffffff;
  box-shadow: 1px 0px 6px 0px #ebeef5;
  padding: 16px;
}
.label-box {
  width: 100%;
  margin-top: 16px;
  display: flex;
  .label-item {
    height: 36px;
    padding: 8px 12px;
    border-radius: 4px;
    background: rgba(94, 129, 244, 0.1);
    margin: 0 12px 12px 0;
    .text {
      font-size: 14px;
      color: #5e81f4;
      line-height: 20px;
    }
  }
}
</style>
