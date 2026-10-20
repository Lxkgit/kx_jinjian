<!-- 主任务视图表单 -->
<template>
  <div class="task-form-value">
    <el-form ref="form" :model="form" label-width="110px">
      <el-form-item label="任务名称">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="活动时间">
        <el-date-picker
          v-model="form.B"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          format="yyyy-MM-dd"
        >
        </el-date-picker>
      </el-form-item>

      <el-form-item label="是否周期任务">
        <el-radio-group v-model="form.C">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="任务类型">
        <el-input v-model="form.D"></el-input>
      </el-form-item>

      <el-form-item label="客群类型">
        <el-input v-model="form.E"></el-input>
      </el-form-item>

      <el-form-item label="任务优先级">
        <el-input v-model="form.F"></el-input>
      </el-form-item>

      <el-row>
        <el-col :span="12">
          <el-form-item label="创建人">
            <el-input v-model="form.G"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="完成率">
            <el-input v-model="form.H"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="子任务数">
        <el-input v-model="form.I"></el-input>
      </el-form-item>

      <el-row>
        <el-col :span="12">
          <el-form-item label="进行中">
            <el-input v-model="form.J"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手动结束">
            <el-input v-model="form.K"></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12">
          <el-form-item label="已完成">
            <el-input v-model="form.L"></el-input>
          </el-form-item>
        </el-col>
        <!-- <el-col :span="12">
          <el-form-item label="手动结束">
            <el-input v-model="form.M"></el-input>
          </el-form-item>
        </el-col> -->
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "",
  props: { form: Object },
  data() {
    return {
      // labelValue: 1,
      // labelList: [
      //   {
      //     name: '聚宝盆',
      //     value: 1
      //   },
      //   {
      //     name: '钱多多',
      //     value: 2
      //   }
      // ],
      // form: {
      //   name: "4月新客走访任务",
      //   A: "系统下发",
      //   B: ["2022-04-04", "2022-04-30"],
      //   C: true,
      //   D: "新客走访",
      //   E: "1",
      //   F: "一级",
      //   G: "曹现义",
      //   H: "12%",
      //   I: "8",
      //   J: "3",
      //   K: "2",
      //   L: "3",
      //   M: "0",
      // },
    };
  },
};
</script>

<style scoped lang="scss">
.task-form-value {
  ::v-deep .el-form-item__label {
    font-size: 16px;
    color: #ffffff;
    font-weight: normal;
  }
  .el-form-item {
    margin-bottom: 40px;
  }
  ::v-deep .el-radio__label {
    color: white;
  }
  ::v-deep .el-radio__inner {
    border-color: #00f6ff;
    background-color: #00f6ff;
  }
  ::v-deep .el-date-editor {
    width: 100%;
  }
  ::v-deep .el-input--medium {
    width: 100%;
  }
  ::v-deep .el-select {
    width: 100%;
  }
  ::v-deep .el-input__inner {
    height: 36px !important;
    background-color: rgba($color: #031628, $alpha: 0.3);
    color: #00f6ff;
    border: 1px solid #16e1ff;
  }
  ::v-deep .el-range-input {
    color: #16e1ff;
    background-color: transparent;
  }
  ::v-deep .el-range-separator {
    color: rgba($color: #ffffff, $alpha: 0.4);
  }
}
</style>
