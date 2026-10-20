<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 新增与删除按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        slot="btn"
        ref="btnBar"
        size="mini"
        class="i-btn-bar"
        @click="handleClick"
      ></i-button-bar>
      <!-- 数据列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight - 50"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        :selection="true"
        :showIndex="true"
        @selectionChange="selectionChange"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
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
      <!-- 新增弹窗 -->
      <add-chance
        v-model="showAdd"
        :status="status"
        :defaultValue="defaultValue"
      ></add-chance>
      <!-- 商机反馈弹窗 -->
      <feedback-chance
        v-model="showFeedback"
        :defaultValue="defaultValue"
      ></feedback-chance>
    </PageContent>
  </div>
</template>

<script>
import services from "../../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
import addChance from "../../components/addChance.vue";
import FeedbackChance from "../../components/feedbackChance.vue";
export default {
  mixins: [pageMixin],
  components: { addChance, FeedbackChance },
  data() {
    return {
      searchColumns: [
        {
          label: "客户名称",
          key: "name",
          type: "text",
        },
        {
          label: "信用代码",
          key: "identity",
          type: "text",
        },
        {
          label: "商机名称",
          key: "businessName",
          type: "text",
        },
        {
          label: "商机状态",
          key: "businessStatus",
          type: "select",
          placeholder: "请选择商机状态",
          options: [
            {
              value: 0,
              label: "营销中",
            },
            {
              value: 1,
              label: "已完成",
            },
            {
              value: 2,
              label: "已结束",
            },
          ],
        },
        {
          label: "拓展方向",
          key: "businessExtend",
          type: "select",
          placeholder: "请选择拓展方向",
          options: [
            {
              value: 0,
              label: "潜客扩展",
            },
            {
              value: 1,
              label: "信贷提升",
            },
            {
              value: 2,
              label: "存量活客",
            },
          ],
        },
        {
          label: "开始时间",
          type: "datetimerange",
          value: [],
          key: "startTime",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "至",
          format: "yyyy-MM-dd",
          valueFormat: "yyyy-MM-dd",
          unlinkPanels: true,
        },
        {
          label: "结束时间",
          type: "datetimerange",
          value: [],
          key: "endTime",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "至",
          format: "yyyy-MM-dd",
          valueFormat: "yyyy-MM-dd",
          unlinkPanels: true,
        },
      ],
      btnLists: [
        {
          icon: "el-icon-plus",
          cname: "新增",
          ename: "add",
          type: "primary",
        },
        {
          icon: "el-icon-delete",
          cname: "删除",
          ename: "delete",
          type: "info",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      columns: [
        { label: "客户名称", prop: "name" },
        { label: "统一社会信用代码", prop: "identity" },
        { label: "商机名称", prop: "businessName" },
        { label: "业务拓展方向", prop: "businessExtend" },
        { label: "商机状态", prop: "businessStatus" },
        { label: "商机来源", prop: "businessResource" },
        { label: "商机发布人", prop: "createBy" },
        { label: "商机发布日期", prop: "startTime" },
        { label: "商机结束日期", prop: "endTime" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "商机反馈",
          ename: "handleFeedback",
          type: "text",
        },
      ],
      tableData: [],
      status: "add",
      showAdd: false,
      showFeedback: false,
      defaultValue: {},
    };
  },
  methods: {
    // 点击新增或者删除按钮
    handleClick(click) {
      // 新建商机
      if (click === "add") {
        this.showAdd = true;
        this.defaultValue = {};
      }
      // 删除多个商机
      if (click === "delete") {
        this.makeSure("确认删除多个商机吗?", () => {
          this.delChance(this.multiSelected);
        });
      }
    },
    // 删除商机
    delChance(row) {
      // console.log("即将删除的商机", row);
      let ids = row
        .map((item) => {
          return item.id;
        })
        .toString();
      console.log(ids);
      services
        .opportunityDelByIds({ ids })
        .then((res) => {
          // console.log(res);
          if (res.code == "200") {
            this.$message({
              type: "success",
              message: "已删除",
            });
          }
          this.fetchList();
        })
        .catch((err) => {});
    },
    // 点击表格右侧商机反馈
    handleFeedback() {
      this.showFeedback = true;
      this.defaultValue = {};
    },
    // 获取列表数据
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        let param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        param.startTimeSearchStart = formInfo.startTime[0];
        param.startTimeSearchEnd = formInfo.startTime[1];
        param.endTimeSearchStart = formInfo.endTime[0];
        param.endTimeSearchEnd = formInfo.endTime[1];
        delete param["endTime"];
        delete param["startTime"];
        // console.log(param);
        services
          .opportunityList(param)
          .then((res) => {
            // console.log("res", res);
            res.rows.forEach((item) => {
              // 业务扩展方向 0-潜客扩展 1-信贷提升 2-存量活客
              item.businessExtend = this.moreif(
                item.businessExtend,
                "潜客扩展",
                "信贷提升",
                "存量活客"
              );
              // 商机状态 0-营销中 1-已完成 2-已结束
              item.businessStatus = this.moreif(
                item.businessStatus,
                "营销中",
                "已完成",
                "已结束"
              );
              // 商机来源 0-手工创建 1-信贷系统 2-外部接入
              item.businessResource = this.moreif(
                item.businessResource,
                "手工创建",
                "信贷系统",
                "外部接入"
              );
            });
            this.tableData = res.rows;
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
          });
      });
    },
  },
  mounted() {
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped>
</style>