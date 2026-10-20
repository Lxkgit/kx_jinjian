<script>
import orgTitle from '@/modules/module-gridManagement/components/orgTitle.vue'
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
export default {
  components: {
    orgTitle,tableTitle
  },
  data() {
    return {
      maxHeight: window.innerHeight - 468,
      searchColumns: [],
      btnLists: [],
      tableBtn: [],
      loading: false,
      total: 0,
      tableData: [],
      columns: [],
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      multiSelected: [],
    };
  },
  activated() {
    this.fetchList()
  },
  methods: {
    fetchList() { },
    searchHandle() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.fetchList();
    },
    resetHandle() {
      this.formInline.pageNum = 1;
      this.formInline.pageSize = 10;
      this.$refs.filterBar.reset();
      this.fetchList();
    },
    clickHandle(type, data) {
      this[type].call(this, data);
    },
    currentChange(val) {
      this.formInline.pageNum = val;
      this.fetchList();
    },
    sizeChange(val) {
      this.formInline.pageSize = val;
      this.fetchList();
    },
    selectionChange(val) {
      this.multiSelected = val;
    },
    // 二次确认弹框
    makeSure(msg, callback) {
      this.$confirm(msg, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          callback();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    // 根据后端返回数据字段的数值去转换成对应的文字
    // 如 商机来源 0-手工创建 1-信贷系统 2-外部接入
    moreif(str, one, two, three) {
      if (str == 0) {
        return one;
      }
      if (str == 1) {
        return two;
      }
      if (str == 2) {
        return three;
      }
    },
  },
};
</script>