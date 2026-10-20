<template>
  <div
    class="item-box"
    :style="{ '--gridColumns': `repeat(${gridColumns},1fr)`,'--gridRows': `${gridRows}` }"
  >
    <div
      class="item"
      :style="{'--background': item.background ? item.background : '#fff',height: height + 'px' }"
      v-for="(item, index) in imgList"
      :key="index"
    >
      <img :src="item.img" alt="" class="imgIcon" />
      <div class="conetent">
        <div class="title">{{ item.name }}</div>
        <div class="num">{{ formatNumber(dataList[item.key]) }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dataList: {
      type: Object,
      default: () => ({
        key1: 12434,
        key2: 45334,
        key3: 24334,
        key4: 1245334,
        key5: 82434,
        key6: 34,
        key7: 2434,
        key8: 1234,
        key9: 12434,
        key10: 2434,
        key11: 1434,
        key12: 2434,
      }),
    },
    imgList: {
      type: Array,
      default: () => [
        {
          name: "网格总数",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg1.png"),
          key: "key1",
        },
        {
          name: "社区网格数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg2.png"),
          key: "key2",
        },
        {
          name: "行政村网格数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg3.png"),
          key: "key3",
        },
        {
          name: "责任区网格数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg4.png"),
          key: "key4",
        },
        {
          name: "网格责任银行网点数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg5.png"),
          key: "key5",
        },
        {
          name: "已对接基层社区(行政村)",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg6.png"),
          key: "key6",
        },
        {
          name: "网格专兼职工作人员数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg7.png"),
          key: "key7",
        },
        {
          name: "现金收付线索摸排次数",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg8.png"),
          key: "key8",
        },
        {
          name: "网格内开展现金服务次数",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg9.png"),
          key: "key9",
        },
        {
          name: "网格内宣传次数",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg10.png"),
          key: "key10",
        },
        {
          name: "宣传受众人数",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg11.png"),
          key: "key11",
        },
        {
          name: "发现拒收现金线索数量",
          img: require("@/modules/module-gridManagement/assets/images/tongjiImg12.png"),
          key: "key12",
        },
      ],
    },
    width: {
      type: String,
      default: "258",
    },
    height: {
      type: String,
      default: "92",
    },
    gridColumns: {
      type: Number,
      default: 6,
    },
    gridRows: {
      type: String,
      default: '1fr 1fr',
    },
  },
  data() {
    return {};
  },
  methods: {
    formatNumber(num) {
      if (num < 1000) return num.toString();
      return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>
<style lang="scss" scoped>
.item-box {
  width: 100%;
  display: grid;
  grid-template-columns: var(--gridColumns);
  grid-template-rows: var(--gridRows);
  gap: 12px;
  .item {
    display: flex;
    padding: 16px 0 16px 16px;
    border-radius: 8px;
    background: var(--background);
    border: 1px solid #ebeef5;
    .imgIcon {
      width: 60px;
      height: 60px;
      margin-right: 12px;
    }
    .conetent {
      display:flex;
      flex-direction: column;
      justify-content: space-around;
    }
    .title {
      font-size: 14px;
      line-height: 20px;
      color: #666666;
      // margin-bottom: 8px;
    }
    .num {
      font-size: 22px;
      font-weight: 500;
      line-height: 32px;
      color: #333333;
    }
  }
}
</style>
