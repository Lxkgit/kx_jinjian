<template>
  <div>
    <PageContent>
      <div class="top">
        <el-button class="btn" @click="cancel">取消</el-button>
      </div>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :showPagination="false"
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
      <div class="supeiopBott">
        <div class="left">
          <h2>机构自评结果</h2>
          <div class="ratingBox" v-for="item in probolmData" :key="item.id">
            <div class="ratingBox-title">
              <i
                :class="`el-icon-arrow-${item.flag ? 'down' : 'up'}`"
                @click="showleaveTwo(item)"
              ></i>
              <span>{{ item.classification }}</span>
            </div>
            <div
              class="ratingBox-leaveTwo"
              v-show="item.flag"
              v-for="ele in item.children"
              :key="ele.id"
            >
              <div class="ratingBox-title">
                <i
                  :class="`el-icon-arrow-${ele.flag ? 'down' : 'up'}`"
                  @click="showleaveTwo(ele)"
                ></i>
                <span>{{ ele.classification }}</span>
              </div>
              <div
                class="ratingBox-leaveThree"
                v-show="ele.flag"
                v-for="e in ele.children"
                :key="e.id"
              >
                <el-row>
                  <el-col :span="19" style="margin-right: 15px;">{{ e.classification }}</el-col>
                  <el-col :span="4">
                    <span style="color: #5a9cf8;">{{ e.score }}</span>
                    <span>分</span>
                  </el-col>
                </el-row>
                <div class="leaveThreeInt">
                  <span>扣分理由：</span>
                  <el-input type="textarea" style="width: 300px"></el-input>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="right">
          <h2>上级评价</h2>
          <div class="ratingBox" v-for="item in probolmData" :key="item.id">
            <div class="ratingBox-title">
              <i
                :class="`el-icon-arrow-${item.flag ? 'down' : 'up'}`"
                @click="showleaveTwo(item)"
              ></i>
              <span>{{ item.classification }}</span>
            </div>
            <div
              class="ratingBox-leaveTwo"
              v-show="item.flag"
              v-for="ele in item.children"
              :key="ele.id"
            >
              <div class="ratingBox-title">
                <i
                  :class="`el-icon-arrow-${ele.flag ? 'down' : 'up'}`"
                  @click="showleaveTwo(ele)"
                ></i>
                <span>{{ ele.classification }}</span>
              </div>
              <div
                class="ratingBox-leaveThree"
                v-show="ele.flag"
                v-for="e in ele.children"
                :key="e.id"
              >
                <el-row>
                  <el-col :span="19">{{ e.classification }}</el-col>
                  <el-col :span="5">
                    <el-input
                      v-model="e.score"
                      placeholder="请输入分数"
                      style="width: 100px"
                    ></el-input>
                  </el-col>
                </el-row>
                <div class="leaveThreeInt">
                  <span>扣分理由：</span>
                  <el-input type="textarea" style="width: 300px"></el-input>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="bottom-btnList">
        <el-button class="btn" @click="cancel">取消</el-button>
        <el-button class="btn" type="primary" @click="confirm">确认</el-button>
      </div>
    </PageContent>
  </div>
</template>
  
<script>
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  data() {
    return {
      tableData: [
        {
          org: "农业银行金华江北支行",
          orgNum: "50",
          org1: "120",
          org2: "10",
          org3: "5",
          org4: "8",
          org5: "100",
          org6: "90",
          org7: "50",
          org8: "140",
        },
      ],
      columns: [
        { label: "银行机构", prop: "org" },
        { label: "现金服务网点数量", prop: "orgNum", width: "180px" },
        { label: "现金服务网格数量", prop: "org1" },
        { label: "上报网点动态数量", prop: "org2" },
        { label: "投诉事件数量", prop: "org3" },
        { label: "网格长数量", prop: "org4" },
        { label: "网点摸排登记台账数量", prop: "org5", width: "180px" },
        { label: "反假业务检查台账数量", prop: "org6", width: "180px" },
        { label: "全额清分台账数量", prop: "org7" },
        { label: "纪念币巡查台账数量", prop: "org8" },
      ],
      probolmData: [
        {
          id: "1",
          classification: "市分行",
          score: "8",
          leave: 1,
          flag: true,
          children: [
            {
              id: "1-1",
              classification: "（一）现金服务",
              score: "8",
              leave: 2,
              flag: true,
              children: [
                {
                  id: "1-1-1",
                  classification:
                    "1、保持所辖银行网点合理柜台规模，做好零钱备付，配备必要现金服务设施。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
                {
                  id: "1-1-2",
                  classification:
                    "2、每半年至少组织开展一次现金服务状况暗访，每次暗访覆盖所辖网点总数的10%。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-2",
              classification: "（二）网格管理",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-2-1",
                  classification:
                    "1、是否制定年度网格化管理工作计划，指导、督促所辖网格责任网点的现金服务网格化工作，建立网格台账。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:6,
                },
                {
                  id: "1-2-2",
                  classification:
                    "2、定期组织开展本行所辖责任网格交叉检查，积极参与人民银行组织的交叉检查。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:4,
                },
                {
                  id: "1-2-3",
                  classification:
                    "3、加强培训，提升临柜人员反假识假能力，加强现金机具鉴伪能力管理，保障现金流通领域公众用现安全。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:4,
                },
              ],
            },
            {
              id: "1-3",
              classification: "（三）反假管理",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-3-1",
                  classification:
                    "1、加强培训，提升临柜人员反假识假能力，加强现金机具鉴伪能力管理，保障现金流通领域公众用现安全。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
                {
                  id: "1-3-2",
                  classification:
                    "2、加强机具检测，每半年常规开展1 次现金机具鉴伪能力检测",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
                {
                  id: "1-3-3",
                  classification:
                    "3、强化指导督促，对所辖银行机构的反假货币工作进行定期或不定期的现场督导或评估，发现问题要立即督促整改，并以适当形式反馈督导结果，形成压力传导机制。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-4",
              classification: "（四）“零钱包”投放",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-4-1",
                  classification:
                    "1、组织所辖银行机构加大“零钱包”投放，采取有零钞兑换优先服务窗口、绿色通道等方式，实现零钱快速兑换，并做好零钱包兑换台账登记。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-5",
              classification: "（五）整洁度管理",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-5-1",
                  classification:
                    "1、落实全额清分工作要求，对外付出人民币现金符合整洁度要求，并组织开展全额清分自查。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
                {
                  id: "1-5-2",
                  classification:
                    "2、配合人民银行做好整洁度监测工作，并通过开展残损人民币回收提升监测点整洁度，监测点整洁度达90%以上。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
                {
                  id: "1-5-3",
                  classification:
                    "3、是否组织所辖银行网点开展现金服务“大篷车”活动",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-6",
              classification: "（六）现金服务点",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-6-1",
                  classification:
                    "1、是否组织所辖银行设立现金服务点，并提供零钞兑换、残币回收等服务，是否放置小面额自助兑换机。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-7",
              classification: "（七）宣传引导",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-7-1",
                  classification:
                    "1、是否制定年度现金宣传工作计划，有针对性开展整治拒收现金、爱护人民币、人民币图样使用等相关宣传",
                  score: "",
                  type: "扣分",
                  leave: 3,
                },
                {
                  id: "1-7-2",
                  classification:
                    "2、是否开展舆情监测，及时处置舆情，未及时处置扣1-5分",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-8",
              classification: "（八）现金服务点",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-8-1",
                  classification:
                    "1、创新现金服务模式，被人民银行或上级行推广的；承接人民银行试点或创新，最高加5分，自评总分不超100分",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
            {
              id: "1-9",
              classification: "（九）现金服务点",
              score: "8",
              flag: true,
              leave: 2,
              children: [
                {
                  id: "1-9-1",
                  classification:
                    "1、人民银行或上级银行机构接到银行网点现金服投诉的，每个投诉扣5分。",
                  score: "",
                  type: "扣分",
                  leave: 3,
                  score:5,
                },
              ],
            },
          ],
        },
      ],
    };
  },
  methods: {
    showleaveTwo(item) {
      item.flag = !item.flag;
    },
    cancel() {
      this.$router.go(-1);
    },
    confirm() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped lang="scss">
.supeiopBott {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  .left,
  .right {
    width: 49.5%;
    height: 70vh;
    border: 1px solid rgb(201, 201, 201);
    padding: 10px;
    overflow: auto;
    h2 {
      font-size: 18px;
    }
  }
}
.ratingBox {
  font-size: 16px;
  padding-left: 10px;
  margin: 10px 0;
  line-height: 20px;
  &-title {
    display: flex;
    align-items: center;
  }
  > span {
    font-weight: bolder;
  }
  i {
    font-size: 20px;
    font-weight: bolder;
    margin-right: 10px;
    cursor: pointer;
  }
  &-leaveTwo {
    margin: 10px 0;
    padding-left: 20px;
    > span {
      font-weight: bolder;
    }
  }
  &-leaveThree {
    padding-left: 20px;
    margin: 20px 0;

    .leaveThreeInt {
      padding-left: 20px;
      margin-top: 20px;
      display: flex;
      align-items: center;
      .el-input {
        width: 200px !important;
      }
    }
  }
}
.top {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
.bottom-btnList {
  width: 100%;
  display: flex;
  justify-content: space-evenly;
  margin: 20px 0;
  .btn {
    width: 100px;
  }
}
</style>