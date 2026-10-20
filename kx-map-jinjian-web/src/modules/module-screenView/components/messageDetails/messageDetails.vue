<template>
  <el-dialog :visible.sync="dialogVisible" width="60vw" :before-close="handleClose" class="collect-class"
    :close-on-click-modal="false" :append-to-body="true">
    <div slot="title" class="dialog-title">
      <span>网格动态</span>
    </div>
    <div class="news">
      <div class="newsTitle">{{ messageDetails.title }}</div>
      <div class="newsTitleInfo">
        <span class="newsTime">发布时间：{{ messageDetails.time }}</span>
        <span class="newsDepartment">网点机构：{{ messageDetails.deptName }}</span>
        <span class="location" @click="$emit('location', messageDetails.id)"><i
            class="el-icon-location"></i>定位到该网点</span>
      </div>
      <div class="newsImg">
        <!-- <img :src="require(`../trends/mock/${messageDetails.img}`)" /> -->
      </div>
      <div class="textText">
        <div v-for="(item, index) in messageDetails.text" :key="index">
          <div class="textDisplay">
            {{ item }}
          </div>
          <br />
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
    },
    messageDetailsInfo: { type: Object },
  },
  data() {
    return {
      dialogVisible: this.value,
      isDetailsShow: false,
      messageDetails: {},
    };
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    messageDetailsInfo: {
      handler(newVal, oldVal) {
        if (newVal) {
          this.messageDetails = newVal;
          // console.log(this.messageDetails);
        }
        console.log(newVal, oldVal);
      },
      deep: true,
      immediate: true,
    },
  },
  mounted() { },
  methods: {
    handleClose() {
      this.dialogVisible = false;
      this.$emit("detailsDialog", this.messageDetails);
    },
  },
};
</script>

<style lang="scss" scoped>
.collect-class {
  ::v-deep .el-dialog__header {
    background: #ea931c;

    .active {
      background-color: #5b1c8a;
    }
  }
}

.dialog-title {
  span {
    color: #fff;
    cursor: pointer;

    font-size: 16px;
  }
}

::v-deep .el-dialog__body {
  padding: 15px;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}

.news {
  background: #fbfbfa;
  box-shadow: 0px 1px 12px 1px rgba(0, 5, 8, 0.09);
  border-radius: 6px;
  border: 1px solid #eee;
  margin: 15px;
  padding: 20px;

  .newsTitle {
    font-weight: bold;
    font-size: 20px;
    color: #212121;
    line-height: 44px;
  }

  .newsImg {
    text-align: center;
    margin-top: 10px;

    img {
      width: 200px;
    }
  }

  .newsTitleInfo {
    display: flex;
    justify-content: center;

    .newsTime {
      font-weight: 400;
      font-size: 16px;
      color: #333333;
      line-height: 30px;
    }

    .newsDepartment {
      font-weight: 400;
      font-size: 16px;
      color: #6c4818;
      line-height: 30px;
      margin-left: 30px;
    }

    .location {
      color: #32aab5;
      line-height: 30px;
      margin-left: 30px;
      cursor: pointer;
      text-decoration: underline;
    }
  }

  .textText {
    margin-top: 10px;
    max-height: 370px;
    overflow: auto;
    line-height: 1.5;
    scrollbar-width: none;

    .textDisplay {
      font-weight: 400;
      font-size: 16px;
      color: #666666;
      text-indent: 22px;
    }
  }
}
</style>