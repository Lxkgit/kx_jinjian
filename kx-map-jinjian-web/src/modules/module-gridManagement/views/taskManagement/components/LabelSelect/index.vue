<template>
  <div class="label-wrapper">
    <el-tag v-for="(item,index) in newTagLists" :key="index" size="mini"
            :effect="ifSelected(item)? 'dark' : 'plain'" @click="clickLabel(item)">
      {{ item.labelName }}
    </el-tag>
  </div>
</template>

<script>
// import {
//   getTypeLabelSelect,
// } from "@/api/taskmanagement/index.js";
export default ({
  name: 'LabelSelect',
  props: {
    value: {
      type: Array,
      default: () => [],
    },
    tagLists: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      labelSelect: this.value,
      newTagLists: [],
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
    this.getNewTags()
  },
  methods: {
    async getNewTags() {
      const res = await getTypeLabelSelect();
      res.data.forEach(item => {
        this.tagLists.forEach(tag => {
          if (tag[0] === item.id) {
            let [t] = item.children.filter(el => {
              return el.id === tag[1]
            })
            this.newTagLists.push(t)
          }
        })
      })
    },
    // 查找匹配项
    checkLabel(list, item) {
      let res = -1
      for (let i = 0; i < list.length; i++) {
        if (list[i] === item) {
          res = i
          return res
        } else {
          res = -1
        }
      }
      return res
    },
    ifSelected(item) {
      const index = this.checkLabel(this.labelSelect, item)
      console.log(index)
      if (index > -1) {
        return true
      } else {
        return false
      }
    },
    clickLabel(item) {
      console.log(item)
      for (let i = 0; i < this.labelSelect.length; i++) {
        if (this.labelSelect[i] === item) {
          this.labelSelect.splice(i, 1)
          return
        }
      }
      this.labelSelect.push(item)
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
  ::v-deep .el-tag {
    margin-right: 4px;
    cursor: pointer;
  }
}
</style>