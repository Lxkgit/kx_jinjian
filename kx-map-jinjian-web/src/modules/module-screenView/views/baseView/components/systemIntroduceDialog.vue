<template>
  <div class="view-dialog-wrapper" v-if="value">
    <div class="dialog-content-wrapper">
      <div class="title">
        <div class="close" @click="$emit('close')">
          <span class="el-icon-close content-close"></span>
          <span style="margin-left: 5px">关闭</span>
        </div>
      </div>
      <div class="content">
        <div class="field-title">系统简介</div>
        <div class="introduce-text">{{ msg }}</div>
        <div class="field-title">一期系统功能</div>
        <div class="function" v-for="item in list" :key="item.title">
          <div class="function-title">{{ item.title }}</div>
          <div class="function-box">
            <div
              class="function-box-item"
              v-for="i in item.children"
              :key="i.label"
              :style="{ gridColumnStart: `span ${i.span}` }"
            >
              <div class="function-box-item-label">{{ i.label }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin";
export default {
  // mixins: [dialogMixin],
  props: {
    value: {
      type: Boolean,
    },
  },
  data() {
    return {
      msg: "为深入贯彻执行中国人民银行总行及省分行关于货币金银业务流程优化的工作部署，根据《中国人民银行货币金银局关于进一步强化现金服务工作的通知》（银货金〔2022〕71号）文件中<银行机构现金服务网格管理工作指引>、《中国人民银行办公厅关于持续做好优化现金使用环境工作的通知》(银办发〔2024〕185号 )和《浙江省银行机构现金服务网格化管理办法》（浙银货币〔2024〕19号）文件要求，中国人民银行金华市分行积极响应，通过深入的现场调研与分析，金华市分行决定着手建立现金服务网格化管理平台。目前，平台一期工程以“现金服务网格地图”为核心，搭建了网格信息管理、舆情监控和综合评价等现金流通管理的基础功能，构建了城乡网格一张图，为每个网格创建一个数字档案，推动实现小网格大服务，形成“营业网点广参与、一张网络全覆盖”的现金管理和服务体系。",
      list: [
        {
          title: "网格管理",
          children: [
            { label: "可视化网格地图" },
            { label: "网格数据列表" },
            { label: "网格电子边界" },
            { label: "网格责任网点" },
            { label: "银行网点列表" },
            { label: "现金服务点" },
            { label: "农村金融服务站" },
            { label: "兑换鉴定网点" },
          ],
        },
        {
          title: "舆情监控",
          children: [{ label: "网格事件舆情监控" }, { label: "网点事件舆情监控" }],
        },
        {
          title: "综合评价",
          children: [
            { label: "调查问卷" },
            { label: "商户摸排检查台账" },
            { label: "银行网点现金服务巡查"},
            { label: "商户现金收付情况巡查"},
            { label: "反假业务检查台账" },
            { label: "全额清分台账" },
            { label: "现金抽查环境评价" },
            { label: "网格管理年度评价" },
          ],
        },
        // {
        //   title: "系统维护",
        //   children: [
        //     { label: "用户管理" },
        //     { label: "角色管理" },
        //     { label: "菜单管理" },
        //   ],
        // },
      ],
    };
  },
  methods: {
    gridStyle(span) {
      return `span ${span}`;
    },
  },
};
</script>

<style lang="scss" scoped>
.view-dialog-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  // background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
  .dialog-content-wrapper {
    // border: 1px solid red;
    width: 40%;
    height: 70%;
    padding: 20px;
    border: 1px solid #00f6ff;
    border-radius: 10px;
    background-color: rgba(6, 14, 24, 0.75);
    // background: url(../assets/image/box.png) no-repeat;
    background-size: 100% 100%;
    pointer-events: auto;
    .title {
      // border: 1px solid red;
      display: flex;
      justify-content: flex-end;
      .close {
        width: fit-content;
        color: #a8e3f2;
        font-size: 16px;
        cursor: pointer;
      }
    }
    .content {
      font-family: "Noto Sans SC" !important;
      .field-title {
        // margin-left: 24px;
        padding-left: 20px;
        color: #fff;
        font-size: 18px;
        margin: 10px 0;
        position: relative;
        &::before {
          content: "";
          position: absolute;
          width: 13px;
          height: 13px;
          border-radius: 50%;
          top: 50%;
          left: 0;
          transform: translateY(-50%);
          background-color: #00f6ff;
        }
      }
      .introduce-text {
        line-height: 24px;
        margin-bottom: 30px;
        color: #fff;
        font-size: 18px;
      }
      .function {
        // border: 1px solid #fff;
        font-size: 16px;
        color: #fff;
        margin-bottom: 20px;
        &-title {
        }
        &-box {
          margin: 15px 0;
          display: grid;
          grid-template-columns: repeat(4, 1fr);
          gap: 10px;
          &-item {
            text-align: center;
            border-radius: 8px;
            background: linear-gradient(to bottom, #3b8f96, #19a8bd);
            opacity: 0.9;
            &-label {
              color: #a8e3f2;
              padding: 15px 0;
              margin: 1px;
              border-radius: 8px;
              background: linear-gradient(to top, #31474f, #1a242b);
            }
          }
        }
      }
    }
  }
}
</style>
