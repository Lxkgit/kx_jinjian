<template>
  <PageContent>
    <orgTitleSelect>
      <template #right>
        <div class="time-select">
          <div class="time-select-now" :class="date ? '' : 'active'">当年(默认)</div>
          <div class="time-select-text">时间范围</div>
          <el-date-picker
              class="time-select-date-picker"
              v-model="date"
              type="daterange"
              start-placeholder="开始日期"
              value-format="yyyy-MM-dd"
              @change="handleChange"
              end-placeholder="结束日期">
          </el-date-picker>
        </div>
      </template>
    </orgTitleSelect>
    <div class="info">
      <tableTitle title="数据统计" class="top-title"></tableTitle>
      <div class="circle-charts">
        <div class="circle-charts-left">
          <div class="total-shop">
            <img :src="require('@/modules/module-gridManagement/assets/images/circle-shop.png')" class="imgIcon" />
            <div class="conetent">
              <div class="title">总商户数</div>
              <div class="num">{{ 1234 }}</div>
            </div>
          </div>
          <pieChartList :data="shopData" :styleClass="shopStyleClass" gridColumns="3"></pieChartList>
        </div>
        <div class="circle-charts-right">
          <pieChartList :data="shopTransfromData" :styleClass="shopTransfromDataTransformStyleClass" itemHeight="108px" circleWidth="76px" circleHeight="76px" gridColumns="2"></pieChartList>
        </div>
      </div>
      <div class="title-charts">
        <div class="title-charts-circle">
          <smallTitle title="摸排结果统计"></smallTitle>
          <circleCharts></circleCharts>
        </div>
        <div class="title-charts-bar">
          <smallTitle title="各项指标达标情况"></smallTitle>
          <barCharts></barCharts>
        </div>
      </div>
      <div class="bottom-charts">
        <smallTitle title="摸排数据趋势"></smallTitle>
        <lineCharts></lineCharts>
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
    <table-title title="详细统计数据" class="table-title">
        <template #right>
         <el-button>导出</el-button>
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
        :showPagination="false"
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
import orgTitleSelect from '@/modules/module-gridManagement/components/orgTitleSelect.vue';
import smallTitle from "@/modules/module-gridManagement/components/smallTitle.vue";
import circleCharts from "../components/circleCharts.vue";
import barCharts from "../components/barCharts.vue";
import lineCharts from "../components/lineCharts.vue";
import pieChartList from '@/modules/module-gridManagement/components/pieChartList';
export default {
  mixins: [pageMixin],
  
  components: {
    orgTitleSelect,smallTitle,circleCharts,barCharts,lineCharts,pieChartList
  },
  data() {
    return {
      date:'',
      maxHeight:'500',
      tableData:[],
      columns:[
        {label: "机构名称", prop: "1",showOverFlowTooltip: true,width:150},
        {label: "地区商户总数", prop: "2",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "摸排商户总数", prop: "3",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "摸排商户占比", prop: "4",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "红牌商户数", prop: "5",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "黄牌商户数", prop: "6",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "绿牌商户数", prop: "7",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "具备人民币现金收付条件", prop: "8",showOverFlowTooltip: true,sortable:true,width:200 },
        {label: "张贴“支持现金支付”标志", prop: "9",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "零钞备付充足（超过300元）", prop: "19",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "小面额自助兑换机具数量(整换零)", prop: "29",showOverFlowTooltip: true,sortable:true,width:180 },
        {label: "备付人民币现金整洁", prop: "229",showOverFlowTooltip: true,sortable:true,width:180 },
        {label:'操作',slot:true,fixed:'right'}
      ],
      searchColumns:[
        {label: "机构名称",key: "servePointName",type: "text",},
      ],
      shopData:[
        { text:'红牌商户数',rate:'20',value:'6182' },
        { text:'黄牌商户数',rate:'10',value:'6182' },
        { text:'绿牌商户数',rate:'70',value:'6182' },
      ],
      shopStyleClass:[
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#FF9797 ',//右边边25%渐变色
          rightlCircleColorBack: '#DC5564 ',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#FFD95A  ',//右边边25%渐变色
          rightlCircleColorBack: '#FFB800  ',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#62FFC3  ',//右边边25%渐变色
          rightlCircleColorBack: '#37C38C  ',//右边边100%渐变色
        },
      ],
      shopTransfromData:[
        { text:'红转绿商户数',rate:'20',value:'6182' },
        { text:'绿转红商户数',rate:'50',value:'6182' },
      ],
      shopTransfromDataTransformStyleClass:[
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#97B6FF  ',//右边边25%渐变色
          rightlCircleColorBack: '#3284FF  ',//右边边100%渐变色
        },
        {
          fillColor: '#fff',
          rateSize: '14',
          circleTextSize: '18',
          circleTextColor: '#323548',
          circleFontFamily: 'YouSheBiaoTiHei',
          rightCircleColorFront: '#FFC997   ',//右边边25%渐变色
          rightlCircleColorBack: '#FF8934   ',//右边边100%渐变色
        },
      ],
    }
  },
  computed: {},
  methods: {
    handleChange(val) {

    },
  },
};
</script>

<style scoped lang="scss">
.time-select {
  display: flex;
  align-items: center;
  &-now {
    width: 80px;
    height: 36px;
    text-align: center;
    line-height: 34px;
    border-radius: 4px;
    border: 1px solid rgba(94, 129, 244, 0.1);
    background: rgba(94, 129, 244, 0.15);
    color: #3D3D3D;
    box-shadow: inset 0px 1px 4px 0px rgba(12, 22, 72, 0.1);
    cursor: pointer;
  }
  &-text {
    margin-left: 16px;
    color: #494D68;
    font-size: 14px;
  }
  &-date-picker {
    margin-left: 12px;
    width: 280px;
  }
  .active {
    background: linear-gradient(180deg, #92ABFF 0%, #5E81F4 100%);
    border: 1px solid rgba(255, 255, 255, 0.4);
    color: #FFFFFF;
  }
}
.info {
  width: 100%;
  margin-top: 12px;
  padding: 16px;
  background: #fff;
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
  .title-charts {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    &-bar {
      flex: 1;
    }
  }
}
.filterBar-box {
  margin: 12px 0;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.circle-charts {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  &-left {
    width: 60%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: #EBEEF5;
    padding: 8px;
    .total-shop {
      display: flex;
      width: 260px;
      padding: 16px 0 16px 16px;
      border-radius: 8px;
      background: #EBEEF5;
      border: 1px solid #ebeef5;
      margin-right: 12px;
      .imgIcon {
        width: 60px;
        height: 60px;
        margin-right: 12px;
      }
      .conetent {
        display:flex;
        flex-direction: column;
        justify-content: space-around;
      }
      .title {
        font-size: 14px;
        line-height: 20px;
        color: #666666;
        // margin-bottom: 8px;
      }
      .num {
        font-size: 22px;
        font-weight: 500;
        line-height: 32px;
        color: #333333;
      }
    }
  }
  &-right {
    flex:1;
    margin-left: 12px;
  }
}
.table-title {
  padding: 16px;
  height: 60px;
}
.i-table {
  height: auto;
  background: #fff;
  padding: 0 16px 16px 16px;
}
</style>