const width_type = {
  level1: 40,
  level2: 36,
  level3: 50,
  level4: 85,
};
export const level_1_content = [
  {
    label: "授信",
    key: 'credit',
    childLabel: ['户数','金额'],
    slot: true,
    width: width_type["level1"],
    flexDirection: "column",
  },
  {
    label: "贷款",
    key: 'credit',
    childLabel: ['户数','余额'],
    slot: true,
    width: width_type["level1"],
    flexDirection: "column",
  },
];
export const level_2_content = [
  {
    label: "户数",
    width: width_type["level2"],
    flexDirection: "column",
  },
  {
    label: "金额",
    width: width_type["level2"],
    flexDirection: "column",
  },
];
export const level_3_content = [
  {
    label: "总数",
    width: width_type["level3"],
    className: "total-num",
    flexDirection: "row",
  },
  {
    label: "内",
    width: width_type["level3"],
    className: "inside",
    flexDirection: "row",
  },
  {
    label: "外",
    width: width_type["level3"],
    className: "outside",
    flexDirection: "row",
  },
];
export const level_4_content = [
  {
    label: "",
    width: width_type["level4"],
  },
  {
    label: "",
    className: "percent",
    width: width_type["level4"],
  },
  {
    label: "",
    number: true,
    className: "number",
    width: width_type["level4"],
  },
  {
    label: "",
    number: true,
    className: "number",
    width: width_type["level4"],
  },
];
