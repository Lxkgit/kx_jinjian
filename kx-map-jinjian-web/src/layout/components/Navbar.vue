<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container"
               @toggleClick="toggleSideBar" />
    <tags-view v-if="needTagsView" class="breadcrumb-container"/>
    <breadcrumb v-else id="breadcrumb-container" class="breadcrumb-container" />
  </div>
</template>

<script>
import TagsView from './TagsView'
import { mapGetters } from 'vuex'
import Breadcrumb from '../cell/Breadcrumb'
import Hamburger from '../cell/Hamburger'
import { mapState } from 'vuex'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    TagsView
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'device'
    ]),
    ...mapState({
      needTagsView: state => state.settings.tagsView,
    }),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
  }
}
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/variables.scss";
.navbar {
  height: $base-tags-height;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  .hamburger-container {
    line-height: $base-tags-height;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;
    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
}
</style>
