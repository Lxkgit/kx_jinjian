<template>
  <div class="bank-detail-info">
    <orgTitle :title="branchName" goBack></orgTitle>
    <div class="detail-info">
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
            <el-tab-pane v-for="(item,index) in tabBtns" :key="index" :name="item.name" :label="item.label">
                <div class="detail-content">
                    <basicInfo v-if="item.name == 'basicInfo'" :type="type"></basicInfo>
                    <gridEvent v-if="item.name == 'gridEvent'" :type="type"></gridEvent>
                    <branchEvent v-if="item.name == 'branchEvent'" :type="type"></branchEvent>
                    <dynamicInfo v-if="item.name == 'dynamicInfo'" :type="type"></dynamicInfo>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
  </div>
</template>

<script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import basicInfo from '../components/basicInfo.vue';
import gridEvent from '../components/gridEvent.vue';
import branchEvent from '../components/branchEvent.vue';
import dynamicInfo from '../components/dynamicInfo.vue';
export default {
    components:{
        orgTitle,basicInfo,gridEvent,branchEvent,dynamicInfo
    },
    data() {
        return {
            tabBtns:[
                {name:'basicInfo',label:'基本信息'},
                {name:'gridEvent',label:'网格事件'},
                {name:'branchEvent',label:'网点事件'},
                {name:'dynamicInfo',label:'动态信息'},
            ],
            activeName:'',
            componentId:'',
            type:'',
            branchName:''
        }
    },
    mounted() {
        this.activeName = this.tabBtns[0]?.name;
        this.componentId = this.tabBtns[0]?.name;
        const { type,id,branchInfo } = this.$route.query
        this.branchName = branchInfo?.branchName
        this.type = type
    },
    methods:{
        handleClick(val) {
            this.activeName = val?.name
            this.componentId = val?.name;
        },
        
        
    }
}
</script>

<style lang="scss" scoped>
::v-deep .el-tabs__nav-scroll {
    margin-left: 16px;
}
::v-deep .el-tabs.el-tabs--card{
    height: 100%;
}
::v-deep .el-tabs__content {
    height: calc(100% - 41px - 16px);
    padding: 0 16px 0;
}
.bank-detail-info {
    height: 100%;
    .detail-info {
        margin-top: 12px;
        width: 100%;
        min-height: calc(100vh - 64px - 56px - 64px - 24px - 12px);
        background: #fff;
        padding-top: 16px;
    }
}
.detail-content {

}
</style>