(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-50418475"],{"1bd7":function(e,t,n){"use strict";var a=n("aad4"),i=n.n(a);i.a},"34d5":function(e,t,n){},3671:function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"manage-box"}},[n("SimpMain",[n("SliderMenu",{attrs:{menus:e.menus,activeName:e.activeName,openNames:e.openNames}})],1)],1)},i=[],r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"manage-box"}},[n("div",{attrs:{id:"content"}},[n("div",{staticClass:"sliderMenu_box"},[e._t("default")],2),n("div",{staticClass:"right_box"},[n("div",{staticClass:"navbar_box"},[n("navbar")],1),n("div",{staticClass:"routerView_box"},[n("router-view")],1)])]),e._m(0)])},s=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"footer"}},[n("p",[n("span",[e._v("Copyright © 安徽省生态环境厅  版权所有")]),n("span",{staticStyle:{"margin-left":"80px"}},[e._v("技术支持单位：生态环境部卫星环境应用中心")])])])}],u=n("d178"),c={name:"SimpMain",components:{Navbar:u["a"]},mounted:function(){}},l=c,o=(n("7c79"),n("2877")),m=Object(o["a"])(l,r,s,!1,null,"4d117b44",null),d=m.exports,f=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"slidermenu"}},[n("Menu",{attrs:{theme:"light","active-name":e.activeName,"open-names":e.openNames}},e._l(e.menus,function(t,a){return n("Submenu",{key:a,attrs:{name:t.id}},[n("template",{slot:"title"},[n("Icon",{attrs:{type:t.icon}}),e._v("\n          "+e._s(t.title)+"\n      ")],1),e._l(t.child,function(t,a){return n("MenuItem",{key:a,attrs:{name:t.id}},[n("router-link",{staticClass:"link_style",attrs:{to:t.url}},[e._v("\n        "+e._s(t.title)+"\n        ")])],1)})],2)}),1)],1)},p=[],v={name:"SliderMenu",props:{menus:{},activeName:{},openNames:{}}},h=v,g=(n("6c0b"),Object(o["a"])(h,f,p,!1,null,"328fb6ad",null)),b=g.exports,_={name:"manage",data:function(){return{activeName:"1-1",openNames:["1"],menus:[{id:"1",title:"系统管理",icon:"ios-paper",child:[{id:"1-2",title:"巡查人员管理",url:"/main/systemManage/mobileUserManage",meta:{id:30}},{id:"1-3",title:"角色管理",url:"/main/systemManage/roleManage",meta:{id:20}},{id:"1-4",title:"备份管理",url:"/main/systemManage/userManage",meta:{id:23}},{id:"1-7",title:"字典维护",url:"/main/systemManage/userManage",meta:{id:24}},{id:"1-12",title:"人类活动类型管理",url:"/main/systemManage/activitiesType",meta:{id:32}},{id:"1-11",title:"底图类别管理",url:"/main/systemManage/baseMapManagement",meta:{id:31}},{id:"1-8",title:"参数设置",url:"/main/systemManage/parameterSettings",meta:{id:27}},{id:"1-9",title:"日志管理",url:"/main/systemManage/logManagement",meta:{id:28}},{id:"1-10",title:"登录管理",url:"/main/systemManage/loginManagement",meta:{id:29}}]}]}},components:{SimpMain:d,SliderMenu:b},mounted:function(){},methods:{}},M=_,x=(n("e39c"),Object(o["a"])(M,a,i,!1,null,"d15c9e02",null));t["default"]=x.exports},6331:function(e,t,n){},"6c0b":function(e,t,n){"use strict";var a=n("98b4"),i=n.n(a);i.a},"7c79":function(e,t,n){"use strict";var a=n("6331"),i=n.n(a);i.a},"7f7f":function(e,t,n){var a=n("86cc").f,i=Function.prototype,r=/^\s*function ([^ (]*)/,s="name";s in i||n("9e1e")&&a(i,s,{configurable:!0,get:function(){try{return(""+this).match(r)[1]}catch(e){return""}}})},"98b4":function(e,t,n){},aad4:function(e,t,n){},bd11:function(e,t){e.exports=a;var n=/\((?!\?)/g;function a(e,t,i){i=i||{},t=t||[];var r,s=i.strict,u=!1!==i.end,c=i.sensitive?"":"i",l=0,o=t.length,m=0,d=0;if(e instanceof RegExp){while(r=n.exec(e.source))t.push({name:d++,optional:!1,offset:r.index});return e}if(Array.isArray(e))return e=e.map(function(e){return a(e,t,i).source}),new RegExp("(?:"+e.join("|")+")",c);e=("^"+e+(s?"":"/"===e[e.length-1]?"?":"/?")).replace(/\/\(/g,"/(?:").replace(/([\/\.])/g,"\\$1").replace(/(\\\/)?(\\\.)?:(\w+)(\(.*?\))?(\*)?(\?)?/g,function(e,n,a,i,r,s,u,c){n=n||"",a=a||"",r=r||"([^\\/"+a+"]+?)",u=u||"",t.push({name:i,optional:!!u,offset:c+l});var o=(u?"":n)+"(?:"+a+(u?n:"")+r+(s?"((?:[\\/"+a+"].+?)?)":"")+")"+u;return l+=o.length-e.length,o}).replace(/\*/g,function(e,n){var a=t.length;while(a-- >o&&t[a].offset>n)t[a].offset+=3;return"(.*)"});while(r=n.exec(e)){var f=0,p=r.index;while("\\"===e.charAt(--p))f++;f%2!==1&&((o+m===t.length||t[o+m].offset>r.index)&&t.splice(o+m,0,{name:d++,optional:!1,offset:r.index}),m++)}return e+=u?"$":"/"===e[e.length-1]?"":"(?=\\/|$)",new RegExp(e,c)}},d178:function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"navbar"},e._l(e.levelList,function(t,a){return n("div",{key:t.path},["noredirect"===t.redirect||a==e.levelList.length-1?n("span",{staticClass:"no-redirect"},[e._v("\n        "+e._s(e.generateTitle(t.meta.title))+"\n      ")]):n("a",{on:{click:function(n){return n.preventDefault(),e.handleLink(t)}}},[e._v(e._s(e.generateTitle(t.meta.title))+" >  ")])])}),0)},i=[],r=(n("7f7f"),n("bd11")),s=n.n(r);function u(e){return e}var c={name:"Navbar",data:function(){return{levelList:null}},watch:{$route:function(){this.getBreadcrumb()}},created:function(){this.getBreadcrumb()},methods:{generateTitle:u,getBreadcrumb:function(){var e=this.$route.matched.filter(function(e){return e.name}),t=e[0];t&&t.name.trim().toLocaleLowerCase()!=="Dashboard".toLocaleLowerCase()&&(e=[{path:"/dashboard",meta:{title:""}}].concat(e)),this.levelList=e.filter(function(e){return e.meta&&e.meta.title&&!1!==e.meta.breadcrumb})},pathCompile:function(e){var t=this.$route.params,n=s.a.compile(e);return n(t)},handleLink:function(e){var t=e.redirect,n=e.path;t?this.$router.push(t):this.$router.push(this.pathCompile(n))}}},l=c,o=(n("1bd7"),n("2877")),m=Object(o["a"])(l,a,i,!1,null,"36a9766d",null);t["a"]=m.exports},e39c:function(e,t,n){"use strict";var a=n("34d5"),i=n.n(a);i.a}}]);
//# sourceMappingURL=chunk-50418475.59bbf9a2.js.map