<template>
  <buttonOperation :src="src">
    <ul class="map-switch">
      <li v-for="item in switchType" :key="item.label">
        <span>{{ item.label }}</span>
        <span
          class="switch-btn"
          :class="{ 'switch-off': !item.switch }"
          @click="
            item.switch = !item.switch;
            switchHandle(item);
          "
        ></span>
      </li>
    </ul>
  </buttonOperation>
</template>

<script>
import buttonOperation from "../common/buttonOperation";
export default {
  components: {
    buttonOperation,
  },
  data() {
    return {
      src: require("@/modules/module-screenView/assets/image/switch.png"),
      switchType: [
        { label: "风险热力图", switch: false, value: "1" },
        // { label: '网格授信热力图', switch: false, value: '2' },
      ],
    };
  },
  mounted() {
    this.switchType[0].switch = true;
    this.switchType[1].switch = false;
    this.switchHandle(this.switchType[0]);
    this.switchHandle(this.switchType[1]);
  },
  methods: {
    switchHandle(item) {
      const { switch: flag, value } = item;
      switch (value) {
        case "1":
          this.$store.commit("screenView/SET_CUSTOMER_HOT", flag);
          break;
        case "2":
          this.$store.commit("screenView/SET_CREDIT_HOT", flag);
          break;
        default:
          break;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.map-switch {
  border-radius: 6px;
  background: rgba($color: #0d191e, $alpha: 0.8);
  padding: 12px 24px;
  > li {
    color: #fff;
    font-size: 16px;
    margin: 12px 0;
    display: flex;
    justify-content: space-between;
    align-items: center;
    span {
      display: block;
      white-space: nowrap;
    }
    .switch-btn {
      cursor: pointer;
      margin-left: 15px;
      width: 60px;
      height: 24px;
      background: url(~@/modules/module-screenView/assets/image/switch-bg.png)
        no-repeat;
      background-size: 100% 100%;
      position: relative;
      transition: opacity 500ms;
      &.switch-off {
        opacity: 0.5;
        &::before {
          left: calc(100% - 15px - 6px);
        }
      }
      &::before {
        content: "";
        width: 15px;
        height: 15px;
        background: url(~@/modules/module-screenView/assets/image/switch-icon.png)
          no-repeat;
        background-size: 100% 100%;
        position: absolute;
        left: 6px;
        top: 50%;
        transform: translateY(-50%);
        transition: left 500ms;
      }
    }
  }
}
</style>
