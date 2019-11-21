package com.gistone.util;

public class PointHelp {
    /**
     * 度分秒转经纬度
     * @param jwd
     * @author Cai_YF
     * @return
     */
    public static String Dms2D(String jwd){
        if(ObjectUtils.isNotNullAndEmpty(jwd)&&(jwd.contains("°"))){//如果不为空并且存在度单位
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
     * 将经纬度转换为度分秒格式
     * @param pdu 116.418847
     * @return 116°25'7.85"
     */
    public static String toDfm(String pdu) {
        double du = Double.parseDouble(pdu);
        int du1 = (int) du;
        double tp = (du - du1) * 60;
        int fen = (int) tp;
        String miao = String.format("%.2f", Math.abs(((tp - fen) * 60)));
        return du1 + "°" + Math.abs(fen) + "′" + miao + "″";
    }

    public static void main(String[] args) {
        System.out.println(toDfm("111.2321"));
    }
}
