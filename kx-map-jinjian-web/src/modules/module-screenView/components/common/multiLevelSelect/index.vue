<template>
  <div class="multi-level-select-wrapper">
    <div v-for="(item, index) in options" :key="index" class="total-type">
      <el-checkbox
        v-model="item.selected"
        :label="item.label"
        @change="(val) => handleChanges(val, item)"
      >
      </el-checkbox>
      <i
        v-if="item.children.length > 0"
        class="el-icon-caret-bottom check-arrow"
        :class="{ unfold: item.unfold }"
        @click.stop="item.unfold = !item.unfold"
      />
      <div class="sub-type-wrapper">
        <cascadeCheckbox
          ref="cascadeCheckbox"
          v-model="checkChildList"
          :ifClose="item.selected"
          :infoArray="item.children"
        />
      </div>
    </div>
  </div>
</template>

<script>
import cascadeCheckbox from "./components/cascadeCheckbox";
const MUT = ["绩效客户", "行内客户", "收单客户", "不含乡贷授信客户"];
const SUB_TYPE = {
  1: "riskTypeList",
  2: "guaranteeTypeList",
  3: "purposeTypeList",
  4: "villageLoanTypeList",
};
export default {
  components: {
    cascadeCheckbox,
  },
  data() {
    return {
      options: [
        {
          label: "行内客户",
          value: "2",
          selected: false,
          unfold: false,
          children: [
            {
              label: "风险类型",
              value: "riskTypeList",
              flag: false,
              children: [
                { label: "正常类", value: "11" },
                { label: "逾欠类", value: "12" },
                { label: "不良类", value: "13" },
              ],
            },
            {
              label: "担保方式",
              value: "guaranteeTypeList",
              flag: false,
              children: [
                { label: "保证", value: "22" },
                { label: "信用", value: "23" },
                { label: "抵押", value: "20" },
                { label: "质押", value: "21" },
              ],
            },
            {
              label: "用途",
              value: "purposeTypeList",
              flag: false,
              children: [
                { label: "经营", value: "30" },
                { label: "消费", value: "31" },
              ],
            },
            {
              label: "乡村振兴贷",
              value: "villageLoanTypeList",
              flag: false,
              children: [
                { label: "用信客户", value: "41" },
                { label: "签约未用信客户", value: "42" },
              ],
            },
          ],
        },
        // {
        //   label: '绩效客户',
        //   value: '1',
        //   selected: false,
        //   unfold: false,
        //   children: [],
        // },

        // {
        //   label: '不含乡贷授信客户',
        //   value: '4',
        //   selected: false,
        //   unfold: false,
        //   children: [],
        // },
        {
          label: "建档客户",
          value: "3",
          selected: false,
          unfold: false,
          children: [],
        },
      ],
      checkChildList: [],
    };
  },
  computed: {
    checkList() {
      return this.options
        .filter((item) => item.selected)
        .map((item) => item.value);
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
  },
  watch: {
    checkChildList: {
      handler(val) {
        if (val.length > 0) {
          for (let item of this.options) {
            if (item.children.length > 0) {
              item.selected = true;
            } else if (MUT.includes(item.label) && item.children.length === 0) {
              item.selected = false;
            }
          }
        }
      },
    },
    checkList: {
      handler(val) {
        if (!val.includes("2")) {
          this.initCheckChild();
        }
      },
      deep: true,
    },
  },
  mounted() {
    this.options[0].selected = true;
  },
  methods: {
    // 初始化
    initData() {
      const { typeList } = this.filterParam;
      if (typeList) {
        this.options.forEach((item) => {
          if (typeList.includes(item.value)) {
            item.selected = true;
          } else {
            item.selected = false;
          }
        });
      }
      let hasChild = false;
      Object.keys(this.filterParam).forEach((key) => {
        if (key !== "typeList") {
          const typeValue = getKeyByValue(SUB_TYPE, key);
          if (typeValue) {
            hasChild = true;
            this.checkChildList = this.filterParam[key].map(
              (item) => `${typeValue}${item}`
            );
            this.$refs.cascadeCheckbox[1].initCheckAll(key);
          }
        }
      });

      if (!hasChild) {
        this.initCheckChild();
      }

      function getKeyByValue(obj, value) {
        for (let key in obj) {
          if (obj[key] === value) {
            return key;
          }
        }
        return null;
      }
    },
    initCheckChild() {
      for (let item of this.options) {
        if (item.children.length > 0) {
          item.children.forEach((el) => {
            el.flag = false;
          });
        }
      }
      this.$refs.cascadeCheckbox[1].initData();
    },
    // 点击选择
    handleChanges(val, item) {
      const { label, value } = item;
      if (item.children.length > 0) {
        item.unfold = true;
      }
      if (val) {
        if (MUT.includes(label)) {
          this.options.forEach((el) => {
            if (MUT.includes(el.label) && el.label !== label) {
              el.selected = false;
            }
          });
        }
      }
    },
    // 获取当前选择项
    getCurrentData(cb) {
      const checkType =
        this.checkChildList.length === 0
          ? ""
          : SUB_TYPE[this.checkChildList[0].charAt(0)];
      const checkChildList = this.checkChildList.map((item) => item.charAt(1));
      cb && cb({ checkList: this.checkList, checkChildList, checkType });
    },
  },
};
</script>

<style lang="scss" scoped>
.multi-level-select-wrapper {
  width: 100%;
  .total-type {
    margin: 14px 0;
    position: relative;
    .check-arrow {
      cursor: pointer;
      position: absolute;
      top: 12px;
      right: 18px;
      color: #fff;
      &.unfold {
        transform: rotate(180deg);
        & + .sub-type-wrapper {
          height: 100%;
        }
      }
    }
    ::v-deep .el-checkbox {
      display: block;
    }
    ::v-deep .el-checkbox__input {
      width: 20px;
      height: 20px;
      border: none;
      background-color: transparent;
      border-radius: 4px;
    }
    ::v-deep .el-checkbox__inner {
      width: 20px;
      height: 20px;
      border: 2px solid #00f6ff;
      background-color: rgba($color: #1d4552, $alpha: 0.6);
    }
    ::v-deep .el-checkbox__input.is-checked .el-checkbox__inner::after {
      transform: translate(-50%, -50%) scaleY(1);
    }
    ::v-deep .el-checkbox__inner::after {
      box-sizing: content-box;
      content: "";
      width: 10px;
      height: 10px;
      background: #00f6ff;
      border-radius: 50%;
      border: none;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%) scaleY(0);
      transition: none;
      transform-origin: center;
    }
    ::v-deep .el-checkbox__label {
      display: inline-block;
      width: calc(100% - 22px - 7px);
      height: 36px;
      font-size: 16px;
      margin-left: 7px;
      color: #8bf6ff;
      line-height: 36px;
      border-radius: 6px;
      background: rgba($color: #8bf6ff, $alpha: 0.15);
    }
    .sub-type-wrapper {
      height: 0;
      overflow: hidden;
    }
  }
}
</style>