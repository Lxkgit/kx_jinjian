<template>
  <div class="back-part-wrapper">
    <img :src="require('@/modules/module-screenView/assets/image/back.png')" @click="backHandle"
      v-if="orgList.length > 1 || activityDetail" alt="">
  </div>
</template>

<script>
export default ({
  computed: {
    orgList() {
      return this.$store.getters['screenView/orgList']
    },
    activityDetail() {
      return this.$store.getters['screenView/activityDetail']
    },
  },
  methods: {
    backHandle() {
      //营销活动详情 退出
      if (this.activityDetail) {
        this.$store.commit('screenView/SET_ACTIVITY_DETAIL', null)
        return
      }
      const orgList = JSON.parse(JSON.stringify(this.orgList))
      let backObj = orgList.pop()
      const [lastObj] = orgList.slice(-1)
      console.log(lastObj, 54)
      if (lastObj.type === 'boundary' && lastObj.code === backObj.code) {
        this.$store.commit('screenView/SET_ORG_CODE', lastObj.code)
        this.$store.commit('screenView/SET_ORG_LIST', orgList)
        this.$EventBus.$emit('mapEvent', ['orgDataFun'])
        this.$EventBus.$emit('mapView')
      } else if (lastObj.type === 'boundary') {
        this.$store.commit('screenView/SET_ORG_CODE', lastObj.code)
        this.$store.commit('screenView/SET_ORG_LIST', orgList)
      } else {
        this.$store.commit('screenView/SET_ORG_LIST', orgList)
        this.backHandle()
      }
    }
  },
})
</script>

<style lang="scss" scoped>
.back-part-wrapper {
  width: 36px;
  height: 36px;
  cursor: pointer;

  img {
    width: 100%;
  }
}
</style>