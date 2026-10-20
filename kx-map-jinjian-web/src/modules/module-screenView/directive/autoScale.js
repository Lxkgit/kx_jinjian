const Debounce = (fn, t) => {
  const delay = t || 500
  let timer
  return function () {
    const args = arguments
    if (timer) {
      clearTimeout(timer)
    }
    timer = setTimeout(() => {
      timer = null
      fn.apply(this, args)
    }, delay)
  }
}

export  const autoScaleFun = (el, { value = { width: 1920, height: 1080 }}) => {
  const { width = 1920, height = 1080 } = value
  const wrapper = el
  const [content] = wrapper.childNodes
  const objDom = document.createElement('object')
  const _handleObjectLoad = () => {
    objDom.contentDocument.defaultView.addEventListener('resize', Debounce(() => {
      const { width: cW, height: cH } = wrapper.getBoundingClientRect()
      // 初始化即进行缩放
      const hScale = cH / height
      const wScale =  cW / width
      content.style.transform = `scaleX(${wScale}) scaleY(${hScale})`
    }))
    const { width: cW, height: cH } = wrapper.getBoundingClientRect()
    const hScale = cH / height
    const wScale = cW / width
    content.style.transform = `scaleX(${wScale}) scaleY(${hScale})`
    content.style.position = 'absolute'
    content.style.display = 'block'
    content.style.overflow = 'hidden'
    content.style.transformOrigin = '0 0'
    content.style.height = `${height}px`
    content.style.width = `${width}px`
  }
  objDom.style.cssText = `
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    overflow: hidden;
    opacity: 0;
    pointer-events: none;
    z-index: -1;
  `
  objDom.onload = _handleObjectLoad
  objDom.type = 'text/html'
  objDom.data = 'about:blank'
  wrapper.appendChild(objDom)
}