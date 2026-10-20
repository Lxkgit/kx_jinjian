<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 新增与删除按钮 -->
      <!-- <i-button-bar :btn-lists="btnLists" slot="btn" ref="btnBar" size="mini" class="i-btn-bar"
                    @click="clickHandle"></i-button-bar> -->
      <!-- 数据列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @selectionChange="selectionChange"
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
    <addGroup
      v-model="showDialog"
      :status="status"
      :defaultValue="defaultValue"
      :categoryId="categoryId"
    >
    </addGroup>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin.vue";
import addGroup from "../components/addGroup.vue";
import dialogMixinVue from "@/mixins/dialogMixin.vue";
import service from "../api";
export default {
  mixins: [pageMixin, dialogMixinVue],
  components: {
    addGroup,
  },
  props: {
    categoryId: {
      type: Number,
    },
  },
  data() {
    return {
      searchColumns: [
        {
          label: "网点名称",
          key: "customGroupName",
          type: "text",
          placeholder: "请输入网点分类名称",
          clearable: true,
        },
      ],
      btnLists: [
        {
          icon: "el-icon-plus",
          cname: "新增客群",
          ename: "addHandle",
          type: "primary",
        },
        {
          icon: "el-icon-delete",
          cname: "批量删除",
          ename: "deleteHandle",
          type: "primary",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      columns: [
        {
          label: "网点分类名称",
          prop: "customGroupName",
          showOverFlowTooltip: true,
        },
        { label: "网点数量", prop: "customNum", showOverFlowTooltip: true },
        // {
        //   label: "客群来源",
        //   prop: "customGroupSource",
        //   showOverFlowTooltip: true,
        // },
        { label: "说明", prop: "groupDesc", showOverFlowTooltip: true },
        { label: "状态", prop: "groupStatusDesc", showOverFlowTooltip: true },
        { label: "创建人", prop: "createBy", showOverFlowTooltip: true },
        {
          label: "创建时间",
          prop: "createTime",
          showOverFlowTooltip: true,
          formatter: ({ createTime }) => {
            return this.parseTime(createTime);
          },
        },
        { label: "操作", slot: true, width: 160 },
      ],
      tableBtn: [
        {
          cname: "分类明细",
          ename: "memberManagement",
          type: "text",
        },
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
        },
        {
          cname: "删除",
          ename: "handleDel",
          type: "text",
        },
      ],
      tableData: [],
      status: "add", //打开的弹窗是新增还是编辑
      defaultValue: {},
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    addHandle() {
      this.defaultValue = {};
      this.showDialog = true;
      this.status = "add";
    },
    deleteHandle() {
      this.makeSure("确认删除所选客群?", () => {
        this.delGroupList(this.multiSelected);
      });
    },
    // 成员管理
    memberManagement(row) {
      this.$store.commit("gridManagement/SET_GROUP_INFO", row);
      this.$router.push({ name: "MemberManagement" });
    },
    // 编辑
    handleEdit(row) {
      this.defaultValue = { ...row };
      this.showDialog = true;
      this.status = "edit";
    },
    // 表格右侧点击删除
    handleDel(row) {
      this.makeSure("确认删除该客群?", () => {
        this.delGroup([row]);
      });
    },
    // 获取列表数据
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const data = {
          // categoryId: this.categoryId,
          pageSize,
          pageNum,
          ...formInfo,
        };
        service
          .customerGroupList(data)
          .then((res) => {
            this.total = res.total;
            this.tableData = res.rows;
            this.loading = false;
          })
          .catch((error) => {
            if (error) {
              this.tableData = [];
              this.total = 0;
            }
            this.loading = false;
          });
      });
    },
    // 批量删除客群
    delGroupList(row) {
      let ids = row.map((item) => {
        return item.id;
      });
      service.delGroupList({ ids }).then((res) => {
        if (res.code == "200") {
          this.$message({
            type: "success",
            message: "已删除",
          });
        }
        this.fetchList();
      });
    },
    // 删除客群
    delGroup(row) {
      let id = row
        .map((item) => {
          return item.id;
        })
        .toString();
      service.delGroup({ id }).then((res) => {
        if (res.code == "200") {
          this.$message({
            type: "success",
            message: "已删除",
          });
        }
        this.fetchList();
      });
    },
    delGroupCustomer(ids) {},
  },
};
</script>

<style lang="scss" scoped></style>
