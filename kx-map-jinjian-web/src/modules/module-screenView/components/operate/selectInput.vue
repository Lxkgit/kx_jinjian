<template>
    <div class="customer-filter-wrapper">
        <el-form class="elform" label-position="top" label-width="80px" :model="form">
            <el-form-item label="银行机构">
                <el-select class="full-width-select" v-model="form.selectedBank" placeholder="选择银行机构"
                    @change="handleChange" popper-class="mypopper">
                    <el-option v-for="bank in banks" :key="bank.value" :label="bank.label" :value="bank.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="银行机构分类">
                <el-select class="full-width-select" v-model="form.bankType" placeholder="选择银行机构分类"
                    @change="handleChange" popper-class="mypopper">
                    <el-option v-for="bankType in bankTypes" :key="bankType.value" :label="bankType.label"
                        :value="bankType.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="城乡网点分类">
                <el-select class="full-width-select" v-model="form.cityType" placeholder="选择城乡网点分类"
                    @change="handleChange" popper-class="mypopper">
                    <el-option v-for="cityType in cityTypes" :key="cityType.value" :label="cityType.label"
                        :value="cityType.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="地区分类">
                <el-select class="full-width-select" v-model="form.area" placeholder="地区分类" popper-class="mypopper">
                    <el-option v-for="area in areaTypes" :key="area.value" :label="area.label" :value="area.value">
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <div class="filter-footer">
            <div class="filter-btn" @click="resetHandle">取消</div>
            <div class="filter-btn confirm-btn" @click="confirmHandle">确定</div>
        </div>
    </div>
</template>

<script>

export default ({
    data() {
        return {
            activeValue: '1',
            tabList: [
                { label: '类型筛选', value: '1' },
                { label: '客群筛选', value: '2' },
            ],
            showSelect: false,
            groupSelected: [],
            form: {
                selectedBank: '',
                bankType: '',
                cityType: '',
                area: ''
            },
            banks: [
                { value: 'bank1', label: '工商银行' },
                { value: 'bank2', label: '建设银行' },
                { value: 'bank3', label: '农业银行' },
                { value: 'bank4', label: '中国银行' },
                { value: 'bank5', label: '交通银行' },
            ],
            bankTypes: [
                { value: 'bankType1', label: '国有商业银行' },
                { value: 'bankType2', label: '股份制商业银行' },
                { value: 'bankType3', label: '中国邮政储蓄银行' },
                { value: 'bankType4', label: '城市商业银行' },
                { value: 'bankType5', label: '农村商业银行' },
                { value: 'bankType6', label: '村镇银行' },
                { value: 'bankType7', label: '外资银行' },
            ],
            cityTypes: [
                { value: 'cityType1', label: '社区' },
                { value: 'cityType2', label: '行政村' },
            ],
            areaTypes: [
                { value: 'hangz', label: '杭州' },
                { value: 'nb', label: '宁波' },
                { value: 'wz', label: '温州' },
                { value: 'sx', label: '绍兴' },
                { value: 'hz', label: '湖州' },
                { value: 'jx', label: '嘉兴' },
                { value: 'jh', label: '金华' },
                { value: 'qz', label: '衢州' },
                { value: 'zs', label: '舟山' },
                { value: 'tz', label: '台州' },
                { value: 'ls', label: '丽水' }
            ]
        }
    },
    computed: {
        filterType() {
            return this.$store.getters['screenView/filterType']
        },
    },
    watch: {
        showSelect(val) {
            if (val) {
                this.activeValue = this.filterType
                // this.$refs.multiLevelSelect.initData()
                this.$refs.groupSelect.initData()
            }
        }
    },
    mounted() {
        this.activeValue = '2'
        this.confirmHandle()
    },
    methods: {
        checkTab({ value }) {
            this.activeValue = value
        },
        resetHandle() {
            this.showSelect = false
            // let param
            // console.log(11, this.activeValue);
            // switch (this.activeValue) {
            //   case '1':
            //     param = {
            //       typeList: ['2'],
            //     }
            //     this.$store.commit('screenView/SET_FILTER_PARAM', param)
            //     this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
            //     // this.$refs.multiLevelSelect.initData()
            //     break
            //   case '2':
            //     param = {
            //       groupIdList: [],
            //       groupCategory: ''
            //     }
            //     this.$store.commit('screenView/SET_FILTER_PARAM', param)
            //     this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
            //     this.$refs.groupSelect.initData()
            //     break
            //   default:
            //     break
            // }
        },
        confirmHandle() {
            this.showSelect = false
            // this.$store.commit('screenView/SET_FILTER_TYPE', this.activeValue)
            // switch (this.activeValue) {
            //   // case '1':
            //   //   this.$refs.multiLevelSelect.getCurrentData(info => {
            //   //     const { checkList, checkChildList, checkType } = info
            //   //     if (checkList.length === 0) {
            //   //       this.$modal.msgWarning('类型筛选不能为空')
            //   //       return
            //   //     }
            //   //     const param = {
            //   //       typeList: checkList,
            //   //     }
            //   //     param[checkType] = checkChildList
            //   //     this.$store.commit('screenView/SET_FILTER_PARAM', param)
            //   //     this.showSelect = false
            //   //   })
            //   //   break
            //   case '2':
            //     this.$refs.groupSelect.getCurrentData(info => {
            //       // if (info.groupCategory === '') {
            //       //   this.$modal.msgWarning('客群筛选不能为空')
            //       //   return
            //       // }
            //       if (info.groupIdList[0] == null) {
            //         info.groupIdList = []
            //       }
            //       info.groupCategory = '2'
            //       this.$store.commit('screenView/SET_FILTER_PARAM', info)
            //       this.showSelect = false
            //     })
            //     break
            //   default:
            //     break
            // }

        },
        handleChange(value) {
            console.log(value);
        }
    },
})
</script>

<style lang="scss" scoped>
.customer-filter-wrapper {
    width: 620px;
    height: 448px;
    // background: url(~@/modules/module-screenView/assets/image/customerFilterbg.png) no-repeat;
    background-size: 100%;
    border-radius: 8px;
    // border: 1px solid #3278F1;
    position: relative;
    // padding: 15px 0px;

    .filter-header {
        height: 54px;
        line-height: 54px;
        color: #fff;
        font-size: 20px;
        padding-left: 24px;
        font-weight: bold;
        border-bottom: 1px solid rgba(59, 161, 255, 0.2);

    }

    .elform {
        padding: 0 100px;
    }

    .filter-tab {
        width: 185px;
        line-height: 32px;
        margin: auto;
        background: rgba($color: #3278F1, $alpha: 0.2);
        border-radius: 6px;
        color: #3278F1;
        font-size: 16px;
        display: flex;

        >li {
            width: 50%;
            text-align: center;
            cursor: pointer;

            &.active {
                color: #fff;
                border-radius: 6px;
                background: #3278F1;
            }
        }
    }

    .filter-content {
        width: calc(100% - 3px);
        padding: 0 3px;
        margin: auto;
        height: calc(100% - 37px);
        overflow: auto;

        &::-webkit-scrollbar {
            width: 3px;
        }

        &::-webkit-scrollbar-track {
            background: transparent;
        }

        &::-webkit-scrollbar-thumb {
            background: #3278F1;
            border-radius: 2px;
        }
    }

    .filter-footer {
        display: flex;
        justify-content: right;
        height: 72px;
        border-top: 1px solid rgba(59, 161, 255, 0.2);
        padding: 16px 0;

        .filter-btn {
            cursor: pointer;
            width: 120px;
            line-height: 40px;
            background: rgba($color: #294e59, $alpha: 0.6);
            border-radius: 6px;
            font-size: 20px;
            color: #007AEE;
            text-align: center;
            border: 1px solid #007AEE;
            margin: 0 10px;
            background: transparent;
        }

        .confirm-btn {
            color: #fff;
            background: #007AEE;
            border: 1px solid #007AEE;
        }
    }

    ::v-deep .el-form-item__label {
        color: #fff;
        font-size: 16px;
        padding: 0px;
    }

    ::v-deep .el-form-item {
        padding-bottom: 0px;
        margin-bottom: 16px;
    }


    .full-width-select {
        width: 100%;




        ::v-deep .el-input__inner {
            border-radius: 8px;
            color: #fff !important;
            background: linear-gradient(180deg, #5392FF 0%, rgba(16, 39, 93, 0.72) 100%);
            border: 1px solid #5392FF;
        }



    }


}
</style>