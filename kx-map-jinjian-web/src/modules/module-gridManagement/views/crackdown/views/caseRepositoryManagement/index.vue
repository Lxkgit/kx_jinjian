<template>
  <div class="case-management-page">
    <!-- 统计卡片 -->
    <div class="statistic-cards">
      <div class="stat-card" v-for="(item, index) in statisticData" :key="index">
        <div class="card-icon">
          <i :class="item.icon"></i>
        </div>
        <div class="card-info">
          <div class="card-title">{{ item.title }}</div>
          <div class="card-value">{{ item.value }}</div>
        </div>
      </div>
    </div>

    <!-- 搜索区域（无 label） -->
    <div class="search-area">
      <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="0">
        <el-form-item>
          <el-input v-model="queryParams.keyword" placeholder="案例标题/摘要/来源档案号" clearable
            @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.type" placeholder="案例类型" clearable>
            <el-option label="非法集资" value="非法集资" />
            <el-option label="不法中介" value="不法中介" />
            <el-option label="养老领域涉非" value="养老领域涉非" />
            <el-option label="违规借贷" value="违规借贷" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.street" placeholder="所属街道" clearable>
            <el-option v-for="street in collaborationStreetOptions" :key="street" :label="street" :value="street" />
            <el-option label="梧田街道" value="梧田街道" />
            <el-option label="娄桥街道" value="娄桥街道" />
            <el-option label="新桥街道" value="新桥街道" />
            <el-option label="仙岩街道" value="仙岩街道" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.level" placeholder="发布级别" clearable>
            <el-option label="条线通报" value="条线通报" />
            <el-option label="内部培训" value="内部培训" />
            <el-option label="宣传宣教" value="宣传宣教" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryParams.status" placeholder="发布状态" clearable>
            <el-option label="已发布" value="已发布" />
            <el-option label="待审核" value="待审核" />
            <el-option label="已驳回" value="已驳回" />
          </el-select>
        </el-form-item>
        <el-form-item class="btn-group">
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          <el-button type="primary" plain @click="handleAdd">新增案例</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 表格 -->
    <div class="table-area">
      <el-table :data="pagedTableData" v-loading="loading" stripe style="width: 100%" class="warning-table">
        <el-table-column type="index" label="序号" width="60" align="center" fixed="left" />
        <el-table-column prop="title" label="案例标题" min-width="240" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" @click.stop="handleDetail(scope.row)">{{ scope.row.title }}</el-link>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="案例类型" width="100" />
        <el-table-column prop="tag" label="场景标签" width="150" />
        <el-table-column prop="street" label="所属街道" width="100" />
        <el-table-column prop="level" label="发布级别" width="100">
          <template slot-scope="{row}">
            <el-tag :type="getLevelType(row.level)" size="small">{{ row.level }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="发布状态" width="100">
          <template slot-scope="{row}">
            <el-tag :type="getStatusType(row.status)" size="small">{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="code" label="来源档案号" width="130" />
        <el-table-column prop="count" label="使用次数" width="90" align="center" />
        <el-table-column prop="time" label="最近更新" width="150" />
        <el-table-column label="操作" width="120" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click.stop="handleDetail(scope.row)">详情</el-button>
            <el-button type="text" size="small" @click.stop="handleGenerate(scope.row)">生成素材</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
          :page-sizes="[10, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
          :total="filteredTableData.length" />
      </div>
    </div>

    <!-- 新增弹窗 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="案例标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入案例标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="案例类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择" style="width: 100%">
                <el-option label="非法集资" value="非法集资" />
                <el-option label="不法中介" value="不法中介" />
                <el-option label="养老领域涉非" value="养老领域涉非" />
                <el-option label="违规借贷" value="违规借贷" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="场景标签" prop="tag">
              <el-input v-model="form.tag" placeholder="例如：线上引流+线下会销" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属街道" prop="street">
              <el-select v-model="form.street" placeholder="请选择" style="width: 100%">
                <el-option v-for="street in collaborationStreetOptions" :key="street" :label="street" :value="street" />
                <el-option label="梧田街道" value="梧田街道" />
                <el-option label="娄桥街道" value="娄桥街道" />
                <el-option label="新桥街道" value="新桥街道" />
                <el-option label="仙岩街道" value="仙岩街道" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="发布级别" prop="level">
              <el-select v-model="form.level" placeholder="请选择" style="width: 100%">
                <el-option label="条线通报" value="条线通报" />
                <el-option label="内部培训" value="内部培训" />
                <el-option label="宣传宣教" value="宣传宣教" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="来源档案号" prop="code">
              <el-input v-model="form.code" placeholder="例如：DA-202608-001" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="案例摘要" prop="desc">
          <el-input type="textarea" :rows="3" v-model="form.desc" placeholder="请输入案例摘要" />
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="培训要点(选填)">
              <el-input type="textarea" :rows="3" v-model="form.training" placeholder="请输入培训要点" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="处置动作(选填)">
              <el-input type="textarea" :rows="3" v-model="form.action" placeholder="请输入处置动作" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="现场照片">
          <el-upload action="#" list-type="picture-card" :auto-upload="false" :on-change="handlePhotoChange"
            :on-remove="handlePhotoRemove" :file-list="form.photoFiles">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="附件材料">
          <el-upload action="#" :auto-upload="false" :on-change="handleFileChange" :on-remove="handleFileRemove"
            :file-list="form.fileList">
            <el-button size="small" icon="el-icon-upload">上传附件</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="新闻报道(选填)">
          <el-input type="textarea" :rows="3" v-model="form.news" placeholder="请输入新闻报道" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </div>
    </el-dialog>

    <!-- 详情抽屉 -->
    <el-drawer title="案例详情" :visible.sync="drawerVisible" direction="rtl" size="800px" append-to-body>
      <div class="drawer-container">
        <div class="block-box">
          <div class="block-title">基础信息</div>
          <el-descriptions :column="2" border size="small">
            <el-descriptions-item label="案例标题">{{ currentRow.title }}</el-descriptions-item>
            <el-descriptions-item label="案例类型">{{ currentRow.type }}</el-descriptions-item>
            <el-descriptions-item label="场景标签">{{ currentRow.tag }}</el-descriptions-item>
            <el-descriptions-item label="所属区域">{{ currentRow.area }}</el-descriptions-item>
            <el-descriptions-item label="发布级别"><el-tag type="info" size="small">{{ currentRow.level
            }}</el-tag></el-descriptions-item>
            <el-descriptions-item label="来源档案号">{{ currentRow.code }}</el-descriptions-item>
            <el-descriptions-item label="发布状态"><el-tag type="success" size="small">{{ currentRow.status
            }}</el-tag></el-descriptions-item>
            <el-descriptions-item label="最近更新">{{ currentRow.time }}</el-descriptions-item>
          </el-descriptions>
        </div>

        <div class="block-box">
          <div class="block-title">案例摘要</div>
          <div class="text-content">{{ currentRow.desc }}</div>
        </div>

        <div class="grid-box">
          <div class="grid-item">
            <div class="block-title small">培训要点</div>
            <div class="text-content list-style">{{ currentRow.training }}</div>
          </div>
          <div class="grid-item">
            <div class="block-title small">处置动作</div>
            <div class="text-content list-style">{{ currentRow.action }}</div>
          </div>
          <div class="grid-item full-width">
            <div class="block-title small">现场照片</div>
            <div class="photo-list">
              <div class="photo-item" v-for="(img, idx) in currentRow.imgs" :key="idx">
                <el-image :src="img.url" fit="cover" :preview-src-list="[img.url]">
                  <div slot="error" class="image-slot"><i class="el-icon-picture-outline"></i></div>
                </el-image>
                <div class="photo-desc">{{ img.name }}</div>
              </div>
            </div>
          </div>
          <div class="grid-item">
            <div class="block-title small">附件材料</div>
            <div class="file-list">
              <div class="file-item" v-for="(f, idx) in currentRow.files" :key="idx">
                <i class="el-icon-document"></i>
                <span class="name">{{ f.name }}</span>
                <el-button type="text" size="mini" class="btn-view">查看</el-button>
              </div>
            </div>
          </div>
          <div class="grid-item">
            <div class="block-title small">新闻报道/宣传参考</div>
            <div class="news-list">
              <div class="news-item" v-for="(n, idx) in currentRow.newsList" :key="idx">
                <div class="badge">{{ n.type }}</div>
                <div class="info">
                  <div class="title">{{ n.title }}</div>
                  <div class="meta">{{ n.meta }}</div>
                </div>
                <el-button type="text" size="mini" class="btn-view">查看报道</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { collaborationStreetOptions, extraCaseRepository } from '../caseCollaborationMockData'

export default {
  name: 'CaseManagement',
  data () {
    return {
      loading: false,
      pageNum: 1,
      pageSize: 10,
      queryParams: { keyword: '', type: '', street: '', level: '', status: '' },
      tableData: [
        {
          id: 1,
          title: '梧田街道直播引流型非法集资识别案例',
          type: '非法集资',
          tag: '线上引流+线下会销',
          street: '梧田街道',
          level: '条线通报',
          status: '已发布',
          code: 'DA-202608-001',
          count: 126,
          time: '2026-08-09 18:00',
          area: '浙江省-温州市-瓯海区-梧田街道',
          desc: '2026年8月3日，浙江省温州市瓯海区梧田街道龙霞社区直播会销点位被识别出存在以“稳健收益”为噱头吸引老年客群的风险宣传。瓯海区处非办随即联动责任网点、社区和街道开展现场核查、账户排摸、宣传劝阻和证据固定。经处置，会销活动已停止，重点参与人员均已完成风险提示，涉事主体纳入持续跟踪名单，并形成条线通报案例。',
          training: '将线上流量线索转化为线下走访任务\n一线网点如何固定宣传证据\n如何在客户沟通中避免引发集中投诉',
          action: '快速锁定宣传点位与主讲人员\n由责任网点同步开展账户排摸\n联动社区完成重点人群劝阻提示',
          imgs: [
            { name: '宣传折页拍摄照片', url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg' },
            { name: '会销现场照片', url: 'https://fuss10.elemecdn.com/1/34/19aa98b1fcb2781c4fba33d850549jpeg.jpeg' }
          ],
          files: [
            { name: '会销签到表扫描件' },
            { name: '账户排摸摘要表' },
            { name: '联合核查纪要' }
          ],
          newsList: [
            { type: '报道', title: '瓯海区梧田街道开展直播引流涉非风险排查工作简报', meta: '瓯海区防非工作专班 | 2026-08-04' },
            { type: '报道', title: '温州晚报防非专栏关注梧田街道老年客群风险提示行动', meta: '温州晚报 | 2026-08-06' }
          ]
        },
        {
          id: 2,
          title: '娄桥街道助贷中介违规宣传整治案例',
          type: '不法中介',
          tag: '写字楼招商+门店导流',
          street: '娄桥街道',
          level: '内部培训',
          status: '已发布',
          code: 'DA-202608-002',
          count: 82,
          time: '2026-08-07 09:40',
          area: '浙江省-温州市-瓯海区-娄桥街道',
          desc: '针对近期发现的助贷中介违规揽客问题，娄桥街道开展专项整治，联合市场监管、公安等部门对辖区内涉及违规宣传的中介机构进行突击检查，责令整改并建立黑名单制度。',
          training: '识别违规话术\n固定电子证据\n跨部门协作机制',
          action: '约谈中介机构\n责令整改\n建立黑名单制度',
          imgs: [],
          files: [
            { name: '现场检查笔录' },
            { name: '责令整改通知书' }
          ],
          newsList: [
            { type: '报道', title: '娄桥街道重拳出击整治不法贷款中介', meta: '温州日报 | 2026-08-08' }
          ]
        },
        {
          id: 3,
          title: '新桥街道养老项目涉非宣传劝阻案例',
          type: '养老领域涉非',
          tag: '社区活动+养老公寓推介',
          street: '新桥街道',
          level: '宣传宣教',
          status: '待审核',
          code: 'DA-202607-015',
          count: 54,
          time: '2026-08-08 18:10',
          area: '浙江省-温州市-瓯海区-新桥街道',
          desc: '近期，新桥街道在开展社区养老驿站巡查时，发现某养老机构存在夸大收益、混淆养老与金融产品概念的行为，及时进行了劝阻和风险提示。',
          training: '养老项目常见涉非话术\n如何向老年群体普及金融风险\n与民政部门联动机制',
          action: '现场制止宣传活动\n约谈机构负责人\n移交线索至处置专班',
          imgs: [],
          files: [],
          newsList: []
        },
        {
          id: 4,
          title: '仙岩街道违规借贷广告清理销项案例',
          type: '违规借贷',
          tag: '街边门店广告',
          street: '仙岩街道',
          level: '内部培训',
          status: '已发布',
          code: 'DA-202607-021',
          count: 39,
          time: '2026-08-05 11:00',
          area: '浙江省-温州市-瓯海区-仙岩街道',
          desc: '仙岩街道组织城管、市监等部门对辖区内的违规借贷广告进行全面清理，累计拆除违规广告牌12块，约谈相关商户5家。',
          training: '违规借贷广告识别标准\n联合执法流程\n长效管理机制',
          action: '全面摸排\n集中清理\n约谈警示',
          imgs: [],
          files: [],
          newsList: []
        },
        ...extraCaseRepository
      ],
      dialogVisible: false,
      drawerVisible: false,
      form: {
        title: '',
        type: '',
        tag: '',
        street: '',
        level: '',
        code: '',
        desc: '',
        training: '',
        action: '',
        news: '',
        photoFiles: [],
        imgs: [],
        fileList: [],     // 新增：附件上传的文件列表
        files: []         // 新增：存储附件信息
      },
      rules: {
        title: [{ required: true, message: '请输入案例标题', trigger: 'blur' }],
        type: [{ required: true, message: '请选择案例类型', trigger: 'change' }]
      },
      currentRow: {}
    }
  },
  computed: {
    collaborationStreetOptions () {
      return collaborationStreetOptions
    },
    statisticData () {
      return [
        { title: '已发布案例', value: this.tableData.filter(item => item.status === '已发布').length, icon: 'el-icon-document-checked' },
        { title: '本月新增', value: this.tableData.filter(item => item.time.startsWith('2026-08')).length, icon: 'el-icon-plus' },
        { title: '重点涉非案例', value: this.tableData.filter(item => item.type === '非法集资' || item.type === '养老领域涉非').length, icon: 'el-icon-warning-outline' },
        { title: '培训使用次数', value: this.tableData.reduce((sum, item) => sum + Number(item.count || 0), 0), icon: 'el-icon-view' }
      ]
    },
    filteredTableData () {
      let list = this.tableData;
      if (this.queryParams.keyword) {
        list = list.filter(item =>
          item.title.includes(this.queryParams.keyword) ||
          item.code.includes(this.queryParams.keyword)
        );
      }
      if (this.queryParams.type) {
        list = list.filter(item => item.type === this.queryParams.type);
      }
      if (this.queryParams.street) {
        list = list.filter(item => item.street === this.queryParams.street);
      }
      if (this.queryParams.level) {
        list = list.filter(item => item.level === this.queryParams.level);
      }
      if (this.queryParams.status) {
        list = list.filter(item => item.status === this.queryParams.status);
      }
      return list;
    },
    pagedTableData () {
      const start = (this.pageNum - 1) * this.pageSize
      return this.filteredTableData.slice(start, start + this.pageSize)
    }
  },
  methods: {
    getLevelType (l) {
      const map = { '条线通报': 'primary', '内部培训': 'info', '宣传宣教': 'warning' };
      return map[l] || '';
    },
    getStatusType (s) {
      const map = { '已发布': 'success', '待审核': 'warning' };
      return map[s] || 'info';
    },
    handleQuery () {
      this.pageNum = 1;
      this.loading = true;
      setTimeout(() => { this.loading = false; }, 300);
    },
    resetQuery () {
      // 修复1：清空所有查询条件
      this.queryParams = { keyword: '', type: '', street: '', level: '', status: '' };
      this.$refs.queryForm.resetFields();
      this.handleQuery();
    },
    handleSizeChange (v) {
      this.pageSize = v;
      this.pageNum = 1;
    },
    handleCurrentChange (v) { this.pageNum = v; },
    handleDetail (row) {
      this.currentRow = row;
      this.drawerVisible = true;
    },
    handleGenerate (row) {
      this.$message.success(`已为「${row.title}」生成素材`);
    },
    handleAdd () {
      this.form = {
        title: '',
        type: '',
        tag: '',
        street: '',
        level: '',
        code: '',
        desc: '',
        training: '',
        action: '',
        news: '',
        photoFiles: [],
        imgs: [],
        fileList: [],
        files: []
      };
      this.dialogVisible = true;
    },
    // 图片上传
    handlePhotoChange (file, fileList) {
      const url = URL.createObjectURL(file.raw);
      this.form.imgs.push({ name: file.name, url: url });
      this.form.photoFiles = fileList;
    },
    handlePhotoRemove (file, fileList) {
      const index = this.form.imgs.findIndex(img => img.name === file.name);
      if (index !== -1) {
        URL.revokeObjectURL(this.form.imgs[index].url);
        this.form.imgs.splice(index, 1);
      }
      this.form.photoFiles = fileList;
    },
    // 附件上传
    handleFileChange (file, fileList) {
      this.form.files.push({ name: file.name });
      this.form.fileList = fileList;
    },
    handleFileRemove (file, fileList) {
      const index = this.form.files.findIndex(f => f.name === file.name);
      if (index !== -1) {
        this.form.files.splice(index, 1);
      }
      this.form.fileList = fileList;
    },
    submitForm () {
      this.$refs.form.validate(valid => {
        if (valid) {
          const newItem = {
            id: Date.now(),
            title: this.form.title,
            type: this.form.type,
            tag: this.form.tag,
            street: this.form.street,
            level: this.form.level,
            status: '待审核',
            code: this.form.code,
            count: 0,
            time: new Date().toLocaleString(),
            area: this.form.street,
            desc: this.form.desc,
            training: this.form.training || '',
            action: this.form.action || '',
            imgs: this.form.imgs.slice(),
            files: this.form.files.slice(),   // 修复2：将附件信息存入新记录
            newsList: this.form.news ? [{ type: '报道', title: this.form.news.substring(0, 20), meta: '刚刚发布' }] : []
          };
          this.tableData.unshift(newItem);
          this.dialogVisible = false;
          this.$message.success('新增案例成功');
        } else {
          this.$message.error('请填写必填项');
        }
      });
    }
  }
}
</script>

<style lang="scss" scoped>
/* ========== 以下样式与上一版完全一致，未做任何修改 ========== */
.case-management-page {
  padding: 20px;
  background: #f0f2f5;
  min-height: 100vh;

  .statistic-cards {
    display: flex;
    justify-content: space-between;
    margin-bottom: 20px;

    .stat-card {
      background: #fff;
      border-radius: 4px;
      padding: 20px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
      display: flex;
      align-items: center;
      width: 24%;
      transition: all 0.3s;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 20px 0 rgba(0, 0, 0, 0.1);
      }

      .card-icon {
        font-size: 40px;
        margin-right: 15px;
        color: #409EFF;
      }

      .card-info {
        .card-title {
          font-size: 14px;
          color: #606266;
          margin-bottom: 5px;
        }

        .card-value {
          font-size: 24px;
          font-weight: bold;
          color: #303133;
        }
      }
    }
  }

  .search-area {
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    margin-bottom: 20px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

    ::v-deep .el-form--inline .el-form-item {
      margin-right: 20px;
      margin-bottom: 0;
    }

    ::v-deep .el-form-item__label {
      display: none;
    }

    ::v-deep .el-form-item__content {
      line-height: 32px;
    }

    .btn-group {
      margin-left: auto;
    }
  }

  .table-area {
    background: #fff;
    padding: 20px;
    border-radius: 4px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);

    .el-table {
      margin-top: 10px;
    }

    .pagination-container {
      margin-top: 20px;
      text-align: right;
    }
  }
}

.warning-table {
  ::v-deep th.el-table__cell {
    height: 54px;
    padding: 0;
    background: #fafafa;
    color: #303133;
    font-weight: 600;
  }

  ::v-deep td.el-table__cell {
    height: 64px;
    padding: 0;
    color: #303133;
  }

  ::v-deep .cell {
    line-height: 20px;
  }

  ::v-deep .el-table__fixed {
    box-shadow: 4px 0 8px rgba(0, 0, 0, 0.04);
  }

  ::v-deep .el-table__fixed-right {
    box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
  }
}

.drawer-container {
  padding: 0 20px 20px;
  background: #f0f2f5;
  height: 100%;
  box-sizing: border-box;
  overflow-y: auto;

  .block-box {
    background: #fff;
    padding: 15px;
    border-radius: 4px;
    margin-bottom: 15px;
    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);

    .block-title {
      font-size: 14px;
      font-weight: bold;
      margin-bottom: 12px;
      padding-bottom: 8px;
      border-bottom: 1px solid #ebeef5;
      color: #303133;
    }

    .text-content {
      font-size: 13px;
      color: #606266;
      line-height: 1.6;
    }

    .list-style {
      white-space: pre-line;
    }
  }

  .grid-box {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-template-rows: auto auto auto;
    gap: 15px;
    margin-bottom: 15px;

    .grid-item {
      background: #fff;
      padding: 15px;
      border-radius: 4px;
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
      display: flex;
      flex-direction: column;

      &.full-width {
        grid-column: 1 / 3;
      }
    }

    .block-title.small {
      font-size: 13px;
      margin-bottom: 8px;
      border-bottom: none;
      padding-bottom: 0;
    }
  }

  .photo-list {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    margin-top: 5px;

    .photo-item {
      width: calc(50% - 5px);

      .el-image {
        width: 100%;
        height: 100px;
        border-radius: 4px;
        background: #eee;
      }

      .photo-desc {
        font-size: 12px;
        color: #909399;
        text-align: center;
        margin-top: 4px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }

  .file-list,
  .news-list {
    margin-top: 5px;
    flex: 1;
    overflow-y: auto;

    .file-item,
    .news-item {
      display: flex;
      align-items: center;
      padding: 6px 0;
      border-bottom: 1px solid #f5f7fa;
    }

    .file-item i {
      color: #409EFF;
      margin-right: 8px;
      font-size: 14px;
    }

    .file-item .name {
      flex: 1;
      font-size: 13px;
      color: #606266;
    }

    .news-item {
      .badge {
        width: 32px;
        text-align: center;
        background: #e6f0ff;
        color: #409EFF;
        font-size: 11px;
        padding: 2px 0;
        border-radius: 2px;
        margin-right: 10px;
        flex-shrink: 0;
      }

      .info {
        flex: 1;
      }

      .title {
        font-size: 13px;
        color: #303133;
        line-height: 1.4;
      }

      .meta {
        font-size: 11px;
        color: #909399;
        margin-top: 2px;
      }
    }

    .btn-view {
      padding: 0;
      font-size: 12px;
      color: #409EFF;
    }
  }
}
</style>
