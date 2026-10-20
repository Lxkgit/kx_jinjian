<template>
  <div class="btn-bar-wrapper">
    <!-- 按钮部分 -->
    <div v-for="item in tableBtns" :key="item.ename" class="btn-box">
      <el-button
        v-if="!item.slot"
        type="text"
        :disabled="item.disabled && data[item.disabled]"
        :class="item.danger ? 'el-button-danger-text' : ''"
        class="btn-text-gap"
        @click="$emit('click', item.ename)"
      >
        <i
          v-if="item.icon"
          :class="[
            'el-icon--left',
            item.icon.includes('el') ? item.icon : [item.icon, 'iconfont'],
          ]"
        ></i>
        {{ item.cname }}
      </el-button>
      <slot v-else :name="item.ename" :btn="item" />
    </div>
    <!-- 下拉部分 -->
    <el-dropdown
      v-if="dropdownBtns.length > 0"
      size="mini"
      @command="(command) => $emit('click', command)"
      placement="bottom"
    >
      <el-button size="mini" type="text"
        ><i class="iconfont icon-gengduo"
      /></el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item
          :command="item.ename"
          v-for="item in dropdownBtns"
          :key="item.ename"
        >
          <el-button
            v-if="!item.slot"
            type="text"
            :disabled="item.disabled && data[item.disabled]"
          >
            <i
              v-if="item.icon"
              :class="[
                'el-icon--left',
                item.icon.includes('el') ? item.icon : [item.icon, 'iconfont'],
              ]"
            ></i>
            {{ item.cname }}
          </el-button>
          <slot v-else :name="item.ename" :btn="item" />
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>
<script>
export default {
  name: "ITableBtns",
  props: {
    count: {
      //大于count 就不显示数据
      type: Number,
      default: 2,
    },
    data: {
      //
      type: [Object, Array],
    },
    btnLists: {
      type: Array,
      default: () => [
        {
          icon: "el-icon-upload",
          cname: "编辑",
          ename: "edit",
          disabled: "disabled", //控制disable的key
          isShow: "hasBtn", //控制是否展示的 key
          // isShow:true,  //字段控制的是否显示
          // permi:'test1'   //权限控制的是否显示
        },
        {
          icon: "el-icon-upload",
          cname: "编辑2",
          ename: "edit2",
          type: "warning",
          disabled: true,
          permi: "test2",
        },
      ],
    },
  },
  computed: {
    tableBtns() {
      const showBtns = [];
      this.dropdownBtns = [];
      for (let item of this.btnLists) {
        let permi = item.permi === undefined || this.$auth.hasPermi(item.permi);
        let isShow = true;
        if (item.isShow !== undefined) {
          if (typeof item.isShow === "function") {
            isShow = item.isShow.call(this, this.data);
          } else {
            isShow = this.data[item.isShow];
          }
        }
        let needDrop = showBtns.length > this.count - 1;
        if (permi && isShow) {
          needDrop ? this.dropdownBtns.push(item) : showBtns.push(item);
        }
      }
      return showBtns;
    },
  },
  data() {
    return {
      dropdownBtns: [],
    };
  },
};
</script>

<style lang="scss" scoped>
.btn-bar-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  .btn-box {
    margin-right: 10px;
    position: relative;
  }
  .btn-box:not(:last-child) {
    &::after {
      content: "|";
      position: absolute;
      top: 5px;
      right: -7px;
      color: #e9e9e9;
    }
  }
}
</style>
