<template>
  <el-dialog
    title="扫楼清街"
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
import dayjs from "dayjs";
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
      // 模拟数据配置
      mockConfig: {
        depts: [
          "国家金融监督管理总局上城监管支局",
          "国家金融监督管理总局拱墅监管支局",
          "国家金融监督管理总局西湖监管支局",
          "国家金融监督管理总局滨江监管支局",
          "国家金融监督管理总局萧山监管支局",
          "国家金融监督管理总局余杭监管支局",
          "国家金融监督管理总局临平监管支局",
          "国家金融监督管理总局钱塘监管支局",
        ],
        persons: [
          "陈昊宇",
          "林诗瑶",
          "王梓轩",
          "赵一诺",
          "沈亦凡",
          "冯可欣",
          "李沐雪",
          "张语桐",
          "周瑾辰",
          "苏雨欣",
          "陆泽宇",
          "杨若曦",
          "江皓轩",
          "许悠然",
          "高梓涵",
          "马思远",
        ],
        areas: [
          "浙江省杭州市上城区彭埠街道三花国际3幢2单元101室",
          "浙江省杭州市拱墅区祥符街道中大银泰城2号楼1502室",
          "浙江省杭州市西湖区文三路478号华星时代广场B座",
          "浙江省杭州市滨江区江南大道100号恒生大厦",
          "浙江省杭州市萧山区市心北路万象汇1幢1001室",
          "浙江省杭州市余杭区未来科技城海创园5幢",
          "浙江省杭州市临平区南苑街道银泰城9幢",
          "浙江省杭州市钱塘区下沙街道金沙湖壹号2幢",
        ],
        subjectTypes: ["商户", "小微企业", "办公室"],
        reporters: [
          "沈亦凡",
          "冯可欣",
          "李沐雪",
          "张语桐",
          "周瑾辰",
          "苏雨欣",
          "陆泽宇",
          "杨若曦",
        ],
        reviewStatus: ["通过", "待复核"],
      },
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
        { label: "扫楼清街上报", key: "test1", value: 24 },
        { label: "扫楼清街复核通过", key: "test2", value: 11 },
        { label: "扫楼清街待复核", key: "test3", value: 32 },
      ];
      arr.forEach((item) => {
        item.value = Math.floor(Math.random() * 100);
        item.xxx = this.dialogKey;
      });
      return arr;
    },
    tableColumns() {
      const arr = [
        { label: "排查编号", prop: "test1", width: 140 },
        { label: "排查日期", prop: "test2", width: 140 },
        {
          label: "牵头部门",
          prop: "test3",
          width: 140,
          showOverFlowTooltip: true,
        },
        { label: "牵头人员", prop: "test4", width: 140 },
        {
          label: "排查区域",
          prop: "test5",
          width: 140,
          showOverFlowTooltip: true,
        },
        { label: "主体类型", prop: "test6", width: 140 },
        { label: "主体数量", prop: "test7", width: 140 },
        // { label: "排查人次", prop: "test8", width: 140 },
        { label: "填报人", prop: "test10", width: 140 },
        { label: "填报时间", prop: "test9", width: 170 },
        { label: "状态", prop: "test11", width: 140 },
      ];
      return arr;
    },
  },
  methods: {
    initData() {
      // this.tableData = [
      //   {
      //     test1: "PC202603100",
      //     test2: "2026-03-01",
      //     test3: "国家金融监督管理总局浙江监管局",
      //     test4: "陈昊宇",
      //     test5: "杭州市上城区",
      //     test6: "商户",
      //     test7: "8",
      //     test8: "2",
      //     test9: "沈亦凡",
      //     test10: "2026-03-01 09:15:00",
      //     test11: "待复核",
      //   },
      // ];
      this.dialogKey++;
      this.generateMockData(15);
    },
    generateMockData(count) {
      const { depts, persons, areas, subjectTypes, reporters, reviewStatus } =
        this.mockConfig;
      const baseDate = dayjs("2026-02-01");
      const mockData = [];

      for (let i = 0; i < count; i++) {
        // 随机日期（2026-02-01 至 2026-02-10）
        const randomDay = Math.floor(Math.random() * 10);
        const checkDate = baseDate.add(randomDay, "day").format("YYYY-MM-DD");

        // 随机时间（08:00 至 18:00）
        const randomHour = 8 + Math.floor(Math.random() * 10);
        const randomMinute = Math.floor(Math.random() * 60);
        const reportTime = `${checkDate} ${randomHour
          .toString()
          .padStart(2, "0")}:${randomMinute.toString().padStart(2, "0")}:00`;

        // 随机生成数据
        mockData.push({
          test1: `PC${checkDate.replace(/-/g, "")}${i
            .toString()
            .padStart(3, "0")}`,
          test2: checkDate,
          test3: depts[Math.floor(Math.random() * depts.length)],
          test4: persons[Math.floor(Math.random() * persons.length)],
          test5: areas[Math.floor(Math.random() * areas.length)],
          test6: subjectTypes[Math.floor(Math.random() * subjectTypes.length)],
          test7: Math.floor(Math.random() * 10) + 1, // 1-10
          test8: reporters[Math.floor(Math.random() * reporters.length)],
          test9: reportTime,
          test10: reviewStatus[Math.floor(Math.random() * reviewStatus.length)],
          test11: "待复核",
        });
      }

      this.tableData = mockData;
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
