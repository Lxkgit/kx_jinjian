const mockData = {
  code: 200,
  msg: "success",
  data: {
    lat: 25.235615,
    lon: 110.179752,
    radius: 5000,
    name: "广西壮族自治区桂林市临桂区西城中路69号",
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
