<template>
  <div class="app-container">
    <div class="container">
      <div class="container-top">
        <div class="container-title">全行主任务统计</div>
      </div>
      <div class="container-one">
        <draw-pie :width="gridWidth" :height="gridHeight"
                  :num="rateAndCount[0].rate" :topText="'主任务完成率'" :topNum="rateAndCount[0].rate"
                  :bottomText="'总任务数量'" :bottomNum="rateAndCount[0].taskCount"></draw-pie>
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-分行排行榜" :title="gridTitle2"
                 :flexList="flexList1" :config="gridConfig1" :list="masterTaskSeparateRank" />
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-支行排行榜" :title="gridTitle1"
                 :flexList="flexList1" :config="gridConfig1" :list="masterTaskOrgRank" />
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-客户经理排行榜"
                 :title="gridTitle" :flexList="flexList" :config="gridConfig"
                 :list="masterTaskManagerRank" />
      </div>
      <el-divider></el-divider>
      <div class="container-top">
        <div class="container-title">全行子任务统计</div>
      </div>
      <div class="container-two">
        <draw-pie :width="gridWidth" :height="gridHeight"
                  :num="rateAndCount[1].rate" :topText="'子任务完成进度'" :topNum="rateAndCount[1].rate"
                  :bottomText="'子任务数量'" :bottomNum="rateAndCount[1].taskCount"></draw-pie>
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-分行排行榜" :title="gridTitle2"
                 :flexList="flexList1" :config="gridConfig1" :list="childTaskSeparateRank" />
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-支行排行榜" :title="gridTitle1"
                 :flexList="flexList1" :config="gridConfig1" :list="childTaskOrgRank" />
        <Ranking :width="gridWidth" :height="gridHeight" headerTitle="完成率-客户经理排行榜"
                 :title="gridTitle" :flexList="flexList" :config="gridConfig"
                 :list="childTaskManagerRank" />
      </div>
    </div>
    <el-divider></el-divider>
    <div class="container">
      <div class="container-top">
        <div class="container-title">权限内主任务进度</div>
        <div class="container-search">
          <auto-input :label="'主任务名称'" :suggest="suggest2" :valueKey="'taskName'"
                      @handleSelect="getTaskProgress"
                      @valueChange="getSearchSuggest($event, 2)"></auto-input>
        </div>
      </div>
      <div class="taskProgress">
        <progress-card :cardWidth="'33vw'" :cardHeight="'20vw'" :color="'#FBBC25'" :title="'未开始的任务'"
                       :paogressData="notStartedTask"></progress-card>
        <progress-card :cardWidth="'33vw'" :cardHeight="'20vw'" :title="'进行中的任务'"
                       :paogressData="runningTask"></progress-card>
        <progress-card :cardWidth="'33vw'" :cardHeight="'20vw'" :color="'#999999'" :title="'已结束的任务'"
                       :paogressData="endTask"></progress-card>
      </div>
    </div>
    <el-divider></el-divider>
    <div class="container">
      <div class="container-top">
        <div class="container-title">权限内任务明细</div>
      </div>
      <PageContent>
        <!-- 过滤器 -->
        <i-filter-bar :columns="searchColumns" ref="filterBar" slot="filter" :gridColumns="4"
                      label-width="80px">
          <!-- 搜索与重置按钮 -->
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </i-filter-bar>
        <!-- 任务导出与全部任务导出 -->
        <!-- <i-button-bar
          :btn-lists="btnLists"
          slot="btn"
          ref="btnBar"
          size="mini"
          class="i-btn-bar"
          @click="handleClick"
        ></i-button-bar> -->
        <i-table ref="table" :loading="loading" :data="detailData" :max-height="maxHeight"
                 align="center" stripe border :allColumns="columns" :total="total"
                 :pageNum="formInline.pageNum" :pageSize="formInline.pageSize" :selection="true"
                 @selectionChange="selectionChange" @currentChange="currentChange"
                 @sizeChange="sizeChange">
        </i-table>
      </PageContent>
    </div>
  </div>
</template>

<script>
import drawPie from "../../components/drawPie/index.vue";
import progressCard from "../../components/progressCard/index.vue";
import Ranking from "../../components/Ranking/index.vue";
import autoInput from "../../components/autoInput/index.vue";
import services from "../../api";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  name: 'TaskStatistics',
  components: { drawPie, progressCard, Ranking, autoInput },
  mixins: [pageMixin],
  dicts: ["master_task_status", "child_task_status"],
  data() {
    return {
      rateAndCount: [{rate:0,numRate:0,taskCount:0},{rate:0,numRate:0,taskCount:0}], //子任务和主任务的完成率
      gridWidth: "47%",
      gridHeight: "300px",
      gridTitle: [
        "前五",
        "支行",
        "客户经理",
        "完成率",
        "后五",
        "支行",
        "客户经理",
        "完成率",
      ],
      flexList: [1, 2, 2, 2, 1, 2, 2, 2],
      gridConfig: [
        { key: "topRank", type: "top" },
        { key: "topOrg", type: "org" },
        { key: "topManager" },
        { key: "topRate" },
        { key: "lastRank", type: "last" },
        { key: "lastOrg", type: "org" },
        { key: "lastManager" },
        { key: "lastRate" },
      ],
      gridTitle1: [
        "前五",
        "支行",
        "完成率",
        "后五",
        "支行",
        "完成率",
      ],
      gridTitle2: [
        "前五",
        "分行",
        "完成率",
        "后五",
        "分行",
        "完成率",
      ],
      flexList1: [1, 2, 2, 1, 2, 2],
      gridConfig1: [
        { key: "topRank", type: "top" },
        { key: "topManager", type: "org" },
        { key: "topRate" },
        { key: "lastRank", type: "last" },
        { key: "lastManager", type: "org" },
        { key: "lastRate" },
      ],
      childTaskOrgRank: [], //子任务支行排行
      childTaskSeparateRank: [], //子任务分行排行
      childTaskManagerRank: [], //子任务客户经理排行
      masterTaskOrgRank: [], //主任务支行排行
      masterTaskSeparateRank: [], //主任务分行排行
      masterTaskManagerRank: [], //主任务客户经理排行
      runningTask: [], //进行中的任务
      advancedTask: [], //超前完成的任务
      notStartedTask: [], //未开始的任务
      endTask: [], //已结束的任务
      searchColumns: [
        {
          key: "one",
          type: "doubleSelect",
          placeholder2: "请选择任务状态",
          value: {
            select1: "0",
            select2: "",
          },
          options1: [
            {
              value: "0",
              label: "主任务状态",
              options: () => this.dict.type.master_task_status,
            },
            {
              value: "1",
              label: "子任务状态",
              options: () => this.dict.type.child_task_status,
            },
          ],
        },
        {
          key: "two",
          type: "composeInput",
          value: {
            input: "",
            select: "0",
          },
          options: [
            {
              value: "0",
              label: "机构名称",
            },
            {
              value: "1",
              label: "执行人名称",
            },
          ],
        },
        {
          key: "three",
          type: "composeInput",
          value: {
            input: "",
            select: "0",
          },
          options: [
            {
              value: "0",
              label: "任务名称",
            },
            {
              value: "1",
              label: "任务对象",
            },
          ],
        },
        // {
        //   label: "执行人",
        //   key: "managerName",
        //   type: "text",
        //   placeholder: " ",
        //   clearable: true,
        // },
      ],
      // btnLists: [
      //   {
      //     cname: "任务导出",
      //     ename: "export",
      //     type: "primary",
      //     disabled: () => this.multiSelected.length === 0,
      //   },
      //   {
      //     cname: "全部任务导出",
      //     ename: "exportAll",
      //     type: "primary",
      //   },
      // ],
      columns: [
        { label: "任务对象", prop: "customerName" },
        { label: "证件号", prop: "customerIdentity" },
        { label: "任务类型", prop: "taskTypeName", showOverFlowTooltip: true, },
        { label: "任务名称", prop: "taskName", showOverFlowTooltip: true, },
        {
          label: "主任务状态", prop: "masterTaskStatus", formatter: ({ masterTaskStatus }) => {
            return this.dict.label.master_task_status[masterTaskStatus]
          }
        },
        { label: "任务优先级", prop: "taskLevelName" },
        {
          label: "子任务状态", prop: "childTaskStatus", formatter: ({ childTaskStatus }) => {
            return this.dict.label.child_task_status[childTaskStatus]
          }
        },
        { label: "责任机构", prop: "deptName", showOverFlowTooltip: true, },
        { label: "执行人", prop: "managerName", showOverFlowTooltip: true, },
        { label: "任务时间", prop: "timeRange", width: 200 },
        // { label: "任务开始时间", prop: "startTime" },
        // { label: "任务结束时间", prop: "endTime" },
        { label: "信息完整度", prop: "integrity" },
      ],
      detailData: [], // 任务明细
      suggest1: [], // 任务数据搜索的建议
      suggest2: [], // 主任务进度搜索的建议
    };
  },
  mounted() {
    this.fetchList();
    this.getListTaskRank();
    this.getTaskProgress();
    this.getSearchSuggest();
  },
  methods: {
    // 获取筛选器的选项数据
    getFormInfo() {
      let data = {};
      this.$refs.filterBar.getForm((formInfo) => {
        const { one, two, three, managerName } = formInfo;
        if (one.select2) {
          if (one.select1 == "0") {
            data.masterTaskStatus = one.select2; //主任务状态
          } else {
            data.childTaskStatus = one.select2; //子任务状态
          }
        }
        if (two.input) {
          if (two.select == "0") {
            data.deptName = two.input; //执行机构
          } else {
            data.managerName = two.input; //执行人
          }
        }
        if (three.input) {
          if (three.select == "0") {
            data.taskName = three.input; //任务名称
          } else {
            data.customerName = three.input; //任务对象
          }
        }
        // data.managerName = managerName
      });
      return data;
    },
    // 获取任务明细表格的数据
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      let data = { pageNum, pageSize, ...this.getFormInfo() };
      services
        .taskPage(data)
        .then((res) => {
          if (res.code == 200) {
            res.rows.forEach((item) => {
              if (item.startTime && item.endTime) {
                item.startTime = item.startTime.slice(0, 10);
                item.endTime = item.endTime.slice(0, 10);
                item.timeRange = item.startTime + "—" + item.endTime;
              }
            });
            this.detailData = res.rows;
            this.total = res.total;
            this.loading = false;
          }
        })
        .catch((err) => {
          if (err) {
            this.detailData = [];
            this.total = 0;
          }
          this.loading = false;
        });
    },
    // 任务导出按钮组的点击事件
    // handleClick(e) {
    //   let data = { ...this.getFormInfo() };
    //   if (e === "export") {
    //     this.makeSure(
    //       this.multiSelected.length > 1
    //         ? "是否导出多个任务?"
    //         : "是否导出该任务",
    //       () => {
    //         data.ids = this.multiSelected.map((item) => {
    //           return item.id;
    //         });
    //         this.exportTask(data);
    //       }
    //     );
    //   }
    //   if (e === "exportAll") {
    //     this.makeSure("是否导出全部任务?", () => {
    //       this.exportTask(data);
    //     });
    //   }
    // },
    // 任务导出接口
    // exportTask(data) {
    //   // console.log("data", data);
    //   services
    //     .taskDetailExport(data)
    //     .then((res) => {
    //       const blob = new Blob([res], { type: "application/vnd.ms-excel" });
    //       const fileName = "任务明细.xlsx"; //下载文件名称
    //       const elink = document.createElement("a");
    //       elink.download = fileName;
    //       elink.style.display = "none";
    //       elink.href = URL.createObjectURL(blob);
    //       document.body.appendChild(elink);
    //       elink.click();
    //       URL.revokeObjectURL(elink.href); // 释放URL 对象
    //       document.body.removeChild(elink);
    //     })
    //     .catch((err) => {
    //       console.log("err", err);
    //     });
    // },
    // 任务排行榜统计
    getListTaskRank(obj) {
      let data = {};
      if (obj) {
        data.id = obj.id;
      }
      services.listTaskRank(data).then((res) => {
        const {
          // 子任务
          topFiveChildTaskOrgRank,
          lastFiveChildTaskOrgRank,
          topFiveChildTaskSeparateRank,
          lastFiveChildTaskSeparateRank,
          topFiveChildTaskManagerRank,
          lastFiveChildTaskManagerRank,

          // 主任务
          topFiveMasterTaskOrgRank,
          lastFiveMasterTaskOrgRank,
          topFiveMasterTaskSeparateRank,
          lastFiveMasterTaskSeparateRank,
          topFiveMasterTaskManagerRank,
          lastFiveMasterTaskManagerRank,
        } = res.data;
        if(res.data.rateAndCount.length>0){
          res.data.rateAndCount.forEach((item,idx)=>{
            // 返回的结果不带%
            // item.numRate = Number(item.rate)
            //   返回的结果带%时，将%去掉
            item.rate = Number(item.rate.slice(0,-1))
            // item.numRate = item.rate
            this.rateAndCount[idx] = item
          })
        }
        console.log('rateAndCount',this.rateAndCount);

        // 子任务支行排行
        this.childTaskOrgRank = this.disposeRankRes(
          topFiveChildTaskOrgRank,
          lastFiveChildTaskOrgRank
        );
        // 子任务分行排行
        this.childTaskSeparateRank = this.disposeRankRes(
          topFiveChildTaskSeparateRank,
          lastFiveChildTaskSeparateRank
        );
        // 子任务客户经理排行
        this.childTaskManagerRank = this.disposeRankRes(
          topFiveChildTaskManagerRank,
          lastFiveChildTaskManagerRank
        );
        // 主任务支行排行
        this.masterTaskOrgRank = this.disposeRankRes(
          topFiveMasterTaskOrgRank,
          lastFiveMasterTaskOrgRank
        );
        // 主任务分行排行
        this.masterTaskSeparateRank = this.disposeRankRes(
          topFiveMasterTaskSeparateRank,
          lastFiveMasterTaskSeparateRank
        );
        // 主任务客户经理排行
        this.masterTaskManagerRank = this.disposeRankRes(
          topFiveMasterTaskManagerRank,
          lastFiveMasterTaskManagerRank
        );
      });
    },
    disposeRankRes(topFive, lastFive) {
      return topFive.map((v, i) => {
        return {
          topRank: v.rank,
          topOrg: v.belongDept,
          topManager: v.rankObject,
          topRate: v.rate,
          lastRank: lastFive[i].rank,
          lastOrg: lastFive[i].belongDept,
          lastManager: lastFive[i].rankObject,
          lastRate: lastFive[i].rate,
        };
      });
    },
    // 主任务进度
    getTaskProgress(obj) {
      let data = {};
      if (obj) {
        data.taskName = obj.taskName;
      }
      services
        .listTaskProgress(data)
        .then((res) => {
          const { runningTask, advancedTask, notStartedTask, endTask } =
            res.data;
          this.runningTask = runningTask;
          this.advancedTask = advancedTask;
          this.notStartedTask = notStartedTask;
          this.endTask = endTask;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 获取搜索建议
    getSearchSuggest(val, num) {
      let data = {};
      if (val) {
        data.taskName = val;
      }
      // 搜索建议为空时，重新获取对应部分的数据
      if (!val) {
        if (num == "1") {
          this.getListTaskRank();
        }
        if (num == "2") {
          this.getTaskProgress();
        }
      }
      services
        .listPaTaskSearch(data)
        .then((res) => {
          if (num == "1") {
            this.suggest1 = res.data;
          } else if (num == "2") {
            this.suggest2 = res.data;
          } else {
            this.suggest1 = res.data;
            this.suggest2 = res.data;
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-table__body-wrapper::-webkit-scrollbar {
  width: 0;
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  .container-top {
    display: flex;
    align-items: center;
    height: 50px;
    width: 95%;
    position: relative;
    .container-title {
      color: #3e3f45;
      font-size: 18px;
      font-weight: bold;
      margin-left: 30px;
    }
    .container-title::before {
      content: "";
      width: 22px;
      height: 22px;
      border: 5px solid #7491f5;
      border-radius: 50%;
      position: absolute;
      left: 0px;
      background-color: #fff;
      margin-right: 10px;
    }
    .container-search {
      position: absolute;
      right: 0px;
    }
  }
  .container-one,
  .container-two {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-evenly;
    align-items: center;
  }
  .container-one {
    margin-bottom: 20px;
  }
  .taskProgress {
    width: 100%;
    display: flex;
  }
}
</style>
