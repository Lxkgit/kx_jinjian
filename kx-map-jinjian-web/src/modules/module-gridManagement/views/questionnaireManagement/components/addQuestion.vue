<template>
  <PageContent>
    <h2>模版信息</h2>
    <i-form
      :gridColumns="2"
      ref="addForm"
      :columns="firstFormcolumns"
      label-width="150px"
      label-position="right"
    />
    <h2>模版内容</h2>
    <div>
      <el-button @click="addField" size="mini" type="primary"
        >添加模版</el-button
      >
    </div>
    <i-table
      :showPagination="false"
      :loading="loading"
      :data="tableData"
      stripe
      align="center"
      :max-height="maxHeight - 80"
      :allColumns="columns"
    >
      <template #fieldName="{ row }">
        <el-input
          :size="size"
          style="width: 100%"
          v-model="row.fieldName"
          placeholder="请输入字段名称"
        ></el-input>
      </template>
      <template #content="{ row }">
        <el-input
          v-if="['3', '4', '5'].includes(row.fillingForm)"
          :size="size"
          style="width: 100%"
          v-model="row.content"
          placeholder="请输入域值（例男,女）"
        ></el-input>
        <el-cascader
          v-if="row.fillingForm == '9' && row.isRequired == '1'"
          v-model="row.content"
          :size="size"
          style="width: 100%"
          :options="tagOptions"
          :props="tagProps"
          collapse-tags
          claerable
          :show-all-levels="false"
        ></el-cascader>
      </template>
      <template #isRequired="{ row }">
        <el-switch
          :size="size"
          v-model="row.isRequired"
          style="width: 100%"
          active-value="1"
          inactive-value="0"
        ></el-switch>
      </template>
      <template #fillingForm="{ row }">
        <div class="form-type">
          <el-select
            :size="size"
            v-model="row.fillingForm"
            @change="handleChange(row)"
            placeholder="请选择填写形式"
            style="width: 100%"
          >
            <el-option
              v-for="item in dict.type.filling_form"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
      </template>
      <template #action="scope">
        <el-button
          :size="size"
          type="danger"
          @click="
            makeSure('是否继续删除该字段?', () => {
              handleDel(scope.row);
            })
          "
        >
          删除
        </el-button>
      </template>
    </i-table>
    <div class="fotter">
      <el-button @click="goBack">取消</el-button>
      <el-button type="primary" @click="handleSave">确定</el-button>
    </div>
  </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin.vue";
import services from "../api/index";
import { mapGetters } from "vuex";

export default {
  mixins: [pageMixin],
  dicts: ["filling_form", "custom_kind"],
  data() {
    return {
      firstFormcolumns: [
        {
          label: "问卷名称",
          key: "taskTypeName",
          type: "text",
          required: true,
          rules: [
            {
              required: true,
              message: "请输入问卷名称",
              trigger: ["change", "blur"],
            },
          ],
        },
      ],
      columns: [
        { label: "字段名称", prop: "fieldName", slot: true },
        { label: "填写形式", prop: "fillingForm", slot: true, width: 200 },
        { label: "域值", prop: "content", slot: true },
        { label: "是否必填", prop: "isRequired", slot: true, width: 90 },
        { label: "操作", slot: true, width: 90 },
      ],
      tableData: [],
      size: "mini",
      status: "add",
      ableConfirm: true,
      taskObjectId: null,
    };
  },
  computed: {
    ...mapGetters("gridManagement", ["questionInfo"]),
  },
  mounted() {
    if (this.questionInfo.status == "edit") {
      services.taskTypeDetail({ id: this.questionInfo.id }).then((res) => {
        this.$refs.addForm.setForm(res.data);
        this.taskObjectId = res.data.taskObjectId;
        if (res.data.list) {
          let time = +new Date();
          let list = res.data.list.map((item) => {
            item.content = item.content !== "" ? item.content : "";
            if (item.fillingForm == "9" && item.isRequired == "1") {
              item.content = item.content.split(",");
            }
            item.frontKey = time;
            time += 1;
            return item;
          });
          this.tableData = list || [];
        }
      });
    }
  },
  methods: {
    addField() {
      this.tableData.push({ frontKey: +new Date(), content: "" });
    },
    // 填写形式选择
    handleChange(val) {
      val.content = "";
    },
    // 删除字段
    handleDel(row) {
      this.tableData = this.tableData.filter((item) => {
        return item.frontKey !== row.frontKey;
      });
    },
    handleSave() {
      this.$refs.addForm.getForm((formInline) => {
        for (const item of this.tableData) {
          if (
            item.fieldName == null ||
            item.fieldName == "" ||
            item.fillingForm == null
          ) {
            this.ableConfirm = false;
            break;
          }
          if (
            ["3", "4", "5"].includes(item.fillingForm) &&
            item.content == ""
          ) {
            this.ableConfirm = false;
            break;
          }
          if (item.fillingForm == "9" && item.isRequired == "1") {
            if (item.content == "") {
              this.ableConfirm = false;
              break;
            }
            item.content = item.content.join(",");
          }
        }
        if (!this.ableConfirm) {
          this.ableConfirm = true;
          this.$message.error("模板内容中存在字段值为空");
          return;
        }
        let data = {
          ...formInline,
          taskObjectId: "1",
          list: this.tableData,
          taskObject: this.taskObject,
          //   taskObjectId: this.taskObjectId,
        };
        console.log("data111", data);
        if (this.questionInfo.status == "add") {
          services.addTaskType(data).then((res) => {
            if (res.code == 200) {
              this.$message.success("新增成功！");
              this.$router.push("questionContent");
            }
          });
        }
        if (this.questionInfo.status == "edit") {
          services.editTaskType(data).then((res) => {
            if (res.code == 200) {
              this.$message.success("编辑成功！");
              this.$router.push("questionContent");
            }
          });
        }
      });
    },
    goBack() {
      this.$router.push("questionContent");
    },
  },
};
</script>

<style scoped lang="scss">
h2 {
  font-size: 18px;
  margin-bottom: 10px;
  font-weight: bold;
}
.fotter {
  text-align: center;
}
</style>