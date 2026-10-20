import {identityMasking,phoneMasking} from '../utils'
const encryption_type = {
  'customerIdentity': (str)=>identityMasking(str),
  'customerPhone':(str)=>phoneMasking(str)
}
// v-encryption:[info.key]="{itemValue:value}"
export default {
  inserted(el,{value,arg}) { 
    const input = el.nodeName.toLowerCase() ==='input'?el:el.getElementsByTagName('input')[0]
    const {itemValue} = value
    el.$value = itemValue[arg]
    el.focusHandler=()=>{
      input.value = el.$value
    }
    el.blurHandler=()=>{
      const str = encryption_type[arg] ?encryption_type[arg](el.$value):el.$value
      input.value = str
    } 
    input.addEventListener('focus',el.focusHandler)
    input.addEventListener('blur',el.blurHandler)
    
  },
  componentUpdated(el,{value,arg}){
    el.$value = value.itemValue[arg]
  },
}