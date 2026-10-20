<template>
  <div v-loading="loading" class="iframe">
    <iframe
      :src="src"
      frameborder="no"
      style="width: 100%; height: 100%"
      scrolling="auto"
    />
  </div>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
      loading: true,
      src: "",
    };
  },
  async mounted() {
    setTimeout(() => {
      this.loading = false;
    }, 300);
    const { data: config } = await axios.get('/map.json')
    let { username='admin',password='admin123',path} = this.$route.query;
    let sPath = this.$route.path.replace('/gridManagement','')
    path = path||`businessCenter${sPath}`
    // this.src = `http://localhost:9091/redirectWithLogin?path=${path}&username=${username}&password=${password}&isIframe=true`
    this.src = `${config.base_url}/redirectWithLogin?path=${path}&username=${username}&password=${password}&isIframe=true`
   
  },
  computed: {
    
  },
  methods:{
  
  }
};
</script>
<style lang="scss" scoped>
.iframe{
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
}

.filter-box{
  width: 250px;
  top:12px;
  left: 90px;
  position: absolute;
}

.filter-btn {
  width: 110px;
  height: 30px;
  background: url('../assets//images/btn.png') no-repeat;
  background-size: 100% 100%;
  font-size: 16px;
  color: #fff;
  text-align: center;
  line-height: 30px;
  font-weight: 800;
  margin-left: 10px;
  cursor: pointer;
  position: absolute;
  overflow: hidden;
  top:0px;
  right: 0px;
}
.right120{
  right: 120px;
}

.filter-box3 {
    width: 120px;
    height: 200px;
    border: 1px solid #00F6FF;
    background: linear-gradient(180deg, #0F3243 0%, #2BADB9 100%);
    border-radius: 8px;
    position: absolute;
    right: 0px;
    top: 50px;
    >div{
      color: #fff;
      text-align: center;
      line-height: 40px;
      font-size: 16px;
      cursor: pointer;
      &:hover{
        background: #00F6FF30;
      }
    }
  }
</style>
