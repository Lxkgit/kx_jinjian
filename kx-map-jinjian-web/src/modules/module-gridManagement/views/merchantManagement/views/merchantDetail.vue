<template>
  <PageContent>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane v-for="item in tabList" :label="item.label" :key="item.value" :name="item.value">
        <merchantInfo v-if="activeName == '1' && item.active" :customerParam="customerParam">
        </merchantInfo>
        <FileDetail v-if="activeName == '2' && item.active" :customerModelType="customerType" :customerParam="customerParam"
          :modelId="modelId">
        </FileDetail>
        <VisitInfo v-if="activeName == '3' && item.active" color="#4ccbcd" backgroundColor="#1B7A7B" customerType="2"
          :customerParam="customerParam"></VisitInfo>
        <CustomerTrip v-if="activeName == '4' && item.active" :customerParam="customerParam" color="#4ccbcd"
          backgroundColor="#edfafa">
        </CustomerTrip>
      </el-tab-pane>
    </el-tabs>
  </PageContent>
</template>

<script>
import merchantInfo from '@/components/BaseDetail/cell/merchantInfo';
import detailMixin from '@/modules/module-gridManagement/mixins/detailMixin.vue'
export default ({
  mixins: [detailMixin],
  components: {
    merchantInfo,
  },
});
</script>

<style lang="scss" scoped>
.merchant-detail-wrapper {
  width: 100%;
  height: 100%;
}
</style>
