<template>
  <IDialog title="商机分配" width="60%" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <IDescriptions title="商机信息" :infoData="approveInfo"></IDescriptions>
    <el-divider></el-divider>
    <IDescriptions title="移交表单"></IDescriptions>
    <i-form ref="myForm" :columns="formColumns" labelPosition="right" label-width="100px">
      <template #receive>
        <el-autocomplete class="form-item" v-model="param.moveInManagerName"
                         :fetch-suggestions="querySearchAsync" placeholder="请输入工号进行查询"
                         @select="handleSelect">
          <template slot-scope="{ item }">
            <div class="item-name">{{ item.nickName }}</div>
            <span class="item-value">({{ item.managerNum }})</span>
          </template>
        </el-autocomplete>
      </template>
    </i-form>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import commonService from '@/modules/module-gridManagement/api';
export default ({
  mixins: [dialogMixin],
  data() {
    return {
      approveInfo: [
        { label: '客户名称', value: '', key: 'moveOutManagerName' },
        { label: '商机类型', value: '', key: 'moveOutManagerCode' },
        { label: '业务扩展方式', value: '', key: 'moveOutOrgName' },
        { label: '商机概览', value: '', key: 'moveInManagerName' },
        { label: '商机发布时间', value: '', key: 'moveInManagerCode' },
        { label: '商机结束时间', value: '', key: 'moveInOrgName' },
      ],
      formColumns: [
        {
          label: '选择接收人',
          type: 'slot',
          slotName: 'receive',
          required: true,
        },
      ],
      param: {
        moveInManagerName: ''
      }
    }
  },
  methods: {
    querySearchAsync(queryString, cb) {
      if (queryString) {
        this.param.moveInManagerName = queryString
        const param = {
          managerNum: queryString
        }
        commonService.getByManagerNum(param).then(res => {
          const { data } = res
          cb(data)
        })
      } else {
        this.param.moveInManagerName = ''
        cb([])
      }
    },
    handleSelect(val) {
      const { dept: { deptName, orgNum }, managerNum, nickName } = val
      this.param.moveInManagerCode = managerNum
      this.param.moveInManagerName = nickName
      this.param.moveInOrgCode = orgNum
      this.param.moveInOrgName = deptName
    },
    handleClose() { },
    handleConfirm() { }
  },
})
</script>

<style lang="scss" scoped>
.form-item {
  width: 100%;
}
.item-name,
.item-value {
  display: inline-block;
  line-height: 1;
}
.item-value {
  font-size: 12px;
  color: #999;
}
</style>