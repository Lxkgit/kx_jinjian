<template>
  <PageContent>
    <IDescriptions title="商机信息" :infoData="infoData"></IDescriptions>
    <el-divider></el-divider>
    <IDescriptions title="商机反馈"></IDescriptions>
    <i-form v-if="columns.length>0" ref="myForm" :columns="columns" label-position="right"
            label-width="140px">
    </i-form>
    <div class="btn-wrapper">
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
      </i-button-bar>
    </div>
  </PageContent>
</template>

<script>
export default ({
  data() {
    return {
      infoData: [
        { label: '客户名称', value: '', key: 'moveOutManagerName' },
        { label: '所属网格', value: '', key: 'moveOutManagerCode' },
        { label: '所属分行', value: '', key: 'moveOutOrgName', span: 2 },
        { label: '商机执行人工号', value: '', key: 'moveInManagerName' },
        { label: '商机执行人姓名', value: '', key: 'moveInManagerCode', span: 2 },
        { label: '商机类型', value: '', key: 'moveInManagerCode' },
        { label: '业务扩展方式', value: '', key: 'moveInManagerCode' },
        { label: '商机概览', value: '', key: 'moveInManagerCode' },
        { label: '商机发布时间', value: '', key: 'moveInManagerCode' },
        { label: '商机结束时间', value: '', key: 'moveInManagerCode', span: 2 },
        { label: '商机说明', value: '', key: 'moveInManagerCode' },
        { label: '措施及思路', value: '', key: 'moveInManagerCode' },
      ],
      columns: [],
      btnLists: [
        {
          cname: '取消',
          ename: 'cancelHandle',
        },
        {
          cname: '确定',
          ename: 'confirmHandle',
          type: 'primary',
        },
      ]
    }
  },
  methods: {
    clickHandle(type, data) {
      this[type].call(this, data);
    },
    cancelHandle() { },
    confirmHandle() { }
  },
})
</script>

<style lang="scss" scoped>
.btn-wrapper {
  width: 100%;
  display: flex;
  justify-content: flex-end;
}
</style>