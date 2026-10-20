<template>
  <transition name="fade">
    <div v-if="showDialog" class="dialog">
      <div class="mask" />
      <div class="dialog_body">
        <div class="icons">
          <svg-icon v-if="type == 'danger'" class="icon" icon-class="dangerIcon" />
          <svg-icon v-if="type == 'info'" class="icon" icon-class="infoIcon" />
          <svg-icon v-if="type == 'success'" class="icon" icon-class="successIcon" />
        </div>
        <div class="title bold">{{ title }}</div>
        <div class="content">{{ message }}</div>
        <div class="footer_btn">
          <el-button v-if="showConfirmBtn" type="primary" :loading="confirmBtn" @click="confirm">{{ confirmBtnText }}</el-button>
          <el-button v-if="type!='success' && showCancelBtn" @click="cancel">{{ cancelBtnText }}</el-button>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  data() {
    return {
      showDialog: false,
      title: '',
      message: '',
      type: 'info', // danger, success, info
      confirmBtn: false,
      confirmBtnText: '确 定',
      cancelBtnText: '取 消',
      showConfirmBtn: true,
      showCancelBtn: true
    }
  },
  methods: {
    confirm() {},
    cancel() {}
  }
}
</script>

<style scoped lang="scss">
.fade-enter-active, .fade-leave-active {
  transition: all .2s;
}
.fade-enter, .fade-leave-active {
  opacity: 0
}
.dialog{
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 99999;
    .mask{
        position: absolute;
        top: 0;
        left: 0;
        width: 100vw;
        height: 100vh;
        background: #010101;
        opacity: 0.52;
        z-index: 1;
    }
    .dialog_body{
        z-index: 2;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 432px;
        min-height: 192px;
        background: #FFFFFF;
        box-shadow: 0px 4px 12px 0px rgba(0,0,0,0.2);
        border-radius: 10px;
        padding: 32px;
        padding-left: 70px;
        padding-bottom: 68px;
        .icons{
            width: 22px;
            height: 22px;
            position: absolute;
            top: 32px;
            left: 32px;
            .icon{
                width: 100%;
                height: 100%;
            }
        }
        .title{
            font-size: 16px;
            font-weight: 500;
            color: #323548;
            margin-bottom: 12px;
            line-height: 24px;
        }
        .content{
            font-size: 14px;
            font-weight: 400;
            color: #898EAC;
            line-height: 22px;
        }
    }
    .footer_btn{
        position: absolute;
        bottom: 24px;
        right: 24px;
        .el-button{
            margin: 0;
            margin-right: 8px;
            min-width: 65px;
            height: 32px;
            // min-width: 0;
            padding: 5px 16px;
            &:last-child{
                margin-right: 0;
            }
        }
    }
}
</style>
