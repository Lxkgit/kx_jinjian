<template>
  <div class="card-item" :style="{'--height':itemHeight,'background-image':`url(${background})`}">
    <div class="card-title">{{ data.title }}</div>
    <div
      class="card-value"
      :title="`${formatValue(data.value, data.unit)}${_displayUnit || data.unit}`"
    >
      {{ formatValue(data.value, data.unit) }}<span class="unit">{{ _displayUnit || data.unit }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      default: () => ({
        title: '零钞预约金额',
        value: '1423432432423',
        unit: '元'
      })
    },
    itemHeight: {
      type: String,
      default: '92px'
    },
    background: {
        type: String,
        default: require('@/modules/module-gridManagement/assets/images/card-blue.png')
    }
  },
  data() {
    return {
      _displayUnit: this.data.unit
    }
  },
  methods: {
    formatValue(value, unit) {
      if (value === null || value === undefined || value === '') return '--';
      let num = Number(value);
      // 新增：单位为元且大于1万时，显示为万元
      if (unit === '元' && num >= 10000) {
        let wanVal = num / 10000;
        let parts = wanVal.toFixed(2).split('.');
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this._displayUnit = '万元';
        return parts.join('.');
      } else if (unit === '元') {
        let parts = num.toFixed(2).split('.');
        parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        this._displayUnit = '元';
        return parts.join('.');
      } else {
        let intVal = Math.round(num);
        this._displayUnit = unit;
        return intVal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.card-item {
  border-radius: 8px;
  padding: 16px 24px;
  color: #fff;
  min-width: 200px;
  height: var(--height);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  background-size: 100% 100%;
  .card-title {
    font-size: 14px;
    color: #FFFFFF;
  }
  .card-value {
    font-size: 22px;
    line-height: 22px;
    color: #FFFFFF;
    white-space: nowrap;         // 不换行
    overflow: hidden;            // 超出隐藏
    text-overflow: ellipsis;     // 超出显示...
    .unit {
      font-size: 14px;
      margin-left: 4px;
      color: #FFFFFF;
    }
  }
}
</style>