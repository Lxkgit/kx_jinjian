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
          title: '网格内摸排商户数',
          value: "3603",
        },
        {
          title: '网格内现金收付摸排次数',
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
  margin-top: -10px;
  width: 430px;
  padding-bottom: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  dl {
    display: flex;
    flex-direction: column;
    justify-content: center;
    padding-left: 80px;
    height: 94px;
    width: 204px;
    background: url('../../assets/cardBig1.png') no-repeat;
    background-size: 100%;



    dt {
      font-size: 11px;
      color: #fff;
    }

    dd {
      font-size: 32px;
      margin-bottom: 10px;
      font-weight: bold;
      background: linear-gradient(180deg, #FFFFFF 0%, #0084E9 99%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    &:nth-child(2) {
      background: url('../../assets/cardBig2.png') no-repeat;
      background-size: 100%;

      dd {
        background: linear-gradient(180deg, #FDFAFD 0%, #E98F00 99%, #E98F00 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }
    }
  }

}
</style>
