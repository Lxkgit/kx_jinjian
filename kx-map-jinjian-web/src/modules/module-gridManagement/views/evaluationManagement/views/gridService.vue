<template>
  <PageContent>
    <div class="gridBox">
      <div class="left">
        <div class="left-one">
          <h2>网络统计</h2>
          <div class="leftOneList">
            <div class="leftOneItem" v-for="(item, index) in list" :key="index">
              <div>{{ item.value }}</div>
              <div>{{ item.label }}</div>
            </div>
          </div>
        </div>
        <div class="left-two">
          <div class="events">
            <h2>网格中投诉事件</h2>
            <div class="leftOneList">
              <div
                class="leftOneItem"
                v-for="(item, index) in countsList"
                :key="index"
              >
                <div>{{ item.value }}</div>
                <div>{{ item.label }}</div>
              </div>
            </div>
            <div style="text-align: right">
              <i class="sort el-icon-d-caret"></i>
            </div>
            <div style="overflow: auto; height: calc(100% - 150px)">
              <div class="complaint" v-for="item in rankList" :key="item.name">
                <div style="display: flex; justify-content: space-between">
                  <h2>{{ item.name }}</h2>
                  <!-- <span>未处理</span> -->
                </div>
                <infoList
                  class="infoList"
                  :infoData="getInfoDataForItem(infoData, item)"
                  :gridColumns="1"
                ></infoList>
              </div>
            </div>
          </div>
          <div class="counts">
            <div class="counts-title">
              <h2>网格商户数量排行</h2>
              <i class="sort el-icon-d-caret"></i>
            </div>
            <div style="overflow: auto; height: calc(100% - 20px)">
              <div
                class="complaint"
                v-for="(item, index) in rankList2"
                :key="item.name"
              >
                <div style="display: flex; justify-content: space-between">
                  <h2>{{ item.name }}</h2>
                  <span>{{ index + 1 }}</span>
                </div>
                <infoList
                  class="infoList"
                  :infoData="getInfoDataForItem(infoData2, item)"
                  :gridColumns="1"
                ></infoList>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="right">
        <div class="right-one">
          <h2>网格中宣传活动</h2>
          <div class="filter">
            <el-input
              style="margin-top: 8px"
              placeholder="请输入网格名称"
            ></el-input>
            <i class="sort el-icon-d-caret"></i>
          </div>
          <div style="overflow: auto; height: calc(100% - 60px)">
            <div
              class="publicity"
              v-for="(item, index) in rankList"
              :key="item.name"
            >
              <div style="display: flex; justify-content: space-between">
                <h2>{{ item.name }}</h2>
              </div>
              <infoList
                class="infoList"
                :infoData="infoData1"
                :gridColumns="1"
              ></infoList>
            </div>
          </div>
        </div>
        <div class="right-two">
          <h2>未设置责任网点的网格</h2>
          <div style="margin: 5px 0; font-size: 14px">总计：9</div>
          <i-table
            align="center"
            ref="table"
            :data="tableData"
            stripe
            :allColumns="columns"
            :showPagination="false"
            rowKey="id"
            :defaultExpandAll="true"
            :maxHeight="maxHeight"
          >
            <template #action="scope">
              <i-table-btns
                :btn-lists="tableBtn"
                @click="(type) => clickHandle(type, scope.row)"
                :data="scope.row"
              >
              </i-table-btns>
            </template>
          </i-table>
        </div>
        <div class="right-three">
          <h2>未设置现金服务点的网格</h2>
          <div style="margin: 5px 0; font-size: 14px">总计：10</div>
          <i-table
            align="center"
            ref="table"
            :data="tableData1"
            stripe
            :allColumns="columns"
            :showPagination="false"
            rowKey="id"
            :defaultExpandAll="true"
            :maxHeight="maxHeight"
          >
            <template #action="scope">
              <i-table-btns
                :btn-lists="tableBtn"
                @click="(type) => clickHandle(type, scope.row)"
                :data="scope.row"
              >
              </i-table-btns>
            </template>
          </i-table>
        </div>
      </div>
    </div>
  </PageContent>
</template>

<script>
import infoList from "@/components/BaseDetail/components/infoList.vue";
import services from "@/modules/module-gridManagement/views/gridManagement/api";
export default {
  components: {
    infoList,
  },
  data() {
    return {
      list: [
        {
          label: "现金服务网格数",
          value: 4806,
        },
        {
          label: "网格中现金服务点数量",
          value: 54,
        },
        {
          label: "网格中农村金融服务站数量",
          value: 693,
        },
        {
          label: "网格中商户数量",
          value: 456743,
        },
      ],
      countsList: [
        {
          label: "总投诉数量",
          value: 563,
        },
        {
          label: "已处理投诉数量",
          value: 233,
        },
        {
          label: "未处理投诉数量",
          value: 330,
        },
      ],
      rankList: [
        {
          name: "西市街社区居委会",
          peo: "20",
          type: "12",
          time: "8",
          dict: "10",
        },
        {
          name: "桃园社区居委会",
          peo: "18",
          type: "9",
          time: "9",
          dict: "10",
        },
        {
          name: "上浮桥社区居委会",
          peo: "16",
          type: "8",
          time: "8",
          dict: "8",
        },
        {
          name: "旌孝街社区居委会",
          peo: "12",
          type: "6",
          time: "6",
          dict: "6",
        },
      ],
      rankList2: [
        {
          name: "西市街社区居委会",
          num: "430",
          org: "农业银行金华江北支行",
          time: "2024-11-13",
        },
        {
          name: "桃园社区居委会",
          num: "100",
          org: "农业银行金华城西支行",
          time: "2024-11-13",
        },
        {
          name: "上浮桥社区居委会",
          num: "67",
          org: "金华银行股份有限公司三江支行",
          time: "2024-11-13",
        },
        {
          name: "旌孝街社区居委会",
          num: "50",
          org: "金华银行股份有限公司婺西支行",
          time: "2024-11-13",
        },
      ],
      infoData: [
        { label: "总投诉数量", value: "", key: "peo" },
        { label: "拒收现金投诉数量", value: "", key: "type" },
        { label: "现金服务投诉数量", value: "", key: "time" },
        { label: "未处理投诉数量", value: "", key: "dict" },
      ],
      infoData2: [
        { label: "网格内商户数量", value: "", key: "num" },
        { label: "责任网点", value: "", key: "org" },
        { label: "最近摸排时间", value: "", key: "time" },
      ],
      infoData1: [
        { label: "开展宣传活动数量", value: "180", key: "complainant" },
        {
          label: "最近一次宣传活动时间",
          value: "2024-10-10",
          key: "complainantType",
        },
      ],
      maxHeight: "calc(100% - 40px)",
      columns: [
        { label: "网格名称", prop: "name", showOverFlowTooltip: true },
        { label: "所在镇街", prop: "name1", showOverFlowTooltip: true },
        { label: "所在区县", prop: "name2", showOverFlowTooltip: true },
      ],
      tableData: [
        {
          name: "桃园社区居委会",
          name1: "城东街道",
          name2: "金华市婺城区",
        },
        {
          name: "上浮桥社区居委会",
          name1: "城东街道",
          name2: "金华市婺城区",
        },
        {
          name: "青春路社区居委会",
          name1: "城东街道",
          name2: "金华市婺城区",
        },
        {
          name: "旌孝街社区居委会",
          name1: "城东街道",
          name2: "金华市婺城区",
        },
        {
          name: "八咏楼社区居委会",
          name1: "城东街道",
          name2: "金华市婺城区",
        },
        {
          name: "杨思岭社区居委会",
          name1: "城中街道",
          name2: "金华市婺城区",
        },
        {
          name: "明月楼社区居委会",
          name1: "城中街道",
          name2: "金华市婺城区",
        },
        {
          name: "西市街社区居委会",
          name1: "城中街道",
          name2: "金华市婺城区",
        },
        {
          name: "西郊社区居委会",
          name1: "城西街道",
          name2: "金华市婺城区",
        },
      ],
      tableData1: [
        {
          name: "朱基头社区居委会",
          name1: "秋滨街道",
          name2: "金华市婺城区",
        },
        {
          name: "张坞垅社区居委会",
          name1: "秋滨街道",
          name2: "金华市婺城区",
        },
        {
          name: "陆村社区居委会",
          name1: "秋滨街道",
          name2: "金华市婺城区",
        },
        {
          name: "秋高社区居委会",
          name1: "秋滨街道",
          name2: "金华市婺城区",
        },
        {
          name: "邵姜社区居委会",
          name1: "城西街道",
          name2: "金华市婺城区",
        },
      ],
      total: 0,
    };
  },
  mounted() {
    // this.fetchList()
  },
  methods: {
    getInfoDataForItem(infoData, item) {
      return infoData.map((info) => ({
        label: info.label,
        value: item[info.key],
        key: info.key,
      }));
    },
    fetchList() {
      const param = {
        pageSize: 10,
        pageNum: 1,
      };
      services
        .gridMessageList(param)
        .then((res) => {
          const { total, rows } = res;
          this.total = total;
        })
        .catch((error) => {
          if (error) {
            this.total = 0;
          }
        });
    },
  },
};
</script>

<style scoped lang="scss">
.gridBox {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .left {
    width: 63%;
    height: 100%;
    &-one {
      width: 100%;
      height: 17vh;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 10px;
      .leftOneItem {
        width: 23%;
      }
    }
    &-two {
      width: 100%;
      height: 63vh;
      margin-top: 10px;
      display: flex;
      justify-content: space-between;
      .events,
      .counts {
        width: 49%;
        height: 100%;
        border: 1px solid #ccc;
        border-radius: 10px;
        padding: 10px;
      }
      .counts {
        .complaint {
          height: 130px;
        }
        .counts-title {
          display: flex;
          justify-content: space-between;
          align-items: center;
          font-size: 20px;
        }
      }
      .events {
        .leftOneItem {
          width: 30%;
        }
        .sort {
          font-size: 20px;
        }
      }
    }
  }
  .right {
    width: 36%;
    height: 100%;
    &-one {
      width: 100%;
      height: 30vh;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 10px;
      .publicity {
        margin: 0 auto;
        width: 98%;
        height: 80px;
        border: 1px solid #ccc;
        border-radius: 10px;
        margin-top: 10px;
        padding: 5px;
        &-item {
          color: #8bf6ff;
        }
        ::v-deep .infoList {
          font-size: 14px !important;
          padding: 5px !important;
        }
        ::v-deep .infoList li {
          line-height: 25px !important;
        }
      }
      .filter{
        display: flex;
        align-items: center;
        font-size: 22px;
        i{
          margin-left: 10px;
        }
      }
    }
    &-two {
      width: 100%;
      height: 25vh;
      margin-top: 5px;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 10px;
    }
    &-three {
      width: 100%;
      height: 25vh;
      margin-top: 5px;
      border: 1px solid #ccc;
      border-radius: 10px;
      padding: 10px;
    }
  }
}
h2 {
  font-size: 16px;
}
.leftOneList {
  margin-top: 20px;
  display: flex;
  font-size: 16px;
  justify-content: space-around;
  .leftOneItem {
    height: 100px;
    border: 1px solid #ccc;
    border-radius: 10px;
    padding: 10px;
    margin-bottom: 10px;
    text-align: center;
    div {
      margin-top: 10px;
    }
  }
}
.complaint {
  margin: 0 auto;
  width: 98%;
  height: 160px;
  border: 1px solid #ccc;
  border-radius: 10px;
  margin-top: 10px;
  padding: 5px;
  &-item {
    color: #8bf6ff;
  }
  ::v-deep .infoList {
    font-size: 14px !important;
  }
  ::v-deep .infoList li {
    line-height: 25px !important;
  }
}
</style>