<script>
import fillingEdit from '@/modules/module-gridManagement/components/fillingEdit';
export default ({
  components: {
    fillingEdit
  },
  data() {
    return {
      showEdit: false,
      fillingInfo: null,
    }
  },
  methods: {
    buildHandle(data) {
      this.fillingInfo = data
      this.showEdit = true
    }
  },
})
</script>