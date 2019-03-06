package cn.itrip.dao;

import cn.itrip.common.Page;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;

public class BaseQueryDao<T> {
    public static String url="http://localhost:8095/solr/hotel";

    HttpSolrClient httpSolrClient;
    public BaseQueryDao(){
        httpSolrClient=new HttpSolrClient(url);
        httpSolrClient.setParser(new XMLResponseParser());//设置响应解析器
        httpSolrClient.setConnectionTimeout(500);//建立连接的最长时间
    }

    //封装solr分页方法
    public Page<T> queryPage(SolrQuery solrQuery,int pageNo,int pageSize,Class T){
        solrQuery.setStart((pageNo-1)*pageSize);//从第几条开始
        solrQuery.setRows(pageSize);//一页显示几条
        solrQuery.setSort("id",SolrQuery.ORDER.asc);//按id升序排列

        QueryResponse queryResponse= null;
        try {
            queryResponse = httpSolrClient.query(solrQuery);
            List<T> list=queryResponse.getBeans(T);

            //读取solr中所有数据
            SolrDocumentList docs = queryResponse.getResults();
            Page<T> page=new Page<>(pageNo,pageSize,new Long(docs.getNumFound()).intValue());
            page.setRows(list);
            return page;
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //封装返回数据的方法
    public List<T> getPageList(SolrQuery solrQuery,int count,Class T) {

        solrQuery.setStart(0);//从第几条开始
        solrQuery.setRows(count);//一页显示几条
        solrQuery.setSort("id",SolrQuery.ORDER.asc);//按id升序排列

        QueryResponse queryResponse= null;
        try {
            queryResponse = httpSolrClient.query(solrQuery);
            List<T> list=queryResponse.getBeans(T);
            return list;
        } catch (SolrServerException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
