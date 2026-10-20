<template>
  <div class="network-wrapper">
    <titleBox title="基本信息">
      <infoList :infoData="infoData" :gridColumns="3"></infoList>
      <!-- <infoList :infoData="infoData2" :gridColumns="3"></infoList> -->
    </titleBox>
    <titleBox title="网点功能">
      <div class="label-list">
        <span
          v-for="(item, index) in labelList"
          :key="index"
          class="label-item"
        >
          <span class="index">{{ index + 1 }}</span>
          <span class="item">{{ item }}</span>
        </span>
      </div>
    </titleBox>
    <titleBox title="管理网格信息" v-if="isDutyPoint">
      <!-- <div class="title">网格信息</div> -->
      <i-table
        ref="table1"
        :loading="loading1"
        :data="tableData1"
        align="center"
        stripe
        :allColumns="columns1"
        :showPagination="false"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="table1Btns"
            @click="(type) => clickHandle(type, scope.row)"
          >
          </i-table-btns>
        </template>
        <el-empty
          slot="empty"
          description="暂无数据"
          :image-size="100"
        ></el-empty>
      </i-table>
      <!-- <div class="title">网格中现金服务点信息</div>
      <i-table
        ref="table2"
        :loading="loading2"
        :data="tableData2"
        align="center"
        stripe
        :allColumns="columns2"
        :showPagination="false"
      >
        <el-empty
          slot="empty"
          description="暂无数据"
          :image-size="100"
        ></el-empty>
      </i-table>
      <div class="title">网格中金融服务站信息</div>
      <i-table
        ref="table3"
        :loading="loading3"
        :data="tableData3"
        align="center"
        stripe
        :allColumns="columns3"
        :showPagination="false"
      >
        <el-empty
          slot="empty"
          description="暂无数据"
          :image-size="100"
        ></el-empty>
      </i-table> -->
    </titleBox>
  </div>
</template>

<script>
import titleBox from "@/components/BaseDetail/components/titleBox.vue";
import infoList from "@/components/BaseDetail/components/infoList.vue";
import labelItem from "@/components/BaseDetail/components/labelItem.vue";
import {
  branchInfo,
  getbranchInfolabel,
  getEquipmentNum,
  getManageGridInfo,
  getCashPointInGrid,
  getFinancialInGrid,
} from "../api";
import pageMixin from "@/mixins/pageMixin";
export default {
  components: {
    titleBox,
    infoList,
    labelItem,
  },
  mixins: [pageMixin],
  dicts: ["service_type", "new_grid_type"],
  data() {
    return {
      infoData: [
        {
          label: "网点名称",
          value: "",
          key: "customerName",
          gridColumn: "1/-1",
        },
        {
          label: "网点地址",
          value: "",
          key: "customerAddress",
          gridColumn: "1/-1",
        },
        { label: "所属银行机构", value: "", key: "bankAgencyName" },
        { label: "网点级别", value: "", key: "anchorRanks" },
        { label: "网点所在地类型", value: "", key: "ruralGridType" },
        { label: "网点负责人", value: "", key: "dutyName" },
        { label: "负责人固定电话", value: "", key: "dutyTelephone" },
        { label: "负责人移动电话", value: "", key: "dutyPhone" },
        { label: "所属网格", value: "", key: "gridName" },
        { label: "所属网格网格长", value: "", key: "gridMainManagerName" },
        { label: "网格长固定电话", value: "", key: "gridMainManagerPhone" },
        { label: "自动取款机ATM数量", value: "", key: "atmMachine" },
        { label: "存取款一体机CRS", value: "", key: "depositMachine" },
        { label: "现金柜员机", value: "", key: "cashTellerMachine" },
      ],
      labelList: [],
      columns1: [
        {
          label: "网格名称",
          prop: "belongGrid",
          showOverFlowTooltip: true,
        },
        {
          label: "所在镇街",
          prop: "belongTown",
        },
        {
          label: "所在县域",
          prop: "belongCounty",
        },
        {
          label: "操作",
          slot: true,
        },
      ],
      tableData1: [],
      table1Btns: [
        {
          cname: "详情",
          ename: "viewGridDetail",
        },
      ],
      columns2: [
        {
          label: "服务点名称",
          prop: "servePointName",
          showOverFlowTooltip: true,
        },
        {
          label: "所在网格",
          prop: "gridName",
        },
        {
          label: "服务点地址",
          prop: "servePointAddr",
        },
        {
          label: "现金服务点负责人",
          prop: "dutyName",
        },
        {
          label: "联系电话",
          prop: "dutyPhone",
        },
      ],
      tableData2: [],
      columns3: [
        {
          label: "金融服务站名称",
          prop: "finicialName",
          showOverFlowTooltip: true,
        },
        {
          label: "所在网格",
          prop: "belongGrid",
        },
        {
          label: "具体地址",
          prop: "addrDetail",
        },
        {
          label: "站长姓名",
          prop: "stationMasterName",
        },
        {
          label: "联系电话",
          prop: "linkPhone",
        },
      ],
      tableData3: [],
      loading1: false,
      loading2: false,
      loading3: false,
    };
  },
  computed: {
    customerParam() {
      return this.$store.getters["gridManagement/customerParam"];
    },
    isDutyPoint() {
      return this.$store.getters[
        "gridManagement/customerParam"
      ].dotType?.includes("2");
    },
    serviceType() {
      return this.dict.label.service_type;
    },
  },
  watch: {
    customerParam: {
      handler(val) {
        this.tableData1 = [];
        this.tableData2 = [];
        this.tableData3 = [];
        this.getBranchInfoInfo();
        // this.getEquipment();
        this.getbranchInfolabel();
        this.fecthList1();
      },
      deep: true,
      immediate: true,
    },
  },
  methods: {
    getBranchInfoInfo() {
      const param = {
        customerName: this.customerParam.customerName,
      };
      branchInfo(param).then((res) => {
        const { data } = res;
        this.infoData = this.infoData.filter((i) => i.key != "dotIntroduce");
        let { dotType } = this.customerParam;
        if (dotType && (dotType.includes("0") || dotType.includes("1"))) {
          this.infoData.splice(9, 0, {
            label: "网点介绍",
            value: "",
            key: "dotIntroduce",
            gridColumn: "span 3",
          });
        }
        this.infoData.forEach((item) => {
          if (item.key == "ruralGridType") {
            item.value = this.dict.label.new_grid_type[data[item.key]];
          } else {
            item.value = data[item.key] || "";
          }
        });
        this.getEquipment();
      });
    },
    getbranchInfolabel() {
      const param = {
        customerName: this.customerParam.customerName,
      };
      getbranchInfolabel(param).then((res) => {
        const { data } = res;
        this.labelList = data;
        console.log("serviceType", this.serviceType);
        // let arr = [
        //   "",
        //   "人民币现金存取",
        //   "本外币现金存取",
        //   "小面额现金兑换",
        //   "数字人民币服务",
        //   "自助现金存取",
        //   "外卡存取现",
        //   "假币鉴定",
        //   "真伪授权鉴定",
        //   "特殊残损人民币兑换",
        //   "停止流通人民币兑换",
        // ];

        // this.labelList = this.labelList.map((item) => arr[item] || "");
        this.labelList = this.labelList.map(
          // (item) => this.serviceType[item] || ""
          (item) => this.dict.label.service_type[item] || ""
        );
      });
    },
    getEquipment() {
      const param = {
        anchorName: this.customerParam.customerName + "",
      };
      getEquipmentNum(param).then((res) => {
        const { data } = res;
        let arr = ["atmMachine", "depositMachine", "cashTellerMachine"];
        this.infoData.forEach((item) => {
          if (arr.some((i) => i == item.key))
            item.value = data[0][item.key] || "";
        });
      });
    },
    fecthList1() {
      if (!this.isDutyPoint) return;
      this.loading1 = true;
      let param = {
        dutyNetworkName: this.customerParam.customerName,
      };
      getManageGridInfo(param)
        .then((res) => {
          this.tableData1 = res.data;
          this.loading1 = false;
          // let arr = res.data.map((i) => i.gridCode);
          // this.fecthList2(arr);
          // this.fecthList3(arr);
        })
        .catch((err) => {
          this.loading1 = false;
        });
    },
    fecthList2(gridCodes) {
      this.loading2 = true;
      let data = gridCodes;
      getCashPointInGrid(data)
        .then((res) => {
          this.tableData2 = res.data;
          this.loading2 = false;
        })
        .catch((err) => {
          this.loading2 = false;
        });
    },
    fecthList3(gridCodes) {
      this.loading3 = true;
      let data = gridCodes;
      getFinancialInGrid(data)
        .then((res) => {
          this.tableData3 = res.data;
          this.loading3 = false;
        })
        .catch((err) => {
          this.loading3 = false;
        });
    },
    viewGridDetail(row) {
      this.$store.commit("gridManagement/SET_GRID_INFO", {
        type: 1,
        arrCode: row.gridCode,
      });
      if (this.$route.name == "PerformanceManage") {
        this.$router.push({
          name: "GridInfoManage",
        });
      } else if (this.$route.name == "BaseView") {
        const route = this.$router.resolve({
          path: "/gridManagement/gridManage/gridInfoManage",
        });
        window.open(route.href, "_blank");
      }
    },
  },
};
</script>

<style scoped lang="scss">
.network-wrapper {
  padding: 20px;
  .label-list {
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
  }
}
.label-item {
  display: inline-block;
  margin-right: 10px;
  margin-bottom: 10px;
  background: rgba($color: #4a92ed, $alpha: 0.6);
  border-radius: 16px;
  line-height: 33px;
  font-size: 16px;
  padding: 0 20px;
  color: #fff;
  .index {
    margin-right: 7px;
  }
}
.title {
  font-size: 18px;
  font-weight: bold;
  margin-top: 10px;
}
</style>