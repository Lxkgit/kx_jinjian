
const req = require.context('../../assets/icons/svg', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys()


const re = /\.\/(.*)\.svg/

const icons = requireAll(req).map(i => {
  return i.match(re)[1]
})


// 注入的icon的下拉选择框
let svgs = require('../../assets/iconfont/iconfont.json')
svgs.glyphs.forEach(v=>{
  icons.push(v.font_class)
})
export default icons
