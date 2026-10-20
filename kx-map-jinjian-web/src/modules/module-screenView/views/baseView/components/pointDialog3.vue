<template>
  <el-dialog
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    class="collect-class"
    :close-on-click-modal="false"
    :append-to-body="true"
  >
    <div class="dialog-wrapper">
      <div class="header">风险事件</div>
      <div class="content">
        <div class="title">基本信息</div>
        <div class="dialog-close" @click="handleClose">
          <img
            src="@/modules/module-screenView/assets/image/dialog/dialog-close.png"
            alt=""
          />
        </div>
        <div class="item-box">
          <div class="item" v-for="item in tableColumns" :key="item.prop">
            <div class="label">{{ item.label }}</div>
            <div class="value">{{ item.value }}</div>
          </div>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<script>
export default {
  props: {
    value: {
      type: Boolean,
    },
    info: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      dialogVisible: this.value,
      tableData: [],
      total: 1,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
        if (val) {
          this.initData();
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit("input", val);
      },
    },
  },
  computed: {
    tableColumns() {
      const arr = [
        { label: "小区名称", prop: "name", value: "-" },
        { label: "风险类型", prop: "test2", value: "-" },
        { label: "风险等级", prop: "test3", value: "-" },
        { label: "预警时间", prop: "test4", value: "-" },
        { label: "风险置信度", prop: "test5", value: "-" },
        { label: "风险标签", prop: "test6", value: "-" },
        { label: "活跃高频时段", prop: "test7", value: "-" },
        { label: "数据来源", prop: "test8", value: "-" },
        { label: "责任网点", prop: "test9", value: "-" },
        { label: "处置状态", prop: "test10", value: "-" },
      ];

      arr.forEach((item) => {
        item.value = this.info[item.prop];
      });
      return arr;
    },
  },
  methods: {
    initData() {
      this.tableData = [
        {
          test1: "PC202603100",
          test2: "2026-03-01",
          test3: "国家金融监督管理总局浙江监管局",
          test4: "陈昊宇",
          test5: "杭州市上城区",
          test6: "商户",
          test7: "8",
          test8: "2",
          test9: "沈亦凡",
          test10: "2026-03-01 09:15:00",
          test11: "待复核",
        },
      ];
    },
    handleClose() {
      this.dialogVisible = false;
    },
  },
};
</script>
<style lang="scss" scoped>
.collect-class {
  ::v-deep {
    .el-dialog__header {
      display: none;
    }
    .el-dialog {
      background: none;
      box-shadow: none;
      display: flex;
      justify-content: center;
      margin-top: 20vh !important;
    }
  }
}

.dialog-wrapper {
  .header {
    color: #458fff;
    font-size: 18px;
    width: 186px;
    height: 36px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: url("~@/modules/module-screenView/assets/image/dialog/dialog1.png")
      no-repeat;
    background-size: 100% 100%;
  }
  .content {
    position: relative;
    width: 500px;
    background: url("~@/modules/module-screenView/assets/image/dialog/dialog3.png")
      no-repeat;
    background-size: 100% 100%;
    padding: 18px 14px;
    .title {
      color: #458fff;
      font-size: 16px;
      font-weight: bold;
      line-height: 24px;
      margin-top: 10px;
      margin-bottom: 10px;
    }
    .dialog-close {
      position: absolute;
      width: 30px;
      height: 20px;
      right: 10px;
      top: 10px;
      background: url("~@/modules/module-screenView/assets/image/dialog/dialog4.png")
        no-repeat;
      background-size: 100% 100%;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      img {
        width: 5px;
        height: 5px;
      }
    }
    .item-box {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      padding: 0 20px;
      .item {
        // margin-top: 10px;
        display: flex;
        align-items: flex-start;
        min-width: 40%;
        font-size: 14px;
        line-height: 1.5;
        .label {
          color: #94b9fa;
          padding-right: 10px;
          white-space: nowrap;
          word-break: normal;
        }
        .value {
          color: #fff;
        }
      }
    }
  }
}
</style>
