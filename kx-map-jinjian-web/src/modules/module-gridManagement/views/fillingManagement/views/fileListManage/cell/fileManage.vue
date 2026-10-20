<template>
  <div>
    <!-- 过滤器 -->
    <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="4">
      <div v-show="searchColumns.length > 0">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </i-filter-bar>
    <!-- 按钮 -->
    <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
      <template #import>
        <el-upload class="upload-demo" :action="upload.action" :headers="upload.headers"
                   accept=".xlsx, .xls" :file-list="fileList" :show-file-list="false"
                   :on-success="successHandle">
          <el-button type="primary">导入客户</el-button>
        </el-upload>
      </template>
    </i-button-bar>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :maxHeight="maxHeight" :allColumns="columns" :total="total"
             :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
             @currentChange="currentChange" @sizeChange="sizeChange" selection
             @selectionChange="selectionChange" :selectable="selectable">
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
    </i-table>
    <customerHandover v-model="showHandover" :selected="multiSelected"></customerHandover>
    <addFile v-model="showEdit" :editInfo="editInfo"></addFile>
    <saveGroup v-model="showSave" :defaultValue="defaultValue" :type="2" @fetchList="fetchList">
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
      btnLists: [
        {
          cname: "新增建档",
          ename: "addFileHandle",
          type: "primary",
          permi: 'fillingManagement:fileListManage:add',
        },
        {
          cname: "客户导入",
          ename: "import",
          slot: true,
          permi: 'fillingManagement:fileListManage:import',
        },
        {
          cname: "移交",
          ename: "batchHandoverHandle",
          type: "primary",
          permi: 'fillingManagement:fileListManage:handover',
          disabled: () => this.multiSelected.length === 0
        },
        {
          cname: "保存客群",
          ename: "saveHandle",
          type: "primary",
          permi: 'fillingManagement:fileListManage:save',
        },
        {
          cname: "下载模版",
          ename: "templateHandle",
          permi: 'fillingManagement:fileListManage:import',
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
        { label: "所属网格", prop: "belongGridName" },
        { label: "建档人", prop: "filingManagerName" },
        { label: "建档机构", prop: "filingOrgName" },
        {
          label: "建档状态", prop: "filingStatus", formatter: ({ filingStatus }) => {
            return this.dict.label.filling_status_type[filingStatus]
          }
        },
        { label: "建档日期", prop: "filingDate" },
        { label: "信息完整度", prop: "filingProgress" },
        {
          label: "建档来源", prop: "filingSource", formatter: ({ filingSource }) => {
            return this.dict.label.filling_source_type[filingSource]
          }
        },
        { label: "操作", slot: true, width: '150px' },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
          permi: 'fillingManagement:fileListManage:view'
        },
        {
          cname: "编辑",
          ename: "editHandle",
          permi: 'fillingManagement:fileListManage:edit'
        },
        {
          cname: "移交",
          ename: "handoverHandle",
          isShow: (data) => !data.moveStatus,
          permi: 'fillingManagement:fileListManage:handover'
        },
      ],
      maxHeight: window.innerHeight - 370,
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
    selectable(row) {
      const { moveStatus } = row
      return !moveStatus
    },
    handleClick() {
      this.resetHandle()
    },
    addFileHandle() {
      this.editInfo = {}
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'add')
      this.showEdit = true
    },
    editHandle(row) {
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'edit')
      this.showEdit = true
      this.editInfo = row
    },
    saveHandle() {
      this.$refs.filterBar.getForm(formInfo => {
        let flag = false
        Object.keys(formInfo).forEach((key) => {
          if (Array.isArray(formInfo[key])) {
            flag = flag || formInfo[key].reduce((f, cur) => {
              return f || (!!cur || cur === 0)
            }, false)
          } else {
            flag = flag || !!formInfo[key]
          }
        })
        if (!flag) {
          this.$modal.msgWarning('请至少填写一项')
          return
        }
        this.showSave = true
        this.defaultValue = formInfo
      })
    },
    handoverHandle(row) {
      this.multiSelected = [row]
      this.showHandover = true
    },
    templateHandle() {
      service.modelDownload().then((res) => {
        const { msg } = res
        this.download('/system/common/download', {
          fileName: msg
        }, msg)
      })
    },
    batchHandoverHandle() {
      this.showHandover = true
    },
    viewHandle(row) {
      const { customerType, customerIdentity, customerPhone } = row
      const param = {
        identity: customerIdentity,
        phone: customerPhone,
        customerType,
        type: '3',
      }
      this.$store.commit('gridManagement/SET_CUSTOMER_PARAM', param)
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'view')
      if (customerType === '1') {
        this.$router.push({ name: 'CustomerDetail', params: { activeName: '2' } })
      } else {
        this.$router.push({ name: 'MerchantDetail', params: { activeName: '2' } })
      }

    },
    successHandle() {
      this.$modal.msgSuccess('上传成功,等待解析')
    },
  },
};
</script>

<style lang="scss" scoped></style>
