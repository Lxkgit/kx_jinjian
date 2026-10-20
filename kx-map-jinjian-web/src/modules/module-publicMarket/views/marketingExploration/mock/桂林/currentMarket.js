const mockData = {
  code: 200,
  msg: "success",
  data: {
    total: 1,
    count: 1,
    items: [
      {
        businessClueInfo: {
          describe:
            "2024-05-27,桂林市临桂区洋帆游泳俱乐部（个体工商户）在广西壮族自治区桂林市临桂区新成立,注册地址为桂林市临桂区临桂镇平桂路9号彰泰·中央城商住地块2幢1单元2层02号,注册资本为 --",
          u_id: "82ca69d5-86dd-49e3-aa38-c9efbd0563b7_XS001",
          publish_time: "2024-05-27 00:00:00",
          type: "XS001",
        },
        startTime: 1716739200,
        induCodeL2CN: "体育",
        tags: [
          {
            tagName: "小微企业",
            tagCode: "01008001",
          },
        ],
        clueType: "新成立公司",
        newStatus: "存续",
        description:
          "2024-05-27,桂林市临桂区洋帆游泳俱乐部（个体工商户）在广西壮族自治区桂林市临桂区新成立,注册地址为桂林市临桂区临桂镇平桂路9号彰泰·中央城商住地块2幢1单元2层02号,注册资本为 --",
        newStatusMark: "positive",
        gdLocation: [
          {
            lon: 110.164429,
            lat: 25.248667,
          },
        ],
        province: "广西壮族自治区",
        induCodeL3CN: "体育组织",
        count: 1,
        startDate: "2024-05-27",
        induCodeL4CN: "",
        name: "桂林市临桂区洋帆游泳俱乐部（个体工商户）",
        induCodeL1CN: "文化、体育和娱乐业",
        city: "桂林市",
        operName: "陶忠盛",
        regCapi: "",
        eid: "82ca69d5-86dd-49e3-aa38-c9efbd0563b7",
        logoUrl: "",
        newStatusCode: "1",
        regCapiNum: 0,
        clueTypeCode: "XS001",
        score: 0,
      },
    ],
    searchtime: 0.136,
    aggregations: {
      eid: {
        other_counts: "0",
        buckets: [
          {
            key: "82ca69d5-86dd-49e3-aa38-c9efbd0563b7",
            count: "1",
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
