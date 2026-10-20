<template>
  <div class="inside-info-wrapper">
    <pop-title class="margin-top-total" text="商户基本信息" fontSize="15px" color="#4ccbcc"></pop-title>
    <div class="info-box">
      <infoList :baseInfo="baseInfoList"></infoList>
      <el-row :gutter="24">
        <el-col :span="8">
          <div class="left-img">
            <img :src="inLineInfo.image" />
          </div>
        </el-col>
        <el-col :span="8">
          <itemEchart color="#5fbfec" v-if="monthList.length > 0" :list="monthList" id="one"
                      title="商户本年月度交易额变化趋势" />
        </el-col>
        <el-col :span="8">
          <itemEchart color="#fac758" v-if="dayList.length > 0" :list="dayList" id="two"
                      title="商户年日均变化趋势" />
        </el-col>
      </el-row>
      <div class="line"></div>
    </div>
    <pop-title class="margin-top-total" text="客户标签" fontSize="15px" color="#4ccbcc"></pop-title>
    <div class="c-lable">
      <span>网格标签：</span>
      <div class="c-lable-r">
        <div class="label-s" v-for="(item,i) in gridTagList" :key="i">
          {{item.labelName}}
        </div>
      </div>

    </div>
    <pop-title class="margin-top-total" text="商户产品信息" fontSize="15px" color="#4ccbcc"></pop-title>
    <!-- 商户产品信息 -->
    <productInfo :code="code" />
    <pop-title class="margin-top-total" v-if="inLineInfo.detailId" text="商户法人产品持有信息" fontSize="15px"
               color="#4ccbcc"></pop-title>
    <div class="money-b" v-if="inLineInfo.detailId">
      <div class="money-i">
        <div class="money-i-one">
          <span class="icon">
            <i class="el-icon-document"></i>
          </span>
          <span class="text">授信金额</span>
        </div>
        <div class="money-i-one">
          <span class="money">
            {{ inLineInfo.grant || 0 }}
          </span>
          <span class="text">元</span>
        </div>
      </div>
      <div class="money-i">
        <div class="money-i-one">
          <span class="icon">
            <i class="el-icon-document"></i>
          </span>
          <span class="text">用信金额</span>
        </div>
        <div class="money-i-one">
          <span class="money">
            {{ inLineInfo.using || 0 }}
          </span>
          <span class="text">元</span>
        </div>
      </div>
    </div>
    <!--  -->
    <prodlistNew v-if="inLineInfo.detailId" :prod="prodNew" />
  </div>
</template>

<script>
import popTitle from "../components/popTitle.vue";
import itemEchart from "../components/itemEchart.vue";
import productInfo from "../components/productInfo.vue";
import prodlistNew from "../components/prodList.vue";

import infoList from '../components/infoList';
import { basicInfoList } from '../config';
import service from '../api';
import mixin from '../mixins/mixin'
export default ({
  components: {
    popTitle,
    itemEchart,
    productInfo,
    prodlistNew,
    infoList
  },

  mixins: [mixin],
  props: {
    customerId: {
      type: [String, Number]
    },
  },
  data() {
    return {
      baseInfoList: JSON.parse(JSON.stringify(basicInfoList)),
    }
  },
  mounted() {
    this.init();
    this.getCustomerBaseInfo()
  },
  methods: {
    getCustomerBaseInfo() {
      const param = {
        id: this.customerId
      }
      service.customerDetail(param).then(res => {
        const { data: { customerInfo } } = res
        for (let item of this.baseInfoList) {
          item.value = customerInfo[item.prop]
        }
      })
    },
  },
})
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
// 二期
.margin-top-total {
  margin: 10px;
}
:fullscreen .margin-top-total {
  margin: 20px 0 30px 10px !important;
}

// -------------分割线
.left-img {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  img {
    width: 160px;
    height: 100px;
    margin-bottom: 20px;
  }
}
.address {
  margin-bottom: 10px;
}
.fs {
  line-height: 30px;
}
.label {
  color: #333;
  font-size: 14px;
}
.content {
  font-size: 14px;
  font-weight: bold;
}
.label-r,
.content-r {
  font-size: 16px;
}
.info-box {
  padding: 0 20px;

  .line {
    width: 100%;
    height: 2px;
    background: #030d10;
    opacity: 0.05;
    border-radius: 1px;
  }
}
.c-lable {
  display: flex;
  align-items: center;
  padding-left: 20px;
  .c-lable-r {
    display: flex;
    flex-wrap: wrap;
    margin-left: 15px;
    width: 80%;
    > div {
      padding: 6px 15px;
      background-color: #92bef4;
      color: #fff;
      border-radius: 40px;
      margin-right: 10px;
      font-size: 14px;
    }
    .label-s {
      margin-bottom: 10px;
      background-color: #92bef4;
    }
    .label-q {
      color: #fff;
      background-color: #989896;
    }
    .label-c {
      background-color: #24d79e;
    }
  }
}

.address {
  width: 100% !important;
  .item:nth-child(1) {
    width: 20%;
  }
  .item:nth-child(2) {
    width: 78%;
  }
}
.money-b {
  display: flex;
  justify-content: space-around;
  margin-bottom: 10px;
  .money-i {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 48%;
    padding: 10px 20px;
    border-radius: 10px;
    .money-i-one {
      display: flex;
      align-items: center;
      .icon {
        color: #fff;
        padding: 4px;
        border-radius: 4px;
        background-color: #409eff;
        margin: 0 20px 0 20px;
      }
      .text {
        color: #999;
        font-size: 14px;
        margin-right: 10px;
      }
      .money {
        color: #1677dc;
        font-weight: bold;
        font-size: 18px;
        margin-right: 20px;
      }
    }
  }
  .money-i:nth-child(1) {
    background-color: #ecf5ff;
  }
  .money-i:nth-child(2) {
    background-color: #fff2e9;
    .money-i-one {
      .icon {
        background-color: #ff8429;
      }
      .money {
        color: #ff8429;
      }
    }
  }
}
</style>