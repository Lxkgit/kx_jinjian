<template>
  <div class="person-info-wrapper"
       :style="{'--width':width,'--height':height,'--minHeight': minHeight}">
    <div class="info-content">
      <img class="info-avatar" :src="avatar" alt="">
      <div class="info-main">
        <div class="info-item info-user">欢迎回来, <span>{{ user.userName }}</span></div>
        <div class="info-item info-value">所属部门: <span>{{ user.dept&&user.dept.deptName }}</span>
        </div>
        <div class="info-item info-value">所属角色: <span>{{ roleGroup }}</span></div>
      </div>
    </div>
    <!-- <div class="info-operate">
      <el-button>修改密码</el-button>
    </div> -->
  </div>
</template>

<script>
import { getUserProfile } from "@/api/system/user";
export default ({
  props: {
    width: {
      type: String,
    },
    height: {
      type: String,
    },
    minHeight: {
      type: String
    },
  },
  data() {
    return {
      user: {},
      roleGroup: {},
    }
  },
  computed: {
    avatar() {
      return this.$store.getters.avatar
    }
  },
  created() {
    this.getUser();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
      });
    }
  }
})
</script>

<style lang="scss" scoped>
.person-info-wrapper {
  width: var(--width);
  height: var(--height);
  min-height: var(--minHeight);
  background: url(../assets/intro-bg.png) no-repeat;
  background-size: 100% 100%;
  box-shadow: 0px 2px 3px 0px rgba(70, 19, 12, 0.08);
  margin-bottom: 15px;
  position: relative;

  .info-content {
    padding: 8% 10% 0;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .info-avatar {
      width: 24%;
      border-radius: 50%;
    }
    .info-main {
      color: #fff;
      font-size: 16px;
      .info-item {
        line-height: 1.5;
        > span {
          display: inline-block;
          margin-left: 10px;
        }
      }
      .info-user {
        line-height: 2;
        > span {
          font-size: 24px;
          font-weight: bold;
        }
      }
    }
  }
  .info-operate {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    bottom: 10px;
    ::v-deep .el-button {
      color: #7088d5;
    }
  }
}
</style>