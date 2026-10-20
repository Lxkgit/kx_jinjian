<template>
  <div class="baseDisplay">
    <ul>
      <li v-for="(item,index) in columns" :key="index" :class="{formItem:true,liImage:item.type === 'image'}">
        <div class="label">{{ item.label }}: </div> 
        <div v-if="item.slot" class="value">
          <!-- <slot name="gridLabel"> </slot> -->
          <slot :name="item.prop" :data="data"/>
        </div>
        <ul v-else-if="item.type == 'image'" class="image">
          <li v-for="img in data[item.prop]" :key="img" class="imgItem">
            < img :src="img" alt="" @click="handleImageClick(img)">
          </li>
        </ul>
        <div v-else-if="item.formatter" class="ellipsis_value">{{ item.formatter(data[item.prop]) }} </div>
        <div v-else class="value">{{ data[item.prop] }}</div> 
      </li>
    </ul> 
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" append-to-body> 
      < img :src="showImg" class="showImage" alt="" >
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>
export default {
  name: "BaseInfo",
  components: {},
  props: {
    columns: { type: [Array, Object], required: true, default: () => {} },
    data: { type: Object, required: true, default: () => {} }
  },
  data() {
    return {
      dialogVisible: false,
      showImg: ""
    };
  },
  computed: {},
  created() {},
  methods: {
    handleImageClick(val) {
      this.showImg = val;
      this.dialogVisible = true;
      // console.log(val, this.dialogVisible)
    }
  }
};
</script>

<style lang="scss" scoped>
.showImage {
  width: 100%;
  height: 300px;
  object-fit: contain;
}
.dialog-footer {
  text-align: center;
}
.baseDisplay {
  // background-color: rgba(198, 203, 209, 0.3);
  padding: 5px 10px;
  overflow: hidden;
  border-radius: 3px;
  margin: 10px 0;
  li.formItem {
    display: flex;
    align-items: center;
    min-width: 50%;
    max-width: 100%;
    float: left;
    // font-size: 10px;
    line-height: 30px;
    .label {
      color: #333;
      white-space: nowrap;
    }
    .ellipsis_value {
      flex: 1;
      color: #000;
      text-overflow: ellipsis;
      overflow: hidden;
      white-space: nowrap;
      font-weight: 500;
      padding-left: 15px;
    }
    .value {
      line-height: 22px;
    }
    &.liImage {
      width: 100%;
    }
    .image {
      display: flex;
      flex-wrap: wrap;
      .imgItem {
        width: 90px;
        height: 90px;
        object-fit: contain;
        background-color: pink;
        border-radius: 5px;
        overflow: hidden;
        margin: 3px;
        img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>