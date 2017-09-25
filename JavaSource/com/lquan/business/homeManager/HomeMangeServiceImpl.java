package com.lquan.business.homeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import snt.common.dao.base.CommonDAO;
import snt.common.dao.base.PaginationSupport;
import snt.common.dao.base.RowSelection;

@Service(value="homeMangeService")
@Transactional
public class HomeMangeServiceImpl implements IHomeMangeService {

	
	@Autowired
	@Qualifier("commonDAO")
	private CommonDAO commonDao;
	/**
	 * 系统公告
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> systemAd(String ctxPath) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select top 3  pk_id,case when flage_new =1 then '<img  style=display: inline; src="+ctxPath+"/img/new/new1.gif>' else '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' end flage_new,title as title,title_img,content,CONVERT(varchar(100), noticeTime, 20 )noticeTime,sortTime,updateTime from dbo.notice order by noticeTime desc");
		List<Map<String, Object>> list = this.commonDao.queryForMapList(sql.toString());
		 
		return list;
	}
	
	/**
	 * 活动展示
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@Override
	public List<Map<String, Object>> systemActive() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select top 5 pk_id,title,content,tourtime,sortTime,updateTime from dbo.tour order by tourtime desc");
		List<Map<String, Object>> list = this.commonDao.queryForMapList(sql.toString());
		return list;
	}

	/**
	 * 获取活动展示的图片
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getImglist() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,tourid,fileName,description,updateAt,updateBy from dbo.tourImg  order by updateAt desc");
		List<Map<String, Object>> list = this.commonDao.queryForMapList(sql.toString());
		
		return list;
	}

	
	/**
	 * 首页展示获取的数据
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getHomeImglist() throws Exception {
		List<Map<String, Object>> listActive = systemActive();
		List<Map<String, Object>> listImg = getImglist();
		List<Map<String, Object>>  listTemp = new ArrayList<Map<String, Object>>();
		for(Map<String, Object> active :listActive){
			int count =0;
			for(Map<String, Object> img :listImg){
				if(active.get("pk_id").equals(img.get("tourid"))){
					Map<String, Object> map = new HashMap<String, Object>();
					// upload\\Img
					map.put("tourid", active.get("pk_id"));
					map.put("title",  active.get("title"));
					map.put("path", "//upload//Img//"+img.get("fileName"));
					listTemp.add(map);
					break;
					
				}else{
					count++;
				}
				if(count==listImg.size()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("tourid", active.get("pk_id"));
					map.put("title",  active.get("title"));
					map.put("path", "//upload//demo.jpg");
					listTemp.add(map);
				}
				
			}
			
		}
		
		
		return listTemp;
	}

	/**
	 * 规章制度
	 * @param basePath
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getHomeruler(String basePath,String ctxPath) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.pk_id,t.type,t.fileName, path,t.createAt,t.createby  from ( ");
		sql.append("select top 5 pk_id,type,case when flage_new =1 then '<img  style=display:inline; src="+ctxPath+"/img/new/new4.gif>' else '' end +''+ fileName as fileName,'");
		sql.append(basePath);
		sql.append(" ' + convert(varchar(20),type) +'/' +fileAllName as path ,");
		sql.append(" createAt,createby from dbo.ruleFile  where type=1 ");
		sql.append("UNION ");
		sql.append("select top 5 pk_id,type,case when flage_new =1 then '<img  style=display:inline; src="+ctxPath+"/img/new/new4.gif>' else '' end +''+ fileName as fileName,'");
		sql.append(basePath);
		sql.append(" '+ convert(varchar(20),type) +'/'  +fileAllName as path ,");
		sql.append(" createAt,createby from dbo.ruleFile  where type=2 ");
		sql.append("UNION ");
		sql.append("select top 5 pk_id,type,case when flage_new =1 then '<img  style=display:inline; src="+ctxPath+"/img/new/new4.gif>' else '' end +''+ fileName as fileName,'");
		sql.append(basePath);
		sql.append(" ' + convert(varchar(20),type) +'/' +fileAllName as path ,");
		sql.append(" createAt,createby from dbo.ruleFile  where type=3 ");
		sql.append(")t order by t.createAt desc");
		List<Map<String, Object>> list = this.commonDao.queryForMapList(sql.toString());
		
		return list;
	}

	
	/**
	 * 获取查看的规章制度的pdf
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getOnePdfflow(String pk_id) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id,type,fileName, convert(varchar(20),type) +'//' +fileAllName as path ,fileAllName, createAt,createby from dbo.ruleFile where pk_id =");
		sql.append(pk_id);
		
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		Map<String, Object> objectx =null;
		if(list!=null){
			return list;
		}else
			return null;
		
	}

	/**
	 * 获取对应类型的规章制度
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public PaginationSupport getRuleList(String page, String rows, String sort,String order, String fileName,String type) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select pk_id, case when type =1 then '基本制度' when type=2 then 'IT制度' when type=3 then '财务制度' end type ,");
		sql.append(" fileName,fileAllName,CONVERT(varchar(100), createAt, 23) dateTime,createBy from dbo.ruleFile where type ='");
		sql.append(type);
		sql.append("'");
		if(!"".equals(fileName)&& fileName!=null){
			sql.append(" AND fileAllName like '%");
			sql.append(fileName);
			sql.append("%'");
		}
		
		
		String orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sql.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
		
	}
	
	/**
	 * 获取对应类型的规章制度
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public PaginationSupport getNoticeList(String page, String rows, String sort,String order, String title,String type) throws Exception {
		StringBuffer sql = new StringBuffer();
		//  order by noticeTime desc
		sql.append(" select pk_id,title,content,CONVERT(varchar(100), noticeTime, 23) noticeTime, CONVERT(varchar(100), sortTime, 23) sortTime,CONVERT(varchar(100), updateTime, 23) updateTime from dbo.notice where 1=1");
		if(title!=null && !"".equals(title)){
			sql.append(" AND title like '%").append(title).append("%'");
		}
		String orderBy = null;
		if("".equals(sort) ||sort ==null){
			orderBy = " order by noticeTime desc";
		}else 
			orderBy = " order by " + sort + " " + order;
		PaginationSupport ps = commonDao.queryForPaginatedMapList(sql.toString(), new Object[]{},new int[]{},new RowSelection(Integer.valueOf(page)-1 ,Integer.valueOf(rows)),orderBy);
		
		if(null != ps){
			return ps;
		}
		return null;
		
	}

	/**
	 * 获取活动展示的而标题和和简介以及时间
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getActive(String pk_id) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_id ,title,content,CONVERT(varchar(100), tourTime, 23) tourTime from dbo.tour where pk_id='");
		sql.append(pk_id);
		sql.append("'");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

	/**
	 * 获取活动展示的照片以及照片简介
	 * @param pk_id
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String, Object>> getActiveImg(String pk_id,String basePath) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" select pk_id,tourid,fileName, '");
		sql.append(basePath);
		sql.append("' + fileName as imgPath,description,CONVERT(varchar(100), updateAt, 23) updateAt from dbo.tourImg where tourid='");
		sql.append(pk_id);
		sql.append("'");
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}

	@Override
	public List<Map<String, Object>> getHomeActive(String activityBasePath,String ctxPath) throws Exception {
		StringBuffer sql = new StringBuffer();						//<img alt="" style="display: inline;" src="${ctxPath}/img/new/new4.gif">
		sql.append("select top 5 pk_id,case when flage_new =1 then '<img  style=display:inline; src="+ctxPath+"/img/new/new4.gif>' else '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;' end +''+title as title,case when filePath is null then 'upload/demo.jpg' else '");
		sql.append(activityBasePath).append("' + filePath end path,");
		sql.append(" CONVERT(varchar(100),createAt, 23) from activity_title  order by  createAt desc");
		
		List<Map<String, Object>> list = commonDao.queryForMapList(sql.toString());
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	

}
