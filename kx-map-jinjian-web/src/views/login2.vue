<template>
  <div class="login">

    <div class="header">
      <!-- <img src="../assets/images/header.png" alt="" /> -->
      <!-- <div class="title">防非打非“金哨”案档与台账管理平台</div> -->
    </div>
    <div class="content">
      <div class="planet-big"></div>
      <!-- 登录页内容 -->
      <div class="main" v-show="!showSelectRole">
        <div class="planet" />
        <div class="logo">
          <!-- <img src="../assets/images/login_newBg.png" alt="" /> -->
        </div>
        <div class="system">
          <div class="contain">
            <div class="contain-header">
              <div>防非打非“金哨”案档与台账管理平台</div>
              <!-- <div style="margin-top: 8px;">网格化管理系统</div> -->
            </div>
            <div class="contain-form">
              <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="top" class="login-form">
                <el-form-item prop="username">
                  <el-input class="elinput" v-model="loginForm.username" type="text" auto-complete="off"
                    placeholder="请输入登录系统的账户" />
                </el-form-item>
                <el-form-item prop="password">
                  <el-input class="elinput" v-model="loginForm.password" type="password" auto-complete="off"
                    placeholder="请输入登录系统的密码" @keyup.enter.native="toGetSelect" />
                </el-form-item>
                <el-form-item prop="code" v-if="captchaEnabled">
                  <el-input class="elinput-code" v-model="loginForm.code" auto-complete="off" placeholder="请输入验证码"
                    @keyup.enter.native="toGetSelect" />
                  <div class="login-code">
                    <img :src="codeUrl" @click="getCode" class="login-code-img" />
                  </div>
                </el-form-item>
                <el-checkbox v-model="loginForm.rememberMe" style="margin: 0px 0px 20px 0px">记住密码</el-checkbox>
                <el-form-item style="width: 100%" class="login-btn">
                  <el-button :loading="loading" size="medium" type="primary" style="width: 100%"
                    @click.native.prevent="toGetSelect">
                    <span v-if="!loading">立即登录</span>
                    <span v-else>登 录 中...</span>
                  </el-button>
                  <div style="float: right" v-if="register">
                    <router-link class="link-type" :to="'/register'">立即注册</router-link>
                  </div>
                </el-form-item>
              </el-form>
            </div>

            <div class="contain-footer"></div>
          </div>
        </div>
      </div>
      <!-- 角色选择内容 -->
       <div class="select" v-show="showSelectRole">
          <div class="login-out" @click="showSelectRole = false">
            <svg-icon icon-class="login-out" class="icon"></svg-icon>
            <div class="out-text">退出登录</div>
          </div>
          <div class="select-content">
            <div class="select-title">角色选择</div>
            <div class="select-line"></div>
            <div class="select-box">
              <div class="box-item" v-for="(item,index) in roleList" :key="index" @click="activeRole = item.role" :class="activeRole == item.role ? 'active' : ''">
                <div class="box-content">
                  <svg-icon :icon-class="item.icon" class="icon"></svg-icon>
                  <div class="name"> {{ item.role }} </div>
                </div>
              </div>
            </div>
            <div class="select-btn" @click="handleLogin">
              <span v-if="!loading">确认</span>
              <span v-else>登 录 中...</span>
            </div>
          </div>
       </div>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";

export default {
  name: "Login",
  data() {
    return {
      roleList:[
        {role:'浙江省人民银行管理员',icon:'province-select'},
        {role:'杭州市人民银行管理员',icon:'city-select'},
      ],
      activeRole:'',
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" },
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" },
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }],
      },
      loading: false,
      // 验证码开关
      captchaEnabled: true,
      // 注册开关
      register: false,
      redirect: undefined,
      showSelectRole:false,
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true,
    },
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  methods: {
    getCode() {
      getCodeImg().then((res) => {
        this.captchaEnabled =
          res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30,
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30,
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || "/" }).catch(() => { });
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaEnabled) {
                this.getCode();
              }
            });
        }
      });
    },
    //跳转到选择角色弹框
    toGetSelect() {
      this.$refs.loginForm.validate((valid) => {
        if(valid) {
          // this.showSelectRole = true
          this.handleLogin()
        }
      })
    }
  },
};
</script>

<style scoped lang="scss">
.login {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
  position: relative;
  // background-image: url("../assets/images/login-background.png");
  background-color: #edf0f6;
  overflow: hidden;
  // background-image: url("../assets/images/login_bg.jpg");
  // background-size: cover;
}

.header {
  height: 20px;

  .title {
    font-size: 38px;
    color: #fff;
    font-family: "Helvetica Neue"
  }
}

.content {
  width: 1116px;
  height: 650px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/images/login-bg.png");
  position: relative;
  background: #FFFFFF;
  padding: 16px;
  border-radius: 24px;
  box-shadow: 14px 14px 40px 4px rgba(32, 51, 177, 0.1),-4px -4px 30px 0px rgba(32, 51, 177, 0.1),-14px -14px 40px 4px rgba(255, 255, 255, 0.4);
  .planet-big {
      width: 460px;
      height: 250px;
      position: absolute;
      bottom: -170px;
      left: -260px;
      background-image: url('../assets/images/planet.png');
      background-size: cover;
    }

  .main {
    width: 100%;
    height: 100%;
    border-radius: 14px;
    display: flex;


    .planet {
      position: absolute;
      top: -10px;
      right: 16px;
      width: 270px;
      height: 253px;
      background-image: url('../assets/images/planet1.png');
      background-size: cover;
      z-index: 2;
    }
    .logo {
      width: 500px;
      height: 618px;
      background-image: url('../assets/images/login_newBg.png');
      background-size: 100% 100%;
      z-index: 2;
    }

    .system {
      width: calc(100% - 500px);
      height: 100%;
      position: relative;

      .contain {
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        right: 0;
        height: 400px;
        margin: auto;
        display: flex;
        flex-direction: column;
        align-items: center;


        &-header {
          font-size: 24px;
          color: #323548;
          font-weight: bold;
          display: flex;
          flex-direction: column;
          align-items: center;
        }

        &-form {
          margin-top: 20px;
          ::v-deep .el-form-item__label {
            height: 14px;
            font-size: 14px;
            color: #666666;
            margin-bottom: 12px;
          }

          ::v-deep .is-required {
            margin-bottom: 14px;
          }

          ::v-deep .el-input__inner {
            width: 456px;
            height: 48px;
            font-size: 14px;
            color: #323548;
            position: relative;
            background: #F6F7FA;
            border-color: #F6F7FA;
            border-radius: 4px;
            margin-top: 12px;
          }

          ::v-deep .el-checkbox__inner {
            border: 1px solid #666;
            border-radius: 50%;
          }

          ::v-deep .is-checked .el-checkbox__inner {
            border: 1px solid #5e81f4 !important;
            border-radius: 50%;
          }

          ::v-deep .el-checkbox__label {
            font-size: 14px;
            color: #4a507f;
          }
          .elinput-code{
            ::v-deep .el-input__inner {
              width: 318px;
            }
          }

          .login-btn {
            display: flex;
            justify-content: center;
            align-content: center;

            ::v-deep .el-form-item__content {
              width: 456px;
              height: 48px;
            }

            ::v-deep .el-button {
              height: 100%;
              background: #5e81f4;
              box-shadow: 0px 11px 14px 2px rgba(84, 123, 213, 0.35);
              border-radius: 4px;
              font-size: 16px;
            }
          }

          .login-code {
            position: absolute;
            right: 1px;
            top: 12px;


            img {
              width: 120px;
              height: 48px;
              border:1px solid #f6f7fa;
              cursor: pointer;
            }
          }
        }
      }
    }
  }
  .select {
    width: 100%;
    height: 100%;
    background: #F6F7FA;
    position: relative;
    .login-out {
      position: absolute;
      right: 24px;
      top: 24px;
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      .icon {
        width: 16px;
        height: 16px;
        margin-right: 8px;
      }
      .out-text {
        color: #323548;
        font-size: 14px;
      }
    }
    .select-content {
      position: absolute;
      left: 0;
      top: 0;
      right: 0;
      bottom: 0;
      margin: auto;
      width: auto;
      height: 450px;
      display: flex;
      flex-direction: column;
      align-items: center;
      .select-title {
        font-size: 24px;
        color: #323548;
        font-weight: bold;
      }
      .select-line {
        width: 20px;
        height: 4px;
        background: #5E81F4;
        border-radius: 2px;
        margin-top: 24px;
      }
      .select-box {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 54px;
        width: 650px;
        .box-item {
          width: 308px;
          height: 204px;
          border-radius: 26px;
          background: #FFFFFF;
          box-sizing: border-box;
          border: 1px solid #E0E2EC;
          position: relative;
          cursor: pointer;
          .box-content {
            width: 200px;
            height: 120px;
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: center;
          }
          .icon {
            width: 80px;
            height: 80px;
          }
          .name {
            font-size: 18px;
            color: #323548;
            font-weight: bold;
          }
        }
        .active {
          border: 1px solid #7789FF;
          box-shadow: 4px 4px 10px 4px rgba(32, 51, 177, 0.1),-4px -4px 10px 4px #FFFFFF;
        }
      }
      .select-btn {
        margin-top: 94px;
        width: 456px;
        height: 48px;
        text-align: center;
        line-height: 48px;
        border-radius: 4px;
        background: #5E81F4;
        color: #FFFFFF;
        cursor: pointer;
      }
    }
  }
}
</style>
