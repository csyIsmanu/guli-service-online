package com.csy.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.csy.eduService.client.UcenterClient;
import com.csy.eduService.entity.EduComment;
import com.csy.eduService.service.IEduCommentService;
import com.csy.utils.JwtUtils;
import com.csy.utils.Result;
import com.csy.vo.UcenterMemberVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author csy
 * @since 2020-09-24
 */
@RestController
@RequestMapping("/eduService/comment")
//@CrossOrigin
public class EduCommentController {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private IEduCommentService commentService;

    //根据课程id查询评论列表
    @ApiOperation(value = "评论分页列表")
    @GetMapping("getCommentPageList/{page}/{limit}/{courseId}")
    public Result getCommentPageList(@ApiParam(name = "page", value = "当前页码", required = true)
                                     @PathVariable Long page,
                                     @ApiParam(name = "limit", value = "每页记录数", required = true)
                                     @PathVariable Long limit,
                                     @ApiParam(name = "courseId", value = "课程id", required = true)
                                     @PathVariable String courseId) {
        Page<EduComment> commentPage = new Page<>(page,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        commentService.page(commentPage,wrapper);
        List<EduComment> records = commentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", commentPage.getCurrent());
        map.put("pages", commentPage.getPages());
        map.put("size", commentPage.getSize());
        map.put("total", commentPage.getTotal());
        map.put("hasNext", commentPage.hasNext());
        map.put("hasPrevious", commentPage.hasPrevious());
        return Result.ok().data(map);
    }
    @ApiOperation(value = "添加评论")
    @PostMapping("saveComment")
    public Result saveComment(@RequestBody EduComment comment, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)) {
            return Result.error().code(28004).message("请登录");
        }
        comment.setMemberId(memberId);
        UcenterMemberVo userInfo = ucenterClient.getUserInfo(memberId);
        comment.setNickname(userInfo.getNickname());
        comment.setAvatar(userInfo.getAvatar());
        commentService.save(comment);
        return Result.ok();
    }
}

