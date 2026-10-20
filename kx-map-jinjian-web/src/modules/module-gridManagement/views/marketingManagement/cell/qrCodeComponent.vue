<template>
  <canvas ref="qrCodeCanvas"></canvas>
</template>

<script>
import QRCode from 'qrcode'
export default ({
  components: {
    QRCode
  },
  props: {
    text: {
      type: String,
      required: true
    }
  },
  mounted() {
    this.generateQRCode()
  },
  methods: {
    generateQRCode() {
      const canvas = this.$refs.qrCodeCanvas
      QRCode.toCanvas(canvas, this.text, error => {
        if (error) console.error(error)
      })
    }
  }
})
</script>

<style lang="scss" scoped>
</style>