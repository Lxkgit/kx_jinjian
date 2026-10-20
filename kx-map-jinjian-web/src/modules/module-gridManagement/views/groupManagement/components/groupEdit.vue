<template>
  <IDialog
    :title="title"
    v-model="showDialog"
    width="45%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <el-form
      ref="formData"
      :rules="rules"
      :model="formData"
      label-width="100px"
    >
      <el-form-item label="客群名称">
        <el-input
          v-model="formData.customGroupName"
          placeholder="请填写客群名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="说明">
        <el-input
          type="textarea"
          :rows="4"
          v-model="formData.groupDesc"
          placeholder="请填写客群说明"
        ></el-input>
      </el-form-item>
      <div v-if="groupType == '0'">
        <el-form-item label="标签圈群">
          <div class="label-group-wrapper">
            <el-form-item label="客户类型">
              <el-select
                class="item-select"
                disabled
                v-model="formData.customType"
              >
                <el-option
                  v-for="item in typeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <div class="operation-wrapper">
              <div
                class="operation-item"
                v-for="(item, index) in formData.operateList"
                :key="index"
              >
                <el-cascader
                  class="item-select"
                  v-model="item.id"
                  :options="labelOptions"
                  :props="props"
                ></el-cascader>
                <div
                  class="el-icon-remove-outline item-btn"
                  @click="deleteClick(item)"
                ></div>
              </div>
              <div class="operation-item" @click="addClick">
                <i class="el-icon-circle-plus-outline"></i>
              </div>
            </div>
          </div>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否更新">
              <el-switch
                v-model="formData.updateFlag"
                :active-value="1"
                :inactive-value="0"
              >
              </el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="更新周期">
              <el-select
                v-model="formData.updateCycle"
                placeholder="请选择更新周期"
              >
                <el-option
                  v-for="el in dict.type.refresh_cycle_type"
                  :key="el.value"
                  :label="el.label"
                  :value="el.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-else-if="groupType == '1'">
        <el-form-item label="客户类型">
          <el-select class="item-select" v-model="formData.customType">
            <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="附件上传">
          <el-upload
            class="upload-demo"
            :action="upload.action"
            :limit="1"
            :file-list="fileList"
            :show-file-list="false"
            :on-success="successHandle"
            :on-remove="removeHandle"
          >
            <el-button type="primary">文件上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
    </el-form>
  </IDialog>
</template>

<script>
import service from "../api";
import dialogMixin from "@/mixins/dialogMixin.vue";
const TITLE_TYPE = {
  add: "新增",
  update: "编辑",
  import: "导入",
};
export default {
  mixins: [dialogMixin],
  dicts: ["anchor_type", "refresh_cycle_type"],
  props: {
    status: {
      type: String,
      default: "add",
    },
    editInfo: {
      type: Object,
    },
    groupType: {
      type: String, // 0-规则, 1-导入
      default: "0",
    },
  },
  data() {
    return {
      formData: {
        customGroupName: "",
        groupDesc: "",
        customType: null,
        operateList: [],
        updateFlag: 0,
      },
      rules: {},
      typeOptions: [
        { label: "个人客户", value: 1 },
        { label: "公司客户", value: 2 },
        { label: "商户客户", value: 3 },
      ],
      labelOptions: [],
      props: {
        value: "id",
        label: "name",
        children: "child",
        emitPath: false,
      },
      upload: {},
      fileList: [],
    };
  },
  computed: {
    title() {
      return TITLE_TYPE[this.status];
    },
  },
  mounted() {},
  methods: {
    initData() {
      this.formData.customType = this.editInfo.parentId;
      this.cascadeLabel();

      if (this.status === "add") {
        return;
      }
      this.formData = Object.assign(this.formData, this.editInfo);
    },
    cascadeLabel() {
      const parma = {
        id: this.formData.customType,
      };
      service.cascadeLabel(parma).then((res) => {
        const { data } = res;
        this.labelOptions = data;
      });
    },
    addClick() {
      this.formData.operateList.push({ id: null });
    },
    deleteClick(item) {
      const index = this.formData.operateList.indexOf(item);
      this.formData.operateList.splice(index, 1);
    },
    handleClose() {
      this.formData = {
        customGroupName: "",
        groupDesc: "",
        customType: null,
        operateList: [],
        updateFlag: 0,
      };
      this.$refs.formData.resetFields();
      this.showDialog = false;
    },
    handleConfirm() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          let param = {};
          if (this.status === "add") {
            param = {
              categoryId: this.editInfo.id,
              ...this.formData,
              labelIds: this.formData.operateList.map((item) => item.id),
            };
          } else {
            param = {
              ...formData,
              labelIds: this.formData.operateList.map((item) => item.id),
            };
          }
          service.addOrEditGroup(param).then(() => {
            this.$modal.msgSuccess(`${TITLE_TYPE[this.status]}`);
            this.$parent.fetchList();
            this.handleClose();
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.label-group-wrapper {
  width: 100%;
  padding: 10px 30px;
  background: rgba($color: #000000, $alpha: 0.1);
  .operation-wrapper {
    margin-top: 20px;
    margin-left: 70px;
    position: relative;
    &::before {
      content: "且";
      position: absolute;
      top: 50%;
      width: 24px;
      height: 24px;
      line-height: 24px;
      text-align: center;
      transform: translate(-50%, -50%);
      color: #1890ff;
      border-radius: 50%;
      background: rgba($color: #acd4ff, $alpha: 1);
      z-index: 99;
    }
    &::after {
      content: "";
      position: absolute;
      top: 18px;
      width: 1px;
      height: calc(100% - 36px);
      background: #1890ff;
      z-index: 9;
    }
    .operation-item {
      padding-left: 30px;
      margin-bottom: 10px;
      position: relative;
      &::before {
        content: "";
        width: 30px;
        height: 1px;
        background: #1890ff;
        position: absolute;
        top: 50%;
        left: 0;
      }
    }

    .item-btn {
      margin-left: 10px;
    }
  }
  .item-select {
    width: 300px;
  }
}
</style>
