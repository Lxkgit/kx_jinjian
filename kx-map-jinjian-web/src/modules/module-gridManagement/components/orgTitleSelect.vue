<!-- 机构标题组件 -->
<template>
    <div class="org-title">
        <div class="org-left">
            <div class="org-img"></div>
            <template v-if="title">
                <span class="title-text">{{ title }}</span>
            </template>
            <span v-else class="title-text">暂无机构</span>
        </div>
        <div class="org-right">
            <slot name="right"></slot>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        title:{
            type:String,
            default:''
        }
    },
    methods:{
    }
}
</script>

<style lang="scss" scoped>
.org-title {
    width: 100%;
    height: 64px;
    padding: 20px 16px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background: #FFFFFF;
    box-shadow: 1px 0px 6px 0px #EBEEF5;
    .org-left {
        display: flex;
        align-items: center;
    }
    .org-right {
        .el-btn {
            width: 96px;
            height: 32px;
            line-height: 0;
        }
    }
}

.org-img {
    width: 24px;
    height: 24px;
    background-image: url('~@/modules/module-gridManagement/assets/images/orgSelect.png');
    background-size: 100% 100%;
    background-repeat: no-repeat;
    margin-right: 8px;
}
.title-text{
    font-size: 20px;
    color: #323548;
    font-weight: bolder;
}
</style>