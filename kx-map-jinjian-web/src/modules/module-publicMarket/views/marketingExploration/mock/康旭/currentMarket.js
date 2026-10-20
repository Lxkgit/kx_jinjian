const mockData = {
  code: 200,
  msg: "success",
  data: {
    total: 1,
    count: 9,
    items: [
      {
        businessClueInfo: {
          describe:
            "2024-05-30中标了【2024-CQ-0014-01:中信银行重庆分行2024-2025年“中信选书”采购】项目,招标方为中信银行股份有限公司,中标方为康旭科技有限公司,金额：--万元",
          u_id: "aed9a0be-6d5e-4641-a30b-f47d26d285ac_6657db40ec020d3cbb46473b_XS027",
          publish_time: "2024-05-30 00:00:00",
          type: "XS027",
        },
        startTime: 1509379200,
        induCodeL2CN: "批发业",
        tags: [
          {
            tagName: "中型企业",
            tagCode: "01008002",
          },
          {
            tagName: "科技型企业",
            tagCode: "10201021",
          },
          {
            tagName: "民营企业",
            tagCode: "10104022",
          },
        ],
        clueType: "新增中标",
        newStatus: "存续",
        description:
          "2024-05-30中标了【2024-CQ-0014-01:中信银行重庆分行2024-2025年“中信选书”采购】项目,招标方为中信银行股份有限公司,中标方为康旭科技有限公司,金额：--万元",
        newStatusMark: "positive",
        gdLocation: [
          {
            lon: 120.003831,
            lat: 30.291495,
          },
        ],
        province: "浙江省",
        induCodeL3CN: "批发业",
        count: 9,
        startDate: "2017-10-31",
        induCodeL4CN: "批发业",
        name: "康旭科技有限公司",
        induCodeL1CN: "批发和零售业",
        city: "杭州市",
        operName: "励建科",
        regCapi: "10000万元人民币",
        eid: "aed9a0be-6d5e-4641-a30b-f47d26d285ac",
        logoUrl: "",
        newStatusCode: "1",
        regCapiNum: 10000,
        clueTypeCode: "XS027",
        score: 733,
      },
    ],
    searchtime: 0.102,
    aggregations: {
      eid: {
        other_counts: "0",
        buckets: [
          {
            key: "aed9a0be-6d5e-4641-a30b-f47d26d285ac",
            count: "9",
          },
        ],
      },
    },
    fun_ver: "es_default",
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
