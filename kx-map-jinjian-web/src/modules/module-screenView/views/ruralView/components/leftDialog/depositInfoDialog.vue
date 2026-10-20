<template>
  <div class="dialog-wrapper">
    <ftpBox title="存款FTP" :value="depositFTP" />
    <div class="deposit-wrapper">
      <depositCard class="deposit-item" v-for="item in depositCardData" :key="item.label"
                   :dataItem="item"></depositCard>
    </div>
  </div>
</template>

<script>
import dialogBox from './dialogBox'
import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import depositCard from "@/modules/module-screenView/viewComponents/components/depositCard";
export default ({
  components: {
    dialogBox,
    ftpBox,
    depositCard
  },
  inject: [
    'depositFTP',
    'depositCardData'
  ]
})
</script>

<style lang="scss" scoped>
.deposit-wrapper {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  width: 100%;
  padding: 0 20px;
  .deposit-item {
    width: 23%;
    margin-bottom: 20px;
  }
}
</style>