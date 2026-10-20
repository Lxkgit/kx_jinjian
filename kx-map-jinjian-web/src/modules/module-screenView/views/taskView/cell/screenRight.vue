<template>
  <div class="screen-right" :class="{ 'screen-right-show': rightShow }">
    <div class="repeat-down" />
    <div class="main-wrapper">
      <titleBox height="300px" title="任务排名">
        <task-rate id="suibianle"></task-rate>
        <div class="task-rate-title">我的排名</div>
        <div class="rank">
          <div class="bank-rank">
            <div class="bank-rank-name rank-name">总行排名</div>
            <div class="bank-rank-value rank-value">12/505</div>
          </div>
          <div class="mechanism-rank">
            <div class="mechanism-rank-name rank-name">机构排名</div>
            <div class="mechanism-rank-value rank-value">1/552</div>
          </div>
        </div>
      </titleBox>

      <titleBox height="660px">
        <!-- 主/子任务 列表 -->
        <task-list @checkDetail="checkDetail" @indicatorView="indicatorView"
                   @checkSonDetail="checkSonDetail" @getTaskSon="getTaskSon" :list="list"
                   @view="view"></task-list>

        <!-- 主任务详情 -->
        <main-task-detail @close="close(1)" :form="form" v-show="showMainTaskDetail"
                          class="main-task-detail manager-masking"></main-task-detail>

        <!-- 客户反馈 -->
        <manager-task-feedback class="manager-task-feedback manager-masking" @close="close(2)"
                               :form="formSon" v-show="showSonTaskDetail"></manager-task-feedback>

        <!-- 指标视图 -->
        <task-indicator-view @close="close(3)" v-if="showIndicatorView" :taskIndex="taskIndex"
                             class="manager-masking"></task-indicator-view>
      </titleBox>
    </div>
    <div class="operate-wrapper">
      <backPart></backPart>
      <screenSelect></screenSelect>
    </div>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";

import screenSelect from "@/modules/module-screenView/components/operate/screenSelect";
import backPart from "@/modules/module-screenView/components/operate/backPart";
import taskRate from "../components/taskRate";
import taskList from "../components/taskList";
import taskIndicatorView from "../components/taskIndicatorView";
import mainTaskDetail from "../components/mainTaskDetail";
import managerTaskFeedback from "../components/managerTaskFeedback";

import { getVisitRecordInfoByDetailId } from "./mock";
import services from "./";

export default {
  components: {
    titleBox,
    screenSelect,
    backPart,
    taskRate,
    taskList,
    taskIndicatorView,
    mainTaskDetail,
    managerTaskFeedback,
  },
  props: {
    rightShow: Boolean,
  },
  data() {
    return {
      wealthBusinessList: [],
      savingsStructureData: [],
      assetBusinessList: [],
      dashList: [],
      list: [],

      showSonTaskDetail: false, // 子任务视图
      showMainTaskDetail: false, //主任务视图
      showIndicatorView: false, // 指标视图
      form: {}, //主任务数据
      formSon: {}, //子任务数据
    };
  },
  mounted() {
    this.getlistTask();
  },
  methods: {
    // 主任务列表
    async getlistTask() {
      services.taskListPage({ pageNum: 1, pageSize: 10 }).then((res) => {
        this.list = res.rows.map((v) => {
          return {
            name: v.taskName,
            status: v.taskStatus,
            dept: v.deptName,
            sonList: null,
            rata: `${v.completeProcess}%`,
            id: v.id,
          };
        });
      });
    },
    // 主任务详情
    async checkDetail(id) {
      const res = await services.taskListPage({ id });
      const {
        taskName: name,
        startTime: beginDate,
        endTime: endDate,
        isCycle,
        customGroupName: E,
        taskTypeName: D,
        taskLevelName: F,
        createBy: G,
        unFinishNum: J,
        completeProcess: H,
        finishNum: L,
        taskDetailNum: I,
        manualEndNum: K,
      } = res.rows[0];
      this.form = {
        name,
        B: [beginDate, endDate],
        C: !!isCycle,
        D,
        E,
        F,
        G,
        H: `${H}%`,
        I,
        J,
        K,
        L,
      };
      this.showMainTaskDetail = true;
    },
    // 子任务详情
    async checkSonDetail(id) {
      const taskMethodRes = await services.listTaskMethod();
      const type = taskMethodRes.data;
      const res = await services.myTaskPage({ ids: [id] });
      const {
        plan: { taskRemark: B, taskWayTwo },
        whetherPublic,
        custom,
        paCompanyInfo,
      } = res.rows;
      let name = "";
      if (taskWayTwo) {
        name = type[taskWayTwo - 1].name;
      }

      //客户信息
      let subInfo = [];
      if (whetherPublic) {
        //对公
        const {
          name,
          nm,
          outlet,
          managerName,
          address,
          telephone,
          legalPerson,
          creditCode,
        } = paCompanyInfo;
        let names = [
          "企业名称",
          "企业内码",
          "所属网点",
          "客户经理",
          "经营地址",
          "联系电话",
          "法人姓名",
          "统一社会信用代码",
        ];
        let values = [
          name,
          nm,
          outlet,
          managerName,
          address,
          telephone,
          legalPerson,
          creditCode,
        ];
        names.forEach((name, index) => {
          subInfo.push({ name, value: values[index] });
        });
      } else {
        //对私
        const {
          name,
          homeAddress,
          work,
          star,
          maritalStatus,
          identify,
          contractAddress,
          profession,
          telephone,
          managerName,
        } = custom;
        let names = [
          "客户姓名",
          "户籍地址",
          "工作单位",
          "客户星级",
          "婚姻状况",
          "身份证号",
          "联系地址",
          "客户职业",
          "联系电话",
          "客户经理",
        ];
        let values = [
          name,
          homeAddress,
          work,
          star,
          maritalStatus,
          identify,
          contractAddress,
          profession,
          telephone,
          managerName,
        ];
        names.forEach((name, index) => {
          subInfo.push({ name, value: values[index] });
        });
      }

      this.formSon = { B, name, subInfo };
      this.showSonTaskDetail = true;
    },
    // 子任务列表
    async getTaskSon(taskName, index) {
      let res = await services.myTaskPage({
        taskName,
        pageNum: 1,
        pageSize: 10,
      });
      let r = res.rows.map((v) => {
        return {
          name: v.customerName,
          status: v.childTaskStatus,
          manage: v.businessManageUser,
          dept: v.deptName,
          id: v.id,
        };
      });
      this.list[index].sonList = r;
    },
    // 指标视图
    indicatorView(e) {
      this.taskIndex = e;
      this.showIndicatorView = true;
    },
    close(e) {
      if (e == 1) {
        this.showMainTaskDetail = false;
      } else if (e == 2) {
        this.showSonTaskDetail = false;
      } else if (e == 3) {
        this.showIndicatorView = false;
      }
    },
    async view(type, index) {
      if (type == "main") {
        let c = this.list[index];
        if (!c.sonList) {
          await this.getTaskSon(c.id, index);
        }
        let res = [];
        let points = [];
        c.sonList.forEach((v) => {
          const { lon, lat, positions } = v;
          let position = positions ? JSON.parse(positions)[0] : [];
          res.push(position);
          points.push([lon, lat]);
        });
        if (res.length) {
          // this.$parent.$parent.$refs.meshMap.drawLine(res,points)
          // this.$parent.$parent.$refs.meshMap.animate(points[0],15)
        }
      } else {
        const { lon, lat, positions } = type;
        let position = positions ? JSON.parse(positions)[0] : [];
        let res = [position];
        let points = [[lon, lat]];
        // this.$parent.$parent.$refs.meshMap.drawLine(res,points)
        // this.$parent.$parent.$refs.meshMap.animate([lon,lat],16)
      }
    },
  },
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.ml-margin {
  margin: 0 15px;
}
.bussiness {
  margin-top: 15px;
}
.screen-right {
  position: absolute;
  width: 960px;
  top: 71px;
  height: 1009px;
  right: 0;
  pointer-events: none;
  transition: all 0.3s linear;
  background: url("~@/modules/module-screenView/assets/image/bg-right.png"),
    url("~@/modules/module-screenView/assets/image/repeat-up.png") 470px top
      no-repeat;
  background-size: 100% 100%, 0px 89px;
  &.screen-right-show {
    right: -440px;
    background: url("~@/modules/module-screenView/assets/image/bg-right.png"),
      url("~@/modules/module-screenView/assets/image/repeat-up.png") 30px top
        no-repeat;
    background-size: 100% 100%, 440px 89px;
    .repeat-down {
      width: 440px;
      left: -440px;
    }
  }
  .repeat-down {
    width: 0px;
    height: 24px;
    position: absolute;
    bottom: 0px;
    left: 0px;
    background: url("~@/modules/module-screenView/assets/image/repeat-down.png")
      repeat;
    transition: all 0.3s linear;
  }

  .main-wrapper {
    width: 417px;
    height: 980.6px;
    position: absolute;
    top: 10px;
    right: 20px;
    pointer-events: auto;
  }
  .operate-wrapper {
    position: absolute;
    right: 490px;
    top: 40px;
    z-index: 999;
    pointer-events: auto;
    display: flex;
  }
}
// 任务排名
.task-rate-title {
  margin-left: 30px;
  color: #00f6ff;
}
.rank {
  margin-top: 20px;
  @include flex;
  color: white;
  .rank-name {
    font-size: 14px;
    margin-left: 60px;
  }
  .rank-value {
    font-size: 24px;
    margin-left: 60px;
    font-family: screenSpecial;
  }
  .bank-rank {
    width: 180px;
    height: 60px;
    background-size: 100% 100%;
    background-image: url("../assets/image/bankRank.png");
  }
  .mechanism-rank {
    width: 160px;
    height: 60px;
    background-size: 100% 100%;
    background-image: url("../assets/image/mechanismRank.png");
  }
}

.manager-masking {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
.main-task-detail {
  padding: 10px 20px 0 20px;
}
</style>
