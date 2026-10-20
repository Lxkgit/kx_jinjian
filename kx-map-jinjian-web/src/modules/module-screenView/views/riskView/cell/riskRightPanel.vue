<template>
  <div class="risk-side risk-right-panel">
    <titleBox class="special-panel" title="防非打非专项指标" height="480px">
      <div class="metric-grid special-grid">
        <div v-for="item in special" :key="item.label" class="metric-cell" :class="item.color">
          <label>{{ item.label }}</label><div><strong>{{ item.value }}</strong><small>{{ item.unit }}</small></div>
        </div>
      </div>
      <div class="progress-row"><span>风险拦截数</span><b>{{ current.intercept }} 户</b></div>
      <div class="progress"><i :style="{ width: current.interceptRate + '%' }"></i></div>
      <div class="progress-row"><span>预警闭环率</span><b class="blue-text">{{ current.closedRate }}%</b></div>
      <div class="sub-title"><span>风险等级分布</span></div>
      <pieRight :info="pieData" />
    </titleBox>
    <titleBox class="warning-panel" title="风险预警管理" height="430px">
      <div class="warning-summary"><strong>{{ current.warnings }}</strong><span>件联动事项</span></div>
      <div class="tags"><span>{{ current.areas }}跨地区</span><span>{{ current.departments }}跨部门</span><span>{{ current.levels }}条级进</span></div>
      <div class="rate-head"><span>联合处置率 <b>{{ current.jointRate }}%</b></span><span>反馈及时率 <b>{{ current.feedbackRate }}%</b></span></div>
      <div class="dual-progress"><i :style="{width:current.jointRate+'%'}"></i><em :style="{width:current.feedbackRate+'%'}"></em></div>
      <div class="sub-title"><span>联动处置成效</span></div>
      <barOne :info="barData" />
    </titleBox>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import pieRight from "@/modules/module-screenView/viewComponents/components/pieRight/index.vue";
import barOne from "@/modules/module-screenView/viewComponents/components/barOne";

const LEVEL_DATA = {
  1: { riskCustomers: 23840, disposalRate: 88, accuracy: 89, reviewHours: 26, intercept: 5860, interceptRate: 82, closedRate: 89, high: 16, medium: 34, low: 50, warnings: 4280, areas: 11, departments: 42, levels: 4, jointRate: 88, feedbackRate: 87, chart: [{name:"公安",start:1280,finish:1090,rate:85},{name:"市场监管",start:960,finish:830,rate:86},{name:"街道社区",start:1380,finish:1240,rate:90},{name:"分支行",start:760,finish:650,rate:86}] },
  2: { riskCustomers: 2860, disposalRate: 89, accuracy: 91, reviewHours: 22, intercept: 684, interceptRate: 85, closedRate: 91, high: 17, medium: 33, low: 50, warnings: 538, areas: 8, departments: 36, levels: 4, jointRate: 90, feedbackRate: 89, chart: [{name:"公安",start:168,finish:145,rate:86},{name:"市场监管",start:126,finish:112,rate:89},{name:"街道社区",start:172,finish:158,rate:92},{name:"分支行",start:98,finish:86,rate:88}] },
  3: { riskCustomers: 1090, disposalRate: 91, accuracy: 93, reviewHours: 16, intercept: 271, interceptRate: 88, closedRate: 94, high: 19, medium: 31, low: 50, warnings: 128, areas: 5, departments: 28, levels: 3, jointRate: 92, feedbackRate: 93, chart: [{name:"公安",start:38,finish:34,rate:89},{name:"市场监管",start:31,finish:28,rate:90},{name:"街道社区",start:46,finish:43,rate:93},{name:"分支行",start:26,finish:23,rate:88}] },
  4: { riskCustomers: 109, disposalRate: 93, accuracy: 95, reviewHours: 12, intercept: 27, interceptRate: 91, closedRate: 96, high: 18, medium: 32, low: 50, warnings: 39, areas: 3, departments: 18, levels: 2, jointRate: 94, feedbackRate: 95, chart: [{name:"公安",start:5,finish:4,rate:82},{name:"市场监管",start:4,finish:4,rate:88},{name:"街道社区",start:12,finish:11,rate:88},{name:"分支行",start:17,finish:15,rate:82}] },
  5: { riskCustomers: 12, disposalRate: 95, accuracy: 97, reviewHours: 8, intercept: 4, interceptRate: 94, closedRate: 98, high: 17, medium: 33, low: 50, warnings: 8, areas: 1, departments: 8, levels: 1, jointRate: 96, feedbackRate: 97, chart: [{name:"公安",start:2,finish:2,rate:92},{name:"市场监管",start:1,finish:1,rate:95},{name:"街道社区",start:4,finish:4,rate:96},{name:"分支行",start:2,finish:2,rate:94}] },
};
export default {
  name: "RiskRightPanel",
  components: { titleBox, pieRight, barOne },
  props: { showLevelView: [Number, String] },
  computed: {
    level() { return Math.min(5, Math.max(1, Number(this.showLevelView) || 1)); },
    current() { return LEVEL_DATA[this.level]; },
    special() {
      const d = this.current;
      return [
        { label: "风险客户数", value: d.riskCustomers.toLocaleString(), unit: "户", color: "red" },
        { label: "预警处置率", value: d.disposalRate + "%", color: "green" },
        { label: "识别准确率", value: d.accuracy + "%", color: "blue" },
        { label: "线索核查均时", value: d.reviewHours, unit: "小时", color: "orange" },
      ];
    },
    pieData() {
      const d = this.current;
      return [
        { value: d.high, name: "高风险", share: d.high + "%" },
        { value: d.medium, name: "中风险", share: d.medium + "%" },
        { value: d.low, name: "低风险", share: d.low + "%" },
      ];
    },
    barData() {
      const d = this.current;
      return {
        title: "发起联动",
        xData: d.chart.map(i => i.name),
        yData: d.chart.map(i => i.start),
        lineTitle: "闭环率",
        lineData: d.chart.map(i => i.rate),
      };
    },
  },
};
</script>

<style lang="scss" scoped>
@import "./riskPanel.scss";
// .risk-right-panel{display:flex;flex-direction:column;gap:12px}
.risk-right-panel ::v-deep .title-box-wrapper{flex:1;min-height:0;margin-bottom:0}
// .risk-right-panel ::v-deep .title-box-wrapper.warning-panel{flex:1.4}
.risk-right-panel ::v-deep .KeyPerson-echarts,
.risk-right-panel ::v-deep .bar-box{width:100%;height:220px}
.special-grid{grid-template-columns:repeat(2,1fr)}
.warning-summary{margin:3px 0 6px}.warning-summary strong{font-size:35px;text-shadow:0 0 10px rgba(255,255,255,.2)}.warning-summary span{margin-left:8px;color:#8ba1be}.tags{display:flex;gap:8px}.tags span{padding:4px 9px;border:1px solid rgba(89,145,221,.28);border-radius:3px;background:#dbe8ff;color:#3575d6}.tags span:nth-child(2){background:#eaffdf;color:#54a725}.tags span:nth-child(3){background:#fff5d8;color:#d69a00}.rate-head{display:flex;justify-content:space-between;margin-top:12px;color:#8298b7}.rate-head b{color:#6ee52c}.rate-head span:last-child b{color:#2f88ff}.dual-progress{height:6px;margin-top:6px;background:#142b52;position:relative;overflow:hidden}.dual-progress i,.dual-progress em{position:absolute;left:0;height:3px}.dual-progress i{top:0;background:#52c41a}.dual-progress em{bottom:0;background:#1682ff}
</style>
