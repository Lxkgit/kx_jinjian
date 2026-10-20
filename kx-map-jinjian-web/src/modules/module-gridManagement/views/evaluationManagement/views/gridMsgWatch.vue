<template>
  <PageContent>
    <div class="top">
      <div
        class="box"
        :class="{ active: item.value == activeValue }"
        v-for="item in topBtnList"
        :key="item.value"
        @click="changeActiveValue(item.value)"
      >
        <div class="num">{{ item.num }}</div>
        <div class="label">{{ item.label }}</div>
      </div>
    </div>
    <i-table
      ref="table"
      :loading="loading"
      :data="tableData"
      align="center"
      stripe
      :allColumns="columns"
      :total="total"
      :pageNum="formInline.pageNum"
      :maxHeight="maxHeight"
      :pageSize="formInline.pageSize"
      @currentChange="currentChange"
      @sizeChange="sizeChange"
    >
      <template #complaintPerson="scope">
        <div
          class="left-circle"
          :style="{
            '--bgColor': scope.row.hadResolve == '0' ? 'green' : 'red',
          }"
        >
          {{ scope.row.complaintPerson }}
        </div>
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
    <msg-watch-detail
      v-model="showDetail"
      :defaultValue="defaultValue"
    ></msg-watch-detail>
  </PageContent>
</template>

<script>
import tableData from "../gridMsgWatchData.js";
import pageMixin from "@/mixins/pageMixin";
import msgWatchDetail from "../components/msgWatchDetail.vue";
export default {
  components: { msgWatchDetail },
  mixins: [pageMixin],
  dicts: ["complaint_source"],
  data() {
    return {
      activeValue: "1",
      topBtnList: [
        { value: "1", label: "总投诉数量", num: "25" },
        { value: "2", label: "已处理投诉数量", num: "25" },
        { value: "3", label: "未处理投诉数量", num: "0" },
        { value: "4", label: "外来投诉事件数量", num: "20" },
        { value: "5", label: "网点上报事件", num: "5" },
      ],
      columns: [
        {
          label: "投诉人",
          prop: "complaintPerson",
          slot: true,
        },
        {
          label: "投诉人联系电话",
          prop: "complaintPersonPhone",
        },
        // {
        //   label: "投诉来源",
        //   prop: "complaintPersonSource",
        //   formatter: ({ complaintPersonSource }) => {
        //     return this.dict.label.complaint_source[complaintPersonSource];
        //   },
        // },
        {
          label: "投诉时间",
          prop: "complaintTime",
        },
        {
          label: "线索来源",
          prop: "leadSource",
        },
        {
          label: "行业",
          prop: "industry",
        },
        {
          label: "场景",
          prop: "scene",
        },
        {
          label: "投诉所在网格",
          prop: "complaintInGrid",
        },
        {
          label: "网格责任网点",
          prop: "gridDutyPoint",
        },
        {
          label: "责任网点负责人",
          prop: "dutyPointDutyPerson",
        },
        {
          label: "负责人联系电话",
          prop: "dutyPersonPhone",
        },
        {
          label: "网格长",
          prop: "gridManager",
        },
        {
          label: "网格长联系电话",
          prop: "gridManagerPhone",
        },
        {
          label: "操作",
          slot: true,
        },
      ],
      tableData: [],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandel",
        },
      ],
      showDetail: false,
      defaultValue: {},
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      let data = [...tableData];
      switch (this.activeValue) {
        case "2":
          this.tableData = data.filter((item) => item.hadResolve == "0");
          break;
        case "3":
          this.tableData = data.filter((item) => item.hadResolve == "1");
          break;
        case "4":
          this.tableData = data.filter(
            (item) => item.complaintPersonSource == "1"
          );
          break;
        case "5":
          this.tableData = data.filter(
            (item) => item.complaintPersonSource == "2"
          );
          break;
        default:
          this.tableData = data;
          break;
      }
      this.total = this.tableData.length;
    },
    changeActiveValue(val) {
      if (val == this.activeValue) return;
      else {
        this.activeValue = val;
        this.fetchList();
      }
    },
    viewHandel(row) {
      this.defaultValue = row;
      this.showDetail = true;
    },
  },
};
</script>

<style scoped lang="scss">
.top {
  // border: 1px solid red;
  display: flex;
  justify-content: space-around;
  align-items: center;
  // padding: 10px;
  // margin: 10px;
  .box {
    // width: 15%;
    flex: 1;
    display: flex;
    border: 3px solid #5e81f4;
    border-radius: 10px;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
    margin: 10px 20px;
    padding: 10px 20px;
    cursor: pointer;
    .num {
      font-size: 18px;
      margin: 5px 0;
    }
    .label {
      // margin: 5px 0;
    }
  }
  .active {
    border: 3px solid #fff;
    color: #fff;
    background-color: #5e81f4;
  }
}
.left-circle {
  position: relative;
  width: fit-content;
  margin: 0 auto;
  &::before {
    position: absolute;
    content: "";
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background-color: var(--bgColor);
    top: 50%;
    left: 0%;
    transform: translate(-140%, -50%);
  }
}
</style>