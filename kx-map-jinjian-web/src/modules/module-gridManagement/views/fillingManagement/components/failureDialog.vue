<template>
  <i-dialog :title="'失败原因'" v-model="showDialog" width="40%" :showFooter="false"
            @handleClose="handleClose">
    <div v-html="failureReason"></div>
  </i-dialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  mixins: [dialogMixin],
  props: {
    reason: {
      type: String,
    },
  },
  data() {
    return {
      failureReason: "",
    };
  },
  watch: {
    reason: {
      handler(newVal) {
        if (newVal) {
          this.failureReason = newVal.replace(/\\n/g, '<br>');
        }
      },
    },
  },
  methods: {
    handleClose() {
      this.showDialog = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.fail-content {
  white-space: pre-wrap;
}
</style>