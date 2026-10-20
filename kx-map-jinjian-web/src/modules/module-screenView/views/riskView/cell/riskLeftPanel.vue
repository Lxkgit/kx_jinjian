<template>
  <div class="risk-side risk-left-panel">
    <titleBox class="overview-panel" title="网格概况">
      <div slot="operate" class="level-badge">{{ levelName }}</div>
      <div class="metric-grid overview-grid">
        <div v-for="item in overview" :key="item.label" class="metric-cell" :class="item.color">
          <label>{{ item.label }}</label>
          <div><strong>{{ item.value }}</strong><small>{{ item.unit }}</small></div>
        </div>
      </div>
    </titleBox>

    <titleBox class="promote-panel" title="业务推进 + 风险防控">
      <div class="file-metric-list">
        <div v-for="item in fileMetrics" :key="item.label" class="file-metric-item" :class="item.color">
          <div class="file-metric-head">
            <span class="file-value">{{ item.value }}</span>
            <span class="file-delta">
              <i class="arrow-down"></i>{{ item.delta }}
            </span>
            <span class="file-last">较上月 {{ item.last }}</span>
          </div>
          <div class="file-metric-foot">
            <span>{{ item.label }}覆盖率</span>
            <b>{{ item.rate }}%</b>
          </div>
        </div>
      </div>
      <div class="metric-grid promote-grid">
        <div v-for="item in metrics" :key="item.label" class="metric-cell" :class="item.color">
          <label>{{ item.label }}</label><div><strong>{{ item.value }}</strong><small>{{ item.unit }}</small></div>
        </div>
      </div>
    </titleBox>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";

const LEVEL_DATA = {
  1: { grids: 12480, finance: 6860, targets: 428600, population: 65770000, economy: 82500, communities: 31500, communityPopulation: 49800000, parks: 1480, companies: 3180000, institutions: 7420, businessDistricts: 920, outreach: 12680, visits: 98500, conversion: 72, intercepted: 5860, warningRate: 89, outreachRate: 74, personalFile: 13421, personalFileDelta: 12, personalFileLast: 11098, personalFileRate: 54, businessFile: 7657, businessFileDelta: 176, businessFileLast: 743, businessFileRate: 54, smeFile: 734, smeFileDelta: 12, smeFileLast: 598, smeFileRate: 35 },
  2: { grids: 1360, finance: 768, targets: 52600, population: 9780000, economy: 8100, communities: 4520, communityPopulation: 7380000, parks: 186, companies: 386000, institutions: 860, businessDistricts: 116, outreach: 1680, visits: 12600, conversion: 75, intercepted: 684, warningRate: 91, outreachRate: 77, personalFile: 2860, personalFileDelta: 18, personalFileLast: 2450, personalFileRate: 56, businessFile: 1680, businessFileDelta: 24, businessFileLast: 1520, businessFileRate: 52, smeFile: 128, smeFileDelta: 6, smeFileLast: 118, smeFileRate: 38 },
  3: { grids: 136, finance: 147, targets: 9830, population: 820000, economy: 2030, communities: 440, communityPopulation: 618800, parks: 52, companies: 43900, institutions: 168, businessDistricts: 32, outreach: 386, visits: 2960, conversion: 78, intercepted: 271, warningRate: 94, outreachRate: 81, personalFile: 890, personalFileDelta: 8, personalFileLast: 820, personalFileRate: 58, businessFile: 510, businessFileDelta: 12, businessFileLast: 480, businessFileRate: 55, smeFile: 38, smeFileDelta: 3, smeFileLast: 34, smeFileRate: 40 },
  4: { grids: 13, finance: 18, targets: 968, population: 126000, economy: 286, communities: 44, communityPopulation: 92800, parks: 5, companies: 4930, institutions: 24, businessDistricts: 7, outreach: 129, visits: 978, conversion: 81, intercepted: 49, warningRate: 96, outreachRate: 86, personalFile: 96, personalFileDelta: 3, personalFileLast: 90, personalFileRate: 60, businessFile: 58, businessFileDelta: 4, businessFileLast: 55, businessFileRate: 58, smeFile: 5, smeFileDelta: 1, smeFileLast: 4, smeFileRate: 42 },
  5: { grids: 1, finance: 2, targets: 86, population: 6800, economy: 18, communities: 6, communityPopulation: 5100, parks: 1, companies: 286, institutions: 3, businessDistricts: 1, outreach: 18, visits: 126, conversion: 84, intercepted: 7, warningRate: 98, outreachRate: 91, personalFile: 12, personalFileDelta: 1, personalFileLast: 10, personalFileRate: 62, businessFile: 8, businessFileDelta: 1, businessFileLast: 7, businessFileRate: 60, smeFile: 1, smeFileDelta: 0, smeFileLast: 1, smeFileRate: 45 },
};

export default {
  name: "RiskLeftPanel",
  components: { titleBox },
  props: { showLevelView: [Number, String] },
  computed: {
    level() { return Math.min(5, Math.max(1, Number(this.showLevelView) || 1)); },
    levelName() { return ["", "省级", "市级", "区县级", "乡镇/街道级", "村/社区级"][this.level]; },
    current() { return LEVEL_DATA[this.level]; },
    overview() {
      const d = this.current;
      return [
        { label: "当前层级", value: this.levelName }, { label: "下辖网格数", value: d.grids.toLocaleString(), unit: "个" },
        { label: "金融网点数", value: d.finance.toLocaleString(), unit: "家" }, { label: "监测覆盖对象", value: d.targets.toLocaleString(), unit: "个" },
        { label: "网格人口", value: d.population.toLocaleString(), unit: "人", color: "blue" }, { label: "经济总量", value: d.economy.toLocaleString(), unit: "亿元", color: "green" },
        { label: "小区数量", value: d.communities.toLocaleString(), unit: "个", color: "orange" }, { label: "小区人口", value: d.communityPopulation.toLocaleString(), unit: "人", color: "green" },
        { label: "工业园数量", value: d.parks.toLocaleString(), unit: "个", color: "cyan" }, { label: "企业数量", value: d.companies.toLocaleString(), unit: "家", color: "blue" },
        { label: "金融机构数量", value: d.institutions.toLocaleString(), unit: "家", color: "purple" }, { label: "商圈数量", value: d.businessDistricts.toLocaleString(), unit: "个", color: "orange" },
      ];
    },
    metrics() {
      const d = this.current;
      return [
        { label: "外拓人数", value: d.outreach.toLocaleString(), unit: "人", color: "blue" },
        { label: "拜访数", value: d.visits.toLocaleString(), unit: "次", color: "purple" },
        { label: "建档转化率", value: d.conversion + "%", color: "orange" },
        { label: "风险拦截数", value: d.intercepted.toLocaleString(), color: "red" },
        { label: "预警闭环率", value: d.warningRate + "%", color: "green" },
        { label: "外拓覆盖率", value: d.outreachRate + "%", color: "blue" },
      ];
    },
    fileMetrics() {
      const d = this.current;
      return [
        { label: "个人客户建档", value: d.personalFile.toLocaleString(), delta: d.personalFileDelta, last: d.personalFileLast.toLocaleString(), rate: d.personalFileRate, color: "blue" },
        { label: "个体工商户建档", value: d.businessFile.toLocaleString(), delta: d.businessFileDelta, last: d.businessFileLast.toLocaleString(), rate: d.businessFileRate, color: "purple" },
        { label: "小微企业建档", value: d.smeFile.toLocaleString(), delta: d.smeFileDelta, last: d.smeFileLast.toLocaleString(), rate: d.smeFileRate, color: "green" },
      ];
    },
  },
};
</script>

<style lang="scss" scoped>
@import "./riskPanel.scss";
.risk-left-panel{display:flex;flex-direction:column;gap:12px}
.risk-left-panel ::v-deep .title-box-wrapper{flex:1;min-height:0;margin-bottom:0}
.overview-grid,.promote-grid{grid-template-columns:repeat(2,1fr)}
.level-badge{padding:4px 9px;border:1px solid rgba(84,158,244,.48);border-radius:4px;background:linear-gradient(180deg,#e9f3ff,#bdd8fa);color:#2864ae;font-size:12px;font-style:normal;text-shadow:none;line-height:1}

.file-metric-list{display:flex;flex-direction:column;gap:8px;margin-bottom:10px}
.file-metric-item{position:relative;padding:8px 12px;border:1px solid rgba(25,89,159,.32);border-radius:7px;background:linear-gradient(135deg,rgba(17,52,99,.94),rgba(11,39,79,.82));box-shadow:inset 0 0 14px rgba(25,94,171,.09);overflow:hidden}
.file-metric-item:after{content:"";position:absolute;left:0;top:11px;width:2px;height:28px;background:#8097b5;opacity:.65}
.file-metric-item.blue:after{background:#2d8dff}
.file-metric-item.purple:after{background:#b369ff}
.file-metric-item.green:after{background:#62de32}
.file-metric-head{display:flex;align-items:baseline;gap:10px}
.file-value{font-size:22px;font-weight:bold;color:#f2f7ff;font-family:Arial,sans-serif}
.file-metric-item.blue .file-value{color:#2d8dff}
.file-metric-item.purple .file-value{color:#b369ff}
.file-metric-item.green .file-value{color:#62de32}
.file-delta{display:inline-flex;align-items:center;gap:2px;font-size:12px;color:#ff7875}
.arrow-down{display:inline-block;width:0;height:0;border-left:4px solid transparent;border-right:4px solid transparent;border-top:5px solid currentColor}
.file-last{font-size:12px;color:#7189aa}
.file-metric-foot{display:flex;justify-content:space-between;align-items:center;margin-top:4px;font-size:12px;color:#8199b8}
.file-metric-foot b{font-size:13px;color:#6ee52c}
</style>
