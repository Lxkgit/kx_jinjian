<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar :columns="searchColumns" ref="filterBar" slot="filter" :gridColumns="4"
                    label-width="80px">
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 新增与删除按钮 -->
      <i-button-bar :btn-lists="btnLists" slot="btn" ref="btnBar" size="mini" class="i-btn-bar"
                    @click="handleClick"></i-button-bar>
      <!-- 数据列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" :max-height="maxHeight"
               align="center" stripe :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize" :selection="false"
               @selectionChange="selectionChange" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #qrLink="scope">
          <el-button type="text" @click="watchLink(scope.row)">查看</el-button>
        </template>
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <addActivity v-model="showDialog" :status="status" :defaultValue="defaultValue">
    </addActivity>
    <!-- <qr-link-dialog v-model="showQRLink" :defaultValue="defaultValue"></qr-link-dialog> -->
    <qr-link-dialog v-model="showQRLink" :defaultValue="{link:'http://www.kangxukeji.com'}"></qr-link-dialog>
  </div>
</template>

<script>
import services from "../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
import addActivity from "../components/addActivity.vue";
import qrLinkDialog from "../components/qrLinkDialog.vue";
const activityType = [
  {
    label: "未开始",
    value: "1",
  },
  {
    label: "进行中",
    value: "2",
  },
  {
    label: "已结束",
    value: "3",
  },
];
export default {
  name: "activityList",
  mixins: [pageMixin],
  components: { addActivity, qrLinkDialog },
  data() {
    return {
      searchColumns: [
        {
          label: "活动名称",
          key: "actName",
          type: "text",
          placeholder: "请输入活动名称",
        },
        {
          label: "活动状态",
          key: "actStatus",
          type: "select",
          placeholder: "请选择客户类型",
          options: () => activityType,
        },
        {
          label: "创建人",
          key: "createBy",
          type: "text",
          placeholder: "请输入创建人",
        },
        {
          label: "创建机构",
          key: "createDeptName",
          type: "text",
          placeholder: "请输入创建机构",
        },
        {
          label: "创建开始日期",
          type: "date",
          key: "createTimeStart",
          placeholder: "请选择创建开始时间",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "创建结束日期",
          type: "date",
          key: "createTimeEnd",
          placeholder: "请选择创建结束时间",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "活动开始日期",
          type: "date",
          key: "actTimeStart",
          placeholder: "请选择活动开始时间",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "活动结束日期",
          type: "date",
          key: "actTimeEnd",
          placeholder: "请选择活动结束时间",
          valueFormat: "yyyy-MM-dd",
        },
        // {
        //   label: "活动日期",
        //   type: "daterange",
        //   value: [],
        //   key: "actTime",
        //   startPlaceholder: "开始日期",
        //   endPlaceholder: "结束日期",
        //   rangeSeparator: "-",
        //   format: "yyyy-MM-dd",
        //   valueFormat: "yyyy-MM-dd",
        //   unlinkPanels: true,
        // },
      ],
      searchBtn: [
        {
          icon: "el-icon-search",
          cname: "搜索",
          ename: "search",
          type: "primary",
        },
        {
          icon: "el-icon-refresh",
          cname: "重置",
          ename: "reset",
          // type: "info",
        },
      ],
      btnLists: [
        {
          icon: "el-icon-plus",
          cname: "活动创建",
          ename: "add",
          type: "primary",
          permi: "taskManagement:taskType:add",
        },
        // {
        //   icon: "el-icon-delete",
        //   cname: "活动删除",
        //   ename: "delete",
        //   type: "info",
        //   permi: "taskManagement:taskType:delete",
        //   disabled: () => this.multiSelected.length === 0,
        // },
      ],
      columns: [
        { label: "活动名称", prop: "actName" },
        { label: "创建人", prop: "createBy" },
        { label: "创建机构", prop: "createDeptName" },
        {
          label: "创建时间", prop: "createTime",
          formatter: ({ createTime }) => {
            return createTime.substr(0, 10);
          },
        },
        {
          label: "活动状态",
          prop: "actStatus",
          formatter: ({ actStatus }) => {
            return activityType[Number(actStatus) - 1].label;
          },
        },
        {
          label: "活动日期",
          prop: "actTimeList",
          width: 200,
          formatter: ({ actTimeList }) => {
            return actTimeList[0] + "—" + actTimeList[1];
          },
        },
        { label: "二维码/链接", prop: "qrLink", slot: true },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "活动成果",
          ename: "handleResult",
          type: "text",
          permi: "taskManagement:taskType:edit",
        },
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
          // isShow: ({ actStatus }) => actStatus == "1",
        },
        // {
        //   cname: "删除",
        //   ename: "handleDel",
        //   type: "text",
        //   isShow: ({ actStatus }) => actStatus == "1",
        // },
      ],
      tableData: [
        // {
        //   id: 0,
        //   actName: "活动333",
        //   actTimeList: ["2024-05-01", "2024-05-06"],
        //   actduration: "6天",
        //   createTime: "2024-05-01",
        //   createBy: "用户333",
        //   createManagerNum: "333",
        //   createDeptName: "总行",
        //   createOrgNum: "333",
        //   actdesc: "其他描述",
        //   actParticipateNum: "22人",
        //   ortherExpectDesc: "预期结果",
        //   actStatus: "3",
        // },
        // {
        //   id: 1,
        //   actName: "活动222",
        //   actTimeList: ["2024-06-01", "2024-06-20"],
        //   actduration: "20天",
        //   createTime: "2024-06-01",
        //   createBy: "用户222",
        //   createManagerNum: "222",
        //   createDeptName: "成都分行",
        //   createOrgNum: "222",
        //   actdesc: "123456",
        //   actParticipateNum: "33人",
        //   ortherExpectDesc: "预期预期预期",
        //   actStatus: "2",
        // },
        // {
        //   id: 2,
        //   actName: "活动111",
        //   actTimeList: ["2024-07-01", "2024-07-10"],
        //   actduration: "10天",
        //   createTime: "2024-07-01",
        //   createBy: "用户111",
        //   createManagerNum: "111",
        //   createDeptName: "重庆分行",
        //   createOrgNum: "111",
        //   actdesc: "000000000",
        //   actParticipateNum: "100人",
        //   ortherExpectDesc: "我是预期成果",
        //   actStatus: "1",
        // },
      ],
      defaultValue: {},
      status: "add",
      showDialog: false,
      showQRLink: false,
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    // 发送删除请求
    delData(row) {
      let ids = row
        .map((item) => {
          return item.id;
        })
        .toString();
      console.log(ids);
      // services.deleteTaskType({ ids }).then((res) => {
      //   if (res.code == "200") {
      //     this.$message({
      //       type: "success",
      //       message: "已删除",
      //     });
      //   }
      //   this.fetchList();
      // });
    },
    // 编辑
    handleEdit(row) {
      console.log("row", row);
      // this.defaultValue = row
      this.defaultValue = JSON.parse(JSON.stringify(row));
      this.status = "edit";
      this.showDialog = true;
    },
    // 表格右侧点击删除
    handleDel(row) {
      this.makeSure("确认删除该类型?", () => {
        this.delData([row]);
      });
    },
    // 点击新增或者删除按钮
    handleClick(click) {
      // 新建活动
      if (click === "add") {
        this.status = "add";
        this.showDialog = true;
        this.defaultValue = {};
      }
      // 删除多个活动
      if (click === "delete") {
        this.makeSure("确认删除多个任务吗?", () => {
          this.delData(this.multiSelected);
        });
      }
    },
    // 获取列表数据  /  或进行搜索
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        console.log("param", param);
        // this.loading = false;
        services
          .searchAct(param)
          .then((res) => {
            this.tableData = res.rows;
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            if (err) {
              this.tableData = [];
              this.total = 0;
            }
            this.loading = false;
          });
      });
    },
    watchLink(row) {
      console.log("watchLinkRow", row);
      this.showQRLink = true;
    },
    // 活动成果
    handleResult(row) {
      const { id } = row
      this.$router.push({ name: 'ActivityDetail', params: { id } })
      // this.defaultValue =row
      // this.defaultValue =JSON.parse(JSON.stringify(row));
      // this.status = "result";
      // this.showDialog = true;
    },
  },
};
</script>

<style lang="scss" scoped></style>
