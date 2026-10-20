const mockData = {
  code: 200,
  msg: "success",
  data: {
    parkSearchCount: 14,
    associationCount: 12,
    businessClueCount: 10099,
    businessClueUserCount: 1,
    newEnterpriseCount: 540,
    companyCount: 65950,
  },
};

function returnPromise() {
  return new Promise(function (resolve) {
    resolve(mockData);
  });
}
export default returnPromise;
