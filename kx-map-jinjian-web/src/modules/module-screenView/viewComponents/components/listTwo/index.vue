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
          title: '人工现金柜台数',
          value: "3603",
          unit: '个'
        },
        {
          title: '自助现金机具数',
          value: "363",
          unit: '台'
        },
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
  justify-content: space-between;
  align-items: center;
  padding-bottom: 10px;

  dl {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 206px;
    height: 60px;
    background: url('../../assets/listTwo.png') no-repeat;
    background-size: 100%;

    dt {
      font-size: 14px;
      color: #fff;

    }

    dd {
      margin-bottom: 10px;
      font-size: 24px;
      font-weight: bold;
      color: #FFC300;

      span {
        font-size: 12px;
        color: rgba(255, 255, 255, 0.64);
        margin-left: 5px;

      }
    }
  }

}
</style>
