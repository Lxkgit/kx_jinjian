<template>
  <svg class="svg-icon" aria-hidden="true" :width="width" :height="height">
    <use :xlink:href="`#${name}`" />
  </svg>
</template>
 <script>
import { iconfontUrl } from "./config";
function createScriptUrlElements(scriptUrls, index = 0) {
  const currentScriptUrl = scriptUrls[index];
  if (typeof currentScriptUrl === 'string' && currentScriptUrl.length && !document.getElementById(currentScriptUrl)) {
    const script = document.createElement('script');
    script.setAttribute('id', currentScriptUrl);
    script.setAttribute('src', currentScriptUrl);
    script.setAttribute('data-namespace', currentScriptUrl);

    if (scriptUrls.length > index + 1) {
      script.onload = function () {
        createScriptUrlElements(scriptUrls, index + 1);
      };

      script.onerror = function () {
        createScriptUrlElements(scriptUrls, index + 1);
      };
    }
    document.body.appendChild(script);
  } else {
    if (scriptUrls.length > index + 1) {
      createScriptUrlElements(scriptUrls, index + 1);
    }
  }
}

createScriptUrlElements(iconfontUrl.reverse());

export default {
  name: 'IconFont',
  props: {
    name: {
      type: String,
      required: true
    },
    width: {
      type: String,
      default: '1.2em'
    },
    height: {
      type: String,
      default: '1.2em'
    }
  },
  data() {
    return {}
  },
  methods: {},
}
 </script>
 <style lang="scss" scoped>
.svg-icon {
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>