(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-46ce82a2"],{7302:function(t,e,n){},"7f7f":function(t,e,n){var r=n("86cc").f,o=Function.prototype,a=/^\s*function ([^ (]*)/,i="name";i in o||n("9e1e")&&r(o,i,{configurable:!0,get:function(){try{return(""+this).match(a)[1]}catch(t){return""}}})},"99b4":function(t,e,n){"use strict";n.d(e,"H",function(){return o}),n.d(e,"E",function(){return a}),n.d(e,"G",function(){return i}),n.d(e,"F",function(){return u}),n.d(e,"m",function(){return s}),n.d(e,"v",function(){return d}),n.d(e,"s",function(){return c}),n.d(e,"w",function(){return l}),n.d(e,"x",function(){return f}),n.d(e,"z",function(){return p}),n.d(e,"y",function(){return h}),n.d(e,"c",function(){return m}),n.d(e,"b",function(){return b}),n.d(e,"a",function(){return g}),n.d(e,"D",function(){return y}),n.d(e,"C",function(){return j}),n.d(e,"B",function(){return O}),n.d(e,"f",function(){return w}),n.d(e,"e",function(){return v}),n.d(e,"d",function(){return k}),n.d(e,"j",function(){return M}),n.d(e,"t",function(){return D}),n.d(e,"h",function(){return L}),n.d(e,"k",function(){return x}),n.d(e,"u",function(){return z}),n.d(e,"i",function(){return S}),n.d(e,"q",function(){return _}),n.d(e,"A",function(){return B}),n.d(e,"n",function(){return N}),n.d(e,"o",function(){return q}),n.d(e,"p",function(){return U}),n.d(e,"r",function(){return V}),n.d(e,"l",function(){return H}),n.d(e,"g",function(){return C});var r=n("b775"),o=function(t){return Object(r["a"])({url:"/sys/sysMobileUser/list",method:"post",data:t})},a=function(t){return Object(r["a"])({url:"/sys/sysMobileUser/add",method:"post",data:t})},i=function(t){return Object(r["a"])({url:"/sys/sysMobileUser/edit",method:"post",data:t})},u=function(t){return Object(r["a"])({url:"/sys/sysMobileUser/delete",method:"post",data:t})},s=function(t){return Object(r["a"])({url:"/sys/sysDepartment/getDepartmentList",method:"post",data:t})},d=function(t){return Object(r["a"])({url:"/sys/sysMobileUser/resetPassword",method:"post",data:t})},c=function(t){return Object(r["a"])({url:"/sys/role/list",method:"post",data:t})},l=function(t){return Object(r["a"])({url:"/sys/resource/list",method:"post",data:t})},f=function(t){return Object(r["a"])({url:"/sys/role/add",method:"post",data:t})},p=function(t){return Object(r["a"])({url:"/sys/role/edit",method:"post",data:t})},h=function(t){return Object(r["a"])({url:"/sys/role/delete",method:"post",data:t})},m=function(t){return Object(r["a"])({url:"/sys/sysAppVersion/list",method:"post",data:t})},b=function(t){return Object(r["a"])({url:"/sys/sysAppVersion/delete",method:"post",data:t})},g=function(t){return Object(r["a"])({url:"/sys/sysAppVersion/add",method:"post",data:t})},y=function(t){return Object(r["a"])({url:"/sys/sysShapeVersion/list",method:"post",data:t})},j=function(t){return Object(r["a"])({url:"/sys/sysShapeVersion/delete",method:"post",data:t})},O=function(t){return Object(r["a"])({url:"/sys/sysShapeVersion/add",method:"post",data:t})},w=function(t){return Object(r["a"])({url:"/sys/sysDbVersion/list",method:"post",data:t})},v=function(t){return Object(r["a"])({url:"/sys/sysDbVersion/delete",method:"post",data:t})},k=function(t){return Object(r["a"])({url:"/sys/sysDbVersion/add",method:"post",data:t})},M=function(t){return Object(r["a"])({url:"/ktdb/lmMarkerMobile/deletedList",method:"post",data:t})},D=function(t){return Object(r["a"])({url:"/ktdb/lmMarkerMobile/recover",method:"post",data:t})},L=function(t){return Object(r["a"])({url:"/ktdb/lmMarkerMobile/deleteForever",method:"post",data:t})},x=function(t){return Object(r["a"])({url:"/ktdb/lmBoard/deletedList",method:"post",data:t})},z=function(t){return Object(r["a"])({url:"/ktdb/lmBoard/recover",method:"post",data:t})},S=function(t){return Object(r["a"])({url:"/ktdb/lmBoard/deleteForever",method:"post",data:t})},_=function(t){return Object(r["a"])({url:"/ktdb/shpBatch/getNewList",method:"post",data:t})},B=function(t){return Object(r["a"])({url:"/ktdb/shpBatch/importPreData",method:"post",data:t})},N=function(t){return Object(r["a"])({url:"/sys/sysLog/list",method:"post",data:t})},q=function(t){return Object(r["a"])({url:"/sys/login/list",method:"post",data:t})},U=function(t){return Object(r["a"])({url:"/user/listUser",method:"post",data:t})},V=function(t){return Object(r["a"])({url:"/user/insertUser",method:"post",data:t})},H=function(t){return Object(r["a"])({url:"/user/updateUser",method:"post",data:t})},C=function(t){return Object(r["a"])({url:"/user/deleteUser",method:"post",data:t})}},decc:function(t,e,n){"use strict";var r=n("7302"),o=n.n(r);o.a},f4da:function(t,e,n){"use strict";n.r(e);var r=function(){var t=this,e=t.$createElement,n=t._self._c||e;return n("div",{attrs:{id:"loginManagement"}},[n("div",{staticClass:"queryBox"},[n("QueryBox",{ref:"getQuery",staticStyle:{width:"100%"},attrs:{formArr:t.queryFrom,id:"logQuery"},on:{query:t.getQueryData}})],1),n("div",{staticClass:"content_box"},[n("div",[n("Table",{attrs:{border:"",stripe:"",loading:t.loading,columns:t.columns1,data:t.data1,"highlight-row":"",size:"small","max-height":t.tableHeight}}),n("div",{staticClass:"page_box",attrs:{id:"pageBox"}},[n("Page",{ref:"pages",attrs:{total:t.total,"page-size":t.pageSize,current:t.pageNum,size:"small","show-total":"","show-elevator":""},on:{"update:current":function(e){t.pageNum=e},"on-change":t.pageChange}})],1)],1)])])},o=[],a=(n("7f7f"),n("aff7")),i=n("99b4"),u=n("cf45"),s={name:"logManagement",data:function(){var t=this;return{tableHeight:window.innerHeight-290,queryData:{},columns1:[{title:"序号",key:"index",width:100,align:"center",render:function(e,n){return e("span",n.index+(t.pageNum-1)*t.pageSize+1)}},{title:"登录人名称",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"login_name"},{title:"登录时间",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"create_time",render:function(t,e){return t("span",Object(u["c"])(e.row.create_time))}},{title:"登录设备类型",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"type",render:function(t,e){return 0===e.row.type?t("span","pc端"):1===e.row.type?t("span","移动端"):void 0}},{title:"登录IP",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"login_ip"},{title:"登录类型",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"login_type",render:function(t,e){return"0"===e.row.login_type?t("span","登入"):"1"===e.row.login_type?t("span","登出"):void 0}}],data1:[],total:0,pageNum:1,pageSize:Math.floor((window.innerHeight-290)/40)-1,loading:!1}},components:{QueryBox:a["a"]},computed:{queryFrom:function(){return[{type:1,field:"name",title:"登陆人名称"}]}},created:function(){this.getTableList()},mounted:function(){var t=this;window.addEventListener("resize",function(){t.tableHeight=window.innerHeight-290,t.pageSize=Math.floor((window.innerHeight-290)/40)-1,t.getTableList()},!1)},methods:{getQueryData:function(t){this.queryData=t,this.pageNum=1,this.getTableList()},getTableList:function(){var t,e=this;t=void 0===this.queryData.name?"":this.queryData.name,this.loading=!0,Object(i["o"])({data:{pageSize:this.pageSize,pageNum:this.pageNum,name:t}}).then(function(t){"0000"===t.data.code&&(e.data1=t.data.data.poList,e.total=t.data.data.poSum,e.loading=!1)}).catch(function(){e.$Notice.error({title:"服务器错误"})})},pageChange:function(t){this.pageNum=t,this.getTableList()}}},d=s,c=(n("decc"),n("2877")),l=Object(c["a"])(d,r,o,!1,null,"393b865e",null);e["default"]=l.exports}}]);
//# sourceMappingURL=chunk-46ce82a2.02eba8a8.js.map