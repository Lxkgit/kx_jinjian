<template>
  <PageContent>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" :showPagination="false">
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
    </i-table>
  </PageContent>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
export default ({
  mixins: [pageMixin],
  data() {
    return {
      tableBtn: [
        {
          cname: '编辑',
          ename: 'editHandle',
        },
      ],
      columns: [
        { label: '商机类型', prop: 'modelName' },
        { label: '类型备注', prop: 'modelType' },
        { label: '创建人', prop: 'createTime' },
        { label: '状态', prop: 'createTime' },
        { label: '创建时间', prop: 'createTime' },
        { label: '更新时间', prop: 'createTime' },
        { label: '操作', slot: true },
      ],
    }
  },

})
</script>
<style lang="scss" scoped>
</style>