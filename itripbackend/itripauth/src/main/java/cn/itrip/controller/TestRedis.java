package cn.itrip.controller;

import cn.itrip.common.RedisAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestRedis {
    @Resource
    RedisAPI redisAPI;

    @RequestMapping("/test")
    @ResponseBody
    public Object getRedis(){
        try {
            redisAPI.set("k1",30,"测试的值");
            return "插入成功";
        }catch (Exception e){
            return "插入失败";
        }
    }
}
