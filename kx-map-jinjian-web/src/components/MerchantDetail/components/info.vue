<template>
  <div>
    <div class="info-top">
     <div class="top-ul">
       <ul>
        <li v-for="(item,i) in infoList" :key="i">
          <span class="name">{{item.name}}：</span>
          <span class="content">{{baseInfo[item.prop]}}</span>
        </li>
        <li v-if="baseInfo.otherBankProduct=='是'">
          <span class="name">他行产品：</span>
          <span class="content">{{baseInfo.other}}</span>
        </li>
        <li>
          <span class="name">备注：</span>
          <span class="content">{{baseInfo.productRemark}}</span>
        </li>
      </ul>
     </div>
     <div class="label-box">
       <div class="cus-label">客户标签：</div>
       <div class="label-item">
         <div class="item" v-for="(item,i) in labelListA" :key="i">
           <span>{{labelNames[i]}}</span>
           <ul>
             <li  v-for="(v,j) in item" :key="j">
               <div  v-for="(item,i) in labelList" :key="i">
                 <span class="li" v-if="item===v.labelCode">{{v.labelName}}</span>
               </div>
               
             </li>
           </ul>
         </div>
       </div>
      </div>
      <div>
        <div style="padding: 0 0 10px 0;">走访照片:</div>
        <div>
          <el-image  v-for="(item,i) in baseInfo.picFiles" :key="i"
            style="width: 100px; height: 100px;marginRight: 20px;borderRadiu:4px;"
            :src="item" 
            :preview-src-list="baseInfo.picFiles"
            >
          </el-image>  
        </div>
      </div>
    </div>
  </div>
</template>
 
<script>
// import {
//   getInstitutionLabel, // 商户标签
// } from "@/api/meshMap/meshIndex.js";
import storeAllLabel from '../mock/storeAllLabel.json'
export default {
  name: "info",
  props:{
    baseInfo:{
      type:Object,
      default:()=>{}
    },
    labelNames: {
      type: Array,
      default: () => ["经营情况", "店面情况", "线上情况"],
    },
  },
  components: {
    
  },
  data() {
    return {
      types:this.type,
      url: 'static/img/assetIcon/woman.png',
      srcList: ['static/img/assetIcon/1.png','static/img/assetIcon/woman.png'],
      labelListA:[]
    };
  },
  watch: {},
  computed: {
    infoList() {
      const list = [
        {
          prop: "ownBankProduct",
          name: "本行收单产品是否正常使用"
        },
        {
          prop: "normalOperate",
          name: "是否正常营业"
        },
        {
          prop: "licenseValid",
          name: "营业执照是否有效"
        },
        {
          prop: "addressType",
          name: "经营场所类型"
        },
        {
          prop: "creditDemand",
          name: "有无信贷需求"
        },
        {
          prop: "intentionalProduct",
          name: "意向产品"
        },
        {
          prop: "otherBankProduct",
          name: "是否有他行收单产品"
        },
      ];
      return list;
    },
    labelList() { 
      let list= this.baseInfo.labelList && this.baseInfo.labelList.map(item=>item.labelCode)
      return list
    }
  },
  mounted() { 
    this.getInstitutionLabel();
  },
  methods: {
    async getInstitutionLabel() { 
      // const { data } = await getInstitutionLabel();
      this.labelListA = storeAllLabel;
    },
  }
};
</script>
 
<style lang="scss" scoped>

li {
  list-style: none;
}

.info-top {
  padding: 0 0 10px 40px;
  .top-ul {
    ul {
      display: flex;
      flex-wrap: wrap;
      li {
        display: flex;
        align-items: center;
        width: 33.33%;
        margin-bottom: 10px;
        font-size: 14px;
        .name {
          color: #333;
        }
        .content {
          font-weight: bold;
        }
        .inline{
          display: inline-block;
          color: #00b5fd;
          font-size: 12px;
          border: 1px solid #00b5fd;
          border-radius: 4px;
          padding: 4px 10px;
          margin-left: 6px;
          background-color: rgba(218, 234, 244, 0.37);
        }
      }
    }
  }
  .label-box {
    display: flex;
    align-items: flex-start;
    margin: 16px 0;
    .cus-label{
      line-height: 28px;
    }
    .label-item {
      flex: 1;
      .item {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
        span {
          color: #999;
        }
        ul {
          flex: 1;
          display: flex;
          margin-left: 10px;
          .li {
            display: inline-block;
            color: #00b5fd;
            border: 1px solid #00b5fd;
            border-radius: 4px;
            padding: 4px 14px;
            background-color: rgba(218, 234, 244, 0.37);
            margin-right: 10px;
          }
        }
      }
    }
  }
}
.other{
  margin-top: 10px;
}
</style>