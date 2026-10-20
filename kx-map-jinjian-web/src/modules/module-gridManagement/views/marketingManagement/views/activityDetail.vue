<template>
  <PageContent>
    <SubTitle title="营销活动成果"></SubTitle>
    <IDescriptions title="" :infoData="activityInfo"></IDescriptions>
    <SubTitle title="扫码人员概况"></SubTitle>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
             @sizeChange="sizeChange">
    </i-table>
  </PageContent>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import service from '../api'
const TYPE = {
  0: '是',
  1: '否'
}
export default ({
  mixins: [pageMixin],
  data() {
    return {
      activityInfo: [
        { label: '活动名称', value: '', key: 'actName' },
        // { label: '活动网格', value: '', key: '' },
        { label: '活动时间', value: '', key: 'actTimeList' },
        { label: '活动参与人数', value: '', key: 'actParticipateNum' },
        { label: '扫码人数', value: '', key: 'scanCodeNum' },
        { label: '信息收集人数', value: '', key: 'infoCollectNum' },
        { label: '有效信息人数', value: '', key: 'infoCollectEffectNum' },
        { label: '业务办理人数', value: '', key: 'busProcNum' },

      ],
      columns: [
        { label: '姓名', prop: 'customerName' },
        { label: '手机号', prop: 'customerPhone' },
        { label: '身份证号', prop: 'customerIdentity' },
        {
          label: '是否扫码', prop: 'isScan', formatter: ({ isScan }) => {
            return TYPE[isScan]
          }
        },
        { label: '扫码日期', prop: 'scanTime' },
        {
          label: '是否填写信息', prop: 'isFillIn', formatter: ({ isFillIn }) => {
            return TYPE[isFillIn]
          }
        },
        { label: '信息填写日期', prop: 'fillInTime' },
        {
          label: '信息是否有效', prop: 'isInfoEffect', formatter: ({ isInfoEffect }) => {
            return TYPE[isInfoEffect]
          }
        },
        {
          label: '是否办理业务', prop: 'isBuiness', formatter: ({ isBuiness }) => {
            return TYPE[isBuiness]
          }
        },
        { label: '业务办理日期', prop: 'buinessTime' },
      ]
    }
  },
  computed: {
    id() {
      return this.$route.params.id
    }
  },
  mounted() {
    this.fetchList()
    this.getActResult()
  },
  methods: {
    fetchList() {
      this.loading = true
      const param = {
        id: this.id,
        ...this.formInline
      }
      service.activitiesList(param).then(res => {
        const { total, rows } = res
        this.tableData = rows
        this.total = total
        this.loading = false
      })
    },
    getActResult() {
      service.getActResult(this.id).then(res => {
        const { data } = res
        this.activityInfo.forEach(item => {
          item.value = Array.isArray(data[item.key]) ? data[item.key].map(item => this.parseTime(item, '{y}年{m}月{d}日')).join('-') : data[item.key]
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>