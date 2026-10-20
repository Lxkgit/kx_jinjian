<template>
  <div class="trip-wrapper" v-loading="loading">
    <el-timeline reverse v-if="cardData.length>0">
      <el-timeline-item v-for="(item, index) in cardData" :key="index" size="large" :color="color"
                        :timestamp="item.timestamp" placement="top"
                        :style="{ '--background-color': backgroundColor, '--color': color }">
        <el-card class="card">
          <div class="header">
            <img class="image" :src="item.src" alt="" />
            <div class="title">{{ item.title }}</div>
          </div>
          <div class="content">
            <div class="item" v-for="(content, index) in item.content" :key="index">
              <div class="label">{{ content.label }}：</div>
              <div class="value">{{ content.value }}</div>
            </div>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-empty v-else description="暂无数据"></el-empty>
  </div>
</template>

<script>
import service from './api';
const TYPE = {
  1: '开卡',
  2: '授信',
  3: '走访'
}
const CONTENT_TYPE = {
  1: [
    { label: "所有卡类别", value: "", key: 'firstField' },
    { label: "实体卡尾号4位数", value: "", key: 'secondField' },
  ],
  2: [
    { label: "产品名称", value: "", key: 'firstField' },
    { label: "授信金额", value: "", key: 'secondField' },
  ],
  3: [
    { label: "走访人", value: "", key: 'firstField' },
  ],
}
export default {
  name: 'CustomerTrip',
  props: {
    color: String,
    backgroundColor: String,
    customerParam: Object
  },
  data() {
    return {
      loading: false,
      cardData: [],
    };
  },
  mounted() {
    this.getCustomerTrip()
  },
  methods: {
    getCustomerTrip() {
      const param = {
        customerIdentity: this.customerParam.identity
      }
      this.loading = true
      service.customerTrip(param).then((res) => {
        const { data } = res
        this.loading = false
        this.cardData = data.map(item => {
          return {
            title: TYPE[item.type],
            src: require(`@/components/CustomerTrip/assets/type_${item.type}.png`),
            timestamp: this.parseTime(item.travelTime, "{y}年{m}月{d}日"),
            content: JSON.parse(JSON.stringify(CONTENT_TYPE[item.type])).map(el => {
              el.value = item[el.key]
              return el
            })
          }
        })
        this.cardData.reverse(); 
      }).catch(() => {
        this.loading = false
      })
    }
  },
};
</script>

<style scoped lang="scss">
.trip-wrapper {
  width: 100%;
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;

  ::v-deep .el-timeline-item__timestamp {
    padding: 6px 8px;
    width: fit-content;
    font-weight: bold;
    border-radius: 1px;
    background-color: var(--background-color);
    color: var(--color);
  }

  .card {
    width: 600px;
    &::v-deep .el-card__body {
      padding: 20px;
    }
    .header {
      display: flex;
      align-items: flex-start;
      .image {
        width: 30px;
        height: 30px;
      }
      .title {
        margin-top: 4px;
        margin-left: 10px;
        font-weight: bold;
        font-size: 20px;
        color: #212121;
      }
    }
    .content {
      margin-top: 10px;
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      justify-content: space-between;
      padding: 0 30px;
      .item {
        width: 50%;
        box-sizing: border-box;
        padding-left: 10px;
        display: flex;
        align-items: center;
        margin: 5px 0;
        white-space: nowrap;
        .label {
          font-size: 18px;
          color: #333333;
        }
        .value {
          font-weight: bold;
          font-size: 18px;
        }
      }
    }
  }
}
</style>
