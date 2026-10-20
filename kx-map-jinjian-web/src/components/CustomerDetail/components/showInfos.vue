<template>
  <div class="show-infos" :style="{ width, height }">
    <div :style="{ width:itemWidth?itemWidth:item.width?item.width:(!item.type || item.type == 1 ? '50%' : '100%' )}"
         class="show-infos-item" v-for="(item, index) in list" :key="index">
      <div class="show-infos-one" v-if="!item.type || item.type == 1">
        <div class="show-text" :style="[textStyle]">{{ item.name }}:</div>
        <span class="show-value" :style="[textValueStyle]">{{
         item.value || dataValue[item.prop]
        }}</span>
      </div>
      <div class="show-infos-one show-infos-tag" v-if="item.type == 2">
        <div class="show-text" :style="[textStyle]">{{ item.name }}:</div>
        <div v-if="dataValue && dataValue[item.prop]" class="show-tag-value">
          <div class="tag-list" v-for="(card, i) in dataValue[item.prop]" :key="i">
            <el-tag style='height:28px;line-height:28px'>{{ card }}</el-tag>
          </div>
        </div>
      </div>
      <div class="show-infos-one show-infos-img" v-if="item.type == 3">
        <div class="show-text" :style="[textStyle]">{{ item.name }}:</div>
        <div v-if="dataValue && dataValue[item.prop]" class="show-img-value">
          <div :style="{
              backgroundImage: `url(${img})`,
              width: imgStyle.width,
              height: imgStyle.height,
            }" class="img-list" v-for="(img, i) in dataValue[item.prop]" :key="i">
            <div class="img-item"></div>
          </div>
        </div>
      </div>
      <!-- 代发/商户跳转 -->
      <div class="show-infos-one" v-if="item.type == 4">
        <div class="show-text" :style="[textStyle]">{{ item.name }}:</div>
        <span v-if="dataValue &&  dataValue[item.prop] && dataValue[item.prop].length"
              @click="$emit('jump',dataValue[item.prop][0],item.key)" class="jump-class">{{
          dataValue[item.prop][0][item.key]
        }}</span>
        <el-popover v-if="dataValue && dataValue[item.prop]&&dataValue[item.prop].length>1"
                    placement="right" width="300" :close-delay='800' trigger="hover">
          <el-tag size="mini" class="tag-class" v-for="(i,index) in dataValue[item.prop]"
                  @click="$emit('jump',i,item.key)" :key="index">
            {{i[item.key]}}
          </el-tag>
          <el-tag slot="reference" size="mini">更多 </el-tag>
        </el-popover>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  name: "showInfos",
  props: {
    width: {
      type: String,
      default: "100%",
    },
    itemWidth: {
      type: String,
      default: "",
    },
    height: {
      type: String,
      default: "",
    },
    textStyle: {
      type: Object,
      default: () => {
        return {
          fontSize: "16px",
          color: "#333333",
        };
      },
    },
    textValueStyle: {
      type: Object,
      default: () => {
        return {
          fontSize: "16px",
          fontWeight: "normal",
          color: "#000000",
        };
      },
    },
    imgStyle: {
      type: Object,
      default: () => {
        return {
          width: "130px",
          height: "130px",
        };
      },
    },
    list: {
      type: Array,
      default: () => {
        return [
          {
            name: "姓名",
            prop: "name",
            value: "",
            type: "1",
          },];
      },
    },
    dataValue: {
      type: Object,
      default: () => {
        return {
          name: "电话联系电话联系电话",
          phone: "1",
          address: "qweqwda",
          allInfos: "asdasdasd",
          idNum: "asdasdas",
          finance: ["asdasd", "asd", "asdasd", "asdasd"],
          imgList: [
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2F1113%2F011520104U2%2F200115104U2-4-1200.jpg&refer=http%3A%2F%2Flmg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670552051&t=8610bd8789a20df9ec2774b5ad5566fc",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2F1113%2F011520104U2%2F200115104U2-4-1200.jpg&refer=http%3A%2F%2Flmg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670552051&t=8610bd8789a20df9ec2774b5ad5566fc",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2F1113%2F011520104U2%2F200115104U2-4-1200.jpg&refer=http%3A%2F%2Flmg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670552051&t=8610bd8789a20df9ec2774b5ad5566fc",
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2F1113%2F011520104U2%2F200115104U2-4-1200.jpg&refer=http%3A%2F%2Flmg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1670552051&t=8610bd8789a20df9ec2774b5ad5566fc",
          ],
        };
      },
    },
  },
  watch: {
    dataValue: {
      handler(newV) {
      },
      deep: true,
      immediate: true,
    }
  },
  data() {
    return {
      baseStyles: {
        fontSize: "36px",
      },
    };
  },
};
</script>

<style lang="scss" scoped>
.show-infos {
  display: flex;
  min-height: 80px;
  flex-wrap: wrap;
  // width: ;
  &-item {
    margin-bottom: 10px;
    .show-infos-one {
      .show-text {
        display: inline-block;
        margin-right: 15px;
      }
    }
  }
  &-tag {
    .show-tag-value {
      display: inline-flex;
      flex-wrap: wrap;

      .tag-list {
        display: inline-flex;
        margin-top: 8px;
        margin-right: 20px;
      }
    }
  }
  &-img {
    .show-img-value {
      display: flex;
      margin-top: 10px;
      flex-wrap: wrap;
      .img-list {
        margin-bottom: 10px;
        margin-right: 10px;
        border-radius: 10px;
        background-size: 100% 100%;
      }
    }
  }
}

.tag-class {
  margin-top: 10px;
  cursor: pointer;
}
.jump-class {
  font-size: 14px;
  font-weight: normal;
  color: #409eff;
  cursor: pointer;
}
</style>