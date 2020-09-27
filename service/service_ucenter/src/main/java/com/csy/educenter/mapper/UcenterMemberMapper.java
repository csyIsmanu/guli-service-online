package com.csy.educenter.mapper;

import com.csy.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2020-09-22
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegisterDay(String day);

    Integer countLoginDay(String day);
}
