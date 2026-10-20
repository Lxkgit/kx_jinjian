<template>
  <div class="merchant-info-wrapper">
    <titleBox title="基本信息" color="#4CCBCD">
      <subTitle :title="subTitleName" :src="src"></subTitle>
      <infoList :infoData="infoData" />
    </titleBox>
    <titleBox title="客户标签" color="#4CCBCD">
      <infoList :infoData="labelData" :gridColumns="1">
        <template #default="{ data }">
          <div class="label-list">
            <labelItem v-for="(item, index) in data.labelList" :key="index" :label-name="item">
            </labelItem>
          </div>
        </template>
      </infoList>
    </titleBox>
    <titleBox title="客户产品一览表" color="#4CCBCD">
      <tableList :header="header" :source="source" background="#EBF4F3">
        <template #loanAmount="{ data }">
          <span class="balance-num">{{ data }}</span>
        </template>
        <template #manager="{ data }">
          <span class="name-value">{{ data }}</span>
        </template>
      </tableList>
    </titleBox>
  </div>
</template>

<script>
import titleBox from "../components/titleBox";
import subTitle from "../components/subTitle";
import infoList from "../components/infoList";
import labelItem from "../components/labelItem";
import tableList from "../components/tableList";
import service from '../api';
export default {
  props: {
    customerParam: Object,
  },
  components: {
    titleBox,
    subTitle,
    infoList,
    labelItem,
    tableList,
  },
  data() {
    return {
      subTitleName: '',
      src: require("../assets/shop.png"),
      infoData: [
        { label: "法人", value: "", key: 'legalPersonName' },
        { label: "统一社会信用代码", value: "", key: 'customerIdentity' },
        { label: "法人联系电话", value: "", key: 'legalPersonPhone' },
        { label: "法人身份证号", value: "", key: 'legalPersonIdentityNum' },
        { label: "经营地址", value: "", key: 'businessAddress' },
        { label: "归属网格", value: "", key: 'belongGridName' },
        { label: "网格管理员", value: "", key: 'gridManager' },
        { label: "网格归属机构", value: "", key: 'branchBank' },
      ],
      labelData: [
        { label: "网格标签", slot: true, labelList: [] },
      ],
      header: [
        { label: "产品名称", flex: 1, prop: "productName" },
        { label: "合同到期时间", flex: 1, prop: "expireDate" },
        { label: "余额", flex: 1, prop: "loanAmount", slot: true },
        { label: "客户经理", flex: 1, prop: "manager", slot: true },
      ],
      source: [],
    };
  },
  mounted() {
    this.selectCustomByAnchorId()
    this.selectLabelByAnchorId()
    this.selectCreditRecordByAnchorId()
  },
  methods: {
    selectCustomByAnchorId() {
      const param = {
        customerIdentity: this.customerParam.identity
      }
      service.selectCustomByAnchorId(param).then(res => {
        const { data } = res
        this.subTitleName = data.customerName
        this.infoData.forEach(item => {
          item.value = data[item.key]
        })
      })
    },
    selectLabelByAnchorId() {
      const { identity, type } = this.customerParam
      const param = {
        customerIdentity: identity,
        type
      }
      service.selectLabelByAnchorId(param).then(res => {
        const { data } = res
        this.labelData[0].labelList = data
      })
    },
    selectCreditRecordByAnchorId() {
      const { identity } = this.customerParam
      const param = {
        customerIdentity: identity,
      }
      service.selectCreditRecordByAnchorId(param).then(res => {
        const { data } = res
        this.source = data
        console.log('source',this.source);
      })
    }
  }
};
</script>

<style lang="scss" scoped>
.merchant-info-wrapper {
  padding: 36px 30px;
  .label-list {
    display: flex;
    flex-wrap: wrap;
  }
   .balance-num {
    color: #e9931c;
  }
  .name-value {
    color: #4ea6ee;
  }
}
</style>
