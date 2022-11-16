package com.boot.dubbo.mvc.excel;

import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by zengzhangqiang on 07/05/2017.
 */
public class MoneyUtil {

    private MoneyUtil() {

    }

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    /**
     * 元转成分的汇率
     */
    public static final BigDecimal HUNDRED = new BigDecimal(100);

    /**
     * 一万
     */
    public static final long TEN_THOUSAND = 10000;

    /**
     * 分转元
     *
     * @param numCent
     * @return
     */
    public static String getMoneyStr(long numCent) {
        return DECIMAL_FORMAT.format(((double) numCent) / 100);
    }

    /**
     * 元转分
     *
     * @param numYuan
     * @return
     */
    public static Long getMoneyFen(String numYuan) {

        if (StringUtils.isBlank(numYuan)) {
            return 0L;
        }
        BigDecimal bigDecimal = new BigDecimal(numYuan);
        return bigDecimal.multiply(HUNDRED).longValue();
    }

    public static BigDecimal divide(Long b1, Long b2) {
        if (null == b1 || null == b2 || b2 == 0) {
            return null;
        }
        BigDecimal bd1 = new BigDecimal(b1);
        BigDecimal bd2 = new BigDecimal(b2);
        //保留两位小数，四舍五入
        return bd1.divide(bd2, 2, RoundingMode.HALF_UP);
    }

    /**
     * @param b1           乘数1
     * @param bd2          乘数2
     * @param roundingMode 进位模式
     * @return b1*bd2
     */
    public static Long multipleWithRoundingMode(Long b1, BigDecimal bd2, RoundingMode roundingMode) {
        BigDecimal bd1 = new BigDecimal(b1);
        BigDecimal resultBd = bd1.multiply(bd2);
        return resultBd.setScale(0, roundingMode).longValue();
    }

    /**
     * @param b1
     * @param bd2
     * @param type 1:进位  2:退位 3:四舍五入
     * @return
     */
    public static Long multiple(Long b1, BigDecimal bd2, int type) {
        if (null == b1 || null == bd2) {
            return null;
        }
        BigDecimal bd1 = new BigDecimal(b1);
        BigDecimal resultBd = bd1.multiply(bd2);
        Long result = 0L;
        if (1 == type) {
            result = resultBd.setScale(0, RoundingMode.UP).longValue();
        } else if (2 == type) {
            result = resultBd.setScale(0, RoundingMode.DOWN).longValue();
        } else if (3 == type) {
            result = resultBd.setScale(0, RoundingMode.HALF_UP).longValue();
        }
        return result;
    }

    /**
     * @param multiplier
     * @param multiplicand
     * @return
     */
    public static BigDecimal multiple(@NotNull Number multiplier, @NotNull Number multiplicand, RoundingMode roundingMode) {

        BigDecimal multiplierDecimal = new BigDecimal(multiplier + "");
        BigDecimal multiplicandDecimal = new BigDecimal(multiplicand + "");
        BigDecimal result = multiplierDecimal.multiply(multiplicandDecimal);
        return result.setScale(0, roundingMode);
    }

    /**
     * @param divisor      除数
     * @param dividend     被除数
     * @param scale        保留的小数位
     * @param roundingMode 进位策略
     * @return double
     */
    public static BigDecimal divide(Number divisor, Number dividend, int scale, RoundingMode roundingMode) {

        BigDecimal divisorDecimal = new BigDecimal(divisor + "");
        BigDecimal dividendDecimal = new BigDecimal(dividend + "");
        return divisorDecimal.divide(dividendDecimal, scale, roundingMode);
    }


    /**
     * 计算资金专用
     *
     * @param divisor      除数
     * @param dividend     被除数
     * @param roundingMode 进位策略
     * @return double
     */
    public static BigDecimal divideForHighPrecision(Number divisor, Number dividend, RoundingMode roundingMode) {

        BigDecimal divisorDecimal = new BigDecimal(divisor + "");
        BigDecimal dividendDecimal = new BigDecimal(dividend + "");
        return divisorDecimal.divide(dividendDecimal, 20, roundingMode);
    }

    /**
     * 百分比转换
     */
    public static BigDecimal rateToBigDecimal(Long rate) {
        return divide(rate, 10000L, 4, RoundingMode.HALF_UP);
    }
}
