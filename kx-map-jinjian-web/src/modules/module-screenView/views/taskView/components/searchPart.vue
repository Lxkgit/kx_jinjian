<template>
  <div class="search-part">
    <el-autocomplete placeholder="请输入内容" transition='none' :fetch-suggestions="querySearch"
                     :debounce="1000" popper-class="search-popper" :popper-append-to-body="false"
                     v-model="input" class="search-input">
      <div slot="prepend">
        客户
      </div>
      <template slot-scope="{item}">
        <div @click="itemClick(item)">
          <!-- 家庭 -->
          <div class="search-item" v-if="item.anchorType==1">
            <div :class="['search-left',`bg${item.anchorType}`]"></div>
            <div class="search-right">
              <span :title="item.clientAnchorName">锚点：<i>{{item.clientAnchorName}}</i></span>
              <span>身份证:{{ item.identify }}</span>
              <span :title="item.home_address">家庭住址:{{ item.home_address }}</span>
            </div>
          </div>
          <!-- 商户 -->
          <div class="search-item" v-if="item.anchorType==3">
            <div :class="['search-left',`bg${item.anchorType}`]"></div>
            <div class="search-right">
              <span :title="item.clientAnchorName">锚点：<i>{{item.clientAnchorName}}</i></span>
              <span>统一信用代码:{{ item.businessLicenseNo }}</span>
              <span :title="item.home_address">商户住址:{{ item.home_address }}</span>
            </div>
          </div>
          <!-- 公司 -->
          <div class="search-item" v-if="item.anchorType==2">
            <div :class="['search-left',`bg${item.anchorType}`]"></div>
            <div class="search-right">
              <span :title="item.clientAnchorName">锚点：<i>{{item.clientAnchorName}}</i></span>
              <span :title="item.home_address">公司住址:{{ item.home_address }}</span>
            </div>
          </div>
        </div>
      </template>
    </el-autocomplete>
  </div>
</template>

<script>
export default {
  data() {
    return {
      input: ''
    }
  },
  methods: {
    querySearch(queryString, cb) {
      if (queryString) {
        cb([])
      } else {
        cb([])
      }

    },
    itemClick(item) {

    }
  }
}
</script>
<style lang="scss" scoped>
.search-part {
  width: 400px;
  z-index: 9999;
  display: inline-block;
  .search-input {
    width: 260px;
    border: 1px solid #00f7ff80;
    background: linear-gradient(180deg, #0f324380 0%, #15e0e780 100%);
    border-radius: 10px;
    ::v-deep .el-input-group__prepend {
      background: inherit;
      border: none;
      width: 35px;
      font-size: 18px;
      font-weight: 500;
      color: #ffffff;
      position: relative;
      &::after {
        content: "";
        position: absolute;
        right: 0;
        top: 8px;
        width: 1px;
        height: 24px;
        border-left: 1px solid #ffffff60;
      }
    }
    ::v-deep input {
      background: inherit;
      border: none;
      font-size: 18px;
      font-weight: 500;
      color: #ffffff;
    }
    ::v-deep .search-popper {
      transition: none;
      background: rgba(0, 0, 0, 0.6);
      width: 320px !important;
      left: inherit !important;
      border: 1px solid #00f6ff;
      background: linear-gradient(180deg, #0f324380 0%, #2badb980 100%);
      border-radius: 8px;
      .popper__arrow {
        display: none;
      }
      .el-autocomplete-suggestion__wrap {
        padding: 5px;
        li {
          background: #1d4552dd;
          border-radius: 8px;
          margin-bottom: 3px;
          color: #fff;
          box-sizing: border-box;
          padding: 10px;
          &:hover {
            border: 1px solid #00f6ff;
            background: linear-gradient(180deg, #0f3243 0%, #2badb9 100%);
            opacity: 0.8;
          }
        }
      }
    }
  }
}
.search-item {
  display: flex;
  flex-direction: row;
  .search-left {
    background-size: 22px 22px;
    width: 60px;
    height: 70px;
    &.bg1 {
      background: url(../assets/image/point-f.png) center no-repeat;
    }
    &.bg2 {
      background: url("../assets/image/point-c.png") center no-repeat;
    }
    &.bg3 {
      background: url("../assets/image/point-s.png") center no-repeat;
    }
  }
  .search-right {
    flex: 1;
    display: flex;
    flex-direction: column;
    > span {
      font-size: 14px;
      line-height: 24px;
      width: 220px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
    }
    > span:first-of-type {
      font-size: 16px;
      line-height: 26px;
      > i {
        color: #ff9e56;
        font-style: normal;
      }
    }
  }
}
</style>