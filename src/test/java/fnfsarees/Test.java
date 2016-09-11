package fnfsarees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.webapp.models.ProjectModel;

public class Test {

	public static void main(String[] args) {
		
		String str = "this is \n value";
		System.out.println(String.valueOf(str));
		
		ProjectModel p=new ProjectModel();
		p.setTitle("asdasd");
		p.setBookingPrefix("BB");
		List<ProjectModel> prlist=new ArrayList<ProjectModel>();
		prlist.add(p);
		for (ProjectModel projectModel : prlist) {
			System.out.println("\n\t\t projectModel="+projectModel.getTitle());
		}
		
		
		Iterator<ProjectModel> pite=prlist.iterator();
		while(pite.hasNext()){
			ProjectModel pp=pite.next();
			System.out.println(pp.getTitle());
		}
	}
}