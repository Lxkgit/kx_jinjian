<template>
  <div class="title-box-wrapper clearfix" :style="{ width, height }">
    <slot name="bg" />

    <div class="title-box-bg">
      <!-- <div class="bg-left-top" />
      <div class="bg-left-bottom" />
      <div class="bg-right-top" />
      <div class="bg-right-bottom" /> -->
    </div>
    <div class="title-box-header" v-if="title">
      <div class="header-wrapper">
        <span class="text-wrapper" :class="{ 'long-title': title.length > 4 }">
          <span class="header-text">{{ title }}</span>
          <!-- <span class="header-annotation">{{ annotation }}</span> -->
        </span>
        <slot name="operate">
          <span class="header-view-all" @click="$emit('view-all')">查看更多</span>
        </slot>
      </div>

      <!-- <div class="header-border" /> -->
    </div>
    <section class="section-part">
      <slot />
    </section>
  </div>
</template>

<script>
export default {
  props: {
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "100%",
    },
    title: {
      type: String,
      default: "",
    },
    annotation: {
      type: String,
      default: "",
    },
    annotation: {
      type: String,
      default: "",
    },
  },
};
</script>

<style lang="scss" scoped>
.title-box-wrapper {
  box-sizing: border-box;
  position: relative;
  margin-bottom: 20px;
  padding: 10px 0;

  &.wrapper-top {
    .title-box-bg {
      background-image: url("../assets/image/titleBox1.png");
      background-size: 100% 100%;
    }
  }

  &.wrapper-all {
    .title-box-bg {
      background-image: url("../assets/image/titleBox2.png");
      background-size: 100% 100%;
    }
  }

  .title-box-bg {
    width: 100%;
    height: 100%;
    position: absolute;
    background-image: url("../assets/image/titleBox.png");
    background-size: 100% 100%;

    .bg-left-top {
      width: 30px;
      height: 70px;
      top: -16px;
      left: -10px;
      position: absolute;
      background-size: 100% 100%;
      background-image: url("../assets/image/leftTop.png");
    }

    .bg-left-bottom {
      width: 142px;
      height: 54px;
      left: -19px;
      bottom: -24px;
      position: absolute;
      background-size: 100% 100%;
      background-image: url("../assets/image/leftBottom.png");
    }

    .bg-right-top {
      width: 79px;
      height: 52px;
      top: -25px;
      right: -27px;
      position: absolute;
      background-image: url("../assets/image/rightTop.png");
    }

    .bg-right-bottom {
      width: 149px;
      height: 75px;
      right: -26px;
      bottom: -26px;
      position: absolute;
      background-image: url("../assets/image/rightBottom.png");
    }
  }

  .title-box-header {
    display: flex;
    justify-content: space-between;
    position: relative;
    height: 20px;
    margin: 2px 25px 0 28px;

    .header-wrapper {
      width: 100%;
      padding-bottom: 3px;
      position: absolute;
      bottom: 0px;
      display: flex;
      justify-content: space-between;
      align-items: center;
      gap: 10px;

      .text-wrapper {
        position: relative;
        display: inline-block;

        &.long-title::before {
          content: "";
          position: absolute;
          left: -8px;
          top: -4px;
          right: -8px;
          bottom: -4px;
          background: linear-gradient(
            135deg,
            rgba(10, 26, 58, 0.92) 0%,
            rgba(20, 48, 110, 0.75) 100%
          );
          -webkit-mask-image: linear-gradient(to right, transparent 220px, black 220px),
            linear-gradient(to bottom, transparent 20px, black 20px);
          mask-image: linear-gradient(to right, transparent 220px, black 220px),
            linear-gradient(to bottom, transparent 20px, black 20px);
          -webkit-mask-composite: source-in;
          mask-composite: intersect;
          pointer-events: none;
        }

        .header-text {
          color: transparent;
          background: linear-gradient(180deg, #ffffff 35%, #699bff 100%);
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          background-clip: text;
          text-shadow: 0px 0px 2px rgba(190, 209, 255, 0.6);
          font-weight: bold;
          font-size: 18px;
          white-space: nowrap;
        }

        .header-annotation {
          margin-left: 6px;
          color: #9dbec1;
          line-height: 18px;
        }
      }

      .header-line {
        flex: 1;
        min-width: 20px;
        height: 1px;
        background: linear-gradient(
          90deg,
          rgba(105, 155, 255, 0.6) 0%,
          rgba(105, 155, 255, 0.15) 100%
        );
      }

      .header-view-all {
        cursor: pointer;
        white-space: nowrap;
        color: transparent;
        background: linear-gradient(180deg, #ffffff 35%, #699bff 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        text-shadow: 0px 0px 2px rgba(190, 209, 255, 0.6);
        font-size: 14px;
      }
    }

    .header-border {
      position: absolute;
      bottom: 2px;
      right: 0;
      border: 0px;
      min-width: 34px;
      bottom: -2px !important;
      border-bottom: 3px solid #00f6ff !important;
    }
  }

  .section-part {
    height: calc(100% - 20px);
    position: relative;
    padding: 20px 20px 00px;
    overflow: auto;

    &::-webkit-scrollbar {
      width: 3px;
    }

    &::-webkit-scrollbar-track {
      background: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background: rgba(255, 255, 255, 0.2);
      border-radius: 2px;
    }
  }
}
</style>
