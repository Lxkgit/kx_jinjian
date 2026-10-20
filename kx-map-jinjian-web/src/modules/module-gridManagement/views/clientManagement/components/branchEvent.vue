<template>
  <div class="branch-event">
    <tableTitle title="最近一次投诉">
      <template #right>
        <div v-if="showEdit">
          <el-button type="primary" @click="showEventDialog = true ">新增网点投诉</el-button>
        </div>
      </template>
    </tableTitle>

    <div class="event-form">
      <i-form
        ref="baseForm"
        :columns="baseColumns"
        label-width="160px"
        labelPosition="right"
        :gridColumns="2">
      </i-form>
    </div>

    <div >
      <tableTitle title="历史事件" class="content-title">
      </tableTitle>
      <div class="content-content">
        <i-table
          class="table-height"
          ref="table"
          :data="tableData"
          align="center"
          stripe
          showBorder
          maxHeight="150px"
          :allColumns="columns"
          :showPagination="false"
        >
          <template #action="scope">
            <el-button @click="ImportDetail(scope.row)">查看</el-button>
          </template>
        </i-table>
      </div>
    </div>

    <branchEventDialog v-model="showEventDialog"></branchEventDialog>

  </div>
</template>

<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import branchEventDialog from "./branchEventDialog.vue";
export default {
  components: {
    tableTitle,branchEventDialog
  },
  data() {
    return {
      showEdit:true,
      showEventDialog:false,
      baseColumns:[
        {label: "投诉来源",key: "1",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "投诉时间",key: "2",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "投诉人",key: "21",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "投诉人联系电话",key: "22",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "投诉事件描述",key: "27",value: "",type: "textarea",span:2,disabled: true,placeholder: " ",},
        {label: "处置情况",key: "28",value: "",type: "textarea",span:2,disabled: true,placeholder: " ",},
        {label: "网点联络员",key: "12342",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "联络员联系电话",key: "22234",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "上级负责人",key: "3123",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "上级负责人联系电话",key: "215234",value: "",type: "text",disabled: true,placeholder: " ",},
      ],
      columns:[
        {label:'投诉人', prop: "1"},
        {label:'投诉人联系电话', prop: "2"},
        {label:'投诉来源', prop: "3"},
        {label:'投诉时间', prop: "4"},
        {label:'投诉事件描述', prop: "5"},
        {label:'线索来源', prop: "6"},
        {label:'网点联络员', prop: "7"},
        { label: "操作", slot:true },
      ],
      tableData: [],

    }
  },
  methods:{

  },
}
</script>

<style lang="scss" scoped>
.event-form {
  margin-top: 12px;
}
.content-content {
  margin-bottom: 16px;
}
</style>