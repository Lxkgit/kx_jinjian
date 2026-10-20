<template>
  <div v-auto-scale class="main-content">
    <div class="main-content-wrapper">
      <ScreenHead :left-show="leftShow" :right-show="rightShow" @turnSwitch="turnSwitch" />
      <ScreenLeft :left-show="leftShow">
        <leftView></leftView>
      </ScreenLeft>
      <ScreenRight :right-show="rightShow">
        <rightView></rightView>
      </ScreenRight>
    </div>
  </div>
</template>

<script>
import leftView from './cell/leftView';
import rightView from './cell/rightView';
import viewMixin from '@/modules/module-screenView/mixins/viewMixin.vue'
export default ({
  mixins: [viewMixin],
  components: {
    leftView,
    rightView
  },
})
</script>

<style lang="scss" scoped>
.main-content {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 9;
  pointer-events: none;
  .main-content-wrapper {
    position: relative;
    width: 100%;
    height: 100%;
  }
}
</style>