<template>
  <div class="layers-list-wrapper">
    <div class="list-head-wrapper">
      <span v-for="(item, index) in columns" :key="index" class="head-item "
            :style="{ width: item.width }">
        {{ item.label }}
      </span>
    </div>
    <div class="list-content-wrapper">
      <div ref="listContent" class="list-content">
        <div v-for="(item, index) in defaultValue" :key="index" class="list-item">
          <div class="list-item-wrapper">
            <div class="item-content-wrapper">
              <div class="left-wrapper">{{ item.label }}</div>
              <div class="right-wrapper">
                <div class="item-content">
                  <div class="total">总数</div>
                  <div class="num">{{ item.customerNum }}</div>
                  <div class="percentage"></div>
                </div>
                <div class="item-content">
                  <div class="in">内</div>
                  <div class="num">{{ item.InsideCustomerNum }}</div>
                  <div class="percentage">{{ item.InsideCustomerRatio }}%</div>
                </div>
                <div class="item-content">
                  <div class="out">外</div>
                  <div class="num">{{ item.OutsideCustomerNum }}</div>
                  <div class="percentage">{{ item.OutsideCustomerRatio }}%</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    defaultValue: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      columns: [
        { label: "类型", width: "38%" },
        { label: "客户数", width: "38%" },
        { label: "占比", width: "24%" },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.layers-list-wrapper {
  width: 400px;
  height: 100%;
  background: linear-gradient(
    0deg,
    rgba(17, 86, 111, 0.27),
    rgba(35, 55, 63, 0.04)
  );
  border: 1px solid;
  border-image: linear-gradient(180deg, #66cef500, #7ef4ff) 1 1;
  padding: 5px 10px;
  .list-head-wrapper {
    background: linear-gradient(
      180deg,
      rgba(7, 192, 219, 0.3),
      rgba(7, 192, 219, 0.7)
    );
    color: #a8e3f2;
    padding: 0 8px;
    display: flex;
    align-items: center;
    height: 24px;
    text-align: center;
    border-radius: 4px;
  }
  .list-content-wrapper {
    padding: 0 6px;
    width: 100%;
    height: calc(100% - 24px);
    .list-content {
      height: 100%;
      display: flex;
      flex-direction: column;
      .list-item {
        flex: 1;
        display: flex;
        width: 100%;
        &:nth-child(2n) {
          background: rgba(150, 252, 253, 0.1);
        }
        .list-item-wrapper {
          display: flex;
          width: 100%;
          margin: 5px 0;
          .item-content-wrapper {
            width: 100%;
            padding: 0 10px;
            display: flex;
            align-items: center;
            .left-wrapper {
              color: #fff;
              width: fit-content;
              font-size: 16px;
            }
            .right-wrapper {
              flex: 1;
              display: flex;
              flex-direction: column;
              padding: 0 20px 0 12px;
              box-sizing: border-box;
              .item-content {
                width: 100%;
                height: 50%;
                display: flex;
                align-items: center;
                margin: 5px 0;
                .total {
                  text-align: center;
                  line-height: 20px;
                  font-size: 14px;
                  color: #ffffff;
                }
                .in {
                  text-align: center;
                  margin-left: 4px;
                  width: 20px;
                  height: 20px;
                  line-height: 20px;
                  font-size: 14px;
                  background: linear-gradient(
                    0deg,
                    #e57335,
                    rgba(255, 120, 0, 0)
                  );
                  border-radius: 50%;
                  color: #ffffff;
                }
                .out {
                  text-align: center;
                  margin-left: 4px;
                  width: 20px;
                  height: 20px;
                  line-height: 20px;
                  font-size: 14px;
                  background: linear-gradient(
                    0deg,
                    #35dfe5,
                    rgba(26, 86, 101, 0)
                  );
                  border-radius: 50%;
                  color: #ffffff;
                }
                .num {
                  color: #fff;
                  flex: 1;
                  text-align: center;
                  font-size: 14px;
                }
                .percentage {
                  text-align: center;
                  width: 30px;
                  font-size: 14px;
                  color: #fffb8b;
                }
              }
            }
          }
        }
      }
    }
  }
}
</style>
