/* filter */
export const customerSearchColumns = (event) => {
  return [
    {
      label: "客户类型",
      key: "customerCategory",
      type: "select",
      options: event.dict.type.custom_type,
    },
    {
      label: "客户名称",
      key: "customerName",
      type: "text",
    },
    {
      label: "客户内码",
      key: "customerInterNum",
      type: "text",
    },
    {
      label: "责任机构",
      key: "dutyDeptId",
      value: "",
      type: "cascader",
      props: {
        label: "label",
        value: "id",
        children: "children",
        emitPath: false,
      },
      options: event.deptOptions,
    },
    {
      label: "所属网格",
      key: "belongGrid",
      value: "",
      type: "cascader",
      props: {
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: event.lazyLoad,
      },
    },
  ];
};

export const companySearchColumns = (event) => {
  return [
    {
      label: "客户类型",
      key: "customerCategory",
      type: "select",
      options: event.dict.type.custom_type,
    },
    {
      label: "企业名称",
      key: "customerName",
      type: "text",
    },
    {
      label: "法人名称",
      key: "legalPerson",
      type: "text",
    },
    {
      label: "统一社会信用代码",
      key: "creditCode",
      type: "text",
    },
    {
      label: "责任机构",
      key: "dutyDeptId",
      value: "",
      type: "cascader",
      props: {
        label: "label",
        value: "id",
        children: "children",
        emitPath: false,
      },
      options: event.deptOptions,
    },
    {
      label: "所属网格",
      key: "belongGrid",
      value: "",
      type: "cascader",
      props: {
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: event.lazyLoad,
      },
    },
  ];
};

export const merchantSearchColumns = (event) => {
  return [
    {
      label: "客户类型",
      key: "customerCategory",
      type: "select",
      options: event.dict.type.custom_type,
    },
    {
      label: "商户名称",
      key: "customerName",
      type: "text",
    },
    {
      label: "商户经营者",
      key: "merchantManager",
      type: "text",
    },
    {
      label: "统一社会信用代码",
      key: "creditCode",
      type: "text",
    },
    {
      label: "责任机构",
      key: "dutyDeptId",
      value: "",
      type: "cascader",
      props: {
        label: "label",
        value: "id",
        children: "children",
        emitPath: false,
      },
      options: event.deptOptions,
    },
    {
      label: "所属网格",
      key: "belongGrid",
      value: "",
      type: "cascader",
      props: {
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: event.lazyLoad,
      },
    },
  ];
};
/* table */
export const customerColumns = (event) => {
  return [
    { label: "客户名称", prop: "customerName" },
    { label: "客户类型", prop: "bankInterFlag" },
    { label: "证件类型", prop: "cardType" },
    { label: "证件号码", prop: "cardNum" },
    { label: "联系方式", prop: "contactPhone" },
    { label: "客户内码", prop: "customerInterNum" },
    { label: "客户地址", prop: "customerAddress", showOverFlowTooltip: true },
    { label: "所属网格", prop: "belongGridDesc" },
    { label: "责任机构", prop: "dutyDeptDesc" },
    { label: "网格员", prop: "gridUserDesc" },
    { label: "管户人", prop: "businessManageUserDesc" },
    { label: "操作", slot: true },
  ];
};
export const companyColumns = (event) => {
  return [
    { label: "企业名称", prop: "customerName" },
    { label: "统一社会信用代码", prop: "creditCode" },
    { label: "法人名称", prop: "legalPerson" },
    { label: "联系方式", prop: "legalPersonPhone" },
    { label: "客户类型", prop: "customerCategory" },
    { label: "所属网格", prop: "belongGridDesc" },
    { label: "责任机构", prop: "dutyDeptDesc" },
    { label: "网格员", prop: "gridUserDesc" },
    { label: "管户人", prop: "businessManageUserDesc" },
    { label: "操作", slot: true },
  ];
};
export const merchantColumns = (event) => {
  return [
    { label: "商户名称", prop: "customerName" },
    { label: "统一社会信用代码", prop: "creditCode" },
    { label: "客户类型", prop: "customerCategory" },
    { label: "商户经营者", prop: "merchantManager" },
    { label: "联系方式", prop: "contactPhone" },
    { label: "商户地址", prop: "customerAddress" },
    { label: "所属网格", prop: "belongGridDesc" },
    { label: "责任机构", prop: "dutyDeptDesc" },
    { label: "网格员", prop: "gridUserDesc" },
    { label: "管户人", prop: "businessManageUserDesc" },
    { label: "操作", slot: true },
  ];
};

export const normalColumns =(event)=>{
  return [
    { label: "客户名称", prop: "customerName", showOverFlowTooltip: true, },
    { label: "证件号", prop: "customerIdentity", showOverFlowTooltip: true, },
    { label: "手机号码", prop: "customerPhone", showOverFlowTooltip: true, },
    { label: "客户编号", prop: "customerNm", showOverFlowTooltip: true, },
    { label: "网格地址", prop: "customerAddress", showOverFlowTooltip: true, },
    { label: "所属网格", prop: "belongGridName", showOverFlowTooltip: true, },
    { label: "责任机构", prop: "branchBank", showOverFlowTooltip: true, },
    { label: "网格员", prop: "gridManager", showOverFlowTooltip: true, },
    { label: "管户人", prop: "creditManager", showOverFlowTooltip: true, },
    { label: "管户人工号", prop: "creditManagerJobNumber", showOverFlowTooltip: true, },
    { label: "所属分行", prop: "separateBank", showOverFlowTooltip: true, },
    { label: "操作", slot: true },
  ]
} 
export const gradeColumns =(event)=>{
  return [
    { label: "客户号", prop: "customerNm", width: 200 },
    { label: "客户名称", prop: "customerName", showOverFlowTooltip: true, },
    {
      label: "手机号码",
      prop: "customerPhone",
      width: 120,
      showOverFlowTooltip: true,
      formatter: ({ customerPhone }) => {
        return event.$phoneMasking(customerPhone)
      },
    },
    {
      label: "客户评级",
      prop: "customerLevel",
      showOverFlowTooltip: true,
      formatter: ({ customerLevel }) => {return event.dict.label.customer_level[customerLevel]},
    },
    { label: "单位", prop: "workUnit", width: 120, showOverFlowTooltip: true, },
    { label: "职业", prop: "job", width: 120, showOverFlowTooltip: true, },
    { label: "存款余额(元)", prop: "deposit", width: 120, showOverFlowTooltip: true, },
    { label: "贷款余额(元)", prop: "loan", width: 120, showOverFlowTooltip: true, },
    { label: "理财余额(元)", prop: "finance", width: 120, showOverFlowTooltip: true, },
    { label: "管理资产余额(元)", prop: "property", showOverFlowTooltip: true, width: 150 },
    { label: "所属网格", prop: "belongGridName", showOverFlowTooltip: true, },
    { label: "网格员", prop: "gridManager", showOverFlowTooltip: true, width: 120 },
    { label: "操作", slot: true },
  ]
}
export const creditColumns =(event)=>{
  return [
    { label: "客户名称", prop: "customerName", showOverFlowTooltip: true, },
    {
      label: "证件号",
      prop: "customerIdentity",
      width: 180,
      showOverFlowTooltip: true,
      formatter: ({ customerIdentity }) => {
        return event.$identityMasking(customerIdentity);
      },
    },
    {
      label: "手机号码",
      prop: "customerPhone",
      width: 120,
      showOverFlowTooltip: true,
      formatter: ({ customerPhone }) => {
        return event.$phoneMasking(customerPhone)
      },
    },
    {
      label: "客户类型",
      prop: "customerType",
      showOverFlowTooltip: true,
      formatter: ({ customerType }) => { return event.dict.label.custom_type_three[customerType]; },
    },
    { label: "客户地址", prop: "customerAddress", showOverFlowTooltip: true, },
    { label: "授信额度(元)", prop: "creditAmount", showOverFlowTooltip: true, width: 120 },
    { label: "贷款余额(元)", prop: "usingCreditAmount", showOverFlowTooltip: true, width: 120 },
    // {
    //   label: "逾欠情况",
    //   prop: "isInArrears",
    //   showOverFlowTooltip: true,
    //   formatter: ({ isInArrears }) => {
    //     return event.dict.label.in_arrears[isInArrears]
    //   },
    // },
    { label: "所属网格", prop: "belongGridName", showOverFlowTooltip: true, width: 120 },
    { label: "网格员", prop: "gridManager", showOverFlowTooltip: true, width: 120 },
    { label: "管户人员", prop: "creditManager", showOverFlowTooltip: true, },
    { label: "管户人员工号", prop: "creditManagerJobNumber", showOverFlowTooltip: true, width: 120 },
    { label: "所属机构", prop: "branchBank", showOverFlowTooltip: true, },
    { label: "操作", slot: true },
  ]
} 
export const filingColumns = (event)=> {
  return [
    { label: "客户名称", prop: "customerName", showOverFlowTooltip: true,  },
    {
      label: "客户类型", prop: "customerType", formatter: ({ customerType }) => {
        return event.dict.label.custom_type_three[customerType]
      }
    },
    { label: "证件号", prop: "customerIdentity", width: 180, formatter: ({ customerIdentity }) => {
      return event.$identityMasking(customerIdentity);
    }, },
    { label: "手机号", prop: "customerPhone", width: 120, formatter: ({ customerPhone }) => {
      return event.$phoneMasking(customerPhone)
    }, },
    { label: "所属网格", prop: "belongGridName" },
    { label: "建档人", prop: "filingManagerName" },
    { label: "建档机构", prop: "filingOrgName" },
    {
      label: "建档状态", prop: "filingStatus", formatter: ({ filingStatus }) => {
        return event.dict.label.filling_status_type[filingStatus]
      }
    },
    { label: "建档日期", prop: "filingDate" },
    { label: "信息完整度", prop: "filingProgress" },
    {
      label: "建档来源", prop: "filingSource", formatter: ({ filingSource }) => {
        return event.dict.label.filling_source_type[filingSource]
      }
    },
    { label: "操作", slot: true, width: '150px' },
  ]
} 

// 成员管理filter

export const normalSearchColumns = (event) => {
  return [
    {
      label: "客户名称",
      key: "customerName",
      type: "text",
      placeholder: "请输入客户名称",
      clearable: true,
    },
    {
      label: "证件号码",
      key: "customerIdentity",
      type: "text",
      placeholder: "请输入证件号码",
      clearable: true,
    },
    {
      label: "手机号码",
      key: "customerPhone",
      type: "text",
      placeholder: "请输入手机号码",
      clearable: true,
    },
    {
      label: "管户人",
      key: "creditManager",
      type: "text",
      placeholder: "请输入管户人姓名",
      clearable: true,
    },
    {
      label: "所属网格",
      key: "belongGridCodeArr",
      type: "cascader",
      props: {
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: event.lazyLoad,
        multiple: true,
        checkStrictly: true
      },
      showAllLevels: false,
      filterable: true,
      clearable: true,
      collapseTags: true,
      options: [],
    },
  ];
};

export const filingSearchColumns = (event) => {
  return [
    {
      label: "客户名称",
      key: "customerName",
      type: "text",
      placeholder: "请输入客户名称",
      clearable: true,
    },
    {
      label: "证件号码",
      key: "customerIdentity",
      type: "text",
      placeholder: "请输入证件号码",
      clearable: true,
    },
    {
      label: "手机号码",
      key: "customerPhone",
      type: "text",
      placeholder: "请输入手机号码",
      clearable: true,
    },
    {
      label: "所属网格",
      key: "belongGridCodeArr",
      type: "cascader",
      props: {
        value: "code",
        label: "name",
        emitPath: false,
        lazy: true,
        lazyLoad: event.lazyLoad,
        multiple: true,
        checkStrictly: true
      },
      showAllLevels: false,
      filterable: true,
      clearable: true,
      collapseTags: true,
      options: [],
    },
  ];
};