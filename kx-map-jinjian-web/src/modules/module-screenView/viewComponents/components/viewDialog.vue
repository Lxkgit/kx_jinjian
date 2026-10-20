<template>
  <div class="view-dialog-wrapper" v-if="showDialog">
    <div class="dialog-content-wrapper">
      <div class="content-header">
        <span class="content-title">
          {{ title }}
          <span class="content-annotation">{{ annotation }}</span>
        </span>
        <span class="el-icon-close content-close" @click="showDialog = false"></span>
      </div>
      <div class="content">
        <slot />
      </div>
    </div>
  </div>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin";
export default {
  mixins: [dialogMixin],
  props: {
    title: {
      type: String,
      default: "xxxsss",
    },
    annotation: {
      type: String,
      default: "fgfgsf",
    },
  },
};
</script>

<style lang="scss" scoped>
.view-dialog-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;

  .dialog-content-wrapper {
    // min-width: 50%;
    padding: 40px 20px 20px 20px;
    background: url(../assets/dialog-bg.png) no-repeat;
    background-size: 100% 100%;

    .content-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      height: 40px;
      margin-bottom: 10px;
      background: url(../assets/dialog-header.png) no-repeat;
      background-size: 100% 100%;
      padding-bottom: 15px;

      .content-title {
        padding-left: 26px;
        font-family: YouSheBiaoTiHei;
        font-size: 22px;
        font-weight: normal;
        line-height: 30px;
        color: #FFFFFF;

        position: relative;

        .content-annotation {
          font-size: 14px;
          color: #9dbec1;
        }
      }

      .content-close {
        font-size: 16px;
        background: url(../assets/dialog-close.png) no-repeat;
        background-size: 100% 100%;
        height: 40px;
        width: 40px;

        color: #fff;
        cursor: pointer;

        &.el-icon-close:before {
          content: ''
        }
      }
    }

    .content {
      padding: 0 10px;
    }
  }
}
</style>
