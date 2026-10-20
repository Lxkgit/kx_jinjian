<template>
  <i-dialog :title="dialogTitle" v-model="showDialog" width="80%" @handleConfirm="handleConfirm"
            @handleClose="handleClose">
    <el-row :gutter="24">
      <el-form :model="innerForm" ref="innerForm" :label-width="formLabelWidth">
        <el-col :span="12" v-for="item in fieldForm" :key="item.id">
          <el-form-item :label="item.fieldName" :required="item.isRequired == 1">
            <el-input v-if="item.fillingForm == 1" :size="size" style="width: 100%"
                      v-model="item.detailContent" :placeholder="'请输入' + item.fieldName"
                      :style="{ width: '100%' }" :disabled="isDisabled" />
            <el-input v-if="item.fillingForm == 2" :size="size" style="width: 100%" type="textarea"
                      v-model="item.detailContent" :placeholder="'请输入' + item.fieldName"
                      :style="{ width: '100%' }" :disabled="isDisabled" />
            <el-radio-group v-if="item.fillingForm == 3" :size="size" v-model="item.detailContent">
              <el-radio v-for="(value, ind) in item.content.split(',')" :key="ind" :label="`${ind}`"
                        :disabled="isDisabled">
                {{ value }}
              </el-radio>
            </el-radio-group>
            <el-checkbox-group v-if="item.fillingForm == 4" v-model="item.detailContent"
                               :size="size">
              <el-checkbox v-for="obj in item.content.split(',')" :key="obj" :label="obj"
                           :disabled="isDisabled">
              </el-checkbox>
            </el-checkbox-group>
            <el-select v-if="item.fillingForm == 5" v-model="item.detailContent" :size="size"
                       placeholder="请选择" :style="{ width: '100%' }" :disabled="isDisabled">
              <el-option v-for="(value, ind) in item.content.split(',')" :key="ind" :label="value"
                         :value="`${ind}`">
              </el-option>
            </el-select>
            <el-date-picker v-if="item.fillingForm == 6" v-model="item.detailContent" :size="size"
                            type="date" placeholder="选择日期" :style="{ width: '100%' }"
                            :disabled="isDisabled" />
            <el-date-picker v-if="item.fillingForm == 7" v-model="item.detailContent" :size="size"
                            type="datetimerange" range-separator="至" start-placeholder="开始日期"
                            end-placeholder="结束日期" format="yyyy-MM-dd HH:mm:ss"
                            value-format="yyyy-MM-dd HH:mm:ss" :style="{ width: '100%' }"
                            :disabled="isDisabled" />
            <el-upload v-if="item.fillingForm == 8" list-type="picture-card" class="upload-demo"
                       style="width: 100%" :limit="10" :headers="upload.headers"
                       :action="upload.url" :on-success="fieldUpload" :on-remove="fieldRemove"
                       :before-remove="beforeRemove" :file-list="fileList"
                       :style="{ width: '100%' }" :disabled="isDisabled">
              <i class="el-icon-plus"></i>
            </el-upload>
            <div v-if="item.fillingForm == 9" class="cascader">
              <div class="cascader-scroll">
                <div class="panel" v-for="parent in options" :key="parent.id">
                  <div class="up">{{ parent.labelGroupName }}：</div>
                  <div class="item" :class="{
                      active: chooseLabel.map((v) => v.id).includes(child.id),
                    }" v-for="child in parent.childLabels" :key="child.id"
                       @click="childLabelClick(parent, child)">
                    <span class="name">{{ child.labelName }}</span>
                  </div>
                </div>
              </div>
            </div>
            <div v-if="item.fillingForm == 10" style="color: #c0c4cc">
              请前往移动端进行打卡操作
            </div>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
  </i-dialog>
</template>

<script>
import { getToken } from "@/utils/auth";
import Cascader from "../../components/Cascader";
import services from "../../api";
export default {
  name: "taskDetail",
  components: { Cascader },
  props: {
    // 是否展示弹窗
    value: { type: Boolean, default: false, required: true },
    // 弹窗的状态：编辑、查看
    status: { type: String, default: "add" },
    // 打开弹出式携带的数据
    defaultValue: { type: Object, default: () => { } },
  },
  data() {
    return {
      size: "mini",
      innerRules: {},
      fieldForm: [],
      options: [],
      props: {
        multiple: true,
        label: "name",
        value: "id",
        children: "child",
      },
      taskInfo: {
        id: "",
        taskId: "",
      },
      // 文件上传配置
      upload: {
        headers: { Authorization: "Bearer " + getToken() }, // 设置上传的请求头部
        url: process.env.VUE_APP_BASE_API + "/system//minio//upload", // 上传的地址
      },
      formLabelWidth: "120px",
      showDialog: null,
      innerForm: {},
      fileList: [],
      chooseLabel: [],
    };
  },
  watch: {
    value: {
      handler(val) {
        this.showDialog = val;
      },
      immediate: true,
    },
    showDialog: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    defaultValue: {
      handler(val) {
        if (val) {
          this.cascadeLabel(val.taskObjectId);
          this.fieldForm = val.contents.map((i) => {
            if (i.fillingForm == 4) {
              i.detailContent =
                i.detailContent == null ? [] : i.detailContent.split(",");
            }
            if (i.fillingForm == 7) {
              i.detailContent =
                i.detailContent == null
                  ? []
                  : [
                    i.detailContent.split(",")[0],
                    i.detailContent.split(",")[1],
                  ];
            }
            if (i.fillingForm == 8) {
              i.detailContent = i.detailContent ?? "[]";
              this.fileList = JSON.parse(i.detailContent);
            }
            if (i.fillingForm == 9) {
              i.detailContent =
                i.detailContent == null ? "[]" : i.detailContent;
              this.chooseLabel = JSON.parse(i.detailContent);
            }
            return i;
          });
          this.taskInfo.id = val.id;
          this.taskInfo.taskId = val.taskId;
        }
      },
    },
    options: {
      handler(val) {
        if (val) {
          this.options.forEach((item) => {
            item.childLabels.forEach((i) => {
              if (this.chooseLabel.map((v) => v.id).includes(i.id)) {
                i.active = true;
              }
            });
          });
        }
      },
    },
  },
  computed: {
    dialogTitle() {
      if (this.status == "detail") {
        return "任务详情";
      } else {
        return "任务反馈";
      }
    },
    isDisabled() {
      if (this.status == "detail") {
        return true;
      } else {
        return false;
      }
    },
  },
  methods: {
    cascadeLabel(id) {
      services.cascadeLabel({ id }).then((res) => {
        this.options = res.data.gridLabels.map((v) => {
          v.childLabels.forEach((i) => {
            i.active = false;
          });
          return v;
        });
      });
    },
    // 点击确定
    handleConfirm() {
      this.$refs.innerForm.validate((innerValid) => {
        if (innerValid) {
          const contents = this.fieldForm.map((item) => {
            // 上传图片
            if (item.fillingForm == 8) {
              let photos = this.fileList.map((item) => {
                return item.url;
              });
              item.detailContent = photos.length > 0 ? photos.join(",") : "";
            } else if (item.fillingForm == 9) {
              // 标签选择
              item.detailContent = this.chooseLabel.length
                ? this.chooseLabel.map((v) => v.id).join(",")
                : null;
            } else {
              item.detailContent = Array.isArray(item.detailContent)
                ? item.detailContent.join(",")
                : item.detailContent;
            }
            if (item.detailContent === "") {
              item.detailContent = null;
            }
            return item;
          });
          const data = {
            ...this.taskInfo,
            contents,
          };
          console.log("data", data);
          services.editTaskDetail(data).then((res) => {
            if (res.code === 200) {
              this.showDialog = false;
              this.$emit("fetchList");
            }
          });
        }
      });
    },
    handleClose() { },
    // 删除文件之前
    beforeRemove(file, fileList) {
      console.log(file, fileList);
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    // 传输成功
    fieldUpload(response) {
      const { url, fileName } = response
      this.fileList.push({ name: fileName, url });
    },
    // 文件列表移除文件时
    fieldRemove(file) {
      this.fileList = this.fileList.filter((item) => item.url != file.url);
      this.$message.success("图片删除成功");
    },
    childLabelClick(parent, child) {
      if (this.isDisabled) return;
      if (child.active) {
        child.active = false;
        const index = this.chooseLabel.findIndex((i) => i.id == child.id);
        this.chooseLabel.splice(index, 1);
      } else {
        if (parent.isMultiple == 1) {
          child.active = true;
          this.chooseLabel.push(child);
        } else {
          const isMultiple = this.chooseLabel.find(
            (i) => i.labelGroupId == child.labelGroupId
          );
          const index = this.chooseLabel.findIndex(
            (i) => i.labelGroupId == child.labelGroupId
          );
          if (isMultiple) {
            isMultiple.active = false;
            this.chooseLabel.splice(index, 1);
            child.active = true;
            this.chooseLabel.push(child);
          } else {
            child.active = true;
            this.chooseLabel.push(child);
          }
        }
      }
    },
  },
};
</script>

<style scoped lang="scss">
.grid {
  display: grid;
  grid-template-columns: 1fr 100px;
  align-items: start;
  grid-gap: 10px;
}
.customerInfo {
  padding: 10px 20px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  color: #999;
  .infoItem {
    display: grid;
    grid-template-columns: 70px 1fr;
    gap: 10px;
  }
}
.formItem {
  padding: 10px 20px;
  display: grid;
  grid-template-columns: 70px 1fr;
  gap: 10px;
  align-items: center;
  .formLabel {
    color: #606266;
    font-weight: 700;
  }
}
.netWorkBox {
  padding: 10px 0px;
  max-height: 300px;
  overflow-y: auto;

  .el-form {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-gap: 10px;
  }
}
.cascader {
  border: 1px solid #efefef;
  .cascader-scroll {
    height: 250px;
    overflow: scroll;
  }
}
.panel {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  .up {
    padding-left: 10px;
  }
  .item {
    width: fit-content;
    padding: 0 10px;
    border-radius: 8px;
    border: 1px solid #b3d8ff;
    color: #409eff;
    text-align: center;
    margin: 5px;
    &:nth-child(3n) {
      margin-right: 0;
    }
    .name {
      padding: 5px;
      font-size: 14px;
    }
    &.active {
      background-color: #5e81f4;
      color: #fff;
    }
  }
}
</style>
