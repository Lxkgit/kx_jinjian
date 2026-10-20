<template>
    <el-dialog title="动态详情" :visible.sync="dialogVisible" width="50vw" :before-close="handleClose" class="collect-class"
      :close-on-click-modal="false" :append-to-body="true">
      <div class="news-title">
        <div class="newsTitle">{{ messageDetails.title  }}</div>
        <span class="newsTime">发布时间：{{ messageDetails.time }}</span>
        <span class="newsDepartment">网点机构：{{ messageDetails.deptName }}</span>
      </div>
      <div class="news">
        <div class="newsImg">
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
          }
        },
        deep: true,
        immediate: true,
      },
    },
    mounted() { },
    methods: {
      handleClose() {
        this.dialogVisible = false;
      },
    },
  };
  </script>
  
  <style lang="scss" scoped>
  ::v-deep .el-dialog__header {
    padding:20px;
    border-bottom: 1px solid #eee;

  }
  ::v-deep .el-dialog__body{
    padding: 16px 24px;
  }
  .news-title {
    .newsTitle {
      font-weight: bold;
      font-size: 16px;
      color: #323548;
      margin-bottom: 8px;
    }
    .newsTime {
        font-weight: 400;
        font-size: 14px;
        color: #898EAC;
      }
    .newsDepartment {
        font-weight: 400;
        font-size: 14px;
        color: #898EAC;
        margin-left: 60px;
    }
  
  
  }
  .news {
    background: #F6F7FA;
    border-radius: 4px;
    margin-top: 12px;
    padding: 16px;
  
   
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