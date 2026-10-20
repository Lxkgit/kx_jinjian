<template>
  <div class="introBox">
    <ul>
      <li v-for="(item, index) in info" :key="item.left">
        <div class="title">{{ item.left }}</div>
        <div :class="index == 0 ? 'value value1' : 'value'">{{ item.right }}</div>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    info: {
      type: Array,
      default: () => [
        {
          left: "责任网点",
          key: "dutyNetworkName",
          right: "",
        },
        {
          left: "银行机构",
          right: "",
          key: "bankAgencyName",
        },
        {
          left: "网点地址",
          right: "",
          key: "dutyNetworkAddress",
        },
        {
          left: "网点责任人",
          right: "",
          key: "dutyName",
        },
        {
          left: "联系电话",
          right: "",
        }

      ],
    },
  },
};
</script>

<style scoped lang="scss">
.introBox {
  margin-block: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: url("../../assets/depeBg.png") no-repeat;
  background-size: 100% 100%;

  ul {
    padding: 16px 20px;

    li {
      display: flex;
      align-items: center;
      gap: 0 8px;
      line-height: 18px;
      margin-bottom: 8px;

      .title {
        width: 80px;
        color: #A8D6FF;
        font-size: 14px;
        text-align: right;
        white-space: nowrap;
        flex-shrink: 0;
      }

      .value {
        color: #fff;
        font-size: 14px;

        &.value1 {
          color: #28F1FA;
        }

      }

    }
  }

}
</style>
