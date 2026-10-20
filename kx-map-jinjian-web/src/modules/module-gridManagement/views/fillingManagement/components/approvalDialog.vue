<template>
  <i-dialog :title="title" v-model="showDialog" width="50%" @handleClose="handleClose"
            :showConfirm="false">
    <IDescriptions title="客户信息"></IDescriptions>
    <i-table ref="table" :data="tableData" align="center" stripe :allColumns="columns"
             :showPagination="false">
      <template #action="scope">
        <el-button type="text" @click="showDialog=false,viewHandle(scope.row)">详情</el-button>
      </template>
    </i-table>
    <el-divider></el-divider>
    <IDescriptions title="移交信息" :infoData="approveInfo"></IDescriptions>
    <div class="approval-status" v-if="status!=='1' && editInfo.moveStatus!='0'">
      <img :src="require(`@/modules/module-gridManagement/views/fillingManagement/assets/approval_${editInfo.moveStatus}.png`)"
           alt="">
    </div>
    <template #foot>
      <span v-if="status==='1'" class="handle-wrapper">
        <el-button type="primary" @click="confirmHandle(2)">拒 绝</el-button>
        <el-button type="primary" @click="confirmHandle(1)">同 意</el-button>
      </span>
    </template>
  </i-dialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin';
import customerDetailMixins from '@/mixins/customerDetailMixins';
import service from '../api';
const TITLE_TYPE = {
  1: '移交审批',
  2: '详情',
}
const STATUS_TYPE = {
  1: '同意',
  2: '拒绝'
}
export default ({
  mixins: [dialogMixin, customerDetailMixins],
  dicts: ['custom_type_three'],
  props: {
    status: {
      type: String, // 1-审批,2-详情
      default: '1'
    },
    editInfo: {
      type: Object
    },
    activeIndex: {
      type: String,
    }
  },
  data() {
    return {
      approveInfo: [
        { label: '移交人', value: '', key: 'moveOutManagerName' },
        { label: '移交人工号', value: '', key: 'moveOutManagerCode' },
        { label: '移交人所属机构', value: '', key: 'moveOutOrgName' },
        { label: '近两年客户通过率', value: '', span: 2, key: 'twoYearLoanPassRate', hidden: () => this.activeIndex != '3' },
        { label: '上月正常收息率', value: '', key: 'lastMonthNormalInteRate', hidden: () => this.activeIndex != '3' },
        { label: '接收人', value: '', key: 'moveInManagerName', hidden: () => this.activeIndex == '3' },
        { label: '接收人工号', value: '', key: 'moveInManagerCode', hidden: () => this.activeIndex == '3' },
        { label: '接收人所属机构', value: '', key: 'moveInOrgName', hidden: () => this.activeIndex == '3' },
        { label: '移交说明', value: '', key: 'moveManual' },
        { label: '申请日期', value: '', key: 'moveDate' },
      ],
      columns: [
        { label: '客户名称', prop: 'customerName' },
        {
          label: "客户类型", prop: "customerType", formatter: ({ customerType }) => {
            return this.dict.label.custom_type_three[customerType]
          }
        },
        {
          label: "证件号", prop: "customerIdentity", formatter: ({ customerIdentity }) => {
            return this.$identityMasking(customerIdentity);
          },
        },
        {
          label: "手机号", prop: "customerPhone", formatter: ({ customerPhone }) => {
            return this.$phoneMasking(customerPhone)
          },
        },
        { label: '操作', slot: true },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "detailHandle",
        },
      ]
    }
  },
  computed: {
    title() {
      return TITLE_TYPE[this.status]
    },
    tableData() {
      return [this.editInfo]
    }
  },
  methods: {
    initData() {
      this.approveInfo.forEach(item => {
        if (item.key === 'moveDate') {
          item.value = this.parseTime(this.editInfo[item.key], '{y}-{m}-{d}')
        } else if (['twoYearLoanPassRate', 'lastMonthNormalInteRate'].includes(item.key)) {
          item.value = this.editInfo[item.key]?`${this.editInfo[item.key]}%`:''
        } else {
          item.value = this.editInfo[item.key]
        }
      })
    },
    handleClose() {
      this.showDialog = false
    },
    confirmHandle(type) {
      const param = {
        ...this.editInfo,
        moveStatus: type
      }
      this.$modal.confirm(`确定${STATUS_TYPE[type]}审批？`).then(() => {
        service.handoverJudgeBatch([param]).then(res => {
          this.$modal.msgSuccess('审批成功')
          this.handleClose()
          this.$parent.fetchList()
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
.handle-wrapper {
  margin-left: 10px;
}
.approval-status {
  position: absolute;
  top: 20px;
  right: 30px;
}
</style>