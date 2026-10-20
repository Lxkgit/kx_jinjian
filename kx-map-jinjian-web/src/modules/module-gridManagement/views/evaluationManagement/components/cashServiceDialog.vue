<template>
  <i-dialog
    title="责任网格数量"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="false"
  >
    <div style="padding-bottom: 60px">
      <div style="font-weight: bold; font-size: 18px; color: #000">
        {{ defaultValue.networkPoint }}
      </div>
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
        @sizeChange="sizeChange"
        @sortChange="sortChange"
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
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  name: "cashServiceDialog",
  mixins: [dialogMixin, pageMixin],
  dicts: [],
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      columns: [
        { label: "网格名称", prop: "gridName", showOverFlowTooltip: true },
        {
          label: "所在镇街",
          prop: "streetName",
          showOverFlowTooltip: true,
        },
        {
          label: "所在县域",
          prop: "cityName",
          showOverFlowTooltip: true,
        },
        {
          label: "网格长",
          prop: "gridManager",
          showOverFlowTooltip: true,
        },
        {
          label: "网格长联系电话",
          prop: "gridManegerPhone",
          showOverFlowTooltip: true,
        },
      ],
      templateData: [
        [
          {
            gridName: "桃园社区居委会",
            streetName: "城东街道",
            cityName: "金华市婺城区",
            gridManager: "李明",
            gridManegerPhone: "18467835729",
          },
          {
            gridName: "凤凰山社区居委会",
            streetName: "城北街道",
            cityName: "金华市婺城区",
            gridManager: "张三",
            gridManegerPhone: "15678263674",
          },
        ],
        [
          {
            gridName: "王坦社区",
            streetName: "多湖街道",
            cityName: "金华市金东区",
            gridManager: "王刚",
            gridManegerPhone: "13939688457",
          },
        ],
        [
          {
            gridName: "曹宅村委会",
            streetName: "曹宅镇",
            cityName: "金华市金东区",
            gridManager: "王宏",
            gridManegerPhone: "15996535942",
          },
        ],
        [
          {
            gridName: "江南社区居委会",
            streetName: "江东街道",
            cityName: "金华市义乌市",
            gridManager: "孙莉",
            gridManegerPhone: "18535278347",
          },
          {
            gridName: "江滨社区居委会",
            streetName: "福田街道",
            cityName: "金华市义乌市",
            gridManager: "张萍",
            gridManegerPhone: "15678273647",
          },
        ],
        [
          {
            gridName: "凤凰山社区居委会",
            streetName: "城北街道",
            cityName: "金华市婺城区",
            gridManager: "张三",
            gridManegerPhone: "15678263674",
          },
          {
            gridName: "曹宅村委会",
            streetName: "曹宅镇",
            cityName: "金华市金东区",
            gridManager: "王宏",
            gridManegerPhone: "15996535942",
          },
        ],
      ],
      tableData: [],
    };
  },
  computed: {},
  methods: {
    initData() {
      this.tableData = this.templateData[this.defaultValue.idx];
      this.total = this.tableData.length;
    },
    handleConfirm() {
      this.handleClose();
    },
    handleClose() {
      this.tableData = [];
      this.total = 0;
      this.showDisortChangealog = false;
    },
    sortChange() {},
  },
};
</script>
  
  <style>
</style>