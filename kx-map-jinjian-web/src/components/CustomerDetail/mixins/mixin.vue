<script>
import mockData from '../mock/index.json'
import customInfo from '../mock/customInfo.json'
export default {
  name: '',
  components: {},
  data() {
    this.dicTypes = mockData.dicTypes
    const GENDER = mockData.GENDER;
    const WHETHERORNOT = mockData.WHETHERORNOT;
    return {
      activeName: "customer",
      GDPList: mockData.GDPList,
      profile: null, // 所有的数据
      aumList: mockData.aumList,
      textStyle: {
        fontSize: "14px",
        color: "#333333",
      },
      textValueStyle: {
        fontSize: "14px",
        color: "#000000",
        fontWeight: "normal",
      },
      customerData: {},
      option: {},

      otherInfoList: mockData.otherInfoList,
      basicInfoValue: customInfo,
      otherInfoValue: customInfo,
      interviewStatusList: [
        { label: "姓名", prop: "name" },
        { label: "联系电话", prop: "phone" },
        { label: "详细地址", prop: "address" },
        { label: "身份证号", prop: "idenNo" },
        { label: "工作单位", prop: "workUnit" },
        {
          label: "与户主关系",
          prop: "houseHolderRelation",
          formatter: (val) =>
            this.getDictsLabel(this.GRIDFAMILYRELATION, val, true),
        },
        { label: "网格标签", prop: "gridLabel", slot: true }, //
        {
          label: "他行存款到期需求", //
          prop: "gridLabel",
          formatter: (val) => this.getLabelName("G01001001", val),
        },
        {
          label: "是否需要消息通知", //
          prop: "needNotification",
          formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        },
        { label: "到期日期", prop: "maturedDate" },
        { label: "到期金额(元)", prop: "maturedAmount" },
        { label: "备注", prop: "maturedRemark" },
        {
          label: "其他需求",
          prop: "gridLabel",
          formatter: (val) => this.getLabelName("G01001007", val),
        }, //
        { label: "其他需求备注", prop: "otherNeedRemark" },
        // {
        //   label: "是否在家/触达", //
        //   prop: "reachStatus",
        //   formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        // },
        {
          label: "是否为走访黑户", //
          prop: "visitBlackList",
          formatter: (val) => this.getDictsLabel(WHETHERORNOT, val),
        },
        { label: "风险客户备注", prop: "riskCustomerRemark" }, //
        { label: "走访备注", prop: "visitRemark" },
        { label: "走访照片", prop: "visitImgFile", type: "image" },
      ],

      interviewStatusValue: {},
      interviewList: [
        {
          name: "走访时间",
          prop: "createDate",
        },
        {
          name: "走访人员",
          prop: "createUserName",
        },
        {
          name: "走访方式",
          prop: "visitType",
          type: "select",
          formatter: (val) => {
            const arr = ["一键走访", "正常走访"];
            return arr[val - 1];
          },
        },
        {
          name: "操作",
          type: "operation",
          prop: "showDetailText",
          color: "#00B4FD",
        },
      ],
      systemList: [], // 系统标签列表
      nm: "", // 客户内码
      id: "", // 客户唯一标识
      visitsQuery: {
        customDetailId: "",
        pageSize: 10,
        pageIndex: 1,
      },
      newVisitList: {}, // 最新走访信息
      historyVisitList: [], // 历史走访信息
      findVisitList: {},
      showItemDetail: {}, // 查看客户走访信息详情
      showItemD: false, // 点击详情查看详情
      totalnum: 0,
      detailId: null,
      product: null,
      loading: false,
      financeCurrentPie: customInfo.financePie, // 财富信息
      propertyCurrentPie: customInfo.propertyPie, // 资产信息
      prod: customInfo.prod,
      systemLabel: [], // 系统标签
      networkLabel: [], // 网格标签
      productColumns: mockData.productColumns,//产品到期一览表 , 
      recordsColumns: mockData.recordsColumns, //交易记录  ,
      recordsTableData: [],
      adventProductTableData: [],
      cardNumbers: [],
      GRIDFAMILYRELATION: [],
    };
  },
  // 计算属性
  computed: {
    isVisit() {
      return this.$route.query.isVisit;
    },
    isLskh() {
      // 是否是临时客户
      return this.$route.query.isLskh;
    },
  },
  // 侦听器
  watch: {
    isVisit: {
      handler(newV, oldV) {
        if (newV) {
          this.activeName = "visit";
        }
      },
      immediate: true,
    },
  },
  async created() {
    // const { data } = await this.getDicItemByType("GRID_FAMILY_RELATION");
    // this.GRIDFAMILYRELATION = data;
    this.loading = true;
    this.detailId = this.$route.query.detailId;
    this.custNo = this.$route.query.custNo;
    this.isTempCustom = this.$route.query.isTempCustom;
    // this.getMeshCustDetail();
    // this.getMeshSystemLabel(this.detailId);
    // this.getClientLabelByParentLabelCode(this.detailId);
    this.loading = false;
  },
  mounted() { },
  // 组件方法
  methods: {
    //详情跳转
    jump(item, key) {
      if (key == "entName") {
        this.$router.push({
          name: "MeshStore",
          params: item,
        });
      } else if (key == "custName") {
        this.$router.push({
          name: "MeshComp",
          params: item,
        });
      }
    },
    tagName(val) {
      if (!val) return;
      let arr = ["非我行", "飞燕", "小燕", "金燕", "私行", "钻石"];
      let index = arr.findIndex((v) => {
        return val.includes(v);
      });
      return "label" + index;
    },
    close() {
      this.$emit("close");
    },
    // 客户基本信息
    // async getMeshCustDetail() {
    //   let res = {};
    //   if (this.isLskh) {
    //     res = await lsCusDetail({ custNo: this.custNo });
    //   } else {
    //     res = await getMeshCustDetail({ detailId: this.detailId });
    //   }
    //   let { data } = res;
    //   if (data) {
    //     this.financeCurrentPie = data.financePie;
    //     this.propertyCurrentPie = data.propertyPie;
    //   }
    //   // this.otherInfoValue = data.familyList && Object.assign(data, data.familyList);
    //   // this.basicInfoValue = data.familyList && Object.assign(data, data.familyList);
    //   this.otherInfoValue = data;
    //   this.basicInfoValue = data;
    //   // console.log('basicInfoValue',this.basicInfoValue);
    //   // if(!this.otherInfoValue || !this.basicInfoValue) return
    //   if (this.otherInfoValue && this.otherInfoValue.idenType) {
    //     const name =
    //       this.allDics.CUSTLICENCE &&
    //       this.allDics.CUSTLICENCE.filter(
    //         (e) => e.code == this.otherInfoValue.idenType
    //       )[0];
    //     if (name && name.text) {
    //       this.otherInfoValue.idenType = name.text;
    //     }
    //   }
    //   if (this.otherInfoValue.gender) {
    //     const name =
    //       this.allDics.GENDER &&
    //       this.allDics.GENDER.filter(
    //         (e) => e.code == this.otherInfoValue.gender
    //       )[0];
    //     if (name && name.text) {
    //       this.otherInfoValue.gender = name.text;
    //     }
    //   }
    //   if (this.otherInfoValue.nation) {
    //     const name =
    //       this.allDics.NATIONINFO &&
    //       this.allDics.NATIONINFO.filter(
    //         (e) => e.code == this.otherInfoValue.nation
    //       )[0];
    //     if (name && name.text) {
    //       this.otherInfoValue.nation = name.text;
    //     }
    //   }
    //   if (this.basicInfoValue.houseHolder) {
    //     const name = WHETHERORNOT.filter(
    //       (e) => e.value == this.basicInfoValue.houseHolder
    //     )[0];
    //     if (name && name.text) {
    //       this.basicInfoValue.houseHolder = name.text;
    //     }
    //   }
    //   if (this.basicInfoValue.mainCert) {
    //     const name = WHETHERORNOT.filter(
    //       (e) => e.value == this.basicInfoValue.mainCert
    //     )[0];
    //     if (name && name.text) {
    //       this.basicInfoValue.mainCert = name.text;
    //     }
    //   }
    //   if (this.basicInfoValue.newCitizen) {
    //     const name = WHETHERORNOT.filter(
    //       (e) => e.value == this.basicInfoValue.newCitizen
    //     )[0];
    //     if (name && name.text) {
    //       this.basicInfoValue.newCitizen = name.text;
    //     }
    //   }
    //   // 获取客户数据
    //   this.profile = data.profile;
    //   this.getOneHistory();
    //   this.getProfile();
    //   // this.getCardNoListByCustNo();
    //   // this.getExpiryProductListByCustNo();
    // },
    // 系统标签
    async getMeshSystemLabel(e) {
      const post = {
        custNo: e || "",
      };
      const { data } = await getMeshSystemLabel(post);
      this.systemList = data;
    },
    // 客户走访情况历史记录
    // 客户走访情况历史记录
    async getOneHistory() {
      this.visitsQuery.customDetailId = this.detailId;
      const { data } = await getOneHistory(this.visitsQuery);
      if (data && data.list && data.list.length > 0) {
        this.historyVisitList = data.list;
        this.historyVisitList.forEach((item) => {
          item.showDetailText = "查看详情";
        });
      }
      const templist = data.list.filter((item) => {
        return item.visitType !== "1";
      });
      if (templist.length > 0) {
        const aaa = await this.getOneHistoryDetail(templist[0].id);
        this.interviewStatusValue = {
          ...this.basicInfoValue,
          ...aaa,
          visitImgFile: aaa.padVisitImgFile,
        };
      } else {
        this.interviewStatusValue = { ...this.basicInfoValue };
      }
      if (data && data.totalSize) {
        this.totalnum = parseInt(data.totalSize);
      }
    },
    // 客户单个走访记录
    async getOneHistoryDetail(e) {
      const { data } = await getOneHistoryDetail(e);
      return data;
    },
    async ops(e) {
      this.showItemD = true;
      let data = await this.getOneHistoryDetail(e.id);
      this.showItemDetail = {
        ...this.interviewStatusValue,
        ...data,
        visitImgFile: data.padVisitImgFile,
      };
      this.$refs.popContent.scrollTo(0, this.$refs.popContent.scrollHeight);
    },

    // 二期
    getProfile() {
      const params = ["aum", "deposit", "finance", "grant", "using", "overdue"]; //,"GDP"
      // if (!this.aumList[index]) return; /// AAA Del
      this.aumList = params.map((v, index) => {
        let M = this.profile[`${v}MonthInc`];
        let Y = this.profile[`${v}YearInc`];
        return {
          ...this.aumList[index],
          deposit: this.profile[v],
          monthIncValue: M && M.value,
          monthIncNature: M && M.nature,
          yearIncValue: Y && Y.value,
          yearIncNature: Y && Y.nature,
        };
      });
    },
    // 产品持有情况
    // 查询客户拥有的 标签项 产品: L03 系统: L01 网格: G01
    async getClientLabelByParentLabelCode(id) {
      const { data } = await getClientLabelByParentLabelCode({
        parentLabelCode: "G01,L03,L01",
        customDetailId: id,
        isTempCustom: this.isLskh ? "1" : "0",
      });
      this.systemLabel = data.L01;
      this.networkLabel = data.G01;
      this.prod = data.L03;
    },
    // 给客户打标签
    async addCustomerLabel(val) {
      const labelCodes =
        val.length > 0 ? val.map((item) => item.labelCode) : [];
      const params = {
        detailId: this.basicInfoValue.detailId || this.basicInfoValue.id,
        labelCodes,
      };
      const { state } = await addCustomerLabel(params);
      if (state === "000000") {
        this.$message.success("操作成功!");
        this.getClientLabelByParentLabelCode(this.basicInfoValue.detailId);
      }
    },
    // 产品到期
    async getExpiryProductListByCustNo() {
      const { data } = await getExpiryProductListByCustNo({
        custNo: this.basicInfoValue.custNo,
      });
      this.adventProductTableData = data || [];
    },
    // 交易记录
    async custViewProdBankCardDetail({ cardNumber, transactionRecordTime }) {
      if (!cardNumber || transactionRecordTime.length <= 0) return;
      const data = {
        custNo: this.basicInfoValue.custNo,
        cardNo: cardNumber,
        startDt: transactionRecordTime[0],
        endDt: transactionRecordTime[1],
      };
      const res = await custViewProdBankCardDetail(data);
      this.recordsTableData = res.data || [];
    },
    // 交易记录 卡号
    async getCardNoListByCustNo() {
      const { data } = await getCardNoListByCustNo({
        custNo: this.basicInfoValue.custNo,
        manageId: this.basicInfoValue.principalLinkman,
      });
      this.cardNumbers = data || [];
    },
    // 走访记录 options回显
    getDictsLabel(dicts, data, flag) {
      const prop = flag
        ? { label: "text", value: "code" }
        : { label: "label", value: "value" };
      const tempItem =
        dicts &&
        dicts.find((item) => {
          return item[prop.value] === data;
        });
      return (tempItem && tempItem[prop.label]) || "暂无";
    },
    getLabelName(dicts, data) {
      return data && data.includes(dicts) ? "是" : "否";
    },
    ellipsisLabel(val) {
      if (val.length >= 7) {
        val = val.slice(0, 3) + "..." + val.slice(-3);
      }
      return val;
    },
    // 字典
    getDicItemByType(type) {
      return getDicItemByType({ type });
    },
  },
}
</script>

<style scoped lang="scss">
</style>