<template>
  <div class="grid-event">
    <tableTitle title="最近一次事件">
      <template #right>
        <div v-if="showEdit">
          <el-button type="primary" @click="showEventDialog = true ">新增网格事件</el-button>
        </div>
      </template>
    </tableTitle>

    <div class="event-form">
      <i-form
        ref="baseForm"
        :columns="baseColumns"
        label-width="160px"
        labelPosition="right"
        :gridColumns="3">
      </i-form>
      <i-form
        style="width: 67%;"
        ref="peopoleForm"
        :columns="peopleColumns"
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
          maxHeight="200px"
          :allColumns="columns"
          :showPagination="false"
        >
          <template #action="scope">
            <el-button @click="ImportDetail(scope.row)">查看</el-button>
          </template>
        </i-table>
      </div>
    </div>

    <gridEventDialog v-model="showEventDialog"></gridEventDialog>

  </div>
</template>

<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import gridEventDialog from "./gridEventDialog.vue";
export default {
  components: {
    tableTitle,gridEventDialog
  },
  data() {
    return {
      showEdit:true,
      showEventDialog:false,
      tableData: [],
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

      baseColumns:[
      {label: "投诉来源",key: "1",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "投诉时间",key: "2",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "投诉人",key: "21",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "投诉人联系电话",key: "22",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "线索来源",key: "23",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "行业",key: "24",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "发生场景",key: "25",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "是否为拒收人民币行为",key: "26",value: "",type: "text",disabled: true,placeholder: " ",},
      {label: "具体事由",key: "27",value: "",type: "textarea",span:3,disabled: true,placeholder: " ",},
      {label: "处置情况",key: "28",value: "",type: "textarea",span:3,disabled: true,placeholder: " ",},
      ],
      peopleColumns:[
        {label: "网点联络员",key: "1",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "联络员联系电话",key: "2",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "上级负责人",key: "3",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "上级负责人联系电话",key: "4",value: "",type: "text",disabled: true,placeholder: " ",},
      ]
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