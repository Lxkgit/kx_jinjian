<template>
  <div class="sidebar-logo-container">
    <transition name="sidebarLogoFade">
      <router-link key="expand" class="sidebar-logo-link" to="/">
        <!-- <img v-if="logo" :src="logo" class="sidebar-logo" /> -->
        <h1 class="sidebar-title">
          <!-- {{ title }} -->
         防非打非“金哨”案档与台账管理平台
        </h1>
      </router-link>
    </transition>
  </div>
</template>

<script>
import logoImg from "@/modules/module-screenView/assets/image/logo.png";
import variables from "@/assets/styles/variables.scss";

export default {
  name: "SidebarLogo",
  props: {
    collapse: {
      type: Boolean,
      required: true,
    },
  },
  computed: {
    variables() {
      return variables;
    },
    sideTheme() {
      return this.$store.state.settings.sideTheme;
    },
  },
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      logo: logoImg,
    };
  },
};
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/variables.scss";

.sidebarLogoFade-enter-active {
  transition: opacity 1.5s;
}

.sidebarLogoFade-enter,
.sidebarLogoFade-leave-to {
  opacity: 0;
}

.sidebar-logo-container {
  position: relative;
  width: 100%;
  height: $base-header-height;
  line-height: $base-header-height;
  // text-align: center;
  overflow: hidden;

  & .sidebar-logo-link {
    height: 100%;
    width: 100%;
    display: flex;
    // justify-content: center;
    align-items: center;
    margin-left: 50px;
    padding: 0 10px;

    & .sidebar-logo {
      width: 42px;
      height: 42px;
      vertical-align: middle;
      margin-right: 23px;
    }

    & .sidebar-title {
      flex: 1;
      display: inline-block;
      margin: 0;
      color: #fff;
      font-weight: 600;
      line-height: $base-header-height;
      font-size: 24px;
      font-family: Noto Sans S Chinese;
      vertical-align: middle;
      white-space: nowrap;
    }
  }

  &.collapse {
    .sidebar-logo {
      margin-right: 0px;
    }
  }
}
</style>
