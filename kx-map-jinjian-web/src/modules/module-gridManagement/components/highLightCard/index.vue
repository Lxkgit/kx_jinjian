<template>
  <div class="hight-light-card" :style="{ '--gridColumns': `repeat(${gridColumns},1fr)`,'--gridRows': `${gridRows}`,'--gap':`${gap}` }">
    <highLightItem v-for="(item,index) in dataArr" :key="index" :data="item" :itemHeight="itemHeight" :background="backgroundArr[index].background"></highLightItem>
  </div>
</template>

<script>
import highLightItem from './highLightItem.vue';
export default {
    components:{ highLightItem },
    props:{
        dataArr:{
            type: Array,
            default:() => [
                { title: '零钞预约金额', value: '1423432432423', unit: '元'},
                { title: '客户主动取消预约笔数', value: '12345', unit: '笔'},
                { title: '受理后客户未兑换笔数', value: '213213', unit: '笔'},
                { title: '实际已兑换金额', value: '1423432432423', unit: '元'},
                { title: '超期未受理笔数', value: '1423432432423', unit: '笔'},
                { title: '已完成笔数', value: '22222222', unit: '笔'},
            ]
        },
        itemHeight: {
            type: String,
            default: '92px'
        },
        gridColumns: { type: String,default: '6',},
        gridRows: { type: String,default: '1fr',},
        gap:{ type: String,default: '12px',},
        backgroundArr:{
            type:Array,
            default:() => [
                { background: require('@/modules/module-gridManagement/assets/images/card-blue.png') },
                { background: require('@/modules/module-gridManagement/assets/images/card-purple.png') },
                { background: require('@/modules/module-gridManagement/assets/images/card-yellow.png') },
                { background: require('@/modules/module-gridManagement/assets/images/card-red.png') },
                { background: require('@/modules/module-gridManagement/assets/images/card-cyan.png') },
                { background: require('@/modules/module-gridManagement/assets/images/card-green.png') },
            ]
        }
    },
    data() {
        return {

        }
    },
}
</script>

<style lang="scss" scoped>
.hight-light-card {
    width: 100%;
    display: grid;
    grid-template-columns: var(--gridColumns);
    grid-template-rows: var(--gridRows);
    gap: var(--gap);
}
</style>