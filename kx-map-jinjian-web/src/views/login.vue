<template>
  <div class="login">
    <div class="header">
      <div class="picture">
        <img src="../assets/logo/logo-white.png" alt="" />
      </div>
      <div class="title">网格化后台管理系统</div>
    </div>
    <div class="content">
      <div class="main">
        <div class="logo">
          <img src="../assets/images/login-logo.png" alt="" />
        </div>
        <div class="system">
          <div class="contain">
            <div class="contain-header">系统登录</div>
            <div class="contain-form">
              <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-position="top" class="login-form">
                <el-form-item prop="username" label="账户名称">
                  <el-input class="elinput" v-model="loginForm.username" type="text" auto-complete="off"
                    placeholder="请输入登录系统的账户" />
                </el-form-item>
                <el-form-item prop="password" label="输入密码">
                  <el-input class="elinput" v-model="loginForm.password" type="password" auto-complete="off"
                    placeholder="请输入登录系统的密码" @keyup.enter.native="handleLogin" />
                </el-form-item>
                <el-form-item prop="code" label="验证码" v-if="captchaEnabled">
                  <el-input class="elinput" v-model="loginForm.code" auto-complete="off" placeholder="请输入验证码"
                    @keyup.enter.native="handleLogin" />
                  <div class="login-code">
                    <img :src="codeUrl" @click="getCode" class="login-code-img" />
                  </div>
                </el-form-item>
                <el-checkbox v-model="loginForm.rememberMe" style="margin: 0px 0px 39px 0px">记住密码</el-checkbox>
                <el-form-item style="width: 100%" class="login-btn">
                  <el-button :loading="loading" size="medium" type="primary" style="width: 100%"
                    @click.native.prevent="handleLogin">
                    <span v-if="!loading">登 录</span>
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
  background-image: url("../assets/images/login-background.png");
  background-size: cover;
}

.header {
  display: flex;
  align-items: center;

  .picture {
    width: 41px;
    height: 41px;
  }

  .title {
    margin-left: 20px;
    width: 360px;
    height: 41px;
    font-size: 38px;
    color: #fff;
  }
}

.content {
  width: 1354px;
  height: 713px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../assets/images/login-bg.png");
  position: relative;

  .main {
    position: absolute;
    top: 59px;
    left: 64px;
    width: 1236px;
    height: 592px;
    box-shadow: 0px 5px 49px 0px rgba(74, 113, 240, 0.23);

    .logo {
      margin-left: 60px;
      margin-top: 5px;
      width: 600px;
      height: 600px;
    }

    .system {
      position: absolute;
      right: 15px;
      top: 18px;
      width: 540px;
      height: 560px;
      background: #f6f6f6;
      border-radius: 12px;
      opacity: 0.9;

      .contain {
        width: 320px;
        margin: 0 auto;

        &-header {
          margin-top: 52px;
          margin-left: 2px;
          height: 33px;
          font-size: 34px;
          font-weight: 600;
          color: #5e81f4;
          line-height: 48px;
        }

        &-form {
          margin-top: 33px;
          width: 320px;
          height: 320px;

          ::v-deep .el-form-item__label {
            height: 18px;
            font-size: 18px;
            color: #666666;
            margin-bottom: 18px;
          }

          ::v-deep .el-input__inner {
            height: 18px;
            font-size: 18px;
            color: #b6b6b6;
            padding: 19px 18px 18px 2px;
            position: relative;
            background-color: transparent;
            border: none;
            border-radius: 0;
            border-bottom: 1px solid #b6b6b6;
          }

          ::v-deep .el-input__inner:focus {
            color: #5e81f4;
            border-bottom: 1px solid #5e81f4;
          }

          ::v-deep .el-checkbox__inner {
            border: 1px solid #666;
            border-radius: 50%;
          }

          ::v-deep .el-checkbox__label {
            font-size: 14px;
            color: #4a507f;
          }

          .login-btn {
            display: flex;
            justify-content: center;
            align-content: center;

            ::v-deep .el-form-item__content {
              width: 170px;
              height: 47px;
            }

            ::v-deep .el-button {
              background: #5e81f4;
              box-shadow: 0px 11px 14px 2px rgba(84, 123, 213, 0.35);
              border-radius: 4px;
              font-size: 20px;
            }
          }

          .login-code {
            position: absolute;
            right: 1px;
            top: -25px;

            img {
              width: 138px;
              height: 52px;
            }
          }
        }
      }
    }
  }
}
</style>
