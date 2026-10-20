<template>
    <div class="small-title">
        <div class="cirlce"></div>
        <div class="title">{{ title }}</div>
    </div>
</template>

<script>
export default {
    props:{
        title:{type:String,default:''},
    }
}
</script>

<style lang="scss" scoped>
.small-title {
    display: flex;
    align-self: center;
    .cirlce {
        width: 16px;
        height: 16px;
        border-radius: 50%;
        background: rgba(255,229,52,0.3);
        position:relative
    }
    .cirlce::after {
        content:'';
        position: absolute;
        width: 8px;
        height: 8px;
        border-radius: 50%;
        background: #FFB600;
        left:50%;
        top: 50%;
        transform: translate(-50%,-50%);
    }
    .title {
        font-size: 14px;
        color: #557BF9;
        font-weight: 500;
        margin-left: 8px;
    }
}
</style>