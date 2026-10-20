<template>
  <div class="label-select-wrapper">
    <el-cascader :disabled="disabled" class="label-select-wrapper" v-if="isMust=='0'"
                 v-model="selectData" :options="options" :props="props" placeholder="请选择标签"
                 @change="changeHandle" :show-all-levels="false" />
    <LabelTag :disabled="disabled" v-else v-model="selectData" :tagType="tagType"
              :tagOptions="tagOptions"></LabelTag>
  </div>
</template>

<script>
import service from '../api';
import LabelTag from './components/LabelTag';
export default ({
  components: {
    LabelTag
  },
  props: {
    value: {
      type: Array,
      default: () => [],
    },
    tagType: {
      type: String,
    },
    tagOptions: {
      type: Array,
      default: () => [],
    },
    isMust: {
      type: String,
    },
    disabled: {
      type: Boolean,
    },
  },
  data() {
    return {
      selectData: this.value,
      options: [],
      props: {
        value: 'id',
        label: 'labelName',
        children: 'childLabels',
        emitPath: false,
        multiple: true,
        checkStrictly: true
      }
    }
  },
  watch: {
    value: {
      handler(v) {
        this.selectData = v
      },
      deep: true,
    },
    selectData: {
      handler(v) {
        this.$emit('input', v)
      },
    },
  },
  mounted() {
    this.selectChildLabel()
  },
  methods: {
    async selectChildLabel() {
      const param = {
        id: this.tagType
      }
      const { data } = await service.selectChildLabel(param);
      data.forEach(item => {
        item.labelName = item.labelGroupName
        item.disabled = true
      })
      this.options = data
    },
    changeHandle(val) {
      const newOptions = JSON.parse(JSON.stringify(this.options))
      for (let item of newOptions) {
        if (item.isMultiple == 0) {
          let flag = false
          for (let el of item.childLabels) {
            if (val.includes(el.id)) {
              flag = true
              el.disabled = false
            } else {
              el.disabled = true
            }
          }
          if (!flag) {
            item.childLabels.map(item => item.disabled = false)
          }
        }
      }

      this.$nextTick(() => {
        this.options = newOptions
      })
    }
  },
})
</script>

<style lang="scss" scoped>
.label-select-wrapper {
  width: 100%;
}
</style>
