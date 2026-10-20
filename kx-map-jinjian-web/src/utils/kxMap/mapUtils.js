import * as turf from "@turf/turf";

/**
 * @function 判断是否包含
 * @param source {feature} 源多边形
 * @param target {feature} 目标多边形
 * @return {Boolean} true: 包含; false: 不包含
 */
export const judgeContains = (source, target) => {
  // source数据转换
  let sPolygons = null;
  if (turf.getType(source) === "MultiPolygon") {
    sPolygons = turf.flatten(source);
  } else {
    sPolygons = turf.featureCollection([source]);
  }
  let sourceLength = sPolygons.features.length;
  // target数据转换
  let tPolygons = turf.flatten(target);

  // 判断是否包含
  let staArr = []; // 记录各个target多边形包含source情况,二维数组
  let resArr = []; // 统计每组source包含情况,一维数组

  turf.featureEach(tPolygons, (curTarget, tIndex) => {
    turf.featureEach(sPolygons, (curSource) => {
      const r = areaEqual(curSource, curTarget);
      if (staArr[tIndex]) {
        staArr[tIndex].push(r);
      } else {
        staArr[tIndex] = [r];
      }
    });
  });

  for (let i = 0; i < sourceLength; i++) {
    let flag = false;
    staArr.forEach((sta) => {
      flag = flag || sta[i];
    });
    resArr.push(flag);
  }

  return resArr.reduce((pre, cur) => {
    return pre && cur;
  }, true);
};

/**
 * @function 判断是否相交
 * @param source {feature} 源多边形
 * @param target {feature} 目标多边形
 * @return {Boolean} true: 不相交; false: 相交
 */
export const judgeDisjoint = (source, target) => {
  let current = null;
  if (turf.getType(source) === "MultiPolygon") {
    current = turf.flatten(source);
  } else {
    current = turf.featureCollection([source]);
  }
  let temp = turf.flatten(target);

  return turf.featureReduce(
    temp,
    (preTarget, curTarget) => {
      return (
        preTarget &&
        turf.featureReduce(
          current,
          (preSource, curSource) => {
            return (
              preSource &&
              (turf.booleanDisjoint(curTarget, curSource) ||
                !turf.intersect(curTarget, curSource) ||
                turf.booleanEqual(curTarget, curSource))
            );
          },
          true
        )
      );
    },
    true
  );
};

// 计算两个多边形面积是否相等
const areaEqual = (source, target) => {
  return calculateEqual(
    turf.area(source),
    turf.intersect(target, source)
      ? turf.area(turf.intersect(target, source))
      : 0
  );
};

// 计算是否相近
const calculateEqual = (num1, num2) => {
  let max = num1 >= num2 ? num1 : num2;
  let min = num1 + num2 - max;
  return (min / max) * 100 > 99.99;
};
