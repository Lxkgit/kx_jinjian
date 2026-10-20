<template>
  <div :class='latticeData.type'>
    <div class="lattice-box">
      <div class="lattice-num">
        <div v-for="(item, index) in totalValue" :key="index" class="num-item"
          :class="computedTextClass(item) ? 'text-class' : 'num-class'">
          {{ item }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    latticeData: {
      type: Object,
      default: () => {
        return {
          visitorNum: '123456',
          afterUnit: '次',
          type: 'blue'
        }
      }
    },

    //单个矩阵图片
    latticeBgc: {
      type: String,
      default: require('../assets/blue-box.png')
    },

  },
  data() {
    return {
      numMaxLength: 9,
      // numData: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.', '¥', '￥']
      numData: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
    }
  },

  computed: {
    numberValue() {
      console.log('visitorNum:', this.latticeData.visitorNum)
      let t = this.latticeData.visitorNum + ''
      let l = t.length
      if (l <= 9) {
        for (let i = 0; i < (this.numMaxLength - l); i++) {
          t = '0'.concat(t)
        }
      }
      return this.addThousandSeparator(t)
    },
    totalValue() {
      return this.numberValue + this.latticeData?.afterUnit
    }
  },
  methods: {
    addThousandSeparator(num, separator = ",") {
      return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, separator);
    },
    computedTextClass(item) {
      if (this.numData.indexOf(item) == -1) return true
      else return false
    }
  }
};
</script>

<style scoped lang="scss">
.lattice-box {
  padding-bottom: 10px;
}

.lattice-title {
  display: flex;
  justify-content: space-between;
  position: relative;

  margin-bottom: 13px;

  .left {
    font-family: 'PuHuiTi-Regular';
    font-weight: 400;
    font-size: 16px;
    color: #ffffff;
  }

  .right {
    display: flex;
    align-items: flex-end;
    margin-right: 6px;
    cursor: pointer;
    z-index: 10;

    .right-title {
      font-family: 'PuHuiTi-Regular';
      font-weight: 400;
      font-size: 12px;
      color: #929ba1;
      margin-right: 2px;
    }

    .right-value {
      margin-left: 1px;
      font-family: 'DIN-Medium';
      font-weight: 500;
      font-size: 16px;
      color: #ff4835;
    }
  }

}

.lattice-num {
  width: 100%;
  height: 52px;
  line-height: 52px;
  display: flex;
  align-items: center;
  justify-content: center;

  .num-item {
    width: 40px;
    height: 52px;
    text-align: center;
    color: #00FFFF;
    margin-left: 4px;
    background: url(../assets/blue-box.png);
    background-size: 100% 100% !important;
  }

  .text-class {
    font-family: 'PuHuiTi-Medium';
    font-weight: 500;
    width: 30px;

    font-size: 24px;
    background: none;
  }

  .num-class {
    font-family: 'DIN-Medium';
    font-weight: 500;
    font-size: 44px;
  }

  .num-item:first-child {
    margin-left: 0;
  }
}

.yellow {
  .lattice-num {
    .num-item {
      color: #FFF088;
      background: url(../assets/yellow-box.png);
      background-size: 100% 100% !important;
    }

    .text-class {

      background: none;
    }
  }
}

.dblue {
  .lattice-num {
    .num-item {
      color: #0091FF;
      background: url(../assets/dblue-box.png);
      background-size: 100% 100% !important;
    }

    .text-class {

      background: none;
    }
  }
}

.green {
  .lattice-num {
    .num-item {
      color: #00FFCF;
      background: url(../assets/green-box.png);
      background-size: 100% 100% !important;
    }

    .text-class {
      background: none;
    }
  }
}
</style>