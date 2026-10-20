<template>
  <IDialog title="操作日志详情" v-model="showDialog" width="45%" @handleClose="handleClose"
           :showConfirm="false">
    <el-descriptions :column="2">
      <el-descriptions-item v-for="(item,index) in logDetailData" :key="index" :label="item.label"
                            :span="item.span">{{ item.value }}</el-descriptions-item>
    </el-descriptions>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
export default ({
  mixins: [dialogMixin],
  props: {
    detailInfo: {
      type: Object
    },
  },
  data() {
    return {
      logDetailData: [
        { label: '操作模块', value: '', prop: 'operateModule', span: 1 },
        { label: '请求地址', value: '', prop: 'requestAddress', span: 1 },
        { label: '登录信息', value: '', prop: 'loginInfo', span: 1 },
        { label: '请求方式', value: '', prop: 'requestType', span: 1 },
        { label: '操作方法', value: '', prop: 'operateMethod', span: 2 },
        { label: '请求参数', value: '', prop: 'requestParam', span: 2 },
        { label: '返回参数', value: '', prop: 'responseParam', span: 2 },
        { label: '操作状态', value: '', prop: 'statusName', span: 1 },
        { label: '操作时间', value: '', prop: 'createTime', span: 1 },
      ],
    }
  },
  methods: {
    initData() {
      this.logDetailData.forEach(item => {
        item.value = this.detailInfo[item.prop]
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>