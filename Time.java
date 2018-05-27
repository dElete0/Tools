package org.haze.reportformobile.tool;

import org.haze.reportformobile.model.MasterMobileModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Time class
 *
 * @author dElete
 * @date 2018/3/22
 */
public class Time {

    /**yyyy-MM-dd型日期的加减*/
    public static String dateChange(String date,int day) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calc =Calendar.getInstance();
        calc.setTime(sdf.parse(date));
        calc.add(Calendar.DATE, day);
        Date nowDate = calc.getTime();
        return sdf.format(nowDate);
    }

    /**填充空缺*/
    public static List<MasterMobileModel> fillList(List<MasterMobileModel> list,MasterMobileModel period) throws Exception {
        String begin = period.getBegindate();
        String end = period.getEnddate();
        List<String> lDate = findDates(begin, end);
        List<MasterMobileModel> newList = new ArrayList<MasterMobileModel>();
        int i = 0;
        for (String date : lDate) {
            if (i < list.size()){
                if (date.equals(list.get(i).getReportdate())) {
                    newList.add(list.get(i));
                    i++;
                } else {
                    MasterMobileModel newModel = new MasterMobileModel();
                    newModel.setReportdate(date);
                    newList.add(newModel);
                }
            }
        }
        return newList;
    }
    private static List<String> findDates(String beginDate, String endDate) throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = sdf.parse(beginDate);
        Date end = sdf.parse(endDate);
        List<String> result = new ArrayList<String>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(begin);
        while(begin.getTime()<end.getTime()){
            result.add(sdf.format(tempStart.getTime()));
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            begin = tempStart.getTime();
        }
        return result;
    }

    /**合并两段时间*/
    public static List<MasterMobileModel> plusContrast(List<MasterMobileModel> list1,
                                                       List<MasterMobileModel> list2) throws Exception {
        List<MasterMobileModel> newList = new ArrayList<MasterMobileModel>();
        if (list1.size()>list2.size()) {
           int i = 0;
           while (i < list2.size()) {
               MasterMobileModel m = new MasterMobileModel();
               m.setReportdate(list2.get(i).getReportdate());
               m.setTyday(list2.get(i).getTyday());
               m.setContrastvalue(list1.get(i).getTyday());
               newList.add(m);
               i++;
           }
           while (i < list1.size()) {
               MasterMobileModel m = new MasterMobileModel();
               m.setReportdate(list1.get(i).getReportdate());
               m.setContrastvalue(list1.get(i).getTyday());
               i++;
               newList.add(m);
           }
        } else {
            int i = 0;
            while (i < list1.size()) {
                MasterMobileModel m = new MasterMobileModel();
                m.setReportdate(list2.get(i).getReportdate());
                m.setTyday(list2.get(i).getTyday());
                m.setContrastvalue(list1.get(i).getTyday());
                i++;
                newList.add(m);
            }
            while (i < list2.size()) {
                MasterMobileModel m = new MasterMobileModel();
                m.setReportdate(list2.get(i).getReportdate());
                m.setContrastvalue(list2.get(i).getTyday());
                newList.add(m);
                i++;
            }
        }
        return newList;
    }

    /**某周第一天*/
    public static String firstWeek(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setFirstDayOfWeek(Calendar.MONDAY);
        cDate.setTime(sdf.parse(date));
        Calendar firstDate = Calendar.getInstance();
        firstDate.setFirstDayOfWeek(Calendar.MONDAY);
        firstDate.setTime(sdf.parse(date));
        if(cDate.get(Calendar.WEEK_OF_YEAR)==1&&cDate.get(Calendar.MONTH)==11){
            firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR)+1);
        }
        int typeNum  = cDate.get(Calendar.WEEK_OF_YEAR);
        firstDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        firstDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return sdf.format(firstDate.getTime());
    }

    /**某月第一天*/
    public static String firstMouth(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**上个月第一天*/
    public static String frontMouth(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,-1);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**下个月第一天*/
    public static String lastMouth(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,1);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**某季度第一天*/
    public static String firstQuarter(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        int mouth = getQuarterInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**上个季度第一天*/
    public static String frontQuarter(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,-3);
        int mouth = getQuarterInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**下个季度第一天*/
    public static String lastQuarter(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,3);
        int mouth = getQuarterInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**此半年第一天*/
    public static String firstHarf(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        int mouth = getHarfInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**下个半年第一天*/
    public static String lastHarf(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,6);
        int mouth = getHarfInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**上个半年第一天*/
    public static String frontHarf(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.MONTH,-6);
        int mouth = getHarfInMonth(cDate.get(Calendar.MONTH),true);
        cDate.set(Calendar.MONTH,mouth);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**今年第一天*/
    public static String firstYear(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.set(Calendar.MONTH,0);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**去年第一天*/
    public static String frontYear(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.YEAR,-1);
        cDate.set(Calendar.MONTH,0);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**明年第一天*/
    public static String lastYear(String date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cDate = Calendar.getInstance();
        cDate.setTime(sdf.parse(date));
        cDate.add(Calendar.YEAR,1);
        cDate.set(Calendar.MONTH,0);
        cDate.set(Calendar.DAY_OF_MONTH,1);
        return sdf.format(cDate.getTime());
    }

    /**获取所在季度*/
    private static int getQuarterInMonth(int month, boolean isQuarterStart) {
        int months[] = { 0, 3, 6, 9};
        if (!isQuarterStart) {
            months = new int[] { 2, 5, 8, 11};
        }
        if (month >= 0 && month <= 2) {
            return months[0];
        } else if (month >= 3 && month <= 5) {
            return months[1];
        }else if (month >= 6 && month <= 8) {
            return months[2];
        }else {
            return months[3];
        }
    }

    /**获取所在半年*/
    private static int getHarfInMonth(int month, boolean isHarfStart) {
        int months[] = {0, 6};
        if (!isHarfStart) {
            months = new int[] {5, 11};
        }
        if (month >= 0 && month <= 5) {
            return months[0];
        } else {
            return months[1];
        }
    }
}
