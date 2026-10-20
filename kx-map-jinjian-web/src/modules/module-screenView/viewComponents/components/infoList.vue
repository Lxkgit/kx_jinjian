<template>
  <div class="info-list">
    <div class="item" v-for="i in list">
      <template v-if="!i.isSlot">
        <div class="item-left">{{ i.left }}: </div>
        <div class="item-right">
          <template v-if="!i.isRightSlot">{{ i.right }}</template>
          <slot v-else :name="i.rightSlotName" :item="i"></slot>
        </div>
      </template>
      <slot v-else :name="i.name" :item="i"></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "infoList",
  props:{
    list:{
      type:Array,
      default(){
        return [
        { 
          left:'活动时间',
          right:"2024年7月1日-2024年7月31日"
        },
        {
          isSlot:true,
          name:'test1'
        },
        {
          left:'活动状态',
          isRightSlot:true,
          rightSlotName:'test2'
        },
        {
          left:'活动时间',
          right:"2024年7月1日-2024年7月31日"
        },
      ]
      }
    }
  }
};
</script>

<style scoped lang="scss">
.info-list{
 .item{
  height: 30px;
  display: flex;
  // line-height: 20px;
  font-size: 16px;
  margin: 8px 0;
  .item-left{
    color: #9DBEC1;
    min-width:60px;
    // flex-grow: 0;
  }
  .item-right{
    color: #fefefe;
    padding-left: 10px;
    // flex-grow: 1;
  }
 }
}
</style>