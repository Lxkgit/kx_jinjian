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
              ref="btnBar"
            >
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
        :allColumns="columns"
        :total="total"
        border
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

    <importService v-model="showImport"></importService>
  </PageContent>
</template>
  
  <script>
import pageMixin from "@/mixins/pageMixin";
import service from "../api";
import importService from "../components/importService.vue";
import optionMixin from "@/mixins/optionMixin";
const CASE_OPT = [
  { value: "1", label: "洽谈中" },
  { value: "2", label: "建设中" },
  { value: "3", label: "运行中" },
];
export default {
  mixins: [pageMixin, optionMixin],
  components: {
    importService,
  },
  data() {
    return {
      searchColumns: [
        {
          label: "现金服务点责任银行",
          key: "serveBank1",
          type: "text",
          placeholder: "请输入责任银行",
        },
        {
          label: "现金服务点责任网点",
          key: "serveBank",
          type: "text",
          placeholder: "请输入责任网点名称",
        },
        {
          label: "服务点名称",
          key: "servePointName",
          type: "text",
        },
        {
          label: "服务点类别",
          key: "serviceType",
          type: "select",
          options: [
            { value: "交通枢纽", label: "交通枢纽" },
            { value: "出租车", label: "出租车" },
            { value: "酒店", label: "酒店" },
            { value: "农贸市场", label: "农贸市场" },
            { value: "商场（综合体）", label: "商场（综合体）" },
            { value: "特色街区", label: "特色街区" },
            { value: "文化旅游", label: "文化旅游" },
            { value: "医疗卫生", label: "医疗卫生" },
            { value: "文化市场", label: "文化市场" },
          ],
        },
        {
          label: "建设模式",
          key: "constructingMode",
          type: "select",
          clearable:true,
          options: [
            {
              value: "自建",
              label: "自建",
            },
            {
              value: "合建",
              label: "合建",
            },
            {
              value: "待定",
              label: "待定",
            },
          ],
        },
        {
          label: "推进情况",
          key: "constructingCase",
          type: "select",
          clearable:true,
          options: CASE_OPT,
        },
        {
          label: "所在地区",
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
      maxHeight: window.innerHeight - 440,
      btnLists: [
        {
          cname: "服务点导入",
          ename: "importHandle",
        },
        {
          cname: "服务点导出",
          ename: "exportHandle",
        },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
      ],
      columns: [
        { label: "现金服务点责任银行", prop: "serveBank" },
        { label: "现金服务点责任网点", prop: "serveAnchor" },
        { label: "现金服务点责任网点地址", prop: "serveAnchorAddr",showOverFlowTooltip:true },
        { label: "服务点类别", prop: "serviceType" },
        { label: "服务点名称", prop: "servePointName",showOverFlowTooltip:true },
        { label: "服务点地址", prop: "servePointAddr" ,showOverFlowTooltip:true},
        { label: "选点位置", prop: "chosenPosition" },
        { label: "现金服务点责任人", prop: "dutyName" },
        { label: "联系电话", prop: "dutyPhone",width:'120px' },
        { label: "建设模式", prop: "constructingMode" },
        {
          label: "推进情况",
          prop: "constructingCase",
          formatter: ({constructingCase}) => CASE_OPT.find((item) => item.value == constructingCase)?.label,},
        { label: "计划启动时间", prop: "startTime" },
        { label: "服务模式", prop: "serviceMode",showOverFlowTooltip:true },
        { label: "是否布放小面额现金自助兑换设备", prop: "publishingDevice", },
        { label: "所在网格", prop: "gridName" },
        { label: "操作", slot: true },
      ],
      showEdit: false,
      status: "add",
      showImport: false,
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.loading = true;
        service.importBatch(param).then((res) => {
          const { total, row } = res;
          this.tableData = row;
          this.total = total;
          this.loading = false;
        });
      });
    },
    importHandle() {
      this.showImport = true;
    },
    exportHandle() {
      this.makeSure('确认导出吗', () => {
        this.$refs.filterBar.getForm((formInfo) => {
          const param = {
            ...formInfo,
          }
          service.exportBatch(param).then(res => {
            const blob = new Blob([res], { type: "application/vnd.ms-excel" });
            const fileName = "现金服务点数据列表.xlsx"; //下载文件名称
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
        name:'CashServiceDetail'
      })
    },
    editHandle(data) {
      this.$router.push({
        name:'CashServiceDetail'
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
  height: 172px;
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
  height: calc(100vh - 64px - 56px - 172px - 68px - 10px);
  background: #fff;
  padding: 0 16px;
  padding-bottom: 10px;
}
</style>