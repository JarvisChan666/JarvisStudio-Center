package com.jarvis.usercenterbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jarvis.usercenterbackend.common.BaseResponse;
import com.jarvis.usercenterbackend.common.ErrorCode;
import com.jarvis.usercenterbackend.common.ResultUtils;
import com.jarvis.usercenterbackend.exception.BusinessException;
import com.jarvis.usercenterbackend.model.domain.User;
import com.jarvis.usercenterbackend.model.domain.request.UserLoginRequest;
import com.jarvis.usercenterbackend.model.domain.request.UserRegisterRequest;
import com.jarvis.usercenterbackend.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jarvis.usercenterbackend.constant.UserConstant.ADMIN_ROLE;
import static com.jarvis.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

  @PostMapping("/login")
  public BaseResponse<User> userLogin(
      @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
    if (userLoginRequest == null) {
        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    String userAccount = userLoginRequest.getUserAccount();
    String userPassword = userLoginRequest.getUserPassword();
    if (StringUtils.isAnyBlank(userAccount, userPassword)) {
        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }
    User user = userService.userLogin(userAccount, userPassword, request);
    return ResultUtils.success(user);
        }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
            if(request == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            int result = userService.userLogout(request);
            return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO check if user is illegal
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

  @GetMapping("/search")
  public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
    // 仅管理员可查询
    if (!isAdmin(request)) {
        throw new BusinessException(ErrorCode.PARAMS_ERROR);
    }

    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if (StringUtils.isNotBlank(username)) {
      queryWrapper.like("username", username);
    }
    List<User> userList = userService.list(queryWrapper);
    List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {

            if (isAdmin(request)) {
                throw new BusinessException(ErrorCode.NO_AUTH);
            }


        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);

    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
