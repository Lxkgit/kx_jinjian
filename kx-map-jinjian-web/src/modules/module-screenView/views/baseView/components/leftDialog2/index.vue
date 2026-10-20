<template>
  <div>

    <template v-if='showGrid'>
      <div class="left-dialog-wrapper">
        <div class="left-content">

          <boxTitle title="地区现金服务网格数量情况" />
          <newLattice :latticeData="value.latticeData" />
          <boxTitle title="地区人民币知识宣传数量情况" />
          <cardTwo :info='value.cardInfo' />
          <boxTitle title="地区重点网格和重点用现对象数量情况" />
          <pieTwo :info="value.pieTwoData" />
        </div>
        <div class="right-content">
          <boxTitle title="浙江省全域各地市现金服务网格数量情况" />
          <ul class="gaugeList">
            <li>
              <gridGauge :info="value.gaugeInfo" />
            </li>
            <li>
              <gridGauge :info="value.gaugeInfo1" />
            </li>
          </ul>
          <boxTitle title="浙江省人民币知识宣传数量情况" />
          <girdPie3d :optionData="value.pie3dData" />
        </div>
      </div>
    </template>
    <template v-else>
      <div>
        <cardTwo class="bigBox" :info="value.cardTwoData" />
      </div>
      <div class="left-dialog-wrapper">
        <div class="left-content">
          <!-- <boxTitle title="地区全域银行网点数量" />
          <newLattice :latticeData="value.latticeData1" />
          <boxTitle title="地区全域现金服务点数量" />
          <newLattice :latticeData="value.latticeData2" /> -->
          <boxTitle title="地区全域各地市现金服务网格数量情况" />
          <cardThree :info="value.cardThreeData" />
          <boxTitle title="地区银行机构网点数量情况" />
          <gridGauge2 :info="value.gridGauge2Data" />
        </div>
        <div class="right-content">
          <!-- <boxTitle title="地区全域网格责任银行网点数量" />
          <newLattice :latticeData="value.latticeData3" />
          <boxTitle title="地区全域农村金融服务站数量" />
          <newLattice :latticeData="value.latticeData4" /> -->
          <boxTitle title="地区网点服务类型的情况" />
          <listThree :info="value.listThreeData" />
          <listThree class="green" :info="value.listThreeData1" />
          <listTwo :info="value.listTwoData" />
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import boxTitle from "@/modules/module-screenView/viewComponents/components/boxTitle";
import girdPie from "@/modules/module-screenView/viewComponents/components/girdPie";
import barBi from "@/modules/module-screenView/viewComponents/components/barBi";
import girdPie3d from "@/modules/module-screenView/viewComponents/components/girdPie3d";
import newLattice from "@/modules/module-screenView/viewComponents/components/newLattice";
import cardTwo from "@/modules/module-screenView/viewComponents/components/cardTwo";
import cardThree from "@/modules/module-screenView/viewComponents/components/cardThree";
import barStack from "@/modules/module-screenView/viewComponents/components/barStack";
import barStackSimply from "@/modules/module-screenView/viewComponents/components/barStackSimply";
import gridGauge from "@/modules/module-screenView/viewComponents/components/gridGauge";
import pieTwo from "@/modules/module-screenView/viewComponents/components/pieTwo";
import gridGauge2 from "@/modules/module-screenView/viewComponents/components/gridGauge2";
import listTwo from "@/modules/module-screenView/viewComponents/components/listTwo";
import listThree from "@/modules/module-screenView/viewComponents/components/listThree";


export default {
  name: "leftDialog",
  components: {
    boxTitle,
    girdPie,
    barBi,
    girdPie3d,
    newLattice,
    cardTwo,
    cardThree,
    barStack,
    barStackSimply,
    gridGauge,
    pieTwo,
    gridGauge2,
    listTwo,
    listThree

  },
  props: {
    showGrid: {
      type: Boolean,
    },
    dataList: {
      type: Array,
    },
    tableData: {
      type: Array,
    },
    value: {
      type: Object,
      default: () => ({}),
    },
  },
  mounted() {
    console.log("leftDialog", this.value);
  },
  computed: {
    orgLevel() {
      const res = this.$store.getters["screenView/orgInfo"]?.level;
      console.log(res);
      return res;
    },
  },
};
</script>

<style lang="scss" scoped>
.left-dialog-wrapper {
  display: flex;
  justify-content: space-between;

  .left-content {
    height: auto;
    min-width: 430px;
    max-width: 490px;
    margin-right: 20px;
  }

  .right-content {
    width: 430px;
    height: auto;
  }
}

.gaugeList {
  display: flex;
  margin-top: -20px;

  li {
    flex: 1;
  }
}
</style>
