(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-52c41f94"],{"0a34":function(t,e,a){},"39f3":function(t,e,a){t.exports=a.p+"img/Basemapthumbnail1.5f8d6db2.png"},"42e8":function(t,e,a){t.exports=a.p+"img/Basemapthumbnail.6dc37597.png"},"56e4":function(t,e,a){"use strict";var i=a("0a34"),n=a.n(i);n.a},"65d4":function(t,e,a){"use strict";a.d(e,"c",function(){return n}),a.d(e,"a",function(){return r});var i=a("b775"),n=function(t){return Object(i["a"])({url:"/dcxx/dicIndexItem/saveFiled",method:"post",data:t})},r=function(t){return Object(i["a"])({url:"/dcxx/dicIndexItem/getSurveyTableList",method:"post",data:t})}},"7f7f":function(t,e,a){var i=a("86cc").f,n=Function.prototype,r=/^\s*function ([^ (]*)/,d="name";d in n||a("9e1e")&&i(n,d,{configurable:!0,get:function(){try{return(""+this).match(r)[1]}catch(t){return""}}})},"91b2":function(t,e,a){"use strict";a.d(e,"m",function(){return n}),a.d(e,"b",function(){return r}),a.d(e,"o",function(){return d}),a.d(e,"l",function(){return o}),a.d(e,"k",function(){return l}),a.d(e,"g",function(){return s}),a.d(e,"d",function(){return c}),a.d(e,"a",function(){return u}),a.d(e,"n",function(){return p}),a.d(e,"f",function(){return m}),a.d(e,"j",function(){return f}),a.d(e,"c",function(){return g}),a.d(e,"h",function(){return h}),a.d(e,"i",function(){return y}),a.d(e,"e",function(){return b});var i=a("b775"),n=function(t){return Object(i["a"])({url:"/ygjc/image/list",method:"post",data:t})},r=function(t){return Object(i["a"])({url:"/ygjc/image/add",method:"post",data:t})},d=function(t){return Object(i["a"])({url:"/ygjc/iterpretation/add",method:"post",data:t})},o=function(t){return Object(i["a"])({url:"/ygjc/image/detail",method:"post",data:t})},l=function(t){return Object(i["a"])({url:"/ygjc/iterpretation/list",method:"post",data:t})},s=function(t){return Object(i["a"])({url:"/ygjc/rlhdGroup/list",method:"post",data:t})},c=function(t){return Object(i["a"])({url:"/ygjc/rlhdGroup/add",method:"post",data:t})},u=function(t){return Object(i["a"])({url:"/ygjc/rlhdGroup/addDataToGroup",method:"post",data:t})},p=function(t){return Object(i["a"])({url:"/ygjc/rlhdGroup/detail",method:"post",data:t})},m=function(t){return Object(i["a"])({url:"/ygjc/image/delete",method:"post",data:t})},f=function(t){return Object(i["a"])({url:"/ygjc/imageContrast/list",method:"post",data:t})},g=function(t){return Object(i["a"])({url:"/ygjc/imageContrast/add",method:"post",data:t})},h=function(t){return Object(i["a"])({url:"/ygjc/imageContrast/like",method:"post",data:t})},y=function(t){return Object(i["a"])({url:"/ygjc/imageContrast/get",method:"post",data:t})},b=function(t){return Object(i["a"])({url:"/ygjc/imageContrast/delete",method:"post",data:t})}},b1f7:function(t,e,a){"use strict";var i=a("cc70"),n=a.n(i);n.a},b753:function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"tablet"}},[a("div",{staticClass:"queryBox"},[a("QueryBox",{staticStyle:{width:"calc(100% - 300px)"},attrs:{formArr:t.queryFrom},on:{query:t.getQueryData}}),a("div",{staticClass:"queryBtns"},[a("Button",{staticClass:"btn-tabBtn",attrs:{type:"primary",size:"small",icon:"ios-map"},on:{click:function(e){e.stopPropagation(),t.addActive=!0,t.$refs.ledgerManageMap.initCom()}}},[t._v("切换地图")]),a("Button",{staticClass:"btn-import",staticStyle:{padding:"2px 7px 1.8px","margin-right":"10px"},attrs:{type:"success",size:"small"}},[a("Upload",{attrs:{"show-upload-list":!1,format:[".xls"],action:"#"}},[a("Icon",{staticStyle:{"margin-right":"6px"},attrs:{type:"md-cloud-upload"}}),a("span",[t._v("导入")])],1)],1),a("Button",{staticClass:"btn-exportBtn",staticStyle:{"margin-right":"18px"},attrs:{type:"primary",size:"small",icon:"ios-paper-plane"},on:{click:function(e){return e.stopPropagation(),t.handleIssue(e)}}},[t._v("下发")]),a("Button",{staticClass:"btn-add",attrs:{type:"success",size:"small",icon:"md-add"},on:{click:function(e){e.stopPropagation(),t.$refs.ledgerAdd.build=!0}}},[t._v("新建")])],1)],1),a("div",{staticClass:"content_box"},[a("div",[a("Table",{attrs:{border:"",stripe:"",loading:t.loading,columns:t.columns1,data:t.data1,"highlight-row":"","max-height":t.tableHeight}}),a("div",{staticClass:"page_box"},[a("Page",{attrs:{total:t.total,"page-size":t.pageSize,current:t.pageNum,"show-total":""},on:{"update:current":function(e){t.pageNum=e},"on-change":t.pageChange}})],1)],1)]),a("div",{staticClass:"extract_box",class:{addActive:t.addActive}},[a("ledgerManageMap",{ref:"ledgerManageMap",on:{signOut:t.signOut}})],1),a("div",{staticClass:"extract_box",class:{detailActive:t.detailActive}},[a("div",{staticClass:"queryBox",staticStyle:{"text-align":"right"}},[a("Button",{attrs:{size:"small",type:"primary",icon:"ios-arrow-back"},on:{click:function(e){t.detailActive=!1}}},[t._v("返回")])],1),a("div",{staticStyle:{padding:"0 15px 15px"}},[a("Table",{attrs:{border:"",stripe:"",data:t.data2,columns:t.columns2,"max-height":t.tableHeight}}),a("Page",{staticStyle:{width:"100%",padding:"15px 0","text-align":"right"},attrs:{total:t.total1,"show-total":"","page-size":t.pageSize1,current:t.pageNum1},on:{"update:current":function(e){t.pageNum1=e}}})],1)]),a("ledgerAdd",{ref:"ledgerAdd",on:{getTableData:t.getTableList}}),a("ledgerEdit",{ref:"ledgerEdit",attrs:{data:t.editDefaultData},on:{getTableData:t.getTableList}})],1)},n=[],r=(a("6b54"),a("cebc")),d=a("aff7"),o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"map-box"},[a("div",{ref:"viewDiv",attrs:{id:"viewDiv"}}),a("Button",{staticClass:"sign-btn",attrs:{type:"primary",size:"small"},on:{click:function(e){return t.$emit("signOut",!1)}}},[t._v("返回")])],1)},l=[],s=(a("96cf"),a("3b8d")),c=a("9ed9"),u=a("149e"),p=a("f831"),m=a("42e8"),f=a.n(m),g=a("39f3"),h=a.n(g),y={name:"checkInfoMap",data:function(){return{map:{},view:{}}},computed:{},props:{},components:{},watch:{},mounted:function(){this.createMap()},methods:{createMap:function(){var t=Object(s["a"])(regeneratorRuntime.mark(function t(){var e,a,i,n,r,d,o,l,s,m,g,y;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,c["a"].MapView;case 2:return e=t.sent,t.next=5,c["a"].Map;case 5:return a=t.sent,t.next=8,c["a"].Basemap;case 8:return i=t.sent,t.next=11,c["a"].BasemapToggle;case 11:return n=t.sent,t.next=14,Object(p["h"])().then(function(t){return t});case 14:return r=t.sent,t.next=17,Object(p["g"])().then(function(t){return t});case 17:return d=t.sent,t.next=20,Object(p["f"])().then(function(t){return t});case 20:return o=t.sent,t.next=23,Object(p["i"])().then(function(t){return t});case 23:return l=t.sent,t.next=26,Object(p["e"])().then(function(t){return t});case 26:s=t.sent,m=new i({baseLayers:[d,s,o],title:"矢量地图",id:"myBasemap",thumbnailUrl:f.a}),g=new i({baseLayers:[r,s,l],title:"影像地图",id:"myBasemap1",thumbnailUrl:h.a}),this.map=new a({basemap:g}),this.view=new e({map:this.map,container:"viewDiv",center:u["a"].centerPoint,zoom:7}),y=new n({view:this.view,nextBasemap:m}),this.view.ui.add(y,"top-right");case 33:case"end":return t.stop()}},t,this)}));function e(){return t.apply(this,arguments)}return e}(),initCom:function(t){this.map.remove(this.insercLayer),this.view.graphics.removeAll()}}},b=y,v=(a("b1f7"),a("2877")),D=Object(v["a"])(b,o,l,!1,null,"32b5533e",null),x=D.exports,w=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"ledgerAdd"}},[a("Modal",{attrs:{title:"新建人类活动监管台账信息"},on:{"on-cancel":t.reset},model:{value:t.build,callback:function(e){t.build=e},expression:"build"}},[a("Form",{ref:"addForm",staticStyle:{padding:"0 20%"},attrs:{model:t.AddData,rules:t.ruleValidate,"label-width":100}},[a("FormItem",{attrs:{prop:"num",label:"问题点编号："}},[a("Input",{attrs:{placeholder:"请输入问题点编号",size:"default"},model:{value:t.AddData.num,callback:function(e){t.$set(t.AddData,"num","string"===typeof e?e.trim():e)},expression:"AddData.num"}})],1),a("FormItem",{attrs:{prop:"type",label:"所属任务："}},[a("Select",{attrs:{size:"default",placeholder:"请选择所属任务"},model:{value:t.AddData.type,callback:function(e){t.$set(t.AddData,"type","string"===typeof e?e.trim():e)},expression:"AddData.type"}},t._l(t.addTypeList,function(e){return a("Option",{key:e.value,attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"lon",label:"经度："}},[a("Input",{attrs:{placeholder:"请输入经度",size:"default"},model:{value:t.AddData.lon,callback:function(e){t.$set(t.AddData,"lon","string"===typeof e?e.trim():e)},expression:"AddData.lon"}})],1),a("FormItem",{attrs:{prop:"lat",label:"纬度："}},[a("Input",{attrs:{placeholder:"请输入纬度",size:"default"},model:{value:t.AddData.lat,callback:function(e){t.$set(t.AddData,"lat","string"===typeof e?e.trim():e)},expression:"AddData.lat"}})],1),a("FormItem",{attrs:{prop:"typeP",label:"问题点类型："}},[a("Select",{attrs:{size:"default",placeholder:"请选择问题点类型"},model:{value:t.AddData.typeP,callback:function(e){t.$set(t.AddData,"typeP","string"===typeof e?e.trim():e)},expression:"AddData.typeP"}},t._l(t.dotList1,function(e){return a("Option",{key:e.value,attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"desc",label:"问题点备注："}},[a("Input",{attrs:{type:"textarea",rows:4,placeholder:"请输入问题点备注",size:"default"},model:{value:t.AddData.desc,callback:function(e){t.$set(t.AddData,"desc","string"===typeof e?e.trim():e)},expression:"AddData.desc"}})],1)],1),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("Button",{attrs:{type:"primary"},on:{click:t.addSubmit}},[t._v("确定")])],1)],1)],1)},E=[],j=(a("7f7f"),a("65d4")),k={name:"TaskAdd",data:function(){return{addTypeList:[{value:"1",label:"type1"},{value:"2",label:"type2"}],dotList1:[{value:"1",label:"type1"},{value:"2",label:"type2"}],AddData:{num:"",type:"",lon:"",lat:"",typeP:"",desc:""},ruleValidate:{num:[{required:!0,message:"任务名称必须填写",trigger:"blur"}],type:[{required:!0,message:"所属任务必须选择",trigger:"change"}]},build:!1}},mounted:function(){},methods:{addSubmit:function(){var t=this;this.$refs.addForm.validate(function(e){e&&Object(j["c"])({data:{num:t.AddData.name,type:t.AddData.type,lon:t.AddData.lon,lat:t.AddData.lat,typeP:t.AddData.typeP,desc:t.AddData.desc}}).then(function(e){"0000"===e.data.code?(t.$Notice.success({title:"新建人类活动监管台账信息成功"}),t.$emit("getTableData"),t.build=!1,t.AddData={}):t.$Notice.error({title:"新建人类活动监管台账信息失败"})})})},reset:function(){this.AddData={num:"",type:"",lon:"",lat:"",typeP:"",desc:""},this.$refs.addForm.resetFields()}}},A=k,O=Object(v["a"])(A,w,E,!1,null,"45e5dd1e",null),$=O.exports,z=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"ledgerEdit"}},[a("Modal",{attrs:{title:"修改人类活动监管台账信息"},on:{"on-cancel":t.reset},model:{value:t.edit,callback:function(e){t.edit=e},expression:"edit"}},[a("Form",{ref:"editForm",staticStyle:{padding:"0 20%"},attrs:{model:t.EditData,rules:t.EditRule,"label-width":100}},[a("FormItem",{staticStyle:{display:"none"},attrs:{prop:"id",label:"ID："}},[a("Input",{model:{value:t.EditData.id,callback:function(e){t.$set(t.EditData,"id","string"===typeof e?e.trim():e)},expression:"EditData.id"}})],1),a("FormItem",{attrs:{prop:"num",label:"问题点编号："}},[a("Input",{attrs:{placeholder:"请输入问题点编号",size:"default"},model:{value:t.EditData.num,callback:function(e){t.$set(t.EditData,"num","string"===typeof e?e.trim():e)},expression:"EditData.num"}})],1),a("FormItem",{attrs:{prop:"type",label:"所属任务："}},[a("Select",{attrs:{size:"default",placeholder:"请选择所属任务"},model:{value:t.EditData.type,callback:function(e){t.$set(t.EditData,"type","string"===typeof e?e.trim():e)},expression:"EditData.type"}},t._l(t.addTypeList,function(e){return a("Option",{key:e.value,attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"lon",label:"经度："}},[a("Input",{attrs:{placeholder:"请输入经度",size:"default"},model:{value:t.EditData.lon,callback:function(e){t.$set(t.EditData,"lon","string"===typeof e?e.trim():e)},expression:"EditData.lon"}})],1),a("FormItem",{attrs:{prop:"lat",label:"纬度："}},[a("Input",{attrs:{placeholder:"请输入纬度",size:"default"},model:{value:t.EditData.lat,callback:function(e){t.$set(t.EditData,"lat","string"===typeof e?e.trim():e)},expression:"EditData.lat"}})],1),a("FormItem",{attrs:{prop:"typeP",label:"问题点类型："}},[a("Select",{attrs:{size:"default",placeholder:"请选择问题点类型"},model:{value:t.EditData.typeP,callback:function(e){t.$set(t.EditData,"typeP","string"===typeof e?e.trim():e)},expression:"EditData.typeP"}},t._l(t.dotList1,function(e){return a("Option",{key:e.value,attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"desc",label:"问题点备注："}},[a("Input",{attrs:{type:"textarea",rows:4,placeholder:"请输入问题点备注",size:"default"},model:{value:t.EditData.desc,callback:function(e){t.$set(t.EditData,"desc","string"===typeof e?e.trim():e)},expression:"EditData.desc"}})],1)],1),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("Button",{attrs:{type:"primary"},on:{click:t.editSubmit}},[t._v("确定")])],1)],1)],1)},_=[],S={name:"Taskedit",data:function(){return{addTypeList:[{value:"1",label:"type1"},{value:"2",label:"type2"}],dotList1:[{value:"1",label:"type1"},{value:"2",label:"type2"}],EditRule:{num:[{required:!0,message:"任务名称必须填写",trigger:"blur"}],type:[{required:!0,message:"所属任务必须选择",trigger:"change"}]},edit:!1,EditData:{}}},props:{data:{}},watch:{data:function(){this.dataHandle()}},mounted:function(){},methods:{dataHandle:function(){this.EditData.id=this.data.id,this.EditData.num=this.data.num,this.EditData.type=this.data.type,this.EditData.lon=this.data.lon,this.EditData.lat=this.data.lat,this.EditData.typeP=this.data.typeP,this.EditData.desc=this.data.desc},editSubmit:function(){var t=this;this.$refs.editForm.validate(function(e){e&&Object(j["c"])({data:{id:t.EditData.id,num:t.EditData.name,type:t.EditData.type,lon:t.EditData.lon,lat:t.EditData.lat,typeP:t.EditData.typeP,desc:t.EditData.desc}}).then(function(e){"0000"===e.data.code?(t.$Notice.success({title:"编辑人类活动监管台账信息成功"}),t.$emit("getTableData"),t.edit=!1,t.EditData={}):t.$Notice.error({title:"新建人类活动监管台账信息失败"})})})},reset:function(){this.EditData={id:"",num:"",type:"",lon:"",lat:"",typeP:"",desc:""},this.EditData={},this.$refs.editForm.resetFields()}}},I=S,F=Object(v["a"])(I,z,_,!1,null,"286d3d5d",null),T=F.exports,N=a("91b2"),B={name:"extracTable",data:function(){var t=this;return{queryData:{},typeList:[{value:"",label:"请选择"},{value:"1",label:"type1"},{value:"2",label:"type2"}],tableHeight:48*Math.floor((window.innerHeight-300-40)/48)+40,columns1:[{type:"selection",width:60,align:"center"},{title:"序号",key:"index",width:100,align:"center",render:function(e,a){return e("span",a.index+(t.pageNum-1)*t.pageSize+1)}},{title:"台账名称",align:"center",minWidth:200,key:"name"},{title:"说明",align:"center",minWidth:200,key:"remark"},{title:"人类活动版块数量",align:"center",minWidth:200,key:"sonCount"},{title:"操作",key:"address",width:250,align:"center",render:function(e,a){return e("div",[e("Button",{props:{type:"primary",size:"small",icon:"md-eye"},style:{marginRight:"5px"},on:{click:function(){t.showAccountDetail(a.row.id)}}},"查看详情")])}}],data1:[],total:0,pageNum:1,pageSize:Math.floor((48*Math.floor((window.innerHeight-300-40)/48)+40)/48),loading:!1,addActive:!1,editDefaultData:{},detailActive:!1,data2:[],columns2:[{title:"序号",type:"index",align:"center",width:100},{title:"活动名称",align:"center",minWidth:200,key:"activeName"},{title:"活动类型",align:"center",minWidth:200,key:"activeType"},{title:"所属区域类型",align:"center",minWidth:200,key:"region"},{title:"位置",align:"center",minWidth:200,key:"position"},{title:"面积（公顷）",align:"center",minWidth:200,key:"area"}],pageSize1:20,pageNum1:10,total1:0}},computed:{queryFrom:function(){return[{type:1,field:"name",title:"台账名称"}]}},components:{QueryBox:d["a"],ledgerManageMap:x,ledgerAdd:$,ledgerEdit:T},created:function(){this.getTableList()},mounted:function(){var t=this;window.addEventListener("resize",function(){t.tableHeight=48*Math.floor((window.innerHeight-300-40)/48)+40,t.pageSize=Math.floor((48*Math.floor((window.innerHeight-300-40)/48)+40)/48),t.getTableList()},!1)},methods:{getQueryData:function(t){this.queryData=t,this.pageNum=1,this.getTableList()},getTableList:function(){var t=this;this.loading=!0,Object(N["g"])({data:Object(r["a"])({pageSize:this.pageSize,pageNum:this.pageNum},this.queryData)}).then(function(e){"0000"===e.data.code&&(t.data1=e.data.data.rows,t.total=e.data.data.total,t.loading=!1)}).catch(function(){t.$Notice.error({title:"服务器错误"})})},pageChange:function(t){this.pageNum=t,this.getTableList()},infoDel:function(t){function e(e){return t.apply(this,arguments)}return e.toString=function(){return t.toString()},e}(function(t){var e=this;infoDel({data:{id:t}}).then(function(t){"0000"===t.data.code?(e.getTableList(),e.$Notice.success({title:"删除成功"})):e.$Notice.error({title:"删除失败"})}).catch(function(){e.$Notice.error({title:"服务器错误"})})}),signOut:function(t){this.addActive=t},showAccountDetail:function(t){var e=this;this.detailActive=!0,this.pageNum1=1,Object(N["n"])({data:{id:t,pageSize:this.pageSize1,pageNum:this.pageNum1}}).then(function(t){"0000"===t.data.code?(e.data2=t.data.data.rows,e.total1=t.data.data.total):e.$Notice.error({title:"获取人类活动台账详情失败",desc:t.data.msg})})}}},C=B,P=(a("56e4"),Object(v["a"])(C,i,n,!1,null,"130cbf12",null));e["default"]=P.exports},cc70:function(t,e,a){}}]);
//# sourceMappingURL=chunk-52c41f94.f8ded69c.js.map