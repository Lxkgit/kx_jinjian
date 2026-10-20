<!-- 省-现金服务点统计 -->
<template>
    <PageContent>
        <div class="top">
            <!-- 机构标题 -->
            <orgTitleSelect title="人民银行浙江省分行-2025年">
                <template #right>
                    <el-button type="primary" @click="showDetail = true">条件切换</el-button>
                </template>
            </orgTitleSelect>
            <!-- 数据统计 -->
            <div class="top-data">
                <tableTitle title="数据统计" class="top-title"></tableTitle>
                <statisticsItem
                    class="statisticsItem"
                    height="92"
                    :gridColumns="6"
                    :imgList="imgList"
                    :dataList="dataList"
                    gridRows="1fr"
                ></statisticsItem>
            </div>
        </div>

        <!-- 过滤器 -->
        <div class="filterBar-box">
            <i-filter-bar
            ref="filterBar"
            slot="filter"
            class="filterBar"
            :columns="searchColumns"
            :gridColumns="2"
            >
            </i-filter-bar>
            <div>
            <el-button type="primary" @click="searchHandle">搜索</el-button>
            <el-button @click="resetHandle">重置</el-button>
            </div>
        </div>

        <!-- 列表 -->
        <table-title title="明细列表" class="table-title">
            <template #right>
                <el-button>导出</el-button>
            </template>
        </table-title>
        <div class="bottom">
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
        <filterDetail v-model="showDetail" @handleConfirm="handleConfirm"></filterDetail>
    </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import orgTitleSelect from '@/modules/module-gridManagement/components/orgTitleSelect.vue'
import filterDetail from "../../merStatisticsManagement/components/filterDetail.vue";
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
const iconList = [
  {
    name: "农村金融服务站总数(个)",
    img: require("@/modules/module-gridManagement/assets/images/rural1.png"),
    key: "key1",
  },
  {
    name: "所在行政村网格占比",
    img: require("@/modules/module-gridManagement/assets/images/rural2.png"),
    key: "key2",
  },
  {
    name: "小面额功能机具数(个)",
    img: require("@/modules/module-gridManagement/assets/images/cash3.png"),
    key: "key3",
  },
  {
    name: "零钱包兑换数量(个)",
    img: require("@/modules/module-gridManagement/assets/images/cash4.png"),
    key: "key4",
  },
  {
    name: "零钱包兑换金额(元)",
    img: require("@/modules/module-gridManagement/assets/images/cash5.png"),
    key: "key5",
  },
  {
    name: "硬币兑换金额(元)",
    img: require("@/modules/module-gridManagement/assets/images/rural6.png"),
    key: "key6",
  },
]
const dataInfo = {
  'key1':'123456',
  'key2':'123',
  'key3':'456',
  'key4':'789',
  'key5':'999',
  'key6':'9991',
}
export default {
    mixins: [pageMixin],
    components: {
        filterDetail,statisticsItem,orgTitleSelect
    },
    data() {
        return {
            imgList:[],
            dataList:{},
            showDetail:false,
            loading:false,
            maxHeight:320,
            tableData:[],
            searchColumns: [
                {
                    label: "地市分行名称",
                    key: "servePointName",
                    type: "text",
                },
            ],
            columns:[
                {label: "地市分行名称", prop: "1",showOverFlowTooltip: true,width:150},
                {label: "农村金融服务站总数", prop: "2",showOverFlowTooltip: true,sortable:true,width:180 },
                {label: "所在行政村网格占比", prop: "3",showOverFlowTooltip: true,sortable:true,width:180 },
                {label: "小面额功能机具数", prop: "4",showOverFlowTooltip: true,sortable:true,width:180},
                {label: "人工现金柜台总数", prop: "5",showOverFlowTooltip: true,sortable:true,width:180 },
                {label: "存取款一体机CRS", prop: "6",showOverFlowTooltip: true,sortable:true,width:180 },
                {label: "硬币兑换机数量", prop: "7",showOverFlowTooltip: true,sortable:true,width:150 },
                {label: "零钱包兑换金额", prop: "8",showOverFlowTooltip: true,sortable:true,width:150 },
                {label: "硬币兑换枚数", prop: "811",showOverFlowTooltip: true,sortable:true,width:150 },
                {label: "硬币兑换金额", prop: "822",showOverFlowTooltip: true,sortable:true, width:150},
                { label: "操作", slot: true,fixed:'right' },
            ],
            tableBtn: [
                {cname: "明细", ename: "viewHandle",},
            ],
        }
    },
    mounted() {
        this.imgList = iconList
        this.dataList = dataInfo
    },
    methods: {
        viewHandle() {
        
        },
        handleConfirm() {

        },
    },
}
</script>

<style scoped lang="scss">
.top {
  margin-bottom: 13px;
  &-data {
    margin-top: 12px;
    padding: 12px 16px;
    background: #fff;
  }
  .top-title {
    position: relative;
    margin-bottom: 24px;
  }
  .top-title ::after {
    content:'';
    position: absolute;
    bottom:-8px;
    left: -16px;
    width: calc(100% + 32px);
    height: 1px;
    background:#E7EAF6;
  }
}
.filterBar-box {
  margin-bottom:12px;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 60px;
}
.bottom {
    background: #fff;
    padding: 0 16px;
    padding-bottom: 12px;
}
</style>