package cn.itrip.controller;

import cn.itrip.common.DateUtil;
import cn.itrip.common.DtoUtil;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.dao.itripHotelTempStore.ItripHotelTempStoreMapper;
import cn.itrip.pojo.ItripImage;
import cn.itrip.service.HotelService;
import cn.itrip.vo.*;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
public class RoomController {
    private Logger logger = Logger.getLogger(RoomController.class);

    @Resource
    ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    HotelService hotelService;

    @Resource
    ItripHotelTempStoreMapper itripHotelTempStoreMapper;


    @RequestMapping(value="/hotelroom/queryhotelroombyhotel",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public Dto roomfind(@RequestBody SearchHotelRoomVO vo) throws Exception {
        List<List<ItripHotelRoomVO>> hotelRoomVOList = null;

        List<Date> list =  DateUtil.getBetweenDates(vo.getStartDate(),vo.getEndDate());

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("hotelId",vo.getHotelId());
        map.put("list",list);

        List<ItripHotelRoomVO> hotelRoomVOS =  itripHotelRoomMapper.getItripRoom(map);

        hotelRoomVOList = new ArrayList();
        for (ItripHotelRoomVO roomVO : hotelRoomVOS) {
            List<ItripHotelRoomVO> tempList = new ArrayList<ItripHotelRoomVO>();
            tempList.add(roomVO);
            hotelRoomVOList.add(tempList);
        }
        return DtoUtil.returnSuccess("获取成功", hotelRoomVOList);
    }

    @RequestMapping(value = "/hotelroom/getimg/{targetId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Dto getImgById(@PathVariable("targetId")String targetId){
        return DtoUtil.returnSuccess("获取图片成功",hotelService.getImgById(targetId));
    }

    @RequestMapping(value = "/hotelorder/getpreorderinfo", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Dto getpreorderinfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO){
        //如果临时库存表没有数据，从原始库存表导入
        Map<String,Object> map1=new HashMap<>();
        map1.put("hid",validateRoomStoreVO.getHotelId());
        map1.put("rid",validateRoomStoreVO.getRoomId());
        map1.put("sd",validateRoomStoreVO.getCheckInDate());
        map1.put("ed",validateRoomStoreVO.getCheckOutDate());
        itripHotelTempStoreMapper.insertData(map1);
        //返回该时间段内所有日期的房间数量
        Map<String,Object> map2=new HashMap<>();
        map2.put("rid",validateRoomStoreVO.getRoomId());
        map2.put("sd",validateRoomStoreVO.getCheckInDate());
        map2.put("ed",validateRoomStoreVO.getCheckOutDate());

        List<StoreVO> list=itripHotelTempStoreMapper.getStore(map2);

        RoomStoreVO roomStoreVO=new RoomStoreVO();
        roomStoreVO.setHotelName("酒店名字");
        roomStoreVO.setStore(list.get(0).getStore());

        return DtoUtil.returnSuccess("获取成功",roomStoreVO);

        //获取所有房间数量的最小值及房间信息

    }

}
