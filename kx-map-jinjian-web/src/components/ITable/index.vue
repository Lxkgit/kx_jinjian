<template>
  <div class="table-wrapper" :class="{ showBorder: showBorder }">
    <!-- 列表 -->
    <el-table
      ref="elTable"
      v-loading="loading"
      :data="data"
      :border="border"
      :size="size"
      :stripe="stripe"
      :height="maxHeight"
      :empty-text="emptyText"
      tooltip-effect="dark"
      style="width: 100%"
      :row-key="rowKey"
      :default-expand-all="defaultExpandAll"
      :show-summary="showSummary"
      :summary-method="summaryMethod"
      :span-method="spanMethod"
      :tree-props="treeProps"
      :row-style="rowStyle"
      @selection-change="selectionChange"
      :header-cell-style="{ backgroundColor: '#F6F7FA' }"
      @row-click="rowClick"
      @sort-change="sortChange"
    >
      <el-table-column
        v-if="selection"
        type="selection"
        width="55"
        :selectable="singleChoice ? singleSelectable : selectable"
        :align="align"
      />
      <el-table-column
        v-if="showIndex"
        type="index"
        label="序号"
        width="50"
        :align="align"
      >
        <template slot-scope="scope">
          {{ (pageNum - 1) * pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column v-if="expand" type="expand">
        <template slot-scope="scope">
          <slot :name="expandName" :row="scope.row" :index="scope.$index" />
        </template>
      </el-table-column>
      <el-table-column
        v-for="item in columns"
        :key="item.prop"
        :prop="item.prop"
        :sortable="item.sortable || false"
        :label="item.label"
        :width="item.width || 'auto'"
        :min-width="item.minWidth || 'auto'"
        :fixed="item.fixed"
        :formatter="item.formatter"
        :render-header="item.renderHeader"
        :show-overflow-tooltip="item.showOverFlowTooltip"
        :align="align"
      >
        <template v-if="item.slot || item.renderColor" #default="scope">
          <slot
            v-if="item.slot"
            :name="item.prop || 'action'"
            :row="scope.row"
            :index="scope.$index"
          />
          <!-- 文字展示 带颜色渲染 -->
          <template v-if="item.renderColor">
            <span
              class="render-color has-tips"
              :style="rederColor(scope.row[item.colorType], 'color')"
            >
              <i
                v-if="item.colorTips"
                :style="rederColor(scope.row[item.colorType], 'background')"
              ></i>
              {{ scope.row[item.prop] }}
            </span>
          </template>
        </template>
      </el-table-column>
      <slot slot="empty" name="empty"></slot>
    </el-table>
    <!-- 筛选 -->
    <div class="select-check-wrapper" v-if="ifFilter">
      <el-dropdown :hide-on-click="false" trigger="click">
        <span>
          <i class="el-icon-s-grid" /><i class="el-icon-caret-bottom" />
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <el-checkbox-group
              v-model="checkedColumn"
              @change="checkChangeHandle"
            >
              <div v-for="item in allColumns" :key="item.prop">
                <el-checkbox :label="item.prop">
                  {{ item.label }}
                </el-checkbox>
              </div>
            </el-checkbox-group>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <!-- 分页器 -->
    <el-pagination
      v-if="showPagination"
      class="table-pagination"
      background
      :total="total"
      :current-page="pageNum"
      :page-size="pageSize"
      :page-sizes="[10, 20, 50, 100]"
      :layout="pageLayout"
      @current-change="currentChange"
      @size-change="sizeChange"
    />
  </div>
</template>

<script>
import variables from "@/assets/styles/variables.scss";
export default {
  name: "ITable",
  props: {
    value: { type: Array, default: () => [] },
    loading: { type: Boolean, default: false }, // 加载状态
    data: { type: Array, default: () => [] }, // 数据源
    allColumns: { type: Array, default: () => [] }, //配置项
    border: { type: Boolean }, // 是否带有纵向边框
    size: { type: String, default: "medium" }, // 尺寸
    stripe: { type: Boolean, default: true }, // 显示斑马线
    maxHeight: { type: [String, Number], default: null }, //表格高度
    emptyText: { type: String, default: "暂无数据" }, // 空文字说明
    selection: { type: Boolean, default: false }, // 是否显示多选框
    singleChoice: { type: Boolean, default: false }, // 单选模式
    selectable: { type: Function, default: () => true }, // 是否可选择
    rowStyle: { type: Function, default: () => {} }, // row样式控制
    align: { type: String, default: "left" }, // 对齐方式
    showIndex: { type: Boolean, default: false }, // 显示索引
    showPagination: { type: Boolean, default: true }, // 显示分页
    total: { type: Number, default: 0 }, // 总页数
    pageNum: { type: Number, default: 1 }, // 当前页
    pageSize: { type: Number, default: 20 }, // 每页多少条
    pageLayout: {
      type: String,
      default: "total, sizes, prev, pager, next, jumper",
    }, //分页布局
    ifFilter: { type: Boolean, default: false }, //是否动态表头
    rowKey: { type: String, default: null },
    treeProps: {
      type: Object,
      default: () => ({ hasChildren: "hasChildren", children: "children" }),
    },
    expand: { type: Boolean, default: false },
    expandName: { type: String, default: "expand" },
    defaultExpandAll: { type: Boolean, default: false },
    showSummary: { type: Boolean, default: false },
    summaryMethod: { type: Function },
    spanMethod: { type: Function },
    showBorder: { type: Boolean, default: false },
  },
  data() {
    return {
      checkedColumn: this.value,
      columns: [],
    };
  },
  watch: {
    value: {
      handler(val) {
        this.checkedColumn = val;
      },
      deep: true,
    },
    checkedColumn: {
      handler(val) {
        this.$emit("input", val);
      },
      deep: true,
    },
    allColumns: {
      handler() {
        this.initTable();
      },
      deep: true,
      immediate: true,
    },
  },
  methods: {
    initTable() {
      if (this.ifFilter) {
        if (this.checkedColumn.length === 0) {
          this.checkedColumn = this.allColumns.map((item) => item.prop);
        }
        this.columns = this.allColumns.filter((item) => {
          return this.checkedColumn.includes(item.prop);
        });
      } else {
        this.columns = this.allColumns;
      }
    },
    selectionChange(value) {
      this.$emit("selectionChange", value);
    },
    rowClick(value) {
      this.$emit("rowClick", value);
    },
    currentChange(value) {
      this.$emit("currentChange", value);
    },
    sizeChange(value) {
      this.$emit("sizeChange", value);
    },
    sortChange({ column, prop, order }) {
      this.$emit("sortChange", { column, prop, order });
    },
    checkChangeHandle() {
      if (this.checkedColumn.length < 1) {
        this.$message.warning("请至少选择一项数据");
      } else {
        this.columns = this.allColumns.filter((item) => {
          return this.checkedColumn.includes(item.prop);
        });
      }
    },
    rederColor(color, key) {
      if (!color) return;
      let rst = {};
      let c = color;
      if (!color.includes("#")) {
        c =
          variables[
            `tableColor${color
              .toLowerCase()
              .replace(/( |^)[a-z]/g, (L) => L.toUpperCase())}`
          ];
      }
      rst[key] = c;
      return rst;
    },
    //单选模式
    singleSelectable(row, index) {
      // 如果已经有选中的行，则其它行不可选
      if (!this.$refs.elTable) return true;
      const selected = this.$refs.elTable.selection || [];
      // 允许选中当前已选的行，或者还没有选中的时候
      return selected.length === 0 || selected.includes(row);
    },
  },
};
</script>

<style lang="scss" scoped>
$--color: #5e81f4;
.showBorder {
  border: 1px solid #e0e2ec;
}
.table-wrapper {
  position: relative;
  width: 100%;
  height: 100%;
  :deep .el-table__empty-block {
    width: 100% !important;
  }
  :deep .el-table__fixed-right {
    height: 100% !important;
  }

  &-main {
    border: 1px solid #ececec;
    // border:none;
    border-bottom: 0px;
  }
  .table-pagination {
    float: right;
    margin-right: 20px;
    margin-top: 10px;
  }
  .select-check-wrapper {
    position: absolute;
    top: -20px;
    right: 0;
  }
  .render-color {
    position: relative;
    i {
      position: absolute;
      width: 12px;
      height: 12px;
      border-radius: 50%;
      top: 4px;
      left: -16px;
    }
  }
  // 自定义 jumper 文字
  ::v-deep .el-pagination {
    .el-pagination__editor {
      margin: 0 !important;
      padding: 0 !important;
    }
    .el-pagination__jump {
      font-size: 0; // 隐藏原文字
      &::before {
        content: "";
        // font-size: 12px;
        // color: #606266;
      }
      &::after {
        content: "GO";
        display: inline-block;
        font-size: 12px;
        width: 32px;
        height: 30px;
        text-align: center;
        line-height: 28px;
        color: #fff;
        border-radius: 4px 4px 4px 4px;
        // background: $--color !important;
        border: 1px solid #e0e2ec;
        cursor: pointer;
      }
    }
  }
}
</style>
<style lang="scss">
.el-tooltip__popper {
  max-width: 300px;
}
</style>
