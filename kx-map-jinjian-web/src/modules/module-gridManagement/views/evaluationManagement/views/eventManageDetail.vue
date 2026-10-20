<!-- 事件管理详情页面 -->
 <template>
  <div class="event-manage-detail">
    <orgTitle title="事件详情" :imgIcon="imgIcon" goBack></orgTitle>
    <div class="content">
      <tableTitle title="投诉信息"></tableTitle>
      <div class="base-iform">
        <i-form ref="baseForm" :columns="baseColumns" label-width="180px" labelPosition="right"
                :gridColumns="2" style="width:66.5%">
        </i-form>
        <i-form ref="baseForm1" :columns="baseColumns1" label-width="180px" labelPosition="right"
                :gridColumns="3">
        </i-form>
      </div>
      <tableTitle title="处置信息"></tableTitle>
      <div class="base-iform">
        <i-form ref="handleColumns" :columns="handleColumns" label-width="130px"
                labelPosition="right" :gridColumns="3">
        </i-form>
        <i-form ref="handleColumns1" :columns="handleColumns1" label-width="130px"
                labelPosition="right" :gridColumns="3">
        </i-form>
      </div>
      <tableTitle title="所属网格信息"></tableTitle>
      <div class="base-form">
        <i-form ref="gridColumns" :columns="gridColumns" label-width="150px" labelPosition="right"
                :gridColumns="3">
        </i-form>
      </div>
    </div>
  </div>
</template>
 
 <script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
export default {
  components: {
    orgTitle, tableTitle
  },
  data() {
    return {
      imgIcon: require('@/modules/module-gridManagement/assets/images/eventIcon.png'),
      baseColumns: [
        { label: "投诉来源", key: "gridCode1", value: "", type: "text", disabled: true, },
        { label: "投诉时间", key: "gridCode12", value: "", type: "text", disabled: true, },
        { label: "投诉人", key: "gridCode13", value: "", type: "text", disabled: true, },
        { label: "投诉人联系电话", key: "gridCode14", value: "", type: "text", disabled: true, },
      ],
      baseColumns1: [
        { label: "行业", key: "gridCode111", value: "", type: "text", disabled: true, },
        { label: "发生场景", key: "gridCode1222", value: "", type: "text", disabled: true, },
        { label: "是否为拒收人民币现金服务", key: "gridCode1333", value: "", type: "text", disabled: true, },
        { label: "具体事由", key: "123123", value: "", type: "textarea", span: 3, disabled: true, },
      ],
      handleColumns: [
        { label: "处理状态", key: "gridCode11123", value: "", type: "text", disabled: true, },
      ],
      handleColumns1: [
        { label: "处置情况", key: "gridCode11123", value: "", type: "textarea", span: 3, disabled: true, },
      ],
      gridColumns: [
        { label: "所在地区", key: "222", value: "", type: "text", disabled: true, },
        { label: "网格名称", key: "12345", value: "", type: "text", disabled: true, },
        { label: "网格长", key: "asd", value: "", type: "text", disabled: true, },
        { label: "网格长联系电话", key: "2213asd2", value: "", type: "text", disabled: true, },
        { label: "所属银行机构", key: "asfsaf", value: "", type: "text", disabled: true, },
        { label: "网格责任网点", key: "asfasfasb", value: "", type: "text", disabled: true, },
        { label: "责任网点负责人", key: "fdgfdh", value: "", type: "text", disabled: true, },
        { label: "网点负责人联系电话", key: "bfdg", value: "", type: "text", disabled: true, },
      ]
    }
  }

}
 </script>
 
 <style lang="scss" scoped>
 .event-manage-detail {
    height: 100%;
 }
 .content {
  margin-top: 12px;
    height: calc(100% - 68px);
    padding: 16px;
    background: #fff;
 }
.base-iform {
}
</style>