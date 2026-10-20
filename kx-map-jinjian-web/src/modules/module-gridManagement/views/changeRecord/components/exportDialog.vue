<template>
  <i-dialog
    title="导出范围选择"
    v-model="showDialog"
    width="48%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <el-form
      :model="addForm"
      ref="addForm"
      :rules="rules"
    >
      <div class="form-row">
        <el-form-item label="导出数据范围的页码" prop="exportPageStart" :label-width="formLabelWidth">
          <el-input
            :size="size"
            v-model="addForm.exportPageStart"
            placeholder="请输入起始页码"
          />
        </el-form-item>
        <span>-</span>
        <el-form-item prop="exportPageEnd" label-width="0">
          <el-input
            :size="size"
            v-model="addForm.exportPageEnd"
            placeholder="请输入结束页码"
          />
        </el-form-item>
      </div>
      <div class="tip" v-if="showTips">
        提示：页码范围内数据量超过系统导出限制（上限10000条）
      </div>
    </el-form>
  </i-dialog>
</template>

<script>
import { formatDate } from '../../../../../utils';
import services from "../api/index";
const TYPE_NAME = {
  0: "辖区变更记录",
  1: "网格员变更记录",
  2: "锚点变更记录",
};
export default {
  name: "",
  props: {
    value: { type: Boolean, default: false, required: true },
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
    categoryId: {
      type: Number,
    },
  },
  data() {
    return {
      size: "mini",
      showTips:false,
      showDialog:false,
      formLabelWidth: "160px",
      addForm:{
        exportPageStart:'',
        exportPageEnd:'',
      },
      rules:{
        exportPageStart:[
          {required:true,message:'请输入起始页码',trigger:['change','blur']},
          {pattern:/^[0-9]*$/,message:'请输入正确的页码',trigger:'change'}
        ],
        exportPageEnd:[
          {required:true,message:'请输入结束页码',trigger:['change','blur']},
          {pattern:/^[0-9]*$/,message:'请输入正确的页码',trigger:'change'}
        ]
      },
      total:0,
      pageSize:10,
      pageNums:0,
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
         this.total=val.total
         this.pageSize=val.pageSize
         this.pageNums=Math.ceil(val.total / val.pageSize)
        }
      },
    },
    'addForm.exportPageEnd':{
      handler(val) {
        if (val) { 
          if (val < 1) {
            this.addForm.exportPageEnd = 1
          }
          if(val > this.pageNums) {
            this.addForm.exportPageEnd = this.pageNums
          }
          if(this.addForm.exportPageStart && this.addForm.exportPageStart > val) {
            this.addForm.exportPageStart = val
          }
          this.showTips = false
        }
      }
    },
    'addForm.exportPageStart':{
      handler(val){
        if (val) {
          if (val < 1) {
            this.addForm.exportPageStart = 1
          }
          if(this.addForm.exportPageEnd && this.addForm.exportPageEnd < val) {
            this.addForm.exportPageStart = this.addForm.exportPageEnd
          }
          this.showTips = false
        }
      }
    }
  },
  methods: {
    // 新增编辑 提交表单
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          if ((this.addForm.exportPageEnd - this.addForm.exportPageStart) * this.pageSize > 10000) {
            this.showTips = true
            return
          }
          if (this.defaultValue.date) {
            this.defaultValue.startTime = formatDate(this.defaultValue.date[0], true),
            this.defaultValue.endTime = formatDate(this.defaultValue.date[1], true)
            delete this.defaultValue.date
          }
          const data ={
            ...this.defaultValue,
            exportPageStart:this.addForm.exportPageStart * 1,
            exportPageEnd:this.addForm.exportPageEnd * 1,
            logType:this.categoryId,
          }
          if (this.categoryId == 2) {
            // 锚点变更
            this.download(
              services.paChangeLogExportAnchor(),
              data,
              `${TYPE_NAME[this.categoryId]}_${new Date().getTime()}.xlsx`
            );
          } else {
            // 网格、辖区变更
            this.download(
              services.paChangeLogExport(),
              data,
              `${TYPE_NAME[this.categoryId]}_${new Date().getTime()}.xlsx`
            );
          }
        }
      });
    },
    //关闭弹窗
    handleClose() {
      this.$refs.addForm.resetFields()
    },
  },
};
</script>

<style scoped lang="scss">
.form-row{
  display: flex;
  span{
    display: inline-block;
    line-height: 36px;
    margin: 0 10px;
  }
}
.tip{
  width: 100%;
  padding: 0 28px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  color: red;
  font-size: 12px;
}
</style>
