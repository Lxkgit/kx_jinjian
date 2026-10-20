<template>
  <div>
    <PageContent>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="建档客户" name="1"></el-tab-pane>
        <el-tab-pane label="临时建档客户" name="0"></el-tab-pane>
      </el-tabs>
      <component :is="componentId" :activeName="activeName"></component>
    </PageContent>
  </div>
</template>

<script>
import fileManage from './cell/fileManage'
import tempFileManage from './cell/tempFileManage'
const TYPE = {
  1: fileManage,
  0: tempFileManage
}
export default ({
  name: 'FileListManage',
  data() {
    return {
      activeName: '1',
      componentId: fileManage
    }
  },
  methods: {
    handleClick() {
      this.componentId = TYPE[this.activeName]
    }
  },
})
</script>

<style lang="scss" scoped>
</style>