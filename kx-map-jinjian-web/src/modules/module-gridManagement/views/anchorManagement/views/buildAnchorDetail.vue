<template>
  <PageContent>
    <div class="detail-header">
      <i-filter-bar ref="filterBar" :columns="filterColumns" :gridColumns="4">
      </i-filter-bar>
    </div>
    <div class="building-content">
      <ul class="unit-wrapper" v-for="unit in buildData" :key="unit.unitNum">
        <div class="unit-name">{{ unit.unitNum }}</div>
        <li v-for="floor in unit.children" :key="floor.id">
          <div class="floor-name">
            <span>{{ floor.floorNum + "F" }}</span>
          </div>
          <div class="house-wrapper">
            <span :style="getColor(house.type)" :class="{isReside: house.isReside=='1'}"
                  v-for="house in floor.children" :key="house.id" class="house-name">
              {{ house.doorNum }}</span>
          </div>
        </li>
      </ul>
    </div>
  </PageContent>
</template>

<script>
import buildData from '../mock/buildData.json';
const COLORS = [
  '#ea9518', '#7735a8', '#4ccdcf'
]
export default ({
  data() {
    return {
      filterColumns: [
        {
          label: '楼宇锚点',
          key: 'title',
          type: 'text',
        },
        {
          label: '楼宇类型',
          key: 'title',
          type: 'text',
        },
        {
          label: '地址',
          key: 'title',
          type: 'text',
        },
      ],
      buildData: buildData,
    }
  },
  methods: {
    getColor(type) {
      return `background: ${COLORS[Number(type) - 1]}; border-color: ${COLORS[Number(type) - 1]};`
    }
  },
})
</script>

<style lang="scss" scoped>
.building-content {
  margin-top: 20px;
  display: flex;
  align-items: flex-end;
  .unit-wrapper {
    width: 100%;
    margin-right: 18px;
    .unit-name {
      cursor: pointer;
      font-size: 16px;
      margin-bottom: 10px;
      margin-top: 10px;
      margin-left: 10px;
    }
    li {
      display: flex;
      margin-bottom: 10px;
      .floor-name {
        color: #fff;
        text-align: center;
        width: 60px;
        min-height: 38px;
        border-radius: 4px;
        margin-right: 10px;
        background: rgba($color: #909399, $alpha: 1) no-repeat;
        background-size: 100% 100%;
        span {
          display: inline-block;
          position: relative;
          top: 50%;
          transform: translateY(-50%);
        }
      }
      .house-wrapper {
        width: calc(100% - 60px);
        height: 100%;
        .house-name {
          user-select: none;
          cursor: pointer;
          text-align: center;
          display: inline-block;
          min-width: 70px;
          padding: 0 10px;
          line-height: 38px;
          border: #333 1px solid;
          background: #fff;
          border-radius: 6px;
          margin: 6px 0;
          margin-right: 10px;
          &.selected {
            border-color: red;
            box-shadow: 0 0 10px red;
            color: #333;
          }
          &.isReside {
            background: #f56c6c;
            color: #fff;
          }
        }
      }
    }
  }
}
</style>