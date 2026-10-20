<template>
  <el-dialog :visible.sync="dialogVisible" width="60%" :before-close="handleClose"
             :class="setMainClass()" :close-on-click-modal="false">
    <!-- 头部操作 -->
    <div slot="title" class="dialog-title">
      <ul class="tab-wrapper">
        <li v-for="item in tabList" :key="item.value" :class="{active: item.value===activeTab}"
            @click="clickHandle(item.value)">
          <span>{{ item.label }}</span>
        </li>
      </ul>
    </div>
    <!-- 内容 -->
    <div class="info-wrapper">
      <personalInfo v-if="dialogVisible&&activeTab=='1'&&customerType=='1'"
                    :customerParam="customerParam">
      </personalInfo>
      <merchantInfo v-if="dialogVisible&&activeTab=='1'&&customerType!='1'"
                    :customerParam="customerParam">
      </merchantInfo>
      <FileDetail v-if="activeTab=='2'&&dialogVisible" :customerModelType="customerType"
                  :customerParam="customerParam" :modelId="modelId">
      </FileDetail>
      <VisitInfo v-if="activeTab=='3'&&dialogVisible" :color="colors.color"
                 :backgroundColor="colors.backgroundColor" :customerType="customerType"
                 :customerParam="customerParam"></VisitInfo>
      <CustomerTrip v-if="activeTab=='4'&&dialogVisible" :customerParam="customerParam"
                    :color="colors.color" :backgroundColor="colors.bColor">
      </CustomerTrip>
    </div>
  </el-dialog>
</template>

<script>
import personalInfo from "@/components/BaseDetail/cell/personalInfo";
import merchantInfo from '@/components/BaseDetail/cell/merchantInfo';
import service from '../../api'
const COLOR_LIST = [
  {
    color: '#e8921c',
    backgroundColor: '#7b581b',
    bColor: '#fdf4e8'
  },
  {
    color: '#4ccbcd',
    backgroundColor: '#1B7A7B',
    bColor: '#edfafa'
  },
]
const DEFAULT_TAB = [
  { label: '走访信息', value: '3' },
]
const NORMAL_TAB = [
  {
    label: "客户信息",
    value: "1",
  },
  {
    label: "客户旅程",
    value: "4",
  },
]
const BUILDING_TAB = [
  {
    label: "建档信息",
    value: "2",
  },
]
export default ({
  components: {
    personalInfo,
    merchantInfo
  },
  props: {
    value: {
      type: Boolean,
    },
    customerType: {
      type: String,
      default: '1',
    },
  },
  data() {
    return {
      dialogVisible: this.value,
      activeTab: null,
      dataPackageInfo: {},
      loading: false,
      tabList: [],
      modelId: null,
    }
  },
  computed: {
    colors() {
      return COLOR_LIST[this.customerType == '1' ? 0 : 1]
    },
    customerParam() {
      return this.$store.getters['gridManagement/customerParam']
    }
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val
        if (val) {
          this.initData()
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit('input', val)
      }
    }
  },
  methods: {
    initData() {
      this.getTypeByPhoneAndIdentity()
    },
    getTypeByPhoneAndIdentity() {
      const { phone, identity } = this.customerParam
      const param = {
        phone,
        identity
      }
      service.getTypeByPhoneAndIdentity(param).then(res => {
        const { data } = res
        this.modelId = data
        if (this.customerParam.type != '3') {
          if (data) {
            this.tabList = [...NORMAL_TAB, ...BUILDING_TAB, ...DEFAULT_TAB]
          } else {
            this.tabList = [...NORMAL_TAB, ...DEFAULT_TAB]
          }
          this.activeTab = '1'
        } else {
          this.getIsBankCustomer()
        }
      })
    },
    getIsBankCustomer() {
      const param = {
        identity: this.customerParam.identity,
        phone: this.customerParam.phone,
      }
      service.getIsBankCustomer(param).then(res => {
        const { msg } = res
        if (msg == '1') {
          this.tabList = [...NORMAL_TAB, ...BUILDING_TAB, ...DEFAULT_TAB]
          this.activeTab = '1'
        } else {
          this.tabList = [...BUILDING_TAB, ...DEFAULT_TAB]
          this.activeTab = '2'
        }
      })
    },
    handleClose() {
      this.dialogVisible = false
      this.dataPackageInfo = {}
    },
    clickHandle(value) {
      this.activeTab = value
    },
    setMainClass() {
      switch (this.customerType) {
        case '1':
          return 'family-class'
        case '2':
        case '3':
          return 'company-class'
        default:
          break
      }
    },
  },
})
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