<template>
  <PageContent>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane v-for="item in tabList" :label="item.label" :key="item.value" :name="item.value">
        <personalInfo v-if="activeName == '1' && item.active" :customerParam="customerParam">
        </personalInfo>
        <FileDetail v-if="activeName == '2' && item.active" customerModelType="1" :customerParam="customerParam"
          :modelId="modelId">
        </FileDetail>
        <VisitInfo v-if="activeName == '3' && item.active" color="#e8921c" backgroundColor="#7b581b" customerType="1"
          :customerParam="customerParam"></VisitInfo>
        <CustomerTrip v-if="activeName == '4' && item.active" :customerParam="customerParam" color="#e8921c"
          backgroundColor="#fdf4e8">
        </CustomerTrip>
      </el-tab-pane>
    </el-tabs>
  </PageContent>
</template>

<script>
import personalInfo from "@/components/BaseDetail/cell/personalInfo";
import detailMixin from '@/modules/module-gridManagement/mixins/detailMixin.vue'
export default {
  mixins: [detailMixin],
  components: {
    personalInfo,
  },
}
</script>

<style lang="scss" scoped>
.customer-detail-wrapper {
  width: 100%;
  height: 100%;
}
</style>
