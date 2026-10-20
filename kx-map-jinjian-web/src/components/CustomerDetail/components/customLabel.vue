<template>
  <div style="font-size: 16px">
    <div class="label-box">
      <div class="label">系统标签:</div>
      <div>
        <labelEdit v-model="systemLabel" />
      </div>
    </div>
    <div class="label-box">
      <div class="label">网格标签:</div>
      <div>
        <labelEdit v-model="networkLabelValue" :label-tree="labelTree" :editabled="editabled" :props="labelEditProps" @addCustomerLabel="addCustomerLabel" />
      </div>
    </div>
  </div>
</template>

<script>
import labelEdit from './labelEdit'
import gridLabel from '../mock/gridLabel.json'
// import {
//   getGridLabel
// } from '@/api/meshMap/meshCustomer'
export default {
  name: 'CustomLabel',
  components: { labelEdit },
  props: {
    editabled: {
      type: Boolean,
      default: true
    },
    systemLabel: {
      type: Array,
      default: () => {
        return []
      }
    },
    networkLabel: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      networkLabelValue: [],
      list: [
        { labelName: '产品需求' },
        { labelName: '产品规划' }
      ],
      labelEditProps: {
        multiple: true,
        checkStrictly: true,
        value: 'labelCode',
        label: 'labelName',
        children: 'childLabels'
      },
      labelTree: []
    }
  },
  watch: {
    networkLabel: {
      handler(val) {
        this.networkLabelValue = val
      }
    }
  },
  created() {
    this.getGridLabel()
  },
  methods: {
    // 获取标签树
    getGridLabel() {
      let data = gridLabel
      function loop(arr) {
        for (let i = 0; i < arr.length; i++) {
          const element = arr[i]
          element.disabled = element.level !== '3' ? true : null
          element.childLabels && element.childLabels.length > 0 && loop(element.childLabels)
        }
      }
      loop(data)
      this.labelTree = data

    },
    addCustomerLabel() {
      this.$emit('addCustomerLabel', this.networkLabelValue)
    }
  }
}
</script>
  <style lang="scss" scoped>
.label-box {
  margin: 5px;
  display: flex;
}
.label {
  min-width: 90px;
  font-size: 16px;
  line-height: 30px;
  color: #333333;
}
span {
  display: inline-block;
  margin-bottom: 8px;
  margin-right: 10px;
  color: #999;
  line-height: 30px;
  border: 0.5px solid #999;
  border-radius: 4px;
  padding: 0 10px;
  &.active {
    color: #e9921c;
    border-color: #ea931c;
  }
}
</style>