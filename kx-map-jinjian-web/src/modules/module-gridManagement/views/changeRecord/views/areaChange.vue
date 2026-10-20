<template>
  <changeRecord :categoryId="categoryId"></changeRecord>
</template>

<script>
import changeRecord from "../cell/changeRecord.vue";
export default {
  name: 'AreaChange',
  components: { changeRecord },
  data() {
    return {
      categoryId: 0,
    };
  },
};
</script>

<style lang="scss" scoped>
</style>