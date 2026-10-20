<template>
  <div>
    <PageContent>
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <failure-dialog v-model=" showDialog" :reason="reason"> </failure-dialog>
  </div>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin.vue";
import failureDialog from "../components/failureDialog.vue";
import service from '../api';
export default {
  mixins: [dialogMixin, pageMixin],
  dicts: ['export_type'],
  components: {
    failureDialog,
  },
  data() {
    return {
      tableData: [],
      maxHeight: window.innerHeight - 220,
      columns: [
        {
          label: "导入状态", prop: "importStatus", formatter: ({ importStatus }) => {
            return this.dict.label.export_type[importStatus]
          }
        },
        { label: "导入数据", prop: "importNum" },
        { label: "导入时间", prop: "createTime" },
        { label: "操作账号", prop: "userName" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "失败原因",
          ename: "showFailure",
          type: "text",
          isShow: ({ importStatus }) => importStatus == '2'
        },
      ],
      reason: "",
    };
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      this.loading = true
      service.recordList(this.formInline).then(res => {
        const { rows, total } = res
        this.tableData = rows
        this.total = total
        this.loading = false
      })
    },
    showFailure(row) {
      const { failReason } = row
      this.reason = failReason;
      this.showDialog = true;
    },
  },
};
</script>

<style lang="scss" scoped>
</style>