<template>
  <IDialog
    :title="title"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form
      ref="myForm"
      :columns="columns"
      label-width="80px"
      @eventFun="eventFun"
    />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
// import service from "@/modules/module-gridManagement/views/gridManagement/api";
import service from "../api";
export default {
  mixins: [dialogMixin],
  props: {
    selected: {
      type: Array,
    },
    title:{
      type:String,
      default: "分配网点",
    }
  },
  data() {
    return {
      columns: [
        {
          label: "网格名称",
          key: "selects",
          value: "",
          type: "text",
          disabled: true,
        },
        // {
        //   label: "责任机构",
        //   key: "deptId",
        //   value: "",
        //   type: "cascader",
        //   rules: [
        //     { required: true, trigger: "change", message: "责任机构不能为空" },
        //   ],
        //   props: {
        //     label: "label",
        //     value: "id",
        //     children: "children",
        //     emitPath: false,
        //     checkStrictly: true,
        //   },
        //   filterable: true,
        //   options: this.deptTreeSelect,
        // },
        {
          label: "责任网点",
          key: "anchorName",
          value: "",
          type: "autocomplete",
          placeholder: "请输入并选择责任网点",
          rules: [
            { required: true, trigger: "change", message: "责任网点不能为空" },
          ],
          querySearch: this.queryAllanchor,
          valueKey: "anchorName",
          triggerOnFocus: false,
          events: {
            select: true,
          },
          // props: {
          //   label: "label",
          //   value: "id",
          //   children: "children",
          //   emitPath: false,
          //   checkStrictly: true,
          // },
          // filterable: true,
          // options: this.deptTreeSelect,
        },
      ],
      anchorId: "",
      userId: "",
    };
  },
  methods: {
    async deptTreeSelect() {
      const { data } = await service.deptTree();
      return data;
    },
    initData() {
      this.$nextTick(() => {
        const obj = {
          selects: this.selected.map((item) => item.name).join(","),
          anchorName:
            this.selected.length === 1 ? this.selected[0].anchorName : "",
        };
        this.anchorId =
          this.selected.length === 1 ? this.selected[0].anchorId : "";
        this.userId = this.selected.length === 1 ? this.selected[0].userId : "";
        this.$refs.myForm.setForm(obj);
      });
    },
    queryAllanchor(str, cb) {
      let params = {
        anchorName: str,
      };
      service
        .getAllanchor(params)
        .then((res) => {
          cb(res.data);
        })
        .catch((err) => {
          cb([]);
        });
    },
    eventFun(e) {
      if (e.key == "anchorName") {
        this.anchorId = e.val.id;
        this.userId = e.val.userId;
      }
    },
    handleClose() {
      this.$refs.myForm.reset();
      this.showDialog = false;
      this.$parent.multiSelected = [];
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        const { anchorName } = formInfo;
        let param = [];
        this.selected.forEach((item) => {
          let obj = {
            userId: this.userId,
            id: item.id,
            anchorName,
            anchorId: this.anchorId,
          };
          param.push(obj);
        });
        service.updateGridDept(param).then((res) => {
          this.$modal.msgSuccess("分配成功");
          this.$parent.fetchList();
          this.handleClose();
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>