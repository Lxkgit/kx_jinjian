<template>
  <div class="category">
    <div class="item" v-for="(item, index) in list" :key="index">
      <div class="header">
        <div class="dot"></div>
        <div class="title">{{ item.title }}</div>
      </div>
      <div class="content">
        <div class="top">
          <img
            class="image"
            :src="
              require(`@/modules/module-screenView/assets/image/${item.icon}`)
            "
            alt=""
          />
          <div class="household item-top">
            <div class="value">{{ item.households }}</div>
            <div class="label">贷款户数</div>
          </div>
          <div class="money item-top">
            <div class="value">{{ item.money }}</div>
            <div class="label">贷款余额</div>
          </div>
        </div>
      </div>
      <small-box :past="item.pastData" :bad="item.badData"></small-box>
    </div>
  </div>
</template>

<script>
import smallBox from "../smallBox/index.vue";
export default {
  components: { smallBox },
  props: {
    list: {
      type: Array,
      default: () => [
        {
          title: "信用",
          key: "credit",
          icon: "xinyong.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠金额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良金额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "保证",
          key: "ensure",
          icon: "baozheng.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠金额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良金额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "抵押",
          key: "mortgage",
          icon: "diya.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠金额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良金额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
        {
          title: "质押",
          key: "pledge",
          icon: "zhiya.png",
          households: "",
          money: "",
          pastData: {
            money: {
              label: "逾欠金额",
              num: "",
            },
            rate: {
              label: "逾欠率",
              num: "",
            },
          },
          badData: {
            money: {
              label: "不良金额",
              num: "",
            },
            rate: {
              label: "不良率",
              num: "",
            },
          },
        },
      ],
    },
  },
};
</script>

<style scoped lang="scss">
.category {
  width: 100%;
  .item {
    margin-top: 10px;
    .header {
      display: flex;
      align-items: center;
      .dot {
        width: 6px;
        height: 6px;
        background: #00f6ff;
        border-radius: 50%;
      }
      .title {
        margin-left: 10px;
        font-size: 14px;
        color: #00f6ff;
      }
    }
    .content {
      margin-top: 10px;
      .top {
        position: relative;
        width: 100%;
        height: 76px;
        display: flex;
        background: url("../../assets/ck-bg.png") no-repeat;
        background-size: 100% 100%;
        text-align: center;
        .image {
          width: 70px;
          height: 70px;
          position: absolute;
          top: 50%;
          left: 49%;
          transform: translate(-50%, -50%);
        }
        .household {
          position: absolute;
          top: 50%;
          left: 25%;
          transform: translate(-50%, -50%);
        }
        .money {
          position: absolute;
          top: 50%;
          left: 75%;
          transform: translate(-50%, -50%);
        }
        .value {
          font-family: Number;
          font-weight: bold;
          font-size: 20px;
          color: #ffffff;
          // background: linear-gradient(
          //   0deg,
          //   rgb(62, 242, 255) 0%,
          //   rgb(222, 222, 222) 100%
          // );
          color: rgb(62, 242, 255);
          // background-clip: text;
          // -webkit-background-clip: text;
          // -webkit-text-fill-color: transparent;
        }
        .label {
          text-align: center;
          margin-top: 6px;
          font-weight: 500;
          font-size: 16px;
          color: #8bf6ff;
        }
      }
    }
  }
}
</style>
