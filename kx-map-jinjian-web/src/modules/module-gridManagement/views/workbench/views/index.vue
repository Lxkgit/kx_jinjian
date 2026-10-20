<template>
  <div class="workbench-wrapper">
    <div class="left-wrapper">
      <cardBox width="100%" height="20%" minHeight="175px" title="业务导航" overflow="hidden">
        <navigationBar></navigationBar>
      </cardBox>
      <div class="row-wrapper">
        <cardBox width="30%" height="100%" maxHeight="319px" title="数据概览">
          <dataOverview></dataOverview>
        </cardBox>
        <cardBox width="69%" height="100%" maxHeight="319px" title="待办事项">
          <template #operate>
            <div class="operate-wrapper">
              查看全部 <i class="el-icon-d-arrow-right"></i>
            </div>
          </template>
          <scheduleList></scheduleList>
        </cardBox>
      </div>
      <cardBox width="100%" height="34%" minHeight="273px" title="本周工作足迹">
        <template #operate>
          <div class="select-operate-wrapper">
            <el-cascader v-model="formItem.deptId" :options="orgOptions" :props="props"
                         :show-all-levels="false">
            </el-cascader>
            <el-select v-model="formItem.userId" placeholder="请选择">
              <el-option v-for="item in userOptions" :key="item.value" :label="item.label"
                         :value="item.value"></el-option>
            </el-select>
          </div>
        </template>
        <workFootprint :formParam="formItem"></workFootprint>
      </cardBox>
    </div>
    <div class="right-wrapper">
      <personInfo width="100%" height="20%" minHeight="175px"></personInfo>
      <cardBox width="100%" height="74%" title="本周日程">
        <template #operate>
          <div class="operate-wrapper">
            查看更多 <i class="el-icon-d-arrow-right"></i>
          </div>
        </template>
        <weekSchedule></weekSchedule>
      </cardBox>
    </div>
  </div>
</template>

<script>
import cardBox from '../components/cardBox';
import dataOverview from '../cell/dataOverview';
import scheduleList from '../cell/scheduleList';
import workFootprint from '../cell/workFootprint';
import personInfo from '../cell/personInfo';
import weekSchedule from '../cell/weekSchedule';
import navigationBar from '../cell/navigationBar';
import commonService from '@/modules/module-gridManagement/api';
export default ({
  components: {
    cardBox,
    dataOverview,
    scheduleList,
    workFootprint,
    personInfo,
    weekSchedule,
    navigationBar
  },
  data() {
    return {
      formItem: {
        deptId: '',
        userId: '',
      },
      orgOptions: [],
      props: {
        value: 'id',
        emitPath: false,
        checkStrictly: true
      },
      userOptions: [],
    }
  },
  mounted() {
    this.deptTreeSelect()
    this.getPaAddrInfoTypeUser()
  },
  methods: {
    deptTreeSelect() {
      commonService.deptTree().then(res => {
        const { data } = res
        this.orgOptions = data
      })
    },
    async getPaAddrInfoTypeUser() {
      const { data } = await commonService.getPaAddrInfoTypeUser()
      this.userOptions = data.map(item => {
        return {
          label: item.userName,
          value: item.userId,
        }
      })

    },
  },
})
</script>

<style lang="scss" scoped>
.workbench-wrapper {
  width: 100%;
  height: 100%;
  padding-right: 10px;
  background: #f3f4f8;
  display: flex;
  justify-content: space-between;
  .left-wrapper {
    width: 75%;
    height: 100%;
    overflow: auto;
    &::-webkit-scrollbar {
      width: 0;
    }
    .row-wrapper {
      width: 100%;
      height: 38%;
      margin-bottom: 15px;
      display: flex;
      justify-content: space-between;
    }
  }
  .right-wrapper {
    width: 24%;
  }

  .operate-wrapper {
    cursor: pointer;
    line-height: 18px;
    font-size: 16px;
    color: #5e81f4;
    border-bottom: 1px solid #5e81f4;
  }
  .select-operate-wrapper {
    ::v-deep .el-select,
    ::v-deep .el-cascader {
      margin-right: 6px;
    }
    ::v-deep .el-input__inner {
      border-radius: 18px;
    }
  }
}
</style>