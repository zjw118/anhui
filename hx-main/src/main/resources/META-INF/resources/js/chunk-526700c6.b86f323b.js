(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-526700c6"],{"062a":function(t,e,a){},"0baa":function(t,e,a){},1761:function(t,e,a){},"2d16":function(t,e,a){},"336a":function(t,e,a){"use strict";var i=a("0baa"),s=a.n(i);s.a},"35fc":function(t,e,a){"use strict";var i=a("50f8"),s=a.n(i);s.a},3918:function(t,e,a){"use strict";var i=a("9551"),s=a.n(i);s.a},"39f3":function(t,e,a){t.exports=a.p+"img/Basemapthumbnail1.5f8d6db2.png"},"42e8":function(t,e,a){t.exports=a.p+"img/Basemapthumbnail.6dc37597.png"},"50f8":function(t,e,a){},6722:function(t,e,a){"use strict";var i=a("062a"),s=a.n(i);s.a},"784d":function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"tablet"}},[a("div",{staticClass:"queryBox"},[a("QueryBox",{staticStyle:{width:"calc(100% - 370px)",float:"left"},on:{searchSrldId:t.searchSrldId}}),a("div",{staticClass:"queryBtns"},[a("Button",{staticClass:"btn-add",attrs:{type:"success",size:"small",icon:"md-add"},on:{click:function(e){e.stopPropagation(),t.$refs.taskAdd.taskBuild=!0}}},[t._v("新建")]),a("Button",{staticClass:"exportBtn",attrs:{type:"primary",size:"small",icon:"md-cloud-download"},on:{click:t.handleExport}},[t._v("批量导出")]),a("Button",{staticStyle:{"margin-right":"10px"},attrs:{type:"success",size:"small",icon:"md-cloud-download"}},[a("a",{staticStyle:{color:"#fff"},attrs:{href:"epr\\epr\\image\\安徽核查任务导入模板.xls"}},[t._v("下载模板")])]),a("Button",{staticClass:"btn-import",staticStyle:{height:"24px"},attrs:{type:"success",size:"small"}},[a("Upload",{ref:"upload",staticClass:"upload-demo",attrs:{action:"/epr/api/task/importTask",name:"file",multiple:!1,"on-success":t.uploadSuccess,"show-upload-list":!1,accept:".xls"}},[a("Icon",{attrs:{type:"md-cloud-upload"}}),a("span",{staticStyle:{"margin-left":"4px"}},[t._v("批量导入")])],1)],1)],1)],1),a("div",{staticClass:"content_box"},[a("div",[a("Table",{ref:"multipleTable",attrs:{border:"",stripe:"",loading:t.loading,columns:t.columns1,data:t.data1,"highlight-row":"","max-height":t.tableHeight},on:{"on-selection-change":t.handleSelectionChange}}),a("div",{staticClass:"page_box"},[a("Page",{ref:"pages",attrs:{total:t.total,"page-size":t.pageSize,current:t.pageNum,"show-total":""},on:{"update:current":function(e){t.pageNum=e},"on-change":t.pageChange}})],1)],1)]),a("taskAdd",{ref:"taskAdd",attrs:{codeData:t.codeData},on:{getTableData:t.getClearQueryData}}),a("taskEdit",{ref:"taskEdit",attrs:{codeData:t.codeData,data:t.editDefaultData},on:{getTableData:t.getTableList}}),a("IssueList",{ref:"issueList",attrs:{taskIdArr:t.selectRow}}),a("div",{staticClass:"spotsTable",class:{addActive:t.addActive}},[a("spotsTable",{ref:"spotsTable",attrs:{seeId:t.spotsSeeId},on:{signOut:t.signOut}})],1)],1)},s=[],n=(a("28a5"),a("7f7f"),a("ac6a"),function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"query-box"},[a("div",{ref:"queryBox",staticClass:"query-box-title",on:{click:function(e){t.show=!t.show}}},[a("span",{directives:[{name:"show",rawName:"v-show",value:0===t.queryData.length,expression:"queryData.length===0"}],staticClass:"search-title"},[t._v("查询条件")]),a("div",{staticClass:"search-items"},t._l(t.queryData,function(e,i){return a("span",{key:i,staticClass:"queryTerm"},[a("strong",[t._v(t._s(e.name))]),a("span",[t._v("\n          "+t._s(e.text)+"\n        ")])])}),0),a("div",{staticStyle:{width:"100px","text-align":"right"}},[a("Button",{attrs:{type:"primary",size:"small",icon:"ios-arrow-dropdown"}},[t._v("查询条件")])],1)]),a("div",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],ref:"queryDown",staticClass:"query-down"},[a("div",{staticClass:"queryTerm"},[a("CascadeTerm",{ref:"cascade",on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticClass:"queryTerm"},[a("TextTerm",{attrs:{titName:"任务名称",titKey:"cl002"},on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticClass:"queryTerm"},[a("DatePickerTermOne",{attrs:{titName:"任务年份",titKey:"cl010"},on:{updataQueryData:t.updataQueryData,delQueryTrem:t.delQueryTrem}})],1),a("div",{staticStyle:{"text-align":"right","margin-top":"-30px"}},[a("Button",{staticStyle:{"margin-right":"10px"},attrs:{type:"primary",size:"small",icon:"ios-search"},on:{click:t.search}},[t._v("搜索")]),a("Button",{attrs:{type:"primary",size:"small",icon:"md-close"},on:{click:function(e){t.show=!1}}},[t._v("取消")])],1)])])}),r=[],c=a("5d73"),l=a.n(c),o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"cascadeTerm"},[a("strong",{staticStyle:{width:"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[t._v("行政区划：")]),a("div",{staticClass:"cascadeRec_box",staticStyle:{"padding-left":"100px","margin-top":"-22px"}},[t._l(t.listData,function(e,i){return a("p",{key:i},[a("span",{class:{active:-1===t.activeId},on:{click:function(a){return t.clickProvince(e)}}},[t._v("全省")]),t._l(e.children,function(e,i){return a("span",{key:i,class:{active:t.activeId===i},on:{click:function(a){return t.clickCity(e,i)}}},[t._v("\n          "+t._s(e.label)+"\n        ")])})],2)}),a("p",{directives:[{name:"show",rawName:"v-show",value:t.show,expression:"show"}],staticStyle:{"margin-top":"10px","padding-left":"10px"}},t._l(t.showData,function(e,i){return a("span",{key:i,class:{active:t.activeId2===i},on:{click:function(a){return t.clickCounty(e,i)}}},[t._v("\n        "+t._s(e.label)+"\n        ")])}),0)],2)])},d=[],u=a("3ac9"),h=a("5f87"),p={name:"CascadeTerm",data:function(){return{listData:[],show:!1,activeId:-2,activeId2:-2,showData:[]}},mounted:function(){this.getMenu()},methods:{getMenu:function(){var t=this;Object(u["n"])({accessToken:Object(h["a"])(),validate:!0,data:{}}).then(function(e){t.listData=e.data.data}).catch(function(t){console.log(t)})},clickProvince:function(t){if(-1===this.activeId)this.$emit("delQueryTrem","code"),this.show=!1,this.activeId=-2,this.activeId2=-2;else{for(var e in this.show=!1,this.showData=[],t.children)this.showData=this.showData.concat(t.children[e].children);this.activeId=-1,this.activeId2=-2,this.labelValue=t.label;var a={name:"行政区划",text:"全省",key:"code",value:t.comCode};this.updataQueryData(a)}},clickCity:function(t,e){if(this.activeId===e)this.$emit("delQueryTrem","code"),this.show=!1,this.activeId=-2,this.activeId2=-2;else{this.show=!0,this.showData=t.children,this.activeId=e,this.activeId2=-2,this.labelValue=t.label;var a={name:"行政区划",text:this.labelValue,key:"code",value:t.comCode};this.updataQueryData(a)}},clickCounty:function(t,e){this.activeId2=e,this.labelValue=t.label;var a={name:"行政区划",text:this.labelValue,key:"code",value:t.comCode};this.updataQueryData(a)},updataQueryData:function(t){this.$emit("updataQueryData",t)}}},m=p,f=(a("35fc"),a("2877")),g=Object(f["a"])(m,o,d,!1,null,"45bde858",null),v=g.exports,k=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"textTerm"},[a("span",{staticStyle:{"min-width":"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[t._v(t._s(t.titName)+"：")]),a("DatePicker",{staticStyle:{width:"200px"},attrs:{size:"large",type:"year",editable:!1,"start-date":new Date,placement:"bottom-end",placeholder:"请输入"+t.titName},on:{"on-change":function(e){return t.srldChange()}},model:{value:t.cl010,callback:function(e){t.cl010=e},expression:"cl010"}})],1)},y=[],b=a("cf45"),w={name:"TextTerm",data:function(){return{cl010:""}},props:{titName:{},titKey:{}},mounted:function(){},methods:{srldChange:function(){if(null===this.cl010||""===this.cl010)this.$emit("delQueryTrem",this.titKey);else{var t={name:this.titName,text:Object(b["g"])(this.cl010),key:this.titKey,value:Object(b["g"])(this.cl010)};this.$emit("updataQueryData",t)}}}},D=w,I=(a("3918"),Object(f["a"])(D,k,y,!1,null,"0463760a",null)),x=I.exports,L=a("49ae"),C={name:"queryBox",data:function(){return{show:!1,cl002:"",cl010:"",queryData:[]}},components:{CascadeTerm:v,TextTerm:L["a"],DatePickerTermOne:x},mounted:function(){document.addEventListener("click",this.queryHide)},beforeDestroy:function(){document.removeEventListener("click",this.queryHide)},methods:{updataQueryData:function(t){""!==t.text?(this.queryData=this.queryData.filter(function(e){return e.name!==t.name}),this.queryData.push(t)):this.queryData=this.queryData.filter(function(e){return e.name!==t.name})},delQueryTrem:function(t){this.queryData=this.queryData.filter(function(e){return e.key!==t})},search:function(){var t={},e=!0,a=!1,i=void 0;try{for(var s,n=l()(this.queryData);!(e=(s=n.next()).done);e=!0){var r=s.value;t[r.key]=r.value}}catch(c){a=!0,i=c}finally{try{e||null==n.return||n.return()}finally{if(a)throw i}}this.$emit("searchSrldId",t),this.show=!1},queryHide:function(t){this.$refs.queryBox.contains(t.target)||this.$refs.queryDown.contains(t.target)||(this.show=!1)}}},E=C,T=(a("fcc6"),Object(f["a"])(E,n,r,!1,null,"41f26fd8",null)),$=T.exports,S=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"taskAdd"}},[a("Modal",{attrs:{title:"新建核查任务"},on:{"on-cancel":t.reset},model:{value:t.taskBuild,callback:function(e){t.taskBuild=e},expression:"taskBuild"}},[a("Form",{ref:"taskAddForm",staticStyle:{padding:"0 20%"},attrs:{model:t.taskAddData,rules:t.taskAddRuleValidate,"label-width":85}},[a("FormItem",{attrs:{prop:"cl002",label:"任务名称："}},[a("Input",{attrs:{placeholder:"请输入任务名称",size:"default"},model:{value:t.taskAddData.cl002,callback:function(e){t.$set(t.taskAddData,"cl002","string"===typeof e?e.trim():e)},expression:"taskAddData.cl002"}})],1),a("FormItem",{attrs:{prop:"cl016",label:"行政区划："}},[a("Cascader",{attrs:{data:t.codeData,size:"default","change-on-select":""},model:{value:t.taskAddData.cl016,callback:function(e){t.$set(t.taskAddData,"cl016",e)},expression:"taskAddData.cl016"}})],1),a("FormItem",{attrs:{prop:"cl010",label:"任务年份："}},[a("DatePicker",{staticStyle:{width:"100%"},attrs:{size:"default",editable:!1,type:"year",placement:"bottom-end",placeholder:"请选择任务年份"},model:{value:t.taskAddData.cl010,callback:function(e){t.$set(t.taskAddData,"cl010","string"===typeof e?e.trim():e)},expression:"taskAddData.cl010"}})],1),a("FormItem",{attrs:{prop:"ledgerIdList",label:"任务台账："}},[a("i-select",{staticStyle:{width:"100%"},attrs:{multiple:"",filterable:"",placeholder:"请选择任务台账"},model:{value:t.taskAddData.ledgerIdList,callback:function(e){t.$set(t.taskAddData,"ledgerIdList","string"===typeof e?e.trim():e)},expression:"taskAddData.ledgerIdList"}},t._l(t.ledgerList,function(e){return a("i-option",{attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"cl009",label:"任务描述："}},[a("i-input",{attrs:{type:"textarea",autosize:{minRows:2,maxRows:5},placeholder:"请输入任务描述"},model:{value:t.taskAddData.cl009,callback:function(e){t.$set(t.taskAddData,"cl009","string"===typeof e?e.trim():e)},expression:"taskAddData.cl009"}})],1)],1),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("Button",{attrs:{type:"primary"},on:{click:t.taskAddSubmit}},[t._v("确定")]),a("Button",{on:{click:t.closeModal}},[t._v("取消")])],1)],1)],1)},N=[],_=(a("96cf"),a("3b8d")),A=a("6df0"),O={name:"TaskAdd",data:function(){var t=function(t,e,a){""===e?a(new Error("任务台账不能为空")):a()};return{ledgerList:[],taskAddData:{},taskAddRuleValidate:{cl002:[{required:!0,message:"任务名称必须填写",trigger:"blur"}],ledgerIdList:[{required:!0,message:"任务台账必须选择",trigger:"change",type:"array"},{validator:t,trigger:"change"}]},taskBuild:!1}},props:{codeData:{}},mounted:function(){this.getLedgerList()},methods:{getLedgerList:function(){var t=Object(_["a"])(regeneratorRuntime.mark(function t(){var e=this;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(A["c"])({data:{}}).then(function(t){"0000"===t.data.code&&t.data.data.data.forEach(function(t,a){e.ledgerList.push({value:t.id,label:t.name})})}).catch(function(){e.$Notice.error({title:"服务器错误"})});case 2:case"end":return t.stop()}},t,this)}));function e(){return t.apply(this,arguments)}return e}(),taskAddSubmit:function(){var t=this;this.$refs.taskAddForm.validate(function(e){if(e){var a="";a=void 0===t.taskAddData.cl016||0===t.taskAddData.cl016.length?"":t.taskAddData.cl016[t.taskAddData.cl016.length-1],Object(A["f"])({data:{cl002:t.taskAddData.cl002,cl010:Object(b["g"])(t.taskAddData.cl010),ledgerIdList:t.taskAddData.ledgerIdList,cl009:t.taskAddData.cl009,cl016:a}}).then(function(e){"0000"===e.data.code?(t.$Notice.success({title:"新建核查任务成功"}),t.taskBuild=!1,t.taskAddData={},t.reset(),t.$emit("getTableData")):t.$Message.error(e.data.msg)}).catch(function(){t.$Notice.error({title:"服务器错误"})})}})},reset:function(){this.taskAddData={cl002:"",cl010:"",ledgerIdList:"",cl009:"",cl016:[]},this.$refs.taskAddForm.resetFields()},closeModal:function(){this.taskBuild=!1,this.reset()}}},z=O,B=Object(f["a"])(z,S,N,!1,null,"02ad42a4",null),M=B.exports,j=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"taskEdit"}},[a("Modal",{attrs:{title:"修改核查任务"},model:{value:t.taskEdit,callback:function(e){t.taskEdit=e},expression:"taskEdit"}},[a("Form",{ref:"taskEditForm",staticStyle:{padding:"0 20%"},attrs:{model:t.taskEditData,rules:t.taskEditRule,"label-width":85}},[a("FormItem",{staticStyle:{display:"none"},attrs:{prop:"cl001",label:"ID："}},[a("Input",{model:{value:t.taskEditData.cl001,callback:function(e){t.$set(t.taskEditData,"cl001","string"===typeof e?e.trim():e)},expression:"taskEditData.cl001"}})],1),a("FormItem",{attrs:{prop:"cl002",label:"任务名称："}},[a("Input",{attrs:{placeholder:"请输入任务名称",size:"default"},model:{value:t.taskEditData.cl002,callback:function(e){t.$set(t.taskEditData,"cl002","string"===typeof e?e.trim():e)},expression:"taskEditData.cl002 "}})],1),a("FormItem",{attrs:{prop:"cl016",label:"行政区划："}},[a("Cascader",{attrs:{data:t.codeData,size:"default","change-on-select":""},model:{value:t.taskEditData.cl016,callback:function(e){t.$set(t.taskEditData,"cl016",e)},expression:"taskEditData.cl016"}})],1),a("FormItem",{attrs:{prop:"cl010",label:"任务年份："}},[a("DatePicker",{staticStyle:{width:"100%"},attrs:{size:"default",type:"year",editable:!1,placement:"bottom-end",placeholder:"请选择任务年份"},model:{value:t.taskEditData.cl010,callback:function(e){t.$set(t.taskEditData,"cl010","string"===typeof e?e.trim():e)},expression:"taskEditData.cl010"}})],1),a("FormItem",{attrs:{prop:"ledgerIdList",label:"任务台账："}},[a("i-select",{staticStyle:{width:"100%"},attrs:{multiple:"",filterable:"",placeholder:"请选择任务台账"},model:{value:t.taskEditData.ledgerIdList,callback:function(e){t.$set(t.taskEditData,"ledgerIdList","string"===typeof e?e.trim():e)},expression:"taskEditData.ledgerIdList"}},t._l(t.editLedgerList,function(e){return a("i-option",{attrs:{value:e.value}},[t._v(t._s(e.label))])}),1)],1),a("FormItem",{attrs:{prop:"cl009",label:"任务描述："}},[a("i-input",{attrs:{type:"textarea",autosize:{minRows:2,maxRows:5},placeholder:"请输入任务描述"},model:{value:t.taskEditData.cl009,callback:function(e){t.$set(t.taskEditData,"cl009","string"===typeof e?e.trim():e)},expression:"taskEditData.cl009"}})],1)],1),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("Button",{attrs:{type:"primary"},on:{click:t.taskEditSubmit}},[t._v("确定")]),a("Button",{on:{click:t.closeModal}},[t._v("取消")])],1)],1)],1)},q=[],P=a("f499"),R=a.n(P),Q={name:"Taskedit",data:function(){var t=function(t,e,a){""===e?a(new Error("任务台账不能为空")):a()};return{taskEditRule:{cl002:[{required:!0,message:"任务名称必须填写",trigger:"blur"}],ledgerIdList:[{required:!0,message:"任务台账必须选择",trigger:"change",type:"array"},{validator:t,trigger:"change"}]},taskEdit:!1,taskEditData:{},editLedgerList:[]}},props:{data:{},codeData:{}},watch:{data:function(){this.dataHandle()}},created:function(){this.getLedgerList()},methods:{getLedgerList:function(){var t=Object(_["a"])(regeneratorRuntime.mark(function t(){var e=this;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,Object(A["c"])({data:{}}).then(function(t){"0000"===t.data.code&&t.data.data.data.forEach(function(t,a){e.editLedgerList.push({value:t.id,label:t.name})})}).catch(function(){e.$Notice.error({title:"服务器错误"})});case 2:case"end":return t.stop()}},t,this)}));function e(){return t.apply(this,arguments)}return e}(),dataHandle:function(){this.taskEditData.cl001=this.data.cl001,this.taskEditData.cl002=this.data.cl002,this.taskEditData.cl010=this.data.cl010,this.taskEditData.cl009=this.data.cl009;var t=this.data.rlhdGroupList,e=[];t&&t.length>0&&t.forEach(function(t,a){e.push(t.id)}),console.log(e),this.$set(this.taskEditData,"ledgerIdList",e);var a=[];this.getDefaultCode(this.codeData,String(this.data.cl016),a)},getDefaultCode:function(t,e,a){for(var i in t)t[i].value===e?(a.push(t[i].value),this.taskEditData.cl016=JSON.parse(R()(a))):t[0].children.length>0&&(a.push(t[i].value),this.getDefaultCode(t[i].children,e,a));a.pop()},taskEditSubmit:function(){var t=this;this.$refs.taskEditForm.validate(function(e){if(e){var a="";a=void 0===t.taskEditData.cl016||0===t.taskEditData.cl016.length?"":t.taskEditData.cl016[t.taskEditData.cl016.length-1],Object(A["h"])({data:{cl001:t.taskEditData.cl001,cl002:t.taskEditData.cl002,cl010:Object(b["g"])(t.taskEditData.cl010),ledgerIdList:t.taskEditData.ledgerIdList,cl009:t.taskEditData.cl009,cl016:a}}).then(function(e){"0000"===e.data.code?(t.$Notice.success({title:"修改核查任务成功"}),t.$emit("getTableData"),t.taskEdit=!1,t.taskEditData={},t.reset()):t.$Message.error(e.data.msg)}).catch(function(){t.$Notice.error({title:"服务器错误"})})}})},reset:function(){this.taskEditData={cl003:"",cl002:"",cl010:"",ledgerIdList:"",cl009:"",cl016:[]},this.$refs.taskEditForm.resetFields()},closeModal:function(){this.taskEdit=!1}}},F=Q,H=Object(f["a"])(F,j,q,!1,null,"3c7ede45",null),W=H.exports,V=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"issueList"}},[a("Modal",{attrs:{id:"taskIssueModal",title:"下发人员列表",loading:t.modalIssueLoading},on:{"on-cancel":t.handleCancel},model:{value:t.issue,callback:function(e){t.issue=e},expression:"issue"}},[a("Form",{ref:"formInline",staticClass:"formInline",attrs:{inline:"","label-width":75,model:t.formInline}},[a("FormItem",{attrs:{label:"真实姓名："}},[a("Input",{staticStyle:{width:"150px"},attrs:{type:"text",placeholder:"请输入用户名称"},model:{value:t.formInline.sa019,callback:function(e){t.$set(t.formInline,"sa019",e)},expression:"formInline.sa019"}})],1),a("FormItem",{attrs:{label:"手机号："}},[a("Input",{staticStyle:{width:"150px"},attrs:{type:"text",placeholder:"请输入手机号"},model:{value:t.formInline.sa012,callback:function(e){t.$set(t.formInline,"sa012",e)},expression:"formInline.sa012"}})],1),a("FormItem",{staticClass:"ml20",attrs:{"label-width":0}},[a("Button",{staticClass:"mr10",attrs:{type:"primary",size:"small",icon:"ios-search"},on:{click:function(e){return e.stopPropagation(),t.getSearchIssueList(e)}}},[t._v("查询")]),a("Button",{attrs:{type:"primary",size:"small",icon:"md-refresh"},on:{click:function(e){return e.stopPropagation(),t.cleardata(e)}}},[t._v("重置")])],1)],1),a("div",[a("Table",{ref:"IssueMultipleTable",attrs:{border:"",stripe:"",loading:t.IssueLoading,columns:t.IssueColumns1,data:t.IssueData,"highlight-row":"","max-height":t.IssueTableHeight},on:{"on-selection-change":t.handleIssueSelectionChange}}),a("div",{staticClass:"page_box"},[a("Page",{ref:"IssuePages",attrs:{total:t.IssueTotal,"page-size":t.IssuePageSize,current:t.IssuePageNum,"show-total":""},on:{"update:current":function(e){t.IssuePageNum=e},"on-change":t.IssuePageChange}})],1)],1),a("div",{attrs:{slot:"footer"},slot:"footer"},[a("i-button",{staticClass:"mr10",attrs:{type:"text"},on:{click:t.handleCancel}},[t._v("取消")]),a("Button",{attrs:{type:"primary"},on:{click:t.handleIssueTask}},[t._v("下发")])],1)],1)],1)},J=[],G={name:"IssueList",data:function(){var t=this;return{issue:!1,modalIssueLoading:!1,formInline:{sa019:"",sa012:""},IssueTableHeight:48*Math.floor((window.innerHeight-350-40)/48)+40,IssueColumns1:[{type:"selection",width:60,align:"center"},{title:"序号",key:"index",width:100,align:"center",render:function(e,a){return e("span",a.index+(t.IssuePageNum-1)*t.IssuePageSize+1)}},{title:"用户名",align:"center",minWidth:200,key:"sa008"},{title:"手机号码",align:"center",minWidth:200,key:"sa012"},{title:"真实姓名",align:"center",minWidth:200,key:"sa019"}],IssueData:[],IssueTotal:0,IssuePageNum:1,IssuePageSize:10,IssueLoading:!1,selectIssueRow:[],multipleIssueSelection:[],taskParamsArr:[]}},props:{taskIdArr:{}},created:function(){this.getIssueList()},watch:{multipleIssueSelection:function(t){var e=this;this.selectIssueRow=[],t.length>0&&t.forEach(function(t,a){e.selectIssueRow.push(t.sa001)})},taskIdArr:function(t){this.taskParamsArr=t}},mounted:function(){var t=this;window.addEventListener("resize",function(){t.IssueTableHeight=48*Math.floor((window.innerHeight-350-40)/48)+40,t.getIssueList()},!1)},methods:{getSearchIssueList:function(){this.IssuePageNum=1,this.getIssueList()},getIssueList:function(){var t=this;this.IssueLoading=!0,Object(A["i"])({data:{pageSize:this.IssuePageSize,pageNumber:this.IssuePageNum,sa019:this.formInline.sa019,sa012:this.formInline.sa012}}).then(function(e){"0000"===e.data.code&&(t.IssueData=e.data.data.rows,t.IssueTotal=e.data.data.total,t.IssueLoading=!1)}).catch(function(){t.$Notice.error({title:"服务器错误"})})},IssuePageChange:function(t){this.IssuePageNum=t,this.getIssueList()},handleIssueSelectionChange:function(t){this.multipleIssueSelection=t},handleIssueTask:function(){this.selectIssueRow.length<1?this.$Notice.error({title:"下发人员不能为空！"}):this.handleIssue(this.selectIssueRow)},handleIssue:function(t){var e=this;Object(A["g"])({data:{taskId:this.taskParamsArr.join(","),uidList:t}}).then(function(t){"0000"===t.data.code?(e.$Notice.success({title:"任务下发成功"}),e.handleCancel()):e.$Message.error(t.data.msg)}).catch(function(){e.$Notice.error({title:"服务器错误"})})},cleardata:function(){this.formInline={},this.getSearchIssueList()},handleCancel:function(){this.issue=!1,this.selectIssueRow=[],this.multipleIssueSelection=[],this.cleardata()}}},K=G,U=(a("afca"),Object(f["a"])(K,V,J,!1,null,"6266e61d",null)),X=U.exports,Y=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"tablet"}},[a("div",{staticClass:"queryBox"},[a("Button",{staticClass:"sign-btn",attrs:{type:"primary",size:"small",icon:"ios-arrow-back"},on:{click:function(e){return t.$emit("signOut",!1)}}},[t._v("返回")])],1),a("div",{staticClass:"content_box"},[a("div",{staticStyle:{height:"100%"}},[a("Table",{attrs:{border:"",stripe:"",loading:t.spotsLoading,columns:t.spotsColumns1,data:t.spotsData1,"highlight-row":"","max-height":t.tableHeight}})],1)]),a("div",{staticClass:"extract_box",class:{spotsAddActive:t.spotsAddActive}},[a("taskManagementTableMap",{ref:"taskManagementTableMap",on:{signOut:t.signOut}})],1)])},Z=[],tt=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"map-box"},[a("div",{ref:"viewDiv",attrs:{id:"viewDiv"}}),a("Button",{staticClass:"sign-btn",attrs:{type:"primary",size:"small",icon:"ios-arrow-back"},on:{click:function(e){return t.$emit("signOut",!1)}}},[t._v("返回")])],1)},et=[],at=a("9ed9"),it=a("149e"),st=a("f831"),nt=a("42e8"),rt=a.n(nt),ct=a("39f3"),lt=a.n(ct),ot={name:"checkInfoMap",data:function(){return{map:{},view:{}}},computed:{},props:{},components:{},watch:{},mounted:function(){this.createMap()},methods:{createMap:function(){var t=Object(_["a"])(regeneratorRuntime.mark(function t(){var e,a,i,s,n,r,c,l,o,d,u,h;return regeneratorRuntime.wrap(function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,at["a"].MapView;case 2:return e=t.sent,t.next=5,at["a"].Map;case 5:return a=t.sent,t.next=8,at["a"].Basemap;case 8:return i=t.sent,t.next=11,at["a"].BasemapToggle;case 11:return s=t.sent,t.next=14,Object(st["i"])().then(function(t){return t});case 14:return n=t.sent,t.next=17,Object(st["h"])().then(function(t){return t});case 17:return r=t.sent,t.next=20,Object(st["g"])().then(function(t){return t});case 20:return c=t.sent,t.next=23,Object(st["j"])().then(function(t){return t});case 23:return l=t.sent,t.next=26,Object(st["f"])().then(function(t){return t});case 26:o=t.sent,d=new i({baseLayers:[r,o,c],title:"矢量地图",id:"myBasemap",thumbnailUrl:rt.a}),u=new i({baseLayers:[n,o,l],title:"影像地图",id:"myBasemap1",thumbnailUrl:lt.a}),this.map=new a({basemap:u}),this.view=new e({map:this.map,container:"viewDiv",center:it["a"].centerPoint,zoom:7}),h=new s({view:this.view,nextBasemap:d}),this.view.ui.add(h,"top-right");case 33:case"end":return t.stop()}},t,this)}));function e(){return t.apply(this,arguments)}return e}(),initCom:function(t){this.map.remove(this.insercLayer),this.view.graphics.removeAll()}}},dt=ot,ut=(a("6722"),Object(f["a"])(dt,tt,et,!1,null,"7c52b649",null)),ht=ut.exports,pt=a("4cf8"),mt={name:"spotsTable",data:function(){return{tableHeight:Math.floor(window.innerHeight-288),spotsColumns1:[{title:"序号",key:"index",width:100,align:"center",render:function(t,e){return t("span",e.index+1)}},{title:"台账名称",align:"center",minWidth:200,key:"name",render:function(t,e){return t("span",e.row.name)}},{title:"斑块名称",align:"center",minWidth:200,key:"activeName ",render:function(t,e){return t("span",e.row.activeName)}},{title:"斑块编号",align:"center",minWidth:100,key:"cd001 ",render:function(t,e){return t("span",e.row.cd001)}},{title:"下发状态",align:"center",minWidth:100,key:"sendStatus ",render:function(t,e){if(null!==e.row.activeName&&""!==e.row.activeName)return 1===e.row.sendStatus?t("span","已下发"):t("span","未下发")}},{title:"台账备注",align:"center",minWidth:200,key:"remark",render:function(t,e){return t("span",e.row.remark)}}],spotsData1:[],spotsLoading:!1,spotsAddActive:!1,tzTime:"",tzName:"",tzRemark:""}},props:{seeId:{}},components:{taskManagementTableMap:ht},watch:{seeId:function(){this.dataId()}},mounted:function(){},methods:{dataId:function(){this.getTableList(this.seeId),this.getBkList(this.seeId)},getTableList:function(t){var e=this;Object(A["d"])({data:{cl001:t}}).then(function(t){"0000"===t.data.code&&t.data.data.data.rlhdGroupList.length>0&&(e.spotsData1=t.data.data.data.rlhdGroupList,e.tzTime=Object(b["d"])(e.spotsData1[0].createDate),e.tzName=e.spotsData1[0].name,e.tzRemark=e.spotsData1[0].remark)}).catch(function(){e.$Notice.error({title:"服务器错误"})})},getBkList:function(t){var e=this;this.spotsLoading=!0,Object(pt["b"])({data:{taskId:t}}).then(function(t){"0000"===t.data.code&&(""!==t.data.data&&null!==t.data.data?(e.spotsData1=[],t.data.data.forEach(function(t,a){t.st4ScsCdList.forEach(function(a,i){a["name"]=t.name,a["remark"]=t.remark,e.spotsData1.push(a)})})):(e.spotsData1=[],e.$Notice.warning({title:"此任务下尚无绑定的问题斑块！"})),e.spotsLoading=!1)}).catch(function(){e.$Notice.error({title:"服务器错误"}),e.spotsLoading=!1})},signOut:function(t){this.spotsAddActive=t},goMap:function(){this.spotsAddActive=!0,this.$refs.taskManagementTableMap.initCom()}}},ft=mt,gt=(a("336a"),Object(f["a"])(ft,Y,Z,!1,null,"d37e2c12",null)),vt=gt.exports,kt={name:"extracTable",data:function(){var t=this;return{tableHeight:48*Math.floor((window.innerHeight-300-40)/48)+40,loading:!1,columns1:[{title:"序号",key:"index",width:100,align:"center",render:function(e,a){return e("span",a.index+(t.pageNum-1)*t.pageSize+1)}},{title:"任务名称",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"cl002"},{title:"所属区划",align:"center",width:100,key:"comName",render:function(t,e){return e.row.sysCompany&&e.row.sysCompany.comName?t("span",{},e.row.sysCompany.comName):t("span",{},"")}},{title:"任务年份",align:"center",width:100,key:"cl010"},{title:"任务台账",align:"center",minWidth:200,render:function(t,e){var a=e.row,i=[];return a.rlhdGroupList.length>0&&a.rlhdGroupList.forEach(function(t){t.name&&i.push(t.name)}),t("div",{},i.join("，"))}},{title:"任务描述",align:"center",ellipsis:!0,tooltip:!0,minWidth:200,key:"cl009"},{title:"操作",key:"",width:260,align:"center",render:function(e,a){return e("div",[e("Button",{props:{type:"primary",size:"small",icon:"md-eye"},style:{marginRight:"5px"},on:{click:function(){t.spotsSeeId=a.row.cl001,t.addActive=!0,t.$refs.spotsTable.getTableList(t.spotsSeeId)}}},"查看台账"),e("Button",{props:{type:"warning",size:"small",icon:"md-create"},style:{marginRight:"5px"},on:{click:function(){t.editDefaultData=a.row,t.$refs.taskEdit.taskEdit=!0}}},"修改"),e("Button",{props:{type:"error",size:"small",icon:"md-trash"},on:{click:function(){t.$Modal.confirm({title:"确认",content:"请问您确定要删除该核查任务吗？",onOk:function(){t.infoDel(a.row.cl001)},onCancel:function(){}})}}},"删除")])}}],data1:[],total:0,pageNum:1,pageSize:Math.floor((48*Math.floor((window.innerHeight-300-40)/48)+40)/48),editDefaultData:{},addActive:!1,spotsSeeId:0,selectRow:[],multipleSelection:[],obj:"",cl002:"",cl010:"",cl016:"",codeData:[]}},components:{QueryBox:$,taskAdd:M,taskEdit:W,IssueList:X,spotsTable:vt},watch:{multipleSelection:function(t){var e=this;this.selectRow=[],t.length>0&&t.forEach(function(t,a){e.selectRow.push(t.cl001)})}},created:function(){this.getTableList()},mounted:function(){var t=this;window.addEventListener("resize",function(){t.tableHeight=48*Math.floor((window.innerHeight-300-40)/48)+40,t.pageSize=Math.floor((48*Math.floor((window.innerHeight-300-40)/48)+40)/48),t.getTableList()},!1),this.getCodeMenu()},methods:{searchSrldId:function(t){this.cl002=t.cl002,this.cl010=t.cl010,this.cl016=t.code,this.pageNum=1,this.getTableList()},getTableList:function(){var t=this;this.loading=!0,Object(A["e"])({data:{pageSize:this.pageSize,pageNumber:this.pageNum,cl002:this.cl002,cl010:this.cl010,cl016:this.cl016}}).then(function(e){"0000"===e.data.code?(t.data1=e.data.data.rows,t.total=e.data.data.total,t.loading=!1):t.$Notice.error({title:e.data.msg})}).catch(function(){t.$Notice.error({title:"服务器错误"})})},pageChange:function(t){this.pageNum=t,this.getTableList()},infoDel:function(t){var e=this;Object(A["a"])({data:{cl001:t}}).then(function(t){"0000"===t.data.code?(e.$Notice.success({title:"删除成功"}),1===e.data1.length&&e.pageNum>1&&(e.pageNum=e.pageNum-1),e.getTableList()):e.$Message.error(t.data.msg)}).catch(function(){e.$Notice.error({title:"服务器错误"})})},handleSelectionChange:function(t){this.multipleSelection=t},handleIssue:function(){this.$refs.issueList.issue=!1,this.selectRow.length<1?this.$Notice.error({title:"请先勾选任务再点击下发！"}):this.selectRow.length>1?this.$Notice.error({title:"只能选择一个任务！"}):this.$refs.issueList.issue=!0},signOut:function(t){this.addActive=t,!1===t&&(this.spotsSeeId="")},getClearQueryData:function(){this.pageNum=1,this.getTableList()},uploadSuccess:function(t,e){"0000"===t.code?(this.$Notice.success({title:"批量导入核查任务成功！"}),this.getTableList()):this.$Notice.error({title:t.msg})},handleExport:function(){var t=this;Object(A["b"])({data:{cl002:this.cl002,cl010:this.cl010,cl016:this.cl016}}).then(function(t){if("0000"===t.data.code){var e=t.data.data;if(!e)return;var a=document.createElement("a");a.style.display="none";var i=t.data.data.excelPath;a.href="/epr"+i,a.setAttribute("download",i.split("/")[i.split("/").length-1]),document.body.appendChild(a),a.click()}}).catch(function(){t.$Notice.error({title:"服务器错误"})})},getCodeMenu:function(){var t=this;Object(u["n"])({data:{}}).then(function(e){"0000"===e.data.code&&(t.codeData=e.data.data,t.codeData=t.valComCode(t.codeData))}).catch(function(){t.$Notice.error({title:"服务器错误"})})},valComCode:function(t){for(var e in t)t[e].value=t[e].comCode,this.valComCode(t[e].children);return t}}},yt=kt,bt=(a("79f3"),Object(f["a"])(yt,i,s,!1,null,"4423e42e",null));e["default"]=bt.exports},"79f3":function(t,e,a){"use strict";var i=a("2d16"),s=a.n(i);s.a},9551:function(t,e,a){},a21f:function(t,e,a){var i=a("584a"),s=i.JSON||(i.JSON={stringify:JSON.stringify});t.exports=function(t){return s.stringify.apply(s,arguments)}},afca:function(t,e,a){"use strict";var i=a("f45c"),s=a.n(i);s.a},f45c:function(t,e,a){},f499:function(t,e,a){t.exports=a("a21f")},fcc6:function(t,e,a){"use strict";var i=a("1761"),s=a.n(i);s.a}}]);
//# sourceMappingURL=chunk-526700c6.b86f323b.js.map