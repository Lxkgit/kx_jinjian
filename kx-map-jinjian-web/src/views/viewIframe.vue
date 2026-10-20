<template>
  <div v-loading="loading" class="iframe">
    <div class="filter-box" v-if="allShow">
      <div class="filter-btn" @click="changeShow">切换场景</div>
      <div class="filter-btn right120" @click="businessHandle">业务中心</div>
       <!-- 切换场景 -->
      <div class="filter-box3" v-show="show" ref="filterMenu">
        <div @click="changeView(0)">基础网格</div>
        <div @click="changeView(1)">特色场景</div>
      </div>
    </div>

    <iframe
      :src="src"
      frameborder="no"
      style="width: 100%; height: 100%"
      scrolling="auto"
    />
  </div>
</template>
<script>
export default {
  data() {
    return {
      loading: true,
      src: "",
      show:false,
      allShow:false
    };
  },
  mounted: function () {
    setTimeout(() => {
      this.loading = false;
    }, 300);
    // this.src= '/screenView/baseView'
    this.src= '/screenView/baseView'
    setTimeout(()=>{
        this.allShow = true
    },3500)
  },
  computed: {
    addRoutes() {
      return this.$store.getters['addRoutes']
    }
  },
  methods:{
    changeShow(){
      this.show = !this.show
    },
    changeView(i){
      let arr = [ '/screenView/baseView','http://10.1.200.5:8888/redirectWithLogin?path=%2F&username=chongqing&password=123456']
      // let arr = [ '/screenView/baseView','http://localhost:9091/redirectWithLogin?path=%2F&username=chongqing&password=123456']
      this.src= arr[i]
      this.allShow = false
      this.show = false
      setTimeout(()=>{
        this.allShow = true
      },3500)
    },
    businessHandle() {
      const {name,path} = this.getRouterName()
      this.$router.push({ name })
    },
    getRouterName() {
      let target = ''
      for (let route of this.addRoutes) {
        if (route.name === 'GridManagement') {
          target = route.children[0]
        }
      }
      return target
    }
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
