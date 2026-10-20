<template>
  <div>
    <div class="product">
      <div v-for="(item, index) in imgList" :key="index" class="saving">
        < img :src="item.path" class="Img" >
        <div :class="{'textColor': item.flag}" class="text">{{ item.text }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'custProduct',
  props: {
    list: {
      type: Array,
      default: () => {
        return [false, false, true, false, true, false, false, false, false]
      }
    }
  },
  data() {
    return {
      imgList: [
        {
          path: null,
          text: '常惠存'
        },
        {
          path: null,
          text: '大额定期存单'
        },
        {
          path: null,
          text: '整存整取'
        },
        {
          path: null,
          text: '保本理财'
        },
        {
          path: null,
          text: '非保本理财'
        },
        {
          path: null,
          text: '信用卡标卡'
        },
        {
          path: null,
          text: '信用卡资产类'
        },
        {
          path: null,
          text: '手机银行'
        }
      ],
      listBoolean: [false, false, false, false, false, false, false, false, false]
    }
  },
  watch: {
    list: {
      handler(newVal) {
        if (newVal) {
          this.listBoolean = newVal
          this.getImgs()
        } else {
          this.getImgs()
        }
      },
      immediate: true,
      deep: true
    }
  },
  created() {
    this.getImgs()
  },
  methods: {
    getImgs() {
      for (let i = 0; i < this.imgList.length; i++) {
        if (this.listBoolean[i]) {
          this.imgList[i].path = 'static/img/assetIcon/' + (i + 1) + '.png'
          this.imgList[i].flag = true
        } else {
          this.imgList[i].path = 'static/img/assetIcon/' + (i + 11) + '.png'
          this.imgList[i].flag = false
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.product {
  display: flex;
  color: #000000;
  font-weight: 600;
  .saving,
  .paying,
  .wallet {
    width: 13%;
    display: block;
    text-align: center;
  }
  .Img {
    width: 50px;
    height: 50px;
  }
  .text {
    margin-top: 10px;
    font-weight: normal;
    font-size: 14px;
    color: #bababa;
  }
  .textColor {
    color: #000000;
  }
}
</style>