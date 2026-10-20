<template>
  <div class="common-product-wrapper">
    <div class="info-wrapper">
      <amountInfo v-for="(item,index) in commonRuralAssets" :key="index" :infoData="item">
      </amountInfo>
    </div>
    <productList :tableData="commonBadProData"></productList>
  </div>
</template>

<script>
import amountInfo from "@/modules/module-screenView/viewComponents/components/amountInfo";
import productList from "@/modules/module-screenView/viewComponents/components/productList";
export default ({
  components: {
    amountInfo,
    productList
  },
  inject: [
    'commonRuralAssets',
    'commonBadProData'
  ]
})
</script>

<style lang="scss" scoped>
.common-product-wrapper {
  .info-wrapper {
    margin: 20px 0;
  }
}
</style>