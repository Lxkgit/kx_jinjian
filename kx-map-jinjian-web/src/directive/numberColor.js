export default {
  bind: function (el, binding) {
    const { value } = binding;
    if (!!value) {
      let iconElement = document.createElement("i");
      let spanElement = document.createElement("span");
      let iconClassMap = {
        down: "el-icon-bottom minus-number",
        up: "el-icon-top positive-number",
      };
      if (/^-/.test(value)) {
        iconElement.className = iconClassMap.down;
        spanElement.innerHTML = Math.abs(value).toLocaleString();
      } else if (Number(value) > 0) {
        iconElement.className = iconClassMap.up;
        spanElement.innerHTML = Number(value).toLocaleString();
        spanElement.style.color = "#FF3D3D";
      } else {
        spanElement.innerHTML = value
      }
      el.innerHTML = "";
      el.appendChild(iconElement);
      el.appendChild(spanElement);
    }
  },
  update(el, binding, vnode) {
    const { value } = binding;
    if (!!value) {
      let iconElement = document.createElement("i");
      let spanElement = document.createElement("span");
      let iconClassMap = {
        down: "el-icon-bottom minus-number",
        up: "el-icon-top positive-number",
      };
      if (/^-/.test(value)) {
        iconElement.className = iconClassMap.down;
        spanElement.innerHTML = Math.abs(value).toLocaleString();
      } else if (Number(value) > 0) {
        iconElement.className = iconClassMap.up;
        spanElement.innerHTML = Number(value).toLocaleString();
        spanElement.style.color = "#FF3D3D";
      } else {
        spanElement.innerHTML = value
      }
      el.innerHTML = "";
      el.appendChild(iconElement);
      el.appendChild(spanElement);
    }
  },
};
