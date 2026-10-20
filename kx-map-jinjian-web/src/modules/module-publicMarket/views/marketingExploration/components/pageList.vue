<template>
  <div class="page-list-wrapper">
    <div class="total-num">共{{ total }}条</div>
    <el-empty :image-size="100" v-if="tableData.length===0"></el-empty>
    <ul class="list-wrapper" v-else>
      <li v-for="(item,index) in tableData" :key="index">
        <slot :row="item" :index="index" />
      </li>
    </ul>
    <el-pagination small @size-change="handleSizeChange" @current-change="handleCurrentChange"
                   :current-page="pageNum" :pager-count="5" :page-sizes="[5,10,20,50,100]"
                   :page-size="pageSize" layout="sizes, prev, pager, next" :total="total">
    </el-pagination>
  </div>
</template>

<script>
export default ({
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    total: {
      type: Number,
      default: 0
    },
    pageSize: {
      type: Number,
      default: 10,
    },
    pageNum: {
      type: Number,
      default: 1,
    }
  },
  methods: {
    handleSizeChange(val) {
      this.$emit('handleSizeChange', val)
    },
    handleCurrentChange(val) {
      this.$emit('handleCurrentChange', val)
    }
  },
})
</script>

<style lang="scss" scoped>
.page-list-wrapper {
  width: 100%;
  // position: absolute;
  // left: 0;
  // top: 95px;
  // right: 0;
  // bottom: 0;
  // overflow-y: auto;
  // overflow-x: hidden;
  .total-num {
    padding: 6px;
    background: #f4f5fc;
  }
  .list-wrapper {
    padding: 10px 0;
  }
}
::v-deep .el-pagination {
  text-align: right;
  background: #fff;
  margin-bottom: 6px;
}
</style>