<template>
  <IDialog title="专属二维码" width="20%" v-model="showDialog" @handleClose="handleClose"
           :showFooter="false">
    <div class="code-wrapper">
      <qrCodeComponent :text="codeLink"></qrCodeComponent>
    </div>

  </IDialog>
</template>

<script>
import qrCodeComponent from '../cell/qrCodeComponent'
import dialogMixin from '@/mixins/dialogMixin.vue';
export default ({
  mixins: [dialogMixin],
  components: {
    qrCodeComponent
  },
  props: {
    codeLink: String,
  }
})
</script>

<style lang="scss" scoped>
.code-wrapper {
  width: 100%;
  display: flex;
  justify-content: center;
}
</style>