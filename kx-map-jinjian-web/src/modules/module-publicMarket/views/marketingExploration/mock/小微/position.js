const mockData = {
  code: 200,
  msg: "success",
  data: {
    lat: 25.222939,
    lon: 110.172464,
    radius: 5000,
    name: "广西壮族自治区桂林市临桂区西城南路5号麓湖国际社区B区6幢1-2层05号",
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
