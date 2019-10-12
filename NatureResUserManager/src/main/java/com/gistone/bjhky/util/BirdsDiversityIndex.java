package com.gistone.bjhky.util;

import java.util.List;

/** 鸟类多样性指数 */
public class BirdsDiversityIndex {
	
	/**
	 * 水鸟物种多样性
	 * @param p	物种个体数占所有物种个体总数的比例的集合
	 * @return
	 */
	public static double diversity(List<Double> p){
		double h=0.0;
		for(int i=0;i<p.size();i++){
			if(p.get(i)!=0){
				h += p.get(i)*(Math.log10(p.get(i)));
			}
		}
		h = 0-h;
		return h;   
	}
	
	/**
	 * 水鸟群落均匀性
	 * @param h	鸟类多样性指数
	 * @param s	鸟类总种数
	 * @return
	 */
	public static double homogeneity(double h,int s){
		if(s!=0){
			double hmax = Math.log10(s);
			if(hmax!=0){
				double j=h/hmax;
				return j;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	/**
	 * 水鸟丰富度
	 * @param s	水鸟种类数
	 * @param n	水鸟个体总数
	 * @return
	 */
	public static double richness(int s,int n){
		double r = 0;
		if(n!=0){
			if(Math.log(n)!=0){
				r = (s-1)/Math.log(n);
			}else{
				r = 0;
			}
		}
		return r;
	}
	
	/**
	 * 水鸟相似性指数
	 * @param c	群落A和B共有物种数
	 * @param a	群落A的物种数
	 * @param b	群落B的物种数
	 * @return
	 */
	public static double comparability(int c,int a,int b){
		double s = 0;
		if((a+b)!=0){
			s = 2*c/(a+b);
		}
		return s;
	}
}
