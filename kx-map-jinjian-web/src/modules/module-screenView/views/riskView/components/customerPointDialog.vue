<template>
  <el-dialog
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    :close-on-click-modal="false"
    append-to-body
    width="1040px"
    top="5vh"
    custom-class="risk-customer-dialog"
  >
    <div class="customer-detail-shell">
      <header class="customer-hero">
        <div class="customer-avatar" :class="info.customerType">
          {{ typeMeta.short }}
        </div>
        <div class="hero-main">
          <div class="hero-line">
            <h2>{{ info.name || "客户详情" }}</h2>
            <span class="type-badge">{{ typeMeta.label }}</span>
            <span class="risk-badge" :class="riskLevelClass">{{ info.riskLevel || "低风险" }}</span>
          </div>
          <div class="hero-sub">
            <span>客户号 {{ info.customerNo || "-" }}</span>
            <span>{{ info.branch || "-" }}</span>
            <span>客户经理 {{ info.manager || "-" }}</span>
          </div>
        </div>
        <div class="hero-score">
          <strong>{{ info.rating || "A" }}</strong>
          <span>综合评级</span>
        </div>
      </header>

      <div class="customer-kpis">
        <div><span>综合资产</span><strong>{{ info.assets || "-" }}</strong></div>
        <div><span>持有产品</span><strong>{{ productCount }} <small>项</small></strong></div>
        <div><span>近一年走访</span><strong>{{ (info.visits || []).length }} <small>次</small></strong></div>
        <div><span>在途商机</span><strong>{{ (info.opportunities || []).length }} <small>项</small></strong></div>
      </div>

      <el-tabs v-model="activeTab" class="customer-tabs">
        <el-tab-pane label="客户总览" name="overview">
          <div class="detail-grid overview-grid">
            <section class="detail-card basic-card">
              <div class="card-title"><i></i>客户基本信息</div>
              <div class="basic-fields">
                <div><span>客户名称</span><b>{{ info.name || "-" }}</b></div>
                <div><span>联系电话</span><b>{{ info.phone || "-" }}</b></div>
                <div><span>{{ info.customerType === 'personal' ? '证件号码' : '统一社会信用代码' }}</span><b>{{ info.identity || "-" }}</b></div>
                <div><span>所属网格</span><b>{{ info.grid || "-" }}</b></div>
                <div><span>客户状态</span><b>{{ info.customerStatus || "-" }}</b></div>
                <div><span>建档日期</span><b>{{ info.establishDate || "-" }}</b></div>
                <div><span>客户来源</span><b>{{ info.customerSource || "-" }}</b></div>
                <div><span>{{ info.customerType === 'personal' ? '职业类型' : '所属行业' }}</span><b>{{ info.industry || "-" }}</b></div>
                <div class="wide"><span>联系地址</span><b>{{ info.address || "-" }}</b></div>
              </div>
            </section>
            <section class="detail-card tag-card">
              <div class="card-title"><i></i>客户标签</div>
              <div class="tag-cloud">
                <span v-for="tag in info.tags || []" :key="tag">{{ tag }}</span>
              </div>
            </section>
            <section class="detail-card journey-card full-card">
              <div class="card-title"><i></i>客户旅程</div>
              <div class="journey-line">
                <div v-for="(item, index) in info.journey || []" :key="item.time" class="journey-node">
                  <div class="node-index">{{ index + 1 }}</div>
                  <div class="node-time">{{ item.time }}</div>
                  <strong>{{ item.title }}</strong>
                  <p>{{ item.desc }}</p>
                </div>
              </div>
            </section>
          </div>
        </el-tab-pane>

        <el-tab-pane label="风险画像" name="risk">
          <div class="risk-profile-grid">
            <section class="detail-card risk-summary-card">
              <div class="card-title"><i></i>客户风险概况</div>
              <div class="risk-summary-content">
                <div class="risk-level-panel" :class="riskLevelClass">
                  <span>当前风险等级</span>
                  <strong>{{ info.riskLevel || "低风险" }}</strong>
                  <small>{{ riskLevelDesc }}</small>
                </div>
                <div class="risk-type-panel">
                  <span class="field-label">风险类型标签</span>
                  <div class="risk-type-tags">
                    <em
                      v-for="type in info.riskTypes || []"
                      :key="type"
                      :class="riskTypeClass(type)"
                    >{{ type }}</em>
                    <b v-if="!(info.riskTypes || []).length">暂无风险标签</b>
                  </div>
                  <div class="risk-stat-strip">
                    <div><strong>{{ (info.riskChecks || []).length }}</strong><span>近一年核查</span></div>
                    <div><strong>{{ latestRiskCheckDate }}</strong><span>最近核查</span></div>
                    <div><strong>{{ activeRiskCount }}</strong><span>关注事项</span></div>
                  </div>
                </div>
              </div>
            </section>

            <section class="detail-card risk-check-card">
              <div class="card-title">
                <i></i>最近风险核查记录
                <span class="record-count">近一年 {{ (info.riskChecks || []).length }} 条</span>
              </div>
              <div v-if="(info.riskChecks || []).length" class="risk-check-list">
                <article v-for="(record, index) in info.riskChecks" :key="record.date + index" class="risk-check-item">
                  <div class="check-time">
                    <span>{{ record.date }}</span>
                    <i></i>
                  </div>
                  <div class="check-body">
                    <div class="check-head">
                      <strong>{{ record.source }}</strong>
                      <span class="check-risk-level" :class="riskLevelClassOf(record.riskLevel)">{{ record.riskLevel }}</span>
                      <em :class="riskTypeClass(record.type)">{{ record.type }}</em>
                      <b :class="riskStatusClass(record.status)">{{ record.status }}</b>
                    </div>
                    <p><span>风险摘要</span>{{ record.summary }}</p>
                    <p><span>监测特征</span>{{ record.evidence || "-" }}</p>
                    <p class="check-conclusion"><span>核查结论</span>{{ record.conclusion }}</p>
                    <div class="check-handler">
                      <span>核查单号：{{ record.checkNo || "-" }}</span>
                      <span>核查人员：{{ record.handler || "-" }}</span>
                    </div>
                  </div>
                </article>
              </div>
              <div v-else class="empty-risk-record">近一年暂无风险核查记录</div>
            </section>
          </div>
        </el-tab-pane>

        <el-tab-pane label="经营互动" name="interaction">
          <div class="detail-grid interaction-grid">
            <section class="detail-card">
              <div class="card-title"><i></i>走访信息</div>
              <div v-for="item in info.visits || []" :key="item.time" class="record-row">
                <div class="record-date">{{ item.time }}</div>
                <div><strong>{{ item.mode }}</strong><p>{{ item.result }}</p></div>
                <span>{{ item.staff }}</span>
              </div>
            </section>
            <section class="detail-card">
              <div class="card-title"><i></i>商机信息</div>
              <div v-for="item in info.opportunities || []" :key="item.name" class="business-row">
                <div><strong>{{ item.name }}</strong><p>负责人 {{ item.owner }}</p></div>
                <b>{{ item.amount }}</b><span>{{ item.stage }}</span>
              </div>
            </section>
            <section class="detail-card full-card">
              <div class="card-title"><i></i>任务信息</div>
              <div class="task-table">
                <div class="task-head"><span>任务名称</span><span>负责人</span><span>截止日期</span><span>状态</span></div>
                <div v-for="item in info.tasks || []" :key="item.name" class="task-row">
                  <strong>{{ item.name }}</strong><span>{{ item.owner }}</span><span>{{ item.deadline }}</span><em>{{ item.status }}</em>
                </div>
              </div>
            </section>
          </div>
        </el-tab-pane>

        <el-tab-pane label="行内产品" name="products">
          <section class="detail-card product-card">
            <div class="card-title"><i></i>行内产品持有情况</div>
            <div class="product-summary">当前客户共持有 <strong>{{ productCount }}</strong> 项行内产品，服务关系稳定</div>
            <div class="product-grid">
              <div v-for="(product, index) in info.products || []" :key="product" class="product-item">
                <span class="product-icon">{{ index + 1 }}</span>
                <div><strong>{{ product }}</strong><p>{{ productDesc(product, index) }}</p></div>
                <em>持有中</em>
              </div>
            </div>
          </section>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    value: Boolean,
    info: { type: Object, default: () => ({}) },
  },
  data() {
    return { dialogVisible: this.value, activeTab: "overview" };
  },
  computed: {
    typeMeta() {
      const map = {
        personal: { label: "个人客户", short: "个" },
        enterprise: { label: "企业客户", short: "企" },
        merchant: { label: "商户客户", short: "商" },
      };
      return map[this.info.customerType] || map.personal;
    },
    productCount() {
      return (this.info.products || []).length;
    },
    riskLevelClass() {
      const map = { 低风险: "risk-low", 中风险: "risk-medium", 高风险: "risk-high" };
      return map[this.info.riskLevel] || "risk-low";
    },
    riskLevelDesc() {
      const map = {
        低风险: "风险特征较弱，维持常规监测",
        中风险: "存在可疑特征，需加强核实与持续关注",
        高风险: "风险特征明显，正在采取强化管控措施",
      };
      return map[this.info.riskLevel] || map.低风险;
    },
    latestRiskCheckDate() {
      const records = this.info.riskChecks || [];
      return records.length ? records[0].date : "-";
    },
    activeRiskCount() {
      return (this.info.riskChecks || []).filter(
        (item) => !["已排除", "已核查", "已归档"].includes(item.status)
      ).length;
    },
  },
  watch: {
    value(val) {
      this.dialogVisible = val;
      if (val) this.activeTab = "overview";
    },
    dialogVisible(val) {
      this.$emit("input", val);
    },
  },
  methods: {
    handleClose() { this.dialogVisible = false; },
    productDesc(product, index) {
      const detailMap = {
        手机银行: `最近登录 2026-08-${String(12 + index).padStart(2, "0")}`,
        市民卡: "二代市民卡 · 状态正常",
        活期储蓄: `当前余额 ${(12.8 + index * 6.3).toFixed(1)} 万元`,
        定期存款: `存续金额 ${30 + index * 10} 万元 · 期限1年`,
        信用卡: `授信额度 ${5 + index} 万元 · 无逾期`,
        稳健型理财: `持有市值 ${(18.5 + index * 4.2).toFixed(1)} 万元`,
        企业网银: "双人复核模式 · 使用正常",
        结算账户: `近30日结算 ${286 + index * 73} 万元`,
        流动资金贷款: `贷款余额 ${300 + index * 100} 万元 · 正常`,
        代发工资: `最近代发 ${36 + index * 8} 人`,
        单位结算卡: `可用额度 ${20 + index * 5} 万元`,
        票据服务: `在池票据 ${80 + index * 20} 万元`,
        收单: `近30日交易 ${168 + index * 23} 笔`,
        聚合支付: `月交易额 ${(16.8 + index * 3.6).toFixed(1)} 万元`,
        经营贷: `可用额度 ${20 + index * 5} 万元`,
        商户管家: "经营分析服务 · 已开通",
      };
      return detailMap[product] || ["账户运行正常", "近月交易活跃", "服务签约有效"][index % 3];
    },
    riskTypeClass(type) {
      return { 涉非: "type-illegal", 涉诈: "type-fraud", 异常交易: "type-abnormal" }[type] || "type-abnormal";
    },
    riskLevelClassOf(level) {
      return { 低风险: "risk-low", 中风险: "risk-medium", 高风险: "risk-high" }[level] || "risk-low";
    },
    riskStatusClass(status) {
      if (["处置中", "待复核"].includes(status)) return "status-danger";
      if (["持续关注", "核查中"].includes(status)) return "status-warning";
      return "status-done";
    },
  },
};
</script>

<style lang="scss">
.risk-customer-dialog {
  overflow: hidden;
  border: 1px solid rgba(55, 184, 255, 0.68);
  border-radius: 10px;
  background: linear-gradient(145deg, rgba(7, 25, 58, 0.98), rgba(5, 15, 42, 0.99));
  box-shadow: 0 0 50px rgba(0, 133, 255, 0.3), inset 0 0 35px rgba(37, 139, 255, 0.08);
  color: #fff;
  .el-dialog__header { position: absolute; z-index: 5; right: 4px; top: 2px; }
  .el-dialog__headerbtn { font-size: 22px; }
  .el-dialog__close { color: #9fd8ff; }
  .el-dialog__body { padding: 0; }
  .customer-detail-shell { height: 760px; overflow: hidden; }
  .customer-hero { height: 116px; padding: 22px 34px; display: flex; align-items: center; background: linear-gradient(90deg, rgba(16, 95, 181, .45), rgba(12, 45, 94, .22)); border-bottom: 1px solid rgba(75, 177, 255, .28); }
  .customer-avatar { width: 66px; height: 66px; border-radius: 18px; display: flex; align-items: center; justify-content: center; font-size: 26px; font-weight: 700; background: linear-gradient(145deg, #267dff, #31d8ff); box-shadow: 0 0 24px rgba(43, 159, 255, .55); }
  .customer-avatar.enterprise { background: linear-gradient(145deg, #7c5cff, #b071ff); }
  .customer-avatar.merchant { background: linear-gradient(145deg, #ff8a35, #ffc14e); }
  .hero-main { flex: 1; padding-left: 20px; }
  .hero-line { display: flex; align-items: center; gap: 10px; }
  .hero-line h2 { margin: 0 8px 0 0; font-size: 24px; letter-spacing: 1px; }
  .type-badge, .risk-badge { padding: 4px 10px; border-radius: 12px; font-size: 12px; color: #7bdcff; background: rgba(33, 154, 255, .14); border: 1px solid rgba(76, 193, 255, .42); }
  .risk-badge { color: #72f1ab; border-color: rgba(80, 225, 146, .4); background: rgba(40, 197, 117, .12); }
  .risk-badge.risk-medium { color: #ffd05d; border-color: rgba(255, 190, 58, .55); background: rgba(218, 143, 24, .16); }
  .risk-badge.risk-high { color: #ff737d; border-color: rgba(255, 77, 89, .58); background: rgba(224, 49, 65, .17); }
  .hero-sub { margin-top: 12px; color: #91afd1; display: flex; gap: 26px; font-size: 13px; }
  .hero-score { width: 90px; text-align: center; border-left: 1px solid rgba(100, 185, 255, .24); }
  .hero-score strong { display: block; color: #58d6ff; font-size: 34px; line-height: 38px; }
  .hero-score span { color: #829fbe; font-size: 12px; }
  .customer-kpis { display: grid; grid-template-columns: repeat(4, 1fr); margin: 18px 28px 4px; gap: 12px; }
  .customer-kpis > div { padding: 13px 18px; border: 1px solid rgba(62, 151, 236, .2); border-radius: 7px; background: linear-gradient(135deg, rgba(24, 73, 136, .5), rgba(10, 38, 82, .55)); }
  .customer-kpis span { display: block; color: #83a4c9; font-size: 12px; }
  .customer-kpis strong { display: block; color: #64d8ff; font-size: 20px; margin-top: 5px; }
  .customer-kpis small { font-size: 12px; color: #91acc8; }
  .customer-tabs { padding: 0 28px; }
  .el-tabs__header { margin-bottom: 14px; }
  .el-tabs__item { color: #83a7cf; font-size: 15px; }
  .el-tabs__item.is-active { color: #51cfff; }
  .el-tabs__active-bar { background: linear-gradient(90deg, #1d86ff, #4ce3ff); box-shadow: 0 0 10px #2ba9ff; }
  .el-tabs__nav-wrap::after { background: rgba(69, 145, 218, .18); }
  .el-tabs__content { height: 500px; overflow-y: auto; padding-right: 6px; }
  .el-tabs__content::-webkit-scrollbar { width: 5px; }
  .el-tabs__content::-webkit-scrollbar-thumb { background: #246cad; border-radius: 4px; }
  .detail-grid { display: grid; grid-template-columns: 1.35fr 1fr; gap: 14px; }
  .detail-card { border: 1px solid rgba(63, 151, 231, .22); border-radius: 8px; background: rgba(11, 37, 78, .62); padding: 16px 18px; }
  .full-card { grid-column: 1 / -1; }
  .card-title { color: #d9efff; font-size: 15px; font-weight: 600; margin-bottom: 14px; display: flex; align-items: center; }
  .card-title i { width: 3px; height: 16px; margin-right: 9px; background: linear-gradient(#3de4ff, #2779ff); box-shadow: 0 0 8px #35aaff; }
  .basic-fields { display: grid; grid-template-columns: 1fr 1fr; gap: 13px 22px; }
  .basic-fields .wide { grid-column: 1 / -1; }
  .basic-fields span { display: block; color: #7798bc; font-size: 12px; margin-bottom: 5px; }
  .basic-fields b { color: #e3f2ff; font-size: 13px; font-weight: 500; }
  .tag-cloud { display: flex; flex-wrap: wrap; gap: 10px; }
  .tag-cloud span { padding: 7px 13px; color: #77ddff; border-radius: 4px; border: 1px solid rgba(60, 189, 255, .35); background: rgba(24, 136, 214, .13); }
  .journey-line { display: flex; position: relative; padding: 8px 5px; }
  .journey-line::before { content: ''; position: absolute; top: 23px; left: 24px; right: 24px; height: 1px; background: linear-gradient(90deg, #2c8bff, #36d9ff); }
  .journey-node { flex: 1; position: relative; padding: 46px 16px 0 0; }
  .node-index { position: absolute; top: 0; width: 30px; height: 30px; border-radius: 50%; display: flex; align-items: center; justify-content: center; color: #fff; background: #147ee8; border: 4px solid #103c72; box-shadow: 0 0 12px #178eff; }
  .node-time { color: #4fd2ff; font-size: 12px; margin-bottom: 5px; }
  .journey-node strong { font-size: 14px; color: #e8f5ff; }
  .journey-node p, .record-row p, .business-row p { margin: 5px 0 0; color: #7897b9; font-size: 12px; line-height: 1.5; }
  .record-row, .business-row { min-height: 72px; display: grid; grid-template-columns: 82px 1fr 72px; align-items: center; border-bottom: 1px solid rgba(70, 135, 198, .15); }
  .record-date { color: #4bcfff; font-size: 12px; }
  .record-row > span { color: #8aa9c9; text-align: right; }
  .business-row { grid-template-columns: 1fr 82px 72px; }
  .business-row > b { color: #ffd36b; font-weight: 500; }
  .business-row > span { color: #64d9ff; padding: 4px; text-align: center; background: rgba(43, 153, 226, .13); }
  .task-head, .task-row { display: grid; grid-template-columns: 2fr 1fr 1fr .8fr; padding: 11px 14px; align-items: center; }
  .task-head { color: #6f92b8; background: rgba(35, 91, 151, .28); }
  .task-row { color: #adc8e3; border-bottom: 1px solid rgba(75, 143, 208, .12); }
  .task-row strong { color: #e4f2ff; }
  .task-row em { color: #60dcff; font-style: normal; }
  .risk-profile-grid { display: grid; gap: 14px; }
  .risk-summary-card { padding-bottom: 18px; }
  .risk-summary-content { display: grid; grid-template-columns: 255px 1fr; gap: 16px; }
  .risk-level-panel { min-height: 118px; padding: 18px 20px; border: 1px solid rgba(83, 226, 155, .3); border-radius: 7px; background: linear-gradient(135deg, rgba(28, 142, 96, .2), rgba(13, 69, 71, .18)); }
  .risk-level-panel span, .field-label { display: block; color: #7f9fbe; font-size: 12px; }
  .risk-level-panel strong { display: block; margin: 8px 0 7px; color: #68e8a4; font-size: 25px; }
  .risk-level-panel small { color: #8bb8a8; font-size: 12px; }
  .risk-level-panel.risk-medium { border-color: rgba(255, 190, 58, .34); background: linear-gradient(135deg, rgba(176, 111, 19, .22), rgba(74, 57, 18, .18)); }
  .risk-level-panel.risk-medium strong { color: #ffd05d; }
  .risk-level-panel.risk-medium small { color: #c8ae75; }
  .risk-level-panel.risk-high { border-color: rgba(255, 77, 89, .38); background: linear-gradient(135deg, rgba(164, 35, 55, .24), rgba(72, 25, 48, .2)); }
  .risk-level-panel.risk-high strong { color: #ff737d; }
  .risk-level-panel.risk-high small { color: #c9949b; }
  .risk-type-panel { padding: 18px 20px; border-radius: 7px; border: 1px solid rgba(63, 151, 231, .18); background: rgba(10, 32, 69, .44); }
  .risk-type-tags { display: flex; align-items: center; flex-wrap: wrap; gap: 12px; margin-top: 16px; }
  .risk-type-tags em, .check-head em { padding: 6px 13px; border-radius: 14px; font-size: 12px; font-style: normal; }
  .risk-type-tags b { color: #718eac; font-size: 13px; font-weight: 400; }
  .risk-stat-strip { display: grid; grid-template-columns: .8fr 1.2fr .8fr; gap: 8px; margin-top: 17px; padding-top: 14px; border-top: 1px solid rgba(72, 145, 211, .16); }
  .risk-stat-strip div { min-width: 0; }
  .risk-stat-strip strong { display: block; color: #dcefff; font-size: 15px; font-weight: 600; }
  .risk-stat-strip span { display: block; margin-top: 4px; color: #6685a5; font-size: 11px; }
  .type-illegal { color: #ff7c84; border: 1px solid rgba(255, 91, 105, .42); background: rgba(207, 46, 62, .15); }
  .type-fraud { color: #ffc45c; border: 1px solid rgba(255, 181, 61, .42); background: rgba(201, 124, 22, .15); }
  .type-abnormal { color: #67d7ff; border: 1px solid rgba(72, 190, 255, .42); background: rgba(33, 130, 205, .15); }
  .risk-check-card { min-height: 285px; }
  .record-count { margin-left: auto; color: #6f91b4; font-size: 12px; font-weight: 400; }
  .risk-check-list { padding: 0 2px; }
  .risk-check-item { display: grid; grid-template-columns: 96px 1fr; min-height: 132px; }
  .check-time { position: relative; padding-top: 5px; color: #69cfff; font-size: 12px; }
  .check-time::after { content: ''; position: absolute; top: 29px; bottom: -4px; left: 8px; width: 1px; background: linear-gradient(#287ed1, rgba(40, 126, 209, .08)); }
  .risk-check-item:last-child .check-time::after { display: none; }
  .check-time i { display: block; width: 17px; height: 17px; margin-top: 8px; border: 4px solid #123f76; border-radius: 50%; background: #3fcfff; box-shadow: 0 0 10px rgba(42, 183, 255, .7); }
  .check-body { margin-bottom: 12px; padding: 13px 16px; border-radius: 6px; border: 1px solid rgba(61, 137, 211, .17); background: rgba(17, 51, 96, .38); }
  .check-head { display: flex; align-items: center; gap: 9px; }
  .check-head strong { color: #e0f1ff; font-size: 14px; }
  .check-head em { padding: 3px 9px; }
  .check-risk-level { padding: 3px 9px; border-radius: 3px; color: #65dfa0; border: 1px solid rgba(76, 218, 143, .28); background: rgba(43, 191, 111, .12); font-size: 11px; }
  .check-risk-level.risk-medium { color: #ffd064; border-color: rgba(255, 190, 58, .3); background: rgba(220, 150, 30, .13); }
  .check-risk-level.risk-high { color: #ff737d; border-color: rgba(255, 77, 89, .34); background: rgba(215, 54, 70, .14); }
  .check-head b { margin-left: auto; padding: 3px 9px; border-radius: 3px; font-size: 11px; font-weight: 400; }
  .status-done { color: #65dfa0; background: rgba(43, 191, 111, .13); }
  .status-warning { color: #ffd064; background: rgba(220, 150, 30, .14); }
  .status-danger { color: #ff737d; background: rgba(215, 54, 70, .15); }
  .check-body p { margin: 9px 0 0; color: #9db5cd; font-size: 12px; line-height: 1.55; }
  .check-body p span { margin-right: 10px; color: #6487aa; }
  .check-conclusion { color: #bed4e7 !important; }
  .check-handler { display: flex; justify-content: space-between; margin-top: 8px; color: #6686a6; font-size: 11px; }
  .empty-risk-record { padding: 70px 0; color: #62809e; text-align: center; }
  .product-card { min-height: 420px; }
  .product-summary { padding: 13px 16px; color: #8caccc; background: rgba(34, 105, 175, .16); border-left: 3px solid #28b6ff; }
  .product-summary strong { color: #5cddff; font-size: 20px; padding: 0 5px; }
  .product-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 13px; margin-top: 18px; }
  .product-item { display: grid; grid-template-columns: 42px 1fr 58px; align-items: center; padding: 15px; border-radius: 6px; border: 1px solid rgba(64, 154, 236, .2); background: linear-gradient(120deg, rgba(19, 70, 130, .55), rgba(11, 40, 83, .45)); }
  .product-icon { width: 34px; height: 34px; border-radius: 9px; display: flex; align-items: center; justify-content: center; color: #7be6ff; background: rgba(37, 146, 224, .22); border: 1px solid rgba(75, 198, 255, .28); }
  .product-item strong { color: #e1f1ff; }
  .product-item p { margin: 5px 0 0; color: #7394b7; font-size: 12px; }
  .product-item em { color: #63e4a2; font-style: normal; font-size: 12px; }
}
</style>
