<template>
  <div class="table-list-wrapper">
    <div class="list-header-wrapper" :style="{background}">
      <span v-for="(ele,index) in header" v-show="ele.flex>0" :key="index"
            :style="`flex:${ele.flex};`">{{ ele.label }}</span>
    </div>
    <div class="list-content-wrapper">
      <div class="list-item" v-for="(item,index) in source" :key="index">
        <div v-for="(el, i) in header" v-show="el.flex>0" :key="i" :style="`flex:${el.flex};`">
          <span v-if="!el.slot">{{ item[el.prop] }}</span>
          <slot v-else :name="el.prop" :data="item[el.prop]"></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    header: {
      type: Array,
    },
    source: {
      type: Array,
    },
    background: {
      type: String
    }
  },
})
</script>

<style lang="scss" scoped>
.table-list-wrapper {
  padding: 18px;
  .list-header-wrapper {
    display: flex;
    background: #f6eee6;
    line-height: 52px;
    text-align: center;
  }
  .list-content-wrapper {
    .list-item {
      line-height: 52px;
      text-align: center;
      display: flex;
      &:nth-child(2n) {
        background: #ececec;
      }
    }
  }
}
</style>