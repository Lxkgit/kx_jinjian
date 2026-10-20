<template>
  <div class="bar-bottom">
    <div v-for="(it, idx) in displayItems" :key="idx" class="item">
      <div class="left">
        <div class="rank-pill">
          <span class="top-text">TOP</span>
          <span class="rank-num">{{ idx + 1 }}</span>
        </div>
      </div>
      <div class="center">
        <div class="name"><span class="dot">•</span>{{ it.name }}</div>
        <div class="bar-wrap">
          <div class="bar-track">
            <div class="bar-fill" :style="{ width: percent(it) }">
              <div class="striped"></div>
            </div>
            <div class="bar-border"></div>
          </div>
        </div>
      </div>
      <div class="right">{{ it.value }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BarBottom",
  props: {
    items: {
      type: Array,
      default: () => [
        { name: "鹿城区", value: 12345 },
        { name: "永嘉县", value: 10876 },
        { name: "洞头区", value: 9677 },
        { name: "瑞安市", value: 8556 },
        { name: "龙港市", value: 6088 },
      ],
    },
  },
  computed: {
    maxValue() {
      const arr =
        this.items && this.items.length
          ? this.items.map((i) => Number(i.value) || 0)
          : [1];
      const m = Math.max(...arr, 1);
      return m;
    },
    displayItems() {
      return (this.items || []).slice(0, 5);
    },
  },
  methods: {
    percent(item) {
      const v = Number(item.value) || 0;
      const p = Math.round((v / this.maxValue) * 10000) / 100; // keep 2 decimals
      return p + "%";
    },
  },
};
</script>

<style lang="scss" scoped>
.bar-bottom {
  width: 420px;
  box-sizing: border-box;
  display: flex;
  flex-flow: column nowrap;
  gap: 3px 0;
  align-items: center;
  .item {
    width: 100%;
    display: flex;
    align-items: center;
    .left {
      width: 90px;
      display: flex;
      justify-content: flex-end;
      padding-right: 8px;
    }
    .center {
      flex: 1;
      .name {
        color: #d8f6ff;
        font-size: 16px;
        margin-bottom: 6px;
        .dot {
          color: #6fd6ff;
          padding-right: 8px;
        }
      }
      .bar-wrap {
        position: relative;
        height: 20px;
        .bar-track {
          position: relative;
          height: 100%;
          background: linear-gradient(
            180deg,
            rgba(255, 255, 255, 0.02),
            rgba(0, 0, 0, 0.06)
          );
          border: 2px solid rgba(255, 255, 255, 0.06);
          border-radius: 6px;
          overflow: hidden;
        }
        .bar-fill {
          height: 100%;
          background: linear-gradient(
            90deg,
            #19b8ff,
            #0bb1ff 50%,
            #0a6b8f 100%
          );
          position: relative;
          transition: width 1s ease;
        }
        .striped {
          position: absolute;
          inset: 0;
          background-image: repeating-linear-gradient(
            135deg,
            rgba(255, 255, 255, 0.22) 0 12px,
            rgba(255, 255, 255, 0.02) 12px 24px
          );
          mix-blend-mode: overlay;
          animation: moveStripe 1.2s linear infinite;
          opacity: 0.95;
        }
        .bar-border {
          position: absolute;
          top: 0;
          left: 0;
          right: -18px;
          bottom: 0;
          border-radius: 6px;
          box-shadow: inset 0 0 0 2px rgba(255, 255, 255, 0.03);
          pointer-events: none;
        }
      }
    }
    .right {
      min-width: 80px;
      text-align: right;
      font-size: 18px;
      color: #fff;
    }
  }
  .rank-pill {
    display: inline-flex;
    align-items: center;
    background: linear-gradient(90deg, #174559, #1e6b77);
    border-radius: 18px;
    padding: 2px 6px;
    color: #fff;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.4);
    .top-text {
      font-style: italic;
      font-size: 14px;
      opacity: 0.9;
      margin-right: 6px;
    }
    .rank-num {
      font-weight: 700;
      color: #ffd24a;
      font-size: 18px;
      margin-left: 2px;
    }
  }
}

@keyframes moveStripe {
  from {
    background-position: 0 0;
  }
  to {
    background-position: 40px 0;
  }
}
</style>
