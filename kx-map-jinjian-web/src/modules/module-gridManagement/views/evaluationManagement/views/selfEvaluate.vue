<template>
  <div>
    <PageContent>
      <div class="top">
        <el-button class="btn" @click="cancel">取消</el-button>
      </div>
      <!-- tree列表 -->
      <el-tree
        :data="treeData"
        node-key="id"
        default-expand-all
        :expand-on-click-node="false"
      >
        <div class="custom-tree-node" slot-scope="{ data }">
          <div class="label">
            <span>{{ data.label }}</span>
            <!-- <span
              class="rightInput"
              v-if="data.rightKey && reportInfo.status == 'report'"
            >
              —<el-input-number
                size="mini"
                :controls="false"
                v-model="treeObj[data.rightKey]"
              >
              </el-input-number
              >分
            </span> -->
          </div>
          <div v-if="data.bottomList" style="margin-top: 10px;">
            <div v-for="item in data.bottomList" :key="item.key">
              <div class="bottom-item textarea" v-if="item.type == 'textarea'">
                <div class="label">{{ item.label }}</div>
                <el-input
                  width="300px"
                  type="textarea"
                  :rows="3"
                  :placeholder="item.placeholder"
                  v-model="treeObj[item.key]"
                >
                </el-input>
              </div>
              <div class="bottom-item upload" v-else-if="item.type == 'upload'">
                <el-upload
                  class="upload-demo"
                  action="/"
                  :show-file-list="false"
                  multiple
                  :limit="12"
                  :file-list="treeObj[item.key]"
                >
                  <el-button type="primary"> 上 传 </el-button>
                </el-upload>
                <div class="upload-tip">{{ item.placeholder }}</div>
              </div>
            </div>
          </div>
        </div>
      </el-tree>
      <div class="bottom-btnList" v-if="reportInfo.status == 'report'">
        <el-button class="btn" @click="cancel">取消</el-button>
        <el-button class="btn" type="primary" @click="confirm">确认</el-button>
      </div>
    </PageContent>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  data() {
    return {
      treeData111: [{ id: "", label: "", children: [{}] }],
      treeData: [
        // {
        //   id: "1",
        //   label: "市分行",
        //   score: "8",
        //   leave: 1,
        //   flag: true,
        //   children: [
        //     {
        //       id: "1-1",
        //       label: "（一）现金服务",
        //       score: "8",
        //       leave: 2,
        //       flag: true,
        //       children: [
        //         {
        //           id: "1-1-1",
        //           label:
        //             "1、保持所辖银行网点合理柜台规模，做好零钱备付，配备必要现金服务设施。",
        //           score: "",
        //           type: "扣分",
        //           rightKey: "1",
        //           leave: 3,
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "bottom21",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-1-2",
        //           label:
        //             "2、每半年至少组织开展一次现金服务状况暗访，每次暗访覆盖所辖网点总数的10%。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "2",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "bottom21",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-2",
        //       label: "（二）网格管理",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-2-1",
        //           label:
        //             "1、是否制定年度网格化管理工作计划，指导、督促所辖网格责任网点的现金服务网格化工作，建立网格台账。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "3",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-2-2",
        //           label:
        //             "2、定期组织开展本行所辖责任网格交叉检查，积极参与人民银行组织的交叉检查。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "4",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-2-3",
        //           label:
        //             "3、加强培训，提升临柜人员反假识假能力，加强现金机具鉴伪能力管理，保障现金流通领域公众用现安全。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-3",
        //       label: "（三）反假管理",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-3-1",
        //           label:
        //             "1、加强培训，提升临柜人员反假识假能力，加强现金机具鉴伪能力管理，保障现金流通领域公众用现安全。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "54",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-3-2",
        //           label:
        //             "2、加强机具检测，每半年常规开展1 次现金机具鉴伪能力检测",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5312",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-3-3",
        //           label:
        //             "3、强化指导督促，对所辖银行机构的反假货币工作进行定期或不定期的现场督导或评估，发现问题要立即督促整改，并以适当形式反馈督导结果，形成压力传导机制。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "53321",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-4",
        //       label: "（四）“零钱包”投放",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-4-1",
        //           label:
        //             "1、组织所辖银行机构加大“零钱包”投放，采取有零钞兑换优先服务窗口、绿色通道等方式，实现零钱快速兑换，并做好零钱包兑换台账登记。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5813928",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //       bottomList: [
        //         {
        //           label: "扣分理由:",
        //           type: "textarea",
        //           key: "top11",
        //           placeholder: "请输入10字以上其他扣分理由",
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-5",
        //       label: "（五）整洁度管理",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-5-1",
        //           label:
        //             "1、落实全额清分工作要求，对外付出人民币现金符合整洁度要求，并组织开展全额清分自查。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5318297",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-5-2",
        //           label:
        //             "2、配合人民银行做好整洁度监测工作，并通过开展残损人民币回收提升监测点整洁度，监测点整洁度达90%以上。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5372182",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-5-3",
        //           label: "3、是否组织所辖银行网点开展现金服务“大篷车”活动",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5371289731",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-6",
        //       label: "（六）现金服务点",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-6-1",
        //           label:
        //             "1、是否组织所辖银行设立现金服务点，并提供零钞兑换、残币回收等服务，是否放置小面额自助兑换机。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5231",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-7",
        //       label: "（七）宣传引导",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-7-1",
        //           label:
        //             "1、是否制定年度现金宣传工作计划，有针对性开展整治拒收现金、爱护人民币、人民币图样使用等相关宣传",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "531211",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //         {
        //           id: "1-7-2",
        //           label: "2、是否开展舆情监测，及时处置舆情，未及时处置扣1-5分",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "5009",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-8",
        //       label: "（八）现金服务点",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-8-1",
        //           label:
        //             "1、创新现金服务模式，被人民银行或上级行推广的；承接人民银行试点或创新，最高加5分，自评总分不超100分",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "532187",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //     {
        //       id: "1-9",
        //       label: "（九）现金服务点",
        //       score: "8",
        //       flag: true,
        //       leave: 2,
        //       children: [
        //         {
        //           id: "1-9-1",
        //           label:
        //             "1、人民银行或上级银行机构接到银行网点现金服投诉的，每个投诉扣5分。",
        //           score: "",
        //           type: "扣分",
        //           leave: 3,
        //           rightKey: "513u2i",
        //           bottomList: [
        //             {
        //               label: "扣分理由:",
        //               type: "textarea",
        //               key: "top11",
        //               placeholder: "请输入10字以上其他扣分理由",
        //             },
        //           ],
        //         },
        //       ],
        //     },
        //   ],
        // },
        // {
        //   id: 1,
        //   label: "一、基本组织",
        //   children: [
        {
          id: 11,
          label: "一、网格划分情况",
          children: [
            {
              id: 111,
              rightKey: "first1",
              label:
                "1、与网格化管理平台地图中社区、行政村数进行比对，未按要求进行网格划分的扣5分",
              bottomList: [
                {
                  type: "upload",
                  key: "top31",
                  placeholder:
                    "依据文件:(单个文件50M以内,上传总数量不超过12份)",
                },
              ],
            },
            // {
            //   id: 112,
            //   rightKey: "first2",
            //   label:
            //     "2.正式党员人数不足3人，成立联合党支部覆盖下属单位数超过5个的，扣2分；",
            // },
            // {
            //   id: 113,
            //   rightKey: "first3",
            //   label:
            //     "3.党支部由若干个科室的党员组成，应设未设党支部第一书记的，扣2分。",
            //   bottomList: [
            //     {
            //       type: "upload",
            //       key: "top31",
            //       placeholder:
            //         "依据文件:(单个文件50M以内,上传总数量不超过12份)",
            //     },
            //   ],
            // },
          ],
        },
        {
          id: 12,
          label: "二、网格化管理覆盖情况",
          children: [
            {
              id: 121,
              rightKey: "two1",
              label: "1、未做到当地银行机构全覆盖的扣5分",
              bottomList: [
                {
                  type: "upload",
                  key: "top31",
                  placeholder:
                    "依据文件:(单个文件50M以内,上传总数量不超过12份)",
                },
              ],
            },
          ],
        },
        {
          id: 2,
          label: "三、网格责任网点信息更新情况",
          children: [
            {
              id: 121,
              rightKey: "two1",
              label:
                "1、银行网点因迁移、变更、撤并等不适合继续承担网格责任网点职责，却仍承担网格职责的，1个网点扣5分，直至该项分数扣完。",
              bottomList: [
                {
                  type: "upload",
                  key: "top31",
                  placeholder:
                    "依据文件:(单个文件50M以内,上传总数量不超过12份)",
                },
              ],
            },
            {
              id: 121,
              rightKey: "two2",
              label:
                "2、网格长、网格员的姓名、联系方式等信息为及时更新，1人扣0.5分，直至该项分数扣完。",
              bottomList: [
                {
                  type: "upload",
                  key: "top31",
                  placeholder:
                    "依据文件:(单个文件50M以内,上传总数量不超过12份)",
                },
              ],
            },
          ],
        },
        {
          id: 2,
          label: "四、工作报告情况",
          children: [
            {
              id: 121,
              rightKey: "two1",
              label: "1、1家银行机构未报送工作报告扣2分，直至该项分数扣完。",
              bottomList: [
                {
                  type: "upload",
                  key: "top31",
                  placeholder:
                    "依据文件:(单个文件50M以内,上传总数量不超过12份)",
                },
              ],
            },
          ],
        },
        //   ],
        // },
      ],
      treeObj: {},
    };
  },
  computed: {
    ...mapGetters("accountBook", ["reportInfo"]),
  },
  mounted() {
    this.setAllKey(this.treeData);
    console.log("treeobj1111111111", this.treeObj);
  },
  methods: {
    setAllKey(arr) {
      console.log("arr", arr);
      arr.forEach((item) => {
        if (!item.children) {
          if (item.rightKey) this.$set(this.treeObj, item.rightKey, "");
          if (item.bottomList?.length > 0) {
            item.bottomList.forEach((i) => {
              if (i.type == "textarea") this.$set(this.treeObj, i.key, "");
              else if (i.type == "upload") this.$set(this.treeObj, i.key, []);
            });
          }
        } else {
          this.setAllKey(item.children);
        }
      });
    },
    cancel() {
      this.$router.go(-1);
    },
    confirm() {
      this.$modal.msgSuccess('材料上报成功')
      this.$router.go(-1);
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-tree-node {
  white-space: normal;
  outline: 0;

  .el-tree-node__content {
    text-align: left;
    align-items: center;
    margin: 4px;
    height: 100%;
  }
}
.custom-tree-node {
  // border: 1px solid red;
  padding: 10px 0;
  font-size: 16px;
  width: 85vw;
  .label {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .rightInput {
      width: 160px;
      align-items: center;
      white-space: nowrap;
    }
  }
  .textarea {
    display: flex;
    // justify-content: space-evenly;
    align-items: flex-start;
    .label {
      // padding-right: 10px;
      margin-left: 10px;
      margin-bottom: 5px;
      min-width: 10rem;
    }
  }
  .upload {
    display: flex;
    align-items: center;
    padding-left: 10px;
    &-tip {
      margin-left: 10px;
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
