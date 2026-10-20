<template>
  <changeRecord :categoryId="categoryId"></changeRecord>
</template>

<script>
import changeRecord from "../cell/changeRecord.vue";
export default {
  name: 'GridManageChange',
  components: { changeRecord },
  data() {
    return {
      categoryId: 1,
    };
  },
};
</script>

<style lang="scss" scoped>
</style>