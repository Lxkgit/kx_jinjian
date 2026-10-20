<template>
  <i-dialog title="预览表单" v-model="showDialog" width="50%" @handleClose="handleClose"
            :showConfirm="false">
    <el-steps :active="activeStep" :align-center="true" class="step-box">
      <el-step title="第一步: 基础建档要素"></el-step>
      <el-step title="第二步: 有效建档要素"></el-step>
    </el-steps>
    <customerSource v-if="activeStep===0"></customerSource>
    <i-form v-if="columns.length>0" ref="myForm" :columns="columns" label-position="right"
            label-width="140px">
    </i-form>
    <template #foot>
      <span v-if="activeStep===0" class="handle-wrapper">
        <el-button type="primary" @click="nextHandle">下一步</el-button>
      </span>
      <span v-else class="handle-wrapper">
        <el-button @click="backHandle">上一步</el-button>
      </span>
    </template>
  </i-dialog>
</template>

<script>
import service from '../api';
import dialogMixin from '@/mixins/dialogMixin';
import fileConversionMixin from '@/mixins/fileConversionMixin';
export default ({
  mixins: [dialogMixin, fileConversionMixin],
  props: {
  },
  data() {
    return {
      columns: [],
      activeStep: 0,
    }
  },
  computed: {
    modelId() {
      return this.$store.getters['gridManagement/modelId']
    },
  },
  methods: {
    initData() {
      this.activeStep = 0
      this.getFilingModelInfoById()
    },
    handleClose() {
      this.columns = []
    },
    nextHandle() {
      this.activeStep = 1
      this.getFilingModelInfoById()
    },
    backHandle() {
      this.activeStep = 0
      this.getFilingModelInfoById()
    },
    getFilingModelInfoById() {
      const param = {
        id: this.modelId,
        isFinishParam: this.activeStep
      }
      service.getFilingModelInfoById(param).then(res => {
        const { data: { fields } } = res
        this.columns = []
        this.$nextTick(() => {
          this.columns = this.dataConversion(fields)
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
.step-box {
  margin-bottom: 14px;
}
.handle-wrapper {
  margin-left: 10px;
}
</style>