<template>
  <dialogBox>
    <template slot="left">
      <amountInfo v-for="(item,index) in customerInfo" :key="index" :infoData="item">
      </amountInfo>
      <productList :tableData="customerProData"></productList>
    </template>
    <template slot="right">
      <amountInfo v-for="(item,index) in amountInfoData" :key="index" :infoData="item">
      </amountInfo>
      <productList :tableData="badnessProData"></productList>
    </template>
  </dialogBox>
</template>

<script>
import dialogBox from './dialogBox'
import amountInfo from "@/modules/module-screenView/viewComponents/components/amountInfo";
import productList from "@/modules/module-screenView/viewComponents/components/productList";
export default ({
  components: {
    amountInfo,
    productList,
    dialogBox
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
</style>