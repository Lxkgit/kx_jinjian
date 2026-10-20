<template>
  <div>
    <PageContent>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
      <!-- 编辑弹窗 -->
      <edit-config
        v-model="showDialog"
        :defaultValue="defaultValue"
        @fetchList="fetchList()"
      ></edit-config>
    </PageContent>
  </div>
</template>

<script>
import services from "../api";
import pageMixin from "@/mixins/pageMixin.vue";
import EditConfig from "./editConfig.vue";
export default {
  components: { EditConfig },
  mixins: [pageMixin],
  data() {
    return {
      columns: [
        { label: "动作", prop: "action" },
        { label: "积分规则", prop: "integralRule" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
        },
      ],
      tableData: [{}],
      defaultValue: {},
      showDialog: false,
    };
  },
  mounted() {
    // this.fetchList();
  },
  methods: {
    // 编辑
    handleEdit(row) {
      this.showDialog = true;
    },
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
