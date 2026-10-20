<template>
  <div class="manager-task-feed-back">
    <task-close  style="margin-top:15px" @close="close"  text="任务反馈"></task-close>
    <manager-task-form-value style="margin-top:20px" @close="close"></manager-task-form-value>
  </div>
</template>

<script>
import taskClose from "../taskClose";
import managerTaskFormValue from './managerTaskFormValue';
export default {
  name: '',
  props: {form:Object},
  components: {
    taskClose,
    managerTaskFormValue
  },
  data () {
   return {}
  },
  computed: {},
  watch: {},
  created () {},
  mounted () {},
  methods: {
    close(e) {
      this.$emit('close',e)
    }
  },
}
</script> 

<style scoped lang='scss'>
.manager-task-feed-back{
  background-image: url('./imgs/managerTaskBac.png');
  background-size: 100% 100%;
  padding: 0 20px;
  
}
</style>
