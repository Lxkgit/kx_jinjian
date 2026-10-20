<template>
  <el-dialog :visible.sync="dialogVisible" width="70%" :before-close="handleClose" class="family-class"
    :close-on-click-modal="false">
    <!-- 头部操作 -->
    <div slot="title" class="dialog-title">
      <ul class="tab-wrapper">
        <li v-for="item in tabList" :key="item.value" :class="{ active: item.value === activeTab }"
          @click="clickHandle(item.value)">
          <span>{{ item.label }}</span>
        </li>
      </ul>
    </div>
    <!-- 内容 -->
    <div class="info-wrapper">
      <infoList :infoData="infoData" :gridColumns="1">
        <template #exchange="{ data }">
          <div class="little-info">
            <div class="left">{{ data.label }}:</div>
            <ul class="right">
              <li v-for="item in data.children" :key="item.key">
                <span>{{ item.label }}:</span>
                <span>{{ item.value }}</span>
              </li>
            </ul>
          </div>
        </template>
        <template #exchangeMoney="{ data }">
          <div class="little-info">
            <div class="left">{{ data.label }}:</div>
            <ul class="right">
              <li v-for="item in data.children" :key="item.key">
                <span>{{ item.label }}:</span>
                <span>{{ item.value }}</span>
              </li>
            </ul>
          </div>
        </template>
        <template #c="{ data }">
          <div class="little-info">
            <div class="left">{{ data.label }}:</div>
            <ul class="right">
              <li v-for="item in data.children" :key="item.key">
                <span>{{ item.label }}:</span>
                <span>{{ item.value }}</span>
              </li>
            </ul>
          </div>
        </template>
        <template #d="{ data }">
          <div class="little-info">
            <div class="left">{{ data.label }}:</div>
            <ul class="right">
              <li v-for="item in data.children" :key="item.key">
                <span>{{ item.label }}:</span>
                <span>{{ item.value }}</span>
              </li>
            </ul>
          </div>
        </template>
      </infoList>
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
  data () {
    const genKey = () => `k_${Math.random().toString(36).slice(2, 10)}`;
    return {
      dialogVisible: this.value,
      activeTab: "1",
      dataPackageInfo: {},
      loading: false,
      tabList: [
        {
          label: "基本信息",
          value: "1",
        },
      ],
      modelId: null,
      infoData: [
        { label: "网点名称", value: "温州银行股份有限公司永嘉万鑫锦园社区支行", key: genKey() },
        { label: "所属银行机构", value: "温州银行股份有限公司", key: genKey() },
        { label: "银行机构类型", value: "股份制商业银行", key: genKey() },
        { label: "网点负责人", value: "林伟雄", key: genKey() },
        { label: "地址", value: "浙江省永嘉县桥下镇镇前街万鑫锦园1幢111室", key: genKey() },
        { label: "联系电话", value: "0577-66968800", key: genKey() },
        { label: "责任网格", value: "桥头镇、桥下镇、金溪镇", key: genKey() },
        { label: "网点介绍", value: "温州银行股份有限公司永嘉万鑫锦园社区支行隶属于温州银行股份有限公司，位于永嘉县万鑫锦园社区核心区域，周边商住氛围成熟、交通便捷。网点以“便民、亲民、惠民”为服务理念，致力于打造家门口的社区银行，主要为周边居民及小微商户提供存取款、转账结算、银行卡、理财咨询及贷款等综合金融服务。网点配备专业服务团队，服务规范高效，同时常态化开展金融知识宣传与反诈教育，持续提升客户体验与社区金融服务水平。", key: genKey() },
      ],
      detailInfo: {},
    };
  },
  computed: {},
  watch: {
    value: {
      handler (val) {
        this.dialogVisible = val;
        if (val) {
          // this.init();
        }
      },
    },
    dialogVisible: {
      handler (val) {
        this.$emit("input", val);
      },
    },
  },
  methods: {
    init () {
      // service.details({ id: this.info.id }).then((res) => {
      //   this.detailInfo = res;
      this.infoData.forEach((item) => {
        item.value = this.info[item.key] || item.value;
        item.children && item.children.forEach((e) => {
          e.value = this.info[e.key] || e.value;
        });
      });
      // });
    },
    clickHandle (value) {
      this.activeTab = value;
    },
    handleClose () {
      this.dialogVisible = false;
      this.dataPackageInfo = {};
      this.activeTab = "1";
    },
  },
};
</script>

<style lang="scss" scoped>
.family-class {

  /* 遮罩层调整为深色半透明 */
  ::v-deep .v-modal {
    background: rgba(2, 10, 30, 0.65) !important;
  }

  /* 对话框主体使用深蓝渐变 + 细边框与霓虹发光 */
  ::v-deep .el-dialog {
    background: linear-gradient(180deg, #031026 0%, #06132a 100%);
    border-radius: 10px;
    box-shadow: 0 12px 40px rgba(2, 110, 180, 0.14), inset 0 0 40px rgba(10, 90, 160, 0.02);
    border: 1px solid rgba(60, 150, 220, 0.12);
    overflow: hidden;
  }

  ::v-deep .el-dialog__header {
    background: linear-gradient(90deg, rgba(10, 120, 200, 0.06), rgba(30, 60, 120, 0.02));
    padding: 10px 18px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.03);
    color: #dff8ff;
  }

  ::v-deep .el-dialog__headerbtn .el-dialog__close {
    color: #c9f0ff;
    opacity: 0.95;
  }

  ::v-deep .el-dialog__body {
    background: transparent;
    color: #dff8ff;
    padding: 16px 20px;
  }
}

.dialog-title {
  .tab-wrapper {
    display: flex;
    height: 34px;

    li {
      color: #bfe9ff;
      cursor: pointer;
      padding: 4px 12px;
      line-height: 26px;
      border-radius: 6px;
      margin-right: 8px;
      background: rgba(255, 255, 255, 0.02);
      transition: all 0.18s ease;
      box-shadow: none;
    }

    li.active {
      background: linear-gradient(90deg, #08b6ff, #5b9cff);
      color: #00131a;
      font-weight: 600;
      box-shadow: 0 6px 20px rgba(11, 176, 255, 0.16);
    }
  }
}

.info-wrapper {
  overflow: auto;
  overflow-x: hidden;
  position: relative;
  padding: 6px 4px;
  max-height: 62vh;
}

/* 美化滚动条，使其更贴合大屏风格 */
.info-wrapper::-webkit-scrollbar {
  width: 10px;
}

.info-wrapper::-webkit-scrollbar-thumb {
  background: linear-gradient(180deg, rgba(80, 150, 220, 0.16), rgba(30, 80, 140, 0.14));
  border-radius: 8px;
}

.info-wrapper::-webkit-scrollbar-track {
  background: transparent;
}

.little-info {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10px;

  .left {
    min-width: 120px;
    color: #9fe6ff;
    font-weight: 500;
  }

  .right {
    width: calc(100% - 120px);
    display: flex;
    flex-wrap: wrap;
    gap: 8px 20px;
  }

  .right li {
    min-width: 280px;
    color: #d8f6ff;
  }
}
</style>
