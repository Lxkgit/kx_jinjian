<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        class="filterBar"
        :gridColumns="3"
        v-model="filterForm"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">查询</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <div class="i-table">
      <!-- 按钮 -->
      <i-button-bar
        style="margin-bottom: 0"
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
        class="button-bar"
      >
      </i-button-bar>
      <i-table
        ref="table"
        :loading="loading"
        :data="filteredTableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        border
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      >
        <!-- 复核状态插槽 -->
        <template #reviewStatus="scope">
          <span
            :class="
              scope.row.reviewStatus === '通过' ? 'status-green' : 'status-wait'
            "
          >
            {{ scope.row.reviewStatus }}
          </span>
        </template>
        <!-- 操作插槽 -->
        <template #action="scope">
          <i-table-btns
            :count="scope.row.reviewStatus === '通过' ? 1 : 3"
            :btn-lists="
              scope.row.reviewStatus === '通过' ? archivedTableBtn : tableBtn
            "
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </div>

    <addShopDialog v-model="showAddShop"></addShopDialog>
    <reviewDialog
      :visible.sync="showReviewDialog"
      :defaultValue="reviewRow"
      @submit="handleReviewSubmit"
    />
    <!-- 模板中引入批量导入弹窗 -->
    <batchImportDialog
      :visible.sync="showBatchDialog"
      @submit="handleBatchSubmit"
    />
  </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import addShopDialog from "../components/addShopDialog.vue";
import reviewDialog from "../components/reviewDialog.vue";
import batchImportDialog from "../components/batchImportDialog.vue";
import dayjs from "dayjs";

export default {
  mixins: [pageMixin],
  components: {
    addShopDialog,
    reviewDialog,
    batchImportDialog,
  },
  data() {
    return {
      showBatchDialog: false,
      reviewRow: {},
      showReviewDialog: false,
      showAddShop: false,
      loading: false,
      total: 0,
      // 分页参数
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      // 筛选表单数据
      filterForm: {
        checkDate: [], // 日期范围 [开始日期, 结束日期]
        checkArea: "", // 排查区域
        subjectType: "", // 主体类型
      },
      // 模拟50条数据（批量生成）
      originalTableData: [],
      maxHeight: window.innerHeight - 350,
      // 表格列配置
      columns: [
        { label: "排查编号", prop: "checkNo" },
        { label: "排查日期", prop: "checkDate" },
        { label: "牵头部门", prop: "leadDept" },
        { label: "排查牵头部门人员", prop: "leadDeptPerson" },
        { label: "排查区域", prop: "checkArea" },
        { label: "主体类型", prop: "subjectType" },
        { label: "主体数量", prop: "subjectCount" },
        { label: "填报人", prop: "reporter" },
        { label: "填报时间", prop: "reportTime" },
        { label: "复核状态", prop: "reviewStatus", slot: true },
        { label: "操作", slot: true },
      ],
      // 筛选栏配置
      searchColumns: [
        {
          label: "排查日期",
          key: "checkDate",
          type: "daterange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "排查区域",
          key: "checkArea",
          type: "text",
          placeholder: "请输入排查区域",
        },
        {
          label: "主体类型",
          key: "subjectType",
          type: "select",
          options: [
            { label: "商户", value: "商户" },
            { label: "小微企业", value: "小微企业" },
            { label: "办公室", value: "办公室" },
          ],
          placeholder: "请选择主体类型",
        },
      ],
      // 顶部按钮
      btnLists: [
        { cname: "新增", ename: "addShop", type: "primary" },
        { cname: "批量导入", ename: "batchImport", type: "primary" },
      ],
      // 表格操作按钮（待复核）
      tableBtn: [
        { cname: "复核", ename: "reviewHandle", type: "primary" },
        { cname: "编辑", ename: "editHandle", type: "primary" },
        { cname: "删除", ename: "deleteHandle", danger: "danger" },
      ],
      // 表格操作按钮（已归档）
      archivedTableBtn: [
        { cname: "已归档", ename: "archived", type: "text", disabled: true },
      ],
      // 模拟数据配置
      mockConfig: {
        depts: [
          "国家金融监督管理总局上城监管支局",
          "国家金融监督管理总局拱墅监管支局",
          "国家金融监督管理总局西湖监管支局",
          "国家金融监督管理总局滨江监管支局",
          "国家金融监督管理总局萧山监管支局",
          "国家金融监督管理总局余杭监管支局",
          "国家金融监督管理总局临平监管支局",
          "国家金融监督管理总局钱塘监管支局",
        ],
        persons: [
          "陈昊宇",
          "林诗瑶",
          "王梓轩",
          "赵一诺",
          "沈亦凡",
          "冯可欣",
          "李沐雪",
          "张语桐",
          "周瑾辰",
          "苏雨欣",
          "陆泽宇",
          "杨若曦",
          "江皓轩",
          "许悠然",
          "高梓涵",
          "马思远",
        ],
        areas: [
          "浙江省杭州市上城区彭埠街道三花国际3幢2单元101室",
          "浙江省杭州市拱墅区祥符街道中大银泰城2号楼1502室",
          "浙江省杭州市西湖区文三路478号华星时代广场B座",
          "浙江省杭州市滨江区江南大道100号恒生大厦",
          "浙江省杭州市萧山区市心北路万象汇1幢1001室",
          "浙江省杭州市余杭区未来科技城海创园5幢",
          "浙江省杭州市临平区南苑街道银泰城9幢",
          "浙江省杭州市钱塘区下沙街道金沙湖壹号2幢",
        ],
        subjectTypes: ["商户", "小微企业", "办公室"],
        reporters: [
          "沈亦凡",
          "冯可欣",
          "李沐雪",
          "张语桐",
          "周瑾辰",
          "苏雨欣",
          "陆泽宇",
          "杨若曦",
        ],
        reviewStatus: ["通过", "待复核"],
      },
    };
  },
  // 计算属性：高效筛选+分页
  computed: {
    filteredTableData() {
      // 浅拷贝避免修改原数据
      let data = [...this.originalTableData];

      // 1. 排查日期筛选（核心优化：提前判断，减少计算）
      if (this.filterForm.checkDate?.length === 2) {
        const [start, end] = this.filterForm.checkDate;
        const startDate = dayjs(start);
        const endDate = dayjs(end);
        data = data.filter((item) => {
          const itemDt = dayjs(item.checkDate);
          return itemDt.isBetween(startDate, endDate, "day", "[]");
        });
      }

      // 2. 排查区域筛选（模糊匹配，优化：提前trim）
      if (this.filterForm.checkArea) {
        const keyword = this.filterForm.checkArea.trim();
        data = data.filter((item) => item.checkArea.includes(keyword));
      }

      // 3. 主体类型筛选（精准匹配）
      if (this.filterForm.subjectType) {
        data = data.filter(
          (item) => item.subjectType === this.filterForm.subjectType
        );
      }

      // 更新总条数
      this.total = data.length;

      // 4. 分页处理（优化：边界判断）
      const startIdx = (this.formInline.pageNum - 1) * this.formInline.pageSize;
      const endIdx = startIdx + this.formInline.pageSize;
      return data.slice(startIdx, endIdx);
    },
  },
  watch: {
    // 筛选条件变化时重置页码
    filterForm: {
      deep: true,
      handler() {
        this.formInline.pageNum = 1;
      },
    },
  },
  mounted() {
    // 初始化生成50条模拟数据
    this.generateMockData(50);
    this.getTableData();
  },
  methods: {
    /**
     * 批量生成模拟数据
     * @param {Number} count 生成数量
     */
    generateMockData(count) {
      const { depts, persons, areas, subjectTypes, reporters, reviewStatus } =
        this.mockConfig;
      const baseDate = dayjs("2026-02-01");
      const mockData = [];

      for (let i = 0; i < count; i++) {
        // 随机日期（2026-02-01 至 2026-02-10）
        const randomDay = Math.floor(Math.random() * 10);
        const checkDate = baseDate.add(randomDay, "day").format("YYYY-MM-DD");

        // 随机时间（08:00 至 18:00）
        const randomHour = 8 + Math.floor(Math.random() * 10);
        const randomMinute = Math.floor(Math.random() * 60);
        const reportTime = `${checkDate} ${randomHour
          .toString()
          .padStart(2, "0")}:${randomMinute.toString().padStart(2, "0")}:00`;

        // 随机生成数据
        mockData.push({
          checkNo: `PC${checkDate.replace(/-/g, "")}${i
            .toString()
            .padStart(3, "0")}`,
          checkDate,
          leadDept: depts[Math.floor(Math.random() * depts.length)],
          leadDeptPerson: persons[Math.floor(Math.random() * persons.length)],
          checkArea: areas[Math.floor(Math.random() * areas.length)],
          subjectType:
            subjectTypes[Math.floor(Math.random() * subjectTypes.length)],
          subjectCount: Math.floor(Math.random() * 10) + 1, // 1-10
          reporter: reporters[Math.floor(Math.random() * reporters.length)],
          reportTime,
          reviewStatus:
            reviewStatus[Math.floor(Math.random() * reviewStatus.length)],
          operation: "",
        });
      }

      this.originalTableData = mockData;
    },

    /**
     * 初始化加载数据（模拟接口请求）
     */
    getTableData() {
      this.loading = true;
      setTimeout(() => {
        this.resetHandle(true);
        this.loading = false;
      }, 500);
    },

    /**
     * 搜索按钮点击事件
     */
    searchHandle() {
      this.formInline.pageNum = 1;
    },

    /**
     * 重置按钮点击事件
     * @param {Boolean} isInit 是否是初始化调用
     */
    resetHandle(isInit = false) {
      this.filterForm = {
        checkDate: [],
        checkArea: "",
        subjectType: "",
      };
      this.formInline = {
        pageNum: 1,
        pageSize: 10,
      };
      if (!isInit && this.$refs.filterBar) {
        this.$refs.filterBar.resetFields();
      }
    },

    /**
     * 页码切换
     * @param {Number} pageNum 新页码
     */
    currentChange(pageNum) {
      this.formInline.pageNum = pageNum;
    },

    /**
     * 每页条数切换
     * @param {Number} pageSize 新每页条数
     */
    sizeChange(pageSize) {
      this.formInline.pageSize = pageSize;
      this.formInline.pageNum = 1;
    },

    /**
     * 按钮点击统一处理
     * @param {String} type 按钮类型
     * @param {Object} row 行数据
     */
    clickHandle(type, row) {
      switch (type) {
        case "addShop":
          this.showAddShop = true;
          break;
        case "batchImport":
          this.batchImportHandle();
          break;
        case "reviewHandle":
          this.reviewHandle(row);
          break;
        case "editHandle":
          this.editHandle(row);
          break;
        case "deleteHandle":
          this.deleteHandle(row);
          break;
        default:
          break;
      }
    },

    // 批量导入
    batchImportHandle() {
      this.showBatchDialog = true;
    },
    handleBatchSubmit(importData) {
      this.originalTableData = [...this.originalTableData, ...importData];
    },
    // 复核
    reviewHandle(row) {
      this.showReviewDialog = true;
      this.reviewRow = { ...row };
    },
    handleReviewSubmit(formData) {
      const targetIndex = this.originalTableData.findIndex(
        (item) => item.checkNo === this.reviewRow.checkNo
      );
      if (targetIndex !== -1) {
        this.originalTableData[targetIndex].reviewStatus =
          formData.reviewResult;
        this.originalTableData[targetIndex].reviewOpinion =
          formData.reviewOpinion;
        this.showReviewDialog = false;
        this.$message.success("复核成功！");
      }
    },
    // 编辑
    editHandle(row) {
      this.showAddShop = true;
      this.$message.info(`编辑排查编号：${row.checkNo}`);
    },

    // 删除
    deleteHandle(row) {
      this.$confirm("确定删除该条记录吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.originalTableData = this.originalTableData.filter(
            (item) => item.checkNo !== row.checkNo
          );
          this.$message.success("删除成功！");
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.filterBar-box {
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  padding-bottom: 0;
  display: flex;
  justify-content: space-between;

  .filterBar {
    width: 65%;
  }
}

.table-title {
  padding: 16px;
  height: 68px;
}

.i-table {
  background: #fff;
  padding: 0 16px;
  padding-bottom: 10px;
}

.status-green {
  display: inline-block;
  padding: 2px 8px;
  background: rgba(55, 195, 140, 0.2);
  color: #37c38c;
  border-radius: 4px;
}

.status-wait {
  display: inline-block;
  padding: 2px 8px;
  background: rgba(255, 170, 0, 0.2);
  color: #ffaa00;
  border-radius: 4px;
}
.button-bar {
  padding: 20px 0;
}
</style>
