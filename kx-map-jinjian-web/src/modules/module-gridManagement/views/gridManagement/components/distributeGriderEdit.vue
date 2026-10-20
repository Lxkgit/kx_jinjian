<template>
  <IDialog
    title="网格员设置"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form ref="myForm" :columns="columns" label-width="100px">
      <template #userId>
        <el-select
          v-model="param.userManagerNum"
          clearable
          filterable
          remote
          :remote-method="querySearchAsync"
          placeholder="请输入工号或名称进行查询"
          :loading="loading"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
            <span class="label-left">{{ item.label }}</span>
            <span class="label-right">{{ item.info }}</span>
          </el-option>
        </el-select>
      </template>
      <template #secondUserId>
        <el-select
          v-model="param.secondManagerNum"
          clearable
          filterable
          remote
          :multiple="true"
          :remote-method="querySecondSearchAsync"
          placeholder="请输入工号或名称进行查询"
          :loading="loading"
        >
          <el-option
            v-for="item in secondOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
            <span class="label-left">{{ item.label }}</span>
            <span class="label-right">{{ item.info }}</span>
          </el-option>
        </el-select>
      </template>
    </i-form>
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
export default {
  mixins: [dialogMixin],
  dicts: ["grid_type"],
  props: {
    selected: {
      type: Array,
    },
    deptId: {
      type: [String, Number],
    },
  },
  data() {
    return {
      columns: [
        {
          label: "已选网格",
          key: "selects",
          value: "",
          type: "text",
          disabled: true,
        },
        {
          label: "网格主管员",
          key: "userManagerNum",
          type: "slot",
          slotName: "userId",
          required: true,
        },
        {
          label: "网格副管员",
          key: "secondManagerNum",
          type: "slot",
          slotName: "secondUserId",
          required: true,
        },
      ],
      param: {
        userManagerNum: "",
        secondManagerNum: [],
      },
      options: [],
      secondOptions: [],
      loading: false,
    };
  },
  methods: {
    querySearchAsync(queryString) {
      if (queryString) {
        this.loading = true;
        const param = {
          deptId: this.deptId,
          search: [queryString],
          type: 1,
        };
        service.getPaAddrInfoTypeUser(param).then((res) => {
          this.options = res.data.map((i) => {
            return {
              label: i.nickName,
              value: i.managerNum,
              info: i.managerNum,
            };
          });
          this.loading = false;
        });
      } else {
        this.param.userManagerNum = "";
      }
    },
    querySecondSearchAsync(queryString) {
      let search  = queryString instanceof Array ? queryString:[queryString]
      if (queryString) {
        this.loading = true;
        const param = {
          deptId: this.deptId,
          search,
          type: 2,
        };
        service.getPaAddrInfoTypeUser(param).then((res) => {
          this.secondOptions = res.data.map((i) => {
            return {
              label: i.nickName,
              value: i.managerNum,
              info: i.managerNum,
            };
          });
          this.loading = false;
        });
      } else {
        this.param.secondManagerNum = [];
      }
    },
    initData() {
      this.$nextTick(() => {
        const obj = {
          selects: this.selected.map((item) => item.name).join(","),
        };
        const { userManagerNum, secondManagerNum } = this.selected[0];
        this.param = {
          userManagerNum,
          secondManagerNum,
        };
        this.$refs.myForm.setForm(obj);
        this.querySearchAsync(userManagerNum);
        console.log(secondManagerNum,989)
        this.querySecondSearchAsync(secondManagerNum);
      });
    },
    handleClose() {
      this.showDialog = false;
      this.$parent.multiSelected = [];
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        if (!this.param.userManagerNum) {
          this.$modal.msgError("网格员不能为空");
          return;
        }
        if (this.param.secondManagerNum.length == 0) {
          this.$modal.msgError("副网格员不能为空");
          return;
        }
        let param = {
          id: this.selected[0].id,
          ...formInfo,
          ...this.param,
        };

        console.log("param", param);
        service.updateGridUser(param).then((res) => {
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
.item-name,
.item-value {
  display: inline-block;
  line-height: 1;
}
.item-value {
  font-size: 12px;
  color: #999;
}
.label-left {
  float: left;
}
.label-right {
  float: right;
  color: #8492a6;
  font-size: 13px;
}
</style>