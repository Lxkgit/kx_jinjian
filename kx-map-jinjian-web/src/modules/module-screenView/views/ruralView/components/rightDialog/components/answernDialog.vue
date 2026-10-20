<template>
  <dialogBox>
    <template slot="left">
      <amountInfo v-for="(item,index) in answernRuralAssets" :key="index" :infoData="item">
      </amountInfo>
    </template>
    <template slot="right">
      <productList :tableData="answernBadProData"></productList>
    </template>
  </dialogBox>
</template>

<script>
import dialogBox from './dialogBox'
import amountInfo from "@/modules/module-screenView/viewComponents/components/amountInfo";
import productList from "@/modules/module-screenView/viewComponents/components/productList";
export default ({
  components: {
    dialogBox,
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
</style>