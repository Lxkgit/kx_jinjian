<template>
  <div class="left-view-wrapper">
    <titleBox height="310px" title="网格概况" annotation="(金额:万元)">
      <viewAll slot="operate" @click="handleTopViewAll"></viewAll>
      <ftpBox :value="baseFTP" />
      <introBox v-show="orgLevel && orgLevel > 3" :list="introBoxData" />
      <gridBox v-show="!orgLevel || orgLevel <= 3" :list="gridBoxData" />
      <customNum :customerTotalNum="customerTotalNum" :customerList="customerList"
                 @childClick="handleTooltipDialog" />
      <gridDetailInfo :list="gridDetailData" />
    </titleBox>
    <titleBox height="310px" title="存款模块" annotation="(金额:万元 户数:个)">
      <viewAll slot="operate" @click="handleMiddleViewAll"></viewAll>
      <ftpBox title="存款FTP" :value="depositFTP" />
      <div class="deposit-wrapper">
        <depositCard class="deposit-item" v-for="item in depositCardData" :key="item.label"
                     :dataItem="item"></depositCard>
      </div>
    </titleBox>
    <titleBox height="310px" title="贷款模块" annotation="(金额:万元 户数:个)">
      <viewAll slot="operate" @click="handleBottomViewAll"></viewAll>
      <ftpBox title="贷款FTP" :value="loansFTP" />
      <viewTooltip @click="showBusiness"></viewTooltip>
      <mediumBox :creditData="creditData"></mediumBox>
      <viewTooltip @click="showBadness"></viewTooltip>
      <badnessCard :badnessCardData="badnessCardData"></badnessCard>
      <progressInfo :interstData="progressData"></progressInfo>
      <loansInfo v-for="(item,index) in loansInfoData" :key="index" :loansData="item"></loansInfo>
    </titleBox>
    <!-- 提示框 -->
    <tooltipDialog v-model="showTooltip" :is-top="isTop">
      <component :is="componentId" :defaultValue="customListData" :dataList="dataList"
                 :tableData="tableData"></component>
    </tooltipDialog>
    <!-- 查看全部 -->
    <viewDialog v-model="showDialog" :title="dialogTitle" :annotation="dialogAnnotation">
      <component :is="dialogComponentId"></component>
    </viewDialog>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import viewAll from "@/modules/module-screenView/viewComponents/components/viewAll";
import tooltipDialog from "@/modules/module-screenView/viewComponents/components/tooltipDialog";
import viewTooltip from "@/modules/module-screenView/viewComponents/components/viewTooltip";
import viewDialog from "@/modules/module-screenView/viewComponents/components/viewDialog";

import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import gridBox from "@/modules/module-screenView/viewComponents/components/gridBox";
import introBox from "@/modules/module-screenView/viewComponents/components/introBox";
import customNum from "@/modules/module-screenView/viewComponents/components/customNum";
import gridDetailInfo from "@/modules/module-screenView/viewComponents/components/gridDetailInfo";
import depositCard from "@/modules/module-screenView/viewComponents/components/depositCard";
import mediumBox from "@/modules/module-screenView/viewComponents/components/mediumBox";
import badnessCard from '@/modules/module-screenView/viewComponents/components/badnessCard'
import progressInfo from "@/modules/module-screenView/viewComponents/components/progressInfo";
import loansInfo from "@/modules/module-screenView/viewComponents/components/loansInfo";

import customList from "@/modules/module-screenView/viewComponents/components/tooltipContent/customList"
import businessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/businessList"
import badnessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/badnessList";
import gridInfoDialog from '../components/leftDialog/gridInfoDialog'
import depositInfoDialog from '../components/leftDialog/depositInfoDialog'
import loansInfoDialog from '../components/leftDialog/loansInfoDialog'

import {
  introBoxData,
  gridBoxData,
  customerList,
  gridDetailData,
  customListData,
  dataList,
  tableData,
  depositCardData,
  creditData,
  loansInfoData
} from '../config/leftConfig'
export default ({
  components: {
    titleBox,
    viewAll,
    tooltipDialog,
    viewTooltip,
    viewDialog,

    ftpBox,
    gridBox,
    introBox,
    customNum,
    gridDetailInfo,
    depositCard,
    mediumBox,
    badnessCard,
    progressInfo,
    loansInfo
  },
  provide() {
    return {
      ...this
    }
  },
  data() {
    return {
      showTooltip: false,
      isTop: true,
      componentId: customList,
      showDialog: false,
      dialogTitle: "",
      dialogAnnotation: '',
      dialogComponentId: gridInfoDialog,

      baseFTP: '0',
      customerTotalNum: "0",
      depositFTP: '0',
      loansFTP: '0',
      badnessCardData: {
        overDebtNum: '0',
        overDebtRate: '0',
        badnessNum: '0',
        badnessRate: '0',
      },
      progressData: {
        incomeRate: "0",
        incomeLabel: "正常收息率",
        innerRate: "0",
        innerLabel: "内",
        outerRate: "0",
        outerLabel: "外",
      },

      introBoxData,
      gridBoxData,
      customerList,
      gridDetailData,
      customListData,
      dataList,
      tableData,
      depositCardData,
      creditData,
      loansInfoData
    }
  },
  computed: {
    orgLevel() {
      return this.$store.getters["screenView/orgInfo"]?.level;
    },
  },
  methods: {
    handleTopViewAll() {
      this.dialogTitle = '网格概况'
      this.dialogAnnotation = ''
      this.dialogComponentId = gridInfoDialog
      this.showDialog = true
    },
    handleMiddleViewAll() {
      this.dialogTitle = '存款模块'
      this.dialogAnnotation = '(金额:万元)'
      this.dialogComponentId = depositInfoDialog
      this.showDialog = true
    },
    handleBottomViewAll() {
      this.dialogTitle = '贷款模块'
      this.dialogAnnotation = '(金额:万元 户数:个)'
      this.dialogComponentId = loansInfoDialog
      this.showDialog = true
    },
    handleTooltipDialog() {
      this.showTooltip = true;
      this.componentId = customList;
      this.isTop = true;
    },
    showBusiness() {
      this.showTooltip = true;
      this.componentId = businessList;
      this.isTop = false;
    },
    showBadness() {
      this.showTooltip = true;
      this.componentId = badnessList;
      this.isTop = false;
    },
  },
})
</script>

<style lang="scss" scoped>
.left-view-wrapper {
  position: relative;
  .deposit-wrapper {
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    width: 100%;
    .deposit-item {
      width: 43%;
      margin-bottom: 20px;
    }
  }
}
</style>