<template>
  <div class="smallBox">
    <dl v-for="(item, index) in info" :key="item.title" :class="index == 0 ? 'selected' : ''">
      <dt>{{ item.title }}</dt>
      <dd>{{ item.value }}<span>{{ item.unit }}</span></dd>
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
          title: '人工现金柜台数',
          value: "3603",
          unit: '个'
        },
        {
          title: '自助现金机具数',
          value: "363",
          unit: '台'
        },
        {
          title: '小面额自助现金机具数',
          value: "363",
          unit: '台'
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
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding-bottom: 10px;

  dl {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 56px;
    width: 140px;
    background: url('../../assets/cardThree1.png') no-repeat;
    background-size: 100%;

    &.selected {
      background: url('../../assets/cardThree2.png') no-repeat;
      background-size: 100%;
    }

    dt {
      font-size: 12px;
      color: #fff;
      margin-bottom: 5px;
    }

    dd {
      font-size: 18px;
      font-weight: bold;
      color: #01EAFF;

      span {
        font-size: 12px;
        color: rgba(255, 255, 255, 0.64);
        margin-left: 5px;

      }
    }
  }

}
</style>
