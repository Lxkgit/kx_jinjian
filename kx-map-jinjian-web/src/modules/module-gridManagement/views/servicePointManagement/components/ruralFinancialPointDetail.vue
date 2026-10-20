<template>
  <el-dialog
    :visible.sync="dialogVisible"
    width="70%"
    :before-close="handleClose"
    class="family-class"
    :close-on-click-modal="false"
  >
    <!-- 头部操作 -->
    <div slot="title" class="dialog-title">{{ servePointName }}</div>
    <!-- 内容 -->
    <div class="info-wrapper">
      <infoList :infoData="infoData" :gridColumns="2"></infoList>
    </div>
  </el-dialog>
</template>
    
  <script>
import infoList from "@/components/BaseDetail/components/infoList.vue";
import service from "../api";
export default {
  components: {
    infoList,
  },
  props: {
    value: {
      type: Boolean,
    },
    info: {
      type: Object,
    },
  },
  data() {
    return {
      dialogVisible: this.value,
      tabList: [
        {
          label: "基本信息",
          value: "1",
        },
      ],
      servePointName: "测试",
      infoData: [
        { label: "具体地址", value: "", key: "servePointAddr" },
        { label: "所在网格", value: "", key: "gridName" },
        { label: "站长姓名", value: "", key: "dutyName" },
        { label: "联系电话", value: "", key: "dutyPhone" },
      ],
      detailInfo: {},
    };
  },
  computed: {},
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
        if (val) {
          this.init();
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit("input", val);
      },
    },
  },
  methods: {
    init() {
      this.servePointName = this.info.servePointName;
      this.infoData.forEach((item) => {
        item.value = this.info[item.key] || item.value;
      });
      // service.details({ id: this.info.id }).then((res) => {
      //   this.detailInfo = res;
      //   this.infoData.forEach((item) => {
      //     item.value = res[item.key] || item.value;
      //   });
      // });
    },
    clickHandle(value) {
      this.activeTab = value;
    },
    handleClose() {
      this.dialogVisible = false;
      this.activeTab = "1";
    },
  },
};
</script>
    
<style lang="scss" scoped>
.family-class {
  ::v-deep .el-dialog__header {
    background: #ea9518;
    .active {
      background-color: #b27720;
    }
  }
}
.dialog-title {
  color: #fff;
  .tab-wrapper {
    display: flex;
    height: 30px;
    li {
      color: #fff;
      cursor: pointer;
      padding: 0 8px;
      line-height: 30px;
      border-radius: 4px;
      margin-right: 5px;
    }
  }
}
.info-wrapper {
  height: 30vh;
  overflow: auto;
  overflow-x: hidden;
  position: relative;
}
::v-deep .el-dialog__body {
  padding: 15px;
}
::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
.little-info {
  .right {
    width: 124%;
    display: flex;
    margin-left: 50px;
    li {
      width: 400px;
    }
  }
}
</style>