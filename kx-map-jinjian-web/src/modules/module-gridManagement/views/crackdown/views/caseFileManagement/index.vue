<template>
  <div class="case-file-management">
    <el-row :gutter="20" class="top-cards">
      <el-col :span="6" v-for="(card, idx) in cards" :key="idx">
        <div class="card">
          <div class="card-top">
            <div class="card-icon-box">
            </div>
          </div>
          <div class="card-title">{{ card.title }}</div>
          <div class="card-count">
            <svg-icon :icon-class="card.icon" class-name="card-icon" />{{ card.count }}
          </div>
        </div>
      </el-col>
    </el-row>

    <div class="filter-panel">
      <el-input style="width:250px" v-model="filters.keyword" placeholder="档案编号/案件名称/主体名称" clearable />

      <el-select v-model="filters.caseType" placeholder="案件类型" clearable class="filter-item small">
        <el-option v-for="opt in caseTypeOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="filters.riskLevel" placeholder="风险等级" clearable class="filter-item small">
        <el-option v-for="opt in riskOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="filters.status" placeholder="档案状态" clearable class="filter-item small">
        <el-option v-for="opt in statusOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-select v-model="filters.street" placeholder="所属街道" clearable class="filter-item medium">
        <el-option v-for="opt in streetsOptions" :key="opt.value" :label="opt.label" :value="opt.value" />
      </el-select>
      <el-date-picker v-model="filters.dateRange" type="daterange" range-separator="→" start-placeholder="开始日期"
        end-placeholder="结束日期" value-format="yyyy-MM-dd" class="filter-item date-range" />

      <el-button type="primary" @click="handleSearch">查询</el-button>
      <el-button @click="handleReset">重置</el-button>
      <el-button icon="el-icon-plus" @click="handleAdd">新增档案</el-button>
    </div>

    <div class="table-area">
    <el-table :data="pagedData" stripe style="width:100%" class="case-table warning-table">
      <el-table-column prop="code" label="档案编号" width="180" fixed="left">
        <template slot-scope="scope">
          <a class="code-link" href="javascript:;" @click.prevent="handleDetail(scope.row)">{{ scope.row.code }}</a>
        </template>
      </el-table-column>

      <el-table-column prop="name" label="案件名称" min-width="240" show-overflow-tooltip />

      <el-table-column prop="caseTypeLabel" label="案件类型" width="140" />

      <el-table-column prop="riskLabel" label="风险等级" width="110" class-name="risk-col">
        <template slot-scope="scope">
          <el-tag :type="getRiskTagType(scope.row.risk)">{{ scope.row.riskLabel }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="region" label="区域" width="160" show-overflow-tooltip />

      <el-table-column prop="org" label="责任网点" min-width="220" show-overflow-tooltip />

      <el-table-column prop="status" label="档案状态" width="120" class-name="status-col">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'processing'" type="info">处置中</el-tag>
          <el-tag v-else-if="scope.row.status === 'archived'" type="success">已归档</el-tag>
          <el-tag v-else-if="scope.row.status === 'pending'" type="warning">待补证</el-tag>
          <el-tag v-else-if="scope.row.status === 'canceled'" type="success">已销号</el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="updatedAt" label="最近更新时间" width="160" />

      <el-table-column label="操作" width="120" fixed="right">
        <template slot-scope="scope">
          <a class="op-link" href="javascript:;" @click.prevent="handleDetail(scope.row)">详情</a>
          <span class="sep">|</span>
          <a class="op-link" href="javascript:;" @click.prevent="handleWarn(scope.row)">提醒</a>
        </template>
      </el-table-column>
    </el-table>

    <div class="table-footer">
      <el-pagination :total="filteredData.length" :page-size="pageSize" :page-sizes="[10, 20, 50]" layout="total, sizes, prev, pager, next, jumper"
        :current-page.sync="currentPage" @current-change="handlePageChange" />
    </div>
    </div>

    <!-- 新增档案弹窗 -->
    <el-dialog title="新增案件档案" :visible.sync="dialogVisible" width="720px" :close-on-click-modal="false"
      class="add-dialog">
      <el-form :model="formData" :rules="rules" ref="addForm" label-width="110px" label-position="right">
        <div class="form-grid">
          <el-form-item label="案件名称" prop="name">
            <el-input v-model="formData.name" placeholder="请输入案件名称" />
          </el-form-item>
          <el-form-item label="案件类型" prop="caseType">
            <el-select v-model="formData.caseType" placeholder="请选择案件类型" style="width: 100%;">
              <el-option label="非法集资" value="illegal_fund" />
              <el-option label="不法中介" value="illegal_agent" />
              <el-option label="养老领域涉非" value="elderly_illegal" />
              <el-option label="违规借贷" value="illegal_loan" />
            </el-select>
          </el-form-item>

          <el-form-item label="风险等级" prop="risk">
            <el-select v-model="formData.risk" placeholder="请选择风险等级" style="width: 100%;">
              <el-option label="高" value="high" />
              <el-option label="中" value="medium" />
              <el-option label="低" value="low" />
            </el-select>
          </el-form-item>
          <el-form-item label="线索来源" prop="clueSource">
            <el-input v-model="formData.clueSource" placeholder="请输入线索来源" />
          </el-form-item>

          <el-form-item label="主体名称" prop="entityName">
            <el-input v-model="formData.entityName" placeholder="请输入主体名称" />
          </el-form-item>
          <el-form-item label="统一代码" prop="creditCode">
            <el-input v-model="formData.creditCode" placeholder="请输入统一社会信用代码" />
          </el-form-item>

          <el-form-item label="所属街道" prop="street">
            <el-select v-model="formData.street" placeholder="请选择所属街道" style="width: 100%;">
              <el-option label="景山街道" value="jingshan" />
              <el-option label="梧田街道" value="wutian" />
              <el-option label="南白象街道" value="nanbaixiang" />
              <el-option label="茶山街道" value="chashan" />
              <el-option label="娄桥街道" value="louwqiao" />
              <el-option label="新桥街道" value="xinqiao" />
            </el-select>
          </el-form-item>
          <el-form-item label="涉案金额" prop="amount">
            <el-input v-model="formData.amount" placeholder="请输入涉案金额" />
          </el-form-item>

          <el-form-item label="详细地址" prop="address" class="full-width">
            <el-input v-model="formData.address" placeholder="请输入详细地址" />
          </el-form-item>

          <el-form-item label="案件摘要" prop="summary" class="full-width">
            <el-input v-model="formData.summary" type="textarea" :rows="3" placeholder="请输入案件摘要" maxlength="200"
              show-word-limit />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </div>
    </el-dialog>

    <!-- 详情抽屉 -->
    <el-drawer title="案件档案详情" :visible.sync="detailDrawerVisible" size="640px" direction="rtl"
      class="case-detail-drawer" :destroy-on-close="false">
      <div class="detail-container">
        <el-descriptions border column="4" size="small" class="detail-descriptions">
          <el-descriptions-item label="档案编号">{{ selectedDetail.code }}</el-descriptions-item>
          <el-descriptions-item label="案件状态" class="status-cell">
            <el-tag :type="statusTagType(selectedDetail.status)">{{ selectedDetail.statusLabel }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="案件名称" :span="3">{{ selectedDetail.name }}</el-descriptions-item>

          <el-descriptions-item label="案件类型">{{ selectedDetail.caseTypeLabel }}</el-descriptions-item>
          <el-descriptions-item label="涉案金额">{{ selectedDetail.amount }}</el-descriptions-item>
          <el-descriptions-item label="风险等级" class="risk-cell">
            <el-tag :type="getRiskTagType(selectedDetail.risk)">{{ selectedDetail.riskLabel }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="主体名称">{{ selectedDetail.entityName }}</el-descriptions-item>

          <el-descriptions-item label="统一信用代码">{{ selectedDetail.creditCode }}</el-descriptions-item>
          <el-descriptions-item label="归属区域" :span="3">{{ selectedDetail.region }}</el-descriptions-item>

          <el-descriptions-item label="详细地址" :span="3">{{ selectedDetail.address }}</el-descriptions-item>
          <el-descriptions-item label="责任部门">{{ selectedDetail.department }}</el-descriptions-item>
          <el-descriptions-item label="责任网点">{{ selectedDetail.org }}</el-descriptions-item>
          <el-descriptions-item label="线索来源" :span="3">{{ selectedDetail.clueSource }}</el-descriptions-item>
        </el-descriptions>

        <el-card shadow="never" class="detail-card" style="margin-bottom: 12px;">
          <div slot="header" class="detail-card-title">案件摘要</div>
          <div class="detail-card-body">{{ selectedDetail.summary }}</div>
        </el-card>

        <el-row gutter="16" class="detail-card-row">
          <el-col :span="12">
            <el-card shadow="never" class="detail-card">
              <div slot="header" class="detail-card-title">处置措施</div>
              <ul class="detail-list">
                <li v-for="(item, index) in selectedDetail.disposalMeasures" :key="index">{{ item }}</li>
              </ul>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card shadow="never" class="detail-card">
              <div slot="header" class="detail-card-title">证据目录</div>
              <ul class="detail-list">
                <li v-for="(item, index) in selectedDetail.evidenceList" :key="index">{{ item }}</li>
              </ul>
            </el-card>
          </el-col>
        </el-row>

        <el-card shadow="never" class="detail-card">
          <div slot="header" class="detail-card-title">归档时间轴</div>
          <el-timeline class="detail-timeline">
            <el-timeline-item v-for="(item, index) in selectedDetail.timeline" :key="index" :timestamp="item.time"
              :color="timelineColor(item.type)">
              <div class="timeline-text">{{ item.content }}</div>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { collaborationStreetOptions, extraCaseFiles } from '../caseCollaborationMockData'

export default {
  name: 'CaseFileManagement',
  data () {
    return {
      filters: {
        keyword: '',
        caseType: null,
        riskLevel: null,
        status: null,
        street: null,
        dateRange: []
      },
      caseTypeOptions: [
        { label: '非法集资', value: 'illegal_fund' },
        { label: '不法中介', value: 'illegal_agent' },
        { label: '养老领域涉非', value: 'elderly_illegal' },
        { label: '违规借贷', value: 'illegal_loan' }
      ],
      riskOptions: [
        { label: '高', value: 'high' },
        { label: '中', value: 'medium' },
        { label: '低', value: 'low' }
      ],
      statusOptions: [
        { label: '待补证', value: 'pending' },
        { label: '处置中', value: 'processing' },
        { label: '已归档', value: 'archived' },
        { label: '已销号', value: 'canceled' }
      ],
      streetsOptions: [
        ...collaborationStreetOptions.map((label, index) => ({ label, value: `mock-${index}` })),
        { label: '景山街道', value: 'jingshan' },
        { label: '梧田街道', value: 'wutian' },
        { label: '南白象街道', value: 'nanbaixiang' },
        { label: '茶山街道', value: 'chashan' },
        { label: '娄桥街道', value: 'louwqiao' },
        { label: '新桥街道', value: 'xinqiao' }
      ],

      originalData: [
        {
          code: 'DA-202608-001',
          name: '梧田街道直播引流型非法集资案件',
          caseTypeLabel: '非法集资',
          caseType: 'illegal_fund',
          risk: 'high',
          riskLabel: '高风险',
          region: '浙江省温州市瓯海区梧田街道龙霞社区',
          org: '中国建设银行股份有限公司温州龙霞支行',
          status: 'processing',
          statusLabel: '处置中',
          updatedAt: '2026-08-09 16:20',
          entityName: '温州瓯海龙霞商务咨询有限公司',
          creditCode: '91330304MA8F0A1201',
          address: '浙江省温州市瓯海区梧田街道龙霞路118号时代商务中心B座601室',
          department: '瓯海区处非办',
          clueSource: '金啸模型预警+群众举报',
          amount: '约860万元',
          summary: '该主体通过短视频平台持续投放"稳健增值"内容，引导老年客户到现场参加推介会，存在承诺保本收益、拆分特介绍奖励等特征。已完成现场核查、账户排摸和宣传劝阻，等待联合复核意见。',
          disposalMeasures: ['对涉案办公点完成现场取证', '对接责任网点开展资金流向排摸', '纳入重点宣传劝阻名单'],
          evidenceList: ['现场照片4张', '宣传单页扫描件', '账户流水摘要', '网格员走访记录'],
          timeline: [
            { time: '2026-08-03 09:10', content: '金啸模型命中直播高风险主体，自动生成案件档案草稿。', type: 'normal' },
            { time: '2026-08-04 14:30', content: '梧田街道责任网点完成首轮核查，确认存在违规宣传。', type: 'normal' },
            { time: '2026-08-06 10:00', content: '瓯海区处非办发起联合研判，要求补充受众清单与资金闭环证明。', type: 'normal' },
            { time: '2026-08-09 16:20', content: '补证材料上传完毕，待区级复核归档。', type: 'normal' }
          ]
        },
        {
          code: 'DA-202608-002',
          name: '娄桥街道写字楼违规助贷中介案件',
          caseTypeLabel: '不法中介',
          caseType: 'illegal_agent',
          risk: 'medium',
          riskLabel: '中风险',
          region: '浙江省温州市瓯海区娄桥街道中汇社区',
          org: '中国银行股份有限公司温州市瓯海区支行',
          status: 'archived',
          statusLabel: '已归档',
          updatedAt: '2026-08-07 09:40',
          entityName: '温州瓯海某助贷公司',
          creditCode: '91330304MA8F0A1202',
          address: '浙江省温州市瓯海区娄桥街道某写字楼',
          department: '瓯海区金融办',
          clueSource: '日常排查',
          amount: '约300万元',
          summary: '经排查发现该机构存在违规助贷行为，已责令整改。',
          disposalMeasures: ['责令整改', '罚款处理'],
          evidenceList: ['现场检查记录', '整改通知书'],
          timeline: [
            { time: '2026-08-01 10:00', content: '日常排查发现问题', type: 'normal' },
            { time: '2026-08-07 09:40', content: '整改完成，案件归档', type: 'success' }
          ]
        },
        {
          code: 'DA-202607-015',
          name: '新桥街道社区养老项目集资风险案件',
          caseTypeLabel: '养老领域涉非',
          caseType: 'elderly_illegal',
          risk: 'high',
          riskLabel: '高风险',
          region: '浙江省温州市瓯海区新桥街道金鑫社区',
          org: '温州银行股份有限公司新桥支行',
          status: 'pending',
          statusLabel: '待补证',
          updatedAt: '2026-08-08 18:10',
          entityName: '温州某养老服务公司',
          creditCode: '91330304MA8F0A1203',
          address: '浙江省温州市瓯海区新桥街道某小区',
          department: '瓯海区民政局',
          clueSource: '群众举报',
          amount: '约500万元',
          summary: '涉嫌以养老项目名义进行非法集资，正在调查中。',
          disposalMeasures: ['初步调查', '约谈负责人'],
          evidenceList: ['举报材料', '宣传资料'],
          timeline: [
            { time: '2026-07-15 09:00', content: '接到群众举报', type: 'warning' },
            { time: '2026-08-08 18:10', content: '完成初步调查，待补证', type: 'normal' }
          ]
        },
        {
          code: 'DA-202607-021',
          name: '仙岩街道违规民间借贷宣传案件',
          caseTypeLabel: '违规借贷',
          caseType: 'illegal_loan',
          risk: 'low',
          riskLabel: '低风险',
          region: '浙江省温州市瓯海区仙岩街道紫菜社区',
          org: '中国农业银行股份有限公司温州仙岩支行',
          status: 'canceled',
          statusLabel: '已销号',
          updatedAt: '2026-08-05 11:00',
          entityName: '温州某个体户',
          creditCode: '91330304MA8F0A1204',
          address: '浙江省温州市瓯海区仙岩街道某路',
          department: '瓯海区市场监管局',
          clueSource: '网格员巡查',
          amount: '约10万元',
          summary: '违规张贴民间借贷广告，已清理并教育。',
          disposalMeasures: ['清理广告', '口头警告'],
          evidenceList: ['现场照片', '巡查记录'],
          timeline: [
            { time: '2026-07-21 14:00', content: '网格员巡查发现违规广告', type: 'normal' },
            { time: '2026-08-05 11:00', content: '处理完毕，已销号', type: 'success' }
          ]
        },
        ...extraCaseFiles
      ],
      currentPage: 1,
      pageSize: 10,
      detailDrawerVisible: false,
      selectedDetail: {
        code: '',
        name: '',
        caseTypeLabel: '',
        risk: '',
        riskLabel: '',
        status: '',
        statusLabel: '',
        amount: '',
        entityName: '',
        creditCode: '',
        region: '',
        address: '',
        department: '',
        org: '',
        clueSource: '',
        summary: '',
        disposalMeasures: [],
        evidenceList: [],
        timeline: []
      },
      dialogVisible: false,
      formData: {
        name: '',
        caseType: '',
        risk: '',
        clueSource: '',
        entityName: '',
        creditCode: '',
        street: '',
        amount: '',
        address: '',
        summary: ''
      },
      rules: {
        name: [{ required: true, message: '请输入案件名称', trigger: 'blur' }],
        caseType: [{ required: true, message: '请选择案件类型', trigger: 'change' }],
        risk: [{ required: true, message: '请选择风险等级', trigger: 'change' }],
        clueSource: [{ required: true, message: '请输入线索来源', trigger: 'blur' }],
        entityName: [{ required: true, message: '请输入主体名称', trigger: 'blur' }],
        creditCode: [{ required: true, message: '请输入统一社会信用代码', trigger: 'blur' }],
        street: [{ required: true, message: '请选择所属街道', trigger: 'change' }],
        amount: [{ required: true, message: '请输入涉案金额', trigger: 'blur' }],
        address: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
        summary: [{ required: true, message: '请输入案件摘要', trigger: 'blur' }]
      }
    }
  },
  mounted () {
    this.fetchData()
    this.loadFilterOptions()
  },
  computed: {
    cards () {
      const data = this.originalData || []
      return [
        { title: '待补证档案', count: data.filter(item => item.status === 'pending').length, icon: 'clipboard' },
        { title: '处置中案件', count: data.filter(item => item.status === 'processing').length, icon: 'monitor' },
        { title: '已归档案件', count: data.filter(item => item.status === 'archived').length, icon: 'pdf' },
        { title: '高风险案件', count: data.filter(item => item.risk === 'high').length, icon: 'dangerIcon' }
      ]
    },
    filteredData () {
      const f = this.filters
      let data = (this.originalData || []).slice()

      if (f.keyword && f.keyword.toString().trim() !== '') {
        const kw = f.keyword.toString().toLowerCase()
        data = data.filter(item => {
          return (item.code && item.code.toLowerCase().includes(kw)) ||
            (item.name && item.name.toLowerCase().includes(kw)) ||
            (item.org && item.org.toLowerCase().includes(kw))
        })
      }

      if (f.caseType) {
        data = data.filter(item => item.caseType === f.caseType)
      }

      if (f.riskLevel) {
        data = data.filter(item => item.risk === f.riskLevel)
      }

      if (f.status) {
        data = data.filter(item => item.status === f.status)
      }

      if (f.street) {
        const st = this.streetsOptions.find(o => o.value === f.street)
        if (st) data = data.filter(item => item.region && item.region.includes(st.label))
      }

      if (f.dateRange && f.dateRange.length === 2) {
        const start = new Date(f.dateRange[0])
        const end = new Date(f.dateRange[1])
        end.setHours(23, 59, 59, 999)
        data = data.filter(item => {
          if (!item.updatedAt) return false
          const d = new Date(item.updatedAt.replace(/-/g, '/'))
          return d >= start && d <= end
        })
      }

      return data
    },

    pagedData () {
      const start = (this.currentPage - 1) * this.pageSize
      return this.filteredData.slice(start, start + this.pageSize)
    }
  },
  methods: {
    handleSearch () {
      this.currentPage = 1
      console.log('搜索条件：', this.filters)
    },
    handleReset () {
      this.filters = {
        keyword: '',
        caseType: null,
        riskLevel: null,
        status: null,
        street: null,
        dateRange: []
      }
      this.currentPage = 1
    },
    handleAdd () {
      this.formData = {
        name: '',
        caseType: '',
        risk: '',
        clueSource: '',
        entityName: '',
        creditCode: '',
        street: '',
        amount: '',
        address: '',
        summary: ''
      }
      this.dialogVisible = true
    },
    handleSave () {
      this.$refs.addForm.validate(valid => {
        if (valid) {
          const streetLabels = {
            jingshan: '景山街道',
            wutian: '梧田街道',
            nanbaixiang: '南白象街道',
            chashan: '茶山街道',
            louwqiao: '娄桥街道',
            xinqiao: '新桥街道'
          }
          const caseTypeLabels = {
            illegal_fund: '非法集资',
            illegal_agent: '不法中介',
            elderly_illegal: '养老领域涉非',
            illegal_loan: '违规借贷'
          }
          const riskLabels = {
            high: '高风险',
            medium: '中风险',
            low: '低风险'
          }

          const newCase = {
            code: 'DA-' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + '-' + String(this.originalData.length + 1).padStart(3, '0'),
            name: this.formData.name,
            caseType: this.formData.caseType,
            caseTypeLabel: caseTypeLabels[this.formData.caseType] || this.formData.caseType,
            risk: this.formData.risk,
            riskLabel: riskLabels[this.formData.risk] || this.formData.risk,
            region: '浙江省温州市瓯海区' + (streetLabels[this.formData.street] || this.formData.street),
            org: '待分配',
            status: 'pending',
            statusLabel: '待补证',
            updatedAt: new Date().toLocaleString(),
            entityName: this.formData.entityName,
            creditCode: this.formData.creditCode,
            address: this.formData.address,
            department: '待分配',
            clueSource: this.formData.clueSource,
            amount: this.formData.amount,
            summary: this.formData.summary,
            disposalMeasures: [],
            evidenceList: [],
            timeline: [{
              time: new Date().toLocaleString(),
              content: '手动创建案件档案',
              type: 'normal'
            }]
          }

          this.originalData.unshift(newCase)
          this.$message.success('新增档案成功')
          this.dialogVisible = false
          this.currentPage = 1
        } else {
          this.$message.error('请完善表单必填项')
          return false
        }
      })
    },
    handleDetail (row) {
      const statusMap = {
        processing: '处置中',
        archived: '已归档',
        pending: '待补证',
        canceled: '已销号'
      }
      this.selectedDetail = {
        ...row,
        statusLabel: row.statusLabel || statusMap[row.status] || '未知'
      }
      this.detailDrawerVisible = true
    },
    handleWarn (row) {
      this.$message.info('提醒已发送：' + row.code)
    },
    handlePageChange (page) {
      this.currentPage = page
    },
    getRiskTagType (risk) {
      if (risk === 'high') return 'danger'
      if (risk === 'medium') return 'warning'
      return 'success'
    },
    fetchData () {
      console.log('fetchData placeholder')
    },
    loadFilterOptions () {
      console.log('loadFilterOptions placeholder')
    },
    statusTagType (status) {
      if (status === 'processing') return 'info'
      if (status === 'archived') return 'success'
      if (status === 'pending') return 'warning'
      return 'default'
    },
    timelineColor (type) {
      if (type === 'warning') return '#f7ba2a'
      if (type === 'success') return '#67c23a'
      return '#409eff'
    }
  }
}
</script>

<style scoped>
.case-file-management {
  background: #f5f7fa;
}

.top-cards {
  margin-bottom: 20px;
}

.card {
  background: #fff;
  padding: 28px 20px;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03);
  min-height: 100px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-header {
  display: flex;
  align-items: center;
}

.card-icon {
  width: 18px;
  height: 18px;
  color: #409eff;
  margin-right: 10px;
}

.card-title {
  color: #8a8f98;
  font-size: 13px;
}

.card-count {
  font-size: 22px;
  color: #222;
  font-weight: 600;
}

.filter-panel {
  background: #fff;
  padding: 20px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.table-area {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.filter-item {
  flex: 0 0 auto;
  height: 36px;
}

.filter-item .el-input__inner,
.filter-item .el-select {
  height: 36px;
  line-height: 36px;
}

.filter-item.date-range {
  min-width: 240px;
}

.filter-item.keyword {
  width: 520px;
  min-width: 280px
}

.filter-item.small {
  width: 160px;
  min-width: 140px
}

.filter-item.medium {
  width: 220px;
  min-width: 180px
}

.filter-item .el-input__inner,
.filter-item .el-select .el-input__inner {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.filter-panel .el-button {
  margin-left: 6px;
}

.case-table {
  background: #fff;
  border-radius: 6px;
  overflow: hidden;
}

.case-table>>>.el-table__header th {
  background: #fff;
  color: #6b6f76;
  font-weight: 600;
}

.case-table>>>.el-table__row td {
  padding: 14px 12px;
  color: #333;
}

.warning-table>>>th.el-table__cell {
  height: 54px;
  padding: 0;
  background: #fafafa;
  color: #303133;
  font-weight: 600;
}

.warning-table>>>td.el-table__cell {
  height: 64px;
  padding: 0;
  color: #303133;
}

.warning-table>>>.cell {
  line-height: 20px;
}

.warning-table>>>.el-table__fixed {
  box-shadow: 4px 0 8px rgba(0, 0, 0, 0.04);
}

.warning-table>>>.el-table__fixed-right {
  box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
}

.case-table .risk-col .cell,
.case-table .status-col .cell {
  overflow: visible !important;
  white-space: nowrap !important;
}

.case-detail-drawer .el-drawer__header {
  padding: 18px 24px 12px;
  border-bottom: 1px solid #ebeef5;
}

.case-detail-drawer .el-drawer__body {
  padding: 20px 24px 80px;
}

.detail-container {
  min-height: 100%;
  padding: 0 16px;
}

.detail-descriptions {
  background: #fff;
  border-radius: 8px;
  padding: 0;
  margin-bottom: 20px;
}

.detail-table {
  background: #fff;
  border: 1px solid #eaedf2;
  border-radius: 8px;
  overflow: hidden;
}

.detail-row {
  display: grid;
  grid-template-columns: 110px 1fr 110px 1fr;
  grid-gap: 0;
  align-items: center;
  border-bottom: 1px solid #eaedf2;
}

.detail-row:last-child {
  border-bottom: none;
}

.detail-row.full-row {
  grid-template-columns: 110px 1fr;
}

.detail-label,
.detail-value {
  padding: 16px 20px;
}

.detail-label {
  color: #909399;
  font-size: 13px;
  background: #f8f9fb;
}

.detail-value {
  color: #333;
  font-size: 14px;
  background: #fff;
}

.detail-value.span-3 {
  grid-column: span 3;
}

.detail-row.full-row .detail-value {
  grid-column: 2 / -1;
}

.detail-row .detail-value+.detail-label {
  border-left: 1px solid #eaedf2;
}

.status-cell,
.risk-cell {
  display: flex;
  align-items: center;
}

.detail-block {
  background: #fff;
  border: 1px solid #eef2f6;
  border-radius: 6px;
  padding: 18px;
  margin-top: 16px;
}

.detail-card .el-card__header {
  padding: 14px 20px;
  font-weight: 600;
  color: #303133;
  font-size: 14px;
  border-bottom: 1px solid #ebeef5;
}

.detail-card .el-card__body {
  padding: 16px 20px 18px;
}

.detail-block-title {
  color: #303133;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 12px;
}

.detail-block-content {
  color: #606266;
  font-size: 14px;
  line-height: 24px;
}

.grid-two {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.detail-block-panel ul {
  margin: 0;
  padding: 0;
  list-style: none;
}

.detail-block-panel li {
  color: #606266;
  font-size: 14px;
  line-height: 24px;
}

.timeline {
  padding: 8px 0;
}

.timeline-item {
  display: flex;
  align-items: flex-start;
  margin-bottom: 16px;
}

.timeline-marker {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin-right: 12px;
  margin-top: 4px;
}

.timeline-marker.normal {
  background: #409eff;
}

.timeline-main {
  flex: 1;
}

.timeline-time {
  color: #909399;
  font-size: 12px;
  margin-bottom: 4px;
}

.timeline-text {
  color: #606266;
  font-size: 14px;
  line-height: 22px;
}

.table-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: 20px;
  margin-top: 20px;
}

.total {
  color: #999;
}

.sep {
  margin: 0 8px;
  color: #d0d4da;
}

.el-tag.is-danger {
  background: #fde2e2;
  color: #f56c6c;
  border-color: transparent
}

.el-tag.is-warning {
  background: #fff7e6;
  color: #f7ba2a;
  border-color: transparent
}

.el-tag.is-success {
  background: #f0f9eb;
  color: #67c23a;
  border-color: transparent
}

.el-tag.is-info {
  background: #e6f7ff;
  color: #409eff;
  border-color: transparent
}

.case-table .code-link,
.case-table .op-link {
  color: #1890ff;
  cursor: pointer;
  text-decoration: none;
}

.case-table .op-link:hover,
.case-table .code-link:hover {
  text-decoration: underline;
}

.el-drawer__title {
  border-bottom: 1px solid #ebeef5;
}

/* 新增弹窗样式 */
.add-dialog .form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px 20px;
  padding: 10px 10px 0 0;
}

.add-dialog .el-form-item.full-width {
  grid-column: span 2;
}

.add-dialog .dialog-footer {
  display: flex;
  justify-content: flex-end;
}
</style>
