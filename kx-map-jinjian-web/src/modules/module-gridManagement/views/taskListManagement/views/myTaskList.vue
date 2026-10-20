<template>
  <div class="task-box">
    <!-- 顶部tab切换 -->
    <div class="task-period">
        <div v-for="(item,index) in taskTypeList" :key="index" 
         :class="taskActive == item.name ? 'period-box period-active' : 'period-box'" 
         @click="changeTaskType(item)">
            {{ item.name }}
        </div>
     </div>

    <!-- 筛选栏 -->
    <div class="filterBar-box">
        <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        :columns="searchColumns"
        :gridColumns="3"
        />
        <div>
            <el-button type="primary" @click="searchHandle">搜索</el-button>
            <el-button @click="resetHandle">重置</el-button>
        </div>
    </div>

    <!-- 表格 -->
    <div class="table-card">
      <table-title title="详细统计数据" />
      <div class="i-table">
        <i-table
          :data="tableData"
          align="center"
          stripe
          border
          :allColumns="columns"
          :total="total"
          :pageNum="pageNum"
          :pageSize="pageSize"
          :maxHeight="520"
          @currentChange="currentChange"
          @sizeChange="sizeChange"
        >
          <template #status="{row}">
            <span :style="{color: row.status === '1' ? '#37C38C' : '#DC5564'}">{{ row.status === '1' ? '已完成' : '未完成' }}</span>
          </template>
          <template #action="{row}">
            <i-table-btns
            :count="4"
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type,row)"
            :data="row"
            >
            </i-table-btns>
          </template>
        </i-table>
      </div>
    </div>
    <taskDetail v-model="showDetail" :taskType="taskType" />
    <taskObjectChange v-model="showChange" />
  </div>
</template>

<script>
import tableTitle from '@/modules/module-gridManagement/components/tableTitle.vue';
import taskDetail from './components/taskDetail.vue';
import taskObjectChange from './components/taskObjectChange.vue';
import pageMixin from "@/mixins/pageMixin";
const myReceiveTask = [
  { label: '序号', prop: 'id', width: 60 },
  { label: '任务名称', prop: 'name' },
  { label: '任务类型', prop: 'type' },
  { label: '任务状态', prop: 'status', slot: true },
  { label: '任务优先级', prop: 'level' },
  { label: '任务开始时间', prop: 'startTime' },
  { label: '任务结束时间', prop: 'endTime' },
  { label: '创建机构', prop: 'creator' },
  { label: '操作', slot: true, width: 250 },
]
const myPayoutTask = [
  { label: '序号', prop: 'id', width: 60 },
  { label: '任务名称', prop: 'name' },
  { label: '任务类型', prop: 'type' },
  { label: '任务频次', prop: 'type1' },
  { label: '任务状态', prop: 'status', slot: true },
  { label: '任务执行对象数', prop: '2313'},
  { label: '未完成任务数', prop: '22'},
  { label: '已完成任务数', prop: '2222'},
  { label: '完成进度', prop: '222222'},
  { label: '任务开始时间', prop: 'startTime' },
  { label: '任务结束时间', prop: 'endTime' },
  { label: '创建机构', prop: 'creator' },
  { label: '任务转派时间', prop: '213123' },
  { label: '操作', slot: true, width: 250,fixed:'right' },
]
export default {
  components: { tableTitle, taskDetail, taskObjectChange },
  mixins: [pageMixin],
  data() {
    return {
      activeTab: 'received',
      searchColumns: [
        {
          label: "任务名称",
          key: "name",
          type: "text",
        },
        {
          label: "任务类型",
          key: "name1",
          type: "select",
          options:this.taskTypeOptions
        },
        {
          label: "任务状态",
          key: "name2",
          type: "select",
          options:this.taskStatusOptions
        },
       
      ],
      taskTypeOptions: [
        { label: '日常任务', value: 'daily' },
        { label: '专项任务', value: 'special' },
        { label: '其他任务', value: 'other' },
      ],
      taskStatusOptions: [
        { label: '全部', value: '' },
        { label: '已完成', value: '1' },
        { label: '未完成', value: '0' },
      ],
      taskTypeList:[
        { name: "我收到的" },
        { name: "我转派的" },
        { name: "全部" },
      ],
      taskActive:'我收到的',
      taskType:'',
      tableBtn:[
        { cname:'详情',ename:'viewDetail',type:'text'},
        { cname:'反馈',ename:'feedBack',type:'text'},
        { cname:'任务分派',ename:'taskAssign',type:'text'},
      ],
      tableData: [
        {
          id: 1,
          name: '网点现金服务质量提升',
          type: '日常任务',
          status: '1',
          level: '1级',
          startTime: '2025-03-12 15:00',
          endTime: '2025-03-12 18:00',
          creator: '工商银行金华分行',
        },
        {
          id: 2,
          name: '网点现金服务质量提升',
          type: '客户现金服务排查任务',
          status: '0',
          level: '2级',
          startTime: '2025-03-12 15:00',
          endTime: '2025-03-12 18:00',
          creator: '工商银行金华分行',
        },
        {
          id: 3,
          name: '网点现金服务质量提升',
          type: '网点现金服务质量排查任务',
          status: '0',
          level: '1级',
          startTime: '2025-03-12 15:00',
          endTime: '2025-03-12 18:00',
          creator: '工商银行金华分行',
        },
        {
          id: 4,
          name: '网点现金服务质量提升',
          type: '其他任务',
          status: '1',
          level: '1级',
          startTime: '2025-03-12 15:00',
          endTime: '2025-03-12 18:00',
          creator: '工商银行金华分行',
        },
      ],
      columns: [
        { label: '序号', prop: 'id', width: 60 },
        { label: '任务名称', prop: 'name' },
        { label: '任务类型', prop: 'type' },
        { label: '任务状态', prop: 'status', slot: true },
        { label: '任务优先级', prop: 'level' },
        { label: '任务开始时间', prop: 'startTime' },
        { label: '任务结束时间', prop: 'endTime' },
        { label: '创建机构', prop: 'creator' },
        { label: '操作', slot: true, width: 250 },
      ],
      total: 4,
      pageNum: 1,
      pageSize: 10,
      showDetail: false,
      showChange: false,
    };
  },
  methods: {  
    changeTaskType(item) {
      this.taskActive = item.name;
      if(item.name == '我收到的') {
        this.tableBtn = [
          { cname:'详情',ename:'viewDetail',type:'text'},
          { cname:'反馈',ename:'feedBack',type:'text'},
          { cname:'任务分派',ename:'taskAssign',type:'text'},
        ]
        this.columns = myReceiveTask
      }
      else if(item.name =='我转派的') {
        this.tableBtn = [
          { cname:'详情',ename:'viewDetail',type:'text'},
          { cname:'编辑',ename:'feedBack',type:'text'},
          { cname:'任务明细',ename:'taskDetail',type:'text'},
          { cname:'删除',ename:'delete',danger:'danger'},
        ]
        this.columns = myPayoutTask
      }
    },
    handleSearch() {
      // 搜索逻辑
    },
    currentChange(page) {
      this.pageNum = page;
    },
    sizeChange(size) {
      this.pageSize = size;
    },
    viewDetail(row) {
      this.showDetail = true;
      this.taskType = 'view'
    },
    feedBack(row) {
      this.showDetail = true;
      this.taskType = 'edit'
    },
    taskAssign(row) {
        this.$router.push({
            name:'AddTask'
        })
    },
    //点击任务明细
    taskDetail() {

    },
    //点击删除
    delete(row) {
      this.$confirmDialog({
          title: "提示",
          message: "确定要删除当前任务吗？",
          type: "danger",
          confirmBtnText: "确定",
          confirmCallBack: () => {
            console.log(111);
          },
          cancelCallBack: () => {},
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.task-period {
    width: 100%;
    height: 52px;
    background: #fff;
    padding: 16px 16px 0 16px;
    border-bottom: 1px solid #5E81F4;
    display: flex;
    align-items: center;
    .period-box {
      width:120px;
      height: 35px;
      background: #F6F7FA;
      border-radius: 4px 4px 0px 0px;
      border: 1px solid #EBEEF5;
      cursor:pointer;
      color: #323548;
      font-size: 14px;
      text-align: center;
      line-height: 36px;
    }
    .period-active {
      border: 1px solid #5E81F4;
      background: linear-gradient(180deg, #5E81F4 0%, #425AAA 100%);
      color: #FFFFFF;
    }
    .period-box:not(:first-child) {
      margin-left: 20px;
    }
}
.task-box {
  width: 100%;
  height: 100%;
}
.custom-tabs {
  background: #fff;
  padding: 0 16px;
  border-radius: 8px 8px 0 0;
  margin-bottom: 0;
  .el-tabs__nav-wrap {
    margin-bottom: 0;
  }
}
.filterBar-box {
  margin-top: 12px;
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  padding-bottom: 0;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 75%;
  }
}
.table-card {
  background: #fff;
  box-shadow: 1px 0px 6px 0px #ebeef5;
  padding: 16px;
  height: calc(100% - 52px - 68px - 16px);;
}
.i-table {
  margin-top: 12px;
  background: #fff;
  padding: 0 16px 16px 16px;
}
</style>
