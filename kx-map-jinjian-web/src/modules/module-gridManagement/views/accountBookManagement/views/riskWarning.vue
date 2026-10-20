<template>
  <div class="risk-warning-box">
    <!-- 头部操作栏 -->
    <div class="header-box">
      <div class="header-content">
        <el-button type="primary" @click="addWarning">新建预警</el-button>

        <div class="filter-section">
          <el-select
            v-model="formInline.scopeType"
            placeholder="选择范围"
            class="select-item"
          >
            <el-option label="全省" value="province"></el-option>
            <el-option label="特定区县" value="district"></el-option>
          </el-select>

          <el-select
            v-model="formInline.city"
            placeholder="选择城市"
            class="select-item"
            v-if="formInline.scopeType === 'district'"
          >
            <el-option label="杭州市" value="hangzhou"></el-option>
            <el-option label="温州市" value="wenzhou"></el-option>
            <el-option label="宁波市" value="ningbo"></el-option>
            <el-option label="嘉兴市" value="jiaxing"></el-option>
            <el-option label="湖州市" value="huzhou"></el-option>
            <el-option label="绍兴市" value="shaoxing"></el-option>
            <el-option label="金华市" value="jinhua"></el-option>
            <el-option label="衢州市" value="quzhou"></el-option>
            <el-option label="舟山市" value="zhoushan"></el-option>
            <el-option label="台州市" value="taizhou"></el-option>
            <el-option label="丽水市" value="lishui"></el-option>
          </el-select>

          <el-date-picker
            v-model="formInline.dateRange"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            class="date-picker-item"
          ></el-date-picker>

          <el-select
            v-model="formInline.status"
            placeholder="选择状态"
            class="select-item"
          >
            <el-option label="草稿" value="draft"></el-option>
            <el-option label="审核中" value="reviewing"></el-option>
            <el-option label="已发布" value="published"></el-option>
            <el-option label="已归档" value="archived"></el-option>
          </el-select>

          <el-button @click="resetForm" style="margin-left: 12px"
            >重置</el-button
          >
        </div>
      </div>
    </div>

    <!-- 统计卡片 -->
    <div class="statistics-box">
      <div class="statistic-card">
        <div class="card-content">
          <img
            src="@/modules/module-gridManagement/assets/images/tongjiImg4.png"
            alt="预警总数"
            class="card-img"
          />
          <div class="card-info">
            <div class="card-title">预警总数</div>
            <div class="card-value">10</div>
          </div>
        </div>
      </div>
      <div class="statistic-card">
        <div class="card-content">
          <img
            src="@/modules/module-gridManagement/assets/images/tongjiImg9.png"
            alt="已发布"
            class="card-img"
          />
          <div class="card-info">
            <div class="card-title">已发布</div>
            <div class="card-value">2</div>
          </div>
        </div>
      </div>
      <div class="statistic-card">
        <div class="card-content">
          <img
            src="@/modules/module-gridManagement/assets/images/tongjiImg10.png"
            alt="已归档"
            class="card-img"
          />
          <div class="card-info">
            <div class="card-title">已归档</div>
            <div class="card-value">2</div>
          </div>
        </div>
      </div>
    </div>

    <!-- 表格 -->
    <div class="table-box">
      <el-table
        :data="tableData"
        style="width: 100%"
        border
        stripe
        :height="tableHeight"
        :max-height="tableHeight"
      >
        <el-table-column
          prop="code"
          label="预警编号"
          width="120"
        ></el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          min-width="200"
        ></el-table-column>
        <el-table-column prop="scope" label="范围" min-width="150">
          <template slot-scope="scope">
            <el-tag
              v-for="(item, index) in scope.row.scope"
              :key="index"
              size="small"
              :type="item === '全省' ? 'primary' : 'info'"
              effect="plain"
              style="margin-right: 4px"
            >
              {{ item }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="targets" label="推送对象" min-width="250">
          <template slot-scope="scope">
            <el-tag
              v-for="(item, index) in scope.row.targets"
              :key="index"
              size="small"
              type="info"
              effect="plain"
              style="margin-right: 4px"
            >
              {{ item }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="getStatusType(scope.row.status)" size="small">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="创建时间"
          width="140"
        ></el-table-column>
        <el-table-column prop="progress" label="反馈进度" min-width="180">
          <template slot-scope="scope">
            <el-progress
              :percentage="scope.row.progress"
              :stroke-width="10"
              :format="formatProgress"
            ></el-progress>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewDetail(scope.row)"
              >详情</el-button
            >
            <el-button
              v-if="scope.row.status === 'reviewing'"
              type="text"
              size="small"
              @click="reviewWarning(scope.row)"
              >审核</el-button
            >
            <el-button
              v-if="scope.row.status === 'published'"
              type="text"
              size="small"
              @click="feedbackWarning(scope.row)"
              >反馈</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-box">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5, 10, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </div>

    <!-- 新建预警弹窗 -->
    <el-dialog
      title="新建预警"
      :visible.sync="dialogVisible"
      width="500px"
      @close="resetDialogForm"
    >
      <el-form :model="dialogForm" :rules="dialogRules" ref="dialogForm">
        <el-form-item label="预警标题" prop="title" label-width="80px">
          <el-input
            v-model="dialogForm.title"
            placeholder="请输入预警标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="预警范围" prop="scope" label-width="80px">
          <el-cascader
            v-model="dialogForm.scope"
            :options="cascaderOptions"
            placeholder="请选择预警范围"
            :props="cascaderProps"
            style="width: 100%"
          ></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitDialogForm">确定</el-button>
      </span>
    </el-dialog>

    <!-- 预警审核弹窗 -->
    <el-dialog
      title="预警审核"
      :visible.sync="reviewDialogVisible"
      width="600px"
      @close="resetReviewForm"
    >
      <el-form :model="reviewForm" :rules="reviewRules" ref="reviewForm">
        <el-form-item label="审核结果" prop="result" label-width="100px">
          <el-radio-group v-model="reviewForm.result">
            <el-radio label="pass">审核通过</el-radio>
            <el-radio label="reject">审核驳回</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item
          label="推送对象"
          prop="targets"
          label-width="100px"
          v-if="reviewForm.result === 'pass'"
        >
          <el-select
            v-model="reviewForm.targets"
            multiple
            placeholder="请选择监管部门或金融机构"
            style="width: 100%"
          >
            <el-option
              v-for="item in targetOptions"
              :key="item.value"
              :label="item.label"
              :value="item.label"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item
          label="风险主体特征"
          prop="riskCharacteristics"
          label-width="100px"
          v-if="reviewForm.result === 'pass'"
        >
          <el-input
            v-model="reviewForm.riskCharacteristics"
            type="textarea"
            placeholder="请填写风险主体特征"
            rows="3"
          ></el-input>
        </el-form-item>

        <el-form-item
          label="活动形式"
          prop="activityForm"
          label-width="100px"
          v-if="reviewForm.result === 'pass'"
        >
          <el-input
            v-model="reviewForm.activityForm"
            type="textarea"
            placeholder="请填写活动形式"
            rows="3"
          ></el-input>
        </el-form-item>

        <el-form-item
          label="防范建议"
          prop="preventionSuggestions"
          label-width="100px"
          v-if="reviewForm.result === 'pass'"
        >
          <el-input
            v-model="reviewForm.preventionSuggestions"
            type="textarea"
            placeholder="请填写防范建议"
            rows="3"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="reviewDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReviewForm">确定</el-button>
      </span>
    </el-dialog>

    <!-- 机构反馈弹窗 -->
    <el-dialog
      title="机构反馈"
      :visible.sync="feedbackDialogVisible"
      width="600px"
      @close="resetFeedbackForm"
    >
      <el-form :model="feedbackForm" :rules="feedbackRules" ref="feedbackForm">
        <el-form-item label="反馈机构" prop="organization" label-width="100px">
          <el-input
            v-model="feedbackForm.organization"
            placeholder="请输入机构名称（如：杭州市金融办、工商银行浙江分行）"
          ></el-input>
        </el-form-item>

        <el-form-item label="反馈内容" prop="content" label-width="100px">
          <el-input
            v-model="feedbackForm.content"
            type="textarea"
            placeholder="请输入排查情况、处置结果等信息..."
            rows="4"
          ></el-input>
        </el-form-item>

        <el-form-item label="附件" label-width="100px">
          <el-upload
            class="upload-demo"
            action="#"
            :on-change="handleFileChange"
            :auto-upload="false"
            :limit="3"
            :file-list="feedbackForm.attachments"
          >
            <el-button size="small" type="primary">选择附件</el-button>
            <div slot="tip" class="el-upload__tip">
              支持上传 pdf、xlsx、word 等格式文件，最多上传3个
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="feedbackDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitFeedbackForm">确定</el-button>
      </span>
    </el-dialog>

    <!-- 详情抽屉 -->
    <el-drawer
      title=""
      :visible.sync="detailDrawerVisible"
      direction="rtl"
      size="600px"
    >
      <div v-if="currentDetailRow" class="detail-content">
        <!-- 头部操作 -->
        <div class="detail-header">
          <h3 class="detail-title">{{ currentDetailRow.title }}</h3>
          <div class="detail-actions">
            <el-button
              v-if="currentDetailRow.status === 'reviewing'"
              type="primary"
              @click="reviewWarning(currentDetailRow)"
              >审核</el-button
            >
            <el-button
              v-if="currentDetailRow.status === 'published'"
              type="primary"
              @click="feedbackWarning(currentDetailRow)"
              >反馈</el-button
            >
          </div>
        </div>

        <!-- 步骤条 -->
        <div class="detail-steps">
          <el-steps
            :active="getStepIndex(currentDetailRow.status)"
            align-center
          >
            <el-step title="草稿"></el-step>
            <el-step title="审核中"></el-step>
            <el-step title="已发布"></el-step>
            <el-step title="已归档"></el-step>
          </el-steps>
        </div>

        <!-- 基本信息 -->
        <div class="detail-section">
          <h4 class="section-title">基本信息</h4>
          <div class="info-item">
            <span class="info-label">范围：</span>
            <span class="info-value">
              <el-tag
                v-for="(item, index) in currentDetailRow.scope"
                :key="index"
                size="small"
                :type="item === '全省' ? 'primary' : 'info'"
                effect="plain"
                style="margin-right: 4px"
              >
                {{ item }}
              </el-tag>
            </span>
          </div>
          <div class="info-item">
            <span class="info-label">创建时间：</span>
            <span class="info-value">{{ currentDetailRow.createTime }}</span>
          </div>
          <div class="info-item">
            <span class="info-label">推送对象：</span>
            <span class="info-value"
              >{{ currentDetailRow.targets.length }} 个</span
            >
          </div>
          <div class="info-item">
            <span class="info-label">推送详情：</span>
            <span class="info-value">{{
              currentDetailRow.targets.join("、") || "暂无"
            }}</span>
          </div>
        </div>

        <!-- 风险说明 -->
        <div class="detail-section">
          <h4 class="section-title">风险说明</h4>
          <div class="info-item">
            <span class="info-label">主体特征：</span>
            <span class="info-value"
              >主体具备多账户、多通道特征，账户之间频繁短时转账。</span
            >
          </div>
          <div class="info-item">
            <span class="info-label">活动模式：</span>
            <span class="info-value"
              >集中在节假日前后活跃，通过第三方平台形成资金闭环。</span
            >
          </div>
          <div class="info-item">
            <span class="info-label">防范建议：</span>
            <span class="info-value"
              >建议加强账户穿透分析，限制异常交易额度，联动网点开展重点排查。</span
            >
          </div>
        </div>

        <!-- 反馈进度 -->
        <div
          v-if="
            currentDetailRow.status === 'published' ||
            currentDetailRow.status === 'archived'
          "
          class="detail-section"
        >
          <h4 class="section-title">反馈进度</h4>
          <div class="progress-section">
            <el-progress
              :percentage="currentDetailRow.progress"
              :stroke-width="15"
              :format="formatProgress"
            ></el-progress>
          </div>

          <!-- 反馈记录 -->
          <div class="feedback-records">
            <div class="record-item">
              <div class="record-header">
                <span class="record-organization">某地金融办</span>
                <span class="record-time">2026-03-20</span>
              </div>
              <div class="record-content">
                已收到预警信息，正在组织辖区银行进行排查，初步发现疑似账户3个，后续将持续跟踪。
              </div>
              <div class="record-attachment">
                <el-link type="primary">排查清单.xlsx</el-link>
              </div>
            </div>
            <div class="record-item">
              <div class="record-header">
                <span class="record-organization">某地金融办</span>
                <span class="record-time">2026-03-21</span>
              </div>
              <div class="record-content">
                已收到预警信息，正在组织辖区银行进行排查，初步发现疑似账户3个，后续将持续跟踪。
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formInline: {
        scopeType: "",
        city: "",
        dateRange: [],
        status: "",
      },
      currentPage: 1,
      pageSize: 10,
      total: 10,
      tableHeight: null,
      allTableData: [
        {
          code: "YW-1000",
          title: "跨区域资金异常流动预警-1",
          scope: ["全省"],
          targets: ["温州市金融办", "浙江银保监局"],
          status: "draft",
          createTime: "2026-03-22",
          progress: 0,
        },
        {
          code: "YW-1001",
          title: "跨区域资金异常流动预警-2",
          scope: ["杭州市", "温州市", "宁波市"],
          targets: ["建设银行浙江省分行", "网商银行"],
          status: "reviewing",
          createTime: "2026-03-21",
          progress: 50,
        },
        {
          code: "YW-1002",
          title: "跨区域资金异常流动预警-3",
          scope: ["嘉兴市", "湖州市"],
          targets: ["人民银行杭州中心支行", "温州市金融办"],
          status: "published",
          createTime: "2026-03-20",
          progress: 89,
        },
        {
          code: "YW-1003",
          title: "跨区域资金异常流动预警-4",
          scope: ["绍兴市", "金华市", "衢州市"],
          targets: ["浙江银保监局", "建设银行浙江省分行"],
          status: "archived",
          createTime: "2026-03-19",
          progress: 100,
        },
        {
          code: "YW-1004",
          title: "跨区域资金异常流动预警-5",
          scope: ["全省"],
          targets: ["浙江银保监局", "人民银行杭州中心支行"],
          status: "draft",
          createTime: "2026-03-18",
          progress: 0,
        },
        {
          code: "YW-1005",
          title: "跨区域资金异常流动预警-6",
          scope: ["台州市", "丽水市"],
          targets: ["网商银行", "建设银行浙江省分行"],
          status: "reviewing",
          createTime: "2026-03-17",
          progress: 30,
        },
        {
          code: "YW-1006",
          title: "跨区域资金异常流动预警-7",
          scope: ["舟山市", "宁波市"],
          targets: ["温州市金融办", "浙江银保监局"],
          status: "published",
          createTime: "2026-03-16",
          progress: 75,
        },
        {
          code: "YW-1007",
          title: "跨区域资金异常流动预警-8",
          scope: ["全省"],
          targets: ["人民银行杭州中心支行", "建设银行浙江省分行"],
          status: "archived",
          createTime: "2026-03-15",
          progress: 100,
        },
        {
          code: "YW-1008",
          title: "跨区域资金异常流动预警-9",
          scope: ["杭州市", "嘉兴市"],
          targets: ["浙江银保监局", "网商银行"],
          status: "draft",
          createTime: "2026-03-14",
          progress: 0,
        },
        {
          code: "YW-1009",
          title: "跨区域资金异常流动预警-10",
          scope: ["温州市", "金华市"],
          targets: ["温州市金融办", "人民银行杭州中心支行"],
          status: "reviewing",
          createTime: "2026-03-13",
          progress: 45,
        },
      ], // 完整数据
      tableData: [], // 分页后的数据
      dialogVisible: false,
      reviewDialogVisible: false,
      feedbackDialogVisible: false,
      detailDrawerVisible: false,
      currentReviewRow: null,
      currentFeedbackRow: null,
      currentDetailRow: null,
      dialogForm: {
        title: "",
        scope: [],
      },
      dialogRules: {
        title: [{ required: true, message: "请输入预警标题", trigger: "blur" }],
        scope: [{ required: true, message: "请选择预警范围", trigger: "blur" }],
      },
      reviewForm: {
        result: "pass",
        targets: [],
        riskCharacteristics: "",
        activityForm: "",
        preventionSuggestions: "",
      },
      feedbackDialogVisible: false,
      currentFeedbackRow: null,
      feedbackForm: {
        organization: "",
        content: "",
        attachments: [],
      },
      feedbackRules: {
        organization: [
          { required: true, message: "请输入机构名称", trigger: "blur" },
        ],
        content: [
          { required: true, message: "请输入反馈内容", trigger: "blur" },
        ],
      },
      reviewRules: {
        result: [
          { required: true, message: "请选择审核结果", trigger: "change" },
        ],
        targets: [
          {
            required: true,
            message: "请选择监管部门或金融机构",
            trigger: "change",
          },
        ],
        riskCharacteristics: [
          { required: true, message: "请填写风险主体特征", trigger: "blur" },
        ],
        activityForm: [
          { required: true, message: "请填写活动形式", trigger: "blur" },
        ],
        preventionSuggestions: [
          { required: true, message: "请填写防范建议", trigger: "blur" },
        ],
      },
      targetOptions: [
        { label: "温州市金融办", value: "wenzhou-finance" },
        { label: "浙江银保监局", value: "zhejiang-banking" },
        { label: "人民银行杭州中心支行", value: "hangzhou-pboc" },
        { label: "建设银行浙江省分行", value: "ccb-zhejiang" },
        { label: "网商银行", value: "mybank" },
      ],
      cascaderOptions: [
        {
          value: "zhejiang",
          label: "浙江省",
          children: [
            {
              value: "hangzhou",
              label: "杭州市",
              children: [
                { value: "hangzhou-shangcheng", label: "上城区" },
                { value: "hangzhou-xihu", label: "西湖区" },
                { value: "hangzhou-jingjiang", label: "江干区" },
                { value: "hangzhou-gongshu", label: "拱墅区" },
                { value: "hangzhou-binjiang", label: "滨江区" },
              ],
            },
            {
              value: "wenzhou",
              label: "温州市",
              children: [
                { value: "wenzhou-lucheng", label: "鹿城区" },
                { value: "wenzhou-longwan", label: "龙湾区" },
                { value: "wenzhou-ouhai", label: "瓯海区" },
              ],
            },
            {
              value: "ningbo",
              label: "宁波市",
              children: [
                { value: "ningbo-haishu", label: "海曙区" },
                { value: "ningbo-jiangbei", label: "江北区" },
                { value: "ningbo-jiangdong", label: "江东区" },
              ],
            },
          ],
        },
      ],
      cascaderProps: {
        expandTrigger: "hover",
        multiple: false,
        checkStrictly: false,
      },
    };
  },
  methods: {
    addWarning() {
      this.dialogVisible = true;
    },
    resetForm() {
      this.formInline = {
        scopeType: "",
        city: "",
        dateRange: [],
        status: "",
      };
    },
    resetDialogForm() {
      this.dialogForm = {
        title: "",
        scope: [],
      };
      if (this.$refs.dialogForm) {
        this.$refs.dialogForm.resetFields();
      }
    },
    submitDialogForm() {
      this.$refs.dialogForm.validate((valid) => {
        if (valid) {
          // 生成新的预警编号
          const newCode = `YW-${String(
            this.allTableData.length + 1000,
          ).padStart(4, "0")}`;

          // 处理预警范围
          let scope = [];
          if (this.dialogForm.scope.length === 1) {
            // 选择了全省
            scope = ["全省"];
          } else if (this.dialogForm.scope.length === 2) {
            // 选择了市
            const cityOption = this.cascaderOptions[0].children.find(
              (city) => city.value === this.dialogForm.scope[1],
            );
            if (cityOption) {
              scope = [cityOption.label];
            }
          } else if (this.dialogForm.scope.length === 3) {
            // 选择了区县
            const cityOption = this.cascaderOptions[0].children.find(
              (city) => city.value === this.dialogForm.scope[1],
            );
            if (cityOption) {
              const districtOption = cityOption.children.find(
                (district) => district.value === this.dialogForm.scope[2],
              );
              if (districtOption) {
                scope = [districtOption.label];
              }
            }
          }

          // 新增预警数据
          const newWarning = {
            code: newCode,
            title: this.dialogForm.title,
            scope: scope,
            targets: [],
            status: "reviewing",
            createTime: new Date().toISOString().split("T")[0],
            progress: 0,
          };

          // 添加到完整数据
          this.allTableData.unshift(newWarning);
          this.total++;
          // 重新获取分页数据
          this.getTableData();

          // 关闭弹窗
          this.dialogVisible = false;
          this.resetDialogForm();

          // 提示成功
          this.$message.success("预警创建成功");
        } else {
          return false;
        }
      });
    },
    getStatusType(status) {
      switch (status) {
        case "draft":
          return "info";
        case "reviewing":
          return "warning";
        case "published":
          return "success";
        case "archived":
          return "danger";
        default:
          return "";
      }
    },
    getStatusText(status) {
      switch (status) {
        case "draft":
          return "草稿";
        case "reviewing":
          return "审核中";
        case "published":
          return "已发布";
        case "archived":
          return "已归档";
        default:
          return "";
      }
    },
    formatProgress(percentage) {
      return `${percentage}%`;
    },
    viewDetail(row) {
      this.currentDetailRow = row;
      this.detailDrawerVisible = true;
    },
    getStepIndex(status) {
      switch (status) {
        case "draft":
          return 0;
        case "reviewing":
          return 1;
        case "published":
          return 2;
        case "archived":
          return 3;
        default:
          return 0;
      }
    },
    reviewWarning(row) {
      this.currentReviewRow = row;
      this.resetReviewForm();
      this.reviewDialogVisible = true;
    },
    resetReviewForm() {
      this.reviewForm = {
        result: "pass",
        targets: [],
        riskCharacteristics: "",
        activityForm: "",
        preventionSuggestions: "",
      };
      if (this.$refs.reviewForm) {
        this.$refs.reviewForm.resetFields();
      }
    },
    submitReviewForm() {
      // 验证表单
      const requiredFields = ["result"];
      if (this.reviewForm.result === "pass") {
        requiredFields.push(
          "targets",
          "riskCharacteristics",
          "activityForm",
          "preventionSuggestions",
        );
      }

      let isValid = true;
      for (const field of requiredFields) {
        if (
          !this.reviewForm[field] ||
          (Array.isArray(this.reviewForm[field]) &&
            this.reviewForm[field].length === 0)
        ) {
          isValid = false;
          break;
        }
      }

      if (!isValid) {
        this.$message.error("请填写必填项");
        return;
      }

      // 处理审核结果
      if (this.reviewForm.result === "reject") {
        // 审核驳回，状态变为草稿
        this.currentReviewRow.status = "draft";
        this.$message.success("审核驳回，退回草稿");
      } else if (this.reviewForm.result === "pass") {
        // 审核通过，状态变为已发布
        this.currentReviewRow.status = "published";
        this.currentReviewRow.targets = this.reviewForm.targets;
        // 可以在这里保存其他审核信息
        this.$message.success("审核通过");
      }

      // 关闭弹窗
      this.reviewDialogVisible = false;
      this.resetReviewForm();
    },
    feedbackWarning(row) {
      this.currentFeedbackRow = row;
      this.resetFeedbackForm();
      this.feedbackDialogVisible = true;
    },
    resetFeedbackForm() {
      this.feedbackForm = {
        organization: "",
        content: "",
        attachments: [],
      };
      if (this.$refs.feedbackForm) {
        this.$refs.feedbackForm.resetFields();
      }
    },
    handleFileChange(file, fileList) {
      this.feedbackForm.attachments = fileList;
    },
    submitFeedbackForm() {
      this.$refs.feedbackForm.validate((valid) => {
        if (valid) {
          // 反馈成功，增加进度10%
          if (this.currentFeedbackRow.progress < 100) {
            this.currentFeedbackRow.progress = Math.min(
              this.currentFeedbackRow.progress + 10,
              100,
            );
          }

          // 关闭弹窗
          this.feedbackDialogVisible = false;
          this.resetFeedbackForm();

          // 提示成功
          this.$message.success("反馈成功");
        } else {
          return false;
        }
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 重置为第一页
      this.getTableData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getTableData();
    },
    getTableData() {
      // 实现分页逻辑
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.tableData = this.allTableData.slice(start, end);
    },
  },
  mounted() {
    // 初始化分页数据
    this.getTableData();
    // 计算表格高度
    this.calculateTableHeight();
    // 监听窗口大小变化
    window.addEventListener("resize", this.calculateTableHeight);
  },
  beforeDestroy() {
    // 移除事件监听
    window.removeEventListener("resize", this.calculateTableHeight);
  },
  methods: {
    // 计算表格高度
    calculateTableHeight() {
      // 获取页面高度
      const windowHeight = window.innerHeight;
      // 计算表格高度（减去其他元素的高度）
      this.tableHeight = windowHeight - 430; // 可以根据实际情况调整
    },
    addWarning() {
      this.dialogVisible = true;
    },
    resetForm() {
      this.formInline = {
        scopeType: "",
        city: "",
        dateRange: [],
        status: "",
      };
    },
    resetDialogForm() {
      this.dialogForm = {
        title: "",
        scope: [],
      };
      if (this.$refs.dialogForm) {
        this.$refs.dialogForm.resetFields();
      }
    },
    submitDialogForm() {
      this.$refs.dialogForm.validate((valid) => {
        if (valid) {
          // 生成新的预警编号
          const newCode = `YW-${String(
            this.allTableData.length + 1000,
          ).padStart(4, "0")}`;

          // 处理预警范围
          let scope = [];
          if (this.dialogForm.scope.length === 1) {
            // 选择了全省
            scope = ["全省"];
          } else if (this.dialogForm.scope.length === 2) {
            // 选择了市
            const cityOption = this.cascaderOptions[0].children.find(
              (city) => city.value === this.dialogForm.scope[1],
            );
            if (cityOption) {
              scope = [cityOption.label];
            }
          } else if (this.dialogForm.scope.length === 3) {
            // 选择了区县
            const cityOption = this.cascaderOptions[0].children.find(
              (city) => city.value === this.dialogForm.scope[1],
            );
            if (cityOption) {
              const districtOption = cityOption.children.find(
                (district) => district.value === this.dialogForm.scope[2],
              );
              if (districtOption) {
                scope = [districtOption.label];
              }
            }
          }

          // 新增预警数据
          const newWarning = {
            code: newCode,
            title: this.dialogForm.title,
            scope: scope,
            targets: [],
            status: "reviewing",
            createTime: new Date().toISOString().split("T")[0],
            progress: 0,
          };

          // 添加到完整数据
          this.allTableData.unshift(newWarning);
          this.total++;
          // 重新获取分页数据
          this.getTableData();

          // 关闭弹窗
          this.dialogVisible = false;
          this.resetDialogForm();

          // 提示成功
          this.$message.success("预警创建成功");
        } else {
          return false;
        }
      });
    },
    getStatusType(status) {
      switch (status) {
        case "draft":
          return "info";
        case "reviewing":
          return "warning";
        case "published":
          return "success";
        case "archived":
          return "danger";
        default:
          return "";
      }
    },
    getStatusText(status) {
      switch (status) {
        case "draft":
          return "草稿";
        case "reviewing":
          return "审核中";
        case "published":
          return "已发布";
        case "archived":
          return "已归档";
        default:
          return "";
      }
    },
    formatProgress(percentage) {
      return `${percentage}%`;
    },
    viewDetail(row) {
      this.currentDetailRow = row;
      this.detailDrawerVisible = true;
    },
    getStepIndex(status) {
      switch (status) {
        case "draft":
          return 0;
        case "reviewing":
          return 1;
        case "published":
          return 2;
        case "archived":
          return 3;
        default:
          return 0;
      }
    },
    reviewWarning(row) {
      this.currentReviewRow = row;
      this.resetReviewForm();
      this.reviewDialogVisible = true;
    },
    resetReviewForm() {
      this.reviewForm = {
        result: "pass",
        targets: [],
        riskCharacteristics: "",
        activityForm: "",
        preventionSuggestions: "",
      };
      if (this.$refs.reviewForm) {
        this.$refs.reviewForm.resetFields();
      }
    },
    submitReviewForm() {
      // 验证表单
      const requiredFields = ["result"];
      if (this.reviewForm.result === "pass") {
        requiredFields.push(
          "targets",
          "riskCharacteristics",
          "activityForm",
          "preventionSuggestions",
        );
      }

      let isValid = true;
      for (const field of requiredFields) {
        if (
          !this.reviewForm[field] ||
          (Array.isArray(this.reviewForm[field]) &&
            this.reviewForm[field].length === 0)
        ) {
          isValid = false;
          break;
        }
      }

      if (!isValid) {
        this.$message.error("请填写必填项");
        return;
      }

      // 处理审核结果
      if (this.reviewForm.result === "reject") {
        // 审核驳回，状态变为草稿
        this.currentReviewRow.status = "draft";
        this.$message.success("审核驳回，退回草稿");
      } else if (this.reviewForm.result === "pass") {
        // 审核通过，状态变为已发布
        this.currentReviewRow.status = "published";
        this.currentReviewRow.targets = this.reviewForm.targets;
        // 可以在这里保存其他审核信息
        this.$message.success("审核通过");
      }

      // 关闭弹窗
      this.reviewDialogVisible = false;
      this.resetReviewForm();
    },
    feedbackWarning(row) {
      this.currentFeedbackRow = row;
      this.resetFeedbackForm();
      this.feedbackDialogVisible = true;
    },
    resetFeedbackForm() {
      this.feedbackForm = {
        organization: "",
        content: "",
        attachments: [],
      };
      if (this.$refs.feedbackForm) {
        this.$refs.feedbackForm.resetFields();
      }
    },
    handleFileChange(file, fileList) {
      this.feedbackForm.attachments = fileList;
    },
    submitFeedbackForm() {
      this.$refs.feedbackForm.validate((valid) => {
        if (valid) {
          // 反馈成功，增加进度10%
          if (this.currentFeedbackRow.progress < 100) {
            this.currentFeedbackRow.progress = Math.min(
              this.currentFeedbackRow.progress + 10,
              100,
            );
          }

          // 关闭弹窗
          this.feedbackDialogVisible = false;
          this.resetFeedbackForm();

          // 提示成功
          this.$message.success("反馈成功");
        } else {
          return false;
        }
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1; // 重置为第一页
      this.getTableData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.getTableData();
    },
    getTableData() {
      // 实现分页逻辑
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      this.tableData = this.allTableData.slice(start, end);
    },
  },
};
</script>

<style lang="scss" scoped>
.risk-warning-box {
  width: 100%;
  height: 100%;
  padding: 16px;
  background: #f0f2f5;
}

.header-box {
  background: #ffffff;
  padding: 16px;
  border-radius: 8px;
  margin-bottom: 16px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;

  .filter-section {
    display: flex;
    align-items: center;

    .select-item {
      width: 150px;
      margin-left: 12px;
    }

    .date-picker-item {
      width: 280px;
      margin-left: 12px;
    }
  }
}

.statistics-box {
  display: flex;
  justify-content: space-between;
  margin-bottom: 16px;

  .statistic-card {
    flex: 1;
    height: 80px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-right: 12px;

    &:last-child {
      margin-right: 0;
    }

    .card-content {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
      padding: 0 20px;

      .card-img {
        width: 60px;
        height: 60px;
        margin-right: 16px;
      }

      .card-info {
        flex: 1;
        text-align: left;

        .card-title {
          font-size: 14px;
          color: #606266;
          margin-bottom: 4px;
        }

        .card-value {
          font-size: 24px;
          font-weight: bold;
          color: #409eff;
        }
      }
    }
  }
}

.table-box {
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 16px;

  .pagination-box {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}

// 详情抽屉样式
.detail-content {
  padding: 20px;

  .detail-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;

    .detail-title {
      font-size: 18px;
      font-weight: bold;
      margin: 0;
    }
  }

  .detail-steps {
    margin-bottom: 40px;
  }

  .detail-section {
    background: #f9f9f9;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 20px;

    .section-title {
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 16px;
      color: #333;
    }

    .info-item {
      margin-bottom: 12px;
      display: flex;
      align-items: flex-start;

      .info-label {
        width: 100px;
        font-weight: 500;
        color: #666;
      }

      .info-value {
        flex: 1;
        color: #333;
        line-height: 1.5;
      }
    }
  }

  .progress-section {
    margin-bottom: 20px;
  }

  .feedback-records {
    border-top: 1px solid #e8e8e8;
    padding-top: 16px;

    .record-item {
      padding: 16px;
      background: #ffffff;
      border-radius: 6px;
      margin-bottom: 12px;
      border-left: 4px solid #409eff;

      .record-header {
        display: flex;
        justify-content: space-between;
        margin-bottom: 8px;

        .record-organization {
          font-weight: 500;
          color: #333;
        }

        .record-time {
          font-size: 12px;
          color: #999;
        }
      }

      .record-content {
        margin-bottom: 8px;
        line-height: 1.5;
        color: #666;
      }

      .record-attachment {
        font-size: 14px;
      }
    }
  }
}

// 自定义进度条样式
::v-deep .el-progress {
  margin-top: 8px;
}

// 自定义标签样式
::v-deep .el-tag--success {
  background-color: #f0f9eb;
  border-color: #b7eb8f;
  color: #52c41a;
}

::v-deep .el-tag--info {
  background-color: #f4f4f5;
  border-color: #d9d9d9;
  color: #909399;
}
</style>
