<template>
  <div @click="$emit('itemCick')" class="headerCompany">
    <div class="icon">{{ icon }}</div>
    <div class="rightMain ellipsis">
      <div class="header">
        <el-tooltip :disabled="disabledNameTool" effect="dark" :content="info.name" placement="bottom" :open-delay="500">
          <div class="name ellipsis" @click="handleClick('name')">{{ info.name }}</div>
        </el-tooltip>
        <span class="type" v-if="info.status">{{info.status}}</span>
      </div>

      <el-tooltip :disabled="tags.length < 33 " effect="dark" :content="tags" placement="bottom" :open-delay="500">
        <p class="tags ellipsis"><span class="person" @click="handleClick('person')">{{ info.operName + ' | ' }}</span> {{ tags }}</p>
      </el-tooltip>
    </div>
  </div>
</template>
<script>
export default ({
  name: 'headerCompany',
  props: {
    info: Object
  },
  data() {
    return {}
  },
  computed: {
    disabledNameTool() {
      return this.info.name.length < 20
    },
    tags() {
      let { capital, startDate, pcdName } = this.info
      let arr = [capital, startDate, pcdName].filter(item => item)
      return arr.length > 0 ? arr.join(' | ').toString() : ''
    },
    icon() {
      let { name } = this.info
      let temp = name.replace(/\（|\）|\(|\)/g, '')
      return temp.substr(0, 4)
    },
  },
  methods: {
    handleClick(type) {
      this.$emit('handleClick', { type, data: this.info })
    }
  },
})
</script>
<style lang="scss" scoped>
.headerCompany {
  font-size: 12px;
  padding: 10px 5px;
  cursor: pointer;
  display: flex;
  align-items: center;
  .icon {
    width: 50px;
    height: 50px;
    flex-shrink: 0;
    font-size: 14px;
    background-color: #e5eeff;
    color: #1a53ff;
    border-radius: 4px;
    padding: 10px;
    margin-right: 4px;
    font-weight: 600;
  }
  .header {
    flex: 1;
    font-size: 14px;
    display: flex;
    align-items: center;
    margin-bottom: 8px;
    .name {
      flex: 1;
      font-weight: 600;
      &:hover {
        color: #1a53ff;
      }
    }
    span {
      font-size: 12px;
      color: #145a1e;
      border: 1px solid #145a1e;
      border-radius: 3px;
      margin-left: 3px;
      padding: 1px 3px;
    }
  }
  .tags {
    color: #76788b;
    .person {
      color: #1a53ff;
      font-weight: 500;
    }
  }
}
.ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>