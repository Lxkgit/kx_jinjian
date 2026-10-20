<template>
  <div v-auto-scale class="main-content">
    <div class="main-content-wrapper">
      <screenHead :left-show="leftShow" :right-show="rightShow" @turnSwitch="turnSwitch" />
      <screenLeft :left-show="leftShow" />
      <screenRight :right-show="rightShow" />
    </div>
  </div>
</template>

<script>
import { autoScaleFun } from '../../directive/autoScale'
import screenHead from '@/modules/module-screenView/cell/screenHead.vue'
import screenLeft from './cell/screenLeft'
import screenRight from './cell/screenRight'
export default ({
  directives: {
    autoScale: {
      inserted: autoScaleFun,
    },
  },
  components: {
    screenHead,
    screenLeft,
    screenRight,
  },
  data() {
    return {
      leftShow: false,
      rightShow: false,
    }
  },
  watch: {
    leftShow: {
      handler(val) {
        this.$store.commit('screenView/SET_LEFT_HIDE', val)

      },
      immediate: true
    },
    rightShow: {
      handler(val) {
        this.$store.commit('screenView/SET_RIGHT_HIDE', val)
      },
      immediate: true
    },
  },
  mounted() {

  },
  methods: {

    // 两侧收缩
    turnSwitch(position) {
      this[`${position}Show`] = !this[`${position}Show`]
    },
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