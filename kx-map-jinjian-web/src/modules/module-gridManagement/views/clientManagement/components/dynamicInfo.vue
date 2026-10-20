<template>
  <div class="dynamic-info">
    <div class="info-content">
      <div v-for="(item, index) in dataInfo" :key="index" class="news">
        <div class="newsInfo" @click="messageDetailsClick(item)">
          <div v-if="item.img" class="newsImg">
            <img
              :src="
                require(`@/modules/module-screenView/components/trends/mock/${item.img}`)
              "
              alt=""
            />
          </div>
          <div class="newsText">
            <div class="newsTitle">{{ item.title }}</div>
            <div class="newsTitleInfo">
              <div class="newsTime">发布时间：{{ item.time }}</div>
              <div class="newsDepartment">网点机构：{{ item.deptName }}</div>
            </div>
            <div class="textDisplay">{{ item.text[0] }}...</div>
          </div>
        </div>
      </div>
    </div>

    <messageDeatailInfo  v-model="detailsShow" :messageDetailsInfo="messageDetailsInfo"></messageDeatailInfo>

     <!-- 分页器 -->
     <el-pagination class="table-pagination" background :total="total"
      :current-page="pageNum" :page-size="pageSize" layout="total, prev, pager, next, jumper"
      @current-change="currentChange" />
  </div>
</template>

<script>
import mockData from "@/modules/module-screenView/components/trends/mock/data";
import messageDeatailInfo from "./messageDeatailInfo.vue";
import services from "../api";
export default {
  components: { messageDeatailInfo },
  data() {
    return {
      pageNum:1,
      pageSize:10,
      orgNum:'',
      total:0,
      dataInfo:[],
      detailsShow:false,
      messageDetailsInfo:{},
    }
  },
  mounted() {
    const { branchInfo } = this.$route.query
    this.orgNum = branchInfo.orgNum
    this.$nextTick(() => {
      this.getArticleList()
    })
    this.dataInfo = mockData.slice(0, this.pageSize);
  },
  methods: {
    //获取网点动态信息列表
    async getArticleList() {
      let params = {
        pageNum:this.pageNum,
        pageSize:this.pageSize,
        orgNum:this.orgNum,
      }
      const res = await services.articleList(params)
    },
    messageDetailsClick(item) {
      this.messageDetailsInfo = item;
      this.detailsShow = true;
    },
    // 分页器当前页面改变
    currentChange(val) {
      this.pageNum = val;
      console.log('当前页：' + this.pageNum);
    },
  },
}
</script>

<style lang="scss" scoped>
  .dynamic-info {
    padding: 4px 0;
    height: calc(100vh - 64px - 56px - 64px - 24px - 12px - 41px - 32px);
    position: relative;
    .table-pagination {
      position: absolute;
      right: 0;
      bottom: 10px;
    }
    ::v-deep .el-pagination {
      .el-pagination__editor {
        margin: 0 !important;
        padding: 0 !important;

      }
      .el-pagination__jump {
        font-size: 0; // 隐藏原文字
        &::before {
          content: '';
          // font-size: 12px;
          // color: #606266;
        }
        &::after {
          content: 'GO';
          display: inline-block;
          font-size: 12px;
          width: 32px;
          height: 30px;
          text-align: center;
          line-height: 28px;
          color:#fff;
          border-radius: 4px 4px 4px 4px;
          // background: $--color !important;
          border: 1px solid #E0E2EC;
          cursor: pointer;
        }
      }
    }
  }
  .info-content {
    height: calc(100% - 45px);
    overflow: auto;
  }
  .news {
  position: relative;
  background: #fff;
  // box-shadow: 0px 1px 12px 1px rgba(0, 5, 8, 0.09);
  border-radius: 6px;
  border: 1px solid #E0E2EC;
  margin: 15px;
  padding: 8px;
  cursor: pointer;

  .newsInfo {
    display: flex;

    .newsImg {
      padding: 10px;

      img {
        width: 160px;
        height: 100px;
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
</style>