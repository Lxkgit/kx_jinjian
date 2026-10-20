<script>
export default ({
  props: {
    value: {
      type: Boolean,
    }
  },
  data() {
    return {
      showDialog: this.value,
      isLoading:false,
    }
  },
  watch: {
    value: {
      handler(val) {
        this.showDialog = val
        if (val) {
          this.initData && this.initData()
        }
      },
    },
    showDialog: {
      handler(val) {
        this.$emit('input', val)
      }
    },
  },
  methods: {
    initData() { },
    handleClose() { },
    handleConfirm() { },
  },
})
</script>