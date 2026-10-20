const mockData = {
  code: 200,
  msg: "success",
  data: {
    parkSearchCount: 104,
    associationCount: 9,
    businessClueCount: 34981,
    businessClueUserCount: 9,
    newEnterpriseCount: 1373,
    companyCount: 154367,
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
