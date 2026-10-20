<template>
  <buttonOperation :src="src" :direction="'right'">
    <div class="map-select">
      <div class="img-bg" :class="{ unselected: !item.active }" v-for="item in selectObj"
           :key="item.value" @click="changeSelected(item.value)">
        <img :src="item.src" alt="" />
      </div>
    </div>
  </buttonOperation>
</template>

<script>
import buttonOperation from "../common/buttonOperation";
export default {
  components: {
    buttonOperation,
  },
  data() {
    return {
      src: require("@/modules/module-screenView/assets/image/map-tile.png"),
      selectObj: [
        { src: require("../../assets/image/mapSelect1.png"), value: '1', active: false },
        { src: require("../../assets/image/mapSelect2.png"), value: '2', active: false },
      ],
      actvieType: null
    };
  },
  mounted() {
    this.changeSelected('2')
  },
  methods: {
    changeSelected(value) {
      this.actvieType = value
      for (let item of this.selectObj) {
        item.active = this.actvieType == item.value
      }
      this.$store.commit('screenView/SET_MAP_TYPE', value)
    },
  },
};
</script>

<style lang="scss" scoped>
.map-select {
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: rgba($color: #0d191e, $alpha: 0.8);
  padding: 8px;
  border-radius: 6px;
  position: relative;
  top:-35px;
  .img-bg {
    border: 3px solid #48f2f0;
    &:not(:last-child) {
      margin-right: 6px;
    }
    width: 97px;
    height: 65px;
    img {
      width: 100%;
      height: 100%;
    }
    &.unselected {
      border: none;
    }
  }
}
</style>