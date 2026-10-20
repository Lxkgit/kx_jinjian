<template>
  <el-drawer
    title="预警详情"
    :visible.sync="visible"
    direction="rtl"
    size="620px"
    custom-class="warning-detail-drawer"
    @close="handleClose"
  >
    <template v-if="detail && detail.code">
      <!-- 顶部信息 -->
      <div class="warning-header">
        <div class="warning-title">
          <i class="el-icon-warning"></i>
          <div>
            <div class="code">{{ detail.code }}</div>
            <div class="tags">
              <span
                class="tag"
                :class="'risk-' + detail.riskLevel.toLowerCase()"
              >
                {{ riskText(detail.riskLevel) }}
              </span>
              <span class="tag tag-scene">
                {{ sceneText(detail.sceneType) }}
              </span>
              <span
                class="tag"
                :class="'status-' + actualStatus(detail).toLowerCase()"
              >
                {{ processText(actualStatus(detail)) }}
              </span>
            </div>
          </div>
        </div>

        <div class="deadline">
          <span style="padding-bottom: 6px">反馈期限</span>
          <b :class="{ overdueText: isOverdue(detail) }" style="color: #fe4d4e">
            {{ detail.feedbackDeadline }}
          </b>
        </div>
      </div>

      <!-- 来源 -->
      <div class="source-box">
        <i class="el-icon-s-operation"></i>
        预警来源：
        <span>{{ detail.sourceDescription }}</span>
      </div>

      <!-- 流程进展 -->
      <section-title title="流程进展" />

      <el-timeline v-if="detail.records && detail.records.length">
        <el-timeline-item
          v-for="(item, idx) in detail.records"
          :key="item.id || idx"
          :timestamp="item.time"
          placement="top"
        >
          <div class="timeline-title" style="padding-bottom: 4px">
            {{ item.action }}
          </div>
          <!-- <div class="timeline-desc">{{ item.operator }}</div> -->
          <div v-if="item.remark" class="timeline-desc">{{ item.remark }}</div>
        </el-timeline-item>
      </el-timeline>
      <div v-else class="gray">暂无记录</div>

      <!-- 基本信息 -->
      <section-title title="基本信息" />

      <info-table
        :data="[
          ['预警时间', detail.warningTime],
          ['反馈期限', detail.feedbackDeadline],
          // ['风险等级', riskText(detail.riskLevel)],
          ['场景类型', sceneText(detail.sceneType)],
          // ['识别方式', detail.identificationMethod],
          ['处理状态', processText(actualStatus(detail))],
          // ['下发状态', dispatchStatusText(detail.dispatchStatus)],
          [
            '核查反馈',
            detail.feedback ? feedbackText(detail.feedback.result) : '待反馈',
          ],
          // ['涉险主体', detail.subject],
          // ['责任客户经理', detail.managerName || '未指派'],
          ['反馈状态', '待反馈'],
        ]"
      />

      <!-- 地址 -->
      <section-title title="预警地址" />

      <info-table
        :data="[
          ['省/市/区', detail.province + detail.city + detail.district],
          ['街道', detail.street],
          ['社区', detail.community],
          ['详细地址', detail.detailAddress],
          ['完整地址', fullAddress(detail)],
        ]"
      />

      <!-- 网格分配 -->
      <section-title title="网格分配" />

      <info-table
        :data="[
          ['所属网格', detail.gridName || '尚未下发'],
          ['责任网点', detail.branchName || '未指派'],
          ['责任客户经理', detail.managerName || '未指派'],
          ['分配方式', '系统自动分配'],
        ]"
      />

      <!-- 预警内容 -->
      <section-title title="预警内容" />

      <div class="content-box">
        <p>
          <label>涉险主体：</label>
          {{ detail.subject }}
        </p>
        <p>
          <label>统一社会信用代码：</label>
          {{ detail.creditCode }}
        </p>
        <p>
          <label>关联附件：</label>
          1份
        </p>
        <p>
          <label>风险描述：</label>
          {{ detail.content }}
        </p>
      </div>

      <!-- 附件 -->
      <section-title title="附件材料" />

      <div
        v-if="detail.attachments && detail.attachments.length"
        class="attachment"
      >
        <div
          class="file"
          v-for="(file, i) in detail.attachments"
          :key="file.name || i"
        >
          <i class="el-icon-document"></i>
          <span>{{ file.name }}</span>
        </div>
      </div>
      <div v-else class="gray">暂无附件</div>

      <!-- 核查反馈 -->
      <section-title title="现场核查反馈" />

      <template v-if="detail.feedback">
        <div class="feedback">
          <info-table
            :data="[
              ['核查结果', feedbackText(detail.feedback.result)],
              ['定位信息', detail.feedback.location || '-'],
              ['是否转风险线索', detail.feedback.reportClue ? '是' : '否'],
            ]"
          />
          <div v-if="detail.feedback.clueType" class="feedback-extra">
            <span class="fb-label">线索类型：</span
            >{{ detail.feedback.clueType }}
          </div>
          <div v-if="detail.feedback.clueDescription" class="feedback-extra">
            <span class="fb-label">线索描述：</span
            >{{ detail.feedback.clueDescription }}
          </div>
          <div v-if="detail.feedback.description" class="remark">
            <label>核查说明：</label>
            {{ detail.feedback.description }}
          </div>
          <div
            v-if="detail.feedback.photos && detail.feedback.photos.length"
            class="photos"
          >
            <img
              v-for="(img, i) in detail.feedback.photos"
              :src="img"
              :key="i"
              @click="preview(img)"
            />
          </div>
        </div>
      </template>
      <div v-else class="empty-feedback">暂无现场核查反馈</div>

      <!-- 处理记录 -->
      <section-title title="处理记录" />

      <el-timeline v-if="detail.records && detail.records.length">
        <el-timeline-item
          v-for="(item, idx) in detail.records"
          :key="item.id || idx"
          :timestamp="item.time"
          placement="top"
        >
          <div style="padding-bottom: 4px">{{ item.action }}</div>
          <small>
            <span v-if="item.remark">{{ item.remark }}</span>
          </small>
        </el-timeline-item>
      </el-timeline>
      <div v-else class="gray">暂无处理记录</div>

      <!-- 图片预览 -->
      <el-image-viewer
        v-if="previewVisible"
        :url-list="[previewUrl]"
        @close="previewVisible = false"
      />
    </template>

    <div v-else class="gray" style="text-align: center; padding: 40px">
      暂无详情数据
    </div>
  </el-drawer>
</template>

<script>
import SectionTitle from "./SectionTitle.vue";
import InfoTable from "./InfoTable.vue";

export default {
  components: {
    SectionTitle,
    InfoTable,
  },

  props: {
    visible: {
      type: Boolean,
      default: false,
    },
    detail: {
      type: Object,
      default() {
        return {};
      },
    },
  },

  data() {
    return {
      previewVisible: false,
      previewUrl: "",
    };
  },

  methods: {
    handleClose() {
      this.$emit("update:visible", false);
    },

    preview(url) {
      this.previewUrl = url;
      this.previewVisible = true;
    },

    /* ========== 枚举文本 ========== */

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

    dispatchStatusText(value) {
      return (
        {
          DISPATCHED: "已下发",
          WAIT_DISPATCH: "待下发",
        }[value] || "-"
      );
    },

    /* ========== 状态判断 ========== */

    isOverdue(row) {
      if (row.processStatus === "COMPLETED") return false;
      return new Date(row.feedbackDeadline) < new Date();
    },

    actualStatus(row) {
      if (row.processStatus !== "COMPLETED" && this.isOverdue(row)) {
        return "OVERDUE";
      }
      return row.processStatus;
    },

    /* ========== 地址拼接 ========== */

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
  },
};
</script>

<style lang="scss">
.warning-detail-drawer {
  .el-drawer__body {
    padding: 0 20px 20px;
    overflow: auto;
  }

  .warning-header {
    display: flex;
    justify-content: space-between;
    border: 1px solid #f5f5f5;
    padding: 10px;
    border-radius: 12px;
  }

  .warning-title {
    display: flex;
    gap: 12px;

    .el-icon-warning {
      font-size: 28px;
      color: #ff4d4f;
    }

    .code {
      font-weight: bold;
      font-size: 16px;
    }

    .tags {
      margin-top: 8px;
      display: flex;
      gap: 6px;
    }
  }

  .deadline {
    text-align: right;

    span {
      color: #999;
      display: block;
    }
  }

  .source-box {
    margin: 15px 0;
    padding: 12px;
    background: #f5f9ff;
    color: #409eff;
  }

  .content-box {
    background: #fafafa;
    padding: 12px;
    line-height: 24px;

    label {
      color: #666;
    }
  }

  .file {
    height: 45px;
    border: 1px solid #eee;
    display: flex;
    align-items: center;
    padding: 0 12px;
    gap: 10px;

    small {
      margin-left: auto;
      color: #999;
    }
  }

  .photos {
    display: flex;
    gap: 10px;
    margin-top: 10px;

    img {
      width: 90px;
      height: 90px;
      object-fit: cover;
      cursor: pointer;
      border-radius: 4px;
    }
  }

  .remark {
    background: #fafafa;
    padding: 10px;
    margin: 10px 0;
    line-height: 22px;

    label {
      color: #666;
    }
  }

  .feedback-extra {
    padding: 6px 10px;
    color: #333;

    .fb-label {
      color: #666;
    }
  }

  .empty-feedback {
    padding: 30px;
    text-align: center;
    color: #999;
    background: #fafafa;
  }

  .gray {
    color: #999;
  }

  .overdueText {
    color: #f56c6c;
  }

  /* ========== 标签 ========== */

  .tag {
    display: inline-block;
    padding: 3px 8px;
    border-radius: 4px;
    font-size: 12px;
    line-height: 18px;
  }

  .tag-scene {
    background: #f5f5f5;
    color: #666;
  }

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

  /* ========== 时间线 ========== */

  .timeline-title {
    font-weight: 500;
  }

  .timeline-desc {
    color: #666;
    font-size: 12px;
  }
}
</style>
