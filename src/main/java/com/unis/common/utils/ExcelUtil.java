package com.unis.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 将对象数组转换成excel
	 * 
	 * @param pojoList
	 *            对象数组
	 * @param out
	 *            输出流
	 * @param alias
	 *            指定对象属性别名，生成列名和列顺序Map<"类属性名","列名">
	 * @param headLine
	 *            表标题
	 * @throws Exception
	 */
	public static <T> void pojo2Excel(List<T> pojoList, OutputStream out, LinkedHashMap<String, String> alias,
			String headLine) throws Exception {
		// 创建一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建一个表
		HSSFSheet sheet = wb.createSheet();
		// 创建第一行，作为表名
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue(headLine);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

		// 在第一行插入列名
		insertColumnName(1, sheet, alias);

		// *从第2行开始插入数据
		insertColumnDate(2, pojoList, sheet, alias);

		// 输出表格文件
		try {
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			wb.close();
		}
	}

	/**
	 * 将对象数组转换成excel
	 * 
	 * @param pojoList
	 *            对象数组
	 * @param out
	 *            输出流
	 * @param alias
	 *            指定对象属性别名，生成列名和列顺序
	 * @throws Exception
	 */
	public static <T> void pojo2Excel(List<T> pojoList, OutputStream out, LinkedHashMap<String, String> alias)
			throws Exception {
		// 获取类名作为标题
		String headLine = "";
		if (pojoList.size() > 0) {
			Object pojo = pojoList.get(0);
			Class<? extends Object> claz = pojo.getClass();
			headLine = claz.getName();
			pojo2Excel(pojoList, out, alias, headLine);
		}
	}

	/**
	 * 将对象数组转换成excel,列名为对象属性名
	 * 
	 * @param pojoList
	 *            对象数组
	 * @param out
	 *            输出流
	 * @param headLine
	 *            表标题
	 * @throws Exception
	 */
	public static <T> void pojo2Excel(List<T> pojoList, OutputStream out, String headLine) throws Exception {
		// 获取类的属性作为列名
		LinkedHashMap<String, String> alias = new LinkedHashMap<String, String>();
		if (pojoList.size() > 0) {
			Object pojo = pojoList.get(0);
			Field[] fields = pojo.getClass().getDeclaredFields();
			String[] name = new String[fields.length];
			Field.setAccessible(fields, true);
			for (int i = 0; i < name.length; i++) {
				name[i] = fields[i].getName();
				alias.put(isNull(name[i]).toString(), isNull(name[i]).toString());
			}
			pojo2Excel(pojoList, out, alias, headLine);
		}
	}

	/**
	 * 将对象数组转换成excel，列名默认为对象属性名，标题为类名
	 * 
	 * @param pojoList
	 *            对象数组
	 * @param out
	 *            输出流
	 * @throws Exception
	 */
	public static <T> void pojo2Excel(List<T> pojoList, OutputStream out) throws Exception {
		// 获取类的属性作为列名
		LinkedHashMap<String, String> alias = new LinkedHashMap<String, String>();
		// 获取类名作为标题
		String headLine = "";
		if (pojoList.size() > 0) {
			Object pojo = pojoList.get(0);
			Class<? extends Object> claz = pojo.getClass();
			headLine = claz.getName();
			Field[] fields = claz.getDeclaredFields();
			String[] name = new String[fields.length];
			Field.setAccessible(fields, true);
			for (int i = 0; i < name.length; i++) {
				name[i] = fields[i].getName();
				alias.put(isNull(name[i]).toString(), isNull(name[i]).toString());
			}
			pojo2Excel(pojoList, out, alias, headLine);
		}
	}

	/**
	 * 此方法作用是创建表头的列名
	 * 
	 * @param alias
	 *            要创建的表的列名与实体类的属性名的映射集合
	 * @param sheet
	 *            指定行创建列名
	 * @return
	 */
	private static void insertColumnName(int rowNum, HSSFSheet sheet, Map<String, String> alias) {
		HSSFRow row = sheet.createRow(rowNum);
		// 列的数量
		int columnCount = 0;

		Set<Entry<String, String>> entrySet = alias.entrySet();

		for (Entry<String, String> entry : entrySet) {
			// 创建第一行的第columnCount个格子
			HSSFCell cell = row.createCell(columnCount++);
			// 将此格子的值设置为alias中的键名
			cell.setCellValue(isNull(entry.getValue()).toString());
		}
	}

	/**
	 * 从指定行开始插入数据
	 * 
	 * @param beginRowNum
	 *            开始行
	 * @param models
	 *            对象数组
	 * @param sheet
	 *            表
	 * @param alias
	 *            列别名
	 * @throws Exception
	 */
	private static <T> void insertColumnDate(int beginRowNum, List<T> models, HSSFSheet sheet,
			Map<String, String> alias) throws Exception {
		for (T model : models) {
			// 创建新的一行
			HSSFRow rowTemp = sheet.createRow(beginRowNum++);
			logger.info("创建了第:{}行", beginRowNum);

			// 获取列的迭代
			Set<Entry<String, String>> entrySet = alias.entrySet();

			// 从第0个格子开始创建
			int columnNum = 0;
			for (Entry<String, String> entry : entrySet) {
				// 获取属性值
				String property = BeanUtils.getProperty(model, entry.getKey());
				// 创建一个格子
				HSSFCell cell = rowTemp.createCell(columnNum++);
				cell.setCellValue(property);
			}
		}
	}

	// 判断是否为空，若为空设为""
	private static Object isNull(Object object) {
		if (object != null) {
			return object;
		} else {
			return "";
		}
	}

	/**
	 * 将excel表转换成指定类型的对象数组
	 * 
	 * @param inputStream
	 *            类型
	 * @param alias
	 *            列别名,格式要求：Map<"列名","类属性名">
	 * @return
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> List<Map<String, Object>> excel2Pojo(InputStream inputStream,
			LinkedHashMap<String, String> alias) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook(inputStream);
//		try {
			XSSFSheet sheet = wb.getSheetAt(0);

			// 生成属性和列对应关系的map，Map<类属性名，对应一行的第几列>
			Map<String, Integer> propertyMap = generateColumnPropertyMap(sheet, alias);
			// 根据指定的映射关系进行转换
			List<Map<String, Object>> pojoList = generateList(sheet, propertyMap);
			wb.close();
			return pojoList;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			wb.close();
//		}
	}

	/**
	 * 生成一个属性-列的对应关系的map
	 * 
	 * @param sheet
	 *            表
	 * @param alias
	 *            别名
	 * @return
	 */
	private static Map<String, Integer> generateColumnPropertyMap(XSSFSheet sheet,
			LinkedHashMap<String, String> alias) throws Exception {
		Map<String, Integer> propertyMap = new HashMap<>();

		XSSFRow propertyRow = sheet.getRow(0);
		short firstCellNum = propertyRow.getFirstCellNum();
		short lastCellNum = propertyRow.getLastCellNum();

		for (int i = firstCellNum; i < lastCellNum; i++) {
			Cell cell = propertyRow.getCell(i);
			if (cell == null) {
				continue;
			}
			// 列名
			String cellValue = cell.getStringCellValue();
			String propertyName = alias.get(cellValue);
			alias.put(cellValue, "aaa");
			
			// 对应属性名
			propertyMap.put(propertyName, i);
		}
//		for(Entry<String, String> key : alias.entrySet()) {
//			if(key == null || !"aaa".equals(key.getValue())) {
//				throw new RuntimeException("数据模板有误，与下载的模板不一致。");
//			}
//		}
		return propertyMap;
	}

	/**
	 * 根据指定关系将表数据转换成对象数组
	 * 
	 * @param sheet
	 *            表
	 * @param propertyMap
	 *            属性映射关系Map<"属性名",一行第几列>
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static <T> List<Map<String, Object>> generateList(XSSFSheet sheet, Map<String, Integer> propertyMap) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		// 对象数组
		// List<T> pojoList=new ArrayList<>();
		List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();
		for (Row row : sheet) {
			// 跳过第一行的title
			if (row.getRowNum() < 1) {
				continue;
			}
			// T instance = claz.newInstance();
			Set<Entry<String, Integer>> entrySet = propertyMap.entrySet();
			Map<String, Object> m = new HashMap<String, Object>();
			for (Entry<String, Integer> entry : entrySet) {
				// 获取此行指定列的值,即为属性对应的值
				Integer val = entry.getValue();
				Cell property = row.getCell(val);
				if (property == null) {
					continue;
				}
				// String property1 = property.getStringCellValue();
				// String property2 = property1.toString();
				// BeanUtils.setProperty(instance, entry.getKey(),
				// property.getStringCellValue());
				if(getCellValue(property) == null) {
					throw new RuntimeException("传数据模板有误，与下载的模板不一致。");
				}
				m.put(entry.getKey(), getCellValue(property));
			}
			ls.add(m);
		}
		return ls;
	}

	/**
	 * 将excel表转换成指定类型的对象数组，列名即作为对象属性
	 * 
	 * @param claz
	 *            类型
	 * @return
	 * @throws Exception 
	 */
	public static <T> List<Map<String, Object>> excel2Pojo(InputStream inputStream, Class<T> claz)
			throws Exception {
		LinkedHashMap<String, String> alias = new LinkedHashMap<String, String>();
		Field[] fields = claz.getDeclaredFields();
		for (Field field : fields) {
			alias.put(field.getName(), field.getName());
		}
		List<Map<String, Object>> pojoList = excel2Pojo(inputStream, alias);
		return pojoList;
	}
	
	/**
	 * 
	 * @Description: 对表格中数值进行格式化
	 *
	 * @date 2018年12月25日,下午3:46:06
	 * @author xmpu
	 * @version v1.0
	 *
	 * @param cell
	 * @return
	 */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        DecimalFormat df = new DecimalFormat(); // 格式化number String字符
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
//        DecimalFormat df2 = new DecimalFormat("0"); // 格式化数字

        switch (cell.getCellType()) {
        case STRING:
            value = cell.getRichStringCellValue().getString();
            break;
        case NUMERIC:
            if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                value = df.format(cell.getNumericCellValue());
//				value = cell.getNumericCellValue();
            } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                value = sdf.format(cell.getDateCellValue());
            } else {
                value = df.format(cell.getNumericCellValue());
//				value = cell.getNumericCellValue();
            }
            break;
        case BOOLEAN:
            value = cell.getBooleanCellValue();
            break;
        case BLANK:
            value = "";
            break;
        default:
            break;
        }
        return value;
    }
}
