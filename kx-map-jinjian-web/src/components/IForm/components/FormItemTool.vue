<template>
  <div class="form-item-tool">
    <!-- 文本 -->
    <el-input ref="inputRef" v-if="inputTypes.includes(info.type)" v-model="value[info.key]"
              :type="info.type" :placeholder="info.placeholder || `请输入${info.label}`"
              :rows="info.rows || 4" :disabled="getDisabled(info.disabled)"
              :maxlength="info.maxlength" :readonly="info.readonly" :clearable="info.clearable"
              :show-word-limit="info.showWordLimit" />
    <slot v-if="inputTypes.includes(info.type) && info.slot" :name="info.key"></slot>
    <slot v-if="inputTypes.includes(info.type) && info.tip" name="tip"></slot>
    <!-- 数字框 -->
    <el-input-number v-if="info.type === 'number'" v-model="value[info.key]"
                     :placeholder="info.placeholder || `请输入${info.label}`"
                     :precision="(info.precision || 0)" :controls="false"
                     :disabled="getDisabled(info.disabled)" :readonly="info.readonly"
                     :style="{width: `${info.width||100}%`}" />
    <!-- 带输入建议 -->
    <el-autocomplete v-if="info.type === 'autocomplete'" v-model="value[info.key]"
                     :placeholder="info.placeholder || `请输入${info.label}`"
                     :disabled="getDisabled(info.disabled)" :readonly="info.readonly"
                     :style="{width: `${info.width||100}%`}" :fetch-suggestions="info.querySearch"
                     :value-key="info.valueKey" :trigger-on-focus="info.triggerOnFocus"
                     @select="info.events && info.events.select ? eventFun({type:'select', key: info.key, val: $event}) : null" />
    <!-- 单选 -->
    <el-radio-group v-if="info.type === 'radio'" v-model="value[info.key]"
                    :disabled="getDisabled(info.disabled)"
                    @change="info.events && info.events.change ? eventFun({type:'change', key: info.key, val: $event}) : null">
      <el-radio v-for="item in info.options" :key="item.value" :label="item.value">
        {{ item.label }}
      </el-radio>
    </el-radio-group>
    <!-- 多选 -->
    <el-checkbox-group v-if="info.type === 'checkbox'" v-model="value[info.key]" :min="info.min"
                       :max="info.max" :disabled="getDisabled(info.disabled)"
                       @change="info.events && info.events.change ? eventFun({type:'change', key: info.key, val: $event}) : null">
      <el-checkbox v-for="item in info.options" :key="item.value" :label="item.value">
        {{ item.label }}
      </el-checkbox>
    </el-checkbox-group>
    <!-- 开关 -->
    <el-switch v-if="info.type === 'switch'" v-model="value[info.key]"
               :active-color="info.activeColor || '#409EFF'"
               :inactive-color="info.inactiveColor || '#C0CCDA'" :active-value="info.activeValue"
               :inactive-value="info.inactiveValue" :disabled="getDisabled(info.disabled)"
               @change="info.events && info.events.change ? eventFun({type:'change', key: info.key, val: $event}) : null" />
    <!-- 评分 -->
    <el-rate v-if="info.type === 'rate'" v-model="value[info.key]"
             :disabled="getDisabled(info.disabled)" />
    <!-- 下拉框 -->
    <el-select v-if="info.type === 'select'" v-model="value[info.key]" class="filter-item"
               :multiple="info.multiple || false" :collapse-tags="info.collapseTags || false"
               :multiple-limit="info.multipleLimit || 0" :filterable="info.filterable || false"
               :placeholder="info.placeholder || `请选择${info.label}`"
               :disabled="getDisabled(info.disabled)" :clearable="info.clearable||false"
               :style="{width: `${info.width||100}%`}"
               @change="info.events && info.events.change ? eventFun({type:'change', key: info.key, val: $event}) : null">
      <el-option v-for="item in (typeof info.options === 'function'?[]:info.options)"
                 :key="item.value" :label="item.label" :value="item.value" />
    </el-select>

    <!-- 级联选择器 -->
    <el-cascader v-if="info.type === 'cascader'" v-model="value[info.key]" class="filter-item"
                 :filterable="info.filterable"
                 :disabled="getDisabled(info.disabled)"
                 :options="typeof info.options === 'function'?[]:info.options" :props="info.props"
                 :placeholder="info.placeholder || `请选择${info.label}`"
                 @change="info.events && info.events.change ? eventFun({type:'change', key: info.key, val: $event}) : null" />

    <!-- 日期选择器 -->
    <el-date-picker v-if="datePickerTypes.includes(info.type)" v-model="value[info.key]"
                    class="filter-item" :type="info.type" :format="info.format || 'yyyy-MM-dd'"
                    :value-format="info.valueFormat || 'yyyy-MM-dd'"
                    :disabled="getDisabled(info.disabled)" :readonly="info.readonly"
                    :clearable="info.clearable" :unlink-panels="info.unlinkPanels || false"
                    :picker-options="info.pickerOptions || {}"
                    :style="{width: `${info.width||100}%`}"
                    :start-placeholder="info.startPlaceholder" :end-placeholder="info.endPlaceholder"
                    :placeholder="info.placeholder || `请选择${info.label}`" />
    <!-- 树形框 -->
    <tree-select v-if="info.type === 'treeselect'" v-model="value[info.key]" class="filter-item"
                 :options="typeof info.options === 'function'?[]:info.options"
                 :placeholder="info.placeholder || `请选择${info.label}`" :normalizer="info.normalizer"
                 :multiple="info.multiple" :disabled="getDisabled(info.disabled)"
                 @select="info.events && info.events.select ? eventFun({type:'select', key: info.key, val: $event}) : null" />

    <!-- 图片上传 -->
    <img-upload :disabled="getDisabled(info.disabled)" v-if="info.type === 'imgupload'"
                v-model="value[info.key]"></img-upload>

    <!-- 打卡定位 -->
    <ClockIn v-if="info.type === 'checkin'" v-model="value[info.key]"></ClockIn>

    <!-- 标签选择 -->
    <label-select :disabled="getDisabled(info.disabled)" v-if="info.type === 'labelselect'"
                  v-model="value[info.key]" :tagType="info.tagType" :tagOptions="info.options"
                  :isMust="info.isMust">
    </label-select>

  </div>
</template>

<script>
import TreeSelect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import LabelSelect from '../cell/LabelSelect'
import ImgUpload from '../cell/ImgUpload'
import ClockIn from '../cell/ClockIn'
export default ({
  components: {
    TreeSelect,
    LabelSelect,
    ImgUpload,
    ClockIn
  },
  props: {
    value: {
      type: [String, Number, Object, Array, Boolean, Date],
      default: null,
    },
    info: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      // itemValue: null,
      inputTypes: ['text', 'textarea'],
      datePickerTypes: ['year', 'month', 'date', 'dates', 'months', 'years', 'week', 'datetime', 'datetimerange', 'daterange', 'monthrange'],
    }
  },
  async created() {
    // 数据字典直接提取值
    if (typeof this.info.options === 'function') {
      const res = await this.info.options.call(this)
      this.info.options = res
    }
  },
  methods: {
    /*
    * 事件回传
    * { type: 事件类型 key:属性名称 val:属性值 }
    */
    eventFun(obj) {
      this.$emit('eventFun', obj)
    },
    getDisabled(disabled) {
      if (typeof disabled === 'function') {
        return disabled.call()
      } else {
        return disabled
      }
    },
  },
})
</script>

<style lang="scss" scoped>
.filter-item {
  width: 100%;
}
</style>