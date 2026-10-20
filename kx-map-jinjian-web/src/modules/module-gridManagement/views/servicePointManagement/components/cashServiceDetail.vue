<!-- 现在服务点详情 -->
<template>
  <div class="cash-service-detail">
    <orgTitle title="" goBack></orgTitle>
    <div class="cash-content">
      <tableTitle title="基础信息">
        <template #right>
          <div v-if="showEdit">
            <el-button type="primary" @click="edit">编辑</el-button>
          </div>
          <div v-else>
            <el-button type="primary" @click="saveEdit">保存</el-button>
            <el-button @click="showEdit = true">取消</el-button>
          </div>
        </template>
      </tableTitle>
      <!-- 服务点禁止修改的信息 -->
      <div class="base-iform" style="width:66.5%">
        <i-form ref="baseForm" :columns="baseColumns" label-width="170px" labelPosition="right" :gridColumns="2">
        </i-form>
      </div>
      <div class="base-iform">
        <i-form ref="cashColumns" :columns="cashColumns" label-width="130px" labelPosition="right" :gridColumns="3">
        </i-form>
      </div>

      <tableTitle title="现金服务信息"></tableTitle>
      <smallTitle title="本期零钞兑换业务" style="margin-top: 24px;"></smallTitle>
      <div class="base-iform" style="width:66.5%">
        <i-form ref="moneyform1" :columns="moneyform1" label-width="130px" labelPosition="right" :gridColumns="2">
        </i-form>
      </div>
      <smallTitle title="零钞兑换业务累计" style="margin-top: 24px;"></smallTitle>
      <div class="base-iform" style="width:66.5%">
        <i-form ref="moneyform2" :columns="moneyform2" label-width="130px" labelPosition="right" :gridColumns="2">
        </i-form>
      </div>
      <div class="line"></div>

      <div class="base-iform">
        <i-form ref="dataform" :columns="dataform" label-width="160px" labelPosition="right" :gridColumns="3">
        </i-form>
      </div>
    </div>
  </div>
</template>

<script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import smallTitle from "@/modules/module-gridManagement/components/smallTitle.vue";
export default {
  components: {
    orgTitle, tableTitle, smallTitle
  },
  data () {
    return {
      showEdit: true,
      baseColumns: [
        { label: "现金服务点名称", key: "gridCode1", value: "", type: "text", disabled: true },
        { label: "现金服务点责任银行", key: "gridCode2", value: "", type: "text", disabled: true },
        { label: "现金服务点责任网点", key: "gridCode3", value: "", type: "text", disabled: true },
        { label: "现金服务点责任网点地址", key: "gridCode4", value: "", type: "text", disabled: true },
      ],
      cashColumns: [
        { label: "服务点类别", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "现金服务点地址", key: "gridCode2", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "选点位置", key: "gridCode3", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "现金服务点责任人", key: "gridCode4", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "联系电话", key: "gridCode5", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "建设模式", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "推进情况", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "计划启用时间", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "服务模式", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
      ],
      moneyform1: [
        { label: "总笔数", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "零钱包兑换个数", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "总金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "零钱包兑换金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
      ],
      moneyform2: [
        { label: "总笔数", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "零钱包兑换个数", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "总金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "零钱包兑换金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
      ],
      dataform: [
        { label: "本期开展残币收兑金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "累计开展残币收兑金额", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "是否布放小面额现金自助兑换设备", key: "gridCode1", value: "", type: "text", disabled: () => this.showEdit, },
        { label: "好的经验/存在问题", key: "gridCode1", value: "", type: "textarea", span: 3, disabled: () => this.showEdit, },
      ]
    }
  },
  methods: {
    edit () {
      this.showEdit = false;
    },
    saveEdit () {

    },
  },
}
</script>

<style lang="scss" scoped>
.cash-content {
  width: 100%;
  padding: 16px;
  background: #fff;
  margin-top: 12px;
}

.base-iform {
  margin-top: 12px;
}

.line {
  width: 100%;
  height: 1px;
  background: #e8e8e8;
  margin-top: 12px;
}
</style>
