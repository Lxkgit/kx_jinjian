<template>
  <buttonSelect buttonName="网点筛选" v-model="showSelect">
    <div class="customer-filter-wrapper">
      <!-- <ul class="filter-tab">
        <li v-for="item in tabList" :key="item.value" :class="{active: activeValue===item.value}"
            @click="checkTab(item)">
          {{ item.label }}
        </li>
      </ul> -->
      <div class="filter-content">
        <!-- <multiLevelSelect ref="multiLevelSelect" v-show="activeValue==='1'"></multiLevelSelect> -->
        <groupSelect ref="groupSelect" v-model="groupSelected">
        </groupSelect>
      </div>
      <div class="filter-footer">
        <div class="filter-btn" @click="resetHandle">重置</div>
        <div class="filter-btn confirm-btn" @click="confirmHandle">确定</div>
      </div>
    </div>
  </buttonSelect>
</template>

<script>
import buttonSelect from '../common/buttonSelect';
import multiLevelSelect from '../common/multiLevelSelect';
import groupSelect from '../common/groupSelect';
export default ({
  components: {
    buttonSelect,
    multiLevelSelect,
    groupSelect
  },
  data() {
    return {
      activeValue: '1',
      tabList: [
        { label: '类型筛选', value: '1' },
        { label: '客群筛选', value: '2' },
      ],
      showSelect: false,
      groupSelected: []
    }
  },
  computed: {
    filterType() {
      return this.$store.getters['screenView/filterType']
    },
  },
  watch: {
    showSelect(val) {
      if (val) {
        this.activeValue = this.filterType
        // this.$refs.multiLevelSelect.initData()
        this.$refs.groupSelect.initData()
      }
    }
  },
  mounted() {
    this.activeValue = '2'
    this.confirmHandle()
  },
  methods: {
    checkTab({ value }) {
      this.activeValue = value
    },
    resetHandle() {
      let param
      console.log(11, this.activeValue);
      switch (this.activeValue) {
        case '1':
          param = {
            typeList: ['2'],
          }
          this.$store.commit('screenView/SET_FILTER_PARAM', param)
          this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
          // this.$refs.multiLevelSelect.initData()
          break
        case '2':
          param = {
            groupIdList: [],
            groupCategory: ''
          }
          this.$store.commit('screenView/SET_FILTER_PARAM', param)
          this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
          this.$refs.groupSelect.initData()
          break
        default:
          break
      }
    },
    confirmHandle() {
      this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
      switch (this.activeValue) {
        // case '1':
        //   this.$refs.multiLevelSelect.getCurrentData(info => {
        //     const { checkList, checkChildList, checkType } = info
        //     if (checkList.length === 0) {
        //       this.$modal.msgWarning('类型筛选不能为空')
        //       return
        //     }
        //     const param = {
        //       typeList: checkList,
        //     }
        //     param[checkType] = checkChildList
        //     this.$store.commit('screenView/SET_FILTER_PARAM', param)
        //     this.showSelect = false
        //   })
        //   break
        case '2':
          this.$refs.groupSelect.getCurrentData(info => {
            // if (info.groupCategory === '') {
            //   this.$modal.msgWarning('客群筛选不能为空')
            //   return
            // }
            if (info.groupIdList[0] == null) {
              info.groupIdList = []
            }
            info.groupCategory = '2'
            this.$store.commit('screenView/SET_FILTER_PARAM', info)
            this.showSelect = false
          })
          break
        default:
          break
      }

    }
  },
})
</script>

<style lang="scss" scoped>
.customer-filter-wrapper {
  width: 320px;
  height: 400px;
  background: radial-gradient(106% 55% at 50% 50%, rgba(2, 23, 48, 0.8) 0%, rgba(28, 83, 165, 0.6) 100%);
  border-radius: 8px;
  border: 1px solid #3278F1;
  position: relative;
  padding: 15px 20px;

  .filter-tab {
    width: 185px;
    line-height: 32px;
    margin: auto;
    background: rgba($color: #3278F1, $alpha: 0.2);
    border-radius: 6px;
    color: #3278F1;
    font-size: 16px;
    display: flex;

    >li {
      width: 50%;
      text-align: center;
      cursor: pointer;

      &.active {
        color: #fff;
        border-radius: 6px;
        background: #3278F1;
      }
    }
  }

  .filter-content {
    width: calc(100% - 3px);
    padding: 0 3px;
    margin: auto;
    height: calc(100% - 37px);
    overflow: auto;

    &::-webkit-scrollbar {
      width: 3px;
    }

    &::-webkit-scrollbar-track {
      background: transparent;
    }

    &::-webkit-scrollbar-thumb {
      background: #3278F1;
      border-radius: 2px;
    }
  }

  .filter-footer {
    display: flex;
    justify-content: space-between;

    .filter-btn {
      cursor: pointer;
      width: 117px;
      line-height: 37px;
      background: rgba($color: #294e59, $alpha: 0.6);
      border-radius: 19px;
      font-size: 16px;
      color: #fff;
      text-align: center;
      border: 1px solid #3278F1;
    }

    .confirm-btn {
      background: #3278F1;
      border: 1px solid #3278F1;
    }
  }
}
</style>