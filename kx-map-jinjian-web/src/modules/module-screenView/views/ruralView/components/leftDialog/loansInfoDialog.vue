<template>
  <dialogBox>
    <div slot="left">
      <ftpBox title="贷款FTP" :value="loansFTP" />
      <businessList :dataList="dataList"></businessList>
      <badnessList :tableData="tableData"></badnessList>
      <progressInfo :interstData="progressData"></progressInfo>
    </div>
    <div slot="right">
      <loansInfo v-for="(item,index) in loansInfoData" :key="index" :loansData="item"></loansInfo>
    </div>
  </dialogBox>
</template>

<script>
import dialogBox from './dialogBox'
import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import businessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/businessList"
import badnessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/badnessList";
import progressInfo from "@/modules/module-screenView/viewComponents/components/progressInfo";
import loansInfo from "@/modules/module-screenView/viewComponents/components/loansInfo";
export default ({
  components: {
    dialogBox,
    ftpBox,
    businessList,
    badnessList,
    progressInfo,
    loansInfo
  },
  inject: [
    'loansFTP',
    'dataList',
    'tableData',
    'progressData',
    'loansInfoData'
  ]
})
</script>

<style lang="scss" scoped>
</style>