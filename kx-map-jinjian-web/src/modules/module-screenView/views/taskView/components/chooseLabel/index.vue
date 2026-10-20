<!-- 选择标签 -->
<template>
  <div class="choose-label">
    <div
      @click="choose(single, item, index)"
      :style="{ backgroundColor }"
      class="label-item"
      v-for="(item, index) in labelList"
      :key="index"
      :class="[item.isChoose ? 'chooseItem' : 'notChoose']"
    >
      {{ item.name }}
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  props: {
    backgroundColor: {
      type: String,
      default: "pink",
    },
    single: {
      type: Boolean,
      default: false, // 单选或多选
    },
    value: {
      type: [String, Number, Array],
      default: () => "2",
    },
    labelList: {
      type: Array,
      default: () => [
        {
          name: "贷款需求",
          value: 1,
          isChoose: false,
        },
        {
          name: "其他需求",
          value: 2,
          isChoose: false,
        },
        {
          name: "存款需求",
          value: 3,
          isChoose: false,
        },
      ],
    },
  },
  components: {},
  data() {
    return {
      currentIndex: 0,
      defaultValue: null,
    };
  },
  computed: {},
  watch: {
    value: {
      handler(e) {
        this.labelList.forEach((v) => {
          this.$set(v, "isChoose", false);
        }); // 所有先制空
        this.defaultValue = e;
        let isArray = this.defaultValue instanceof Array;
        if (isArray) {
          // 传入数据为数组时
          for (let i in this.defaultValue) {
            for (let j in this.labelList) {
              if (this.defaultValue[i] === this.labelList[j].value) {
                this.labelList[j].isChoose = true;
              }
            }
          }
        } else {
          //
          this.labelList.forEach((v) => {
            if (this.defaultValue == v.value) {
              v.isChoose = true;
            }
          });
        }
      },
      immediate: true,
      deep: true,
    },
  },
  created() {},
  mounted() {},
  methods: {
    // 单选or多选
    choose(isSingle, item, index) {
      if (isSingle) {
        this.currentIndex = index;
        this.labelList.forEach((it, inD) => {
          if (inD == index) {
            it.isChoose = true;
          } else {
            it.isChoose = false;
          }
        });
        const itemChoose = this.labelList.filter((v) => {
          return v.value === item.value;
        })[0];
        console.log(itemChoose, "itemChoose");
        this.$emit("input", itemChoose.value);
        this.$emit("choose", itemChoose);
      } else {
        this.labelList.filter((v) => {
          return v.value == item.value;
        })[0].isChoose = !item.isChoose;
        const chooseList = this.labelList.filter((v) => v.isChoose == true);
        this.$emit(
          "input",
          chooseList.map((v) => v.value)
        );
        this.$emit("choose", chooseList);
      }
    },
  },
};
</script>

<style scoped lang="scss">
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.choose-label {
  display: flex;
  padding: 10px 10px 0 10px;
  background-color: rgba($color: #031628, $alpha: 0.3);
  border: 1px solid #16e1ff;
  flex-wrap: wrap;
  .label-item {
    margin-right: 5px;
    color: white;
    font-size: 14px;
    cursor: pointer;
    padding: 6px 8px;
    border-radius: 5px;
    height: 30px;
    margin-bottom: 10px;
    @include flex;
  }
  .chooseItem {
    opacity: 1;
  }
  .notChoose {
    opacity: 0.5;
  }
}
</style>
