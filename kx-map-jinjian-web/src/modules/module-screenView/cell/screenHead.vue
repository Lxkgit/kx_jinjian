<template>
  <div class="map-head">
    <div class="map-head-btn" @click="$emit('turnSwitch', 'left')">
      <!-- <shrinkButton turn="left" :is-open="leftShow" /> -->
    </div>
    <!-- <orgName></orgName> -->
    <!-- 标题 -->
    <span class="map-title">
      <!-- <img src="../assets/image/logo.png" alt=""> -->
      {{ pageTitle }}
    </span>
    <!-- 地图机构名称 -->

    <div class="map-head-btn" @click="$emit('turnSwitch', 'right')">
      <!-- <shrinkButton turn="right" :is-open="rightShow" /> -->

      <!-- 退出 -->
      <div class="map-btn back-btn" @click="backHandle"></div>
      <!-- 业务中心 -->
      <div class="map-btn business-btn" @click="businessHandle">业务中心</div>
      <!-- 视图切换下拉框 -->
      <div class="view-select-wrapper">
        <select
          class="view-select"
          v-model="currentView"
          @change="handleViewChange"
        >
          <option value="baseView">防非打非"监控大屏"</option>
          <option value="riskView">一体化运营风控视图</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script>
import shrinkButton from "@/modules/module-screenView/components/shrinkButton";
import orgName from "../components/orgName";
import orgList from "../components/orgList.vue";
export default {
  components: {
    shrinkButton,
    orgName,
    orgList,
  },
  props: {
    leftShow: Boolean,
    rightShow: Boolean,
  },
  data() {
    return {
      currentView: "baseView",
    };
  },
  computed: {
    addRoutes() {
      return this.$store.getters["addRoutes"];
    },
    pageTitle() {
      if (this.$route.name === "RiskView") {
        return "一体化运营风控视图";
      }
      return '防非打非"金哨"案档与台账管理平台';
    },
  },
  watch: {
    $route: {
      handler(route) {
        if (route.name === "BaseView") {
          this.currentView = "baseView";
        } else if (route.name === "RiskView") {
          this.currentView = "riskView";
        }
      },
      immediate: true,
    },
  },
  methods: {
    backHandle() {
      this.$confirm("确定注销并退出系统吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$store.dispatch("LogOut").then(() => {
            location.href = "/";
          });
        })
        .catch(() => {});
    },
    businessHandle() {
      const { name, path } = this.getRouterName();
      this.$router.push({ name });
    },
    getRouterName() {
      let target = "";
      for (let route of this.addRoutes) {
        if (route.name === "GridManagement") {
          target = route.children[0];
        }
      }
      return target;
    },
    handleViewChange() {
      if (this.currentView === "baseView") {
        this.$router.push({ name: "BaseView" });
      } else if (this.currentView === "riskView") {
        this.$router.push({ name: "RiskView" });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.map-head {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100px;
  background: url("../assets/image/bg-top.png");
  background-size: 100% 100%;
  display: flex;
  justify-content: space-between;
  pointer-events: auto;

  .map-head-btn {
    height: 34px;
    margin: 20px;
  }

  .map-title {
    height: 70px;
    font-size: 30px;
    font-weight: 800;
    color: #fff;
    display: flex;
    align-items: center;

    img {
      width: 43px;
      height: 43px;
      margin-right: 14px;
    }
  }

  .map-btn {
    position: absolute;
    width: 86px;
    height: 34px;
    line-height: 34px;
    text-align: center;
    background-image: url("~@/modules/module-screenView/assets/image/headBtn.png");
    background-size: 100% 100%;
    color: #fff;
    cursor: pointer;
  }

  .back-btn {
    width: 34px;
    top: 10px;
    right: 35pt;
    background-image: url("~@/modules/module-screenView/assets/image/topExit.png");
    background-size: 100% 100%;
  }

  .business-btn {
    top: 10px;
    right: 90px;
    background-image: url("~@/modules/module-screenView/assets/image/topBtn.png");
    background-size: 100% 100%;
  }

  .view-select-wrapper {
    position: absolute;
    top: 10px;
    right: 186px;
    width: 180px;
    height: 34px;
    background-image: url("~@/modules/module-screenView/assets/image/topBtn.png");
    background-size: 100% 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0 10px;
  }

  .view-select {
    width: 100%;
    height: 28px;
    background: transparent;
    border: none;
    outline: none;
    color: #fff;
    font-size: 14px;
    cursor: pointer;
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='white' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right 5px center;
    background-size: 14px;
    padding-right: 25px;
  }

  .view-select option {
    background-color: #0a2a5e;
    color: #fff;
  }
}
</style>
