import Vue from "vue";
import VueCompositionAPI from "@vue/composition-api";
import App from "./App";
import store from "./store";
import router from "./router";
import directive from "./directive"; // directive
import plugins from "./plugins"; // plugins
import components from "./components";
import ruoyiUtil from "./utils/ruoyi";
import Moment from "moment";

import Cookies from "js-cookie";
import Element from "element-ui";
// import VueMeta from "vue-meta"; // 头部标签组件

import "./assets/styles/element-variables.scss";
import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/ruoyi.scss"; // ruoyi css
import "@/assets/iconfont/iconfont.css";
import "@/assets/iconfont/iconfont.js"; //加入从 iconfont  引入的 icon svg

import "./assets/icons"; // icon
import "./permission"; // permission control

import { download } from "@/utils/request";
import {identityMasking,phoneMasking} from './utils'
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import autoScale from "@/directive/auto-scale";
import Tinymce from "@/components/tinymce/index.vue";

Vue.use(VueCompositionAPI);
// 引入 echarts 核心模块，核心模块提供了 echarts 使用必须要的接口。
import * as echarts from "echarts/core";
// 引入柱状图图表，图表后缀都为 Chart
import { PieChart } from "echarts/charts";
// 引入提示框，标题，直角坐标系，数据集，内置数据转换器组件，组件后缀都为 Component
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  LegendComponent
} from "echarts/components";
// 标签自动布局、全局过渡动画等特性
import { LabelLayout, UniversalTransition } from "echarts/features";
// 引入 Canvas 渲染器，注意引入 CanvasRenderer 或者 SVGRenderer 是必须的一步
import { CanvasRenderer } from "echarts/renderers";
import confirmDialog from '@/modules/module-gridManagement/components/ConfirmDialog/index.js'

// 注册必须的组件
echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  DatasetComponent,
  TransformComponent,
  LegendComponent,
  PieChart,
  LabelLayout,
  UniversalTransition,
  CanvasRenderer,
]);
//注册组件
Vue.prototype.$echarts = echarts;
// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.download = download;
Vue.prototype.$identityMasking = identityMasking
Vue.prototype.$phoneMasking = phoneMasking
Vue.component("tinymce", Tinymce);
Vue.prototype.moment = Moment;
Vue.prototype.$confirmDialog = confirmDialog

Vue.use(directive);
Vue.use(ruoyiUtil);
Vue.use(plugins);
Vue.use(components);
// Vue.use(VueMeta);
Vue.use(autoScale);

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium", // set element-ui default size
});
//事件总线
Vue.prototype.$EventBus = new Vue();
Vue.config.productionTip = false;


new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
