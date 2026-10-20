// components文件夹下  全局组件注册
import Pagination from "./Pagination"; // 分页组件
import RightToolbar from "./RightToolbar"; // 自定义表格工具组件
import Editor from "./Editor"; // 富文本组件
import FileUpload from "./FileUpload"; // 文件上传组件
import ImageUpload from "./ImageUpload"; // 图片上传组件
import ImagePreview from "./ImagePreview"; // 图片预览组件
import DictTag from "./DictTag"; // 字典标签组件
import IButtonBar from "./IButtonBar";
import IDialog from "./IDialog";
import IFilterBar from "./IFilterBar";
import ITable from "./ITable";
import IForm from "./IForm";
import PageContent from "./PageContent";
import SplitPageContent from "./SplitPageContent";
import IconFont from "./IconFont";
import ITableBtns from "./ITableBtns";
import ICalendar from "./ICalendar";
import FileDetail from "./FileDetail";
import CustomerTrip from "./CustomerTrip";
import VisitInfo from "./VisitInfo";
import CustomerSource from "./CustomerSource";
import IDescriptions from "./IDescriptions";
import SubTitle from "./SubTitle";
import IndexView from "./IndexView"; 
import NetworkDetail from "./NetworkDetail";
// 字典数据组件
import DictData from "./DictData";

const component = {
  install: function (Vue) {
    // 全局组件挂载
    Vue.component("DictTag", DictTag);
    Vue.component("Pagination", Pagination);
    Vue.component("RightToolbar", RightToolbar);
    Vue.component("Editor", Editor);
    Vue.component("FileUpload", FileUpload);
    Vue.component("ImageUpload", ImageUpload);
    Vue.component("ImagePreview", ImagePreview);
    Vue.component("IButtonBar", IButtonBar);
    Vue.component("IDialog", IDialog);
    Vue.component("IFilterBar", IFilterBar);
    Vue.component("ITable", ITable);
    Vue.component("IForm", IForm);
    Vue.component("PageContent", PageContent);
    Vue.component("IconFont", IconFont);
    Vue.component("ITableBtns", ITableBtns);
    Vue.component("SplitPageContent", SplitPageContent);
    Vue.component("ICalendar", ICalendar);
    Vue.component("FileDetail", FileDetail);
    Vue.component("CustomerTrip", CustomerTrip);
    Vue.component("VisitInfo", VisitInfo);
    Vue.component("CustomerSource", CustomerSource);
    Vue.component("IDescriptions", IDescriptions);
    Vue.component("SubTitle", SubTitle);
    Vue.component("IndexView", IndexView); 
    Vue.component("NetworkDetail", NetworkDetail); 
    DictData.install();
  },
};

export default component;
