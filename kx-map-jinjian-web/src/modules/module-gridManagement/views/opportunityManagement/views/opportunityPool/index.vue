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
      <i-button-bar
        :btn-lists="btnLists"
        slot="btn"
        ref="btnBar"
        size="mini"
        class="i-btn-bar"
        @click="handleClick"
      ></i-button-bar>
      <!-- 数据表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        :max-height="maxHeight - 50"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        :selection="true"
        :showIndex="true"
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
      <!-- 新增弹窗 -->
      <add-chance
        v-model="showAdd"
        :status="status"
        :defaultValue="defaultValue"
        @fetchList="fetchList"
      ></add-chance>
    </PageContent>
  </div>
</template>

<script>
import services from "../../api/index.js";
import pageMixin from "@/mixins/pageMixin.vue";
import addChance from "../../components/addChance.vue";
export default {
  mixins: [pageMixin],
  components: { addChance },
  data() {
    return {
      searchColumns: [
        {
          label: "客户名称",
          key: "name",
          type: "text",
        },
        {
          label: "信用代码",
          key: "identity",
          type: "text",
        },
        {
          label: "商机名称",
          key: "businessName",
          type: "text",
        },
        // {
        //   label: "认领状态",
        //   key: "chanceStatus",
        //   type: "select",
        //   placeholder: "请选择认领状态",
        //   options: [
        //     {
        //       value: 0,
        //       label: "待认领",
        //     },
        //     {
        //       value: 1,
        //       label: "已结束",
        //     },
        //   ],
        // },
        {
          label: "拓展方向",
          key: "businessExtend",
          type: "select",
          placeholder: "请选择业务拓展方向",
          options: [
            {
              value: 0,
              label: "潜客扩展",
            },
            {
              value: 1,
              label: "信贷提升",
            },
            {
              value: 2,
              label: "存量活客",
            },
          ],
        },
        {
          label: "开始时间",
          type: "date",
          key: "startTime",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "结束时间",
          type: "date",
          key: "endTime",
          valueFormat: "yyyy-MM-dd",
        },
      ],
      btnLists: [
        {
          icon: "el-icon-plus",
          cname: "新增",
          ename: "add",
          type: "primary",
        },
        {
          icon: "el-icon-delete",
          cname: "删除",
          ename: "delete",
          type: "info",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      columns: [
        { label: "客户名称", prop: "name" },
        { label: "统一社会信用代码", prop: "identity" },
        { label: "商机名称", prop: "businessName" },
        { label: "业务拓展方向", prop: "businessExtend" },
        // { label: "认领状态", prop: "claimStatus" },
        { label: "商机来源", prop: "businessResource" },
        { label: "商机发布人", prop: "createBy" },
        { label: "商机发布日期", prop: "startTime" },
        { label: "商机结束日期", prop: "endTime" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "handleEdit",
          type: "text",
        },
        {
          cname: "商机分配",
          ename: "handleDistribute",
          type: "text",
        },
        {
          cname: "商机认领",
          ename: "handleClaim",
          type: "text",
        },
      ],
      tableData: [],
      status: "add",
      showAdd: false,
      defaultValue: {},
    };
  },
  methods: {
    // 点击新增/修改/删除按钮
    handleClick(click) {
      // 新建商机
      if (click === "add") {
        this.status = "add";
        this.showAdd = true;
        this.defaultValue = {};
      }
      // 删除多个任务
      if (click === "delete") {
        this.makeSure("确认删除多个商机吗?", () => {
          this.delChance(this.multiSelected);
        });
      }
    },
    // 删除商机
    delChance(row) {
      console.log("即将删除的商机", row);
    },
    handleEdit(row) {
      this.status = "edit";
      this.defaultValue = {};
      this.getBusinessById(row.id);
      this.showAdd = true;
    },
    // 点击商机分配
    handleDistribute(row) {
      this.status = "distribute";
      this.getBusinessById(row.id);
      this.showAdd = true;
    },
    // 点击商机认领
    handleClaim(row) {
      this.makeSure("是否认领该商机?", () => {
        this.receiveChance(row);
      });
    },
    // 商机认领的确认操作
    receiveChance(row) {
      console.log(row);
    },
    // 根据id获取商机数据
    getBusinessById(id) {
      let data = { id };
      services
        .opportunityGetById(data)
        .then((res) => {
          // console.log(res);
          this.defaultValue = { ...res.data };
        })
        .catch((err) => {
          console.log(err);
        });
    },
    // 删除商机
    delChance(row) {
      // console.log("即将删除的商机", row);
      let ids = row
        .map((item) => {
          return item.id;
        })
        .toString();
      services
        .opportunityDelByIds({ ids })
        .then((res) => {
          // console.log(res);
          if (res.code == "200") {
            this.$message({
              type: "success",
              message: "已删除",
            });
          }
          this.fetchList();
        })
        .catch((err) => {});
    },
    // 获取列表数据
    fetchList() {
      this.loading = true;
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        // console.log(param);
        services
          .opportunityList(param)
          .then((res) => {
            console.log(res);
            res.rows.forEach((item) => {
              // 业务扩展方向 0-潜客扩展 1-信贷提升 2-存量活客
              item.businessExtend = this.moreif(
                item.businessExtend,
                "潜客扩展",
                "信贷提升",
                "存量活客"
              );
              // 商机来源 0-手工创建 1-信贷系统 2-外部接入
              item.businessResource = this.moreif(
                item.businessResource,
                "手工创建",
                "信贷系统",
                "外部接入"
              );
            });
            this.tableData = res.rows;
            this.total = res.total;
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
          });
      });
    },
  },
  mounted() {
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped>
</style>