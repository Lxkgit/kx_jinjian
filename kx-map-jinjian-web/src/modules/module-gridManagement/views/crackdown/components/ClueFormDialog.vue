<template>
  <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="920px" top="3vh" append-to-body :close-on-click-modal="false" class="clue-form-dialog" @closed="reset">
    <div class="dialog-scroll" v-loading="loading">
      <el-form ref="clueForm" :model="form" :rules="rules" label-position="top" size="small">
        <div class="form-section-title">线索信息</div>
        <el-row :gutter="14">
          <el-col :span="12">
            <el-form-item label="线索类型" prop="clueType">
              <el-radio-group v-model="form.clueType" class="radio-wrap">
                <el-radio v-for="item in clueTypeOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="风险等级" prop="riskLevel">
              <el-select v-model="form.riskLevel" placeholder="请选择风险等级" style="width: 100%">
                <el-option v-for="item in riskLevelOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="线索来源" prop="clueSource">
          <el-radio-group v-model="form.clueSource" class="source-radio-wrap">
            <el-radio v-for="item in clueSourceOptions" :key="item.value" :label="item.value">{{ item.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="线索标题" prop="clueTitle"><el-input v-model.trim="form.clueTitle" maxlength="200" placeholder="请输入线索标题" /></el-form-item>
        <el-form-item label="线索描述" prop="clueDescription"><el-input v-model.trim="form.clueDescription" type="textarea" :rows="4" maxlength="1000" show-word-limit placeholder="请输入线索描述（至少10个字符）" /></el-form-item>
        <div class="form-section-title section-orange">涉险主体</div>
        <el-row :gutter="14">
          <el-col :span="12"><el-form-item label="涉险主体名称" prop="riskSubject"><el-input v-model.trim="form.riskSubject" maxlength="200" placeholder="请输入涉险主体名称" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="统一社会信用代码"><el-input v-model.trim="form.unifiedSocialCreditCode" maxlength="32" placeholder="请输入统一社会信用代码" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="涉险主体地址" prop="subjectAddress"><el-input v-model.trim="form.subjectAddress" maxlength="500" placeholder="请输入涉险主体地址" @blur="syncLocationAddress" /></el-form-item>
        <el-row :gutter="14">
          <el-col :span="12"><el-form-item label="联系人"><el-input v-model.trim="form.contactName" maxlength="100" placeholder="请输入联系人姓名" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="联系电话"><el-input v-model.trim="form.contactPhone" maxlength="32" placeholder="请输入联系电话" /></el-form-item></el-col>
        </el-row>
        <el-form-item label="定位信息"><el-input v-model.trim="form.locationAddress" maxlength="500" placeholder="请输入定位信息，默认带出主体地址" /></el-form-item>
        <el-row :gutter="14">
          <el-col :span="12"><el-form-item label="所属行政区"><el-input v-model.trim="form.areaFullName" maxlength="500" placeholder="请输入所属行政区" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="所属街道"><el-input v-model.trim="form.streetName" maxlength="100" placeholder="请输入所属街道" /></el-form-item></el-col>
        </el-row>
        <div class="form-section-title section-green">材料信息</div>
        <el-form-item label="现场照片" prop="photoFiles">
          <el-upload multiple list-type="picture-card" accept=".jpg,.jpeg,.png" :action="upload.action" :headers="upload.headers" :file-list="photoFileList" :limit="9" :before-upload="beforePhotoUpload" :on-success="handlePhotoSuccess" :on-remove="handlePhotoRemove" :on-preview="previewUploadFile" :on-error="handleUploadError">
            <i class="el-icon-plus" /><div slot="tip" class="el-upload__tip">至少上传1张，支持 JPG、PNG，单张不超过20MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="附件材料">
          <el-upload multiple accept=".jpg,.jpeg,.png,.pdf,.doc,.docx,.xls,.xlsx,.zip" :action="upload.action" :headers="upload.headers" :file-list="attachmentFileList" :limit="10" :before-upload="beforeAttachmentUpload" :on-success="handleAttachmentSuccess" :on-remove="handleAttachmentRemove" :on-preview="previewUploadFile" :on-error="handleUploadError">
            <el-button size="small" icon="el-icon-upload2">上传附件</el-button><div slot="tip" class="el-upload__tip">支持图片、PDF、Word、Excel、ZIP，单个不超过50MB</div>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
    <span slot="footer"><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" :loading="submitting" @click="submit">确定</el-button></span>

    <el-dialog title="图片预览" :visible.sync="previewVisible" append-to-body width="720px"><img :src="previewUrl" class="preview-image"></el-dialog>
  </el-dialog>
</template>

<script>
import { getToken } from "@/utils/auth";
import service from "../api";
import { clueSourceOptions, clueTypeOptions, riskLevelOptions } from "../config/clue";

export default {
  name: "ClueFormDialog",
  props: {
    value: { type: Boolean, default: false },
    mode: { type: String, default: "create" },
    clueId: { type: [Number, String], default: null },
  },
  data() {
    return {
      loading: false,
      submitting: false,
      previewVisible: false,
      previewUrl: "",
      form: this.emptyForm(),
      photoFileList: [],
      attachmentFileList: [],
      upload: { action: process.env.VUE_APP_BASE_API + "/system/minio/upload", headers: { Authorization: "Bearer " + getToken() } },
      clueSourceOptions,
      clueTypeOptions,
      riskLevelOptions,
      rules: {
        clueType: [{ required: true, message: "请选择线索类型", trigger: "change" }],
        riskLevel: [{ required: true, message: "请选择风险等级", trigger: "change" }],
        clueSource: [{ required: true, message: "请选择线索来源", trigger: "change" }],
        clueTitle: [{ required: true, message: "请输入线索标题", trigger: "blur" }],
        riskSubject: [{ required: true, message: "请输入涉险主体名称", trigger: "blur" }],
        clueDescription: [{ required: true, validator: (rule, value, callback) => value && value.trim().length >= 10 ? callback() : callback(new Error("线索描述不能少于10个字符")), trigger: "blur" }],
        subjectAddress: [{ required: true, message: "请输入涉险主体地址", trigger: "blur" }],
      },
    };
  },
  computed: {
    dialogVisible: {
      get() { return this.value; },
      set(value) { this.$emit("input", value); },
    },
    dialogTitle() {
      if (this.mode === "resubmit") return "补正重提";
      return this.mode === "edit" ? "修改线索" : "新增线索上报";
    },
  },
  watch: {
    value(visible) {
      if (!visible) return;
      if (this.mode === "create") {
        this.reset();
        this.clearValidate();
      } else {
        this.loadDetail();
      }
    },
  },
  methods: {
    emptyForm() {
      return { id: null, clueSource: null, clueTitle: "", clueType: null, riskSubject: "", unifiedSocialCreditCode: "", clueDescription: "", subjectAddress: "", contactName: "", contactPhone: "", locationAddress: "", longitude: null, latitude: null, coordinateSystem: "GCJ02", riskLevel: null, areaCode: "", areaFullName: "", streetCode: "", streetName: "", files: [] };
    },
    async loadDetail() {
      this.loading = true;
      try {
        const res = await service.riskClueDetail(this.clueId);
        const data = res.data || {};
        this.form = { ...this.emptyForm(), ...data };
        this.photoFileList = this.toUploadList((data.files || []).filter(item => item.fileType === 1));
        this.attachmentFileList = this.toUploadList((data.files || []).filter(item => item.fileType === 2));
        this.clearValidate();
      } finally {
        this.loading = false;
      }
    },
    submit() {
      this.$refs.clueForm.validate(async valid => {
        if (!valid) return;
        if (!this.photoFileList.length) { this.$message.warning("请至少上传一张现场照片"); return; }
        this.submitting = true;
        try {
          const data = { ...this.form, files: this.buildFiles() };
          if (this.mode === "create") await service.riskClueAdd(data);
          else if (this.mode === "resubmit") await service.riskClueResubmit(data);
          else await service.riskClueEdit(data);
          this.$message.success(this.mode === "resubmit" ? "补正重提成功" : "保存成功");
          this.dialogVisible = false;
          this.$emit("success");
        } finally {
          this.submitting = false;
        }
      });
    },
    reset() {
      this.form = this.emptyForm();
      this.photoFileList = [];
      this.attachmentFileList = [];
      this.previewVisible = false;
      this.previewUrl = "";
    },
    clearValidate() { this.$nextTick(() => this.$refs.clueForm && this.$refs.clueForm.clearValidate()); },
    syncLocationAddress() { if (!this.form.locationAddress) this.form.locationAddress = this.form.subjectAddress; },
    toUploadList(files) {
      return files.map(item => ({ name: item.originalName, url: item.fileUrl || this.downloadUrl(item.objectName), uid: `saved-${item.id}`, status: "success", objectName: item.objectName, originalName: item.originalName, fileUrl: item.fileUrl, fileExt: item.fileExt, fileSize: item.fileSize }));
    },
    buildFiles() { return this.photoFileList.map(item => this.fileRecord(item, 1)).concat(this.attachmentFileList.map(item => this.fileRecord(item, 2))); },
    fileRecord(item, type) { return { fileType: type, originalName: item.originalName || item.name, objectName: item.objectName || (item.response && item.response.fileName), fileUrl: item.fileUrl || item.url || (item.response && item.response.url), fileExt: this.extension(item.originalName || item.name), fileSize: item.fileSize || (item.raw && item.raw.size) || null }; },
    beforePhotoUpload(file) { return this.beforeUpload(file, ["jpg", "jpeg", "png"], 20); },
    beforeAttachmentUpload(file) { return this.beforeUpload(file, ["jpg", "jpeg", "png", "pdf", "doc", "docx", "xls", "xlsx", "zip"], 50); },
    beforeUpload(file, allowed, maxMb) {
      const ext = this.extension(file.name);
      if (!allowed.includes(ext)) { this.$message.error("文件格式不支持"); return false; }
      if (file.size / 1024 / 1024 > maxMb) { this.$message.error(`文件不能超过${maxMb}MB`); return false; }
      return true;
    },
    handlePhotoSuccess(res, file) { this.photoFileList = this.afterUpload(this.photoFileList, res, file); },
    handleAttachmentSuccess(res, file) { this.attachmentFileList = this.afterUpload(this.attachmentFileList, res, file); },
    afterUpload(list, res, file) {
      if (res.code !== 200) { this.$message.error(res.msg || "上传失败"); return list.filter(item => item.uid !== file.uid); }
      return list.filter(item => item.uid !== file.uid).concat({ name: file.name, originalName: file.name, objectName: res.fileName, url: res.url, fileUrl: res.url, fileSize: file.size, uid: file.uid, status: "success" });
    },
    handlePhotoRemove(file) { this.photoFileList = this.removeUploadItem(this.photoFileList, file); },
    handleAttachmentRemove(file) { this.attachmentFileList = this.removeUploadItem(this.attachmentFileList, file); },
    removeUploadItem(list, file) { return list.filter(item => item.uid !== file.uid && item.objectName !== file.objectName); },
    handleUploadError() { this.$message.error("文件上传失败，请重试"); },
    previewUploadFile(file) {
      const url = file.url || this.downloadUrl(file.objectName);
      if (["jpg", "jpeg", "png"].includes(this.extension(file.name))) { this.previewUrl = url; this.previewVisible = true; } else window.open(url, "_blank");
    },
    downloadUrl(objectName) { return process.env.VUE_APP_BASE_API + "/system/minio/download?fileName=" + encodeURIComponent(objectName || ""); },
    extension(name) { const index = String(name || "").lastIndexOf("."); return index < 0 ? "" : String(name).substring(index + 1).toLowerCase(); },
  },
};
</script>

<style scoped lang="scss">
.dialog-scroll { max-height:72vh; overflow-x:hidden; overflow-y:auto; padding:0 4px; }
.clue-form-dialog ::v-deep .el-dialog { border-radius:8px; overflow:hidden; }
.clue-form-dialog ::v-deep .el-dialog__header { padding:16px 24px; border-bottom:1px solid #ebeef5; background:#f5f7fa; }
.clue-form-dialog ::v-deep .el-dialog__title { color:#303133; font-size:16px; font-weight:600; }
.clue-form-dialog ::v-deep .el-dialog__body { padding:24px; overflow-x:hidden; }
.clue-form-dialog ::v-deep .el-dialog__footer { padding:16px 24px; border-top:1px solid #ebeef5; }
.clue-form-dialog ::v-deep .el-form-item { margin-bottom:20px; }
.clue-form-dialog ::v-deep .el-form-item__label { color:#303133; font-weight:500; }
.form-section-title { position:relative; margin:0 0 20px; padding:0 0 12px 12px; border-bottom:1px solid #ebeef5; color:#303133; font-size:15px; font-weight:600; }
.form-section-title::before { content:""; position:absolute; left:0; top:2px; bottom:12px; width:3px; border-radius:2px; background:#1890ff; }
.form-section-title.section-orange::before { background:#faad14; }.form-section-title.section-green::before { background:#52c41a; }
.clue-form-dialog ::v-deep .el-radio { white-space: nowrap; }
.radio-wrap { display: flex; flex-wrap: wrap; }
.radio-wrap .el-radio { margin: 0 14px 8px 0; }
.source-radio-wrap { display: flex; flex-wrap: wrap; gap: 10px 0; }
.source-radio-wrap .el-radio { margin-right: 22px; }
.clue-form-dialog ::v-deep .el-upload-list--picture-card { display: flex; flex-wrap: wrap; }
.preview-image { display: block; max-width: 100%; max-height: 70vh; margin: 0 auto; }
</style>
