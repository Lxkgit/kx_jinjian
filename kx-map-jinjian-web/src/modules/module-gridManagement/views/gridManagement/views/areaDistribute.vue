<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <!-- <el-select
            v-model="gridType"
            class="grid-select"
            placeholder="请选择网格体系"
            @change="girdChange"
          >
            <el-option
              v-for="item in gridOptions"
              :key="item.dictCode"
              :value="item.dictCode"
              :label="item.dictLabel"
            ></el-option>
          </el-select> -->
          <div class="grid-select">网格层级</div>
          <el-tree
            ref="tree"
            class="tree-content"
            :props="props"
            node-key="id"
            :expand-on-click-node="false"
            :highlight-current="true"
            :load="loadNode"
            lazy
            @node-click="nodeClickHandle"
          >
            <template #default="{ node, data }">
              <!-- :class="[
                  data.lightOrGray || filterTree.includes(data.name)
                    ? 'important'
                    : 'notImportant',
                ]" -->
              <div>
                <span>{{ data.name }}</span>
                <span v-if="data.minimumNum">({{ data.minimumNum }})</span>
              </div>
            </template>
          </el-tree>
        </div>
      </template>

      <orgTitle :titleArr="selectedNodePath"></orgTitle>
      <!-- 过滤器 -->
      <div class="filterBar-box">
        <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        label-width="130px"
        :columns="searchColumns"
        :gridColumns="2"
      >
        </i-filter-bar>
        <div>
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </div>

      <!-- 列表 -->
      <table-title title="责任网格列表" class="table-title">
        <template #right>
          <i-button-bar
            style="margin-bottom: 0;"
            :btn-lists="btnLists"
            @click="clickHandle"
            slot="btn"
            ref="btnBar"
          >
          </i-button-bar>
        </template>
      </table-title>
      <div class="i-table">
        <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        selection
        @selectionChange="selectionChange"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
      </div>
      
    </SplitPageContent>
    <!-- distributeOrgEdit -->
    <distributeOrgEdit
      title="设置网点"
      v-model="showEdit"
      :selected="multiSelected"
    ></distributeOrgEdit>
  </div>
</template>

<script>
import distributeOrgEdit from "../components/distributeOrgEdit";
import pageMixin from "@/mixins/pageMixin";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
export default {
  name: "AreaDistribute",
  mixins: [pageMixin],
  components: {
    distributeOrgEdit,
  },
  data() {
    return {
      filterTree: ["金华市"],
      gridType: null,
      arrCode: null,
      gridOptions: [],
      node_had: undefined,
      resolve_had: undefined,
      props: {
        label: "name",
        children: "children",
        isLeaf: (data) => {
          return !data.isLeaf;
        },
      },
      searchColumns: [
        {
          label: "网格名称",
          key: "name",
          type: "text",
        },
        {
          label: "网格类型",
          key: "type",
          type: "text",
        },
        {
          label: "网格责任网点",
          key: "anchorName",
          value: "",
          type: "autocomplete",
          querySearch: this.queryAllanchor,
          valueKey: "anchorName",
          triggerOnFocus: false,
          // props: {
          //   label: "label",
          //   value: "id",
          //   children: "children",
          //   emitPath: false,
          // },
          // filterable: true,
          // options: this.deptTreeSelect,
        },
        
        {
          label: "是否有责任网点",
          key: "isDutyAnchor",
          type: "select",
          options: () => [
            {
              label: "是",
              value: "1",
            },
            {
              label: "否",
              value: "0",
            },
          ],
        },
      ],
      btnLists: [
        {
          cname: "批量设置责任网点",
          ename: "distributeAll",
          type: "primary",
          permi: "gridManagement:areaDistribute:edit",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      columns: [
        { label: "网格名称", prop: "name" },
        { label: "网格类型", prop: "gridType" },
        { label: "网格责任网点", prop: "anchorName" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "设置责任网点",
          ename: "distribute",
          permi: "gridManagement:areaDistribute:edit",
        },
      ],
      showEdit: false,
      selectedNodePath: [],
      defaultValue: {},
    };
  },
  mounted() {
    this.iterator = this.initFun();
    this.iterator.next();
  },
  methods: {
    *initFun() {
      yield this.gridTypeList();
      yield this.fetchList();
    },
    async deptTreeSelect() {
      const { data } = await service.deptTree();
      return data;
    },
    queryAllanchor(str, cb) {
      let params = {
        anchorName: str,
      };
      service
        .getAllanchor(params)
        .then((res) => {
          cb(res.data);
        })
        .catch((err) => {
          cb([]);
        });
    },
    gridTypeList() {
      const param = {
        pageNum: 1,
        pageSize: 100,
      };
      service.gridTypeList(param).then((res) => {
        const { rows } = res;
        this.gridOptions = rows;
        this.gridType = rows[0]?.dictCode;
        this.girdChange();
      });
    },
    loadNode(node, resolve) {
      const { level, data } = node;
      if (level === 0) {
        this.node_had = node;
        this.resolve_had = resolve;
      }
      if (this.gridType) {
        const param = {
          typeCode: this.gridType,
          parentCode: level > 0 ? data.code : '100000',
        };
        service.getPaAddrInfoTypeList(param).then((res) => {
          const { data } = res;

          const lanxiIndex = data.findIndex((item) => item.name == "兰溪市");
          const jindongIndex = data.findIndex((item) => item.name == "金东区");
          if (lanxiIndex != -1 && jindongIndex != -1) {
            const lanxiColumn = data.splice(lanxiIndex, 1)[0];
            data.splice(jindongIndex + 1, 0, lanxiColumn);
          }
          if (level === 0) {
            this.arrCode = data[0].code;
            this.selectedNodePath = [data[0]?.name]
          }
          resolve(data);
          this.iterator.next();
        });
      } else {
        resolve([]);
      }
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          typeCode: this.gridType,
          code: this.arrCode,
          ...formInfo,
        };
        this.loading = true;
        service
          .getAddrInfoTypeBottom(param)
          .then((res) => {
            const { total, rows } = res;
            this.tableData = rows;
            this.total = total;
            this.loading = false;
            this.multiSelected = [];
          })
          .catch((error) => {
            if (error) {
              this.tableData = [];
              this.total = 0;
            }
            this.multiSelected = [];
            this.loading = false;
          });
      });
    },
    girdChange() {
      this.node_had.childNodes = [];
      this.loadNode(this.node_had, this.resolve_had);
    },
    nodeClickHandle(data, node) {
      this.arrCode = data.code;
      this.selectedNodePath = this.getNodePath(node);
      this.selectedNodePath = this.selectedNodePath.filter(
        (item) => item !== undefined
      );
      console.log(this.selectedNodePath);
      this.resetHandle();
    },
    getNodePath(node) {
      const path = [];
      let current = node;

      while (current) {
        path.unshift(current?.data?.name);
        current = current.parent;
      }

      return path;
    },
    selectionChange(val) {
      this.multiSelected = val;
    },
    distributeAll() {
      this.showEdit = true;
    },
    distribute(data) {
      this.multiSelected = [data];
      this.showEdit = true;
    },
  },
};
</script>

<style lang="scss" scoped>
.grid-select {
  width: 100%;
  // margin-bottom: 10px;
}
.grid-select {
  width: 100%;
  height: 46px;
  padding: 12px 16px;
  font-size: 16px;
  color: #323548;
  background: #FFFFFF;
  box-shadow: 0px 1px 0px 0px #E0E2EC;
  border-bottom: 1px solid #E0E2EC;
}
.tree-wrapper {
  overflow: hidden;
  width: 100%;
  height: calc(100vh - 64px - 56px - 24px);
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  .tree-content {
    padding: 8px 12px;
    width: 100%;
    height: calc(100% - 46px);
    overflow: auto;
    ::v-deep .el-tree-node > .el-tree-node_children {
      overflow: visible;
    }
  }
}
.filterBar-box {
  margin: 12px 0;
  background: #fff;
  padding: 16px;
  display: flex;
  height: 120px;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 68px;
}
.i-table {
  height: calc(100vh - 64px - 56px - 64px - 120px - 48px - 68px);
  background: #fff;
  padding:0 16px;
}

.important {
  color: #000;
}
.notImportant {
  color: #a8a6a6;
}
</style>