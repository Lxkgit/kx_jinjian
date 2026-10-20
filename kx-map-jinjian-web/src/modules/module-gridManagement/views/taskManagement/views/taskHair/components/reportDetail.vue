<template>
  <i-dialog
    title="上报详情"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <i-form
      :gridColumns="2"
      ref="firstForm"
      :columns="columns"
      label-width="170px"
      label-position="right"
    />
  </i-dialog>
</template>
  
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  name: "addForm",
  mixins: [dialogMixin, pageMixin],
  props: {},
  data() {
    return {
      columns: [
        {
          label: "小面额机具数",
          key: "num1",
          type: "text",
          readonly: true,
        },
        {
          label: "弹性人工柜台数",
          key: "num2",
          type: "text",
          readonly: true,
        },
        {
          label: "具备小面额功能机具数",
          key: "num3",
          type: "text",
          readonly: true,
        },
        {
          label: "自助现金机具数",
          key: "num4",
          type: "text",
          readonly: true,
        },
        {
          label: "硬币兑换机数量",
          key: "num5",
          type: "text",
          readonly: true,
        },
        {
          label: "办理人工现金业务网点数",
          key: "num6",
          type: "text",
          readonly: true,
        },
      ],
      formInfo: {
        num4: "9",
        num3: "6",
        num2: "5",
        num1: "5",
        num5: "5",
        num6: "5",
      },
    };
  },
  computed: {},
  watch: {},
  methods: {
    initData() {
      this.$nextTick(() => [this.$refs.firstForm.setForm(this.formInfo)]);
    },
  },
};
</script>
  
<style scoped lang="scss">
</style>
  