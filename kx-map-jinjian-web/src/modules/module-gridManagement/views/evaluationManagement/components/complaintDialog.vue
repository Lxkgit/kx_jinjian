<template>
  <i-dialog
    title="投诉事件信息"
    v-model="showDialog"
    width="80%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="false"
  >
    <div style="padding-bottom: 60px">
      <div style="font-weight: bold; font-size: 18px; color: #000">
        {{ defaultValue.networkPoint }}
      </div>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
      >
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
  </i-dialog>
</template>
  
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  name: "complaintDialog",
  mixins: [dialogMixin, pageMixin],
  dicts: ["complaint_source"],
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      tableBtn: [{ cname: "详情", ename: "viewDetail" }],
      columns: [
        { label: "投诉人", prop: "complainPerson", showOverFlowTooltip: true },
        {
          label: "投诉人联系电话",
          prop: "complainPhone",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "投诉来源",
          prop: "complainType",
          showOverFlowTooltip: true,
          width: 120,
          formatter: ({ complainType }) => {
            return this.dict.label.complaint_source[complainType];
          },
        },
        {
          label: "投诉时间",
          prop: "complainTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "投诉事件描述",
          prop: "complainMsgDesc",
          showOverFlowTooltip: true,
          width: 300,
        },
        {
          label: "网点联络员",
          prop: "dotLinkMan",
          showOverFlowTooltip: true,
        },
        {
          label: "处理状态",
          prop: "disposeType",
          showOverFlowTooltip: true,
        },
        {
          label: "操作",
          slot: true,
        },
      ],
      templateData: [
        [
          {
            complainPerson: "刘思莲",
            complainPhone: "15239365738",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "近期我多次尝试在贵行的取款机上取款，但机器频繁出现故障，导致无法正常取款，给我的日常生活带来了极大不便。",
            dotLinkMan: "秦子美",
            disposeType: "处理中",
          },
          {
            complainPerson: "徐瑞娟",
            complainPhone: "18525749836",
            complainType: "2",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在行兑换的现金中，多次发现纸币存在破损、污渍或折痕严重等问题，这样的纸币在流通中极易被拒收。",
            dotLinkMan: "谷芳菲",
            disposeType: "处理中",
          },
        ],
        [
          {
            complainPerson: "董芬芬",
            complainPhone: "13975417011",
            complainType: "2",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "ATM机位置设置不合理，距离居住或工作的地方较远，使用起来非常不方便",
            dotLinkMan: "傅子萱",
            disposeType: "处理中",
          },
          {
            complainPerson: "刘元灵",
            complainPhone: "13916677872",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在办理现金存取业务时，经常需要排队等待很长时间，尤其是在高峰期，工作人员处理速度较慢，导致客户体验极差。",
            dotLinkMan: "罗康健",
            disposeType: "处理中",
          },
          {
            complainPerson: "萧正真",
            complainPhone: "15290444211",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在办理现金业务时，部分工作人员服务态度冷漠，缺乏耐心和热情，让我感到非常不满",
            dotLinkMan: "贺乐志",
            disposeType: "处理中",
          },
        ],
        [
          {
            complainPerson: "黎英光",
            complainPhone: "15273563397",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在存款时，点钞机多次出现误差，导致我存入的金额与实际不符，需要反复核对和纠正，非常麻烦。",
            dotLinkMan: "曾飞鸿",
            disposeType: "处理中",
          },
        ],
        [
          {
            complainPerson: "阎子安",
            complainPhone: "13750455783",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在贵行取出的现金中，竟然发现了假币，这严重损害了我的利益，也对贵行的信誉造成了影响",
            dotLinkMan: "李四",
            disposeType: "处理中",
          },
          {
            complainPerson: "蒋高明",
            complainPhone: "15264159602",
            complainType: "2",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "办理现金业务时，发现安全措施不够严密，存在安全隐患，如监控摄像头损坏、门禁系统失灵等",
            dotLinkMan: "邱平安",
            disposeType: "处理中",
          },
        ],
        [
          {
            complainPerson: "范曼云",
            complainPhone: "15284300197",
            complainType: "1",
            complainTime: "2024-11-01 10:00",
            complainMsgDesc:
              "在办理现金业务时，发现贵行关于业务办理流程、费用标准等信息更新不及时，导致我多次咨询和确认，浪费了大量时间。",
            dotLinkMan: "沈叶欣",
            disposeType: "处理中",
          },
          {
            complainPerson: "谭丽瑶",
            complainPhone: "15258558981",
            complainType: "1",
            complainTime: "2024-11-02 11:30",
            complainMsgDesc:
              "银行的ATM机经常出现无法识别钞票、吞卡、出钞错误等故障，给我带来了很大的不便和经济损失。",
            dotLinkMan: "宋寻芳",
            disposeType: "已处理",
          },
        ],
      ],
      tableData: [],
    };
  },
  computed: {},
  methods: {
    initData() {
      this.tableData = this.templateData[this.defaultValue.idx];
      this.total = this.tableData.length;
    },
    viewDetail() {},
    handleConfirm() {
      this.handleClose();
    },
    handleClose() {
      this.showDialog = false;
    },
  },
};
</script>
  
  <style>
</style>