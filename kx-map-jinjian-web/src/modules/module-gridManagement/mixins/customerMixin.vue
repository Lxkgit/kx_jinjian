<script>
import service from '@/modules/module-gridManagement/api/customer.js';
import distributeEdit from '@/modules/module-gridManagement/components/distributeEdit.vue';
import commonService from '@/modules/module-gridManagement/api';
export default ({
  dicts: ['custom_type', 'files_status', 'customer_type'],
  components: {
    distributeEdit
  },
  data() {
    return {
      showDistribute: false,
      editInfo: null,
      clientType: null,
    }
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...this.TYPE_STYLE,
          ...formInfo
        }
        this.loading = true
        service.customerList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows.map(item => {
            return {
              ...item,
              isBuild: item.filesStatus == '0' || !item.filesStatus,
              isUpdate: item.filesStatus == '1'
            }
          })
          this.total = total
          this.loading = false
          this.multiSelected = []
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.multiSelected = [];
          this.loading = false
        })
      })
    },
    lazyLoad(node, resolve) {
      const { value } = node
      const param = {
        parentCode: value
      }
      commonService.getPaAddrInfoList(param).then(res => {
        const { data } = res
        data.forEach(item => {
          item.leaf = !item.isLeaf
        })
        resolve(data)
      })
    },
    async deptTreeSelect() {
      const { data } = await commonService.deptTree()
      return data
    },
    querySearch(queryString, cb) {
      // 调用 callback 返回建议列表的数据
      cb([{ "value": "三全鲜食（北新泾店）", "address": "长宁区新渔路144号" },]);
    },
    distributeHandle(data) {
      this.editInfo = [data]
      this.showDistribute = true
    },
    distributeBatch() {
      this.editInfo = this.multiSelected
      this.showDistribute = true
    },
    customerViewHandle(data) {
      const { customerIdentity, customerType, customerPhone } = data
      const param = {
        identity: customerIdentity,
        phone: customerPhone,
        customerType,
        type: this.clientType,
      }
      this.$store.commit('gridManagement/SET_CUSTOMER_PARAM', param)
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'view')
      if (customerType == '1') {
        this.$router.push({ name: 'CustomerDetail', })
      } else {
        this.$router.push({ name: 'MerchantDetail', })
      }
    },
    merchantViewHandle(data) {
      const { id } = data
      this.$store.commit('gridManagement/SET_CUSTOMER_ID', id)
      this.$router.push({ name: 'MerchantDetail' })
    },
  },
})
</script>
