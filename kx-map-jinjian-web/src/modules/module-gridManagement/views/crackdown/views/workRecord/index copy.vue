<!-- 案件协同管理 - 指导工作台账  -->
<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <div class="search-item">
        <label>指导类型</label>
        <el-select
          v-model="searchForm.guidanceType"
          placeholder="全部"
          clearable
        >
          <el-option label="线索指导" value="线索指导" />
          <el-option label="整改督办" value="整改督办" />
          <el-option label="回头看复核" value="回头看复核" />
          <el-option label="销项复核" value="销项复核" />
        </el-select>
      </div>
      <div class="search-item">
        <label>状态</label>
        <el-select v-model="searchForm.status" placeholder="全部" clearable>
          <el-option label="待签收" value="待签收" />
          <el-option label="整改中" value="整改中" />
          <el-option label="待复核" value="待复核" />
          <el-option label="已销项" value="已销项" />
          <el-option label="已超期" value="已超期" />
        </el-select>
      </div>
      <div class="search-item">
        <label>所属街道</label>
        <el-select v-model="searchForm.street" placeholder="全部" clearable>
          <el-option label="景甜街道" value="景甜街道" />
          <el-option label="梧田街道" value="梧田街道" />
          <el-option label="茶山街道" value="茶山街道" />
          <el-option label="新桥街道" value="新桥街道" />
          <el-option label="娄桥街道" value="娄桥街道" />
          <el-option label="仙岩街道" value="仙岩街道" />
        </el-select>
      </div>
      <div class="search-item">
        <label>台账编号</label>
        <el-input
          v-model="searchForm.code"
          placeholder="编号"
          clearable
          style="width: 150px"
        />
      </div>
      <div class="search-item">
        <label>主题</label>
        <el-input
          v-model="searchForm.topic"
          placeholder="主题关键词"
          clearable
          style="width: 150px"
        />
      </div>
      <div class="action-buttons">
        <el-button type="primary" icon="el-icon-search" @click="handleSearch"
          >查询</el-button
        >
        <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
      </div>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper">
      <el-table
        :data="filteredList"
        border
        stripe
        style="width: 100%"
        row-key="id"
      >
        <el-table-column prop="code" label="台账编号" width="140" />
        <el-table-column
          prop="topic"
          label="指导主题"
          min-width="160"
          show-overflow-tooltip
        />
        <el-table-column prop="sourceType" label="来源事项" width="100" />
        <el-table-column prop="guidanceType" label="指导类型" width="100" />
        <el-table-column prop="issuer" label="下发单位" width="140" />
        <el-table-column
          prop="receiveNode"
          label="接收网点"
          width="160"
          show-overflow-tooltip
        />
        <el-table-column prop="street" label="所属街道" width="100" />
        <el-table-column prop="responsible" label="责任人" width="80" />
        <el-table-column prop="feedbackDeadline" label="反馈截止" width="110" />
        <el-table-column prop="status" label="状态" width="90">
          <template slot-scope="scope">
            <span :class="['status-tag', getStatusClass(scope.row.status)]">
              {{ scope.row.status }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="反馈进度" width="90">
          <template slot-scope="scope">
            <span v-if="scope.row.feedbackProgress" class="progress-text">{{
              scope.row.feedbackProgress
            }}</span>
            <span v-else class="progress-text">-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="130" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="openDetail(scope.row)"
              >详情</el-button
            >
            <el-button type="text" size="mini" @click="openFeedback(scope.row)"
              >反馈</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 (占位) -->
    <div
      style="
        margin-top: 16px;
        display: flex;
        justify-content: flex-end;
        font-size: 13px;
        color: #7a8a9e;
      "
    >
      共 {{ filteredList.length }} 条指导台账
    </div>

    <!-- 详情弹框 -->
    <el-dialog
      title="指导工作台账详情"
      :visible.sync="detailVisible"
      width="800px"
      top="5vh"
      :close-on-click-modal="false"
    >
      <div v-if="currentDetail" class="detail-container">
        <!-- 基本信息卡片 -->
        <div class="detail-header">
          <div class="detail-row">
            <span class="label">台账编号</span
            ><span class="value">{{ currentDetail.code }}</span>
            <span class="label" style="margin-left: 24px">当前状态</span>
            <span
              :class="['status-tag', getStatusClass(currentDetail.status)]"
              >{{ currentDetail.status }}</span
            >
          </div>
          <div class="detail-row">
            <span class="label">指导主题</span
            ><span class="value" style="flex: 1">{{
              currentDetail.topic
            }}</span>
          </div>
          <div class="detail-row">
            <span class="label">下发单位</span
            ><span class="value">{{ currentDetail.issuer }}</span>
            <span class="label" style="margin-left: 24px">接收网点</span
            ><span class="value">{{ currentDetail.receiveNode }}</span>
          </div>
          <div class="detail-row">
            <span class="label">所属区域</span
            ><span class="value"
              >浙江省 - 温州市 - 瓯海区 - {{ currentDetail.street }}</span
            >
            <span class="label" style="margin-left: 24px">责任人</span
            ><span class="value">{{ currentDetail.responsible }}</span>
          </div>
          <div class="detail-row">
            <span class="label">下发时间</span
            ><span class="value">{{
              currentDetail.issueTime || "2026-08-05 09:00"
            }}</span>
            <span class="label" style="margin-left: 24px">反馈截止</span
            ><span class="value">{{ currentDetail.feedbackDeadline }}</span>
          </div>
        </div>

        <!-- 来源事项 -->
        <div class="detail-section">
          <h4>📌 来源事项</h4>
          <div class="info-grid">
            <div>
              <span class="label">来源类型：</span
              >{{ currentDetail.sourceType || "风险线索" }}
            </div>
            <div>
              <span class="label">来源编号：</span
              >{{ currentDetail.sourceCode || "XS-202608-014" }}
            </div>
            <div>
              <span class="label">来源标题：</span
              >{{ currentDetail.sourceTitle || "龙霞社区直播引流涉非线索" }}
            </div>
            <div>
              <span class="label">上报网点：</span
              >{{ currentDetail.receiveNode }}
            </div>
            <div>
              <span class="label">上报时间：</span
              >{{ currentDetail.issueTime || "2026-08-04 16:20" }}
            </div>
          </div>
          <div class="info-text">
            <span class="label">上报内容：</span
            >{{
              currentDetail.sourceContent ||
              "龙霞支行上报老年客群集中到访社区活动点并存在“稳健收益”宣传话术，疑似通过直播引流开展会销宣传。"
            }}
          </div>
        </div>

        <!-- 整改要求 -->
        <div class="detail-section">
          <h4>📋 整改要求</h4>
          <div class="info-text">
            <span class="label">整改目标：</span
            >{{
              currentDetail.remedyTarget ||
              "核实直播引流会销活动真实性，排查宣传主体、重点客群和账户风险，形成网点、街道、社区联动处置意见。"
            }}
          </div>
          <div class="info-text">
            <span class="label">整改要求：</span
            >{{
              currentDetail.remedyRequirement ||
              "对来源线索涉及的会销点位开展现场核查，固定宣传话术、物料和签到情况。对涉事主体近30日重点账户交易开展排摸，说明异常客户情况。对已接触老年客群开展风险提示，并形成解释劝阻台账。"
            }}
          </div>
          <div class="info-text">
            <span class="label">复核重点：</span
            >{{
              currentDetail.reviewFocus ||
              "核查现场处置是否彻底，异常客户是否逐一回访，社区联合签章是否完整。"
            }}
          </div>
        </div>

        <!-- 网点反馈 (最近一次) -->
        <div class="detail-section">
          <h4>📝 网点反馈</h4>
          <div v-if="currentDetail.latestFeedback" class="feedback-box">
            <div>
              <span class="label">反馈类型：</span
              >{{ currentDetail.latestFeedback.type || "整改反馈" }}
            </div>
            <div>
              <span class="label">任务落实：</span
              >{{
                currentDetail.latestFeedback.task ||
                "龙霞支行已完成来源线索认领，安排2名客户经理和1名合规人员对龙霞社区直播会销点开展现场复核。"
              }}
            </div>
            <div>
              <span class="label">核查进展：</span
              >{{
                currentDetail.latestFeedback.progress ||
                "已完成重点楼宇复排、会销点现场核查和近30日重点账户流水排摸，识别异常客户9名，涉事主体已暂停宣传活动。"
              }}
            </div>
            <div>
              <span class="label">整改动作：</span
              >{{
                currentDetail.latestFeedback.action ||
                "已完成现场宣传物料封存、重点客户解释告知和涉事主体账户排摸，并同步形成首轮核查记录。"
              }}
            </div>
          </div>
          <div v-else style="color: #a0b0c0">暂无网点反馈记录</div>
        </div>

        <!-- 过程留痕 -->
        <div class="detail-section">
          <h4>⏳ 过程留痕</h4>
          <ul class="timeline">
            <li
              v-for="(item, idx) in currentDetail.timeline || defaultTimeline"
              :key="idx"
            >
              <span class="time">{{ item.time }}</span>
              <span class="desc">{{ item.desc }}</span>
            </li>
          </ul>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关闭</el-button>
      </span>
    </el-dialog>

    <!-- 反馈弹框 -->
    <el-dialog
      title="提交指导反馈"
      :visible.sync="feedbackVisible"
      width="640px"
      :close-on-click-modal="false"
    >
      <div v-if="feedbackTarget" class="feedback-dialog">
        <el-form label-width="100px" size="small">
          <el-form-item label="反馈类型" required>
            <el-select
              v-model="feedbackForm.type"
              placeholder="请选择反馈类型"
              style="width: 100%"
            >
              <el-option label="整改反馈" value="整改反馈" />
              <el-option label="材料补充" value="材料补充" />
              <el-option label="销项申请" value="销项申请" />
            </el-select>
          </el-form-item>
          <!-- 动态字段模板 -->
          <template v-if="feedbackForm.type === '整改反馈'">
            <el-form-item label="任务落实">
              <el-input
                type="textarea"
                v-model="feedbackForm.task"
                placeholder="请描述任务落实情况"
                :rows="2"
              />
            </el-form-item>
            <el-form-item label="核查进展">
              <el-input
                type="textarea"
                v-model="feedbackForm.progress"
                placeholder="核查及整改进展"
                :rows="2"
              />
            </el-form-item>
            <el-form-item label="整改动作">
              <el-input
                type="textarea"
                v-model="feedbackForm.action"
                placeholder="已采取整改动作"
                :rows="2"
              />
            </el-form-item>
          </template>
          <template v-else-if="feedbackForm.type === '材料补充'">
            <el-form-item label="补充材料">
              <el-input
                type="textarea"
                v-model="feedbackForm.material"
                placeholder="请说明补充的材料名称及内容"
                :rows="3"
              />
            </el-form-item>
            <el-form-item label="补充说明">
              <el-input
                type="textarea"
                v-model="feedbackForm.remark"
                placeholder="其他补充说明"
                :rows="2"
              />
            </el-form-item>
          </template>
          <template v-else-if="feedbackForm.type === '销项申请'">
            <el-form-item label="销项理由">
              <el-input
                type="textarea"
                v-model="feedbackForm.reason"
                placeholder="请阐述销项申请理由及整改完成情况"
                :rows="3"
              />
            </el-form-item>
            <el-form-item label="佐证材料">
              <el-input
                v-model="feedbackForm.evidence"
                placeholder="附件名称或说明"
              />
            </el-form-item>
          </template>
          <el-form-item label="备注">
            <el-input
              type="textarea"
              v-model="feedbackForm.extra"
              placeholder="其他备注（可选）"
              :rows="2"
            />
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="feedbackVisible = false">取消</el-button>
        <el-button type="primary" @click="submitFeedback">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "GuidanceLedger",
  data() {
    return {
      // 搜索表单
      searchForm: {
        guidanceType: "",
        status: "",
        street: "",
        code: "",
        topic: "",
      },
      // 台账数据 (模拟)
      tableData: [
        {
          id: 1,
          code: "ZD-202608-001",
          topic: "关于龙霞支行直播引流涉非线索的核查整改指导单",
          sourceType: "风险线索",
          guidanceType: "线索指导",
          issuer: "温州市金融监管分局",
          receiveNode: "中国建设银行股份有限公司温州龙霞支行",
          street: "梧田街道",
          responsible: "陈海峰",
          feedbackDeadline: "2026-08-12",
          status: "整改中",
          feedbackProgress: "首轮反馈",
          issueTime: "2026-08-05 09:00",
          sourceCode: "XS-202608-014",
          sourceTitle: "龙霞社区直播引流涉非线索",
          sourceContent:
            "龙霞支行上报老年客群集中到访社区活动点并存在“稳健收益”宣传话术，疑似通过直播引流开展会销宣传。",
          remedyTarget:
            "核实直播引流会销活动真实性，排查宣传主体、重点客群和账户风险，形成网点、街道、社区联动处置意见。",
          remedyRequirement:
            "对来源线索涉及的会销点位开展现场核查，固定宣传话术、物料和签到情况。对涉事主体近30日重点账户交易开展排摸，说明异常客户情况。对已接触老年客群开展风险提示，并形成解释劝阻台账。",
          reviewFocus:
            "核查现场处置是否彻底，异常客户是否逐一回访，社区联合签章是否完整。",
          latestFeedback: {
            type: "整改反馈",
            task: "龙霞支行已完成来源线索认领，安排2名客户经理和1名合规人员对龙霞社区直播会销点开展现场复核。",
            progress:
              "已完成重点楼宇复排、会销点现场核查和近30日重点账户流水排摸，识别异常客户9名，涉事主体已暂停宣传活动。",
            action:
              "已完成现场宣传物料封存、重点客户解释告知和涉事主体账户排摸，并同步形成首轮核查记录。",
          },
          timeline: [
            {
              time: "2026-08-04 16:20",
              desc: "龙霞支行提交直播引流涉非线索。",
            },
            {
              time: "2026-08-05 09:00",
              desc: "温州市金融监管分局形成指导单并下发至责任网点。",
            },
            {
              time: "2026-08-08 15:10",
              desc: "责任网点提交首轮整改反馈，要求补充客群解释台账。",
            },
          ],
        },
        {
          id: 2,
          code: "ZD-202608-002",
          topic: "关于新桥支行养老项目上报事项的整改指导",
          sourceType: "扫楼上报",
          guidanceType: "整改督办",
          issuer: "瓯海区处非办",
          receiveNode: "温州银行股份有限公司新桥支行",
          street: "新桥街道",
          responsible: "王璐",
          feedbackDeadline: "2026-08-09",
          status: "已超期",
          feedbackProgress: "逾期未反馈",
          issueTime: "2026-08-02 10:30",
          sourceCode: "SL-202608-021",
          sourceTitle: "新桥社区养老项目扫楼上报",
          sourceContent: "新桥支行在扫楼中发现某养老项目存在违规预收费行为。",
          remedyTarget: "核实养老项目预收费情况，排查资金流向。",
          remedyRequirement: "现场核查预收费协议，核实资金账户。",
          reviewFocus: "预收费是否合规，资金是否专户管理。",
          latestFeedback: null,
          timeline: [
            { time: "2026-08-02 10:30", desc: "新桥支行提交扫楼上报。" },
            { time: "2026-08-03 09:00", desc: "瓯海区处非办下发整改督办单。" },
          ],
        },
        {
          id: 3,
          code: "ZD-202607-015",
          topic: "关于娄桥支行助贷中介回头看事项的复核指导",
          sourceType: "扫楼上报",
          guidanceType: "回头看复核",
          issuer: "瓯海区金融办",
          receiveNode: "中国银行股份有限公司温州娄桥支行",
          street: "娄桥街道",
          responsible: "李慧",
          feedbackDeadline: "2026-08-08",
          status: "待复核",
          feedbackProgress: "销项申请已提交",
          issueTime: "2026-07-28 14:00",
          sourceCode: "SL-202607-019",
          sourceTitle: "娄桥助贷中介回头看",
          sourceContent:
            "娄桥支行上报助贷中介违规广告线索，已整改完毕申请复核。",
          remedyTarget: "确认中介违规广告已清除，无复发。",
          remedyRequirement: "现场复查广告物料，回访周边商户。",
          reviewFocus: "是否彻底清除，有无死灰复燃。",
          latestFeedback: {
            type: "销项申请",
            task: "已完成全部整改动作，申请销项复核。",
            progress: "现场复查未发现违规广告，周边商户反馈良好。",
            action: "已提交销项申请及佐证材料。",
          },
          timeline: [
            { time: "2026-07-28 14:00", desc: "娄桥支行提交扫楼线索。" },
            {
              time: "2026-07-29 10:00",
              desc: "瓯海区金融办下发回头看复核指导。",
            },
            {
              time: "2026-08-05 16:30",
              desc: "责任网点提交销项申请及复核材料。",
            },
          ],
        },
        {
          id: 4,
          code: "ZL-202607-019",
          topic: "关于仙岩支行违规借贷广告事项的销项复核",
          sourceType: "风险线索",
          guidanceType: "销项复核",
          issuer: "瓯海区处非办",
          receiveNode: "中国农业银行股份有限公司温州仙岩支行",
          street: "仙岩街道",
          responsible: "周建",
          feedbackDeadline: "2026-08-05",
          status: "已销项",
          feedbackProgress: "已销项",
          issueTime: "2026-07-20 09:30",
          sourceCode: "XS-202607-031",
          sourceTitle: "仙岩违规借贷广告线索",
          sourceContent: "仙岩支行发现社区内违规借贷广告，已处置。",
          remedyTarget: "全面清除违规借贷广告，加强巡查。",
          remedyRequirement: "现场清除并回访，形成巡查机制。",
          reviewFocus: "广告清除彻底，巡查机制有效。",
          latestFeedback: {
            type: "销项申请",
            task: "已全面清除违规广告，建立巡查制度。",
            progress: "经复核确认整改到位，予以销项。",
            action: "销项归档。",
          },
          timeline: [
            { time: "2026-07-20 09:30", desc: "仙岩支行提交风险线索。" },
            {
              time: "2026-07-21 14:00",
              desc: "瓯海区处非办下发销项复核指导。",
            },
            { time: "2026-08-05 17:00", desc: "复核通过，正式销项。" },
          ],
        },
      ],
      // 过滤后列表
      filteredList: [],
      // 详情
      detailVisible: false,
      currentDetail: null,
      // 反馈
      feedbackVisible: false,
      feedbackTarget: null,
      feedbackForm: {
        type: "",
        task: "",
        progress: "",
        action: "",
        material: "",
        remark: "",
        reason: "",
        evidence: "",
        extra: "",
      },
      defaultTimeline: [
        { time: "2026-08-04 16:20", desc: "龙霞支行提交直播引流涉非线索。" },
        {
          time: "2026-08-05 09:00",
          desc: "温州市金融监管分局形成指导单并下发至责任网点。",
        },
      ],
    };
  },
  mounted() {
    this.filteredList = [...this.tableData];
  },
  methods: {
    handleSearch() {
      const form = this.searchForm;
      this.filteredList = this.tableData.filter((item) => {
        let match = true;
        if (form.guidanceType && item.guidanceType !== form.guidanceType)
          match = false;
        if (form.status && item.status !== form.status) match = false;
        if (form.street && item.street !== form.street) match = false;
        if (form.code && !item.code.includes(form.code)) match = false;
        if (form.topic && !item.topic.includes(form.topic)) match = false;
        return match;
      });
    },
    resetSearch() {
      this.searchForm = {
        guidanceType: "",
        status: "",
        street: "",
        code: "",
        topic: "",
      };
      this.filteredList = [...this.tableData];
    },
    getStatusClass(status) {
      const map = {
        已超期: "overdue",
        待签收: "pending",
        整改中: "ongoing",
        待复核: "review",
        已销项: "closed",
      };
      return map[status] || "";
    },
    openDetail(row) {
      this.currentDetail = row;
      this.detailVisible = true;
    },
    openFeedback(row) {
      this.feedbackTarget = row;
      // 清空表单，不默认回显历史反馈
      this.feedbackForm = {
        type: "",
        task: "",
        progress: "",
        action: "",
        material: "",
        remark: "",
        reason: "",
        evidence: "",
        extra: "",
      };
      this.feedbackVisible = true;
    },
    submitFeedback() {
      if (!this.feedbackForm.type) {
        this.$message.warning("请选择反馈类型");
        return;
      }
      // 模拟提交成功，追加过程留痕
      const now = new Date().toISOString().slice(0, 10).replace(/-/g, "-");
      const timeStr = `${now} ${new Date().toTimeString().slice(0, 5)}`;
      const desc = `提交${this.feedbackForm.type}：${
        this.feedbackForm.type === "整改反馈"
          ? this.feedbackForm.task
          : this.feedbackForm.type === "材料补充"
          ? this.feedbackForm.material
          : this.feedbackForm.reason
      }`;
      if (this.feedbackTarget) {
        if (!this.feedbackTarget.timeline) this.feedbackTarget.timeline = [];
        this.feedbackTarget.timeline.push({ time: timeStr, desc: desc });
        // 更新状态（模拟）
        if (this.feedbackForm.type === "销项申请") {
          this.feedbackTarget.status = "待复核";
          this.feedbackTarget.feedbackProgress = "销项申请已提交";
        } else if (this.feedbackForm.type === "整改反馈") {
          this.feedbackTarget.status = "整改中";
          this.feedbackTarget.feedbackProgress = "已反馈";
        } else if (this.feedbackForm.type === "材料补充") {
          this.feedbackTarget.feedbackProgress = "材料已补充";
        }
        // 更新最新反馈
        this.feedbackTarget.latestFeedback = { ...this.feedbackForm };
        // 更新列表
        const idx = this.tableData.findIndex(
          (d) => d.id === this.feedbackTarget.id,
        );
        if (idx !== -1) this.tableData.splice(idx, 1, this.feedbackTarget);
        this.handleSearch();
      }
      this.$message.success("反馈提交成功，已追加过程留痕");
      this.feedbackVisible = false;
    },
  },
};
</script>

<style scoped>
/* 样式与之前类似，增加细节 */
.app-container {
  max-width: 1440px;
  margin: 0 auto;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.04);
  padding: 24px 28px 32px;
}
.search-bar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 16px 24px;
  background: #f9fafc;
  padding: 18px 20px;
  border-radius: 8px;
  margin-bottom: 24px;
}
.search-item {
  display: flex;
  align-items: center;
  gap: 6px;
  flex-wrap: wrap;
}
.search-item label {
  font-size: 14px;
  color: #4a5a6e;
  font-weight: 500;
  white-space: nowrap;
  min-width: 56px;
}
.search-item .el-select,
.search-item .el-input {
  width: 140px;
}
.action-buttons {
  margin-left: auto;
  display: flex;
  gap: 8px;
}
.table-wrapper {
  overflow-x: auto;
  margin-top: 4px;
}
.el-table th.el-table__cell {
  background-color: #f2f5fa !important;
  color: #1f2a3a;
  font-weight: 600;
}
.el-table .cell {
  font-size: 13px;
}
.status-tag {
  font-weight: 500;
  border: none;
  border-radius: 12px;
  padding: 2px 12px;
  display: inline-block;
  font-size: 12px;
}
.status-tag.overdue {
  background: #fef0f0;
  color: #e34d4d;
}
.status-tag.pending {
  background: #fff3e0;
  color: #e67e22;
}
.status-tag.ongoing {
  background: #e1f0fa;
  color: #2b7fc4;
}
.status-tag.review {
  background: #fef9e7;
  color: #b7950b;
}
.status-tag.closed {
  background: #e8f5e9;
  color: #2e7d32;
}
.progress-text {
  color: #3a4a5e;
  font-size: 13px;
}
.detail-container {
  padding: 8px 4px;
}
.detail-header {
  background: #f8fafd;
  padding: 16px 18px;
  border-radius: 8px;
  margin-bottom: 18px;
}
.detail-row {
  display: flex;
  align-items: baseline;
  flex-wrap: wrap;
  margin-bottom: 8px;
}
.detail-row .label {
  font-weight: 500;
  color: #3a4a5e;
  min-width: 70px;
  font-size: 14px;
}
.detail-row .value {
  color: #1f2a3a;
  font-size: 14px;
  margin-right: 16px;
}
.detail-section {
  margin-top: 18px;
  border-top: 1px solid #edf2f7;
  padding-top: 16px;
}
.detail-section h4 {
  font-weight: 600;
  color: #1f2a3a;
  margin: 0 0 10px 0;
  font-size: 15px;
}
.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 6px 20px;
  background: #f9fafc;
  padding: 12px 16px;
  border-radius: 6px;
}
.info-grid .label,
.info-text .label {
  font-weight: 500;
  color: #4a5a6e;
}
.info-text {
  margin: 6px 0;
  padding: 4px 0;
}
.feedback-box {
  background: #f3f6fb;
  padding: 12px 16px;
  border-radius: 6px;
}
.feedback-box .label {
  font-weight: 500;
  color: #2a3a4e;
}
.timeline {
  list-style: none;
  padding: 0;
  margin: 6px 0;
}
.timeline li {
  display: flex;
  align-items: baseline;
  padding: 6px 0;
  border-bottom: 1px dashed #e9edf2;
}
.timeline .time {
  font-weight: 500;
  color: #2b7fc4;
  min-width: 130px;
  font-size: 13px;
}
.timeline .desc {
  color: #1f2a3a;
  font-size: 13px;
  margin-left: 12px;
}
.feedback-dialog .el-form-item {
  margin-bottom: 16px;
}
</style>
