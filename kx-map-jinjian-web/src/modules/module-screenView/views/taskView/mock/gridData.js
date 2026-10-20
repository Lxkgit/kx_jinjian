const mockData = {
  code: 200,
  msg: 'success',
  data: {
    villageInstitutionalRanking: [
      {
        'rank': 1,
        'name': '碧溪中心支行',
        'flyUp': '42.42%',
        'usingPeople': '6.21%',
        'productPeople': '白敬诚',
      },
      {
        'rank': 2,
        'name': '常熟分行营业部',
        'flyUp': '40.84%',
        'usingPeople': '7.43%',
        'productPeople': '张华民',
      },
      {
        'rank': 3,
        'name': '董浜中心支行',
        'flyUp': '39.63%',
        'usingPeople': '7.25%',
        'productPeople': '李丹',
      },
      {
        'rank': 4,
        'name': '古里中心支行',
        'flyUp': '38.84%',
        'usingPeople': '6.98%',
        'productPeople': '张宣仪',
      },
      {
        'rank': 5,
        'name': '海虞中心支行',
        'flyUp': '38.42%',
        'usingPeople': '8.01%',
        'productPeople': '陈敏',
      },
      {
        'rank': 6,
        'name': '梅李中心支行',
        'flyUp': '38.12%',
        'usingPeople': '6.89%',
        'productPeople': '刘楠楠',
      },
    ],
    institutionalRanking: [
      {
        'rank': 1,
        'name': '沙家浜中心支行',
        'flyUp': '42.42%',
        'usingPeople': '6.21%',
        'productPeople': '123',
      },
      {
        'rank': 2,
        'name': '尚湖中心支行',
        'flyUp': '40.84%',
        'usingPeople': '7.43%',
        'productPeople': '234',
      },
      {
        'rank': 3,
        'name': '辛庄中心支行',
        'flyUp': '39.63%',
        'usingPeople': '7.25%',
        'productPeople': '125',
      },
      {
        'rank': 4,
        'name': '招商中心支行',
        'flyUp': '38.84%',
        'usingPeople': '6.98%',
        'productPeople': '563',
      },
      {
        'rank': 5,
        'name': '支塘中心支行',
        'flyUp': '38.42%',
        'usingPeople': '8.01%',
        'productPeople': '123',
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