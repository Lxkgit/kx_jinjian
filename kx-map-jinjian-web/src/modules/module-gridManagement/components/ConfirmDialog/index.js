import Vue from "vue";
import confirmDialog from "./index.vue";

let confirmConstructor = Vue.extend(confirmDialog);
let myConfirm = function (options) {
  let confirmDom = new confirmConstructor({
    el: document.createElement("div"),
  });
  document.body.appendChild(confirmDom.$el);
  let { type, message, title, confirmCallBack, cancelCallBack, noTitle, confirmBtnText, showConfirmBtn = true, showCancelBtn = true } = options;
  type = type||'info'
  
  if(!title){
    switch (type) {
      case 'info':
        title = '提示'
        break;
      case 'danger':
        title = '确定要删除吗？'
        break;
      case 'success':
        title = '成功'
        break;
    }
  }

  confirmDom.title = noTitle ? '' : title;
  confirmDom.message = message;
  confirmDom.type = type;
  confirmDom.showConfirmBtn = showConfirmBtn;
  confirmDom.showCancelBtn = showCancelBtn;
  confirmDom.confirmBtnText = confirmBtnText||'确定';

  confirmDom.showDialog = true;

  confirmDom.confirm = async () => {
    const back = confirmCallBack();
    if (back && Object.prototype.toString.call(back) == "[object Promise]") {
      confirmDom.confirmBtn = true;
      try {
        await back;
      } catch (error) {}
      confirmDom.confirmBtn = false;
    }
    confirmDom.showDialog = false;
  };

  confirmDom.cancel = function () {
    cancelCallBack?.();
    confirmDom.showDialog = false;
  };
};

export default myConfirm;
