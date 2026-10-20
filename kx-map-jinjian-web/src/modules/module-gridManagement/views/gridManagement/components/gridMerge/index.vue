<template>
  <mapContent>
    <div id="olMap" slot="map"></div>
    <template #header>
      <operateTitle title="网格合并"></operateTitle>
    </template>
    <template #operate>
      <subTitle title="选择网格"></subTitle>
      <div class="select-wrapper">
        <el-select
          v-model="selectGrid"
          multiple
          @change="selectChangeHandle"
          collapse-tags
        >
          <el-option
            v-for="item in gridOptions"
            :key="item.code"
            :value="item.code"
            :label="item.name"
          ></el-option>
        </el-select>
        <el-button type="primary" class="ml10" @click="view">合并</el-button>
      </div>
      <subTitle title="网格信息"></subTitle>
      <el-form ref="gridForm" :model="subForm" label-width="100px">
        <div
          class="sub-item"
          v-for="(item, index) in subForm.gridList"
          :key="index"
        >
          <mapFormItem
            preName="gridList."
            :index="`${index}.`"
            v-model="subForm.gridList[index]"
            :orgOptions="orgOptions"
            @changeHandle="(info) => changeHandle(info, index)"
            @focusHandle="(info) => focusHandle(info, index)"
            @blurHandle="blurHandle"
          >
          </mapFormItem>
        </div>
      </el-form>
    </template>
    <template #footer>
      <i-button-bar
        size="small"
        :plain="false"
        :btn-lists="btnLists"
        @click="clickHandle"
        ref="btnBar"
        justifyContent="space-around"
      >
      </i-button-bar>
    </template>
  </mapContent>
</template>

<script>
import mapContent from "@/modules/module-gridManagement/components/mapContent";
import operateTitle from "@/modules/module-gridManagement/components/operateTitle";
import subTitle from "@/modules/module-gridManagement/components/subTitle";
import mapFormItem from "@/modules/module-gridManagement/components/mapFormItem";
import kxMap from "@/utils/kxMap";
import * as turf from "@turf/turf";
import service from "@/modules/module-gridManagement/views/gridManagement/api";
import mapMixin from "@/mixins/mapMixin.vue";

export default {
  components: {
    mapContent,
    operateTitle,
    subTitle,
    mapFormItem,
  },
  mixins: [mapMixin],
  props: {
    mapData: {
      type: Object,
    },
    orgOptions: {
      type: Array,
    },
  },
  data() {
    return {
      subForm: {
        gridList: [],
      },
      gridOptions: [],
      selectGrid: [],
      selectIndex: [],
      btnLists: [
        {
          cname: "确定",
          ename: "confirmHandle",
          type: "primary",
          disabled: () => this.disabled,
        },
        {
          cname: "取消",
          ename: "cancelHandle",
        },
      ],
      disabled: false,
      viewOptions: {
        padding: [30, 330, 30, 30],
        duration: 300,
      },
    };
  },
  watch: {
    mapData: {
      handler(value, o) {
        this.$nextTick(() => {
          this.initPolygon();
        });
      },
      deep: true,
      immediate: true,
    },
  },
  mounted() {
    this.initMap();
  },
  methods: {
    async initMap() {
      await this.initMapBase();
      this.mergeLayer = new kxMap.PolygonLayer(
        this.olMap,
        { zIndex: 2 },
        "merge"
      );

      this.control = new this.olMap.BaseViewControl(this.olMap);
    },
    initPolygon() {
      const { code, typeCode } = this.mapData;
      const param = {
        areaCode: code,
        jurisdiction: 1,
        typeCode,
      };
      const infoParam = {
        typeCode,
        parentCode: code,
      };
      service.getCoordinatesByCode(param).then((res) => {
        const { data } = res;
        this.basePolygon = data;
        this.mergeLayer.clear();
        this.subForm.gridList = [];
        this.mergeLayer.addGeoFeature(this.basePolygon);
        this.mergeLayer.setExtent();
        this.control.setView(this.mergeLayer.extent, this.viewOptions);
      });

      service.getPaAddrInfoTypeList(infoParam).then((res) => {
        const { data } = res;
        this.gridOptions = data;
      });
    },
    selectChangeHandle(info) {
      this.subForm.gridList = [];
      this.mergePolygon = null;

      this.selectIndex = [];
      const _ = this;
      turf.propEach(
        this.basePolygon,
        function (currentProperties, featureIndex) {
          if (info.includes(currentProperties.code)) {
            currentProperties.fillColor = [255, 0, 0, 0.2];
            _.selectIndex.push(featureIndex);
          } else {
            currentProperties.fillColor = [0, 0, 0, 0.2];
          }
        }
      );
      this.mergeLayer.clear();
      this.mergeLayer.addGeoFeature(this.basePolygon);
      this.mergeLayer.setExtent();
      this.control.setView(this.mergeLayer.extent, this.viewOptions);
    },
    changeHandle(info) {
      const { name } = info;
      turf.propEach(this.mergePolygon, function (currentProperties) {
        if (currentProperties.ifUnion) {
          currentProperties.name = name;
        }
      });
      this.mergeLayer.clear();
      this.mergeLayer.addGeoFeature(this.mergePolygon);
    },
    focusHandle(info, index) {
      turf.propEach(this.mergePolygon, function (currentProperties) {
        if (currentProperties.ifUnion) {
          currentProperties.fillColor = [255, 0, 0, 0.2];
        } else {
          currentProperties.fillColor = [0, 0, 0, 0.2];
        }
      });
      this.mergeLayer.clear();
      this.mergeLayer.addGeoFeature(this.mergePolygon);
    },
    blurHandle() {
      turf.propEach(this.mergePolygon, function (currentProperties) {
        currentProperties.fillColor = [0, 0, 0, 0.2];
      });
      this.mergeLayer.clear();
      this.mergeLayer.addGeoFeature(this.mergePolygon);
    },
    confirmHandle() {
      if (!this.mergePolygon) {
        this.$modal.msgError("请合并所选网格");
        return;
      }
      this.$refs.gridForm.validate((v) => {
        if (v) {
          let coordinates = null;
          turf.featureEach(
            this.mergePolygon,
            function (currentFeature, featureIndex) {
              const { properties } = currentFeature;
              if (properties.ifUnion) {
                const geoData = turf.getGeom(currentFeature);
                coordinates =
                  geoData.type === "Polygon"
                    ? [geoData.coordinates]
                    : geoData.coordinates;
              }
            }
          );
          const param = {
            ...this.subForm.gridList[0],
            code: this.selectGrid.join(","),
            coordinates: JSON.stringify(coordinates),
          };
          this.disabled = true;
          service
            .mergeChildGrid(param)
            .then(() => {
              this.$modal.msgSuccess("合并成功");
              this.disabled = false;
              this.$emit("clearTree", true);
              // this.cancelHandle()
            })
            .catch(() => {
              this.disabled = false;
            });
        }
      });
    },
    cancelHandle() {
      this.$emit("cancelHandle", this.mapData);
    },
    view() {
      if (this.selectIndex.length === 0) {
        this.$modal.msgWarning("请选择网格");
        return;
      }

      const { code, addrLevel } = this.mapData;
      const obj = {
        ...this.mapData,
        name: "",
        parentCode: code,
        addrLevel: addrLevel + 1,
      };
      this.subForm.gridList = [obj];

      let unionGeo = null;
      let selectFeature = [];
      let unselectFeature = [];
      const _ = this;
      turf.featureEach(
        this.basePolygon,
        function (currentFeature, featureIndex) {
          if (_.selectIndex.includes(featureIndex)) {
            selectFeature.push(currentFeature);
          } else {
            unselectFeature.push(currentFeature);
          }
        }
      );
      unionGeo = selectFeature.reduce((pre, cur) => {
        return turf.union(pre, cur);
      });
      turf.propEach(unionGeo, function (currentProperties, featureIndex) {
        currentProperties.ifUnion = true;
      });
      const collection = turf.featureCollection([...unselectFeature, unionGeo]);
      this.mergePolygon = collection;
      this.mergeLayer.clear();
      this.mergeLayer.addGeoFeature(this.mergePolygon);
      this.mergeLayer.setExtent();
      this.control.setView(this.mergeLayer.extent, this.viewOptions);
    },

    clickHandle(type) {
      this[type].call();
    },
  },
};
</script>

<style lang="scss" scoped>
#olMap {
  width: 100%;
  height: 100%;
}
.select-wrapper {
  display: flex;
}
.ml10 {
  margin-left: 10px;
}
</style>