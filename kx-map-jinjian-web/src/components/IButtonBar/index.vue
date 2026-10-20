<template>
  <div class="btn-bar-wrapper" :style="{'--justifyContent':justifyContent}">
    <div v-for="item in pageBtnLists" :key="item.ename" class="btn-box"
         :style="{'--marginLeft': marginLeft}">
      <el-button v-if="!item.slot" :size="size" :plain="item.plain||plain" :type="item.type"
                 :disabled="getDisabled(item.disabled)" @click="$emit('click',item.ename)">
        <i v-if="item.icon"
           :class="['el-icon--left',item.icon.includes('el')?item.icon:[item.icon,'iconfont']]"></i>
        {{ item.cname }}
      </el-button>
      <slot v-else :name="item.ename" :btn="item" />
    </div>
  </div>
</template>

<script>
export default ({
  name: 'IButtonBar',
  props: {
    btnLists: {
      type: Array,
      default: () => [],
    },
    size: {
      type: String,
      default: 'medium',
    },
    plain: {
      type: Boolean,
      default: false
    },
    marginLeft: {
      type: String,
      default: '10px'
    },
    justifyContent: {
      type: String,
      default: 'flex-start'
    },
  },
  computed: {
    pageBtnLists() {
      const showBtns = []
      for (let item of this.btnLists) {
        let permi = item.permi === undefined || this.$auth.hasPermi(item.permi)
        let isShow = true
        if (typeof item.isShow === 'function') {
          const res = item.isShow.call(this)
          isShow = item.isShow === undefined || res
        } else {
          isShow = item.isShow === undefined || item.isShow
        }
        if (permi && isShow) {
          showBtns.push(item)
        }
      }
      return showBtns
    },
  },
  methods: {
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
.btn-bar-wrapper {
  display: flex;
  margin-bottom: 16px;
  justify-content: var(--justifyContent);
  .btn-box {
    margin-right: var(--marginLeft);
  }
}
</style>