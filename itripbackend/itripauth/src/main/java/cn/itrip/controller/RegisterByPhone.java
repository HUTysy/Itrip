package cn.itrip.controller;

import cn.itrip.common.DtoUtil;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.pojo.ItripUser;
import cn.itrip.service.RegisterByPhoneService;
import cn.itrip.vo.Dto;
import cn.itrip.vo.ItripUserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("api")
public class RegisterByPhone {

    @Resource
    RegisterByPhoneService registerByPhoneService;

    @Resource
    RedisAPI redisAPI;

    @RequestMapping(value = "registerbyphone",method = RequestMethod.POST,produces = "application/json")
    public @ResponseBody Dto registerByPhone(@RequestBody ItripUserVO itripUserVO) throws Exception {
        //根据手机号发送验证码
        Long code=(long) (Math.random()*9000)+1000;
        registerByPhoneService.sendCode(itripUserVO.getUserCode(),code+"");
        //将验证码存入redis中
        redisAPI.set("code:"+itripUserVO.getUserCode(),60,code+"");
        //将数据存入到用户表中
        ItripUser user=new ItripUser();
        user.setUserCode(itripUserVO.getUserCode());
        user.setUserName(itripUserVO.getUserName());
        user.setUserPassword(MD5.getMd5(itripUserVO.getUserPassword(),32));
        //此时用户未激活
        user.setActivated(0);
        registerByPhoneService.insertUser(user);

        return DtoUtil.returnSuccess("发送成功");
    }

    //根据手机号，验证激活码
    @RequestMapping("validatephone")
    @ResponseBody
    public Dto activate(String user,String code) {
        //判断redis中是否存在对应数据
        if (registerByPhoneService.ifExist(user, code)) {
            registerByPhoneService.updateStatus(user);
            return DtoUtil.returnSuccess("激活成功！");
        }
        return DtoUtil.returnFail("激活失败！","10001");
    }
}
