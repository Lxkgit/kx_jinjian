<template>
  <ul class="person-list-wrapper">
    <li v-for="(item,index) in personList" :key="index">
      {{ getName(item) }}
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    personList: {
      type: Array,
      default: [
        { name: '花傲天' },
        { name: '花傲地' },
        { name: '花傲人' },
        { name: '小花' },
      ]
    },
  },
  methods: {
    getName(name) {
      return name.length > 2 ? name.slice(-2) : name
    },
  },
})
</script>

<style lang="scss" scoped>
.person-list-wrapper {
  display: flex;
  > li {
    width: 40px;
    height: 40px;
    line-height: 40px;
    border-radius: 50%;
    background: #03a7f0;
    color: #fff;
    text-align: center;
    margin-right: 8px;
  }
}
</style>