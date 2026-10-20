<template>
  <div>
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane
        v-for="item in tabList"
        :key="item.value"
        :label="item.name"
        :name="item.value"
      >
        <tabMessage
          :activeName="activeName"
          :dataInfo="dataInfo"
          @messageDetailsClick="messageDetailsClick"
        ></tabMessage>
        <div class="page">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="100"
            layout="total, sizes, prev, pager, next, jumper"
            :total="10"
          >
          </el-pagination>
        </div>
      </el-tab-pane>
    </el-tabs>
    <messageDetails
      v-model="detailsShow"
      :messageDetailsInfo="messageDetailsInfo"
      @location="(id)=>$emit('location',id)"
    ></messageDetails>
  </div>
</template>

<script>
import mockData from "@/modules/module-screenView/components/trends/mock/data";
import tabMessage from "../cell/tabMessage.vue";
import messageDetails from "@/modules/module-screenView/components/messageDetails/messageDetails.vue";
export default {
  components: {
    messageDetails,
    tabMessage,
  },
  data() {
    return {
      dataInfo: mockData,
      activeName: "1",
      tabList: [
        {
          name: "已读",
          value: "1",
        },
        {
          name: "未读",
          value: "2",
        },
      ],
      detailsShow: false,
      messageDetailsInfo: {},
    };
  },
  methods: {
    messageDetailsClick(item) {
      this.messageDetailsInfo = item;
      this.detailsShow = true;
    },
    handleClick(tab) {
      this.activeName = tab.name;
    },
    handleSizeChange() {},
    handleCurrentChange() {},
  },
};
</script>

<style scoped lang="scss">
.page{
    margin-top: 15px;
    display: flex;
    justify-content: flex-end;
}
</style>