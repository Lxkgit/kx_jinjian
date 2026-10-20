<template>
  <div class="smallBox">
    <div class="content">
      <info-box class="infoBox">
        <div class="num">{{ past.money.num }}</div>
        <div class="label">{{ past.money.label }}</div>
        <div class="footer">
          <div class="footer-label">{{ past.rate.label }}</div>
          <div class="footer-num">{{ past.rate.num }}%</div>
        </div>
      </info-box>
      <info-box class="infoBox">
        <div class="num">{{ bad.money.num }}</div>
        <div class="label">{{ bad.money.label }}</div>
        <div class="footer">
          <div class="footer-label">{{ bad.rate.label }}</div>
          <div class="footer-num">{{ bad.rate.num }}%</div>
        </div>
      </info-box>
    </div>
  </div>
</template>

<script>
import infoBox from "../infoBox.vue";
export default {
  components: { infoBox },
  name: "smallBox",
  props: {
    past: {
      type: Object,
      default: () => {
        return {
          money: {
            label: "逾欠余额",
            num: "1,343,253",
          },
          rate: {
            label: "逾欠率",
            num: "12.43%",
          },
        };
      },
    },
    bad: {
      type: Object,
      default: () => {
        return {
          money: {
            label: "不良余额",
            num: "1,343,253",
          },
          rate: {
            label: "不良率",
            num: "12.43%",
          },
        };
      },
    },
    moreBtn: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {};
  },
  methods: {
    showMore() {
      this.$emit("showMore");
    },
  },
};
</script>

<style lang="scss" scoped>
.smallBox {
  height: 115px;
  flex-direction: column;
  .content {
    width: 100%;
    height: 90px;
    .infoBox {
      width: 45%;
      flex-direction: column;
      padding: 7.5px;
      .num {
        font-weight: bold;
        // background: linear-gradient(
        //   0deg,
        //   rgb(62, 203, 255) 0%,
        //   rgb(222, 222, 222) 100%
        // );
        font-size: 18px;
        color: rgb(62, 203, 255);
        // background-clip: text;
        // -webkit-background-clip: text;
        // -webkit-text-fill-color: transparent;
      }
      .label {
        font-size: 18px;
        color: #fff;
      }
      .footer {
        width: 90%;
      }
      .footer-label {
        color: #9dbec1;
      }
      .footer-num {
        font-size: 16px;
        color: #ffdb6c;
        font-family: Number;
        font-weight: bold;
      }
    }
  }
}
.smallBox,
.content,
.infoBox,
.footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
