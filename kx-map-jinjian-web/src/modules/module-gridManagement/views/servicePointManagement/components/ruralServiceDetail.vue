<!-- 农村金融服务站详情页面 -->
 <template>
    <div class="rural-service-detail">
        <orgTitle title="" goBack></orgTitle>
        <div class="rural-content">
            <tableTitle title="基础信息">
                <template #right>
                    <div v-if="showEdit">
                        <el-button type="primary" @click="edit">编辑</el-button>
                    </div>
                    <div v-else>
                        <el-button type="primary" @click="saveEdit">保存</el-button>
                        <el-button @click="showEdit = true">取消</el-button>
                    </div>
                </template>
            </tableTitle>
            <div class="base-iform">
                <i-form
                    ref="baseForm"
                    :columns="baseColumns"
                    label-width="130px"
                    labelPosition="right"
                    :gridColumns="3">
                </i-form>
            </div>

            <tableTitle title="现金服务信息"></tableTitle>
            <div class="base-iform">
                <i-form
                    ref="moneyForm"
                    :columns="moneyForm"
                    label-width="130px"
                    labelPosition="right"
                    :gridColumns="3">
                </i-form>
            </div>
        </div>
    </div>
 </template>
 
 <script>
  import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
  import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
 export default {
    components:{
        orgTitle,tableTitle
    },
    data() {
        return {
            showEdit:true,
            baseColumns:[
                {label: "服务站名称",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "具体地址",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "所在网格",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "站长姓名",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "联系电话",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
            ],
            moneyForm:[
                {label: "小面额功能机具数",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "人工现金柜台总数",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "存款取一体机CRS",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "硬币兑换机数量",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "零钱包兑换数量",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "零钱包兑换金额",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "硬币兑换枚数",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
                {label: "硬币兑换金额",key: "gridCode1",value: "",type: "text",disabled: () => this.showEdit,},
            ]
        }
    },
    methods:{
        edit() {
            this.showEdit = false;
        },
        saveEdit() {

        },
  },
 }
 </script>
 
 <style lang="scss" scoped>
 .rural-service-detail {
    height: 100%;
 }
 .rural-content {
    width: 100%;
    height: calc(100% - 68px);
    padding: 16px;
    background: #fff;
    margin-top: 12px;
 }
 .base-iform {
    margin-top: 12px;
 }
 </style>