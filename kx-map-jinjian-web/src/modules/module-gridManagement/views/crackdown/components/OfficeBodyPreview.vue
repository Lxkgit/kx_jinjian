<template>
  <div v-loading="loading" class="office-body-preview">
    <vue-office-docx v-if="type === 'docx'" :src="url" @rendered="handleRendered" @error="handleError" />
    <vue-office-excel v-else-if="type === 'xlsx'" :src="url" @rendered="handleRendered" @error="handleError" />
    <div v-if="errorMessage" class="preview-message">{{ errorMessage }}</div>
  </div>
</template>
<script>
import VueOfficeDocx from "@vue-office/docx";
import VueOfficeExcel from "@vue-office/excel";
import "@vue-office/docx/lib/index.css";
import "@vue-office/excel/lib/index.css";
export default {
  name: "OfficeBodyPreview", components: { VueOfficeDocx, VueOfficeExcel },
  props: { url: { type:String, default:"" }, type: { type:String, default:"" } },
  data() { return { loading:true, errorMessage:"" }; },
  watch: { url() { this.loading = true; this.errorMessage = ""; } },
  methods: {
    handleRendered() { this.loading = false; this.errorMessage = ""; },
    handleError() { this.loading=false; this.errorMessage="文件预览失败，请下载后查看"; this.$emit("error", this.url); },
  },
};
</script>
<style scoped>.office-body-preview{min-height:300px;max-height:72vh;overflow:auto;background:#f2f3f5}.preview-message{padding:100px 20px;text-align:center;color:#909399}</style>
