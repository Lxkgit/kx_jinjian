<template>
  <div class="basic-info">
    <tableTitle title="基础信息">
      <template #right v-if="type == 'edit'">
        <div v-if="showEdit">
          <el-button type="primary" @click="edit">编辑</el-button>
        </div>
        <div v-else>
          <el-button type="primary" @click="saveEdit">保存</el-button>
          <el-button @click="showEdit = true">取消</el-button>
        </div>
      </template>
    </tableTitle>

    <div class="info-form">
      <i-form
        ref="baseForm"
        :columns="baseColumns"
        label-width="130px"
        labelPosition="right"
        :gridColumns="3">
      </i-form>
      <div class="branchEquipment">
        <div class="title">网点机具</div>
        <el-button type="primary" style="margin-right: 8px;" v-if="!showEdit" @click="openAddEquipment">添加<svg-icon icon-class="add-white" style="margin-left: 8px;"></svg-icon></el-button>
        <div class="equipment-item" v-if="equipmentList.length == 0">暂无机具</div>
        <template v-else>
          <div class="equipment-item" v-for="(item,index) in equipmentList" :key="index">
            {{ item.name }}: {{ item.value}}
          </div>
        </template>
      </div>
    </div>

    <div style="margin-top: 6px;">
      <tableTitle title="网点功能">
      <template #left>
          <div class="add-content" @click="openAddFunction" v-if="!showEdit">
            <svg-icon icon-class="add" class="add-icon"></svg-icon>
            <span>功能选择</span>
          </div>
        </template>
      </tableTitle>
      <div class="funcion-list">
        <div class="function-item" v-if="funcitonList.length == 0">暂无机具</div>
        <template v-else>
          <div class="function-item" v-for="(item,index) in funcitonList" :key="index">
            {{ item.name }}
          </div>
        </template>
      </div>
    </div>

    <div v-if="showEdit">
      <tableTitle title="管理网格信息" class="content-title">
      </tableTitle>
      <div class="content-content">
        <i-table
          class="table-height"
          ref="table"
          :data="tableData"
          align="center"
          stripe
          showBorder
          maxHeight="145px"
          :allColumns="columns"
          :showPagination="false"
        >
          <template #action="scope">
            <el-button @click="ImportDetail(scope.row)">详情</el-button>
          </template>
        </i-table>
      </div>
    </div>

    <div v-else>
      <tableTitle title="现金服务信息" class="content-title">
      </tableTitle>
      <i-form
        style="width: 67%;"
        ref="moneyForm"
        :columns="moneyColumns"
        label-width="150px"
        labelPosition="right"
        :gridColumns="2">
      </i-form>
    </div>

    <!-- 添加机具弹窗 -->
    <el-dialog
      title="添加网点机具"
      :visible.sync="equipmentDialogVisible"
      width="600px"
      :close-on-click-modal="false"
      custom-class="equipment-dialog"
    >
      <div class="equipment-form">
        <div v-for="(item, index) in equipmentOptions" :key="index" class="equipment-item-row">
          <el-checkbox v-model="item.checked">{{ item.label }}</el-checkbox>
          <el-input 
            v-model="item.value" 
            :disabled="!item.checked"
            :placeholder="'请输入' + item.label"
            size="small"
            class="equipment-input"
          ></el-input>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="equipmentDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAddEquipment">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 网点功能选择弹窗 -->
    <el-dialog
      title="网点功能选择"
      :visible.sync="functionDialogVisible"
      width="600px"
      :close-on-click-modal="false"
      custom-class="function-dialog"
    >
      <div class="function-form">
        <div v-for="(item, index) in functionOptions" :key="index" class="function-item-row">
          <el-checkbox v-model="item.checked">{{ item.label }}</el-checkbox>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="functionDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAddFunction">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import services from "../api";
export default {
  components: {
    tableTitle,
  },
  props:{
    type:{
      type:String,
      default:''
    }
  },
  data(){
    return {
      showEdit:true,
      baseColumns:[
        {label: "网点名称", key: "branchName", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "网点地址", key: "branchAddr", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "所属银行机构", key: "parentOrgNum", value: "", type: "text", disabled: true, placeholder: " ",},
        {label: "网点负责人", key: "branchChargePerson", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "负责人固定电话", key: "chargeTelephone", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "负责人移动电话", key: "chargeMobilePhone", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "所属网格", key: "parentDeptName", value: "", type: "text", disabled: true, placeholder: " ",},
        {label: "所属网格网格长", key: "gridManager", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "网格长联系电话", key: "gridManagerPhone", value: "", type: "text", disabled: () => this.showEdit, placeholder: " ",},
        {label: "网点介绍", key: "branchDesc", value: "", type: "textarea", span: 3, disabled: () => this.showEdit, placeholder: " ",},
      ],
      equipmentList:[
        {name:'网点服务柜台数',value:3},
      ],
      funcitonList:[
        {name:'本外币现金存取',value:3},
        {name:'真伪授权鉴定',value:2},
        {name:'自助现金存取',value:4},
        {name:'停止流通人民币兑换',value:4},
      ],
      tableData:[],
      columns:[
        { label: "网格名称", prop: "1" },
        { label: "所在街道", prop: "2" },
        { label: "所在区域", prop: "3" },
        { label: "操作", slot:true },
      ],
      moneyColumns:[
        {label:'网点零钱包兑换数量',key:'changeWalletsNum',value:'',type:'text'},
        {label:'网点零钱包兑换次数',key:'changeWalletsCount',value:'',type:'text'},
        {label:'残损币收兑次数',key:'brokenCoinCount',value:'',type:'text'},
        {label:'残损币收兑金额',key:'brokenCoinAmount',value:'',type:'text'},
        {label:'硬币兑换枚数',key:'coinExchangeCount',value:'',type:'text'},
        {label:'硬币兑换金额',key:'coinExchangeAmount',value:'',type:'text'},
      ],
      equipmentDialogVisible: false,
      equipmentOptions: [
        { label: '网点服务柜台数', value: '', checked: false, key: 'artificialCashNum' },
        { label: '自助现金ATM存取', value: '', checked: false, key: 'autoCashMachinesNum' },
        { label: '小额硬币服务且具具备批量功能(整换零)', value: '', checked: false, key: 'smallCashMachinesNum' },
        { label: '小额硬币兑换机具', value: '', checked: false, key: 'coinExchangeFlat' },
        { label: '存取款一体机CRS', value: '', checked: false, key: 'integratedMachine' },
        { label: '现金柜台机', value: '', checked: false, key: 'cashCounterMachine' },
        { label: '硬币兑换机具', value: '', checked: false, key: 'coinExchangeMachine' }
      ],
      functionDialogVisible: false,
      functionOptions: [
        { label: '人民币现金存存', checked: false },
        { label: '本外币现金存取', checked: false },
        { label: '小面额纸币兑换', checked: false },
        { label: '数字人民币服务', checked: false },
        { label: '假币鉴别', checked: false },
        { label: '真伪授权鉴定', checked: false },
        { label: '特殊残损人民币兑换', checked: false },
        { label: '停止流通人民币兑换', checked: false },
        { label: '外币清分机鉴定', checked: false },
        { label: '纪念币纪念钞服务', checked: false }
      ],
    }
  },
  mounted() {
      const { type,id,branchInfo } = this.$route.query
      // 组件挂载后等待DOM渲染完成再获取数据
      this.$nextTick(() => {
        if (id) this.getBankInfo(id)
      })
   },
  methods:{
    //获取网点信息详情
    async getBankInfo(id) {
        try {
          const res = await services.selectBankInfo({id})
          if (res && res.data) {
            const data = res.data;
            // 填充基础信息表单
            const baseFormData = {
              branchName: data.branchName || '', // 网格名称/编码
              branchAddr: data.branchAddr || '', // 网点地址
              parentOrgNum: data.parentOrgNum || '', // 所属银行机构
              branchChargePerson: data.branchChargePerson || '', // 网点负责人
              chargeTelephone: data.chargeTelephone || '', // 负责人固定电话
              chargeMobilePhone: data.chargeMobilePhone || '', // 负责人移动电话
              parentDeptName: data.parentDeptName || '', // 所属网格
              gridManager: '', // 所属网格长，接口数据中可能没有，需补充
              gridManagerPhone: '', // 网格长联系电话，接口数据中可能没有，需补充
              branchDesc: data.branchDesc || '', // 网点介绍
            };
            
            // 检查表单引用是否存在
            if (this.$refs.baseForm && this.$refs.baseForm.setForm) {
              this.$refs.baseForm.setForm(baseFormData);
            } else {
              console.warn('baseForm引用不存在，无法设置表单值');
              // 可以考虑使用setTimeout延迟设置或其他方式
              setTimeout(() => {
                this.$refs.baseForm && this.$refs.baseForm.setForm(baseFormData);
              }, 100);
            }
            
            // 处理网点机具数据
            if (data.branchMachines) {
              try {
                // 如果是字符串，尝试解析JSON
                let machinesData = typeof data.branchMachines === 'string' ? 
                  JSON.parse(data.branchMachines) : data.branchMachines;
                
                this.equipmentList = [
                  {name: '网点服务柜台数', value: data.artificialCashNum || 0},
                  {name: '现金机具数量', value: data.autoCashMachinesNum || 0},
                  {name: '自助机具数量', value: data.smallCashMachinesNum || 0},
                ];
              } catch (e) {
                console.error('解析网点机具数据失败', e);
              }
            }
            
            // 处理网点功能
            if (data.branchFunction) {
              try {
                // 如果是字符串，尝试解析JSON
                let functionData = typeof data.branchFunction === 'string' ? 
                  JSON.parse(data.branchFunction) : data.branchFunction;
                
                // 如果是数组直接使用，否则可能需要处理成特定格式
                this.funcitonList = Array.isArray(functionData) ? 
                  functionData.map(item => ({name: item})) : 
                  [{name: data.branchFunction}];
              } catch (e) {
                console.error('解析网点功能数据失败', e);
                // 如果解析失败，尝试处理为字符串
                if (data.branchFunction && typeof data.branchFunction === 'string') {
                  const funcArray = data.branchFunction.split(',').filter(Boolean);
                  this.funcitonList = funcArray.map(item => ({name: item}));
                }
              }
            }
            
            // 填充现金服务信息
            if (!this.showEdit && this.$refs.moneyForm && this.$refs.moneyForm.setForm) {
              const moneyFormData = {
                changeWalletsNum: data.changeWalletsNum || '',
                changeWalletsCount: data.changeWalletsCount || '',
                brokenCoinCount: data.brokenCoinCount || '',
                brokenCoinAmount: data.brokenCoinAmount || '',
                coinExchangeCount: data.coinExchangeCount || '',
                coinExchangeAmount: data.coinExchangeAmount || '',
              };
              this.$refs.moneyForm.setForm(moneyFormData);
            }
          } else {
            this.$message.warning('未获取到网点数据');
          }
        } catch (error) {
          console.error('获取网点详情失败', error);
          this.$message.error('获取网点详情失败');
        }
    },
    edit() {
      this.showEdit = false;
    },
    //保存按钮
    async saveEdit() {
      if (!this.$refs.baseForm) {
        this.$message.warning('表单不存在，无法保存');
        return;
      }

      try {
          // 获取基础表单数据
          let baseFormData
          this.$refs.baseForm.getForm((formInfo) => {
            baseFormData = formInfo
          });
          
          
          // 获取现金服务表单数据
          let moneyFormData = {};
          if (this.$refs.moneyForm) {
            this.$refs.moneyForm.getForm((formInfo) => {
              moneyFormData = formInfo
          }) 
          }

          // 处理机具数据 - 将equipmentList转换为接口需要的字段
          let machineData = {};
          this.equipmentList.forEach(item => {
            switch(item.name) {
              case '网点服务柜台数':
                machineData.artificialCashNum = parseInt(item.value) || 0;
                break;
              case '自助现金ATM存取':
                machineData.autoCashMachinesNum = parseInt(item.value) || 0;
                break;
              case '小额硬币服务且具具备批量功能(整换零)':
                machineData.smallCashMachinesNum = parseInt(item.value) || 0;
                break;
              case '小额硬币兑换机具':
                machineData.coinExchangeFlat = parseInt(item.value) || 0;
                break;
              case '存取款一体机CRS':
                machineData.integratedMachine = parseInt(item.value) || 0;
                break;
              case '现金柜台机':
                machineData.cashCounterMachine = parseInt(item.value) || 0;
                break;
              case '硬币兑换机具':
                machineData.coinExchangeMachine = parseInt(item.value) || 0;
                break;
            }
          });

          // 处理功能数据 - 将funcitonList转换为字符串
          const branchFunction = this.funcitonList.map(item => item.name).join(',');

          // 将机具信息序列化为JSON字符串
          const branchMachines = JSON.stringify(this.equipmentList);
          const branchInfo = this.$route.query.branchInfo
          // 构建保存数据对象
          const saveData = {
            //必填信息
            id: this.$route.query.id,
            gridCode: baseFormData.gridCode || '',
            parentId:branchInfo.branchInfo,
            parentDeptName: baseFormData.parentDeptName || '',
            parentOrgNum: baseFormData.parentOrgNum || '',
            orgNum:branchInfo.orgNum,
            // 基础信息
            branchName:baseFormData.branchName || '',
            branchAddr: baseFormData.branchAddr || '',
            branchChargePerson: baseFormData.branchChargePerson || '',
            chargeTelephone: baseFormData.chargeTelephone || '',
            chargeMobilePhone: baseFormData.chargeMobilePhone || '',
            branchDesc: baseFormData.branchDesc || '',
            
            // 网点功能和机具
            branchFunction: branchFunction,
            branchMachines: branchMachines,
            
            // 机具数据
            ...machineData,
            
            // 现金服务数据
            changeWalletsNum: parseInt(moneyFormData.changeWalletsNum) || 0,
            changeWalletsCount: parseInt(moneyFormData.changeWalletsCount) || 0,
            brokenCoinCount: parseInt(moneyFormData.brokenCoinCount) || 0,
            brokenCoinAmount: parseFloat(moneyFormData.brokenCoinAmount) || 0,
            coinExchangeCount: parseInt(moneyFormData.coinExchangeCount) || 0,
            coinExchangeAmount: parseFloat(moneyFormData.coinExchangeAmount) || 0,
          };

          console.log('保存数据:', saveData);
          
          // 调用保存接口
          const res = await services.updateBankInfo(saveData);
          if (res && res.code === 200) {
            this.$message.success('保存成功');
            this.showEdit = true;
            // 重新获取数据刷新页面
            this.getBankInfo(this.$route.query.id);
          } else {
            this.$message.error(res.msg || '保存失败');
          }
        } catch (error) {
          console.error('保存失败', error);
          this.$message.error('保存失败：' + (error.message || '未知错误'));
        }
    },
    //机具添加
    openAddEquipment() {
      this.equipmentDialogVisible = true;
      
      // 先重置所有选项
      this.equipmentOptions.forEach(item => {
        item.checked = false;
        item.value = '';
      });
      
      // 将已有的equipmentList数据回显到弹窗中
      if (this.equipmentList && this.equipmentList.length > 0) {
        this.equipmentList.forEach(equipment => {
          const matchOption = this.equipmentOptions.find(option => option.label === equipment.name);
          if (matchOption) {
            matchOption.checked = true;
            matchOption.value = equipment.value;
          }
        });
      }
    },
    confirmAddEquipment() {
      const selectedOptions = this.equipmentOptions.filter(item => item.checked);
      if (selectedOptions.length === 0) {
        this.$message.warning('请选择要添加的网点机具');
        return;
      }

      // 先检查所有选中的选项是否都填写了数量
      const emptyValueItem = selectedOptions.find(item => !item.value);
      if (emptyValueItem) {
        this.$message.warning(`请输入${emptyValueItem.label}的数量`);
        return;
      }

      const newEquipmentList = [];
      selectedOptions.forEach(item => {
        // 检查是否已存在相同名称的项
        const existingIndex = this.equipmentList.findIndex(e => e.name === item.label);
        if (existingIndex !== -1) {
          // 更新现有项
          this.equipmentList[existingIndex].value = item.value;
        } else {
          // 添加新项
          newEquipmentList.push({
            name: item.label,
            value: item.value,
            key: item.key
          });
        }
      });

      if (newEquipmentList.length > 0) {
        this.equipmentList = [...this.equipmentList, ...newEquipmentList];
      }
      this.equipmentDialogVisible = false;
      this.$message.success('网点机具添加成功');
    },
    //网点功能选择
    openAddFunction() {
      this.functionDialogVisible = true;
      
      // 先重置所有选项
      this.functionOptions.forEach(item => {
        item.checked = false;
      });
      
      // 将已有的funcitonList数据回显到弹窗中
      if (this.funcitonList && this.funcitonList.length > 0) {
        this.funcitonList.forEach(functionItem => {
          const matchOption = this.functionOptions.find(option => option.label === functionItem.name);
          if (matchOption) {
            matchOption.checked = true;
          }
        });
      }
    },
    confirmAddFunction() {
      const selectedOptions = this.functionOptions.filter(item => item.checked);
      if (selectedOptions.length === 0) {
        this.$message.warning('请选择要添加的网点功能');
        return;
      }

      const newFunctionList = [];
      selectedOptions.forEach(item => {
        // 检查是否已存在相同名称的项
        const existingIndex = this.funcitonList.findIndex(f => f.name === item.label);
        if (existingIndex !== -1) {
          // 更新现有项
          this.funcitonList[existingIndex].value = item.value; 
        } else {
          // 添加新项
          newFunctionList.push({
            name: item.label,
            value: item.value, 
            key: item.label 
          });
        }
      });

      if (newFunctionList.length > 0) {
        this.funcitonList = [...this.funcitonList, ...newFunctionList];
      }
      this.functionDialogVisible = false;
      this.$message.success('网点功能添加成功');
    },
  },
}
</script>

<style lang="scss" scoped>
.basic-info {
  position: relative;
}
.info-form {
  margin-top: 12px;
}
.branchEquipment {
  display: flex;
  align-items: center;
  .title {
    width: 130px;
    text-align: right;
    color:#606266;
    padding-right: 12px;
  }
}
.equipment-item {
  width: auto;
  white-space: nowrap;
  padding:0 12px;
  text-align: center;
  line-height: 36px;
  height: 36px;
  border-radius: 4px;
  background: rgba(94, 129, 244, 0.1);
  color: #5E81F4;
}
.equipment-item:not(:first-child){
  margin-left: 8px;
}
.add-content {
  margin-left: 12px;
  cursor: pointer;
  font-size: 14px;
  color: #5E81F4;
  text-decoration: underline;
  line-height: 14px;
  .add-icon {
    width: 14px;
    height: 14px;
    margin-right: 4px;
  }
}
.funcion-list {
  display: flex;
  align-items: center;
  margin-left: 130px;
  margin-top: 5px;
  .function-item {
    width: auto;
    white-space: nowrap;
    padding:0 12px;
    text-align: center;
    line-height: 36px;
    height: 36px;
    border-radius: 4px;
    background: rgba(36, 213, 174, 0.1);
    color: #24D5AE;
  }
  .function-item:not(:first-child){
    margin-left: 8px;
  }
}

/* 添加机具弹窗样式 */
.equipment-dialog {
  .equipment-form {
    max-height: 400px;
    overflow-y: auto;
  }
  .equipment-item-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;
    
    .el-checkbox {
      width: 200px;
      margin-right: 10px;
      margin-left: 0;
      font-size: 14px;
      color: #494D68 !important;
    }
    
    .equipment-input {
      width: 180px;
    }
  }
}

/* 修改Element UI 样式 */
::v-deep .equipment-dialog .el-dialog__body {
  padding: 20px 60px;
}

::v-deep .equipment-dialog .el-checkbox__label {
  font-size: 14px;
}

::v-deep .equipment-dialog .el-input__inner {
  text-align: center;
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
  padding: 20px;
}
::v-deep .el-dialog__footer {
  padding: 12px 24px;
  border-top: 1px solid #e8e8e8;
}
/* 网点功能选择弹窗样式 */
.function-dialog {
  .function-form {
    max-height: 200px;
    display:flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }
  .function-item-row {
    display: flex;
    align-items: center;
    margin-bottom: 16px;
    
    .el-checkbox {
      width: 200px;
      margin-right: 10px;
      margin-left: 0;
      font-size: 14px;
      color: #494D68 !important;
    }
  }
}

/* 修改Element UI 样式 */
::v-deep .function-dialog .el-dialog__body {
  padding: 20px 60px;
}

::v-deep .function-dialog .el-checkbox__label {
  font-size: 14px;
}
</style>