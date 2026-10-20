<template>
  <div>
    <IDialog :title="title" v-model="showDialog" width="30%" @handleClose="handleClose"
             @handleConfirm="handleConfirm">
      <i-form ref="myForm" :columns="columns" labelWidth="100px" labelPosition="right">
        <template #address>
          <div class="address-wrapper">
            <el-cascader ref="network" v-model="formData.networkCode" class="address-cascader"
                         :props="props" @change="changeHandle"></el-cascader>
            <el-input v-model="formData.buildingAddress" class="address-input"></el-input>
          </div>
        </template>
        <template #lonAndLat>
          <div class="address-wrapper">
            <el-input disabled v-model="formData.lonAndLat"></el-input>
            <el-button type="primary" class="address-btn" @click="mapEdit">地图锚点</el-button>
          </div>
        </template>
      </i-form>

    </IDialog>
    <anchorEdit v-model="showEdit" :coordinate="coordinate" @mapEditHandle="mapEditHandle">
    </anchorEdit>
  </div>

</template>

<script>
import anchorEdit from './anchorEdit.vue';
import dialogMixin from '@/mixins/dialogMixin.vue';
import service from '../api';
const TITLE_TYPE = {
  'add': '新增',
  'edit': '编辑'
}
export default ({
  mixins: [dialogMixin],
  dicts: ['aggregate_point_type'],
  components: {
    anchorEdit
  },
  props: {
    status: {
      type: String,
    },
    editInfo: {
      type: Object,
    },
  },
  data() {
    return {
      formData: {
        networkCode: [],
        buildingAddress: '',
        lonAndLat: '',
      },
      showEdit: false,
      props: {
        value: 'code',
        label: 'name',
        lazy: true,
        checkStrictly: true,
        lazyLoad: this.lazyLoad
      },
      columns: [
        {
          label: "楼宇类型",
          key: "buildingType",
          value: "",
          type: "select",
          options: () => this.dict.type.aggregate_point_type,
          rules: [
            { required: true, message: '楼宇类型不能为空', trigger: 'change' }
          ]
        },
        {
          label: "楼宇名称",
          key: "buildingName",
          value: "",
          type: "text",
          rules: [
            { required: true, message: '楼宇名称不能为空', trigger: 'blur' }
          ]
        },
        {
          label: "楼宇地址",
          key: "buildingAddress",
          type: 'slot',
          slotName: 'address',
          required: true
        },
        {
          label: "经纬度",
          key: "lonAndLat",
          slotName: 'lonAndLat',
          type: "slot",
          required: true
        },
        {
          label: "层数",
          key: "floorNum",
          value: "",
          type: "text",
        },
        {
          label: "单元数",
          key: "unitNum",
          value: "",
          type: "text",
        },
        {
          label: "户数",
          key: "householdNum",
          value: "",
          type: "text",
        },
      ],
      coordinate: [],
    }
  },
  computed: {
    title() {
      return TITLE_TYPE[this.status]
    },
  },
  methods: {
    initData() {
      if (this.status === 'add') {
        return
      }
      this.$nextTick(() => {
        this.editInfo.buildingType = this.editInfo.buildingType + ''
        this.$refs.myForm.setForm(this.editInfo)
        this.formData.buildingAddress = this.editInfo.buildingAddress
        const { networkCode } = this.editInfo
        this.formData.networkCode = networkCode.split(',')
        this.formData.lonAndLat = `${this.editInfo.lon},${this.editInfo.lat}`
      })
    },
    lazyLoad(node, resolve) {
      const { value } = node
      const param = {
        parentCode: value
      }
      service.getPaAddrInfoList(param).then(res => {
        const { data } = res
        data.forEach(item => {
          item.leaf = !item.isLeaf
        })
        resolve(data)
      })
    },
    handleClose() {
      this.formData = {
        networkCode: [],
        buildingAddress: '',
        lonAndLat: '',
      }
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(formInfo => {
        const c = this.formData.lonAndLat.split(',')
        if (this.status === 'add') {
          const param = {
            ...formInfo,
            ...this.formData,
            networkCode: this.formData.networkCode.join(','),
            lon: c[0],
            lat: c[1],
          }
          service.buildingAnchorPointAdd(param).then(res => {
            this.$modal.msgSuccess('新增成功')
            this.handleClose()
            this.$parent.fetchList()
          })
        } else {
          const param = {
            ...formInfo,
            ...this.formData,
            networkCode: this.formData.networkCode.join(','),
            id: this.editInfo.id,
            lon: c[0],
            lat: c[1],
          }
          service.buildingAnchorPointEdit(param).then(res => {
            this.$modal.msgSuccess('编辑成功')
            this.handleClose()
            this.$parent.fetchList()
          })
        }
      })
    },
    changeHandle(data) {
      console.log(data);
      const [temp] = this.$refs.network.getCheckedNodes()
      const { data: { centerPoint } } = temp
      this.formData.lonAndLat = JSON.parse(centerPoint).join(',')
    },

    mapEditHandle(point) {
      this.formData.lonAndLat = point.coordinates.join(',')
    },
    mapEdit() {
      this.coordinate = this.formData.lonAndLat.split(',')
      this.showEdit = true
    },
  },
})
</script>

<style lang="scss" scoped>
.address-wrapper {
  display: flex;
  .address-cascader {
    flex: 1;
  }
  .address-input {
    width: 200px;
    margin-left: 8px;
  }
  .address-btn {
    margin-left: 8px;
  }
}
</style>