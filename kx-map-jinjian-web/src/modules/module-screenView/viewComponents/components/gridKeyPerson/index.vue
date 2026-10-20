<template>
  <div class="keyPersonBox">
    <div class="keyPersonBox-title">网格关键人</div>
    <rankList
      className="key-person"
      :columns="columns"
      :tableData="tableData"
      :rows="2"
      :background="background"
      :height="height"
      rowBorderRadius="4px"
      :intervalColor="intervalColor"
    >
    </rankList>
  </div>
</template>

<script>
import rankList from "../rankList";
export default {
  components: {
    rankList,
  },
  props: {
    tableData: {
      type: Array,
      default: () => [
        {
          name: "张三",
          sex: "男",
          phone: "15566667777",
          identity: "412678199509056789",
          address: "浙江省杭州市上城区稠银大厦",
          work: "办公",
        },
        {
          name: "张三",
          sex: "男",
          phone: "15566667777",
          identity: "412678199509056789",
          address: "浙江省杭州市上城区稠银大厦",
          work: "办公",
        },
        {
          name: "张三",
          sex: "男",
          phone: "15566667777",
          identity: "412678199509056789",
          address: "浙江省杭州市上城区稠银大厦",
          work: "办公",
        },
        {
          name: "张三",
          sex: "男",
          phone: "15566667777",
          identity: "412678199509056789",
          address: "浙江省杭州市上城区稠银大厦",
          work: "办公",
        },
      ],
    },
  },
  data() {
    return {
      background:
        "linear-gradient(180deg,rgba(7, 192, 219, 0.3),rgba(7, 192, 219, 0.8)) ",
      intervalColor: "rgba(150, 252, 253, 0.1)",
      height: 40,
      columns: [
        { label: "姓名", prop: "cruxName", flex: 1 },
        { label: "性别", prop: "cruxSex", flex: 1 },
        { label: "手机号码", prop: "cruxPhone", flex: 2 },
        { label: "证件号码", prop: "cruxIdentity", flex: 2 },
        { label: "地址", prop: "cruxAddress", flex: 3 },
        { label: "职务", prop: "cruxWork", flex: 1 },
      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.keyPersonBox {
  margin-bottom: 10px;
}
</style>