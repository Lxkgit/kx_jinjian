<template>
  <div class="KeyPerson-echarts">
    <div class="KeyPerson-echarts-box">
      <div ref="pieCharts"></div>
      <ul>
        <li v-for="(item, index) in pieData" :key="item.name">
          <span>
            <span :style="{ backgroundColor: color[index] }"></span>{{ item.name }}</span>
          <span :style="{ color: color[index] }">{{ item.share }}</span>
          <span>{{ item.value }}</span>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default {
  props: {
    info: {
      type: Array,
      default: () => {
        return [
          { value: 23, name: "杭州市", share: "" },
          { value: 17, name: "宁波市", share: "" },
          { value: 52, name: "温州市", share: "" },
          { value: 26, name: "绍兴市", share: "" },
          { value: 13, name: "湖州市", share: "" },
          { value: 13, name: "嘉兴市", share: "" },
          { value: 2, name: "金华市", share: "" },
          { value: 3, name: "衢州市", share: "" },
          { value: 2, name: "舟山市", share: "" },
          { value: 2, name: "台州市", share: "" },
          { value: 1, name: "丽水市", share: "" },
        ];
      },
    },
  },
  data () {
    return {
      // 中心显示的第二行文案
      centerText: "村居数量",
      // 扩展色卡，大约30种颜色，供图例和图表使用
      color: [
        "#3FA1FB",
        "#1DC788",
        "#FFBE4E",
        "#F7696B",
        "#9B8CFF",
        "#FF7AB6",
        "#6EE7B7",
        "#4CC0FF",
        "#FFD36E",
        "#7BE0FF",
        "#A6FFCB",
        "#FFB38A",
        "#C6A9FF",
        "#FF98A3",
        "#8FD1FF",
        "#FFE8A1",
        "#D6F6FF",
        "#BFE7A7",
        "#F0A6FF",
        "#9EE7FF",
        "#FFDBB8",
        "#E6B3FF",
        "#B8FFD6",
        "#FFD1E0",
        "#CFE9FF",
        "#FFE0A8",
        "#D1FFD9",
        "#F6C7FF",
        "#C8F0FF",
        "#FFE7D1",
      ],
      _pieChart: null,
      _resizeHandler: null,
    };
  },
  computed: {
    totalSum () {
      return this.info.reduce((s, it) => s + Number(it.value), 0);
    },
    // 代表饼图的数据：固定五条标签，优先使用传入的前五条，不足时随机生成
    pieData () {
      const labels = [
        "非法集资",
        "网络传销",
        "金融诈骗",
        "违规借贷",
        "其他",
      ];
      const src = Array.isArray(this.info) ? this.info.slice(0, 5) : [];
      const items = [];
      const loopCount = src.length > 0 ? src.length : 5;
      for (let i = 0; i < loopCount; i++) {
        const s = src[i];
        if (s && s.value != null) {
          const value = Number(s.value) || 0;
          const name = s.name != null ? s.name : labels[i];
          const share = s.share != null && String(s.share) !== "" ? String(s.share) : "";
          items.push({ value, name, share });
        } else {
          // 随机生成一个 10-99 的值作为占比基数
          const value = Math.floor(Math.random() * 90) + 10;
          const name = labels[i];
          items.push({ value, name, share: "" });
        }
      }

      // 计算 share 字段为百分比字符串（如 45%），如果原始传入有值且为非空字符串则保留
      const total = items.reduce((s, it) => s + Number(it.value), 0) || 1;
      return items.map((it) => {
        const share = it.share && String(it.share).trim() !== "" ? String(it.share) : `${Math.round((Number(it.value) / total) * 100)}%`;
        return { value: Number(it.value), name: it.name, share };
      });
    },

  },
  mounted () {
    this.setEcharts();
  },
  beforeDestroy () {
    if (this._pieChart) {
      this._pieChart.dispose();
      this._pieChart = null;
    }
  },
  methods: {
    setEcharts () {
      if (this._pieChart) {
        this._pieChart.dispose();
        this._pieChart = null;
      }
      this._pieChart = echarts.init(this.$refs.pieCharts);
      // 使用 this.color 数组（hex）构建内外圈颜色
      const hexColors =
        this.color && this.color.length
          ? this.color
          : ["#3FA1FB", "#1DC788", "#FFBE4E", "#F7696B"];
      const colorListInner = [];
      const colorListOuter = [];

      // helper: convert hex to rgba string
      function hexToRgba (hex, alpha = 1) {
        if (!hex) return `rgba(0,0,0,${alpha})`;
        const h = hex.replace("#", "");
        const bigint = parseInt(
          h.length === 3
            ? h
              .split("")
              .map((c) => c + c)
              .join("")
            : h,
          16
        );
        const r = (bigint >> 16) & 255;
        const g = (bigint >> 8) & 255;
        const b = bigint & 255;
        return `rgba(${r}, ${g}, ${b}, ${alpha})`;
      }

      // 以间隔空位构建内外颜色列表（用于 valdata/outLinedata 插入 spacer）
      const maxEntries = Math.max(this.pieData.length, 1);
      for (let i = 0; i < maxEntries; i++) {
        const hex = hexColors[i % hexColors.length];
        colorListInner.push(hexToRgba(hex, 0.32));
        colorListInner.push("");
        colorListOuter.push(hex);
        colorListOuter.push("");
      }
      const sum = this.pieData.reduce((s, it) => s + Number(it.value), 0);
      // 增大间隔，避免外阴影或阴影扩散时相邻扇区连在一起
      const spacer = Math.max(sum * 0.02, sum / 200);

      const valdata = [];
      const outLinedata = [];
      this.pieData.forEach((item, idx) => {
        const innerColor =
          colorListInner[idx * 2] ||
          hexToRgba(hexColors[idx % hexColors.length], 0.32);
        const outerShadow = hexToRgba(hexColors[idx % hexColors.length], 0.98);
        valdata.push({
          value: item.value,
          name: item.name,
          itemStyle: {
            color: innerColor,
            shadowBlur: 12,
            shadowColor: outerShadow,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
          },
        });
        valdata.push({
          name: "",
          value: spacer,
          itemStyle: { color: "transparent" },
        });

        outLinedata.push({
          value: item.value,
          name: item.name,
          // 使用实色作为外圈，shadow 使用不透明颜色
          itemStyle: {
            color: colorListOuter[idx * 2] || hexColors[idx % hexColors.length],
            shadowBlur: 10,
            shadowColor: outerShadow,
            shadowOffsetX: 0,
            shadowOffsetY: 0,
          },
        });
        outLinedata.push({
          name: "",
          value: spacer,
          itemStyle: { color: "transparent" },
        });
      });

      const option = {
        legend: { show: false },
        // 中心两行文本通过 graphic 放置，便于不同大小样式控制
        // graphic: [
        //   {
        //     type: "group",
        //     left: "50%",
        //     top: "45%",
        //     bounding: "raw",
        //     children: [
        //       {
        //         type: "text",
        //         z: 10,
        //         style: {
        //           text: String(this.totalSum),
        //           fill: "#ffffff",
        //           font: "700 22px Arial",
        //           textAlign: "center",
        //         },
        //         // 相对 group 的位置
        //         x: 0,
        //         y: -12,
        //       },
        //       {
        //         type: "text",
        //         z: 10,
        //         style: {
        //           text: this.centerText,
        //           fill: "rgba(255,255,255,0.8)",
        //           font: "12px Arial",
        //           textAlign: "center",
        //         },
        //         x: 0,
        //         y: 14,
        //       },
        //     ],
        //   },
        // ],
        series: [
          {
            type: "pie",
            zlevel: 3,
            radius: ["48%", "75%"],
            center: ["50%", "50%"],
            label: { show: false },
            hoverAnimation: false,
            data: valdata,
          },
          {
            // 外圈：保持与内圈相同的角度分隔，但半透明/阴影更明显用于外部发光
            type: "pie",
            zlevel: 1,
            silent: true,
            radius: ["75%", "80%"],
            center: ["50%", "50%"],
            label: { show: false },
            hoverAnimation: false,
            data: outLinedata,
          },
        ],
      };
      this._pieChart.setOption(option);
    },
  },
  watch: {
    info: {
      handler () {
        // 数据变化时重绘图表和中心文案
        this.setEcharts();
      },
      deep: true,
    },
  },
};
</script>
<style lang="scss" scoped>
@mixin fontStyle($size, $weight, $color) {
  font-size: $size;
  font-weight: $weight;
  color: $color;
}

.KeyPerson {
  &-echarts {
    width: 430px;
    height: 240px;

    &-box {
      height: calc(100% - 20px);
      display: flex;
      justify-content: space-between;
      align-items: center;

      >div {
        width: 173px;
        height: 100%;
      }

      >ul {
        padding: 10px 0;
        display: flex;
        flex-flow: column nowrap;
        justify-content: center;
        align-items: flex-start;
        gap: 2px 10px;

        li {
          width: 100%;
          line-height: 20px;
          padding-left: 8px;
          border-radius: 13.12px;
          @include fontStyle(12px, 500, #fff);
          display: grid;
          grid-template-columns: 100px 40px 1fr;
          gap: 15px;

          >span {

            //圆点
            >span {
              vertical-align: middle;
              display: inline-block;
              width: 10px;
              height: 10px;
              border-radius: 50%;
              margin-right: 4px;
            }
          }
        }
      }
    }
  }
}
</style>
