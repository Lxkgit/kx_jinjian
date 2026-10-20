<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <table-title title="网格编辑" class="title" fontSize="16px"></table-title>
    </template>
    <template #operate>
      <el-form ref="gridForm" :model="baseForm" :disabled="disabled||mapStatus==='1'"
               label-width="100px" label-position="left">
        <mapFormItem v-model="baseForm" @changeHandle="baseChangeHandle"
                     @focusHandle="baseFocusHandle" @blurHandle="baseBlurHandle"
                     :orgOptions="orgOptions" :mapStatus="mapStatus"></mapFormItem>
      </el-form>
    </template>
    <template #footer>
      <i-button-bar size="small" :plain="false" :btn-lists="btnLists" @click="clickHandle"
                    ref="btnBar" justifyContent="space-around">
      </i-button-bar>
    </template>
  </mapContent>
</template>

<script>
import mapContent from '@/modules/module-gridManagement/components/mapContent';
import operateTitle from '@/modules/module-gridManagement/components/operateTitle';
import tableTitle from "@/modules/module-gridManagement/components/tableTitle.vue";
import subTitle from '@/modules/module-gridManagement/components/subTitle';
import mapFormItem from '@/modules/module-gridManagement/components/mapFormItem';
import kxMap from '@/utils/kxMap';
import * as turf from '@turf/turf';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
import mapMixin from '@/mixins/mapMixin.vue';

export default ({
  components: {
    mapContent,
    operateTitle,
    tableTitle,
    subTitle,
    mapFormItem
  },
  mixins: [mapMixin],
  props: {
    mapData: {
      type: Object
    },
    orgOptions: {
      type: Array,
    },
    mapStatus: {
      type: String,
    },
  },
  data() {
    return {
      baseForm: {},
      disabled: false,
      btnLists: [
        {
          cname: '确定',
          ename: 'confirmHandle',
          type: 'primary',
          isShow: () => this.mapStatus === '0',
          disabled: () => this.disabled || this.mapData.disabled
        },
        {
          cname: '返回',
          ename: 'cancelHandle',
        },
      ],
      viewOptions: {
        padding: [30, 330, 30, 30],
        duration: 300
      },
    }
  },
  watch: {
    mapData: {
      handler(value, o) {
        this.$nextTick(() => {
          this.initPolygon()
        })
      },
      deep: true,
      immediate: true,
    }
  },
  mounted() {
    this.initMap()
  },
  methods: {
    async initMap() {
      await this.initMapBase()
      this.baseLayer = new kxMap.DrawLayer(this.olMap, { zIndex: 2 })
      this.control = new this.olMap.BaseViewControl(this.olMap)
    },
    initPolygon() {
      if (JSON.stringify(this.mapData) === '{}') {
        this.baseForm = {}
        this.baseLayer.clear()
        this.disabled = true
        this.$refs.gridForm.resetFields();
        return
      }
      this.disabled = false
      const { code, typeCode } = this.mapData
      const param = {
        areaCode: code,
        typeCode,
        jurisdiction: 0
      }
      service.getCoordinatesByCode(param).then(res => {
        this.baseForm = JSON.parse(JSON.stringify(this.mapData))
        const { data } = res

        this.basePolygon = data

        this.baseLayer.clear()
        this.baseLayer.addGeoFeature(this.basePolygon)
        const extent = turf.bbox(this.basePolygon)

        this.control.setView(extent, this.viewOptions)
      })
    },
    baseChangeHandle(info) {
      const { name } = info
      turf.propEach(this.basePolygon, function (currentProperties) {
        currentProperties.name = name
      });
      this.baseLayer.clear();
      this.baseLayer.addGeoFeature(this.basePolygon)
    },
    baseFocusHandle() {
      turf.propEach(this.basePolygon, function (currentProperties) {
        currentProperties.fillColor = [255, 0, 0, 0.2]
      });
      this.baseLayer.clear();
      this.baseLayer.addGeoFeature(this.basePolygon)
    },
    baseBlurHandle() {
      turf.propEach(this.basePolygon, function (currentProperties) {
        currentProperties.fillColor = [0, 0, 0, 0.2]
      });
      this.baseLayer.clear();
      this.baseLayer.addGeoFeature(this.basePolygon)
    },
    clickHandle(type) {
      this[type].call()
    },
    cancelHandle() {
      this.$router.back()
    },
    confirmHandle() {
      if (this.disabled || this.mapStatus === '1') {
        return
      }
      this.$refs.gridForm.validate((v) => {
        if (v) {
          this.disabled = true
          service.updateGrid(this.baseForm).then(res => {
            this.$modal.msgSuccess('编辑成功')
            this.disabled = false
            this.$emit('clearTree', false)
            // this.$emit('cancelHandle', this.baseForm)
          }).catch(() => {
            this.$modal.msgError('编辑失败')
            this.disabled = false
            this.$emit('cancelHandle', this.baseForm)
          })
        }
      })

    },
  },
})
</script>

<style lang="scss" scoped>
#olMap {
  width: 100%;
  height: 100%;
}
.title {
  padding: 12px;
  margin-bottom: 16px;
}
</style>