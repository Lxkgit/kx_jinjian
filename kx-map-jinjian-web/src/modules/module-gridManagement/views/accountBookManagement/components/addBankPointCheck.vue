<template>
  <i-dialog
    :title="dialogType == 'store' ? '商户选择' : '银行网点选择'"
    v-model="showDialog"
    width="50%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <el-form ref="form" :model="selectForm" label-width="80px">
      <el-form-item
        :label="
          dialogType == 'store' ? '选择抽查的商户主体' : '选择抽查的银行网点'
        "
        label-width="170px"
        :required="true"
      >
        <el-autocomplete
          v-model="selectForm.searchStr"
          :fetch-suggestions="querySearch"
          :placeholder="
            dialogType == 'store'
              ? '请输入要抽查的商户主体'
              : '请输入要抽查的银行网点'
          "
          @select="handleSelect"
          style="width: 100%"
        ></el-autocomplete>
      </el-form-item>
    </el-form>
    <div style="margin-bottom: 20px">
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        stripe
        :maxHeight="maxHeight"
        :allColumns="tableColumns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        :showPagination="false"
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
  </i-dialog>
</template>
  
<script>
import pageMixin from "@/mixins/pageMixin";
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  name: "addBankPointCheck",
  mixins: [dialogMixin, pageMixin],
  props: {
    defaultValue: {
      type: [Object, Array],
    },
    dialogType: {
      type: String,
    },
  },
  computed: {
    tableColumns() {
      if (this.dialogType == "store") {
        return [
          { label: "商户主体名称", key: "name" },
          { label: "商户主体行业", key: "name" },
          { label: "商户主体地址", key: "name" },
          { label: "商户主体联系人", key: "name" },
          { label: "联系电话", key: "name" },
          { label: "操作", slot: true },
        ];
      } else {
        return [
          { label: "网点名称", key: "bankPointName" },
          { label: "网点地址", key: "bankPointAddress" },
          { label: "网点负责人", key: "dutyPerson" },
          { label: "联系电话", key: "dutyPersonPhone" },
          { label: "操作", slot: true },
        ];
      }
    },
  },
  data() {
    return {
      maxHeight: "300px",
      selectForm: {
        searchStr: "",
      },
      tableBtn: [
        {
          cname: "移除",
          ename: "delete",
        },
      ],
      storeList: [],
      bankPointList: [],
    };
  },
  methods: {
    initData() {},
    handleConfirm() {},
    handleClose() {
      this.showDialog = false;
    },
    querySearch(queryString, cb) {
      let results = [];
      cb(results);
    },
  },
};
</script>
  
<style>
</style>