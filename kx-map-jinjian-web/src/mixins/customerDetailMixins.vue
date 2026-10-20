<script>
export default ({
  methods: {
    viewHandle(row) {
      const { customerType, customerIdentity, customerPhone } = row
      const param = {
        identity: customerIdentity,
        phone: customerPhone,
        customerType,
        type: '3'
      }
      this.$store.commit('gridManagement/SET_CUSTOMER_PARAM', param)
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'view')
      if (customerType === '1') {
        this.$router.push({ name: 'CustomerDetail', params: { activeName: '2' } })
      } else {
        this.$router.push({ name: 'MerchantDetail', params: { activeName: '2' } })
      }
    },
  },
})
</script>