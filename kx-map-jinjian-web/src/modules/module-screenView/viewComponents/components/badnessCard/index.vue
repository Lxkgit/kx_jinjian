<template>
  <div class="badness-card-wrapper">
    <loansCard class="card-item" :cardNum="badnessCardData.overDebtNum" cardLabel="逾欠余额"
               subLabel="逾欠率" :subNum="badnessCardData.overDebtRate">
    </loansCard>
    <loansCard class="card-item" :cardNum="badnessCardData.badnessNum" cardLabel="不良余额"
               subLabel="不良率" :subNum="badnessCardData.badnessRate">
    </loansCard>
  </div>
</template>

<script>
import loansCard from '../loansCard'
export default ({
  components: {
    loansCard
  },
  props: {
    badnessCardData: {
      type: Object,
    },
  },
})
</script>

<style lang="scss" scoped>
.badness-card-wrapper {
  display: flex;
  justify-content: space-between;

  .card-item {
    width: 178px;
    height: 80px;
  }
}
</style>