<template>
  <div class="grid-wrapper">
    <div class="left-wrapper">
      <div class="left-header" :title="title">
        {{ title }}
      </div>
      <div class="tree-wrapper">
        <el-tree ref="tree" :props="props" :load="loadNode" lazy @node-contextmenu="openMenu"
                 @node-click="nodeClickHandle" :expand-on-click-node="false"
                 :highlight-current="true" node-key="id">
          <template slot-scope="{ node, data }">
            <span :class="{'tree-disable':node.disabled}">{{ node.label }}</span>
          </template>
        </el-tree>
      </div>
    </div>
    <div class="right-wrapper">
      <component :is="componentId" :mapData="mapData" @clearTree="clearTree"
                 @cancelHandle="nodeClickHandle" :orgOptions="orgOptions" :mapStatus="mapStatus">
      </component>
    </div>
    <ul v-show="visible&&mapStatus==='0'&&!selectLeaf.disabled"
        :style="{left:left+'px',top:top+'px'}" class="contextmenu">
      <li v-if="checkDivision(selectLeaf)" @click="divisionHandle">平分网格</li>
      <li v-if="checkAdd(selectLeaf)" @click="addHandle">新增网格</li>
      <li v-if="checkSplit(selectLeaf)&&checkAdd(selectLeaf)" @click="splitHandle">下切网格</li>
      <li v-if="checkSubDelete(selectLeaf)" @click="mergeHandle">合并子网格</li>
      <li @click="adjustHandle">边界调整</li>
      <li v-if="checkSubDelete(selectLeaf)" @click="deleteSubHandle">删除子网格</li>
      <li v-if="checkDivision(selectLeaf)" @click="deleteHandle">删除</li>
    </ul>
  </div>
</template>

<script>
import gridAdd from '../components/gridAdd';
import gridDivision from '../components/gridDivision';
import gridEdit from '../components/gridEdit';
import gridMerge from '../components/gridMerge';
import gridSplit from '../components/gridSplit';
import gridAdjust from '../components/gridAdjust';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
export default ({
  components: {
    gridAdd,
    gridDivision,
    gridEdit,
    gridMerge,
    gridSplit,
    gridAdjust
  },
  data() {
    return {
      props: {
        label: 'name',
        children: 'children',
        isLeaf: (data) => {
          return !data.isLeaf
        }
      },
      maps: new Map(),
      nodeId: null,
      node_had: undefined,
      resolve_had: undefined,
      visible: false,
      left: 0,
      top: 0,
      selectLeaf: null,
      componentId: gridEdit,
      mapData: {},
      orgOptions: [],
    }
  },
  computed: {
    systemInfo() {
      return this.$store.getters['gridManagement/systemInfo']
    },
    mapStatus() {
      return this.$store.getters['gridManagement/mapStatus']
    },
    title() {
      return this.systemInfo.dictLabel
    }
  },
  watch: {
    visible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }
    }
  },
  mounted() {
    this.deptTreeSelect()
  },
  methods: {
    deptTreeSelect() {
      service.deptTree().then(res => {
        const { data } = res
        this.orgOptions = data
      })
    },
    loadNode(node, resolve, ifShow = false) {
      const { level, data } = node
      const param = {
        typeCode: this.systemInfo.dictCode,
        parentCode: level > 0 ? data.code : '100000',
      }
      service.getPaAddrInfoTypeList(param).then(res => {
        const { data } = res
        data.forEach(item => {
          item.disabled = !item.editable
        })
        if (level === 0) {
          this.rootCodes = data.map(item => item.code)
          this.$nextTick(() => {
            this.mapData = JSON.stringify(this.mapData) === '{}' ? data[0] : this.mapData
            JSON.stringify(this.mapData) === '{}' ? this.$refs.tree.setCurrentKey(data[0].id) : this.$refs.tree.setCurrentKey(this.mapData.id)
          })
        }
        if (data.length > 0 && ifShow) {
          this.nodeClickHandle(data[0])
        }
        resolve(data)
      })
      if (level === 0) {
        this.node_had = node
        this.resolve_had = resolve
        this.rootId = node.id
        this.maps.set(node.id, { node, resolve })
      } else {
        this.maps.set(node.id, { node, resolve })
      }
    },
    clearTree(isShow) {
      if (this.nodeId === this.rootId) {
        this.node_had.childNodes = [];
        this.loadNode(this.node_had, this.resolve_had, isShow);
      } else {
        const { node, resolve } = this.maps.get(this.nodeId)
        this.loadNode(node, resolve, isShow)
      }
    },
    openMenu(e, data, node) {
      this.left = e.clientX
      this.top = e.clientY
      this.visible = true
      this.selectLeaf = data
      this.nodeId = node.parent.id
    },
    closeMenu() {
      this.visible = false
    },
    nodeClickHandle(data, node) {
      const param = {
        typeCode: this.systemInfo.dictCode,
        code: data.code
      }
      service.getPaAddrInfoType(param).then(res => {
        this.mapData = res.data[0]
      })

      node && (this.nodeId = node.parent.id)
      this.componentId = gridEdit
    },
    divisionHandle() {
      this.mapData = this.selectLeaf
      this.componentId = gridDivision
    },
    addHandle() {
      this.mapData = this.selectLeaf
      this.componentId = gridAdd
    },
    splitHandle() {
      this.mapData = this.selectLeaf
      this.componentId = gridSplit
    },
    mergeHandle() {
      this.mapData = this.selectLeaf
      this.componentId = gridMerge
    },
    adjustHandle() {
      this.mapData = this.selectLeaf
      this.componentId = gridAdjust
    },
    deleteSubHandle() {
      const { code, typeCode } = this.selectLeaf
      const param = {
        parentCode: code,
        typeCode,
      }
      this.$modal.confirm('该操作将永久删除所属子网格, 是否确认?').then(() => {
        service.cleanChildGrid(param).then(() => {
          this.$modal.msgSuccess('删除成功')
          this.clearTree(true)
          this.mapData = {}
          this.componentId = gridEdit
        })
      }).catch(() => {

      })
    },
    deleteHandle() {
      const { id } = this.selectLeaf
      this.$modal.confirm('该操作将永久删除该网格, 是否确认?').then(() => {
        service.deleteGrid({ id }).then(() => {
          this.$modal.msgSuccess('删除成功')
          this.clearTree(true)
          this.mapData = {}
          this.componentId = gridEdit
        })
      }).catch(() => {

      })
    },
    checkSubDelete(data) {
      if (data) {
        return data.isLeaf
      }
    },
    checkSplit(data) {
      if (data) {
        return !data.isLeaf
      }
    },
    checkDivision(data) {
      if (data) {
        return !this.rootCodes.includes(data.code)
      }
    },
    checkAdd(data) {
      if (data) {
        return data.addrLevel < 6
      }
    }
  },
})
</script>

<style lang="scss" scoped>
.grid-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  background: #f3f4f8;

  ::v-deep .sub-item {
    border-radius: 10px;
    background: #f9fafe;
    box-shadow: 0 0 5px #999;
    margin: 10px auto;
    padding: 10px;
  }

  .left-wrapper {
    height: calc(100% - 5px);
    width: 260px;
    margin-right: 10px;
    border-radius: 6px;
    background: #fff;
    border: 1px solid #d8e1eb;
    box-shadow: 0px 2px 3px 0px rgba(70, 19, 12, 0.08);
    .left-header {
      padding: 12px 16px;
      // text-align: center;
      height: 48px;
      font-size: 16px;
      color: #323548;
      background: #FFFFFF;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      box-shadow: 0px 1px 0px 0px #E0E2EC;
      border-bottom: 1px solid #E0E2EC;
      margin-bottom: 8px;
    }
    .tree-wrapper {
      padding-bottom: 120px;
      height: calc(100% - 55px);
      overflow: auto;
      .tree-disable {
        color: #e0e0e0;
      }
    }
  }
  .right-wrapper {
    flex: 1;
  }
  .contextmenu {
    position: fixed;
    margin: 0;
    background: #fff;
    z-index: 99999;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      &:hover {
        background: #eee;
      }
    }
  }
}
</style>