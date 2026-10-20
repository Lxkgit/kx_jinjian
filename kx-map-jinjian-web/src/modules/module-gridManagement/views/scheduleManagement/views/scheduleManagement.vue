<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <div class="content-wrapper">
        <div class="left-wrapper">
          <div class="left-header">
            <el-input v-model="searchKey" placeholder="请输入内容" clearable
                      prefix-icon="el-icon-search" />
          </div>
          <div class="tree-wrapper">
            <el-tree ref="tree" :props="props" :data="userOptions" lazy
                     @node-click="nodeClickHandle" :expand-on-click-node="false"
                     :highlight-current="true" node-key="id">
            </el-tree>
          </div>
        </div>
        <div class="right-wrapper">
          <i-calendar @dblclick="dblclick" @fetchDate="fetchDate">
            <template #header_right>
              <el-button type="primary" size="small" @click="addHandle">新建日程</el-button>
            </template>
            <template #month="{monthInfo}">
              <div class="schedule-list-wrapper">
                <ul class="schedule-list">
                  <li v-for="item in getScheduleList(monthInfo.date)" :key="item.id" @dblclick.stop
                      @click="clickHandle(item)"
                      :class="{'not-self': item.createBy != item.owner,'last-day': ifLastDay(item.endTime)}"
                      :style="{'--color': item.markColor,'--background': `${item.markColor}`}">
                    {{ item.scheduleTitle }}</li>
                </ul>
              </div>
            </template>
          </i-calendar>
        </div>
      </div>
    </PageContent>
    <!-- 日程编辑 -->
    <scheduleEdit v-model="showEdit" :editInfo="editInfo" :status="status" :dateTime="curDate">
    </scheduleEdit>
    <!-- 日程详情 -->
    <scheduleDetail v-model="showDetail" :editInfo="editInfo"></scheduleDetail>
  </div>
</template>

<script>
import service from '../api';
import scheduleEdit from '../components/scheduleEdit';
import scheduleDetail from '../components/scheduleDetail';
import pageMixin from '@/mixins/pageMixin';
export default ({
  mixins: [pageMixin],
  components: {
    scheduleEdit,
    scheduleDetail
  },
  data() {
    return {
      searchColumns: [
        {
          label: '日程归属',
          type: 'select',
          key: 'date',
          options: [],
        },
        {
          label: '日程名称',
          key: 'scheduleTitle',
          type: 'text',
        },
      ],
      searchKey: '',
      props: {},
      userOptions: [],
      tableData: [],
      dateTime: new Date(),
      showEdit: false,
      showDetail: false,
      status: 'add',
      editInfo: null,
      curDate: null,
    }
  },
  watch: {
    dateTime: {
      handler() {
        this.$nextTick(() => {
          this.fetchList()
        })
      },
      deep: true,
    }
  },
  methods: {
    fetchDate(date) {
      this.dateTime = date
    },
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        let nextMonth = Number(this.parseTime(this.dateTime, '{m}')) + 1
        const param = {
          ...formInfo,
          startTime: `${this.parseTime(this.dateTime, '{y}-{m}')}-01 00:00:00`,
          endTime: `${this.parseTime(this.dateTime, '{y}')}-${nextMonth > 10 ? nextMonth : `0${nextMonth}`}-01 00:00:00`
        }
        this.tableData = []
        service.scheduleList(param).then(res => {
          const { rows } = res
          this.tableData = rows
        })
      })
    },
    getScheduleList(date) {
      return this.tableData.filter(item => this.getDateTime(item.startTime) === this.getDateTime(date))
    },
    getDateTime(date) {
      return this.parseTime(date, '{y}-{m}-{d}')
    },
    ifLastDay(time) {
      return new Date(time).getTime() < new Date().getTime()
    },
    nodeClickHandle() {

    },
    addHandle() {
      this.status = 'add'
      this.showEdit = true
    },
    dblclick(day) {
      this.status = 'add'
      this.curDate = this.parseTime(day.date, '{y}-{m}-{d} {h}:{i}:{s}')
      this.showEdit = true
    },
    editHandle(schedule) {
      this.editInfo = schedule
      this.status = 'update'
      this.showEdit = true
    },
    clickHandle(schedule) {
      this.editInfo = schedule
      this.showDetail = true
    },
  },
})
</script>

<style lang="scss" scoped>
.content-wrapper {
  height: calc(100% - 36px);
  display: flex;
  .left-wrapper {
    width: 260px;
  }
  .right-wrapper {
    width: calc(100% - 260px);
    .schedule-list-wrapper {
      height: calc(100px - 22px);
      width: 100%;
      .schedule-list {
        width: 100%;
        height: 100%;
        overflow: auto;
        > li {
          cursor: pointer;
          margin: 0 10px;
          line-height: 24px;
          text-align: center;
          color: var(--color);
          font-weight: bold;
          margin-bottom: 4px;
          border-radius: 10px;
          position: relative;
          &.not-self {
            &::after {
              content: "派";
              width: 23px;
              height: 23px;
              line-height: 23px;
              border-radius: 50%;
              border: 1px #dc1e3a solid;
              position: absolute;
              top: 0;
              left: 0;
              color: #dc1e3a;
              background: #fff;
            }
          }
          &.last-day {
            filter: grayscale(1);
          }
          &::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            border-radius: 10px;
            background: var(--background);
            opacity: 0.3;
          }
        }
      }
    }
  }
}
</style>