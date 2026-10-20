<template>
  <div @click="$emit('itemCick')" class="itemCompany">
    <div class="header">
      <div class="icon">{{ icon }}</div>
      <el-tooltip :disabled="disabledNameTool" effect="dark" :content="info.name" placement="bottom" :open-delay="500">
        <div class="name ellipsis">{{ info.name }}</div>
      </el-tooltip>
      <div class="business" v-if="info.boCount">{{ info.boCount }}<span>条商机</span> </div>
    </div>
    <el-tooltip :disabled="tags.length < 35 " effect="dark" :content="tags" placement="bottom" :open-delay="500">
      <p class="tags ellipsis">{{ tags }}</p>
    </el-tooltip>
    <div class="desc" v-if="info.clueType || (info.description && info.description !== '-')">
      <div v-if="info.clueTypeCode" class="type" :style="{'--color':typeColor[info.clueTypeCode]? typeColor[info.clueTypeCode].color : '#0d2eb7','--bgc':typeColor[info.clueTypeCode]?typeColor[info.clueTypeCode].bgc : '#f2f7ff'}">{{ info.clueType }}</div>
      <el-tooltip :disabled="!info.description" effect="dark" :content="info.description" placement="bottom" :open-delay="500">
        <span v-if="info.description && info.description !== '-'" class="ellipsis">{{ info.description }}</span>
      </el-tooltip>
    </div>
    <ul class="labels ellipsis" v-if="info.tags.length">
      <li v-for="label in showLabels" :key="label">{{ label }}</li>
    </ul>
  </div>
</template>
<script>
export default ({
  name: 'itemCompany',
  props: {
    info: Object
  },
  data() {
    return {
    }
  },
  computed: {
    disabledNameTool() {
      return (this.info.boCount && this.info.name.length < 22) || (!this.info.boCount && this.info.name.length < 25)
    },
    tags() {
      let { operName, regCapiDesc, startDate, pcdName } = this.info
      let arr = [operName, regCapiDesc, startDate, pcdName].filter(item => item)
      return arr.length > 0 ? arr.join(' | ').toString() : ''
    },
    icon() {
      return this.info.name.substr(0, 1)
    },
    typeColor() {
      let arr = [
        'XS015',
        'XS017',
        'XS021',
        'XS022',
        'XS023',
        'XS024',
        'XS025',
        'XS026',
        'XS027',
        'XS028',
        'XS031',
        'XS035',
        'XS040',
        'XS042',
        'XS046',
        'XS047',
        'XS061',
        'XS062',
        'XS063',
        'XS064',
        'XS081',
        'XS082']
      let obj = {}
      arr.map(item => {
        let ind = item.substr(3, 2)
        if (ind <= 25) {
          obj[item] = { color: '#0d2eb7', bgc: '#f2f7ff' }
        } else if (ind > 25 && ind <= 28) {
          obj[item] = { color: '#006d90', bgc: '#dff7f2' }

        } else if (ind > 28 && ind <= 62) {
          obj[item] = { color: '#d2362d', bgc: '#fff4eb' }
        } else if (ind > 62 && ind <= 64) {
          obj[item] = { color: '#7a1d04', bgc: '#fff8eb' }
        } else if (ind > 64) {
          obj[item] = { color: '#145a1e', bgc: '#f5fcf2' }
        }
      })
      return obj
      // [
      //   { color: '#0d2eb7', bgc: '#f2f7ff' },//<25
      //   { color: '#006d90', bgc: '#dff7f2' },// <28 25>
      //   { color: '#d2362d', bgc: '#fff4eb' },//<62 28>
      //   { color: '#7a1d04', bgc: '#fff8eb' },//<64  62>
      //   { color: '#145a1e', bgc: '#f5fcf2' },//  64>
      // ]
    },
    showLabels() {
      if (!this.info.tags || !this.info.tags.length) return []
      let { tags } = this.info
      let labels = []
      let len = 0

      for (let i = 0; i < tags.length; i++) {
        const element = tags[i].tag || tags[i].tagName;
        if (element.length + len > 25) {
          labels.push('...')
          break
        } else {
          labels.push(element)
          len += element.length
        }
      }
      return labels
    }
  }
})
</script>
<style lang="scss" scoped>
.itemCompany {
  font-size: 12px;
  padding: 10px 5px;
  cursor: pointer;
  &:hover {
    background-color: rgba(244, 245, 252, 0.6);
  }
  .header {
    font-size: 13px;
    display: flex;
    align-items: center;
    margin-bottom: 4px;
    .icon {
      background-color: #e5eeff;
      color: #1a53ff;
      border-radius: 3px;
      padding: 3px;
      margin-right: 4px;
      font-weight: 600;
    }
    .name {
      flex: 1;
      font-weight: 600;
    }
    .business {
      font-weight: 600;
      span {
        font-weight: 400;
        color: #76788b;
      }
    }
  }
  .tags {
    color: #76788b;
  }
  .desc {
    display: flex;
    align-items: center;
    margin: 10px 3px;
    .type {
      background-color: var(--bgc);
      color: var(--color);
      border-radius: 30px;
      padding: 2px 6px;
    }
    span {
      flex: 1;
    }
  }
  .labels {
    margin: 10px 3px 3px;
    display: flex;
    li {
      background-color: #f4f5fc;
      border-radius: 3px;
      margin-right: 3px;
      padding: 2px 6px;
    }
  }
}
.ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>