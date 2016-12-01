package org.pqh.controller;

import org.pqh.dao.BiliDao;
import org.pqh.entity.Bili;
import org.pqh.service.AvCountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class FindController {
	@Resource BiliDao biliDao;
	@Resource
	AvCountService avCountService;
	@RequestMapping("/findcid.do")
	public String findcid(HttpServletRequest req){
		int cid=Integer.valueOf(req.getParameter("id"));
		Bili bili=biliDao.findByCid(cid);
		req.setAttribute("bili", bili);
		return "jsp/showbili";
	}
	@RequestMapping("/findaid.do")
	public String findaid(HttpServletRequest req){
		int aid=Integer.valueOf(req.getParameter("id"));
		Bili bili=biliDao.findByAid(aid);
		req.setAttribute("bili", bili);
		return "jsp/showbili";
	}
	@RequestMapping("/findcids.do")
	public ModelAndView findids(int a,int b){
		List<Bili> list=biliDao.findCids(a, b);
		ModelAndView av=new ModelAndView("jsp/showbilis");
		av.getModel().put("list", list);
		return av;
	}
	@RequestMapping("/findaids.do")
	public String findcids(HttpServletRequest req){
		int aid=Integer.valueOf(req.getParameter("startid"));
		List<Bili> list=biliDao.findAids(aid);
		req.setAttribute("list", list);
		return "jsp/showbilis";
	}
	@RequestMapping("/getAid.do")
	@ResponseBody
	public String getAid(int id){
		return "AID:"+biliDao.getAid(id).getBilibili();
	}
	@RequestMapping("/start.do")
	public String start(){
		return "redirect:insert.do?aid="+biliDao.getAid(1).getBilibili();
	}
	@RequestMapping("/findfp.do")
	public ModelAndView getfp(int a,int b){
		List<Bili> list=biliDao.findVCC(a,b);
		ModelAndView av=new ModelAndView("showaids");
		av.getModel().put("list", list);
		return av;
	}
	@RequestMapping(value="/findAvCount.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, List> findAvCount(){
		return avCountService.getAvCount();
	}

	@RequestMapping(value="/findAvPlay.do",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Map<String, Object> findAvPlay() {
		return avCountService.getAvPlay();
	}
}
