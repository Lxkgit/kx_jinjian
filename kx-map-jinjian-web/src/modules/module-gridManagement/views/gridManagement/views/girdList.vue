<template>
  <div class="gridList">
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
          :columns="searchColumns"
          class="filterBar"
          :gridColumns="2"
        >
        </i-filter-bar>
        <div>
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </div>
      <!-- 列表 -->
      <table-title title="网格数据导出" class="table-title">
        <template #right>
          <el-button type="primary" @click="clickHandle('exportHandle')">网格数据导出</el-button>
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
      v-model="showEdit"
      :selected="multiSelected"
    ></distributeOrgEdit>
  </div>
</template>
  
  <script>
import distributeOrgEdit from "../components/distributeOrgEdit";
import pageMixin from "@/mixins/pageMixin";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import services from "../api";
const options = ["行政村网格", "社区网格"];
const isCashList = ["否", "是"];
export default {
  name: "GirdList",
  mixins: [pageMixin],
  dicts: ["new_grid_type"],
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
          key: "gridName",
          type: "text",
        },
        {
          label: "责任网点",
          key: "dutyNetworkName",
          value: "",
          type: "autocomplete",
          querySearch: this.queryAllanchor,
          valueKey: "anchorName",
          triggerOnFocus: false,
        },
        // {
        //   label: "责任机构",
        //   key: "dutyNetworkNames",
        //   value: "",
        //   type: "cascader",
        //   collapseTags: true,
        //   props: {
        //     label: "label",
        //     value: "label",
        //     children: "children",
        //     emitPath: false,
        //     multiple: true,
        //   },
        //   filterable: true,
        //   options: this.deptTreeSelect,
        // },
        {
          label: "是否重点网格",
          key: "isCash",
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
        {
          label: "网格类型",
          key: "ruralGridType",
          type: "select",
          collapseTags: true,
          options: () => this.dict.type.new_grid_type,
        },
      ],
      btnLists: [
        {
          cname: "网格数据导出",
          ename: "exportHandle",
          type: "primary",
        },
        // {
        //   cname: "模版下载",
        //   ename: "dowlonadHandle",
        // },
      ],
      columns: [
        {
          label: "网格编号",
          prop: "gridCode",
        },
        {
          label: "网格名称",
          prop: "gridName",
        },
        {
          label: "网格类型",
          prop: "ruralGridType",
          formatter: ({ ruralGridType }) => {
            return this.dict.label.new_grid_type[ruralGridType];
          },
        },
        {
          label: "银行网格长",
          prop: "dutyName",
        },
        {
          label: "是否重点网格",
          prop: "gridName2",
        },
        // {
        //   label: "是否现金服务重点网格",
        //   prop: "isCash",
        //   formatter: ({ isCash }) => {
        //     return isCashList[isCash];
        //   },
        // },
        // { label: "银行机构", prop: "bankAgencyName" },
        { label: "网格责任网点", prop: "dutyNetworkName" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandel",
          permi: "gridManagement:areaDistribute:edit",
        },
        {
          cname: "编辑",
          ename: "editHandel",
          permi: "gridManagement:areaDistribute:edit",
        },
      ],
      showEdit: false,
      selectedNodePath: [],
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
    exportHandle() {
      this.makeSure('确认导出吗', () => {
        this.$refs.filterBar.getForm((formInfo) => {
          const param = {
            ...formInfo,
            typeCode: this.gridType,
            code: this.arrCode,
          }
          services.gridExport(param).then(res => {
            const blob = new Blob([res], { type: "application/vnd.ms-excel" });
            const fileName = "网格数据列表.xlsx"; //下载文件名称
            const elink = document.createElement("a");
            elink.download = fileName;
            elink.style.display = "none";
            elink.href = URL.createObjectURL(blob);
            document.body.appendChild(elink);
            elink.click();
            URL.revokeObjectURL(elink.href); // 释放URL 对象
            document.body.removeChild(elink);
          })
        })
      })
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
    viewHandel(row) {
      this.$store.commit("gridManagement/SET_GRID_INFO", {
        type: 1,
        arrCode: row.gridCode,
        titleArr:this.selectedNodePath,
      });
      this.$router.push({
        name: "GridInfoManage",
      });
    },
    editHandel(row) {
      this.$store.commit("gridManagement/SET_GRID_INFO", {
        type: 2,
        arrCode: row.gridCode,
        titleArr:this.selectedNodePath,
      });
      this.$router.push({
        name: "GridInfoManage",
      });
    },
    async deptTreeSelect() {
      const { data } = await service.deptTree();
      return data;
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
      console.log(data,'dataaaaa')
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
        console.log(param);
        this.loading = true;
        services
          .gridMessageList(param)
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
      console.log(node);
      this.arrCode = data.code;
      this.selectedNodePath = this.getNodePath(node);
      this.selectedNodePath = this.selectedNodePath.filter(
        (item) => item !== undefined
      );
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
  .gridList {
    height: 100%;
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
  padding: 0 16px;
}
.important {
  color: #000;
}
.notImportant {
  color: #a8a6a6;
}
.btn-list {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>