<template>
  <div class="button-style">
    <div v-if="turn == 'left'" class="icon-left icon" />
    <div :class="[turn == 'left' ? 'text-left' : 'text-right']">
      {{ isOpen?"展开":"收起" }}
    </div>
    <div v-if="turn == 'right'" class="icon-right icon" />
  </div>
</template>

<script>
export default ({
  props: {
    turn: {
      type: String,
      default: 'left',
    },
    isOpen: {
      type: Boolean,
      default: true,
    },
  },
  methods: {
  },
})
</script>

<style lang="scss" scoped>
.button-style {
  cursor: pointer;
  font-size: 14px;
  color: #fff;
  width: 86px;
  height: 34px;
  background-image: url("../assets/image/headBtn.png");
  background-size: 100% 100%;
  display: flex;
  align-items: center;
  .icon {
    width: 14px;
    height: 16px;
    background-size: 100% 100%;
  }
  .icon-left {
    margin-left: 14px;
    background-image: url("../assets/image/toLeft.png");
  }
  .icon-right {
    margin-left: 10px;
    background-image: url("../assets/image/toRight.png");
  }
  .text-left {
    margin-left: 12px;
  }
  .text-right {
    margin-left: 14px;
  }
}
</style>