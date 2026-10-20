<template>
  <div>
    <!-- 过滤器 -->
    <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="4">
      <div v-show="searchColumns.length > 0">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </i-filter-bar>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :maxHeight="maxHeight" :allColumns="columns" :total="total"
             :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
             @currentChange="currentChange" @sizeChange="sizeChange" selection
             @selectionChange="selectionChange">
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
    </i-table>
    <customerHandover v-model="showHandover" :selected="multiSelected"></customerHandover>
    <addFile v-model="showEdit" :editInfo="editInfo"></addFile>
    <saveGroup v-model="showSave" :defaultValue="defaultValue" :type="3" @fetchList="fetchList">
    </saveGroup>
  </div>
</template>

<script>
import service from '../../../api';
import pageMixin from "@/mixins/pageMixin";
import customerHandover from "@/modules/module-gridManagement/views/fillingManagement/components/customerHandover";
import addFile from '@/modules/module-gridManagement/views/fillingManagement/components/addFile';
import saveGroup from '@/modules/module-gridManagement/views/fillingManagement/components/saveGroup';
import { getToken } from "@/utils/auth";
export default {
  mixins: [pageMixin],
  dicts: ['custom_type_three', 'filling_status_type', 'filling_source_type'],
  components: {
    customerHandover,
    addFile,
    saveGroup
  },
  props: {
    activeName: {
      type: String,
    }
  },
  data() {
    return {
      searchColumns: [
        {
          label: "客户名称",
          key: "customerName",
          type: "text",
        },
        {
          label: "证件号",
          key: "customerIdentity",
          type: "text",
        },
        {
          label: "手机号",
          key: "customerPhone",
          type: "text",
        },
      ],
      columns: [
        { label: "客户名称", prop: "customerName", showOverFlowTooltip: true, },
        {
          label: "客户类型", prop: "customerType", formatter: ({ customerType }) => {
            return this.dict.label.custom_type_three[customerType]
          }
        },
        {
          label: "证件号", prop: "customerIdentity", width: 180, formatter: ({ customerIdentity }) => {
            return this.$identityMasking(customerIdentity);
          },
        },
        {
          label: "手机号", prop: "customerPhone", width: 120, formatter: ({ customerPhone }) => {
            return this.$phoneMasking(customerPhone)
          },
        },
        // { label: "所属网格", prop: "belongGridName" },
        { label: "建档人", prop: "filingManagerName" },
        { label: "建档机构", prop: "filingOrgName" },
        { label: "更新时间", prop: "updateTime" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "删除",
          ename: "deleteHandle",
        },
      ],
      maxHeight: window.innerHeight - 330,
      showHandover: false,
      showEdit: false,
      showSave: false,
      status: 'add',
      editInfo: {},
      tableData: [],
      upload: {
        action: process.env.VUE_APP_BASE_API + "/system/filing/importBatch",
        headers: { Authorization: "Bearer " + getToken() },
      },
      fileList: [],
      defaultValue: {}
    };
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.loading = true
      this.$refs.filterBar.getForm(formInfo => {
        const param = {
          ...formInfo,
          pageNum,
          pageSize,
          filingStatus: this.activeName
        }
        service.filingMessageList(param).then(res => {
          const { rows, total } = res
          this.loading = false
          this.tableData = rows
          this.total = total
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.loading = false;
        });
      })
    },
    handleClick() {
      this.resetHandle()
    },
    addFileHandle() {
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'add')
      this.showEdit = true
    },
    editHandle(row) {
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'edit')
      this.showEdit = true
      this.editInfo = row
    },
    viewHandle(row) {
      const { customerType, customerIdentity } = row
      const param = {
        identity: customerIdentity,
        customerType,
        type: '3'
      }
      this.$store.commit('gridManagement/SET_CUSTOMER_PARAM', param)
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'view')
      if (customerType === '1') {
        this.$router.push({ name: 'CustomerDetail', params: { activeName: '2' } })
      } else {
        this.$router.push({ name: 'MerchantDetail', params: { activeName: '2' } })
      }
    },
    deleteHandle(row) {
      const { id } = row
      const param = {
        ids: id
      }
      this.$modal.confirm('确认删除该客户?').then(() => {
        service.deleteByIds(param).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        })
      })
    }
  },
};
</script>

<style lang="scss" scoped></style>
