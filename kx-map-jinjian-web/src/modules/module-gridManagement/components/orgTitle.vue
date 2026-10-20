<!-- 机构标题组件 -->
<template>
    <div class="org-title">
        <div class="org-left">
            <div class="org-img" :style="{'background-image':`url(${imgIcon})`}"></div>
            <div v-if="!title">
                <!-- 浙江省/杭州市/拱墅区  类型的标题 -->
                <template v-if="titleArr.length > 0">
                    <span v-for="(item, index) in titleArr" :key="index" class="title-text">
                        <span v-if="item">{{ item}}</span>
                        <span v-if="index != titleArr.length - 1">/</span>
                    </span>
                </template>
                <span v-else class="title-text">暂无机构</span>
            </div>
            <div class="org-left" v-else>
                <!-- 杭州银行  类型的机构标题 -->
                <span class="title-text">{{ title }}</span>
                <slot name="left"></slot>
            </div>
        </div>
        <div class="org-right">
            <el-button class="el-btn" v-if="goBack" @click="$router.go(-1)">返回</el-button>
        </div>
    </div>
</template>

<script>
export default {
    props:{
        titleArr:{
            type:Array,
            default:()=>[]
        },
        title:{
            type:String,
            default:''
        },
        goBack:{
            type:Boolean,
            default:false
        },
        imgIcon:{
            type:String,
            default:require('@/modules/module-gridManagement/assets/images/orgTitle.png')
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
    // background-image: url('~@/modules/module-gridManagement/assets/images/orgTitle.png');
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