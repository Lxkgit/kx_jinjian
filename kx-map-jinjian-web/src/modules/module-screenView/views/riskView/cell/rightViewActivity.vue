<template>
  <div class="right-view-wrapper">
    <titleBox
      class="wrapper-top"
      height="960px"
      title="营销活动名单"
      annotation=""
    >
      <eventCustomer
        v-for="item in list"
        :key="item.id"
        :itemData="item"
      ></eventCustomer>
    </titleBox>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import eventCustomer from "@/modules/module-screenView/viewComponents/components/eventCustomer";
import service from '../../screenView/api';
export default {
  components: {
    titleBox,
    eventCustomer,
  },
  computed: {
    activityDetail() {
      return this.$store.getters['screenView/activityDetail']
    },
  },
  watch: {
    activityDetail:{
      handler(v) {
        this.initData(v)
      },
      immediate:true
    }
  },
  data() {
    return {
      id: 12,
      list:[]
    };
  },
  created() {},
  mounted() {},
  methods: {
    async initData(v){
      let rst  =  await service.activitiesList({
        id: v.id,
        pageNum: 1,
        pageSize: 100,
      })
      this.list = rst.rows
    }
  },
};
</script>

<style lang="scss" scoped>
.right-view-wrapper {
  position: relative;
}
</style>
