<template>
  <div class="risk-alert-wrapper">
    <!-- 第一行：两大统计卡片 -->
    <div class="stat-cards-row">
      <div
        class="stat-card main-card"
        v-for="card in statCards"
        :key="card.label"
      >
        <div class="card-icon-wrapper" :class="card.iconClass">
          <img
            :src="require(`@/modules/module-screenView/assets/image/${card.icon}`)"
            class="icon-img"
          />
        </div>
        <div class="card-content">
          <div class="card-value">
            <span class="value-num">{{ card.value }}</span>
            <span class="value-unit">{{ card.unit }}</span>
          </div>
          <div class="card-label">{{ card.label }}</div>
        </div>
      </div>
    </div>

    <!-- 第二行：风险分类列表 -->
    <div class="category-section">
      <div class="category-row" v-for="(item, idx) in categoryList" :key="idx">
        <span class="row-dot"></span>
        <span class="category-name">{{ item.name }}</span>
        <div class="status-tags">
          <span
            class="status-tag"
            v-for="(tag, tIdx) in item.tags"
            :key="tIdx"
            :class="tag.class"
          >
            <i class="tag-dot"></i>
            {{ tag.label }}
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 按机构层级模拟的风险预警数据
const LEVEL_DATA = {
  1: {
    stats: [
      { label: "风险下发", value: "13808", unit: "条", icon: "issue.png", iconClass: "icon-issue" },
      { label: "风险处置", value: "1456", unit: "条", icon: "disposal.png", iconClass: "icon-dispose" },
    ],
    categories: [
      { name: "财经人物（7月1日至8日）金融风险防范与化解专题报告", tags: [{ label: "财富管理", class: "tag-yellow" }, { label: "第三方", class: "tag-orange" }] },
      { name: "身边不少亲戚朋友最近都被熟人拉进非法集资圈套", tags: [{ label: "中通·养老", class: "tag-green" }, { label: "第三方", class: "tag-orange" }] },
      { name: "雷达财经出品文丁禹编|孟帅利用AI技术识别金融诈骗新手段", tags: [{ label: "回购", class: "tag-blue" }, { label: "第三方", class: "tag-orange" }] },
    ],
  },
  2: {
    stats: [
      { label: "风险下发", value: "2680", unit: "条", icon: "issue.png", iconClass: "icon-issue" },
      { label: "风险处置", value: "284", unit: "条", icon: "disposal.png", iconClass: "icon-dispose" },
    ],
    categories: [
      { name: "财经人物（7月1日至8日）金融风险防范与化解专题报告", tags: [{ label: "财富管理", class: "tag-yellow" }, { label: "第三方", class: "tag-orange" }] },
      { name: "某市分行发现利用数字货币钱包进行洗钱的线索", tags: [{ label: "数字货币", class: "tag-blue" }, { label: "第三方", class: "tag-orange" }] },
      { name: "辖区内多起以“养老理财”为名义的非法集资事件", tags: [{ label: "养老理财", class: "tag-green" }, { label: "第三方", class: "tag-orange" }] },
    ],
  },
  3: {
    stats: [
      { label: "风险下发", value: "628", unit: "条", icon: "issue.png", iconClass: "icon-issue" },
      { label: "风险处置", value: "72", unit: "条", icon: "disposal.png", iconClass: "icon-dispose" },
    ],
    categories: [
      { name: "财经人物（7月1日至8日）金融风险防范与化解专题报告", tags: [{ label: "财富管理", class: "tag-yellow" }, { label: "第三方", class: "tag-orange" }] },
      { name: "区县内某街道出现冒充银行客服的电信诈骗", tags: [{ label: "电信诈骗", class: "tag-orange" }, { label: "第三方", class: "tag-orange" }] },
      { name: "社区排查发现“消费返利”式非法集资活动", tags: [{ label: "消费返利", class: "tag-blue" }, { label: "第三方", class: "tag-orange" }] },
    ],
  },
  4: {
    stats: [
      { label: "风险下发", value: "86", unit: "条", icon: "issue.png", iconClass: "icon-issue" },
      { label: "风险处置", value: "12", unit: "条", icon: "disposal.png", iconClass: "icon-dispose" },
    ],
    categories: [
      { name: "财经人物（7月1日至8日）金融风险防范与化解专题报告", tags: [{ label: "财富管理", class: "tag-yellow" }, { label: "第三方", class: "tag-orange" }] },
      { name: "街道某商户疑似参与“炒币”非法金融活动", tags: [{ label: "虚拟货币", class: "tag-blue" }, { label: "第三方", class: "tag-orange" }] },
      { name: "社区网格员上报多起“杀猪盘”网络诈骗警情", tags: [{ label: "网络诈骗", class: "tag-orange" }, { label: "第三方", class: "tag-orange" }] },
    ],
  },
  5: {
    stats: [
      { label: "风险下发", value: "12", unit: "条", icon: "issue.png", iconClass: "icon-issue" },
      { label: "风险处置", value: "3", unit: "条", icon: "disposal.png", iconClass: "icon-dispose" },
    ],
    categories: [
      { name: "财经人物（7月1日至8日）金融风险防范与化解专题报告", tags: [{ label: "财富管理", class: "tag-yellow" }, { label: "第三方", class: "tag-orange" }] },
      { name: "社区内出现上门推销“高收益理财产品”的可疑人员", tags: [{ label: "上门推销", class: "tag-orange" }, { label: "第三方", class: "tag-orange" }] },
      { name: "网格员发现居民群中传播可疑投资链接", tags: [{ label: "网络传销", class: "tag-green" }, { label: "第三方", class: "tag-orange" }] },
    ],
  },
};

export default {
  name: "riskalert",
  props: {
    // 机构层级 1-5（省/市/区县/乡镇/村）
    orgLevel: { type: [Number, String], default: 1 },
    orgCode: { type: [Number, String], default: "" },
    orgName: { type: String, default: "" },
  },
  computed: {
    level() {
      return Math.min(5, Math.max(1, Number(this.orgLevel) || 1));
    },
    current() {
      const source = LEVEL_DATA[this.level] || LEVEL_DATA[1];
      const seed = this.hashCode(`${this.orgCode || "330000"}-${this.level}`);
      const regionName = this.orgName || ["浙江省", "杭州市", "当前区县", "当前街道", "当前社区"][this.level - 1];
      const ratio = 0.88 + (seed % 25) / 100;
      return {
        stats: source.stats.map((item, index) => ({
          ...item,
          value: String(
            Math.max(
              1,
              Math.round(Number(item.value) * (ratio + ((seed >> (index + 2)) % 7) / 100))
            )
          ),
        })),
        categories: source.categories.map((item, index) => ({
          ...item,
          name: `${regionName}：${this.localRiskText(index)}`,
        })),
      };
    },
    statCards() {
      return this.current.stats;
    },
    categoryList() {
      return this.current.categories;
    },
  },
  methods: {
    hashCode(value) {
      return String(value)
        .split("")
        .reduce((result, char) => ((result * 31 + char.charCodeAt(0)) >>> 0), 17);
    },
    localRiskText(index) {
      const texts = [
        "天罗监测发现疑似高收益理财宣传，已下发属地核查",
        "监测到养老投资类异常传播信息，正在开展关联排查",
        "发现冒充金融机构客服的涉诈线索，已进入处置流程",
      ];
      return texts[index] || texts[0];
    },
  },
};
</script>

<style lang="scss" scoped>
.risk-alert-wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
}

// ===== 第一大行：统计卡片 =====
.stat-cards-row {
  display: flex;
  gap: 16px;
  margin-bottom: 12px;
}

.stat-card {
  flex: 1;
  display: flex;
  align-items: center;
  padding: 12px 16px;
  // background: linear-gradient(
  //   180deg,
  //   rgba(53, 78, 88, 0.3) 0%,
  //   rgba(35, 55, 63, 0.05) 100%
  // );
  border: 1px solid rgba(102, 206, 245, 0.12);
  border-radius: 4px;
  position: relative;

  &::after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 10%;
    width: 80%;
    height: 1px;
    background: linear-gradient(
      90deg,
      transparent,
      rgba(102, 206, 245, 0.3),
      transparent
    );
  }
}

// 卡片图标
.card-icon-wrapper {
  width: 44px;
  height: 44px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 14px;
  flex-shrink: 0;

  &.icon-issue {
    background: rgba(0, 213, 255, 0.12);
    border: 1px solid rgba(0, 213, 255, 0.35);
    .icon-img {
      width: 28px;
      height: 28px;
      object-fit: contain;
    }
  }

  &.icon-dispose {
    background: rgba(69, 255, 140, 0.1);
    border: 1px solid rgba(69, 255, 140, 0.3);
    .icon-img {
      width: 28px;
      height: 28px;
      object-fit: contain;
    }
  }
}

// 卡片内容
.card-content {
  display: flex;
  flex-direction: column;
}

.card-value {
  display: flex;
  align-items: baseline;
  gap: 4px;
  line-height: 1.2;

  .value-num {
    font-size: 28px;
    font-weight: bold;
    font-family: "Number", "DIN Alternate", "Arial", sans-serif;
    color: transparent;
    background: linear-gradient(180deg, #ffffff 0%, #00d5ff 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }

  .value-unit {
    font-size: 13px;
    color: rgba(255, 255, 255, 0.6);
  }
}

.card-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.56);
  margin-top: 2px;
}

// ===== 风险分类列表 =====
.category-section {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 10px;
}

.category-row {
  display: flex;
  align-items: center;
  padding: 9px 0;
  // border-bottom: 1px solid rgba(102, 206, 245, 0.08);

  background: linear-gradient(
    270deg,
    #95add147 0%,
    #95add147 65%,
    #7eb7ca00 97%
  );
  &:last-child {
    border-bottom: none;
  }
}

// 行前圆点
.row-dot {
  background-image: url("~@/modules/module-screenView/assets/image/row.png");
  background-size: 100% 100%;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  flex-shrink: 0;
  // margin-right: 10px;

  &.dot-red {
    background: #ff4545;
    box-shadow: 0 0 5px rgba(255, 69, 69, 0.5);
  }
  &.dot-orange {
    background: #ff8c45;
    box-shadow: 0 0 5px rgba(255, 140, 69, 0.5);
  }
  &.dot-yellow {
    background: #ffc045;
    box-shadow: 0 0 5px rgba(255, 192, 69, 0.5);
  }
}

// 分类名
.category-name {
  display: inline-block;
  font-size: 14px;
  color: #fff;
  flex: 1;
  white-space: nowrap; /* 强制不换行 */
  overflow: hidden; /* 溢出隐藏 */
  text-overflow: ellipsis;
  min-width: 0;
  width: 120px;
}

// 状态标签
.status-tags {
  display: flex;
  gap: 8px;
  flex-shrink: 0;
}

.status-tag {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  padding: 3px 8px;
  border-radius: 3px;
  white-space: nowrap;

  .tag-dot {
    width: 6px;
    height: 6px;
    border-radius: 50%;
    flex-shrink: 0;
  }

  &.tag-orange {
    color: #ff8c45;
    background: rgba(255, 140, 69, 0.1);
    border: 1px solid rgba(255, 140, 69, 0.25);
    .tag-dot {
      background: #ff8c45;
    }
  }

  &.tag-yellow {
    color: #ffc045;
    background: rgba(255, 192, 69, 0.1);
    border: 1px solid rgba(255, 192, 69, 0.25);
    .tag-dot {
      background: #ffc045;
    }
  }

  &.tag-green {
    color: #45ff8c;
    background: rgba(69, 255, 140, 0.1);
    border: 1px solid rgba(69, 255, 140, 0.25);
    .tag-dot {
      background: #45ff8c;
    }
  }

  &.tag-blue {
    color: #45a3ff;
    background: rgba(69, 163, 255, 0.1);
    border: 1px solid rgba(69, 163, 255, 0.25);
    .tag-dot {
      background: #45a3ff;
    }
  }
}

// ===== 底部滚动消息 =====
.news-ticker {
  margin-top: 6px;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.ticker-item {
  display: flex;
  align-items: center;
  gap: 6px;
  overflow: hidden;
}

.ticker-arrow {
  color: rgba(0, 213, 255, 0.6);
  font-size: 12px;
  flex-shrink: 0;
}

.ticker-text {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.45);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
