<template>
  <PageContent>
    <!-- 搜索区域 -->
    <div class="search-card">
      <!-- <div class="card-header">
        <i class="el-icon-search card-header-icon" />
        <span class="card-header-title">筛选条件</span>
      </div> -->
      <el-form :inline="true" :model="query" size="small" class="search-form">
        <el-row :gutter="20" class="search-row">
          <el-col :xs="24" :sm="12" :md="8" class="search-col">
            <el-form-item label="关键词" class="search-item">
              <el-input
                v-model.trim="query.keyword"
                clearable
                placeholder="排查编号/区域/地址"
                prefix-icon="el-icon-search"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" class="search-col">
            <el-form-item label="排查时间" class="search-item">
              <el-date-picker
                v-model="inspectionRange"
                type="daterange"
                value-format="yyyy-MM-dd"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" class="search-col">
            <el-form-item label="复核状态" class="search-item">
              <el-select
                v-model="query.reviewStatus"
                clearable
                placeholder="复核状态"
              >
                <el-option
                  v-for="item in reviewOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" class="search-col">
            <el-form-item label="所属街道" class="search-item">
              <el-input
                v-model.trim="query.streetName"
                clearable
                placeholder="所属街道"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="8" class="search-col">
            <el-form-item label="主体类型" class="search-item">
              <el-select
                v-model="query.subjectType"
                clearable
                placeholder="主体类型"
              >
                <el-option
                  v-for="item in subjectOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :xs="24"
            :sm="12"
            :md="8"
            class="search-col search-actions-col"
          >
            <el-form-item class="search-actions">
              <el-button type="primary" icon="el-icon-search" @click="search"
                >查询</el-button
              >
              <el-button icon="el-icon-refresh" @click="resetSearch"
                >重置</el-button
              >
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <div class="table-card">
      <div class="table-toolbar">
        <div class="toolbar-left">
          <el-button
            v-hasPermi="['system:buildingSweep:add']"
            type="primary"
            icon="el-icon-plus"
            @click="openCreate"
            >扫楼上报</el-button
          >
          <!-- <i class="el-icon-s-grid toolbar-icon" /> -->
          <!-- <span class="toolbar-title">扫楼记录</span> -->
          <!-- <span class="toolbar-count"
            >共 <em>{{ total }}</em> 条</span
          > -->
        </div>
      </div>
      <el-table v-loading="loading" :data="rows" stripe height="100%" class="warning-table">
        <el-table-column label="排查编号" prop="reportNo" width="155" fixed="left">
          <template slot-scope="scope"
            ><el-link type="primary" @click="openDetail(scope.row)">{{
              scope.row.reportNo
            }}</el-link></template
          >
        </el-table-column>
        <el-table-column label="排查日期" prop="inspectionTime" width="110">
          <template slot-scope="scope">{{
            dateOnly(scope.row.inspectionTime)
          }}</template>
        </el-table-column>
        <el-table-column
          label="牵头部门"
          prop="leadDeptName"
          min-width="130"
          show-overflow-tooltip
        />
        <el-table-column
          label="排查区域"
          prop="areaFullName"
          min-width="210"
          show-overflow-tooltip
        />
        <el-table-column label="主体类型" min-width="160">
          <template slot-scope="scope">
            <el-tag
              v-for="type in subjectValues(scope.row.subjectTypes)"
              :key="type"
              size="mini"
              class="type-tag"
              >{{ subjectLabel(type) }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column
          label="覆盖主体数"
          prop="coveredSubjectCount"
          width="105"
          align="center"
        />
        <el-table-column
          label="排查人次"
          prop="inspectionPersonTimes"
          width="90"
          align="center"
        />
        <el-table-column label="填报人" prop="reporterNickName" width="100" />
        <el-table-column label="复核状态" width="105" align="center">
          <template slot-scope="scope"
            ><el-tag
              size="mini"
              :type="reviewTagType(scope.row.reviewStatus)"
              >{{ reviewLabel(scope.row.reviewStatus) }}</el-tag
            ></template
          >
        </el-table-column>
        <el-table-column label="关联线索" width="120">
          <template slot-scope="scope">{{
            scope.row.relatedClueNo || "待关联"
          }}</template>
        </el-table-column>
        <el-table-column label="上报时间" prop="reportTime" width="165" />
        <el-table-column label="操作" fixed="right" width="250">
          <template slot-scope="scope">
            <el-button type="text" @click="openDetail(scope.row)"
              >详情</el-button
            >
            <template v-if="scope.row.reviewStatus === 1">
              <el-button
                v-hasPermi="['system:buildingSweep:review']"
                type="text"
                @click="openReview(scope.row)"
                >复核</el-button
              >
              <el-button
                v-hasPermi="['system:buildingSweep:edit']"
                type="text"
                @click="openEdit(scope.row, false)"
                >修改</el-button
              >
              <el-button
                v-hasPermi="['system:buildingSweep:remove']"
                type="text"
                class="danger-text"
                :loading="deleting === scope.row.id"
                @click="removeRow(scope.row)"
                >删除</el-button
              >
            </template>
            <el-button
              v-if="scope.row.reviewStatus === 3"
              v-hasPermi="['system:buildingSweep:edit']"
              type="text"
              @click="openEdit(scope.row, true)"
              >补正重报</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="query.pageNum"
        :limit.sync="query.pageSize"
        @pagination="loadList"
      />
    </div>

    <el-dialog
      :title="
        formMode === 'create'
          ? '新增扫楼上报'
          : formMode === 'resubmit'
          ? '补正重报'
          : '修改扫楼上报'
      "
      :visible.sync="formVisible"
      width="920px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="reportForm"
        :model="form"
        :rules="rules"
        label-width="130px"
        class="report-form"
      >
        <!-- 基本信息 -->
        <div class="form-section">
          <div class="form-section-title">基本信息</div>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="排查时间" prop="inspectionTime">
                <el-date-picker
                  v-model="form.inspectionTime"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="请选择时间"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="牵头部门" prop="leadDeptName">
                <department-tree-select
                  v-model="form.leadDeptCode"
                  :names.sync="form.leadDeptName"
                  placeholder="请选择牵头部门"
                  @change="clearParticipantPersons"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="参与部门" prop="participantDeptNames">
                <department-tree-select
                  v-model="form.participantDeptCodes"
                  :names.sync="form.participantDeptNames"
                  multiple
                  placeholder="请选择参与部门"
                  @change="clearParticipantPersons"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="参与排查人员">
            <participant-user-select
              v-model="form.participantPersonUserNames"
              :names.sync="form.participantPersonNames"
              :department-codes="personScopeCodes"
            />
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="省市区" prop="regionCascade">
                <el-cascader
                  v-model="form.regionCascade"
                  :options="regionOptions"
                  :props="{
                    value: 'code',
                    label: 'name',
                    children: 'children',
                  }"
                  placeholder="请选择省市区"
                  style="width: 100%"
                  filterable
                  clearable
                  @change="onRegionCascadeChange"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="街道" prop="streetName">
                <el-input
                  v-model.trim="form.streetName"
                  placeholder="请输入街道"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="社区" prop="communityName">
                <el-input
                  v-model.trim="form.communityName"
                  placeholder="请输入社区"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="详细地址" prop="detailAddress">
            <el-input
              v-model.trim="form.detailAddress"
              maxlength="500"
              placeholder="请输入详细地址"
            />
          </el-form-item>
        </div>

        <!-- 排查情况 -->
        <div class="form-section form-section-last">
          <div class="form-section-title">排查情况</div>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="覆盖主体类型" prop="subjectTypeValue">
                <el-select
                  v-model="form.subjectTypeValue"
                  placeholder="请选择主体类型"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in subjectOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="String(item.value)"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="覆盖主体数" prop="coveredSubjectCount">
                <el-input-number
                  v-model="form.coveredSubjectCount"
                  :min="1"
                  :max="999999"
                  controls-position="right"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="排查人次" prop="inspectionPersonTimes">
                <el-input-number
                  v-model="form.inspectionPersonTimes"
                  :min="1"
                  :max="999999"
                  controls-position="right"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="排查问题简述">
            <el-input
              v-model.trim="form.issueSummary"
              type="textarea"
              :rows="3"
              maxlength="2000"
              show-word-limit
              placeholder="请输入排查发现的问题（选填）"
            />
          </el-form-item>
        </div>

        <!-- 佐证材料 -->
        <div class="form-section form-section-last">
          <div class="form-section-title">佐证材料</div>
          <el-upload
            multiple
            list-type="picture-card"
            accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx"
            :action="evidenceUpload.action"
            :headers="evidenceUpload.headers"
            :file-list="evidenceFileList"
            :limit="10"
            :before-upload="beforeEvidenceUpload"
            :on-success="handleEvidenceSuccess"
            :on-remove="handleEvidenceRemove"
            :on-preview="handleEvidencePreview"
            :on-error="handleEvidenceError"
          >
            <i class="el-icon-plus" />
            <div slot="tip" class="el-upload__tip">
              支持图片、PDF、Word、Excel，单个文件不超过20MB
            </div>
          </el-upload>
        </div>
      </el-form>
      <span slot="footer"
        ><el-button @click="formVisible = false">取消</el-button
        ><el-button type="primary" :loading="saving" @click="submitForm"
          >确定</el-button
        ></span
      >
    </el-dialog>

    <el-drawer
      title="扫楼上报详情"
      :visible.sync="detailVisible"
      size="620px"
      append-to-body
    >
      <div v-if="detail.id" v-loading="detailLoading" class="detail-body">
        <!-- 流程节点 -->
        <div class="process-card">
          <div class="process-flow">
            <div class="process-node done">
              <div class="process-dot">
                <i class="el-icon-check" />
              </div>
              <div class="process-info">
                <div class="process-title">已填报</div>
                <div class="process-time">{{ detail.reportTime }}</div>
              </div>
            </div>
            <div
              class="process-line"
              :class="{ done: detail.reviewStatus !== 1 }"
            />
            <div
              class="process-node"
              :class="{
                active: detail.reviewStatus === 1,
                done: detail.reviewStatus !== 1,
                reject: detail.reviewStatus === 3,
              }"
            >
              <div class="process-dot">
                <i v-if="detail.reviewStatus === 2" class="el-icon-check" />
                <i
                  v-else-if="detail.reviewStatus === 3"
                  class="el-icon-close"
                />
                <i v-else class="el-icon-time" />
              </div>
              <div class="process-info">
                <div class="process-title">
                  {{ reviewLabel(detail.reviewStatus) }}
                </div>
                <div class="process-time">
                  {{
                    detail.reviewStatus === 1 ? "等待复核" : detail.reviewTime
                  }}
                </div>
              </div>
            </div>
          </div>
        </div>
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="排查编号">{{
            detail.reportNo
          }}</el-descriptions-item>
          <el-descriptions-item label="复核状态"
            ><el-tag size="mini" :type="reviewTagType(detail.reviewStatus)">{{
              reviewLabel(detail.reviewStatus)
            }}</el-tag></el-descriptions-item
          >
          <el-descriptions-item label="排查时间">{{
            detail.inspectionTime
          }}</el-descriptions-item>
          <el-descriptions-item label="上报时间">{{
            detail.reportTime
          }}</el-descriptions-item>
          <el-descriptions-item label="牵头部门">{{
            detail.leadDeptName
          }}</el-descriptions-item>
          <el-descriptions-item label="填报人">{{
            detail.reporterNickName
          }}</el-descriptions-item>
          <el-descriptions-item label="参与部门" :span="2">{{
            detail.participantDeptNames || "-"
          }}</el-descriptions-item>
          <el-descriptions-item label="参与人员" :span="2">{{
            detail.participantPersonNames || "-"
          }}</el-descriptions-item>
        </el-descriptions>
        <div class="detail-section">
          <div class="section-title">排查区域</div>
          <p>行政区划：{{ detail.areaFullName }}</p>
          <p>详细地址：{{ detail.detailAddress }}</p>
        </div>
        <div class="detail-section">
          <div class="section-title">排查情况</div>
          <el-row :gutter="16" class="stat-row">
            <el-col :span="8">
              <div class="stat-card">
                <div class="stat-icon stat-icon--blue">
                  <i class="el-icon-office-building" />
                </div>
                <div class="stat-body">
                  <div class="stat-value">
                    {{ detail.coveredSubjectCount }} <small>家</small>
                  </div>
                  <div class="stat-label">覆盖主体数</div>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="stat-card">
                <div class="stat-icon stat-icon--green">
                  <i class="el-icon-user" />
                </div>
                <div class="stat-body">
                  <div class="stat-value">
                    {{ detail.inspectionPersonTimes }} <small>人次</small>
                  </div>
                  <div class="stat-label">排查人次</div>
                </div>
              </div>
            </el-col>
            <el-col :span="8">
              <div class="stat-card">
                <div class="stat-icon stat-icon--orange">
                  <i class="el-icon-collection-tag" />
                </div>
                <div class="stat-body">
                  <div class="stat-tags">
                    <el-tag
                      v-for="type in subjectValues(detail.subjectTypes)"
                      :key="type"
                      size="mini"
                      class="type-tag"
                      >{{ subjectLabel(type) }}</el-tag
                    >
                  </div>
                  <div class="stat-label">主体类型</div>
                </div>
              </div>
            </el-col>
          </el-row>
          <div class="metric-label issue-label">排查发现问题简述</div>
          <p>{{ detail.issueSummary || "无" }}</p>
        </div>
        <div class="detail-section">
          <div class="section-title">佐证材料</div>
          <div v-if="evidenceFiles(detail.evidenceMaterials).length">
            <div
              v-for="file in evidenceFiles(detail.evidenceMaterials)"
              :key="file"
              class="file-link-item"
            >
              <el-link
                type="primary"
                @click="previewDetailEvidence(file)"
              >
                <i :class="isImageFile(file) ? 'el-icon-picture-outline' : 'el-icon-document'" />
                {{ fileName(file) }}
              </el-link>
            </div>
          </div>
          <span v-else>无</span>
        </div>
        <div class="detail-section">
          <div class="section-title">复核信息</div>
          <template v-if="detail.reviewStatus === 1"
            >当前待管理员复核。</template
          >
          <template v-else
            ><p>复核人：{{ detail.reviewerNickName || "-" }}</p>
            <p>复核意见：{{ detail.reviewOpinion || "-" }}</p></template
          >
        </div>
        <div class="detail-section">
          <div class="section-title">后续关联</div>
          关联线索编号：{{ detail.relatedClueNo || "待关联" }}
        </div>
      </div>
    </el-drawer>

    <el-dialog
      title="扫楼上报复核"
      :visible.sync="reviewVisible"
      width="500px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form
        ref="reviewForm"
        :model="reviewForm"
        :rules="reviewRules"
        label-position="top"
      >
        <el-form-item label="复核结果" prop="reviewResult"
          ><el-select
            v-model="reviewForm.reviewResult"
            placeholder="请选择"
            style="width: 100%"
            ><el-option label="复核通过" :value="2" /><el-option
              label="退回补正"
              :value="3" /></el-select
        ></el-form-item>
        <el-form-item label="复核意见" prop="reviewOpinion"
          ><el-input
            v-model.trim="reviewForm.reviewOpinion"
            type="textarea"
            :rows="4"
            maxlength="1000"
            show-word-limit
            placeholder="请输入复核意见"
        /></el-form-item>
        <el-form-item label="是否关联线索" prop="isRelatedClue">
          <el-select
            v-model="reviewForm.isRelatedClue"
            placeholder="请选择是否关联线索"
            style="width: 100%"
            @change="handleRelatedClueChange"
          >
            <el-option label="是" :value="1" />
            <el-option label="否" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item
          v-if="reviewForm.isRelatedClue === 1"
          label="关联线索"
          prop="relatedClueNo"
        >
          <el-select
            v-model="reviewForm.relatedClueNo"
            filterable
            remote
            reserve-keyword
            clearable
            :remote-method="searchRelatedClues"
            :loading="relatedClueLoading"
            placeholder="请选择关联线索"
            style="width: 100%"
          >
            <el-option
              v-for="item in relatedClueOptions"
              :key="item.clueNo"
              :label="relatedClueLabel(item)"
              :value="item.clueNo"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer"
        ><el-button @click="reviewVisible = false">取消</el-button
        ><el-button type="primary" :loading="reviewing" @click="submitReview"
          >确定</el-button
        ></span
      >
    </el-dialog>

    <el-dialog
      :visible.sync="evidencePreviewVisible"
      append-to-body
      width="720px"
      @closed="releaseEvidencePreview"
    >
      <span slot="title">{{ evidencePreviewTitle }} <el-button type="text" icon="el-icon-download" class="preview-download" @click="downloadEvidenceFile">下载</el-button></span>
      <div v-loading="evidencePreviewLoading" class="evidence-preview-body">
        <img
          v-if="!evidencePreviewLoading && isImageFile(evidencePreviewFile)"
          :src="evidencePreviewUrl"
          class="evidence-preview-image"
          @error="handleEvidencePreviewError"
        />
        <pdf-body-preview
          v-else-if="!evidencePreviewLoading && isPdfFile(evidencePreviewFile)"
          :key="'pdf-' + evidencePreviewUrl"
          :url="evidencePreviewUrl"
          class="evidence-preview-pdf"
          @error="handleEvidencePreviewError"
        />
        <office-body-preview v-else-if="!evidencePreviewLoading && isOfficePreviewFile(evidencePreviewFile)" :key="'office-' + evidencePreviewUrl" :url="evidencePreviewUrl" :type="fileExtension(evidencePreviewFile)" @error="handleEvidencePreviewError" />
        <div v-else-if="!evidencePreviewLoading" class="evidence-preview-fallback">
          <i class="el-icon-document evidence-preview-fallback-icon" />
          <p class="evidence-preview-fallback-name">{{ evidencePreviewFile }}</p>
          <p class="evidence-preview-fallback-tip">该文件类型暂不支持在线预览</p>
          <el-button type="primary" @click="downloadEvidenceFile">下载文件</el-button>
        </div>
      </div>
    </el-dialog>
  </PageContent>
</template>

<script>
import { getToken } from "@/utils/auth";
import DepartmentTreeSelect from "../components/DepartmentTreeSelect.vue";
import ParticipantUserSelect from "../components/ParticipantUserSelect.vue";
import PdfBodyPreview from "../../crackdown/components/PdfBodyPreview.vue";
import OfficeBodyPreview from "../../crackdown/components/OfficeBodyPreview.vue";
import {
  provinces,
  cityMap,
  districtMap,
  findProvinceCode,
  findCityCode,
  findDistrictCode,
} from "@/utils/regionData";
import {
  listBuildingSweepReports,
  getBuildingSweepReport,
  addBuildingSweepReport,
  editBuildingSweepReport,
  reviewBuildingSweepReport,
  listRelatedClueOptions,
  resubmitBuildingSweepReport,
  deleteBuildingSweepReport,
} from "../api";

export default {
  name: "BuildingSweepReport",
  components: { DepartmentTreeSelect, ParticipantUserSelect, PdfBodyPreview, OfficeBodyPreview },
  data() {
    return {
      loading: false,
      saving: false,
      reviewing: false,
      relatedClueLoading: false,
      detailLoading: false,
      deleting: null,
      rows: [],
      total: 0,
      inspectionRange: [],
      formVisible: false,
      detailVisible: false,
      reviewVisible: false,
      formMode: "create",
      detail: {},
      evidenceFileList: [],
      regionOptions: [],
      relatedClueOptions: [],
      evidencePreviewVisible: false,
      evidencePreviewLoading: false,
      evidencePreviewUrl: "",
      evidencePreviewFile: "",
      evidencePreviewDirectTried: false,
      evidenceUpload: {
        action: process.env.VUE_APP_BASE_API + "/system/minio/upload",
        headers: { Authorization: "Bearer " + getToken() },
      },
      query: {
        keyword: "",
        reviewStatus: null,
        streetName: "",
        subjectType: null,
        pageNum: 1,
        pageSize: 10,
      },
      form: this.emptyForm(),
      reviewForm: {
        id: null,
        reviewResult: 2,
        reviewOpinion: "",
        isRelatedClue: 1,
        relatedClueNo: "",
      },
      subjectOptions: [
        { label: "商户", value: 1 },
        { label: "小微企业", value: 2 },
        { label: "办公室", value: 3 },
      ],
      reviewOptions: [
        { label: "待复核", value: 1 },
        { label: "复核通过", value: 2 },
        { label: "退回补正", value: 3 },
      ],
      rules: {
        inspectionTime: [
          { required: true, message: "请选择排查时间", trigger: "change" },
        ],
        leadDeptName: [
          { required: true, message: "请输入牵头部门", trigger: "blur" },
        ],
        regionCascade: [
          {
            validator: (_rule, value, callback) => {
              if (!value || value.length < 2) {
                callback(new Error("请选择省市区"));
              } else {
                callback();
              }
            },
            trigger: "change",
          },
        ],
        streetName: [
          { required: true, message: "请输入街道", trigger: "blur" },
        ],
        communityName: [
          { required: true, message: "请输入社区", trigger: "blur" },
        ],
        detailAddress: [
          { required: true, message: "请输入详细地址", trigger: "blur" },
        ],
        subjectTypeValue: [
          { required: true, message: "请选择主体类型", trigger: "change" },
        ],
        coveredSubjectCount: [
          { required: true, message: "请输入覆盖主体数", trigger: "change" },
        ],
        inspectionPersonTimes: [
          { required: true, message: "请输入排查人次", trigger: "change" },
        ],
      },
      reviewRules: {
        reviewResult: [
          { required: true, message: "请选择复核结果", trigger: "change" },
        ],
        reviewOpinion: [
          { required: true, message: "请输入复核意见", trigger: "blur" },
        ],
        isRelatedClue: [
          { required: true, message: "请选择是否关联线索", trigger: "change" },
        ],
        relatedClueNo: [
          {
            validator: (_rule, value, callback) => {
              if (this.reviewForm.isRelatedClue === 1 && !value) {
                callback(new Error("请选择关联线索"));
                return;
              }
              callback();
            },
            trigger: "change",
          },
        ],
      },
    };
  },
  computed: {
    personScopeCodes() {
      return [this.form.leadDeptCode, this.form.participantDeptCodes]
        .filter(Boolean).join(",");
    },
    evidencePreviewTitle() {
      return this.evidencePreviewFile
        ? "佐证材料预览 - " + this.fileName(this.evidencePreviewFile)
        : "佐证材料预览";
    },
  },
  created() {
    this.buildRegionOptions();
    this.loadList();
  },
  methods: {
    clearParticipantPersons() {
      this.form.participantPersonUserNames = "";
      this.form.participantPersonNames = "";
    },
    emptyForm() {
      return {
        id: null,
        inspectionTime: "",
        leadDeptCode: null,
        leadDeptName: "",
        participantDeptCodes: "",
        participantDeptNames: "",
        participantPersonUserNames: "",
        participantPersonNames: "",
        provinceCode: null,
        provinceName: "浙江省",
        cityCode: null,
        cityName: "温州市",
        districtCode: null,
        districtName: "瓯海区",
        regionCascade: ["330000", "330300", "330304"],
        streetCode: null,
        streetName: "",
        communityCode: null,
        communityName: "",
        detailAddress: "",
        subjectTypeValue: "",
        coveredSubjectCount: 1,
        inspectionPersonTimes: 1,
        issueSummary: "",
        evidenceMaterials: "",
      };
    },
    async loadList() {
      this.loading = true;
      try {
        const params = { ...this.query };
        if (this.inspectionRange && this.inspectionRange.length === 2) {
          params.inspectionTimeStart = `${this.inspectionRange[0]} 00:00:00`;
          params.inspectionTimeEnd = `${this.inspectionRange[1]} 23:59:59`;
        }
        const res = await listBuildingSweepReports(params);
        this.rows = res.rows || [];
        this.total = res.total || 0;
      } finally {
        this.loading = false;
      }
    },
    search() {
      this.query.pageNum = 1;
      this.loadList();
    },
    resetSearch() {
      this.query = {
        keyword: "",
        reviewStatus: null,
        streetName: "",
        subjectType: null,
        pageNum: 1,
        pageSize: 10,
      };
      this.inspectionRange = [];
      this.loadList();
    },
    /** 构建省市区级联选项（本地数据） */
    buildRegionOptions() {
      this.regionOptions = provinces.map((province) => {
        const cities = cityMap[province.code] || [];
        return {
          code: province.code,
          name: province.name,
          children: cities.length
            ? cities.map((city) => {
                const districts = districtMap[city.code] || [];
                if (districts.length) {
                  return {
                    code: city.code,
                    name: city.name,
                    children: districts.map((d) => ({
                      code: d.code,
                      name: d.name,
                    })),
                  };
                }
                return { code: city.code, name: city.name };
              })
            : undefined,
        };
      });
    },
    /** 级联选择变更 → 同步到 form 各字段 */
    onRegionCascadeChange(value) {
      if (!value || value.length === 0) {
        this.form.provinceCode = null;
        this.form.provinceName = "";
        this.form.cityCode = null;
        this.form.cityName = "";
        this.form.districtCode = null;
        this.form.districtName = "";
        return;
      }
      const [provinceCode, cityCode, districtCode] = [
        value[0],
        value[1],
        value[2] || null,
      ];
      const province = provinces.find((p) => p.code === provinceCode);
      const cities = cityMap[provinceCode] || [];
      const city = cities.find((c) => c.code === cityCode);
      const districts = districtMap[cityCode] || [];
      const district = districtCode
        ? districts.find((d) => d.code === districtCode)
        : null;

      this.form.provinceCode = provinceCode;
      this.form.provinceName = province ? province.name : "";
      this.form.cityCode = cityCode;
      this.form.cityName = city ? city.name : "";
      this.form.districtCode = districtCode;
      this.form.districtName = district ? district.name : "";
    },
    /** 回显时设置级联值 */
    setRegionCascade(provinceName, cityName, districtName) {
      const provinceCode = findProvinceCode(provinceName);
      if (!provinceCode) {
        this.form.regionCascade = [];
        return;
      }
      const cityCode = findCityCode(cityName, provinceCode);
      if (!cityCode) {
        this.form.regionCascade = [provinceCode];
        return;
      }
      const districtCode = findDistrictCode(districtName, cityCode);
      this.form.regionCascade = districtCode
        ? [provinceCode, cityCode, districtCode]
        : [provinceCode, cityCode];
    },
    async openCreate() {
      this.formMode = "create";
      this.form = this.emptyForm();
      this.evidenceFileList = [];
      this.formVisible = true;
      this.$nextTick(
        () => this.$refs.reportForm && this.$refs.reportForm.clearValidate()
      );
    },
    async openEdit(row, resubmit) {
      const res = await getBuildingSweepReport(row.id);
      const data = res.data || {};
      this.formMode = resubmit ? "resubmit" : "edit";
      this.form = {
        ...this.emptyForm(),
        ...data,
        subjectTypeValue: this.subjectValues(data.subjectTypes)[0] || "",
      };
      this.evidenceFileList = this.evidenceFiles(data.evidenceMaterials).map(
        (fileName) => ({
          name: fileName,
          url: this.minioDownloadUrl(fileName),
        })
      );
      this.formVisible = true;
      this.setRegionCascade(
        this.form.provinceName,
        this.form.cityName,
        this.form.districtName
      );
      this.$nextTick(
        () => this.$refs.reportForm && this.$refs.reportForm.clearValidate()
      );
    },
    submitForm() {
      this.$refs.reportForm.validate(async (valid) => {
        if (!valid) return;
        this.saving = true;
        try {
          const data = {
            ...this.form,
            subjectTypes: this.form.subjectTypeValue,
          };
          delete data.subjectTypeValue;
          delete data.regionCascade;
          if (this.formMode === "create") await addBuildingSweepReport(data);
          else if (this.formMode === "resubmit")
            await resubmitBuildingSweepReport(data);
          else await editBuildingSweepReport(data);
          this.$message.success(
            this.formMode === "resubmit" ? "补正重报成功" : "保存成功"
          );
          this.formVisible = false;
          this.loadList();
        } finally {
          this.saving = false;
        }
      });
    },
    async openDetail(row) {
      this.detailVisible = true;
      this.detailLoading = true;
      this.detail = {};
      try {
        const res = await getBuildingSweepReport(row.id);
        this.detail = res.data || {};
      } finally {
        this.detailLoading = false;
      }
    },
    openReview(row) {
      this.reviewForm = {
        id: row.id,
        reviewResult: 2,
        reviewOpinion: "",
        isRelatedClue: 1,
        relatedClueNo: "",
      };
      this.relatedClueOptions = [];
      this.reviewVisible = true;
      this.loadRelatedClueOptions();
      this.$nextTick(
        () => this.$refs.reviewForm && this.$refs.reviewForm.clearValidate()
      );
    },
    async loadRelatedClueOptions(keyword = "") {
      this.relatedClueLoading = true;
      try {
        const res = await listRelatedClueOptions(keyword.trim());
        this.relatedClueOptions = Array.isArray(res.data) ? res.data : [];
      } finally {
        this.relatedClueLoading = false;
      }
    },
    searchRelatedClues(keyword) {
      this.loadRelatedClueOptions(keyword || "");
    },
    relatedClueLabel(item) {
      return item.clueTitle
        ? `${item.clueNo} - ${item.clueTitle}`
        : item.clueNo;
    },
    handleRelatedClueChange(value) {
      if (value === 0) {
        this.reviewForm.relatedClueNo = "";
      } else if (!this.relatedClueOptions.length) {
        this.loadRelatedClueOptions();
      }
      this.$nextTick(() => {
        if (this.$refs.reviewForm) {
          this.$refs.reviewForm.clearValidate("relatedClueNo");
        }
      });
    },
    submitReview() {
      this.$refs.reviewForm.validate(async (valid) => {
        if (!valid) return;
        this.reviewing = true;
        try {
          await reviewBuildingSweepReport(this.reviewForm);
          this.$message.success("复核成功");
          this.reviewVisible = false;
          this.loadList();
        } finally {
          this.reviewing = false;
        }
      });
    },
    removeRow(row) {
      this.$confirm(`确认删除排查记录"${row.reportNo}"吗？`, "提示", {
        type: "warning",
      })
        .then(async () => {
          this.deleting = row.id;
          try {
            await deleteBuildingSweepReport(row.id);
            this.$message.success("删除成功");
            this.loadList();
          } finally {
            this.deleting = null;
          }
        })
        .catch(() => {});
    },
    subjectValues(value) {
      return value ? String(value).split(",").filter(Boolean) : [];
    },
    subjectLabel(value) {
      const item = this.subjectOptions.find(
        (v) => String(v.value) === String(value)
      );
      return item ? item.label : value;
    },
    reviewLabel(value) {
      const item = this.reviewOptions.find((v) => v.value === value);
      return item ? item.label : "未知";
    },
    reviewTagType(value) {
      return value === 2 ? "success" : value === 3 ? "danger" : "warning";
    },
    dateOnly(value) {
      return value ? value.slice(0, 10) : "-";
    },
    evidenceFiles(value) {
      return value ? value.split(",").filter(Boolean) : [];
    },
    fileName(value) {
      return decodeURIComponent(value.substring(value.lastIndexOf("/") + 1));
    },
    minioDownloadUrl(fileName) {
      return (
        process.env.VUE_APP_BASE_API +
        "/system/minio/download?fileName=" +
        encodeURIComponent(fileName)
      );
    },
    beforeEvidenceUpload(file) {
      const suffix = file.name
        .substring(file.name.lastIndexOf(".") + 1)
        .toLowerCase();
      const allowed = [
        "jpg",
        "jpeg",
        "png",
        "pdf",
        "doc",
        "docx",
        "xls",
        "xlsx",
      ];
      if (!allowed.includes(suffix)) {
        this.$message.error("文件格式不支持");
        return false;
      }
      if (file.size / 1024 / 1024 > 20) {
        this.$message.error("单个文件不能超过20MB");
        return false;
      }
      return true;
    },
    handleEvidenceSuccess(res, file) {
      if (res.code !== 200) {
        this.$message.error(res.msg || "文件上传失败");
        return;
      }
      const uploadedFile = {
        name: res.fileName,
        url: res.url,
        uid: file.uid,
        status: "success",
      };
      this.evidenceFileList = this.evidenceFileList
        .filter((item) => item.uid !== file.uid)
        .concat(uploadedFile);
      this.form.evidenceMaterials = this.evidenceFileList
        .map((item) => item.name)
        .join(",");
    },
    handleEvidenceRemove(file) {
      const fileName =
        file.response && file.response.fileName
          ? file.response.fileName
          : file.name;
      this.evidenceFileList = this.evidenceFileList.filter(
        (item) => item.name !== fileName
      );
      this.form.evidenceMaterials = this.evidenceFileList
        .map((item) => item.name)
        .join(",");
    },
    handleEvidenceError() {
      this.$message.error("文件上传失败，请重试");
    },
    /** 判断文件名是否为图片格式 */
    isImageFile(fileName) {
      const suffix = fileName
        .substring(fileName.lastIndexOf(".") + 1)
        .toLowerCase();
      return ["jpg", "jpeg", "png", "gif", "bmp", "webp"].includes(suffix);
    },
    isPdfFile(fileName) {
      return String(fileName || "").toLowerCase().endsWith(".pdf");
    },
    fileExtension(fileName) { const index=String(fileName||"").lastIndexOf("."); return index<0?"":String(fileName).substring(index+1).toLowerCase(); },
    isOfficePreviewFile(fileName) { return ["docx","xlsx"].includes(this.fileExtension(fileName)); },
    /** 详情页佐证材料预览（先弹窗再加载） */
    previewDetailEvidence(fileName, useDirectUrl = true) {
      this.releaseEvidencePreview();
      this.evidencePreviewFile = fileName;
      this.evidencePreviewUrl = "";
      this.evidencePreviewLoading = true;
      this.evidencePreviewVisible = true;
      this.evidencePreviewDirectTried = false;
      const directUrl = useDirectUrl && (this.isImageFile(fileName) || this.isPdfFile(fileName) || this.isOfficePreviewFile(fileName)) && this.detail.evidencePreviewUrls
        ? this.detail.evidencePreviewUrls[fileName]
        : "";
      if (directUrl) {
        this.evidencePreviewDirectTried = true;
        this.evidencePreviewUrl = directUrl;
        this.evidencePreviewLoading = false;
        return;
      }
      const url = this.minioDownloadUrl(fileName);
      fetch(url, {
        headers: { Authorization: "Bearer " + getToken() },
      })
        .then((res) => res.blob())
        .then((blob) => {
          const source = blob instanceof Blob ? blob : new Blob([blob]);
          const mime = this.isPdfFile(fileName)
            ? "application/pdf"
            : this.isImageFile(fileName)
              ? "image/" + fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().replace("jpg", "jpeg")
              : "application/octet-stream";
          const url = URL.createObjectURL(new Blob([source], { type: mime }));
          if (this.isImageFile(fileName) || this.isPdfFile(fileName) || this.isOfficePreviewFile(fileName) || ["doc","xls"].includes(this.fileExtension(fileName))) {
            this.evidencePreviewUrl = url;
          } else {
            const a = document.createElement("a");
            a.href = url;
            a.download = this.fileName(fileName);
            a.click();
            setTimeout(() => URL.revokeObjectURL(url), 1000);
            this.evidencePreviewVisible = false;
          }
        })
        .catch(() => {
          this.$message.error("文件预览失败");
          this.evidencePreviewVisible = false;
        })
        .finally(() => {
          this.evidencePreviewLoading = false;
        });
    },
    handleEvidencePreviewError(failedSource) {
      if (!this.evidencePreviewVisible) return;
      if (typeof failedSource === "string" && failedSource !== this.evidencePreviewUrl) return;
      if (!this.evidencePreviewDirectTried || !this.evidencePreviewFile) {
        if (this.isImageFile(this.evidencePreviewFile)) this.$message.error("图片加载失败");
        return;
      }
      const fileName = this.evidencePreviewFile;
      this.previewDetailEvidence(fileName, false);
    },
    releaseEvidencePreview() {
      if (this.evidencePreviewUrl && this.evidencePreviewUrl.indexOf("blob:") === 0) {
        URL.revokeObjectURL(this.evidencePreviewUrl);
      }
      this.evidencePreviewUrl = "";
      this.evidencePreviewLoading = false;
      this.evidencePreviewDirectTried = false;
    },
    /** 预览弹窗中下载当前文件 */
    downloadEvidenceFile() {
      if (this.evidencePreviewUrl) {
        const a = document.createElement("a");
        a.href = this.evidencePreviewUrl;
        a.download = this.evidencePreviewFile
          ? this.fileName(this.evidencePreviewFile)
          : "file";
        a.click();
      }
    },
    handleEvidencePreview(file) {
      this.evidencePreviewFile = file.name;
      const suffix = file.name
        .substring(file.name.lastIndexOf(".") + 1)
        .toLowerCase();
      if (["jpg", "jpeg", "png"].includes(suffix)) {
        this.evidencePreviewUrl = file.url;
        this.evidencePreviewVisible = true;
        return;
      }
      window.open(file.url || this.minioDownloadUrl(file.name), "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
// =============================================
// Design tokens
// =============================================
$color-primary: #1890ff;
$color-primary-light: #e8f4fd;
$color-success: #52c41a;
$color-success-light: #edf9e8;
$color-warning: #faad14;
$color-warning-light: #fff8e6;
$color-danger: #f56c6c;
$color-danger-light: #fef0f0;
$color-border: #ebeef5;
$color-bg: #f5f7fa;
$color-text-primary: #303133;
$color-text-regular: #606266;
$color-text-secondary: #909399;
$radius-base: 8px;
$radius-sm: 4px;
$shadow-card: 0 2px 8px rgba(0, 0, 0, 0.04);
$shadow-hover: 0 4px 16px rgba(0, 0, 0, 0.08);

// =============================================
// Card header (shared between search & table)
// =============================================
.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid $color-border;

  .card-header-icon {
    font-size: 16px;
    color: $color-primary;
    margin-right: 8px;
  }

  .card-header-title {
    font-size: 14px;
    font-weight: 600;
    color: $color-text-primary;
  }
}

// =============================================
// Search area
// =============================================
.search-card {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

.search-form {
  .search-row {
    display: flex;
    flex-wrap: wrap;
    row-gap: 12px;
  }

  ::v-deep .el-form-item {
    margin-bottom: 0;
  }

  .search-col {
    display: flex;
  }

  .search-item {
    display: flex;
    width: 100%;

    ::v-deep .el-form-item__label {
      flex-shrink: 0;
      min-width: 68px;
      text-align: right;
      padding-right: 8px;
      color: $color-text-regular;
      font-weight: 500;
    }

    ::v-deep .el-form-item__content {
      flex: 1;
      min-width: 0;

      .el-input,
      .el-select,
      .el-date-editor {
        width: 100%;
      }
    }
  }

  .search-actions {
    ::v-deep .el-form-item__content {
      margin-left: 0 !important;
      display: flex;
      align-items: center;
      gap: 8px;
      flex-wrap: wrap;
    }
  }
}

// =============================================
// Table area
// =============================================
.table-card {
  flex: 1;
  min-height: 0;
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
}

.table-toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 0 12px;
  flex-shrink: 0;

  .toolbar-left {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .toolbar-icon {
    font-size: 16px;
    color: $color-primary;
  }

  .toolbar-title {
    font-size: 14px;
    font-weight: 600;
    color: $color-text-primary;
  }

  .toolbar-count {
    font-size: 13px;
    color: $color-text-secondary;
    margin-left: 4px;

    em {
      font-style: normal;
      color: $color-primary;
      font-weight: 600;
      margin: 0 2px;
    }
  }
}

::v-deep .el-table {
  flex: 1;
  border-radius: $radius-base $radius-base 0 0;

  // Header
  th.el-table__cell {
    background-color: #fafbfc;
    color: $color-text-primary;
    font-weight: 600;
    font-size: 13px;
    padding: 12px 0;
    border-bottom: 2px solid $color-border;

    .cell {
      line-height: 1.5;
    }
  }

  // Body rows
  td.el-table__cell {
    padding: 10px 0;
  }

  // Zebra striping
  .el-table__body tr:nth-child(even) > td {
    background-color: #fafafa;
  }

  .el-table__body tr:hover > td {
    background-color: #f0f7ff !important;
  }

  .cell {
    line-height: 1.5;
  }

  // Fix border radius with border
  .el-table__header-wrapper {
    border-radius: $radius-base $radius-base 0 0;
  }
}

::v-deep .pagination-container {
  margin-top: 20px;
  padding: 0;
  text-align: right;
}

// =============================================
// Tags & links
// =============================================
.type-tag {
  margin-right: 4px;
  margin-bottom: 2px;
  border-radius: 3px;
  font-weight: 500;
}

.danger-text {
  color: $color-danger;
  transition: opacity 0.2s;

  &:hover {
    opacity: 0.8;
  }
}

// =============================================
// Form dialog
// =============================================
.report-form {
  ::v-deep .el-form-item {
    margin-bottom: 20px;
  }

  ::v-deep .el-form-item__label {
    font-weight: 500;
    color: $color-text-primary;
  }
}

.form-section {
  margin-bottom: 4px;
}

.form-section-last {
  margin-bottom: 0;
}

.form-section-title {
  font-size: 15px;
  font-weight: 600;
  color: $color-text-primary;
  padding: 0 0 12px 12px;
  margin-bottom: 20px;
  border-bottom: 1px solid $color-border;
  position: relative;
  letter-spacing: 0.5px;

  &::before {
    content: "";
    position: absolute;
    left: 0;
    top: 2px;
    bottom: 12px;
    width: 3px;
    background: $color-primary;
    border-radius: 2px;
  }
}

// =============================================
// Detail drawer
// =============================================
.detail-body {
  padding: 0 4px 32px;
}

// ---- process flow ----
.process-card {
  background: #fff;
  border: 1px solid $color-border;
  border-radius: $radius-base;
  padding: 28px 32px;
  margin-bottom: 16px;
}

.process-flow {
  display: flex;
  align-items: center;
  justify-content: center;
}

.process-node {
  display: flex;
  align-items: center;
  gap: 12px;
  flex-shrink: 0;
}

.process-dot {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: $color-bg;
  border: 2px solid $color-border;
  color: $color-text-secondary;
  font-size: 14px;
  transition: all 0.3s ease;
  flex-shrink: 0;

  .process-node.done & {
    background: $color-primary-light;
    border-color: $color-primary;
    color: $color-primary;
  }

  .process-node.reject & {
    background: $color-danger-light;
    border-color: $color-danger;
    color: $color-danger;
  }

  .process-node.active & {
    background: $color-warning-light;
    border-color: $color-warning;
    color: $color-warning;
    animation: pulse 2s infinite;
  }
}

@keyframes pulse {
  0%,
  100% {
    box-shadow: 0 0 0 0 rgba($color-warning, 0.3);
  }
  50% {
    box-shadow: 0 0 0 8px rgba($color-warning, 0);
  }
}

.process-info {
  .process-title {
    font-size: 14px;
    font-weight: 600;
    color: $color-text-primary;
    margin-bottom: 2px;

    .process-node.done & {
      color: $color-primary;
    }
    .process-node.reject & {
      color: $color-danger;
    }
    .process-node.active & {
      color: $color-warning;
    }
  }

  .process-time {
    font-size: 12px;
    color: $color-text-secondary;
    white-space: nowrap;
  }
}

.process-line {
  width: 80px;
  height: 2px;
  background: $color-border;
  margin: 0 16px;
  flex-shrink: 0;
  transition: background 0.4s ease;

  &.done {
    background: $color-primary;
  }
}

// ---- detail sections ----
.detail-section {
  border: 1px solid $color-border;
  border-radius: $radius-base;
  margin-bottom: 16px;
  padding: 20px 24px;
  background: #fff;
  transition: box-shadow 0.25s ease;

  &:hover {
    box-shadow: $shadow-hover;
  }
}

.section-title {
  font-weight: 600;
  font-size: 14px;
  margin-bottom: 16px;
  color: $color-text-primary;
  padding-left: 10px;
  border-left: 3px solid $color-primary;
  line-height: 1.4;
}

.detail-section p {
  margin: 10px 0;
  line-height: 1.8;
  color: $color-text-regular;
  font-size: 14px;
}

// ---- stat cards ----
.stat-row {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 16px;
  background: $color-bg;
  border-radius: $radius-base;
  border: 1px solid transparent;
  transition: all 0.25s ease;
  height: 100%;

  &:hover {
    background: #fff;
    border-color: $color-border;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
    transform: translateY(-1px);
  }
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: $radius-base;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex-shrink: 0;

  &--blue {
    background: $color-primary-light;
    color: $color-primary;
  }

  &--green {
    background: $color-success-light;
    color: $color-success;
  }

  &--orange {
    background: $color-warning-light;
    color: #e6a23c;
  }
}

.stat-body {
  flex: 1;
  min-width: 0;
}

.stat-value {
  font-size: 22px;
  font-weight: 700;
  color: $color-text-primary;
  line-height: 1.2;
  margin-bottom: 2px;

  small {
    font-size: 13px;
    font-weight: 400;
    color: $color-text-secondary;
  }
}

.stat-label {
  font-size: 12px;
  color: $color-text-secondary;
}

.stat-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
  margin-bottom: 4px;
}

// ---- issue area ----
.issue-label {
  margin-top: 24px;
  font-weight: 600;
  color: $color-text-primary;
  border-top: 1px dashed $color-border;
  padding-top: 16px;
}

// ---- file links ----
.file-link-item {
  margin-bottom: 8px;
  padding: 10px 14px;
  background: $color-bg;
  border-radius: $radius-sm;
  transition: background 0.2s, transform 0.15s;

  &:hover {
    background: $color-primary-light;
    transform: translateX(2px);
  }

  .el-link {
    font-size: 13px;

    i {
      margin-right: 6px;
    }
  }
}

// ---- descriptions ----
::v-deep .el-descriptions {
  margin-bottom: 16px;
  border-radius: $radius-base;
  overflow: hidden;

  .el-descriptions-item__label {
    font-weight: 600;
    color: $color-text-primary;
    background-color: $color-bg;
    width: 100px;
  }

  .el-descriptions-item__content {
    color: $color-text-regular;
  }
}

// =============================================
// Upload area
// =============================================
::v-deep .el-upload {
  .el-upload__tip {
    margin-top: 10px;
    color: $color-text-secondary;
    font-size: 12px;
    line-height: 1.6;
  }
}

// =============================================
// Evidence preview image
// =============================================
.evidence-preview-body {
  min-height: 200px;
}

.evidence-preview-image {
  display: block;
  max-width: 100%;
  max-height: 70vh;
  margin: 0 auto;
  border-radius: $radius-sm;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12);
}

.warning-table {
  ::v-deep th.el-table__cell {
    height: 54px;
    padding: 0;
    background: #fafafa;
    color: #303133;
    font-weight: 600;
  }

  ::v-deep td.el-table__cell {
    height: 64px;
    padding: 0;
    color: #303133;
  }

  ::v-deep .cell {
    line-height: 20px;
  }

  ::v-deep .el-table__fixed {
    box-shadow: 4px 0 8px rgba(0, 0, 0, 0.04);
  }

  ::v-deep .el-table__fixed-right {
    box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
  }
}
.evidence-preview-pdf { width:100%; height:72vh; border:0; }
.preview-download { float:right; margin-right:28px; padding:0; }

.evidence-preview-fallback {
  text-align: center;
  padding: 48px 24px;

  &-icon {
    font-size: 64px;
    color: #dcdfe6;
    margin-bottom: 16px;
  }

  &-name {
    font-size: 14px;
    color: $color-text-primary;
    margin: 0 0 8px;
    word-break: break-all;
  }

  &-tip {
    font-size: 13px;
    color: $color-text-secondary;
    margin: 0 0 24px;
  }
}

// =============================================
// Dialogs & Drawer global overrides
// =============================================
::v-deep .el-dialog {
  border-radius: $radius-base;
  overflow: hidden;

  .el-dialog__header {
    background-color: $color-bg;
    padding: 16px 24px;
    border-bottom: 1px solid $color-border;

    .el-dialog__title {
      font-size: 16px;
      font-weight: 600;
      color: $color-text-primary;
    }
  }

  .el-dialog__body {
    padding: 24px;
  }

  .el-dialog__footer {
    padding: 16px 24px;
    border-top: 1px solid $color-border;
    text-align: right;

    .el-button + .el-button {
      margin-left: 10px;
    }
  }
}

::v-deep .el-drawer {
  .el-drawer__header {
    padding: 16px 24px;
    margin-bottom: 0;
    border-bottom: 1px solid $color-border;

    > :first-child {
      font-size: 16px;
      font-weight: 600;
      color: $color-text-primary;
    }
  }

  .el-drawer__body {
    padding: 16px 24px;
    overflow-y: auto;
  }
}

// =============================================
// Responsive
// =============================================
@media (max-width: 768px) {
  .search-card {
    padding: 16px 16px 4px;
  }

  .table-card {
    padding: 0 16px 8px;
  }

  .search-actions {
    ::v-deep .el-form-item__content {
      justify-content: flex-start;
    }
  }

  .process-line {
    width: 40px;
    margin: 0 8px;
  }

  .process-card {
    padding: 20px 16px;
  }

  .stat-card {
    margin-bottom: 12px;
  }
}
</style>
