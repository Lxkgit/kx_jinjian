import hasRole from "./permission/hasRole";
import hasPermi from "./permission/hasPermi";
import dialogDrag from "./dialog/drag";
import dialogDragWidth from "./dialog/dragWidth";
import dialogDragHeight from "./dialog/dragHeight";
import clipboard from "./module/clipboard";
import clickoutside from "./clickoutside";
import numberColor from "./numberColor";
import numberConver from "./numberConver";
import watermark from './watermark'
import encryption from './encryption'

const install = function (Vue) {
  Vue.directive("hasRole", hasRole);
  Vue.directive("hasPermi", hasPermi);
  Vue.directive("clipboard", clipboard);
  Vue.directive("dialogDrag", dialogDrag);
  Vue.directive("dialogDragWidth", dialogDragWidth);
  Vue.directive("dialogDragHeight", dialogDragHeight);
  Vue.directive("clickoutside", clickoutside);
  Vue.directive("number-color", numberColor);
  Vue.directive("number-conver", numberConver);
  Vue.directive("watermark", watermark);
  Vue.directive("encryption", encryption);
};

if (window.Vue) {
  window["hasRole"] = hasRole;
  window["hasPermi"] = hasPermi;
  Vue.use(install); // eslint-disable-line
}

export default install;
