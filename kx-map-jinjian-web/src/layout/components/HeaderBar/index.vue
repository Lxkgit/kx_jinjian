<template>
  <div class="header-bar-wrapper">
    <div class="logo-wrapper">
      <logo :collapse="isCollapse" />
    </div>
    <top-nav id="topmenu-container" class="topmenu-container" />
    <div class="right-menu">
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="avatar-wrapper">
          <!-- <img class="user-avatar" :src="avatar" alt="">
           <span class="avatar-name">{{ name }}<i class="el-icon-caret-bottom"></i></span> -->
          <i class="user-avatar el-icon-user-solid"></i>
          <span class="avatar-name">{{ name }}<i class="el-icon-caret-bottom"></i></span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>个人中心</el-dropdown-item>
          </router-link>
          <!-- <el-dropdown-item @click.native="setting = true">
            <span>布局设置</span>
          </el-dropdown-item> -->
          <el-dropdown-item divided @click.native="logout">
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Logo from "../Sidebar/Logo";
import TopNav from '../../cell/TopNav'
export default ({
  components: {
    Logo,
    TopNav
  },
  computed: {
    ...mapGetters(["sidebar", 'avatar', 'name']),
    isCollapse() {
      return !this.sidebar.opened;
    },
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
  },
  methods: {
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/';
        })
      }).catch(() => { });
    }
  },
})
</script>

<style lang="scss" scoped>
@import "~@/assets/styles/variables.scss";
.header-bar-wrapper {
  height: $base-header-height;
  display: flex;
  box-shadow: 5px 6px 8px 0px rgba(153, 153, 153, 0.18);
  background: $base-header-bg;
  .logo-wrapper {
    width: $logo-width;
  }
  .topmenu-container {
    flex: 1;
  }
  .right-menu {
    height: 100%;
    line-height: $base-header-height;
    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #fff;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        position: relative;
        display: flex;
        align-items: center;
        .user-avatar {
          display: inline-block;
          cursor: pointer;
          width: 42px;
          height: 42px;
          background: #fff;
          border-radius: 50%;
          display: flex;
          justify-content: center;
          align-items: center;
          font-size: 22px;
          margin: 0 16px;
          color: #4a507f;
        }
        .avatar-name {
          display: inline-block;
          font-size: 18px;
          font-weight: 400;
          color: #fff;
          line-height: $base-header-height;
          > i {
            margin-left: 14px;
          }
        }
      }
    }
  }
}
</style>