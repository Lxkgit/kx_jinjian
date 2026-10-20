<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="3"
        label-width="120px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 数据列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight"
        align="center"
        stripe
        showIndex
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
      <stats-detail
        v-model="showDialog"
        :defaultValue="defaultValue"
        @fetchList="fetchList()"
      ></stats-detail>
    </PageContent>
  </div>
</template>

<script>
import statsDetail from "./statsDetail.vue";
import services from "../api";
import pageMixin from "@/mixins/pageMixin.vue";
export default {
  components: { statsDetail },
  mixins: [pageMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "客户经理姓名",
          key: "taskTypeName",
          type: "text",
          placeholder: "请输入客户经理姓名",
        },
      ],
      searchBtn: [
        {
          icon: "el-icon-search",
          cname: "搜索",
          ename: "search",
          type: "primary",
        },
        {
          icon: "el-icon-refresh",
          cname: "重置",
          ename: "reset",
        },
      ],
      columns: [
        { label: "客户经理姓名", prop: "taskTypeName", width: 120 },
        { label: "客户经理工号", prop: "taskObjectId", width: 120 },
        { label: "所属团队", prop: "createBy", width: 120 },
        { label: "所属一级支行", prop: "createDate", width: 120 },
        { label: "所属二级支行", prop: "updateDate", width: 120 },
        { label: "所属分行", prop: "updateDate", width: 120 },
        { label: "本年度累计积分", prop: "updateDate", width: 120 },
        { label: "近一年累计积分", prop: "updateDate", width: 120 },
        { label: "累计积分", prop: "updateDate", width: 120 },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "查看明细",
          ename: "handleDetail",
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
    handleDetail(row) {
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
