<template>
  <div class="outside-info-wrapper">
    <div class="top-box">
      <div class="img-box">
        <img style="width: 30px; height: 30px" src="../assets/compImg/home.png" />
        <div>{{ outInfo.entName }}</div>
      </div>

      <div class="detail-box">
        <div class="one">
          <div>
            <span class="fs label label-r">经营者：</span>
            <span class="fs content content-r">{{ outInfo.person }}</span>
          </div>
          <div>
            <span class="fs label label-r">成立日期：</span>
            <span class="fs content content-r">{{
                    outInfo.startDate
                  }}</span>
          </div>
        </div>
        <div>
          <span class="fs label label-r">经营地址：</span>
          <span class="fs content content-r">{{ outInfo.address }}</span>
        </div>
      </div>
      <div class="line"></div>

      <pop-title class="margin-top-total" text="基本信息" fontSize="15px" color="#4ccbcc"></pop-title>
      <div class="info-list">
        <div class="info-item" :class="{ address: item.name === '经营范围' }"
             v-for="(item, index) in outList" :key="index">
          <div class="item item-l">{{ item.name }}</div>
          <div class="item item-r">{{ item.content }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import popTitle from "../components/popTitle.vue";

import mixin from '../mixins/mixin'
export default ({
  components: {
    popTitle,
  },
  mixins: [mixin],
  mounted() {
    this.init();
  },
})
</script>

<style lang="scss" scoped>
.address {
  margin-bottom: 10px;
}
.fs {
  line-height: 30px;
}
.label {
  color: #333;
  font-size: 14px;
}
.content {
  font-size: 14px;
  font-weight: bold;
}
.label-r,
.content-r {
  font-size: 16px;
}
.info-box {
  padding: 0 20px;

  .line {
    width: 100%;
    height: 2px;
    background: #030d10;
    opacity: 0.05;
    border-radius: 1px;
  }
}
.top-box {
  // display: flex;
  // align-items: flex-start;
  .img-box {
    display: flex;
    align-items: center;
    color: #4ccbcd;
    font-size: 20px;
    font-weight: bold;
    img {
      margin: 15px 20px;
    }
  }
  .detail-box {
    margin-left: 70px;
    .one {
      display: flex;
      align-items: center;
      div {
        margin-right: 60px;
      }
    }
  }
}
.info-list {
  display: flex;
  flex-wrap: wrap;
  .info-item {
    width: 50%;
    display: flex;
    font-size: 15px;
    .item {
      width: 50%;
      padding: 10px;
      text-align: center;
      border: 1px solid #f6f6f6;
      box-sizing: border-box;
    }
    .item-l {
      width: 40%;
    }
    .item-r {
      width: 60%;
    }
    .item:nth-child(odd) {
      background-color: #f6f6f6;
    }
  }
}
.address {
  width: 100% !important;
  .item:nth-child(1) {
    width: 20%;
  }
  .item:nth-child(2) {
    width: 78%;
  }
}
</style>