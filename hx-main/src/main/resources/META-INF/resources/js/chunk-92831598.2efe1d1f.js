(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-92831598"],{"0d3a":function(t,e,a){"use strict";var n=a("be9a"),i=a.n(n);i.a},"3ac9":function(t,e,a){"use strict";a.d(e,"o",function(){return i}),a.d(e,"m",function(){return r}),a.d(e,"q",function(){return s}),a.d(e,"i",function(){return o}),a.d(e,"l",function(){return c}),a.d(e,"g",function(){return u}),a.d(e,"h",function(){return l}),a.d(e,"f",function(){return d}),a.d(e,"s",function(){return h}),a.d(e,"e",function(){return m}),a.d(e,"a",function(){return f}),a.d(e,"c",function(){return y}),a.d(e,"k",function(){return p}),a.d(e,"t",function(){return b}),a.d(e,"v",function(){return v}),a.d(e,"d",function(){return x}),a.d(e,"p",function(){return g}),a.d(e,"j",function(){return w}),a.d(e,"r",function(){return k}),a.d(e,"n",function(){return T}),a.d(e,"u",function(){return C}),a.d(e,"y",function(){return S}),a.d(e,"w",function(){return D}),a.d(e,"x",function(){return _}),a.d(e,"b",function(){return j});var n=a("b775"),i=function(t){return Object(n["a"])({url:"/ktdb/lmPoint/getPointList",method:"post",data:t})},r=function(t){return Object(n["a"])({url:"/sys/menu/getMenu",method:"post",data:t})},s=function(t){return Object(n["a"])({url:"/ktdb/lmMarkerMobile/getPreMarkerList",method:"post",data:t})},o=function(t){return Object(n["a"])({url:"/ktdb/lmBoard/getPreLmBoardList",method:"post",data:t})},c=function(t){return Object(n["a"])({url:"/ktdb/lmMarkerMobile/getMarkerByCoordinate",method:"post",data:t})},u=function(t){return Object(n["a"])({url:"/ktdb/lmPoint/export_Excel",method:"post",data:t})},l=function(t){return Object(n["a"])({url:"/ktdb/lmMarkerMobile/export_PreExcel",method:"post",data:t})},d=function(t){return Object(n["a"])({url:"/ktdb/lmBoard/export_PreExcel",method:"post",data:t})},h=function(t){return Object(n["a"])({url:"ktdb/dataRedlineRegister/list",method:"post",data:t})},m=function(t){return Object(n["a"])({url:"ktdb/environment/bottomChart/list",method:"post",data:t})},f=function(t){return Object(n["a"])({url:"ktdb/environment/bottomChart/add",method:"post",data:t})},y=function(t){return Object(n["a"])({url:"ktdb/environment/bottomChart/delete",method:"post",data:t})},p=function(t){return Object(n["a"])({url:"/ktdb/bottomchartType/list",method:"post",data:t})},b=function(t){return Object(n["a"])({url:"/ktdb/bottomchartType/add",method:"post",data:t})},v=function(t){return Object(n["a"])({url:"/ktdb/bottomchartType/update",method:"post",data:t})},x=function(t){return Object(n["a"])({url:"/ktdb/bottomchartType/delete",method:"post",data:t})},g=function(t){return Object(n["a"])({url:"/ktdb/total/getPreMarkAndRedlineTotal",method:"post",data:t})},w=function(t){return Object(n["a"])({url:"/ktdb/total/getBoardAndRedlineTotal",method:"post",data:t})},k=function(t){return Object(n["a"])({url:"/ktdb/total/getRedlineCount",method:"post",data:t})},T=function(t){return Object(n["a"])({url:"/ktdb/total/getPointCount",method:"post",data:t})},C=function(t){return Object(n["a"])({url:"/ktdb/lmBoard/updateBoard",method:"post",data:t})},S=function(t){return Object(n["a"])({url:"/ktdb/dataRedline/update",method:"post",data:t})},D=function(t){return Object(n["a"])({url:"/ktdb/lmMarkerMobile/updateMaker",method:"post",data:t})},_=function(t){return Object(n["a"])({url:"/ktdb/lmPoint/updatePoint",method:"post",data:t})},j=function(t){return Object(n["a"])({url:"/ktdb/dataRedlineRegister/allList",method:"post",data:t})}},"469f":function(t,e,a){a("6c1c"),a("1654"),t.exports=a("7d7b")},"49ae":function(t,e,a){"use strict";var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"textTerm"},[a("span",{staticStyle:{"min-width":"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[t._v(t._s(t.titName)+"：")]),a("Input",{staticStyle:{width:"200px"},attrs:{size:"large",placeholder:"请输入"+t.titName},on:{"on-change":function(e){return t.srldChange(t.UnitName)}},model:{value:t.UnitName,callback:function(e){t.UnitName="string"===typeof e?e.trim():e},expression:"UnitName"}})],1)},i=[],r={name:"TextTerm",data:function(){return{UnitName:""}},props:{titName:{},titKey:{}},mounted:function(){},methods:{srldChange:function(){if(null===this.UnitName)this.$emit("delQueryTrem",this.titKey);else{var t={name:this.titName,text:this.UnitName,key:this.titKey,value:this.UnitName};this.$emit("updataQueryData",t)}}}},s=r,o=(a("eb80"),a("2877")),c=Object(o["a"])(s,n,i,!1,null,"16da56a2",null);e["a"]=c.exports},"5d73":function(t,e,a){t.exports=a("469f")},"69b7":function(t,e,a){"use strict";a.r(e);var n=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"areaManagement-wrapper"}},[a("QueryBox",{on:{searchSrldId:t.searchSrldId}}),a("div",{staticStyle:{height:"calc(100% - 65px)"}},[a("div",{staticStyle:{"text-align":"left","padding-left":"20px"}},[t._v("\n      统计维度：\n      "),a("RadioGroup",{attrs:{type:"button"},on:{"on-change":t.handleChangeType},model:{value:t.analysisType,callback:function(e){t.analysisType=e},expression:"analysisType"}},[a("Radio",{attrs:{label:"sf"}},[t._v("行政区划")]),a("Radio",{attrs:{label:"sa015"}},[t._v("所属部门")])],1)],1),a("div",{staticClass:"showBox"},[a("transition",{attrs:{name:"fade"}},[a("div",{staticClass:"sta_box sta_box1",class:{activeBox:1===t.boxstate}},[a("div",{staticClass:"con_box"},[a("Button",{staticClass:"boxBtn",attrs:{icon:1===t.boxstate?"md-contract":"md-expand",size:"small"},on:{click:function(e){t.boxstate=1===t.boxstate?0:1}}}),a("div",{staticClass:"bigBox",class:{smallToBig:1===t.boxstate}},[a("div",{staticClass:"smallBox"},[a("BarChart",{attrs:{data:t.data1}})],1)]),t.spinShow?a("Spin",{attrs:{size:"large",fix:""}}):t._e()],1)])]),a("div",{staticClass:"sta_box sta_box2",class:{activeBox:2===t.boxstate}},[a("div",{staticClass:"con_box"},[a("Button",{staticClass:"boxBtn",attrs:{icon:2===t.boxstate?"md-contract":"md-expand",size:"small"},on:{click:function(e){t.boxstate=2===t.boxstate?0:2}}}),a("div",{staticClass:"bigBox",class:{smallToBig:2===t.boxstate}},[a("div",{staticClass:"smallBox"},[a("Table",{attrs:{border:"",stripe:"",loading:t.loading,columns:t.columns1,data:t.data1,"highlight-row":"","max-height":t.tableHeight}})],1)])],1)])],1)])],1)},i=[],r=(a("ac6a"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"query-box"},[a("div",{ref:"queryBox",staticClass:"query-box-title",on:{click:function(e){t.show=!t.show}}},[a("span",{directives:[{name:"show",rawName:"v-show",value:0===t.queryData.length,expression:"queryData.length===0"}],staticClass:"search-title"},[t._v("查询条件")]),a("div",{staticClass:"search-items"},t._l(t.queryData,function(e,n){return a("span",{key:n,staticClass:"queryTerm"},[a("strong",[t._v(t._s(e.name))]),a("span",[t._v("\n              "+t._s(e.text)+"\n            ")])])}),0),a("div",{staticStyle:{width:"100px","text-align":"right"}},[a("Button",{attrs:{type:"primary",size:"small",icon:"ios-arrow-dropdown"}},[t._v("查询条件")])],1)]),a("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],ref:"queryDown",staticClass:"query-down"},[a("div",{staticClass:"queryTerm"},[a("CascadeTerm",{ref:"cascade",on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticClass:"queryTerm"},[a("TextTerm",{attrs:{titName:"核查人名称",titKey:"uname"},on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticClass:"queryTerm"},[a("TextTerm",{attrs:{titName:"所属部门",titKey:"deptName"},on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticStyle:{"text-align":"right","margin-top":"-30px"}},[a("Button",{staticStyle:{"margin-right":"10px"},attrs:{type:"primary",size:"small",icon:"ios-search"},on:{click:t.search}},[t._v("搜索")]),a("Button",{attrs:{type:"primary",size:"small",icon:"md-close"},on:{click:function(e){t.show=!1}}},[t._v("取消")])],1)])])}),s=[],o=a("5d73"),c=a.n(o),u=(a("7f7f"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"cascadeTerm"},[a("strong",{staticStyle:{width:"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[t._v("行政区划：")]),a("div",{staticClass:"cascadeRec_box",staticStyle:{"padding-left":"100px","margin-top":"-22px"}},[t._l(t.listData,function(e,n){return a("p",{key:n},[a("span",{class:{active:-1===t.activeId},on:{click:function(a){return t.clickProvince(e)}}},[t._v("全省")]),t._l(e.children,function(e,n){return a("span",{key:n,class:{active:t.activeId===n},on:{click:function(a){return t.clickCity(e,n)}}},[t._v("\n          "+t._s(e.label)+"\n        ")])})],2)}),a("p",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticStyle:{"margin-top":"10px","padding-left":"10px"}},t._l(t.showData,function(e,n){return a("span",{key:n,class:{active:t.activeId2===n},on:{click:function(a){return t.clickCounty(e,n)}}},[t._v("\n        "+t._s(e.label)+"\n        ")])}),0)],2)])}),l=[],d=a("3ac9"),h=a("5f87"),m={name:"CascadeTerm",data:function(){return{listData:[],show:!1,activeId:-2,activeId2:-2,showData:[]}},mounted:function(){this.getMenu()},methods:{getMenu:function(){var t=this;Object(d["m"])({accessToken:Object(h["a"])(),validate:!0,data:{}}).then(function(e){t.listData=e.data.data}).catch(function(t){console.log(t)})},clickProvince:function(t){if(-1===this.activeId)this.$emit("delQueryTrem","code"),this.show=!1,this.activeId=-2,this.activeId2=-2;else{for(var e in this.show=!1,this.showData=[],t.children)this.showData=this.showData.concat(t.children[e].children);this.activeId=-1,this.activeId2=-2,this.labelValue=t.label;var a={name:"行政区划",text:"全省",key:"code",value:t.value};this.updataQueryData(a)}},clickCity:function(t,e){if(this.activeId===e)this.$emit("delQueryTrem","code"),this.show=!1,this.activeId=-2,this.activeId2=-2;else{this.show=!0,this.showData=t.children,this.activeId=e,this.activeId2=-2,this.labelValue=t.label;var a={name:"行政区划",text:this.labelValue,key:"code",value:t.value};this.updataQueryData(a)}},clickCounty:function(t,e){this.activeId2=e,this.labelValue=t.label;var a={name:"行政区划",text:this.labelValue,key:"code",value:t.value};this.updataQueryData(a)},updataQueryData:function(t){this.$emit("updataQueryData",t)}}},f=m,y=(a("b973"),a("2877")),p=Object(y["a"])(f,u,l,!1,null,"3f1a5789",null),b=p.exports,v=a("49ae"),x={name:"queryBox",data:function(){return{show:!1,cl002:"",uname:"",deptName:"",queryData:[]}},components:{CascadeTerm:b,TextTerm:v["a"]},mounted:function(){document.addEventListener("click",this.queryHide)},beforeDestroy:function(){document.removeEventListener("click",this.queryHide)},methods:{updataQueryData:function(t){""!==t.text?(this.queryData=this.queryData.filter(function(e){return e.name!==t.name}),this.queryData.push(t)):this.queryData=this.queryData.filter(function(e){return e.name!==t.name})},delQueryTrem:function(t){this.queryData=this.queryData.filter(function(e){return e.key!==t})},search:function(){var t={},e=!0,a=!1,n=void 0;try{for(var i,r=c()(this.queryData);!(e=(i=r.next()).done);e=!0){var s=i.value;t[s.key]=s.value}}catch(o){a=!0,n=o}finally{try{e||null==r.return||r.return()}finally{if(a)throw n}}this.$emit("searchSrldId",t),this.show=!1},queryHide:function(t){this.$refs.queryBox.contains(t.target)||this.$refs.queryDown.contains(t.target)||(this.show=!1)}}},g=x,w=(a("0d3a"),Object(y["a"])(g,r,s,!1,null,"63740292",null)),k=w.exports,T=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{directives:[{name:"resize",rawName:"v-resize",value:t.redraw,expression:"redraw"}],ref:"chart",staticStyle:{width:"100%",height:"100%"},attrs:{id:"myChart"}})},C=[],S={name:"BarChart",data:function(){return{myChart:{},datas:{}}},computed:{xAxisData:function(){var t=[],e=!0,a=!1,n=void 0;try{for(var i,r=c()(this.datas);!(e=(i=r.next()).done);e=!0){var s=i.value;null===s.name&&(s.name=""),t.push(s.name)}}catch(o){a=!0,n=o}finally{try{e||null==r.return||r.return()}finally{if(a)throw n}}return t},rwcs:function(){var t=[],e=!0,a=!1,n=void 0;try{for(var i,r=c()(this.datas);!(e=(i=r.next()).done);e=!0){var s=i.value;t.push(s.rwcs)}}catch(o){a=!0,n=o}finally{try{e||null==r.return||r.return()}finally{if(a)throw n}}return t},gjlc:function(){var t=[],e=!0,a=!1,n=void 0;try{for(var i,r=c()(this.datas);!(e=(i=r.next()).done);e=!0){var s=i.value;t.push(s.gjlc)}}catch(o){a=!0,n=o}finally{try{e||null==r.return||r.return()}finally{if(a)throw n}}return t},rwsc:function(){var t=[],e=!0,a=!1,n=void 0;try{for(var i,r=c()(this.datas);!(e=(i=r.next()).done);e=!0){var s=i.value;t.push(s.rwsc)}}catch(o){a=!0,n=o}finally{try{e||null==r.return||r.return()}finally{if(a)throw n}}return t}},props:{data:{}},watch:{data:function(){this.dataHandle()}},methods:{dataHandle:function(){this.datas=this.data,this.drawLine()},drawLine:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.chart),this.myChart.setOption({grid:{left:"40px",right:"40px",top:"20%",containLabel:!0},legend:{data:["巡查次数","巡查里程","巡查时长"],top:10,right:60},tooltip:{trigger:"axis",axisPointer:{type:"cross",crossStyle:{color:"#999"}},formatter:function(t){return t[0].name+"<br>"+t[0].seriesName+":"+t[0].value+"次<br>"+t[1].seriesName+":"+t[1].value+"m<br>"+t[2].seriesName+":"+t[2].value+"小时"}},xAxis:{type:"category",data:this.xAxisData},yAxis:[{type:"value",name:"巡查次数/次",minInterval:1,min:0,axisLabel:{formatter:"{value}"}},{type:"value",name:"巡查里程/m",minInterval:10,min:0,axisLabel:{formatter:"{value}"}}],dataZoom:[{type:"slider",show:!0}],series:[{name:"巡查次数",type:"bar",z:3,itemStyle:{normal:{color:"rgb(194,53,49)"}},yAxisIndex:0,data:this.rwcs,barCategoryGap:"35%"},{name:"巡查里程",type:"bar",z:3,itemStyle:{normal:{color:"rgb(47,69,84)"}},yAxisIndex:1,data:this.gjlc,barCategoryGap:"35%"},{name:"巡查时长",type:"bar",z:3,itemStyle:{normal:{color:"rgb(97,160,168)"}},yAxisIndex:0,data:this.rwsc,barCategoryGap:"35%"}]}),window.addEventListener("resize",function(){return t.myChart.resize()},!1)},redraw:function(){void 0!==this.myChart.id&&this.myChart.resize()}}},D=S,_=Object(y["a"])(D,T,C,!1,null,"a188f38e",null),j=_.exports,L=a("b775"),O=function(t){return Object(L["a"])({url:"/statis/listPatrol",method:"post",data:t})},N={name:"dataAnalysis",data:function(){return{obj:"",cl002:"",comFPkid:"1",uname:"",deptName:"",data1:[],tableHeight:window.innerHeight-315,spinShow:!1,columns1:[{title:"序号",key:"index",width:100,align:"center",render:function(t,e){return t("span",e.index+1)}},{title:"核查人员",align:"center",key:"uname",ellipsis:!0,tooltip:!0,minWidth:80},{title:"巡查次数(次)",align:"center",key:"rwcs",minWidth:80},{title:"巡查里程(m)",align:"center",key:"gjlc",minWidth:80},{title:"巡查时长(小时)",align:"center",key:"rwsc",minWidth:80}],loading:!1,boxstate:0,analysisType:"sf"}},components:{QueryBox:k,BarChart:j},created:function(){this.getTableData()},methods:{searchSrldId:function(t){this.comFPkid=t.code,this.cl002=t.cl002,this.uname=t.uname,this.getTableData()},getTableData:function(){var t=this;this.spinShow=!0,this.loading=!0,O({data:{st4ScsCl:{cl002:this.cl002},sysCompany:{comFPkid:this.comFPkid},deptName:this.deptName,uname:this.uname,groupByName:this.analysisType}}).then(function(e){"0000"===e.data.code?(t.data1=[],e.data.data.length>0&&("sf"===t.analysisType?e.data.data.forEach(function(e,a){t.data1.push({name:e.sysCompany.comName,rwcs:e.rwcs,gjlc:e.gjlc,rwsc:e.rwsc})}):"sa015"===t.analysisType&&e.data.data.forEach(function(e,a){e.st4SysSa&&e.st4SysSa.length>0&&t.data1.push({name:e.st4SysSa.sa015,rwcs:e.rwcs,gjlc:e.gjlc,rwsc:e.rwsc})})),t.spinShow=!1,t.loading=!1):t.$Notice.error({title:e.data.msg})}).catch(function(){t.$Notice.error({title:"服务器错误"})})},handleChangeType:function(t){this.spinShow=!0,this.loading=!0,this.getTableData()}}},B=N,q=(a("6b11"),Object(y["a"])(B,n,i,!1,null,"9b36700e",null));e["default"]=q.exports},"6b11":function(t,e,a){"use strict";var n=a("818b"),i=a.n(n);i.a},"7d7b":function(t,e,a){var n=a("e4ae"),i=a("7cd6");t.exports=a("584a").getIterator=function(t){var e=i(t);if("function"!=typeof e)throw TypeError(t+" is not iterable!");return n(e.call(t))}},"7f7f":function(t,e,a){var n=a("86cc").f,i=Function.prototype,r=/^\s*function ([^ (]*)/,s="name";s in i||a("9e1e")&&n(i,s,{configurable:!0,get:function(){try{return(""+this).match(r)[1]}catch(t){return""}}})},"818b":function(t,e,a){},"96f3":function(t,e,a){},ac6a:function(t,e,a){for(var n=a("cadf"),i=a("0d58"),r=a("2aba"),s=a("7726"),o=a("32e9"),c=a("84f2"),u=a("2b4c"),l=u("iterator"),d=u("toStringTag"),h=c.Array,m={CSSRuleList:!0,CSSStyleDeclaration:!1,CSSValueList:!1,ClientRectList:!1,DOMRectList:!1,DOMStringList:!1,DOMTokenList:!0,DataTransferItemList:!1,FileList:!1,HTMLAllCollection:!1,HTMLCollection:!1,HTMLFormElement:!1,HTMLSelectElement:!1,MediaList:!0,MimeTypeArray:!1,NamedNodeMap:!1,NodeList:!0,PaintRequestList:!1,Plugin:!1,PluginArray:!1,SVGLengthList:!1,SVGNumberList:!1,SVGPathSegList:!1,SVGPointList:!1,SVGStringList:!1,SVGTransformList:!1,SourceBufferList:!1,StyleSheetList:!0,TextTrackCueList:!1,TextTrackList:!1,TouchList:!1},f=i(m),y=0;y<f.length;y++){var p,b=f[y],v=m[b],x=s[b],g=x&&x.prototype;if(g&&(g[l]||o(g,l,h),g[d]||o(g,d,b),c[b]=h,v))for(p in n)g[p]||r(g,p,n[p],!0)}},b0bc:function(t,e,a){},b973:function(t,e,a){"use strict";var n=a("b0bc"),i=a.n(n);i.a},be9a:function(t,e,a){},eb80:function(t,e,a){"use strict";var n=a("96f3"),i=a.n(n);i.a}}]);
//# sourceMappingURL=chunk-92831598.2efe1d1f.js.map