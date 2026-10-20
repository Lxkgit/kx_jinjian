<template>
  <div class="cascader">
    <div class="panel" v-for="item in list" :key="item.id">
      <div class="up">{{ item.labelGroupName }}：</div>
      <div
        class="item"
        :class="{ active: child.active }"
        v-for="(child, ind) in item.childLabels"
        :key="child.id"
        @click="childLabelClick(item, child, ind)"
      >
        <span class="name">{{ child.labelName }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cascader",
  props: {
    list: {
      typeof: Array,
      default: () => [],
    },
  },
  data() {
    return {
      chooseLabel: [],
    };
  },
  methods: {
    childLabelClick(item, child, ind) {
      if (child.active) {
        child.active = false;
        const index = this.chooseLabel.findIndex((i) => i.id == child.id);
        this.chooseLabel.splice(index, 1);
      } else {
        if (item.isMultiple == 1) {
          child.active = true;
          this.chooseLabel.push(child);
        } else {
          const isMultiple = this.chooseLabel.find(
            (i) => i.labelGroupId == child.labelGroupId
          );
          const index = this.chooseLabel.findIndex(
            (i) => i.labelGroupId == child.labelGroupId
          );
          if (isMultiple) {
            // this.$message.error("该标签只支持单选");
            isMultiple.active = false;
            this.chooseLabel.splice(index, 1);
            child.active = true;
            this.chooseLabel.push(child);
          } else {
            child.active = true;
            this.chooseLabel.push(child);
          }
        }
      }
    },
  },
};
</script>
<style scoped lang="scss">
.cascader {
  border: 1px solid #efefef;
}
.panel {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  .up {
    padding-left: 10px;
  }
  .item {
    width: fit-content;
    padding: 0 10px;
    border-radius: 8px;
    border: 1px solid #b3d8ff;
    color: #409eff;
    text-align: center;
    margin: 5px;
    &:nth-child(3n) {
      margin-right: 0;
    }
    .name {
      padding: 5px;
      font-size: 14px;
    }
    &.active {
      background-color: #5e81f4;
      color: #fff;
    }
  }
}
</style>
