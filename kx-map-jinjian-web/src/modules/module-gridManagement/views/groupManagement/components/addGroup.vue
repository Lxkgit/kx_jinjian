<template>
  <i-dialog :title="title" v-model="showDialog" width="60%" @handleClose="handleClose"
            @handleConfirm="handleConfirm" :showFooter="true">
    <el-form :model="groupInfo" ref="groupInfo" :rules="rules" :label-width="formLabelWidth">
      <!-- 客群名称 -->
      <el-row>
        <el-col :span="16">
          <el-form-item label="网点分类名称" prop="customGroupName">
            <el-input :maxlength="50" :size="size" v-model="groupInfo.customGroupName"
                      autocomplete="off" placeholder="请输入网点分类名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="16">
          <el-form-item label="说明" prop="groupDesc">
            <el-input :maxlength="200" showWordLimit :size="size" type="textarea" :rows="4"
                      v-model="groupInfo.groupDesc" autocomplete="off"
                      placeholder="请输入说明"></el-input>
          </el-form-item>
        </el-col>
        <div v-if="
            (status === 'edit' && groupInfo.customGroupSource === '标签圈选') ||
            status === 'add'
          ">
          <el-col :span="16">
            <el-form-item label="标签圈群" prop="labelIds">
              <el-cascader v-model="groupInfo.labelIds" placeholder="请选择标签圈群" :options="tagOptions"
                           :props="tagProps" :show-all-levels="false" collapse-tags clearable>
              </el-cascader>
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="标签关联关系" prop="updateFlag">
              <el-radio-group v-model="groupInfo.updateFlag">
                <el-radio :label="1">且</el-radio>
                <el-radio :label="0">或</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </div>
      </el-row>
    </el-form>
  </i-dialog>
</template>

<script>
// import { mapGetters } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
import service from "../api";
export default {
  name: "addGroup",
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
    },
    categoryId: {
      type: Number,
    },
    status: {
      type: String,
    },
  },
  data() {
    return {
      size: "mini",
      formLabelWidth: "150px",
      groupInfo: {
        customType: null,
        customGroupName: "",
        groupDesc: "",
        labelIds: [], //客群的标签
        updateFlag: 1,
      },
      rules: {
        customGroupName: [
          {
            required: true,
            message: "请输入客群名称",
            trigger: ['change', 'blur']
          },
        ],
        labelIds: [
          {
            required: true,
            message: "请选择标签圈群",
            type: "array",
            trigger: ['change', 'blur']
          },
        ],
        updateFlag: [
          { required: true, message: "请选择标签关联关系", trigger: 'change' }
        ]
      },
      tagOptions: [],
      tagProps: {
        label: "name",
        value: "id",
        children: "child",
        emitPath: false,
        multiple: true, //是否多选
      },
    };
  },
  computed: {
    // ...mapGetters(["name"]),
    title() {
      if (this.status === "add") {
        return "新增";
      }
      if (this.status === "edit") {
        return "编辑";
      }
    },
  },
  mounted() {
    this.getCascadeLabel();
  },
  methods: {
    initData() {
      if (this.status === "add") {
        return;
      }
      this.groupInfo = JSON.parse(JSON.stringify(this.defaultValue));
      console.log("groupInfo", this.groupInfo);
    },
    // 关闭弹窗
    handleClose() {
      this.groupInfo = {
        customGroupName: "",
        groupDesc: "",
        labelIds: [], //客群的标签
        updateFlag: 1,
      };
      this.$refs.groupInfo.clearValidate();
      this.$refs.groupInfo.resetFields();
      this.showDialog = false;
    },
    // 弹窗确认按钮
    handleConfirm() {
      this.$refs.groupInfo.validate((valid) => {
        if (valid) {
          let data = {
            ...this.groupInfo,
            customType: this.categoryId,
            categoryId: this.categoryId,
          };
          service.addOrEditGroup(data).then((res) => {
            this.$message.success(`${this.title}成功！`);
            this.$parent.fetchList();
            this.handleClose();
          });
        }
      });
    },
    // 获取标签圈群
    getCascadeLabel() {
      service.cascadeLabel({ id: this.categoryId }).then((res) => {
        console.log("res", res.data);
        // 0是网格，1是系统
        res.data.forEach(item=>{
          item.child.forEach(val=>{
            val.name = val.type=='0'?val.name+'(网格标签)':(val.type=='1'?val.name+'(系统标签)':val.name)
          })
        })
        this.tagOptions = res.data;
      });
    },
  },
};
</script>

<style>
</style>
