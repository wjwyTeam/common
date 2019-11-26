package com.unis.common.utils;

import io.swagger.annotations.ApiModel;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Table;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntityUtil {

	/**
	 * 读取<@Table>注解获取表名
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class<?> clazz){
		return clazz.getAnnotation(Table.class).name();
	}

	/**
	 * 在<主表id串>后拼接根据 <子表数据>查询出的后缀
	 * 如主表数据<CBL007>传入子表数据<CBL007A003>返回串拼接后<CBL007A004>
	 * 如传入<CBL>返回串为<CBL00000001>
	 * @param childCode  子级的id
	 * @param i  当前循环的索引号
	 * @return
	 */
	public static String getIdByChildId(String childCode,int i){
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotEmpty(childCode)){
			String max=getChildMaxIndex(childCode);
			if(StringUtils.isNotEmpty(max)){
				int maxLen=max.length();
				sb.append(childCode.substring(0,childCode.length()-maxLen));
				sb.append(String.format("%0"+(maxLen)+"d",Integer.valueOf(max)+i+1));
			}else{
				sb.append(String.format("%08d",i+1));
			}
		}
		return sb.toString();
	}

	/**
	 * 在<主表id串>后拼接后缀
	 * 如主表数据<CBL007>返回拼接后串<CBL007A001>
	 * 如传入<CBL>返回串为<CBLA001>
	 * @param parentCode
	 * @param i
	 * @return
	 */
	public static String getIdByParentId(Class<?> clazz,String parentCode,int i){
		return getIdByParentId(clazz,parentCode,i,3);
	}

	/**
	 * 在<主表id串>后拼接后缀
	 * 如主表数据<CBL007>返回拼接后串<CBL007A001>
	 * 如传入<CBL>返回串为<CBLA001>
	 * @param clazz  子数据的类对象
	 * @param parentCode 父级的id
	 * @param i	  当前循环的索引号
	 * @return
	 */
	public static String getIdByParentId(Class<?> clazz,String parentCode,int i,int length){
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotEmpty(parentCode)){
			sb.append(parentCode);
		}
		sb.append(getChildTableRemark(clazz));
		sb.append(String.format("%0"+length+"d",i+1));
		return sb.toString();
	}

	/**
	 * 读取<@ApiModel>注解的<description>获取子表的身份标示
	 * @param clazz  子数据的类对象
	 * @return
	 */
	public static String getChildTableRemark(Class<?> clazz){
		ApiModel annotation= clazz.getAnnotation(ApiModel.class);
		if(annotation!=null){
			return  annotation.description();
		}else{
			return "";
		}
	}

	/**
	 * 获取子表数据顺序号最大值
	 * @param code 子级的id
	 * @return
	 */
	public static String getChildMaxIndex(String code){
		String find=null;
		if(StringUtils.isNotEmpty(code)){
			Pattern pattern=Pattern.compile("^[\\s\\S]+?(\\d+)$");
			Matcher matcher= pattern.matcher(code);
			while(matcher.find()){
				find=matcher.group(1);
				break;
			}
		}
		return find;
	}
}
