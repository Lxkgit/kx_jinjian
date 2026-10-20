const mockData = {
  code: 200,
  msg: "success",
  data: {
    parkSearchCount: 13,
    associationCount: 15,
    businessClueCount: 10722,
    businessClueUserCount: 1,
    newEnterpriseCount: 585,
    companyCount: 73599,
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
