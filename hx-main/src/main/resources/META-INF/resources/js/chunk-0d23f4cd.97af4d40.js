(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0d23f4cd"],{2457:function(t,e,i){},"2dc4":function(t,e,i){"use strict";var a=i("2457"),l=i.n(a);l.a},"65d4":function(t,e,i){"use strict";i.d(e,"b",function(){return l}),i.d(e,"c",function(){return r}),i.d(e,"d",function(){return n}),i.d(e,"a",function(){return s}),i.d(e,"f",function(){return o}),i.d(e,"e",function(){return d});var a=i("b775"),l=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/getSonTableList",method:"post",data:t})},r=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/getSurveyTableList",method:"post",data:t})},n=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/getTableInfo",method:"post",data:t})},s=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/getFileds",method:"post",data:t})},o=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/updateSonTable",method:"post",data:t})},d=function(t){return Object(a["a"])({url:"/dcxx/dicIndexItem/save",method:"post",data:t})}},"672c":function(t,e,i){"use strict";var a=i("c7cb"),l=i.n(a);l.a},a077:function(t,e,i){"use strict";var a=i("b2c5"),l=i.n(a);l.a},a21f:function(t,e,i){var a=i("584a"),l=a.JSON||(a.JSON={stringify:JSON.stringify});t.exports=function(t){return l.stringify.apply(l,arguments)}},b2c5:function(t,e,i){},b62c:function(t,e,i){"use strict";i.r(e);var a=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"content"},[i("Row",[i("Col",{attrs:{span:"5"}},[i("Card",{staticClass:"card_con",scopedSlots:t._u([{key:"title",fn:function(){return[t._v("数据采集表单")]},proxy:!0}])},[i("ul",t._l(t.tableList,function(e,a){return i("li",{key:e.pk_id,class:{tablelist:!0,active:t.activeList==a},on:{click:function(i){return t.tabChange(e,a)}}},[t._v("\n            "+t._s(e.index_item)+"\n          ")])}),0)])],1),i("Col",{staticStyle:{padding:"0 10px"},attrs:{span:"14"}},[i("Card",{staticClass:"card_con",scopedSlots:t._u([{key:"title",fn:function(){return[t._v("表单字段")]},proxy:!0}])},[i("EditTableField",{attrs:{SonTableListId:t.SonTableListId}})],1)],1),i("Col",{attrs:{span:"5"}},[i("Card",{staticClass:"card_con",scopedSlots:t._u([{key:"title",fn:function(){return[t._v("调查表介绍")]},proxy:!0}])},[i("div",{staticClass:"introduce"},[i("textarea",{directives:[{name:"model",rawName:"v-model",value:t.item.remark,expression:"item.remark"}],attrs:{placeholder:"调查表介绍"},domProps:{value:t.item.remark},on:{input:function(e){e.target.composing||t.$set(t.item,"remark",e.target.value)}}})]),i("p",{staticClass:"introduce_btn"},[i("Button",{staticStyle:{"margin-left":"20px"},attrs:{type:"primary"},on:{click:t.updateSonTable}},[t._v("保存")])],1)])],1)],1)],1)},l=[],r=i("65d4"),n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"editTable"},[i("draggable",{attrs:{group:"people"},on:{start:function(e){t.drag=!0},end:function(e){t.drag=!1},change:t.fieldDataChange},model:{value:t.fieldlist,callback:function(e){t.fieldlist=e},expression:"fieldlist"}},t._l(t.fieldlist,function(e,a){return i("div",{key:e.sort,staticClass:"li_box"},[i("span",{staticClass:"from_lable"},[t._v("\n              "+t._s(e.filedName)+"\n              "),i("i",{directives:[{name:"show",rawName:"v-show",value:"1"==e.isMust,expression:"element.isMust == '1'"}]},[t._v("*")])]),1==e.filedType?i("Input",{staticStyle:{width:"250px"},attrs:{placeholder:e.filedName}}):t._e(),2==e.filedType?i("Input",{staticStyle:{width:"250px"},attrs:{placeholder:e.filedName,max:null===e.max?1/0:1*e.max,min:null===e.min?-1/0:1*e.min,"active-change":!1}}):t._e(),3==e.filedType?i("Input",{staticStyle:{width:"250px"},attrs:{placeholder:e.filedName}}):t._e(),4==e.filedType?i("Select",{staticStyle:{width:"250px"}},t._l(e.selects,function(a,l){return i("Option",{key:e.sort.toString()+l,attrs:{value:l}},[t._v("\n          "+t._s(a)+"\n        ")])}),1):t._e(),5==e.filedType?i("Select",{staticStyle:{width:"250px"},attrs:{multiple:""}},t._l(e.selects,function(a,l){return i("Option",{key:e.sort.toString()+l,attrs:{value:l}},[t._v("\n          "+t._s(a)+"\n        ")])}),1):t._e(),6==e.filedType?i("RadioGroup",t._l(e.selects,function(t,a){return i("Radio",{key:e.sort.toString()+a,staticStyle:{height:"30px","line-height":"30px"},attrs:{label:t,value:a}})}),1):t._e(),7==e.filedType?i("DatePicker",{staticStyle:{width:"250px"},attrs:{type:"date",placeholder:e.filedName}}):t._e(),i("span",{staticClass:"dttails_btn",on:{click:function(i){t.filedDetails=t.filedDetails==e.sort?-1:e.sort}}},[t._v("\n        详情"),i("Icon",{attrs:{type:"md-arrow-dropdown"}})],1),i("span",{staticClass:"dttails_btn",staticStyle:{"margin-right":"15px"},on:{click:function(i){return t.delField(a,e.id)}}},[t._v("\n        删除\n      ")]),i("div",{directives:[{name:"show",rawName:"v-show",value:t.filedDetails==e.sort,expression:"filedDetails == element.sort"}],staticClass:"details_box"},[i("span",{staticStyle:{"margin-right":"15px"}},[t._v("是否必填："+t._s(1==e.isMust?"是":"否"))]),1==e.filedType&&null!==e.stringLength?i("span",{staticStyle:{"margin-right":"15px"}},[t._v("长度："+t._s(e.stringLength))]):t._e(),2!=e.filedType&&3!=e.filedType||null===e.max?t._e():i("span",{staticStyle:{"margin-right":"15px"}},[t._v("最大值："+t._s(e.max))]),2!=e.filedType&&3!=e.filedType||null===e.min?t._e():i("span",{staticStyle:{"margin-right":"15px"}},[t._v("最小值："+t._s(e.min))]),3==e.filedType&&null!==e.max?i("span",{staticStyle:{"margin-right":"15px"}},[t._v("保留小数位："+t._s(e.decimalPoint))]):t._e()])],1)}),0),i("p",{staticStyle:{"text-align":"right",margin:"10px 20px 0",position:"absolute",bottom:"25px",right:"20px"}},[i("Button",{attrs:{type:"primary"},on:{click:function(e){t.$refs.addField.addFieldModel=!0}}},[t._v("添加字段")]),i("Button",{staticStyle:{"margin-left":"20px"},attrs:{type:"primary",loading:t.loading},on:{click:t.updataFieldList}},[t._v("保存")])],1),i("EditTableAddField",{ref:"addField",on:{addArr:t.addArr}})],1)},s=[],o=i("795b"),d=i.n(o),m=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",{staticClass:"content"},[i("Modal",{attrs:{title:"添加字段"},model:{value:t.addFieldModel,callback:function(e){t.addFieldModel=e},expression:"addFieldModel"}},[i("Form",{ref:"FormRef",attrs:{model:t.formItem,rules:t.loginRules,"label-width":125}},[i("FormItem",{attrs:{label:"字段名称",prop:"filedName"}},[i("Input",{attrs:{placeholder:"请输入字段名称"},model:{value:t.formItem.filedName,callback:function(e){t.$set(t.formItem,"filedName","string"===typeof e?e.trim():e)},expression:"formItem.filedName"}})],1),i("FormItem",{attrs:{label:"字段类型"}},[i("Select",{on:{"on-change":t.delAddValue},model:{value:t.formItem.filedType,callback:function(e){t.$set(t.formItem,"filedType",e)},expression:"formItem.filedType"}},[i("Option",{attrs:{value:"1"}},[t._v("文本")]),i("Option",{attrs:{value:"2"}},[t._v("整数")]),i("Option",{attrs:{value:"3"}},[t._v("小数")]),i("Option",{attrs:{value:"4"}},[t._v("单选下拉框")]),i("Option",{attrs:{value:"5"}},[t._v("多选下拉框")]),i("Option",{attrs:{value:"6"}},[t._v("勾选框")]),i("Option",{attrs:{value:"7"}},[t._v("时间框")])],1)],1),i("FormItem",{attrs:{label:"是否必填"}},[i("RadioGroup",{model:{value:t.formItem.isMust,callback:function(e){t.$set(t.formItem,"isMust",e)},expression:"formItem.isMust"}},[i("Radio",{attrs:{label:"1"}},[t._v("必填")]),i("Radio",{attrs:{label:"0"}},[t._v("非必填")])],1)],1),"1"===t.formItem.filedType?i("FormItem",{attrs:{label:"字符串最大长度",prop:"stringLength"}},[i("Input",{attrs:{placeholder:"请输入字符串最大长度"},model:{value:t.formItem.stringLength,callback:function(e){t.$set(t.formItem,"stringLength","string"===typeof e?e.trim():e)},expression:"formItem.stringLength"}})],1):t._e(),"2"===t.formItem.filedType?i("FormItem",{attrs:{label:"最小值",prop:"min"}},[i("Input",{attrs:{placeholder:"请输入最小值"},model:{value:t.formItem.min,callback:function(e){t.$set(t.formItem,"min","string"===typeof e?e.trim():e)},expression:"formItem.min"}})],1):t._e(),"2"===t.formItem.filedType?i("FormItem",{attrs:{label:"最大值",prop:"max"}},[i("Input",{attrs:{placeholder:"请输入最大值"},model:{value:t.formItem.max,callback:function(e){t.$set(t.formItem,"max","string"===typeof e?e.trim():e)},expression:"formItem.max"}})],1):t._e(),"3"===t.formItem.filedType?i("FormItem",{attrs:{label:"最小值",prop:"dmin"}},[i("Input",{attrs:{placeholder:"请输入最小值"},model:{value:t.formItem.min,callback:function(e){t.$set(t.formItem,"min","string"===typeof e?e.trim():e)},expression:"formItem.min"}})],1):t._e(),"3"===t.formItem.filedType?i("FormItem",{attrs:{label:"最大值",prop:"dmax"}},[i("Input",{attrs:{placeholder:"请输入最大值"},model:{value:t.formItem.max,callback:function(e){t.$set(t.formItem,"max","string"===typeof e?e.trim():e)},expression:"formItem.max"}})],1):t._e(),"3"===t.formItem.filedType?i("FormItem",{attrs:{label:"保留小数位数",prop:"decimalPoint"}},[i("Input",{attrs:{placeholder:"请输入保留小数位"},model:{value:t.formItem.decimalPoint,callback:function(e){t.$set(t.formItem,"decimalPoint","string"===typeof e?e.trim():e)},expression:"formItem.decimalPoint"}})],1):t._e(),"6"===t.formItem.filedType?i("FormItem",{attrs:{label:"单选按钮值"}},[i("Input",{attrs:{placeholder:"请输入单选按钮值，用逗号隔开"},model:{value:t.formItem.rediaoType,callback:function(e){t.$set(t.formItem,"rediaoType","string"===typeof e?e.trim():e)},expression:"formItem.rediaoType"}})],1):t._e(),"4"===t.formItem.filedType?i("FormItem",{attrs:{label:"单选下拉框值"}},[i("Input",{attrs:{placeholder:"请输入单选下拉框值，用逗号隔开"},model:{value:t.formItem.singleSelect,callback:function(e){t.$set(t.formItem,"singleSelect","string"===typeof e?e.trim():e)},expression:"formItem.singleSelect"}})],1):t._e(),"5"===t.formItem.filedType?i("FormItem",{attrs:{label:"多选下拉框值"}},[i("Input",{attrs:{placeholder:"请输入多选下拉框值，用逗号隔开"},model:{value:t.formItem.multipleSelect,callback:function(e){t.$set(t.formItem,"multipleSelect","string"===typeof e?e.trim():e)},expression:"formItem.multipleSelect"}})],1):t._e(),"7"===t.formItem.filedType?i("FormItem",{attrs:{label:"日期格式"}},[i("Select",{staticStyle:{width:"200px"},model:{value:t.formItem.timeLimit,callback:function(e){t.$set(t.formItem,"timeLimit",e)},expression:"formItem.timeLimit"}},[i("Option",{attrs:{value:1}},[t._v("yyyy-MM-dd HH:mm:ss")]),i("Option",{attrs:{value:2}},[t._v("yyyy-MM-dd")])],1)],1):t._e()],1),i("div",{attrs:{slot:"footer"},slot:"footer"},[i("Button",{attrs:{type:"primary"},on:{click:t.addField}},[t._v("添加")])],1)],1)],1)},c=[],f=(i("28a5"),i("f499")),u=i.n(f),p={name:"editTableAddField",data:function(){var t=this,e=function(e,i,a){void 0!==t.formItem.min&&void 0!==t.formItem.max&&""!==t.formItem.min&&""!==t.formItem.max?1*t.formItem.min>1*t.formItem.max?(a(new Error("最大值必须大于最小值")),t.$refs.FormRef.validateField("min")):(a(),t.$refs.FormRef.validateField("min")):a()},i=function(e,i,a){void 0!==t.formItem.min&&void 0!==t.formItem.max&&""!==t.formItem.min&&""!==t.formItem.max&&1*t.formItem.min>1*t.formItem.max?a(new Error("最小值必须小于最大值")):a()};return{tableList:[],activeList:0,formItem:{filedName:"",filedType:"1",isMust:"1"},loginRules:{stringLength:[{pattern:/^[1-9]\d*$/,message:"该字段为整数",trigger:"blur"}],filedName:[{required:!0,message:"该字段不能为空",trigger:"blur"}],max:[{pattern:/^-?[1-9]\d*$/,message:"该字段只能是整数",trigger:"blur"},{validator:e,trigger:"blur"}],min:[{pattern:/^-?[1-9]\d*$/,message:"该字段只能是整数",trigger:"blur"},{validator:i,trigger:"blur"}],dmax:[{pattern:/^-?[0-9]+([.]{1}[0-9]+){0,1}$/,message:"该字段只能是数字或小数",trigger:"blur"},{validator:e,trigger:"blur"}],dmin:[{pattern:/^-?[0-9]+([.]{1}[0-9]+){0,1}$/,message:"该字段只能是数字或小数",trigger:"blur"},{validator:i,trigger:"blur"}],decimalPoint:[{pattern:/^[1-9]\d*$/,message:"该字段为正整数",trigger:"blur"}]},addFieldModel:!1,constraint:0,valid:!1}},props:{},mounted:function(){this.delDefault()},methods:{addField:function(){var t=JSON.parse(u()(this.formItem));switch("4"===t.filedType&&(t.selects=t.singleSelect.split(/[,，;；]/),t.singleSelect=t.selects.join(",")),"5"===t.filedType&&(t.selects=t.multipleSelect.split(/[,，;；]/),t.multipleSelect=t.selects.join(",")),"6"===t.filedType&&(t.selects=t.rediaoType.split(/[,，;；]/),t.rediaoType=t.selects.join(",")),t.timeLimit){case 1:t.timeLimit="yyyy-MM-dd HH:mm:ss";break;case 2:t.timeLimit="yyyy-MM-dd";break}this.handleSubmit(),this.valid&&(this.addFieldModel=!1,this.delDefault(),this.$emit("addArr",t))},handleSubmit:function(){var t=this;this.$refs["FormRef"].validate(function(e){t.valid=e})},delAddValue:function(){var t=this.formItem.filedName,e=this.formItem.filedType;this.formItem={filedName:t,filedType:e,isMust:"1"},this.$refs.FormRef.resetFields()},delDefault:function(){this.formItem={filedName:"",filedType:"1",isMust:"1"}}}},h=p,g=(i("2dc4"),i("2877")),v=Object(g["a"])(h,m,c,!1,null,"ff1912d4",null),I=v.exports,b=i("1980"),y=i.n(b),x={name:"editTable",data:function(){return{tableList:[],addFieldModel:!1,filedDetails:!1,idArr:[],loading:!1,fieldlist:[]}},watch:{SonTableListId:function(){this.getFileds()}},props:{SonTableListId:{}},components:{EditTableAddField:I,draggable:y.a},methods:{getFileds:function(){var t=this;Object(r["a"])({data:{id:this.SonTableListId}}).then(function(e){"0000"===e.data.code&&(null===e.data.data?t.fieldlist=[]:t.fieldlist=e.data.data)})},save:function(t){var e=this,i=new d.a(function(i){Object(r["e"])({data:t}).then(function(t){"0000"===t.data.code?(i("ok"),e.loading=!1,e.$Notice.success({title:"保存成功"})):e.$Notice.error({title:t.data.msg})}).catch(function(){e.$Notice.error({title:"服务器错误"})})});return i},fieldDataChange:function(){for(var t in this.fieldlist)this.fieldlist[t].sort=1*t+1},addArr:function(t){this.fieldlist.push(t),this.fieldDataChange()},delField:function(t,e){void 0!==e&&this.idArr.push(e),this.fieldlist.splice(t,1),this.fieldDataChange()},updataFieldList:function(){var t=this;this.loading=!0;var e={delete:[],add:[],update:[]};for(var i in this.fieldlist)this.fieldlist[i].indexItemId=this.SonTableListId,void 0===this.fieldlist[i].id?e.add.push(this.fieldlist[i]):e.update.push(this.fieldlist[i]);e.delete=this.idArr,this.save(e).then(function(){t.getFileds(),t.idArr=[]})}}},_=x,T=(i("a077"),Object(g["a"])(_,n,s,!1,null,"164afce6",null)),S=T.exports,F={name:"editTable",data:function(){return{tableList:[],activeList:0,addFieldModel:!1,SonTableListId:1,item:{}}},components:{EditTableField:S},created:function(){this.getSonTableList()},mounted:function(){},methods:{getSonTableList:function(){var t=this;Object(r["b"])({data:{pageSize:20,pageNumber:1,tableName:""}}).then(function(e){"0000"===e.data.code&&(t.tableList=e.data.data.rows,t.item=t.tableList[0],t.SonTableListId=t.item.pk_id)})},updateSonTable:function(){var t=this;Object(r["f"])({data:{tableName:this.item.index_item,pkId:this.item.pk_id,remark:this.item.remark}}).then(function(e){"0000"===e.data.code?t.$Notice.success({title:"保存成功"}):t.$Notice.error({title:e.data.msg})}).catch(function(){t.$Notice.error({title:"服务器错误"})})},saveFiled:function(){this.$refs.addField.saveFiled()},tabChange:function(t,e){this.item=t,this.activeList=e,this.SonTableListId=t.pk_id}}},k=F,L=(i("672c"),Object(g["a"])(k,a,l,!1,null,"2d14d604",null));e["default"]=L.exports},c7cb:function(t,e,i){},f499:function(t,e,i){t.exports=i("a21f")}}]);
//# sourceMappingURL=chunk-0d23f4cd.97af4d40.js.map