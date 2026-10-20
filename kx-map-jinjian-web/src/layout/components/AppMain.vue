<template>
  <section class="app-main">

    <!-- 临时处理 -->
    <transition name="fade-transform" mode="out-in" v-if="$route.name=='MapExploration'">
      <keep-alive >
        <router-view v-if="!$route.meta.link" :key="key" />
      </keep-alive>
    </transition>


    <transition name="fade-transform" mode="out-in" v-else>
      <keep-alive :include="cachedViews">
        <router-view v-if="!$route.meta.link" :key="key" />
      </keep-alive>
    </transition>

   
    <iframe-toggle />
  </section>
</template>

<script>
import iframeToggle from "./IframeToggle/index"

export default {
  name: 'AppMain',
  components: { iframeToggle },
  computed: {
    cachedViews() {
      return this.$store.state.tagsView.cachedViews
    },
    key() {
      return this.$route.path
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/variables.scss";
.app-main {
  height: calc(100vh - 100px);
  width: 100%;
  position: relative;
  overflow: auto;
}

.fixed-header + .app-main {
  padding-top: 100px;
}

.hasTagsView {
  .app-main {
    /* 134 = navbar + tags-view + headerBar = 50 + 34 + 50 */
    // height: calc(100vh - 134px);
    height: calc(100vh - #{$base-header-height} - #{$base-tags-height}) ;
    // width: calc(100% - 10px);
    width: 100%;
    // height: 100%;
    padding: 12px;
    // margin: 10px 0 0 10px;
    // background: #fff;
  }

  .fixed-header + .app-main {
    padding-top: $base-header-height + $base-tags-height;
  }
}
</style>

<style lang="scss">
// fix css style bug in open el-dialog
.el-popup-parent--hidden {
  .fixed-header {
    padding-right: 6px;
  }
}

::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background-color: #f1f1f1;
}

::-webkit-scrollbar-thumb {
  background-color: #c0c0c0;
  border-radius: 3px;
}
</style>
