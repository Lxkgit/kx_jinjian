<template>
  <PageContent>
    <section class="evaluation-search">
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        :buttonSpan="3"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
    </section>

    <section class="evaluation-content">
      <tableTopbar
        leftTitle="详细统计数据"
        buttonText="启动非现场评价"
        @click="openDialog('edit')"
      />
      <section class="evaluation-content-body">
        <ul
          class="evaluation-content-body-item"
          v-for="item in cardList"
          :key="item"
        >
          <li
            class="evaluation-content-body-item-title"
            @click="openDialog('readonly')"
          >
            {{ Math.random() * 100 }}
          </li>
          <li class="evaluation-content-body-item-progress">
            <span class="evaluation-content-body-item-progress-label">
              评价进度：
            </span>
            <div
              :style="{
                '--width':
                  parseInt((Math.floor(11 * Math.random()) / 11) * 100) + '%',
              }"
              class="evaluation-content-body-item-progress-element"
            ></div>
            <p class="evaluation-content-body-item-progress-count">
              <span>{{ Math.floor(11 * Math.random()) }}</span>
              <span>/11</span>
            </p>
          </li>
          <li class="evaluation-content-body-item-result">
            <span>评价结果：</span>
            <span
              >--评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：评价结果：</span
            >
          </li>
          <li class="evaluation-content-body-item-time">
            启动评价时间：2024-02-01 15：00
          </li>
          <li class="evaluation-content-body-item-btnList">
            <button
              v-for="btn in btnList"
              :key="btn.type"
              :style="{ color: btn.color }"
              @click="clickHandle(btn.type, item)"
            >
              {{ btn.label }}
            </button>
          </li>
        </ul>
      </section>
    </section>

    <dialogComponent
      :dialogVisible.sync="dialogVisible"
      :title="title"
      :isReadonlyForm="isReadonlyForm"
    />
  </PageContent>
</template>
<script>
import tableTopbar from "../components/tableTopbar.vue";
import dialogComponent from "../components/dialogComponent.vue";
export default {
  name: "EvaluationList",
  components: { tableTopbar, dialogComponent },
  data() {
    return {
      searchColumns: [
        {
          label: "客户名称",
          key: "name",
          type: "text",
        },
      ],
      btnList: [
        { label: "评价规则", type: "evaluationRules", color: "#5E81F4" },
        { label: "评价结果", type: "evaluationResultList", color: "#5E81F4" },
        { label: "删除", type: "delete", color: "#DC5564" },
      ],
      cardList: Array.from({ length: 30 }, (_, i) => i + 1),
      dialogVisible: false,
      title: "",
      isReadonlyForm: true,
    };
  },
  methods: {
    openDialog(type, item) {
      switch (type) {
        case "edit":
          this.title = "启动年度非现场评价";
          this.isReadonlyForm = false;
          break;
        case "readonly":
          this.title = "启动年度非现场评价";
          this.isReadonlyForm = true;
          break;
        default:
          break;
      }
      this.dialogVisible = true;
      console.log("执行啦");
    },
    resetHandle() {
      // Reset search fields
      console.log("Reset search fields");
    },
    clickHandle(btnType, cardItem) {
      switch (btnType) {
        case "evaluationRules":
          this.$router.push({
            path: "/evaluate/netevaluationManagement/evaluationRules",
          });
          break;
        case "evaluationResultList":
          this.$router.push({
            path: "/evaluate/netevaluationManagement/evaluationResultList",
          });
          break;
        case "delete":
          this.$confirm(
            "删除后，将无法选择查看此非现场评价，请谨慎操作！",
            "",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "error",
            }
          )
            .then(() => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除",
              });
            });
          break;
        default:
          break;
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.evaluation {
  &-search {
    padding: 16px;
    background: #fff;
    margin-bottom: 12px;
    ::v-deep .filter-button-wrapper {
      text-align: right;
    }
  }
  &-content {
    padding: 24px 16px;
    background: #fff;
    &-body {
      margin-top: 24px;
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(387px, 1fr));
      gap: 16px;
      // ul
      &-item {
        border-radius: 8px;
        background: #ffffff;
        border: 1px solid #d6dbff;
        box-shadow: 0px 8px 10px 0px rgba(5, 13, 45, 0.08);
        // li
        &-title {
          --right: calc(100% - 16px);
          height: 54px;
          line-height: 54px;
          background: url("../assets/rightcircle.svg") no-repeat var(--right)
            center/16px 16px;
          padding-left: 16px;
          border: 1px solid #ebeef5;
          cursor: pointer;
        }
        &-progress {
          height: 60px;
          padding: 0 16px;
          display: flex;
          flex-flow: row nowrap;
          justify-content: flex-start;
          align-items: center;
          gap: 4px;
          &-label {
            flex-shrink: 0;
            font-size: 14px;
            color: #494d68;
            white-space: nowrap;
          }
          &-element {
            flex: 1;
            height: 20px;
            border-radius: 4px;
            background: #edf0f6;
            padding: 2px;
            &::after {
              display: block;
              content: "";
              height: 100%;
              width: var(--width, 0);
              border-radius: 4px;
              background: linear-gradient(90deg, #4b74ff 1%, #7789ff 100%);
            }
          }
          &-count {
            flex-shrink: 0;
            white-space: nowrap;
            font-size: 14px;
            span:first-child {
              color: #5e81f4;
            }
            span:last-child {
              color: #494d68;
            }
          }
        }
        &-result {
          display: flex;
          flex-flow: row nowrap;
          justify-content: flex-start;
          align-items: flex-start;
          font-size: 14px;
          gap: 4px;
          padding: 0 16px;
          line-height: 20px;
          margin-bottom: 24px;
          span:first-child {
            color: #494d68;
            flex-shrink: 0;
          }
          span:last-child {
            color: #c2c4d6;
          }
        }
        &-time {
          height: 36px;
          line-height: 36px;
          text-indent: 16px;
          font-size: 14px;
          color: #898eac;
          background: #f6f7fa;
        }
        &-btnList {
          height: 45px;
          line-height: 45px;
          display: grid;
          grid-template-columns: repeat(3, 1fr);
          button {
            font-size: 14px;
            background: transparent;
            border: none;
            outline: none;
            cursor: pointer;
            position: relative;
            &:not(:last-child)::after {
              content: "";
              position: absolute;
              top: 50%;
              right: 0;
              transform: translateY(-50%);
              width: 1px;
              height: 20px;
              background: #ebeef5;
            }
          }
        }
      }
    }
  }
}
</style>
