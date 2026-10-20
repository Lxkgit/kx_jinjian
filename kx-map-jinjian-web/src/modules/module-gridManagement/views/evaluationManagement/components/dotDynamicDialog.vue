<template>
  <i-dialog
    title="网点信息上报"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="false"
  >
    <div
      style="
        font-weight: bold;
        font-size: 18px;
        color: #000;
        margin-bottom: 10px;
      "
    >
      {{ defaultValue.networkPoint }}
    </div>
    <div class="tabPane">
      <div v-for="(item, index) in tableData" :key="index" class="news">
        <div :class="`triangle-${activeName}`"></div>
        <div class="newsInfo" @click="messageDetailsClick(item)">
          <div v-if="item.img" class="newsImg">
            <img :src="require(`../mock/${item.img}`)" alt="" />
          </div>
          <div class="newsText">
            <div class="newsTitle">{{ item.title }}</div>
            <div class="textDisplay">{{ item.text[0] }}...</div>
            <div class="newsTitleInfo">
              <div class="newsTime">发布时间：{{ item.time }}</div>
              <div class="newsDepartment">网点机构：{{ item.deptName }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page="formInline.pageNum"
      :page-sizes="[5, 10, 20, 30]"
      :page-size="formInline.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>
  </i-dialog>
</template>
  
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin";
import mockData from "../mock/data";
export default {
  name: "dotDynamicDialog",
  mixins: [dialogMixin, pageMixin],
  dicts: [],
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return { tableData: mockData, activeName: "read", currentPage: 1 };
  },
  computed: {},
  methods: {
    initData() {
      this.tableData = mockData[this.defaultValue.idx];
      this.total = this.tableData.length;
    },
    handleConfirm() {
      this.handleClose();
    },
    handleClose() {
      this.showDialog = false;
    },
  },
};
</script>
  
<style lang="scss" scoped>
.tabPane {
  height: 60vh;
  overflow: auto;
  overflow-x: hidden;
  scrollbar-width: none;
  position: relative;
  margin-bottom: 20px;
}
.news {
  background: #fbfbfa;
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