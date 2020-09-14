package com.ruoyi.dfm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.dfm.constant.UserConstants;
import com.ruoyi.dfm.pojo.*;
import com.ruoyi.dfm.service.FileService;
import com.ruoyi.dfm.service.ProjectService;
import com.ruoyi.dfm.service.UserService;
import com.ruoyi.dfm.util.TimeUtil;
import com.ruoyi.framework.util.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.*;

@Controller
@RequestMapping("/project.do")
public class ProjectController extends com.ruoyi.common.core.controller.BaseController
{
  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

  @Value("${wait.time:10}")
  private Integer waitTime;

  @Autowired
  private ProjectService projectService;
  @Autowired
  private FileService fileService;
  @Autowired
  private UserService userService;

  @RequestMapping("/")
  public ModelAndView defaultHandle(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    return new ModelAndView("dfm/addProject");
  }
  @RequestMapping("/add")
  public ModelAndView getAddPage(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    return new ModelAndView("dfm/addProject");
  }

  @RequestMapping("/addSave")
  @ResponseBody
  public AjaxResult add(HttpServletRequest req, HttpServletResponse res, @RequestParam("pcbFile") MultipartFile pcbFile, @RequestParam(value = "bomFile", required = false) MultipartFile bomFile)
    throws Exception
  {
    try
    {
      //FIXME 修改上传文件
//      MultipartResolver cmr = null;//new CosMultipartResolver(request.getSession().getServletContext());

//      MultipartHttpServletRequest req = cmr.resolveMultipart(request);

      String projectName = req.getParameter("projectName");
      String[] dfmCheckArr = req.getParameterValues("dfmCheck");
      String dfmCheck = "";
      if (dfmCheckArr != null)
      {
        for (String check : dfmCheckArr)
        {
          dfmCheck = dfmCheck + check + ",";
        }
      }
      int version = (req.getParameter("version").trim() == "") ? 0 : Integer.parseInt(req.getParameter("version").trim());
      int pri = (req.getParameter("pri").trim() == "") ? 0 : Integer.parseInt(req.getParameter("pri").trim());
      int checkType = (req.getParameter("checkType").trim() == "") ? 0 : Integer.parseInt(req.getParameter("checkType").trim());
      int pcbType = (req.getParameter("pcbType").trim() == "") ? 0 : Integer.parseInt(req.getParameter("pcbType").trim());
      int hdiModel = (req.getParameter("hdiModel").trim() == "") ? 0 : Integer.parseInt(req.getParameter("hdiModel").trim());
      int panelModel = (req.getParameter("panelModel").trim() == "") ? 0 : Integer.parseInt(req.getParameter("panelModel").trim());
      float boardThickness = (req.getParameter("boardThickness").trim() == "") ? 0.0F : Float.parseFloat(req.getParameter("boardThickness").trim());
      float maxHeightTop = (req.getParameter("maxHeightTop").trim() == "") ? 0.0F : Float.parseFloat(req.getParameter("maxHeightTop").trim());
      int railwayPosition = (req.getParameter("railwayPosition").trim() == "") ? 0 : Integer.parseInt(req.getParameter("railwayPosition").trim());
      float maxHeightBot = (req.getParameter("maxHeightBot").trim() == "") ? 0.0F : Float.parseFloat(req.getParameter("maxHeightBot").trim());

      int assemblyProcessTop = (req.getParameter("assemblyProcessTop").trim() == "") ? 0 : Integer.parseInt(req.getParameter("assemblyProcessTop").trim());
      int subPcbNum = (req.getParameter("subPcbNum").trim() == "") ? 0 : Integer.parseInt(req.getParameter("subPcbNum").trim());
      int havePb = (req.getParameter("havePb").trim() == "") ? 0 : Integer.parseInt(req.getParameter("havePb").trim());
      int assemblyProcessBot = (req.getParameter("assemblyProcessBot").trim() == "") ? 0 : Integer.parseInt(req.getParameter("assemblyProcessBot").trim());
      int surfaceProcess = (req.getParameter("surfaceProcess").trim() == "") ? 0 : Integer.parseInt(req.getParameter("surfaceProcess").trim());
      int directionTop = (req.getParameter("directionTop").trim() == "") ? 0 : Integer.parseInt(req.getParameter("directionTop").trim());
      int primarySide = (req.getParameter("primarySide").trim() == "") ? 0 : Integer.parseInt(req.getParameter("primarySide").trim());
      int directionBot = (req.getParameter("directionBot").trim() == "") ? 0 : Integer.parseInt(req.getParameter("directionBot").trim());
      int directionBotFs = (req.getParameter("directionBotFs").trim() == "") ? 0 : Integer.parseInt(req.getParameter("directionBotFs").trim());
      String density = req.getParameter("density");
      
      String lastVersion = null == req.getParameter("lastVersion") ? "" : req.getParameter("lastVersion").trim();
      String CCtoOther = null == req.getParameter("CCtoOther") ? "" : req.getParameter("CCtoOther").trim();
      
      String reportLanguage = null == req.getParameter("reportLanguage") ? "中文" : req.getParameter("reportLanguage").trim();

      Project project = new Project();
      project.setPri(pri);
      project.setAssemblyProcessBot(assemblyProcessBot);
      project.setAssemblyProcessTop(assemblyProcessTop);
      project.setBoardThickness(boardThickness);
      project.setCheckType(checkType);
      project.setDfmCheck(dfmCheck);
      project.setDirectionBot(directionBot);
      project.setDirectionTop(directionTop);
      project.setHavePb(havePb);
      project.setHdiModel(hdiModel);
      project.setMaxHeightBot(maxHeightBot);
      project.setMaxHeightTop(maxHeightTop);
      project.setPcbType(pcbType);
      project.setPrimarySide(primarySide);
      project.setProjectName(projectName);
      project.setRailwayPosition(railwayPosition);
      project.setSubPcbNum(subPcbNum);
      project.setSurfaceProcess(surfaceProcess);
      project.setVersion(version);

      project.setPanelModel(panelModel);
      project.setDirectionBotFs(directionBotFs);
      project.setDensity(density);
      project.setLastVersion(lastVersion);
      
      project.setReportLanguage(reportLanguage);

      UserInfo user = ShiroUtils.getLoginUser();
      project.setSubmitUser(user.getId());
      project.setSubmitUserName(user.getUsername());
      project.setSubmitTime(TimeUtil.getNowChar14());
      
      //拼装抄送邮件
      String userCC = user.getCcEmail();
      String ccEmail = "";
      
      if(StringUtils.isEmpty(userCC)) {
    	  ccEmail = CCtoOther;
      }
      else {
    	  if(userCC.endsWith(";")) {
    		  ccEmail = userCC + CCtoOther;
          } else {
        	  ccEmail = userCC + ";" + CCtoOther;
          }
      }
      
      project.setCCtoOther(ccEmail);

      this.projectService.addProject(ShiroUtils.getLoginUser(), pcbFile, bomFile, project);
//      outputMsg(res, "<script>alert('添加项目成功，点击确定继续添加！');document.location.href='project.do?method=getAddPage';</script>");
//      return null;
      return AjaxResult.success();
    } catch (Exception e) {
      logger.error("添加项目失败", e);
//      outputMsg(res, "<script>alert('添加项目失败，请检查数据正确性，重新添加！');document.location.href='project.do?method=getAddPage';</script>"); }
////    return null;
      return AjaxResult.error();
    }
  }

  @RequestMapping("/getLastVersion")
  @ResponseBody
  public Project getLastVersion(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    int uid = ShiroUtils.getLoginUser().getId();
    String projectName = req.getParameter("projectName");
    Project project = this.projectService.getLastVersion(uid, projectName);
    return project;
//    String msg = "";
//    if (project != null)
//    {
//      //msg = JSONObject.fromObject(project).toString();
//      msg = JSON.toJSONString(project);
//    }
//
//    outputJson(res, msg);
  }

  @RequestMapping("/getAttrValue")
  @ResponseBody
  public JSONArray getAttrValue(HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    String attrName = req.getParameter("attrName");
    List list = this.projectService.getAttrValue(attrName);
//    String msg = "";
    JSONArray arr = new JSONArray();
    if ((list != null) || (!(list.isEmpty())))
    {

      for (int i = 0; i < list.size(); ++i)
      {
        JSONObject obj = new JSONObject();
        obj.put("text", ((Map)list.get(i)).get("F_ATTR_VALUE"));
        obj.put("value", ((Map)list.get(i)).get("F_ID"));
        obj.put("isDefault", ((Map)list.get(i)).get("F_IS_DEFAULT"));
        arr.add(obj);
      }
//      msg = arr.toString();
    }

//    outputJson(res, msg);
    return arr;
  }

  @RequestMapping("/queueManage")
  public ModelAndView queueManage(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String[] states = new String[3];
    states[0] = "待查";
    states[1] = "在查";
    states[2] = "暂停";

    String currentPage = req.getParameter("currentPage");
    Page page = new Page();
    if ((currentPage == null) || ("".equals(currentPage.trim())))
    {
      page.setCurrentPage(1);
    }
    else
    {
      page.setCurrentPage(Integer.parseInt(currentPage));
    }
    UserInfo user = ShiroUtils.getLoginUser();
    List<Project> projects = null;
    Project pre = null;
    Project next = null;
    List users = null;

    //查询排序
    Page tempPage = new Page();
    tempPage.setCurrentPage(1);
    tempPage.setPageSize(999999);
    //查询所有的项目，按照优先级排序
    List<Project> totalByStates = projectService.getProjectByStates(states, tempPage, null);

    if (UserConstants.USER_LEVEL_ADMIN == user.getGroupId() )
    {
      projects = this.projectService.getProjectByStates(states, page, null);
      if ((projects != null) && (!(projects.isEmpty())))
      {
        pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), null, "待查");
        next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), null, "待查");
      }
      users = this.userService.getAllUser();
    }
    else if (UserConstants.USER_LEVEL_NORMAL == user.getGroupId() || UserConstants.USER_LEVEL_SUPER_USER == user.getGroupId())
    {
      projects = this.projectService.getProjectByStates(states, new int []{user.getId()}, page, null);
      if ((projects != null) && (!(projects.isEmpty())))
      {
        pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), new int[]{user.getId()}, "待查");
        next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), new int[]{user.getId()}, "待查");
      }
      users = Arrays.asList(user);
    }
    //如果是部门管理员，则可以查看部门所有的项目
    else if (UserConstants.USER_LEVEL_DEP_ADMIN == user.getGroupId())
    {
        //根据部门查询部门所有用户
      String department = user.getDepartment();
      List<UserInfo> depUsers = userService.getByDepartment(department);
      //if(null == depUsers || depUsers.isEmpty()) {}
      int[] uids = new int [depUsers.size()];
      for (int i=0;i<uids.length;i++) {
        uids[i] = depUsers.get(i).getId();
      }
      //根据部门用户查询出所有部门的项目
      projects = this.projectService.getProjectByStates(states, uids, page, null);

      if ((projects != null) && (!(projects.isEmpty())))
      {
        pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), uids, "待查");
        next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), uids, "待查");
      }

      users = depUsers;
    }

//    Integer waitTime = Integer.parseInt(PropertiesUtils.getProperties().getProperty("wait.time", "10"));

    if(null != projects && !projects.isEmpty()) {
      //融合实际排序
      for(Project project : projects) {
        for(int i=0;i<totalByStates.size();i++) {
          Project projectOrder = totalByStates.get(i);
          if(project.getId() == projectOrder.getId()) {
            project.setRealOrder(i+1);
            project.setWaitTime(i*waitTime);
            break;
          }
        }
      }
    }

    req.setAttribute("pre", pre);
    req.setAttribute("next", next);
    req.setAttribute("projects", projects);
    req.setAttribute("page", page);
    req.setAttribute("users", users);
    return new ModelAndView("dfm/queueManage");
  }


  @RequestMapping("/pause")
  @ResponseBody
  public AjaxResult pause(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    try
    {
      String pid = req.getParameter("ids");
      String[] pids = pid.split(",");

//      String currentPage = req.getParameter("currentPage");
      this.projectService.pauseProject(pids);
      return toAjax(1);
//      outputMsg(res, "<script>document.location.href='project.do?method=queueManage&currentPage=" + currentPage + "';</script>");
    } catch (Exception e) {
      logger.error("暂停项目失败！", e);
      return toAjax(0);
//      outputMsg(res, "<script>alert('暂停项目失败，请联系管理员！');window.history.go(-1);</script>");
    }
  }

  @RequestMapping("/start")
  @ResponseBody
  public AjaxResult start(HttpServletRequest req, HttpServletResponse res)
		    throws Exception
  {
    try
    {
      String pid = req.getParameter("ids");
      String[] pids = pid.split(",");

//      String currentPage = req.getParameter("currentPage");
      this.projectService.startProject(pids);
//      outputMsg(res, "<script>document.location.href='project.do?method=queueManage&currentPage=" + currentPage + "';</script>");
      return toAjax(1);
    } catch (Exception e) {
      logger.error("批量开始项目失败！", e);
      return toAjax(0);
//      outputMsg(res, "<script>alert('暂停开始失败，请联系管理员！');window.history.go(-1);</script>");
    }
  }

  @RequestMapping("/restore")
  @ResponseBody
  public AjaxResult restore(HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    try
    {
      String pid = req.getParameter("pid");

//      String currentPage = req.getParameter("currentPage");
      this.projectService.restoreProject(pid);
      return toAjax(1);
//      outputMsg(res, "<script>document.location.href='project.do?method=queueManage&currentPage=" + currentPage + "';</script>");
    } catch (Exception e) {
      logger.error("恢复项目失败！", e);
//      outputMsg(res, "<script>alert('恢复项目失败，请联系管理员！');window.history.go(-1);</script>");
      return toAjax(0);
    }
  }

  @RequestMapping("/delete")
  @ResponseBody
  public AjaxResult delete(HttpServletRequest req, HttpServletResponse res) throws Exception
  {
    try
    {
//      String source = req.getParameter("source");
      String pid = req.getParameter("ids");

      String[] pids = pid.split(",");
      this.projectService.deleteProject(pids);

//      String currentPage = req.getParameter("currentPage");
      return toAjax(1);
//      outputMsg(res, "<script>alert('删除成功，确定跳转到队列管理！');document.location.href='project.do?method=" + source + "&currentPage=" + currentPage + "';</script>");
    } catch (Exception e) {
      logger.error("删除项目失败！", e);
//      outputMsg(res, "<script>alert('删除失败，确定跳转到队列管理！');window.history.go(-1);</script>");
      return toAjax(0);
    }
  }


  @RequestMapping("/resultDownload")
  public ModelAndView resultDownload(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    String[] states = new String[3];
    states[0] = "完成";
    states[1] = "出错";
    states[2] = "中断";

    String currentPage = req.getParameter("currentPage");
    Page page = new Page();
    page.setOrderCase(" ORDER BY F_END_TIME DESC ");
    if ((currentPage == null) || ("".equals(currentPage.trim())))
    {
      page.setCurrentPage(1);
    }
    else
    {
      page.setCurrentPage(Integer.parseInt(currentPage));
    }

    UserInfo user = ShiroUtils.getLoginUser();
    List projects = null;
    List users = null;

//    String taskResultInquire = (String)PropertiesUtils.getProperties().get("task.result.inquire");
//    if ("user".equals(taskResultInquire))
//    {
//      projects = this.projectService.getProjectByStates(states, new int[]{user.getId()}, page, null);
//      users = Arrays.asList(user);
//    }
//    else
    if (UserConstants.USER_LEVEL_ADMIN == user.getGroupId() || UserConstants.USER_LEVEL_SUPER_USER == user.getGroupId())
    {
      projects = this.projectService.getProjectByStates(states, page, null);
      users = this.userService.getAllUser();
    }
    else if (UserConstants.USER_LEVEL_NORMAL == user.getGroupId())
    {
      projects = this.projectService.getProjectByStates(states, new int[]{user.getId()}, page, null);
      users = Arrays.asList(user);
    }
    //部门管理员可以查看部门所有人的项目
    else if (UserConstants.USER_LEVEL_DEP_ADMIN == user.getGroupId())
    {
      String department = user.getDepartment();
      List<UserInfo> depUsers = userService.getByDepartment(department);
      //if(null == depUsers || depUsers.isEmpty()) {}
      int[] uids = new int [depUsers.size()];
      for (int i=0;i<uids.length;i++) {
        uids[i] = depUsers.get(i).getId();
      }
      projects = this.projectService.getProjectByStates(states, uids, page, null);
      users = depUsers;
    }

    req.setAttribute("users", users);
    req.setAttribute("projects", projects);
    req.setAttribute("page", page);
    req.setAttribute("oper", user);
    req.setAttribute("queryParam", "{}");
    req.setAttribute("loadType", "init");
    return new ModelAndView("dfm/resultDownload");
  }


  @RequestMapping("/downloadResultFile")
  public void downloadResultFile(HttpServletRequest req, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String fid = req.getParameter("fid");
      final String fname = req.getParameter("fname");
      FileInfo pcbFileInfo = this.fileService.getById(Integer.parseInt(fid));
      //fname = new String(fname.getBytes("iso8859-1"), "utf-8");
      String decodeFname = URLDecoder.decode(fname, "utf-8");

      String path = this.fileService.getRootPath() + "/" + pcbFileInfo.getRelaPath() + "/" + decodeFname;
      File file = new File(path);
      if (!(file.exists()))
      {
        outputMsg(response, "<script>alert('结果文件不存在，请联系管理员！');window.history.go(-1);</script>");
        logger.error("结果文件不存在");
        return;
      }
      response.setHeader("Location", fname);
      response.setHeader("Cache-Control", "max-age=");
      response.setHeader("Content-Disposition", "attachment; filename=" + new String(decodeFname.getBytes("utf-8"),"iso8859-1"));
      response.setContentLength((int)file.length());
      OutputStream outputStream = response.getOutputStream();
      InputStream inputStream = new FileInputStream(file);
      byte[] buffer = new byte[1024];
      int i = -1;
      while ((i = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, i);
      }
      outputStream.flush();
      outputStream.close();
      inputStream.close();
      outputStream = null;
    }
    catch (Exception e) {
      logger.error("下载结果文件失败！", e);
      outputMsg(response, "<script>alert('下载结果文件失败，请联系管理员！');window.history.go(-1);</script>");
    }
  }

  @RequestMapping("/downloadParamFile")
  public void downloadParamFile(HttpServletRequest req, HttpServletResponse response)
    throws Exception
  {
    try
    {
      String fid = req.getParameter("fid");
      String fname = req.getParameter("fname");
      FileInfo pcbFileInfo = this.fileService.getById(Integer.parseInt(fid));
      String decodeFname = URLDecoder.decode(fname, "utf-8");

      String path = this.fileService.getRootPath() + "/" + pcbFileInfo.getRelaPath() + "/" + decodeFname;
      File file = new File(path);
      if (!(file.exists()))
      {
        outputMsg(response, "<script>alert('参数文件不存在，请联系管理员！');window.history.go(-1);</script>");
        logger.error("参数文件不存在");
        return;
      }
      response.setHeader("Location", fname);
      response.setHeader("Cache-Control", "max-age=");
      response.setHeader("Content-Disposition", "attachment; filename=" + new String(decodeFname.getBytes("utf-8"),"iso8859-1"));
      response.setContentLength((int)file.length());
      OutputStream outputStream = response.getOutputStream();
      InputStream inputStream = new FileInputStream(file);
      byte[] buffer = new byte[1024];
      int i = -1;
      while ((i = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, i);
      }
      outputStream.flush();
      outputStream.close();
      inputStream.close();
      outputStream = null;
    }
    catch (Exception e) {
      logger.error("下载参数文件失败！", e);
      outputMsg(response, "<script>alert('下载参数文件失败，请联系管理员！');window.history.go(-1);</script>");
    }
  }


  @RequestMapping("/downloadPcbAndBomFile")
	public void downloadPcbAndBomFile(HttpServletRequest req, HttpServletResponse response)
		    throws Exception
  {
    try
    {
    	String fid = req.getParameter("fid");
		FileInfo fileInfo = fileService.getById(Integer.parseInt(fid));
		
//		UserInfo currentUser = getUserInfo(req);
//		if(currentUser.getGroupId() == UserConstants.USER_LEVEL_NORMAL && currentUser.getId() != fileInfo.getUploadUser()) {
//			outputMsg(response, "<script>alert('该文件没有权限下载，请联系管理员！');window.history.go(-1);</script>");
//			return ;
//		}
//		
		File file = fileService.getPhysicFile(fileInfo);
//		if(null == file){
//			outputMsg(response, "<script>alert('该文件不存在，下载失败，请联系管理员！');window.history.go(-1);</script>");
//			return ;
//		}
		//重命名文件为之前存储的名字
		String fname = fileInfo.getFileName();
		try {
			fname = new String(fname.getBytes("utf-8"),"iso8859-1");
			response.setHeader("Location", fname);
			response.setHeader("Cache-Control", "max-age=" + "");
			response.setHeader("Content-Disposition", "attachment; filename=" + fname);
			response.setContentLength((int) file.length());
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			outputStream = null;
		} catch (Exception e) {
			outputMsg(response, "<script>alert('下载文件失败，请联系管理员！');window.history.go(-1);</script>");
		} 
    } catch (Exception e) {
		outputMsg(response, "<script>alert('下载文件失败，请联系管理员！');window.history.go(-1);</script>");
	} 
  }

  @RequestMapping("/checkDownloadPcbAndBomFile")
	public void checkDownloadPcbAndBomFile(HttpServletRequest req, HttpServletResponse response) throws Exception {
		try {
			String fid = req.getParameter("fid");
			FileInfo fileInfo = fileService.getById(Integer.parseInt(fid));

			UserInfo currentUser = ShiroUtils.getLoginUser();
			if (currentUser.getGroupId() == UserConstants.USER_LEVEL_NORMAL
					&& currentUser.getId() != fileInfo.getUploadUser()) {
				outputMsg(response, "{\"success\":false,\"message\":\"该文件没有权限下载，请联系管理员！\"}");
				return;
			}

			File file = fileService.getPhysicFile(fileInfo);
			if (null == file) {
				outputMsg(response, "{\"success\":false,\"message\":\"该文件不存在，下载失败，请联系管理员！\"}");
				return;
			}

			outputMsg(response, "{\"success\":true,\"message\":\"\"}");

		} catch (Exception e) {
			outputMsg(response, "{\"success\":false,\"message\":\"下载文件失败，请联系管理员！\"}");
		}
	}


  @RequestMapping("/queryProject")
  @ResponseBody
  public TableDataInfo queryProject(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    startPage();
    String queryType = req.getParameter("queryType");
    String projectName = req.getParameter("projectName");
    String username = req.getParameter("username");
    String state = req.getParameter("state");
    String startSubmitTime = req.getParameter("startSubmitTime");
    String endSubmitTime = req.getParameter("endSubmitTime");

    ProjectQueryBean queryBean = new ProjectQueryBean();
    queryBean.setEndSubmitTime(endSubmitTime);
    queryBean.setProjectName(projectName);
    queryBean.setStartSubmitTime(startSubmitTime);
    queryBean.setState(state);
    queryBean.setUsername(username);

    Page page = new Page();
    PageDomain pageDomain = TableSupport.getPageDomain();
    page.setCurrentPage(pageDomain.getPageNum());
    page.setPageSize(pageDomain.getPageSize());

    String[] states = new String[3];
    List<Project> projects = null;
    UserInfo user = ShiroUtils.getLoginUser();
    List users = null;
    if ("resultDownload".equals(queryType))
    {
      states[0] = "完成";
      states[1] = "出错";
      states[2] = "中断";

      page.setOrderCase(" ORDER BY F_END_TIME DESC ");

      if (UserConstants.USER_LEVEL_ADMIN == user.getGroupId() || UserConstants.USER_LEVEL_SUPER_USER == user.getGroupId())
      {
        projects = this.projectService.getProjectByStates(states, page, queryBean);
        users = this.userService.getAllUser();
      }
      else if (UserConstants.USER_LEVEL_NORMAL == user.getGroupId())
      {
        projects = this.projectService.getProjectByStates(states, new int[]{user.getId()}, page, queryBean);
        users = Arrays.asList(user);
      }
      //部门管理员可以查看部门所有人的项目
      else if (UserConstants.USER_LEVEL_DEP_ADMIN == user.getGroupId())
      {
        String department = user.getDepartment();
        List<UserInfo> depUsers = userService.getByDepartment(department);
        int[] uids = new int [depUsers.size()];
        for (int i=0;i<uids.length;i++) {
          uids[i] = depUsers.get(i).getId();
        }
        projects = this.projectService.getProjectByStates(states, uids, page, queryBean);
        users = depUsers;
      }

      req.setAttribute("projects", projects);
//      req.setAttribute("page", page);
//      req.setAttribute("users", users);
      //FIXME 修正查询条件中的时间 json
//      req.setAttribute("queryParam", JSONObject.fromObject(queryBean));
//      req.setAttribute("queryParam", JSON.toJSONString(queryBean));
//      req.setAttribute("loadType", "query");
      //FIXME 修改返回类型
//      return new ModelAndView("resultDownload");
//      return null;
      if(null == projects) {
        projects = Collections.emptyList();
      }
      return getDataTable(projects, page.getTotalCount());
    }
    if ("queueManage".equals(queryType))
    {
      states[0] = "待查";
      states[1] = "在查";
      states[2] = "暂停";

      //查询排序
      Page tempPage = new Page();
      tempPage.setCurrentPage(1);
      tempPage.setPageSize(999999);
      Project pre = null;
      Project next = null;
      //查询所有的项目，按照优先级排序
      List<Project> totalByStates = projectService.getProjectByStates(states, tempPage, null);

      if (UserConstants.USER_LEVEL_ADMIN == user.getGroupId())
      {
        projects = this.projectService.getProjectByStates(states, page, queryBean);
        if ((projects != null) && (!(projects.isEmpty())))
        {
          pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), null, "待查");
          next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), null, "待查");
        }
        users = this.userService.getAllUser();
      }
      else if (UserConstants.USER_LEVEL_NORMAL == user.getGroupId() || UserConstants.USER_LEVEL_SUPER_USER == user.getGroupId())
      {
        projects = this.projectService.getProjectByStates(states, new int[]{user.getId()}, page, queryBean);
        if ((projects != null) && (!(projects.isEmpty())))
        {
          pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), new int[]{user.getId()}, "待查");
          next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), new int[]{user.getId()}, "待查");
        }
        users = Arrays.asList(user);
      }
      //部门管理员可以查看部门所有人的项目
      else if (UserConstants.USER_LEVEL_DEP_ADMIN == user.getGroupId())
      {
        String department = user.getDepartment();
        //根据部门查询部门所有用户
        List<UserInfo> depUsers = userService.getByDepartment(department);
        int[] uids = new int [depUsers.size()];
        for (int i=0;i<uids.length;i++) {
          uids[i] = depUsers.get(i).getId();
        }
        //根据部门用户查询出所有部门的项目
        projects = this.projectService.getProjectByStates(states, uids, page, queryBean);
        if ((projects != null) && (!(projects.isEmpty())))
        {
          pre = this.projectService.getByPriState("up", ((Project)projects.get(0)).getId(), uids, "待查");
          next = this.projectService.getByPriState("down", ((Project)projects.get(projects.size() - 1)).getId(), uids, "待查");
        }
        users = depUsers;
      }
//      Integer waitTime = Integer.parseInt(PropertiesUtils.getProperties().getProperty("wait.time", "10"));
      if(null != projects && !projects.isEmpty()) {
        //融合实际排序
        for(Project project : projects) {
          project.setHasNext(null != next);
          project.setHasPre(null != pre);
          for(int i=0;i<totalByStates.size();i++) {
            Project projectOrder = totalByStates.get(i);
            if(project.getId() == projectOrder.getId()) {
              project.setRealOrder(i+1);
              project.setWaitTime(i*waitTime);
              break;
            }
          }
        }
      }

//      req.setAttribute("projects", projects);
//      req.setAttribute("page", page);
//      req.setAttribute("users", users);
//      req.setAttribute("hasPre", null != pre);
//      req.setAttribute("hasNext", null != next);
//      return new ModelAndView("queueManage");
      if(null == projects) {
        projects = Collections.emptyList();
      }
      return getDataTable(projects, page.getTotalCount());
    }

    return null;
  }

  @ResponseBody
  @RequestMapping("/changePri")
  public AjaxResult changePri(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    try
    {
      String pid = req.getParameter("pid");
      String change = req.getParameter("change");

//      String currentPage = req.getParameter("currentPage");
      int uids[] = null;
      UserInfo loginUser = ShiroUtils.getLoginUser();
      if (UserConstants.USER_LEVEL_NORMAL == loginUser.getGroupId())
      {
        uids = new int[]{loginUser.getId()};
      }
      else if (UserConstants.USER_LEVEL_DEP_ADMIN == loginUser.getGroupId())
      {
        //根据部门查询部门所有用户
        String department = loginUser.getDepartment();
        List<UserInfo> depUsers = userService.getByDepartment(department);
        uids = new int [depUsers.size()];
        for (int i=0;i<uids.length;i++) {
          uids[i] = depUsers.get(i).getId();
        }
      }
      this.projectService.changePri(pid, uids, change);
      return toAjax(1);
//      outputMsg(res, "<script>document.location.href='project.do?method=queueManage&currentPage=" + currentPage + "';</script>");
    }
    catch (Exception e) {
      logger.error("调整优先级失败！", e);
//      outputMsg(res, "<script>alert('调整优先级失败，请联系管理员！');window.history.go(-1);</script>");
      return toAjax(0);
    }
  }

  @RequestMapping("/recheck")
  @ResponseBody
  public AjaxResult recheck(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    try
    {
      String pid = req.getParameter("ids");
      String[] pids = pid.split(",");

//      String currentPage = req.getParameter("currentPage");
      this.projectService.recheck(pids);
      return toAjax(1);
//      outputMsg(res, "<script>document.location.href='project.do?method=resultDownload&currentPage=" + currentPage + "';</script>");
    }
    catch (Exception e) {
      logger.error("再查项目失败！", e);
      return toAjax(0);
//      outputMsg(res, "<script>alert('再查项目失败，请联系管理员！');window.history.go(-1);</script>");
    }
  }

  @RequestMapping("/serverMonitor")
  public ModelAndView serverMonitor(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    List servers = this.projectService.getAllServers();
    req.setAttribute("servers", servers);
    return new ModelAndView("dfm/serverMonitor");
  }


  @RequestMapping("/serverMonitor/list")
  @ResponseBody
  public AjaxResult serverMonitorList(HttpServletRequest req, HttpServletResponse res)
          throws Exception
  {
    String server = req.getParameter("server");
    if (StringUtils.isBlank(server)) {
      return AjaxResult.success();
    }
    server = new String(server.getBytes("iso-8859-1"), "utf-8");
    Project project = this.projectService.getProjectByServerState(server, "在查");
    if (project != null) {
      int pid = project.getId();
      List stages = this.projectService.getStagesByProject(pid);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("project", project);
      jsonObject.put("stages", stages);
      return AjaxResult.success(jsonObject);
    }
    return AjaxResult.error();
  }


  @RequestMapping("/stopProject")
  @ResponseBody
  public AjaxResult stopProject(HttpServletRequest req, HttpServletResponse res)
    throws Exception
  {
    try
    {
      String pid = req.getParameter("pid");
      this.projectService.stopProject(pid);
      return AjaxResult.success();
    }
    catch (Exception e) {
      logger.error("中止项目失败！", e);
      return AjaxResult.error();
    }
  }

  /**
   * 上传preDFM文件
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  @RequestMapping("/uploadPreDFM")
  @ResponseBody
  public AjaxResult uploadPreDFM(HttpServletRequest req, HttpServletResponse res, @RequestParam("preDFMFile") MultipartFile preDFMFile)
    throws Exception
  {
    try
    {
     
      //项目ID
      Integer pid = Integer.parseInt(req.getParameter("pid"));
      
      Project project = projectService.getProjectById(pid);
      
      UserInfo projectUser = userService.getUserById(project.getSubmitUser());
    		  
      String dir = projectUser.getUsername() + "/" + 
    	        project.getProjectName() + "/" + "Ver" + project.getVersion();

      List<FileInfo> fileList = new ArrayList<FileInfo>();
      //FIXME 修复上传文件错误
//      this.fileService.savePhysicFile(req, fileList, dir, true, "pre-");

      UserInfo loginUser = ShiroUtils.getLoginUser();
      MultipartFile[] multipartFiles = new MultipartFile[]{preDFMFile};
      this.fileService.savePhysicFile(loginUser, multipartFiles, fileList, dir, true , "pre-");

      projectService.updateProjectPreDFMFile(pid, fileList.get(0).getId(), fileList.get(0).getFileName());
//      req.setAttribute("uploadResult", "上传成功");
//      req.setAttribute("uploadType", "preDFM");
//      req.setAttribute("fid", fileList.get(0).getId());
//      req.setAttribute("fname", fileList.get(0).getFileName());
//      req.setAttribute("pid", pid);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("fid", fileList.get(0).getId());
      jsonObject.put("fname", fileList.get(0).getFileName());
      return AjaxResult.success("上传成功", jsonObject);
    } catch (Exception e) {
      logger.error("上传PreDFM报告失败", e);
//      req.setAttribute("uploadResult", "上传失败");
//      req.setAttribute("uploadType", "preDFM");
      return toAjax(false);
    }
//    return new ModelAndView("upload");
  }
  
  /**
   * 上传preDFM文件
   * @param req
   * @param res
   * @return
   * @throws Exception
   */
  @RequestMapping("/uploadPostDFM")
  @ResponseBody
  public AjaxResult uploadPostDFM(HttpServletRequest req, HttpServletResponse res, @RequestParam("postDFMFile") MultipartFile postDFMFile)
    throws Exception
  {
    try
    {
      //项目ID
      Integer pid = Integer.parseInt(req.getParameter("pid"));
      
      Project project = projectService.getProjectById(pid);
      
      UserInfo projectUser = userService.getUserById(project.getSubmitUser());
	  
      String dir = projectUser.getUsername() + "/" + 
    	        project.getProjectName() + "/" + "Ver" + project.getVersion();

      List<FileInfo> fileList = new ArrayList<FileInfo>();
      //FIXME 修复上传文件错误
//      this.fileService.savePhysicFile(req, fileList, dir, true, "post-");
      UserInfo loginUser = ShiroUtils.getLoginUser();
      MultipartFile[] multipartFiles = new MultipartFile[]{postDFMFile};
      this.fileService.savePhysicFile(loginUser, multipartFiles, fileList, dir, true , "post-");
      
      projectService.updateProjectPostDFMFile(pid, fileList.get(0).getId(), fileList.get(0).getFileName());
      
//      req.setAttribute("uploadResult", "上传成功");
//      req.setAttribute("uploadType", "postDFM");
//      req.setAttribute("fid", fileList.get(0).getId());
//      req.setAttribute("fname", fileList.get(0).getFileName());
//      req.setAttribute("pid", pid);
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("fid", fileList.get(0).getId());
      jsonObject.put("fname", fileList.get(0).getFileName());
      return AjaxResult.success("上传成功", jsonObject);
    } catch (Exception e) {
      logger.error("上传PostDFM报告失败", e);
//      req.setAttribute("uploadResult", "上传失败");
//      req.setAttribute("uploadType", "postDFM");
      return toAjax(false);
    }
//    return new ModelAndView("upload");
  }
  
}