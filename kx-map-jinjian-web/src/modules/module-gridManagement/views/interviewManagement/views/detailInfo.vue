<template>
  <div>
    <i-dialog title="走访详细信息" v-model="showDialog" width="80%" @handleClose="handleClose"
              :showFooter="true">
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
      </i-button-bar>
      <i-table :loading="loading" :data="tableData" align="center" stripe showIndex
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
      </i-table>
    </i-dialog>
    <exportDialog v-model="showExport" :dataInfo="dataInfo"
                  edportUrl="system/customerVisit/exportManagerCode" fileName="走访详情" />
  </div>

</template>

<script>
import dialogMixin from '@/mixins/dialogMixin'
import pageMixin from "@/mixins/pageMixin";
import service from "../api";
import exportDialog from '../components/exportDialog'
export default {
  name: "detailInfo",
  mixins: [dialogMixin, pageMixin],
  components: {
    exportDialog
  },
  props: {
    editInfo: {
      type: Object
    }
  },
  data() {
    return {
      size: "mini",
      columns: [
        { label: "走访人", prop: "visitsManager" },
        { label: "走访人工号", prop: "visitsManagerCode" },
        { label: "客户名称", prop: "customerName" },
        {
          label: "走访时间", prop: "visitsTime"
        },
        { label: "走访地点", prop: "visitsAddress" },
      ],
      btnLists: [
        {
          cname: "导出",
          ename: "exportHandle",
          type: "primary",
        },
      ],
      showExport: false,
      dataInfo: {},
    };
  },
  methods: {
    initData() {
      this.fetchList()
    },
    // 名单列表
    fetchList() {
      const { visitsManagerCode } = this.editInfo
      const param = {
        ...this.formInline,
        visitsManagerCode
      };
      this.loading = true
      service.listByManagerCode(param)
        .then((res) => {
          this.tableData = res.rows;
          this.total = res.total;
          this.loading = false;
        })
        .catch((err) => {
          if (err) {
            this.tableData = [];
            this.total = 0;
          }
          this.loading = false;
        });
    },
    exportHandle() {
      const { visitsManagerCode } = this.editInfo
      const param = {
        ...this.formInline,
        visitsManagerCode
      };
      this.dataInfo = param
      this.showExport = true
    }
  },
};
</script>

<style scoped lang="scss"></style>
