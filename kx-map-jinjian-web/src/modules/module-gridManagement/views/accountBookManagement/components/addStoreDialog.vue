<template>
  <i-dialog
    :title="status == 'add' ? '新增商户' : '编辑商户'"
    v-model="showDialog"
    width="30%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <i-form
      ref="storeForm"
      :columns="columns"
      labelWidth="130px"
      labelPosition="right"
    ></i-form>
  </i-dialog>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  name: "addStoreDialog",
  mixins: [dialogMixin],
  dicts: ["merchant_type"],
  props: {
    defaultValue: {
      type: Object,
    },
    status: {
      type: String,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "商户名称",
          key: "storeName",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入商户名称",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户地址",
          key: "storeAddress",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入商户地址",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户类型",
          key: "storeType",
          type: "select",
          options: () => this.dict.type.merchant_type,
          rules: [
            {
              required: true,
              message: "请选择商户类型",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户联系人",
          key: "storeLinkMan",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入商户联系人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "商户联系人电话",
          key: "storeLinkManPhone",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入商户联系人电话",
              trigger: ["change", "blur"],
            },
          ],
        },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", [
      "bankList",
      "gridPointList",
      "nowGridInfo",
      "storeList",
    ]),
  },
  methods: {
    ...mapMutations("accountBook", ["updeteStoreList", "updateTargetStoreNum"]),
    initData() {
      console.log("defaultValue", this.defaultValue);
      if (this.status == "edit") {
        this.$nextTick(() => {
          this.$refs.storeForm.setForm(this.defaultValue);
        });
      }
    },
    handleConfirm() {
      this.$refs.storeForm.getForm((formInfo) => {
        // 新增商户
        let arr = this.storeList;
        let obj = {};
        if (this.status == "add") {
          obj = {
            gridId: this.nowGridInfo.id,
            id: new Date().getTime(),
            visitList: [],
            visitNum: "",
            lastVisitTime: "",
          };
        } else if (this.status == "edit") {
          let { gridId, id, visitList, visitNum, lastVisitTime } =
            this.defaultValue;
          arr = arr.filter((item) => !(item.id == id && item.gridId == gridId));
          obj = { gridId, id, visitList, visitNum, lastVisitTime };
        }
        // let num = Math.random();
        // if (num >= 0 && num < 0.33) {
        //   obj.storeClassify = "1";
        // } else if (num >= 0.33 && num < 0.66) {
        //   obj.storeClassify = "2";
        // } else if (num >= 0.66 && num < 1) {
        //   obj.storeClassify = "3";
        // }
        this.updeteStoreList([{ ...obj, ...formInfo }, ...arr]);
        this.$message.success(
          `${this.status == "add" ? "新增" : "修改"}商户成功!`
        );
        // 更新网格中的商户数量
        this.updateTargetStoreNum();
        this.$parent.fetchList();
        this.handleClose();
      });
    },
    handleClose() {
      this.$refs.storeForm.reset();
      this.showDialog = false;
    },
  },
};
</script>

<style>
</style>