 <template>
  <div @click="$emit('itemCick')" class="itemOpportunity">
    <div class="header">
       <div v-if="info.clueTypeCode" class="type" :style="{'--color':typeColor[info.clueTypeCode]? typeColor[info.clueTypeCode].color : '#0d2eb7','--bgc':typeColor[info.clueTypeCode]?typeColor[info.clueTypeCode].bgc : '#f2f7ff'}">{{ info.clueType }}</div>
      <div class="header-right">
        <div class="star">
          <span v-for="item in 5" :key="item" :class="['el-icon-star-on',item <= info.star && 'bright']"></span>
        </div>
        <div class="devision">|</div>
        <div class="date"> {{info.date}} </div>
      </div>
    </div>
    <div class="desc">{{ info.desc }}</div>
  </div>
</template>
<script>
export default ({
  name: 'itemOpportunity',
  props: {
    info: Object
  },
  data() {
    return { 
    }
  },
  computed: { 
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
      // return [
      //   { color: '#0d2eb7', bgc: '#f2f7ff' },
      //   { color: '#006d90', bgc: '#dff7f2' },
      //   { color: '#d2362d', bgc: '#fff4eb' },
      //   { color: '#7a1d04', bgc: '#fff8eb' },
      //   { color: '#145a1e', bgc: '#f5fcf2' },
      // ]
    },
  },
  methods: {
  },
})
</script>
<style lang="scss" scoped>
.itemOpportunity {
  font-size: 12px;
  padding: 10px 5px;
  &:hover {
    background-color: rgba(244, 245, 252, 0.6);
  }
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .header-right {
      display: flex;
      .star {
        color: #dfdfdf;
        .bright {
          color: #ffc300;
        }
      }
      .devision {
        color: #76788b;
        margin: 0 5px;
      }
      .date {
        color: #76788b;
      }
    }
  }
  .type {
    background-color: var(--bgc);
    color: var(--color);
    border-radius: 30px;
    padding: 2px 6px;
  }
  .desc {
    font-size: 13px;
    line-height: 18px;
    margin-top: 5px;
  }
}
</style>