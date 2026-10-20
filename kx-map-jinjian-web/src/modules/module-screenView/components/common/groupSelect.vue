<template>
  <div class="group-select-wrapper">
    <ul class="group-select">
      <li v-for="(item, index) in options" :key="index">
        <!-- <span
          class="select-type"
          :class="{ unfold: item.unfold }"
          @click.stop="
            item.unfold = !item.unfold;
            item.unfold && getCheckChild(item.value);
          "
        >
          {{ item.label }}
          <i class="el-icon-caret-bottom check-arrow"></i>
        </span> -->

        <!-- 多选 -->
        <!-- <div class="select-children">
          <el-checkbox-group
            v-model="checkedItems"
            class="children-check-wrapper"
            @change="handleChanges(item)"
          >
            <el-checkbox
              v-for="items in item.children"
              :key="items.id"
              :label="items.id"
            >
              {{ items.customGroupName }}
            </el-checkbox>
          </el-checkbox-group>
        </div> -->

        <!-- 单选 -->
        <div class="group-select-wrapper">
          <ul class="group-select">
            <li v-for="(item, index) in options" :key="index">
              <div class="select-children">
                <el-radio-group v-model="checkedItem" class="children-check-wrapper" @change="handleChanges(item)">
                  <el-radio v-for="items in item.children" :key="items.id" :label="items.id">
                    {{ items.customGroupName }}
                  </el-radio>
                </el-radio-group>
              </div>
            </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import service from "../../api";
export default {
  data() {
    return {
      options: [
        // {
        //   label: '绩效客户',
        //   value: '1',
        //   unfold: false,
        //   children: [],
        // },
        {
          label: "行内客户",
          value: "2",
          unfold: false,
          children: [],
        },
        // {
        //   label: "建档客户",
        //   value: "3",
        //   unfold: false,
        //   children: [],
        // },
      ],
      // checkedItems: [],
      activeType: "",
      checkedItem: null,
    };
  },
  computed: {
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
  },
  methods: {
    initData() {
      const { groupIdList, groupCategory } = this.filterParam;
      // this.checkedItems = groupIdList ?? [];
      if (groupIdList[0] == null) {
        this.checkedItem = null;
      } else {
        this.checkedItem = groupIdList[0] ?? null;
      }
      this.activeType = groupCategory ?? "";
      this.getCheckChild("2");
    },
    getCheckChild(type) {
      const param = {
        categoryId: type,
      };
      service.groupListNotPage(param).then((res) => {
        const { rows } = res;
        this.options.forEach((item) => {
          if (item.value === type) {
            item.children = rows;
          }
        });
      });
    },
    handleChanges(val) {
      // const infoLists = val.children.map((ite) => ite.id);
      // this.checkedItems = this.checkedItems.filter((item) =>
      //   infoLists.includes(item)
      // );
      // this.activeType = val.value;
      // if (this.checkedItems.length === 0) {
      //   this.activeType = "";
      // }
      this.checkedItem =
        val.children.find((ite) => ite.id === this.checkedItem)?.id || null;
      this.activeType = val.value;
    },
    getCurrentData(cb) {
      cb &&
        cb({ groupIdList: [this.checkedItem], groupCategory: this.activeType });
      // cb({ groupIdList: this.checkedItems, groupCategory: this.activeType });
    },
  },
};
</script>

<style lang="scss" scoped>
.group-select-wrapper {
  width: 100%;
  padding: 0 3px;

  .group-select {
    >li {
      margin: 14px 0;

      .select-type {
        display: flex;
        justify-content: space-between;
        align-items: center;
        line-height: 36px;
        border-radius: 6px;
        color: #8bf6ff;
        font-size: 16px;
        padding: 0 12px;
        background: rgba($color: #8bf6ff, $alpha: 0.15);

        &.unfold {
          .check-arrow {
            transform: rotate(180deg);
          }

          &+.select-children {
            height: 100%;
          }
        }

        .check-arrow {
          cursor: pointer;
          color: #fff;
        }
      }

      .select-children {
        height: 100%;
        // height: 0;
        overflow: hidden;

        ::v-deep .el-checkbox {
          display: block;
          margin: 8px 4px 0;
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
          // transition: transform 0.15s ease-in 0.05s;
          transition: none;
          transform-origin: center;
        }

        ::v-deep .el-checkbox__label {
          font-size: 14px;
          line-height: 22px;
          color: #fff;
        }
      }
    }
  }
}

.el-radio-group {
  .el-radio {
    line-height: 2;
  }

  ::v-deep .el-radio__label {
    color: #fff;
    font-size: 16px;
  }

  ::v-deep .el-radio__inner {
    width: 20px;
    height: 20px;
    border: 2px solid #5DABFF;
    background: rgba(29, 69, 82, 0.6);
  }

  ::v-deep .el-radio__inner::after {
    width: 10px;
    height: 10px;
    border: none;
    background: #5DABFF;
  }
}
</style>