<template>
  <div class="info-wrapper">

  </div>
</template>

<script>
export default {
  components: {

  },
  data() {
    return {

    };
  },
  computed: {
    customForm() {
      return this.$store.getters["customForm"];
    },
  },
  watch: {

  },
  methods: {

  },
};
</script>

<style lang="scss" scoped>
.info-wrapper {
  height: 78vh;
  overflow: auto;
  overflow-x: hidden;
  position: relative;
}
</style>