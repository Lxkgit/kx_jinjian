<template>
  <div class="answern-product-wrapper">
    <div class="info-wrapper">
      <amountInfo v-for="(item,index) in answernRuralAssets" :key="index" :infoData="item">
      </amountInfo>
    </div>
    <productList :tableData="answernBadProData"></productList>
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
    'answernRuralAssets',
    'answernBadProData'
  ]
})
</script>

<style lang="scss" scoped>
.answern-product-wrapper {
  .info-wrapper {
    margin: 20px 0;
  }
}
</style>