export const customerColumns = (event) => {
  return [
    {
      label: "客户名称",
      key: "customerName",
      value: "",
      type: "text",
    },
    {
      label: "性别",
      key: "customerSex",
      value: "",
      type: "select",
      options: event.dict.type.sys_user_sex,
    },
    {
      label: "婚姻状况",
      key: "marriageFlag",
      value: "",
      type: "select",
      options: event.dict.type.marriage_status,
    },
    {
      label: "职业",
      key: "career",
      value: "",
      type: "text",
    },
    {
      label: "身份证号",
      key: "cardNum",
      value: "",
      type: "text",
    },
    {
      label: "联系方式",
      key: "contactPhone",
      value: "",
      type: "text",
    },
    {
      label: "户籍地址",
      key: "customerAddress",
      value: "",
      type: "text",
    },
    {
      label: "工作地址",
      key: "workAddress",
      value: "",
      type: "text",
    },
    {
      label: "年收入",
      key: "incomeYear",
      value: "",
      type: "text",
    },
  ];
};
export const companyColumns = (event) => {
  return [
    {
      label: "企业名称",
      key: "customerName",
      value: "",
      type: "text",
    },
    {
      label: "统一社会信用代码",
      key: "creditCode",
      value: "",
      type: "text",
    },
    {
      label: "法人代表",
      key: "legalPerson",
      value: "",
      type: "text",
    },
    {
      label: "法人身份证号",
      key: "legalPersonCardNum",
      value: "",
      type: "text",
    },
    {
      label: "法人联系方式",
      key: "legalPersonPhone",
      value: "",
      type: "text",
    },
    {
      label: "企业地址",
      key: "customerAddress",
      value: "",
      type: "text",
    },
    {
      label: "注册资本",
      key: "registerCapital",
      value: "",
      type: "text",
    },
    {
      label: "近一年累计营业收入",
      key: "incomeYear",
      value: "",
      type: "text",
    },
  ];
};
export const merchantColumns = (event) => {
  return [
    {
      label: "商户名称",
      key: "customerName",
      value: "",
      type: "text",
    },
    {
      label: "统一社会信用代码",
      key: "creditCode",
      value: "",
      type: "text",
    },
    {
      label: "商户经营者",
      key: "merchantManager",
      value: "",
      type: "text",
    },
    {
      label: "身份证号",
      key: "cardNum",
      value: "",
      type: "text",
    },
    {
      label: "联系方式",
      key: "contactPhone",
      value: "",
      type: "text",
    },
    {
      label: "商户地址",
      key: "customerAddress",
      value: "",
      type: "text",
    },
    {
      label: "经营场所类型",
      key: "businessPlaceType",
      value: "",
      type: "radio",
      options: event.dict.type.business_premises_type,
    },
    {
      label: "近一年累计营业收入",
      key: "incomeYear",
      value: "",
      type: "text",
    },
  ];
};
