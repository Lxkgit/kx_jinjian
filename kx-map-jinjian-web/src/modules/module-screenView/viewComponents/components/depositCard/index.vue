<template>
  <info-box class="cunkuan">
    <div class="header">
      <div class="header-count">{{ dataItem.name }}</div>
      <!-- src="../../assets/save-1.png" -->
      <img
        class="image"
        :src="require(`../../assets/save-${dataItem.imgUrl}.png`)"
        alt=""
      />
      <div class="header-title">{{ dataItem.label }}</div>
    </div>
    <div class="last-month">
      <div class="top">
        <!-- <div class="number" v-number-color="dataItem.lastMonth"></div> -->
        <div class="number" :title="dataItem.girdTypeValue">
          {{ dataItem.girdTypeValue }}
        </div>
      </div>
      <div class="footer">
        <div class="title">{{ dataItem.girdType }}</div>
      </div>
    </div>
    <!-- <div class="early">
      <div class="top">
        <div class="number" v-number-color="dataItem.beginYear"></div>
      </div>
      <div class="footer">
        <div class="title">较年初</div>
      </div>
    </div> -->
  </info-box>
</template>

<script>
import infoBox from "../infoBox.vue";
export default {
  name: "myCunKuan",
  props: {
    dataItem: {
      type: Object,
      default: () => {
        return {
          allNum: "1343253",
          label: "存款余额",
          lastMonth: "2424",
          beginYear: "-42144",
          imgUrl: "1",
        };
      },
    },
  },
  components: { infoBox },
};
</script>

<style scoped lang="scss">
.cunkuan {
  height: 250px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  padding: 20px 0;
  // background: linear-gradient(
  //   0deg,
  //   rgba(53, 78, 88, 0.5),
  //   rgba(35, 55, 63, 0.08)
  // );
  // border: 1px solid;
  // opacity: 0.9;
  // border-image: linear-gradient(180deg, #66cef5, #66cef5, #7ef4ff) 1 1;
}
.header {
  height: 55%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 115px;
}
.header-count {
  font-family: Number;
  font-weight: bold;
  font-size: 22px;
  color: rgb(62, 203, 255);
  // background: linear-gradient(
  //   0deg,
  //   rgb(62, 203, 255) 0%,
  //   rgb(222, 222, 222) 100%
  // );
  // background-clip: text;
  // -webkit-background-clip: text;
  // -webkit-text-fill-color: transparent;
}
.image {
  margin: 10px 0;
  width: 68px;
  height: 43px;
}
.header-title {
  font-size: 14px;
  color: #9dbec1;
}
.top {
  display: flex;
  justify-content: center;
}
.number {
  margin: 10px 0;
  margin-left: 4px;
  font-size: 18px;
  color: #8bf6ff;
  font-family: Number;
  word-break: break-word;
  overflow: hidden; /* 隐藏超出部分 */
  text-overflow: ellipsis; /* 超出部分显示省略号 */
  display: -webkit-box; /* 必须设置为 flex 或 -webkit-box */
  -webkit-box-orient: vertical; /* 垂直排列文本 */
  -webkit-line-clamp: 2; /* 显示三行，超出部分省略 */
}
.footer {
  display: flex;
  justify-content: center;
  align-items: center;
}
.title {
  font-size: 14px;
  color: #9dbec1;
}
.last-month,
.early {
  margin: 5px 0;
}
</style>
