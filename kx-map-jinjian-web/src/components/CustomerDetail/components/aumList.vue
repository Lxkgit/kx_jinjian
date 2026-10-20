<template>
  <div class="aum-title-list">
    <div class="aum-title">
      <div
        class="aum-title-item"
        v-for="(item, index) in titleList"
        :key="index"
      >
        {{ item }}
      </div>
    </div>
    <div class="aum-content">
      <div
        class="aum-content-item"
        v-for="(item, index) in aumList"
        :key="index"
      >
        <div class="item-text itme-flex">{{ item.text }}</div>
        <div class="item-deposit itme-flex" :style="item.style">
          {{ item.deposit }}
        </div>
        <div class="item-monthIncValue itme-flex">
          <div
            :style="{
              color:
                parseFloat(item.monthIncNature) > 0 ? '#00A7F4' : '#FF5B00',
            }"
          >
            {{ item.monthIncValue }}
          </div>
          <div
            v-show="parseFloat(item.monthIncNature) > 0"
            class="icon-type up-icon icon-left"
          ></div>
          <div
            v-show="parseFloat(item.monthIncNature) < 0"
            class="icon-type down-icon icon-left"
          ></div>
        </div>
        <div class="item-yearIncValue itme-flex">
          <div
            :style="{
              color: parseFloat(item.yearIncNature) > 0 ? '#00A7F4' : '#FF5B00',
            }"
          >
            {{ item.yearIncValue }}
          </div>
          <div
            v-show="parseFloat(item.yearIncNature) > 0"
            class="icon-type up-icon icon-left"
          ></div>
          <div
            v-show="parseFloat(item.yearIncNature) < 0"
            class="icon-type down-icon icon-left"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // 组件名称
  name: "aumList",
  // 组件参数 接收来自父组件的数据
  props: {
    aumList: Array,
  },
  // 局部注册的组件
  components: {},
  // 组件状态值
  computed: {},
  // 侦听器
  watch: {
    aumList: {
      handler(e) {
        // console.log('aumList', e);
      },
      immediate: true,
      deep: true,
    },
  },
  data() {
    return {
      titleList: ["类型", "金额", "较上月", "较年初"],
      textList: [
        "楼宇AUM总值",
        "楼宇总存款",
        "楼宇总理财",
        "楼宇总授信",
        "楼宇总用信",
      ],
      contentList: [
        {
          text: "家庭AUM总值",
          deposit: "464646", // 金额
          monthIncNature: 1, // 较上月的正负值
          monthIncValue: "45%",
          yearIncNature: 1, // 较年初的正负值
          yearIncValue: "54%",
        },
        {
          text: "家庭AUM总值",
          deposit: "464646", // 金额
          monthIncNature: 1, // 较上月的正负值
          monthIncValue: "45%",
          yearIncNature: 1, // 较年初的正负值
          yearIncValue: "54%",
        },
        {
          text: "家庭AUM总值",
          deposit: "464646", // 金额
          monthIncNature: 1, // 较上月的正负值
          monthIncValue: "45%",
          yearIncNature: 1, // 较年初的正负值
          yearIncValue: "54%",
        },
        {
          text: "家庭AUM总值",
          deposit: "464646", // 金额
          monthIncNature: 1, // 较上月的正负值
          monthIncValue: "45%",
          yearIncNature: 1, // 较年初的正负值
          yearIncValue: "54%",
        },
        {
          text: "家庭AUM总值",
          deposit: "464646", // 金额
          monthIncNature: 1, // 较上月的正负值
          monthIncValue: "45%",
          yearIncNature: 1, // 较年初的正负值
          yearIncValue: "54%",
        },
      ],
    };
  },
  // 计算属性
  created() {},
  mounted() {},
  // 组件方法
  methods: {},
};
</script> 

<style lang='scss' scoped>
.aum-title-list {
  .aum-title {
    background-color: #f0e5f5;
    display: flex;
    color: #666666;
    .aum-title-item {
      height: 40px;
      line-height: 40px;
      font-size: 15px;
      flex: 1;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
  .aum-content {
    .aum-content-item {
      display: flex;
      .itme-flex {
        height: 40px;
        line-height: 40px;
        flex: 1;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #00a7f4;
      }
      .item-text {
        font-size: 14px;
        font-weight: bold;
        color: #333333;
      }
      .item-deposit {
        // font-size: 14px;
        color: #00a7f4; 
      }
    }
    .aum-content-item:nth-child(2n) {
      background-color: rgba($color: #f0e5f5, $alpha: 0.6);
    }
  }
}
.icon-type {
  width: 6px;
  height: 16px;
  background-size: 100% 100%;
}
.up-icon {
  background-image: url("../assets/compImg/iconType2.png");
}
.down-icon {
  background-image: url("../assets/compImg/iconType4.png");
}
.icon-left {
  margin-left: 10px;
}
</style>