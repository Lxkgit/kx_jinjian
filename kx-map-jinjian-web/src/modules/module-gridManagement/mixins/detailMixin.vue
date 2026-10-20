<script>
import service from '../api';
const DEFAULT_TAB = [
  { label: '走访信息', value: '3', active: false },
]
const NORMAL_TAB = [
  { label: '客户信息', value: '1', active: false },
  { label: '客户旅程', value: '4', active: false },
]
const BUILDING_TAB = [
  { label: '建档信息', value: '2', active: false },
]
export default ({
  data() {
    return {
      activeName: "1",
      tabList: [],
      modelId: null,
    }
  },
  computed: {
    customerId() {
      return this.$store.getters["gridManagement/customerId"];
    },
    customerParam() {
      return this.$store.getters['gridManagement/customerParam']
    },
    customerType() {
      return this.customerParam.customerType
    }
  },
  mounted() {
    if (this.$route.params.activeName) {
      this.activeName = this.$route.params.activeName
    }
    this.getTypeByPhoneAndIdentity()
  },
  methods: {
    getTypeByPhoneAndIdentity() {
      const { phone, identity } = this.customerParam
      const param = {
        phone,
        identity
      }
      service.getTypeByPhoneAndIdentity(param).then(res => {
        const { data } = res
        this.modelId = data
        if (this.customerParam.type != '3') {
          if (data) {
            this.tabList = [...NORMAL_TAB, ...BUILDING_TAB, ...DEFAULT_TAB]
          } else {
            this.tabList = [...NORMAL_TAB, ...DEFAULT_TAB]
          }
          this.activeName = '1'
          this.handleClick()
        } else {
          this.getIsBankCustomer()
        }
      })
    },
    getIsBankCustomer() {
      const { phone, identity } = this.customerParam
      const param = {
        identity,
        phone
      }
      service.getIsBankCustomer(param).then(res => {
        const { msg } = res
        if (msg == '1') {
          this.tabList = [...NORMAL_TAB, ...BUILDING_TAB, ...DEFAULT_TAB]
          this.activeName = '1'
        } else {
          this.tabList = [...BUILDING_TAB, ...DEFAULT_TAB]
          this.activeName = '2'
        }
        this.handleClick()
      })
    },
    handleClick(tab) {
      for (let item of this.tabList) {
        if (item.value === this.activeName) {
          item.active = true
        } else {
          item.active = false
        }
      }
    }
  },
})
</script>