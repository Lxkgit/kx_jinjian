<template>
  <info-box class="progress-info-wrapper">
    <div class="info-icon">
      <img class="icon-img" src="../../assets/progress.png" />
    </div>
    <div class="progress-wrapper">
      <titleWithProgress :ratio="interstData.incomeRate">
        <span class="interest">{{ interstData.incomeLabel }}</span>
      </titleWithProgress>
      <div class="sub-progress-wrapper">
        <titleWithProgress :ratio="interstData.innerRate" font-size="16px">
          <span class="in">{{ interstData.innerLabel }}</span>
        </titleWithProgress>
        <titleWithProgress :ratio="interstData.outerRate" font-size="16px"
                           :colors="['#AF995D', '#FBCF34', '#FF9B58', '#FFDB6C']">
          <span class="out">{{ interstData.outerLabel }}</span>
        </titleWithProgress>
      </div>
    </div>
  </info-box>
</template>

<script>
import titleWithProgress from "./titleWithProgress";
import infoBox from "../infoBox.vue";
export default {
  components: {
    titleWithProgress,
    infoBox,
  },
  props: {
    interstData: {
      type: Object,
      default: () => {
        return {
          incomeRate: "67.98",
          incomeLabel: "正常收息率",
          innerRate: "67.98",
          innerLabel: "内",
          outerRate: "12.08",
          outerLabel: "外",
        };
      },
    },
  },
};
</script>

<style lang="scss" scoped>
.progress-info-wrapper {
  width: 100%;
  height: 125px;
  margin: 20px 0;
  padding: 10px;
  border: 1px solid;
  background: linear-gradient(
    0deg,
    rgba(53, 78, 88, 0.5),
    rgba(35, 55, 63, 0.08)
  );
  border-image: linear-gradient(180deg, #66cef5, #66cef522, #7ef4ff) 1 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .info-icon {
    width: 45px;
  }
  .progress-wrapper {
    margin-left: 15px;
    width: calc(100% - 60px);
    .sub-progress-wrapper {
      margin-top: 14px;
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 48px;
    }
    .interest {
      font-size: 16px;
      color: #ffffff;
    }
    .in {
      text-align: center;
      width: 20px;
      height: 20px;
      line-height: 20px;
      font-size: 14px;
      background: linear-gradient(0deg, #e57335, rgba(255, 120, 0, 0));
      border-radius: 50%;
      color: #ffffff;
    }
    .out {
      text-align: center;
      width: 20px;
      height: 20px;
      line-height: 20px;
      font-size: 14px;
      background: linear-gradient(0deg, #35dfe5, rgba(26, 86, 101, 0));
      border-radius: 50%;
      color: #ffffff;
    }
  }
}
</style>
