<template>
  <div class="iframe-wrapper">
    <iframe id="pdf-box" :src="linkUrl" frameborder="0"
            :style="{'height':'100%','width':'100%'}"></iframe>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: "",
  props: {},
  data() {
    return {
      linkUrl: "",
      subUrl: '',
      param: ''
    };
  },
  computed: {
    ifHeight() {
      return document.body.clientHeight + 'px';
    }
  },
  watch: {},
  async created() {

  },
  mounted() {
    this.getSrc()
    this.getToken();

  },
  methods: {
    getSrc() {
      const arr = this.$route.path.split('/')
      let i = 0
      let j = 0
      while (j < arr.length) {
        if (arr[i] === 'params') {
          if (j < i) j = i + 1
          this.param = `${arr[j]}=${arr[j + 1]}`
          j += 2
        }
        if (arr[j] === 'returnUrl') {
          if (i < j) i = j + 1
          this.subUrl += `/${arr[i]}`
          i++
        } else {
          j++
        }
      }
      console.log(this.subUrl, this.param)
    },
    // 前端加密方式
    aesEncrypt(data, keyStr, ivStr) {
      let CryptoJS = require('crypto-js');
      let key = CryptoJS.enc.Utf8.parse(keyStr);
      let iv = CryptoJS.enc.Utf8.parse(ivStr);
      let encrypted = CryptoJS.AES.encrypt(data, key, {
        iv: iv
      });

      var encryptedBase64Str = encrypted.toString().replace(/\//g, "_");
      return encryptedBase64Str;
    },

    getToken() {
      var nowTime = this.moment().unix();
      var tenant = "tyzt";
      var name = "18163569569"; // 前端获取的子账号唯一标识，不可写死，不可为中文。子账号数有限制
      var key = "EF2FBE9FE8374A12";
      var iv = "EE609351DF00401C";
      var returnUrl = `https://per-plugin.qixin.com${this.subUrl}?${this.param}`;

      var str = `${tenant}+${name}+${nowTime}`;
      var token = this.aesEncrypt(str, key, iv);
      var sig = token.replace(/\+/g, "%2B");


      // 授权接口
      var url = "//b-plugin.qixin.com/authTokenApi/fetch_token?tenant=" + tenant +
        "&sig=" +
        sig;
      axios
        .get(url)
        .then((res) => {
          // 获取token 拼接打开启信慧眼
          this.linkUrl =
            "https://b-plugin.qixin.com/third-login?tenant=" +
            tenant +
            "&token=" +
            res.data.token +
            "&returnUrl=" +
            returnUrl;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style scoped lang="scss">
.iframe-wrapper {
  height: 100%;
  overflow: hidden;
}
</style>
