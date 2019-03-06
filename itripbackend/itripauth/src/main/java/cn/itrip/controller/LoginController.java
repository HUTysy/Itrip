package cn.itrip.controller;

import cn.itrip.common.DtoUtil;
import cn.itrip.common.MD5;
import cn.itrip.pojo.ItripUser;
import cn.itrip.vo.ItripTokenVO;
import cn.itrip.service.LoginService;
import cn.itrip.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
@Api(value = "login",description = "登录模块")
public class LoginController {
    @Resource
    LoginService loginService;

    @Resource
    TokenService tokenService;

    @ApiOperation(value = "根据用户名和密码返回数据",notes = "正确返回一个json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="form",required=true,value="用户名",name="name",defaultValue="itrip@163.com"),
            @ApiImplicitParam(paramType="form",required=true,value="密码",name="password",defaultValue="123456"),
    })
    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    @ResponseBody
    public Object doLogin(HttpServletRequest request, @RequestParam("name")String name, String password){
        //验证用户名和密码
        ItripUser user=loginService.getUserByCode(name,MD5.getMd5(password,32));
        if(user!=null){
            //根据用户的请求信息及当前用户生成一个key：token
            String token=tokenService.generateToken(request.getHeader("User-Agent"),user);
            //把token存入redis
            tokenService.save(token,user);
            //生成tokenVos实体，方便前台传递
            ItripTokenVO tokenVO=new ItripTokenVO(token,60*60*2,Calendar.getInstance().getTimeInMillis());
            //返回前台数据
            //return DtoUtil.returnDataSuccess(tokenVO);
            return DtoUtil.returnSuccess("成功！","200");
        }
        return DtoUtil.returnFail("用户名密码错误","10001");
    }
}
