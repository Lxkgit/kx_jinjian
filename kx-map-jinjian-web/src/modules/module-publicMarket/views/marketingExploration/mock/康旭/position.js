const mockData = {
  code: 200,
  msg: "success",
  data: {
    lat: 30.291495,
    lon: 120.003831,
    radius: 5000,
    name: "康旭科技有限公司",
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
