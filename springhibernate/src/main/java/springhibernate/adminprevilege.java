package springhibernate;

import java.lang.ProcessBuilder.Redirect;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.AdminCred;
import com.pojo.MapperClass;

import springdao.DAOClass;


@Controller
public class adminprevilege {

	@Autowired
	DAOClass daoclass;
	
	MapperClass mcobject;
	

	Map<Integer, String>userlist;
	

	
	@RequestMapping(value="/deleteuser/{id}")
	public ModelAndView deletuser(@PathVariable("id") int id,Model model,HttpSession session,ModelMap map)
	{
		 System.out.println("session id"+session.getId());
		System.out.println(id);
		int result=daoclass.deleteuser(id);
		//model.addAttribute("Success","User Deleted");

		
		//implement listuser->fetchuser
		
		
		Map<Integer, List<String>>userlist=daoclass.useradminlist(daoclass.fetchuserslist());
		
		System.out.println(userlist.toString());
		ModelAndView modelandview=new ModelAndView("firstpage");
		model.addAttribute("lists",userlist);
		model.addAttribute("message", "true1");
		return modelandview;
		
		
	}
	
	@RequestMapping("/edit_user_details")
	public ModelAndView edit_user_details(@RequestParam("id") int id,@RequestParam("email") String email,@RequestParam("password") String password,Model model)
	{
		mcobject=new MapperClass();
		mcobject.setId(id);
		mcobject.setPassword(password);
		mcobject.setEmail(email);
		System.out.print(id+email+password);
		System.out.print(mcobject.getEmail());
		int i=daoclass.user_detail_update(mcobject);
		Map<Integer, List<String>>userlist=daoclass.useradminlist(daoclass.fetchuserslist());
		ModelAndView modelandview=new ModelAndView("firstpage");
		model.addAttribute("lists",userlist);
		if(i>0)
		{
			
			model.addAttribute("message","true1");
			model.addAttribute("UserMessage","Updated Successfully");
			return modelandview;
		}
		else
		{
			model.addAttribute("message","Error In Update");
			model.addAttribute("message","true1");
			model.addAttribute("UserMessage","Error In Updating");
			return modelandview;
		}
		
	}
	
	
	
	
}
