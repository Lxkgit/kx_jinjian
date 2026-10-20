<template>
  <div class="data-overview-wrapper">
    <purpleOverviewCard title="网格" :detailData="gridDetailData"></purpleOverviewCard>
    <blueOverviewCard title="客户" :totalData="customerTotalData" :detailData="customerDetailData">
    </blueOverviewCard>
    <blueOverviewCard title="任务" :totalData="taskTotalData" :detailData="taskDetailData">
    </blueOverviewCard>
  </div>
</template>

<script>
import blueOverviewCard from '../components/blueOverviewCard';
import purpleOverviewCard from '../components/purpleOverviewCard';
import service from '../api';
export default ({
  components: {
    blueOverviewCard,
    purpleOverviewCard
  },
  data() {
    return {
      gridDetailData: [
        { label: '基础网格', value: 0, unit: '个', prop: 'baseGridNumber' },
        { label: '特色网格', value: 0, unit: '个', prop: 'featureGridNumber' },
      ],
      customerTotalData: { value: 0, unit: '个', prop: 'customNumber' },
      customerDetailData: [
        { label: '个人客户', value: 0, unit: '个', prop: 'personCustomNumber' },
        { label: '商户客户', value: 0, unit: '个', prop: 'businessCustomNumber' },
        { label: '公司客户', value: 0, unit: '个', prop: 'companyCustomNumber' },
      ],
      taskTotalData: { value: 0, unit: '项', prop: 'taskNumber' },
      taskDetailData: [
        { label: '已完成', value: 0, unit: '项', prop: 'finishTaskNumber' },
        { label: '待完成', value: 0, unit: '项', prop: 'unFinishTaskNumber' },
      ],
    }
  },
  mounted() {
    this.dataOverview()
  },
  methods: {
    dataOverview() {
      service.dataOverview().then(res => {
        const { data } = res
        this.setDataValue(this.gridDetailData, data)
        this.setDataValue(this.customerDetailData, data)
        this.setDataValue([this.customerTotalData], data)
        this.setDataValue([this.taskTotalData], data)
        this.setDataValue(this.taskDetailData, data)
      })
    },
    setDataValue(dataInfo, source) {
      for (let item of dataInfo) {
        item.value = source[item.prop]
      }
    },
  },
})
</script>

<style lang="scss" scoped>
.data-overview-wrapper {
  // height: 100%;
  // overflow: auto;
}
</style>