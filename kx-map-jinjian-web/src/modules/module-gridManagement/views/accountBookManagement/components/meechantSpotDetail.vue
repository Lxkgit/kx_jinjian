<template>
  <IDialog
    title="商户主体现金收付情况抽查记录"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <infoList :infoData="infoData" :gridColumns="2"> </infoList>
    <div style="width: 700px">
      <i-form
        :gridColumns="1"
        ref="firstForm"
        :columns="columns"
        label-width="200px"
        label-position="right"
      />
      <h2>接受宣传情况</h2>
      <i-form
        :gridColumns="1"
        ref="secondForm"
        :columns="secondColumns"
        label-width="300px"
        label-position="right"
      />
      <h2>零钱备付情况</h2>
      <i-form
        :gridColumns="1"
        ref="thirdForm"
        :columns="thirdColumns"
        label-width="300px"
        label-position="right"
      />
      <h2>网格摸排情况</h2>
      <i-form
        :gridColumns="1"
        ref="fourthForm"
        :columns="fourthColumns"
        label-width="300px"
        label-position="right"
      />
    </div>
  </IDialog>
</template>
      
<script>
import { mapState, mapMutations } from "vuex";
import infoList from "@/components/BaseDetail/components/infoList.vue";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  name: "meechantSpotDetail",
  mixins: [dialogMixin],
  components: {
    infoList,
  },
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      showDialog: this.value,
      columns: [
        {
          label: "抽查时间",
          key: "checkTime",
          type: "date",
          clearable: true,
          rules: [
            { required: true, message: "请选择抽查时间", trigger: "change" },
          ],
        },
      ],
      secondColumns: [
        {
          label: "是否知晓拒收现金是违法行为",
          key: "a1",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
          span: 12,
        },
        {
          label: "是否了解人民银行在开展整治拒收现金工作",
          key: "a2",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
          span: 12,
          rules: [
            {
              required: true,
              message: "请选择是否了解人民银行在开展整治拒收现金工作",
              trigger: "change",
            },
          ],
        },
        {
          label: "是否了解金融机构提供的现金服务",
          key: "a3",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
          span: 12,
        },
        {
          label: "是否存在拒收现金行为",
          key: "a4",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
          span: 12,
        },
      ],
      thirdColumns: [
        {
          label: "商户是否收取现金",
          key: "b1",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "是否备付零钞",
          key: "b2",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
          rules: [
            {
              required: true,
              message: "请选择是否备付零钞",
              trigger: "change",
            },
          ],
        },
      ],
      fourthColumns: [
        {
          label: "银行网点是否到访",
          key: "c1",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "备注",
          key: "c2",
          type: "textarea",
        },
      ],
      tableColumns: [
        { label: "商户主体名称", key: "name" },
        { label: "主体行业", key: "name" },
        { label: "主体地址", key: "name" },
        { label: "主体联系人", key: "name" },
        { label: "操作", slot: true },
      ],
      infoData: [
        { label: "商户主体名称", value: "", key: "storeName" },
        { label: "所在网格", value: "", key: "belongGrid" },
        {
          label: "商户主体地址",
          value: "",
          key: "storeAddress",
          gridColumn: "span 2",
        },
        { label: "商户主体联系人", value: "", key: "storePerson" },
        { label: "联系电话", value: "", key: "storePhone" },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", [
      "cashUseEnvCheckList",
      "cashUseEditStatus",
      "nowCashUseCheckInfo",
    ]),
  },
  methods: {
    ...mapMutations("accountBook", [
      "updateCashUseEnvCheckList",
      "updateCashEditStatus",
      "updateNowCashUseCheckInfo",
    ]),
    initData() {
      this.infoData.forEach((item) => {
        item.value = this.defaultValue[item.key];
      });
      this.$nextTick(() => {
        this.$refs.firstForm.setForm(this.defaultValue);
        this.$refs.secondForm.setForm(this.defaultValue.checkInfo);
        this.$refs.thirdForm.setForm(this.defaultValue.checkInfo);
        this.$refs.fourthForm.setForm(this.defaultValue.checkInfo);
      });
    },
    handleConfirm() {
      this.$refs.firstForm.getForm((formInfo) => {
        let formInfo2 = this.getSecondFormInfo();
        let formInfo3 = this.getThirdFormInfo();
        let formInfo4 = this.getFourthFormInfo();
        let checkInfo = {
          ...formInfo2,
          ...formInfo3,
          ...formInfo4,
        };
        if (!(checkInfo.a2 && checkInfo.b2)) return;
        let obj = {
          ...this.defaultValue,
          checkTime: formInfo.checkTime,
          checkStatus: "2",
          checkInfo,
          checkPerson: this.$store.getters.userInfo.userName,
        };
        // 更新当前选中的抽查列表
        let storeList = this.nowCashUseCheckInfo.checkStoreList.filter(
          (i) => i.id != this.defaultValue.id
        );
        this.updateNowCashUseCheckInfo({
          ...this.nowCashUseCheckInfo,
          checkStoreList: [obj, ...storeList],
        });
        // 对抽查列表进行更新
        let arr = this.cashUseEnvCheckList.map((i) =>
          i.id == this.nowCashUseCheckInfo.id ? this.nowCashUseCheckInfo : i
        );
        this.updateCashUseEnvCheckList(arr);
        this.handleClose();
      });
    },
    handleClose() {
      this.infoData.forEach((item) => (item.value = ""));
      this.$refs.firstForm.reset();
      this.$refs.secondForm.reset();
      this.$refs.thirdForm.reset();
      this.$refs.fourthForm.reset();
      this.$parent.$parent.initData();
      this.showDialog = false;
    },
    getSecondFormInfo() {
      let obj = {};
      this.$refs.secondForm.getForm((formInfo) => {
        obj = formInfo;
      });
      return obj;
    },
    getThirdFormInfo() {
      let obj = {};
      this.$refs.thirdForm.getForm((formInfo) => {
        obj = formInfo;
      });
      return obj;
    },
    getFourthFormInfo() {
      let obj = {};
      this.$refs.fourthForm.getForm((formInfo) => {
        obj = formInfo;
      });
      return obj;
    },
  },
};
</script>
      
<style lang="scss" scoped>
h2 {
  font-size: 18px;
  font-weight: bolder;
  margin: 10px 0;
}
</style>