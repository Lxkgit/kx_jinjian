<template>
  <div class="right-wrapper">
    <template v-if="showGrid === 'top'">
      <cardTwo class="bigBox" :info="value.cardTwoData" />
      <div class='content'>
        <div class="item">
          <boxTitle title="地区零钞预约券别需求量情况" />
          <gridRankList class="w100" :columns="value.rankColumns" :tableData="value.rankData" />
        </div>
      </div>
    </template>

    <template v-if="showGrid === 'middle'">
      <div class='content'>
        <div class="item3">
          <cardBigTwo :info="value.cardBigTwoData" />
          <boxTitle title="地区商户分类情况" />
          <pieOne :info="value.pieOneData" />

        </div>
        <div class="item2">
          <boxTitle title="地区商户摸排情况" />
          <barOne :info="value.barOneData" />
        </div>
      </div>
    </template>

    <template v-if="showGrid === 'bottom'">
      <div class='content'>
        <div class="item">
          <cardOne :info="value.cardOneData" />
          <boxTitle title="地区各地市投诉事件情况" />
          <girdLineOne :info="value.girdLineOneData" />
        </div>

      </div>
    </template>

  </div>
</template>

<script>
import boxTitle from "@/modules/module-screenView/viewComponents/components/boxTitle";
import cardTwo from "@/modules/module-screenView/viewComponents/components/cardTwo";
import gridRankList from "@/modules/module-screenView/viewComponents/components/gridRankList";
import barBv from "@/modules/module-screenView/viewComponents/components/barBv";
import cardBigTwo from "@/modules/module-screenView/viewComponents/components/cardBigTwo";
import gridRadar from "@/modules/module-screenView/viewComponents/components/gridRadar";
import barTwo from "@/modules/module-screenView/viewComponents/components/barTwo";
import wordCloud from "@/modules/module-screenView/viewComponents/components/wordCloud";
import gridRankHot from "@/modules/module-screenView/viewComponents/components/gridRankHot";
import barOne from "@/modules/module-screenView/viewComponents/components/barOne";
import pieOne from "@/modules/module-screenView/viewComponents/components/pieOne";
import cardOne from "@/modules/module-screenView/viewComponents/components/cardOne";
import girdLineOne from "@/modules/module-screenView/viewComponents/components/girdLineOne";


import ftpBox from "@/modules/module-screenView/viewComponents/components/ftpBox";
import depositCard from "@/modules/module-screenView/viewComponents/components/depositCard";
import businessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/businessList";
import progressInfo from "@/modules/module-screenView/viewComponents/components/progressInfo/index.vue";
import badnessList from "@/modules/module-screenView/viewComponents/components/tooltipContent/badnessList";
export default {
  name: "rightDialog",
  components: {
    boxTitle,
    cardTwo,
    gridRankList,
    barBv,
    cardBigTwo,
    gridRadar,
    barTwo,
    wordCloud,
    gridRankHot,
    barOne,
    pieOne,
    cardOne,
    girdLineOne,

    ftpBox,
    depositCard,
    businessList,
    progressInfo,
    badnessList,
  },
  props: {
    showGrid: {
      type: String,
      dafault: "top",
      required: true,
    },
    saveData: {
      type: Array,
    },
    interstData: {
      type: Object,
    },
    dataList: {
      type: Array,
    },
    tableData: {
      type: Array
    },
    value: {
      type: Object,
      default: () => ({}),
    },
  },
};
</script>

<style lang="scss" scoped>
.right-wrapper {
  position: relative;

  .content {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    padding-bottom: 20px;

    .item {
      flex: 1;
      padding: 0 10px;
    }

    .item2 {
      flex: 2;
      padding: 0 10px;
    }

    .item3 {
      flex: 3;
      padding: 0 10px;
    }
  }

  .content-wrapper {
    display: flex;
    justify-content: space-between;

    .content-left {
      margin-right: 20px;
    }
  }
}
</style>
