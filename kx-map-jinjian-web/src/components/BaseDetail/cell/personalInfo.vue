<template>
  <div class="personal-info-wrapper">
    <titleBox title="基本信息">
      <subTitle :title="subTitleName" :src="src"></subTitle>
      <infoList :infoData="infoData" />
    </titleBox>
    <titleBox title="客户标签">
      <infoList :infoData="labelData" :gridColumns="1">
        <template #default="{ data }">
          <div class="label-list">
            <labelItem v-for="(item, index) in data.labelList" :key="index" :label-name="item">
            </labelItem>
          </div>
        </template>
      </infoList>
    </titleBox>
    <titleBox title="乡村振兴维度" v-if="false">
      <specialList :infoData="villageInfo"></specialList>
      <infoList :infoData="villageLabel" :gridColumns="1">
        <template #default="{ data }">
          <div class="label-list">
            <specialLabel v-for="(item, index) in data.labelList" :key="index" :label-item="item">
            </specialLabel>
          </div>
        </template>
      </infoList>
    </titleBox>
    <titleBox title="信贷产品一览表">
      <tableList :header="header" :source="productData">
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
import specialList from "../components/specialList";
import specialLabel from "../components/specialLabel";
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
    specialList,
    specialLabel,
    tableList,
  },
  dicts: ['marriage_status', 'sys_user_sex', 'customer_level'],
  data() {
    return {
      subTitleName: "",
      src: require("../assets/person.png"),
      infoData: [
        {
          label: "性别", value: "", key: "gender"
        },
        { label: "年龄", value: "", key: "age" },
        { label: "出生日期", value: "", key: "birthday" },
        { label: "身份证号", value: "", key: "customerIdentity" },
        { label: "联系电话", value: "", key: "customerPhone" },
        { label: "婚姻状况", value: "", key: "maritalStatus" },
        { label: "职业", value: "", key: "job" },
        { label: "工作单位", value: "", key: "workUnit" },
        { label: "客户等级", value: "", key: "customerLevel" },
        { label: "客户编号", value: "", key: "customerNm" },
        { label: "归属网络", value: "", key: "belongGridName" },
        { label: "归属机构", value: "", key: "branchBank" },
        { label: "网格主管员", value: "", key: "gridManager" },
        { label: "网格地址", value: "", key: "customerAddress" },
      ],
      labelData: [
        { label: "网格标签", slot: true, labelList: [] },
      ],
      villageInfo: [
        {
          label: "乡村振兴贷授信金额",
          value: 210312312,
          unit: "元",
          width: "49%",
        },
        {
          label: "乡村振兴贷用信金额",
          value: 210312312,
          unit: "元",
          width: "49%",
        },
        {
          label: "客户征信概要",
          value: "想爱你给个机会发光时代飞机哥",
          width: "100%",
        },
        { label: "行为全行排序", value: 12, color: "#DC963C", width: "33%" },
        { label: "价值分", value: 123, width: "33%" },
        { label: "价值全行排序", value: 12, color: "#DC963C", width: "33%" },
      ],
      villageLabel: [
        {
          label: "网格标签",
          slot: true,
          labelList: [
            { label: "线下引流-有抵押贷款", flag: true },
            { label: "振兴贷低于他行平均授信", flag: true },
            { label: "置换他行-利率置换", flag: true },
            { label: "担保置换", flag: true },
            { label: "信贷提升-有经营实体", flag: false },
            { label: "线下引流-有抵押贷款", flag: true },
          ],
        },
      ],
      header: [
        { label: "产品名称", flex: 1, prop: "productName" },
        { label: "合同到期时间", flex: 1, prop: "expireDate" },
        { label: "余额", flex: 1, prop: "loanAmount", slot: true },
        { label: "客户经理", flex: 1, prop: "manager", slot: true },
      ],
      productData: []
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
          const value = data[item.key]
          switch (item.key) {
            case 'gender':
              item.value = this.dict.label.sys_user_sex[value]
              break
            case 'maritalStatus':
              item.value = this.dict.label.marriage_status[value]
              break
            case 'customerLevel':
              item.value = this.dict.label.customer_level[value]
              break
            case 'customerIdentity':
              item.value = this.$identityMasking(value)
              break
            case 'customerPhone':
              item.value = this.$phoneMasking(value)
              break
            default:
              item.value = value
              break
          }
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
        this.productData = data
      })
    }
  }
};
</script>

<style lang="scss" scoped>
.personal-info-wrapper {
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
