<template>
  <IDialog title="走访详情" v-model="showDialog" :showFooter="false" width="80%" appendToBody>
    <div class="visit-info">
      <ul class="visit-ul">
        <li class="item" :class="item.className" v-for="(item, i) in infoList" :key="i">
          <template v-if="item.className === 'image'">
            <div class="label" style="padding: 0 0 10px 0">走访照片:</div>
            <div class="pic">
              <el-image v-for="(pic, i) in item.value" :key="i" style="
                  width: 100px;
                  height: 100px;
                  margin-right: 20px;
                  margin-bottom: 10px;
                  border-radius: 4px;
                " :src="pic" :preview-src-list="item.value">
              </el-image>
            </div>
          </template>
          <template v-else-if="item.label === '网格标签'">
            <div class="label">网格标签:</div>
            <div class="tagBox">
              <div class="tag" v-for="tag in item.value" :key="tag">{{tag}}</div>
            </div>
          </template>
          <template v-else>
            <span class="label">{{ item.label }}：</span>
            <span class="value">{{ item.value }}</span>
          </template>
        </li>
      </ul>
    </div>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
export default ({
  mixins: [dialogMixin],
  props: {
    infoList: {
      type: Array
    }
  }
})
</script>

<style lang="scss" scoped>
.visit-info {
  .visit-ul {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    // justify-content: space-between;
    .item {
      width: 33.333%;
      box-sizing: border-box;
      padding-left: 30px;
      margin: 5px 0;
      white-space: nowrap;
      display: flex;
      align-items: center;
      &.image {
        width: 100%;
        .pic {
          display: flex;
          flex-wrap: wrap;
        }
      }
      .label {
        font-size: 18px;
        color: #333333;
      }
      .tagBox{
          display: flex;
          flex-direction: row;
          align-items: center;
          max-width: 600px;
          flex-wrap: wrap;
        .tag{
          // width: fit-content;
          color: #e7921d;
          font-weight: bold;
          font-size: 16px;
          margin: 3px;
        }
      }
      .value {
        font-weight: bold;
        font-size: 18px;
        width: 600px;
        display: inline-block;
        white-space: normal;
      }
      &.expireMoney {
        .value {
          font-weight: bold;
          font-size: 18px;
          color: #e7921d;
        }
      }
      &.gridLabel {
        width: 66.667%;
        .value {
          font-weight: bold;
          font-size: 18px;
          color: #e7921d;
        }
      }
      &.middle {
        width: 66.667%;
      }
      &.large {
        width: 100%;
      }
      .pic {
        padding-left: 10px;
        margin: 0 5px;
      }
    }
  }
}
</style>