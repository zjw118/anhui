(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0765b592"],{"02e7":function(e,t,n){"use strict";var a=n("e96b"),i=n.n(a);i.a},"1bd7":function(e,t,n){"use strict";var a=n("aad4"),i=n.n(a);i.a},3671:function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"manage-box"}},[n("SimpMain",[n("SliderMenu",{attrs:{menus:e.menus,activeName:e.activeName,openNames:e.openNames}})],1)],1)},i=[],r=n("5d73"),s=n.n(r),u=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"manage-box"}},[n("div",{attrs:{id:"content"}},[n("div",{staticClass:"sliderMenu_box"},[e._t("default")],2),n("div",{staticClass:"right_box"},[n("div",{staticClass:"navbar_box"},[n("navbar")],1),n("div",{staticClass:"routerView_box"},[n("router-view")],1)])]),e._m(0)])},o=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"footer"}},[n("p",[n("span",[e._v("Copyright 2019 © 青海省生态环境厅  版权所有")]),n("span",{staticStyle:{"margin-left":"80px"}},[e._v("技术支持单位：北京山海础石信息技术有限公司")])])])}],l=n("d178"),c={name:"SimpMain",components:{Navbar:l["a"]},mounted:function(){}},m=c,d=(n("38bd"),n("2877")),f=Object(d["a"])(m,u,o,!1,null,"6c4f7f18",null),h=f.exports,v=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"slidermenu"}},[n("Menu",{attrs:{theme:"light","active-name":e.activeName,"open-names":e.openNames}},e._l(e.menus,function(t,a){return n("Submenu",{key:a,attrs:{name:t.id}},[n("template",{slot:"title"},[n("Icon",{attrs:{type:t.icon}}),e._v("\n          "+e._s(t.title)+"\n      ")],1),e._l(t.child,function(t,a){return n("MenuItem",{key:a,attrs:{name:t.id}},[n("router-link",{staticClass:"link_style",attrs:{to:t.url}},[e._v("\n        "+e._s(t.title)+"\n        ")])],1)})],2)}),1)],1)},p=[],g={name:"SliderMenu",props:{menus:{},activeName:{},openNames:{}}},b=g,_=(n("6c0b"),Object(d["a"])(b,v,p,!1,null,"328fb6ad",null)),x=_.exports,y={name:"manage",data:function(){return{activeName:"1-1",openNames:["1"],menus:[{id:"1",title:"系统管理",icon:"ios-paper",child:[{id:"1-1",title:"用户管理",url:"/main/systemManage/userManage",meta:{id:19}},{id:"1-2",title:"角色管理",url:"/main/systemManage/roleManage",meta:{id:20}},{id:"1-4",title:"备份管理",url:"/main/systemManage/userManage",meta:{id:23}},{id:"1-5",title:"版本管理",url:"/main/systemManage/versionManage",meta:{id:21}},{id:"1-3",title:"回收站",url:"/main/systemManage/recycleManage",meta:{id:22}},{id:"1-6",title:"字典维护",url:"/main/systemManage/userManage",meta:{id:24}}]}]}},components:{SimpMain:h,SliderMenu:x},mounted:function(){var e=[];if(null!==this.$store.getters.getloginInfor()){var t=!0,n=!1,a=void 0;try{for(var i,r=s()(this.$store.getters.getloginInfor().resource);!(t=(i=r.next()).done);t=!0){var u=i.value;e.push(u.id)}}catch(o){n=!0,a=o}finally{try{t||null==r.return||r.return()}finally{if(n)throw a}}}this.menus[0].child=this.menus[0].child.filter(function(t){return-1!==e.indexOf(t.meta.id)})},methods:{}},M=y,w=(n("02e7"),Object(d["a"])(M,a,i,!1,null,"1503a971",null));t["default"]=w.exports},"38bd":function(e,t,n){"use strict";var a=n("eae3"),i=n.n(a);i.a},"6c0b":function(e,t,n){"use strict";var a=n("98b4"),i=n.n(a);i.a},"7f7f":function(e,t,n){var a=n("86cc").f,i=Function.prototype,r=/^\s*function ([^ (]*)/,s="name";s in i||n("9e1e")&&a(i,s,{configurable:!0,get:function(){try{return(""+this).match(r)[1]}catch(e){return""}}})},"98b4":function(e,t,n){},aad4:function(e,t,n){},bd11:function(e,t){e.exports=a;var n=/\((?!\?)/g;function a(e,t,i){i=i||{},t=t||[];var r,s=i.strict,u=!1!==i.end,o=i.sensitive?"":"i",l=0,c=t.length,m=0,d=0;if(e instanceof RegExp){while(r=n.exec(e.source))t.push({name:d++,optional:!1,offset:r.index});return e}if(Array.isArray(e))return e=e.map(function(e){return a(e,t,i).source}),new RegExp("(?:"+e.join("|")+")",o);e=("^"+e+(s?"":"/"===e[e.length-1]?"?":"/?")).replace(/\/\(/g,"/(?:").replace(/([\/\.])/g,"\\$1").replace(/(\\\/)?(\\\.)?:(\w+)(\(.*?\))?(\*)?(\?)?/g,function(e,n,a,i,r,s,u,o){n=n||"",a=a||"",r=r||"([^\\/"+a+"]+?)",u=u||"",t.push({name:i,optional:!!u,offset:o+l});var c=(u?"":n)+"(?:"+a+(u?n:"")+r+(s?"((?:[\\/"+a+"].+?)?)":"")+")"+u;return l+=c.length-e.length,c}).replace(/\*/g,function(e,n){var a=t.length;while(a-- >c&&t[a].offset>n)t[a].offset+=3;return"(.*)"});while(r=n.exec(e)){var f=0,h=r.index;while("\\"===e.charAt(--h))f++;f%2!==1&&((c+m===t.length||t[c+m].offset>r.index)&&t.splice(c+m,0,{name:d++,optional:!1,offset:r.index}),m++)}return e+=u?"$":"/"===e[e.length-1]?"":"(?=\\/|$)",new RegExp(e,o)}},d178:function(e,t,n){"use strict";var a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"navbar"},e._l(e.levelList,function(t,a){return n("div",{key:t.path},["noredirect"===t.redirect||a==e.levelList.length-1?n("span",{staticClass:"no-redirect"},[e._v("\n        "+e._s(e.generateTitle(t.meta.title))+"\n      ")]):n("a",{on:{click:function(n){return n.preventDefault(),e.handleLink(t)}}},[e._v(e._s(e.generateTitle(t.meta.title))+" >  ")])])}),0)},i=[],r=(n("7f7f"),n("bd11")),s=n.n(r);function u(e){return e}var o={name:"Navbar",data:function(){return{levelList:null}},watch:{$route:function(){this.getBreadcrumb()}},created:function(){this.getBreadcrumb()},methods:{generateTitle:u,getBreadcrumb:function(){var e=this.$route.matched.filter(function(e){return e.name}),t=e[0];t&&t.name.trim().toLocaleLowerCase()!=="Dashboard".toLocaleLowerCase()&&(e=[{path:"/dashboard",meta:{title:""}}].concat(e)),this.levelList=e.filter(function(e){return e.meta&&e.meta.title&&!1!==e.meta.breadcrumb})},pathCompile:function(e){var t=this.$route.params,n=s.a.compile(e);return n(t)},handleLink:function(e){var t=e.redirect,n=e.path;t?this.$router.push(t):this.$router.push(this.pathCompile(n))}}},l=o,c=(n("1bd7"),n("2877")),m=Object(c["a"])(l,a,i,!1,null,"36a9766d",null);t["a"]=m.exports},e96b:function(e,t,n){},eae3:function(e,t,n){}}]);
//# sourceMappingURL=chunk-0765b592.6a8ed950.js.map