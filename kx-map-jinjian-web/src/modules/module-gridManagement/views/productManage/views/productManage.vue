<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
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
    </PageContent>
    <addProduct v-model="showAdd"></addProduct>
    <qrCodeDialog
      v-model="showCode"
      codeLink="http://www.kangxukeji.com/"
    ></qrCodeDialog>
    <!-- <qrCodeDialog v-model="showCode" :codeLink="codeLink"></qrCodeDialog> -->
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import addProduct from "../components/addProduct";
import qrCodeDialog from "../components/qrCodeDialog";
import service from "../api";
export default {
  mixins: [pageMixin],
  components: {
    addProduct,
    qrCodeDialog,
  },
  dicts: ["product_type"],
  data() {
    return {
      searchColumns: [
        {
          label: "产品名称",
          key: "productName",
          type: "text",
        },
        {
          label: "产品类型",
          key: "productType",
          type: "select",
          options: () => this.dict.type.product_type,
        },
      ],
      // btnLists: [
      //   {
      //     cname: '新增产品',
      //     ename: 'addHandle',
      //     type: 'primary',
      //   },
      // ],
      columns: [
        { label: "产品名称", prop: "productName" },
        {
          label: "产品类型",
          prop: "productType",
          formatter: ({ productType }) => {
            return this.dict.label.product_type[productType];
          },
        },
        { label: "操作", slot: true },
      ],
      tableData: [],
      tableBtn: [
        {
          cname: "专属二维码",
          ename: "viewHandle",
        },
        {
          cname: "二维码扫描统计",
          ename: "detailHandle",
        },
      ],
      showAdd: false,
      showCode: false,
      codeLink: "",
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.loading = true;
        service.productList(param).then((res) => {
          const { total, rows } = res;
          this.tableData = rows;
          this.total = total;
          this.loading = false;
        });
      });
    },
    addHandle() {
      this.showAdd = true;
    },
    viewHandle(row) {
      const { businessLink } = row;
      this.codeLink = businessLink;
      this.showCode = true;
    },
    detailHandle(row) {
      const { id } = row;
      this.$store.commit("gridManagement/SET_PRODUCT_ID", id);
      this.$router.push({ name: "ProductDetail" });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>