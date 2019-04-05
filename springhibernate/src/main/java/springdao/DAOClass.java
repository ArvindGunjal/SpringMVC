package springdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.hibernate.Query;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import org.apache.commons.logging.impl.Log4JLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.Connection;
import com.pojo.MapperClass;

import springhibernate.IndexController;

@Component
public class DAOClass {


	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void register(MapperClass mpclass)
	{
		
		Session session=sessionFactory.getCurrentSession();
		try {
			
			session.save(mpclass);
			
			
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		finally {
//		session.close();
		}
		
		System.out.println("Run"+mpclass);
	}
	
	@Transactional
	public boolean loginsearch(MapperClass mpclass)
	{
	
		Session session=sessionFactory.openSession();
		try {
			
			//call to store procedure search_user
			StoredProcedureQuery procedurecall = session.createStoredProcedureQuery("search_user");
			procedurecall.registerStoredProcedureParameter("email", String.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter("upassword", String.class, ParameterMode.IN);
			procedurecall.registerStoredProcedureParameter("result", Integer.class, ParameterMode.OUT);
			procedurecall.setParameter("email", mpclass.getEmail());
			procedurecall.setParameter("upassword",mpclass.getPassword());
 
			
			procedurecall.execute();
			
			
			int result=(Integer) procedurecall.getOutputParameterValue("result");
			if(result>0)
			{
			
				return true;
			}
			else {
				return false;
			}
			
			}
		
		catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
		finally {
			
		session.close();
		
		}
	}
	
	public List fetchuserslist() {
		Session session=sessionFactory.openSession();
		 List l=null;
		try {
			
			l=session.createQuery("from MapperClass").list();
	        
	         return l;
			
		
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return l;
		}
		finally {
			session.close();
			return l;
		}

			
	}
	
	

	public int deleteuser(int id) {
		// TODO Auto-generated method stub
		Session session=sessionFactory.openSession();
		int result=0;
		try {
			
			System.out.println(id);
			session.beginTransaction();
			String strquery=
					"delete from MapperClass where id = :id";
			Query query=session.createQuery(strquery);
			
			query.setParameter("id", id);
			
			System.out.print(query.executeUpdate());
			session.getTransaction().commit();
			 
			return result;
			
			
		}
		catch (Exception e) {
			System.out.println(e);
			
			// TODO: handle exception
		}
		finally {
			session.close();
		}
		return result;
	}
	


	public Map<Integer, List<String>> useradminlist(List listuser) {
		MapperClass retr = null;

		
		
		Map<Integer, List<String>> map=new HashMap<Integer, List<String>>();
		 for (Iterator iterator = listuser.iterator(); iterator.hasNext();) {
             retr =(MapperClass) iterator.next();
             map.put(retr.getId(), new ArrayList<String>(Arrays.asList(retr.getEmail(),retr.getPassword())));


        }	
		 
//		    
//		 	List<MapperClass>mapuser=new ArrayList<MapperClass>();
//		 	mapuser.add(retr);
		 
		 System.out.println("New User Details From useradmin"+map.toString());
		 
		 
		return map;
	}


	public int user_detail_update(MapperClass map)
	{
		Session session = sessionFactory.openSession();
		int i=0;
		
		try {
			
			System.out.println("Exce");
			String sqlquery="update MapperClass set email = :email,upassword = :password where id = :id";
			Query query=session.createQuery(sqlquery);
			query.setParameter("id", map.getId());
			query.setParameter("email", map.getEmail());
			query.setParameter("password", map.getPassword());
			session.beginTransaction();
			i=query.executeUpdate();
			session.getTransaction().commit();
			return i;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception "+e);
		}
		return i;
	}
	
}
