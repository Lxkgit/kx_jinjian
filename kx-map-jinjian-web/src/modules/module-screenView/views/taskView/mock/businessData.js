const mockData = {
  code: 200,
  msg: 'success',
  data: {
    showTopList: [
      {
        name: '二级网格',
        value: 236,
        type: 1,
      },
      {
        name: '三级网格',
        value: 1123,
        type: 1,
      },
      {
        name: '总家庭数',
        value: 304832,
        type: 1,
      },
      {
        name: '走访家庭数',
        value: 39382,
        type: 0,
      }, 
    ],
    dashList: [
      {
        text: '万元户率',
        circleV: '64',
        circleC: 3928,
        circleP: 5,
        positive: 1,
        showPecent: true,
      },
      {
        text: '授信覆盖率',
        circleV: '11',
        circleC: 1231,
        circleP: 8,
        positive: 1,
      },
      {
        text: '用信覆盖率',
        circleV: '8',
        circleC: 433,
        circleP: 12,
        positive: 1,
      },
      {
        text: '产品覆盖率',
        circleV: '13',
        circleC: 2312,
        circleP: 24,
        positive: 1,
      },
    ],
    contentList: [
      {
        text: '飞燕以下客户数',
        population: '62831',
        nature: 1,
        value: 1292,
        radio: 21.25,
      },
      {
        text: '飞燕及以上客户数',
        population: '232831',
        nature: 1,
        value: 3293,
        radio: 78.75,
      },
      {
        text: '用信人数',
        population: '89381',
        nature: 1,
        value: 12931,
      },
      {
        text: '产品持有数',
        population: '48371',
        nature: 1,
        value: 102834,
      },
    ],
  },
}

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData)
  })
}
export default returnPromise