const mockData = {
  code: 200,
  msg: "success",
  data: {
    lat: 25.246979,
    lon: 110.165243,
    radius: 5000,
    name: "广西壮族自治区桂林市临桂区公园北路8号",
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
