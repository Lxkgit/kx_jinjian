<template>
  <ul class="cascade-checkbox">
    <li v-for="(item,index) in infoArray" :key="index">
      <el-checkbox v-model="item.flag" :checked="item.flag" class="all-checkbox" :label="item.value"
                   :indeterminate="itemIndeterminate(item)"
                   @change="(val) => handleCheckAll(val, item)">
        {{ item.label }}
      </el-checkbox>
      <el-checkbox-group v-model="checkedItems" class="children-check-wrapper"
                         @change="handleChanges(item)">
        <el-checkbox v-for="items in item.children" :key="items.label" :label="items.value">
          {{ items.label }}
        </el-checkbox>
      </el-checkbox-group>
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    value: {
      type: Array,
      default: () => [],
    },
    infoArray: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      checkedItems: this.value,
      isIndeterminate: false,
    }
  },
  watch: {
    value: {
      handler(val) {
        this.checkedItems = val
      },
      deep: true,
    },
    checkedItems: {
      handler(val) {
        this.$emit('input', val)
      },
      deep: true,
    },
  },
  mounted() {
  },
  methods: {
    initData() {
      this.checkedItems = []
    },
    initCheckAll(value) {
      this.infoArray.forEach(item => {
        if (item.value === value) {
          item.flag = true
        } else {
          item.flag = false
        }
      })
    },
    handleCheckAll(val, item) {
      if (val) {
        this.infoArray.forEach(el => {
          if (el.value !== item.value) {
            el.flag = false
          }
        })
      }
      const next = item.children.map(item => item.value)
      this.checkedItems = val ? next : []
    },
    itemIndeterminate(item) {
      const infoLists = item.children.map(ite => ite.value)
      return (
        infoLists.some((v) => this.checkedItems.indexOf(v) > -1) &&
        !infoLists.every((v) => this.checkedItems.indexOf(v) > -1)
      )
    },
    handleChanges(val) {
      const infoLists = val.children.map(ite => ite.value)
      this.checkedItems = this.checkedItems.filter(item => infoLists.includes(item))
      if (!val.flag) {
        this.infoArray.forEach(el => {
          if (el.value !== val.value) {
            el.flag = false
          }
        })
        val.flag = true
      }
      if (this.checkedItems.length === 0) {
        val.flag = false
      }
    },
  },
})
</script>

<style lang="scss" scoped>
.cascade-checkbox {
  margin-left: 29px;
  > li {
    margin: 15px 0;
    .all-checkbox {
      ::v-deep .el-checkbox__label {
        background: transparent;
      }
    }
    .children-check-wrapper {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      ::v-deep .el-checkbox__label {
        color: #fff;
        background: transparent;
      }
    }
  }
}
::v-deep .el-checkbox__input.is-indeterminate .el-checkbox__inner::before {
  content: "";
  position: absolute;
  display: block;
  background-color: #fff;
  height: 4px;
  left: 0;
  right: 0;
  top: 50%;
  transform: translateY(-50%) scale(0.5);
}
</style>