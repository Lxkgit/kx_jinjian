<template>
  <div class="screen-left" :class="{ 'screen-left-show': leftShow }">
    <!-- <div class="repeat-down" /> -->
    <div class="main-wrapper">
      <slot></slot>
    </div>
    <!-- <div class="left-detail" :class="{ 'left-detail-show': show }">
      <popover></popover>
      <div class="main-btn" @click="viewLeft" :class="{ 'main-btn-show': show }">
        {{ show ? "收起" : "展开" }}
        <img v-if="show" src="../assets/image/toLeft.png" style="margin-top: 8px"/>
        <img v-else src="../assets/image/toRight.png" style="margin-top: 8px" />
      </div>
    </div> -->

  </div>
</template>

<script>
import mapSwitch from "../components/operate/mapSwitch";
import mapservice from "../components/operate/mapservice";
import searchPart from "../components/searchPart";
import popover from "../components/popover";
export default {
  components: {
    mapSwitch,
    searchPart,
    mapservice,
    popover,
  },
  props: {
    leftShow: {
      type: Boolean,
    },
  },
  data() {
    return {
      show: true,
    };
  },
  methods: {
    viewLeft() {
      this.show = !this.show;
      this.$emit("viewLeft", this.show);
    },
  },
};
</script>

<style lang="scss" scoped>
@mixin columnFlex {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

@font-face {
  font-family: numSpe;
  src: url("../assets/font/baseNum.ttf");
}

.ml-margin {
  margin: 0 10px;
}

.town {
  position: relative;
  top: -30px;
}

.screen-left {
  position: absolute;
  width: 548px;
  height: 1009px;
  top: 100px;
  left: 0px;

  &.screen-left-show {
    left: -440px;
    background-size: 100% 100%, 440px 89px;

    .repeat-down {
      width: 440px;
      right: -440px;
    }
  }

  // .repeat-down {
  //   width: 0px;
  //   height: 24px;
  //   position: absolute;
  //   bottom: 0px;
  //   right: 0px;
  //   background: url("~@/modules/module-screenView/assets/image/repeat-down.png") repeat;
  //   transition: all 0.3s linear;
  // }

  .main-wrapper {
    width: 476px;
    height: 920px;
    position: absolute;
    top: 10px;
    left: 40px;
    pointer-events: auto;

    .top-list {
      margin: 15px 10px 0 10px;
      display: flex;
      justify-content: space-around;

      .top-item {
        display: flex;

        .top-left {
          @include columnFlex;

          .tl-num {
            font-family: numSpe;
            font-size: 22px;
            color: white;
            margin-bottom: 2px;
          }

          .tl-text {
            font-size: 16px;
            color: #00f6ff;
          }
        }

        .top-right {
          width: 1px;
          height: 33px;
          background-image: url("~@/modules/module-screenView/assets/image/Verticalline.png");
          background-size: 100% 100%;
          margin-left: 20px;
        }
      }
    }

    .township-list {
      display: flex;
      flex-wrap: wrap;

      .township-item {
        width: 48%;
        margin-right: 2%;
      }
    }
  }

  .operate-wrapper {
    position: absolute;
    left: 490px;
    top: 40px;
    z-index: 999;
    pointer-events: auto;
  }

  .bottom-wrapper {
    position: absolute;
    bottom: 25px;
    left: 465px;
    pointer-events: auto;
  }
}

.left-detail {
  color: #fff;
  width: 440px;
  height: 450px;
  position: absolute;
  top: 150px;
  left: 0px;
  background: rgba(42, 46, 49, 0.7);
  transition: all 0.3s linear;
  // background: url("~@/modules/module-screenView/assets/image/bg-left.png"),
  //   url("~@/modules/module-screenView/assets/image/repeat-up.png") 490px top
  //     no-repeat;
  padding: 5px 15px 10px 20px;
  box-sizing: border-box;
  pointer-events: auto;
  border-radius: 0 15px 15px 0;

  &.left-detail-show {
    left: 440px;
    background-size: 100% 100%, 440px 89px;
  }

  .main-btn {
    position: absolute;
    right: -60px;
    top: 0px;
    font-size: 16px;
    cursor: pointer;
    background: url("../assets/image/box.png");
    width: 40px;
    height: 95px;
    background-size: 100% 100%;
    color: #fff;
    word-wrap: break-word;
    padding: 16px 11px;
    line-height: 20px;
    pointer-events: auto;

    &.main-btn-show {
      right: -40px;
    }
  }
}
</style>