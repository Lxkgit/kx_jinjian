<template>
  <!-- <div id="app" v-watermark="{text:waterInfo,size:30,color:'#9993'}"> -->
    <div id="app" >
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
export default {
  name: "App",
  components: { ThemePicker },
  // metaInfo() {
  //   return {
  //     title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
  //     titleTemplate: title => {
  //       return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
  //     }
  //   }
  // },
  data() {
    return {}
  },
  computed: {
    waterInfo() {
      return this.$store.getters['waterInfo'] ?? '网格营销管理平台'
    },
  },
  created() {
    // //在页面加载时读取sessionStorage里的状态信息
    // if (this.$cache.session.getJSON("store")) {
    //   this.$store.replaceState(Object.assign({}, this.$store.state, this.$cache.session.getJSON("store")))
    // }
    // //在页面刷新时将vuex里的信息保存到sessionStorage里
    // window.addEventListener("beforeunload", () => {
    //   this.$cache.session.setJSON("store", this.$store.state)
    // })
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
