<template>
  <div class="smallBox">
    <dl v-for="(item, index) in info" :key="item.title" :class="index == 0 ? 'selected' : ''">
      <dd>{{ item.value }}</dd>
      <dt>{{ item.title }}</dt>

    </dl>
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
          title: '人民币现金存取',
          value: "3603",

        },
        {
          title: '本外部现金存取',
          value: "363",
        },
        {
          title: '小面额兑换支取',
          value: "363",
        }
        ];
      },
    },

  },
  data() {
    return {};
  },

};
</script>

<style lang="scss" scoped>
.smallBox {
  width: 430px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-bottom: 10px;


  dl {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 138px;
    height: 64px;
    background: url('../../assets/listThree.png') no-repeat;
    background-size: 100%;

    dt {
      font-size: 14px;
      color: #fff;

    }

    dd {
      margin-bottom: 10px;
      font-size: 24px;
      font-weight: bold;
      color: #00C0FF;

      span {
        font-size: 12px;
        color: rgba(255, 255, 255, 0.64);
        margin-left: 5px;

      }
    }

  }

  &.green {
    dl {
      background: url('../../assets/listThreeGreen.png') no-repeat;
      background-size: 100%;

      dd {
        margin-bottom: 10px;
        font-weight: bold;
        color: #00FFF0;

      }
    }
  }

}
</style>
