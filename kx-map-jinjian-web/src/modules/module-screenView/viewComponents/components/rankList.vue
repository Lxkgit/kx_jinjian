<template>
  <div class="rank-list-wrapper"
       :style="{...styleOption, '--rowPadding': rowPadding, '--rowBorderRadius': rowBorderRadius}">
    <div class="list-head-wrapper" :style="{'--height':`${height}px`,background,color }">
      <span class="head-item" v-for="(item, index) in columns" :key="index"
            :style="{flex:item.flex, textAlign: item.align}">
        {{ item.label }}
      </span>
    </div>
    <div :class="className" class="list-content-wrapper">
      <div class="list-content" ref="listContent">
        <div class="list-item" v-for="(item,index) in tableData" :key="index"
             :style="{'--intervalColor': intervalColor,'--height':`${height}px`}">
          <span class="content-item"
                :style="{flex: el.flex,color:el.color, textAlign: el.align, fontFamily: el.fontFamily + '!important'}"
                v-for="(el,i) in columns" :key="i">
            <span v-if="!el.slot&&!el.number">
              {{ item[el.prop] }}
            </span>
            <span v-else-if="el.number" v-number-color="item[el.prop]"></span>
            <slot v-else :name="el.prop" :row="item" />
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    className: {
      type: String,
      required: true,
    },
    height: {
      type: Number,
      default: 36,
    },
    background: {
      type: String,
      default: 'linear-gradient( 180deg, rgba(29, 167, 231, 0.6) 0%, rgba(5, 74, 89, 0.6) 100%)'
    },
    color: {
      type: String,
      default: '#A8E3F2'
    },
    columns: {
      type: Array,
      default: () => [
        { label: '类型', prop: 'type', flex: 1, color: '#f00' },
        { label: '新增发放户数', prop: 'type1', flex: 1 },
        { label: '新增占比', prop: 'type2', flex: 1 },
        { label: '较年初', prop: 'type3', flex: 1 },
      ]
    },
    rows: {
      type: [Number, String],
      default: 3,
    },
    tableData: {
      type: Array,
      default: () => [
        { type: 123, type1: 231, type2: 342423, type3: 3424 },
        { type: 1231, type1: 231, type2: 342423, type3: 3424 },
        { type: 1231, type1: 231, type2: 342423, type3: 3424 },
        { type: 1231, type1: 231, type2: 342423, type3: 3424 },
        { type: 1231, type1: 231, type2: 342423, type3: 3424 },
        { type: 1231, type1: 231, type2: 342423, type3: 3424 },
      ]
    },
    intervalColor: {
      type: String,
      default: 'linear-gradient( 180deg, rgba(29, 167, 231, 0.2) 0%, rgba(29, 167, 231, 0.2) 100%)'
    },
    ifMove: {
      type: Boolean,
      default: false,
    },
    styleOption: {
      type: Object,
      default: () => ({})
    },
    rowPadding: {
      type: String,
      default: '0 12px'
    },
    rowBorderRadius: {
      type: String,
      default: '8px'
    },
  },
  data() {
    return {
      interval: null,
      scrollTop: 0,
    }
  },
  mounted() {
    let moveDom = document.getElementsByClassName(`${this.className}`)[0];
    if (moveDom && this.ifMove) {
      moveDom.addEventListener("mouseenter", () => {
        this.clear();
        moveDom.addEventListener("mouseleave", () => {
          this.clear();
          this.scrollTop = Math.floor(moveDom.scrollTop / this.height) * this.height; // 整数倍的滚动
          this.move();
        });
      });
    }
  },
  methods: {
    clear() {
      if (this.interval) {
        clearInterval(this.interval);
        this.interval = null;
      }
    },
    move() {
      let moveDom = document.getElementsByClassName(`${this.className}`)[0];
      if (moveDom) {
        this.interval = setInterval(() => {
          moveDom.scrollTop = this.scrollTop;
          this.scrollTop += this.height;
          if (
            moveDom.scrollTop + moveDom.clientHeight >=
            moveDom.scrollHeight
          ) {
            this.scrollTop = 0;
          }
        }, 2000);
      }
    },
  },
})
</script>

<style lang="scss" scoped>
* {
  font-size: 14px;
}
.rank-list-wrapper {
  padding: 8px;
  background: linear-gradient(
    0deg,
    rgba(17, 86, 111, 0.27),
    rgba(35, 55, 63, 0.04)
  );
  border-radius: 4px;
  border: 1px solid;
  opacity: 0.9;
  border-image: linear-gradient(180deg, #66cef500, #7ef4ff) 1 1;
  overflow: hidden;
  width: 100%;
  height: 100%;
  .list-head-wrapper {
    border-radius: var(--rowBorderRadius);
    display: flex;
    text-align: center;
    height: var(--height);
    line-height: var(--height);
    .head-item {
      padding: var(--rowPadding);
      white-space: nowrap;
    }
  }
  .list-content-wrapper {
    width: 100%;
    overflow-y: scroll;
    &::-webkit-scrollbar {
      width: 0;
    }
    .list-content {
      white-space: nowrap;
      .list-item:nth-child(2n) {
        background: var(--intervalColor);
      }
      .list-item {
        color: #fff;
        min-height: var(--height);
        line-height: var(--height);
        display: flex;
        text-align: center;
        align-items: center;
        .content-item {
          padding: var(--rowPadding);
        }
      }
    }
  }
}
</style>