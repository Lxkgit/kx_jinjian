<template>
  <div class="info-table">
    <div class="row" v-for="(item, index) in data" :key="index">
      <div class="label">
        {{ item[0] }}
      </div>

      <div class="value">
        {{ item[1] }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Array,
      default: () => [],
    },
  },
};
</script>

<style scoped lang="scss">
.info-table {
  border: 1px solid #ebeef5;

  .row {
    display: flex;

    border-bottom: 1px solid #ebeef5;

    &:last-child {
      border: none;
    }

    .label {
      width: 120px;

      background: #fafafa;

      padding: 10px;

      color: #666;
    }

    .value {
      flex: 1;

      padding: 10px;
    }
  }
}
</style>
