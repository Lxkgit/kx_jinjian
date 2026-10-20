export const clueSourceOptions = [
  { label: "预警系统推送", value: 1 },
  { label: "扫楼清街发现", value: 2 },
  { label: "群众举报", value: 3 },
  { label: "监管通报", value: 4 },
  { label: "舆情监测", value: 5 },
  { label: "公安协查", value: 6 },
  { label: "日常巡检", value: 7 },
  { label: "新机构排查", value: 8 },
  { label: "其他", value: 9 },
];

export const clueTypeOptions = [
  { label: "非法集资", value: 1 },
  { label: "金融诈骗", value: 2 },
  { label: "违规借贷", value: 3 },
  { label: "不法中介", value: 4 },
  { label: "其他", value: 5 },
];

export const riskLevelOptions = [
  { label: "高", value: 1 },
  { label: "中", value: 2 },
  { label: "低", value: 3 },
];

export const reviewOptions = [
  { label: "草稿", value: 0 },
  { label: "待复核", value: 1 },
  { label: "复核通过", value: 2 },
  { label: "退回补正", value: 3 },
];

export const transferStatusOptions = [
  { label: "待移送", value: 0 },
  { label: "已移送", value: 1 },
];

export const feedbackStatusOptions = [
  { label: "待反馈", value: 0 },
  { label: "已反馈", value: 1 },
];

export function optionLabel(options, value, fallback = "-") {
  const item = options.find(option => String(option.value) === String(value));
  return item ? item.label : fallback;
}

export function reviewTagType(value) {
  if (value === 2) return "success";
  if (value === 3) return "danger";
  if (value === 0) return "info";
  return "warning";
}

export function riskTagType(value) {
  if (value === 1) return "danger";
  if (value === 2) return "warning";
  return "";
}

export function nowText() {
  const date = new Date();
  const pad = value => String(value).padStart(2, "0");
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
}
