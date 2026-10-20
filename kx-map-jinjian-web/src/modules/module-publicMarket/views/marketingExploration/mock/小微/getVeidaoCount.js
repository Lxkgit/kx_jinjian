const mockData = {
  code: 200,
  msg: "success",
  data: {
    parkSearchCount: 13,
    associationCount: 13,
    businessClueCount: 9651,
    businessClueUserCount: 1,
    newEnterpriseCount: 501,
    companyCount: 57912,
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
