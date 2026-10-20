<template>
  <buttonSelect buttonName="范围选择" v-model="showSelect">
    <!-- 默认筛选 -->
    <div class="filter-box" ref="filterMenu">
      <div class="filter-box-top">
        <!-- 左侧 -->
        <ul class="filter-left">
          <li v-for="(item) in leftList" :class="activeId == item.id && 'active'" :key="item.id"
              @click="changeActive(item)">
            <span>{{ item.name }}</span>
            <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAYAAAAmlE46AAAAyklEQVQokZXPPUpDQRQG0IO/rZVdSEoLsbAUhGAb0lkJLkCsLNyBpQGrLCJo5w5CSEDsXEFaEdF0gzIyMA+CmOebDy7M3Jlz4RLif9UW4kSIZ/nfTIjva+pziDGOcqXsYacO9jBFK9+/lx9XwQs8YGvV1L/gDYbYrltiY+mcpg9wWQd+w03co98EVbCNR+w3RfKOu+iUoAo+oYuPUpjyjAO8lMKUOU4wK4UprzjGqBSmfOEcd6UwJeAK14i5t94EVrnFKT7xlpsL+AE5qDf5W/c/1QAAAABJRU5ErkJggg=="
                 class="left-img" v-if="item.id!=rootId" @click.stop="goBack(item)" />
          </li>
        </ul>

        <!-- 右侧 -->
        <div class="filter-right" ref="filterRight">
          <el-checkbox-group v-model="selectedCheck" @change="changeHandle">
            <el-checkbox v-for="(item, ind) in rightList" :label="item.code" :key="item.id">
              <span :title="item.name">
                {{ item.name }}
              </span>
              <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAYAAAAmlE46AAAA2ElEQVQokZ3SrUqEQRTG8Z+rScQkWAWbglewRcRg1WTwBiwWsxegzWtYtq0XILJ5o4hdDTYRRTFMGRk8LyzLu/CODwznOWf4z5z5IOULKT9J+VTKJR9IeRR+7uhhGxvY9KddHOIOi+aogO8x9RnxJeIeHrDehvZaagtTfguP2OkCzmoN99ivBZsubnFcCzYa4rz4pUqw6KqcvXbHRgf/AcflzWvBQbzvTw14iZMm6Xo5Z7ieLnQBj3AzWyzgSvjliKsRv+O3TNpWK+AbvvARteJf0cdzaw/4Bad/N5wL4MhLAAAAAElFTkSuQmCC"
                   class="right-img" v-if="item.type===1" @click.prevent="goNext(item)" />
            </el-checkbox>
          </el-checkbox-group>
        </div>
      </div>
      <div class="filter-box-bottom">
        <div class="filter-box-btn" @click="resetHandle">重置</div>
        <div v-show="showSubmit" class="filter-box-btn" @click="submitHandle">确定</div>
      </div>
    </div>
  </buttonSelect>
</template>

<script>
import service from '../../api';
import buttonSelect from "../common/buttonSelect";
export default {
  components: {
    buttonSelect,
  },
  data() {
    return {
      selectedCheck: [],
      activeId: null,
      deptList: [],
      leftList: [],
      rightList: [],
      showSelect: false,
      rootId: null,
      curDept: null,
    };
  },
  computed: {
    deptId() {
      return this.$store.getters['userInfo'].deptId
    },
    roles() {
      return this.$store.getters['roles']
    },
    orgList() {
      return this.$store.getters['screenView/orgList']
    },
    showSubmit() {
      return this.rightList.length > 0
    }
  },
  created() {
    this.initRoleParam()
  },
  mounted() {
    // this.listDeptMember()
  },
  methods: {
    initRoleParam() {
      let { managerNum, managedOrgCode } = this.$store.getters['userInfo']
      // 判断是否是客户经理
      let isManage = true
      for (let i of this.roles) {
        if (i !== 'manager') {
          isManage = false
          break
        }
      }
      let param = { ...(isManage ? { managerCodes: managerNum } : { orgCodes: managedOrgCode }) }
      this.$store.commit('screenView/SET_ROLE_PARAM', param)
      this.$store.commit('screenView/SET_INIT_ROLE', param)
    },
    listDeptMember(deptId) {
      const param = {
        deptId
      }
      service.listDeptMember(param).then(res => {
        const { data } = res
        this.leftList = [data]
        this.rightList = data.children
        this.activeId = data.id
        // this.selectedCheck = []
        this.topScrollClick()
        if (!deptId) {
          this.rootId = data.id
        }
      })
    },
    goNext(item) {
      const { id } = item
      this.listDeptMember(id)
    },
    goBack(item) {
      const { parentId } = item
      this.listDeptMember(parentId)
    },
    topScrollClick() {
      this.$nextTick(() => {
        this.$refs.filterRight.scrollTo({ top: 0, behavior: 'smooth' })
      })
    },
    changeActive(item) {
      this.activeId = item.id
      this.rightList = item.children
      // this.selectedCheck = []
    },
    changeHandle(val) {
      if (val.length > 1) {
        this.selectedCheck = val.slice(1)
        this.curDept = this.rightList.filter(item => item.code === val)[0]
      }

    },
    resetHandle() {
      this.listDeptMember()
      const { addrTypeCodeSet } = this.$store.getters['userInfo']
      const orgCodes = addrTypeCodeSet.join(',')
      this.selectedCheck = []
      this.$store.commit('screenView/SET_ORG_LIST', [
        {
          type: 'boundary',
          code: orgCodes
        }
      ])
      this.$store.commit('screenView/SET_ORG_CODE', orgCodes)
      this.initRoleParam()
    },
    submitHandle() {
      if (this.selectedCheck.length === 0) {
        this.$modal.msgWarning('请选择筛选项')
        return
      }
      let orgCodes = []
      let managerCodes = []
      let arrCodes = []
      for (let item of this.rightList) {
        if (this.selectedCheck.includes(item.code)) {
          if (item.type === 1) {
            orgCodes.push(item.code)
          } else {
            managerCodes.push(item.code)
          }
          item.addrTypes && arrCodes.push(...item.addrTypes)
        }
      }
      this.$store.commit('screenView/SET_ROLE_PARAM', { orgCodes: orgCodes.join(','), managerCodes: managerCodes.join(',') })
      this.$store.commit('screenView/SET_ORG_CODE', arrCodes.join(','))
      this.$store.commit('screenView/SET_ORG_LIST', [{ code: arrCodes.join(','), type: 'boundary' }])
      this.showSelect = false
    },
  },
};
</script>

<style lang="scss" scoped>
.filter-box {
  width: 390px;
  height: 300px;
  background: linear-gradient(
    180deg,
    rgba($color: #31474f, $alpha: 0.9),
    rgba($color: #1a242b, $alpha: 0.9)
  );
  border-radius: 8px;
  border: 1px solid rgba($color: #00f6ff, $alpha: 0.5);
  > div.filter-box-top {
    display: flex;
    height: 250px;
  }
  > div.filter-box-bottom {
    display: flex;
    justify-content: space-between;
    margin-top: 12px;
  }

  .filter-box-btn {
    cursor: pointer;
    color: white;
    background-color: rgba(0, 246, 255, 0.6);
    border-radius: 8px;
    width: 100px;
    height: 28px;
    line-height: 28px;
    text-align: center;
    margin: 0 30px;
    &:hover {
      background-color: rgba(0, 246, 255, 0.8);
    }
  }
  .filter-left {
    width: 170px;
    margin: 10px 0;
    list-style: none;
    padding: 0;
    border-right: 3px solid #ffffff56;
    overflow: auto;
    &::-webkit-scrollbar {
      display: none;
    }
    li {
      font-size: 18px;
      font-weight: bold;
      color: #fff;
      line-height: 42px;
      text-align: left;
      list-style-type: none;
      padding: 0;
      cursor: pointer;
      padding-left: 30px;
      position: relative;
      width: 130px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      .left-img {
        position: absolute;
        top: 15px;
        left: 10px;
      }
      &:hover,
      &.active {
        color: #00f6ff;
        background: #00000040;
      }
    }
  }
  .filter-right {
    width: 210px;
    height: 100%;
    overflow: auto;
    &::-webkit-scrollbar {
      display: none;
    }
    ::v-deep .el-checkbox {
      width: 100%;
      height: 40px;
      line-height: 40px;
      padding-left: 14px;
      box-sizing: border-box;
      color: #fff;
      position: relative;
      padding-right: 40px;

      .right-img {
        position: absolute;
        right: 10px;
        top: 10px;
      }
      &:hover {
        background: #00000040;
      }
      .el-checkbox__inner {
        width: 22px;
        height: 22px;
        border-width: 2px;
        background-color: #1d455240;
        border-color: #00f6ff88;

        &::after {
          height: 10px;
          width: 5px;
          left: 5px;
          border-width: 2px;
          transition: none;
          display: none;
        }
      }
      .el-checkbox__input {
        position: relative;
        top: -15px;
      }
      .el-checkbox__label {
        font-size: 18px;
        line-height: 40px;
        display: inline-block;
        height: 40px;
        padding-left: 6px;
        > span {
          line-height: 40px;
          display: inline-block;
          height: 40px;
          width: 130px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }
      &.is-checked {
        .el-checkbox__label {
          color: #fff;
        }
        .el-checkbox__inner {
          background-color: #00f6ff88;
          border-color: #00f6ff88;
          &::after {
            display: block;
          }
        }
      }
    }
  }
}
</style>
