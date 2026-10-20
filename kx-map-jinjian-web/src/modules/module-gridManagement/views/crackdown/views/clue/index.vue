<template>
  <PageContent>
    <div class="risk-clue-page">
      <div class="search-card">
        <el-form :inline="true" :model="query" size="small" class="search-form">
          <el-form-item><el-input v-model.trim="query.keyword" clearable placeholder="线索编号/标题/涉险主体" style="width: 210px" @keyup.enter.native="search" /></el-form-item>
          <el-form-item><el-date-picker v-model="reportRange" type="daterange" value-format="yyyy-MM-dd" range-separator="至" start-placeholder="报送开始日期" end-placeholder="报送结束日期" style="width: 230px" /></el-form-item>
          <el-form-item>
            <el-select v-model="query.clueType" clearable placeholder="线索类型" style="width: 128px"><el-option v-for="item in clueTypeOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="query.reviewStatus" clearable placeholder="复核状态" style="width: 128px"><el-option v-for="item in reviewOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="query.transferStatus" clearable placeholder="移送状态" style="width: 128px"><el-option v-for="item in transferStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="query.feedbackStatus" clearable placeholder="反馈状态" style="width: 128px"><el-option v-for="item in feedbackStatusOptions" :key="item.value" :label="item.label" :value="item.value" /></el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="search">查询</el-button>
            <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="table-card">
        <div class="table-toolbar">
          <el-button v-hasPermi="['system:riskClue:add']" type="primary" icon="el-icon-plus" @click="openForm('create')">线索上报</el-button>
        </div>
        <el-table v-loading="loading" :data="rows" stripe height="100%" class="clue-table warning-table">
          <el-table-column label="线索编号" prop="clueNo" width="132" fixed="left"><template slot-scope="scope"><el-link type="primary" @click="openDetail(scope.row)">{{ scope.row.clueNo }}</el-link></template></el-table-column>
          <el-table-column label="线索标题" prop="clueTitle" min-width="205" show-overflow-tooltip />
          <el-table-column label="线索类型" width="104" align="center"><template slot-scope="scope"><el-tag size="mini" :class="`clue-type-${scope.row.clueType}`">{{ optionLabel(clueTypeOptions, scope.row.clueType) }}</el-tag></template></el-table-column>
          <el-table-column label="风险等级" width="88" align="center"><template slot-scope="scope"><el-tag size="mini" :type="riskTagType(scope.row.riskLevel)">{{ optionLabel(riskLevelOptions, scope.row.riskLevel) }}</el-tag></template></el-table-column>
          <el-table-column label="复核状态" width="100" align="center"><template slot-scope="scope"><el-tag size="mini" :type="reviewTagType(scope.row.reviewStatus)">{{ optionLabel(reviewOptions, scope.row.reviewStatus) }}</el-tag></template></el-table-column>
          <el-table-column label="移送状态" width="94" align="center"><template slot-scope="scope"><el-tag size="mini" :type="scope.row.transferStatus === 1 ? 'primary' : 'info'">{{ optionLabel(transferStatusOptions, scope.row.transferStatus) }}</el-tag></template></el-table-column>
          <el-table-column label="反馈状态" width="94" align="center"><template slot-scope="scope"><el-tag size="mini" :type="scope.row.feedbackStatus === 1 ? 'success' : 'info'">{{ optionLabel(feedbackStatusOptions, scope.row.feedbackStatus) }}</el-tag></template></el-table-column>
          <el-table-column label="涉险主体" prop="riskSubject" min-width="180" show-overflow-tooltip />
          <el-table-column label="报送时间" prop="reportTime" width="180" />
          <el-table-column label="操作" fixed="right" width="230">
            <template slot-scope="scope">
              <el-button type="text" @click="openDetail(scope.row)">详情</el-button>
              <template v-if="scope.row.reviewStatus === 0 || scope.row.reviewStatus === 1">
                <el-button v-if="scope.row.reviewStatus === 1" v-hasPermi="['system:riskClue:review']" type="text" icon="el-icon-circle-check" @click="openReview(scope.row)">复核</el-button>
                <el-button v-hasPermi="['system:riskClue:edit']" type="text" icon="el-icon-edit" @click="openForm('edit', scope.row)">修改</el-button>
                <el-button v-hasPermi="['system:riskClue:remove']" type="text" class="danger-text" icon="el-icon-delete" @click="removeRow(scope.row)">删除</el-button>
              </template>
              <el-button v-if="scope.row.reviewStatus === 2 && scope.row.transferStatus === 0" v-hasPermi="['system:riskClue:transfer']" type="text" icon="el-icon-position" @click="openTransfer(scope.row)">移送</el-button>
              <el-button v-if="scope.row.reviewStatus === 3" v-hasPermi="['system:riskClue:edit']" type="text" icon="el-icon-edit-outline" @click="openForm('resubmit', scope.row)">补正重提</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" :page.sync="query.pageNum" :limit.sync="query.pageSize" @pagination="loadList" />
      </div>
    </div>

    <clue-form-dialog v-model="formVisible" :mode="formMode" :clue-id="selectedClue.id" @success="loadList" />
    <clue-detail-drawer v-model="detailVisible" :clue-id="selectedClue.id" />
    <clue-review-dialog v-model="reviewVisible" :clue="selectedClue" @success="loadList" />
    <clue-transfer-dialog v-model="transferVisible" :clue="selectedClue" @success="loadList" />
  </PageContent>
</template>

<script>
import service from "../../api";
import ClueFormDialog from "../../components/ClueFormDialog.vue";
import ClueDetailDrawer from "../../components/ClueDetailDrawer.vue";
import ClueReviewDialog from "../../components/ClueReviewDialog.vue";
import ClueTransferDialog from "../../components/ClueTransferDialog.vue";
import {
  clueTypeOptions, riskLevelOptions, reviewOptions, transferStatusOptions,
  feedbackStatusOptions, optionLabel, reviewTagType, riskTagType,
} from "../../config/clue";

export default {
  name: "RiskClueManagement",
  components: { ClueFormDialog, ClueDetailDrawer, ClueReviewDialog, ClueTransferDialog },
  data() {
    return {
      loading: false,
      rows: [],
      total: 0,
      reportRange: [],
      formVisible: false,
      detailVisible: false,
      reviewVisible: false,
      transferVisible: false,
      formMode: "create",
      selectedClue: {},
      query: { keyword: "", clueType: null, reviewStatus: null, transferStatus: null, feedbackStatus: null, pageNum: 1, pageSize: 10 },
      clueTypeOptions, riskLevelOptions, reviewOptions, transferStatusOptions, feedbackStatusOptions,
    };
  },
  created() { this.loadList(); },
  methods: {
    optionLabel,
    reviewTagType,
    riskTagType,
    async loadList() {
      this.loading = true;
      try {
        const data = { ...this.query };
        if (this.reportRange && this.reportRange.length === 2) {
          data.reportTimeStart = `${this.reportRange[0]} 00:00:00`;
          data.reportTimeEnd = `${this.reportRange[1]} 23:59:59`;
        }
        const res = await service.riskClueList(data);
        this.rows = res.rows || [];
        this.total = res.total || 0;
      } finally {
        this.loading = false;
      }
    },
    search() { this.query.pageNum = 1; this.loadList(); },
    resetSearch() {
      this.query = { keyword: "", clueType: null, reviewStatus: null, transferStatus: null, feedbackStatus: null, pageNum: 1, pageSize: 10 };
      this.reportRange = [];
      this.loadList();
    },
    openForm(mode, row = {}) { this.formMode = mode; this.selectedClue = row; this.formVisible = true; },
    openDetail(row) { this.selectedClue = row; this.detailVisible = true; },
    openReview(row) { this.selectedClue = row; this.reviewVisible = true; },
    openTransfer(row) { this.selectedClue = row; this.transferVisible = true; },
    removeRow(row) {
      this.$confirm(`确认删除线索“${row.clueNo}”吗？`, "提示", { type: "warning" }).then(async() => {
        await service.riskClueDelete(row.id);
        this.$message.success("删除成功");
        this.loadList();
      }).catch(() => {});
    },
  },
};
</script>

<style scoped lang="scss">
$primary: #1890ff; $border: #ebeef5; $bg: #f5f7fa; $text: #303133; $secondary: #909399; $radius: 8px; $shadow: 0 2px 8px rgba(0,0,0,.04);
.risk-clue-page { height:100%; display:flex; flex-direction:column; background:$bg; }
.search-card { padding:20px; margin-bottom:20px; background:#fff; border-radius:4px; box-shadow:0 2px 12px 0 rgba(0,0,0,.05); }
.search-form { display:flex; flex-wrap:wrap; gap:12px 4px; }
.search-form ::v-deep .el-form-item { margin-bottom:0; }
.table-card { flex:1; min-height:0; padding:20px; background:#fff; border-radius:4px; box-shadow:0 2px 12px 0 rgba(0,0,0,.05); display:flex; flex-direction:column; }
.table-toolbar { flex-shrink:0; padding:16px 0 12px; }
.clue-table { flex: 1; }
.clue-table ::v-deep th.el-table__cell { padding:12px 0; background:#fafbfc; border-bottom:2px solid $border; color:$text; font-size:13px; font-weight:600; }
.clue-table ::v-deep td.el-table__cell { padding:10px 0; }
.clue-table ::v-deep .el-table__body tr:nth-child(even)>td { background:#fafafa; }
.clue-table ::v-deep .el-table__body tr:hover>td { background:#f0f7ff !important; }
.warning-table ::v-deep th.el-table__cell { height:54px; padding:0; background:#fafafa; color:#303133; font-weight:600; }
.warning-table ::v-deep td.el-table__cell { height:64px; padding:0; color:#303133; }
.warning-table ::v-deep .cell { line-height:20px; }
.warning-table ::v-deep .el-table__fixed { box-shadow:4px 0 8px rgba(0,0,0,.04); }
.warning-table ::v-deep .el-table__fixed-right { box-shadow:-4px 0 8px rgba(0,0,0,.04); }
::v-deep .pagination-container { margin-top:20px; padding:0; text-align:right; }
.danger-text { color: #f56c6c; }
.clue-type-1 { color: #f56c6c; background: #fef0f0; border-color: #fde2e2; }
.clue-type-2 { color: #e6a23c; background: #fdf6ec; border-color: #faecd8; }
.clue-type-3 { color: #9b59b6; background: #f7effa; border-color: #eadcf0; }
.clue-type-4 { color: #17a2a4; background: #ecfafa; border-color: #d4f1f1; }
@media (max-width: 1200px) { .search-form ::v-deep .el-form-item { flex:1 1 220px; }.search-form ::v-deep .el-form-item:last-child { flex-basis:100%; } }
</style>
