<template>
  <div>
    <SplitPageContent>
      <template #operate>
        <div class="tree-wrapper">
          <el-select
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
          </el-select>
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
            <template #default="{ data }">
              <div
                :class="[
                  data.lightOrGray || filterTree.includes(data.name)
                    ? 'important'
                    : 'notImportant',
                ]"
              >
                <span>{{ data.name }}</span>
                <span v-if="data.minimumNum">({{ data.minimumNum }})</span>
              </div>
            </template>
          </el-tree>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
        labelWidth="90px"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
      >
      </i-button-bar>
      <!-- 列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :maxHeight="maxHeight"
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
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
    </SplitPageContent>
  </div>
</template>
    
<script>
import { mapState, mapGetters, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addBankPointDialog from "../components/addBankPointDialog.vue";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  components: { addBankPointDialog },
  dicts: ["merchant_true_false"],
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
          key: "deptName",
          type: "text",
        },
      ],
      btnLists: [],
      tableBtn: [
        {
          cname: "查看详情",
          ename: "viewDeatil",
        },
      ],
      columns: [
        {
          label: "网格名称",
          prop: "gridName",
          showOverFlowTooltip: true,
        },
        {
          label: "责任网点",
          prop: "deptName",
          showOverFlowTooltip: true,
        },
        {
          label: "银行机构",
          prop: "bankDept",
          showOverFlowTooltip: true,
        },
        {
          label: "商户数量(个)",
          prop: "merchantNum",
          showOverFlowTooltip: true,
        },
        {
          label: "网格内巡查次数",
          prop: "inGridInspectNum",
          showOverFlowTooltip: true,
        },
        { label: "操作", slot: true, width: 120 },
      ],
      tableData: [],
    };
  },
  computed: {
    ...mapGetters("accountBook", [
      "merchantCashGridList",
      "merchantInspectList",
    ]),
  },
  mounted() {
    this.iterator = this.initFun();
    this.iterator.next();
  },
  methods: {
    ...mapMutations("accountBook", ["updateNowMerchantCashGridInfo"]),
    *initFun() {
      yield this.gridTypeList();
      yield this.fetchList();
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
    girdChange() {
      this.node_had.childNodes = [];
      this.loadNode(this.node_had, this.resolve_had);
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        let list = this.merchantCashGridList;
        list.forEach((i) => {
          i.inGridInspectNum = this.merchantInspectList.filter(
            (j) => j.gridId == i.gridId
          ).length;
        });
        this.tableData = list;
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
          parentCode: level > 0 ? data.code : null,
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
          }
          resolve(data);
          this.iterator.next();
        });
      } else {
        resolve([]);
      }
    },
    nodeClickHandle(data, node) {
      this.arrCode = data.code;
      this.selectedNodePath = this.getNodePath(node);

      this.selectedNodePath = this.selectedNodePath.filter(
        (item) => item !== undefined
      );
      this.resetHandle();
    },
    viewDeatil(row) {
      this.updateNowMerchantCashGridInfo(row);
      this.$router.push({ name: "MerchantCashInSpectList" });
    },
  },
};
</script>
    
<style lang="scss" scoped></style>