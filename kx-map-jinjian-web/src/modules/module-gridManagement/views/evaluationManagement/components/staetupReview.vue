<template>
  <PageContent>
    <div class="topBtn">
      <el-button class="btn" @click="cancel">取消</el-button>
    </div>
    <div class="top">
      <h2>基础设置</h2>
      <div style="width: 1100px; margin: 10px 0 0 20px">
        <i-form
          ref="filterBar"
          :columns="baseColumns"
          label-position="right"
          label-width="150px"
          :gridColumns="2"
        >
        </i-form>
      </div>
    </div>
    <div class="bottom">
      <el-radio-group
        v-model="active"
        style="margin-bottom: 30px; width: 500px"
      >
        <el-radio-button label="1">评价材料收集内容</el-radio-button>
        <el-radio-button label="2">暗访现场评价内容</el-radio-button>
      </el-radio-group>
      <div class="title">
        <el-button
          type="primary"
          size="small"
          @click="addHandle"
          v-if="active == '1'"
          >添加材料要求</el-button
        >
        <el-button
          type="primary"
          size="small"
          @click="addEventList"
          v-if="active == '2'"
          >新增现场评价内容</el-button
        >
      </div>
      <i-table
        v-if="active == '1'"
        ref="table"
        :data="tableData"
        stripe
        :allColumns="columns"
        :showPagination="false"
        :defaultExpandAll="true"
        :maxHeight="maxHeight"
        class="table"
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
      <i-table
        v-if="active == '2'"
        ref="table"
        :data="twoTabledata"
        stripe
        :allColumns="twoColumns"
        :showPagination="false"
        :defaultExpandAll="true"
        :maxHeight="maxHeight"
        align="center"
      >
        <template #classification="{ row }">
          <el-input
            v-model="row.classification"
            placeholder="请输入内容"
          ></el-input>
        </template>
        <template #score="{ row }">
          <el-input v-model="row.score" placeholder="请输入总分"></el-input>
        </template>
        <template #evaluation="{ row }">
          <el-input
            v-model="row.evaluation"
            placeholder="请输入评价项目"
          ></el-input>
        </template>
        <template #standards="{ row }">
          <el-input
            v-model="row.standards"
            placeholder="请输入得分标准"
          ></el-input>
        </template>
        <template #association="{ row }">
          <el-select
            v-model="row.association"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in associationOpt"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
        </template>
        <template #assContent="{ row }">
          <el-select
            v-model="row.assContent"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="item in assContentOpt"
              :key="item.value"
              :value="item.value"
              :label="item.label"
            ></el-option>
          </el-select>
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
    </div>
    <div class="bottom-btnList" v-if="staetupInfo.status == 'add'">
      <el-button class="btn" @click="cancel">取消</el-button>
      <el-button class="btn" type="primary" @click="confirm">确认</el-button>
    </div>
    <addProject
      v-model="showDialog"
      :status="status"
      :editInfo="editInfo"
      @updateData="updateData"
    ></addProject>
  </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import addProject from "./addProject.vue";
import { mapGetters, mapMutations } from "vuex";
import itemEchartVue from '../../../../../components/MerchantDetail/components/itemEchart.vue';
export default {
  mixins: [pageMixin],
  components: {
    addProject,
  },
  data() {
    return {
      baseColumns: [
        {
          label: "评价年度",
          key: "evaluationYear",
          type: "select",
          labelWidth: "80px",
          options: [
            { label: "2025年", value: "2025" },
            { label: "2024年", value: "2024" },
            { label: "2023年", value: "2023" },
            { label: "2022年", value: "2022" },
          ],
          rules: [
            { required: true, trigger: "change", message: "评价年度不能为空" },
          ],
        },
        {
          label: "名称",
          key: "name",
          type: "text",
          labelWidth: "80px",
          rules: [{ required: true, trigger: "blur", message: "名称不能为空" }],
        },
        {
          label: "评价材料收集时间",
          key: "time",
          type: "daterange",
          rules: [
            {
              required: true,
              trigger: "change",
              message: "评价材料收集时间不能为空",
            },
          ],
        },
        {
          label: "备注",
          key: "remark",
          type: "textarea",
          labelWidth: "80px",
          value: [],
          span: 24,
          rules: [{ required: true, trigger: "blur", message: "备注不能为空" }],
        },
      ],
      columns: [
        { label: "评价材料类型", prop: "classification" },
        { label: "上报材料说明", prop: "remark", width: "700px" },
        { label: "操作", slot: true, width: "100px" },
      ],
      twoColumns: [
        { label: "评价内容", prop: "classification", slot: true },
        { label: "总分", prop: "score", slot: true },
        { label: "评价项目", prop: "evaluation", slot: true },
        { label: "得分标准", prop: "standards", slot: true },
        // { label: "关联模块", prop: "association", slot: true },
        // { label: "关联内容", prop: "assContent", slot: true },
        { label: "操作", slot: true, width: "80px" },
      ],
      tableData: [],
      twoTabledata: [],
      tableBtn: [
        {
          cname: "编辑",
          ename: "editHandle",
          isShow: ({ leave }) => this.active == 1,
        },
        {
          cname: "删除",
          ename: "deleteHandle",
        },
      ],
      maxHeight: "350px",
      showDialog: false,
      status: "add",
      editInfo: {},
      active: 1,
      associationOpt: [
        { label: "指标", value: "1" },
        { label: "台账", value: "2" },
        { label: "指标、台账", value: "3" },
      ],
      assContentOpt: [
        { label: "平台社区/行政村网格数", value: "1" },
        { label: "管辖银行机构数、分配网格银行机构数", value: "2" },
        {
          label: "银行网点修改台账、网格长修改台账、网格员修改台账",
          value: "3",
        },
        { label: "工作报告台账", value: "4" },
      ],
    };
  },
  computed: {
    ...mapGetters("accountBook", ["evaluationSiteList",'evaluationList','staetupInfo','releaseEvaList']),
  },
  // watch: {
  //   evaluationSiteList: {
  //     handler(val) {
  //       if (this.staetupInfo.status == 'add') {
  //         this.twoTabledata = []
  //       } else {
  //         this.twoTabledata = JSON.parse(JSON.stringify(val));
  //         this.twoTabledata = this.twoTabledata.filter(item => item.parentId == this.staetupInfo.id)
  //       }
  //     },
  //     deep: true,
  //     immediate: true,
  //   },
  //   evaluationList: {
  //     handler(val) {
  //       if (this.staetupInfo.status == 'add') {
  //         this.tableData = []
  //       } else {
  //         this.tableData = JSON.parse(JSON.stringify(val));
  //         this.tableData = this.tableData.filter(item => item.parentId == this.staetupInfo.id)
  //       }
  //     },
  //     deep: true,
  //     immediate: true,
  //   }
  // },
  mounted() {
    console.log(this.staetupInfo.status )
    if (this.staetupInfo.status == 'add') {
      this.twoTabledata = []
      this.tableData = []
    } else if (this.staetupInfo.status == 'view') {
      console.log(this.evaluationSiteList,'this.evaluationSiteList');
      this.twoTabledata = JSON.parse(JSON.stringify(this.evaluationSiteList));
      this.twoTabledata = this.twoTabledata.filter(item => item.parentId == this.staetupInfo.id)

      this.tableData = JSON.parse(JSON.stringify(this.evaluationList));
      this.tableData = this.tableData.filter(item => item.parentId == this.staetupInfo.id)
      this.staetupInfo.time = [
        this.staetupInfo.evaluationStart,
        this.staetupInfo.evaluationEnd,
      ]
      this.$refs.filterBar.setForm(this.staetupInfo)
    }
    // if (this.staetupInfo.status == 'view') {
    //   this.active = 2;
    // }
  },
  methods: {
    ...mapMutations('accountBook', ['updateEvaluationSiteList','updateReleaseEvaList','updateEvaluationList']),
    addEventList() {
      this.twoTabledata.push({
        id:new Date().getTime(),
        classification: "",
        score: "",
        evaluation: "",
        standards:"",
        association: "",
        assContent: "",
      })
    },
    addHandle() {
      this.editInfo = {}
      this.status = "add";
      this.showDialog = true;
    },
    editHandle(row) {
      this.editInfo = row;
      this.status = "edit";
      this.showDialog = true;
    },
    deleteHandle(row) {
      if (this.active == 1) {
        this.makeSure("确认删除吗？", () => {
          this.deleteItem(row.id);
        });
      } else {
        this.makeSure("确认删除吗？", () => {
          this.removeItem(row.id);
        });
      }
    },
    updateData(val) {
      const obj = this.tableData.find((item) => item.id == val.id);
      obj && Object.assign(obj, val);
    },
    deleteItem(id) {
      this.tableData = this.tableData.filter((item) => item.id != id);
    },
    removeItem(id) {
      this.twoTabledata = this.twoTabledata.filter((item) => item.id != id);
    },
    cancel() {
      this.$router.go(-1);
    },
    confirm() {
      this.$refs.filterBar.getForm((formInline) => {
        const param = {
          ...formInline,
          id: new Date().getTime(), // 模拟id
          evaluationStart: formInline.time[0],
          evaluationEnd: formInline.time[1],
          superiorProgress: "5/19",
          progress: "1/3",
          startupTime:this.parseTime(new Date(),'{y}-{m}-{d}'),
        }
        this.twoTabledata.forEach(item => {
          item.parentId = param.id
        })
        this.tableData.forEach(item => {
          item.parentId = param.id
        })
        let arr = this.releaseEvaList
        this.updateReleaseEvaList([{...param},...arr])
        this.updateEvaluationSiteList([...this.twoTabledata,...this.evaluationSiteList])
        
        this.updateEvaluationList([...this.tableData,...this.evaluationList])
        this.$router.go(-1);
        this.$modal.msgSuccess("操作成功");
      })
    },
  },
};
</script>

<style scoped lang="scss">
.top {
  // width: 100%;
  // height: 26vh;
  margin-bottom: 8px;
  border-bottom: 1px solid #ccc;
}
.bottom {
  // width: 100%;
  // height: 65vh;
  margin-top: 10px;
  .title {
    display: flex;
    align-items: center;
    .el-button {
      margin-left: 10px;
    }
  }
}
h2 {
  font-size: 20px;
}
.topBtn {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.bottom-btnList {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  margin: 20px 0;
  .btn {
    width: 100px;
  }
}
.table{
  height: auto !important;
}
</style>