<template>
  <div class="list-item-wrapper" :style="{ '--flexDirection': itemData.flexDirection }">
    <div :style="{ width: `${itemData.width}px` }" :class="[itemData.className]">
      <span v-if="!itemData.slot && !itemData.number" :data-value="itemData.label">
        {{ itemData.label }}
      </span>
      <span v-else-if="itemData.number" v-number-color="itemData.label"></span>
      <slot :name="itemData.key" :data="itemData"></slot>
    </div>
    <div class="item-content-wrapper">
      <list-item v-for="(item, index) in itemData.children" :key="index" :item-data="item">
      </list-item>
    </div>
  </div>
</template>

<script>
export default {
  name: "ListItem",
  props: {
    itemData: {
      type: Object,
      default: () => ({}),
    },
  },
};
</script>

<style lang="scss" scoped>
.list-item-wrapper {
  display: flex;
  color: #fff;
  width: 100%;
  height: 100%;
  text-align: center;
  align-items: center;
  > div {
    height: 100%;
  }
  .item-content-wrapper {
    display: flex;
    line-height: 32px;
    flex-direction: var(--flexDirection);
  }
}
.total-num {
  color: #a8e3f2;
}
.inside {
  margin: 5px 0;
  > span {
    position: relative;
    left: 50%;
    transform: translate(-50%);
    display: block;
    width: 20px;
    height: 20px;
    line-height: 18px;
    background: linear-gradient(0deg, #e57335, rgba(255, 120, 0, 0));
    border-radius: 50%;
    border: 1px solid #fb8c42;
  }
}
.outside {
  margin: 5px 0;
  > span {
    position: relative;
    left: 50%;
    transform: translate(-50%);
    display: block;
    width: 20px;
    height: 20px;
    line-height: 18px;
    background: linear-gradient(0deg, #35dfe5, rgba(26, 86, 101, 0));
    border-radius: 50%;
    border: 1px solid #35dfe5;
  }
}
.percent {
  color: #ffdb6c;
  span[data-value]::after {
    content: "%";
  }
}
.number {
  text-align: left;
  width: 100%;
  color: #8bf6ff;
  span {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
