<template>
  <div>
    <SplitPageContent :leftSpan="7" :rightSpan="17">
      <template #operate>
        <i-table
          ref="table"
          :loading="typeLoading"
          :data="typeTableData"
          align="center"
          stripe
          @rowClick="rowClick"
          :rowStyle="rowStyleFun"
          :allColumns="typeColumns"
          :showPagination="false"
        >
        </i-table>
      </template>

      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="3"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
        <el-dropdown class="drop-css">
          <el-button type="primary">
            批量处理<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              @click.native="
                changeState(2, multiSelected.map((v) => v.id).join(','))
              "
              >批量设置已处理</el-dropdown-item
            >
            <el-dropdown-item
              @click.native="
                changeState(1, multiSelected.map((v) => v.id).join(','))
              "
              >批量设置待处理</el-dropdown-item
            >
            <el-dropdown-item
              @click.native="
                changeStar(1, multiSelected.map((v) => v.id).join(','))
              "
              >批量设置星标</el-dropdown-item
            >
            <el-dropdown-item
              @click.native="
                changeStar(0, multiSelected.map((v) => v.id).join(','))
              "
              >批量取消星标</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </i-filter-bar>
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
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        :maxHeight="maxHeight"
        @sizeChange="sizeChange"
        showIndex
        selection
        @selectionChange="selectionChange"
      >
        <template #messageStar="scope">
          <span
            ><i
              class="el-icon-star-on"
              style="color: #ff9227"
              v-if="scope.row.messageStar === '1'"
            ></i
          ></span>
        </template>
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
            :count="1"
          >
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
  </div>
</template>

<script>
import service from "../api";
import pageMixin from "@/mixins/pageMixin";
export default {
  dicts: ["message_source_type", "message_type"],
  mixins: [pageMixin],
  data() {
    return {
      maxHeight: window.innerHeight - 320,
      curMessageType: "",
      searchColumns: [
        {
          label: "",
          key: "messageTitle",
          type: "text",
        },
        {
          label: "日期选择",
          key: "date",
          type: "daterange",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          valueFormat: "yyyy-MM-dd",
        },
        {
          label: "消息来源",
          key: "messageSources",
          type: "select",
          options: () => this.dict.type.message_source_type,
        },
        {
          label: "处理状态",
          key: "messageState",
          type: "select",
          options: [
            { value: "", label: "全部" },
            { value: "1", label: "待处理" },
            { value: "2", label: "已处理" },
          ],
        },
        {
          label: "星标",
          key: "messageStar",
          type: "select",
          options: [
            { value: "", label: "全部" },
            { value: "0", label: "否" },
            { value: "1", label: "是" },
          ],
        },
      ],
      columns: [
        { label: "消息标题", prop: "messageTitle" },
        {
          label: "消息来源",
          formatter: (row) =>
            this.getDictName("message_source_type", row.messageSources),
        },
        { label: "提醒时间", prop: "notifyTime" },
        { label: "消息状态", prop: "messageStateName" },
        { label: "星标", prop: "messageStar", slot: true },
        { label: "经办机构", prop: "networkName" },
        { label: "经办人", prop: "managerName" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "设置已处理",
          ename: "processed",
          isShow: "btnFlagDeal1",
        },
        {
          cname: "设置待处理",
          ename: "pending",
          isShow: "btnFlagDeal2",
        },
        {
          cname: "设置星标",
          ename: "setStar",
          isShow: "btnFlagStar1",
        },
        {
          cname: "取消星标",
          ename: "cancelStar",
          isShow: "btnFlagStar2",
        },
      ],
      typeLoading: false,
      typeTableData: [],
      typeColumns: [
        { label: "消息类型", formatter: (row) => this.getMessageTypeName(row) },
        { label: "信息量", prop: "clientQuantity" },
        { label: "待处理", prop: "notClientQuantity" },
        { label: "已处理", prop: "alreadyClientQuantity" },
        { label: "完成率", prop: "completionRate" },
      ],
    };
  },
  mounted() {
    this.fetchListLeft();
    this.fetchList();
  },
  methods: {
    rowStyleFun({ row }) {
      if (this.curMessageType == row.messageType) {
        return { color: "#54a7fb" };
      }
    },
    getMessageTypeName(row) {
      if (row.messageType == 0) return "全部";
      return this.getDictName("message_type", row.messageType);
    },
    fetchListLeft() {
      this.loading = true;
      service
        .listMessageStatistics()
        .then((res) => {
          this.typeTableData = res.data;
          this.loading = false;
        })
        .catch((error) => {
          this.loading = false;
          if (error) {
            this.typeTableData = [];
          }
        });
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const { date } = formInfo;
        console.log(date);
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
          messageType: this.curMessageType != 0 ? this.curMessageType : "",
          startDate: date && date[0],
          endDate: date && date[1],
        };

        this.loading = true;
        service
          .list(param)
          .then((res) => {
            const { total, rows } = res;
            this.tableData = rows.map((item) => {
              return {
                ...item,
                messageStateName: [, "待处理", "已处理"][item.messageState],
                btnFlagDeal1: item.messageState == 1,
                btnFlagDeal2: item.messageState == 2,
                btnFlagStar1: item.messageStar == 0,
                btnFlagStar2: item.messageStar == 1,
              };
            });
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
    rowClick(row) {
      this.formInline.pageNum = 1;
      this.curMessageType = row.messageType;
      this.fetchList();
    },
    clickHandle(e, item) {
      switch (e) {
        case "processed":
        case "pending":
          this.changeState(3 - item.messageState, item.id);
          break;
        case "setStar":
        case "cancelStar":
          this.changeStar(1 - item.messageStar, item.id);
          break;
      }
    },
    async changeState(messageState, ids) {
      if (!ids) return;
      let { code } = await service.updateMessageState({ ids, messageState });
      if (code == 200) {
        this.$message({ type: "success", message: "操作成功!" });
        this.fetchListLeft();
        this.fetchList();
      }
    },
    async changeStar(messageStar, ids) {
      if (!ids) return;
      let { code } = await service.updateMessageStar({ ids, messageStar });
      if (code == 200) {
        this.$message({ type: "success", message: "操作成功!" });
        this.fetchListLeft();
        this.fetchList();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.drop-css {
  width: 80px;
  margin-left: 10px;
}
</style>
