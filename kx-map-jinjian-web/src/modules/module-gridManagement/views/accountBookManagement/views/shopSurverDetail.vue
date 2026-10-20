<!-- 商户摸牌详情 -->
 <template>
    <div class="shop-survey-detail">
      <orgTitle title="商户摸排详情" :imgIcon="imgIcon" goBack></orgTitle>
      <div class="content">
         <tableTitle title="商户详情"></tableTitle>
         <div class="base-iform">
            <i-form ref="baseForm" :columns="baseColumns" label-width="100px" labelPosition="right"
                :gridColumns="3">
            </i-form>
         </div>
         <tableTitle title="商户摸排旅程"></tableTitle>
         <div class="time-date">
            <div class="date-item" v-for="(item,index) in timeBtn" :key="index" @click="changeDay(item)" :class="activeDay == item.name ? 'active' : ''">
               {{ item.name }}
            </div>
            <el-date-picker
               class="date-picker"
               v-model="date"
               type="daterange"
               start-placeholder="开始日期"
               value-format="yyyy-MM-dd"
               @change="handleChange"
               end-placeholder="结束日期">
            </el-date-picker>
         </div>
         <div class="block">
            <el-timeline>
               <el-timeline-item
                  v-for="(shop, index) in shopList"
                  :key="index"
                  icon="el-icon-time"
                  placement="top"      
                  size="large"
                  :color="shop.color"
                  :timestamp="shop.timestamp">
                  <div class="block-box">
                     <div class="block-box-title">
                        <div class="title">{{ shop.shop }}</div>
                        <svg-icon :icon-class="shop.showDetail ? 'up' : 'down'" class="icon" @click="showDetail(shop,index)"></svg-icon>
                     </div>
                     <div class="block-box-visitor">
                        <span>走访人：{{shop.name}}</span>
                        <span class="org">走访机构：{{ shop.org }}</span>
                     </div>
                     <div class="block-box-icon">
                        <div class="block-icon-item">
                           <svg-icon icon-class="green-shop"></svg-icon>
                           <span class="icon-text">经营主体支持现金收付</span>
                        </div>
                        <div class="block-icon-item">
                           <svg-icon icon-class="red-shop"></svg-icon>
                           <span class="icon-text">经营主体曾发生无法找零情况</span>
                        </div>
                     </div>
                     <div class="block-box-detail" v-if="shop.showDetail">
                        <tableTitle title="基础信息"></tableTitle>
                        <i-form ref="myForm" :columns="columns" labelWidth="150px" labelPosition="right" :gridColumns="2" class="detail-form" />
                        <tableTitle title="商户摸排登记"></tableTitle>
                        <i-form ref="shopForm" :columns="shopColumns" labelWidth="150px" labelPosition="right" :gridColumns="2" class="detail-form"/>
                        <i-form ref="radioForm" :columns="radioColumns" labelWidth="150px" labelPosition="right" />
                        <i-form ref="remarkForm" :columns="remarkColumns" labelWidth="100px" labelPosition="right" :gridColumns="2"/>
                     </div>
                  </div>
               </el-timeline-item>
            </el-timeline>
         </div>
      </div>
    </div>
 </template>
 
 <script>
  import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
  import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
  const radioOptions = [
    { label: "是", value: 1 },
    { label: "否", value: 0 },
  ]
 export default {
   components:{
        orgTitle,tableTitle
    },
    data() {
      return {
         imgIcon:require('@/modules/module-gridManagement/assets/images/shop.png'),
         activeDay:'今天',
         date:'',
         baseColumns:[
            { label: "商户名称", key: "1", value: "", type: "text", disabled: true, },
            { label: "商户地址", key: "2", value: "", type: "text", disabled: true, },
            { label: "商户所属行业", key: "3", value: "", type: "text", disabled: true, },
            { label: "网格联系人 ", key: "4", value: "", type: "text", disabled: true, },
            { label: "商户联系人", key: "5", value: "", type: "text", disabled: true, },
            { label: "商户联系方式", key: "6", value: "", type: "text", disabled: true, },
         ],
         timeBtn:[
            {name:'今天',query:'now'},
            {name:'昨天',query:'yesday'},
            {name:'最近七天',query:'svenday'},
         ],
         columns:[
            {label: "商户名称", key: "12", value: "",type: "text",disabled:true},
            {label: "商户地址", key: "22", value: "",type: "text"},
            {label: "所属行业", key: "223", value: "",type: "text"},
            {label: "网格联系人", key: "42", value: "",type: "text"},
            {label: "商户联系人", key: "52", value: "",type: "text"},
            {label: "商户联系方式", key: "62", value: "",type: "text"},
         ],
         shopColumns:[
            {label: "零钱包兑换个数", key: "12", value: "",type: "text",disabled:true},
            {label: "零钱包兑换金额", key: "212", value: "",type: "text"},
         ],
         radioColumns:[
            {label: "经营主体是否支持现金收付", key: "125", value: "",type: "radio",options:radioOptions,labelWidth:"300px",disabled:true},
            {label: "经营主体是否张贴“支持现金支付”标志", key: "12213", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体零钞备付是否充足(超过300元)", key: "12132", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体备付人民币现金是否整洁", key: "12412", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体是否了解拒收人民币现金是违法行为", key: "13522", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体是否了解兑换小面额人民币现金方式", key: "12312", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体是否曾发生无法找零情况", key: "13242", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
            {label: "经营主体是否需要小面额现金上门兑换服务", key: "11232", value: "",type: "radio",options:radioOptions,labelWidth:"300px"},
         ],
         remarkColumns:[
            {label: "备注", key: "1212", value: "",type: "textarea"},
         ],
         shopList: [
            { timestamp: '2018-04-12 20:46',color:'#4B74FF',name:'小明',shop:'小明超市',org:'建设银行金华支行',showDetail:false,}, 
            { timestamp: '2018-04-12 20:46',color:'',name:'小明',shop:'小明超市',org:'建设银行金华支行',showDetail:false,}, 
            { timestamp: '2018-04-12 20:46',color:'',name:'小明',shop:'小明超市',org:'建设银行金华支行',showDetail:false,}, 
            { timestamp: '2018-04-12 20:46',color:'',name:'小明',shop:'小明超市',org:'建设银行金华支行',showDetail:false,}, 
            { timestamp: '2018-04-12 20:46',color:'',name:'小明',shop:'小明超市',org:'建设银行金华支行',showDetail:false,}, 
         ]
      }
    },
    methods:{
      changeDay(item) {
         this.activeDay = item.name;
      },
      handleChange(val) {
         console.log('选择了时间',val);
         if(val) {
            this.activeDay = ''
         }
      },
      //商户详情
      showDetail(item,index) {
         this.$set(this.shopList[index],'showDetail',!item.showDetail)

      },
    }
 }
 </script>
 
 <style lang="scss" scoped>

 ::v-deep .el-timeline-item:first-child{
   .el-timeline-item__timestamp{
      color:#4B74FF;
   }
 }
 .shop-survey-detail {
   height: 100%;
 }
 .content {
   margin-top: 12px;
    min-height: calc(100% - 68px);
    padding: 16px;
    background: #fff;
 }
 .base-iform {
   margin-top: 12px;
 }
 .time-date {
   margin-top: 12px;
   display: flex;
   align-items: center;
   .date-item {
      width: 80px;
      height: 36px;
      text-align: center;
      line-height: 34px;
      border-radius: 4px;
      border: 1px solid rgba(94, 129, 244, 0.1);
      background: rgba(94, 129, 244, 0.15);
      color: #3D3D3D;
      box-shadow: inset 0px 1px 4px 0px rgba(12, 22, 72, 0.1);
      cursor: pointer;
   }
   .date-item:not(:first-child) {
      margin-left: 12px;
   }
   .active {
      background: linear-gradient(180deg, #92ABFF 0%, #5E81F4 100%);
      border: 1px solid rgba(255, 255, 255, 0.4);
      color: #FFFFFF;
   }
   .date-picker {
      margin-left: 12px;
      width: 260px;
   }
 }
 .block {
   margin-top: 32px;
   .block-box {
      width: 1080px;
      min-height: 104px;
      border-radius: 4px;
      background: #F7F8FC;
      padding: 12px;
      &-title {
         display: flex;
         justify-content: space-between;  
         align-items: center;
         .title {
            color: #323548;
            font-size: 14px;
         }
      }
      &-visitor {
         margin-top: 8px;
         font-size: 14px;
         color: #323548;
         .org {
            margin-left: 40px;
         }
      }
      &-icon {
         margin-top:12px;
         display: flex;
         align-items: center;
         color: #323548;
         font-size: 14px;
         .block-icon-item:not(:first-child) {
            margin-left: 60px;
         }
         .icon-text {
            margin-left: 8px;
         }
      }
      .icon {
         width: 20px;
         height: 20px;
         cursor: pointer;
      }
   }
   
 }
 .block-box-detail {
   margin-top: 12px;
   width: 100%;
   padding: 16px;
   background: #FFFFFF;
   border-radius: 4px;
   .detail-form {
      margin-top: 16px;
   }
 }
 </style>