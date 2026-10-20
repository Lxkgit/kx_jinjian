/**
 * 计算表格指定列的行合并信息
 * @param {Array<Record<string,any>>} array - 表格数据数组
 * @param {Array<string>} key - 需要合并的列的 key 数组
 * @returns {Object:{[key]:Array<{rowspan:number, colspan:number}>}} resultHash - 每列对应的合并信息数组，结构如 { key1: [{rowspan, colspan}, ...], key2: [...] }
 */
export const setRowSpan = (array, key = []) => {
  if (!Array.isArray(key)) throw new Error('key must be an Array');

  const resultHash = {}; // 存储每列的合并信息
  const indexHash = {};  // 记录每列当前合并块的起始索引

  for (let rowIndex = 0; rowIndex < array.length; rowIndex++) {
    const row = array[rowIndex];
    for (let keyIndex = 0; keyIndex < key.length; keyIndex++) {
      const colKey = key[keyIndex];

      if (rowIndex === 0) {
        // 第一行初始化
        resultHash[colKey] = [{ rowspan: 1, colspan: 1 }];
        indexHash[colKey] = 0;
      } else {
        const prevRow = array[rowIndex - 1];
        if (prevRow[colKey] === row[colKey]) {
          // 当前行与上一行该列内容相同，合并
          resultHash[colKey][indexHash[colKey]].rowspan++;
          resultHash[colKey].push({ rowspan: 0, colspan: 0 });
        } else {
          // 不同则初始化hash
          indexHash[colKey] = rowIndex;
          resultHash[colKey].push({ rowspan: 1, colspan: 1 });
        }
      }
    }
  }
  return resultHash;
};
