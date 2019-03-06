package cn.itrip.service;

import cn.itrip.common.Page;
import cn.itrip.dao.BaseQueryDao;
import cn.itrip.vo.ItripHotelVO;
import cn.itrip.vo.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SearchHotelService {

    public List<ItripHotelVO> getList(int cityid,int count){
        BaseQueryDao<ItripHotelVO> baseQueryDao=new BaseQueryDao();
        SolrQuery solrQuery=new SolrQuery("*:*");
        solrQuery.addFilterQuery("cityId:"+cityid);
        return baseQueryDao.getPageList(solrQuery,count,ItripHotelVO.class);
    }

    public Page<ItripHotelVO> searchItripHotelPage(SearchHotelVO searchHotelVO, int pageNo, int pageSize){
        BaseQueryDao<ItripHotelVO> baseQueryDao=new BaseQueryDao();
        SolrQuery solrQuery=new SolrQuery("*:*");

        if(searchHotelVO.getDestination()!=null)
        {
            solrQuery.addFilterQuery(" destination:" + searchHotelVO.getDestination());
        }

        if(searchHotelVO.getKeywords()!=null&&searchHotelVO.getKeywords()!="")
        {
            solrQuery.addFilterQuery(" keyword:"+searchHotelVO.getKeywords());
        }

        if(searchHotelVO.getMinPrice()!=null)
        {
            solrQuery.addFilterQuery(" minPrice:"+"["+ searchHotelVO.getMinPrice()+" TO *]");
        }

        if (searchHotelVO.getMaxPrice()!=null)
        {
            solrQuery.addFilterQuery(" maxPrice:"+"[* TO "+searchHotelVO.getMaxPrice()+"]");
        }

        if(searchHotelVO.getFeatureIds()!=null&&searchHotelVO.getFeatureIds()!="") {
            String[] substr = searchHotelVO.getFeatureIds().split(",");

            for (String i : substr) {
                solrQuery.addFilterQuery(" featureIds:*," + i + ",*");
            }
        }

        if (searchHotelVO.getTradeAreaIds()!=null&&searchHotelVO.getTradeAreaIds()!=""){
            String[] substr = searchHotelVO.getTradeAreaIds().split(",");

            for (String i : substr) {
                solrQuery.addFilterQuery(" tradingAreaIds:*," + i + ",*");
            }
        }

        if(searchHotelVO.getAscSort()!=null&&searchHotelVO.getAscSort()!="")
        {
            solrQuery.addSort(searchHotelVO.getAscSort(),SolrQuery.ORDER.asc);
        }

        if(searchHotelVO.getDescSort()!=null&&searchHotelVO.getDescSort()!="")
        {
            solrQuery.addSort(searchHotelVO.getDescSort(),SolrQuery.ORDER.desc);
        }

        return baseQueryDao.queryPage(solrQuery,pageNo,pageSize,ItripHotelVO.class);
    }
}
