<template>
  <div>
    <i-dialog
      :title="'二维码/链接'"
      v-model="showDialog"
      width="60%"
      @handleClose="handleClose"
      @handleConfirm="handleConfirm"
      :showFooter="false"
    >
      <div class="cotentBox">
        <span class="title">营销活动二维码</span>
        <qrCodeComponent :text="codeLink"></qrCodeComponent>
      </div>
      <div class="cotentBox">
        <span class="title">营销活动链接</span>
        <el-link class="link" type="primary" @click="toLink">{{
          defaultValue.link || "http://www.baidu.com/"
        }}</el-link>
      </div>
    </i-dialog>
  </div>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import qrCodeComponent from '../cell/qrCodeComponent'

export default {
  name: "qrLinkDialog",
  mixins: [dialogMixin],
  components: {
    qrCodeComponent
  },
  computed:{
    codeLink(){
      return this.defaultValue.link || "http://www.baidu.com/"
    }
  },
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {};
  },
  methods: {
    toLink() {
      window.open(
        this.defaultValue.link || "http://www.kangxukeji.com/",
        "_blank"
      );
    },
    handleClose() {
      this.showDialog = false;
    },
    handleConfirm() {},
  },
};
</script>

<style lang="scss" scoped>
.cotentBox {
  display: flex;
  align-items: flex-start;
  margin-bottom: 20px;
  .title {
    height: 20px;
    font-size: 18px;
    font-weight: bold;
    margin-right: 20px;
  }
  .qrCodeImg {
    width: 150px;
    height: 150px;
  }
  .link {
    height: 20px;
    line-height: 20px;
  }
}
</style>