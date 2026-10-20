<template>
  <div class="badness-list-wrapper">
    <rankList className="badness-list" :columns="columns" :tableData="tableData" :rows="2"
              :background="background" :height="height" rowBorderRadius="4px"
              :intervalColor="intervalColor">
      <template #GridRatio="{row}">
        <div class="percent-wrapper">
          <span class="percent-num">{{ row.Ratio }}%</span>
          <div class="detail-percent">
            <span>
              <span class="inside">内</span>
              <span class="percent-num">{{ row.GridInsideRatio }}%</span>
            </span>
            <span>
              <span class="outside">外</span>
              <span class="percent-num">{{ row.GridOutsideRatio }}%</span>
            </span>
          </div>

        </div>
      </template>
    </rankList>
  </div>
</template>

<script>
import rankList from '../../rankList';
export default ({
  components: {
    rankList
  },
  props: {
    tableData: {
      type: Array,
    }
  },
  data() {
    return {
      background: 'linear-gradient(180deg,rgba(7, 192, 219, 0.3),rgba(7, 192, 219, 0.8)) ',
      intervalColor: 'rgba(150, 252, 253, 0.1)',
      height: 24,
      columns: [
        { label: '类型', prop: 'typeName', flex: 1.5 },
        { label: '当前值', prop: 'Bal', flex: 1.5 },
        { label: '较上月', prop: 'CompareMonthBal', flex: 1.2, number: true, color: '#8BF6FF' },
        { label: '较年初', prop: 'CompareYearBal', flex: 1.2, number: true, color: '#8BF6FF' },
        { label: '逾欠率/不良率', prop: 'GridRatio', flex: 3, slot: true },
      ],
    }
  },
})
</script>

<style lang="scss" scoped>
.badness-list-wrapper {
  width: 490px;
  margin-bottom: 10px;

  .percent-wrapper {
    display: flex;
    align-items: center;
    .percent-num {
      display: inline-block;
      width: 48%;
      color: #fffb8b;
    }
    .detail-percent {
      width: 50%;
      display: flex;
      flex-direction: column;
      .inside {
        display: inline-block;
        width: 16px;
        height: 16px;
        line-height: 14px;
        font-size: 12px;
        background: linear-gradient(0deg, #e57335, rgba(255, 120, 0, 0));
        border-radius: 50%;
        border: 1px solid #fb8c42;
        margin-right: 4px;
      }
      .outside {
        display: inline-block;
        width: 16px;
        height: 16px;
        line-height: 14px;
        font-size: 12px;
        background: linear-gradient(0deg, #35dfe5, rgba(26, 86, 101, 0));
        border-radius: 50%;
        border: 1px solid #35dfe5;
        margin-right: 4px;
      }
    }
  }
}
</style>