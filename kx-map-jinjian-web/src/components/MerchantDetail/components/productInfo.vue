<template>
  <div>
    <div class="product-box" v-if="toggle">
      <div class="nav-box">
        <div class="nav-item" v-for="(item,index) in list1" :key="index" @click="navClick(index)">
          <span :class="current===index?'active':''">{{item}}</span>
          <span class="line">|</span>
        </div>
      </div>
      <el-table :data="tableData" stripe style="width: 100%" :header-cell-style="tableHeaderStyle">
        <el-table-column align="center" prop="institutionCode" label="客户编号" width="180">
        </el-table-column>
        <el-table-column align="center" prop="simpleName" label="商户简称" width="180">
        </el-table-column>
        <el-table-column align="center" prop="name" label="商户名称">
        </el-table-column>
        <el-table-column align="center" label="操作">
          <template slot-scope="scope">
            <div style="color: #5dadef;cursor: pointer;" @click="queryhandle(scope.row.institutionCode)">查看</div>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[10, 20, 50, 100]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <div class="info-box" v-else>
      <div class="info-close">
        <span @click="closehandle">关闭</span>
      </div>
      <div class="info-item">
        <el-row :gutter="24" v-if="current===0">
          <el-col :span="8">
            <div>
              <span class="fs label">商户编号：</span>
              <span class="fs content">{{produceInfo.institutionCode}}</span>
            </div>
            <div>
              <span class="fs label">商户全称：</span>
              <span class="fs content">{{produceInfo.name}}</span>
            </div>
            <div>
              <span class="fs label">行业类型：</span>
              <span class="fs content">{{produceInfo.industryType}}</span>
            </div>
            <div>
              <span class="fs label">客户经理姓名：</span>
              <span class="fs content">{{produceInfo.managerName}}</span>
            </div>
            <div>
              <span class="fs label">费率名称：</span>
              <span class="fs content">{{produceInfo.rateName}}</span>
            </div>
            <div>
              <span class="fs label">当前减免金额：</span>
              <span class="fs content">{{produceInfo.deductionAmount}}元</span>
            </div>
            <div>
              <span class="fs label">信用卡单笔限额：</span>
              <span class="fs content">{{produceInfo.creditCartSingleQuota}}元</span>
            </div>
            <div>
              <span class="fs label">信用卡日累计限额：</span>
              <span class="fs content">{{produceInfo.creditCartDayQuota}}元</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <span class="fs label">商户状态：</span>
              <span class="fs content">{{produceInfo.state}}</span>
            </div>
            <div>
              <span class="fs label">商户性质：</span>
              <span class="fs content">{{produceInfo.institutionCharacter}}</span>
            </div>
            <div>
              <span class="fs label">商户类别：</span>
              <span class="fs content">{{produceInfo.institutionCategory}}</span>
            </div>
            <div>
              <span class="fs label">结算方式：</span>
              <span class="fs content">{{produceInfo.settltAccountsType}}</span>
            </div>
            <div>
              <span class="fs label">结算卡号：</span>
              <span class="fs content">{{produceInfo.settleAccountsCard}}</span>
            </div>
            <div>
              <span class="fs label">借记卡日累计限额：</span>
              <span class="fs content">{{produceInfo.debitCartDayQuota}}元</span>
            </div>
            <div>
              <span class="fs label">借记卡月累计限额：</span>
              <span class="fs content">{{produceInfo.debitCartMonthQuota}}元</span>
            </div>
            <div>
              <span class="fs label">卡内活期：</span>
              <span class="fs content" v-if="produceInfo.curBal">{{produceInfo.curBal}}元</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div>
              <span class="fs label">商户简称：</span>
              <span class="fs content">{{produceInfo.simpleName}}</span>
            </div>
            <div>
              <span class="fs label">商户类型：</span>
              <span class="fs content">{{produceInfo.institutionType}}</span>
            </div>
            <div>
              <span class="fs label">客户经理工号：</span>
              <span class="fs content">{{produceInfo.managerId}}</span>
            </div>
            <div>
              <span class="fs label">入账方式：</span>
              <span class="fs content">{{produceInfo.passEntryType}}</span>
            </div>
            <div>
              <span class="fs label">当前减免已使用金额：</span>
              <span class="fs content">{{produceInfo.deductionUserAmount}}元</span>
            </div>
            <div>
              <span class="fs label">借记卡单笔限额：</span>
              <span class="fs content">{{produceInfo.debitCartSingleQuota}}元</span>
            </div>
            <div>
              <span class="fs label">信用卡月累计限额：</span>
              <span class="fs content">{{produceInfo.creditCartMonthQuota}}元</span>
            </div>
            <div>
              <span class="fs label">卡内定期：</span>
              <span class="fs content" v-if="produceInfo.deposit">{{produceInfo.deposit}}元</span>
            </div>
          </el-col>
        </el-row>
        <div class="pos-box" v-else>
          <div class="item" v-for="(item,i) in produceList" :key="i">
            <span class="fs label">{{item.name}}</span>
            <span class="fs content">{{item.content}}</span>
          </div>
        </div>
        <el-row :gutter="24">
          <el-col :span="6">
            <pieChart id="bone" v-if="data1.length>0" title="本月累计交易笔数" :source='data1' />
          </el-col>
          <el-col :span="6">
            <pieChart id="btwo" v-if="data2.length>0" title="本月累计交易金额" :source='data2' />
          </el-col>
          <el-col :span="6">
            <pieChart id="three" v-if="data3.length>0" title="本年累计交易笔数" :source='data3' />
          </el-col>
          <el-col :span="6">
            <pieChart id="fout" v-if="data4.length>0" title="本年累计交易金额" :source='data4' />
          </el-col>
        </el-row>

      </div>
    </div>
  </div>
</template>

<script>
// import {
//   getselectProduct, // 获取pos机和卡通宝数据
//   getselectProcductInfoByCode, // 获取pos机和卡通宝数据详情
//   getMSFByCode,// 获取码上付数据
//   getSelectMSFInfoByCode, // 获取码上付详情
//   getselectPayTypeByInititution, // 根据商户编号获取产品支付统计
// } from "@/api/meshMap/meshIndex.js";
import pieChart from './pieChart.vue'
import msf from '../mock/msf.json'

export default {
  name: "productInfo",
  props: {
    code: String
  },
  watch: {},
  components: {
    pieChart
  },
  data() {
    return {
      list1: ["码上付", "卡通宝", "POS"],
      current: 0,
      tableData: msf,
      toggle: true,
      type: 9, // 9:pos,4卡通宝
      currentPage: 1,
      pageSize: 10,
      total: msf.length,
      produceInfo: {}, // 商户产品信息
      produceList: [],
      data1: [],
      data2: [],
      data3: [],
      data4: [],
    };
  },
  mounted() {
    // this.getMSFByCodeData() // 获取码上付
  },
  methods: {
    navClick(index) {
      this.current = index;
      if (index === 0) {
        // this.getMSFByCodeData() // 码上付
        this.tableData = msf
        this.total = msf.length
      } else if (index === 1 || index === 2) {
        this.type = index === 1 ? 4 : 9
        this.tableData = []
        this.total = 0
        // this.getselectProductData() // pos 和卡通宝
      }
    },
    // 表格表头颜色更改
    tableHeaderStyle({ row, column, rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        return "background:#e6f4f6;";
      }
    },
    // 查看
    queryhandle(code) {
      if (this.current === 0) {
        // 码上付
        // this.getSelectMSFInfoByCodeData(code)
        this.produceInfo = msf[0]
      } else {
        //  获取pos机和卡通宝数据
        // this.getselectProcductInfoByCodeData(code)
      }
      // this.getselectPayTypeByInititutionData(code)
      this.toggle = !this.toggle
    },
    // 关闭
    closehandle() {
      this.toggle = !this.toggle
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    getList() {
      if (this.current === 0) {
        // this.getMSFByCodeData() // 码上付
      } else {
        this.type = this.current === 1 ? 4 : 9
        // this.getselectProductData() // pos 和卡通宝
      }
    },
    //  获取pos机和卡通宝数据
    getselectProductData() {
      const data = {
        code: this.code,
        type: this.type,
        pageIndex: this.currentPage,
        pageSize: this.pageSize
      }
      getselectProduct(data).then(res => {
        if (res.state === '000000') {
          this.tableData = res.data.list
          this.total = Number(res.data.totalSize)
        }
      })
    },
    // 码上付
    getMSFByCodeData() {
      const data = {
        code: this.code,
        pageIndex: this.currentPage,
        pageSize: this.pageSize
      }
      getMSFByCode(data).then(res => {
        if (res.state === '000000') {
          this.tableData = res.data.list
          this.total = Number(res.data.totalSize)
        }
      })
    },
    // 码上付详情
    getSelectMSFInfoByCodeData(institutioncode) {
      getSelectMSFInfoByCode({ institutioncode }).then(res => {
        if (res.state === '000000') {
          this.produceInfo = res.data
        }
      })
    },
    //  获取pos机和卡通宝数据
    getselectProcductInfoByCodeData(code) {
      getselectProcductInfoByCode({ code }).then(res => {
        if (res.state === '000000') {
          this.produceList = [{
            name: '商户编号：',
            content: res.data.institutionCode
          }, {
            name: '商户名称：',
            content: res.data.name
          }, {
            name: '终端号：',
            content: res.data.terminalNumber
          }, {
            name: '工商注册号：',
            content: res.data.businessRegistrationNumber
          }, {
            name: '机构号：',
            content: res.data.orgCode
          }, {
            name: '机构名称：',
            content: res.data.orgName
          }, {
            name: '是否支持信用卡：',
            content: res.data.supportCreditCard
          }, {
            name: '商户地址：',
            content: res.data.address
          }, {
            name: '专业市场：',
            content: "没有字段"
          }, {
            name: '行业名称：',
            content: res.data.industryName
          }, {
            name: '手机号码',
            content: res.data.phone
          }, {
            name: '结算户名：',
            content: res.data.settleAccountsName
          }, {
            name: '结算卡号：',
            content: res.data.settleAccountsCardNumber
          }, {
            name: '绑定电话：',
            content: res.data.bindingPhone
          }, {
            name: '硬件序列号：',
            content: res.data.hardwareSerialNumber
          }, {
            name: '开户日期：',
            content: res.data.openAccountsDate
          }, {
            name: '卡内活期：',
            content: res.data.curBal ? res.data.curBal + '元' : ''
          }, {
            name: '卡内定期：',
            content: res.data.deposit ? res.data.deposit + '元' : ''
          },]
        }
      })
    },
    randomColor(index) {
      let i = index % 4
      let arr = ['#ffdc60', '#3ba272', '#9a60b4', '#ea7ccc']
      return arr[i]
    },
    getselectPayTypeByInititutionData(code) {
      this.data1 = []
      this.data2 = []
      this.data3 = []
      this.data4 = []
      getselectPayTypeByInititution({ code: code }).then(res => {
        if (res.state === '000000') {
          res.data.forEach((v, i) => {
            if ('微信支付'.includes(v.tranType)) {
              v.color = "#91cc75"
            } else if ('支付宝支付'.includes(v.tranType)) {
              v.color = "#6b83ce"
            } else if ('银联支付'.includes(v.tranType)) {
              v.color = "#ee6666"
            } else {
              v.color = this.randomColor(i)
            }
            const { monthTransactionAmount, tranType, monthTransactionNumber, yearTransactionNumber, yearTransactionAmount, color } = v
            this.data1.push({ name: tranType, value: monthTransactionNumber, color })
            this.data2.push({ name: tranType, value: monthTransactionAmount, color })
            this.data3.push({ name: tranType, value: yearTransactionNumber, color })
            this.data4.push({ name: tranType, value: yearTransactionAmount, color })
          })
        }
      })
    }
  },

};
</script>

<style scoped lang="scss">
.product-box {
  padding: 0 20px;
  .nav-box {
    display: flex;
    align-items: center;
    font-size: 14px;
    margin: 10px 0;
    .nav-item {
      cursor: pointer;
      .line {
        padding: 0 6px;
      }
    }
    .nav-item:nth-child(3) {
      .line {
        display: none;
      }
    }
    .active {
      color: #4ccbcc;
      font-size: 16px;
    }
  }
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
.info-box {
  .info-close {
    font-size: 14px;
    display: flex;
    justify-content: flex-end;
    padding: 6px 10px 6px 0;
    background-color: #e6f4f6;
    color: #4ccbcd;
  }
  .info-item {
    padding: 10px 20px;
  }
}
.pos-box {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  .item {
    width: 33%;
  }
}
</style>