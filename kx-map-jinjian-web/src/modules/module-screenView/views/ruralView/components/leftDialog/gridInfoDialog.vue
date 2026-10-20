<template>
  <dialogBox>
    <div slot="left">
      <ftpBox :value="baseFTP" />
      <introBox v-show="orgLevel && orgLevel > 3" :list="introBoxData" />
      <gridBox v-show="!orgLevel || orgLevel <= 3" :list="gridBoxData" />
      <gridDetailInfo :list="gridDetailData" />
    </div>
    <div slot="right">
      <customList :defaultValue="customListData"></customList>
    </div>
  </dialogBox>
</template>

<script>
import dialogBox from './dialogBox'
import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import introBox from "@/modules/module-screenView/viewComponents/components/introBox";
import gridBox from "@/modules/module-screenView/viewComponents/components/gridBox";
import customNum from "@/modules/module-screenView/viewComponents/components/customNum";
import gridDetailInfo from "@/modules/module-screenView/viewComponents/components/gridDetailInfo";
import customList from "@/modules/module-screenView/viewComponents/components/tooltipContent/customList"
export default ({
  components: {
    dialogBox,
    ftpBox,
    introBox,
    gridBox,
    customNum,
    gridDetailInfo,
    customList
  },
  inject: [
    'baseFTP',
    'introBoxData',
    'gridBoxData',
    'gridDetailData',
    'customListData'
  ],
  computed: {
    orgLevel() {
      return this.$store.getters["screenView/orgInfo"]?.level;
    },
  },
})
</script>

<style lang="scss" scoped>
</style>