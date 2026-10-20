<template>
  <div class="important-box">
    <div class="row" v-for="(item) in info" :key="item.importName">
      <div class="row-1">
        <i class="icon"></i>
        <span class="name">{{ item.importName }}</span>
      </div>
      <div class="row-2">地址：{{ item.importAddress }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    info: {
      type: Array,
      default: () => ([
        { id: 1, importName: "涵碧紫金城购物中心", importAddress: "浙江省金华市金东区光南路999号" },
        { id: 2, importName: "涵碧紫金城", importAddress: "浙江省金华市金东区光南路999号" },
      ]),
    },
  },
};

</script>

<style scoped lang="scss">
.important-box {
  margin-bottom: 20px;

  .row {
    width: 100%;
    height: 84px;
    display: flex;
    color: #fff;

    margin-bottom: 10px;
    padding: 10px 20px;
    flex-direction: column;

    background: url('../../assets/importantBg.png') no-repeat;
    background-size: 100% 100%;

    &-1 {
      display: flex;
      align-items: center;
      height: 44px;
      line-height: 44px;

      .icon {
        display: inline-block;
        width: 24px;
        height: 24px;
        background: url('../../assets/importantIcon.png') no-repeat;
        background-size: 100% 100%;
      }

      .name {
        font-size: 16px;
        color: #fff;
        margin-left: 5px;
      }
    }

    &-2 {
      color: #A8D6FF;
      font-size: 14px;
      padding-left: 30px;
    }
  }
}
</style>
