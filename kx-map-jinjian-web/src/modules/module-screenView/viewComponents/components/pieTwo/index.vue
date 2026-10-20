<template>
  <div class="smallBox">
    <div v-for="(item) in info" :key="item.title" class="list">
      <div class="left">
        <div class="rate">{{ item.rate }}</div>
        <div class="desc">占比</div>
      </div>
      <div class="right">
        <div class="title">{{ item.title }}</div>
        <div class="num">{{ item.num }}</div>
      </div>
    </div>
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
          rate: "20%",
          num: '25,034,322',
          title: "重点网格数量"
        },
        {
          rate: "85.72%",
          num: '34,322',
          title: '重点用现对象数量'
        }
        ];
      },
    },

  },
  data() {
    return {};
  }

};
</script>

<style lang="scss" scoped>
.smallBox {
  width: 430px;
  padding-bottom: 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;

  .list {
    display: flex;
    flex-direction: row;
    height: 66px;
    width: 212px;
    background: url('../../assets/pieTwo1.png') no-repeat;
    background-size: 100%;
    position: relative;

    .left {
      display: flex;
      flex-direction: column;
      width: 60px;
      justify-content: center;
      align-items: center;
      color: #fff;
      font-size: 12px;
      margin-right: 10px;

      .rate {
        font-variation-settings: "opsz" auto;
        background: linear-gradient(180deg, #FFFFFF 0%, #00D5FF 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }

      .desc {
        margin-top: 5px;
        font-size: 12px;
      }
    }

    .right {
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: left;

      .title {
        font-size: 14px;
        color: #fff;
      }

      .num {
        margin-top: 5px;
        color: #00D5FF;
        font-size: 20px;
      }
    }


    &:nth-child(2) {
      background: url('../../assets/pieTwo2.png') no-repeat;
      background-size: 100%;

      .left {
        .rate {
          font-variation-settings: "opsz" auto;
          background: linear-gradient(180deg, #85C4FF 16%, #4C6BE9 90%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
        }
      }

      .right {
        .num {
          color: #75A2FF;
        }
      }
    }
  }

}
</style>
