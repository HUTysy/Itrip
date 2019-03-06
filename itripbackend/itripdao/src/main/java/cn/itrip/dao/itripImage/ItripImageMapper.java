package cn.itrip.dao.itripImage;
import cn.itrip.pojo.ItripImage;
import cn.itrip.vo.ItripImageVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItripImageMapper {

	public List<ItripImageVO> getImgById(@Param(value="targetId")String targetId);

	public ItripImage getItripImageById(@Param(value = "id") Long id)throws Exception;

	public List<ItripImageVO>	getItripImageListByMap(Map<String, Object> param)throws Exception;

	public Integer getItripImageCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertItripImage(ItripImage itripImage)throws Exception;

	public Integer updateItripImage(ItripImage itripImage)throws Exception;

	public Integer deleteItripImageById(@Param(value = "id") Long id)throws Exception;

}
