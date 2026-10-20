<template>
  <PageContent>
    <div class="top">
      <h2>抽查信息</h2>
      <el-button size="mini" @click="cancel">返回</el-button>
      <i-form
        :gridColumns="2"
        ref="firstForm"
        :columns="columns"
        label-width="140px"
        label-position="right"
      />
    </div>
    <div class="table-box">
      <div class="table-box-left">
        <h2>抽查商户主体清单（至少50家）</h2>
        <el-form ref="searchform" :model="searchForm">
          <el-form-item label="选择抽查的商户主体" label-width="150px">
            <el-autocomplete
              v-model="searchForm.searchStr1"
              popper-class="my-autocomplete"
              clearable
              :fetch-suggestions="querySearchStore"
              value-key="storeName"
              placeholder="请输入要抽查的商户主体"
              @select="selectStore"
              style="width: 100%"
            >
              <template slot-scope="{ item }">
                <div class="name">{{ item.storeName }}</div>
                <span class="addr">{{ item.storeAddress }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
        </el-form>
        <i-table
          ref="table"
          :data="tableData1"
          align="center"
          stripe
          :maxHeight="maxHeight"
          :allColumns="tableColumns1"
          :total="total1"
          :pageNum="formInline1.pageNum"
          :pageSize="formInline1.pageSize"
        >
          <template #action="scope">
            <el-button type="text" @click="deleteStore(scope.row)"
              >删除</el-button
            >
            <!-- <i-table-btns
              :btn-lists="tableBtn1"
              @click="(type) => clickHandle(type, scope.row)"
              :data="scope.row"
            >
            </i-table-btns> -->
          </template>
        </i-table>
      </div>
      <div class="table-box-right">
        <h2>抽查银行网点清单（至少5个）</h2>
        <el-form ref="searchform" :model="searchForm">
          <el-form-item label="选择抽查的银行网点" label-width="150px">
            <el-autocomplete
              v-model="searchForm.searchStr2"
              popper-class="my-autocomplete"
              clearable
              :fetch-suggestions="querySearchBank"
              value-key="pointName"
              placeholder="请输入要抽查的银行网点"
              @select="selectBankPoint"
              style="width: 100%"
            >
              <template slot-scope="{ item }">
                <div class="name">{{ item.pointName }}</div>
                <span class="addr">{{ item.pointAddress }}</span>
              </template>
            </el-autocomplete>
          </el-form-item>
        </el-form>
        <i-table
          ref="table"
          :data="tableData2"
          align="center"
          stripe
          :maxHeight="maxHeight"
          :allColumns="tableColumns2"
          :total="total2"
          :pageNum="formInline2.pageNum"
          :pageSize="formInline2.pageSize"
        >
          <template #action="scope">
            <el-button type="text" @click="deletePoint(scope.row)"
              >删除</el-button
            >
            <!-- <i-table-btns
              :btn-lists="tableBtn2"
              @click="(type) => clickHandle(type, scope.row)"
              :data="scope.row"
            >
            </i-table-btns> -->
          </template>
        </i-table>
      </div>
    </div>
    <div class="bottom">
      <el-button @click="cancel">取消</el-button>
      <el-button type="primary" @click="confirm">确定</el-button>
    </div>
  </PageContent>
</template>

<script>
import addBankPointCheck from "./addBankPointCheck.vue";
import { mapState, mapMutations } from "vuex";
export default {
  components: {
    addBankPointCheck,
  },
  data() {
    return {
      columns: [
        {
          label: "抽查时间",
          key: "spotCheckTime",
          type: "daterange",
          required: true,
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          //   rangeSeparator: "至",
          format: "yyyy-MM-dd",
          valueFormat: "yyyy-MM-dd",
          rules: [
            {
              required: true,
              message: "请选择抽查时间",
              trigger: ["change"],
            },
          ],
        },
        {
          label: "抽查人员数量",
          key: "spotCheckPersonNum",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入抽查人员数量",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "备注",
          key: "remark",
          type: "textarea",
        },
      ],
      tableColumns1: [
        { label: "商户主体名称", prop: "storeName", width: 120 },
        { label: "商户主体行业", prop: "storeIndustry", width: 120 },
        { label: "商户主体地址", prop: "storeAddress", width: 120 },
        { label: "商户主体联系人", prop: "storePerson", width: 120 },
        { label: "联系电话", prop: "storePhone" },
        { label: "操作", slot: true },
      ],
      tableData1: [],
      tableBtn1: [
        {
          cname: "移除",
          ename: "deleteStore",
        },
      ],
      formInline1: {
        pageNum: 1,
        pageSize: 10,
      },
      tableColumns2: [
        { label: "网点名称", prop: "pointName" },
        { label: "网点地址", prop: "pointAddress" },
        { label: "网点负责人", prop: "dutyPerson" },
        { label: "联系电话", prop: "dutyPersonPhone" },
        { label: "操作", slot: true },
      ],
      tableData2: [],
      tableBtn2: [
        {
          cname: "移除",
          ename: "deletePoint",
        },
      ],
      formInline2: {
        pageNum: 1,
        pageSize: 10,
      },
      searchForm: {
        searchStr1: "",
        searchStr2: "",
      },
      maxHeight: "300px",
      storeList: [
        {
          id: "1",
          storeName: "张锡容小吃店",
          storeAddress: "浙江省金华市婺城区旌孝街276号",
          storePhone: "13865349817",
          storePerson: "张系统",
          storeIndustry: "餐饮",
          belongGrid: "旌孝街社区居委会",
        },
        {
          id: "2",
          storeName: "小胡水果店",
          storeAddress: "旌孝街272号",
          storePhone: "15967652765",
          storePerson: "胡志强",
          storeIndustry: "餐饮",
          belongGrid: "旌孝街社区居委会",
        },
        {
          id: "3",
          storeName: "兰溪手工拉面",
          storeAddress: "金华市婺城区旌孝街东市尚品东侧",
          storePhone: "19787263834",
          storePerson: "刘湾浅",
          storeIndustry: "餐饮",
          belongGrid: "旌孝街社区居委会",
        },
        {
          id: "4",
          storeName: "王晓玲西医诊所",
          storeAddress: "浙江省金华市婺城区旌孝街343号",
          storePhone: "17863766272",
          storePerson: "王晓玲",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "5",
          storeName: "博美门窗阳光房",
          storeAddress: "浙江省金华市金东区桃花路585号",
          storePhone: "13867969023",
          storePerson: "李花",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "6",
          storeName: "龙江贸易",
          storeAddress: "浙江省金华市金东区桃花路668号",
          storePhone: "13957995917",
          storePerson: "刘清华",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "7",
          storeName: "恒远地产",
          storeAddress: "浙江省金华市金东区桃花路672号",
          storePhone: "13777915561",
          storePerson: "王华强",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "8",
          storeName: "路捷汽车服务",
          storeAddress: "浙江省金华市金东区桃花路638号",
          storePhone: "13967480689",
          storePerson: "王斌",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "9",
          storeName: "别提多美",
          storeAddress: "金华市金东区桃花路中港浅水湾",
          storePhone: "13867566763",
          storePerson: "张梅",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "10",
          storeName: "老王装饰玻璃店",
          storeAddress: "浙江省金华市婺城区城东街道上浮桥社区桃花路473号",
          storePhone: "15105896766",
          storePerson: "王星宇",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
        {
          id: "11",
          storeName: "老肖面馆",
          storeAddress: "金华市金东区桃花路桃花苑",
          storePhone: "15956567888",
          storePerson: "肖倩",
          storeIndustry: "餐饮",
          belongGrid: "桃园社区居委会",
        },
      ],
      pointList: [
        {
          id: "1",
          pointName: "中国农业银行股份有限公司金华人民东路支行",
          pointAddress: "金华市婺城区人民东路1187号",
          belongGrid: "旌孝街社区",
          dutyPerson: "金乘风",
          dutyPersonPhone: "15088202157",
        },
        {
          id: "2",
          pointName: "中国银行股份有限公司金华锦都支行",
          pointAddress:
            "浙江省金华市婺城区城东街道东市街397号东市尚品幢101-108室",
          belongGrid: "旌孝街社区",
          dutyPerson: "张岚",
          dutyPersonPhone: "18905791835",
        },
        {
          id: "3",
          pointName: "金华成泰农商银行江北银泰分理处",
          pointAddress: "浙江省金华市婺城区解放东路18号（银泰天地店八馆1楼）",
          belongGrid: "明月楼社区",
          dutyPerson: "黎郁雯",
          dutyPersonPhone: "15282951262",
        },
        {
          id: "4",
          pointName: "中国银行金华市经济开发区支行",
          pointAddress: "浙江省金华市八一南街45号",
          belongGrid: "雅苑社区",
          dutyPerson: "陈献",
          dutyPersonPhone: "13590414637",
        },
        {
          id: "5",
          pointName: "中国银行金华市分行营业部",
          pointAddress: "金华市双溪西路59号",
          belongGrid: "金龙湾社区",
          dutyPerson: "童睿",
          dutyPersonPhone: "15958985177",
        },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", [
      "cashUseEnvCheckList",
      "cashUseEditStatus",
      "nowCashUseCheckInfo",
    ]),
    total1() {
      return this.tableData1.length;
    },
    total2() {
      return this.tableData2.length;
    },
  },
  methods: {
    ...mapMutations("accountBook", [
      "updateCashUseEnvCheckList",
      "updateCashEditStatus",
      "updateNowCashUseCheckInfo",
    ]),
    initDate() {
      if (this.cashUseEditStatus == "edit") {
        let { checkStoreList, checkPointList } = this.nowCashUseCheckInfo;
        this.tableData1 = checkStoreList;
        this.tableData2 = checkPointList;
        this.$nextTick(() => {
          this.$refs.firstForm.setForm(this.nowCashUseCheckInfo);
        });
      }
    },
    querySearchStore(queryString, cb) {
      cb(this.storeList);
    },
    querySearchBank(queryString, cb) {
      cb(this.pointList);
    },
    selectStore(e) {
      this.tableData1 = this.tableData1.filter((i) => i.id != e.id);
      this.tableData1.push({ ...e, checkStatus: "1", checkInfo: {} });
      this.searchForm.searchStr1 = "";
    },
    deleteStore(e) {
      this.tableData1 = this.tableData1.filter((i) => i.id != e.id);
    },
    selectBankPoint(e) {
      this.tableData2 = this.tableData2.filter((i) => i.id != e.id);
      this.tableData2.push({ ...e, checkStatus: "1", checkInfo: {} });
      this.searchForm.searchStr2 = "";
    },
    deletePoint(e) {
      this.tableData2 = this.tableData2.filter((i) => i.id != e.id);
    },
    confirm() {
      this.$refs.firstForm.getForm((formInfo) => {
        let obj = {};
        if (this.cashUseEditStatus == "add") {
          obj = {
            id: new Date().getTime(),
            ...formInfo,
            checkStoreList: this.tableData1,
            checkPointList: this.tableData2,
            ensureSpotCheckStoreNum: "50",
            ensureSpotCheckPointNum: "50",
            spotCheckStatus: "0",
            cashUseEnvScore: "90",
          };
        } else {
          obj = { ...this.nowCashUseCheckInfo };
          obj.checkStoreList = this.tableData1;
          obj.checkPointList = this.tableData2;
          obj = { ...obj, ...formInfo };
        }
        obj.actualSpotCheckStoreNum = obj.checkStoreList.length;
        obj.actualSpotCheckPointNum = obj.checkPointList.length;
        let arr = this.cashUseEnvCheckList;
        arr = arr.filter((i) => i.id != obj.id);
        this.updateCashUseEnvCheckList([obj, ...arr]);
        this.$router.push({ name: "UseCashEnv" });
      });
    },
    cancel() {
      this.tableData1 = [];
      this.tableData2 = [];
      this.$refs.firstForm.reset();
      this.$router.go(-1);
    },
  },
  mounted() {
    this.initDate();
  },
};
</script>

<style scoped lang="scss">
.top {
  padding: 15px;
  border: 1px solid #ddd;
  box-sizing: border-box;
  position: relative;
  .el-button {
    position: absolute;
    top: 15px;
    right: 15px;
  }
}
.table-box {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  &-left,
  &-right {
    width: 49%;
    border: 1px solid #ddd;
    padding: 20px;
    margin-bottom: 20px;
  }
}
h2 {
  font-size: 18px;
  font-weight: 700;
  margin-bottom: 15px;
}
.bottom {
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 99;
  .btnBar {
    width: 25%;
    padding-left: 10px;
    margin: 15px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
}
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;
    height: fit-content !important;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }
    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>