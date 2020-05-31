package com.zoupanpan.www.base;

import com.zoupanpan.www.base.bean.ResultBean;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.validator.HibernateValidator;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author zoupanpan
 * @version 2020/5/17 17:39
 */
public class ValidatorUtils {

    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validatorWithOutFailFast = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(false).buildValidatorFactory().getValidator();

    private static Validator validatorWithFailFast = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(true).buildValidatorFactory().getValidator();

    /**
     * 功能描述: <br> 〈注解验证参数〉
     *
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static <T> ResultBean validate(T obj, Class<?>... group) {
        return getResultBean(obj, group, validatorWithOutFailFast);

    }

    public static <T> ResultBean validateFastFail(T obj, Class<?>... group) {
        return getResultBean(obj, group, validatorWithFailFast);

    }

    private static <T> ResultBean getResultBean(T obj, Class<?>[] group, Validator validator) {
        ResultBean resultBean = ResultBean.SUCCESS();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, group);

        if (CollectionUtils.isEmpty(constraintViolations)) {
            return resultBean;
        }
        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            sb.append(iterator.next().getMessage()).append(";");
        }
        resultBean.setCode(-1);
        resultBean.setMsg(sb.toString());
        System.out.println(sb.toString());
        return resultBean;
    }


    public interface UpdateGroup {

    }

    public interface AddGroup {

    }
}
