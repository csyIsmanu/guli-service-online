package com.csy.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csy.statistics.client.EduCourseClient;
import com.csy.statistics.client.UcenterClient;
import com.csy.statistics.entity.Daily;
import com.csy.statistics.mapper.DailyMapper;
import com.csy.statistics.service.IDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csy.utils.Result;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author csy
 * @since 2020-09-25
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements IDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduCourseClient eduCourseClient;
    @Override
    public void registerCount(String day) {

//        添加之前先把表中相同日期的数据删除
        QueryWrapper<Daily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
//        远程调用得到某一天注册人数
        Result result = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) result.getData().get("countRegister");

        Result result2 = ucenterClient.countLogin(day);
        Integer countLogin = (Integer) result2.getData().get("countLogin");

        Result result1 = eduCourseClient.countCourse(day);
        Integer countCourse =(Integer) result1.getData().get("countCourse");
        //将数据添加到数据库
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);//注册人数
        daily.setDateCalculated(day);//统计日期
        daily.setCourseNum(countCourse);//每日新增课程数
        daily.setLoginNum(countLogin);//每日登录人数
        daily.setVideoViewNum(RandomUtils.nextInt(100,500));//每日视频点播数
        baseMapper.insert(daily);
    }
    //图表显示，返回两部分数据，日期json数组，数量json数组
    @Override
    public Map<String, Object> getChartData(String type, String begin, String end) {
        QueryWrapper<Daily> wrapper =new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<Daily> dailies = baseMapper.selectList(wrapper);
        //因为返回有两部分数据，日期和日期对应数量
        //前端要求数组json结构
        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        for (int i = 0; i < dailies.size(); i++) {
            Daily daily = dailies.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        return map;
    }
}
