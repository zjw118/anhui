(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0681eaaf"],{"0bfb":function(e,t,n){"use strict";var r=n("cb7c");e.exports=function(){var e=r(this),t="";return e.global&&(t+="g"),e.ignoreCase&&(t+="i"),e.multiline&&(t+="m"),e.unicode&&(t+="u"),e.sticky&&(t+="y"),t}},3846:function(e,t,n){n("9e1e")&&"g"!=/./g.flags&&n("86cc").f(RegExp.prototype,"flags",{configurable:!0,get:n("0bfb")})},"39f3":function(e,t,n){e.exports=n.p+"img/Basemapthumbnail1.5f8d6db2.png"},"42e8":function(e,t,n){e.exports=n.p+"img/Basemapthumbnail.6dc37597.png"},"49ae":function(e,t,n){"use strict";var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"textTerm"},[n("span",{staticStyle:{"min-width":"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[e._v(e._s(e.titName)+"：")]),n("Input",{staticStyle:{width:"200px"},attrs:{size:"large",placeholder:"请输入"+e.titName},on:{"on-change":function(t){return e.srldChange(e.UnitName)}},model:{value:e.UnitName,callback:function(t){e.UnitName="string"===typeof t?t.trim():t},expression:"UnitName"}})],1)},a=[],i={name:"TextTerm",data:function(){return{UnitName:""}},props:{titName:{},titKey:{}},mounted:function(){},methods:{srldChange:function(){if(null===this.UnitName)this.$emit("delQueryTrem",this.titKey);else{var e={name:this.titName,text:this.UnitName,key:this.titKey,value:this.UnitName};this.$emit("updataQueryData",e)}}}},u=i,s=(n("eb80"),n("2877")),c=Object(s["a"])(u,r,a,!1,null,"16da56a2",null);t["a"]=c.exports},"65d4":function(e,t,n){"use strict";n.d(t,"b",function(){return a}),n.d(t,"g",function(){return i}),n.d(t,"c",function(){return u}),n.d(t,"d",function(){return s}),n.d(t,"a",function(){return c}),n.d(t,"h",function(){return o}),n.d(t,"f",function(){return l});var r=n("b775"),a=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/getSonTableList",method:"post",data:e})},i=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/saveFiled",method:"post",data:e})},u=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/getSurveyTableList",method:"post",data:e})},s=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/getTableInfo",method:"post",data:e})},c=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/getFileds",method:"post",data:e})},o=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/updateSonTable",method:"post",data:e})},l=function(e){return Object(r["a"])({url:"/dcxx/dicIndexItem/save",method:"post",data:e})}},"6b54":function(e,t,n){"use strict";n("3846");var r=n("cb7c"),a=n("0bfb"),i=n("9e1e"),u="toString",s=/./[u],c=function(e){n("2aba")(RegExp.prototype,u,e,!0)};n("79e5")(function(){return"/a/b"!=s.call({source:"a",flags:"b"})})?c(function(){var e=r(this);return"/".concat(e.source,"/","flags"in e?e.flags:!i&&e instanceof RegExp?a.call(e):void 0)}):s.name!=u&&c(function(){return s.call(this)})},7187:function(e,t,n){},"72ba":function(e,t,n){"use strict";var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"textTerm"},[n("span",{staticStyle:{"min-width":"100px",display:"inline-block","text-align":"right","padding-right":"10px"}},[e._v(e._s(e.titName)+"：")]),n("Select",{staticStyle:{width:"200px"},attrs:{size:"large",placeholder:"请输入"+e.titName},on:{"on-change":function(t){return e.srldChange(e.UnitName)}},model:{value:e.UnitName,callback:function(t){e.UnitName="string"===typeof t?t.trim():t},expression:"UnitName"}},e._l(e.typeList,function(t){return n("Option",{key:t.value,attrs:{value:t.value}},[e._v(e._s(t.label))])}),1)],1)},a=[],i={name:"TextTerm",data:function(){return{UnitName:"",typeList:[{value:"",label:"请选择"},{value:"1",label:"type1"},{value:"2",label:"type2"}]}},props:{titName:{},titKey:{}},mounted:function(){},methods:{srldChange:function(){if(null===this.UnitName||""===this.UnitName||void 0===this.UnitName)this.$emit("delQueryTrem",this.titKey);else{var e={name:this.titName,text:this.UnitName,key:this.titKey,value:this.UnitName};this.$emit("updataQueryData",e)}}}},u=i,s=(n("971c"),n("2877")),c=Object(s["a"])(u,r,a,!1,null,"84e0d5e4",null);t["a"]=c.exports},"7f7f":function(e,t,n){var r=n("86cc").f,a=Function.prototype,i=/^\s*function ([^ (]*)/,u="name";u in a||n("9e1e")&&r(a,u,{configurable:!0,get:function(){try{return(""+this).match(i)[1]}catch(e){return""}}})},"96f3":function(e,t,n){},"971c":function(e,t,n){"use strict";var r=n("7187"),a=n.n(r);a.a},b775:function(e,t,n){"use strict";var r=n("795b"),a=n.n(r),i=n("bc3a"),u=n.n(i),s=(n("c0d6"),n("e069"),n("5f87")),c=n("a78e"),o=n.n(c),l=u.a.create({baseURL:"/epr/api/",timeout:5e5});l.interceptors.request.use(function(e){return e.headers["token"]=Object(s["a"])(),e.headers["uuid"]=o.a.get("uuid"),e},function(e){console.log(e),a.a.reject(e)}),l.interceptors.response.use(function(e){var t=e.data;return"2111"===t.code&&(Object(s["b"])("token"),location.reload()),e},function(e){return console.log("err"+e),a.a.reject(e)}),t["a"]=l},eb80:function(e,t,n){"use strict";var r=n("96f3"),a=n.n(r);a.a},f831:function(e,t,n){"use strict";n.d(t,"g",function(){return u}),n.d(t,"h",function(){return s}),n.d(t,"f",function(){return c}),n.d(t,"e",function(){return o}),n.d(t,"a",function(){return l}),n.d(t,"c",function(){return d}),n.d(t,"d",function(){return p}),n.d(t,"j",function(){return f}),n.d(t,"i",function(){return h}),n.d(t,"b",function(){return m});n("6b54"),n("96cf");var r=n("3b8d"),a=n("9ed9"),i=n("149e"),u=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(){var t,n,r,i;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].BaseTileLayer;case 2:return t=e.sent,e.next=5,a["a"].Request;case 5:return n=e.sent,e.next=8,t.createSubclass({properties:{urlTemplate:null},getTileUrl:function(e,t,n){var r="http://t"+n%8+".tianditu.gov.cn/DataServer?T=img_w&tk=12b886b3d0f324bd6032c29503972e7c&x="+n+"&y="+t+"&l="+e;return r},fetchTile:function(e,t,r){var a=this,i=this.getTileUrl(e,t,r);return n(i,{responseType:"image"}).then(function(e){var t=e.data,n=a.tileInfo.size[0],r=a.tileInfo.size[0],i=document.createElement("canvas"),u=i.getContext("2d");return i.width=n,i.height=r,u.drawImage(t,0,0,n,r),i})}});case 8:return r=e.sent,e.next=11,new r;case 11:return i=e.sent,e.abrupt("return",i);case 13:case"end":return e.stop()}},e,this)}));return function(){return e.apply(this,arguments)}}(),s=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(){var t,n,r,i;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].BaseTileLayer;case 2:return t=e.sent,e.next=5,a["a"].Request;case 5:return n=e.sent,e.next=8,t.createSubclass({properties:{urlTemplate:null},getTileUrl:function(e,t,n){var r="http://t"+n%8+".tianditu.gov.cn/DataServer?T=cia_w&tk=12b886b3d0f324bd6032c29503972e7c&x="+n+"&y="+t+"&l="+e;return r},fetchTile:function(e,t,r){var a=this,i=this.getTileUrl(e,t,r);return n(i,{responseType:"image"}).then(function(e){var t=e.data,n=a.tileInfo.size[0],r=a.tileInfo.size[0],i=document.createElement("canvas"),u=i.getContext("2d");return i.width=n,i.height=r,u.drawImage(t,0,0,n,r),i})}});case 8:return r=e.sent,i=new r,e.abrupt("return",i);case 11:case"end":return e.stop()}},e,this)}));return function(){return e.apply(this,arguments)}}(),c=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(){var t,n,r,i;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].BaseTileLayer;case 2:return t=e.sent,e.next=5,a["a"].Request;case 5:return n=e.sent,e.next=8,t.createSubclass({properties:{urlTemplate:null},getTileUrl:function(e,t,n){var r="http://t"+n%8+".tianditu.gov.cn/DataServer?T=vec_w&tk=12b886b3d0f324bd6032c29503972e7c&x="+n+"&y="+t+"&l="+e;return r},fetchTile:function(e,t,r){var a=this,i=this.getTileUrl(e,t,r);return n(i,{responseType:"image"}).then(function(e){var t=e.data,n=a.tileInfo.size[0],r=a.tileInfo.size[0],i=document.createElement("canvas"),u=i.getContext("2d");return i.width=n,i.height=r,u.drawImage(t,0,0,n,r),i})}});case 8:return r=e.sent,i=new r,e.abrupt("return",i);case 11:case"end":return e.stop()}},e,this)}));return function(){return e.apply(this,arguments)}}(),o=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(){var t,n,r,i;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].BaseTileLayer;case 2:return t=e.sent,e.next=5,a["a"].Request;case 5:return n=e.sent,e.next=8,t.createSubclass({properties:{urlTemplate:null},getTileUrl:function(e,t,n){var r="http://t"+n%8+".tianditu.gov.cn/DataServer?T=cva_w&tk=12b886b3d0f324bd6032c29503972e7c&x="+n+"&y="+t+"&l="+e;return r},fetchTile:function(e,t,r){var a=this,i=this.getTileUrl(e,t,r);return n(i,{responseType:"image"}).then(function(e){var t=e.data,n=a.tileInfo.size[0],r=a.tileInfo.size[0],i=document.createElement("canvas"),u=i.getContext("2d");return i.width=n,i.height=r,u.drawImage(t,0,0,n,r),i})}});case 8:return r=e.sent,i=new r,e.abrupt("return",i);case 11:case"end":return e.stop()}},e,this)}));return function(){return e.apply(this,arguments)}}(),l=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r,u,s;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].FeatureLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"红线斑块",title:"红线斑块",url:i["a"].serverUrl+"/arcgis/rest/services/qhhx/hongxian/MapServer",outFields:["*"],opacity:.7,popupTemplate:{title:"{name}",expressionInfos:[{name:"redLineType",title:"红线类型",expression:"if ($feature.type == \"01\") {\n                        return '生态功能重要区'\n                }\n                else if($feature.type == \"02\") {\n                        return '生态环境敏感区'\n                }"},{name:"redLineAttribute",title:"功能属性",expression:"if ($feature.attribute == \"01\") {\n                        return '水土流失'\n                }\n                else if($feature.attribute == \"02\") {\n                        return '土地沙化'\n                }\n                else if($feature.attribute == \"02\") {\n                        return '石漠化'\n                }\n                else if($feature.attribute == \"02\") {\n                        return '盐渍化'\n                }\n                else if($feature.attribute == \"02\") {\n                        return '其他敏感性'\n                }"}],content:[{type:"fields",fieldInfos:[{fieldName:"Id",label:"红线Id"},{fieldName:"行政区",label:"行政区划名称"},{fieldName:"pac",label:"行政区划编码"},{fieldName:"hxcode",label:"红线编码"},{fieldName:"面积",label:"斑块面积/m²",format:{digitSeparator:!0,places:2}},{fieldName:"expression/redLineType"},{fieldName:"expression/redLineAttribute"}]}]},legend:{type:0,backgroundColor:"rgba(255, 255, 255, 0)",border:"2px solid red"}}),r.renderer={type:"simple",symbol:{type:"simple-fill",size:6,color:[0,0,0,.01],outline:{width:.8,color:[255,0,0,.8]}},label:"红线斑块"},u="$feature.hxcode",s={labelExpressionInfo:{expression:u},symbol:{type:"text",color:"black",haloSize:1,haloColor:"white"}},r.labelingInfo=[s],e.abrupt("return",r);case 9:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}(),d=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].FeatureLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"省界",url:i["a"].serverUrl+"/arcgis/rest/services/qhhx/shengjie/MapServer",legend:{type:2,backgroundColor:"rgba(200, 10, 100, 1)",weight:3}}),e.abrupt("return",r);case 5:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}(),p=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].FeatureLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"市界",url:i["a"].serverUrl+"/arcgis/rest/services/qhhx/shijie/MapServer",legend:{type:2,backgroundColor:"rgba(200, 10, 100, 1)",weight:3}}),r.renderer={type:"simple",symbol:{type:"simple-fill",size:6,color:[0,0,0,.01],outline:{width:.8,color:[255,0,0,.8]}}},e.abrupt("return",r);case 6:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}(),f=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].FeatureLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"县界",url:i["a"].serverUrl+"/arcgis/rest/services/qhhx/xianjie/MapServer",legend:{type:2,backgroundColor:"rgba(200, 10, 100, 1)",weight:3}}),e.abrupt("return",r);case 5:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}(),h=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].MapImageLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"省界",url:"https://120.24.69.160:6443/arcgis/rest/services/qhhx/vulnerability/MapServer"}),e.abrupt("return",r);case 5:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}(),m=function(){var e=Object(r["a"])(regeneratorRuntime.mark(function e(t){var n,r;return regeneratorRuntime.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,a["a"].MapImageLayer;case 2:return n=e.sent,r=new n({id:void 0===t?0:t.toString(),name:"省界",url:"https://120.24.69.160:6443/arcgis/rest/services/qhhx/sensitivity/MapServer"}),e.abrupt("return",r);case 5:case"end":return e.stop()}},e,this)}));return function(t){return e.apply(this,arguments)}}()}}]);
//# sourceMappingURL=chunk-0681eaaf.cc5095da.js.map