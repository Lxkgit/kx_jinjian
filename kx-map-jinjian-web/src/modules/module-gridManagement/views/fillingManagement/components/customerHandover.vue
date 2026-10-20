<template>
  <i-dialog title="客户移交" v-model="showDialog" width="60%" @handleClose="handleClose"
            @handleConfirm="handleConfirm">
    <IDescriptions title="客户信息"></IDescriptions>
    <i-table ref="table" :data="tableData" align="center" stripe :allColumns="columns"
             :showPagination="false">
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
    </i-table>
    <el-divider></el-divider>
    <IDescriptions title="移交表单"></IDescriptions>
    <i-form ref="myForm" :columns="formColumns" labelPosition="right" label-width="100px">
      <template #receive>
        <el-autocomplete class="form-item" v-model="param.moveInManagerName"
                         :fetch-suggestions="querySearchAsync" placeholder="请输入工号进行查询"
                         @select="handleSelect">
          <template slot-scope="{ item }">
            <div class="item-name">{{ item.nickName }}</div>
            <span class="item-value">{{ item.managerNum }}</span>
          </template>
        </el-autocomplete>
      </template>
    </i-form>
  </i-dialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin';
import pageMixin from '@/mixins/pageMixin';
import optionMixin from '@/mixins/optionMixin';
import service from '../api';
import commonService from '@/modules/module-gridManagement/api';
export default ({
  mixins: [dialogMixin, pageMixin, optionMixin],
  dicts: ['custom_type_three'],
  props: {
    selected: {
      type: Array
    }
  },
  data() {
    return {
      columns: [
        { label: '客户名称', prop: 'customerName' },
        {
          label: "客户类型", prop: "customerType", formatter: ({ customerType }) => {
            return this.dict.label.custom_type_three[customerType]
          }
        },
        { label: "证件号", prop: "customerIdentity" },
        { label: "手机号", prop: "customerPhone" },
        { label: '操作', slot: true },
      ],
      tableBtn: [
        {
          cname: '删除',
          ename: 'deleteHandle',
          isShow: () => this.tableData.length > 1
        },
      ],
      formColumns: [
        {
          label: '选择接收人',
          type: 'slot',
          slotName: 'receive',
          required: true,
        },
        {
          label: '移交说明',
          type: 'textarea',
          key: 'moveManual',
        },
      ],
      searchName: '',
      param: {
        moveInManagerCode: '',
        moveInManagerName: '',
        moveInOrgCode: '',
        moveInOrgName: '',
        moveManual: ''
      }
    }
  },
  methods: {
    initData() {
      this.tableData = JSON.parse(JSON.stringify(this.selected))
    },
    deleteHandle(row) {
      const index = this.tableData.indexOf(row)
      this.tableData.splice(index, 1)
      this.$nextTick(() => {
        this.$refs.table.$refs.elTable.doLayout();
      });
    },
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
    handleClose() {
      this.param = {
        moveInManagerCode: '',
        moveInManagerName: '',
        moveInOrgCode: '',
        moveInOrgName: '',
        moveManual: ''
      }
      this.tableData = []
      this.showDialog = false
    },
    handleConfirm() {
      if (!this.param.moveInManagerCode) {
        this.$modal.msgError('接收人不能为空')
        return
      }
      this.$refs.myForm.getForm(formInfo => {
        this.param.moveManual = formInfo.moveManual
        const param = this.tableData.map(item => {
          const { customerName, customerIdentity, customerPhone, customerType, id } = item
          return {
            customerName,
            customerIdentity,
            customerPhone,
            customerType,
            filingId: id,
            ...this.param
          }
        })
        service.handoverMoveBatch(param).then(res => {
          this.$modal.msgSuccess('移交成功')
          this.handleClose()
          this.$parent.fetchList()
        })
      })
    }
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