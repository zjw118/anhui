package com.gistone.util;


/**
 * 台账辅助类
 */
public class LedgerHelp {
    public String lat;
    public String lon;

    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public static void main(String[] args) {

		/*for (LedgerHelp rfS : rhSet) {
			if(rfS.equals(rf)){
				flag=true;
				break;
			}
		}*/
		/*if(!flag){
			rhSet.add(rf);
		}*/
        String aa="39°40′15.93″";
        System.out.println(IsRulelon(aa));
        //System.out.println(checkLongude("61.325566"));
        //System.out.println(checkItude("39.371967777777776"));
        //System.out.println(IsRulelon("116°10′47.27″"));
        //System.out.println(LedgerHelp.IsRulelon("116°27′35.72″") || !LedgerHelp.checkLongude("116°27′35.72″"));
        //System.out.println(LedgerHelp.IsRulelon("116°27′35.72″"));
        //System.out.println(!LedgerHelp.checkLongude("116°27′35.72″"));


        //System.out.println(LedgerHelp.IsRulelon("107.918085") || LedgerHelp.checkLongude("107.918085"));
        //System.out.println(LedgerHelp.IsRulelon("107.918085"));
        //System.out.println(LedgerHelp.checkLongude("107.918085"));
        //System.out.println(Dms2D("116°27′35.72″"));
		/*String rpLon="116°59′38.4864″";
		if(rpLon.contains("°")){
			rpLon = LedgerHelp.Dms2D(rpLon);
		}
		String rpLat="38°59′59.208″";
		if(rpLat.contains("°")){
			rpLat = LedgerHelp.Dms2D(rpLat);
		}
		System.out.println(rpLon+"\t" +rpLat);*/

        //System.out.println(!LedgerHelp.IsRulelat("39°40′15.93″") || LedgerHelp.checkItude("39°40′15.93″"));
        //System.out.println(LedgerHelp.IsRulelat("39°40′15.93″") );
        //System.out.println(LedgerHelp.checkItude("39°40′15.93″") );
        //39.371967777777776

    }
    /**
     *  // 要求还是比较严格的：
     // 度、分、秒三个节都要有；
     // 每节的数字不能以 0 开头（比如不能写 08度，而要写 8 度）；
     // 秒的数字可以是小数，小数点后最多有两位数字；
     // 分隔三个节的标志符可以是空格、中横线、逗号、分号、°′"或者度分秒；
     // 取值范围，经度为 0度0分0秒 至 180度0分0秒；纬度为 0度0分0秒 至 90度0分0秒。

     */
    /**
     * 验证经度合法
     */
    public static boolean IsRulelon(String lon ){
        String reglo = "((\\d|[1-9]\\d|1[0-7]\\d)°(\\d|[0-5]\\d)′(\\d|[0-5]\\d)(\\.\\d{1,6})?\\″)|(180°0′0\\″)";

        if (lon.matches(reglo)){
            return true;
        }
        return false;

    }
    /**
     * 验证纬度合法
     */
    public static boolean IsRulelat(String lat ){
        String regla = "((\\d|[1-8]\\d)°(\\d|[0-5]\\d)′(\\d|[0-5]\\d)(\\.\\d{1,6})?\\″)|(90[°]0[′]0\\″)";

        if (lat.matches(regla)){
            return true;
        }
        return false;

    }

    //只校验正数 0-90.000000 0-180.000000 范围内小数点后长度任意
    public static boolean checkLongude(String longitude){
        String reglo = "((?:[0-9]|[1-9][0-9]|1[0-7][0-9])\\.([0-9]{0,}))|((?:180)\\.([0]{0,}))";

        longitude = longitude.trim();
        Boolean aa=longitude.matches(reglo)==true?longitude.matches(reglo):false;
        return aa;
    }
    /**
     * 这里需要同时支持经纬度格式
     * 经度longitude: (?:[0-9]|[1-9][0-9]|1[0-7][0-9]|180)\\.([0-9]{6})
     * 纬度latitude：  (?:[0-9]|[1-8][0-9]|90)\\.([0-9]{6})
     * @return
     */
    public static boolean checkItude(String latitude){

        String regla = "((?:[0-9]|[1-8][0-9])\\.([0-9]{0,}))|((?:90)\\.([0]{0,}))";

        latitude = latitude.trim();
        Boolean aa=latitude.matches(regla)==true?latitude.matches(regla):false;
        return aa;
    }


    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){

        try {
            double db=Double.parseDouble(String.valueOf(str));

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 经纬度转换 ，度分秒转度
     * @param
     * @author Cai_YF
     * @return
     */
    public static String Dms2D(String jwd){
        if(!ObjectUtils.isNotNullAndEmpty(jwd)&&(jwd.contains("°"))){//如果不为空并且存在度单位
            //计算前进行数据处理
            jwd = jwd.replace("E", "").replace("N", "").replace(":", "").replace("：", "");
            double d=0,m=0,s=0;
            d = Double.parseDouble(jwd.split("°")[0]);
            //不同单位的分，可扩展
            if(jwd.contains("′")){//正常的′
                m = Double.parseDouble(jwd.split("°")[1].split("′")[0]);
            }else if(jwd.contains("'")){//特殊的'
                m = Double.parseDouble(jwd.split("°")[1].split("'")[0]);
            }
            //不同单位的秒，可扩展
            if(jwd.contains("″")){//正常的″
                //有时候没有分 如：112°10.25″
                s = jwd.contains("′")?Double.parseDouble(jwd.split("′")[1].split("″")[0]):Double.parseDouble(jwd.split("°")[1].split("″")[0]);
            }else if(jwd.contains("''")){//特殊的''
                //有时候没有分 如：112°10.25''
                s = jwd.contains("'")?Double.parseDouble(jwd.split("'")[1].split("''")[0]):Double.parseDouble(jwd.split("°")[1].split("''")[0]);
            }
            jwd = String.valueOf(d+m/60+s/60/60);//计算并转换为string
            //使用BigDecimal进行加减乘除
		/*BigDecimal bd = new BigDecimal("60");
		BigDecimal d = new BigDecimal(jwd.contains("°")?jwd.split("°")[0]:"0");
		BigDecimal m = new BigDecimal(jwd.contains("′")?jwd.split("°")[1].split("′")[0]:"0");
		BigDecimal s = new BigDecimal(jwd.contains("″")?jwd.split("′")[1].split("″")[0]:"0");
		//divide相除可能会报错（无限循环小数），要设置保留小数点
		jwd = String.valueOf(d.add(m.divide(bd,6,BigDecimal.ROUND_HALF_UP)
	            .add(s.divide(bd.multiply(bd),6,BigDecimal.ROUND_HALF_UP))));*/
        }
        return jwd;
    }

    /**
     * 设置一系列的是否的设置
     * @param value
     * @return
     */
    public static Integer setValueId(String value){
        if("是".equals(value)){
            return 1;
        }else {
            return 0;
        }
    }

    /**
     * 获取整改状态值
     * @param value
     * @return
     */
    public static Integer getRepairProcess(String value){
        value=value.trim();
        if("未整改".equals(value)){
            return 0;
        }else if("整改中".equals(value)) {
            return 1;
        }else if("整改完成".equals(value)||"完成整改".equals(value)){
            return 2;
        }
        return null;
    }


}
