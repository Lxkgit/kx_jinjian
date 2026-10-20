<template>
  <el-dialog :visible.sync="dialogVisible" width="70%" :before-close="handleClose" class="collect-class"
    :close-on-click-modal="false" :append-to-body="true">
    <div slot="title" class="dialog-title">
      <span>网格动态</span>
    </div>
    <div class="info-wrapper">
      <el-tabs type="border-card" v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="已读" name="read">
          <div class="tabPane">
            <div v-for="(item, index) in readDataInfo" :key="index" class="news">
              <div :class="`triangle-${activeName}`"></div>
              <div class="newsInfo" @click="messageDetailsClick(item)">
                <div v-if="item.img" class="newsImg">
                  <img :src="require(`./mock/${item.img}`)" alt="">
                </div>
                <div class="newsText">
                  <div class="newsTitle">{{ item.title }}</div>
                  <div class="newsTitleInfo">
                    <div class="newsTime">发布时间：{{ item.time }}</div>
                    <div class="newsDepartment">网点机构：{{ item.deptName }}</div>
                  </div>
                  <div class="textDisplay">
                    {{ item.text[0] }}...
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="未读" name="unread">
          <div class="tabPane">
            <div v-for="(item, index) in unreadDataInfo" :key="index" class="news">
              <div :class="`triangle-${activeName}`"></div>
              <div class="newsInfo" @click="messageDetailsClick(item)">
                <div v-if="item.img" class="newsImg">
                  <img :src="require(`./mock/${item.img}`)" alt="">
                </div>
                <div class="newsText">
                  <div class="newsTitle">{{ item.title }}</div>
                  <div class="newsTitleInfo">
                    <div class="newsTime">发布时间：{{ item.time }}</div>
                    <div class="newsDepartment">网点机构：{{ item.deptName }}</div>
                  </div>
                  <div class="textDisplay">
                    {{ item.text[0] }}...
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
        :page-sizes="[5, 10, 20, 30]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
  </el-dialog>
</template>

<script>
import mockData from './mock/data'
export default ({
  props: {
    value: {
      type: Boolean,
    },
    readmessageDetails: {
      type: Object,
    }
  },
  data() {
    return {
      dialogVisible: this.value,
      dataInfo: mockData,
      activeName: 'read',
      readDataInfo: [],
      unreadDataInfo: [],
      currentPage: 1,
      pageSize: 5,
      total: 9
    }
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
        if (val) {
          this.initData();
        }
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit('input', val)
      }
    }
  },
  mounted() {
  },
  methods: {
    initData() {
      this.activeTab = '1'
      this.unreadDataInfo = []
      this.readDataInfo = []
      this.activeName = 'read'
      // if (this.readmessageDetails && this.readmessageDetails.id) {
      //   let newDataInfo = this.dataInfo.filter(item => item.id !== this.readmessageDetails.id)
      // }
      this.dataInfo.forEach((item, index) => {
        if (item.sataus === 0) {
          this.unreadDataInfo.push(item)
        } else {
          this.readDataInfo.push(item)
        }
      })
      this.total = this.readDataInfo.length
    },
    handleClick(tab, event) {
      if (tab.index === '0') {
        this.total = this.readDataInfo.length
      } else {
        this.total = this.unreadDataInfo.length
      }
    },
    handleClose() {
      this.dialogVisible = false;
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
    },
    messageDetailsClick(item) {
      this.$emit('messageDetails', item)
    }
  },
})
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
.info-wrapper {
  height: 78vh;
  overflow: auto;
  overflow-x: hidden;
  scrollbar-width: none;
  position: relative;
  margin-bottom: 20px;
}

.news {
  background: #FBFBFA;
  box-shadow: 0px 1px 12px 1px rgba(0, 5, 8, 0.09);
  border-radius: 6px;
  margin: 15px;
  position: relative;
  padding: 8px;
  cursor: pointer;

  .triangle-read {
    width: 25px;
    height: 25px;
    background-color: #ea9518;
    position: relative;
    clip-path: polygon(0% 0%, 100% 0%, 0% 100%);
    border-top-left-radius: 6px;
    position: absolute;
    top: 0;
    left: 0;
  }

  .triangle-unread {
    width: 25px;
    height: 25px;
    background-color: #919190;
    position: relative;
    clip-path: polygon(0% 0%, 100% 0%, 0% 100%);
    border-top-left-radius: 6px;
    position: absolute;
    top: 0;
    left: 0;
  }

  .newsInfo {
    display: flex;

    .newsImg {
      padding: 10px;

      img {
        width: 160px;
        height: 100px
      }
    }

    .newsText {
      .newsTitle {
        font-weight: bold;
        font-size: 18px;
        color: #212121;
        line-height: 44px;
      }

      .newsTitleInfo {
        display: flex;

        .newsTime {
          font-weight: 400;
          font-size: 14px;
          color: #333333;
          line-height: 30px;
        }

        .newsDepartment {
          font-weight: 400;
          font-size: 14px;
          color: #333333;
          line-height: 30px;
          margin-left: 30px;
        }
      }

      .textDisplay {
        font-weight: 400;
        font-size: 16px;
        color: #666666;
        line-height: 22px;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        white-space: normal;
      }
    }
  }
}


::v-deep .el-dialog__body {
  padding: 15px;
}

::v-deep .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
</style>