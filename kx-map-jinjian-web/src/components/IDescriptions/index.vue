<template>
  <div class="descriptions-wrapper">
    <el-descriptions :title="title" :column="column">
      <el-descriptions-item v-for="(item,index) in infoData.filter(i => !getFunOrValue(i.hidden))"
                            :span="item.span" :key="index" :label="item.label">
        <span v-if="!item.slot">{{ item.value }}</span>
        <slot v-else :name="item.slotName" :data="item"></slot>
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default ({
  name: 'IDescriptions',
  props: {
    title: {
      type: String,
    },
    infoData: {
      type: Array,
      default: () => []
    },
    column: {
      type: Number
    }
  },
  methods: {
    getFunOrValue(fun) {
      if (typeof fun === 'function') {
        return fun.call()
      } else {
        return fun
      }
    },
  },
})
</script>

<style lang="scss" scoped>
</style>