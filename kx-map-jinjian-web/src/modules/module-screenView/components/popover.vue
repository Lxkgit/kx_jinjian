<template>
  <titleBox class="wrapper-top" height="100%">
    <littleBox title="系统简介" class="littleBox"></littleBox>
    <div class="Introduction">
      为深入贯彻执行中国人民银行总行及省分行关于货币金银业务流程优化的工作部署，根据《银行机构现金服务网格管理工作指引（试行）》（银货金〔2022〕71号）和《浙江省银行机构现金服务网格化管理办法》（浙银货币〔2024〕19号）文件要求，中国人民银行金华市分行积极响应，通过深入的现场调研与分析，金华市分行决定着手建立现金服务网格化管理平台。目前，平台一期工程以“现金服务网格地图”为核心，搭建了网格信息管理、综合评价和舆情监控等现金流通管理的基础框架，旨在实现网格动态管理、舆情快速响应和现金服务综合评价等功能，推动现金服务管理水平迈上新台阶。
    </div>
    <littleBox title="系统功能" class="littleBox"></littleBox>
    <div class="features">
      <div v-for="item in list" :key="item">
        {{ item }}
      </div>
    </div>
  </titleBox>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import littleBox from "@/modules/module-screenView/viewComponents/components/littleBox";

export default {
  components: {
    titleBox,
    littleBox,
  },
  data() {
    return {
      list: [
        "可视化网点地图",
        "网点数据管理",
        "商户摸排登记台账",
        "网格框架管理",
        "网点日常管理",
        "反假业务检查台账",
        "网格信息管理",
        "网点分类管理",
        "全额清分台账",
        "网格责任网点",
        "现金服务点",
        "纪念币巡查台账",
      ],
    };
  },
};
</script>

<style scoped lang="scss">
.wrapper-top {
  .littleBox {
    margin: 10px 0;
  }
  .Introduction {
    text-indent: 2em;
    line-height: 20px;
  }
  .features {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    div {
      margin: 5px 5px 5px 0;
      background: rgba(37, 41, 44, 0.9);
      text-align: center;
      border-radius: 5px;
      padding: 8px 0;
      color: #a4bfc6;
      border: 1px solid #466066;
    }
  }
}
</style>