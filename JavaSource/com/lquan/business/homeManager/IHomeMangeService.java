package com.lquan.business.homeManager;

import java.util.List;
import java.util.Map;

import snt.common.dao.base.PaginationSupport;

/**
 * @description 首页的接口
 * @author liuquan
 * @date 2016年10月25日10:12:37
 */
public interface IHomeMangeService {
	
	/**
	 * 系统公告
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> systemAd(String ctxPath) throws Exception;
	
	/**
	 * 活动展示
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> systemActive() throws Exception;
	
	/**
	 * 获取活动展示的图片
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getImglist() throws Exception;
	
	/**
	 * 首页展示获取的数据
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getHomeImglist() throws Exception;
	
	/**
	 * 规章制度
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getHomeruler(String basePath,String ctxPath ) throws Exception;
	
	
	/**
	 * 获取查看的规章制度的pdf
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getOnePdfflow(String pk_id) throws Exception;
	/**
	 * 获取对应类型的规章制度
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public PaginationSupport getRuleList(String page, String rows, String sort,String order, String title,String type) throws Exception;
	/**
	 * 查询最新的公告
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @param title
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public PaginationSupport getNoticeList(String page, String rows, String sort,String order, String title,String type) throws Exception;

	/**
	 * 获取活动展示的而标题和和简介以及时间
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getActive(String pk_id) throws Exception;
	
	/**
	 * 获取活动展示的照片以及照片简介
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getActiveImg(String pk_id,String basePath) throws Exception;
	
	/**
	 *Home页面的数据显示及图片的显示
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getHomeActive(String activityBasePath,String ctxPath) throws Exception;
}
