<!-- 风险预警管理 (创建预警、详情、预警下发弹框待调整) -->
<template>
  <div class="warning-page">
    <div class="search-panel">
      <el-input
        v-model="query.keyword"
        class="keyword-input"
        clearable
        prefix-icon="el-icon-search"
        placeholder="预警编号/内容/主体/地址/客户经理"
        @keyup.enter.native="search"
      />

      <el-select v-model="query.riskLevel" clearable placeholder="风险等级">
        <el-option label="高风险" value="HIGH" />
        <el-option label="中风险" value="MEDIUM" />
        <el-option label="低风险" value="LOW" />
      </el-select>

      <el-select v-model="query.processStatus" clearable placeholder="处理状态">
        <el-option label="待处理" value="PENDING" />
        <el-option label="处理中" value="PROCESSING" />
        <el-option label="已完成" value="COMPLETED" />
        <el-option label="已超期" value="OVERDUE" />
      </el-select>

      <el-select v-model="query.sceneType" clearable placeholder="场景类型">
        <el-option label="非法集资" value="ILLEGAL_FUNDRAISING" />
        <el-option label="金融诈骗" value="FINANCIAL_FRAUD" />
        <el-option label="违规借贷" value="ILLEGAL_LENDING" />
        <el-option label="不法中介" value="ILLEGAL_AGENCY" />
        <el-option label="其他" value="OTHER" />
      </el-select>

      <el-select
        v-model="query.dispatchStatus"
        clearable
        placeholder="下发状态"
      >
        <el-option label="已下发" value="DISPATCHED" />
        <el-option label="待下发" value="WAIT_DISPATCH" />
      </el-select>

      <el-select
        v-model="query.feedbackStatus"
        clearable
        placeholder="反馈状态"
      >
        <el-option label="待反馈" value="WAIT_FEEDBACK" />
        <el-option label="已反馈" value="FEEDBACK" />
      </el-select>

      <el-date-picker
        v-model="query.warningTime"
        type="daterange"
        value-format="yyyy-MM-dd"
        range-separator="→"
        start-placeholder="预警开始时间"
        end-placeholder="预警结束时间"
        class="date-picker"
      />

      <div class="search-btns">
        <el-button type="primary" icon="el-icon-search" @click="search">
          查询
        </el-button>

        <el-button @click="reset"> 重置 </el-button>
      </div>
    </div>

    <!-- =========================================================
         统计
    ========================================================== -->
    <div class="statistics">
      <div class="stat-card">
        <div class="stat-title">待处理</div>
        <div class="stat-value">
          {{ statistics.pending }}
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-title">处理中</div>
        <div class="stat-value">
          {{ statistics.processing }}
        </div>
      </div>

      <div class="stat-card">
        <div class="stat-title">待反馈</div>
        <div class="stat-value">
          {{ statistics.waitFeedback }}
        </div>
      </div>

      <div class="stat-card overdue">
        <div class="stat-title">已超期</div>
        <div class="stat-value">
          {{ statistics.overdue }}
        </div>
      </div>
    </div>

    <!-- =========================================================
         操作栏
    ========================================================== -->
    <div class="table-area">
    <div class="toolbar">
      <div class="toolbar-left">
        <el-button type="primary" icon="el-icon-plus" @click="openCreate">
          创建预警
        </el-button>

        <el-button icon="el-icon-refresh" @click="refresh"> 刷新 </el-button>

        <el-button icon="el-icon-document" @click="exportData">
          导出
        </el-button>

        <el-button
          v-if="selectedRows.length"
          icon="el-icon-position"
          @click="batchDispatch"
        >
          批量下发
        </el-button>
      </div>

      <div class="total">当前共 {{ filteredData.length }} 条记录</div>
    </div>

    <!-- =========================================================
         表格
    ========================================================== -->
    <el-table
      ref="table"
      v-loading="loading"
      :data="filteredData"
      stripe
      class="warning-table"
      @selection-change="selectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />

      <el-table-column label="预警编号" width="130" fixed="left">
        <template slot-scope="{ row }">
          <span class="warning-code" @click="openDetail(row)">
            {{ row.code }}
          </span>
        </template>
      </el-table-column>

      <el-table-column label="风险等级" width="90" align="center">
        <template slot-scope="{ row }">
          <span class="tag" :class="'risk-' + row.riskLevel.toLowerCase()">
            {{ riskText(row.riskLevel) }}
          </span>
        </template>
      </el-table-column>

      <el-table-column label="场景类型" width="125">
        <template slot-scope="{ row }">
          <div class="scene-name">
            {{ sceneText(row.sceneType) }}
          </div>

          <div class="scene-method">
            {{ row.identificationMethod }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="预警内容" min-width="230" show-overflow-tooltip>
        <template slot-scope="{ row }">
          {{ row.content }}
        </template>
      </el-table-column>

      <el-table-column label="预警地址" width="250" show-overflow-tooltip>
        <template slot-scope="{ row }">
          <div class="address">
            <i class="el-icon-location-outline" />
            {{ fullAddress(row) }}
          </div>
        </template>
      </el-table-column>

      <el-table-column label="所属网格/网点" width="155">
        <template slot-scope="{ row }">
          <template v-if="row.gridName">
            <div class="grid-name">
              {{ row.gridName }}
            </div>

            <div class="branch-name">
              {{ row.branchName }}
            </div>
          </template>

          <span v-else class="gray"> 待下发网格 </span>
        </template>
      </el-table-column>

      <el-table-column label="处理状态" width="95" align="center">
        <template slot-scope="{ row }">
          <span
            class="tag"
            :class="'status-' + actualStatus(row).toLowerCase()"
          >
            {{ processText(actualStatus(row)) }}
          </span>
        </template>
      </el-table-column>

      <el-table-column label="核查反馈" width="105" align="center">
        <template slot-scope="{ row }">
          <span
            v-if="row.feedback"
            class="tag"
            :class="'feedback-' + row.feedback.result.toLowerCase()"
          >
            {{ feedbackText(row.feedback.result) }}
          </span>

          <span v-else class="gray"> 待反馈 </span>
        </template>
      </el-table-column>

      <el-table-column label="责任客户经理" width="125">
        <template slot-scope="{ row }">
          <template v-if="row.managerName">
            <i class="el-icon-user" />
            {{ row.managerName }}
          </template>

          <span v-else class="gray"> 未指派 </span>
        </template>
      </el-table-column>

      <el-table-column label="预警时间" width="135">
        <template slot-scope="{ row }">
          {{ row.warningTime }}
        </template>
      </el-table-column>

      <el-table-column label="反馈期限" width="125">
        <template slot-scope="{ row }">
          <span :class="{ overdueText: isOverdue(row) }">
            {{ row.feedbackDeadline }}
          </span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="180" fixed="right">
        <template slot-scope="{ row }">
          <el-button type="text" @click="openDetail(row)"> 详情 </el-button>

          <el-button
            v-if="canFeedback(row)"
            type="text"
            @click="openFeedback(row)"
          >
            反馈
          </el-button>

          <el-button
            v-if="canDispatch(row)"
            type="text"
            @click="openDispatch(row)"
          >
            预警下发
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- =========================================================
         分页
    ========================================================== -->
    <div class="pagination">
      <el-pagination
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50]"
        :page-size="10"
        :total="filteredData.length"
      />
    </div>
    </div>

    <el-dialog
      title="创建预警"
      :visible.sync="createDialogVisible"
      width="720px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="createForm"
        :model="createForm"
        :rules="createRules"
        label-width="110px"
      >
        <!-- 第一行 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="风险等级" prop="riskLevel">
              <el-select
                v-model="createForm.riskLevel"
                placeholder="请选择风险等级"
                style="width: 100%"
              >
                <el-option label="高风险" value="high" />
                <el-option label="中风险" value="medium" />
                <el-option label="低风险" value="low" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="场景类型" prop="sceneType">
              <el-select
                v-model="createForm.sceneType"
                placeholder="请选择场景类型"
                style="width: 100%"
              >
                <el-option label="非法集资" value="illegal_fundraising" />
                <el-option label="金融诈骗" value="financial_fraud" />
                <el-option label="违规借贷" value="illegal_lending" />
                <el-option label="不法中介" value="illegal_agency" />
                <el-option label="其他" value="other" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 识别方式 + 预警来源 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="识别方式" prop="identifyType">
              <el-select
                v-model="createForm.identifyType"
                placeholder="请选择识别方式"
                style="width: 100%"
              >
                <el-option label="网络异常行为检测" value="network_detection" />
                <el-option
                  label="时空与行业聚集性分析"
                  value="spatial_industry_analysis"
                />
                <el-option
                  label="线上线下交叉对比"
                  value="online_offline_comparison"
                />
                <el-option label="外部协查移交" value="external_transfer" />
                <el-option label="手动创建" value="manual" />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预警来源" prop="warningSource">
              <el-select
                v-model="createForm.warningSource"
                placeholder="请选择预警来源"
                style="width: 100%"
              >
                <el-option label="系统监测" value="system_monitor" />
                <el-option label="风险线索" value="risk_clue" />
                <el-option label="扫楼上报" value="building_report" />
                <el-option label="外部协查" value="external_assistance" />
                <el-option label="人工上报" value="manual_report" />
                <el-option label="其他" value="other" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 时间 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="预警时间" prop="warningTime">
              <el-date-picker
                v-model="createForm.warningTime"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                format="yyyy-MM-dd HH:mm:ss"
                placeholder="请选择预警时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="反馈期限" prop="feedbackDeadline">
              <el-date-picker
                v-model="createForm.feedbackDeadline"
                type="date"
                value-format="yyyy-MM-dd"
                format="yyyy-MM-dd"
                placeholder="请选择反馈期限"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 地址 -->
        <el-form-item label="预警地址" required>
          <div class="address-form">
            <el-select
              v-model="createForm.street"
              placeholder="下发街道"
              @change="handleStreetChange"
            >
              <el-option
                v-for="item in streetOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>

            <el-select
              v-model="createForm.community"
              placeholder="社区/村"
              :disabled="!createForm.street"
              @change="handleCommunityChange"
            >
              <el-option
                v-for="item in communityOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>

            <el-select
              v-model="createForm.building"
              placeholder="楼宇/门牌"
              :disabled="!createForm.community"
              @change="handleBuildingChange"
            >
              <el-option
                v-for="item in buildingOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>

            <el-select
              v-model="createForm.unit"
              placeholder="单元"
              :disabled="!createForm.building"
            >
              <el-option
                v-for="item in unitOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </div>
        </el-form-item>

        <!-- 涉险主体 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="涉险主体" prop="riskSubject">
              <el-input
                v-model="createForm.riskSubject"
                placeholder="请输入涉险主体"
                clearable
              />
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="统一社会信用代码" prop="creditCode">
              <el-input
                v-model="createForm.creditCode"
                placeholder="请输入统一社会信用代码"
                maxlength="18"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 来源说明 -->
        <el-form-item label="来源说明" prop="sourceDescription">
          <el-input
            v-model="createForm.sourceDescription"
            type="textarea"
            :rows="3"
            maxlength="500"
            show-word-limit
            placeholder="请输入预警来源说明"
          />
        </el-form-item>

        <!-- 预警内容 -->
        <el-form-item label="预警内容" prop="warningContent">
          <el-input
            v-model="createForm.warningContent"
            type="textarea"
            :rows="5"
            maxlength="1000"
            show-word-limit
            placeholder="请输入预警内容"
          />
        </el-form-item>

        <!-- 附件名称 -->
        <el-form-item label="附件名称" prop="attachmentNames">
          <el-input
            v-model="createForm.attachmentNames"
            type="textarea"
            :rows="3"
            maxlength="500"
            show-word-limit
            placeholder="请输入附件名称，多个附件可使用换行或逗号分隔"
          />
          <!-- <div class="form-tip">多个附件名称可使用换行或逗号分隔</div> -->
        </el-form-item>
      </el-form>

      <div slot="footer">
        <el-button @click="createDialogVisible = false"> 取消 </el-button>

        <el-button
          type="primary"
          :loading="createSubmitting"
          @click="submitCreateWarning"
        >
          创建预警
        </el-button>
      </div>
    </el-dialog>

    <!-- =========================================================
         预警详情
    ========================================================== -->
    <el-dialog
      title="预警详情"
      :visible.sync="detailVisible"
      width="850px"
      append-to-body
    >
      <div v-if="currentWarning" class="detail-dialog">
        <!-- 顶部状态 -->
        <div class="detail-header">
          <div>
            <div class="detail-code">
              {{ currentWarning.code }}
            </div>

            <div class="detail-time">
              创建时间：{{ currentWarning.warningTime }}
            </div>
          </div>

          <div>
            <span
              class="tag"
              :class="'risk-' + currentWarning.riskLevel.toLowerCase()"
            >
              {{ riskText(currentWarning.riskLevel) }}
            </span>

            <span
              class="tag detail-status"
              :class="'status-' + actualStatus(currentWarning).toLowerCase()"
            >
              {{ processText(actualStatus(currentWarning)) }}
            </span>
          </div>
        </div>

        <!-- 流程 -->
        <div class="detail-section">
          <div class="section-title">流程进展</div>

          <el-steps
            :active="stepActive(currentWarning)"
            finish-status="success"
          >
            <el-step title="预警产生" />

            <el-step title="网格下发" />

            <el-step title="现场核查" />

            <el-step title="处理完成" />
          </el-steps>
        </div>

        <!-- 基本信息 -->
        <div class="detail-section">
          <div class="section-title">基本信息</div>

          <div class="detail-grid">
            <div>
              <label>预警来源</label>
              <span>{{ currentWarning.sourceDescription }}</span>
            </div>

            <div>
              <label>识别方式</label>
              <span>{{ currentWarning.identificationMethod }}</span>
            </div>

            <div>
              <label>风险等级</label>
              <span>{{ riskText(currentWarning.riskLevel) }}</span>
            </div>

            <div>
              <label>场景类型</label>
              <span>{{ sceneText(currentWarning.sceneType) }}</span>
            </div>

            <div>
              <label>涉险主体</label>
              <span>{{ currentWarning.subject }}</span>
            </div>

            <div>
              <label>反馈期限</label>
              <span>{{ currentWarning.feedbackDeadline }}</span>
            </div>
          </div>
        </div>

        <!-- 地址 -->
        <div class="detail-section">
          <div class="section-title">预警地址</div>

          <div class="address-detail">
            <span>{{ currentWarning.province }}</span>
            <i class="el-icon-arrow-right" />

            <span>{{ currentWarning.city }}</span>
            <i class="el-icon-arrow-right" />

            <span>{{ currentWarning.district }}</span>
            <i class="el-icon-arrow-right" />

            <span>{{ currentWarning.street }}</span>
            <i class="el-icon-arrow-right" />

            <span>{{ currentWarning.community }}</span>

            <div class="address-more">
              {{ currentWarning.detailAddress }}
            </div>
          </div>
        </div>

        <!-- 网格 -->
        <div class="detail-section">
          <div class="section-title">网格分配</div>

          <div class="grid-detail">
            <div>
              <label>所属网格</label>

              <span v-if="currentWarning.gridName">
                {{ currentWarning.gridName }}
              </span>

              <span v-else class="gray"> 尚未下发 </span>
            </div>

            <div>
              <label>责任客户经理</label>

              <span>
                {{ currentWarning.managerName || "未指派" }}
              </span>
            </div>

            <div>
              <label>责任网点</label>

              <span>
                {{ currentWarning.branchName || "未指派" }}
              </span>
            </div>
          </div>
        </div>

        <!-- 内容 -->
        <div class="detail-section">
          <div class="section-title">预警内容</div>

          <div class="content-box">
            {{ currentWarning.content }}
          </div>
        </div>

        <!-- 附件 -->
        <div class="detail-section">
          <div class="section-title">附件材料</div>

          <div v-if="currentWarning.attachments.length" class="attachment-list">
            <div
              v-for="item in currentWarning.attachments"
              :key="item.name"
              class="attachment"
            >
              <i class="el-icon-document" />
              {{ item.name }}
            </div>
          </div>

          <div v-else class="gray">暂无附件</div>
        </div>

        <!-- 现场反馈 -->
        <div class="detail-section">
          <div class="section-title">现场核查反馈</div>

          <template v-if="currentWarning.feedback">
            <div class="feedback-detail">
              <div class="feedback-result">
                <label>核查结果</label>

                <span
                  class="tag"
                  :class="
                    'feedback-' + currentWarning.feedback.result.toLowerCase()
                  "
                >
                  {{ feedbackText(currentWarning.feedback.result) }}
                </span>
              </div>

              <div>
                <label>核查说明</label>
                <p>
                  {{ currentWarning.feedback.description }}
                </p>
              </div>

              <div>
                <label>定位信息</label>
                <p>
                  {{ currentWarning.feedback.location }}
                </p>
              </div>

              <div>
                <label>是否转风险线索</label>
                <p>
                  {{ currentWarning.feedback.reportClue ? "是" : "否" }}
                </p>
              </div>

              <div v-if="currentWarning.feedback.reportClue">
                <label>线索类型</label>
                <p>
                  {{ currentWarning.feedback.clueType }}
                </p>
              </div>

              <div v-if="currentWarning.feedback.photos.length">
                <label>现场照片</label>

                <div class="photo-list">
                  <img
                    v-for="photo in currentWarning.feedback.photos"
                    :key="photo"
                    :src="photo"
                  />
                </div>
              </div>
            </div>
          </template>

          <div v-else class="empty-feedback">暂无现场核查反馈</div>
        </div>

        <!-- 处理记录 -->
        <div class="detail-section">
          <div class="section-title">处理记录</div>

          <el-timeline>
            <el-timeline-item
              v-for="record in currentWarning.records"
              :key="record.id"
              :timestamp="record.time"
              placement="top"
            >
              <div class="record-title">
                {{ record.action }}
              </div>

              <div class="record-content">
                {{ record.operator }}
                <span v-if="record.remark"> ：{{ record.remark }} </span>
              </div>
            </el-timeline-item>
          </el-timeline>
        </div>
      </div>

      <span slot="footer">
        <el-button
          v-if="currentWarning && canDispatch(currentWarning)"
          type="primary"
          @click="openDispatch(currentWarning)"
        >
          预警下发
        </el-button>

        <el-button
          v-if="currentWarning && canFeedback(currentWarning)"
          type="primary"
          @click="openFeedback(currentWarning)"
        >
          现场反馈
        </el-button>

        <el-button @click="detailVisible = false"> 关闭 </el-button>
      </span>
    </el-dialog>

    <!-- =========================================================
         下发弹框
    ========================================================== -->
    <el-dialog
      title="预警下发"
      :visible.sync="dispatchVisible"
      width="620px"
      append-to-body
    >
      <div class="dispatch-info">
        <div class="dispatch-count">
          已选择
          <b>{{ dispatchRows.length }}</b>
          条预警
        </div>

        <div v-for="row in dispatchRows" :key="row.id" class="dispatch-row">
          <div class="dispatch-code">
            {{ row.code }}
          </div>

          <div class="dispatch-address">
            {{ fullAddress(row) }}
          </div>
        </div>
      </div>

      <el-form label-width="90px">
        <el-form-item label="下发方式">
          <el-radio-group v-model="dispatchMode">
            <el-radio label="AUTO"> 系统自动匹配 </el-radio>

            <el-radio label="MANUAL"> 手动选择 </el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 自动 -->
        <template v-if="dispatchMode === 'AUTO'">
          <div class="match-result">
            <div class="match-title">系统匹配结果</div>

            <div class="match-content">
              <div>
                <label>所属网格</label>
                {{ dispatchGrid.gridName }}
              </div>

              <div>
                <label>责任客户经理</label>
                {{ dispatchGrid.managerName }}
              </div>

              <div>
                <label>责任网点</label>
                {{ dispatchGrid.branchName }}
              </div>
            </div>
          </div>
        </template>

        <!-- 手动 -->
        <template v-else>
          <el-form-item label="所属网格">
            <el-select
              v-model="manualGrid"
              style="width: 100%"
              placeholder="请选择网格"
              @change="selectGrid"
            >
              <el-option
                v-for="item in gridList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>

          <div v-if="manualGridInfo" class="manager-info">
            <div>
              责任客户经理：
              <b>{{ manualGridInfo.managerName }}</b>
            </div>

            <div>
              责任网点：
              {{ manualGridInfo.branchName }}
            </div>
          </div>
        </template>
      </el-form>

      <span slot="footer">
        <el-button @click="dispatchVisible = false"> 取消 </el-button>

        <el-button type="primary" @click="confirmDispatch">
          确认下发
        </el-button>
      </span>
    </el-dialog>

    <!-- =========================================================
         现场反馈
    ========================================================== -->
    <el-dialog
      title="现场核查反馈"
      :visible.sync="feedbackVisible"
      width="650px"
      append-to-body
    >
      <div v-if="feedbackWarning" class="feedback-dialog">
        <!-- 顶部信息 -->
        <div class="feedback-header">
          <div>
            <span>预警编号</span>
            <b>{{ feedbackWarning.code }}</b>
          </div>

          <div>
            <span>风险等级</span>

            <span
              class="tag"
              :class="'risk-' + feedbackWarning.riskLevel.toLowerCase()"
            >
              {{ riskText(feedbackWarning.riskLevel) }}
            </span>
          </div>
        </div>

        <el-divider />

        <el-form ref="feedbackForm" :model="feedbackForm" label-position="top">
          <el-form-item label="核查结果" required>
            <el-radio-group v-model="feedbackForm.result">
              <el-radio-button label="NORMAL"> 正常 </el-radio-button>

              <el-radio-button label="ABNORMAL"> 异常 </el-radio-button>

              <el-radio-button label="FOLLOW"> 需跟进 </el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="核查说明">
            <el-input
              v-model="feedbackForm.description"
              type="textarea"
              :rows="4"
              placeholder="请填写核查说明"
            />
          </el-form-item>

          <el-form-item label="现场照片">
            <el-upload
              action="#"
              list-type="picture-card"
              :auto-upload="false"
              :file-list="feedbackForm.photos"
              :on-change="photoChange"
              :on-remove="photoRemove"
            >
              <i class="el-icon-plus" />
            </el-upload>
          </el-form-item>

          <el-form-item label="定位信息">
            <el-input
              v-model="feedbackForm.location"
              placeholder="请输入现场定位信息"
            >
              <el-button
                slot="append"
                icon="el-icon-location"
                @click="getLocation"
              >
                获取定位
              </el-button>
            </el-input>
          </el-form-item>

          <el-form-item label="是否作为风险线索上报" required>
            <el-radio-group v-model="feedbackForm.reportClue">
              <el-radio :label="true"> 是 </el-radio>

              <el-radio :label="false"> 否 </el-radio>
            </el-radio-group>
          </el-form-item>

          <template v-if="feedbackForm.reportClue">
            <el-form-item label="线索类型">
              <el-select v-model="feedbackForm.clueType" style="width: 100%">
                <el-option label="非法集资" value="非法集资" />
                <el-option label="金融诈骗" value="金融诈骗" />
                <el-option label="违规借贷" value="违规借贷" />
                <el-option label="不法中介" value="不法中介" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>

            <el-form-item label="线索描述">
              <el-input
                v-model="feedbackForm.clueDescription"
                type="textarea"
                :rows="3"
                placeholder="请输入线索描述"
              />
            </el-form-item>
          </template>
        </el-form>
      </div>

      <span slot="footer">
        <el-button @click="feedbackVisible = false"> 取消 </el-button>

        <el-button type="primary" @click="submitFeedback"> 提交反馈 </el-button>
      </span>
    </el-dialog>

    <warning-detail-drawer
      :visible.sync="drawerVisible"
      :detail="currentWarning"
    />
  </div>
</template>
<script>
import WarningDetailDrawer from "./WarningDetailDrawer.vue";
export default {
  name: "WarningManagement",
  components: { WarningDetailDrawer },
  data() {
    return {
      loading: false,
      drawerVisible: false,
      currentWarning: {},
      /* ================= 查询 ================= */

      query: {
        keyword: "",
        riskLevel: "",
        processStatus: "",
        sceneType: "",
        dispatchStatus: "",
        feedbackStatus: "",
        warningTime: [],
      },

      /* ================= 统计 ================= */

      statistics: {
        pending: 2,
        processing: 2,
        waitFeedback: 2,
        overdue: 1,
      },

      selectedRows: [],

      /* ================= 创建 ================= */

      createVisible: false,
      createDialogVisible: false,
      createSubmitting: false,
      createForm: {
        riskLevel: "",
        sceneType: "",
        identifyType: "",

        warningTime: "",
        feedbackDeadline: "",

        street: "",
        community: "",
        building: "",
        unit: "",

        riskSubject: "",
        creditCode: "",

        warningSource: "",
        sourceDescription: "",
        warningContent: "",
        attachmentNames: "",
      },
      streetOptions: [
        {
          label: "景山街道",
          value: "jingshan",
        },
        {
          label: "梧田街道",
          value: "wutian",
        },
        {
          label: "茶山街道",
          value: "chashan",
        },
        {
          label: "娄桥街道",
          value: "louqiao",
        },
      ],

      // 社区
      communityOptions: [],

      // 楼宇/门牌
      buildingOptions: [],

      // 单元
      unitOptions: [],

      createRules: {
        riskLevel: [
          {
            required: true,
            message: "请选择风险等级",
            trigger: "change",
          },
        ],

        sceneType: [
          {
            required: true,
            message: "请选择场景类型",
            trigger: "change",
          },
        ],

        identifyType: [
          {
            required: true,
            message: "请选择识别方式",
            trigger: "change",
          },
        ],

        warningTime: [
          {
            required: true,
            message: "请选择预警时间",
            trigger: "change",
          },
        ],

        feedbackDeadline: [
          {
            required: true,
            message: "请选择反馈期限",
            trigger: "change",
          },
        ],

        riskSubject: [
          {
            required: true,
            message: "请输入涉险主体",
            trigger: "blur",
          },
        ],

        creditCode: [
          {
            required: true,
            message: "请输入统一社会信用代码",
            trigger: "blur",
          },
        ],

        warningSource: [
          {
            required: true,
            message: "请选择预警来源",
            trigger: "change",
          },
        ],

        sourceDescription: [
          {
            required: true,
            message: "请输入来源说明",
            trigger: "blur",
          },
        ],

        warningContent: [
          {
            required: true,
            message: "请输入预警内容",
            trigger: "blur",
          },
        ],

        attachmentNames: [
          {
            required: true,
            message: "请输入附件名称",
            trigger: "blur",
          },
        ],
      },

      /* ================= 详情 ================= */

      detailVisible: false,
      currentWarning: null,

      /* ================= 下发 ================= */

      dispatchVisible: false,
      dispatchRows: [],
      dispatchMode: "AUTO",

      manualGrid: "",
      manualGridInfo: null,

      dispatchGrid: {
        gridName: "景山街道",
        managerName: "张晓峰",
        branchName: "中国农业银行",
      },

      /* ================= 反馈 ================= */

      feedbackVisible: false,
      feedbackWarning: null,

      feedbackForm: {
        result: "",
        description: "",
        photos: [],
        location: "",
        reportClue: false,
        clueType: "",
        clueDescription: "",
      },

      /* ================= 网格 ================= */

      gridList: [
        {
          id: "G001",
          name: "景山街道",
          managerName: "张晓峰",
          branchName: "中国农业银行",
        },
        {
          id: "G002",
          name: "梧桐街道",
          managerName: "李晨曦",
          branchName: "中国建设银行",
        },
        {
          id: "G003",
          name: "茶山街道",
          managerName: "王璐",
          branchName: "中国银行",
        },
        {
          id: "G004",
          name: "姜桥街道",
          managerName: "周铭",
          branchName: "中国银行",
        },
        {
          id: "G005",
          name: "仙岩街道",
          managerName: "吴欣",
          branchName: "温州银行",
        },
      ],

      /* ================= 地址 ================= */

      addressOptions: [
        {
          value: "zhejiang",
          label: "浙江省",
          children: [
            {
              value: "wenzhou",
              label: "温州市",
              children: [
                {
                  value: "xihaigu",
                  label: "西海区",
                  children: [
                    {
                      value: "jingshan",
                      label: "景山街道",
                      children: [
                        {
                          value: "jingshan-community",
                          label: "景山社区",
                        },
                      ],
                    },
                    {
                      value: "wutong",
                      label: "梧桐街道",
                      children: [
                        {
                          value: "wutong-community",
                          label: "梧桐社区",
                        },
                      ],
                    },
                    {
                      value: "jiangqiao",
                      label: "姜桥街道",
                    },
                    {
                      value: "xianyan",
                      label: "仙岩街道",
                    },
                  ],
                },
              ],
            },
          ],
        },
      ],

      /* ================= Mock 数据 ================= */

      tableData: [
        {
          id: 1,
          code: "W20260808001",

          riskLevel: "HIGH",
          sceneType: "ILLEGAL_FUNDRAISING",
          identificationMethod: "网络异常行为检测",

          content:
            "系统识别到该地址存在高频高风险网址跳转和异常访问行为，疑似存在风险活动。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "景山街道",
          community: "景山社区",
          detailAddress: "景山小区12号楼2单元",

          gridName: "景山街道",
          branchName: "中国农业银行",
          managerName: "张晓峰",

          processStatus: "PENDING",
          dispatchStatus: "DISPATCHED",
          feedbackStatus: "WAIT_FEEDBACK",

          warningTime: "2026-08-08 09:20",
          feedbackDeadline: "2026-08-15",

          subject: "某投资咨询公司",

          sourceDescription: "系统风险监测",

          attachments: [
            {
              name: "风险监测截图.png",
            },
          ],

          feedback: null,

          records: [
            {
              id: 1,
              time: "2026-08-08 09:20",
              action: "创建预警",
              operator: "系统",
              remark: "系统自动识别风险",
            },
            {
              id: 2,
              time: "2026-08-08 09:21",
              action: "自动下发网格",
              operator: "系统",
              remark: "自动匹配景山街道",
            },
          ],
        },

        {
          id: 2,
          code: "W20260807002",

          riskLevel: "MEDIUM",
          sceneType: "FINANCIAL_FRAUD",
          identificationMethod: "时空与行业聚集性分析",

          content: "监测到同一区域内多起虚假理财宣传和金融诈骗相关行为。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "梧桐街道",
          community: "梧桐社区",
          detailAddress: "梧桐小区8幢",

          gridName: "梧桐街道",
          branchName: "中国建设银行",
          managerName: "李晨曦",

          processStatus: "PROCESSING",
          dispatchStatus: "DISPATCHED",
          feedbackStatus: "WAIT_FEEDBACK",

          warningTime: "2026-08-07 13:40",
          feedbackDeadline: "2026-08-13",

          subject: "某金融咨询机构",

          sourceDescription: "系统风险监测",

          attachments: [],

          feedback: null,

          records: [
            {
              id: 1,
              time: "2026-08-07 13:40",
              action: "创建预警",
              operator: "系统",
            },
            {
              id: 2,
              time: "2026-08-07 14:10",
              action: "下发预警",
              operator: "管理员",
              remark: "下发至梧桐街道",
            },
          ],
        },

        {
          id: 3,
          code: "W20260806003",

          riskLevel: "LOW",
          sceneType: "ILLEGAL_LENDING",
          identificationMethod: "线上线下交叉比对",

          content: "线上异常借贷广告流量与线下扫码覆盖数据存在明显异常。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "茶山街道",
          community: "随州村",
          detailAddress: "随州村18号",

          gridName: "茶山街道",
          branchName: "中国银行",
          managerName: "王璐",

          processStatus: "COMPLETED",
          dispatchStatus: "DISPATCHED",
          feedbackStatus: "FEEDBACK",

          warningTime: "2026-08-06 10:05",
          feedbackDeadline: "2026-08-09",

          subject: "个人涉险主体",

          sourceDescription: "系统风险监测",

          attachments: [],

          feedback: {
            result: "NORMAL",
            description: "现场核查未发现异常情况。",
            location: "浙江省温州市西海区茶山街道随州村18号",
            reportClue: false,
            clueType: "",
            clueDescription: "",
            photos: [],
          },

          records: [
            {
              id: 1,
              time: "2026-08-06 10:05",
              action: "创建预警",
              operator: "系统",
            },
            {
              id: 2,
              time: "2026-08-06 14:20",
              action: "提交现场核查",
              operator: "王璐",
              remark: "核查结果：正常",
            },
            {
              id: 3,
              time: "2026-08-06 14:20",
              action: "预警完成",
              operator: "系统",
            },
          ],
        },

        {
          id: 4,
          code: "W20260805004",

          riskLevel: "HIGH",
          sceneType: "ILLEGAL_FUNDRAISING",
          identificationMethod: "网络异常行为检测",

          content: "检测到异常APP高频交互、投资群推广和多次异常资金相关行为。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "姜桥街道",
          community: "姜桥村",
          detailAddress: "姜桥村8号",

          gridName: "姜桥街道",
          branchName: "中国银行",
          managerName: "周铭",

          processStatus: "OVERDUE",
          dispatchStatus: "DISPATCHED",
          feedbackStatus: "FEEDBACK",

          warningTime: "2026-08-05 08:50",
          feedbackDeadline: "2026-08-08",

          subject: "某投资机构",

          sourceDescription: "系统风险监测",

          attachments: [],

          feedback: {
            result: "ABNORMAL",
            description: "现场发现存在疑似非法集资宣传行为，已进行现场处置。",
            location: "浙江省温州市西海区姜桥街道姜桥村",
            reportClue: true,
            clueType: "非法集资",
            clueDescription: "疑似存在面向居民的非法集资宣传行为。",
            photos: [],
          },

          records: [
            {
              id: 1,
              time: "2026-08-05 08:50",
              action: "创建预警",
              operator: "系统",
            },
            {
              id: 2,
              time: "2026-08-05 09:10",
              action: "下发预警",
              operator: "管理员",
            },
            {
              id: 3,
              time: "2026-08-09 10:20",
              action: "现场核查",
              operator: "周铭",
              remark: "核查结果：异常",
            },
          ],
        },

        {
          id: 5,
          code: "W20260804005",

          riskLevel: "MEDIUM",
          sceneType: "ILLEGAL_AGENCY",
          identificationMethod: "外部访查发现",

          content: "外部协查发现某机构疑似违规代办贷款，需要进一步核实。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "新桥街道",
          community: "新桥社区",
          detailAddress: "新桥小区6幢",

          gridName: "",
          branchName: "",
          managerName: "",

          processStatus: "PENDING",
          dispatchStatus: "WAIT_DISPATCH",
          feedbackStatus: "WAIT_FEEDBACK",

          warningTime: "2026-08-04 14:10",
          feedbackDeadline: "2026-08-15",

          subject: "某贷款中介",

          sourceDescription: "外部协查",

          attachments: [],

          feedback: null,

          records: [
            {
              id: 1,
              time: "2026-08-04 14:10",
              action: "创建预警",
              operator: "管理员",
            },
          ],
        },

        {
          id: 6,
          code: "W20260803006",

          riskLevel: "HIGH",
          sceneType: "FINANCIAL_FRAUD",
          identificationMethod: "时空与行业聚集性分析",

          content: "区域内金融咨询与荐股宣传短时爆发，存在疑似风险聚集。",

          province: "浙江省",
          city: "温州市",
          district: "西海区",
          street: "仙岩街道",
          community: "繁荣村",
          detailAddress: "繁荣村21号",

          gridName: "仙岩街道",
          branchName: "温州银行",
          managerName: "吴欣",

          processStatus: "PROCESSING",
          dispatchStatus: "DISPATCHED",
          feedbackStatus: "FEEDBACK",

          warningTime: "2026-08-03 11:30",
          feedbackDeadline: "2026-08-10",

          subject: "某金融咨询机构",

          sourceDescription: "系统风险监测",

          attachments: [],

          feedback: {
            result: "FOLLOW",
            description:
              "现场存在相关宣传行为，目前无法完全排除风险，需要持续跟进。",
            location: "浙江省温州市西海区仙岩街道繁荣村",
            reportClue: false,
            clueType: "",
            clueDescription: "",
            photos: [],
          },

          records: [
            {
              id: 1,
              time: "2026-08-03 11:30",
              action: "创建预警",
              operator: "系统",
            },
            {
              id: 2,
              time: "2026-08-03 12:00",
              action: "下发预警",
              operator: "管理员",
            },
            {
              id: 3,
              time: "2026-08-04 15:20",
              action: "现场核查",
              operator: "吴欣",
              remark: "核查结果：需跟进",
            },
          ],
        },
      ],
    };
  },

  computed: {
    /**
     * 根据搜索条件过滤数据
     */
    filteredData() {
      return this.tableData.filter((row) => {
        if (this.query.keyword) {
          const keyword = this.query.keyword.toLowerCase();

          const text = [
            row.code,
            row.content,
            row.subject,
            this.fullAddress(row),
            row.managerName,
          ]
            .join(" ")
            .toLowerCase();

          if (!text.includes(keyword)) {
            return false;
          }
        }

        if (this.query.riskLevel && row.riskLevel !== this.query.riskLevel) {
          return false;
        }

        if (this.query.sceneType && row.sceneType !== this.query.sceneType) {
          return false;
        }

        if (
          this.query.processStatus &&
          this.actualStatus(row) !== this.query.processStatus
        ) {
          return false;
        }

        if (
          this.query.dispatchStatus &&
          row.dispatchStatus !== this.query.dispatchStatus
        ) {
          return false;
        }

        if (
          this.query.feedbackStatus &&
          row.feedbackStatus !== this.query.feedbackStatus
        ) {
          return false;
        }

        return true;
      });
    },
  },

  created() {
    this.refreshStatistics();
  },

  methods: {
    submitCreateWarning() {
      this.$refs.createForm.validate((valid) => {
        if (!valid) {
          return;
        }

        this.createSubmitting = true;

        const form = this.createForm;

        // 拼接完整地址
        const streetName = this.getOptionLabel(this.streetOptions, form.street);
        const communityName = this.getOptionLabel(
          this.communityOptions,
          form.community,
        );
        const buildingName = this.getOptionLabel(
          this.buildingOptions,
          form.building,
        );
        const unitName = this.getOptionLabel(this.unitOptions, form.unit);

        // 识别方式 value → 显示文本 映射
        const identifyLabelMap = {
          network_detection: "网络异常行为检测",
          spatial_industry_analysis: "时空与行业聚集性分析",
          online_offline_comparison: "线上线下交叉对比",
          external_transfer: "外部协查移交",
          manual: "手动创建",
        };

        // 预警来源 value → 显示文本 映射
        const warningSourceLabelMap = {
          system_monitor: "系统监测",
          risk_clue: "风险线索",
          building_report: "扫楼上报",
          external_assistance: "外部协查",
          manual_report: "人工上报",
          other: "其他",
        };

        // 附件名称 → attachments 数组
        const attachmentList = form.attachmentNames
          ? form.attachmentNames
              .split(/[\n,，]/)
              .map((name) => name.trim())
              .filter(Boolean)
              .map((name) => ({ name }))
          : [];

        setTimeout(() => {
          // 生成预警编号：W + 日期 + 序号（当天第几条）
          const now = new Date();
          const dateStr = `${now.getFullYear()}${String(
            now.getMonth() + 1,
          ).padStart(2, "0")}${String(now.getDate()).padStart(2, "0")}`;
          const existingCount = this.tableData.filter(
            (item) => item.code && item.code.startsWith(`W${dateStr}`),
          ).length;
          const seq = String(existingCount + 1).padStart(3, "0");
          const code = `W${dateStr}${seq}`;

          const newWarning = {
            id: Date.now(),
            code,

            // 风险等级 & 场景类型 — 转为大写以匹配表格已有数据
            riskLevel: form.riskLevel.toUpperCase(),
            sceneType: form.sceneType.toUpperCase(),
            identificationMethod:
              identifyLabelMap[form.identifyType] || form.identifyType,

            // 预警内容
            content: form.warningContent,

            // 地址 — 按表格字段拆分
            province: "浙江省",
            city: "温州市",
            district: "瓯海区",
            street: streetName,
            community: communityName,
            detailAddress: [buildingName, unitName].filter(Boolean).join(""),

            // 网格 — 新建预警尚未下发
            gridName: "",
            branchName: "",
            managerName: "",

            // 状态 — 使用表格统一的枚举值
            processStatus: "PENDING",
            dispatchStatus: "WAIT_DISPATCH",
            feedbackStatus: "WAIT_FEEDBACK",

            // 时间
            warningTime: form.warningTime,
            feedbackDeadline: form.feedbackDeadline,

            // 涉险主体
            subject: form.riskSubject,

            // 来源说明：优先用「来源说明」，为空时用「预警来源」的显示文本
            sourceDescription:
              form.sourceDescription ||
              warningSourceLabelMap[form.warningSource] ||
              "",

            // 附件
            attachments: attachmentList,

            // 反馈（暂无）
            feedback: null,

            // 处理记录
            records: [
              {
                id: 1,
                time: form.warningTime || this.now(),
                action: "创建预警",
                operator: "管理员",
                remark: "手工创建外部风险预警",
              },
            ],
          };

          this.tableData.unshift(newWarning);

          this.createSubmitting = false;
          this.createDialogVisible = false;

          // 重置表单
          this.$refs.createForm.resetFields();

          this.$message.success("预警创建成功");
          this.refreshStatistics();
        }, 500);
      });
    },
    getOptionLabel(options, value) {
      const item = options.find((item) => item.value === value);

      return item ? item.label : "";
    },

    formatWarningNo() {
      const date = new Date();

      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");

      const random = String(Math.floor(Math.random() * 9999)).padStart(4, "0");

      return `${year}${month}${day}${random}`;
    },
    handleStreetChange(value) {
      this.createForm.community = "";
      this.createForm.building = "";
      this.createForm.unit = "";

      this.buildingOptions = [];
      this.unitOptions = [];

      const data = {
        jingshan: [
          { label: "景山社区", value: "jingshan_community" },
          { label: "西山村", value: "xishan_village" },
        ],

        wutian: [
          { label: "梧田社区", value: "wutian_community" },
          { label: "南村社区", value: "nancun_community" },
        ],

        chashan: [
          { label: "茶山社区", value: "chashan_community" },
          { label: "舜岙村", value: "shunao_village" },
        ],

        louqiao: [
          { label: "娄桥社区", value: "louqiao_community" },
          { label: "玕西村", value: "wanxi_village" },
        ],
      };

      this.communityOptions = data[value] || [];
    },

    handleCommunityChange(value) {
      this.createForm.building = "";
      this.createForm.unit = "";

      this.unitOptions = [];

      const data = {
        jingshan_community: [
          { label: "景山嘉园1幢", value: "jingshan_1" },
          { label: "景山嘉园2幢", value: "jingshan_2" },
          { label: "景山街道办事处", value: "jingshan_office" },
        ],

        xishan_village: [
          { label: "西山村18号", value: "xishan_18" },
          { label: "西山村28号", value: "xishan_28" },
        ],

        wutian_community: [
          { label: "梧田街道商业综合体", value: "wutian_commercial" },
          { label: "梧田嘉园3幢", value: "wutian_3" },
        ],

        chashan_community: [
          { label: "茶山街道金融服务楼", value: "chashan_finance" },
          { label: "茶山嘉园8幢", value: "chashan_8" },
        ],
      };

      this.buildingOptions = data[value] || [];
    },

    handleBuildingChange(value) {
      this.createForm.unit = "";

      this.unitOptions = [
        {
          label: "1单元",
          value: "1",
        },
        {
          label: "2单元",
          value: "2",
        },
        {
          label: "3单元",
          value: "3",
        },
      ];
    },
    /* =========================================================
       基础枚举
    ========================================================== */

    riskText(value) {
      return (
        {
          HIGH: "高风险",
          MEDIUM: "中风险",
          LOW: "低风险",
        }[value] || "-"
      );
    },

    sceneText(value) {
      return (
        {
          ILLEGAL_FUNDRAISING: "非法集资",
          FINANCIAL_FRAUD: "金融诈骗",
          ILLEGAL_LENDING: "违规借贷",
          ILLEGAL_AGENCY: "不法中介",
          OTHER: "其他",
        }[value] || "-"
      );
    },

    processText(value) {
      return (
        {
          PENDING: "待处理",
          PROCESSING: "处理中",
          COMPLETED: "已完成",
          OVERDUE: "已超期",
        }[value] || "-"
      );
    },

    feedbackText(value) {
      return (
        {
          NORMAL: "正常",
          ABNORMAL: "异常",
          FOLLOW: "需跟进",
        }[value] || "-"
      );
    },

    /* =========================================================
       状态
    ========================================================== */

    isOverdue(row) {
      if (row.processStatus === "COMPLETED") {
        return false;
      }

      return new Date(row.feedbackDeadline) < new Date();
    },

    actualStatus(row) {
      if (row.processStatus !== "COMPLETED" && this.isOverdue(row)) {
        return "OVERDUE";
      }

      return row.processStatus;
    },

    /* =========================================================
       地址
    ========================================================== */

    fullAddress(row) {
      return [
        row.province,
        row.city,
        row.district,
        row.street,
        row.community,
        row.detailAddress,
      ]
        .filter(Boolean)
        .join("");
    },

    /* =========================================================
       查询
    ========================================================== */

    search() {
      this.$message.success("查询成功");

      this.refreshStatistics();
    },

    reset() {
      this.query = {
        keyword: "",
        riskLevel: "",
        processStatus: "",
        sceneType: "",
        dispatchStatus: "",
        feedbackStatus: "",
        warningTime: [],
      };

      this.refreshStatistics();
    },

    refresh() {
      this.loading = true;

      setTimeout(() => {
        this.loading = false;

        this.$message.success("刷新成功");

        this.refreshStatistics();
      }, 400);
    },

    refreshStatistics() {
      const data = this.tableData;

      this.statistics.pending = data.filter(
        (item) => this.actualStatus(item) === "PENDING",
      ).length;

      this.statistics.processing = data.filter(
        (item) => this.actualStatus(item) === "PROCESSING",
      ).length;

      this.statistics.waitFeedback = data.filter(
        (item) => item.feedbackStatus === "WAIT_FEEDBACK",
      ).length;

      this.statistics.overdue = data.filter(
        (item) => this.actualStatus(item) === "OVERDUE",
      ).length;
    },

    /* =========================================================
       多选
    ========================================================== */

    selectionChange(rows) {
      this.selectedRows = rows;
    },

    /* =========================================================
       创建
    ========================================================== */

    openCreate() {
      this.createDialogVisible = true;
    },

    createWarning() {
      if (!this.createForm.riskLevel) {
        this.$message.warning("请选择风险等级");
        return;
      }

      if (!this.createForm.sceneType) {
        this.$message.warning("请选择场景类型");
        return;
      }

      if (!this.createForm.content) {
        this.$message.warning("请输入预警内容");
        return;
      }

      const id = Date.now();

      const row = {
        id,

        code: `W${id}`,

        riskLevel: this.createForm.riskLevel,

        sceneType: this.createForm.sceneType,

        identificationMethod: this.createForm.identificationMethod,

        content: this.createForm.content,

        province: "浙江省",
        city: "温州市",
        district: "西海区",
        street: "景山街道",
        community: "景山社区",

        detailAddress: this.createForm.detailAddress,

        gridName: "",
        branchName: "",
        managerName: "",

        processStatus: "PENDING",

        dispatchStatus: this.createForm.directDispatch
          ? "DISPATCHED"
          : "WAIT_DISPATCH",

        feedbackStatus: "WAIT_FEEDBACK",

        warningTime: this.createForm.warningTime,

        feedbackDeadline: this.createForm.feedbackDeadline,

        subject: this.createForm.subject,

        sourceDescription: this.createForm.sourceDescription,

        attachments: [],

        feedback: null,

        records: [
          {
            id: 1,
            time: this.createForm.warningTime,
            action: "创建预警",
            operator: "管理员",
            remark: "手工创建外部风险预警",
          },
        ],
      };

      this.tableData.unshift(row);

      this.createVisible = false;

      if (this.createForm.directDispatch) {
        this.autoDispatch([row]);
      } else {
        this.$message.success("预警创建成功");

        this.resetCreateForm();
      }

      this.refreshStatistics();
    },

    resetCreateForm() {
      this.createForm = {
        riskLevel: "MEDIUM",
        sceneType: "FINANCIAL_FRAUD",
        identificationMethod: "外部访查发现",
        warningTime: "2026-08-11 18:00",
        feedbackDeadline: "2026-08-15",
        subject: "",
        address: [],
        detailAddress: "",
        content: "",
        sourceDescription: "",
        attachments: [],
        directDispatch: false,
      };
    },

    /* =========================================================
       详情
    ========================================================== */

    openDetail(row) {
      this.currentWarning = row;
      this.drawerVisible = true;
      console.log("数据呢: ", row);
    },

    stepActive(row) {
      if (row.processStatus === "COMPLETED") {
        return 4;
      }

      if (row.feedback) {
        return 3;
      }

      if (row.gridName) {
        return 2;
      }

      return 1;
    },

    /* =========================================================
       下发
    ========================================================== */

    canDispatch(row) {
      return (
        row.processStatus === "PENDING" &&
        row.dispatchStatus === "WAIT_DISPATCH" &&
        !row.gridName
      );
    },

    openDispatch(row) {
      this.dispatchRows = [row];

      this.dispatchMode = "AUTO";

      this.manualGrid = "";

      this.manualGridInfo = null;

      this.dispatchVisible = true;
    },

    batchDispatch() {
      if (!this.selectedRows.length) {
        this.$message.warning("请选择预警记录");
        return;
      }

      const invalid = this.selectedRows.some((row) => {
        return !this.canDispatch(row);
      });

      if (invalid) {
        this.$message.warning("请选择待处理且尚未下发网格的预警记录");

        return;
      }

      this.dispatchRows = [...this.selectedRows];

      this.dispatchMode = "AUTO";

      this.manualGrid = "";

      this.manualGridInfo = null;

      this.dispatchVisible = true;
    },

    selectGrid(id) {
      this.manualGridInfo = this.gridList.find((item) => item.id === id);
    },

    autoDispatch(rows) {
      rows.forEach((row) => {
        row.gridName = this.dispatchGrid.gridName;
        row.managerName = this.dispatchGrid.managerName;
        row.branchName = this.dispatchGrid.branchName;

        row.dispatchStatus = "DISPATCHED";

        row.processStatus = "PENDING";

        row.feedbackStatus = "WAIT_FEEDBACK";

        row.records.push({
          id: Date.now(),
          time: this.now(),
          action: "自动下发网格",
          operator: "系统",
          remark: `自动匹配${row.gridName}`,
        });
      });

      this.$message.success("预警下发成功");

      this.resetCreateForm();

      this.refreshStatistics();
    },

    confirmDispatch() {
      if (!this.dispatchRows.length) {
        return;
      }

      let gridInfo = this.dispatchGrid;

      if (this.dispatchMode === "MANUAL") {
        if (!this.manualGridInfo) {
          this.$message.warning("请选择下发网格");
          return;
        }

        gridInfo = this.manualGridInfo;
      }

      this.dispatchRows.forEach((row) => {
        row.gridName = gridInfo.name || gridInfo.gridName;

        row.managerName = gridInfo.managerName;

        row.branchName = gridInfo.branchName;

        row.dispatchStatus = "DISPATCHED";

        row.feedbackStatus = "WAIT_FEEDBACK";

        row.processStatus = "PENDING";

        row.records.push({
          id: Date.now() + Math.random(),
          time: this.now(),
          action: "下发预警",
          operator: "管理员",
          remark: `下发至${row.gridName}`,
        });
      });

      this.dispatchVisible = false;

      this.selectedRows = [];

      this.$refs.table.clearSelection();

      this.$message.success("预警下发成功");

      this.refreshStatistics();
    },

    /* =========================================================
       反馈
    ========================================================== */

    canFeedback(row) {
      return (
        row.dispatchStatus === "DISPATCHED" &&
        row.feedbackStatus === "WAIT_FEEDBACK" &&
        row.processStatus !== "COMPLETED"
      );
    },

    openFeedback(row) {
      this.feedbackWarning = row;

      this.feedbackForm = {
        result: "",

        description: "",

        photos: [],

        location: this.fullAddress(row),

        reportClue: false,

        clueType: "",

        clueDescription: "",
      };

      this.feedbackVisible = true;

      this.detailVisible = false;
    },

    photoChange(file, fileList) {
      this.feedbackForm.photos = fileList;
    },

    photoRemove(file, fileList) {
      this.feedbackForm.photos = fileList;
    },

    getLocation() {
      if (!navigator.geolocation) {
        this.$message.warning("当前浏览器不支持定位");

        return;
      }

      navigator.geolocation.getCurrentPosition(
        (position) => {
          this.feedbackForm.location = `${position.coords.latitude}, ${position.coords.longitude}`;

          this.$message.success("定位获取成功");
        },
        () => {
          this.$message.error("定位获取失败");
        },
      );
    },

    submitFeedback() {
      if (!this.feedbackForm.result) {
        this.$message.warning("请选择核查结果");

        return;
      }

      if (!this.feedbackForm.description) {
        this.$message.warning("请输入核查说明");

        return;
      }

      if (this.feedbackForm.reportClue && !this.feedbackForm.clueType) {
        this.$message.warning("请选择线索类型");

        return;
      }

      const row = this.feedbackWarning;

      row.feedback = {
        ...this.feedbackForm,
      };

      row.feedbackStatus = "FEEDBACK";

      /*
       * 正常 / 异常 → 已完成
       * 需跟进 → 处理中
       */

      if (
        this.feedbackForm.result === "NORMAL" ||
        this.feedbackForm.result === "ABNORMAL"
      ) {
        row.processStatus = "COMPLETED";
      } else {
        row.processStatus = "PROCESSING";
      }

      row.records.push({
        id: Date.now(),
        time: this.now(),
        action: "现场核查反馈",
        operator: row.managerName || "管理员",
        remark: `核查结果：${this.feedbackText(this.feedbackForm.result)}`,
      });

      this.feedbackVisible = false;

      this.$message.success("反馈提交成功");

      this.refreshStatistics();
    },

    /* =========================================================
       导出
    ========================================================== */

    exportData() {
      this.$message.success(`已导出 ${this.filteredData.length} 条预警记录`);
    },

    now() {
      const date = new Date();

      const pad = (value) => String(value).padStart(2, "0");

      return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(
        date.getDate(),
      )} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(
        date.getSeconds(),
      )}`;
    },
  },
};
</script>

<style lang="scss" scoped>
/* =========================================================
   页面
========================================================= */

.warning-page {
  min-height: 100%;
  padding: 20px;
  background: #f0f2f5;
  box-sizing: border-box;
}

/* =========================================================
   搜索
========================================================= */

.search-panel {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 12px 28px;
  padding: 20px;
  border-radius: 4px;
  background: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

  .el-input,
  .el-select {
    width: 168px;
  }

  .keyword-input {
    width: 425px;
  }

  .date-picker {
    width: 425px;
  }

  .search-btns {
    display: flex;
    gap: 8px;
  }
}

/* =========================================================
   统计
========================================================= */

.statistics {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 14px;
  margin-top: 20px;
}

.stat-card {
  height: 76px;
  padding: 14px;
  background: #fff;
  border: 1px solid #ebeef5;
  border-radius: 7px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.04);
  box-sizing: border-box;

  .stat-title {
    color: #666;
    font-size: 13px;
  }

  .stat-value {
    margin-top: 7px;
    color: #303133;
    font-size: 22px;
    font-weight: 600;
  }

  &.overdue {
    .stat-value {
      color: #f56c6c;
    }
  }
}

/* =========================================================
   工具栏
========================================================= */

.toolbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0 0 14px;

  .toolbar-left {
    display: flex;
    gap: 8px;
  }

  .total {
    color: #999;
    font-size: 13px;
  }
}

.table-area {
  margin-top: 20px;
  padding: 20px;
  border-radius: 4px;
  background: #fff;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

/* =========================================================
   表格
========================================================= */

.warning-table {
  th {
    height: 54px;
    background: #fafafa;
    color: #303133;
    font-weight: 600;
  }

  td {
    height: 64px;
    color: #303133;
  }

  .cell {
    line-height: 20px;
  }
}

.warning-code {
  color: #1677ff;
  cursor: pointer;

  &:hover {
    text-decoration: underline;
  }
}

.tag {
  display: inline-block;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 12px;
  line-height: 18px;
}

/* =========================================================
   风险
========================================================= */

.risk-high {
  color: #f56c6c;
  background: #fff1f0;
}

.risk-medium {
  color: #e6a23c;
  background: #fff7e6;
}

.risk-low {
  color: #52c41a;
  background: #f6ffed;
}

/* =========================================================
   场景
========================================================= */

.scene-name {
  display: inline-block;
  padding: 2px 7px;
  border-radius: 3px;
  background: #f5f5f5;
  font-size: 12px;
}

.scene-method {
  margin-top: 4px;
  color: #999;
  font-size: 12px;
}

/* =========================================================
   地址
========================================================= */

.address {
  color: #303133;

  i {
    margin-right: 4px;
    color: #1677ff;
  }
}

.grid-name {
  color: #1677ff;
}

.branch-name {
  margin-top: 3px;
  color: #999;
  font-size: 12px;
}

.gray {
  color: #999;
}

.overdueText {
  color: #f56c6c;
}

/* =========================================================
   处理状态
========================================================= */

.status-pending {
  color: #e6a23c;
  background: #fff7e6;
}

.status-processing {
  color: #1677ff;
  background: #eaf4ff;
}

.status-completed {
  color: #52c41a;
  background: #f6ffed;
}

.status-overdue {
  color: #f56c6c;
  background: #fff1f0;
}

/* =========================================================
   反馈状态
========================================================= */

.feedback-normal {
  color: #52c41a;
  background: #f6ffed;
}

.feedback-abnormal {
  color: #f56c6c;
  background: #fff1f0;
}

.feedback-follow {
  color: #e6a23c;
  background: #fff7e6;
}

/* =========================================================
   分页
========================================================= */

.pagination {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 20px;
  margin-top: 20px;

  color: #666;
  font-size: 13px;
}

/* =========================================================
   弹窗公共
========================================================= */

.form-section,
.detail-section {
  margin-bottom: 24px;
}

.section-title {
  margin-bottom: 16px;
  padding-left: 10px;
  border-left: 3px solid #1677ff;
  color: #303133;
  font-size: 15px;
  font-weight: 600;
}

.form-tip {
  margin-top: 6px;
  color: #999;
  font-size: 12px;
}

/* =========================================================
   详情
========================================================= */

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 18px;
}

.detail-code {
  color: #303133;
  font-size: 18px;
  font-weight: 600;
}

.detail-time {
  margin-top: 6px;
  color: #999;
  font-size: 12px;
}

.detail-status {
  margin-left: 8px;
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px 30px;

  > div {
    display: flex;

    label {
      width: 90px;
      flex-shrink: 0;
      color: #999;
    }
  }
}

.address-detail {
  padding: 14px;
  border-radius: 5px;
  background: #f8f9fb;
  color: #303133;

  i {
    margin: 0 6px;
    color: #aaa;
  }
}

.address-more {
  margin-top: 8px;
  color: #666;
}

.grid-detail {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;

  label {
    display: block;
    margin-bottom: 6px;
    color: #999;
    font-size: 12px;
  }
}

.content-box {
  padding: 14px;
  border-radius: 5px;
  background: #f8f9fb;
  color: #333;
  line-height: 1.8;
}

.attachment {
  display: inline-flex;
  align-items: center;
  margin-right: 12px;
  padding: 8px 12px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
  color: #1677ff;

  i {
    margin-right: 6px;
  }
}

.feedback-detail {
  > div {
    margin-bottom: 15px;
  }

  label {
    display: block;
    margin-bottom: 6px;
    color: #999;
    font-size: 12px;
  }

  p {
    margin: 0;
    color: #333;
    line-height: 1.7;
  }
}

.photo-list {
  display: flex;
  gap: 10px;

  img {
    width: 90px;
    height: 90px;
    object-fit: cover;
    border-radius: 5px;
  }
}

.empty-feedback {
  padding: 30px;
  text-align: center;
  color: #999;
  background: #fafafa;
}

/* =========================================================
   下发
========================================================= */

.dispatch-count {
  margin-bottom: 15px;
  color: #666;

  b {
    margin: 0 4px;
    color: #1677ff;
  }
}

.dispatch-row {
  margin-bottom: 8px;
  padding: 10px 12px;
  border: 1px solid #ebeef5;
  border-radius: 4px;

  .dispatch-code {
    color: #1677ff;
    font-weight: 500;
  }

  .dispatch-address {
    margin-top: 4px;
    color: #999;
    font-size: 12px;
  }
}

.match-result {
  margin: 10px 0 20px;
  padding: 16px;
  border-radius: 5px;
  background: #f6faff;
}

.match-title {
  margin-bottom: 12px;
  color: #1677ff;
  font-weight: 600;
}

.match-content {
  > div {
    margin-bottom: 8px;
  }

  label {
    display: inline-block;
    width: 110px;
    color: #999;
  }
}

.manager-info {
  margin-left: 90px;
  padding: 12px;
  border-radius: 5px;
  background: #f8f9fb;
  color: #666;

  div + div {
    margin-top: 5px;
  }
}

/* =========================================================
   反馈
========================================================= */

.feedback-header {
  display: grid;
  grid-template-columns: 1fr 1fr;
  padding: 12px 14px;
  border: 1px solid #ebeef5;
  border-radius: 5px;
  background: #fafafa;

  > div {
    display: flex;
    align-items: center;
  }

  span:first-child {
    width: 80px;
    color: #999;
  }
}

/* =========================================================
   Element UI 微调
========================================================= */

::v-deep .el-dialog__body {
  max-height: 70vh;
  overflow-y: auto;
}

::v-deep .el-dialog__header {
  border-bottom: 1px solid #f0f0f0;
}

::v-deep .el-dialog__footer {
  border-top: 1px solid #f0f0f0;
}

::v-deep .el-table__fixed-right {
  box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
}
</style>
