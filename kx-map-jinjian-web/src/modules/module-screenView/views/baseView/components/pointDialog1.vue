<template>
  <el-dialog
    title="线索报送"
    :visible.sync="dialogVisible"
    width="800px"
    :before-close="handleClose"
    class="collect-class"
    :close-on-click-modal="false"
    :append-to-body="true"
  >
    <div class="dialog-container">
      <div class="card-wrapper">
        <div class="card-item" v-for="item in cardColumns" :key="item.key">
          <div class="label">{{ item.label }}</div>
          <div class="value">{{ item.value }}</div>
        </div>
      </div>
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        stripe
        :maxHeight="500"
        :total="total"
        :allColumns="tableColumns"
        :showPagination="false"
      >
      </i-table>
    </div>
  </el-dialog>
</template>
<script>
export default {
  props: {
    value: {
      type: Boolean,
    },
    dataList: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      dialogVisible: this.value,
      tableData: [],
      total: 1,
      dialogKey: 1,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
        if (val) {
          this.initData();
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit("input", val);
      },
    },
  },
  computed: {
    cardColumns() {
      const arr = [
        { label: "风险线索上报", key: "test4", value: 21 },
        { label: "风险线索已办结", key: "test5", value: 23 },
        { label: "风险线索待办结", key: "test6", value: 15 },
      ];
      arr.forEach((item) => {
        item.value = Math.floor(Math.random() * 100);
        item.xx = this.dialogKey;
      });
      return arr;
    },
    tableColumns() {
      const arr = [
        { label: "线索编号", prop: "test1", width: 160 },
        {
          label: "线索来源机构",
          prop: "test2",
          width: 140,
          showOverFlowTooltip: true,
        },
        { label: "线索来源渠道", prop: "test3", width: 140 },
        { label: "填报时间", prop: "test4", width: 170 },
        {
          label: "涉险主体信息",
          prop: "test5",
          width: 140,
          showOverFlowTooltip: true,
        },
        { label: "线索类型", prop: "test6", width: 140 },
        { label: "线索状态", prop: "test7", width: 140 },
        { label: "移交状态", prop: "test8", width: 140 },
      ];
      return arr;
    },
  },
  methods: {
    initData() {
      this.dialogKey++;
      this.tableData = [
        {
          test1: "FX-20260310-001",
          test2: "中国建设银行股份有限公司杭州西溪支行",
          test3: "网格走访",
          test4: "2026-03-01 09:00:00",
          test5:
            "杭州上城某某商务服务有限公司 统一社会信用代码：913301000MA210X20 地址：浙江省杭州市上城区彭埠街道",
          test6: "非法集资",
          test7: "待复核",
          test8: "已移交",
        },
        {
          test1: "FX-20260318-001",
          test2: "中国建设银行股份有限公司杭州文晖支行",
          test3: "网格走访",
          test4: "2026-03-18 08:30:00",
          test5:
            "杭州上城某某商务服务有限公司\n统一社会信用代码：91330102MA2H8182C3\n地址：浙江省杭州市上城区彭埠街道花家港路58号东润寓苑3幢2单元102室",
          test6: "不法中介",
          test7: "待复核",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-002",
          test2: "中国建设银行股份有限公司宁波江北支行",
          test3: "客户举报",
          test4: "2026-03-18 09:07:00",
          test5:
            "宁波鄞州某某投资咨询有限公司\n统一社会信用代码：91330212MA284F5D5E\n地址：浙江省宁波市鄞州区首南街道天童南路556号银座环球中心1幢1502室",
          test6: "非法集资",
          test7: "复核通过",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-003",
          test2: "中国建设银行股份有限公司湖州吴兴支行",
          test3: "风险监测平台",
          test4: "2026-03-18 09:44:00",
          test5:
            "温州鹿城某某资产管理有限公司\n统一社会信用代码：91330302MA29H8769H\n地址：浙江省温州市鹿城区五马街道车站大道789号财富中心8楼802室",
          test6: "非法放贷",
          test7: "退回补正",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-004",
          test2: "中国建设银行股份有限公司温州鹿城支行",
          test3: "网格走访",
          test4: "2026-03-18 10:15:00",
          test5:
            "温州龙湾某某金融服务有限公司\n统一社会信用代码：91330303MA284G6E7F\n地址：浙江省温州市龙湾区永中街道永定路1188号万达商业广场1幢2105室",
          test6: "不法中介",
          test7: "待复核",
          test8: "已移交",
          test9: "张三",
          test10: "2026-03-18 14:20:00",
          test11: "已受理",
        },
        {
          test1: "FX-20260318-005",
          test2: "中国建设银行股份有限公司绍兴越城支行",
          test3: "客户举报",
          test4: "2026-03-18 11:02:00",
          test5:
            "绍兴柯桥某某投资管理有限公司\n统一社会信用代码：91330603MA29H9870J\n地址：浙江省绍兴市柯桥区柯桥街道金柯桥大道1299号万国中心B座1808室",
          test6: "非法集资",
          test7: "复核通过",
          test8: "已移交",
          test9: "李四",
          test10: "2026-03-18 15:30:00",
          test11: "处理中",
        },
        {
          test1: "FX-20260318-006",
          test2: "中国建设银行股份有限公司嘉兴南湖支行",
          test3: "风险监测平台",
          test4: "2026-03-18 11:45:00",
          test5:
            "嘉兴秀洲某某贷款咨询有限公司\n统一社会信用代码：91330411MA2GJ7892K\n地址：浙江省嘉兴市秀洲区新城街道洪兴路2399宝地大厦10楼1008室",
          test6: "非法放贷",
          test7: "退回补正",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-007",
          test2: "中国建设银行股份有限公司金华婺城支行",
          test3: "网格走访",
          test4: "2026-03-18 13:20:00",
          test5:
            "金华金东某某商务咨询有限公司\n统一社会信用代码：91330703MA2DD6785L\n地址：浙江省金华市金东区多湖街道光南路1699号万达广场1幢2506室",
          test6: "不法中介",
          test7: "待复核",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-008",
          test2: "中国建设银行股份有限公司台州椒江支行",
          test3: "客户举报",
          test4: "2026-03-18 14:10:00",
          test5:
            "台州黄岩某某投资有限公司\n统一社会信用代码：91331003MA2HE5678M\n地址：浙江省台州市黄岩区西城街道世纪大道1号黄岩大厦12楼1203室",
          test6: "非法集资",
          test7: "复核通过",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-009",
          test2: "中国建设银行股份有限公司丽水莲都支行",
          test3: "风险监测平台",
          test4: "2026-03-18 15:05:00",
          test5:
            "丽水龙泉某某金融信息服务有限公司\n统一社会信用代码：91331181MA2E04567N\n地址：浙江省丽水市龙泉市龙渊街道中山西路123号华楼街商城4楼402室",
          test6: "非法放贷",
          test7: "退回补正",
          test8: "已移交",
          test9: "王五",
          test10: "2026-03-18 16:40:00",
          test11: "补充材料",
        },
        {
          test1: "FX-20260318-010",
          test2: "中国建设银行股份有限公司衢州柯城支行",
          test3: "网格走访",
          test4: "2026-03-18 16:20:00",
          test5:
            "衢州江山某某资产管理有限公司\n统一社会信用代码：91330881MA2DG3458P\n地址：浙江省衢州市江山市虎山街道鹿溪南路388号时代广场2幢1805室",
          test6: "不法中介",
          test7: "待复核",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-011",
          test2: "中国建设银行股份有限公司舟山定海支行",
          test3: "客户举报",
          test4: "2026-03-18 17:15:00",
          test5:
            "舟山普陀某某投资咨询有限公司\n统一社会信用代码：91330903MA2K82349Q\n地址：浙江省舟山市普陀区东港街道海印路850号杉杉普陀天地1幢3楼308室",
          test6: "非法集资",
          test7: "复核通过",
          test8: "已移交",
          test9: "赵六",
          test10: "2026-03-18 18:00:00",
          test11: "已办结",
        },
        {
          test1: "FX-20260318-012",
          test2: "中国建设银行股份有限公司杭州滨江支行",
          test3: "风险监测平台",
          test4: "2026-03-19 08:10:00",
          test5:
            "杭州西湖某某贷款服务有限公司\n统一社会信用代码：91330106MA2J01230R\n地址：浙江省杭州市西湖区文三路90号东部软件园创新大厦B座10楼1002室",
          test6: "非法放贷",
          test7: "退回补正",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-013",
          test2: "中国建设银行股份有限公司宁波鄞州支行",
          test3: "网格走访",
          test4: "2026-03-19 09:05:00",
          test5:
            "宁波海曙某某商务服务有限公司\n统一社会信用代码：91330203MA2C59871S\n地址：浙江省宁波市海曙区解放南路202号建设大厦15楼1506室",
          test6: "不法中介",
          test7: "待复核",
          test8: "已移交",
          test9: "钱七",
          test10: "2026-03-19 10:30:00",
          test11: "受理中",
        },
        {
          test1: "FX-20260318-014",
          test2: "中国建设银行股份有限公司温州瓯海支行",
          test3: "客户举报",
          test4: "2026-03-19 10:20:00",
          test5:
            "温州乐清某某投资管理有限公司\n统一社会信用代码：91330382MA2A78902T\n地址：浙江省温州市乐清市乐成街道伯乐东路918号国贸大厦18楼1808室",
          test6: "非法集资",
          test7: "复核通过",
          test8: "未移交",
          test9: "-",
          test10: "-",
          test11: "-",
        },
        {
          test1: "FX-20260318-015",
          test2: "中国建设银行股份有限公司绍兴柯桥支行",
          test3: "风险监测平台",
          test4: "2026-03-19 11:15:00",
          test5:
            "绍兴上虞某某金融咨询有限公司\n统一社会信用代码：91330604MA2J87653U\n地址：浙江省绍兴市上虞区百官街道市民大道688号闰土大厦12楼1205室",
          test6: "非法放贷",
          test7: "退回补正",
          test8: "已移交",
          test9: "孙八",
          test10: "2026-03-19 12:40:00",
          test11: "待补充",
        },
      ];
    },
    handleClose() {
      this.dialogVisible = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.dialog-container {
  // padding-bottom: 40px;
  .card-wrapper {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 10px;
    line-height: 1.8;
    margin-bottom: 10px;
    .card-item {
      border: 1px solid #f6f6f6;
      border-radius: 6px;
      padding: 10px;
    }
    .label {
      font-size: 18px;
      color: #91cbf9;
    }
    .value {
      font-size: 24px;
      color: #2d8cf0;
    }
  }
}
</style>
