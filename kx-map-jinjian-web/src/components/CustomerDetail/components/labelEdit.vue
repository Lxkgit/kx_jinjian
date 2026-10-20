<template>
  <div>
    <span v-for="(i,ind) in checkedList" :key="ind" class="active">{{ i[props.label] }}
      <i v-show="showEdit" class="el-icon-close" @click="handleDel(i)"/>
    </span>
    <el-button v-show="showEdit" type="primary" size="small" @click="findFather">添加</el-button>
    <el-button v-if="editabled" type="warning" size="small" @click="showEdit = !showEdit">编辑 </el-button>
    <el-dialog :visible.sync="dialogFormVisible" :modal-append-to-body="false" width="30%" title="添加">
      <el-cascader
        ref="cascader"
        v-model="cascaderModelArr"
        :options="labelTree"
        :props="props"
        size="small"
        clearable
        @change="handleChange"/>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="editLabel">确 定</el-button>
        <el-button size="small" @click="dialogFormVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'LabelEdit',
  props: {
    value: {
      type: Array,
      retuired: true,
      default: () => {
        return []
      }
    },
    labelTree: {
      type: Array,
      retuired: true,
      default: () => {
        return []
      }
    },
    editabled: { type: Boolean, default: false },
    props: {
      type: Object,
      default: () => {
        return {
          multiple: false,
          checkStrictly: false,
          value: 'labelCode',
          label: 'labelName',
          children: 'childLabels'
        }
      }
    }
  },
  data() {
    return {
      checkedList: [], // 选中的 标签
      showEdit: false,
      dialogFormVisible: false,
      cascaderModelArr: [], // cascader绑定的值
      leaf: {} // 叶子节点对象

    }
  },
  watch: {
    value: {
      handler(val) {
        this.checkedList = val
      },
      immediate: true
    },
    labelTree: {
      handler(val) {
        const obj = {}
        function getLeaf(data) {
          for (let i = 0; i < data.length; i++) {
            const element = data[i]
            if (element.childLabels && element.childLabels.length > 0) {
              getLeaf(element.childLabels)
            } else {
              obj[element.labelCode] = element
            }
          }
        }
        getLeaf(val)
        this.leaf = obj
      },
      immediate: true
    }
  },
  created() {
  },
  methods: {
    handleDel(val) {
      this.checkedList = this.checkedList.filter(item => {
        return item.labelCode !== val.labelCode
      })
      this.$emit('input', this.checkedList)
      this.$emit('addCustomerLabel')
    },
    handleChange(val) {
      if (!this.props.checkStrictly) return
      const { childLabels: secondLevel } = this.labelTree[0]
      const selectLeaf = val.map(item => item[2])
      secondLevel.forEach(item => {
        let flag = false
        if (item.isSingle === '1' && item.childLabels.length > 0) {
          item.childLabels.forEach(v => {
            if (selectLeaf.includes(v.labelCode)) {
              flag = true
              v.disabled = false
            } else {
              v.disabled = true
            }
          })
          if (!flag) {
            item.childLabels.forEach(v => {
              v.disabled = false
            })
          }
        }
      })
      this.cascaderModelArr = selectLeaf.map(item => {
        return [item.slice(0, 3), item.slice(0, 6), item]
      })
    },
    findFather() {
      this.dialogFormVisible = true
      this.cascaderModelArr = this.checkedList.map(item => {
        const code = item.labelCode
        return [code.slice(0, 3), code.slice(0, 6), code]
      })
      if (!this.props.checkStrictly) return
      // 禁用标签初始化
      this.handleChange(this.cascaderModelArr)
    },
    editLabel() {
      this.dialogFormVisible = false
      const data = this.cascaderModelArr.map(item => {
        return this.leaf[item[item.length - 1]]
      })
      this.$emit('input', data)
      this.$emit('addCustomerLabel')
      this.cancle()
    },
    cancle() {
      this.cascaderModelArr = []
      this.dialogFormVisible = false
    }
  }
}
</script>
<style lang="scss" scoped>
span{
  display: inline-block;
    margin-bottom: 8px;
    margin-right: 10px;
    color: #999;
    line-height: 30px;
    border: .5px solid #999;
    border-radius: 4px;
    padding:0 10px  ;
    &.active{
      color: #e9921c;
      border-color: #ea931c;
    }
  }
</style>