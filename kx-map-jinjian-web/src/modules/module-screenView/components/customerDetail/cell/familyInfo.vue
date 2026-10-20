<template>
  <div class="info-wrapper">
    <div class="info-wrapper-left">
      <leftMenu />
    </div>
    <div class="info-wrapper-right">
      <personInfo></personInfo>
    </div>
  </div>
</template>

<script>
import leftMenu from '../components/leftMenu';
import personInfo from '@/components/CustomerDetail/cell/personInfo';
export default {
  components: {
    personInfo,
    leftMenu
  },
  data() {
    return {

    };
  },
  computed: {

  },
  watch: {

  },
  mounted() {

  },
  methods: {

  },
};
</script>

<style lang="scss" scoped>
.info-wrapper {
  display: flex;
  &-left {
    width: 125px;
    padding-top: 15px;
  }
  &-right {
    margin-top: 30px;
    flex: 1;
    padding-left: 25px;
    border-left: 1px solid #eee;
    width: 100%;

    overflow-y: scroll;
  }
}
</style>