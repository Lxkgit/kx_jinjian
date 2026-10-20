<template>
  <div class="left-view-wrapper">
    <titleBox height="460px" title="网格概况" annotation="">
      <boxWithTitle :title="activityDetail.actName">
        <info-list :list="activityInfo">
          <template #activityStatus>
            <span :class="['activity-status', `status${activityDetail.actStatus}`]">{{activityType[activityDetail.actStatus-1].label}}</span>
          </template>
          <template #hopeTitle>
            <span class="hope-title">活动预期成果</span>
          </template>
        </info-list>
      </boxWithTitle>
    </titleBox>
    <titleBox height="480px" title="营销活动统计" annotation="">
      <joinNumBox style="height: 160px; width: 93.5%; margin: 15px auto" :config="joinConfig" :joinMsg="joinMsg">
      </joinNumBox>
      <div class="statisticBox">
        <statisticCard class="statisticCard-item" v-for="(item, index) in cardList" :key="index" :dataItem="item"
          :config="configList[index]"></statisticCard>
      </div>
    </titleBox>
  </div>
</template>

<script>
import titleBox from "@/modules/module-screenView/components/titleBox";
import joinNumBox from "@/modules/module-screenView/viewComponents/components/joinNumBox";
import statisticCard from "@/modules/module-screenView/viewComponents/components/statisticCard";
import boxWithTitle from "@/modules/module-screenView/viewComponents/components/boxWithTitle";
import infoList from "@/modules/module-screenView/viewComponents/components/infoList";
import service from '../../screenView/api';
const activityType = [
  {
    label: "未开始",
    value: "1",
  },
  {
    label: "进行中",
    value: "2",
  },
  {
    label: "已结束",
    value: "3",
  },
];
export default {
  components: {
    titleBox,
    joinNumBox,
    statisticCard,
    boxWithTitle,
    infoList
  },
  watch: {
    activityDetail: {
      handler(v) {
        this.initData(v)
      },
      immediate: true
    }
  },
  data() {
    return {
      activityType,
      joinConfig: [
        { label: "扫码人数", key: "scanNum" },
        { label: "信息手机人数", key: "phoneNum" },
      ],
      configList: [
        [
          { label: "有效信息人数", key: "effectiveMsgNum" },
          {
            label: "活动有效率",
            key: "effectiveRate",
            valueColor: "#FCBE3F ",
          },
        ],
        [
          { label: "业务办理人数", key: "employeesNum" },
          {
            label: "活动转换率",
            key: "transformRate",
            valueColor: "#FCBE3F ",
          },
        ],
      ],
      info: {}
    };
  },
  computed: {
    activityDetail() {
      return this.$store.getters['screenView/activityDetail']
    },
    activityInfo() {
      if (this.activityDetail) {
        return [
          {
            left: '活动时间',
            right: this.activityDetail.actTimeList[0] + "—" + this.activityDetail.actTimeList[1]
          },
          {
            left: '活动网格',
            right: this.activityDetail.gridName
          },
          {
            left: '活动状态',
            isRightSlot: true,
            rightSlotName: 'activityStatus'
          },
          {
            left: '活动创建人',
            right: this.activityDetail.createBy
          },
          {
            isSlot: true,
            name: 'hopeTitle'
          },
          {
            left: '互动参与人数',
            right:  this.activityDetail.actParticipateNum
          },
          {
            left: '预期成果描述',
            right: this.activityDetail.otherExpectDesc
          },
        ]
      }
      return []
    },
    joinMsg(){
      if(this.info){
        const {scanCodeNum,infoCollectNum} = this.info
        return {
          scanNum: scanCodeNum,
          phoneNum:infoCollectNum,
          ratio:(infoCollectNum/scanCodeNum*100).toFixed(2),
          progressTitle: "活动参与率",
        }
      }
      return {}
    },
    cardList(){
      if(this.info){
        const {infoCollectEffectNum,busProcNum,scanCodeNum }  = this.info 
        return [
        {
          effectiveMsgNum:infoCollectEffectNum,
          effectiveRate: (infoCollectEffectNum/scanCodeNum*100).toFixed(2)+"%",
          imgUrl: 5,
        },
        {
          employeesNum: busProcNum,
          transformRate:(busProcNum/scanCodeNum*100).toFixed(2)+"%",
          imgUrl: "6",
        },
        ]
      }
      return []
    }
  },
  methods: {
    async initData(v) {
      let res  =  await service.getActResult(v.id)
      this.info = res.data
    }
  },
};
</script>

<style lang="scss" scoped>
.left-view-wrapper {
  position: relative;

  .statisticBox {
    display: flex;
    align-items: center;

    .statisticCard-item {
      width: 43%;
      margin: 0 auto;
    }
  }
}

.activity-status {
  position: relative;
  padding-left: 25px;

  &::before {
    content: "";
    position: absolute;
    left: 10px;
    top: 5px;
    width: 10px;
    height: 10px;
    border-radius: 50%;
  }

  &.status1 {
    color: #875407;

    &::before {
      background: #875407;
    }
  }

  &.status2 {
    color: #23F2AB;

    &::before {
      background: #23F2AB;
    }
  }

  &.status3 {
    color: #464b49;

    &::before {
      background: #464b49;
    }
  }

}

.hope-title {
  background: #8BF6FF30;
  height: 26px;
  line-height: 26px;
  font-size: 16px;
  border-radius: 15px;
  padding: 0 15px 0 30px;
  margin: 10px 0;
  color: #8BF6FF;
  position: relative;

  &::before {
    content: "";
    position: absolute;
    left: 8px;
    top: 8px;
    width: 8px;
    height: 8px;
    background: #8BF6FF;
    border-radius: 50%;
  }
}
</style>
