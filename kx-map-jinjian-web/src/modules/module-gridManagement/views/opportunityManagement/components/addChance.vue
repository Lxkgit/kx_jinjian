<template>
  <i-dialog
    :title="`商机${status2}`"
    v-model="showAdd"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <el-form
      :model="businessForm"
      ref="businessForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row>
        <!-- 商机名称 -->
        <el-col :span="12">
          <el-form-item label="商机名称" prop="businessName">
            <el-input
              :size="size"
              v-model="businessForm.businessName"
              autocomplete="off"
              placeholder="请输入商机名称"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 业务扩展方向 -->
        <el-col :span="12">
          <el-form-item label="扩展方向" prop="businessExtend">
            <el-select
              :size="size"
              class="form-select"
              v-model="businessForm.businessExtend"
              placeholder="请选择商机扩展方向"
            >
              <el-option
                v-for="item in businessExtend_list"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <!-- 商机结束时间 -->
        <el-col :span="12">
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              class="date-picker"
              :size="size"
              type="date"
              v-model="businessForm.endTime"
              placeholder="请选择商机结束时间"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 客户姓名 -->
        <el-col :span="12">
          <el-form-item label="客户姓名" prop="name">
            <el-autocomplete
              :size="size"
              class="el-autocomplete"
              v-model="businessForm.name"
              :fetch-suggestions="querySearchAsync"
              placeholder="请输入客户姓名"
              @select="handleSelect"
            ></el-autocomplete>
          </el-form-item>
        </el-col>
        <!-- 统一社会信用代码 -->
        <el-col :span="12">
          <el-form-item label="信用代码" prop="identity">
            <el-input
              :size="size"
              v-model="businessForm.identity"
              autocomplete="off"
              placeholder="请输入信用代码"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 执行机构 -->
        <el-col :span="12">
          <el-form-item label="执行结构" prop="executeNetworkCode">
            <el-cascader
              ref="myCascader"
              class="executeNetworkCode"
              :size="size"
              v-model="businessForm.executeNetworkCode"
              :options="netWorkOptions"
              :props="netWorkOptionsProp"
              @change="netWorkChange"
              collapse-tags
              clearable
              placeholder="请输入执行机构"
            ></el-cascader>
          </el-form-item>
        </el-col>
        <!-- 执行客户经理 -->
        <el-col :span="12">
          <el-form-item label="客户经理" prop="executeManagerName">
            <el-input
              :size="size"
              v-model="businessForm.executeManagerName"
              autocomplete="off"
              placeholder="请输入执行客户经理"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 备注 -->
        <el-col :span="12">
          <el-form-item label="备注" prop="remark">
            <el-input
              :size="size"
              v-model="businessForm.remark"
              type="textarea"
              placeholder="请输入备注"
              width="50%"
              :autosize="{ minRows: 2, maxRows: 4 }"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </i-dialog>
</template>

<script>
import { mapGetters } from "vuex";
import services from "../api/index.js";
export default {
  name: "addChance",
  props: {
    value: {
      type: Boolean,
      default: false,
      required: true,
    },
    status: {
      type: String,
      default: "add",
    },
    defaultValue: {
      type: Object,
      default: () => {},
    },
  },
  computed: {
    ...mapGetters(["name"]),
    status2() {
      if (this.status === "add") {
        return "新增";
      }
      if (this.status === "edit") {
        return "编辑";
      }
      if (this.status === "distribute") {
        return "分配";
      }
    },
  },
  data() {
    return {
      size: "mini",
      businessForm: {
        id: "",
        businessName: "", //商机名称
        businessExtend: "", //业务拓展方向
        startTime: "", //商机开始日期，默认为增加商机的当天
        updateTime: "", //商机更新时间
        endTime: "", //商机结束日期
        name: "", //客户名称
        identity: "", //统一社会信用代码
        executeNetworkName: "", //执行机构
        executeNetworkCode: "", //执行机构编号
        executeManagerName: "", //执行客户经理
        executeManagerCode: "", //执行机构客户经理编号
        commonStatus: "", //公共池状态 1-机构公共池 2-总行公共池 3-已分配
        remark: "", //备注
        createBy: "",
      },
      rules: {
        businessName: [
          {
            required: true,
            message: "请输入商机名称",
            trigger: ["change", "blur"],
          },
        ],
        businessExtend: [
          {
            required: true,
            message: "请选择业务扩展名称",
            trigger: ["change", "blur"],
          },
        ],
        endTime: [
          {
            required: true,
            message: "请选择商机结束日期",
            trigger: ["change", "blur"],
          },
        ],
        name: [
          {
            required: true,
            message: "请输入客户名称",
            trigger: ["change", "blur"],
          },
        ],
      },
      businessExtend_list: [
        {
          value: "0",
          label: "潜客拓展",
        },
        {
          value: "1",
          label: "信贷提升",
        },
        {
          value: "2",
          label: "存量活客",
        },
      ],
      formLabelWidth: "100px",
      showAdd: false,
      restaurants: [],
      netWorkOptions: [
        // 一级
        { id: 1, label: 1 },
        // 二级
        {
          id: 2,
          label: 2,
          children: [
            { id: 21, label: 21 },
            { id: 22, label: 22 },
          ],
        },
        // 三级
        {
          id: 3,
          label: 3,
          children: [
            {
              id: 31,
              label: 31,
              children: [
                { id: 311, label: 311 },
                { id: 312, label: 312 },
              ],
            },
            {
              id: 32,
              label: 32,
              children: [
                { id: 321, label: 321 },
                { id: 322, label: 322 },
              ],
            },
          ],
        },
        // 四级
        {
          id: 4,
          label: 4,
          children: [
            {
              id: 41,
              label: 41,
              children: [
                {
                  id: 411,
                  label: 411,
                  children: [
                    { id: 4111, label: 4111 },
                    { id: 4112, label: 4112 },
                  ],
                },
                {
                  id: 412,
                  label: 412,
                  children: [
                    { id: 4121, label: 4121 },
                    { id: 4122, label: 4122 },
                  ],
                },
              ],
            },
            {
              id: 42,
              label: 42,
              children: [
                {
                  id: 421,
                  label: 421,
                  children: [
                    { id: 4211, label: 4211 },
                    { id: 4212, label: 4212, children: [] },
                  ],
                },
                {
                  id: 422,
                  label: 422,
                  children: [
                    { id: 4221, label: 4221 },
                    { id: 4222, label: 4222 },
                  ],
                },
              ],
            },
          ],
        },
      ],
      netWorkOptionsProp: {
        label: "label",
        value: "id",
        children: "children",
        //在选中节点改变时，是否返回由该节点所在的各级菜单的值所组成的数组，若设置 false，则只返回该节点的值
        emitPath: false,
        multiple: false, //是否多选
      },
    };
  },
  watch: {
    value: {
      handler(val) {
        this.showAdd = val;
      },
      immediate: true,
    },
    showAdd: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    defaultValue: {
      handler(val) {
        if (val) {
          this.businessForm = val;
        }
        if (this.status == "add") {
          this.businessForm.createBy = this.name;
        }
      },
    },
  },
  methods: {
    // 弹窗确认按钮
    handleConfirm() {
      this.$refs.businessForm.validate((valid) => {
        if (valid) {
          this.showAdd = false;
          // 修改商机的公共池状态
          this.changeCommonStatus();
          if (this.status === "add") {
            this.businessForm.startTime = this.getTime();
          }
          this.businessForm.updateTime = this.getTime();
          console.log(this.businessForm);
          let data = { ...this.businessForm };
          // services
          //   .opportunitySave(data)
          //   .then((res) => {
          //     console.log("res", res);
          //     if (res.code === 200) {
          //       this.$message.success("新增成功！");
          //       this.showAdd = false;
          //       this.$emit("fetchList");
          //     }
          //   })
          //   .catch((err) => {});
        }
      });
    },
    // 关闭弹窗
    handleClose() {
      this.$refs.businessForm.resetFields();
    },
    // 获取客户姓名输入框的下拉数据
    loadAll() {
      return [
        {
          value: "李冰",
          identity: "333",
        },
        {
          value: "李四",
          identity: "444",
        },
        {
          value: "王五",
          identity: "555",
        },
        {
          value: "赵六",
          identity: "666",
        },
        {
          value: "李冰冰",
          identity: "333444",
        },
        {
          value: "王五赵六",
          identity: "555666",
        },
      ];
    },
    // 点击客户姓名输入框中的下拉数据
    handleSelect(item) {
      console.log(item);
      this.businessForm.identity = item.identity;
    },
    // 执行机构变化时
    netWorkChange(value) {
      let node = this.$refs.myCascader.getCheckedNodes()[0];
      console.log("node", node);
      if (node) {
        const fullName = node.pathLabels.join("");
        this.businessForm.executeNetworkName = fullName;
      }
    },
    // 修改公共池状态
    changeCommonStatus() {
      //若为新增商机，没有选择执行机构，公共池状态为2(总行公共池)
      if (this.status == "add") {
        this.businessForm.commonStatus = "2";
      }
      // 选择的有执行机构，公共池状态为1(机构公共池)
      if (this.businessForm.executeNetworkCode) {
        this.businessForm.commonStatus = "1";
      }
      // 若为商机分配，公共池状态为3(已分配)
      if (this.status == "distribute") {
        this.businessForm.commonStatus = "3";
      }
    },
    getTime() {
      let date = new Date();
      let year = date.getFullYear();
      let month =
        date.getMonth() + 1 < 10
          ? "0" + (date.getMonth() + 1)
          : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let hours =
        date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
      let minutes =
        date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
      let seconds =
        date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
      return (
        year +
        "-" +
        month +
        "-" +
        day +
        " " +
        hours +
        ":" +
        minutes +
        ":" +
        seconds
      );
    },
    //客户姓名输入框获取焦点时调用的方法
    querySearchAsync(queryString, cb) {
      var restaurants = this.restaurants;
      var results = queryString
        ? restaurants.filter(this.createStateFilter(queryString))
        : restaurants;
      cb(results);
    },
    createStateFilter(queryString) {
      return (item) => {
        // 调用的是indexOf，是精确匹配
        // return item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0;
        // 调用match方法实现模糊匹配
        return item.value.toUpperCase().match(queryString.toUpperCase());
      };
    },
  },
  mounted() {
    this.restaurants = this.loadAll();
  },
};
</script>

<style scoped lang="scss">
.form-select,
.date-picker,
.el-autocomplete,
.executeNetworkCode {
  width: 100% !important;
}
</style>