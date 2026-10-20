<template>
  <div class="count-to">
    <slot :num="formattedValue"></slot>
  </div>
</template>
<script>
export default {
  name: 'CountTo',
  props: {
    value: {
      type: Number,
      default: 0,
    },
    duration: {
      type: Number,
      default: 2000,
    },
    useGrouping: {
      type: Boolean,
      default: true,
    },
    decimals: {
      type: Number,
      default: 0,
    },
  },
  data () {
    return {
      currentValue: 0,
      animationFrameId: null,
    };
  },
  computed: {
    formattedValue () {
      return Number(this.currentValue).toLocaleString(undefined, {
        minimumFractionDigits: this.decimals,
        maximumFractionDigits: this.decimals,
        useGrouping: this.useGrouping,
      });
    },
  },
  methods: {
    // 缓动函数（easeOutQuad）
    easeOut (t, b, c, d) {
      t /= d;
      return -c * t * (t - 2) + b;
    },
    // 执行动画
    animate (startValue, endValue, duration) {
      const vm = this;
      const startTime = Date.now();
      const valueDiff = endValue - startValue;
      function update () {
        const elapsed = Date.now() - startTime;
        if (elapsed >= duration) {
          vm.currentValue = endValue;
          vm.animationFrameId = null;
          return;
        }
        vm.currentValue = vm.easeOut(elapsed, startValue, valueDiff, duration);
        vm.animationFrameId = requestAnimationFrame(update);
      }
      update();
    },
  },
  watch: {
    value: {
      handler (newVal) {
        if (this.animationFrameId) {
          cancelAnimationFrame(this.animationFrameId);
          this.animationFrameId = null;
        }
        this.animate(Number(this.currentValue) || 0, Number(newVal) || 0, this.duration);
      },
      immediate: true,
    },
  },
  beforeDestroy () {
    if (this.animationFrameId) {
      cancelAnimationFrame(this.animationFrameId);
      this.animationFrameId = null;
    }
  },
};
</script>
