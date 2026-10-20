<template>
  <PageContent>
    <div class="content-title">模版信息</div>
    <!-- 过滤器 -->
    <i-form ref="baseForm" :columns="searchColumns" :gridColumns="3">
      <template #indate>
        <div class="indate-wrapper">
          <el-input-number :controls="false" placeholder="请输入数字"></el-input-number>
          <el-select>
            <el-option v-for="item in dateOptions" :key="item.value" :label="item.label"
                       :value="item.value"></el-option>
          </el-select>
        </div>
      </template>
    </i-form>
    <div class="content-title">模版内容</div>
    <!-- 按钮 -->
    <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
    </i-button-bar>
    <!-- 表单编辑 -->
    <el-form class="table-form" ref="tableForm" :rules="rules" :model="baseForm">
      <el-table :height="height" :data="baseForm.fields" style="width: 100%">
        <el-table-column label="字段名称">
          <template slot-scope="scope">
            <el-form-item :prop="'fields.' + scope.$index + '.chineseName'"
                          :rules="rules.chineseName">
              <el-input v-model="scope.row.chineseName" placeholder="请输入字段名称"></el-input>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="填写形式">
          <template slot-scope="scope">
            <el-form-item :prop="'fields.' + scope.$index + '.fieldType'" :rules="rules.fieldType">
              <el-select v-model="scope.row.fieldType" placeholder="请选择填写形式">
                <el-option v-for="item in formTypeOptions" :key="item.value" :label="item.label"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="域值">
          <template slot-scope="scope">
            <el-form-item v-if="['3','4','5'].includes(scope.row.fieldType)">
              <el-input v-model="scope.row.content" placeholder="请填写域值(例:男,女)"></el-input>
            </el-form-item>
            <el-form-item v-if="['10'].includes(scope.row.fieldType)&&scope.row.isMust=='1'">
              <el-cascader v-model="scope.row.content" :options="tagOptions" :props="tagProps"
                           collapse-tags clearable :show-all-levels="false"></el-cascader>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="是否作为必填">
          <template slot-scope="scope">
            <el-form-item>
              <el-switch v-model="scope.row.isMust" active-value="1" inactive-value="0">
              </el-switch>
            </el-form-item>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-form-item>
              <el-button size="mini" type="danger" @click="deleteItem(scope.$index, scope.row)">删除
              </el-button>
            </el-form-item>
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </PageContent>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
export default ({
  mixins: [pageMixin],
  data() {
    return {
      searchColumns: [
        {
          label: '商机类型',
          key: 'modelType',
          type: 'text',
          disabled: true,
        },
        {
          label: '商机概览',
          key: 'modelType',
          type: 'text',
          disabled: true,
        },
        {
          label: '商机有效期',
          type: 'slot',
          slotName: 'indate'
        }
      ],
      dateOptions: [
        { label: '日', value: '1' },
        { label: '月', value: '2' },
      ],
      rules: {},
      baseForm: {
        fields: [],
      },
      btnLists: [
        {
          cname: '添加字段',
          ename: 'addItem',
          type: 'primary',
        },
      ],
      formTypeOptions: [
        { label: '文本', value: '1' },
        { label: '文本域', value: '2' },
        { label: '单选', value: '3' },
        { label: '多选', value: '4' },
        { label: '下拉列表', value: '5' },
        { label: '日期', value: '6' },
        { label: '开关', value: '8' },
        { label: '图片', value: '9' },
        { label: '标签选择', value: '10' },
        { label: '打卡', value: '11' },
      ],
      height: window.innerHeight - 420,
    }
  },
  methods: {
    addItem() {
      this.baseForm.fields.push({
        chineseName: "",
        fieldType: "",
        content: null,
        isMust: '0',
      });
    },
    deleteItem(index, row) {
      this.baseForm.fields.splice(index, 1);
    },
  },
})
</script>

<style lang="scss" scoped>
.content-title {
  font-size: 20px;
  margin: 10px 0;
}
.indate-wrapper {
  display: flex;
}
</style>