<template>
  <div class="rural-product-wrapper">
    <div class="info-wrapper">
      <amountInfo v-for="(item,index) in customerInfo" :key="index" :infoData="item"></amountInfo>
    </div>
    <div class="info-wrapper">
      <amountInfo v-for="(item,index) in amountInfoData" :key="index" :infoData="item"></amountInfo>
    </div>
    <productList :tableData="customerProData"></productList>
    <productList :tableData="badnessProData"></productList>
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
    'customerInfo',
    'amountInfoData',
    'customerProData',
    'badnessProData',
  ]
})
</script>

<style lang="scss" scoped>
.rural-product-wrapper {
  .info-wrapper {
    margin: 20px 0;
  }
}
</style>