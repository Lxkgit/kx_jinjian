<template>
  <div class="search-part">
    <el-autocomplete placeholder="请输入内容" :fetch-suggestions="querySearch" :debounce="1000" popper-class="search-popper"
      :trigger-on-focus="false" :popper-append-to-body="false" v-model="searchName" class="search-input"
      @select="handleSelect">
      <div slot="prepend">
        <el-select v-model="searchType" placeholder="请选择">
          <el-option label="网点搜索" value="0"></el-option>
          <el-option label="网格搜索" value="2"></el-option>
          <el-option label="地址搜索" value="1"></el-option>
        </el-select>
      </div>
      <template slot-scope="{ item }">
        <div class="search-item">
          <div :class="['search-left', `bg-icon${searchType}`]"></div>
          <div class="search-right">
            <span :title="item.name"><i>{{ item.name }}</i></span>
            <span :title="item.address" v-if="item.address">{{
              item.address
              }}</span>
          </div>
        </div>
      </template>
    </el-autocomplete>
  </div>
</template>

<script>
import service from "../api";
export default {
  data() {
    return {
      searchName: "",
      searchType: "0",
    };
  },
  computed: {
    orgCode() {
      return this.$store.getters["screenView/orgCode"];
    },
    filterParam() {
      return this.$store.getters["screenView/filterParam"];
    },
    filterType() {
      return this.$store.getters["screenView/filterType"];
    },
    /* 角色切换 */
    roleParam() {
      return this.$store.getters["screenView/roleParam"];
    },
  },
  methods: {
    querySearch(queryString, cb) {
      if (queryString) {
        // this.searchName = queryString
        // const { typeList, groupCategory } = this.filterParam
        // const { orgCodes, managerCodes } = this.roleParam
        // const param = {
        //   customerName: queryString,
        //   types: this.filterType === '1' ? typeList : [groupCategory],
        //   orgCodes: orgCodes && orgCodes.split(','),
        //   managerCodes: managerCodes && managerCodes.split(','),
        // }
        // orgCodes === '' && delete param.orgCodes
        // managerCodes === '' && delete param.managerCodes
        // service.queryCustomerByName(param).then(res => {
        //   const { data } = res
        //   cb(data)
        // })
        let methods = [
          "queryCustomerByAnchorName",
          "queryCustomerByAddr",
          "queryCustomerByAnchor",
        ];
        service[methods[this.searchType]](queryString).then((res) => {
          const { data } = res;
          let rst = [];
          data.forEach((v) => {
            const {
              addressDesc,
              anchorName,
              belongGrid,
              lon,
              lat,
              name,
              addrLevel,
              code,
              address,
            } = v;
            rst.push({
              ...v,
              name: anchorName || name,
              address: addressDesc || address,
              code: belongGrid || code,
              lon,
              lat,
              lev: addrLevel || 5,
              searchType: this.searchType,
            });
          });
          cb(rst);
        });
      } else {
        this.searchName = "";
        cb([]);
      }
    },
    handleSelect(item) {
      service.category({ code: item.code }).then((res) => {
        console.log('res.data.path', res.data.path)
        this.$store.commit("screenView/SET_LAYERS", res.data.path);
        this.$store.commit(
          "screenView/SET_MINGRIDCOUNT",
          res.data.minGridCount
        );
      });
      this.$store.commit("screenView/SET_ANCHOR_INFO", item);
    },
  },
};
</script>
<style lang="scss" scoped>
.search-part {
  width: 500px;
  z-index: 9999;
  display: inline-block;

  .search-input {
    width: 360px;
    border: 1px solid #5483D4;
    // background: linear-gradient(180deg, #506b72 0%, #082232 100%);
    background: linear-gradient(180deg, #5392FF 0%, rgba(16, 39, 93, 0.72) 100%);


    border-radius: 10px;

    ::v-deep .el-input-group__prepend {
      background: inherit;
      border: none;
      width: 120px;
      font-size: 14px;
      font-weight: 500;
      color: #ffffff;
      position: relative;

      &::after {
        content: "";
        position: absolute;
        right: 0;
        top: 8px;
        width: 1px;
        height: 24px;
        border-left: 1px solid #ffffff60;
      }
    }

    ::v-deep input {
      background: inherit;
      border: none;
      font-size: 14px;
      font-weight: 500;
      color: #ffffff;
    }

    ::v-deep .search-popper {
      transition: none;
      background: rgba(0, 0, 0, 0.6);
      width: 360px !important;
      left: inherit !important;
      border: 1px solid #5483D4;
      // background: linear-gradient(180deg, rgba(83, 146, 255, 1), rgba(16, 39, 93, 0.72));
      border-radius: 8px;

      .popper__arrow {
        display: none;
      }

      .el-autocomplete-suggestion__wrap {
        padding: 5px;

        li {
          background: rgba(16, 39, 93, 0.72);
          border: 1px solid #5483D4;
          border-radius: 8px;
          margin-bottom: 3px;
          color: #fff;
          box-sizing: border-box;
          padding: 10px;

          &:hover {
            border: 1px solid #5483D4;
            background: rgba(84, 131, 212, 1);

            opacity: 0.9;
          }
        }
      }
    }
  }
}

.search-item {
  display: flex;
  flex-direction: row;

  .search-left {
    background-size: 22px 22px;
    width: 50px;
    min-height: 22px;

    &.bg1 {
      background: url(../assets/common/point-f.png) center no-repeat;
    }

    &.bg2 {
      background: url("../assets/common/point-c.png") center no-repeat;
    }

    &.bg3 {
      background: url("../assets/common/point-s.png") center no-repeat;
    }

    &.bg-icon0 {
      background: url("../assets/common/bank.png") center no-repeat;
      background-size: 22px 22px;
    }

    &.bg-icon1 {
      background: url("../assets/common/address.png") center no-repeat;
      background-size: 22px 22px;
    }

    &.bg-icon2 {
      background: url("../assets/common/grid.png") center no-repeat;
      background-size: 22px 22px;
    }
  }

  .search-right {
    flex: 1;
    display: flex;
    flex-direction: column;

    &:hover {
      >span:first-of-type>i {
        color: #fff;
      }
    }

    >span {
      font-size: 14px;
      line-height: 24px;
      width: 280px;
      white-space: pre-wrap;
      // overflow: hidden;
      // white-space: nowrap;
      // text-overflow: ellipsis;
      color: #ffffff90;
    }

    >span:first-of-type {
      font-size: 16px;
      line-height: 26px;

      >i {
        color: #fff;
        font-weight: bold;
        font-style: normal;
      }
    }
  }
}
</style>