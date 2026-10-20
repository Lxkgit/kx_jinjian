<template>
  <div class="mesh-menu">
    <div class="menu-item" :class="{'chooseOne':currentIndex == index}" @click="choose(item,index)"
         v-for="(item,index) in menuList" :key="index">
      <div class="item-new">{{item[text]}}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    text: {
      type: String,
      default: 'name'
    },
    chooseLeftIndex: [String, Number],
    menuList: {
      type: Array,
      default: () => {
        return [
          {
            name: '小明'
          },
          {
            name: '小明'
          },
          {
            name: '小明'
          },
          {
            name: '小明'
          },
          {
            name: '小明'
          },
          {
            name: '小明'
          },
        ]
      }
    }
  },
  // 局部注册的组件
  components: {},
  // 组件状态值
  computed: {},
  // 侦听器
  watch: {
    chooseLeftIndex: {
      handler(newV) {
        this.currentIndex = newV
      },
      immediate: true
    }
  },
  data() {
    return {
      currentIndex: 0,
    }
  },
  // 计算属性
  created() {
  },
  mounted() {
  },
  // 组件方法
  methods: {
    choose(e, i) {
      this.currentIndex = i
      const obj = {
        obj: e,
        index: i
      }
      this.$emit('chooseOne', obj)
    }
  },
}
</script> 

<style lang='scss' scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.menu {
  // width: 100%;
  height: 100%;
  &-item {
    width: 100%;
    height: 40px;
    @include flex;
    color: #666666;
    font-size: 16px;
  }

  &-item:hover {
    color: rgba($color: #dc963c, $alpha: 0.6);
    background-color: #e8e0d7;
  }
}
.item-new {
  width: 124px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  text-align: center;
}
.chooseOne {
  color: #dc963c;
  background-color: rgba(201, 196, 192, 0.4) !important;
}
</style>