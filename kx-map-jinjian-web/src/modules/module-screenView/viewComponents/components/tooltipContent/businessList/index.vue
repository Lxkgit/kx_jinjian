<template>
  <div class="layers-list-wrapper">
    <div class="list-head-wrapper">
      <span v-for="(item, index) in columns" :key="index" class="head-item"
            :style="{ '--flex': item.flex }">
        {{ item.label }}
      </span>
    </div>
    <div class="list-content-wrapper">
      <div ref="listContent" class="list-content">
        <div v-for="(item, index) in tableData" :key="index" class="list-item">
          <ListItem :item-data="item">
            <template #credit="{ data }">
              <div class="credit-type">
                <img :src="
                    require(`@/modules/module-screenView/viewComponents/assets/credit-${
                      data.label == '贷款' ? 1 : 2
                    }.png`)
                  " alt="" />
                {{ data.label }}
              </div>
            </template>
          </ListItem>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ListItem from "./ListItem.vue";
import {
  level_1_content,
  level_2_content,
  level_3_content,
  level_4_content,
} from "./config";

export default {
  components: {
    ListItem,
  },
  props: {
    dataList: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      columns: [
        { label: "类型", prop: "type", flex: 1.5 },
        { label: "当前值", prop: "type", flex: 1 },
        { label: "占比", prop: "type", flex: 1 },
        { label: "较上月", prop: "type", flex: 1 },
        { label: "较年初", prop: "type", flex: 1 },
      ],
      tableData: [],
    };
  },
  mounted() {
    this.setTableData();
  },
  methods: {
    setTableData() {
      let i = 0;
      for (let level1 of level_1_content) {
        let l2Content = JSON.parse(JSON.stringify(level_2_content))
        l2Content.forEach((level2, i2) => {
          level2.label = level1.childLabel[i2]
          let l3Content = JSON.parse(JSON.stringify(level_3_content))
          for (let level3 of l3Content) {
            let temp = this.dataList[i];
            let arr = JSON.parse(JSON.stringify(level_4_content));
            arr.forEach((item, index) => {
              item.label = temp[index].value;
            });
            level3.children = arr;
            i++;
          }
          level2.children = l3Content;
        })
        level1.children = l2Content
      }
      this.tableData = level_1_content;
    },
  },
};
</script>

<style lang="scss" scoped>
.layers-list-wrapper {
  width: 490px;
  background: linear-gradient(
    0deg,
    rgba(17, 86, 111, 0.27),
    rgba(35, 55, 63, 0.04)
  );
  border: 1px solid;
  border-image: linear-gradient(180deg, #66cef500, #7ef4ff) 1 1;
  padding: 5px 10px;
  margin-bottom: 10px;
  .list-head-wrapper {
    background: linear-gradient(
      180deg,
      rgba(7, 192, 219, 0.3),
      rgba(7, 192, 219, 0.8)
    );
    display: flex;
    height: 24px;
    line-height: 24px;
    color: #a8e3f2;
    border-radius: 4px;
    text-align: center;
    .head-item {
      display: block;
      flex: var(--flex);
    }
  }
  .list-content-wrapper {
    .list-content {
      .list-item {
        &:nth-child(2n) {
          background: rgba(150, 252, 253, 0.1);
        }
        .credit-type {
          display: flex;
          flex-direction: column;
          color: #8bf6ff;
          text-align: left;
          white-space: nowrap;
          margin: 0 14px;
          img {
            width: 25px;
            height: 25px;
            margin-bottom: 3px;
          }
        }
      }
    }
  }
}
</style>
