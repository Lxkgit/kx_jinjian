<!-- 新增任务页 -->
 <template>
   <div class="add-task">
        <orgTitle title="新增任务" :imgIcon="imgIcon" goBack></orgTitle>
        <div class="task-main">
            <!-- 创建任务基本要素 -->
            <template>
                <div class="task-main-title">
                <div class="task-main-title-item">
                    <div class="left">
                        <div class="left-img"></div>
                        <div class="text">任务基本要素</div>
                    </div>
                    <div class="center" :class="step == 1 ? 'center-none' : 'center-active' "></div>
                    <div class="right">
                        <div class="right-img" :class="step == 1 ? 'right-none' : 'right-active' "></div>
                        <div class="text">任务表单</div>
                    </div>
                </div>
                </div>
                <!-- 第一步创建基本要素 -->
                <template v-if="step == 1">
                    <tableTitle title="任务基础信息" style="margin-top:8px"></tableTitle>
                    <i-form
                        style="margin-top:8px"
                        ref="taskForm"
                        :columns="taskForm"
                        label-width="150px"
                        labelPosition="right"
                        :gridColumns="3">
                    </i-form>
                    <tableTitle title="任务基础信息"></tableTitle>
                    <div class="select-box">
                        <div class="select-top">
                            <div class="select-left">
                                <div class="box" :class="active == item.name ? 'active' : ''" v-for="(item,index) in boxList" :key="index" @click="active = item.name">{{ item.name }}</div>
                            </div>
                            <div class="select-right">
                                <el-select v-model="selectValue" placeholder="请选择">
                                    <el-option  v-for="item in optionList" :key="item.value"></el-option>
                                </el-select>
                            </div>
                        </div>
                        <div class="select-content">
                            <div class="no-content">选择后显示</div>
                        </div>
                    </div>
                </template>
                <!-- 第二部创建表单 -->
                <template v-else-if="step == 2">
                    <table-title title="任务表单" style="margin-top:8px"></table-title>
                    <dynamicFieldConfig style="margin-top:8px" :fields="fields"></dynamicFieldConfig>
                    <div class="config-button">
                        <el-button type="primary" @click="showDialog = true">引用模板</el-button>
                    </div>
                </template>
                <div class="task-bottom">
                    <el-button type="primary" @click="nextStep" v-if="step == 1">下一步</el-button>
                    <el-button type="primary" @click="nextStep" v-if="step == 2">发布</el-button>
                    <el-button  @click="preStep" plain v-if="step == 2">上一步</el-button>
                    <el-button>取消</el-button>
                </div>
            </template>
        </div>
        <templateDialog v-model="showDialog"></templateDialog>
   </div>
 </template>
 
 <script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import dynamicFieldConfig from "./dynamicFieldConfig.vue";
import templateDialog from './templateDialog.vue';
 export default {
    components:{
        orgTitle,tableTitle,dynamicFieldConfig,templateDialog
    },
    data() {
        return {
            step:'1',
            selectValue:'',
            active:'人民银行',
            showDialog:false,
            boxList:[{name:'人民银行'},{name:'银行机构'},{name:'网格'}],
            imgIcon:require('@/modules/module-gridManagement/assets/images/task.png'),
            taskForm:[
                {label: "任务名称",key: "1",value: "",type: "text",rules: [{ required: true, message: '请输入任务名称', trigger: ['blur', 'change'] }],},
                {label: "任务类型",key: "2",value: "",type: "text",rules: [{ required: true, message: '请输入任务类型', trigger: ['blur', 'change'] }],},
                {label: "任务优先级",key: "3",value: "",type: "text",rules: [{ required: true, message: '请输入任务优先级', trigger: ['blur', 'change'] }],},
                {label: "任务频次",key: "4",value: "",type: "select",rules: [{ required: true, message: '请输入任务频次', trigger: ['blur', 'change'] }],},
                {label: "任务起止时间",key: "5",value: "",type: "text"},
                {label: "执行周期",key: "6",value: "",type: "daterange"},
                {label: "备注",key: "7",value: "",type: "textarea",span:3},
            ],
            optionList:[],
            fields:[{},{},{},{}]
        }
    },
    methods: {
        //下一步
        nextStep(){
            this.step = '2'
        },
        //上一步
        preStep() {
            this.step = '1'
        }
    },
 }
 </script>
 
 <style lang="scss" scoped>
 .add-task {
    width: 100%;
    height:100%;
    .task-main {
        width: 100%;
        margin-top:12px;
        min-height:calc(100% - 64px);
        background-color:#fff;
        padding: 16px;
        position:relative;
        &-title {
            width: 100%;
            height: 92px;
            display:flex;
            justify-content: center;
            align-items: center;
            &-item {
                display: flex;
                justify-content: center;
                align-items: center;
                .left {
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    .left-img {
                        width: 60px;
                        height: 60px;
                        background: url('~@/modules/module-gridManagement/assets/images/task-1-active.png') no-repeat;
                        background-size: 100% 100%;
                    }
                }
                .center {
                    margin: 0 10px 20px 10px;
                    width: 540px;
                    height: 20px;
                }
                .center-none {
                    background: url('~@/modules/module-gridManagement/assets/images/task-line.png') no-repeat;
                    background-size: 100% 100%;
                }
                .center-active {
                    background: url('~@/modules/module-gridManagement/assets/images/task-line-active.png') no-repeat;
                    background-size: 100% 100%;
                }
                .right {
                    display: flex;
                    flex-direction: column;
                    justify-content: center;
                    align-items: center;
                    .right-img {
                        width: 60px;
                        height: 60px;
                        
                    }
                    .right-none {
                        background: url('~@/modules/module-gridManagement/assets/images/task-2.png') no-repeat;
                        background-size: 100% 100%;
                    }
                    .right-active {
                        background: url('~@/modules/module-gridManagement/assets/images/task-2-active.png') no-repeat;
                        background-size: 100% 100%;
                    }
                }
                

                .text {
                    margin-top:8px;
                    font-size: 14px;
                    color: #494D68;
                }
            }
        }
    }
 }
 .select-top {
    display: flex;
    align-items: center;
    margin-top:16px;
    .select-left {
        width: 264px;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px solid #EBEEF5;
        border-radius: 4px;
    .box {
        width: 88px;
        height: 34px;
        line-height:32px;
        text-align: center;
        background: #F6F7FA;
        cursor:pointer;
        color: #323548;
        font-size: 14px;
    }
    .active {
        color: #FFFFFF;
        background: linear-gradient(180deg, #5E81F4 0%, #425AAA 100%);
        border: 1px solid #5E81F4;
    }
    }
    .select-right {
        margin-left: 12px;
    }
 }
 .select-content {
    margin-top:16px;
    margin-bottom:45px;
    .no-content {
        width: 94px;
        height: 36px;
        background: #F6F7FA;
        border-radius: 4px;
        line-height:36px;
        text-align: center;
    }
 }
 .task-bottom {
    position: absolute;
    bottom: 16px;
    right: 45%;
 }
 .config-button {
    margin: auto;
    width: 1068px;
    height: auto;
    margin-top: 16px;
 }
 </style>