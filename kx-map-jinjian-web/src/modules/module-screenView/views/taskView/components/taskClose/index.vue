<template>
  <div class="task-close">
    <div class="contain">
      <div class="task-box">
        <div class="task-detail-icon"></div>
        <div class="task-close-text">{{ text }}</div>
      </div>
      <div v-if="needClose" class="task-close-icon el-icon-close" @click="close">关闭</div>
    </div>
    <!-- <div class="task-close-line"></div> -->
  </div>
</template>

<script>
export default {
  name: "",
  props: {
    text: String,
    needClose: {
      type: Boolean,
      default: true
    }
  },
  components: {},
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    close() {
      this.$emit('close', false)
      //关闭
    }
  },
};
</script> 

<style scoped lang='scss'>
.task-close {
  border-bottom: 1px solid rgba($color: #B7FBFC, $alpha: .2);
  .contain {
    display: flex;
    align-items: center;
    justify-content: space-between;
    .task-box {
      display: flex;
      align-items: center;
      color: white;
      .task-detail-icon {
        width: 47px;
        height: 35px;
        background-size: 100% 100%;
        background-image: url("./img/taskDetail.png");
      }
      .task-close-text {
        margin-left: 10px;
        font-size: 18px;
      }
    }
    .task-close-icon{
      color: #78EFFC;
      cursor: pointer;
    }
  }
}
</style>
