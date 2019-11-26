package com.unis.common.utils;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * 数据拷贝工具类
 */
public class LsBeanUtils {

    //private static Logger logger = LoggerFactory.getLogger(LsBeanUtils.class);

    /**
     * @param source：源数据
     * @param clazz：目标数据
     * @describe 数据拷贝
     */
    public synchronized static <S, T> T copyProperties(S source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param source：源数据集合
     * @param target：目标数据集合
     * @param clazz：目标数据类型
     * @describe 集合内数据拷贝
     */
    public synchronized static <S, T> void copyCollection(Collection<S> source, Collection<T> target, Class<T> clazz) {
        try {
            for (Iterator<S> iterator = source.iterator(); iterator.hasNext(); ) {
                S s = (S) iterator.next();
                T t = clazz.newInstance();
                BeanUtils.copyProperties(s, t);
                target.add(t);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 数据拷贝 忽略数据源(source)中值为null的属性
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        if (source == null) {
        }
        try {
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
        } catch (Exception e) {
        }
    }

    /**
     * 数据拷贝
     *
     * @param source    源对象
     * @param target    目录对象
     */
    public static void copyAllProperties(Object source, Object target) {
        Assert.notNull(source, "源对象 不能为null");
        Assert.notNull(target, "目录对象 不能为null");
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
        }
    }

    /**
     * 获取属性为null的属性名
     *
     * @param source
     * @return
     */
    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    /**
     * 将源对象中非空属性的值替换到属性名相同的目标对象。
     *
     * @param source
     * @param target
     */
    public synchronized static void copyNoNullProperties(Object source, Object target) {
        Assert.notNull( target, "clazz must not be null" );
        try {
            Map<String, Object> map = getNoNullProperties( source );
            BeanWrapper srcBean = new BeanWrapperImpl( target );
            srcBean.setPropertyValues( map );
        } catch (Exception e) {
        }
    }

    /**
     * @param source 目标源数据
     * @return 将目标源中不为空的字段取出
     */
    public static Map <String, Object> getNoNullProperties(Object source) {
        Map <String, Object> map = new HashMap <>();
        BeanWrapper srcBean = new BeanWrapperImpl( source );
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();

        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue( p.getName() );
            if (value != null) {
                map.put( p.getName(), value );
            }
        }
        return map;
    }

    public static Map <String, Object> objectToMap(Object source) {
        Map<String, Object> map = new HashMap <>();

        BeanWrapper srcBean = new BeanWrapperImpl( source );
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();

        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue( p.getName() );
            map.put( p.getName(), value );
        }
        return map;
    }


    /**
     * Copy page t.
     *
     * @param <S>    the type parameter 源数据类型
     * @param <T>    the type parameter DTO实体类
     * @param source the source
     * @param clazz  the clazz
     * @return the t
     */
/*    public synchronized  static <S,T> Page<T> copyPage(Page<S> source, Class<T> clazz){
        List<T> list=new ArrayList<T>(  );
        if (ObjectUtils.equals( source,null ) || source.getContent().size()<=0){
            return new PageImpl <T>(list,source.getPageable(),source.getTotalPages());
        }
        List <S> content = source.getContent();

        for (S s : content) {
            T t = LsBeanUtils.copyProperties( s, clazz );
            list.add( t );
        }
        return new PageImpl<T>(list,source.getPageable(),source.getTotalPages());
    }*/
}
