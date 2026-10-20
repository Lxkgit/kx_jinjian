<!-- 任务进度根据页面 -->
<template>
  <div class="task-box">
    <orgTitle goBack :imgIcon="imgIcon" title="2024.01 商户摸排任务">
        <template #left>
            <span class="doing-left">共执行<span style="color:#5E81F4">XX</span>次，已执行<span style="color:#5E81F4">3</span>次</span>
            <span class="doing">进行中</span>
        </template>
    </orgTitle>
    <!-- 周期性任务跟进头 -->
     <div class="task-period">
        <div v-for="(item,index) in peroidTiemList" :key="index" 
         :class="perodiActive == item.name ? 'period-box period-active' : 'period-box'" 
         @click="perodiActive = item.name">
            {{ item.name }}
        </div>
     </div>
    <!-- 头部统计 -->
    <div class="task-header">
      <div class="header-top">
        <table-title title="汇总统计">
          <template #right>
            <div class="time-btn">
              <div
                :class="active == index ? 'btn activeBtn' : 'btn'"
                v-for="(item, index) in btnList"
                :key="index"
                @click="handleBtn(index)"
              >
                {{ item.name }}
              </div>
            </div>
            <div class="time-select-text">时间范围</div>
            <el-date-picker
                class="time-select-date-picker"
                v-model="date"
                type="daterange"
                start-placeholder="开始日期"
                value-format="yyyy-MM-dd"
                @change="handleChange"
                end-placeholder="结束日期">
            </el-date-picker>
          </template>
        </table-title>
      </div>
      <div class="header-bottom">
        <div class="task-card">
            <div style="width:60%">
                <statistics-item
                height="92"
                :imgList="imgList"
                :gridColumns="3"
                gridRows="1fr"
                ></statistics-item>
            </div>
            <div :style="{width:'40%',marginLeft:'12px'}">
                <pieChartList gridColumns="2" :data="taskData" :styleClass="taskClass"></pieChartList>
            </div>
        </div>
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
    <table-title title="明细清单" class="table-title">
        <template #right>
          <el-button>导出</el-button>
        </template>
    </table-title>
    <div class="i-table">
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        maxHeight="500"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      > 
        <template #status="{row}">
            <span :style="{color:row.status == 1 ? '#37C38C' : '#DC5564' }">{{ row.status == 1 ? '已完成' : '未完成' }}</span>
        </template>
        <template #action="scope">
            <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </div>
    <taskDetail v-model="showDetail"></taskDetail>
    <taskObjectChange v-model="showChange"></taskObjectChange>
  </div>
</template>

<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
import pieChartList from '@/modules/module-gridManagement/components/pieChartList';
import taskDetail from "./taskDetail.vue";
import taskObjectChange from "./taskObjectChange.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  components: {
    tableTitle,
    statisticsItem,orgTitle,pieChartList,taskDetail,taskObjectChange
  },
  data() {
    return {
      date:'',
      active: 0,
      imgList: [
        {
          name: "总任务数",
          img: require("@/modules/module-gridManagement/assets/images/task-total1.png"),
          key: "key1",
        },
        {
          name: "已完成任务数",
          img: require("@/modules/module-gridManagement/assets/images/task-end.png"),
          key: "key2",
        },
        {
          name: "未完成任务数",
          img: require("@/modules/module-gridManagement/assets/images/task-noend.png"),
          key: "key3",
        },
      ],
      taskData:[
        { text:'任务完成率',centerText:'完成率',rate:'20',value:'6182' },
      ],
      taskClass:[
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: 12,
          circleTextColor: '#323548',
          rightCircleColorFront: '#97B6FF',//右边边25%渐变色
          rightlCircleColorBack: '#3284FF',//右边边100%渐变色
        }
      ],
      searchColumns: [
        {
          label: "执行对象",
          key: "executor",
          type: "text",
        },
        {
          label: "任务状态",
          key: "status",
          type: "select",
          options: [
            { label: "全部", value: "" },
            { label: "已完成", value: "done" },
            { label: "未完成", value: "undone" },
          ],
        },
      ],
      tableData: [
        {
          executor: "张三",
          status: "1",
          progress: "100%",
          finishTime: "2024-01-01 12:00:00",
        },
        {
          executor: "李四",
          status: "0",
          progress: "50%",
          finishTime: "2024-01-02 12:00:00",
        }
                
      ],
      loading: false,
      showDetail:false,
      showChange:false,
      columns: [
        { label: "任务执行对象", prop: "executor" },
        { label: "任务状态", prop: "status",slot:true },
        { label: "任务上报完成度", prop: "progress" },
        { label: "任务完成时间", prop: "finishTime" },
        { label: "操作", slot: true, width: 200 },
      ],
      total: 0,
      formInline: {
        pageNum: 1,
        pageSize: 20,
      },
      imgIcon:require('@/modules/module-gridManagement/assets/images/task.png'),
      tableBtn:[
        { cname:'执行对象变更',ename:'changeExecutor',type:'text'},
        { cname:'详情',ename:'view',type:'text'},
      ],
      btnList: [{ name: "最近7天" }, { name: "最近30天" }],
      peroidTiemList:[
        { name: "2024年1月" },
        { name: "2024年2月" },
        { name: "2025年2月" },
      ],
      perodiActive:'',
    };
  },
  methods: {
    //时间范围变化
    handleChange(val) {

    },
    handleBtn(index) {
      this.active = index;
      // TODO: 切换时间范围逻辑
    },
    searchHandle() {
      // TODO: 搜索逻辑
    },
    resetHandle() {
      // TODO: 重置逻辑
    },
    currentChange(page) {
      this.formInline.pageNum = page;
      // TODO: 分页逻辑
    },
    sizeChange(size) {
      this.formInline.pageSize = size;
      // TODO: 分页逻辑
    },
    viewDetail(row) {
      // TODO: 跳转详情或变更页面
    },
    changeExecutor(row) {
        this.showChange = true
    },
    view() {
        this.showDetail = true
    },
  },
};
</script>

<style lang="scss" scoped>
.doing {
    margin-left: 8px;
    display: inline-block;
    width: 92px;
    height: 32px;
    border-radius: 20px;
    background: rgba(94, 129, 244, 0.2);
    color: #5E81F4;
    text-align: center;
    line-height: 32px;
}
.doing-left {
  margin-left: 12px;
  color: #898EAC
}
.time-select-text {
    margin-left: 16px;
    color: #494D68;
    font-size: 14px;
}
.time-select-date-picker {
    margin-left: 12px;
    width: 280px;
}
.task-box {
  width: 100%;
  height: 100%;
}
.task-period {
    margin-top: 12px;
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
.task-header {
  margin-top: 12px;
  width: 100%;
  height: 216px;
  background: #ffffff;
  box-shadow: 1px 0px 6px 0px #ebeef5;
}
.header-top {
  padding: 12px 16px;
  .time-btn {
    width: 196px;
    height: 36px;
    display: flex;
    border-radius: 4px;
    background: #f6f7fa;
    border: 1px solid #ebeef5;
    margin-right: 16px;
    .btn {
      width: 97px;
      height: 35px;
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 14px;
      color: #323548;
      cursor: pointer;
    }
    .activeBtn {
      border-radius: 4px;
      background: linear-gradient(180deg, #92abff 0%, #5e81f4 100%);
      border: 1px solid rgba(255, 255, 255, 0.4);
      color: #ffffff;
    }
  }
  .filter-bar-wrapper {
    margin-bottom: 0;
    display: inline;
  }
}

.filterBar-box {
  margin-top: 12px;
    margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 75%;
  }
}

.header-bottom {
  width: 100%;
  height: 156px;
  padding: 24px 16px;
  border-top: 1px solid #e7eaf6;
  .task-card {
    width: 100%;
    height: 100%;
    padding: 8px 16px;
    border-radius: 8px;
    display: flex;
    align-items: center;
  }
}

.table-title {
  padding: 16px;
  height: 68px;
}
.i-table {
  height: 560px;

  background: #fff;
  padding: 0 16px 16px 16px;
  padding-bottom: 10px;
}
</style>