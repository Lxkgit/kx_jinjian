<template>
  <IDialog title="锚点编辑" v-model="showDialog" width="50%" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <div class="map-wrapper">
      <div id="olMap"></div>
    </div>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import kxMap from '@/utils/kxMap';
import * as turf from '@turf/turf';
export default ({
  mixins: [dialogMixin],
  props: {
    coordinate: {
      type: Array,
    },
  },
  data() {
    return {
      currentCoordinate: [],
    }
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.initMap()
        this.getAnchorDetail()
      })
    },
    initMap() {
      this.olMap = new kxMap.KMap('olMap', {
        center: [120.7, 31.65],
        zoom: 16,
        minZoom: 3,
        maxZoom: 20
      },
      )
      this.pointLayer = new kxMap.MarkerLayer(this.olMap, { zIndex: 2 }, 'point')

      this.pointLayer.addSelect()
      this.pointLayer.addModify()

      this.control = new this.olMap.BaseViewControl(this.olMap)

    },
    handleClose() {
      this.olMap && this.olMap.destroy()
    },
    handleConfirm() {
      const [point] = this.pointLayer.getDataCoordinates();
      this.$emit("mapEditHandle", point);
      this.showDialog = false;
      this.handleClose();
    },
    getAnchorDetail() {
      this.currentCoordinate = this.coordinate
      if (!this.currentCoordinate || this.currentCoordinate.length === 0) {
        this.currentCoordinate = [120.7, 31.65];
      }
      const point = turf.point(this.currentCoordinate, {
        name: "",
        type: null,
        src: require("@/modules/module-gridManagement/views/anchorManagement/assets/building.png")
      });
      this.pointLayer.addGeoFeature(point);
      this.control.move(this.currentCoordinate);

    },
  },
})
</script>

<style lang="scss" scoped>
.map-wrapper {
  width: 100%;
  height: 70vh;
  #olMap {
    width: 100%;
    height: 70vh;
  }
}
</style>