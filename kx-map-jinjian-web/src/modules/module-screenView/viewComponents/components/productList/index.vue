<template>
  <div class="product-list-wrapper">
    <div class="list-head-wrapper">
      <span v-for="(item, index) in columns" :key="index" class="head-item"
            :style="{ '--flex': item.flex }">
        {{ item.label }}
      </span>
    </div>
    <div class="list-content-wrapper">
      <div v-for="(item, index) in tableData" :key="index" class="list-item">
        <div class="label-wrapper">
          <img :src="require(`@/modules/module-screenView/viewComponents/assets/${item.src}.png`)"
               alt="">
          <span>{{item.label}}</span>
        </div>
        <div class="content-wrapper">
          <div class="content-list" v-for="(content,ci) in item.textArr" :key="ci">
            <div class="content-item" v-for="(el,i) in columns" :key="i">
              <span v-if="!el.slot && !el.number">
                {{ content[i] }}
              </span>
              <span class="number-item" v-else-if="el.number" v-number-color="content[i]"></span>
              <slot :name="el.key" :data="content"></slot>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    tableData: {
      type: Array
    }
  },
  data() {
    return {
      columns: [
        { label: "类型", prop: "type", flex: 2 },
        { label: "当前值", prop: "type", flex: 1 },
        { label: "较上月", prop: "type", flex: 1, number: true },
        { label: "较年初", prop: "type", flex: 1, number: true },
      ]
    }
  },
})
</script>

<style lang="scss" scoped>
.product-list-wrapper {
  width: 100%;
  background: linear-gradient(
    0deg,
    rgba(17, 86, 111, 0.27),
    rgba(35, 55, 63, 0.04)
  );
  border: 1px solid;
  border-image: linear-gradient(180deg, #66cef500, #7ef4ff) 1 1;
  padding: 5px 10px;
  font-size: 14px;
  color: #fff;
  margin-bottom: 20px;
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
    .list-item {
      display: flex;
      align-items: center;
      &:nth-child(2n) {
        background: rgba(150, 252, 253, 0.1);
      }
      .label-wrapper {
        width: 20%;
        display: flex;
        flex-direction: column;
        align-items: center;
        img {
          width: 25px;
          height: 25px;
        }
        span {
          color: #8bf6ff;
          line-height: 18px;
        }
      }
      .content-wrapper {
        width: 80%;
        .content-list {
          display: flex;
          align-items: center;
          text-align: center;
          .content-item {
            line-height: 1;
            width: 25%;
            padding: 9px 0;
            .number-item {
              color: #8bf6ff;
            }
          }
        }
      }
    }
  }
}
</style>