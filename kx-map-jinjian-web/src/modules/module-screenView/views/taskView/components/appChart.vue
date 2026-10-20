<template>
  <div :style="{ width, height }">
    <div
      :id="id"
      style="height: 100%; width: 100%" />
  </div>
</template>

<script>
import * as echarts from 'echarts'
export default {
  name: 'AppChart',
  props: {
    option: {
      type: Object,
      default: () => {
        return {}
      },
    },
    width: {
      type: String,
      default: '100%',
    },
    height: {
      type: String,
      default: '100%',
    },
    id: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      myChart: null,
    }
  },
  watch: {
    option: {
      handler() {
        if (this.myChart) {
          this.$nextTick(() => this.resize())
          this.myChart.setOption(this.option, true)
        }
      },
      deep: true,
    },
    height: {
      handler(newV) {
        if (newV) {
          this.$nextTick(() => this.resize())
        }
      },
    },
  },
  mounted() {
    this.$nextTick(() => {
      this.myChart = echarts.init(document.getElementById(`${this.id}`))
      this.myChart.setOption(this.option)
      this.myChart.on('click', (params) => {
        this.$emit('click', params)
      })
    })
    window.addEventListener('resize', () => {
      this.resize()
    })
  },
  methods: {
    resize() {
      this.myChart.resize()
    },
  },
}
</script>

<style lang="scss" scoped>
</style>