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

export default (Vue) => {
  Vue.directive('auto-scale', {
    inserted: function (el, { value = { width: 1920, height: 1080 } }) {
      const { width = 1920, height = 1080 } = value
      const wrapper = el
      const [content] = wrapper.childNodes

      const applyStyles = (scale) => {
        content.style.transform = `scaleX(${scale.w}) scaleY(${scale.h})`
        content.style.position = 'absolute'
        content.style.display = 'block'
        content.style.overflow = 'hidden'
        content.style.transformOrigin = '0 0'
        content.style.height = `${height}px`
        content.style.width = `${width}px`
      }

      const updateScale = () => {
        const { width: cW, height: cH } = wrapper.getBoundingClientRect()
        const hScale = cH / height
        const wScale = cW / width
        applyStyles({ w: wScale, h: hScale })
      }

      const debouncedUpdateScale = Debounce(updateScale, 200)

      // Add resize listener to wrapper instead of object's contentDocument
      window.addEventListener('resize', debouncedUpdateScale)

      // Initial scale application
      updateScale()

      // Create and append object element (if still needed)
      // const objDom = document.createElement('object')
      // objDom.style.cssText = `
      //   display: block;
      //   position: absolute;
      //   top: 0;
      //   left: 0;
      //   height: 100%;
      //   width: 100%;
      //   overflow: hidden;
      //   opacity: 0;
      //   pointer-events: none;
      //   z-index: -1;
      // `
      // objDom.type = 'text/html'
      // objDom.data = 'about:blank'
      // wrapper.appendChild(objDom)

      // // Clean up listener when directive is unbound (optional, depending on use case)
      el._debouncedUpdateScale = debouncedUpdateScale
    },
    unbind: function (el) {
      if (el._debouncedUpdateScale) {
        window.removeEventListener('resize', el._debouncedUpdateScale)
        delete el._debouncedUpdateScale
      }
    },
  })
}
