<template>
  <IDialog :title="title" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="80px" />
  </IDialog>
</template>

<script>
import service from '../api';
import dialogMixin from '@/mixins/dialogMixin.vue';
const TITLE_TYPE = {
  add: '新增',
  update: '编辑'
}
const add_columns = [
  {
    label: '上级分类',
    key: 'parentName',
    value: '',
    type: 'text',
    disabled: true,
  },
  {
    label: '客群类型',
    key: 'categoryName',
    value: '',
    type: 'text',
  },
]
const update_columns = [
  {
    label: '客群类型',
    key: 'categoryName',
    value: '',
    type: 'text',
  },
]
const COLUMNS_LIST = {
  add: add_columns,
  update: update_columns
}
export default ({
  mixins: [dialogMixin],
  props: {
    status: {
      type: String,
      default: 'add'
    },
    editInfo: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
    }
  },
  computed: {
    columns() {
      return COLUMNS_LIST[this.status]
    },
    title() {
      return TITLE_TYPE[this.status]
    }
  },
  methods: {
    initData() {
      let info = {}
      if (this.status === 'add') {
        info = {
          parentName: this.editInfo.categoryName,
        }
      } else {
        info = {
          categoryName: this.editInfo.categoryName,
        }
      }
      this.$nextTick(() => {
        this.$refs.myForm.setForm(info)
      })
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(async formInfo => {
        try {
          let param = {}
          if (this.status === 'add') {
            param = {
              ...formInfo,
              parentId: this.editInfo.id
            }
          } else {
            param = {
              ...formInfo,
              id: this.editInfo.id,
              parentId: this.editInfo.parentId,
            }
          }
          const res = await service.addOrEditCategory(param)
          this.$message.success(`${TITLE_TYPE[this.status]}成功!`);
          this.handleClose()
          this.$parent.customerGroupList()
        } catch {

        }
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>