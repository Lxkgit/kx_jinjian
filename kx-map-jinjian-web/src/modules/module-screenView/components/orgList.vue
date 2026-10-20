<template>
  <div class="orgList">
    <!-- <div v-for="(org, index) in orgList" :key="org.name">
      <span>{{ org.name }}</span>
      <span v-if="index != orgList.length - 1 && index != 0">/</span>
    </div> -->
    <div class="layers">{{ layers }}(网格：{{ minGridCount }})</div>
    <div class="gridCount" v-if="$store.state.screenView.serviceFlag">
      <span>现金服务点:</span>
      <span>{{ $store.state.screenView.cashPointTotal }}</span>
    </div>
    <div
      class="gridCount"
      v-else-if="$store.state.screenView.ruralFinancialFlag"
    >
      <span>农村金融服务站:</span>
      <span>{{ $store.state.screenView.ruralPointTotal }}</span>
    </div>
    <div class="gridCount" v-else>
      <span>{{ getGridName(gridCount.customGroupId) }}:</span>
      <span>{{ gridCount.count }}</span>
    </div>
  </div>
</template>

<script>
import service from "../api";
export default {
  data() {
    return {
      gridCount: {},
      options: [],
    };
  },
  computed: {
    orgList() {
      return this.$store.getters["screenView/orgList"];
    },
    layers() {
      return this.$store.getters["screenView/layers"];
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
    minGridCount() {
      return this.$store.getters["screenView/minGridCount"];
    },
  },
  watch: {
    orgList: {
      handler(val) {
        if (val.length > 0) {
          this.getLayers();
        }
        this.getTotalCount();
      },
    },
    filterParam: {
      handler(val) {
        if (val.groupIdList) {
          this.getTotalCount();
        }
      },
    },
  },
  mounted() {
    this.getCheckChild(2);
  },
  methods: {
    getCheckChild(type) {
      const param = {
        categoryId: type,
      };
      service.groupListNotPage(param).then((res) => {
        const { rows } = res;
        this.options = rows;
      });
    },
    getLayers() {
      const orgList = JSON.parse(JSON.stringify(this.orgList));
      const [lastObj] = orgList.slice(-1);
      service.category({ code: lastObj.code }).then((res) => {
        this.$store.commit("screenView/SET_LAYERS", res.data.path);
        this.$store.commit(
          "screenView/SET_MINGRIDCOUNT",
          res.data.minGridCount
        );
      });
    },
    getGridName(val) {
      if (val == 0) {
        return "网点";
      } else {
        const [obj] = this.options.filter((item) => item.id == val);
        return obj.customGroupName;
      }
    },
    getTotalCount() {
      const orgList = JSON.parse(JSON.stringify(this.orgList));
      const [lastObj] = orgList.slice(-1);
      const { groupIdList } = this.filterParam;
      const param = {
        gridCode: lastObj.code,
        groupId: groupIdList[0] || null,
      };
      service.totalCount(param).then((res) => {
        const { data } = res;
        this.gridCount = data;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.orgList {
  position: absolute;
  font-size: 22px;
  font-weight: 800;
  color: #78effc;
  left: 50px;
  top: 16px;
  .gridCount {
    font-size: 18px;
    margin-top: 8px;
  }
}
</style>