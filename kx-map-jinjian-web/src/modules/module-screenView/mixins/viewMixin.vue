<script>
import { autoScaleFun } from '@/modules/module-screenView/directive/autoScale.js'
import ScreenHead from '@/modules/module-screenView/cell/screenHead'
import ScreenLeft from '@/modules/module-screenView/cell/ScreenLeft'
import ScreenRight from '@/modules/module-screenView/cell/ScreenRight'
export default ({
  directives: {
    autoScale: {
      inserted: autoScaleFun,
    },
  },
  components: {
    ScreenHead,
    ScreenLeft,
    ScreenRight,
  },
  data() {
    return {
      leftShow: true,
      rightShow: true,
    }
  },
  watch: {
    leftShow: {
      handler(val) {
        this.$store.commit('screenView/SET_LEFT_HIDE', val)

      },
      immediate: true
    },
    rightShow: {
      handler(val) {
        this.$store.commit('screenView/SET_RIGHT_HIDE', val)
      },
      immediate: true
    },
  },
  mounted() {

  },
  methods: {
    // 两侧收缩
    turnSwitch(position) {
      console.log(position)
      this[`${position}Show`] = !this[`${position}Show`]
      // if (this.rightShow == true) {
      //   this.$store.commit('screenView/SET_RIGHT_FLAG', false)
      // }
    },
  },
})
</script>