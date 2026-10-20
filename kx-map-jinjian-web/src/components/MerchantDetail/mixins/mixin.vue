<script>
import column from '../mock/column.json'
import baseInfo from '../mock/baseInfo.json'
import labelObj from '../mock/label.json'
import gridLabel from '../mock/gridLabel.json'
export default {
  props: {
    familyName: String,
    showName: {
      type: Boolean,
      default: true,
    },
    showClose: {
      type: Boolean,
      default: true,
    },
    familyData: {
      type: Object,
      default: () => { },
    },

    vr: {
      type: Boolean,
      default: false,
    },
    type: {
      type: String,
      default: "2", // 1行外，2行内
    },
    code: String, // 行内信息
    isSJJ: Boolean,
  },
  provide() {
    return {
      type: this.type,
    };
  },
  data() {
    return {
      loading: false,
      currentIndex: 0,
      topColor: "#4ccacc",
      homesteadDetail: 0, // 0 家庭视图  1家庭成员
      list: [],
      // 分割线----------------
      prodNew: [],
      outInfo: {}, // 行外信息
      outList: [], //行外基本信息
      inLineInfo: {}, // 行内信息
      monthList: [
        10, 11, 12, 13, 15, 90, 23
      ], // 月度趋势数据
      dayList: [43, 23, 67, 10, 45, 93, 89], //日变化趋势
      moneyObj: {},
      institutionId: '',
      gridTagList: [],
      SJJData: []
    }
  },
  methods: {
    init() {
      let arr = [{ name: "外部信息" }, { name: "走访信息" }]
      if (this.type == "2") {
        //行内我的商户
        this.list = [
          { name: "行内信息" },
          ...arr,
          // { name: "新tab" },
        ];
        this.getSelectinternalInfoData(); // 获取行内信息
        // this.getpaInstitutionMonthlyTransactionData(); //月度变化趋势
        // this.getDayListData(); // 日变化趋势
        this.getSelectInfoData(); // 获取行外信息
      } else {
        //行内非我的商户  行外商户
        this.currentIndex = 1;
        this.list = [
          {},
          ...arr
        ];
        this.getSelectInfoData(); // 获取行外信息
      }
      // 市监局数据
      if (this.isSJJ) {
        this.list.push({ name: "市监局数据" })
        // this.getByUnifiedCode()
      }
    },
    // 分割线------------------------------
    // 表格表头颜色更改 
    tableHeaderStyle({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "display:none;";
      }
    },
    columnBackgroundStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex % 2 == 0) {
        return "background:#f6f6f6;";
      }
    },
    // 当前选中top标签
    popTitleClick(e) {
      this.currentIndex = e;
    },
    // 关闭按钮
    close() {
      this.$emit("close");
    },
    // 行外信息
    getSelectInfoData() {
      this.outInfo = baseInfo;
      this.institutionId = baseInfo.id
      this.outList = column.outList.map(item => {
        let data = { ...item }
        data.content = baseInfo[item.prop]
        return data
      })
    },
    // 行内信息
    getSelectinternalInfoData() {
      const { id } = baseInfo;
      baseInfo.phone = this.phonehidden(baseInfo.phone, 3)
      baseInfo.identity = this.phonehidden(baseInfo.identity, 10)
      this.inLineInfo = baseInfo;
      this.institutionId = baseInfo.id
      if (this.type == "2" && baseInfo.detailId) {
        this.getcustomerDetailData(baseInfo.detailId);
      }
      if (id) {
        this.getLabelByInstitutionIdData(); // 获取标签数据
      }
    },
    // 手机号码 身份证号码部分不可见
    phonehidden(val, start, len = 4) {
      return val &&
        val.substring(0, start) +
        "****" +
        val.substring(start + len);
    },
    // 商户法人产品持有信息
    getcustomerDetailData(id) {
      this.prodNew = labelObj.L03 || [];
    },
    // 获取标签
    getLabelByInstitutionIdData() {
      this.gridTagList = gridLabel[0].childLabels
    },
    // 获取标签
    // getLabelByInstitutionIdData() {
    //   getLabelByInstitutionId({ institutionId: this.inLineInfo.id }).then(
    //     (res) => {
    //       this.gridTagList = res.data.map((item) => {
    //         return {
    //           labelName: item,
    //         };
    //       });
    //     }
    //   );
    // },
    // // 商户法人产品持有信息
    // getcustomerDetailData(id) {
    //   // 128339
    //   getClientLabelByParentLabelCode({
    //     parentLabelCode: "G01,L03,L01",
    //     customDetailId: id,
    //   }).then((res) => {
    //     if (res.state === "000000") {
    //       this.prodNew = res.data.L03 || [];
    //     }
    //   });
    // },
    //月度变化趋势
    getpaInstitutionMonthlyTransactionData() {
      getpaInstitutionMonthlyTransaction({ code: this.code }).then((res) => {
        if (res.state === "000000") {
          this.monthList = res.data || [];
        }
      });
    },
    getDayListData() {
      getDayList({ code: this.code }).then((res) => {
        if (res.state === "000000") {
          this.dayList = res.data || [];
        }
      });
    },
    getByUnifiedCode() {
      getByUnifiedCode({ unifiedCode: this.code }).then((res) => {
        if (res.state === "000000") {
          //名称、地址、法人、经营范围、联系方式
          let arr = [
            { name: '名称', ename: 'corpName' },
            { name: '地址', ename: 'dzwgAddress' },
            { name: '法人', ename: 'personName' },
            { name: '联系方式', ename: 'telephone' },
            { name: '经营范围', ename: 'businessScope' },
          ]
          this.SJJData = arr.map(v => {
            v.content = res.data[v.ename]
            return v
          })
        }
      });
    },
  },
  mounted() {
  }
}
</script>

<style scoped lang="scss">
</style>