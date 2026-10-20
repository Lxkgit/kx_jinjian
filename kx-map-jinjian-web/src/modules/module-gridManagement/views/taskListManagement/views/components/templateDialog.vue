<template>
    <IDialog title="任务模板选择" v-model="showDialog" @handleClose="handleClose" width="50%"
             @handleConfirm="handleConfirm" :loading="loading">
        <div class="search">
            <el-input
                placeholder="请输入搜索内容"
                v-model="inputValue"
                class="input-with-select"
            >
                <el-button
                slot="append"
                class="search_btn"
                :loading="buttonLoading"
                @click="search"
                >搜索</el-button>
            </el-input>
        </div>
        <div class="content">
            <div class="tree">
                <el-tree :data="treeData" :props="defaultProps" @node-click="handleNodeClick"  :highlight-current="true"></el-tree>
            </div>
            <div class="i-table">
                <div class="text">请选择一个模板</div>
                <i-table
                    ref="table"
                    :loading="tableLoading"
                    :data="tableData"
                    align="center"
                    stripe
                    selection
                    singleChoice
                    :allColumns="columns"
                    :total="formInline.total"
                    :pageNum="formInline.pageNum"
                    :maxHeight="500"
                    :showPagination="false"
                    :pageSize="formInline.pageSize"
                    @currentChange="currentChange"
                    @sizeChange="sizeChange"
                    @selectionChange="selectionChange"
                >
                    <template #action="scope">
                        <el-button type="text" @click="view(scope.row)">预览</el-button>
                    </template>
                </i-table>
            </div>
        </div>
    </IDialog>
  </template>
  
  <script>
  import dialogMixin from "@/mixins/dialogMixin.vue";
  export default {
    mixins: [dialogMixin],
    props: {
      defaultValue: {
        type: Object,
        default: () => ({}),
      },
    },
    data() {
      return {
        loading: false,
        inputValue:'',
        buttonLoading:false,
        tableLoading:false,
        selectTemplate:'',
        tableData:[
            {1: '模板1', 2:'3次', 3:'机构A'},
            {1: '模板1', 2:'3次', 3:'机构A'},
            {1: '模板1', 2:'3次', 3:'机构A'},
        ],
        formInline:{
            pageNum:1,
            pageSize:10,
            total:0,
        },
        treeData:[
            {label:'我创建的',children:[]},
            {label:'已使用的',children:[]},
            {label:'未使用的',children:[]},
        ],
        defaultProps: {
          children: 'children',
          label: 'label'
        },
        columns:[
            {label: "任务模板名称", prop: "1"},
            {label: "模板引用次数", prop: "2"},
            {label: "创建机构", prop: "3"},
            {label: "创建时间", prop: "gridCode"},
            {label: "操作", slot:true,},

        ]
      };
    },
    methods: {
      initData() { 

      },
      handleClose() {
        this.showDialog = false;
      },
      handleConfirm() {

      },
      search() {

      },
      handleNodeClick(val) {

      },
      currentChange(val) {

      },
      sizeChange(val) {
        
      },
      selectionChange(val) {
        if(val.length > 0) {
          this.selectTemplate = val[val.length - 1]
        }else {
            this.selectTemplate = ''
        }
        console.log('选中的模板',this.selectTemplate);
      },
      view(row) {

      },
    },
  };
  </script>
  
  <style lang="scss" scoped>
  ::v-deep .el-dialog__body {
    padding: 0 !important;
  }
  .search {
    margin: 16px 16px 0 16px;
  }
.input-with-select.el-input {
  height: 36px;
  border-radius: 8px;
  ::v-deep .el-input__inner {
    height: 100%;
    border-radius: 8px 0px 0px 8px !important;
  }
  ::v-deep .el-input-group__append {
    border-radius: 0px 8px 8px 0px;
    width: 52px;
    padding: 0 20px 0 0;
  }
  ::v-deep .search_btn {
    width: 52px;
    height: 100%;
    background: rgba(119, 137, 255, 0.1);
    border-radius: 0px 8px 8px 0px !important;
    padding: 0;
    font-size: 14px;
    color: #5E81F4;
    margin-left: 0px;
    min-width: 0;
    &:hover {
      background: rgba(119, 137, 255, 0.2);
      box-shadow: none;
    }
    &:focus {
      background: rgba(119, 137, 255, 0.1);
      box-shadow: none;
    }
    &:active {
      background: rgba(119, 137, 255, 0.36);
      box-shadow: none;
    }
  }
}
.content {
    width: 100%;
    padding-right: 16px;
    height: 520px;
    overflow:hidden;
    margin-top: 24px;
    display: flex;
    .tree {
        width: 136px;
        height: 520px;
        border-right: 1px solid #EBEEF5;
    }
    .i-table {
        width: calc(100% - 136px);
        margin-left: 16px;
        .text {
            font-size: 12px;
            color: #898EAC;
            margin-bottom: 8px;
        }
    }
}
  </style>
  