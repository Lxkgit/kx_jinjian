<template>
  <div class="label-wrapper">
    <div class="label-list-wrapper" v-for="item in selectLabelOptions" :key="item.id">
      <label>{{ item.labelGroupName }}</label>
      <div class="label-item-wrapper">
        <el-tag v-for="(el,index) in item.childLabels" :key="el.id" size="mini"
                :effect="ifSelected(el)? 'dark' : 'plain'" @click="clickLabel(el,item)">
          {{ el.labelName }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script>
import service from '../../api';
export default ({
  name: 'LabelSelect',
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
    disabled: {
      type: Boolean,
    },
  },
  data() {
    return {
      labelSelect: this.value,
      selectLabelOptions: [],
    }
  },
  watch: {
    value: {
      handler(v) {
        this.labelSelect = v
      },
      deep: true,
    },
    labelSelect: {
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
      this.selectLabelOptions = []
      const tags = this.tagOptions.map(item => Number(item.label))
      for (let item of data) {
        if (tags.includes(item.id)) {
          this.selectLabelOptions.push(item)
        }
      }
    },
    // 查找匹配项
    checkLabel(list, item, key) {
      for (let el of list) {
        if (el[key] === item[key]) {
          return true
        }
      }
      return false
    },
    ifSelected({ id }) {
      return this.labelSelect.includes(id)
    },
    clickLabel(item, parent) {
      if (this.disabled) {
        return
      }
      const { childLabels, isMultiple } = parent
      const checkIds = childLabels.map(el => el.id)
      for (let i = 0; i < this.labelSelect.length; i++) {
        if (this.labelSelect[i] === item.id) {
          this.labelSelect.splice(i, 1)
          return
        }
        if (isMultiple == '0' && checkIds.includes(this.labelSelect[i])) {
          this.labelSelect.splice(i, 1)
        }
      }
      const { id, labelName } = item
      this.labelSelect.push(id)
    },
  },

})
</script>

<style lang="scss" scoped>
.label-wrapper {
  width: 100%;
  min-height: 32px;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  padding: 2px 4px;
  box-sizing: border-box;
  .label-list-wrapper {
    display: flex;
    > label {
      white-space: nowrap;
      margin-right: 10px;
    }
  }
  ::v-deep .el-tag {
    margin-right: 4px;
    cursor: pointer;
  }
}
</style>