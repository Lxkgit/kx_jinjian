<template>
  <div v-if="showRecordList">
    <div class="record-list">
      <div class="record-list-top" :class="{'showClose': showClose }" v-if="showCloseV">
        <div class="record-list-top-text" @click="close">关闭</div>
      </div>
      <div class="record-list-title" >
        <div
          class="record-list-title-item"
          :style="{flex:`${item.flex||1}`}"
          v-for="(item, index) in titleList"
          :key="index"
        >
          {{ item.name }}
        </div>
      </div>
      <div class="record-list-content">
        <div
          class="record-list-content-item"
          v-for="(item, index) in contentList"
          :key="index"
        >
          <div
            class="record-list-content-item-son"
            v-for="(it, i) in titleList"
            :style="{flex:`${titleList[i].flex||1}`}"
            :key="i"
          > 
            <div class="rlci-normal" v-if="it.type == 'text' || !it.type">
              {{ item[it.prop] }}
            </div>
            <div class="rlci-normal" v-if="it.type == 'select'">
              {{ it.formatter(item[it.prop]) }} 
            </div>
            <div
              :style="{ color: it.color }"
              class="rlci-normal"
              v-if="it.type == 'money'"
            >
              ￥{{ item[it.prop] }}
            </div>
            <div
              @click="ops(item)"
              style="cursor: pointer"
              :style="{ color: it.color }"
              :class="{'rlci-normal':true,'disabled':item.visitType == '1'}"
              v-if="it.type == 'operation'"
            >
              {{ item[it.prop] }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  props: {
    showClose: {
      type: Boolean,
      default:false,
    },
    showCloseV:{
      type: Boolean,
      default:true,
    },
    showRecordList: {
      type: Boolean,
      default: false,
    },
    titleList: {
      type: Array,
      default: () => {
        return [
          {
            name: "财富名称",
            prop: "name",
          },
          {
            name: "办理时间",
            prop: "time",
          },
          {
            name: "金钱",
            type: "money",
            prop: "money",
            color: "pink",
          },
          {
            name: "操作",
            type: "operation",
            prop: "ops",
            color: "blue",
          },
        ];
      },
    },
    contentList: {
      type: Array,
      default: () => {
        return [
          {
            name: "asdasda",
            time: "超出超出超出超出超出超出超出超出超出超出",
            money: "999",
            ops: "查看详情",
          },
          {
            name: "asdasda",
            time: "超出超出超出超出超出超出超出超出超出超出",
            money: "131313",
            ops: "查看详情",
          },
        ];
      },
    },
  },
  data() {
    return {};
  },
  methods: {
    close() {
      this.$emit("update: showRecordList", false);
    },
    ops(e) {
      if(e.visitType == '1')return 
      this.$emit('ops', e )
    }
  },
};
</script>

<style lang="scss" scoped>
@mixin flex {
  display: flex;
  justify-content: center;
  align-items: center;
}
.showClose{
  visibility: hidden;
}
.record-list {
  &-top {
    align-items: center;
    background-color: rgba($color: #e8e0d7, $alpha: 0.3);
    height: 32px;
    display: flex;
    justify-content: flex-end;
    &-text {
      cursor: pointer;
      margin-right: 20px;
      font-size: 14px;
      color: #999999;
    }
  }
  &-title {
    display: flex;
    height: 30px;
    background-color: rgba($color: #e8e0d7, $alpha: 1);
    &-item {
      @include flex;

      flex: 1;
    }
  }
  &-content {
    &-item:nth-child(2n-1) {
      background-color: rgba($color: #e8e0d7, $alpha: 0.3);
    }
    &-item {
      display: flex;

      &-son {
        @include flex;
        min-height: 30px;
        flex: 1;
      }
    }
  }
}
.disabled{
  color: #ccc!important;
}
</style>