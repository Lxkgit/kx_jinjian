<template>
  <el-dialog
    :visible.sync="dialogVisible"
    width="70%"
    :before-close="handleClose"
    class="family-class"
    :close-on-click-modal="false"
  >
    <!-- 头部操作 -->
    <div slot="title" class="dialog-title">
      <ul class="tab-wrapper">
        <li
          v-for="item in tabList"
          :key="item.value"
          :class="{ active: item.value === activeTab }"
          @click="clickHandle(item.value)"
        >
          <span>{{ item.label }}</span>
        </li>
      </ul>
    </div>
    <!-- 内容 -->
    <div class="info-wrapper">
      <netWorkInfo v-if="activeTab == 1"></netWorkInfo>
      <gridInfo v-if="activeTab == 2"></gridInfo>
      <eventsInfo v-if="activeTab == 3"></eventsInfo>
      <message v-if="activeTab == 4" @location="location"></message>
    </div>
  </el-dialog>
</template>
  
<script>
import netWorkInfo from "./components/netWorkInfo.vue";
import gridInfo from "./components/gridInfo.vue";
import eventsInfo from "./components/eventsInfo.vue";
import message from "./components/message.vue";
export default {
  components: {
    netWorkInfo,
    gridInfo,
    eventsInfo,
    message,
  },
  props: {
    value: {
      type: Boolean,
    },
  },
  data() {
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
        {
          label: "网格事件",
          value: "2",
        },
        {
          label: "网点事件",
          value: "3",
        },
        {
          label: "网格动态",
          value: "4",
        },
      ],
      modelId: null,
    };
  },
  computed: {},
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
        if (val) {
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
    clickHandle(value) {
      this.activeTab = value;
    },
    handleClose() {
      this.dialogVisible = false;
      this.dataPackageInfo = {};
      this.activeTab = "1";
    },
    location(id) {
      let netMap = {
        1: {
          name: "金华银行总行营业部",
          address: "金华市婺城区丹溪路1388号",
          lon: "119.63912300",
          lat: "29.07940400",
          code: "330702007004",
          lev: 5,
          searchType: 0,
        },
        2: {
          name: "浙江稠州商业银行金华分行",
          address: "浙江省金华市婺城区八一南街161号",
          lon: "119.650255",
          lat: "29.092258",
          code: "330702005017",
          lev: 5,
          searchType: 0,
        },
        3: {
          name: "浙江泰隆商业银行义乌支行",
          address: "福田街道金融商务局6街,福田银座B座",
          lon: "120.104168",
          lat: "29.328532",
          code: "330782008hb02",
          lev: 5,
          searchType: 0,
        },
        4: {
          name: "建行义乌分行营业部",
          address: "浙江省义乌市稠州北路468号",
          lon: "120.087117",
          lat: "29.316667",
          code: "330782001010",
          lev: 5,
          searchType: 0,
        },
        5: {
          name: "浙江稠州商业银行义乌稠江支行",
          address: "义乌市新科路F8号稠州大厦",
          lon: "120.038802",
          lat: "29.283228",
          code: "330782003015",
          lev: 5,
          searchType: 0,
        },
        6: {
          name: "浙商银行金华分行",
          address: "浙江省金华市金东区宾虹东路358号嘉福商务大厦2号楼10楼",
          lon: "119.669854",
          lat: "29.096801",
          code: "330703001003",
          lev: 5,
          searchType: 0,
        },
        7: {
          name: "中信银行金华分行营业部",
          address: "浙江省金华市婺城区西关街道宾虹路999号置信大厦",
          lon: "119.637475",
          lat: "29.084855",
          code: "330702007004",
          lev: 5,
          searchType: 0,
        },
        8: {
          name: "嘉兴银行金华分行营业部",
          address: "浙江省金华市金东区政和街33号",
          lon: "119.69083",
          lat: "29.103724",
          code: "330703001002",
          lev: 5,
          searchType: 0,
        },
        9: {
          name: "浙江稠州商业银行金东支行",
          address: "浙江省金华市金东区环城东路489号",
          lon: "119.689506",
          lat: "29.094874",
          code: "330703001hb01",
          lev: 5,
          searchType: 0,
        },
      };
      this.handleClose();
      this.$store.commit("screenView/SET_ANCHOR_INFO", netMap[id]);
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
.shop-class {
  ::v-deep .el-dialog__header {
    background: #4ccdcf;
    .active {
      background-color: #1e8b8d;
    }
  }
}
.company-class {
  ::v-deep .el-dialog__header {
    background: #7735a8;
    .active {
      background-color: #5b1c8a;
    }
  }
}
.dialog-title {
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
  height: 78vh;
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
</style>