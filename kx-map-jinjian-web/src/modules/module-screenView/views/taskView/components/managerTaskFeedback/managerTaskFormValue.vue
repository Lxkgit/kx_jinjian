<template>
  <div class="manager-task-form-value">
    <el-form ref="form" :model="form" label-width="110px">
      <el-form-item label="任务方式">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="打卡签到">
        <el-input v-model="form.A"></el-input>
      </el-form-item>
      <el-form-item label="任务反馈">
        <el-input v-model="form.B"></el-input>
      </el-form-item>

      <el-form-item label="客户是否锚点">
        <el-radio-group v-model="form.C">
          <el-radio :label="true">是</el-radio>
          <el-radio :label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="标签1">
        <choose-label backgroundColor="rgba(81,255,195, .3)" :labelList="labelList1" single v-model="label1Value"></choose-label>
      </el-form-item>

      <el-form-item label="标签2">
        <choose-label backgroundColor="rgba(81,255,195, .3)" :labelList="labelList2" :single="false" v-model="label2Value"></choose-label>
      </el-form-item>

      <el-form-item label="走访反馈">
        <el-input v-model="form.F"></el-input>
      </el-form-item>
    </el-form>
    <div class="button-list">
      <div class="cancel btn" @click="cancel">取消</div>
      <div class="submit btn" @click="submit">提交信息</div>
    </div>
  </div>
</template>

<script>
// import chooseLabel from "../chooseLabel";
export default {
  name: "",
  props: {},
  components: {
    // chooseLabel,
  },
  data() {
    return {
      label1Value: 1,
      label2Value: ['1','2'],
      labelList1: [
        {
          name: '聚宝盆',
          value: 1
        },
        {
          name: '钱多多',
          value: 2
        }
      ],
      labelList2 :[
        {
          name: '满额用信1',
          value: '1'
        },
        {
          name: '钱多多',
          value: '2'
        },
        {
          name: '满额用信2',
          value: '3'
        },
        {
          name: '满额用信3',
          value: '4'
        },
      ],
      form: {
        name: "邵桂兰—4月新客走访任务",
        A: "现场签到",
        B: "已走访",
        C: true,
        D: "asd",
        E: "已沟通",
        F: "已走访沟通",
        G: [
          "https://img2.baidu.com/it/u=910465597,2855693683&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500",
          "https://img1.baidu.com/it/u=1617167232,326386947&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500",
        ],
      },
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    cancel(){
      this.$emit('close')
    },
    submit() {
      this.$emit('close')
    }
  },
};
</script> 

<style scoped lang='scss'>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.manager-task-form-value {
  ::v-deep .el-form-item__label {
    font-size: 16px;
    color: #ffffff;
    font-weight: normal;
  }
  .el-form-item {
    margin-bottom: 22px;
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
  .button-list {
    padding-top: 20px;
    @include flex;
    .cancel {
      color: rgba($color: #ffffff, $alpha: 0.6);
      border: 1px solid #175e6b;
    }
    .submit {
      background-color: #00aeff;
      color: white;
      margin-left: 20px;
    }
    .btn {
      width: 120px;
      height: 36px;
      @include flex;
      border-radius: 20px;
      cursor: pointer;
    }
  }
}
</style>
