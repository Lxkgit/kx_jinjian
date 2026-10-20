const state = {
  systemInfo: {}, // 网格体系
  mapStatus: "0", // 0-编辑,1-查看

  customerId: null, // 客户id
  customerType: "1", // 客户类型
  customerParam: null, // 客户

  anchorInfo: null, // 锚点信息

  groupInfo: {}, // 客群信息
  /* 采集模版 */
  modelStatus: "add",
  modelId: "",
  filingId: "",

  /* 产品 */
  productId: "",
  prodInfo: {},

  // 网格列表
  gridInfo: {},

  // 调查问卷
  questionInfo: {},

  // 任务接收、转派
  taskReceiveList: [
    {
      id: "1",
      taskName: "2024.01指标任务",
      taskType: "周期任务",
      feedStatus: "已反馈",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "50%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
    {
      id: "2",
      taskName: "2024.02指标任务",
      taskType: "日常任务",
      feedStatus: "未反馈",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "60%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
    {
      id: "3",
      taskName: "2024.03指标任务",
      taskType: "日常任务",
      feedStatus: "已失效",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "60%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
  ],
  taskTurnList: [
    {
      id: "1",
      taskName: "2024.01商户摸排任务",
      taskType: "周期任务",
      taskStatus: "进行中",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "50%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
    {
      id: "2",
      taskName: "2024.02现金宣传扫街",
      taskType: "日常任务",
      taskStatus: "已失效",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "60%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
  ],
  taskHairList: [
    {
      id: "1",
      taskName: "2024.01商户摸排任务",
      taskType: "周期任务",
      taskStatus: "进行中",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "50%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
    {
      id: "2",
      taskName: "2024.02现金宣传扫街",
      taskType: "日常任务",
      taskStatus: "已失效",
      startTime: "2024-01-01 15：00",
      endTime: "2024-02-01 17：00",
      progress: "60%",
      hairTime: "2024-02-01 15：00",
      hairOrg: "金华市人行",
      startToEnd: ["2024-01-01 15:00:00", "2024-02-01 17:00:00"],
      cycle: "月",
      times: "2",
      list: [
        {
          frontKey: 1741255987641,
          content: "",
          isRequired: "1",
          fieldName: "测试",
          fillingForm: "1",
        },
        {
          frontKey: 1741255995129,
          content: "男,女",
          isRequired: "1",
          fieldName: "选择",
          fillingForm: "3",
        },
      ],
      taskObject: [
        {
          deptId: "12378",
          deptName: "中国银行金华分行",
          orgNum: "10601bb",
          executors: [
            {
              executorName: "中国银行股份有限公司金华汇金支行",
              executorNum: "789096",
            },
            {
              executorName: "中国银行股份有限公司金华锦都支行",
              executorNum: "789099",
            },
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
          ],
        },
        {
          deptId: "16788",
          deptName: "东阳农商银行",
          orgNum: "9870aa",
          executors: [
            {
              executorName: "中国银行金华通汇支行",
              executorNum: "789095",
            },
            {
              executorName: "中国银行金华市分行营业部",
              executorNum: "789093",
            },
          ],
        },
      ],
    },
  ],
};
const mutations = {
  SET_SYSTEM_INFO: (state, systemInfo) => {
    state.systemInfo = systemInfo;
  },
  SET_ANCHOR_INFO: (state, anchorInfo) => {
    state.anchorInfo = anchorInfo;
  },
  SET_CUSTOMER_ID: (state, customerId) => {
    state.customerId = customerId;
  },
  SET_CUSTOMER_TYPE: (state, customerType) => {
    state.customerType = customerType;
  },
  SET_MAP_STATUS: (state, mapStatus) => {
    state.mapStatus = mapStatus;
  },
  SET_GROUP_INFO: (state, groupInfo) => {
    state.groupInfo = groupInfo;
  },
  SET_MODEL_STATUS: (state, modelStatus) => {
    state.modelStatus = modelStatus;
  },
  SET_MODEL_ID: (state, modelId) => {
    state.modelId = modelId;
  },
  SET_FILING_ID: (state, filingId) => {
    state.filingId = filingId;
  },
  SET_CUSTOMER_PARAM: (state, customerParam) => {
    state.customerParam = customerParam;
  },
  SET_PRODUCT_ID: (state, productId) => {
    state.productId = productId;
  },
  SET_PROD_INFO: (state, prodInfo) => {
    state.prodInfo = prodInfo;
  },
  SET_GRID_INFO: (state, gridInfo) => {
    state.gridInfo = gridInfo;
  },
  SET_QUESTION_INFO: (state, questionInfo) => {
    state.questionInfo = questionInfo;
  },
  SET_TASK_RECEIVE_LIST: (state, taskReceiveList) => {
    state.taskReceiveList = taskReceiveList;
  },
  SET_TASK_TURN_LIST: (state, taskTurnList) => {
    state.taskTurnList = taskTurnList;
  },
  SET_TASK_HAIR_LIST: (state, taskHairList) => {
    state.taskHairList = taskHairList;
  },
};
const actions = {};
const getters = {
  systemInfo: (state) => state.systemInfo,
  anchorInfo: (state) => state.anchorInfo,
  customerId: (state) => state.customerId,
  customerType: (state) => state.customerType,
  mapStatus: (state) => state.mapStatus,
  groupInfo: (state) => state.groupInfo,
  modelStatus: (state) => state.modelStatus,
  modelId: (state) => state.modelId,
  filingId: (state) => state.filingId,
  customerParam: (state) => state.customerParam,
  productId: (state) => state.productId,
  prodInfo: (state) => state.prodInfo,
  gridInfo: (state) => state.gridInfo,
  questionInfo: (state) => state.questionInfo,
  taskReceiveList: (state) => state.taskReceiveList,
  taskTurnList: (state) => state.taskTurnList,
  taskHairList: (state) => state.taskHairList,
};
export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
