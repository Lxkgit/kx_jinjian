<template>
  <PageContent>
    <el-tabs v-model="activeName">
      <el-tab-pane label="辖区变动" name="0">
      </el-tab-pane>
      <el-tab-pane label="网格员变动" name="1"></el-tab-pane>
      <el-tab-pane label="客户调动" name="2"></el-tab-pane>
    </el-tabs>
    <logList :activeName="activeName"></logList>
  </PageContent>
</template>

<script>
import logList from '../cell/logList';
export default ({
  components: {
    logList
  },
  data() {
    return {
      activeName: '0',
    }
  },
})
</script>

<style lang="scss" scoped>
</style>