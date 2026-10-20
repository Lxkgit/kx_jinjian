<template>
  <div class="dialog-wrapper">
    <productSelect class="select-wrapper" v-model="activeType"></productSelect>
    <component :is="productComp"></component>
  </div>
</template>

<script>
import productSelect from '../productSelect'
import ruralDialog from './components/ruralDialog'
import answernDialog from './components/answernDialog'
import commonDialog from './components/commonDialog'
const product_type = {
  1: ruralDialog,
  2: answernDialog,
  3: commonDialog
}
export default ({
  components: {
    productSelect
  },
  data() {
    return {
      activeType: '1',
    }
  },
  computed: {
    productComp() {
      return product_type[this.activeType]
    }
  },
})
</script>

<style lang="scss" scoped>
.dialog-wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  .select-wrapper {
    width: 400px;
  }
}
</style>