package cn.itrip.entity;

import cn.itrip.pojo.ItripHotel;
import cn.itrip.vo.ItripHotelVO;
import cn.itrip.vo.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;

import java.io.IOException;
import java.util.List;

public class HotelEntity {


    @Field
    private String id;
    @Field
    private String hotelName;
    @Field
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public static void main(String[] args) throws IOException, SolrServerException {
        //连接url
        String url="http://localhost:8095/solr/hotel";
        HttpSolrClient httpSolrClient=new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());//设置相应解析器
        httpSolrClient.setConnectionTimeout(500);//建立连接的最长时间
        SolrQuery solrQuery=new SolrQuery();//新建查询
        solrQuery.setQuery("*:*");
        solrQuery.setStart(0);//从第几条开始
        solrQuery.setRows(20);//一页显示几条
        solrQuery.setSort("id",SolrQuery.ORDER.asc);//按id升序排列
        QueryResponse queryResponse=httpSolrClient.query(solrQuery);
        List<ItripHotelVO> list=queryResponse.getBeans(ItripHotelVO.class);

        for(ItripHotelVO itripHotelVO:list){
            System.out.println(itripHotelVO.getAddress());
        }
    }
}
