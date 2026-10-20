<template>
  <div class="customer-box">
    <div class="avatar-box">
      <img class="avatar" src="../../assets/event-avatar.png" alt="" />
      <div class="name">{{ itemData.customerName }}</div>
    </div>
    <div class="middle"></div>
    <div class="right">
      <div
        class="right-rowItem"
        v-for="(item, index) in rightConfig"
        :key="index"
      >
        <div class="label">{{ item.label }}:</div>
        <div class="value" :style="{ color: item.valueColor }">
          {{ itemData[item.key] }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "eventCustomer",
  props: {
    itemData: {
      type: Object,
      default: () => {},
    },
    rightConfig: {
      type: Array,
      default: () => [
        {
          label: "手机号",
          key: "customerPhone",
        },
        {
          label: "身份证号",
          key: "customerIdentity",
        },
        {
          label: "参与日期",
          key: "scanTime",
          valueColor: "#FCBE3F",
        },
      ],
    },
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
.customer-box {
  font-family: "Lucida Sans Regular";
  margin: 10px auto;
  padding: 10px;
  width: 100%;
  height: 110px;
  display: flex;
  align-items: center;
  justify-content: space-around;
  background: url("../../assets/eventCustomer-bg.png") no-repeat center;
  background-size: 100% 100%;
  .middle {
    background: linear-gradient(
      to bottom,
      rgba(76, 245, 255, 0),
      rgba(76, 245, 255, 1),
      rgba(76, 245, 255, 0)
    );
    width: 1px;
    height: 75%;
    border-radius: 50%;
  }
  .avatar-box {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .name {
      color: #fff;
    }
    .avatar {
      width: 28px;
      height: 36px;
      display: block;
      margin-bottom: 3px;
    }
  }
  .right {
    height: 90%;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    .right-rowItem {
      display: flex;
      align-items: center;
      font-size: 16px;
      .label {
        width: 70px;
        color: #a8e3f2;
      }
      .value {
        color: #fff;
      }
    }
  }
}
</style>