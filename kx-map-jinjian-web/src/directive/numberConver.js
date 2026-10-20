export default {
  bind: function (el, binding) {
    const { value } = binding;
    if (!!value) {
      if(Number(value)>99999999) {
        el.innerHTML = `${(Number(value)/10000).toFixed(2)}`
      } else{
        el.innerHTML = value
      }
    } else{
      el.innerHTML = '0'
    }
  },
  update(el, binding, vnode) {
    const { value } = binding;
    if (!!value) {
      if(Number(value)>99999999) {
        el.innerHTML = `${(Number(value)/10000).toFixed(2)}`
      } else{
        el.innerHTML = value
      }
    } else{
      el.innerHTML = '0'
    }
  },
};