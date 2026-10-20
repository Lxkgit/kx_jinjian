<template>
  <PageContent>
    <section class="onSiteTemplateList-search">
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        :buttonSpan="3"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
    </section>

    <section class="onSiteTemplateList-content">
      <tableTopbar
        leftTitle="数据明细"
        buttonText="新增"
        @click="clickHandle('add')"
        style="margin-bottom: 16px"
      />
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :showPagination="false"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
        <template #b="{ row }">
          <el-switch
            v-model="row.b"
            active-color="#7789FF"
            inactive-color="#C2C4D6"
          />
        </template>
      </i-table>
    </section>
  </PageContent>
</template>
<script>
import tableTopbar from "../components/tableTopbar.vue";
export default {
  name: "onSiteTemplateList",
  components: { tableTopbar },
  data() {
    return {
      searchColumns: [
        {
          label: "模板名称",
          key: "name",
          type: "text",
        },
      ],
      tableData: [
        { a: "走访任务", b: true, c: "刘华", d: "2021-11-29 13:00" },
        { a: "协同任务", b: false, c: "张梅", d: "2021-11-29 13:00" },
        { a: "预警任务", b: true, c: "刘华", d: "2021-11-29 13:00" },
        { a: "协同任务", b: false, c: "张梅", d: "2021-11-29 13:00" },
      ],
      columns: [
        {
          label: "模板名称",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "使用状态",
          prop: "b",
          slot: true,
        },
        {
          label: "创建人",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "创建时间", showOverFlowTooltip: true, prop: "d" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "edit",
        },
        {
          cname: "删除",
          ename: "delete",
          danger: true,
        },
      ],
    };
  },
  methods: {
    openDialog(type, item) {
      console.log("执行啦");
    },
    resetHandle() {
      // Reset search fields
      console.log("Reset search fields");
    },
    clickHandle(type, row) {
      switch (type) {
        case "add":
          this.$router.push(
            "/evaluate/onSiteTemplate/onSiteTemplateListDetails"
          );
          break;
        case "edit":
          this.$router.push({
            path: "/evaluate/onSiteTemplate/onSiteTemplateListDetails",
            query: {
              id: row.d,
            },
          });
          break;
        case "delete":
          let index = this.tableData.findIndex((item) => item.a == row.a);
          this.tableData.splice(index, 1);
          break;
        default:
          break;
      }
      console.log(type, row);
    },
  },
};
</script>
<style lang="scss" scoped>
.onSiteTemplateList {
  &-search {
    padding: 16px;
    background: #fff;
    margin-bottom: 12px;
    ::v-deep .filter-button-wrapper {
      text-align: right;
    }
  }
  &-content {
    padding: 24px 16px;
    background: #fff;
    ::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
      border-bottom: 0.5px solid #ebeef5;
    }
  }
}
</style>
