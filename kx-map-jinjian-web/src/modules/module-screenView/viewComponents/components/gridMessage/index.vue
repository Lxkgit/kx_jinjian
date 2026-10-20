<template>
  <div class="gridBox">
    <div class="section">
      <div class="left">
        <div class="icon"></div>
      </div>
      <div class="right">
        <div class="type">
          <h2>网格类型</h2>
          <p>{{ info.gridType }}</p>
        </div>
        <div class="type name">
          <h2>网格名称:</h2>
          <p>{{ info.gridName }}</p>
        </div>
      </div>
    </div>
    <div class="section2 yellow">
      <p> <i class="icon"></i>网格员: <span class="highlight">{{ info.bankLeader }}</span></p>
      <p class="link">联系方式: <span class="highlight">{{ info.leaderPhone }}</span></p>
    </div>
    <!-- <div class="section2">
      <p><i class="icon icon2"></i>银行网格员: <span class="highlight">{{ info.gridStaff }}</span> </p>
      <p class="link">联系方式: <span class="highlight">{{ info.staffPhone }}</span></p>
    </div> -->
    <!-- <div class="section3">
      <p>商户数量</p>
      <p class="small"> <span class="blue">{{ info.merchantsNum }}</span>家</p>
    </div>
    <div class="section4">
      <div>人民币知识宣传次数<span class="highlight">{{ info.knowledgeTimes }}次</span></div>
      <div>人民币现金服务次数<span class="highlight">{{ info.cashServiceTimes }}次</span></div>
    </div> -->
  </div>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      default: () => ({
        gridType: '社区',
        gridName: '泉源社区',
        bankLeader: '章琳',
        leaderPhone: '13566769915',
        gridStaff: '张章',
        staffPhone: '13566769915',
        merchantsNum: '-',
        knowledgeTimes: "-",
        cashServiceTimes: "-",
      }),
    },
  },
};
</script>

<style scoped lang="scss">
.gridBox {
  width: 100%;
  margin-bottom: 20px;

  .section {
    display: flex;
    align-items: center;
    width: 100%;
    height: 74px;
    background: url('../../assets/gridSection1.png');
    background-size: 100% 100%;
    margin-bottom: 10px;

    .left {
      width: 68px;
      height: 49px;
      margin-left: 12px;

      .icon {
        width: 100%;
        height: 100%;
        background: url('../../assets/gridSectionIcon1.png');
        background-size: 100% 100%;
      }
    }

    .right {
      display: flex;
      justify-content: space-around;
      flex: 1;

      .type {
        display: flex;
        flex-direction: column;
        text-align: left;
        width: 100px;

        h2 {
          font-size: 16px;
          color: #fff;
          margin-bottom: 10px;
        }

        p {
          font-size: 20px;
          color: #00C2FF;

        }

        &.name {
          p {
            color: #FFDB6C;
          }
        }
      }
    }
  }

  .section2 {
    width: 100%;
    height: 56px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: url('../../assets/gridSection2.png');
    background-size: 100% 100%;
    padding-left: 80px;
    padding-right: 20px;
    margin-bottom: 4px;

    &.yellow {
      background: url('../../assets/gridSection2-1.png');
      background-size: 100% 100%;
    }

    p {
      color: #fff;
      font-size: 16px;

      &.link {
        font-size: 14px;
        color: rgba(255, 255, 255, 0.85);
      }
    }
  }

  .section3 {
    margin: 10px 0;
    height: 72px;
    display: flex;
    width: 100%;
    background: url('../../assets/gridSection3.png');
    background-size: 100% 100%;
    padding-left: 100px;
    padding-right: 20px;
    justify-content: space-between;
    align-items: center;

    p {
      color: #fff;
      font-size: 18px;

      &.small {
        font-size: 16px;
      }

      .blue {
        background: linear-gradient(180deg, #FFFFFF 0%, #FFFFFF 24%, #00D5FF 96%, #00FFFF 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        font-size: 24px;

      }
    }

  }

  .section4 {
    width: 100%;
    height: 36px;
    display: flex;
    justify-content: space-between;

    div {
      display: flex;
      justify-content: space-around;
      align-items: center;
      width: 210px;
      height: 36px;
      font-size: 12px;
      background: url('../../assets/gridSection4.png');
      background-size: 100% 100%;
      color: #fff;
    }
  }

  .highlight {
    font-size: 16px;
    color: #01EAFF;
    font-weight: bold;
  }
}
</style>
