package cn.itrip.service;

import cn.itrip.dao.itripAreaDic.ItripAreaDicMapper;
import cn.itrip.dao.itripHotel.ItripHotelMapper;
import cn.itrip.dao.itripHotelRoom.ItripHotelRoomMapper;
import cn.itrip.dao.itripImage.ItripImageMapper;
import cn.itrip.dao.itripLabelDic.ItripLabelDicMapper;
import cn.itrip.pojo.ItripAreaDic;
import cn.itrip.pojo.ItripImage;
import cn.itrip.pojo.ItripLabelDic;
import cn.itrip.vo.ItripHotelRoomVO;
import cn.itrip.vo.ItripImageVO;
import cn.itrip.vo.ItripSearchDetailsHotelVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HotelService {

    @Resource
    ItripAreaDicMapper itripAreaDicMapper;

    @Resource
    ItripLabelDicMapper itripLabelDicMapper;

    @Resource
    ItripHotelRoomMapper itripHotelRoomMapper;

    @Resource
    ItripImageMapper itripImageMapper;

    @Resource
    ItripHotelMapper itripHotelMapper;

    //获取床型图片
    public List<ItripImageVO> getImgById(@Param("targetId")String targetId){
        return itripImageMapper.getImgById(targetId);
    }

    //获取酒店房间床型
//    public List<ItripHotelRoomVO> getItripRoom(Map<String, Object> param) throws Exception {
//        return itripHotelRoomMapper.getItripRoom(param);
//    }

    //获取特色酒店列表
    public List<ItripLabelDic> getFirstData(){
        return itripLabelDicMapper.getFirstData();
    }

    //根据类型获取热门城市
    public List<ItripAreaDic> getData(int type){
        return itripAreaDicMapper.getItripAreaDicByType(type);
    }
}
