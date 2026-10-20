<template>
  <PageContent>
    <!-- 过滤器 -->
    <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4"
                  label-width="80px" >
      <el-button type="primary" @click="fetchList">
        查询
      </el-button>
    </i-filter-bar>
    <!-- 按钮操作 -->
    <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      <template #upload>
        <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/" multiple
                   :limit="3" :file-list="fileList">
          <el-button  type="info" plain>
            任务明细导出
          </el-button>
        </el-upload>
      </template>
    </i-button-bar>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe 
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :pageSize="formInline.pageSize" @currentChange="currentChange"
             @sizeChange="sizeChange">
      <template slot="yy" slot-scope="scope">
        <i-table-btns :btn-lists="tableBtn" @click="e=>clickHandle(e,scope.row)" :data="scope.row">
          <template #upload>
            <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/"
                       multiple :limit="3" :file-list="fileList">
              <el-button size="mini" type="text">
                点击上传
              </el-button>
            </el-upload>
          </template>
        </i-table-btns>
      </template>
      <template slot="yy2" slot-scope="scope">
        <i-table-btns :btn-lists="tableBtn" @click="e=>clickHandle(e,scope.row)" :data="scope.row">
          <template #upload>
            <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/"
                       multiple :limit="3" :file-list="fileList">
              <el-button size="mini" type="text">
                点击上传
              </el-button>
            </el-upload>
          </template>
        </i-table-btns>
      </template>
    </i-table>
  </PageContent>
</template>

<script>
export default ({
  data() {
    return {
      searchColumns: [
        {
          label: '责任机构',
          key: 'title',
          type: 'text',
          placeholder: '请输入关键字搜索',
        },
        {
          label: '任务状态',
          key: 'title',
          type: 'text',
          placeholder: '请输入关键字搜索',
        },
        {
          label: '任务名称',
          key: 'title',
          type: 'text',
          placeholder: '请输入关键字搜索',
        },
        {
          label: '层级选择',
          key: 'level',
          type: 'select',
          value: '0',
          options: [
            {
              value: '0',
              label: '本级通知',
            },
            {
              value: '1',
              label: '所有下辖',
            },
          ],
        },
        {
          label: '过期收拾',
          key: 'validPeriod',
          value: '2',
          type: 'select',
          options: [
            {
              value: '2',
              label: '全部',
            },
            {
              value: '0',
              label: '未过期',
            },
            {
              value: '1',
              label: '已过期',
            },
          ],
        },
      ],
      fileList: [],
      btnLists: [
        {
          icon: 'icon-Web',
          cname: '新建任务',
          ename: 'add',
          type: 'primary',
          // disabled: true,
        },
        {
          // icon: 'el-icon-upload',
          cname: '催办',
          ename: 'edit',
          type: 'primary',
          plain:true,
        },
        {
          cname: '任务明细导出',
          ename: 'upload',
          slot: true,
        },
      ],
      tableBtn: [
        {
          icon: 'el-icon-upload',
          cname: '新增',
          ename: 'edit',
          permi: 'test1',
          isShow: 'hasBtn'
        },
        {
          icon: 'el-icon-upload',
          cname: '发布',
          ename: 'open',
          disabled: "disabled",
        },
        {
          cname: '上传',
          ename: 'upload',
          slot: true,
        },
      ],
      loading: false,
      tableData: [
        { appName: '123',color1:'多菜颜色文字',color2:'多菜颜色文字',colorType:'danger'}, 
        { appName: '13', color1:'灰色',colorType:'info',disabled: true }, 
        { appName: '13', color1:'自定义',colorType: '#ff8822' }, 
        { appName: '12', hasBtn: true }]
       ,
      columns: [
        { label: '应用名称', prop: 'appName' },
        { label: 'IP', prop: 'requestIp' },
        { label: '请求接口', prop: 'gisApiName' },
        { label: '颜色', prop: 'color2',renderColor:true,colorType:'colorType',colorTips:true},
        { label: '颜色-没有tips', prop: 'color1',renderColor:true,colorType:'colorType'},
        { label: '操作', prop: 'yy', slot: true },
      ],
      formInline: {
        pageNum: 1,
        pageSize: 10,
      }
    }
  },
  methods: {
    clickHandle(e, item) {
      console.log(e, item)
    },
    currentChange() { },
    sizeChange() { },
  },
})
</script>

<style lang="scss" scoped>
</style>