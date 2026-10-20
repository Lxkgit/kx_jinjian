<!-- 案件协同管理 - 指导工作台账  -->
<template>
  <div class="guidance-page">
    <!-- ==================== 统计卡片 ==================== -->
    <div class="statistics">
      <div class="stat-card">
        <div class="stat-title">待签收</div>
        <div class="stat-value">
          <i class="el-icon-s-promotion"></i>
          {{ statistics.pending }}
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-title">已超期</div>
        <div class="stat-value warning">
          <i class="el-icon-time"></i>
          {{ statistics.overdue }}
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-title">待复核</div>
        <div class="stat-value">
          <i class="el-icon-refresh"></i>
          {{ statistics.review }}
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-title">已销项</div>
        <div class="stat-value">
          <i class="el-icon-circle-check"></i>
          {{ statistics.review }}
        </div>
      </div>
    </div>

    <!-- ==================== 搜索区域 ==================== -->

    <!-- ==================== 表格 ==================== -->
    <div class="search-panel">
        <el-input
          v-model="query.keyword"
          placeholder="台账编号/主题/来源单号/接收网点"
          clearable
          class="keyword-input"
          @keyup.enter.native="handleSearch"
        />

        <el-select v-model="query.type" placeholder="指导类型" clearable>
          <el-option
            v-for="item in typeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>

        <el-select v-model="query.status" placeholder="状态" clearable>
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>

        <el-select v-model="query.street" placeholder="所属街道" clearable>
          <el-option
            v-for="item in streetOptions"
            :key="item"
            :label="item"
            :value="item"
          />
        </el-select>

        <el-button type="primary" icon="el-icon-search" @click="handleSearch">
          查询
        </el-button>

        <el-button @click="handleReset"> 重置 </el-button>
    </div>

    <div class="table-wrapper">
      <el-table
        :data="filteredData"
        stripe
        height="calc(100vh - 360px)"
        class="ledger-table warning-table"
        @row-click="handleRowClick"
      >
        <!-- 台账编号 -->
        <el-table-column prop="id" label="台账编号" width="150" fixed="left">
          <template slot-scope="{ row }">
            <span class="link-text">
              {{ row.id }}
            </span>
          </template>
        </el-table-column>

        <!-- 指导主题 -->
        <el-table-column
          prop="subject"
          label="指导主题"
          min-width="240"
          show-overflow-tooltip
        />

        <!-- 来源事项 -->
        <el-table-column label="来源事项" width="155">
          <template slot-scope="{ row }">
            <div class="source-cell">
              <div>{{ row.sourceType }}</div>
              <span>{{ row.sourceNo }}</span>
            </div>
          </template>
        </el-table-column>

        <!-- 指导类型 -->
        <el-table-column prop="type" label="指导类型" width="120">
          <template slot-scope="{ row }">
            <span class="type-tag">
              {{ row.type }}
            </span>
          </template>
        </el-table-column>

        <!-- 下发单位 -->
        <el-table-column
          prop="issueUnit"
          label="下发单位"
          width="155"
          show-overflow-tooltip
        />

        <!-- 接收网点 -->
        <el-table-column
          prop="receiveUnit"
          label="接收网点"
          width="200"
          show-overflow-tooltip
        />

        <!-- 所属街道 -->
        <el-table-column prop="street" label="所属街道" width="120" />

        <!-- 责任人 -->
        <el-table-column prop="person" label="责任人" width="90" />

        <!-- 反馈截止 -->
        <el-table-column label="反馈截止" width="125">
          <template slot-scope="{ row }">
            <span :class="{ 'deadline-danger': row.status === '已超期' }">
              {{ row.deadline }}
            </span>
          </template>
        </el-table-column>

        <!-- 状态 -->
        <el-table-column label="状态" width="100">
          <template slot-scope="{ row }">
            <span class="status-tag" :class="statusClass(row.status)">
              {{ row.status }}
            </span>
          </template>
        </el-table-column>

        <!-- 反馈进度 -->
        <el-table-column label="反馈进度" width="150">
          <template slot-scope="{ row }">
            <div class="progress-cell">
              <el-progress
                :percentage="row.progress"
                :show-text="false"
                :stroke-width="6"
              />
              <span>{{ row.progress }}%</span>
            </div>
          </template>
        </el-table-column>

        <!-- 操作 -->
        <el-table-column label="操作" width="130" fixed="right">
          <template slot-scope="{ row }">
            <el-button type="text" @click.stop="openDetail(row)">
              详情
            </el-button>

            <el-button
              v-if="canFeedback(row)"
              type="text"
              @click.stop="openFeedback(row)"
            >
              反馈
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[10, 20, 50]"
          :current-page="1"
          :page-size="10"
          :total="filteredData.length"
        />
      </div>
    </div>

    <!-- ========================================================= -->
    <!-- ======================== 详情 Drawer ==================== -->
    <!-- ========================================================= -->

    <el-drawer
      title="指导工作台账详情"
      :visible.sync="detailVisible"
      direction="rtl"
      size="620px"
      :with-header="true"
      custom-class="guidance-detail-drawer"
    >
      <div v-if="currentRow" class="detail-content">
        <!-- 基本信息 -->
        <div class="detail-section">
          <div class="section-title">基本信息</div>

          <div class="info-grid">
            <div class="info-item">
              <label>台账编号</label>
              <span>{{ currentRow.id }}</span>
            </div>

            <div class="info-item">
              <label>当前状态</label>
              <span
                class="status-tag"
                :class="statusClass(currentRow.status)"
                style="flex: 0; margin-left: 10px; padding: 6px"
              >
                {{ currentRow.status }}
              </span>
            </div>

            <div class="info-item full">
              <label>指导主题</label>
              <span>{{ currentRow.subject }}</span>
            </div>

            <div class="info-item">
              <label>下发单位</label>
              <span>{{ currentRow.issueUnit }}</span>
            </div>

            <div class="info-item">
              <label>接收网点</label>
              <span>
                {{ currentRow.receiveUnit }}
              </span>
            </div>

            <div class="info-item">
              <label>所属区域</label>
              <span>{{ currentRow.street }}</span>
            </div>

            <div class="info-item">
              <label>责任人</label>
              <span>{{ currentRow.person }}</span>
            </div>

            <div class="info-item full">
              <label>下发时间</label>
              <span>2026-08-05</span>
            </div>

            <div class="info-item full">
              <label>反馈截止</label>
              <span>2026-08-12</span>
            </div>
          </div>
        </div>

        <!-- 来源事项 -->
        <div class="detail-section">
          <div class="section-title">来源事项</div>

          <div class="source-detail">
            <div>
              <label>来源类型：</label>
              {{ currentRow.sourceType }}
            </div>

            <div>
              <label>来源单号：</label>
              {{ currentRow.sourceNo }}
            </div>

            <div>
              <label>来源标题：</label>
              {{ currentRow.sourceTitle }}
            </div>

            <div>
              <label>上报网点：</label>
              {{ currentRow.reportUnit }}
            </div>

            <div>
              <label>上报时间：</label>
              {{ currentRow.reportTime }}
            </div>

            <div class="source-content">
              <label>上报内容：</label>
              {{ currentRow.sourceContent }}
            </div>
          </div>
        </div>

        <!-- 整改要求 -->
        <div class="detail-two-column">
          <div class="detail-section">
            <div class="section-title">整改要求</div>

            <div class="requirement-content">
              <div>
                <strong>整改目标</strong>
                <p>
                  {{ currentRow.rectifyTarget }}
                </p>
              </div>

              <div>
                <strong>整改要求</strong>
                <p>
                  {{ currentRow.rectifyRequirement }}
                </p>
              </div>
            </div>
          </div>

          <div class="detail-section">
            <div class="section-title">报送与复核要求</div>

            <div class="requirement-content">
              <div>
                <strong>反馈材料</strong>

                <p>
                  {{ currentRow.feedbackMaterial }}
                </p>
              </div>

              <div>
                <strong>复核重点</strong>

                <p>
                  {{ currentRow.reviewPoint }}
                </p>
              </div>
            </div>
          </div>
        </div>

        <!-- 网点反馈 -->
        <div class="detail-section">
          <div class="section-title">
            网点反馈
            <span v-if="currentRow.latestFeedback" class="feedback-type">
              {{ currentRow.latestFeedback.type }}
            </span>
          </div>

          <div v-if="currentRow.latestFeedback" class="feedback-detail">
            <!-- 整改反馈 -->
            <template v-if="currentRow.latestFeedback.type === '整改反馈'">
              <div class="feedback-row">
                <label>整改情况：</label>
                <span>
                  {{ currentRow.latestFeedback.content }}
                </span>
              </div>

              <div class="feedback-row">
                <label>整改结果：</label>
                <span>
                  {{ currentRow.latestFeedback.result }}
                </span>
              </div>

              <div class="feedback-row">
                <label>整改时间：</label>
                <span>
                  {{ currentRow.latestFeedback.time }}
                </span>
              </div>
            </template>

            <!-- 材料补充 -->
            <template v-else-if="currentRow.latestFeedback.type === '材料补充'">
              <div class="feedback-row">
                <label>补充说明：</label>
                <span>
                  {{ currentRow.latestFeedback.content }}
                </span>
              </div>

              <div class="feedback-row">
                <label>补充材料：</label>
                <span>
                  {{ currentRow.latestFeedback.material }}
                </span>
              </div>
            </template>

            <!-- 销项申请 -->
            <template v-else>
              <div class="feedback-row">
                <label>销项说明：</label>
                <span>
                  {{ currentRow.latestFeedback.content }}
                </span>
              </div>

              <div class="feedback-row">
                <label>申请结果：</label>
                <span>
                  {{ currentRow.latestFeedback.result }}
                </span>
              </div>
            </template>
          </div>

          <div v-else class="empty-feedback">暂无网点反馈</div>
        </div>

        <!-- 附件 -->
        <div class="detail-section">
          <div class="section-title">附件材料</div>

          <div class="attachment-list">
            <div
              v-for="(file, index) in currentRow.attachments"
              :key="index"
              class="attachment-item"
            >
              <i class="el-icon-document"></i>
              {{ file }}
            </div>
          </div>
        </div>

        <!-- 过程留痕 -->
        <div class="detail-section">
          <div class="section-title">过程留痕</div>

          <el-timeline>
            <el-timeline-item
              v-for="(item, index) in currentRow.records"
              :key="index"
              :timestamp="item.time"
              placement="top"
            >
              <div class="timeline-title">
                {{ item.title }}
              </div>

              <div class="timeline-content">
                {{ item.content }}
              </div>

              <div class="timeline-user">
                {{ item.user }}
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>
    </el-drawer>

    <el-dialog
      title="提交指导反馈"
      :visible.sync="feedbackDialogVisible"
      width="620px"
      :close-on-click-modal="false"
      @close="handleFeedbackClose"
    >
      <el-form
        ref="feedbackForm"
        :model="feedbackForm"
        :rules="feedbackRules"
        label-width="140px"
      >
        <!-- 反馈类型 -->
        <el-form-item label="反馈类型" prop="feedbackType">
          <el-select
            v-model="feedbackForm.feedbackType"
            placeholder="请选择反馈类型"
            style="width: 100%"
            @change="handleFeedbackTypeChange"
          >
            <el-option
              v-for="item in feedbackTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>

        <!-- 整体反馈 -->
        <template v-if="feedbackForm.feedbackType === 'overall'">
          <el-form-item label="任务落实情况" prop="taskImplementation">
            <el-input
              v-model="feedbackForm.taskImplementation"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入责任网点当前落实指导任务的安排和执行情况"
            />
          </el-form-item>

          <el-form-item label="核查及整改进展" prop="inspectionProgress">
            <el-input
              v-model="feedbackForm.inspectionProgress"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入现场核查、客户排查、宣传清理等整改进展"
            />
          </el-form-item>

          <el-form-item label="已采取整改动作" prop="rectificationActions">
            <el-input
              v-model="feedbackForm.rectificationActions"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入已完成的整改动作和处置措施"
            />
          </el-form-item>

          <el-form-item label="待协调问题" prop="coordinationProblems">
            <el-input
              v-model="feedbackForm.coordinationProblems"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入当前仍需街道、区处非办或其他部门协调的问题"
            />
          </el-form-item>

          <el-form-item label="下一步安排" prop="nextArrangement">
            <el-input
              v-model="feedbackForm.nextArrangement"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入下一步整改计划和预计完成时间"
            />
          </el-form-item>
        </template>

        <!-- 材料补充 -->
        <template v-if="feedbackForm.feedbackType === 'material'">
          <el-form-item label="补充说明" prop="supplementDescription">
            <el-input
              v-model="feedbackForm.supplementDescription"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入本次补充材料对应的背景说明"
            />
          </el-form-item>

          <el-form-item label="本次补充材料" prop="supplementMaterial">
            <el-input
              v-model="feedbackForm.supplementMaterial"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入本次已补充上传的材料名称和主要内容"
            />
          </el-form-item>

          <el-form-item label="材料对应事项" prop="materialRelatedItem">
            <el-input
              v-model="feedbackForm.materialRelatedItem"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入这些材料对应的整改要求、来源事项或复核节点"
            />
          </el-form-item>

          <el-form-item label="仍缺失材料/原因" prop="missingMaterialReason">
            <el-input
              v-model="feedbackForm.missingMaterialReason"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入尚未提交的材料及未提交原因"
            />
          </el-form-item>

          <el-form-item label="提交说明" prop="submitDescription">
            <el-input
              v-model="feedbackForm.submitDescription"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入材料提交后的说明，是否满足复核或仍需继续补件"
            />
          </el-form-item>
        </template>

        <!-- 销项申请 -->
        <template v-if="feedbackForm.feedbackType === 'cancel'">
          <el-form-item label="销项依据" prop="cancelBasis">
            <el-input
              v-model="feedbackForm.cancelBasis"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请填写销项依据"
            />
          </el-form-item>

          <el-form-item label="整改完成情况" prop="rectificationCompletion">
            <el-input
              v-model="feedbackForm.rectificationCompletion"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入申请销项的依据和判定理由"
            />
          </el-form-item>

          <el-form-item label="已归档材料" prop="archivedMaterials">
            <el-input
              v-model="feedbackForm.archivedMaterials"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入已归档的审批表、照片、台账等材料"
            />
          </el-form-item>

          <el-form-item label="后续管控安排" prop="followUpManagement">
            <el-input
              v-model="feedbackForm.followUpManagement"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入销项后常态巡检、回访或宣传安排"
            />
          </el-form-item>

          <el-form-item label="申请事项" prop="applicationItem">
            <el-input
              v-model="feedbackForm.applicationItem"
              type="textarea"
              :rows="3"
              maxlength="500"
              show-word-limit
              placeholder="请输入本次申请进入复核或正式销项的事项"
            />
          </el-form-item>
        </template>
      </el-form>

      <div slot="footer">
        <el-button @click="feedbackDialogVisible = false"> 取消 </el-button>

        <el-button
          type="primary"
          :loading="feedbackSubmitting"
          @click="submitFeedback"
        >
          提交
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { collaborationStreetOptions, extraWorkRecords } from '../caseCollaborationMockData'

export default {
  name: "GuidanceLedger",

  data() {
    return {
      // ==================== 搜索 ====================
      query: {
        keyword: "",
        type: "",
        status: "",
        street: "",
      },

      typeOptions: [
        {
          label: "线索指导",
          value: "线索指导",
        },
        {
          label: "整改督办",
          value: "整改督办",
        },
        {
          label: "回头看复核",
          value: "回头看复核",
        },
        {
          label: "销项复核",
          value: "销项复核",
        },
      ],

      statusOptions: [
        {
          label: "待签收",
          value: "待签收",
        },
        {
          label: "整改中",
          value: "整改中",
        },
        {
          label: "待复核",
          value: "待复核",
        },
        {
          label: "已销项",
          value: "已销项",
        },
        {
          label: "已超期",
          value: "已超期",
        },
      ],

      streetOptions: [...collaborationStreetOptions, "景甜街道", "梧田街道", "茶山街道", "新桥街道", "娄桥街道", "仙岩街道"],

      // ==================== 统计 ====================
      statistics: {
        pending: 0,
        overdue: 0,
        review: 0,
      },

      // ==================== 列表 ====================
      tableData: [
        {
          id: "ZD-202608-001",

          subject: "关于龙霞支行直播引流涉非线索的核查整改指导单",

          sourceType: "风险线索",
          sourceNo: "XS-202608-014",

          sourceTitle: "龙霞社区直播引流涉非线索",

          reportUnit: "中国建设银行股份有限公司温州龙霞支行",

          reportTime: "2026-08-04 16:20",

          sourceContent:
            "网点上报发现直播间存在疑似涉非宣传内容，需及时进行核查并整改。",

          type: "线索指导",

          issueUnit: "温州市金融监管分局",

          receiveUnit: "中国建设银行股份有限公司温州龙霞支行",

          street: "梧田街道",

          person: "陈海峰",

          deadline: "2026-08-12",

          status: "整改中",

          progress: 70,

          rectifyTarget:
            "核实直播引流涉非活动真实性，排清宣传主体、重点客户群和账户风险。",

          rectifyRequirement:
            "对来源线索涉及的销售点位开展现场核查，固定宣传语、材料和监测情况，并对涉事主体逐项核查。",

          feedbackMaterial:
            "现场核查记录、账户排摸表、相关客户核查明细、联动反馈表。",

          reviewPoint:
            "线索真实性是否核清，网点排摸范围是否完整，客群解释动作是否落地。",

          attachments: [
            "指导单.pdf",
            "线索原始截图.zip",
            "责任网点反馈模板.docx",
          ],

          latestFeedback: {
            type: "整改反馈",
            content: "已完成相关客户排摸及现场核查，目前未发现新的风险情况。",
            result: "已完成整改",
            time: "2026-08-10 15:30",
          },

          records: [
            {
              time: "2026-08-05 09:00",
              title: "指导下发",
              content: "区金融监管分局向责任网点下发指导任务。",
              user: "张海峰",
            },
            {
              time: "2026-08-06 14:20",
              title: "网点签收",
              content: "责任网点已签收指导任务并开始整改。",
              user: "陈海峰",
            },
            {
              time: "2026-08-10 15:30",
              title: "提交整改反馈",
              content: "网点提交阶段性整改反馈。",
              user: "陈海峰",
            },
          ],
        },

        {
          id: "ZD-202608-002",

          subject: "关于新桥支行养老项目上报事项的整改督办单",

          sourceType: "扫楼上报",
          sourceNo: "SL-202608-021",

          sourceTitle: "新桥街道养老项目风险排查",

          reportUnit: "中国银行温州新桥支行",

          reportTime: "2026-08-03 10:20",

          sourceContent: "现场排查发现疑似违规宣传，需要责任网点进一步核查。",

          type: "整改督办",

          issueUnit: "瓯海区处非办",

          receiveUnit: "中国银行股份有限公司温州新桥支行",

          street: "新桥街道",

          person: "王璐",

          deadline: "2026-08-09",

          status: "已超期",

          progress: 45,

          rectifyTarget: "完成相关项目真实性核查并形成整改闭环。",

          rectifyRequirement:
            "对涉及主体、客户、宣传内容逐项核查，并留存相关证据材料。",

          feedbackMaterial: "现场照片、客户核查记录、整改说明。",

          reviewPoint: "整改措施是否真实有效，材料是否完整。",

          attachments: ["问题线索.docx"],

          latestFeedback: null,

          records: [
            {
              time: "2026-08-03 14:00",
              title: "指导下发",
              content: "区处非办下发整改任务。",
              user: "监管人员",
            },
          ],
        },

        {
          id: "ZD-202607-015",

          subject: "关于娄桥支行助贷中介有关事项的复核指导单",

          sourceType: "扫楼上报",
          sourceNo: "SL-202607-098",

          sourceTitle: "助贷中介风险排查",

          reportUnit: "中国银行温州娄桥支行",

          reportTime: "2026-07-28 09:30",

          sourceContent: "发现疑似违规助贷中介活动。",

          type: "回头看复核",

          issueUnit: "瓯海区金融办",

          receiveUnit: "中国银行温州娄桥支行",

          street: "娄桥街道",

          person: "李慧",

          deadline: "2026-08-15",

          status: "待复核",

          progress: 100,

          rectifyTarget: "对前期整改事项开展回头看。",

          rectifyRequirement: "重点检查前期问题是否再次发生。",

          feedbackMaterial: "回头看检查表、现场照片。",

          reviewPoint: "前期问题是否整改到位。",

          attachments: [],

          latestFeedback: {
            type: "销项申请",
            content: "前期整改事项已全部落实，现申请销项。",
            result: "已完成整改",
            time: "2026-08-11 10:20",
          },

          records: [
            {
              time: "2026-08-01 09:00",
              title: "指导下发",
              content: "下发回头看复核任务。",
              user: "监管人员",
            },
            {
              time: "2026-08-11 10:20",
              title: "提交销项申请",
              content: "责任网点申请销项，当前等待复核。",
              user: "李慧",
            },
          ],
        },

        {
          id: "ZD-202607-011",

          subject: "关于仙岩街道违规借贷广告事项的销项复核单",

          sourceType: "风险线索",
          sourceNo: "XS-202607-031",

          sourceTitle: "仙岩街道违规借贷广告",

          reportUnit: "中国农业银行温州仙岩支行",

          reportTime: "2026-07-26 13:40",

          sourceContent: "发现疑似违规借贷广告宣传。",

          type: "销项复核",

          issueUnit: "瓯海区处非办",

          receiveUnit: "中国农业银行温州仙岩支行",

          street: "仙岩街道",

          person: "周建",

          deadline: "2026-08-08",

          status: "已销项",

          progress: 100,

          rectifyTarget: "完成违规借贷广告清理。",

          rectifyRequirement: "全面排查相关宣传点位并完成整改。",

          feedbackMaterial: "现场照片、整改报告。",

          reviewPoint: "广告是否全部清理。",

          attachments: ["整改前后照片.zip"],

          latestFeedback: {
            type: "整改反馈",
            content: "已完成全部违规广告清理。",
            result: "已完成整改",
            time: "2026-08-06 16:20",
          },

          records: [
            {
              time: "2026-07-28 09:00",
              title: "指导下发",
              content: "下发整改指导。",
              user: "监管人员",
            },
            {
              time: "2026-08-06 16:20",
              title: "整改反馈",
              content: "网点反馈已完成整改。",
              user: "周建",
            },
            {
              time: "2026-08-07 09:30",
              title: "复核通过",
              content: "经复核符合销项条件。",
              user: "监管人员",
            },
          ],
        },
        ...extraWorkRecords,
      ],

      // ==================== 详情 ====================
      detailVisible: false,
      currentRow: null,

      // ==================== 反馈 ====================
      feedbackVisible: false,
      feedbackDialogVisible: false,
      feedbackSubmitting: false,
      feedbackTypeOptions: [
        {
          label: "整改反馈",
          value: "overall",
        },
        {
          label: "材料补充",
          value: "material",
        },
        {
          label: "销项申请",
          value: "cancel",
        },
      ],

      currentFeedbackRow: null,

      feedbackForm: {
        feedbackType: "",

        // 整体反馈
        taskImplementation: "",
        inspectionProgress: "",
        rectificationActions: "",
        coordinationProblems: "",
        nextArrangement: "",

        // 材料补充
        supplementDescription: "",
        supplementMaterial: "",
        materialRelatedItem: "",
        missingMaterialReason: "",
        submitDescription: "",

        // 销项申请
        cancelBasis: "",
        rectificationCompletion: "",
        archivedMaterials: "",
        followUpManagement: "",
        applicationItem: "",
      },
      feedbackRules: {
        feedbackType: [
          {
            required: true,
            message: "请选择反馈类型",
            trigger: "change",
          },
        ],

        taskImplementation: [
          {
            required: true,
            message: "请输入责任网点当前落实指导任务的安排和执行情况",
            trigger: "blur",
          },
        ],
        inspectionProgress: [
          {
            required: true,
            message: "请输入现场核查、客户排查、宣传清理等整改进展",
            trigger: "blur",
          },
        ],
        rectificationActions: [
          {
            required: true,
            message: "请输入已完成的整改动作和处置措施",
            trigger: "blur",
          },
        ],
        coordinationProblems: [
          {
            required: true,
            message: "请输入当前仍需街道、区处非办或其他部门协调的问题",
            trigger: "blur",
          },
        ],
        nextArrangement: [
          {
            required: true,
            message: "请输入下一步整改计划和预计完成时间",
            trigger: "blur",
          },
        ],

        supplementDescription: [
          {
            required: true,
            message: "请输入本次补充材料对应的背景说明",
            trigger: "blur",
          },
        ],
        supplementMaterial: [
          {
            required: true,
            message: "请输入本次已补充上传的材料名称和主要内容",
            trigger: "blur",
          },
        ],
        materialRelatedItem: [
          {
            required: true,
            message: "请输入这些材料对应的整改要求、来源事项或复核节点",
            trigger: "blur",
          },
        ],
        missingMaterialReason: [
          {
            required: true,
            message: "请输入尚未提交的材料及未提交原因",
            trigger: "blur",
          },
        ],
        submitDescription: [
          {
            required: true,
            message: "请输入材料提交后的说明，是否满足复核或仍需继续补件",
            trigger: "blur",
          },
        ],

        cancelBasis: [
          {
            required: true,
            message: "请输入申请销项的依据和判定理由",
            trigger: "blur",
          },
        ],
        rectificationCompletion: [
          {
            required: true,
            message: "请输入问题整改完成情况和最终结论",
            trigger: "blur",
          },
        ],
        archivedMaterials: [
          {
            required: true,
            message: "请输入已归档的审批表、照片、台账等材料",
            trigger: "blur",
          },
        ],
        followUpManagement: [
          {
            required: true,
            message: "请输入销项后常态巡检、回访或宣传安排",
            trigger: "blur",
          },
        ],
        applicationItem: [
          {
            required: true,
            message: "请输入本次申请进入复核或正式销项的事项",
            trigger: "blur",
          },
        ],
      },
    };
  },

  computed: {
    /**
     * 搜索过滤
     */
    filteredData() {
      const { keyword, type, status, street } = this.query;

      return this.tableData.filter((item) => {
        if (keyword) {
          const text = [
            item.id,
            item.subject,
            item.sourceNo,
            item.receiveUnit,
          ].join("");

          if (!text.includes(keyword)) {
            return false;
          }
        }

        if (type && item.type !== type) {
          return false;
        }

        if (status && item.status !== status) {
          return false;
        }

        if (street && item.street !== street) {
          return false;
        }

        return true;
      });
    },
  },

  mounted() {
    this.updateStatistics();
  },

  methods: {
    // ============================================================
    // 搜索
    // ============================================================

    handleSearch() {
      this.updateStatistics();
    },

    handleReset() {
      this.query = {
        keyword: "",
        type: "",
        status: "",
        street: "",
      };

      this.updateStatistics();
    },

    updateStatistics() {
      const data = this.filteredData;

      this.statistics.pending = data.filter(
        (item) => item.status === "待签收",
      ).length;

      this.statistics.overdue = data.filter(
        (item) => item.status === "已超期",
      ).length;

      this.statistics.review = data.filter(
        (item) => item.status === "待复核",
      ).length;
    },

    // ============================================================
    // 表格
    // ============================================================

    handleRowClick(row) {
      this.openDetail(row);
    },

    statusClass(status) {
      const map = {
        待签收: "status-pending",
        整改中: "status-processing",
        待复核: "status-review",
        已销项: "status-finished",
        已超期: "status-overdue",
      };

      return map[status] || "";
    },

    /**
     * 只有已经接收并且尚未销项的任务允许反馈
     */
    canFeedback(row) {
      return ["整改中", "已超期", "待复核"].includes(row.status);
    },

    // ============================================================
    // 详情
    // ============================================================

    openDetail(row) {
      this.currentRow = row;
      this.detailVisible = true;
    },

    // ============================================================
    // 反馈
    // ============================================================

    openFeedback(row) {
      this.feedbackDialogVisible = true;

      this.resetFeedbackForm();

      // 这里只保存当前操作的是哪条台账
      this.currentFeedbackRow = row;

      this.$nextTick(() => {
        this.$refs.feedbackForm.clearValidate();
      });
    },
    resetFeedbackForm() {
      this.feedbackForm = {
        feedbackType: "",

        taskImplementation: "",
        inspectionProgress: "",
        rectificationActions: "",
        coordinationProblems: "",
        nextArrangement: "",

        supplementDescription: "",
        supplementMaterial: "",
        materialRelatedItem: "",
        missingMaterialReason: "",
        submitDescription: "",

        cancelBasis: "",
        rectificationCompletion: "",
        archivedMaterials: "",
        followUpManagement: "",
        applicationItem: "",
      };
    },
    handleFeedbackClose() {
      this.resetFeedbackForm();

      this.$nextTick(() => {
        if (this.$refs.feedbackForm) {
          this.$refs.feedbackForm.clearValidate();
        }
      });
    },

    handleFeedbackTypeChange() {
      const type = this.feedbackForm.feedbackType;

      this.feedbackForm.taskImplementation = "";
      this.feedbackForm.inspectionProgress = "";
      this.feedbackForm.rectificationActions = "";
      this.feedbackForm.coordinationProblems = "";
      this.feedbackForm.nextArrangement = "";

      this.feedbackForm.supplementDescription = "";
      this.feedbackForm.supplementMaterial = "";
      this.feedbackForm.materialRelatedItem = "";
      this.feedbackForm.missingMaterialReason = "";
      this.feedbackForm.submitDescription = "";

      this.feedbackForm.cancelBasis = "";
      this.feedbackForm.rectificationCompletion = "";
      this.feedbackForm.archivedMaterials = "";
      this.feedbackForm.followUpManagement = "";
      this.feedbackForm.applicationItem = "";

      this.$nextTick(() => {
        this.$refs.feedbackForm.clearValidate();
      });
    },

    handleFileChange(file, fileList) {
      this.feedbackForm.files = fileList;
    },

    submitFeedback() {
      this.$refs.feedbackForm.validate((valid) => {
        if (!valid) {
          return;
        }

        this.feedbackSubmitting = true;

        const type = this.feedbackForm.feedbackType;
        const row = this.currentFeedbackRow;

        // 模拟提交
        setTimeout(() => {
          // 1. 根据反馈类型更新台账状态
          if (type === "cancel") {
            // 销项申请 → 待复核
            row.status = "待复核";
            row.progress = 100;
          } else if (type === "overall") {
            // 整体反馈 → 进度推进
            row.progress = Math.min(row.progress + 20, 100);
            // 如果之前是"待签收"，改为"整改中"
            if (row.status === "待签收") {
              row.status = "整改中";
            }
          } else if (type === "material") {
            // 材料补充 → 进度小幅推进
            row.progress = Math.min(row.progress + 10, 100);
          }

          // 2. 更新 latestFeedback（详情抽屉展示用）
          const feedbackTime = this.formatNow();
          const feedbackTypeLabel = this.getFeedbackTypeName(type);
          const feedbackSummary = this.getFeedbackSummary();

          row.latestFeedback = {
            type: feedbackTypeLabel,
            content: feedbackSummary,
            result:
              type === "cancel"
                ? "待复核"
                : type === "overall"
                ? "整改进行中"
                : "材料已补充",
            time: feedbackTime,
          };

          // 3. 追加过程留痕记录（兼容详情抽屉的 timeline）
          if (!row.records) {
            row.records = [];
          }
          row.records.push({
            time: feedbackTime,
            title:
              type === "cancel"
                ? "提交销项申请"
                : type === "material"
                ? "提交材料补充"
                : "提交整改反馈",
            content: feedbackSummary,
            user: "当前登录用户",
          });

          // 4. 刷新统计
          this.updateStatistics();

          this.feedbackSubmitting = false;
          this.feedbackDialogVisible = false;

          this.$message.success(
            type === "cancel" ? "销项申请已提交，进入待复核" : "反馈提交成功",
          );
        }, 500);
      });
    },

    // ============================================================
    // 反馈辅助方法
    // ============================================================

    /**
     * 将反馈类型值转为中文名称
     */
    getFeedbackTypeName(type) {
      const map = {
        overall: "整改反馈",
        material: "材料补充",
        cancel: "销项申请",
      };
      return map[type] || "整改反馈";
    },

    /**
     * 根据当前表单内容生成反馈摘要
     */
    getFeedbackSummary() {
      const form = this.feedbackForm;
      const type = form.feedbackType;

      if (type === "overall") {
        return (
          form.taskImplementation ||
          form.inspectionProgress ||
          form.rectificationActions ||
          "已提交整体反馈"
        );
      }
      if (type === "material") {
        return (
          form.supplementDescription ||
          form.supplementMaterial ||
          "已提交材料补充"
        );
      }
      if (type === "cancel") {
        return (
          form.cancelBasis || form.rectificationCompletion || "已提交销项申请"
        );
      }
      return "已提交反馈";
    },

    formatNow() {
      const date = new Date();

      const pad = (n) => String(n).padStart(2, "0");

      return (
        date.getFullYear() +
        "-" +
        pad(date.getMonth() + 1) +
        "-" +
        pad(date.getDate()) +
        " " +
        pad(date.getHours()) +
        ":" +
        pad(date.getMinutes())
      );
    },
  },
};
</script>

<style lang="scss" scoped>
.guidance-page {
  // min-height: 100vh;
  padding: 0;
  background: #f5f6f8;
  color: #303133;
  box-sizing: border-box;
}

/* ============================================================
   统计卡片
   ============================================================ */

.statistics {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 16px;
  margin-bottom: 16px;
}

.stat-card {
  height: 72px;
  padding: 14px 18px;
  box-sizing: border-box;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #ebeef5;

  .stat-title {
    color: #909399;
    font-size: 13px;
    margin-bottom: 5px;
  }

  .stat-value {
    font-size: 22px;
    font-weight: 500;

    i {
      font-size: 20px;
      margin-right: 5px;
    }

    &.warning {
      color: #e6a23c;
    }
  }
}

/* ============================================================
   搜索
   ============================================================ */

.search-panel {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  .keyword-input {
    width: 245px;
  }

  .el-select {
    width: 140px;
  }
}

/* ============================================================
   表格
   ============================================================ */

.table-wrapper {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.table-header {
  height: 50px;
  padding: 0 18px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid #ebeef5;
  font-size: 15px;
  font-weight: 500;

  .total {
    color: #909399;
    font-size: 13px;
    font-weight: normal;
  }
}

.ledger-table {
  border: none;

  ::v-deep th {
    background: #fafafa;
    color: #303133;
    font-weight: 500;
  }

  ::v-deep td {
    font-size: 13px;
  }
}

.warning-table {
  ::v-deep th.el-table__cell {
    height: 54px;
    padding: 0;
    background: #fafafa;
    color: #303133;
    font-weight: 600;
  }

  ::v-deep td.el-table__cell {
    height: 64px;
    padding: 0;
    color: #303133;
  }

  ::v-deep .cell {
    line-height: 20px;
  }

  ::v-deep .el-table__fixed {
    box-shadow: 4px 0 8px rgba(0, 0, 0, 0.04);
  }

  ::v-deep .el-table__fixed-right {
    box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
  }
}

.link-text {
  color: #1677ff;
  cursor: pointer;
}

.source-cell {
  line-height: 20px;

  span {
    color: #909399;
    font-size: 12px;
  }
}

.type-tag {
  display: inline-block;
  padding: 2px 7px;
  color: #606266;
  background: #f4f4f5;
  border-radius: 3px;
  font-size: 12px;
}

.status-tag {
  display: inline-block;
  padding: 3px 8px;
  border-radius: 3px;
  font-size: 12px;
  white-space: nowrap;
}

.status-pending {
  color: #909399;
  background: #f4f4f5;
}

.status-processing {
  color: #409eff;
  background: #ecf5ff;
}

.status-review {
  color: #e6a23c;
  background: #fdf6ec;
}

.status-finished {
  color: #67c23a;
  background: #f0f9eb;
}

.status-overdue {
  color: #f56c6c;
  background: #fef0f0;
}

.deadline-danger {
  color: #f56c6c;
}

.progress-cell {
  display: flex;
  align-items: center;
  gap: 7px;

  .el-progress {
    flex: 1;
  }

  span {
    width: 34px;
    color: #909399;
    font-size: 12px;
  }
}

.pagination {
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 20px;

  > span {
    color: #909399;
    font-size: 13px;
  }
}

/* ============================================================
   Drawer
   ============================================================ */

.detail-content {
  padding: 0 16px 30px;
}

.detail-section {
  margin-bottom: 14px;
  padding: 14px;
  border: 1px solid #ebeef5;
  border-radius: 5px;
  background: #fff;
}

.section-title {
  position: relative;
  margin-bottom: 14px;
  padding-left: 10px;
  font-size: 14px;
  font-weight: 600;

  &::before {
    content: "";
    position: absolute;
    left: 0;
    top: 0px;
    width: 3px;
    height: 14px;
    border-radius: 2px;
    background: #409eff;
  }
}

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  border-top: 1px solid #ebeef5;
  border-left: 1px solid #ebeef5;
}

.info-item {
  min-height: 40px;
  display: flex;
  align-items: center;
  border-right: 1px solid #ebeef5;
  border-bottom: 1px solid #ebeef5;

  label {
    width: 82px;
    height: 100%;
    display: flex;
    align-items: center;
    padding: 0 8px;
    box-sizing: border-box;
    color: #909399;
    background: #fafafa;
    font-size: 12px;
  }

  span {
    flex: 1;
    padding: 0 10px;
    font-size: 12px;
  }

  &.full {
    grid-column: 1 / 3;
  }
}

.source-detail {
  color: #606266;
  font-size: 12px;
  line-height: 22px;

  label {
    color: #909399;
  }

  .source-content {
    margin-top: 5px;
  }
}

.detail-two-column {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  strong {
    font-weight: 500;
  }
}

.requirement-content {
  font-size: 12px;
  color: #606266;
  line-height: 20px;

  strong {
    display: block;
    margin-bottom: 5px;
    color: #303133;
    font-size: 12px;
  }

  p {
    margin: 0 0 14px;
  }
}

.feedback-type {
  float: right;
  color: #409eff;
  background: #ecf5ff;
  padding: 2px 7px;
  border-radius: 3px;
  font-size: 11px;
  font-weight: normal;
}

.feedback-row {
  display: flex;
  margin-bottom: 10px;
  color: #606266;
  font-size: 12px;
  line-height: 20px;

  label {
    width: 75px;
    flex-shrink: 0;
    color: #909399;
  }
}

.empty-feedback {
  padding: 20px 0;
  text-align: center;
  color: #c0c4cc;
  font-size: 12px;
}

.attachment-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.attachment-item {
  color: #409eff;
  font-size: 12px;
  cursor: pointer;

  i {
    margin-right: 5px;
  }
}

.timeline-title {
  color: #303133;
  font-size: 13px;
  margin-bottom: 5px;
}

.timeline-content {
  color: #606266;
  font-size: 12px;
  line-height: 20px;
}

.timeline-user {
  margin-top: 3px;
  color: #909399;
  font-size: 11px;
}

/* ============================================================
   反馈
   ============================================================ */

.feedback-form {
  padding: 0 4px;
}

.form-item {
  margin-bottom: 20px;

  > label {
    display: block;
    margin-bottom: 8px;
    color: #606266;
    font-size: 13px;
  }

  &.required {
    > label::before {
      content: "*";
      margin-right: 4px;
      color: #f56c6c;
    }
  }
}

/* ============================================================
   Element UI 微调
   ============================================================ */

::v-deep {
  .el-drawer__header {
    margin-bottom: 0;
    padding: 18px 20px;
    border-bottom: 1px solid #ebeef5;

    span {
      font-size: 15px;
      font-weight: 600;
    }
  }

  .el-drawer__body {
    background: #f7f8fa;
  }

  .el-dialog__header {
    border-bottom: 1px solid #ebeef5;
  }

  .el-dialog__footer {
    border-top: 1px solid #ebeef5;
  }

  .el-table__body tr {
    cursor: pointer;
  }

  .el-progress-bar__outer {
    background: #f0f2f5;
  }
}

@media screen and (max-width: 1200px) {
  .search-panel {
    flex-wrap: wrap;
  }

  .statistics {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>
