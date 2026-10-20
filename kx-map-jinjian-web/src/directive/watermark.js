const globalCanvas = document.createElement('canvas')
const globalWaterMark = document.createElement('div')
let waterMarkObserver = null
let waterMarkStyle = ''

// 定义指令配置项
export default {
  // ----- vue2 ----
  // 初始化设置
  // eslint-disable-next-line no-unused-vars
  bind(el, binding, vnode) {
    binding.def?.init(el, binding)
  },
  // 元素插入父元素时调用
  // eslint-disable-next-line no-unused-vars
  inserted(el, binding, vnode) {},
  // 组件更新时调用
  // eslint-disable-next-line no-unused-vars
  update(el, binding, vnode) {
    binding.def?.init(el, binding)
  },
  // 组件及子组件更新后调用
  // eslint-disable-next-line no-unused-vars
  componentUpdated(el, binding, vnode) {},
  // 解绑时调用
  // eslint-disable-next-line no-unused-vars
  unbind(el, binding, vnode) {
    // 停止观察。调用该方法后，DOM 再发生变动，也不会触发观察器
    waterMarkObserver && waterMarkObserver.disconnect()
  },
  // ----- vue2 ----
  // ----- vue3 ----
  // 在绑定元素的 attribute 或事件监听器被应用之前调用
  // eslint-disable-next-line no-unused-vars
  created(el, binding, vnode, prevNode) {
    binding.dir?.init(el, binding)
  },
  // 当指令第一次绑定到元素并且在挂载父组件之前调用。
  beforeMount() {},
  // 在绑定元素的父组件被挂载后调用，大部分自定义指令都写在这里。
  mounted() {},
  // 在更新包含组件的 VNode 之前调用。
  beforeUpdate() {},
  // 在包含组件的 VNode 及其子组件的 VNode 更新后调用。
  updated() {},
  // 在卸载绑定元素的父组件之前调用
  beforeUnmount() {},
  // 当指令与元素解除绑定且父组件已卸载时，只调用一次。
  unmounted() {
    // 停止观察。调用该方法后，DOM 再发生变动，也不会触发观察器
    waterMarkObserver && waterMarkObserver.disconnect()
  },
  // ----- vue3 ----

  /**
   * 初始化水印
   * @param el
   * @param binding
   */
  init(el, binding) {
    // 设置水印
    binding.def?.setWaterMark(el, binding) ||
      binding.dir?.setWaterMark(el, binding)
    // 启动监控
    binding.def?.createObserver(el, binding) ||
      binding.dir?.createObserver(el, binding)
  },
  /**
   * 默认配置
   * @returns {{fontFamily: string, color: string, size: number, text: string}}
   */
  defaultOptions() {
    return {
      // 水印文字
      text: '网格营销管理平台',
      // 水印文字颜色
      color: 'rgba(150, 150, 150, 1)',
      // 水印字体大小
      size: 12,
      // 水印字体类型
      fontFamily: 'Arial',
    }
  },
  /**
   * 水印容器class名
   * @returns {string}
   */
  waterMarkName() {
    return 'lx-water-mark'
  },
  /**
   * 水印容器样式
   * @returns {string}
   */
  waterMarkStyle() {
    let style = {
      display: 'block',
      overflow: 'hidden',
      position: 'absolute',
      left: '0px',
      top: '0px',
      'z-index': 100000,
      'font-size': '12px',
      'background-repeat': 'repeat',
      'background-position': 'center',
      'pointer-events': 'none',
      width: '100%',
      height: '100%',
    }
    let styleArr = Object.keys(style).map((key) => {
      return `${key}:${style[key]}`
    })
    return styleArr.join(';') + ';'
  },
  /**
   * 设置水印
   * @param el
   * @param binding
   */
  setWaterMark(el, binding) {
    const parentEl = el
    // eslint-disable-next-line no-unused-vars
    const { width, height } = parentEl?.getBoundingClientRect()
    // 拼接配置
    let defaultOptions =
      binding.def?.defaultOptions() || binding.dir?.defaultOptions()
    if (Object.prototype.toString.call(binding.value) === '[object Object]') {
      defaultOptions = Object.assign(defaultOptions, {
        text: binding.value.text || defaultOptions.text,
        color: binding.value.color || defaultOptions.color,
        size:
          binding.value?.size?.toString().replace('px', '') ||
          defaultOptions.size,
        fontFamily: binding.value.fontFamily || defaultOptions.fontFamily,
      })
    }
    // 获取对应的 canvas 画布相关的 base64 url
    const url =
      binding.def?.getDataUrl(defaultOptions) ||
      binding.dir?.getDataUrl(defaultOptions)
    // 创建 waterMark 父元素
    const waterMark = globalWaterMark || document.createElement('div')
    waterMark.className =
      binding.def?.waterMarkName() || binding.dir?.waterMarkName() // 方便自定义展示结果
    waterMarkStyle = `${
      binding.def?.waterMarkStyle() || binding.dir?.waterMarkStyle()
    };background-image: url(${url})`
    waterMark.setAttribute('style', waterMarkStyle)
    // 如果父元素有自己的stayle 则获取后和自定义的拼接，并避免重复添加
    let currStyle = parentEl?.getAttribute('style')
      ? parentEl?.getAttribute('style')
      : ''
    currStyle = currStyle?.includes('position: relative')
      ? currStyle
      : currStyle + 'position: relative;'
    // 将对应图片的父容器作为定位元素
    parentEl?.setAttribute('style', currStyle)
    // 将图片元素移动到 waterMark 中
    parentEl?.appendChild(waterMark)
  },
  /**
   * 生成水印图片，返回一个包含图片展示的数据 URL
   * @param options
   * @returns {string} 水印图片：base64-url
   */
  getDataUrl(options) {
    const { text, size, fontFamily, color } = options
    const rotate = -20
    const canvas = globalCanvas || document.createElement('canvas')
    const ctx = canvas.getContext('2d') // 获取canvas画布的绘图环境
    const width = 500
    const height = 250
    canvas.width = width // 单个水印大小，宽度
    canvas.height = height // 高度

    ctx.fillStyle = 'rgba(0, 0, 0, 0)' // 背景填充色
    ctx.fillRect(0, 0, width, height) // 填充区域大小

    ctx.save()
    ctx.font = `${size}px ${fontFamily}` // 文字字体大小
    ctx.fillStyle = color // 文字颜色
    ctx.translate(width / 2, height / 2) // 平移，旋转的中心点
    ctx?.rotate((rotate * Math.PI) / 360) // 水印旋转角度
    ctx.textBaseline = 'middle' // 垂直居中
    ctx.textAlign = 'center' // 水平居中
    ctx?.fillText(text, 0, 0)
    ctx.restore()
    ctx.clip()
    return canvas.toDataURL('image/png')
  },
  /**
   * 添加观察者，监听DOM变化，用 MutationObserver 对水印元素进行监听，删除、属性变化时，再立即生成一个水印元素
   * @param el
   * @param binding
   */
  createObserver(el, binding) {
    const className =
      binding.def?.waterMarkName() || binding.dir?.waterMarkName()
    const waterMarkEl = el.querySelector(`.${className}`)
    waterMarkObserver = new MutationObserver((mutationsList) => {
      if (mutationsList.length) {
        const { removedNodes, type, target } = mutationsList[0]
        const currStyle = waterMarkEl?.getAttribute('style')
        // 证明被删除了
        if (removedNodes[0] === waterMarkEl) {
          // 停止观察。调用该方法后，DOM 再发生变动，也不会触发观察器
          waterMarkObserver.disconnect()
          // 重新初始化（设置水印，启动监控）
          binding.def?.init(el, binding) || binding.dir?.init(el, binding)
        } else if (
          type === 'attributes' &&
          target === waterMarkEl &&
          currStyle !== waterMarkStyle
        ) {
          waterMarkEl.setAttribute('style', waterMarkStyle)
        }
      }
    })
    waterMarkObserver.observe(el, {
      attributes: true,
      childList: true,
      subtree: true,
      attributeOldValue: true,
    })
  },
}
