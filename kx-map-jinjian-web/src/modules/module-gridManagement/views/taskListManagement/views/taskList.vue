<template>
  <div class="task-box">
    <div class="task-header">
      <div class="header-top">
        <table-title title="任务汇总">
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
          <statistics-item
            height="92"
            :imgList="imgList"
            :gridColumns="5"
            gridRows="1fr"
          ></statistics-item>
        </div>
      </div>
    </div>

    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        :columns="searchColumns"
        :gridColumns="3"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <!-- 列表 -->
    <table-title title="详细统计数据" class="table-title">
      <template #right>
          <el-button type="primary" @click="addTask">新建任务</el-button>
        </template>
    </table-title>
    <div class="i-table">
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        maxHeight="500"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        :showIndex="true"
      >
        <template #action="scope">
          <i-table-btns
            :count="4"
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </div>
    <div class="table-box">
    
      
    </div>
  </div>
</template>

<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  components: {
    tableTitle,
    statisticsItem,
  },
  data() {
    return {
      date: "",

      searchDateColumns: [
        {
          label: "时间范围",
          key: "servePointName1",
          type: "date",
        },
      ],
      btnList: [{ name: "最近7天" }, { name: "最近30天" }],
      active: 0,
      imgList: [
        {
          name: "总任务数",
          img: require("@/modules/module-gridManagement/assets/images/task-total.png"),
          background: "none",
          key: "key1",
        },
        {
          name: "进行中任务数",
          img: require("@/modules/module-gridManagement/assets/images/task-doing1.png"),
          key: "key2",
        },
        {
          name: "执行任务总数",
          img: require("@/modules/module-gridManagement/assets/images/task-doing.png"),
          key: "key3",
        },
        {
          name: "已完成任务",
          img: require("@/modules/module-gridManagement/assets/images/task-end.png"),
          key: "key4",
        },
        {
          name: "未完成任务",
          img: require("@/modules/module-gridManagement/assets/images/task-noend.png"),
          key: "key5",
        },
      ],
      searchColumns: [
        {
          label: "任务名称",
          key: "servePointName",
          type: "text",
        },
        {
          label: "任务执行机构",
          key: "servePointName2",
          type: "select",
        },
      ],
      tableData: [{ servePointAddr: "测试" }],
      loading: false,
      columns: [
        {
          label: "任务名称",
          prop: "servePointAddr",
        },
        {
          label: "任务类型",
          prop: "dutyName",
        },
        { label: "任务频次", prop: "dutyPhone" },
        { label: "任务状态", prop: "gridName" },
        { label: "任务执行对象数", prop: "gridName1", width: 200 },
        { label: "未完成任务数", prop: "gridName2" },
        {
          label: "已完成任务数",
          prop: "gridName3",
        },
        { label: "完成进度", prop: "gridName4" },
        {
          label: "任务开始时间",
          prop: "gridName6",
        },
        { label: "操作", slot: true, width: 300 },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "摸排登记",
          ename: "recordHandle",
        },
        {
          cname: "删除",
          ename: "delHandle",
          danger:'danger'
        },
      ],
    };
  },
  methods: {
    //时间范围变化
    handleChange(val) {

    },
    handleBtn(index) {
      this.active = index;
    },
    searchHandle() {},
    addTask() {
      this.$router.push({
        name:'AddTask'
      })
    },
    clickHandle(type, rows) {
      if (type == "delHandle") {
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
      }else if(type=='viewHandle'){
        this.$router.push({name:'TaskView',query:{type:'view'}})
      }else if(type=='editHandle'){
        this.$router.push({name:'TaskView',query:{type:'edit'}})
      }else if(type == 'recordHandle') {

      }
    },
  },
};
</script>

<style lang="scss" scoped>
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
.task-header {
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
    height: 100%;
    padding: 8px 16px;
    border-radius: 8px;
    background: rgba(94, 129, 244, 0.1);
    border: 1px solid #ebeef5;
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
