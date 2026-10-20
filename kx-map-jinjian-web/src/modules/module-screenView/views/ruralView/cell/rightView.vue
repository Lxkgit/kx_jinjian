<template>
  <div class="right-view-wrapper">
    <titleBox height="420px" title="农村资产业务" annotation="(金额:万元 户数:个)">
      <!-- <viewAll slot="operate" @click="handleTopViewAll"></viewAll> -->
      <amountInfo v-for="(item,index) in ruralAssets" :key="index" :infoData="item"></amountInfo>
      <productList :tableData="badProData"></productList>
    </titleBox>
    <titleBox height="520px" title="乡贷产品模块" annotation="(金额:万元 户数:个)">
      <viewAll slot="operate" @click="handleBottomViewAll"></viewAll>
      <productSelect v-model="activeType"></productSelect>
      <component :is="productComp"></component>
    </titleBox>
    <!-- 查看全部 -->
    <viewDialog v-model="showDialog" :title="dialogTitle" :annotation="dialogAnnotation">
      <component :is="dialogComponentId"></component>
    </viewDialog>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import viewAll from "@/modules/module-screenView/viewComponents/components/viewAll";
import productSelect from '../components/productSelect'
import viewDialog from "@/modules/module-screenView/viewComponents/components/viewDialog";

import amountInfo from "@/modules/module-screenView/viewComponents/components/amountInfo";
import productList from "@/modules/module-screenView/viewComponents/components/productList";

import ruralProduct from '../components/ruralProduct'
import answernProduct from '../components/answernProduct'
import commonProduct from '../components/commonProduct'
import productInfoDialog from '../components/rightDialog/productInfoDialog'

import {
  ruralAssets,
  badProData,
  customerInfo,
  amountInfoData,
  customerProData,
  badnessProData,
  answernRuralAssets,
  answernBadProData,
  commonRuralAssets,
  commonBadProData
} from '../config/rightConfig'
const product_type = {
  1: ruralProduct,
  2: answernProduct,
  3: commonProduct
}
export default ({
  components: {
    titleBox,
    viewAll,
    productSelect,
    viewDialog,

    amountInfo,
    productList
  },
  provide() {
    return {
      ...this
    }
  },
  data() {
    return {
      activeType: '1',
      ruralAssets,
      badProData,
      showDialog: false,
      dialogTitle: '',
      dialogAnnotation: '',
      dialogComponentId: null,

      customerInfo,
      amountInfoData,
      customerProData,
      badnessProData,
      answernRuralAssets,
      answernBadProData,
      commonRuralAssets,
      commonBadProData
    }
  },
  computed: {
    productComp() {
      return product_type[this.activeType]
    }
  },
  mounted() {

  },
  methods: {
    handleTopViewAll() { },
    handleBottomViewAll() {
      this.dialogTitle = '乡村产品模块'
      this.dialogAnnotation = '(金额:万元 户数:个)'
      this.dialogComponentId = productInfoDialog
      this.showDialog = true
    },
  },
})
</script>

<style lang="scss" scoped>
.right-view-wrapper {
  position: relative;
}
</style>