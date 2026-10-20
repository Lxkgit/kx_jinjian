<template>
  <div class="smallBox">
    <ul>
      <li v-for="(item, index) in info" :key="item.title">
        <div class="left">
          <div :class="['icon', 'icon' + index]"></div>
        </div>
        <div class="right">
          <div class="title">{{ item.title }}</div>
          <div class="valueBox"><span class="value">{{ item.value }}</span><span class="unit">{{ item.unit }}</span>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "smallBox",
  props: {
    info: {
      type: Array,
      default: () => {
        return [{
          title: '预约兑换笔数',
          value: "3603",
          unit: '次'
        },
        {
          title: '预约兑换总金额',
          value: "363",
          unit: '万元'
        },
        {
          title: '预约兑换完成率',
          value: "46.32",
          unit: '%'
        },
        {
          title: '预约兑换率',
          value: "53.11",
          unit: '%'
        },
        ];
      },
    },

  },
  data() {
    return {};
  },
  watch: {
    info: {
      handler(newVal) {
        // console.log(newVal);
      },
      deep: true,
      immediate: true // 添加 immediate: true 以确保在绑定时立即执行一次 handler
    }
  }

};
</script>

<style lang="scss" scoped>
.smallBox {
  &.bigBox {
    width: 100%;

    ul {
      width: 100%;

      li:nth-child(2n) {
        margin-right: 8px;
      }

    }
  }

  width: 430px;
  display: flex;

  ul {
    width: 430px;
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;


    li {
      padding: 14px 24px;
      width: 210px;
      height: 70px;
      background: url('../../assets/cardTwo.png') no-repeat;
      background-size: 100%;
      display: flex;

      align-items: center;
      flex-direction: row;
      margin-right: 8px;
      margin-bottom: 8px;

      &:nth-child(2n) {
        margin-right: 0;
      }

      .left {
        width: 52px;
        height: 44px;
        margin-right: 12px;

        .icon {
          width: 52px;
          height: 44px;
          background: url('../../assets/cardTwoicon1.png') no-repeat;
          background-size: 100%;

          &.icon2 {
            background: url('../../assets/cardTwoicon2.png') no-repeat;
            background-size: 100%;
          }

          &.icon3 {
            background: url('../../assets/cardTwoicon3.png') no-repeat;
            background-size: 100%;
          }

          &.icon4 {
            background: url('../../assets/cardTwoicon4.png') no-repeat;
            background-size: 100%;
          }
        }
      }

      .right {
        flex-direction: column;

        .title {
          font-size: 14px;
          color: #fff;
          margin-bottom: 6px;
        }

        .valueBox {
          display: flex;
          align-items: baseline;
          line-height: 22px;

          .value {
            font-size: 22px;
            font-weight: bold;
            text-align: right;
            display: flex;
            align-items: center;
            letter-spacing: 1.22px;
            background: linear-gradient(180deg, #FFFFFF 0%, #00D5FF 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
          }

          .unit {
            font-size: 12px;
            color: #fff;

          }
        }


      }
    }
  }
}
</style>
