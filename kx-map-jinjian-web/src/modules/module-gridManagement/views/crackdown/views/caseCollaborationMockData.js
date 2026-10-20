const seeds = [
  ['DA-202608-101', '湖滨街道高端康养项目推介涉非风险', '养老领域涉非', 'elderly_illegal', '康养推介+会员预付', '杭州市', '上城区', '湖滨街道', '东坡路社区', '中国工商银行杭州湖滨支行', '杭州颐享康养服务有限公司', '91330102MA2KX8R71Q', '延安路258号湖滨商务楼5层', '上城区金融办', '群众举报+街道排查', '约1,260万元', 'high', 'processing', '2026-08-18 16:40', '条线通报', '已发布', 186, '整改督办', '整改中', 62, '周明远', '2026-08-25'],
  ['DA-202608-102', '四季青街道直播电商供应链返利风险', '非法集资', 'illegal_fund', '直播招商+供应链返利', '杭州市', '上城区', '四季青街道', '钱江苑社区', '杭州银行钱江支行', '杭州云贸供应链管理有限公司', '91330102MA7L6C9X3H', '钱江路1366号华润大厦A座1208室', '上城区处非办', '预警系统推送', '约2,480万元', 'high', 'pending', '2026-08-17 11:25', '内部培训', '待审核', 93, '线索指导', '待签收', 10, '陈思雨', '2026-08-24'],
  ['DA-202608-103', '武林街道老年客户理财沙龙违规宣传', '非法集资', 'illegal_fund', '理财沙龙+保本承诺', '杭州市', '拱墅区', '武林街道', '竹竿巷社区', '中国建设银行杭州武林支行', '浙江嘉和财富管理咨询有限公司', '91330105MA2H3Q8D6P', '体育场路288号建银中心7层', '拱墅区金融办', '银行网点报告', '约780万元', 'medium', 'archived', '2026-08-16 09:50', '宣传宣教', '已发布', 142, '销项复核', '已销项', 100, '沈佳宁', '2026-08-16'],
  ['DA-202608-104', '拱宸桥街道沿街商铺违规助贷整治', '不法中介', 'illegal_agent', '电话营销+包装征信', '杭州市', '拱墅区', '拱宸桥街道', '桥西社区', '中国农业银行杭州拱宸桥支行', '杭州融捷商务信息咨询有限公司', '91330105MA8G4U2N8K', '上塘路958号水晶城商务楼303室', '拱墅区市场监管局', '扫楼清街发现', '约360万元', 'medium', 'processing', '2026-08-15 15:10', '内部培训', '已发布', 117, '整改督办', '待复核', 100, '汪子涵', '2026-08-20'],
  ['DA-202608-105', '翠苑街道科技园助贷中介批量获客风险', '不法中介', 'illegal_agent', '园区获客+贷款包装', '杭州市', '西湖区', '翠苑街道', '翠苑一区社区', '中国银行杭州高新技术开发区支行', '杭州企信融资服务有限公司', '91330106MA2J7V5B4M', '文三路478号华星时代广场6层', '西湖区金融办', '舆情监测+投诉转办', '约920万元', 'high', 'processing', '2026-08-14 18:05', '条线通报', '已发布', 208, '线索指导', '整改中', 78, '陆文博', '2026-08-22'],
  ['DA-202608-106', '蒋村街道私募基金超范围募集核查', '非法集资', 'illegal_fund', '私募推介+不特定对象', '杭州市', '西湖区', '蒋村街道', '河滨社区', '浙商银行杭州城西支行', '浙江澄远股权投资管理有限公司', '91330106MA2B0D7F9C', '双龙街199号金色西溪商务中心2号楼', '西湖区处非办', '监管通报', '约3,600万元', 'high', 'pending', '2026-08-13 14:30', '条线通报', '待审核', 76, '整改督办', '已超期', 45, '俞安琪', '2026-08-18'],
  ['DA-202608-107', '长河街道数字藏品投资返利风险处置', '非法集资', 'illegal_fund', '数字藏品+拉新返利', '杭州市', '滨江区', '长河街道', '江汉社区', '南京银行杭州滨江科技支行', '杭州数藏网络科技有限公司', '91330108MA2KQ9T51E', '江虹路611号上峰电商产业园2号楼', '滨江区金融办', '公安协查', '约1,850万元', 'high', 'processing', '2026-08-12 10:15', '内部培训', '已发布', 164, '回头看复核', '待复核', 100, '方启航', '2026-08-23'],
  ['DA-202608-108', '西兴街道企业贷款服务费争议核查', '不法中介', 'illegal_agent', '企业助贷+前置收费', '杭州市', '滨江区', '西兴街道', '西陵社区', '宁波银行杭州滨江支行', '杭州盛融企业服务有限公司', '91330108MA7C3Y6J2R', '阡陌路459号聚光中心B座402室', '滨江区市场监管局', '12345投诉转办', '约210万元', 'medium', 'archived', '2026-08-11 17:20', '宣传宣教', '已发布', 69, '销项复核', '已销项', 100, '顾晨曦', '2026-08-14'],
  ['DA-202608-109', '白杨街道校园贷营销线索核查', '违规借贷', 'illegal_loan', '校园推广+消费分期', '杭州市', '钱塘区', '白杨街道', '云滨社区', '中国邮政储蓄银行杭州下沙支行', '杭州轻享消费服务有限公司', '91330114MA2L1S8P7W', '2号大街1158号高校生活区周边', '钱塘区教育局', '高校保卫处通报', '约95万元', 'medium', 'processing', '2026-08-10 13:45', '宣传宣教', '已发布', 132, '线索指导', '整改中', 55, '章若琳', '2026-08-21'],
  ['DA-202608-110', '下沙街道跨境电商代运营返利投诉处置', '非法集资', 'illegal_fund', '代运营+固定回报', '杭州市', '钱塘区', '下沙街道', '东方社区', '杭州联合银行下沙支行', '杭州海拓跨境电子商务有限公司', '91330114MA2KG6N83A', '金沙大道600号东部国际商务中心西峰8层', '钱塘区处非办', '群众举报', '约680万元', 'medium', 'archived', '2026-08-09 09:35', '内部培训', '已发布', 88, '回头看复核', '待复核', 100, '余泽宇', '2026-08-19'],
  ['DA-202608-111', '首南街道商务楼宇助贷中介联合检查', '不法中介', 'illegal_agent', '商务楼宇+征信修复', '宁波市', '鄞州区', '首南街道', '格兰春天社区', '宁波银行鄞州中心区支行', '宁波汇诚商务咨询有限公司', '91330212MA2K7F6V9D', '天童南路575号罗蒙大厦12层', '鄞州区金融发展服务中心', '日常巡检', '约430万元', 'medium', 'archived', '2026-08-08 16:05', '内部培训', '已发布', 61, '销项复核', '已销项', 100, '林晓舟', '2026-08-12'],
  ['DA-202608-112', '东栅街道农产品订单返利风险排查', '非法集资', 'illegal_fund', '订单农业+收益承诺', '嘉兴市', '南湖区', '东栅街道', '云阳社区', '嘉兴银行南湖支行', '嘉兴禾兴农业科技有限公司', '91330402MA2J9R4C6T', '中环南路富润大厦901室', '南湖区处非办', '网格员巡查', '约320万元', 'medium', 'pending', '2026-08-07 10:40', '宣传宣教', '待审核', 34, '线索指导', '待签收', 5, '徐嘉禾', '2026-08-20'],
  ['DA-202608-113', '迪荡街道企业融资顾问违规收费核查', '不法中介', 'illegal_agent', '融资顾问+前置收费', '绍兴市', '越城区', '迪荡街道', '都泗社区', '绍兴银行城东支行', '绍兴企融信息咨询有限公司', '91330602MA2D8P5L7X', '梅龙湖路56号财源中心10层', '越城区市场监管局', '企业投诉', '约160万元', 'low', 'canceled', '2026-08-06 14:15', '内部培训', '已发布', 27, '销项复核', '已销项', 100, '冯子墨', '2026-08-10'],
  ['DA-202608-114', '城中街道沿街门店民间借贷广告清理', '违规借贷', 'illegal_loan', '沿街广告+快速放款', '金华市', '婺城区', '城中街道', '明月楼社区', '金华银行江北支行', '金华快捷商务服务部', '92330702MA8E5C1Q4U', '解放东路238号沿街商铺', '婺城区综合行政执法局', '扫楼清街发现', '约35万元', 'low', 'canceled', '2026-08-05 09:20', '宣传宣教', '已驳回', 18, '整改督办', '已超期', 80, '何一帆', '2026-08-15']
].map(row => {
  const [code, title, type, caseType, tag, city, district, street, community, org, entity, credit, address, department, source, amount, risk, status, updated, level, publishStatus, useCount, workType, workStatus, progress, person, deadline] = row
  return { code, title, type, caseType, tag, city, district, street, community, org, entity, credit, address, department, source, amount, risk, status, updated, level, publishStatus, useCount, workType, workStatus, progress, person, deadline }
})

const riskLabels = { high: '高风险', medium: '中风险', low: '低风险' }
const statusLabels = { pending: '待补证', processing: '处置中', archived: '已归档', canceled: '已销号' }

export const collaborationStreetOptions = [...new Set(seeds.map(item => item.street))]

export const extraCaseFiles = seeds.map(item => ({
  code: item.code, name: `${item.title}案件`, caseTypeLabel: item.type, caseType: item.caseType,
  risk: item.risk, riskLabel: riskLabels[item.risk], region: `浙江省${item.city}${item.district}${item.street}${item.community}`,
  org: item.org, status: item.status, statusLabel: statusLabels[item.status], updatedAt: item.updated,
  entityName: item.entity, creditCode: item.credit, address: `浙江省${item.city}${item.district}${item.street}${item.address}`,
  department: item.department, clueSource: item.source, amount: item.amount,
  summary: `排查发现${item.entity}在${item.street}开展“${item.tag.replace('+', '、')}”相关活动，涉及金额${item.amount}。属地已联合责任网点完成主体核验、宣传材料固定和重点账户排摸，并根据风险等级持续推进分类处置。`,
  disposalMeasures: ['完成经营场所现场核查和证据固定', '由责任网点开展关联账户及重点客户排摸', item.risk === 'high' ? '纳入区级重点风险主体持续监测' : '责令限期整改并安排回头看检查'],
  evidenceList: ['现场核查照片', '主体登记信息及宣传材料', '责任网点账户排摸摘要', '部门联合核查记录'],
  timeline: [
    { time: `${item.updated.slice(0, 10)} 09:10`, content: `${item.source}发现风险特征，属地完成线索登记。`, type: 'normal' },
    { time: `${item.updated.slice(0, 10)} 13:40`, content: `${item.department}联合${item.org}开展核查并固定材料。`, type: 'normal' },
    { time: item.updated, content: `形成阶段性处置意见，当前状态更新为“${statusLabels[item.status]}”。`, type: ['archived', 'canceled'].includes(item.status) ? 'success' : 'normal' }
  ]
}))

export const extraCaseRepository = seeds.map((item, index) => ({
  id: 101 + index, title: `${item.title}处置案例`, type: item.type, tag: item.tag, street: item.street,
  level: item.level, status: item.publishStatus, code: item.code, count: item.useCount, time: item.updated,
  area: `浙江省-${item.city}-${item.district}-${item.street}`,
  desc: `${item.updated.slice(0, 10)}，${item.department}根据${item.source}反映的情况，对${item.entity}开展联合核查。现场围绕经营资质、宣传话术、资金流向和目标客群逐项取证，由${item.org}同步完成账户排摸。经研判，该事项风险等级为${riskLabels[item.risk]}，目前已按“${statusLabels[item.status]}”要求落实分类处置。`,
  training: `识别“${item.tag}”场景的典型风险特征\n规范现场检查和电子证据固定流程\n落实属地部门与责任网点协同核查机制`,
  action: `第一时间核验主体及经营地址\n同步排摸关联账户和重点客户\n根据${riskLabels[item.risk]}落实差异化处置`,
  imgs: [], files: [{ name: `${item.street}现场核查记录.pdf` }, { name: `${item.entity}账户排摸摘要.xlsx` }],
  newsList: item.publishStatus === '已发布' ? [{ type: '简报', title: `${item.district}${item.street}开展${item.type}风险专项核查`, meta: `${item.department} | ${item.updated.slice(0, 10)}` }] : []
}))

export const extraWorkRecords = seeds.map((item, index) => ({
  id: `ZD-202608-${String(101 + index).padStart(3, '0')}`,
  subject: `关于${item.street}${item.title}的${item.workType}单`, sourceType: index % 2 ? '扫楼上报' : '风险线索',
  sourceNo: `${index % 2 ? 'SL' : 'XS'}-202608-${String(51 + index).padStart(3, '0')}`, sourceTitle: item.title,
  reportUnit: item.org, reportTime: item.updated, sourceContent: `${item.source}发现${item.entity}存在“${item.tag}”相关风险特征，需属地和责任网点协同核查。`,
  type: item.workType, issueUnit: item.department, receiveUnit: item.org, street: item.street, person: item.person,
  deadline: item.deadline, status: item.workStatus, progress: item.progress,
  rectifyTarget: `核清${item.entity}经营活动、目标客群及资金往来情况，推动风险事项形成闭环。`,
  rectifyRequirement: `围绕${item.tag}场景开展现场核查，固定宣传材料，排摸关联账户和重点客户，并按节点反馈整改进展。`,
  feedbackMaterial: '现场检查记录、主体信息核验表、账户排摸摘要、整改前后对比材料。',
  reviewPoint: `材料是否完整，${riskLabels[item.risk]}对应的管控措施是否落实，相关风险是否实质消除。`,
  attachments: [`${item.street}指导单.pdf`, '联合核查反馈模板.docx'],
  latestFeedback: item.progress >= 50 ? { type: item.progress === 100 ? '销项申请' : '整改反馈', content: item.progress === 100 ? '现场核查和账户排摸已完成，相关整改措施均已落实，现申请复核。' : `已完成主体核验和首轮账户排摸，整体进度${item.progress}%，剩余材料正在补充。`, result: item.progress === 100 ? '已完成整改' : '持续整改中', time: item.updated } : null,
  records: [
    { time: `${item.updated.slice(0, 10)} 09:20`, title: '指导下发', content: `${item.department}向责任网点下发协同处置任务。`, user: '监管经办人' },
    { time: `${item.updated.slice(0, 10)} 14:10`, title: '任务签收', content: `${item.org}签收任务并明确专人负责。`, user: item.person },
    ...(item.progress >= 50 ? [{ time: item.updated, title: item.progress === 100 ? '提交复核' : '反馈进展', content: `责任网点反馈当前处置进度${item.progress}%。`, user: item.person }] : [])
  ]
}))
