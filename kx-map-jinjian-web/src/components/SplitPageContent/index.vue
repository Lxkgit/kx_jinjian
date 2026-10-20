<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="leftSpan" :xs="24">
        <div :class="['operate-wrapper',hasLeftbg&&'operate-wrapper-bg']">
          <slot name="operate"></slot>
        </div>

      </el-col>
      <el-col :span="rightSpan" :xs="24">
        <!-- 过滤器 -->
        <slot name="filter"></slot>
        <!-- 按钮 -->
        <slot name="btn"></slot>
        <slot></slot>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default ({
  name: 'SplitPageContent',
  props: {
    leftSpan: {
      type: Number,
      default: 5,
    },
    rightSpan: {
      type: Number,
      default: 19,
    },
    hasLeftbg: {
      type: Boolean,
      default: true,
    }
  },
})
</script>

<style lang="scss" scoped>
.app-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  .operate-wrapper {
    width: 100%;
  }
  .operate-wrapper-bg {
    // background: #F6F6F6;
    border-radius: 4px;
    border: 1px solid #eee;
    padding: 10px 5px;
    height: calc(100vh - 180px);
  }
}
</style>