<template>
  <div class="task-list">
    <div class="hd">
      <span
        v-for="(i, index) in hd"
        :key="index"
        :style="`width:${i.width}%`"
        v-html="i.name"
      ></span>
    </div>
    <div class="bd">
      <task-item
        :hd="hd"
        v-for="(i, index) in list"
        :key="index"
        :taskInfo="i"
        @getTaskSon="getTaskSon(i.name, index)"
        @checkSonDetail="checkSonDetail"
        @checkDetail="checkDetail"
        @indicatorView="indicatorView(index + 1)"
        @view="(e) => view(e, index)"
      ></task-item>
    </div>
  </div>
</template>
<script>
import TaskItem from "./TaskItem.vue";
export default {
  name: "TaskList",
  components: {
    TaskItem,
  },
  props: {
    hd: {
      type: Array,
      default() {
        return [
          { name: "名称", width: 25 },
          { name: "任务状态", width: 18 },
          { name: "完成率/<br>客户经理", width: 18 },
          { name: "机构", width: 15 },
          { name: "操作", width: 24 },
        ];
      },
    },
    list: Array,
  },
  data() {
    return {
      //请求数据
      // list:[
      //   {name:'四月走访任务',status:0,rata:'80%',dept:'总行',sonList:[
      //     {name:'李彤',status:0,manage:'王小明',dept:'xx支行'},
      //     {name:'李彤',status:1,manage:'王小明',dept:'xx支行x支行'},
      //     {name:'李彤李',status:2,manage:'王小明',dept:'xx支行'},
      //   ]},
      //   {name:'5月任务',status:1,rata:'55%',dept:'总行',sonList:[]},
      //   {name:'6月',status:2,rata:'12%',dept:'xx支行',sonList:[]}
      // ]
    };
  },
  computed: {},
  methods: {
    // 查看主任务详情
    checkDetail(id) {
      this.$emit("checkDetail", id);
    },
    // 查看子任务详情
    checkSonDetail(id) {
      this.$emit("checkSonDetail", id);
    },
    // 指标视图
    indicatorView(e) {
      this.$emit("indicatorView", e);
    },
    view(e, index) {
      this.$emit("view", e, index);
    },
    getTaskSon(name, index) {
      this.$emit("getTaskSon", name, index);
    },
  },
};
</script>
<style lang="scss" scoped>
.task-list {
  width: 100%;
  height: 100%;
  padding: 18px;
  .hd {
    height: 40px;
    background: #125e6a;
    display: flex;
    justify-content: center;
    align-items: center;
    > span {
      display: inline-block;
      font-weight: 500;
      color: #a8e3f2;
      text-align: center;
    }
  }
}
.bd {
  height: calc(100% - 40px);
  overflow-y: scroll;
  &::-webkit-scrollbar {
    display: none;
  }
}
</style>
