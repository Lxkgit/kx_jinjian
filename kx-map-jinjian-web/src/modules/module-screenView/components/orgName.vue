<template>
  <div class="org-name-wrapper">

    <img v-show="orgName" src="../assets/image/position.png" alt="">
    <!-- <span>人民银行{{ orgName }}分行</span> -->
    <span>中国人民银行浙江省分行</span>
  </div>
</template>

<script>
export default ({
  computed: {
    orgName() {
      return this.$store.getters['screenView/orgInfo']?.name ?? ''
    }
  },
})
</script>

<style lang="scss" scoped>
.org-name-wrapper {
  position: absolute;
  font-size: 20px;
  color: #fff;
  left: 90px;
  top: 20px;
  display: flex;
  align-items: center;

  img {
    margin-right: 10px;
    width: 20px;
    height: 20px;
  }

  >span {
    display: inline-block;
    white-space: nowrap;
    width: 250px;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}
</style>