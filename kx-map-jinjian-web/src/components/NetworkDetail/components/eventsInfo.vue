<template>
  <div class="network-wrapper">
    <titleBox title="最近一次投诉" class="title-box">
      <el-button type="primary" size="small" @click="addEvent"
        >新增网点投诉</el-button
      >
      <el-card class="box-card">
        <infoList :infoData="infoData" :gridColumns="2"></infoList>
      </el-card>
    </titleBox>

    <titleBox title="历史投诉">
      <i-table
        :allColumns="columns"
        border
        style="margin-top: 10px"
        :showPagination="false"
        align="center"
        :data="tableData"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </titleBox>

    <addEvent
      v-model="showFlag"
      :status="status"
      :editInfo="editInfo"
    ></addEvent>
  </div>
</template>

<script>
import titleBox from "@/components/BaseDetail/components/titleBox.vue";
import infoList from "@/components/BaseDetail/components/infoList.vue";
import labelItem from "@/components/BaseDetail/components/labelItem.vue";
import addEvent from "../cell/addEvent";
import pageMixin from "@/mixins/pageMixin";
import { getEvensRecently, getEvensAll, deleteEven } from "../api";
export default {
  components: {
    titleBox,
    infoList,
    labelItem,
    addEvent,
  },
  mixins: [pageMixin],
  dicts: ["complaint_source"],
  data() {
    return {
      infoData: [
        { label: "投诉来源", value: "", key: "complainantType" },
        { label: "投诉时间", value: "", key: "complainTime" },
        { label: "投诉人", value: "", key: "complainant" },
        { label: "投诉人联系电话", value: "", key: "complainantPhone" },
        {
          label: "投诉事件描述",
          value: "",
          key: "description",
          gridColumn: "1/-1",
        },
        { label: "网点联络员", value: "", key: "handler" },
        { label: "联络员联系电话", value: "", key: "handlerPhone" },
        { label: "上级负责人名称", value: "", key: "superiorsName" },
        { label: "上级负责人电话", value: "", key: "superiorsPhone" },
      ],
      columns: [
        { label: "投诉人", prop: "complainant" },
        { label: "投诉人联系电话", prop: "complainantPhone" },
        {
          label: "投诉来源",
          prop: "complainantType",
          formatter: ({ complainantType }) => {
            return this.dict.label.complaint_source[complainantType];
          },
        },
        { label: "投诉时间", prop: "complainTime" },
        { label: "投诉事件描述", prop: "description" },
        { label: "网点联络员", prop: "handler" },
        { label: "联络员联系电话", prop: "handlerPhone" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewhandle",
        },
        {
          cname: "删除",
          ename: "deletehandle",
        },
      ],
      showFlag: false,
      tableData: [],
      editInfo: {},
      status: "add",
    };
  },
  computed: {
    customerParam() {
      return this.$store.getters["gridManagement/customerParam"];
    },
    complaintType() {
      return;
    },
  },
  watch: {
    // customerParam: {
    //   handler() {
    //     this.getEvensRecently();
    //     this.getEvensAll();
    //   },
    //   immediate: true,
    // },
  },
  mounted() {
    this.getEvensRecently();
    this.getEvensAll();
  },
  methods: {
    addEvent() {
      this.showFlag = true;
      this.editInfo = {};
      this.status = "add";
    },
    viewhandle(row) {
      this.showFlag = true;
      this.editInfo = row;
      this.status = "edit";
    },
    deletehandle(row) {
      this.makeSure("确认删除?", () => {
        const data = {
          id: row.id,
        };
        deleteEven(data).then((res) => {
          this.$modal.msgSuccess("删除成功");
          this.getEvensAll();
          this.getEvensRecently();
        });
      });
    },
    getEvensRecently() {
      const param = {
        customerName: this.customerParam.customerName,
      };
      getEvensRecently(param).then((res) => {
        const { data } = res;
        this.infoData.forEach((item) => {
          const value = data[item.key];
          if (item.key == "complainantType") {
            item.value = this.dict.label.complaint_source[value];
          } else {
            item.value = value || "";
          }
        });
      });
    },
    getEvensAll() {
      const param = {
        customerName: this.customerParam.customerName,
      };
      getEvensAll(param).then((res) => {
        this.tableData = res.data;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.network-wrapper {
  padding: 20px;
}
.box-card {
  margin: 20px 0;
  ::v-deep label {
    font-size: 16px;
  }
}
.title-box {
  position: relative;
  .el-button {
    position: absolute;
    right: 0px;
    top: 0px;
  }
}
</style>