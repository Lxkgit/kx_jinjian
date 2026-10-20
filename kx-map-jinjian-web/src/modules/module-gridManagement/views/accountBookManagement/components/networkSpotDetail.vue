<template>
  <i-dialog
    title="银行网点现金服务质量抽查记录"
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
    </div>
  </i-dialog>
</template>
        
<script>
import { mapState, mapMutations } from "vuex";
import infoList from "@/components/BaseDetail/components/infoList.vue";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
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
            {
              required: true,
              message: "请选择抽查时间",
              trigger: "change",
            },
          ],
        },
        {
          label: "零钞兑换时间",
          key: "1",
          type: "date",
          clearable: true,
          rules: [
            {
              required: true,
              message: "请选择零钞兑换时间",
              trigger: "change",
            },
          ],
        },
        {
          label: "零钞兑换是否说明预约方式",
          key: "2",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "预约方式",
          key: "3",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "电话预约" },
            { value: "0", label: "现场预约" },
            { value: "2", label: "App/小程序预约" },
          ],
        },
        {
          label: "对外支付现金中残缺污损人民币比例",
          key: "4",
          type: "text",
          clearable: true,
        },
        {
          label: "是否存在客户未预约无法进行小面额人民币取现、兑换",
          key: "5",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "是否存在外拓零钞服务需要网点员工垫款",
          key: "6",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "是否存在未为老年客户提供便利化现金服务",
          key: "7",
          type: "select",
          clearable: true,
          options: [
            { value: "1", label: "是" },
            { value: "0", label: "否" },
          ],
        },
        {
          label: "备注",
          key: "8",
          type: "textarea",
        },
      ],
      infoData: [
        {
          label: "网点名称",
          value: "",
          key: "pointName",
          gridColumn: "span 2",
        },
        { label: "所在网格", value: "", key: "belongGrid" },
        {
          label: "网点地址",
          value: "",
          key: "pointAddress",
          gridColumn: "span 2",
        },
        { label: "网点联系人", value: "", key: "dutyPerson" },
        { label: "负责人联系电话", value: "", key: "dutyPersonPhone" },
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
        this.$refs.firstForm.setForm(this.defaultValue.checkInfo);
      });
    },
    handleConfirm() {
      this.$refs.firstForm.getForm((formInfo) => {
        let obj = {
          ...this.defaultValue,
          checkTime: formInfo.checkTime,
          checkStatus: "2",
          checkInfo: formInfo,
          checkPerson: this.$store.getters.userInfo.userName,
        };
        // // 更新当前选中的抽查列表
        let pointList = this.nowCashUseCheckInfo.checkPointList.filter(
          (i) => i.id != this.defaultValue.id
        );
        this.updateNowCashUseCheckInfo({
          ...this.nowCashUseCheckInfo,
          checkPointList: [obj, ...pointList],
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
      this.$parent.$parent.initData();
      this.showDialog = false;
      this.$refs.firstForm.reset();
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