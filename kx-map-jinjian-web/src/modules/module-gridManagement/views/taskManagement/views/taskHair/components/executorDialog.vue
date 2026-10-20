<template>
  <i-dialog
    title="执行人选择"
    v-model="showDialog"
    width="80%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
    :appendToBody="true"
  >
    <el-row :gutter="20">
      <el-col :span="5">
        <div class="sub-title">机构选择</div>
        <div class="scroll-box left">
          <div
            class="org-item"
            v-for="(item, index) in orgList"
            :class="{
              selected: item.deptId == selectedOrg.deptId,
              noselected: item.deptId != selectedOrg.deptId,
            }"
            :key="index"
            @click="changeSelect(item)"
          >
            <span>{{ item.deptName }}</span>
            <i v-show="hasOrgItem(item)" class="el-icon-circle-check icon"></i>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="middle">
          <div class="sub-title">执行网点选择</div>
          <i-filter-bar
            :columns="searchColumns"
            ref="filterBar"
            slot="filter"
            :gridColumns="2"
            label-width="80px"
          >
            <!-- 搜索与重置按钮 -->
            <el-button type="primary">搜索</el-button>
            <el-button>重置</el-button>
          </i-filter-bar>
          <i-table
            ref="table"
            :loading="loading"
            :data="tableData"
            :max-height="350"
            align="center"
            :allColumns="columns"
            :total="total"
            :smallPage="false"
            :stripe="false"
            :pageNum="formInline.pageNum"
            :pageSize="formInline.pageSize"
            :rowStyle="selectRow"
            @currentChange="currentChange"
            @rowClick="updateSelectedList"
          >
          </i-table>
        </div>
      </el-col>
      <el-col :span="7">
        <div class="sub-title">已选择</div>
        <div class="scroll-box right">
          <span v-for="dept in dutyDepts" :key="dept.orgNum">
            <el-tag
              style="margin: 0 7.5px 5px 0"
              v-for="item in dept.executors"
              :key="item.orgNum"
              closable
              @close="deleteTarget(item, dept)"
            >
              {{ item.executorName }}({{ dept.deptName }})
            </el-tag>
          </span>
        </div>
      </el-col>
    </el-row>
  </i-dialog>
</template>
  
<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  executorName: "executorDialog",
  mixins: [dialogMixin, pageMixin],
  props: {
    selectedList: {
      type: Array,
    },
  },
  data() {
    return {
      orgList: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
        },
        {
          deptId: "16718",
          deptName: "中国银行金华分行",
          orgNum: "9170aa",
        },
        {
          deptId: "16238",
          deptName: "建设银行金华分行",
          orgNum: "9880aa",
        },
      ],
      tableData: [],
      selectedOrg: {},
      searchColumns: [
        {
          label: "网点名称",
          key: "executorName",
          type: "text",
          placeholder: "请输入网点名称",
        },
      ],
      columns: [{ label: "网点名称", prop: "executorName" }],
      dutyDepts: [],
    };
  },
  computed: {},
  watch: {
    selectedList: {
      handler(val) {
        this.dutyDepts = JSON.parse(JSON.stringify(val));
      },
      immediate: true,
      deep: true,
    },
  },
  methods: {
    changeSelect(item) {
      this.selectedOrg = item;
      this.tableData = [
        {
          executorName: "中国银行股份有限公司金华锦都支行",
          executorNum: "789099",
        },
        {
          executorName: "中国银行股份有限公司金华汇金支行",
          executorNum: "789096",
        },
        {
          executorName: "中国银行金华通汇支行",
          executorNum: "789095",
        },
        {
          executorName: "中国银行广苑支行",
          executorNum: "789094",
        },
        {
          executorName: "中国银行金华市分行营业部",
          executorNum: "789093",
        },
        {
          executorName: "中国银行东莱支行",
          executorNum: "789021",
        },
        {
          executorName: "中国银行金华江南支行",
          executorNum: "789090",
        },
        {
          executorName: "中国银行金华宾虹支行",
          executorNum: "789087",
        },
      ];
    },
    selectRow({ row, index }) {
      let idx = this.dutyDepts.findIndex(
        (item) => item.orgNum == this.selectedOrg.orgNum
      );
      if (
        idx >= 0 &&
        this.dutyDepts[idx].executors.some(
          (item) => item.executorNum == row.executorNum
        )
      ) {
        return { background: "rgba(149,200,253,0.5)" };
      }
    },
    updateSelectedList(obj) {
      let idx = this.dutyDepts.findIndex(
        (item) => item.orgNum == this.selectedOrg.orgNum
      );
      if (idx >= 0) {
        if (
          this.dutyDepts[idx].executors.some(
            (item) => item.executorNum == obj.executorNum
          )
        ) {
          this.dutyDepts[idx].executors = this.dutyDepts[idx].executors.filter(
            (item) => item.executorNum != obj.executorNum
          );
        } else {
          this.dutyDepts[idx].executors.push(obj);
        }
      } else {
        this.dutyDepts.push({ ...this.selectedOrg, executors: [obj] });
      }
    },
    hasOrgItem(obj) {
      return this.dutyDepts.some((item) => item.orgNum == obj.orgNum);
    },
    deleteTarget(item, dept) {
      this.dutyDepts.forEach((ele) => {
        if (ele.orgNum == dept.orgNum) {
          ele.executors = ele.executors.filter(
            (ele) => ele.executorNum != item.executorNum
          );
        }
      });
    },
    handleConfirm() {
      this.$emit("confirm", this.dutyDepts);
      this.handleClose();
    },
    handleClose() {
      this.tableData = [];
      this.dutyDepts = [];
      this.selectedOrg = {};
      this.showDialog = false;
    },
  },
};
</script>
  
<style scoped lang="scss">
.sub-title {
  margin-bottom: 10px;
}
.scroll-box {
  max-height: 400px;
  overflow-y: scroll;
}
.left {
  font-size: 16px;
  .org-item {
    margin: 5px 5px 5px 0;
    padding: 5px;
    border: 1px solid #7e9af6;
    border-radius: 5px;
    text-align: center;
    position: relative;
    .icon {
      position: absolute;
      right: 7.5px;
    }
  }
  .selected {
    color: #fff;
    background: #7e9af6;
  }
  .noselected {
    color: #7e9af6;
  }
}
</style>
  