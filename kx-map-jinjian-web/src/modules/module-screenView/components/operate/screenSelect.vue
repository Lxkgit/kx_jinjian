<template>
  <div class="screen-select-wrapper" v-clickoutside="()=>showSelect=false">
    <div class="selected-name" @click="showSelect = !showSelect">{{ activeName }}</div>
    <div class="select-list" v-show="showSelect">
      <ul class="list-content">
        <li v-for="item in screenRouters" :key="item.name" @click="clickHandle(item.name)">
          {{ item.meta.title }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default ({
  data() {
    return {
      showSelect: false
    }
  },
  computed: {
    screenRouters() {
      return this.$store.getters['screenRouters']
    },
    activeName() {
      return this.$route.meta.title
    },
  },
  mounted() {

  },
  methods: {
    clickHandle(routeName) {
      this.$router.push({ name: routeName })
    },
    handleClose() {
      this.showSelect = false
    }
  },
})
</script>

<style lang="scss" scoped>
.screen-select-wrapper {
  margin-right: 14px;
  .selected-name {
    width: 120px;
    height: 40px;
    background: url("../../assets/image/btn-bg.png") no-repeat;
    background-size: 100% 100%;
    font-size: 18px;
    color: #fff;
    text-align: center;
    line-height: 40px;
    font-weight: 800;
    cursor: pointer;
  }
  .select-list {
    width: 120px;
    max-height: 278px;
    background: linear-gradient(
      180deg,
      rgba($color: #31474f, $alpha: 0.9),
      rgba($color: #1a242b, $alpha: 0.9)
    );
    border-radius: 8px;
    border: 1px solid rgba($color: #00f6ff, $alpha: 0.5);
    margin-top: 10px;
    display: flex;
    overflow: scroll;
    scrollbar-width: none;
    -ms-overflow-style: none;
    &::-webkit-scrollbar {
      display: none;
    }
    .list-content {
      width: 120px;
      margin: 10px 0;
      list-style: none;
      padding: 0;
      li {
        font-size: 18px;
        font-weight: bold;
        color: #fff;
        line-height: 42px;
        text-align: center;
        list-style-type: none;
        padding: 0;
        cursor: pointer;
        &:hover {
          color: #00f6ff;
          background: #00000040;
        }
      }
    }
  }
}
</style>