package springhibernate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import org.apache.commons.logging.impl.Log4JLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.AdminCred;
import com.pojo.MapperClass;

import springdao.DAOClass;

@Controller

public class IndexController {
	
	@Autowired
	DAOClass daoclass;
	
	MapperClass mcobject;
	
	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@RequestMapping(value="/welcome",method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView display(HttpSession session,HttpServletRequest request)
	{
		
		logger.info("Msg from display method");
	
		
		//session closed
		System.out.println("Session That is going to close  is "+session.getId());
		session.invalidate();
		System.out.println("Previous session close");
		ModelAndView modelandview=new ModelAndView("welcomepage");
		return modelandview;
	}
	
	
	@RequestMapping(value="/userloginform",method= RequestMethod.POST)
	public ModelAndView userform(@RequestParam("email")String email,@RequestParam("password") String password,HttpSession session,HttpServletRequest request,Model model)
	{	
		logger.info("Msg from userform method");
		mcobject=new MapperClass();
		mcobject.setEmail(email);
		mcobject.setPassword(password);
		boolean usercheck=daoclass.loginsearch(mcobject);
	
		System.out.println(usercheck);
		if(usercheck)
		{
			//session start
			session=request.getSession();
			System.out.println("Current Session ID is "+session.getId()+"Time is "+session.getCreationTime());
			ModelAndView modelandview=new ModelAndView("firstpage");
			model.addAttribute("username", email);
			return modelandview;
		}
		else {
			
			ModelAndView modelandview=new ModelAndView("welcomepage");
			modelandview.addObject("message", "Please Register");
			return modelandview; 
		}
		

	    		
	}
	
	@RequestMapping(value="/registrationpage")
	public ModelAndView registrationpage(HttpSession session)
	{
		ModelAndView modelandview=new ModelAndView("registrationpage");
		
		return modelandview;
	}
	
	@RequestMapping(value="/registration",method= {RequestMethod.POST,RequestMethod.GET})	
	public String registration(@RequestParam("email") String email,@RequestParam("password") String password, @RequestParam("password-repeat") String passwordrepeat,Model model)
	{
			logger.info("Msg from Registration Method");
			mcobject=new MapperClass();
			mcobject.setEmail(email);
			mcobject.setPassword(password);
			daoclass.register(mcobject);
			model.addAttribute("message","Successfully Registered");
	
			return "welcomepage";
		
		
	}
		
	@RequestMapping("/adminloginpage")
	public ModelAndView adminloginpage(HttpSession session,Model model)
	{
		logger.info("Session Invalidated");
		session.invalidate();
		
		ModelAndView modelandview=new ModelAndView("adminloginpage");
		model.addAttribute("message","Admin");
		return modelandview;
		
	}
	@RequestMapping("/adminauthentication")
	public ModelAndView adminAuthentication(@RequestParam("email") String email,@RequestParam("password") String password,Model model,HttpSession session)
	{
		session.invalidate();
		
		if(email.equals("admin@gmail.com") && password.equals("admin@123")) {
			ModelAndView modelandview=new ModelAndView("firstpage");
			
			//call
			logger.info("Call to Dao service for fetch records");
			
			
			
			Map<Integer, List<String>>userlist =(Map<Integer, List<String>>) daoclass.useradminlist(daoclass.fetchuserslist());


			System.out.println(userlist.keySet());
			logger.info("List Created");
			
			
			model.addAttribute("lists", userlist);
			model.addAttribute("message","true1");
			
			return modelandview;
		}
		else {
			ModelAndView modelandview=new ModelAndView("adminloginpage");
			model.addAttribute("message","Invalid Credentials");
			return modelandview;
		}
		
		
		
		
	}

	
}
