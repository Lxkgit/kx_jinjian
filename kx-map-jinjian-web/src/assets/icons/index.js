import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component
// 加入从 iconfont  引入的 icon svg 
// require('../iconfont/iconfont.js')
// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
