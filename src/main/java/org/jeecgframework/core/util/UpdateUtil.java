package org.jeecgframework.core.util;

import java.lang.reflect.Field;

import com.jinbo.customer.entity.customerservice.CustomerSerEntity;

public class UpdateUtil {
  public static  <T> T update(T old_entity,T new_entity) throws IllegalArgumentException, IllegalAccessException{
	 Field[] fields =  old_entity.getClass().getDeclaredFields();
	 for(Field f : fields){
		 f.setAccessible(true);
		 Class<?> type = f.getType();
		 if(type.toString().endsWith("java.lang.String")){
			 			String old =  (String) f.get(old_entity);
			 			String ne = (String) f.get(new_entity);
			 	       if(ne!=null&&ne.length()>0&&!old.equalsIgnoreCase(ne)){
			 	    	   f.set(old_entity, ne);			 	    	   
			 	       }else{
			 	    	   continue;
			 	       }
			 			
		 }else if(type.toString().endsWith("java.util.Date")){
			 			 
		 }else if(type.toString().endsWith("java.lang.Integer")){
				Integer old =  (Integer) f.get(old_entity);
				Integer ne =  (Integer) f.get(new_entity);
	 	       if(ne!=null&&ne!=0&&old!=ne){
	 	    	   f.set(old_entity, ne);			 	    	   
	 	       }else{
	 	    	   continue;
	 	       }
		 }

	 }
	  return old_entity;
  }
  
  public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
	  CustomerSerEntity c = new CustomerSerEntity();
	  c.setAcontent("旧的");
	  c.setAtype(1);
	  CustomerSerEntity c1 = new CustomerSerEntity();
	  c1.setAcontent("新的");
	  c1.setAtype(3);
	  update(c,c1);
	  System.out.println("更新后是:"+c.getAcontent()+":"+c1.getAcontent());
	  System.out.println("更新后是:"+c.getAtype()+":"+c1.getAtype());
  }
}
