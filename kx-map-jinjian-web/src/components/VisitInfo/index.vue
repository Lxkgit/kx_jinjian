<template>
  <div class="visit">
    <pop-title class="margin-top-total" text="最近走访" fontSize="16px" :color="color">
    </pop-title>
    <div class="visit-info">
      <ul class="visit-ul">
        <li class="item" :class="item.className" v-for="(item, i) in infoList" :key="i">
          <template v-if="item.className === 'image'">
            <div class="label" style="padding: 0 0 10px 0">走访照片:</div>
            <div class="pic">
              <el-image v-for="(pic, i) in item.value" :key="i" style="
                  width: 100px;
                  height: 100px;
                  margin-right: 20px;
                  margin-bottom: 10px;
                  border-radius: 4px;
                " :src="pic" :preview-src-list="item.value">
              </el-image>
            </div>
          </template>
          <template v-else-if="item.label === '网格标签'">
            <div class="label">网格标签:</div>
            <div class="tagBox">
              <div class="tag" v-for="tag in item.value" :key="tag">{{tag}}</div>
            </div>
          </template>
          <template v-else>
            <span class="label">{{ item.label }}：</span>
            <span class="value">{{ item.value }}</span>
          </template>
        </li>
      </ul>
    </div>
    <pop-title class="margin-top-total" text="历史走访" fontSize="16px" :color="color">
    </pop-title>
    <i-table ref="table" :data="tableData" align="center" stripe showIndex :allColumns="columns"
             :showPagination="false">
      <template #action="scope">
        <el-button type="text" @click="detailHandle(scope.row)">查看详情</el-button>
      </template>
    </i-table>
    <visitInfo v-model="showDialog" :infoList="editInfo"></visitInfo>
  </div>
</template>

<script>
import service from './api';
import popTitle from "../CustomerDetail/components/popTitle.vue";
import { customerList, companyList } from "./config.js";
import visitInfo from './components/visitInfo'
export default {
  props: {
    color: String,
    backgroundColor: String,
    customerParam: {
      type: Object
    },
    customerType: String
  },
  components: {
    popTitle,
    visitInfo
  },
  data() {
    return {
      tableData: [],
      columns: [
        {
          label: "走访时间", prop: "visitsTime"
        },
        { label: "走访人员", prop: "visitsManager" },
        { label: "走访地址", prop: "visitsAddress" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "查看详情",
          ename: "handleDetail",
          type: "text",
        },
      ],
      infoList: [],
      showDialog: false,
      editInfo: [],
    };
  },
  mounted() {
    this.infoList = this.customerType == "1" ? JSON.parse(JSON.stringify(customerList)) : JSON.parse(JSON.stringify(companyList));
    this.editInfo = this.customerType == "1" ? JSON.parse(JSON.stringify(customerList)) : JSON.parse(JSON.stringify(companyList));
    this.customerVisits()
  },
  methods: {
    customerVisits() {
      service.customerVisits(this.customerParam).then((res) => {
        res.data.forEach(item => {
          // 是否为走访黑户
          item.isVisitsBlack = item.isVisitsBlack === "1" ? "是" : (item.isVisitsBlack === "0" ? '否' : '');
          item.otherBankDueDemand = item.otherBankDueDemand === "1" ? "是" : (item.otherBankDueDemand === "0" ? '否' : '');
          // 走访照片数据格式处理
          if (item.visitsPicture.includes(',') && item.visitsPicture.length > 0) {
            item.visitsPicture = item.visitsPicture.split(',')
          } else if (!item.visitsPicture.includes(',') && item.visitsPicture.length > 0) {
            item.visitsPicture = [item.visitsPicture]
          } else {
            item.visitsPicture = []
          }
          item.visitLabel = item.visitLabel ? JSON.parse(item.visitLabel) : ''
        })
        const { data } = res
        this.tableData = data
        const obj = data[0]
        this.infoList.forEach(item => {
          switch (item.key) {
            case 'customerPhone':
              item.value = this.$phoneMasking(obj[item.key])
              break
            default:
              item.value = obj[item.key]
              break
          }
        })
      });
    },
    detailHandle(obj) {
      this.showDialog = true
      this.editInfo.forEach(item => {
        switch (item.key) {
          case 'customerPhone':
            item.value = this.$phoneMasking(obj[item.key])
            break
          default:
            item.value = obj[item.key]
            break
        }
      })
    }
  },
};
</script>

<style lang="scss" scoped>
.visit {
  width: 100%;
  // height: 600px;
  .margin-top-total {
    margin: 10px;
    .date {
      font-weight: 500;
      font-size: 16px;
      color: var(--color);
    }
  }
  .visit-info {
    .visit-ul {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      // justify-content: space-between;
      .item {
        width: 33.333%;
        box-sizing: border-box;
        padding-left: 30px;
        margin: 5px 0;
        white-space: nowrap;
        display: flex;
        align-items: center;
        &.image {
          width: 100%;
          .pic {
            display: flex;
            flex-wrap: wrap;
          }
        }
        .label {
          font-size: 18px;
          color: #333333;
        }
        .tagBox {
          display: flex;
          flex-direction: row;
          align-items: center;
          max-width: 600px;
          flex-wrap: wrap;
          .tag {
            // width: fit-content;
            color: #e7921d;
            font-weight: bold;
            font-size: 16px;
            margin: 3px;
          }
        }
        .value {
          font-weight: bold;
          font-size: 18px;
          width: 600px;
          display: inline-block;
          white-space: normal;
        }
        &.expireMoney {
          .value {
            font-weight: bold;
            font-size: 18px;
            color: #e7921d;
          }
        }
        &.gridLabel {
          width: 66.667%;
          .value {
            font-weight: bold;
            font-size: 18px;
            color: #e7921d;
          }
        }
        &.middle {
          width: 66.667%;
        }
        &.large {
          width: 100%;
        }
        .pic {
          padding-left: 10px;
          margin: 0 5px;
        }
      }
    }
  }
}
</style>
