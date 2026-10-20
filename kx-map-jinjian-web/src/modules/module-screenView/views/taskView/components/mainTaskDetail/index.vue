<!-- 主任务视图 -->
<template>
  <div class="main-task-detail">
    <div class="masking-out"></div>
    <task-close class="task-close"  text="任务基本详情" @close="close"></task-close>
    <main-task-form-value class="main-task-form-value" :form="form"></main-task-form-value>
  </div>
</template>

<script>
import taskClose from "../taskClose";
import mainTaskFormValue from "../mainTaskFormValue";
export default {
  name: "mainTaskDetail",
  props: {form:Object},
  components: {
    taskClose,
    mainTaskFormValue,
  },
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    close(e) {
      this.$emit('close', e)
    }
  },
};
</script> 

<style scoped lang='scss'>
.main-task-detail{
  background-image: url('./img/managerBac.png');
  background-size: 100% 100%;
  overflow: scroll;
  position: relative;
  .masking-out{
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 2;
  }
  .task-close{
    position: relative;
    z-index: 3;
    padding-top: 5px;
  }
  .main-task-form-value{
    margin-top: 40px;
  }
  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
