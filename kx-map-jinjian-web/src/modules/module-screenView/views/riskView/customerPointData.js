const typeDefaults = {
  personal: {
    typeName: "个人客户",
    manager: "陈雨桐",
    products: ["手机银行", "市民卡", "活期储蓄", "定期存款", "信用卡", "稳健型理财"],
    tags: ["高净值", "代发客户", "理财偏好", "活跃客户"],
  },
  enterprise: {
    typeName: "企业客户",
    manager: "林致远",
    products: ["企业网银", "结算账户", "流动资金贷款", "代发工资", "单位结算卡", "票据服务"],
    tags: ["专精特新", "制造业", "纳税A级", "成长型企业"],
  },
  merchant: {
    typeName: "商户客户",
    manager: "周嘉宁",
    products: ["收单", "聚合支付", "经营贷", "商户管家", "结算账户", "手机银行"],
    tags: ["优质商户", "交易活跃", "社区生活", "经营稳定"],
  },
};

const customerProfiles = {
  personal: {
    assets: ["86 万元", "132 万元", "205 万元", "318 万元", "476 万元", "1,260 万元"],
    ratings: ["A", "A", "A+", "A", "AA-"],
    opportunities: ["稳健型理财配置", "家庭资产综合规划", "消费信贷额度提升", "社保卡综合服务"],
    amounts: ["20万元", "35万元", "50万元", "80万元"],
    visitResults: ["核实职业及收入情况，近期有稳健理财配置需求", "联系方式及住址信息无变化，手机银行使用正常", "客户家庭资金安排稳定，关注定期产品到期衔接"],
  },
  enterprise: {
    assets: ["2,860 万元", "4,750 万元", "6,320 万元", "8,960 万元", "1.28 亿元"],
    ratings: ["A+", "AA-", "AA", "AA-", "A+"],
    opportunities: ["流动资金授信", "供应链结算服务", "代发工资扩面", "设备更新贷款"],
    amounts: ["300万元", "500万元", "800万元", "1,200万元"],
    visitResults: ["生产经营正常，订单较去年同期小幅增长", "完成实际经营地址核验，应收账款周转稳定", "企业近期有设备更新计划，融资需求待测算"],
  },
  merchant: {
    assets: ["58 万元", "83 万元", "109 万元", "146 万元", "218 万元"],
    ratings: ["A", "A-", "A", "A+", "A"],
    opportunities: ["聚合收单升级", "商户经营贷", "交易流水归集", "员工工资代发"],
    amounts: ["10万元", "20万元", "30万元", "50万元"],
    visitResults: ["门店经营正常，日均交易流水较为稳定", "完成经营场所和收款码核验，暂无异常交易", "节假日客流增长明显，有短期备货资金需求"],
  },
};

const riskTypeOptions = {
  personal: ["涉诈", "异常交易", "涉非"],
  enterprise: ["异常交易", "涉非", "涉诈"],
  merchant: ["异常交易", "涉诈", "涉非"],
};

const riskScenarios = {
  personal: {
    涉非: "近期多次向非持牌投资咨询机构转账，资金用途需进一步核实",
    涉诈: "夜间向新开立的异地账户连续转账，交易行为与历史习惯存在偏离",
    异常交易: "短期内资金快进快出频次上升，交易对手较为分散",
  },
  enterprise: {
    涉非: "经营范围外出现投资咨询类收款，部分交易附言与主营业务不一致",
    涉诈: "企业账户收到多笔个人小额汇入后集中转出，需核实真实贸易背景",
    异常交易: "月度结算规模较历史均值明显上升，新增交易对手集中度较高",
  },
  merchant: {
    涉非: "收单交易中出现投资理财类备注，需核实门店是否存在代收行为",
    涉诈: "非营业时段交易笔数异常增加，部分付款账户已命中外部风险名单",
    异常交易: "退款率和异地交易占比较上月上升，交易场景需现场核验",
  },
};

function createRiskProfile(data, index, riskLevel, manager) {
  const typeOptions = riskTypeOptions[data.customerType];
  const primaryType = typeOptions[index % typeOptions.length];
  const riskTypeCount = riskLevel === "高风险" ? 3 : 2;
  const riskTypes = data.riskTypes || Array.from(
    { length: riskTypeCount },
    (_, offset) => typeOptions[(index + offset) % typeOptions.length]
  );
  const secondRiskType = riskTypes[1] || riskTypes[0];
  const thirdRiskType = riskTypes[2] || riskTypes[0];
  const month = index % 3 === 0 ? "07" : "08";
  const day = String(8 + (index * 3) % 18).padStart(2, "0");
  const conclusions = {
    低风险: "已核验客户身份及交易用途，资金往来与正常生活或经营场景相符，本次预警予以排除。",
    中风险: "已完成首次联系，客户能够说明主要交易用途，部分佐证材料待补充，纳入30日持续监测。",
    高风险: "发现交易背景与客户说明存在不一致，已采取强化核验措施并移交风险管理人员进一步处置。",
  };
  const statuses = {
    低风险: "已排除",
    中风险: "持续关注",
    高风险: "处置中",
  };
  const sources = ["交易监测模型", "反诈风险名单", "网格走访核查", "账户行为监测"];
  const evidenceMap = {
    personal: [
      `近7日向${3 + (index % 5)}名新交易对手转账${8 + (index % 9)}笔，累计${(18.6 + index * 1.7).toFixed(1)}万元`,
      `夜间交易占比${24 + (index % 6) * 7}%，较本人近90日均值上升${12 + (index % 5) * 4}个百分点`,
      `近30日资金快进快出${5 + (index % 7)}次，平均停留时间不足${2 + (index % 4)}小时`,
    ],
    enterprise: [
      `近30日结算流水${(286 + index * 37).toLocaleString()}万元，较近6个月均值增长${32 + (index % 6) * 9}%`,
      `新增异地交易对手${4 + (index % 8)}户，其中${1 + (index % 3)}户为首次发生大额往来`,
      `对公账户收到个人账户汇款${13 + (index % 11)}笔，累计${(68 + index * 8.5).toFixed(1)}万元`,
    ],
    merchant: [
      `近14日收单${126 + index * 17}笔，退款率${(4.2 + (index % 6) * 1.1).toFixed(1)}%`,
      `非营业时段交易占比${18 + (index % 7) * 5}%，异地付款账户占比${25 + (index % 5) * 6}%`,
      `单笔整数金额交易${9 + (index % 8)}笔，较上月同期增加${20 + (index % 6) * 8}%`,
    ],
  };
  const identityDigits = String(data.identity || "330000").replace(/\D/g, "");
  const riskAreaCode = /^(91|92)/.test(identityDigits)
    ? identityDigits.slice(2, 8)
    : identityDigits.slice(0, 6);
  const checkNo = (sequence) =>
    `RH${(riskAreaCode || "330000").slice(0, 4)}2026${String(index + 1).padStart(3, "0")}${sequence}`;
  const records = [
    {
      checkNo: checkNo("03"),
      date: `2026-${month}-${day}`,
      riskLevel,
      type: primaryType,
      source: sources[index % sources.length],
      summary: riskScenarios[data.customerType][primaryType],
      evidence: evidenceMap[data.customerType][index % evidenceMap[data.customerType].length],
      conclusion: conclusions[riskLevel],
      handler: manager,
      status: statuses[riskLevel],
    },
    {
      checkNo: checkNo("02"),
      date: `2026-05-${String(10 + (index * 2) % 16).padStart(2, "0")}`,
      riskLevel,
      type: secondRiskType,
      source: index % 2 ? "客户经理日常排查" : "账户行为监测",
      summary: riskScenarios[data.customerType][secondRiskType],
      evidence: evidenceMap[data.customerType][(index + 1) % evidenceMap[data.customerType].length],
      conclusion: riskLevel === "高风险"
        ? "核查发现部分交易背景材料仍不完整，维持强化监测并由风险管理人员持续跟进。"
        : riskLevel === "中风险"
          ? "已完成初步核实，主要交易用途能够说明，部分佐证材料待补充，继续观察。"
          : index % 3 === 0
            ? "已完成电话核实并补充交易背景材料，相关资金往来具有合理业务依据。"
            : "通过流水比对和客户回访完成核查，未发现需要立即阻断的风险情形。",
      handler: index % 2 ? "王璟" : manager,
      status: riskLevel === "高风险" ? "持续关注" : "已核查",
    },
    {
      checkNo: checkNo("01"),
      date: `2025-${index % 2 ? "12" : "11"}-${String(8 + (index * 3) % 17).padStart(2, "0")}`,
      riskLevel,
      type: thirdRiskType,
      source: index % 3 === 0 ? "年度客户风险评估" : "反洗钱监测规则",
      summary: riskScenarios[data.customerType][thirdRiskType],
      evidence: evidenceMap[data.customerType][(index + 2) % evidenceMap[data.customerType].length],
      conclusion: `客户身份、职业或经营信息已完成核验，当次评估维持${riskLevel}，按对应频率继续监测。`,
      handler: index % 2 ? manager : "李明哲",
      status: riskLevel === "高风险" ? "持续关注" : "已归档",
    },
  ];
  if (riskLevel === "高风险") {
    records.push({
      checkNo: `RH33002026${String(index + 1).padStart(3, "0")}00`,
      date: `2026-02-${String(7 + (index * 2) % 18).padStart(2, "0")}`,
      riskLevel,
      type: riskTypes[0],
      source: "外部风险名单交叉核验",
      summary: "交易对手命中外部关注名单，系统自动发起增强型尽职调查任务",
      evidence: `命中外部关注交易对手${1 + (index % 2)}户，关联交易${4 + (index % 5)}笔，累计金额${(42 + index * 3.6).toFixed(1)}万元`,
      conclusion: "已完成交易对手关系核实并提交风险管理部门复核，相关账户保持强化监测。",
      handler: "风险管理专员",
      status: "持续关注",
    });
  }
  records.sort((a, b) => b.date.localeCompare(a.date));
  const normalizedRecords = (data.riskChecks || records).map((record) => ({
    ...record,
    riskLevel: record.riskLevel || riskLevel,
  }));
  const levelWeight = { 低风险: 1, 中风险: 2, 高风险: 3 };
  const resolvedRiskLevel = normalizedRecords.reduce(
    (highest, record) =>
      levelWeight[record.riskLevel] > levelWeight[highest]
        ? record.riskLevel
        : highest,
    "低风险"
  );
  const resolvedRiskTypes = [
    ...new Set(normalizedRecords.map((record) => record.type).filter(Boolean)),
  ];
  return {
    riskLevel: resolvedRiskLevel,
    riskTypes: resolvedRiskTypes,
    riskChecks: normalizedRecords,
  };
}

function createCustomer(data, index) {
  const defaults = typeDefaults[data.customerType];
  const profile = customerProfiles[data.customerType];
  const no = String(index + 1).padStart(3, "0");
  const identityDigits = String(data.identity || "330000").replace(/\D/g, "");
  const areaCode = (/^(91|92)/.test(identityDigits)
    ? identityDigits.slice(2, 8)
    : identityDigits.slice(0, 6)) || "330000";
  const profileIndex = index % profile.assets.length;
  const firstOpportunity = profile.opportunities[index % profile.opportunities.length];
  const secondOpportunity = profile.opportunities[(index + 1) % profile.opportunities.length];
  const manager = data.manager || defaults.manager;
  const riskLevel = data.riskLevel ||
    (index % 11 === 8 ? "高风险" : index % 7 === 5 ? "中风险" : "低风险");
  const riskProfile = createRiskProfile(data, index, riskLevel, manager);
  const industryMap = {
    personal: ["企事业单位职员", "个体经营者", "专业技术人员", "企业管理人员"],
    enterprise: ["智能制造", "批发和供应链", "信息技术服务", "文化旅游"],
    merchant: ["生活零售", "餐饮服务", "文旅住宿", "专业市场商户"],
  };
  const sourceOptions = ["网格走访", "存量客户转介", "厅堂识别", "批量拓客", "线上渠道"];
  const month = String(2 + (index % 6)).padStart(2, "0");
  const day = String(5 + (index * 3) % 20).padStart(2, "0");
  return {
    ...defaults,
    ...data,
    pointKind: "customer",
    customerNo: data.customerNo || `KH${areaCode}${2023 + (index % 3)}${no}`,
    riskLevel,
    ...riskProfile,
    customerStatus: data.customerStatus || (riskLevel === "高风险" ? "重点关注" : "正常服务"),
    establishDate: data.establishDate || `202${3 + (index % 3)}-${String(1 + (index % 9)).padStart(2, "0")}-${String(5 + (index * 2) % 20).padStart(2, "0")}`,
    customerSource: data.customerSource || sourceOptions[index % sourceOptions.length],
    industry: data.industry || industryMap[data.customerType][index % industryMap[data.customerType].length],
    rating: data.rating || profile.ratings[index % profile.ratings.length],
    assets: data.assets || profile.assets[profileIndex],
    journey: data.journey || [
      { time: `2025-${month}`, title: "客户建档", desc: "完成身份、联系方式及经营地址核验" },
      { time: `2026-${String(1 + (index % 5)).padStart(2, "0")}`, title: "首次走访", desc: profile.visitResults[(index + 1) % profile.visitResults.length] },
      { time: `2026-${String(6 + (index % 3)).padStart(2, "0")}`, title: "需求更新", desc: `识别${firstOpportunity}需求并持续跟进` },
      { time: "2026-08", title: riskLevel === "低风险" ? "服务回访" : "风险复核", desc: riskLevel === "低风险" ? "完成近期服务回访，客户关系保持稳定" : "结合监测提示完成客户风险信息复核" },
    ],
    visits: data.visits || [
      { time: `2026-08-${day}`, staff: manager, mode: index % 3 === 0 ? "网点面谈" : "上门走访", result: profile.visitResults[index % profile.visitResults.length] },
      { time: `2026-0${5 + (index % 2)}-${String(8 + (index * 2) % 18).padStart(2, "0")}`, staff: index % 2 ? "王璟" : "李明哲", mode: "电话回访", result: "基础信息核验一致，已更新本次联系记录" },
      { time: `2026-03-${String(6 + (index * 3) % 20).padStart(2, "0")}`, staff: manager, mode: index % 2 ? "视频回访" : "上门走访", result: profile.visitResults[(index + 2) % profile.visitResults.length] },
    ],
    opportunities: data.opportunities || [
      { name: firstOpportunity, amount: profile.amounts[index % profile.amounts.length], stage: index % 3 === 0 ? "需求确认" : "方案沟通", owner: manager },
      { name: secondOpportunity, amount: profile.amounts[(index + 1) % profile.amounts.length], stage: index % 2 ? "持续跟进" : "初步接洽", owner: index % 2 ? "王璟" : "李明哲" },
      { name: profile.opportunities[(index + 2) % profile.opportunities.length], amount: profile.amounts[(index + 2) % profile.amounts.length], stage: index % 3 === 1 ? "方案拟定" : "线索储备", owner: manager },
    ],
    tasks: data.tasks || [
      { name: index % 2 ? "补充客户经营信息" : "完成客户年度核验", deadline: `2026-08-${String(20 + (index % 9)).padStart(2, "0")}`, status: "进行中", owner: manager },
      { name: `跟进${firstOpportunity}`, deadline: `2026-09-${String(5 + (index % 18)).padStart(2, "0")}`, status: index % 4 === 0 ? "已安排" : "待处理", owner: index % 2 ? "王璟" : "李明哲" },
      { name: riskLevel === "低风险" ? "更新产品适配建议" : "完成风险核查材料归档", deadline: `2026-09-${String(12 + (index % 15)).padStart(2, "0")}`, status: index % 3 === 0 ? "进行中" : "待处理", owner: manager },
    ],
  };
}

const additionalCustomerSeeds = [
  { lon: 120.2636, lat: 30.1841, customerType: "enterprise", name: "杭州萧山智造科技有限公司", phone: "0571-82****36", identity: "91330109MA2H****6K", address: "杭州市萧山区经济技术开发区", grid: "萧山开发区产业网格", branch: "杭州萧山支行", assets: "7,920 万元", rating: "AA" },
  { lon: 120.3018, lat: 30.4219, customerType: "merchant", name: "良渚社区生活馆", phone: "137****5062", identity: "92330110MA2J****8R", address: "杭州市余杭区良渚街道", grid: "良渚街道商圈网格", branch: "杭州余杭支行", assets: "118 万元" },
  { lon: 119.7247, lat: 30.2331, customerType: "personal", name: "许安然", phone: "139****3105", identity: "330185********3027", address: "杭州市临安区锦城街道", grid: "锦城街道综合网格", branch: "杭州临安支行", assets: "286 万元" },
  { lon: 120.1908, lat: 30.2534, customerType: "enterprise", name: "杭州钱江数科服务有限公司", phone: "0571-87****19", identity: "91330102MA2B****3P", address: "杭州市上城区四季青街道新业路", grid: "钱江新城金融综合网格", branch: "杭州分行营业部", assets: "6,780 万元", rating: "AA-", manager: "沈嘉禾" },
  { lon: 120.1682, lat: 30.3086, customerType: "merchant", name: "运河邻里生鲜店", phone: "136****5827", identity: "92330105MA2K****6T", address: "杭州市拱墅区小河街道桥西直街", grid: "小河街道社区商业网格", branch: "杭州拱墅支行", assets: "136 万元", manager: "陆欣怡" },
  { lon: 120.1296, lat: 30.2718, customerType: "personal", name: "周砚宁", phone: "138****4176", identity: "330106********2639", address: "杭州市西湖区翠苑街道文一路", grid: "翠苑街道综合网格", branch: "杭州西湖支行", assets: "426 万元", manager: "陈雨桐" },
  { lon: 120.2115, lat: 30.2069, customerType: "enterprise", name: "杭州滨江云链科技有限公司", phone: "0571-86****52", identity: "91330108MA2C****8W", address: "杭州市滨江区长河街道江虹路", grid: "长河数字产业网格", branch: "杭州滨江支行", assets: "1.16 亿元", rating: "AA", manager: "林致远" },
  { lon: 120.2017, lat: 30.1816, customerType: "merchant", name: "星光大道轻食工坊", phone: "150****9361", identity: "92330108MA2L****4C", address: "杭州市滨江区西兴街道星光大道", grid: "西兴商圈综合网格", branch: "杭州滨江支行", assets: "208 万元", manager: "周嘉宁" },
  { lon: 120.2814, lat: 30.2048, customerType: "personal", name: "顾景川", phone: "137****2058", identity: "330109********6812", address: "杭州市萧山区北干街道金城路", grid: "北干街道综合网格", branch: "杭州萧山支行", assets: "512 万元", manager: "叶思源" },
  { lon: 120.2469, lat: 30.1872, customerType: "merchant", name: "钱江世纪城便利中心", phone: "159****7480", identity: "92330109MA2M****1N", address: "杭州市萧山区盈丰街道市心北路", grid: "钱江世纪城商务网格", branch: "杭州萧山支行", assets: "174 万元", manager: "周嘉宁" },
  { lon: 120.0041, lat: 30.2965, customerType: "enterprise", name: "杭州未来智谷信息技术有限公司", phone: "0571-89****33", identity: "91330110MA2D****5R", address: "杭州市余杭区仓前街道文一西路", grid: "未来科技城产业网格", branch: "杭州余杭支行", assets: "9,460 万元", rating: "AA+", manager: "林致远" },
  { lon: 120.1169, lat: 30.4126, customerType: "personal", name: "宋予安", phone: "135****8642", identity: "330110********5124", address: "杭州市余杭区仁和街道和平路", grid: "仁和街道综合网格", branch: "杭州余杭支行", assets: "198 万元", manager: "沈嘉禾" },
  { lon: 120.3045, lat: 30.4268, customerType: "enterprise", name: "杭州临平新材制造有限公司", phone: "0571-88****70", identity: "91330113MA2E****9A", address: "杭州市临平区南苑街道迎宾路", grid: "南苑产业综合网格", branch: "杭州临平支行", assets: "7,350 万元", rating: "AA-", manager: "许文博" },
  { lon: 120.3138, lat: 30.3812, customerType: "merchant", name: "乔司服装供应链商行", phone: "158****3264", identity: "92330113MA2F****7H", address: "杭州市临平区乔司街道杭海路", grid: "乔司专业市场网格", branch: "杭州临平支行", assets: "264 万元", manager: "陆欣怡" },
  { lon: 120.3546, lat: 30.3094, customerType: "enterprise", name: "杭州钱塘生物医药研发有限公司", phone: "0571-86****91", identity: "91330114MA2G****2J", address: "杭州市钱塘区白杨街道科技园路", grid: "下沙医药产业网格", branch: "杭州钱塘支行", assets: "1.08 亿元", rating: "AA", manager: "林致远" },
  { lon: 120.3381, lat: 30.3175, customerType: "personal", name: "林书言", phone: "136****1093", identity: "330114********3945", address: "杭州市钱塘区白杨街道学林街", grid: "白杨街道综合网格", branch: "杭州钱塘支行", assets: "156 万元", manager: "陈雨桐" },
  { lon: 119.9524, lat: 30.0493, customerType: "enterprise", name: "杭州富阳高端装备有限公司", phone: "0571-63****48", identity: "91330111MA2H****6M", address: "杭州市富阳区银湖街道富闲路", grid: "银湖产业网格", branch: "杭州富阳支行", assets: "8,280 万元", rating: "AA", manager: "许文博" },
  { lon: 119.9472, lat: 30.0548, customerType: "merchant", name: "富春江畔文创茶馆", phone: "151****6728", identity: "92330111MA2J****3Q", address: "杭州市富阳区富春街道江滨西大道", grid: "富春文旅商业网格", branch: "杭州富阳支行", assets: "112 万元", manager: "周嘉宁" },
  { lon: 119.7312, lat: 30.2397, customerType: "enterprise", name: "临安青山湖微电子有限公司", phone: "0571-61****25", identity: "91330112MA2K****8D", address: "杭州市临安区青山湖科技城", grid: "青山湖科技城产业网格", branch: "杭州临安支行", assets: "1.32 亿元", rating: "AA+", manager: "林致远" },
  { lon: 119.6857, lat: 29.7974, customerType: "merchant", name: "桐庐富春民宿", phone: "137****4916", identity: "92330122MA2L****5E", address: "杭州市桐庐县城南街道大奇山路", grid: "城南文旅服务网格", branch: "杭州桐庐支行", assets: "189 万元", manager: "陆欣怡" },
  { lon: 119.0412, lat: 29.6087, customerType: "enterprise", name: "淳安千岛湖生态食品有限公司", phone: "0571-64****83", identity: "91330127MA2M****7K", address: "杭州市淳安县千岛湖镇睦州大道", grid: "千岛湖生态产业网格", branch: "杭州淳安支行", assets: "4,920 万元", rating: "A+", manager: "沈嘉禾" },
  { lon: 119.2818, lat: 29.4746, customerType: "personal", name: "夏知远", phone: "139****7521", identity: "330182********1756", address: "杭州市建德市新安江街道新安路", grid: "新安江街道综合网格", branch: "杭州建德支行", assets: "238 万元", manager: "叶思源" },
  { lon: 121.8446, lat: 29.8992, customerType: "enterprise", name: "宁波港城精密制造有限公司", phone: "0574-86****52", identity: "91330206MA2G****4M", address: "宁波市北仑区新碶街道", grid: "北仑临港产业网格", branch: "宁波北仑支行", assets: "9,680 万元", rating: "AA+" },
  { lon: 121.2665, lat: 30.1698, customerType: "merchant", name: "慈溪青瓷茶社", phone: "158****6209", identity: "92330282MA2K****7Q", address: "宁波市慈溪市浒山街道", grid: "浒山街道商业网格", branch: "宁波慈溪支行", assets: "142 万元" },
  { lon: 120.8112, lat: 27.9327, customerType: "enterprise", name: "温州龙湾数字产业有限公司", phone: "0577-86****18", identity: "91330303MA2L****2D", address: "温州市龙湾区蒲州街道", grid: "蒲州数字经济网格", branch: "温州龙湾支行", assets: "6,350 万元", rating: "AA" },
  { lon: 120.6219, lat: 27.9662, customerType: "merchant", name: "瓯海梧田邻里超市", phone: "135****8491", identity: "92330304MA2C****5A", address: "温州市瓯海区梧田街道", grid: "梧田街道社区网格", branch: "温州瓯海支行", assets: "96 万元" },
  { lon: 120.6551, lat: 27.7784, customerType: "personal", name: "周景行", phone: "136****2748", identity: "330381********5412", address: "温州市瑞安市安阳街道", grid: "安阳街道综合网格", branch: "温州瑞安支行", assets: "328 万元" },
  { lon: 120.9706, lat: 28.1201, customerType: "enterprise", name: "乐清海创电气有限公司", phone: "0577-62****73", identity: "91330382MA2A****9T", address: "温州市乐清市城南街道", grid: "乐清电气产业网格", branch: "温州乐清支行", assets: "8,120 万元", rating: "AA+" },
  { lon: 120.6821, lat: 28.1516, customerType: "merchant", name: "永嘉瓯越农产品店", phone: "159****4370", identity: "92330324MA2B****1N", address: "温州市永嘉县南城街道", grid: "南城街道商户网格", branch: "温州永嘉支行", assets: "83 万元" },
  { lon: 120.6792, lat: 30.5205, customerType: "personal", name: "顾云舟", phone: "138****7516", identity: "330481********1834", address: "嘉兴市海宁市硖石街道", grid: "硖石街道综合网格", branch: "嘉兴海宁支行", assets: "205 万元" },
  { lon: 119.9788, lat: 30.5425, customerType: "enterprise", name: "德清莫干山文旅发展有限公司", phone: "0572-80****65", identity: "91330521MA2D****3H", address: "湖州市德清县武康街道", grid: "德清文旅产业网格", branch: "湖州德清支行", assets: "4,760 万元" },
  { lon: 120.4814, lat: 30.0817, customerType: "merchant", name: "柯桥轻纺面料商行", phone: "137****9025", identity: "92330603MA2J****6C", address: "绍兴市柯桥区中国轻纺城", grid: "轻纺城专业市场网格", branch: "绍兴柯桥支行", assets: "186 万元" },
  { lon: 119.6478, lat: 29.0791, customerType: "personal", name: "唐知远", phone: "150****6819", identity: "330702********6618", address: "金华市婺城区江北街道", grid: "江北街道综合网格", branch: "金华婺城支行", assets: "241 万元" },
  { lon: 120.0333, lat: 28.8897, customerType: "enterprise", name: "永康新锐五金科技有限公司", phone: "0579-87****41", identity: "91330784MA2M****5E", address: "金华市永康市经济开发区", grid: "永康五金产业网格", branch: "金华永康支行", assets: "7,260 万元", rating: "AA" },
  { lon: 118.8726, lat: 28.9684, customerType: "merchant", name: "衢州南孔文化书店", phone: "152****3956", identity: "92330802MA2F****8L", address: "衢州市柯城区府山街道", grid: "府山文化商圈网格", branch: "衢州柯城支行", assets: "72 万元" },
  { lon: 122.1068, lat: 30.0194, customerType: "enterprise", name: "舟山蓝海供应链有限公司", phone: "0580-20****39", identity: "91330902MA2A****7P", address: "舟山市定海区临城街道", grid: "临城港航产业网格", branch: "舟山定海支行", assets: "5,480 万元" },
  { lon: 121.1441, lat: 28.8583, customerType: "personal", name: "江闻溪", phone: "133****8250", identity: "331082********2746", address: "台州市临海市古城街道", grid: "临海古城综合网格", branch: "台州临海支行", assets: "194 万元" },
  { lon: 120.2896, lat: 28.1393, customerType: "merchant", name: "青田侨乡咖啡馆", phone: "156****1732", identity: "92331121MA2E****4W", address: "丽水市青田县鹤城街道", grid: "鹤城侨乡商业网格", branch: "丽水青田支行", assets: "109 万元" },
];

const baseCustomerPointData = [
  createCustomer({
    lon: 120.1551, lat: 30.2741, customerType: "personal", name: "叶知秋",
    phone: "138****2651", identity: "330102********4218", address: "杭州市上城区钱江新城",
    grid: "钱江新城综合网格", branch: "杭州分行营业部", assets: "368 万元",
    tags: ["高净值", "私行潜力", "基金偏好", "信用良好"],
  }, 0),
  createCustomer({
    lon: 121.5503, lat: 29.8746, customerType: "personal", name: "沈清禾",
    phone: "136****9173", identity: "330203********1026", address: "宁波市鄞州区东部新城",
    grid: "东部新城核心网格", branch: "宁波鄞州支行", rating: "A+", assets: "215 万元",
  }, 1),
  createCustomer({
    lon: 120.6728, lat: 28.0042, customerType: "personal", name: "林若川",
    phone: "159****3842", identity: "330302********6015", address: "温州市鹿城区滨江商务区",
    grid: "滨江街道金融网格", branch: "温州鹿城支行", assets: "176 万元",
  }, 2),
  createCustomer({
    lon: 120.5853, lat: 30.0208, customerType: "enterprise", name: "浙江越新智能装备有限公司",
    phone: "0575-88****16", identity: "91330602MA2D****7X", address: "绍兴市越城区袍江智造园",
    grid: "袍江产业园网格", branch: "绍兴越城支行", assets: "8,600 万元", rating: "AA",
    tags: ["专精特新", "智能制造", "纳税A级", "授信客户"],
  }, 3),
  createCustomer({
    lon: 120.7064, lat: 30.7627, customerType: "enterprise", name: "嘉兴禾创供应链科技有限公司",
    phone: "0573-82****90", identity: "91330401MA2B****5N", address: "嘉兴市南湖区科技城",
    grid: "南湖科技城网格", branch: "嘉兴南湖支行", assets: "5,280 万元", rating: "AA-",
  }, 4),
  createCustomer({
    lon: 120.1087, lat: 29.3151, customerType: "enterprise", name: "义乌云帆跨境电商有限公司",
    phone: "0579-85****33", identity: "91330782MA29****8P", address: "金华市义乌市国际商贸城五区",
    grid: "国际商贸城网格", branch: "义乌稠州支行", assets: "3,950 万元",
    products: ["企业网银", "跨境结算", "出口信保融资", "代发工资"],
  }, 5),
  createCustomer({
    lon: 120.0644, lat: 30.8694, customerType: "merchant", name: "太湖里生活超市",
    phone: "137****6088", identity: "92330502MA2F****6Q", address: "湖州市吴兴区太湖路188号",
    grid: "太湖街道社区网格", branch: "湖州吴兴支行", assets: "126 万元",
  }, 6),
  createCustomer({
    lon: 121.4208, lat: 28.6555, customerType: "merchant", name: "椒江海味坊",
    phone: "158****7721", identity: "92331002MA2D****3C", address: "台州市椒江区市府大道399号",
    grid: "白云街道商圈网格", branch: "台州椒江支行", assets: "98 万元",
    tags: ["优质商户", "餐饮零售", "收单活跃", "经营满五年"],
  }, 7),
  createCustomer({
    lon: 119.9166, lat: 28.4514, customerType: "merchant", name: "莲都山水民宿",
    phone: "135****4930", identity: "92331102MA2E****9M", address: "丽水市莲都区大港头镇古堰画乡",
    grid: "古堰画乡文旅网格", branch: "丽水莲都支行", assets: "152 万元",
    products: ["聚合支付", "商户收单", "文旅贷", "信用卡"],
  }, 8),
  ...additionalCustomerSeeds.map((item, index) => createCustomer(item, index + 9)),
];

const nearbyCopies = [0, 1, 2, 3];

const personalNames = [
  "陈嘉树", "王舒宁", "赵明远", "徐若溪", "吴景程", "郑知夏", "孙致和", "朱清越",
  "胡嘉言", "何书宁", "郭云帆", "林静姝", "高文博", "罗予晴", "梁泽宇", "宋安禾",
  "谢明川", "唐婉清", "许星河", "韩嘉悦", "冯景行", "曹思远", "彭语桐", "陆闻舟",
  "蒋知行", "蔡清妍", "叶承泽", "董书瑶", "潘嘉诚", "余安澜", "杜明轩", "方若宁",
  "程致远", "沈嘉禾", "戴云舒", "金予安", "夏清越", "邵文昊", "章静宜", "钱景川",
];

const enterpriseBrands = [
  "启辰", "华澜", "远望", "嘉禾", "云帆", "新越", "鼎盛", "博源", "弘毅", "景行",
  "瑞丰", "明州", "钱潮", "瓯越", "之江", "海创", "智谷", "星瀚", "德润", "恒泰",
];

const enterpriseIndustries = [
  "数字科技", "智能装备", "供应链管理", "新材料科技", "信息技术", "绿色能源", "精密制造", "生物科技",
];

const merchantBrands = [
  "邻里优选", "悦生活", "四季鲜", "拾光里", "云水间", "禾木", "江南味道", "悦享家",
  "小满", "青禾", "暖邻", "知味", "栖迟", "春和", "有间", "乐邻",
];

const merchantIndustries = [
  "生活超市", "餐饮店", "便利店", "茶饮店", "文创馆", "生鲜店", "社区服务中心", "咖啡馆",
];

const nearbyRoads = ["春和路", "景明路", "安澜路", "嘉业路", "文创路", "兴业街", "惠民路", "清宁巷"];

function cityShortName(address) {
  const match = String(address || "").match(/(?:浙江省)?([^省市区县]+市)/);
  return match ? match[1].replace("市", "") : "浙江";
}

function seededRandom(seed) {
  const value = Math.sin(seed * 12.9898 + 78.233) * 43758.5453;
  return value - Math.floor(value);
}

function createIrregularOffset(baseIndex, copyIndex) {
  const seed = (baseIndex + 1) * 31 + (copyIndex + 1) * 17;
  const angle = seededRandom(seed) * Math.PI * 2;
  const distance = 0.006 + seededRandom(seed + 19) * 0.03;
  const longitudeOffset = Math.cos(angle) * distance;
  const latitudeOffset = Math.sin(angle) * distance * (0.72 + seededRandom(seed + 37) * 0.36);
  return [longitudeOffset, latitudeOffset];
}

function createNearbyCustomer(base, baseIndex, copyIndex) {
  const sequence = baseIndex * nearbyCopies.length + copyIndex;
  const offset = createIrregularOffset(baseIndex, copyIndex);
  const city = cityShortName(base.address);
  const road = nearbyRoads[sequence % nearbyRoads.length];
  const houseNo = 18 + (sequence * 17) % 268;
  const personalName = personalNames[sequence % personalNames.length];
  const enterpriseName = `${city}${enterpriseBrands[sequence % enterpriseBrands.length]}${enterpriseIndustries[(baseIndex + copyIndex) % enterpriseIndustries.length]}有限公司`;
  const merchantName = `${city}${merchantBrands[sequence % merchantBrands.length]}${merchantIndustries[(baseIndex + copyIndex) % merchantIndustries.length]}`;
  const identityDigits = String(base.identity || "330000").replace(/\D/g, "");
  const areaCode = (/^(91|92)/.test(identityDigits)
    ? identityDigits.slice(2, 8)
    : identityDigits.slice(0, 6)) || "330000";
  const mobilePrefixes = ["133", "135", "136", "137", "138", "139", "150", "158", "159"];
  const mobile = `${mobilePrefixes[sequence % mobilePrefixes.length]}****${String(1200 + (sequence * 137) % 8700).padStart(4, "0")}`;
  const enterprisePhonePrefix = String(base.phone || "0571").split("-")[0];
  const phone = base.customerType === "enterprise"
    ? `${enterprisePhonePrefix}-${String(80 + (sequence % 19)).padStart(2, "0")}****${String(10 + (sequence * 7) % 89).padStart(2, "0")}`
    : mobile;
  const identity = base.customerType === "personal"
    ? `${areaCode}********${String(1000 + (sequence * 83) % 8999).padStart(4, "0")}`
    : `${base.customerType === "enterprise" ? "91" : "92"}${areaCode}MA2${String.fromCharCode(65 + (sequence % 26))}****${sequence % 10}${String.fromCharCode(65 + ((sequence + 7) % 26))}`;

  return createCustomer({
    lon: Number((base.lon + offset[0]).toFixed(6)),
    lat: Number((base.lat + offset[1]).toFixed(6)),
    customerType: base.customerType,
    name: base.customerType === "personal"
      ? personalName
      : base.customerType === "enterprise" ? enterpriseName : merchantName,
    phone,
    identity,
    address: `${base.address}附近${road}${houseNo}号`,
    grid: base.grid,
    branch: base.branch,
    manager: base.manager,
  }, baseCustomerPointData.length + sequence);
}

// 保留原始客户，并为每条数据补充 4 条同区域客户，总量为原来的 5 倍。
export const customerPointData = baseCustomerPointData.reduce((result, customer, baseIndex) => {
  result.push(customer);
  nearbyCopies.forEach((copyIndex) => {
    result.push(createNearbyCustomer(customer, baseIndex, copyIndex));
  });
  return result;
}, []);
