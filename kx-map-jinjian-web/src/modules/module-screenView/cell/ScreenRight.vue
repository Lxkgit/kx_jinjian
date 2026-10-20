<template>
  <div class="screen-right" :class="{ 'screen-right-show': rightShow }">
    <div class="repeat-down" />
    <div class="main-wrapper">
      <slot></slot>
      <!-- <div
        class="main-btn"
        @click="$emit('turnSwitch', 'right')"
        v-if="orgInfo.level == 5"
      >
        {{ rightShow ? "展开" : "收起" }}
        <img
          v-if="rightShow"
          src="../assets/image/toLeft.png"
          style="margin-top: 8px"
        />
        <img v-else src="../assets/image/toRight.png" style="margin-top: 8px" />
      </div> -->
    </div>
    <!-- <div class="operate-wrapper">
      <backPart></backPart>
      <roleSwitch v-show="false"></roleSwitch>
      <customerFilter></customerFilter>
      <el-badge :value="5">
        <div class="trends-part-wrapper" @click="dynamicClick">动态信息</div>
      </el-badge>
    </div> -->

    <!-- <div class="bottom-wrapper">
      <legendPart></legendPart>
      <mapTile v-show="false"></mapTile>
    </div> -->
    <!-- 聚合点详情 -->
    <!-- <trends class="pop-up-build" v-model="collectShow" :readmessageDetails="readmessageDetails"
      @messageDetails="messageDetailsClick"></trends> -->
    <!-- 消息详情 -->
    <messageDetails class="pop-up-build-message" v-model="detailsShow" :messageDetailsInfo="messageDetailsInfo"
      @detailsDialog="detailsDialogClick" @location="location"></messageDetails>
  </div>
</template>

<script>
import screenSelect from "@/modules/module-screenView/components/operate/screenSelect";
import backPart from "@/modules/module-screenView/components/operate/backPart";
import roleSwitch from "@/modules/module-screenView/components/operate/roleSwitch";
import customerFilter from "@/modules/module-screenView/components/operate/customerFilter";
import legendPart from "../components/operate/legendPart";
import mapTile from "../components/operate/mapTile";
import trends from "../components/trends";
import messageDetails from "../components/messageDetails/messageDetails.vue";

export default {
  components: {
    screenSelect,
    backPart,
    roleSwitch,
    customerFilter,
    legendPart,
    mapTile,
    trends,
    messageDetails,
  },
  props: {
    rightShow: {
      type: Boolean,
    },
  },
  computed: {
    orgInfo() {
      return this.$store.getters["screenView/orgInfo"];
    },
  },
  data() {
    return {
      collectShow: false,
      detailsShow: false,
      messageDetailsInfo: {},
      readmessageDetails: {},
    };
  },
  methods: {
    dynamicClick() {
      this.collectShow = true;
    },
    messageDetailsClick(item) {
      this.collectShow = false;
      this.detailsShow = true;
      this.messageDetailsInfo = item;
    },
    detailsDialogClick(item) {
      item.sataus = 1;
      this.detailsShow = false;
      this.collectShow = true;
      this.readmessageDetails = item;
    },
    location(id) {
      this.detailsShow = false;
      let netMap = {
        1: {
          name: "金华银行总行营业部",
          address: "金华市婺城区丹溪路1388号",
          lon: "119.63912300",
          lat: "29.07940400",
          code: "330702007004",
          lev: 5,
          searchType: 0,
        },
        2: {
          name: "浙江稠州商业银行金华分行",
          address: "浙江省金华市婺城区八一南街161号",
          lon: "119.650255",
          lat: "29.092258",
          code: "330702005017",
          lev: 5,
          searchType: 0,
        },
        3: {
          name: "浙江泰隆商业银行义乌支行",
          address: "福田街道金融商务局6街,福田银座B座",
          lon: "120.104168",
          lat: "29.328532",
          code: "330782008hb02",
          lev: 5,
          searchType: 0,
        },
        4: {
          name: "建行义乌分行营业部",
          address: "浙江省义乌市稠州北路468号",
          lon: "120.087117",
          lat: "29.316667",
          code: "330782001010",
          lev: 5,
          searchType: 0,
        },
        5: {
          name: "浙江稠州商业银行义乌稠江支行",
          address: "义乌市新科路F8号稠州大厦",
          lon: "120.038802",
          lat: "29.283228",
          code: "330782003015",
          lev: 5,
          searchType: 0,
        },
        6: {
          name: "浙商银行金华分行",
          address: "浙江省金华市金东区宾虹东路358号嘉福商务大厦2号楼10楼",
          lon: "119.669854",
          lat: "29.096801",
          code: "330703001003",
          lev: 5,
          searchType: 0,
        },
        7: {
          name: "中信银行金华分行营业部",
          address: "浙江省金华市婺城区西关街道宾虹路999号置信大厦",
          lon: "119.637475",
          lat: "29.084855",
          code: "330702007004",
          lev: 5,
          searchType: 0,
        },
        8: {
          name: "嘉兴银行金华分行营业部",
          address: "浙江省金华市金东区政和街33号",
          lon: "119.69083",
          lat: "29.103724",
          code: "330703001002",
          lev: 5,
          searchType: 0,
        },
        9: {
          name: "浙江稠州商业银行金东支行",
          address: "浙江省金华市金东区环城东路489号",
          lon: "119.689506",
          lat: "29.094874",
          code: "330703001hb01",
          lev: 5,
          searchType: 0,
        },
      };
      this.$store.commit("screenView/SET_ANCHOR_INFO", netMap[id]);
    },
  },
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}

.ml-margin {
  margin: 0 15px;
}

.bussiness {
  margin-top: 15px;
}

.screen-right {
  position: absolute;
  width: 548px;
  top: 100px;
  height: 1009px;
  right: 0;
  pointer-events: none;
  // &.screen-right-show {
  //   right: -440px;
  //   background: url("~@/modules/module-screenView/assets/image/bg-right.png"),
  //     url("~@/modules/module-screenView/assets/image/repeat-up.png") 31px top
  //       no-repeat;
  //   background-size: 100% 100%, 440px 89px;
  //   .repeat-down {
  //     width: 440px;
  //     left: -440px;
  //   }
  // }
  // .repeat-down {
  //   width: 0px;
  //   height: 24px;
  //   position: absolute;
  //   bottom: 0px;
  //   left: 0px;
  //   background: url("~@/modules/module-screenView/assets/image/repeat-down.png")
  //     repeat;
  //   transition: all 0.3s linear;
  // }

  .main-wrapper {
    width: 476px;
    height: 980.6px;
    position: absolute;
    top: 10px;
    right: 40px;
    pointer-events: auto;

    .vertical-banner {
      margin-top: 15px;
      display: flex;
      align-items: center;

      .savings-structure {
        flex: 1;
      }
    }

    .dash-list {
      margin-top: 15px;
      display: flex;
      flex-wrap: wrap;

      .dash-item {
        height: 100px;
        width: 50%;
        @include flex;
      }
    }
  }

  .operate-wrapper {
    position: absolute;
    right: 490px;
    top: 40px;
    z-index: 999;
    pointer-events: auto;
    display: flex;
    // align-items: center;
  }

  .bottom-wrapper {
    position: absolute;
    bottom: 25px;
    right: 465px;
    pointer-events: auto;
  }
}

.main-btn {
  position: absolute;
  left: -60px;
  top: 100px;
  font-size: 16px;
  cursor: pointer;
  background: url("../assets/image/box.png");
  width: 40px;
  height: 95px;
  background-size: 100% 100%;
  color: #fff;
  word-wrap: break-word;
  padding: 16px 11px;
  line-height: 20px;
}

.trends-part-wrapper {
  width: 120px;
  height: 40px;
  background: url("../assets/image/headBtn.png") no-repeat;
  background-size: 100% 100%;
  font-size: 18px;
  color: #fff;
  text-align: center;
  line-height: 40px;
  font-weight: 800;
  cursor: pointer;
  margin-left: 20px;
}

.pop-up-build {
  top: 0;
  position: absolute;
  z-index: 999;
  pointer-events: auto;
  width: 100%;
  height: 800px;
}

.pop-up-build-message {
  overflow: hidden;
  position: absolute;
  pointer-events: auto;
  width: 100%;
  max-height: 800px;
}
</style>