<template>
  <!-- <buttonOperation :src="src" :direction="'rightTop'"> -->
  <div class="icon-box">
    <!-- <p class="icon-title">客户锚点</p> -->
    <div class="customer-box">
      <div class="customerItem" v-for="item in customerArr" :key="item.label">
        <img class="img" :src="item.src" alt="" />
        <div class="lable">{{ item.label }}</div>
      </div>

      <!-- <div class="customerItem w50" v-for="(item) in customerArr2" :key="item.label">
        <el-checkbox class="mycheckBox">
          <img class="img" :src="item.src" alt="" />{{ item.label }}
        </el-checkbox>

      </div> -->
    </div>
    <div class="customer-box">
      <div
        class="customerItem w50"
        v-for="item in switchType"
        :key="item.label"
        @click="toggleSwitch(item)"
      >
        <div :class="['check', item.switch && 'active']"></div>
        <img class="img" :src="item.src" alt="" />
        <div class="label">{{ item.label }}</div>
      </div>
    </div>
  </div>
  <!-- </buttonOperation> -->
</template>

<script>
// import buttonOperation from "../common/buttonOperation";
export default {
  // components: {
  //   buttonOperation,
  // },
  // computed: {
  //   layerCheck() {
  //     return this.$store.getters["screenView/layerCheck"];
  //   },
  // },
  data() {
    return {
      src: require("@/modules/module-screenView/assets/image/legend.png"),
      customerArr: [
        // { src: require("../../assets/icon/bank_3.png"), label: "定位锚点" },
        // { src: require("../../assets/icon/bank_2.png"), label: "普通网点" },
        // { src: require("../../assets/icon/bank_1.png"), label: "责任网点" },
      ],
      switchType: [
        {
          // src: require("../../assets/icon/bank_4.png"),
          src: require("../../assets/icon/commonJJ.png"),
          label: "金融监管局",
          switch: false,
          value: "1",
        },
        {
          // src: require("../../assets/icon/bank_5.png"),
          src: require("../../assets/icon/commonBank.png"),
          label: "银行网点",
          switch: false,
          value: "2",
        },
        {
          src: require("../../assets/icon/headHot.png"),
          label: "风险热力图",
          switch: false,
          value: "headHot",
        },
        {
          src: require("../../assets/icon/riskPoint.png"),
          label: "风险事件点",
          switch: false,
          value: "riskPoint",
        },
        {
          src: require("../../assets/icon/customer_10.png"),
          label: "商户客户",
          switch: false,
          value: "merchantCustomers",
        },
        {
          src: require("../../assets/icon/customer_11.png"),
          label: "企业客户",
          switch: false,
          value: "enterpriseCustomer",
        },
        {
          src: require("../../assets/icon/police.png"),
          label: "派出所",
          switch: false,
          value: "police",
        },
      ],
      timer: null,
    };
  },
  mounted() {
    this.switchType[0].switch = false;
    this.switchHandle(this.switchType[0]);

    this.switchType[1].switch = false;
    this.switchHandle(this.switchType[1]);

    this.switchType[2].switch = false;
    this.switchHandle(this.switchType[2]);

    this.switchType[3].switch = true;
    this.switchHandle(this.switchType[3]);

    this.switchType[4].switch = false;
    this.switchHandle(this.switchType[4]);

    this.switchType[5].switch = false;
    this.switchHandle(this.switchType[5]);
  },
  methods: {
    toggleSwitch(item) {
      item.switch = !item.switch;
      this.debounceClick(item);
    },
    debounceClick(item) {
      if (this.timer) {
        clearTimeout(this.timer);
      }
      this.timer = setTimeout(() => {
        this.switchHandle(item);
      }, 1000);
    },
    switchHandle(item) {
      const { switch: flag, value } = item;
      console.log(flag, value,888);
      switch (value) {
        case "1":
          this.$store.commit("screenView/SET_SERVICE_FLAG", flag);
          // this.switchType[1].switch = false;
          // this.$store.commit("screenView/SET_RURAL_FINANCIAL_FLAG", false);
          break;
        case "2":
          this.$store.commit("screenView/SET_RURAL_FINANCIAL_FLAG", flag);
          // this.switchType[0].switch = false;
          // this.$store.commit("screenView/SET_SERVICE_FLAG", false);
          break;
        case "headHot": {
          this.$store.commit("screenView/SET_CUSTOMER_HOT", flag);
          break;
        }
        case "riskPoint": {
          this.$store.commit("screenView/SET_RISK_POINT", flag);
          break;
        }
        case "police": {
          this.$store.commit("screenView/SET_POLICE", flag);
          break;
        }
        case "merchantCustomers": {
          this.$store.commit("screenView/SET_POINT_STATUS1", flag);
          break;
        }
        case "enterpriseCustomer": {
          this.$store.commit("screenView/SET_POINT_STATUS2", flag);
          break;
        }
        default:
          break;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.icon-box {
  // width: 338px;
  // height: 116px;
  width: 280px;
  height: fit-content;
  padding: 10px;
  background: url("~@/modules/module-screenView/assets/image/legendbg.png")
    no-repeat;
  background-size: 100% 100%;

  .icon-title {
    color: #fff;
    font-size: 16px;
    line-height: 30px;
  }

  .customer-box {
    display: flex;
    align-items: center;
    flex-wrap: wrap;

    .customerItem {
      width: 33%;
      height: 54px;
      display: flex;
      align-items: center;
      color: #fff;

      &.w50 {
        width: 50%;
      }

      .img {
        width: 22px;
        height: 32px;
        margin: 10px;
      }

      .label {
        color: #fff;
        font-size: 13px;
      }
    }
  }

  .check {
    width: 20px;
    height: 20px;
    // border: 2px solid #4faaa9;
    // border-radius: 4px;
    background: url("~@/modules/module-screenView/assets/icon/uncheck.png")
      no-repeat;
    background-size: 100%;

    &.active {
      // position: relative;
      background: url("~@/modules/module-screenView/assets/icon/checked.png")
        no-repeat;
      background-size: 100%;

      // &::after {
      //   content: "";
      //   width: 12px;
      //   height: 12px;
      //   border-radius: 50%;
      //   position: absolute;
      //   background: #70f0fc;
      //   top: 5px;
      //   left: 5px;
      // }
    }
  }
}
</style>
