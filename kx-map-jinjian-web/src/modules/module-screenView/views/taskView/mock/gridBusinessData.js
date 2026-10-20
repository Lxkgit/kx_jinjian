const mockData = {
  code: 200,
  msg: 'success',
  data: {
    wealthBusinessList: [
      {
        'text': '活期储蓄',
        'population': '483321.31',
        'nature': -1,
        'value': 17.76,
        'radio': 8.73,
      },
      {
        'text': '定期储蓄',
        'population': '4102918.12',
        'nature': 1,
        'value': 14.21,
        'radio': 74.11,
      },
      {
        'text': '理财',
        'population': '941231.56',
        'nature': 1,
        'value': 1.710,
        'radio': 17,
      },
      {
        'text': '其他',
        'population': '8472.93',
        'nature': 1,
        'value': 0.64,
        'radio': 0.15,
      },
    ],
    savingsStructureData: [
      [
        34,
        41,
        48,
      ],
      [
        98,
        102,
        109,
      ],
      [
        278,
        293,
        301,
      ],
    ],
    assetBusinessList: [
      {
        'text': '消费贷款',
        'population': '102831.87',
        'nature': 1,
        'value': 1.09,
        'radio': 28.05,
      },
      {
        'text': '经营贷款',
        'population': '134221.12',
        'nature': 1,
        'value': 1.23,
        'radio': 36.61,
      },
      {
        'text': '按揭贷款',
        'population': '123124.43',
        'nature': -1,
        'value': 3.12,
        'radio': 33.59,
      },
      {
        'text': '线上贷款',
        'population': '6421.12',
        'nature': -1,
        'value': 6.12,
        'radio': 1.75,
      },
    ],
    dashList: [
      {
        'value': 14,
        'text': '三代社保卡覆盖率',
      },
      {
        'value': 33,
        'text': '手机银行覆盖率',
      },
      {
        'value': 7,
        'text': '代发代扣覆盖率',
      },
      {
        'value': 49,
        'text': '支付业务绑卡率',
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