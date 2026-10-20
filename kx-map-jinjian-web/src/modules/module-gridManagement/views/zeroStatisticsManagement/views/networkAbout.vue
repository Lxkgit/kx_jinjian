<template>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <!-- <i-button-bar
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
      >
      </i-button-bar> -->
      <!-- 列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
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
      <rural-financial-point-detail
        v-model="showDetail"
        :info="info"
      ></rural-financial-point-detail>
    </PageContent>
  </template>
    
    <script>
  import pageMixin from "@/mixins/pageMixin";
  import optionMixin from "@/mixins/optionMixin";
  export default {
    mixins: [pageMixin, optionMixin],
    data() {
      return {
        searchColumns: [
          {
            label: "客户名称",
            key: "servePointName",
            type: "text",
          },
        ],
        maxHeight: window.innerHeight - 270,
        btnLists: [
          {
            cname: "服务站导出",
            ename: "exportHandle",
            type: "primary",
          },
        ],
        tableBtn: [
          {
            cname: "详情",
            ename: "viewHandle",
            permi: "gridManagement:systemManage:view",
          },
        ],
        columns: [
          {
            label: "客户名称",
            prop: "servePointName",
            showOverFlowTooltip: true,
            width: 300,
          },
          {
            label: "零钞券别",
            prop: "servePointAddr",
            showOverFlowTooltip: true,
            width: 300,
          },
          {
            label: "预约数量",
            prop: "dutyName",
            showOverFlowTooltip: true,
          },
          { label: "是否预约兑换“零钱包”", prop: "dutyPhone" },
          { label: "是否选择“大篷车小钱箱”现场兑换", prop: "gridName" },
          { label: "是否上门配送", prop: "gridName" },
          { label: "操作", slot: true },
        ],
        tableData: [{
          servePointName:'-',
          servePointAddr:'-',
          dutyPhone:'-',
          gridName:'-',
          dutyName:'-',
        }],
        info: {},
        showDetail: false,
      };
    },
    mounted() {
    //   this.fetchList();
    },
    methods: {
      fetchList() {
        
      },
      viewHandle(data) {
      },
    },
  };
  </script>
    
    <style lang="scss" scoped>
  </style>