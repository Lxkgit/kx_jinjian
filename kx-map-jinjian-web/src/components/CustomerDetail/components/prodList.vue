<template>
  <div style="font-size: 16px">
    <!-- 循环 -->
    <div v-for="(item, index) in list" :key="index" class="label-box">
      <div class="label">{{ item.name }}:</div>
      <div class="tags-mesh">
        <div v-for="(i, ind) in item.list" :key="ind" :class="hasProd(i.value) && 'active'">
          <el-tooltip v-if="hasProduct[i.value]" class="item" effect="dark" placement="top">
            <div slot="content" v-html="handleBR(i.value)" />
            <span>
              {{ i.name }}
            </span>
          </el-tooltip>
          <span v-else>
            {{ i.name }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "prodList",
  props: {
    prod: {
      type: Array,
      default: () => {
        return ["L0302066001"];
      },
    },
  },
  data() {
    return {
      list: [
        {
          name: "储蓄类",
          list: [
            { name: "定期存款", value: "L0302023001" },
            { name: "大额存单", value: "L0302026001" },
            { name: "结构性存款", value: "L0302027001" },
            { name: "通知存款", value: "L0302029001" },
            { name: "活期储蓄", value: "L0302022001" },
          ],
        },
        {
          name: "贷款类",
          list: [
            { name: "消费类", value: "L0309004001" },
            { name: "经营类", value: "L0302072001" },
            { name: "信用卡资产类", value: "L0302004001" },
            { name: "按揭类", value: "L0302014001" },
            { name: "市民贷类", value: "L0302076001" },
          ],
        },
        {
          name: "理财类",
          list: [
            { name: "天天利", value: "L0302073001" },
            { name: "非天天利类", value: "L0302074001" },
            { name: "代销理财", value: "L0302069001" },
          ],
        },
        {
          name: "代发类",
          list: [{ name: "代发工资", value: "L0301007001" }],
        },
        {
          name: "银行卡类",
          list: [
            { name: "普通借记卡", value: "L0302001001" },
            { name: "市民卡", value: "L0302003001" },
            { name: "社保卡", value: "L0302068001" },
            { name: "贷记卡", value: "L0302004001" },
          ],
        },
        {
          name: "中间业务类",
          list: [
            { name: "保险", value: "L0301014001" },
            { name: "贵金属", value: "L0301013001" },
            { name: "基金", value: "L0301015001" },
          ],
        },
        {
          name: "代收代付类",
          list: [
            { name: "水费", value: "L0302044001" },
            { name: "电费", value: "L0302045001" },
            { name: "有线电视费", value: "L0302046001" },
            { name: "电信固话费", value: "L0302047001" },
            { name: "社保", value: "L0302049001" },
          ],
        },
        {
          name: "电子渠道类",
          list: [
            { name: "手机银行 (大众版)", value: "L0302066001" },
            { name: "手机银行(专业版)", value: "L0302067001" },
            { name: "常银生活", value: "L0302075001" },
            { name: "网上银行", value: "L0301018001" },
            { name: "微银行", value: "L0301012001" },
            { name: "三方支付", value: "L0301008001" },
            { name: "收单", value: "L0301016001" },
          ],
        },
        {
          name: "其他",
          list: [
            { name: "三方存管", value: "L0301011001" },
            { name: "数币归集", value: "L0301019001" },
            { name: "ETC", value: "L0301010001" },
            { name: "短信", value: "L0301020001" },
          ],
        },
      ],
      hasProduct: {},
    };
  },
  watch: {
    prod: {
      handler(val) {
        console.log(val, 909);
        const data = {};
        val &&
          val.map((item) => {
            data[item.labelCode] = item.labelContent;
            return item;
          });
        this.$nextTick(() => {
          this.hasProduct = data;
          console.log(data, 90);
        });
      },
      immediate: true
    },
  },
  mounted() {
    // console.log('234234',this.prod);
  },
  methods: {
    hasProd(i) {
      const isArr = i instanceof Array;
      const arr = isArr ? i : [i];
      let hasProd = false;
      this.prod.forEach((v) => {
        if (arr.includes(v.labelCode)) {
          hasProd = true;
        }
      });
      return hasProd;
    },
    handleBR(val) {
      const list = this.hasProduct[val].split("\\n");
      let str = " ";
      list.map((item) => {
        str += `<div>${item}</div>`;
      });
      return str;
    },
  },
};
</script>
<style lang="scss" scoped>
.label-box {
  margin: 5px;
  display: flex;
}
.label {
  min-width: 90px;
  font-size: 16px;
  line-height: 30px;
  color: #333333;
}
.tags-mesh {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  > div,
  > span {
    margin-bottom: 8px;
    margin-right: 10px;
    height: 30px;
    color: #999;
    line-height: 30px;
    border: 0.5px solid #999;
    border-radius: 4px;
    background: url("../assets/compImg/x.png") no-repeat 5px center;
    padding: 0 10px 0 26px;
    cursor: pointer;
    &.active {
      color: #e9921c;
      border-color: #ea931c;
      background: url("../assets/compImg/v.png") no-repeat 5px center;
    }
  }
}
</style>