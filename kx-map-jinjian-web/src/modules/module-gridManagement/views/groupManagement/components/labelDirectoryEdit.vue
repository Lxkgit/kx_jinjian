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
    label: '上级分组',
    key: 'parentName',
    value: '',
    type: 'text',
    disabled: true,
  },
  {
    label: '标签分组',
    key: 'labelGroupName',
    value: '',
    type: 'text',
    rules: [
      { required: true, message: '标签分组不能为空' }
    ]
  },
  {
    label: '是否多选',
    key: 'isMultiple',
    value: 0,
    type: 'switch',
    activeValue: 1,
    inactiveValue: 0
  },
]
const update_columns = [
  {
    label: '标签分组',
    key: 'labelGroupName',
    value: '',
    type: 'text',
    rules: [
      { required: true, message: '标签分组不能为空' }
    ]
  },
  {
    label: '是否多选',
    key: 'isMultiple',
    value: 0,
    type: 'switch',
    activeValue: 1,
    inactiveValue: 0
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
          parentName: this.editInfo.labelGroupName,
          isMultiple: 0
        }
      } else {
        info = {
          labelGroupName: this.editInfo.labelGroupName,
          isMultiple: this.editInfo.isMultiple
        }
      }
      this.$nextTick(() => {
        this.$refs.myForm.setForm({ ...info, })
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
          const res = await service.labelDirectoryAdd(param)
          this.$message.success(`${TITLE_TYPE[this.status]}成功!`);
          this.handleClose()
          this.$parent.labelDirectory()
        } catch {

        }
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>