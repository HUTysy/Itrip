package cn.itrip.service;

import cn.itrip.dao.itripUser.ItripUserMapper;
import cn.itrip.pojo.ItripUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

    @Resource
    ItripUserMapper mapper;

    public ItripUser getUserByCode(@Param(value = "usercode") String code, @Param(value = "password") String password){
        return mapper.getUserByCode(code, password);
    }
}
