(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-4185d745"],{"1bd7":function(t,e,n){"use strict";var r=n("53df"),a=n.n(r);a.a},2638:function(t,e,n){"use strict";var r=n("c107"),a=n.n(r);a.a},"53df":function(t,e,n){},5456:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"manage-box"}},[n("div",{attrs:{id:"content"}},[n("div",{staticClass:"right_box"},[n("div",{staticClass:"navbar_box"},[n("navbar")],1),n("div",{staticClass:"routerView_box"},[n("router-view")],1)])]),t._m(0)])},a=[function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"footer"}},[n("p",[n("span",[t._v("Copyright © 安徽省生态环境厅  版权所有")]),n("span",{staticStyle:{"margin-left":"80px"}},[t._v("技术支持单位：生态环境部卫星环境应用中心")])])])}],i=n("d178"),c={name:"manage",components:{Navbar:i["a"]},mounted:function(){}},o=c,s=(n("2638"),n("6691")),u=Object(s["a"])(o,r,a,!1,null,"e80a0706",null);e["a"]=u.exports},"54f2":function(t,e,n){"use strict";var r=n("fea7"),a=n.n(r);a.a},"7cfd":function(t,e,n){var r=n("d3d8").f,a=Function.prototype,i=/^\s*function ([^ (]*)/,c="name";c in a||n("f9a5")&&r(a,c,{configurable:!0,get:function(){try{return(""+this).match(i)[1]}catch(t){return""}}})},a839:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"projectApproval"}},[n("Main")],1)},a=[],i=n("5456"),c={name:"ProjectApproval",components:{Main:i["a"]}},o=c,s=(n("54f2"),n("6691")),u=Object(s["a"])(o,r,a,!1,null,"2a128812",null);e["default"]=u.exports},be66:function(t,e){t.exports=r;var n=/\((?!\?)/g;function r(t,e,a){a=a||{},e=e||[];var i,c=a.strict,o=!1!==a.end,s=a.sensitive?"":"i",u=0,l=e.length,f=0,d=0;if(t instanceof RegExp){while(i=n.exec(t.source))e.push({name:d++,optional:!1,offset:i.index});return t}if(Array.isArray(t))return t=t.map((function(t){return r(t,e,a).source})),new RegExp("(?:"+t.join("|")+")",s);t=("^"+t+(c?"":"/"===t[t.length-1]?"?":"/?")).replace(/\/\(/g,"/(?:").replace(/([\/\.])/g,"\\$1").replace(/(\\\/)?(\\\.)?:(\w+)(\(.*?\))?(\*)?(\?)?/g,(function(t,n,r,a,i,c,o,s){n=n||"",r=r||"",i=i||"([^\\/"+r+"]+?)",o=o||"",e.push({name:a,optional:!!o,offset:s+u});var l=(o?"":n)+"(?:"+r+(o?n:"")+i+(c?"((?:[\\/"+r+"].+?)?)":"")+")"+o;return u+=l.length-t.length,l})).replace(/\*/g,(function(t,n){var r=e.length;while(r-- >l&&e[r].offset>n)e[r].offset+=3;return"(.*)"}));while(i=n.exec(t)){var p=0,h=i.index;while("\\"===t.charAt(--h))p++;p%2!==1&&((l+f===e.length||e[l+f].offset>i.index)&&e.splice(l+f,0,{name:d++,optional:!1,offset:i.index}),f++)}return t+=o?"$":"/"===t[t.length-1]?"":"(?=\\/|$)",new RegExp(t,s)}},c107:function(t,e,n){},d178:function(t,e,n){"use strict";var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{staticClass:"navbar"},t._l(t.levelList,(function(e,r){return n("div",{key:e.path},["noredirect"===e.redirect||r==t.levelList.length-1?n("span",{staticClass:"no-redirect"},[t._v("\n        "+t._s(t.generateTitle(e.meta.title))+"\n      ")]):n("a",{on:{click:function(n){return n.preventDefault(),t.handleLink(e)}}},[t._v(t._s(t.generateTitle(e.meta.title))+" >  ")])])})),0)},a=[],i=(n("7cfd"),n("be66")),c=n.n(i);function o(t){return t}var s={name:"Navbar",data:function(){return{levelList:null}},watch:{$route:function(){this.getBreadcrumb()}},created:function(){this.getBreadcrumb()},methods:{generateTitle:o,getBreadcrumb:function(){var t=this.$route.matched.filter((function(t){return t.name})),e=t[0];e&&e.name.trim().toLocaleLowerCase()!=="Dashboard".toLocaleLowerCase()&&(t=[{path:"/dashboard",meta:{title:""}}].concat(t)),this.levelList=t.filter((function(t){return t.meta&&t.meta.title&&!1!==t.meta.breadcrumb}))},pathCompile:function(t){var e=this.$route.params,n=c.a.compile(t);return n(e)},handleLink:function(t){var e=t.redirect,n=t.path;e?this.$router.push(e):this.$router.push(this.pathCompile(n))}}},u=s,l=(n("1bd7"),n("6691")),f=Object(l["a"])(u,r,a,!1,null,"36a9766d",null);e["a"]=f.exports},fea7:function(t,e,n){}}]);
//# sourceMappingURL=chunk-4185d745.9550bf0d.js.map