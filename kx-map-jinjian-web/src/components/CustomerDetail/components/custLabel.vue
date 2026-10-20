<template>
  <div style="font-size: 16px">
    <div class="system-label laebl-box">
      <div class="label">系统标签:</div>
      <div class="tags-mesh">
        <el-tag class="item-tag" v-for="(item, index) in systemList" :key="index" style="backgroundColor: rgba(216,229,246,.37);color:#00B7FF;borderColor:#00B7FF">{{ item }}</el-tag>
      </div>
    </div>
  </div>
</template>

<script> 
export default {
  name: "custLabel",
  props: {
    textObj: {
      type: Object,
      default: () => {
        return {
          text: "tagName",
          id: "",
        };
      },
    },
    systemText: {
      type: String,
      default: 'tagName'
    },
    systemList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      systemLabel: [
        {
          tagName: "一星客户",
        },
        {
          tagName: "一星客户",
        },
        {
          tagName: "一星客户",
        },
        {
          tagName: "一星客户",
        },
      ],
      meshLabel: [
        {
          tagName: "一星客户1",
          closable: false,
        },
        {
          tagName: "一星客户2",
          closable: false,
        },
        {
          tagName: "一星客户3",
          closable: false,
        },
        {
          tagName: "一星客户4",
          closable: false,
        },
      ],
      cloneLabel: [],
      tagValue: "",
      editChoose: false,
      options: [
        {
          value: "黄金糕",
          label: "黄金糕",
        },
      ],
    };
  },
  methods: {
    edit() {
      this.editChoose = true;
      this.cloneLabel = { ...this.meshLabel };
      this.meshLabel.forEach((item) => {
        item.closable = true;
      });
    },
    handleClose(e) {
      this.meshLabel.splice(e, 1);
    },
    chooseTag(e) {
      const obj = { ...this.meshLabel[0] };
      obj[this.textObj.text] = e;
      let tagList = this.meshLabel.map((item) => item[this.textObj.text])
      if (tagList.indexOf(e) == -1) {
        this.meshLabel.push(obj);
      } else {
        this.$message({
          message: '该标签已添加',
          type: 'warning'
        })
      }

    },
    cancel() {
      this.meshLabel = { ...this.cloneLabel };
      this.cloneLabel = [];
      this.editChoose = false;
    },
    success() {
      this.editChoose = false;
      this.meshLabel.forEach((item) => {
        item.closable = false
      })
      this.$emit("success", this.meshLabel);
    },
  },
};
</script>

<style lang="scss" scoped>
.laebl-box {
  margin: 10px;
}
.edit-label {
  display: flex;
}
.system-label {
  display: flex;
}
.label {
  min-width: 80px;
  font-size: 16px;
  line-height: 34px;
  color: #333333;
}
.tags-mesh {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  .item-tag {
    margin-bottom: 8px;
    margin-right: 10px;
    height: 34px;
    line-height: 34px;
    border: 0.5px solid #00b7ff;
  }
}
.btn-box {
  height: 34px;
  line-height: 34px;
  min-width: 150px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  .btn {
    cursor: pointer;
  }
  .success {
    color: #00b5fd;
  }
  .cancel {
    color: #333333;
  }
}
.edit {
  height: 34px;
  line-height: 34px;
  min-width: 150px;
  color: #00b5fd;
}
</style>