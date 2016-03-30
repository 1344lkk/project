package com.hzcwtech.wuzhong.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @ClassName MathUtil 
 * @Description 描述
 * @author ieastar
 * @date 2014-11-11
 * @version V1.0
 */
public class MathUtil {
	/**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static double round(double value, int scale, 
             int roundingMode) {   
        BigDecimal bd = new BigDecimal(value);   
        bd = bd.setScale(scale, roundingMode);   
        double d = bd.doubleValue();   
        bd = null;   
        return d;   
    }
    
    /**  
     * 对double数据进行取精度.  
     * @param value  double数据.  
     * @param scale  精度位数(保留的小数位数).  
     * @param roundingMode  精度取值方式.  
     * @return 精度计算后的数据.  
     */  
    public static String round(String value, int scale, 
             int roundingMode) {
    	if(StringUtil.isTrimBlank(value)){
    		return "0";
    	}
        BigDecimal bd = new BigDecimal(value);   
        bd = bd.setScale(scale, roundingMode);   
        String d = bd.toString();   
        bd = null;   
        return d;   
    }
    
    public static String formatter(double val,String parten){
    	String d = "0";
    	if(val==0){
    		return d;
    	}
    	if(StringUtil.isTrimBlank(parten)){
    		parten = "0.00";
    	}
    	DecimalFormat df = new DecimalFormat(parten);
    	d = df.format(val);
    	return d;
    }

    /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static String sumAndFormat(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return formatter(bd1.add(bd2).doubleValue(),""); 
    }
    
     /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.add(bd2).doubleValue(); 
    }
    
    /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sum(String d1,String d2,String d3){ 
        BigDecimal bd1 = new BigDecimal(d1); 
        BigDecimal bd2 = new BigDecimal(d2);
        BigDecimal bd3 = new BigDecimal(d3);
        return Double.parseDouble(formatter(bd3.add(bd1.add(bd2)).doubleValue(),"")); 
    } 
    
    
    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static String subAndFormat(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return formatter(bd1.subtract(bd2).doubleValue(),""); 
    }


    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 
    
    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double sub(String d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(d1); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static double mul(double d1,double d2){ 
        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.multiply(bd2).doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数 
     * @return 
     */ 
    public static double div(double d1,double d2,int scale){ 
        //  当然在此之前，你要判断分母是否为0，   
        //  为0你可以根据实际需求做相应的处理 

        BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
        BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
        return bd1.divide 
               (bd2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    } 
    
    public static double max(double d1,double d2,double d3){
    	double max = d1;
    	if(d2>max){
    		max = d2;
    	}
    	if(d3>max){
    		max = d3;
    	}
    	return max;
    }
}
