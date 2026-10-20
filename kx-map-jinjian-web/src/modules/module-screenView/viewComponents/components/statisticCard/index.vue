<template>
  <info-box class="cunkuan">
    <img
      class="image"
      :src="require(`../../assets/save-${dataItem.imgUrl}.png`)"
      alt=""
    />
    <div class="row-item" v-for="(item, index) in config" :key="index">
      <div class="value" :style="{ color: item.valueColor }">
        {{ dataItem[item.key] }}
      </div>
      <div class="label">{{ item.label }}</div>
    </div>
  </info-box>
</template>

<script>
import infoBox from "../infoBox.vue";
export default {
  name: "statisticCard",
  components: { infoBox },
  props: {
    dataItem: {
      type: Object,
      default: () => {
        return {
          effectiveMsgNum: "2135",
          effectiveRate: "67",
          imgUrl: 5,
        };
      },
    },
    config: {
      type: Array,
      default: () => [
        { label: "有效信息人数", key: "effectiveMsgNum" },
        {
          label: "活动有效率",
          key: "effectiveRate",
          valueColor: "#FCBE3F ",
        },
      ],
    },
  },
};
</script>

<style scoped lang="scss">
.cunkuan {
  height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  padding: 20px 0;

  .image {
    margin: 10px 0;
    width: 70px;
    height: 45px;
  }
  .row-item {
    margin: 8px 0;
    text-align: center;
    .value {
      font-family: Number;
      color: rgb(62, 203, 255);
      font-size: 24px;
      font-weight: bold;
      margin: 5px auto;
    }
    .label {
      font-size: 18px;
      color: #fff;
      margin: 5px auto;
    }
  }
}
</style>
