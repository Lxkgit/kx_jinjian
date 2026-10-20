<template>
  <el-dialog :visible.sync="dialogVisible" width="50%" :before-close="handleClose" class="collect-class"
    :close-on-click-modal="false" :append-to-body="true">
    <div slot="title" class="dialog-title">
      <span>聚合锚点列表</span>
    </div>
    <i-table ref="table" :data="collectList" align="center" stripe :allColumns="columns" :showPagination="false">
      <template #action="scope">
        <el-button type="text" @click="pointClick(scope.row)">详情</el-button>
      </template>
    </i-table>
  </el-dialog>
</template>

<script>

export default ({
  props: {
    value: {
      type: Boolean,
    },
    collectList: {
      type: Array,
      default: () => []
    }
  },
  dicts: ['custom_kind'],
  data() {
    return {
      dialogVisible: this.value,
      columns: [
        // {
        //   label: '客户类型', prop: 'type', formatter: ({ type }) => {
        //     return this.dict.label.custom_kind[type]
        //   }
        // },
        { label: '网点名称', prop: 'anchorName' },
        { label: '网点地址', prop: 'addressDesc' },
        { label: '操作', slot: true }
      ]
    }
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val
        if (val) {
          this.initData()
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit('input', val)
      }
    }
  },
  methods: {
    initData() {
      this.activeTab = '1'
    },
    handleClose() {
      this.dialogVisible = false
      this.dataPackageInfo = {}
    },
    pointClick(row) {
      this.$emit('pointClick', row)
    },
  },
})
</script>

<style lang="scss" scoped>
.collect-class {
  ::v-deep .el-dialog__header {
    background: #7735a8;

    .active {
      background-color: #5b1c8a;
    }
  }
}

.dialog-title {
  span {
    color: #fff;
    cursor: pointer;

    font-size: 16px;
  }
}

::v-deep .el-dialog__body {
  padding: 15px;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
</style>