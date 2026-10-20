<template>
  <el-drawer title="线索详情" :visible.sync="drawerVisible" size="680px" append-to-body class="clue-detail-drawer" @closed="reset">
    <div v-if="detail.id" v-loading="loading" class="detail-body">
      <div class="detail-card process-card">
        <div class="section-title blue-title">流程节点</div>
        <el-steps :active="processActive" finish-status="success" align-center>
          <el-step title="线索上报" :description="detail.reportTime || '草稿未提交'" />
          <el-step :title="reviewStepTitle" :description="reviewStepDescription" />
          <el-step :title="detail.transferStatus === 1 ? '已移送' : '线索移送'" :description="detail.transferRecord ? detail.transferRecord.transferTime : '待移送'" />
          <el-step :title="detail.feedbackStatus === 1 ? '已反馈' : '接收反馈'" :description="latestFeedbackTime" />
        </el-steps>
      </div>

      <div class="detail-card">
        <div class="section-title blue-title">基本信息</div>
        <el-descriptions :column="2" size="small">
          <el-descriptions-item label="线索编号">{{ detail.clueNo }}</el-descriptions-item>
          <el-descriptions-item label="报送机构">{{ detail.reportOrgName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="线索类型">{{ optionLabel(clueTypeOptions, detail.clueType) }}</el-descriptions-item>
          <el-descriptions-item label="线索来源">{{ optionLabel(clueSourceOptions, detail.clueSource) }}</el-descriptions-item>
          <el-descriptions-item label="风险等级">{{ optionLabel(riskLevelOptions, detail.riskLevel) }}</el-descriptions-item>
          <el-descriptions-item label="所属街道">{{ detail.streetName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="复核状态">{{ optionLabel(reviewOptions, detail.reviewStatus) }}</el-descriptions-item>
          <el-descriptions-item label="移送状态">{{ optionLabel(transferStatusOptions, detail.transferStatus) }}</el-descriptions-item>
          <el-descriptions-item label="反馈状态">{{ optionLabel(feedbackStatusOptions, detail.feedbackStatus) }}</el-descriptions-item>
          <el-descriptions-item label="报送时间">{{ detail.reportTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="线索标题" :span="2">{{ detail.clueTitle }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="detail-card">
        <div class="section-title orange-title">涉险主体信息</div>
        <el-descriptions :column="2" size="small">
          <el-descriptions-item label="主体名称">{{ detail.riskSubject }}</el-descriptions-item>
          <el-descriptions-item label="统一社会信用代码">{{ detail.unifiedSocialCreditCode || '-' }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ detail.contactName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.contactPhone || '-' }}</el-descriptions-item>
          <el-descriptions-item label="主体地址" :span="2">{{ detail.subjectAddress }}</el-descriptions-item>
          <el-descriptions-item label="定位信息" :span="2">{{ detail.locationAddress || '-' }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="detail-card"><div class="section-title green-title">线索描述</div><div class="description-box">{{ detail.clueDescription }}</div></div>

      <div class="detail-card">
        <div class="section-title purple-title">复核信息</div>
        <div v-if="detail.reviewRecords && detail.reviewRecords.length">
          <div v-for="item in detail.reviewRecords" :key="item.id" class="history-item">
            <div>第{{ item.reviewRound }}轮 · {{ optionLabel(reviewOptions, item.reviewResult) }} <span>{{ item.reviewTime }}</span></div>
            <p>{{ item.reviewOpinion }}</p>
          </div>
        </div>
        <span v-else class="muted">当前暂无复核记录</span>
      </div>

      <div class="detail-card">
        <div class="section-title orange-title">材料信息</div>
        <div class="file-group">
          <div class="file-label">现场照片</div>
          <div v-for="file in filesOfType(1)" :key="file.id" class="file-link-item">
            <el-link type="primary" @click="openFile(file)"><i class="el-icon-picture-outline" />{{ file.originalName }}</el-link>
          </div>
          <span v-if="!filesOfType(1).length" class="muted">无</span>
        </div>
        <div class="file-group">
          <div class="file-label">附件材料</div>
          <div v-for="file in filesOfType(2)" :key="file.id" class="file-link-item">
            <el-link type="primary" @click="openFile(file)"><i class="el-icon-document" />{{ file.originalName }}</el-link>
          </div>
          <span v-if="!filesOfType(2).length" class="muted">无</span>
        </div>
      </div>

      <div v-if="detail.transferRecord" class="detail-card">
        <div class="section-title blue-title">移送信息</div>
        <p>接收单位：{{ detail.transferRecord.receiveOrgName }}</p><p>移送依据：{{ detail.transferRecord.transferBasis }}</p><p>公文信息：{{ detail.transferRecord.documentInfo }}</p><p>移送时间：{{ detail.transferRecord.transferTime }}</p>
      </div>
    </div>
    <el-dialog :visible.sync="previewVisible" append-to-body width="720px" class="file-preview-dialog" @closed="releasePreviewUrl">
      <span slot="title">材料预览 - {{ previewName }} <el-button type="text" icon="el-icon-download" class="preview-download" :disabled="previewLoading || !previewUrl" @click="downloadCurrent">下载</el-button></span>
      <div v-loading="previewLoading" class="preview-body">
        <img v-if="!previewLoading && isImageFile(previewName)" :src="previewUrl" class="preview-image" @error="handlePreviewFileError">
        <pdf-body-preview v-else-if="!previewLoading && isPdfFile(previewName)" :key="'pdf-' + previewUrl" :url="previewUrl" class="preview-pdf" @error="handlePreviewFileError" />
        <office-body-preview v-else-if="!previewLoading && isOfficePreviewFile(previewName)" :key="'office-' + previewUrl" :url="previewUrl" :type="extension(previewName)" @error="handlePreviewFileError" />
        <div v-else-if="!previewLoading" class="preview-fallback">
          <i class="el-icon-document preview-fallback-icon" />
          <p class="preview-fallback-name">{{ previewName }}</p>
          <p class="preview-fallback-tip">旧版 DOC、XLS 文件暂不支持浏览器直接预览</p>
          <el-button type="primary" @click="downloadCurrent">下载文件</el-button>
        </div>
      </div>
    </el-dialog>
  </el-drawer>
</template>

<script>
import service from "../api";
import PdfBodyPreview from "./PdfBodyPreview.vue";
import OfficeBodyPreview from "./OfficeBodyPreview.vue";
import {
  clueSourceOptions, clueTypeOptions, riskLevelOptions, reviewOptions,
  transferStatusOptions, feedbackStatusOptions, optionLabel,
} from "../config/clue";

export default {
  name: "ClueDetailDrawer",
  components: { PdfBodyPreview, OfficeBodyPreview },
  props: {
    value: { type: Boolean, default: false },
    clueId: { type: [Number, String], default: null },
  },
  data() {
    return { loading: false, detail: {}, previewVisible: false, previewLoading: false, previewUrl: "", previewName: "", previewRequestId: 0, previewFile: null, imageFallbackTried: false, clueSourceOptions, clueTypeOptions, riskLevelOptions, reviewOptions, transferStatusOptions, feedbackStatusOptions };
  },
  computed: {
    drawerVisible: {
      get() { return this.value; },
      set(value) { this.$emit("input", value); },
    },
    processActive() {
      if (this.detail.feedbackStatus === 1) return 4;
      if (this.detail.transferStatus === 1) return 3;
      if (this.detail.reviewStatus === 2) return 2;
      return this.detail.reviewStatus === 0 ? 0 : 1;
    },
    reviewStepTitle() {
      if (this.detail.reviewStatus === 2) return "复核通过";
      if (this.detail.reviewStatus === 3) return "退回补正";
      return "线索复核";
    },
    reviewStepDescription() {
      const list = this.detail.reviewRecords || [];
      return list.length ? list[list.length - 1].reviewTime : (this.detail.reviewStatus === 0 ? "尚未提交" : "待PC端复核");
    },
    latestFeedbackTime() {
      const list = this.detail.feedbackRecords || [];
      return list.length ? list[list.length - 1].feedbackTime : "待外部反馈";
    },
  },
  watch: {
    value(visible) { if (visible && this.clueId) this.loadDetail(); },
  },
  methods: {
    optionLabel,
    async loadDetail() {
      this.loading = true;
      try {
        const res = await service.riskClueDetail(this.clueId);
        this.detail = res.data || {};
      } finally {
        this.loading = false;
      }
    },
    filesOfType(type) { return (this.detail.files || []).filter(item => item.fileType === type); },
    async openFile(file, useDirectUrl = true) {
      const name = file.originalName || file.objectName || "文件";
      const requestId = ++this.previewRequestId;
      this.releasePreviewUrl(false);
      this.previewName = name;
      this.previewFile = file;
      this.previewLoading = true;
      this.previewVisible = true;
      const directUrl = useDirectUrl && (this.isImageFile(name) || this.isPdfFile(name) || this.isOfficePreviewFile(name))
        ? file.fileUrl
        : "";
      this.imageFallbackTried = !directUrl;
      if (directUrl) {
        this.previewUrl = directUrl;
        this.previewLoading = false;
        return;
      }
      try {
        const data = await service.downloadFile(file.objectName);
        if (requestId !== this.previewRequestId || !this.previewVisible) return;
        const blob = this.normalizeBlob(data, name);
        const url = URL.createObjectURL(blob);
        if (this.isImageFile(name) || this.isPdfFile(name) || this.isOfficePreviewFile(name) || ["doc", "xls"].includes(this.extension(name))) {
          this.previewUrl = url;
        } else {
          this.previewVisible = false;
          this.downloadBlob(url, name);
        }
      } catch (error) {
        if (requestId === this.previewRequestId) { this.previewVisible = false; this.$message.error("文件处理失败"); }
      } finally {
        if (requestId === this.previewRequestId) this.previewLoading = false;
      }
    },
    extension(name) { const index = String(name || "").lastIndexOf("."); return index < 0 ? "" : String(name).substring(index + 1).toLowerCase(); },
    isImageFile(name) { return ["jpg", "jpeg", "png", "gif", "bmp", "webp"].includes(this.extension(name)); },
    isPdfFile(name) { return this.extension(name) === "pdf"; },
    isOfficePreviewFile(name) { return ["docx", "xlsx"].includes(this.extension(name)); },
    handlePreviewFileError(failedSource) {
      if (!this.previewVisible) return;
      if (typeof failedSource === "string" && failedSource !== this.previewUrl) return;
      if (this.imageFallbackTried || !this.previewFile) {
        if (this.isImageFile(this.previewName)) this.$message.error("图片加载失败");
        return;
      }
      const file = this.previewFile;
      this.openFile(file, false);
    },
    downloadCurrent() { if (this.previewUrl) { const link=document.createElement("a"); link.href=this.previewUrl; link.download=this.previewName; link.click(); } },
    normalizeBlob(data, name) {
      const source = data instanceof Blob ? data : data && data.data instanceof Blob ? data.data : new Blob([data]);
      const extension = this.extension(name);
      const mime = this.isImageFile(name) ? `image/${extension === "jpg" ? "jpeg" : extension}` : extension === "pdf" ? "application/pdf" : "application/octet-stream";
      return new Blob([source], { type: mime });
    },
    downloadBlob(url, name) { const link = document.createElement("a"); link.href = url; link.download = name; link.click(); setTimeout(() => URL.revokeObjectURL(url), 1000); },
    releasePreviewUrl(cancelRequest = true) { if (cancelRequest) this.previewRequestId += 1; if (this.previewUrl && this.previewUrl.indexOf("blob:") === 0) URL.revokeObjectURL(this.previewUrl); this.previewUrl = ""; this.previewFile = null; this.imageFallbackTried = false; this.previewLoading = false; },
    downloadUrl(objectName) { return process.env.VUE_APP_BASE_API + "/system/minio/download?fileName=" + encodeURIComponent(objectName || ""); },
    reset() { this.releasePreviewUrl(); this.detail = {}; },
  },
};
</script>

<style scoped lang="scss">
.detail-body { min-height: 100%; padding: 0 4px 32px; }
.detail-card { padding: 20px 24px; margin-bottom: 16px; border: 1px solid #ebeef5; border-radius: 8px; background: #fff; transition: box-shadow .2s; }
.detail-card:hover { box-shadow: 0 2px 12px rgba(0, 0, 0, .08); }
.section-title { position: relative; padding-left: 10px; margin-bottom: 18px; color: #303133; font-size: 14px; font-weight: 600; }
.section-title::before { content: ""; position: absolute; left: 0; top: 2px; width: 3px; height: 17px; border-radius: 2px; background: #409eff; }
.orange-title::before { background: #e6a23c; }.green-title::before { background: #67c23a; }.purple-title::before { background: #8b5cf6; }
.process-card ::v-deep .el-step__title { font-size: 13px; }.process-card ::v-deep .el-step__description { font-size: 11px; }
.description-box { padding: 12px; border-radius: 6px; background: #f8f9fb; line-height: 1.8; white-space: pre-wrap; }
.history-item { padding: 10px 0; border-bottom: 1px solid #ebeef5; }.history-item:last-child { border-bottom: 0; }.history-item span { float: right; color: #909399; font-size: 12px; }.history-item p { margin: 7px 0 0; color: #606266; }
.file-group + .file-group { margin-top: 14px; }.file-label { margin-bottom: 8px; color: #909399; }.file-link-item { padding:10px 12px; margin-bottom:8px; border-radius:4px; background:#f5f7fa; transition:background .2s,transform .15s; }.file-link-item:hover { background:#ecf5ff; transform:translateX(2px); }.file-link-item .el-link { font-size:13px; }.file-link-item .el-link i { margin-right:6px; }
.muted { color: #909399; }
.preview-body { min-height:200px; }.preview-image { display:block; max-width:100%; max-height:70vh; margin:0 auto; border-radius:4px; box-shadow:0 4px 16px rgba(0,0,0,.12); }.preview-pdf { width:100%; height:72vh; border:0; }
.preview-download { float:right; margin-right:28px; padding:0; }.preview-fallback { padding:48px 24px; text-align:center; }.preview-fallback-icon { margin-bottom:16px; color:#dcdfe6; font-size:64px; }.preview-fallback-name { margin:0 0 8px; color:#303133; font-size:14px; word-break:break-all; }.preview-fallback-tip { margin:0 0 24px; color:#909399; font-size:13px; }
.clue-detail-drawer ::v-deep .el-drawer__header { padding: 16px 24px; margin-bottom: 0; border-bottom: 1px solid #ebeef5; color: #303133; font-size: 16px; font-weight: 600; }
.clue-detail-drawer ::v-deep .el-drawer__body { padding: 16px 24px; overflow-y: auto; background: #f5f7fa; }
.clue-detail-drawer ::v-deep .el-descriptions-item__label { color: #909399; }
</style>
