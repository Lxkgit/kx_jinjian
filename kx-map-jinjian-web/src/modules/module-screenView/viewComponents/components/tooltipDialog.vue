<template>
  <div v-show="showDialog" class="tooltip-dialog-wrapper" v-clickoutside="()=>showDialog = false"
       :class="{left:isLeft,right:!isLeft,top:isTop,bottom:!isTop}">
    <div class="dialog-header">
      <i class="el-icon-close header-close" @click="showDialog=false"></i>
    </div>
    <section>
      <slot></slot>
    </section>
  </div>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin';
export default ({
  mixins: [dialogMixin],
  props: {
    isLeft: {
      type: Boolean,
      default: true,
    },
    isTop: {
      type: Boolean,
      default: true,
    },
  }
})
</script>

<style lang="scss" scoped>
.tooltip-dialog-wrapper {
  position: absolute;
  background: rgba($color: #0d191e, $alpha: 0.9);
  padding: 10px;
  border-radius: 6px;
  z-index: 999;
  &.left {
    left: 450px;
  }
  &.right {
    right: 450px;
  }
  &.top {
    top: 90px;
  }
  &.bottom {
    bottom: 0;
  }
  .dialog-header {
    height: 30px;
    position: relative;
    .header-close {
      cursor: pointer;
      position: absolute;
      right: 0;
      color: #32ecee;
    }
  }
}
</style>