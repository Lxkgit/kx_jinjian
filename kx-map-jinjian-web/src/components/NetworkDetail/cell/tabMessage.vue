<template>
  <div class="tabPane">
    <div v-for="(item, index) in dataInfo" :key="index" class="news">
      <div :class="`triangle${activeName}`"></div>
      <div class="newsInfo" @click="messageDetailsClick(item)">
        <div v-if="item.img" class="newsImg">
          <img
            :src="
              require(`@/modules/module-screenView/components/trends/mock/${item.img}`)
            "
            alt=""
          />
        </div>
        <div class="newsText">
          <div class="newsTitle">{{ item.title }}</div>
          <div class="newsTitleInfo">
            <div class="newsTime">发布时间：{{ item.time }}</div>
            <div class="newsDepartment">网点机构：{{ item.deptName }}</div>
          </div>
          <div class="textDisplay">{{ item.text[0] }}...</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    activeName: {
      type: String,Number,
      default: 1,
    },
    dataInfo: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
    messageDetailsClick(item) {
      this.$emit("messageDetailsClick", item);
    },
  },
};
</script>

<style scoped lang="scss">
.triangle1 {
  width: 25px;
  height: 25px;
  background-color: #ea9518;
  position: relative;
  clip-path: polygon(0% 0%, 100% 0%, 0% 100%);
  border-top-left-radius: 6px;
  position: absolute;
  top: 0;
  left: 0;
}
.triangle2 {
  width: 25px;
  height: 25px;
  background-color: #919190;
  position: relative;
  clip-path: polygon(0% 0%, 100% 0%, 0% 100%);
  border-top-left-radius: 6px;
  position: absolute;
  top: 0;
  left: 0;
}
.tabPane{
    height: 650px;
    overflow: auto;
}
.news {
  position: relative;
  background: #fbfbfa;
  box-shadow: 0px 1px 12px 1px rgba(0, 5, 8, 0.09);
  border-radius: 6px;
  border: 1px solid #eee;
  margin: 15px;
  padding: 8px;
  cursor: pointer;

  .newsInfo {
    display: flex;

    .newsImg {
      padding: 10px;

      img {
        width: 160px;
        height: 100px;
      }
    }

    .newsText {
      .newsTitle {
        font-weight: bold;
        font-size: 18px;
        color: #212121;
        line-height: 44px;
      }

      .newsTitleInfo {
        display: flex;

        .newsTime {
          font-weight: 400;
          font-size: 14px;
          color: #333333;
          line-height: 30px;
        }

        .newsDepartment {
          font-weight: 400;
          font-size: 14px;
          color: #333333;
          line-height: 30px;
          margin-left: 30px;
        }
      }

      .textDisplay {
        font-weight: 400;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        white-space: normal;
      }
    }
  }
}
</style>