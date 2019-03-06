package cn.itrip.controller;

import cn.itrip.common.DtoUtil;
import cn.itrip.common.Page;
import cn.itrip.service.SearchHotelService;
import cn.itrip.vo.Dto;
import cn.itrip.vo.ItripHotelVO;
import cn.itrip.vo.SearchHotCityVO;
import cn.itrip.vo.SearchHotelVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("api")
public class SearchHotelController {

    @RequestMapping(value = "/hotellist/searchItripHotelListByHotCity",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchHotel(@RequestBody SearchHotCityVO searchHotCityVO){
        SearchHotelService searchHotelService=new SearchHotelService();
        List<ItripHotelVO> list=searchHotelService.getList(searchHotCityVO.getCityId(),searchHotCityVO.getCityId());
        return DtoUtil.returnDataSuccess(list);
    }

    @RequestMapping(value = "/hotellist/searchItripHotelPage",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto searchItripHotelPage(@RequestBody SearchHotelVO searchHotelVO){
        SearchHotelService searchHotelService=new SearchHotelService();

        if(searchHotelVO.getPageNo()==null){
            searchHotelVO.setPageNo(1);
        }

        Page<ItripHotelVO> page=searchHotelService.searchItripHotelPage(searchHotelVO,searchHotelVO.getPageNo(),6);
        return DtoUtil.returnDataSuccess(page);
    }
}
