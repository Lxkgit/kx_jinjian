<template>
  <div class="detail-dialog-wrapper" v-if="showDialog">
    <div class="detail-dialog-mask" @click="handleClose"></div>
    <div class="detail-dialog">
      <div class="detail-dialog-header">
        <h2>线索全生命周期详情</h2>
        <button class="close-btn" @click="handleClose">&times;</button>
      </div>
      <div class="detail-dialog-content">
        <!-- 生命周期进度条 -->
        <div class="lifecycle-progress">
          <div v-for="(step, index) in lifecycleSteps" :key="step.id" class="progress-step" @click="showDetail(index)">
            <div class="step-circle"
              :class="{ active: step.status === 'completed', current: step.status === 'current' }"
              :style="{ backgroundColor: index < defaultValue.currentNode ? '#E6F4FF' : '' }">
              <i v-if="step.status === 'completed'" class="el-icon-check"
                :style="{ color: index < defaultValue.currentNode ? '#74B0FF' : '' }"></i>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <!--  <div class="step-line" :style="{ backgroundColor: index < defaultValue.currentNode ? '#E6F4FF !important' : '' }"
              :class="{ active: index < 1 }"></div> -->
            <div class="step-content">
              <div class="step-label">{{ step.name }}</div>
              <div class="step-time" v-if="step.time">{{ formatTime(step.time) }}</div>
            </div>
          </div>
        </div>

        <!-- 详情信息 -->
        <div class="detail-section">
          <h3>{{ `${current + 1}.${detailContent[current].title}` }}</h3>
          <div class="detail-table">
            <!-- 线索上报 -->
            <template v-if="current === 0">
              <div class="table-row">
                <div class="table-cell">来源机构</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">来源渠道</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">报送时间</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">线索类型</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">风险等级</div>
                <div class="table-cell">{{ detailContent[current].prop5 }}</div>
                <div class="table-cell">分拨单位</div>
                <div class="table-cell">{{ detailContent[current].prop6 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">分拨时间</div>
                <div class="table-cell" style="flex: 1;">{{ detailContent[current].prop7 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">涉险主体信息</div>
                <div class="table-cell" colspan="3">{{ detailContent[current].prop8 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">基本信息</div>
                <div class="table-cell" colspan="3">{{ detailContent[current].prop9 }}</div>
              </div>
            </template>

            <!-- 线索初审 -->
            <template v-else-if="current === 1">
              <div class="table-row">
                <div class="table-cell">初审结果</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">初审意见</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">初审时间</div>
                <div class="table-cell" style="flex: 1;">{{ detailContent[current].prop3 }}</div>
              </div>
            </template>

            <!-- 线索研判 -->
            <template v-else-if="current === 2">
              <div class="table-row">
                <div class="table-cell">研判时间</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">研判部门</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">参会人员</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">研判结果</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">附件</div>
                <div class="table-cell" style="flex: 1;">{{ detailContent[current].prop5 }}</div>
              </div>
            </template>

            <!-- 线索移送 -->
            <template v-else-if="current === 3">
              <div class="table-row">
                <div class="table-cell">接受单位</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">公函号</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">送达时间</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">告知方式</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
            </template>

            <!-- 联合处置 -->
            <template v-else-if="current === 4">
              <div class="table-row">
                <div class="table-cell">牵头部门</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">处置措施</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">处置进展</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">处置时间</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">附件</div>
                <div class="table-cell" style="flex: 1;">{{ detailContent[current].prop5 }}</div>
              </div>
            </template>

            <!-- 督办反馈 -->
            <template v-else-if="current === 5">
              <div class="table-row">
                <div class="table-cell">督办单位</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">督办时间</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">督办要求</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">反馈结果</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">附件</div>
                <div class="table-cell" style="flex: 1;">{{ detailContent[current].prop5 }}</div>
              </div>
            </template>

            <!-- 销号管理 -->
            <template v-else-if="current === 6">
              <div class="table-row">
                <div class="table-cell">申请时间</div>
                <div class="table-cell">{{ detailContent[current].prop1 }}</div>
                <div class="table-cell">关联清单</div>
                <div class="table-cell">{{ detailContent[current].prop2 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">销号状态</div>
                <div class="table-cell">{{ detailContent[current].prop3 }}</div>
                <div class="table-cell">审核意见</div>
                <div class="table-cell">{{ detailContent[current].prop4 }}</div>
              </div>
              <div class="table-row">
                <div class="table-cell">最终销号时间</div>
                <div class="table-cell">{{ detailContent[current].prop5 }}</div>
                <div class="table-cell">备注</div>
                <div class="table-cell">{{ detailContent[current].prop6 }}</div>
              </div>
            </template>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";

export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      currentStepIndex: 1, // 当前步骤索引
      lifecycleSteps: [
        {
          id: 1,
          name: "线索上报",
        },
        {
          id: 2,
          name: "线索初审",
        },
        {
          id: 3,
          name: "线索研判"
        },
        {
          id: 4,
          name: "线索移送"
        },
        {
          id: 5,
          name: "联合处置"
        },
        {
          id: 6,
          name: "督办反馈"
        },
        {
          id: 7,
          name: "销号管理"
        }
      ],
      lifecycleSteps1: [
        {
          id: 1,
          name: "线索上报",
        },
        {
          id: 2,
          name: "线索初审",
        },
        {
          id: 3,
          name: "线索研判"
        },
        {
          id: 4,
          name: "线索移送"
        },
        {
          id: 5,
          name: "联合处置"
        },
        {
          id: 6,
          name: "督办反馈"
        },
        {
          id: 7,
          name: "销号管理"
        }
      ],
      detailContent: [
        {
          title: '线索上报',
          prop1: '经侦大队',
          prop2: '公安协查',
          prop3: '2024-01-20 09:00:00',
          prop4: '不法中介',
          prop5: '高',
          prop6: '江汉区金融办',
          prop7: '2024-01-20 10:00:00',
          prop8: '武汉某某网络借贷中介 / 91420103MA4K12D8XP / 武汉市江汉区建设大道99号',
          prop9: 'P2P网贷存量风险'
        },
        {
          title: '线索初审',
          prop1: '经侦大队',
          prop2: '公安协查',
          prop3: '2024-01-20 14:00:00',
          prop4: '不法中介',
          prop5: '高',
          prop6: '江汉区金融办',
          prop7: '2024-01-20 10:00:00',
          prop8: '武汉某某网络借贷中介 / 91420103MA4K12D8XP / 武汉市江汉区建设大道99号',
          prop9: 'P2P网贷存量风险'
        },
        {
          title: '线索研判',
          prop1: '联席会议',
          prop2: '周九',
          prop3: '2024-01-21 10:00',
          prop4: '同意结案',
          prop5: '无',
          prop6: '',
          prop7: '',
          prop8: '',
          prop9: ''
        },
        {
          title: '线索移送',
          prop1: '区公安分局',
          prop2: '江处非函[2024]09号',
          prop3: '2024-01-22',
          prop4: '系统移送',
          prop5: '',
          prop6: '',
          prop7: '',
          prop8: '',
          prop9: ''
        },
        {
          title: '联合处置',
          prop1: '区公安分局',
          prop2: '立案打击',
          prop3: '2024-01-23 10:00:00',
          prop4: '案件已判决',
          prop5: '无',
          prop6: '',
          prop7: '',
          prop8: '',
          prop9: ''
        },
        {
          title: '督办反馈',
          prop1: '省金融局',
          prop2: '2024-02-01',
          prop3: '结案报告',
          prop4: '已提交',
          prop5: '无',
          prop6: '',
          prop7: '',
          prop8: '',
          prop9: ''
        },
        {
          title: '销号管理',
          prop1: '2024-02-15',
          prop2: 'QD-2024-088',
          prop3: '已关闭',
          prop4: '同意',
          prop5: '2024-02-16',
          prop6: '风险彻底化解',
          prop7: '',
          prop8: '',
          prop9: ''
        }
      ],
      current: 0
    };
  },
  watch: {
    defaultValue(val) {
      val.currentNode !== 0 && (this.current = val.currentNode - 1)
      this.lifecycleSteps = JSON.parse(JSON.stringify(this.lifecycleSteps1)).map((ele, index) => {
        if (index < val.currentNode) {
          ele.status = "completed";
          ele.time = "2024-02-06 09:30"
        }
        if (index == val.currentNode) {
          ele.status = "current";
        }
        return ele
      })

    }
  },
  methods: {
    showDetail(index) {
      if (index < this.defaultValue.currentNode)
        this.current = index
    },
    handleClose() {
      this.showDialog = false;
      this.$emit("close");
    },
    formatTime(timeStr) {
      // 输入格式：2024-02-06 09:30
      // 输出格式：2024:02:06:09:30:00
      if (!timeStr) return '';
      const parts = timeStr.split(' ');
      const dateParts = parts[0].split('-');
      const timeParts = parts[1] ? parts[1].split(':') : ['00', '00'];
      return `${dateParts[0]}:${dateParts[1]}:${dateParts[2]}:${timeParts[0]}:${timeParts[1]}:00`;
    }
  }
};
</script>

<style lang="scss" scoped>
.detail-dialog-wrapper {
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  z-index: 9999;
  display: flex;
  justify-content: flex-end;
}

.detail-dialog-mask {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
}

.detail-dialog {
  position: relative;
  width: 850px;
  height: 100%;
  background-color: #fff;
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.15);
  animation: slideIn 0.3s ease-out;
  overflow-y: auto;
}

@keyframes slideIn {
  from {
    transform: translateX(100%);
  }

  to {
    transform: translateX(0);
  }
}

.detail-dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #e4e7ed;
  background-color: #f5f7fa;

  h2 {
    margin: 0;
    font-size: 16px;
    font-weight: 600;
  }

  .close-btn {
    width: 32px;
    height: 32px;
    border: none;
    background: none;
    font-size: 20px;
    cursor: pointer;
    line-height: 32px;
    text-align: center;
    color: #909399;

    &:hover {
      color: #606266;
    }
  }
}

.detail-dialog-content {
  padding: 20px;
}

.lifecycle-progress {
  display: flex;
  align-items: flex-start;
  margin-bottom: 30px;
  padding: 16px;
  background-color: #f5f7fa;
  border-radius: 4px;

  .progress-step {
    display: flex;
    align-items: center;
    width: 115px;
    position: relative;
    min-width: 115px;

    .step-circle {
      width: 28px;
      height: 28px;
      border-radius: 50%;
      background-color: #dcdfe6;
      color: #909399;
      font-size: 14px;
      line-height: 28px;
      text-align: center;
      margin-right: 8px;
      flex-shrink: 0;

      i {
        font-size: 16px;
        color: #fff;
      }

      &.active {
        background-color: #409eff;
        color: #fff;
      }

      &.current {
        background-color: #409eff;
        color: #fff;
      }
    }

    .step-line {
      display: none;
    }

    .step-content {
      flex: 1;
      min-width: 0;

      .step-label {
        font-size: 14px;
        font-weight: 500;
        color: #303133;
        margin-bottom: 2px;
        word-wrap: break-word;
        word-break: break-all;
        max-width: 60px;
        line-height: 1.2;
      }

      .step-time {
        font-size: 12px;
        color: #909399;
        word-wrap: break-word;
        word-break: break-all;
        max-width: 60px;
        line-height: 1.2;
      }
    }
  }
}

.detail-section {

  h3 {
    font-size: 14px;
    font-weight: 600;
    margin-bottom: 16px;
    color: #303133;
  }

  .detail-table {
    border: 1px solid #e4e7ed;
    border-radius: 4px;
    overflow: hidden;

    .table-row {
      display: flex;
      border-bottom: 1px solid #e4e7ed;
      height: 52px;

      &:last-child {
        border-bottom: none;
      }

      .table-cell {
        display: flex;
        align-items: center;
        padding: 0 12px;
        border-right: 1px solid #e4e7ed;
        font-size: 14px;
        color: #606266;

        &:nth-child(odd) {
          width: 120px;
          background-color: #f5f7fa;
          font-weight: 500;
          color: #303133;
          flex-shrink: 0;
        }

        &:nth-child(even) {
          flex: 1;
        }

        &:last-child {
          border-right: none;
        }

        &[colspan="3"] {
          flex: 1;
        }
      }
    }
  }
}
</style>