<template>
  <div :style="{ height }" :class="{'bacBorder': border == true}">
    <div class="all-six" v-if="list && list.length <= 6" :style="{ height }">
      <div :style="{ height }" v-if="type == 1 " class="content" style="min-width:9.5rem">
        <div class="content-item"  v-for="(item, index) in listArr" :key="index">
            <i class="circle" :style="{ backgroundColor: item.color }"></i>
            <div class="text" :style="{ 'margin-left': textFeft,'color': textColor }">
               <el-tooltip :content="item.label" >
                <div class="textOver" :style="{ width: textWidth }">{{ item.label }}</div>
              </el-tooltip>
            </div>
            <div class="rate" :style="{ color: item.color }">
              {{ item.ratio }}
            </div>
        </div>
      </div>
      <div
        v-if="type == 2"
        class="content-else"
        :style="{ width: typeTwoWidth }"
        style="min-width:9.5rem"
      >
        <div
          
          :style="{marginBottom: bottom}"
          class="content-else-item"
          v-for="(item, index) in listArr"
          :key="index"
        >
          <i class="circle" :style="{ backgroundColor: item.color }"></i>
          <div class="text" :style="{ 'margin-left': textFeft ,'color': textColor }">
             <el-tooltip :content="item.label" placement="top">
                <div class="textOver" :style="{ width: textWidth }">{{ item.label }}</div>
              </el-tooltip>
          </div>
          <div class="rate" :style="{ color: item.color }">
            {{ item.ratio }}
          </div>
        </div>
      </div>
    </div>
    <div class="all" v-if="list && list.length > 6" :style="{ height }">
      <el-carousel  :autoplay="false" trigger="click" :height="height" style="min-width:9.5rem">
        <el-carousel-item
          class="content"
          v-for="(item, index) in listArr"
          :key="index"
        >
          <div class="content-item" style="min-width:9.5rem" v-for="(itemSon, i) in item" :key="i">
            <i class="circle" :style="{ backgroundColor: itemSon.color || 'white' }"></i>
            <div class="text" :style="{ 'margin-left': textFeft,'color': textColor  }">
              <el-tooltip :content="itemSon.label" placement="top">
                <div class="textOver" :style="{ width: textWidth }">{{ itemSon.label }}</div>
              </el-tooltip>
            </div>
            <div class="rate" :style="{ color: itemSon.color || 'white' }">
              {{ itemSon.ratio }}
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
export default {
  name: "showLegend",
  props: {
    border: {
      type: Boolean,
      default: false
    },
    textFeft: {
      type: String,
      default: "10px"
    },
    type: {
      type: [Number, String],
      default: 1 // 1 众向排布   2 横向排布
    },
    height: {
      type: String,
      default: "120px"
    },
    textWidth: {
      type: String,
      default: "100px"
    },
    typeTwoWidth: {
      type: String,
      default: "300px"
    },
    bottom: {
      // 当为众向排布时，需要间距
      type: String,
      default: ""
    },
    textColor: {
      type: String,
      // default: "rgba(255, 255, 255, 0.8)"
      default: "rgba(255, 255, 255)"
    },
    list: {
      type: Array,
      default: () => [
        // {
        //   color: "#66D4E6",
        //   label: "纯活期",
        //   ratio: 0.56
        // },
        // {
        //   color: "#14E7A0",
        //   label: "常惠存2.0",
        //   ratio: 0.43
        // },
        // {
        //   color: "#15C3E8",
        //   label: "结构性存款",
        //   ratio: 0.36
        // },
        // {
        //   color: "#14E8BF",
        //   label: "三年期常惠存",
        //   ratio: 0.12
        // },
        // {
        //   color: "#DAB54C",
        //   label: "一年期常惠存",
        //   ratio: 0.36
        // },
        // {
        //   color: "#FC924C",
        //   label: "剩余产品",
        //   ratio: 0.12
        // }
      ]
    }
  },
  data() {
    return {
      listArr: null, // 可循环的数组
      colorArr: [
        "#66D4E6",
        "#14E7A0",
        "#15C3E8",
        "#14E8BF",
        "#DAB54C",
        "#FC924C",
      ]
    };
  },
  watch: {
    list: {
      handler(newV, oldV) {
        if (newV) {
          this.listArr = this.getList();
        } else {
          this.listArr = [];
        }
      },
      deep: true
      // immediate: true
    }
  },
  created() {
    this.listArr = this.getList();
  },
  methods: {
    getList() {
      if (this.list && this.list.length > 0) {
        if (this.list.length <= 6) {
          const arr = this.list.map((item, index) => {
            return {
              ratio: parseInt(parseFloat(item.ratio) * 100) + "%",
              color: this.colorArr[index],
              label: item.label
            };
          });
          return arr;
        } else {
          let arr = [];
          for (let i = 0; i < this.list.length / 6; i++) {
            this.$set(arr, i, []);
          }
          for (let j = 0; j < arr.length; j++) {
            for (let m = 0; m < this.list.length; m++) {
              if (m < 6 * (j + 1) && m >= 6 * j) {
                arr[j].push(this.list[m]);
              }
            }
          }
          arr.forEach(v => {
            v.color = v.itemS
          })
          return arr;
        }
      }
    }
  }
};
</script>

<style lang="scss" scoped>
@font-face {
  font-family: adobeheitistd;
  // src: url('../../../assets/adobeheitistd-regular.otf');
}
.all-six {
  .content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    color: "#66D4E6"; 
    &-item {
      display: flex;
      align-items: center;
      min-width: 9.5rem;
    }
    .circle {
      width: 4.5px;
      height: 4.5px;
      border-radius: 50%;
    }
    .text {
      flex: 1;
      font-size: 14px;
      font-family: adobeheitistd;
      // font-size: 12px;
      color: rgba(255, 255, 255, 0.8);
      .textOver {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
    .rate {
      font-size: 14px;
      // font-size: 12px;
      // scale: 0.9;
    }
  }
}

.content-else {
  margin-top: 30px;
  display: flex;
  flex-wrap: wrap;
  // justify-content: space-between;
  &-item {
    display: flex;
    align-items: center;
    width: 50%;
    .circle {
      width: 4.5px;
      height: 4.5px;
      border-radius: 50%;
    }
    .text {
      font-size: 14px;
      // font-size: 12px;
      // color: rgba(255, 255, 255, 0.8);
      .textOver {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
    .rate {
      //old
      // font-size: 12px;
      // scale: 0.9;
      font-size: 14px;
    }
  }
}

.bacBorder {
  box-sizing: content-box;
  border-radius: 5px;
  padding: 10px;
  border: 1px solid rgba($color: #bdbdbd, $alpha: 0.6);
  background-color: rgba($color: #f1f5fb, $alpha: 0.8);
}
.all {
  ::v-deep .is-active {
    transition: none !important;
  }
  ::v-deep .el-carousel__arrow{
    transition: none !important;
  }
  ::v-deep .is-animating {
    transition: none !important;
  }
  ::v-deep .el-carousel{
    transition: none !important;
  }
  ::v-deep .el-carousel__item{
    transition: none !important;
  }
  ::v-deep .el-carousel__indicators{
    display: none !important;
  }
  ::v-deep .el-carousel__container{
    transition: none !important;
  }
  .content {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    color: "#66D4E6";
    &-item {
      display: flex;
      align-items: center;
      min-width: 9.5rem;
    }
    .circle {
      width: 4.5px;
      height: 4.5px;
      border-radius: 50%;
    }
    .text {
      flex: 1;
      font-size: 14px;
      font-family: adobeheitistd;
      // font-size: 12px;
      color: rgba(255, 255, 255, 0.8);
      .textOver {
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
    .rate {
      font-size: 14px;
      // font-size: 12px;
      // scale: 0.9;
    }
  }
}
</style>