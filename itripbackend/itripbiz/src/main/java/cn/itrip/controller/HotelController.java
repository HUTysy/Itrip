package cn.itrip.controller;

import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripLabelDic;
import cn.itrip.service.HotelService;
import cn.itrip.vo.Dto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("api")
public class HotelController {

    @Resource
    HotelService hotelService;

    @Resource
    ItripAreaDicMapper itripAreaDicMapper;

    @RequestMapping(value = "hotel/querytradearea/{cityId}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getAreaById(@PathVariable("cityId")Integer id){
        return DtoUtil.returnDataSuccess(itripAreaDicMapper.getAreaById(id));
    }

    @RequestMapping(value = "/hotel/queryhotelfeature",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getQueryhotelfeature(){
        List<ItripLabelDic> list=hotelService.getFirstData();
        if(list!=null){
            return DtoUtil.returnDataSuccess(list);
        }
        return DtoUtil.returnFail("数据访问失败","10003");
    }

    @RequestMapping(value = "/hotel/queryhotcity/{type}",method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public Dto getCity(@PathVariable("type")Integer Type){
        List<ItripAreaDic> list=hotelService.getData(Type);
        if(list!=null){
            return DtoUtil.returnDataSuccess(list);
        }
        return DtoUtil.returnFail("数据访问失败","10002");
    }
}
