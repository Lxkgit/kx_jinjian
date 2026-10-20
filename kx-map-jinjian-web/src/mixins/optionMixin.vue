<script>
import commonService from '@/modules/module-gridManagement/api';
export default ({
  methods: {
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
  },
})
</script>
