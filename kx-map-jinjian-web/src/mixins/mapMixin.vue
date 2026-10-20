<script>
import kxMap from '@/utils/kxMap';
import axios from 'axios'
export default ({
  data() {
    return {
      olMap: null,
      road: null,
    }
  },
  methods: {
    //初始化地图 地图
    async initMapBase() {
      const {data:config} = await axios.get('/map.json')

      this.olMap = new kxMap.KMap('olMap', {}, { url: config.gg_satellite })
      this.road = new kxMap.BaseLayer(this.olMap, (x, y, z) =>
      config.bd_road.replace('{x}', x).replace('{y}', y).replace('{z}', z),
        {}, 'baiduRoad', '2'
      )
    },
  },
})
</script>