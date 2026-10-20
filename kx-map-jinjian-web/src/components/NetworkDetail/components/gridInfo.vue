<template>
  <div class="network-wrapper">
    <titleBox title="最近一次事件" class="title-box">
      <el-button type="primary" size="small" @click="addEvent"
        >新增网格事件</el-button
      >
      <el-card class="box-card">
        <infoList :infoData="infoData" :gridColumns="2"></infoList>
      </el-card>
    </titleBox>

    <titleBox title="历史事件">
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

    <addGirdInfo
      v-model="showFlag"
      :status="status"
      :editInfo="editInfo"
    ></addGirdInfo>
  </div>
</template>
  
  <script>
import titleBox from "@/components/BaseDetail/components/titleBox.vue";
import infoList from "@/components/BaseDetail/components/infoList.vue";
import labelItem from "@/components/BaseDetail/components/labelItem.vue";
import pageMixin from "@/mixins/pageMixin";
import { getComplaint, getComplaintlist, deleteEven } from "../api";
import addGirdInfo from "../cell/addGirdInfo.vue";
export default {
  components: {
    titleBox,
    infoList,
    labelItem,
    addGirdInfo,
  },
  mixins: [pageMixin],
  dicts: ["complaint_source"],
  data() {
    return {
      infoData: [
        { label: "投诉来源", value: "", key: "complaintSource" },
        { label: "投诉时间", value: "", key: "complaintTime" },
        { label: "投诉人", value: "", key: "complainant" },
        { label: "投诉人联系电话", value: "", key: "complainantPhone" },
        { label: "线索来源", value: "", key: "leadSource" },
        { label: "行业", value: "", key: "industry" },
        { label: "发生场景", value: "", key: "scene" },
        {
          label: "是否为拒收人民币现金行为",
          value: "",
          key: "isCashRejection",
        },
        {
          label: "具体事由",
          value: "",
          key: "specificReason",
          gridColumn: "1/-1",
        },
        {
          label: "处置情况",
          value: "",
          key: "disposalSituation",
          gridColumn: "1/-1",
        },
        { label: "网点联络员", value: "", key: "branchLiaison" },
        { label: "联络员联系电话", value: "", key: "branchLiaisonPhone" },
        { label: "上级负责人名称", value: "", key: "superiorsName" },
        { label: "上级负责人电话", value: "", key: "superiorPhone" },
      ],
      columns: [
        { label: "投诉人", prop: "complainant" },
        { label: "投诉人联系电话", prop: "complainantPhone" },
        {
          label: "投诉来源",
          prop: "complaintSource",
          formatter: ({ complaintSource }) => {
            return this.dict.label.complaint_source[complaintSource];
          },
        },
        {
          label: "线索来源",
          prop: "leadSource",
            formatter: ({ leadSource }) => {
            return  ["投诉",'舆情'][leadSource];
          },
        },
        { label: "投诉时间", prop: "complaintTime" },
        // { label: "投诉事件描述", prop: "description" },
        { label: "网点联络员", prop: "branchLiaison" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewhandle",
        },
        // {
        //   cname: "删除",
        //   ename: "deletehandle",
        // },
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
  mounted() {
    this.getComplaint();
    this.getComplaintlist();
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
          this.getComplaintlist();
          this.getComplaint();
        });
      });
    },
    getComplaint() {
      const param = {
        anchorName: this.customerParam.customerName,
      };
      getComplaint(param).then((res) => {
        const { data } = res;
        this.infoData.forEach((item) => {
          const value = data[item.key];
          if (item.key == "complaintSource") {
            item.value = this.dict.label.complaint_source[value];
          } else if (item.key == 'leadSource') {
            item.value = ["投诉",'舆情'][value]
          } else if (item.key == 'isCashRejection') {
            item.value = ["是",'否'][value]
          }else {
            item.value = value || "";
          }
        });
      });
    },
    getComplaintlist() {
      const param = {
        anchorName: this.customerParam.customerName,
      };
      getComplaintlist(param).then((res) => {
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