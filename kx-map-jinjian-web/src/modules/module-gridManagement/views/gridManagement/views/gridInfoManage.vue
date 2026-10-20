<template>
  <div style="height:100%">
    <PageContent :hasLeftbg="false">
      <div class="content-wrapper">
      <orgTitle :titleArr="[]" goBack></orgTitle>

      <tableTitle title="网格基本信息" class="content-title hasBorderTop">
        <template #right v-if="gridParams.type == 2">
            <el-button
              v-hasPermi="['gridInfoManage:baseInfo:edit']"
              v-if="!editBaseInfo"
              type="primary"
              @click="editBaseInfo = true"
              >编辑</el-button
            >
            <div v-else>
              <el-button
                @click="
                  editBaseInfo = false;
                  fetchList();
                "
                >取消</el-button
              >
              <el-button type="primary" @click="baseInfoSave">保存</el-button>
            </div>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-form
          ref="baseForm"
          :columns="baseColumns"
          label-width="100px"
          labelPosition="right"
          :gridColumns="3"
        />
      </div>

      <!-- <tableTitle title="重点监管对象" class="content-title">
        <template #left v-if="gridParams.type == 2">
          <div class="add-content" v-hasPermi="['gridInfoManage:communityGrid:add']" @click="addGridImportant">
            <svg-icon icon-class="add" class="add-icon"></svg-icon>
            <span>添加</span>
          </div>
        </template>
      </tableTitle> -->
      <!-- <div class="content-content">
        <i-table
          class="table-height"
          ref="tableGrid"
          :loading="importantListLoading"
          :data="importantList"
          align="center"
          stripe
          showBorder
          :allColumns="importantListColumns"
          :showPagination="false"
        >
          <template #action="scope">
            <i-table-btns
              :btn-lists="importantListTableBtns"
              @click="(type) => clickHandle(type, scope.row)"
            >
            </i-table-btns>
          </template>
        </i-table>
      </div> -->

      <!-- <tableTitle title="现金服务信息" class="content-title hasBorderTop">
        <template #right v-if="gridParams.type == 2">
            <el-button
              v-if="!editBaseInfo"
              type="primary"
              @click="editBaseInfo = true"
              >编辑</el-button
            >
            <div v-else>
              <el-button
                @click="
                  editBaseInfo = false;
                  fetchList();
                "
                >取消</el-button
              >
              <el-button type="primary" @click="baseInfoSave">保存</el-button>
            </div>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-form
          ref="moneyForm"
          :columns="cashServiceColumns"
          label-width="170px"
          labelPosition="right"
          :gridColumns="3"
        />
      </div> -->

      <tableTitle title="责任网点信息" class="content-title"></tableTitle>
      <div class="content-content">
        <i-form
          ref="infoForm"
          :columns="infoColumns"
          labelPosition="right"
          label-width="120px"
          :gridColumns="3"
        >
        </i-form>
      </div>

      <!-- <tableTitle title="普通网点信息" class="content-title">
        <template #left>
          <span v-show="keyTableData.length > 0">
              ({{ keyTableData.length }})
            </span>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
            class="table-height"
            ref="tableWatcher"
            :loading="keyTableLoading"
            :data="keyTableData"
            align="center"
            stripe
            showBorder
            :allColumns="keyTableColumns"
            :showPagination="false"
          >
          </i-table>
      </div> -->

      <!-- <tableTitle title="网格内现金服务点信息" class="content-title">
        <template #left>
          <span v-show="cashPointList.length > 0">
              ({{ cashPointList.length }})
            </span>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
          ref="table2"
          :loading="cashPointListLoading"
          :data="cashPointList"
          align="center"
          stripe
          showBorder
          :allColumns="cashPointListColumns"
          :showPagination="false"
        >
        </i-table>
      </div> -->

      <!-- <tableTitle title="网格内金融服务站信息" class="content-title">
        <template #left>
          <span v-show="finicialPointList.length > 0">
              ({{ finicialPointList.length }})
            </span>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
          ref="table3"
          :loading="finicialPointListLoading"
          :data="finicialPointList"
          align="center"
          stripe
          showBorder
          :allColumns="finicialPointListColumns"
          :showPagination="false"
        >
        </i-table>
      </div> -->

      <!-- <tableTitle title="网格内重点商户信息" class="content-title">
        <template #left>
          <span v-show="false">
              ({{ finicialPointList.length }})
            </span>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
          ref="table4"
          :loading="false"
          :data="importantMerachantList"
          align="center"
          stripe
          showBorder
          :allColumns="importantMerachantColumns"
          :showPagination="false"
        >
        </i-table>
      </div> -->

      <tableTitle title="社会网格员" class="content-title">
        <template #left  v-if="gridParams.type == 2">
          <div class="add-content" v-hasPermi="['gridInfoManage:communityWatcher:add']" @click="addCommunityWatcher">
            <svg-icon icon-class="add" class="add-icon"></svg-icon>
            <span>添加</span>
          </div>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
            class="table-height"
            ref="tableWatcher"
            :loading="communityWatcherLoading"
            :data="communityWatchTableData"
            align="center"
            stripe
            showBorder
            :allColumns="communityWatchColumns"
            :showPagination="false"
          >
            <template #action="scope">
              <i-table-btns
                :btn-lists="communityWatcherTableBtns"
                @click="(type) => clickHandle(type, scope.row)"
              >
              </i-table-btns>
            </template>
          </i-table>
      </div>

      <!-- <tableTitle title="基层组织" class="content-title">
        <template #left  v-if="gridParams.type == 2">
          <div class="add-content" v-hasPermi="['gridInfoManage:gridBasicInfo:add']" @click="addGridBasicInfo">
            <svg-icon icon-class="add" class="add-icon"></svg-icon>
            <span>添加</span>
          </div>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
            class="table-height"
            ref="tableWatcher"
            :loading="gridBasicInfoLoading"
            :data="gridBasicInfoTableData"
            align="center"
            stripe
            showBorder
            :allColumns="gridBasicInfoColumns"
            :showPagination="false"
          >
            <template #action="scope">
              <i-table-btns
                :btn-lists="gridBasicInfoTableBtns"
                @click="(type) => clickHandle(type, scope.row)"
              >
              </i-table-btns>
            </template>
          </i-table>
      </div> -->

      <tableTitle title="网格内派出所信息" class="content-title">
        <template #left  v-if="gridParams.type == 2">
          <div class="add-content" v-hasPermi="['gridInfoManage:gridRate:add']" @click="rateAddHandle">
            <svg-icon icon-class="add" class="add-icon"></svg-icon>
            <span>添加</span>
          </div>
        </template>
      </tableTitle>
      <div class="content-content">
        <i-table
          class="table-height"
          ref="table"
          :data="rateTableData"
          align="center"
          stripe
          showBorder
          :allColumns="rateColumns"
          :showPagination="false"
        >
          <template #action="scope">
            <i-table-btns
              :btn-lists="rateTableBtn"
              @click="(type) => clickHandle(type, scope.row)"
            >
            </i-table-btns>
          </template>
        </i-table>
      </div>

    </div>
    </PageContent>
    <gridInfoEdit
      v-model="showGridInfo"
      :arrCode="arrCode"
      :infoId="infoId"
      :gridInfo="gridInfo"
    >
    </gridInfoEdit>
    <keyPersonEdit
      v-model="showKey"
      :status="status"
      :edit-info="editInfo"
      :arrCode="arrCode"
      :infoId="infoId"
    >
    </keyPersonEdit>
    <gridRateEdit
      v-model="showRate"
      :edit-info="editInfo"
      :status="status"
      :arrCode="arrCode"
      :infoId="infoId"
    >
    </gridRateEdit>
    <add-community-grid-dailog
      v-model="showAddCommunityGrid"
      :status="status"
      :defaultValue="defaultValue"
    ></add-community-grid-dailog>
    <add-community-watcher-dialog
      v-model="showAddCommunityWatch"
      :status="status"
      :defaultValue="defaultValue"
    ></add-community-watcher-dialog>
    <add-grid-basic-org
      v-model="showGrdiBasicInfoDialog"
      :status="status"
      :defaultValue="defaultValue"
    ></add-grid-basic-org>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import keyPersonEdit from "../components/keyPersonEdit";
import gridRateEdit from "../components/gridRateEdit";
import gridInfoEdit from "../components/gridInfoEdit.vue";
import AddCommunityGridDailog from "../components/addCommunityGridDailog.vue";
import AddCommunityWatcherDialog from "../components/addCommunityWatcherDialog.vue";
import addGridBasicOrg from "../components/addGridBasicOrg.vue";
export default {
  name: "GridInfoManage",
  mixins: [pageMixin],
  dicts: ["new_grid_type", "basic_org_type"],
  components: {
    keyPersonEdit,
    gridRateEdit,
    gridInfoEdit,
    AddCommunityGridDailog,
    AddCommunityWatcherDialog,
    addGridBasicOrg,
  },
  data() {
    const checkNumber = (rule, value, callback) => {
      if (!/^\d+$/.test(value)) {
        callback(new Error());
      } else {
        callback();
      }
    };
    const checkMax = (rule, value, callback) => {
      if (Number(value) > 999999999) {
        callback(new Error());
      } else {
        callback();
      }
    };

    return {
      filterTree: ["金华市"],
      size: "small",
      arrCode: null,
      infoId: null,
      baseColumns: [
        {
          label: "网格编号",
          key: "gridCode",
          value: "",
          type: "text",
          disabled: true,
          placeholder: " ",
        },
        {
          label: "网格名称",
          key: "gridName",
          value: "",
          type: "text",
          disabled: true,
          placeholder: " ",
        },
        {
          label: "网格类型",
          key: "ruralGridType",
          value: "",
          type: "select",
          disabled: true,
          placeholder: " ",
          options: () => this.dict.type.new_grid_type,
        },
        {
          label: "是否重点网格",
          key: "isCash",
          value: "",
          type: "select",
          disabled: () => !this.editBaseInfo,
          placeholder: " ",
          options: () => [
            { label: "是", value: "1" },
            { label: "否", value: "0" },
          ],
        },
        {
          label: "银行网格长",
          key: "gridMainManagerName",
          value: "",
          type: "text",
          disabled: () => !this.editBaseInfo,
          placeholder: " ",
        },
        {
          label: "联系电话",
          key: "gridMainManagerPhone",
          value: "",
          type: "text",
          disabled: () => !this.editBaseInfo,
          placeholder: " ",
        },
        {
          label: "银行网格员",
          key: "gridInspectorName",
          value: "",
          type: "text",
          disabled: () => !this.editBaseInfo,
          placeholder: " ",
        },
        {
          label: "联系电话",
          key: "gridInspectorPhone",
          value: "",
          type: "text",
          disabled: () => !this.editBaseInfo,
          placeholder: " ",
        },
      ],
      cashServiceColumns: [
        {label: "网格内摸排商户数",key: "gridCode",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内现金收付摸排次数",key: "gridCode1",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内宣传次数",key: "gridCode2",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "宣传受众人数(万人)",key: "gridCode3",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内大篷车、小钱箱等现金服务次数",key: "gridCode4",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "发现拒收现金线索数量",key: "gridCode5",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内小面额兑出数量",key: "gridCode6",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内小面额兑出金额",key: "gridCode7",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内残损币收兑数量",key: "gridCode8",value: "",type: "text",disabled: true,placeholder: " ",},
        {label: "网格内残损币收兑金额",key: "gridCode9",value: "",type: "text",disabled: true,placeholder: " ",},
      ],

      baseBottomColumns: [
        {
          label: "网格概况",
          key: "gridOverview",
          type: "text",
          disabled: () => !this.editBaseInfo,
          maxlength: 500,
          rules: [{ required: true, message: "网格概况不能为空" }],
        },
        {
          label: "网格备注",
          key: "gridRemark",
          type: "text",
          disabled: () => !this.editBaseInfo,
          maxlength: 500,
        },
        {
          label: "网格照片",
          key: "fileList",
          disabled: () => !this.editBaseInfo,
          value: [],
          type: "imgupload",
          rules: [
            { required: true, type: "array", message: "网格照片不能为空" },
          ],
        },
      ],
      infoColumns: [
        {
          label: "责任网点",
          key: "dutyNetworkName",
          type: "text",
          rules: [
            // { validator: checkNumber, message: "请填写非负整数" },
            // { validator: checkMax, message: "不得大于999999999" },
          ],
          disabled: true,
          // disabled: () => !this.ifEdit,
        },
        {
          label: "网点地址",
          key: "dutyNetworkAddress",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "银行机构",
          key: "bankAgencyName",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "网点责任人",
          key: "dutyName",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "联系电话",
          key: "dutyPhone",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "固定电话",
          key: "dutyTelephone",
          type: "text",
          rules: [],
          disabled: true,
        },
        // {
        //   label: "优质单位情况",
        //   key: "hiqUnitsSituation",
        //   type: "text",
        //   rules: [],
        //   disabled: true,
        // },
      ],
      othersColumns: [
        {
          label: "网点名称",
          key: "networkName",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "网点地址",
          key: "networkAddress",
          type: "text",
          rules: [],
          disabled: true,
        },
        {
          label: "银行机构",
          key: "bankAgencyName",
          type: "text",
          rules: [],
          disabled: true,
        },
      ],
      othersData: [],
      ifEdit: false,
      editBaseInfo: false,
      keyTableLoading: false,
      keyTableData: [],
      keyTableColumns: [
        { label: "网点名称", prop: "anchorName" },
        { label: "银行机构", prop: "branchBank" },
        { label: "网点地址", prop: "addressDesc" },
        { label: "网点负责人", prop: "dutyName" },
        { label: "联系电话", prop: "dutyPhone" },
      ],
      rateTableData: [],
      rateColumns: [
        { label: "派出所名称", prop: "policeName" },
        { label: "联系人姓名", prop: "unFakerName" },
        { label: "联系电话", prop: "unFakerPhone" },
        { label: "派出所地址", prop: "policeAddress" },
        { label: "操作", slot: true },
      ],
      rateTableBtn: [
        {
          cname: "编辑",
          ename: "rateEditHandle",
          permi: "gridInfoManage:gridRate:edit",
          isShow: () => this.gridParams.type == 2,
        },
        {
          cname: "删除",
          ename: "rateDeleteHandle",
          permi: "gridInfoManage:gridRate:delete",
          isShow: () => this.gridParams.type == 2,
          danger:'danger'
        },
      ],
      status: "add",
      editInfo: {},
      showKey: false,
      showRate: false,
      showGridInfo: false,
      gridInfo: {},
      importantListColumns: [
        { label: "重点管理对象", prop: "importName" },
        { label: "重点管理对象地址", prop: "importAddress" },
        { label: "操作", slot: true },
      ],
      importantListTableBtns: [
        {
          cname: "编辑",
          ename: "editGridImportant",
          isShow: () => this.gridParams.type == 2,
        },
        {
          cname: "删除",
          ename: "deleteGridImportant",
          isShow: () => this.gridParams.type == 2,
          danger:'danger'
        },
      ],
      importantList: [],
      importantListLoading: false,
      showAddCommunityGrid: false,
      communityWatchColumns: [
        { label: "网格员名称", prop: "supervisorName" },
        { label: "联系电话", prop: "supervisorPhone" },
        { label: "操作", slot: true },
      ],
      communityWatcherTableBtns: [
        {
          cname: "编辑",
          ename: "editCommunityWatcher",
          isShow: () => this.gridParams.type == 2,
        },
        {
          cname: "删除",
          ename: "deleteCommunityWatcher",
          isShow: () => this.gridParams.type == 2,
          danger:'danger'
        },
      ],
      communityWatchTableData: [],
      communityWatcherLoading: false,
      showAddCommunityWatch: false,
      gridBasicInfoColumns: [
        { label: "基层组织名称", prop: "grassroots" },
        {
          label: "基层组织类型",
          prop: "grassrootsType",
          formatter: ({ grassrootsType }) => {
            return this.dict.label.basic_org_type[grassrootsType];
          },
        },
        { label: "联系人", prop: "contactName" },
        { label: "联系电话", prop: "contactPhone" },
        { label: "操作", slot: true },
      ],
      gridBasicInfoTableBtns: [
        {
          cname: "编辑",
          ename: "editGridBasicInfo",
          isShow: () => this.gridParams.type == 2,
        },
        {
          cname: "删除",
          ename: "deleteGridBasicInfo",
          isShow: () => this.gridParams.type == 2,
        },
      ],
      gridBasicInfoTableData: [],
      gridBasicInfoLoading: false,
      showGrdiBasicInfoDialog: false,
      defaultValue: {},
      cashPointListLoading: false,
      cashPointListColumns: [
        {
          label: "服务点名称",
          prop: "servePointName",
          showOverFlowTooltip: true,
        },
        {
          label: "所在网格",
          prop: "gridName",
        },
        {
          label: "服务点地址",
          prop: "servePointAddr",
        },
        {
          label: "现金服务点负责人",
          prop: "dutyName",
        },
        {
          label: "联系电话",
          prop: "dutyPhone",
        },
      ],
      cashPointList: [],
      finicialPointListLoading: false,
      finicialPointListColumns: [
        {
          label: "金融服务站名称",
          prop: "finicialName",
          showOverFlowTooltip: true,
        },
        {
          label: "所在网格",
          prop: "belongGrid",
        },
        {
          label: "具体地址",
          prop: "addrDetail",
        },
        {
          label: "站长姓名",
          prop: "stationMasterName",
        },
        {
          label: "联系电话",
          prop: "linkPhone",
        },
      ],
      finicialPointList: [],
      importantMerachantColumns:[
        {label: "重点商户名称",prop: "finicialName",showOverFlowTooltip: true,},
        {label: "所在网格",prop: "belongGrid",showOverFlowTooltip: true,},
        {label: "具体地址",prop: "addrDetail",showOverFlowTooltip: true,},
        {label: "商户法人姓名",prop: "finicialName11",showOverFlowTooltip: true,},
        {label: "联系电话",prop: "linkPhone",showOverFlowTooltip: true,},
      ],
      importantMerachantList:[],

    };
  },
  computed: {
    managerNum() {
      return this.$store.getters["userInfo"].managerNum;
    },
    ifBaseInfoEdit() {
      if (this.gridInfo) {
        return (
          (this.gridInfo.gridMainManagerCode === this.managerNum &&
            (!this.gridInfo.isModify || this.gridInfo.isModify == "0")) ||
          this.gridInfo.gridMainManagerCode !== this.managerNum
        );
      } else {
        return true;
      }
    },
    gridParams() {
      return this.$store.getters["gridManagement/gridInfo"];
    },
  },
  watch: {
    "gridParams.arrCode": {
      handler(val) {
        // this.arrCode = val;
        this.initData();
      },
      immediate: true,
      deep: true,
    },
  },
  mounted() {

  },
  methods: {
    getOthersColumns(index) {
      return "otherForm" + index;
    },
    initData() {
      this.arrCode = this.gridParams.arrCode;
      this.$refs.baseForm?.reset();
      // this.$refs.baseBottomForm.reset();
      this.$refs.infoForm?.reset();
      this.fileList = [];
      this.keyTableData = [];
      this.rateTableData = [];
      this.fetchList();
      this.fetchImportantList();
      this.getOtherPointList();
      this.fecthCashPointList();
      this.fecthFinicialPointList();
      this.fetchCommunityWatcher();
      this.getGridRatingInfo();
      this.fetchGridBasicOrgInfo();
    },
    fetchList() {
      const param = {
        gridCode: this.arrCode,
      };
      service.getByGridCode(param).then((res) => {
        const { data } = res;
        console.log('>>>>获取的网格信息',data);

        data.gridCustomerTotal = !data.gridCustomerTotal
          ? "0"
          : data.gridCustomerTotal;
        this.gridInfo = data;
        this.infoId = data.id;
        this.$refs.baseForm.setForm(data);
        if (data.picture) {
          data.fileList = data.picture.split(",").map((item) => {
            return {
              url: item,
              name: this.imageName(item),
            };
          });
        } else {
          data.fileList = [];
        }
        // this.$refs.baseBottomForm.setForm(data);
        this.$refs.infoForm.setForm(data);
        this.fileList = [];
      });
    },
    getOtherPointList() {
      this.keyTableLoading = true;
      const param = {
        gridCode: this.arrCode,
      };
      service
        .getOtherPointList(param)
        .then((res) => {
          const { rows } = res;
          this.keyTableData = rows;
          this.keyTableLoading = false;
        })
        .catch((err) => {
          this.keyTableLoading = false;
        });
    },
    getGridRatingInfo() {
      const param = {
        gridCode: this.arrCode,
      };
      service.getGridRatingInfo(param).then((res) => {
        const { data } = res;
        this.rateTableData = data;
      });
    },
    /* 基础信息 */
    openGridInfo() {
      this.showGridInfo = true;
    },
    baseInfoSave() {
      this.$refs.infoForm.getForm((formInfo) => {
        this.$refs.baseForm.getForm((baseInfo) => {
          console.log(formInfo, baseInfo);
          const {
            gridMainManagerName,
            gridMainManagerPhone,
            isCash,
            gridInspectorName,
            gridInspectorPhone,
          } = baseInfo;
          const { dutyName, dutyPhone, dutyTelephone } = formInfo;
          const param = {
            id: this.infoId,
            gridCode: this.arrCode,
            gridMainManagerName,
            gridMainManagerPhone,
            dutyPhone,
            dutyName,
            isCash,
            dutyTelephone,
            gridInspectorName,
            gridInspectorPhone,
            // picture: fileList.map((item) => item.url).join(","),
          };
          service.gridMessageSave(param).then((res) => {
            this.editBaseInfo = false;
            this.fetchList();
          });
        });
      });
    },
    infoSave() {
      this.$refs.infoForm.getForm((formInfo) => {
        const param = {
          ...formInfo,
          id: this.infoId,
          gridCode: this.arrCode,
          isModify: "1",
        };
        service.gridMessageSave(param).then((res) => {
          this.ifEdit = false;
          this.fetchList();
        });
      });
    },
    /* 网格关键人 */
    addKeyHandle() {
      this.status = "add";
      this.showKey = true;
      this.editInfo = {};
    },
    keyEditHandle(row) {
      this.status = "edit";
      this.editInfo = row;
      this.showKey = true;
    },
    keyDeleteHandle(row) {
      const { id } = row;
      this.$modal.confirm("是否确认删除").then(() => {
        service.deleteGridCruxByIds(id).then((res) => {
          this.$modal.msgSuccess("删除成功");
          this.getOtherPointList();
        });
      });
    },
    /* 网格评级维护 */
    rateAddHandle() {
      this.status = "add";
      this.showRate = true;
      this.editInfo = {};
    },
    rateEditHandle(row) {
      this.status = "edit";
      this.editInfo = row;
      this.showRate = true;
    },
    rateDeleteHandle(row) {
      const { id } = row;
      this.$modal.confirm("是否确认删除").then(() => {
        service.deleteGridRatingByIds(id).then((res) => {
          this.$modal.msgSuccess("删除成功");
          this.getGridRatingInfo();
        });
      });
    },
    // 重点管理对象
    fetchImportantList() {
      this.importantListLoading = true;
      let params = {
        gridCode: this.arrCode,
      };
      service
        .getGridImportant(params)
        .then((res) => {
          this.importantList = res;
          this.importantListLoading = false;
        })
        .catch((err) => {
          this.importantList = [];
          this.importantListLoading = false;
        });
    },
    addGridImportant() {
      this.defaultValue = {
        gridCode: this.arrCode,
      };
      this.status = "add";
      this.showAddCommunityGrid = true;
    },
    editGridImportant(row) {
      this.defaultValue = row;
      this.status = "edit";
      this.showAddCommunityGrid = true;
    },
    deleteGridImportant(row) {
      this.makeSure("确认删除?", () => {
        let params = { ...row };
        service.deleteGridImportant(params).then((res) => {
          this.$modal.msgSuccess(`删除成功`);
          this.fetchImportantList();
        });
      });
    },

    // 社会网格员
    fetchCommunityWatcher() {
      this.communityWatcherLoading = true;
      let params = {
        gridCode: this.arrCode,
      };
      service
        .getCommunityWatcherList(params)
        .then((res) => {
          this.communityWatchTableData = res;
          this.communityWatcherLoading = false;
        })
        .catch((err) => {
          this.communityWatchTableData = [];
          this.communityWatcherLoading = false;
        });
    },
    addCommunityWatcher() {
      this.defaultValue = { gridCode: this.arrCode };
      this.status = "add";
      this.showAddCommunityWatch = true;
    },
    editCommunityWatcher(row) {
      this.defaultValue = row;
      this.status = "edit";
      this.showAddCommunityWatch = true;
    },
    deleteCommunityWatcher(row) {
      this.makeSure("确认删除?", () => {
        let params = { ...row };
        service.deleteCommunityWatcher(params).then((res) => {
          this.$modal.msgSuccess(`删除成功`);
          this.fetchCommunityWatcher();
        });
      });
    },
    // 网格内现金服务点和金融服务站列表
    fecthCashPointList() {
      this.cashPointListLoading = true;
      let data = [this.arrCode];
      service
        .getCashPointInGrid(data)
        .then((res) => {
          this.cashPointList = res.data;
          this.cashPointListLoading = false;
        })
        .catch((err) => {
          this.cashPointListLoading = false;
        });
    },
    fecthFinicialPointList() {
      this.finicialPointListLoading = true;
      let data = [this.arrCode];
      service
        .getFinancialInGrid(data)
        .then((res) => {
          this.finicialPointList = res.data;
          this.finicialPointListLoading = false;
        })
        .catch((err) => {
          this.finicialPointListLoading = false;
        });
    },
    imageName(imageURL) {
      return imageURL.substring(imageURL.lastIndexOf("/") + 1);
    },
    fetchGridBasicOrgInfo() {
      this.gridBasicInfoLoading = true;
      let params = {
        gridCode: this.arrCode,
      };
      service
        .getGridBasicOrgList(params)
        .then((res) => {
          this.gridBasicInfoTableData = res;
          this.gridBasicInfoLoading = false;
        })
        .catch((err) => {
          this.gridBasicInfoTableData = [];
          this.gridBasicInfoLoading = false;
        });
    },
    addGridBasicInfo() {
      this.defaultValue = { gridCode: this.arrCode };
      this.status = "add";
      this.showGrdiBasicInfoDialog = true;
    },
    editGridBasicInfo(row) {
      this.defaultValue = row;
      this.status = "edit";
      this.showGrdiBasicInfoDialog = true;
    },
    deleteGridBasicInfo(row) {
      this.makeSure("确认删除?", () => {
        let params = { ...row };
        service.deleteGridBasicOrg(params).then((res) => {
          this.$modal.msgSuccess(`删除成功`);
          this.fetchGridBasicOrgInfo();
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.content-wrapper {
  // padding: 0 20px;
  height: calc(100vh - 110px);
  overflow: auto;
}
.content-title {
  height: 68px;
  padding: 16px;
  // padding-right: 10px;
  // font-size: 20px;
  // margin: 10px 0 30px;
  // display: flex;
  // height: 32px;
  // justify-content: space-between;
  // align-items: center;
  // background: #fff;
}
.hasBorderTop {
  margin-top: 10px;
  border-bottom: 1px solid #e7e6e6;
}
.content-content {
  background: #fff;
  padding: 16px;
}
.add-content {
  margin-left: 12px;
  cursor: pointer;
  font-size: 14px;
  color: #5E81F4;
  text-decoration: underline;
  line-height: 14px;
  .add-icon {
    width: 14px;
    height: 14px;
    margin-right: 4px;
  }
}
::v-deep .table-wrapper {
  height: auto;
}
.othersListBox {
  max-height: 400px;
  overflow: auto;
}
.othersList {
  position: relative;
  &-content {
    width: 10px;
    height: 10px;
    border-radius: 25px;
    background: #555;
    position: absolute;
    left: 0;
    top: 12px;
  }
  .el-form {
    padding-left: 20px;
    .el-input {
      width: 95%;
    }
  }
}

::v-deep .el-upload-list__item,
::v-deep .el-upload-list__item-name {
  transition: none;
  -webkit-transition: none;
}
.grid-select {
  width: 100%;
  margin-bottom: 10px;
}
.tree-wrapper {
  overflow: hidden;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  .tree-content {
    width: 100%;
    height: calc(100% - 46px);
    overflow: auto;
    ::v-deep .el-tree-node > .el-tree-node_children {
      overflow: visible;
    }
  }
}
.allrowed {
  cursor: not-allowed;
}
.el-empty {
  padding: 0 !important;
  .el-empty__image {
    width: 80px !important;
  }
}
.important {
  color: #000;
}
.notImportant {
  color: #a8a6a6;
}
</style>
