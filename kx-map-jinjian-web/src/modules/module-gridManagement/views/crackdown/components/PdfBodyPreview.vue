<template>
  <div v-loading="loading" class="pdf-body-preview">
    <pdf v-for="page in pageCount" :key="page" :src="source" :page="page" class="pdf-page" />
    <div v-if="!loading && errorMessage" class="pdf-error">{{ errorMessage }}</div>
  </div>
</template>

<script>
import pdf from "vue-pdf";

export default {
  name: "PdfBodyPreview",
  components: { pdf },
  props: { url: { type: String, default: "" } },
  data() { return { source: null, pageCount: 0, loading: false, errorMessage: "", loadVersion: 0 }; },
  watch: { url: { immediate: true, handler() { this.load(); } } },
  beforeDestroy() { this.loadVersion += 1; this.destroySource(); },
  methods: {
    load() {
      const version = ++this.loadVersion;
      this.destroySource();
      this.pageCount = 0;
      this.errorMessage = "";
      if (!this.url) return;
      const sourceUrl = this.url;
      this.loading = true;
      const task = pdf.createLoadingTask(sourceUrl);
      this.source = task;
      task.promise.then((document) => {
        if (version === this.loadVersion) this.pageCount = document.numPages;
      }).catch(() => {
        if (version !== this.loadVersion) return;
        this.errorMessage = "PDF加载失败";
        this.$emit("error", sourceUrl);
      }).finally(() => {
        if (version === this.loadVersion) this.loading = false;
      });
    },
    destroySource() { if (this.source && this.source.destroy) this.source.destroy(); this.source = null; },
  },
};
</script>

<style scoped>
.pdf-body-preview { min-height:240px; padding:12px; overflow:auto; background:#e9eaec; }
.pdf-page { display:block; max-width:100%; margin:0 auto 12px; box-shadow:0 1px 6px rgba(0,0,0,.18); }
.pdf-page:last-child { margin-bottom:0; }.pdf-error { padding:80px 0; text-align:center; color:#909399; }
</style>
