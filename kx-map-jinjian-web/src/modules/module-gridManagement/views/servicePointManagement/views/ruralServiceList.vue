<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
      ref="filterBar"
      slot="filter"
      :columns="searchColumns"
      class="filterBar"
      :gridColumns="3"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

     <!-- 列表 -->
     <table-title title="现金服务点列表" class="table-title">
      <template #right>
          <!-- 按钮 -->
          <i-button-bar
            style="margin-bottom: 0;"
            :btn-lists="btnLists"
            @click="clickHandle"
            slot="btn"
            ref="btnBar">
          </i-button-bar>
      </template>
    </table-title>

    <div class="i-table">
      <i-table
      ref="table"
      :loading="loading"
      :data="tableData"
      align="center"
      stripe
      border
      :allColumns="columns"
      :total="total"
      :pageNum="formInline.pageNum"
      :maxHeight="maxHeight"
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
    </div>
   
  </PageContent>
</template>
  
  <script>
import pageMixin from "@/mixins/pageMixin";
import service from "../api";
import optionMixin from "@/mixins/optionMixin";
export default {
  mixins: [pageMixin, optionMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "服务点名称",
          key: "servePointName",
          type: "text",
        },
        {
          label: "所在网格",
          key: "belongGridCodeList",
          value: "",
          type: "cascader",
          clearable: true,
          filterable: true,
          collapseTags: true,
          showAllLevels: false,
          props: {
            value: "code",
            label: "name",
            emitPath: false,
            multiple: true,
            lazy: true,
            lazyLoad: this.lazyLoad,
            checkStrictly: true,
          },
          events: {
            change: true,
          },
        },
      ],
      maxHeight: window.innerHeight - 340,
      btnLists: [
        {
          cname: "服务站导出",
          ename: "exportHandle",
        },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
          permi: "gridManagement:systemManage:view",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
      ],
      columns: [
        {
          label: "服务站名称",
          prop: "servePointName",
          showOverFlowTooltip: true,
          width: 300,
        },
        {
          label: "具体地址",
          prop: "servePointAddr",
          showOverFlowTooltip: true,
          width: 300,
        },
        {
          label: "站长姓名",
          prop: "dutyName",
          showOverFlowTooltip: true,
        },
        { label: "联系电话", prop: "dutyPhone" },
        { label: "所在网格", prop: "gridName" },
        { label: "操作", slot: true },
      ],
      tableData: [
        {
          servePointName: "金东区傅村镇石狮塘村农村金融服务站",
          servePointAddr: "金东区傅村镇石狮塘村",
          servePointManager: "张明",
          dutyPhone: "1345654325",
          gridName: "石狮塘村",
        },
      ],
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const data = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.loading = true;
        console.log("data", data);
        service
          .villageFinancialList(data)
          .then((res) => {
            const { total, rows } = res;
            this.tableData = rows;
            this.total = total;
            this.loading = false;
          })
          .catch((err) => {
            this.tableData = [];
            this.loading = false;
          });
      });
    },
    exportHandle() {
      this.makeSure('确认导出吗', () => {
        this.$refs.filterBar.getForm((formInfo) => {
          const param = {
            ...formInfo,
          }
          service.exportvillageFinancial(param).then(res => {
            const blob = new Blob([res], { type: "application/vnd.ms-excel" });
            const fileName = "农村金融服务站数据列表.xlsx"; //下载文件名称
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink);
          })
        })
      })
    },
    viewHandle(data) {
      this.$router.push({
        name:'RuralServiceDetail'
      })
    },
    editHandle(data) {
      this.$router.push({
        name:'RuralServiceDetail'
      })
    },
  },
};
</script>
  
  <style lang="scss" scoped>
   .filterBar-box {
    margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 85%;
  }
}
.table-title {
  padding: 16px;
  height: 68px;
}
.i-table {
  height: calc(100vh - 64px - 56px - 68px - 68px - 10px);
  background: #fff;
  padding: 0 16px;
  padding-bottom: 10px;
}
</style>