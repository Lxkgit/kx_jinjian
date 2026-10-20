<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        class="filterBar"
        :gridColumns="3"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <div class="i-table">
      <!-- 按钮 -->
      <i-button-bar
        style="margin-bottom: 0"
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
        class="button-bar"
      >
      </i-button-bar>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      >
        <!-- 线索状态插槽（颜色标记） -->
        <template #clueStatus="scope">
          <div :class="getClueStatusClass(scope.row.clueStatus)">
            {{ scope.row.clueStatus || "-" }}
          </div>
        </template>
        <!-- 移交状态插槽（颜色标记） -->
        <template #transferStatus="scope">
          <div :class="getTransferStatusClass(scope.row.transferStatus)">
            {{ scope.row.transferStatus || "-" }}
          </div>
        </template>
        <!-- 操作插槽 -->
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </div>

    <addEvent v-model="showAdd" @success="handlerSuccess"></addEvent>

    <addEvent
      v-model="showEdit"
      :type="1"
      :defaultValue="selectedRow"
      @success="handlerSuccess"
    ></addEvent>
    <appearHandleDialog v-model="showAppear"></appearHandleDialog>
    <ImportRiskClue :visible.sync="showImport" @success="handleImportSuccess" />
  </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import msgWatchDetail from "../components/msgWatchDetail.vue";
import addEvent from "../components/addEvent.vue";
import appearHandleDialog from "../components/appearHandleDialog.vue";
import ImportRiskClue from "../components/ImportRiskClue.vue";
import service from "../api";
export default {
  components: { msgWatchDetail, addEvent, appearHandleDialog, ImportRiskClue },
  mixins: [pageMixin],
  data() {
    return {
      showImport: false,
      loading: false,
      showAdd: false,
      maxHeight: window.innerHeight - 350,
      selectedRow: {},
      showEdit: false,
      // 搜索条件
      searchColumns: [
        {
          label: "报送时间",
          key: "clueSubmitTime",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
        },
        {
          label: "线索类型",
          key: "clueType",
          type: "select",
          clearable: true,
          options: [
            { label: "不法中介", value: "不法中介" },
            { label: "非法集资", value: "非法集资" },
            { label: "非法放贷", value: "非法放贷" },
          ],
        },
        {
          label: "审核状态",
          key: "reviewStatus",
          type: "select",
          clearable: true,
          options: [
            { label: "待复核", value: "待复核" },
            { label: "复核通过", value: "复核通过" },
            { label: "退回补正", value: "退回补正" },
          ],
        },
      ],
      // 顶部按钮
      btnLists: [
        {
          cname: "新增",
          ename: "addHandle",
          type: "primary",
        },
        {
          cname: "导出",
          ename: "exportHandle",
        },
        {
          cname: "导入",
          ename: "importHandle",
        },
      ],
      // 列表头
      columns: [
        { label: "线索编号", prop: "clueNo", width: 180 },
        { label: "线索来源机构", prop: "clueSourceOrg", width: 180 },
        { label: "线索来源渠道", prop: "clueSourceChannel", width: 150 },
        { label: "线索报送时间", prop: "clueSubmitTime", width: 180 },
        {
          label: "涉险主体信息",
          prop: "riskSubjectInfo",
          width: 220,
          showOverFlowTooltip: true,
        },
        { label: "线索类型", prop: "clueType", width: 120 },
        { label: "线索状态", prop: "clueStatus", slot: true, width: 120 },
        { label: "移交状态", prop: "transferStatus", slot: true, width: 120 },
        { label: "接收单位签收人", prop: "receiveSigner", width: 150 },
        { label: "签收日期", prop: "signDate", width: 180 },
        { label: "接收反馈", prop: "receiveFeedback", width: 150 },
        { label: "操作", slot: true, fixed: "right", width: 200 },
      ],
      tableData: [],
      // 行操作
      tableBtn: [
        { cname: "编辑", ename: "editHandle" },
        { cname: "移送", ename: "transferHandle" },
      ],
      showAppear: false,
      // 🔴 修复：将mockData移到data返回对象中（核心修改）
      mockData: [
        // 原有3条 + 新增21条（总计24条）
        {
          clueNo: "FX-20260318-001",
          clueSourceOrg: "中国建设银行股份有限公司杭州文晖支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-18 08:30:00",
          riskSubjectInfo:
            "杭州上城某某商务服务有限公司\n统一社会信用代码：91330102MA2H8182C3\n地址：浙江省杭州市上城区彭埠街道花家港路58号东润寓苑3幢2单元102室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-002",
          clueSourceOrg: "中国建设银行股份有限公司宁波江北支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-18 09:07:00",
          riskSubjectInfo:
            "宁波鄞州某某投资咨询有限公司\n统一社会信用代码：91330212MA284F5D5E\n地址：浙江省宁波市鄞州区首南街道天童南路556号银座环球中心1幢1502室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-003",
          clueSourceOrg: "中国建设银行股份有限公司湖州吴兴支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-18 09:44:00",
          riskSubjectInfo:
            "温州鹿城某某资产管理有限公司\n统一社会信用代码：91330302MA29H8769H\n地址：浙江省温州市鹿城区五马街道车站大道789号财富中心8楼802室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-004",
          clueSourceOrg: "中国建设银行股份有限公司温州鹿城支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-18 10:15:00",
          riskSubjectInfo:
            "温州龙湾某某金融服务有限公司\n统一社会信用代码：91330303MA284G6E7F\n地址：浙江省温州市龙湾区永中街道永定路1188号万达商业广场1幢2105室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "已移交",
          receiveSigner: "张三",
          signDate: "2026-03-18 14:20:00",
          receiveFeedback: "已受理",
        },
        {
          clueNo: "FX-20260318-005",
          clueSourceOrg: "中国建设银行股份有限公司绍兴越城支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-18 11:02:00",
          riskSubjectInfo:
            "绍兴柯桥某某投资管理有限公司\n统一社会信用代码：91330603MA29H9870J\n地址：浙江省绍兴市柯桥区柯桥街道金柯桥大道1299号万国中心B座1808室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "已移交",
          receiveSigner: "李四",
          signDate: "2026-03-18 15:30:00",
          receiveFeedback: "处理中",
        },
        {
          clueNo: "FX-20260318-006",
          clueSourceOrg: "中国建设银行股份有限公司嘉兴南湖支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-18 11:45:00",
          riskSubjectInfo:
            "嘉兴秀洲某某贷款咨询有限公司\n统一社会信用代码：91330411MA2GJ7892K\n地址：浙江省嘉兴市秀洲区新城街道洪兴路2399宝地大厦10楼1008室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-007",
          clueSourceOrg: "中国建设银行股份有限公司金华婺城支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-18 13:20:00",
          riskSubjectInfo:
            "金华金东某某商务咨询有限公司\n统一社会信用代码：91330703MA2DD6785L\n地址：浙江省金华市金东区多湖街道光南路1699号万达广场1幢2506室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-008",
          clueSourceOrg: "中国建设银行股份有限公司台州椒江支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-18 14:10:00",
          riskSubjectInfo:
            "台州黄岩某某投资有限公司\n统一社会信用代码：91331003MA2HE5678M\n地址：浙江省台州市黄岩区西城街道世纪大道1号黄岩大厦12楼1203室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-009",
          clueSourceOrg: "中国建设银行股份有限公司丽水莲都支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-18 15:05:00",
          riskSubjectInfo:
            "丽水龙泉某某金融信息服务有限公司\n统一社会信用代码：91331181MA2E04567N\n地址：浙江省丽水市龙泉市龙渊街道中山西路123号华楼街商城4楼402室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "已移交",
          receiveSigner: "王五",
          signDate: "2026-03-18 16:40:00",
          receiveFeedback: "补充材料",
        },
        {
          clueNo: "FX-20260318-010",
          clueSourceOrg: "中国建设银行股份有限公司衢州柯城支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-18 16:20:00",
          riskSubjectInfo:
            "衢州江山某某资产管理有限公司\n统一社会信用代码：91330881MA2DG3458P\n地址：浙江省衢州市江山市虎山街道鹿溪南路388号时代广场2幢1805室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-011",
          clueSourceOrg: "中国建设银行股份有限公司舟山定海支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-18 17:15:00",
          riskSubjectInfo:
            "舟山普陀某某投资咨询有限公司\n统一社会信用代码：91330903MA2K82349Q\n地址：浙江省舟山市普陀区东港街道海印路850号杉杉普陀天地1幢3楼308室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "已移交",
          receiveSigner: "赵六",
          signDate: "2026-03-18 18:00:00",
          receiveFeedback: "已办结",
        },
        {
          clueNo: "FX-20260318-012",
          clueSourceOrg: "中国建设银行股份有限公司杭州滨江支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-19 08:10:00",
          riskSubjectInfo:
            "杭州西湖某某贷款服务有限公司\n统一社会信用代码：91330106MA2J01230R\n地址：浙江省杭州市西湖区文三路90号东部软件园创新大厦B座10楼1002室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-013",
          clueSourceOrg: "中国建设银行股份有限公司宁波鄞州支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-19 09:05:00",
          riskSubjectInfo:
            "宁波海曙某某商务服务有限公司\n统一社会信用代码：91330203MA2C59871S\n地址：浙江省宁波市海曙区解放南路202号建设大厦15楼1506室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "已移交",
          receiveSigner: "钱七",
          signDate: "2026-03-19 10:30:00",
          receiveFeedback: "受理中",
        },
        {
          clueNo: "FX-20260318-014",
          clueSourceOrg: "中国建设银行股份有限公司温州瓯海支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-19 10:20:00",
          riskSubjectInfo:
            "温州乐清某某投资管理有限公司\n统一社会信用代码：91330382MA2A78902T\n地址：浙江省温州市乐清市乐成街道伯乐东路918号国贸大厦18楼1808室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-015",
          clueSourceOrg: "中国建设银行股份有限公司绍兴柯桥支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-19 11:15:00",
          riskSubjectInfo:
            "绍兴上虞某某金融咨询有限公司\n统一社会信用代码：91330604MA2J87653U\n地址：浙江省绍兴市上虞区百官街道市民大道688号闰土大厦12楼1205室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "已移交",
          receiveSigner: "孙八",
          signDate: "2026-03-19 12:40:00",
          receiveFeedback: "待补充",
        },
        {
          clueNo: "FX-20260318-016",
          clueSourceOrg: "中国建设银行股份有限公司嘉兴秀洲支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-19 13:30:00",
          riskSubjectInfo:
            "嘉兴平湖某某商务咨询有限公司\n统一社会信用代码：91330482MA2F65434V\n地址：浙江省嘉兴市平湖市当湖街道东湖大道617号嘉华广场2幢9楼903室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-017",
          clueSourceOrg: "中国建设银行股份有限公司金华金东支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-19 14:25:00",
          riskSubjectInfo:
            "金华义乌某某投资有限公司\n统一社会信用代码：91330782MA2E54325W\n地址：浙江省金华市义乌市稠城街道宾王路158号银都商务大厦16楼1609室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "已移交",
          receiveSigner: "周九",
          signDate: "2026-03-19 15:50:00",
          receiveFeedback: "处理完毕",
        },
        {
          clueNo: "FX-20260318-018",
          clueSourceOrg: "中国建设银行股份有限公司台州黄岩支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-19 16:10:00",
          riskSubjectInfo:
            "台州路桥某某贷款信息服务有限公司\n统一社会信用代码：91331004MA2XC1236X\n地址：浙江省台州市路桥区路北街道腾达路699号刚泰艺鼎广场B座8楼806室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-019",
          clueSourceOrg: "中国建设银行股份有限公司丽水龙泉支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-19 17:05:00",
          riskSubjectInfo:
            "丽水莲都某某资产管理有限公司\n统一社会信用代码：91331102MA2E89017Y\n地址：浙江省丽水市莲都区花园路2号商会大厦19楼1908室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "已移交",
          receiveSigner: "吴十",
          signDate: "2026-03-19 18:20:00",
          receiveFeedback: "已受理",
        },
        {
          clueNo: "FX-20260318-020",
          clueSourceOrg: "中国建设银行股份有限公司衢州衢江支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-20 08:40:00",
          riskSubjectInfo:
            "衢州龙游某某投资咨询有限公司\n统一社会信用代码：91330825MA2H76548Z\n地址：浙江省衢州市龙游县龙洲街道太平西路1号东方广场1幢10楼1005室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-021",
          clueSourceOrg: "中国建设银行股份有限公司舟山普陀支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-20 09:35:00",
          riskSubjectInfo:
            "舟山岱山某某金融服务有限公司\n统一社会信用代码：91330921MA2K65439AA\n地址：浙江省舟山市岱山县高亭镇沿港中路118号华之友广场3楼302室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "已移交",
          receiveSigner: "郑十一",
          signDate: "2026-03-20 10:50:00",
          receiveFeedback: "补充材料后办结",
        },
        {
          clueNo: "FX-20260318-022",
          clueSourceOrg: "中国建设银行股份有限公司杭州余杭支行",
          clueSourceChannel: "网格走访",
          clueSubmitTime: "2026-03-20 10:25:00",
          riskSubjectInfo:
            "杭州钱塘某某商务服务有限公司\n统一社会信用代码：91330114MA2J54321BB\n地址：浙江省杭州市钱塘区下沙街道金沙大道600号东部国际商务中心1幢20楼2008室",
          clueType: "不法中介",
          clueStatus: "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
        {
          clueNo: "FX-20260318-023",
          clueSourceOrg: "中国建设银行股份有限公司宁波镇海支行",
          clueSourceChannel: "客户举报",
          clueSubmitTime: "2026-03-20 11:15:00",
          riskSubjectInfo:
            "宁波北仑某某投资管理有限公司\n统一社会信用代码：91330206MA2C87654CC\n地址：浙江省宁波市北仑区新碶街道长江路1166号富邦世纪广场1幢8楼803室",
          clueType: "非法集资",
          clueStatus: "复核通过",
          transferStatus: "已移交",
          receiveSigner: "王十二",
          signDate: "2026-03-20 12:40:00",
          receiveFeedback: "办结",
        },
        {
          clueNo: "FX-20260318-024",
          clueSourceOrg: "中国建设银行股份有限公司温州瑞安支行",
          clueSourceChannel: "风险监测平台",
          clueSubmitTime: "2026-03-20 12:30:00",
          riskSubjectInfo:
            "温州永嘉某某贷款咨询有限公司\n统一社会信用代码：91330324MA29D5432DD\n地址：浙江省温州市永嘉县瓯北街道阳光大道1号铂尔曼大酒店写字楼15楼1506室",
          clueType: "非法放贷",
          clueStatus: "退回补正",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        },
      ],
    };
  },
  methods: {
    // 获取列表数据
    async fetchList() {
      try {
        this.loading = true;
        // const formInfo = await this.$refs.filterBar.getForm();

        // // 构建查询参数
        // const params = {
        //   pageNum: this.formInline.pageNum,
        //   pageSize: this.formInline.pageSize,
        //   ...formInfo,
        // };

        // // 处理日期范围参数
        // if (formInfo.clueSubmitTime && Array.isArray(formInfo.clueSubmitTime)) {
        //   params.clueSubmitTimeStart = formInfo.clueSubmitTime[0];
        //   params.clueSubmitTimeEnd = formInfo.clueSubmitTime[1];
        //   delete params.clueSubmitTime;
        // }

        // 模拟接口调用（替换为你的实际接口）
        // service.getGridEventList(params).then(res => {
        //   this.handleListResponse(res);
        // }).catch(() => {
        //   this.loading = false;
        // });

        // 赋值模拟数据
        this.tableData = this.mockData;
        this.total = this.mockData.length;
        this.loading = false;
      } catch (error) {
        console.error("获取列表失败:", error);
        this.loading = false;
      }
    },
    // 根据状态返回样式类
    getClueStatusClass(status) {
      const map = {
        待复核: "status-wait",
        复核通过: "status-pass",
        退回补正: "status-reject",
      };
      return map[status] || "";
    },
    getTransferStatusClass(status) {
      const map = { 未移交: "status-wait", 已移交: "status-pass" };
      return map[status] || "";
    },
    // 搜索方法
    searchHandle() {
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    resetHandle() {
      this.$refs.filterBar.reset();
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    currentChange(pageNum) {
      this.formInline.pageNum = pageNum;
      this.fetchList();
    },
    sizeChange(pageSize) {
      this.formInline.pageSize = pageSize;
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    exportHandle() {
      this.$message.success("已导出 20 条线索！");
    },
    // 导入成功回调（接收导入的数据）
    handleImportSuccess(importedData) {
      // 🔴 这里将导入的数据追加到本地列表
      // 注意：如果是真实环境，这里应该调用接口批量新增，然后刷新列表
      this.tableData = [...importedData, ...this.tableData];
      this.total = this.tableData.length;
      this.$message.success(`已成功添加 ${importedData.length} 条数据到列表`);

      // 如果需要刷新列表，也可以调用 this.fetchList();
    },
    clickHandle(type, row) {
      switch (type) {
        case "addHandle":
          this.showAdd = true;
          break;
        case "editHandle":
          this.selectedRow = row;
          this.showEdit = true; // 编辑时打开新增弹窗
          break;
        case "importHandle":
          this.showImport = true; // 移送弹窗
          break;
        case "transferHandle":
          this.showAppear = true; // 移送弹窗
          break;
        case "exportHandle":
          this.exportHandle();
          break;
      }
    },
    handlerSuccess() {
      this.formInline.pageNum = 1;
      this.fetchList();
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchList();
    });
  },
};
</script>

<style scoped lang="scss">
.button-bar {
  padding: 20px 0;
}
.filterBar-box {
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  padding-bottom: 0;
  display: flex;
  justify-content: space-between;

  .filterBar {
    width: 75%;
  }
}

.table-title {
  padding: 16px;
  height: 68px;
}

.i-table {
  height: calc(100vh - 64px - 56px - 68px - 10px);
  background: #fff;
  padding: 0 16px;
  padding-bottom: 10px;
}

// 状态标签样式
.status-pass {
  padding: 2px 8px;
  background: rgba(55, 195, 140, 0.2);
  color: #37c38c;
  border-radius: 4px;
  font-size: 14px;
}

.status-reject {
  padding: 2px 8px;
  background: rgba(220, 85, 100, 0.2);
  color: #dc5564;
  border-radius: 4px;
  font-size: 14px;
}

.status-wait {
  padding: 2px 8px;
  background: rgba(255, 170, 0, 0.2);
  color: #ffaa00;
  border-radius: 4px;
  font-size: 14px;
}
</style>
