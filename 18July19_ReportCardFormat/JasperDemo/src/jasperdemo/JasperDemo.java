package jasperdemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Math.abs;
import java.util.HashMap;
import java.util.Scanner;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class JasperDemo 
{
    public static void main(String[] args) 
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the rid : ");
        int rid=sc.nextInt();
        String query="select * from student where rid="+rid;
        try 
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","demo");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            String name="",course="";
            String grade="",status="";
            int eng=0,hin=0,sci=0,mat=0,sst=0;
            int min=0,max=0,sum=0,avg=0,sd=0;
            if(rs.next())
            {
              //  rid=rs.getInt(1);
                name=rs.getString(2);
                course=rs.getString(3);
                eng=rs.getInt(4);
                hin=rs.getInt(5);
                sci=rs.getInt(6);
                mat=rs.getInt(7);
                sst=rs.getInt(8);
                sum=eng+hin+sst+sci+mat;
                if((eng>=hin)&&(eng>=sci)&&(eng>=mat)&&(eng>=sst))
                    max=eng;
                else if((hin>=eng)&&(hin>=sci)&&(hin>=mat)&&(hin>=sst))
                    max=hin;
                else if((sci>=eng)&&(sci>=hin)&&(sci>=mat)&&(sci>=sst))
                    max=sci;
                else if((mat>=eng)&&(mat>=sci)&&(mat>=hin)&&(mat>=sst))
                    max=mat;
                else
                    max=sst;
                
                if((eng<=hin)&&(eng<=sci)&&(eng<=mat)&&(eng<=sst))
                    min=eng;
                else if((hin<=eng)&&(hin<=sci)&&(hin<=mat)&&(hin<=sst))
                    min=hin;
                else if((sci<=eng)&&(sci<=hin)&&(sci<=mat)&&(sci<=sst))
                    min=sci;
                else if((mat<=eng)&&(mat<=sci)&&(mat<=hin)&&(mat<=sst))
                    min=mat;
                else
                    min=sst;
                avg=sum/5;
                sd=(abs(eng-avg)+abs(hin-avg)+abs(sst-avg)+abs(sci-avg)+abs(mat-avg))/5;
            
            if(sum>250)
                status="PASS";
            else
                status="FAIL";
            
            if(sum>400)
                grade="A";
            else if(sum>350 && sum<=400)
                grade="B";
            else if(sum>300 && sum<=350)
                grade="C";
            else if(sum>250 && sum<=300)
                grade="D";
            else
                grade="F";
            
            System.out.println("Welcome "+name);
            System.out.println("RID : "+rid+"\nCourse : "+course);
            System.out.println("English : "+eng+"\nHindi : "+hin);
            System.out.println("Math : "+mat+"\nScience : "+sci);
            System.out.println("SST : "+sst+"\n===============");
            System.out.println("Min : "+min+"\nMax : "+max+
                    "\nSum : "+sum+"\nAverage : "+avg+
                    "\nStandard Deviation : "+sd);
                System.out.println("\n=====================\n\n");
                try 
                {
                    HashMap params=new HashMap();
                    params.put("rid",Integer.toString(rid));
                    params.put("name",name);
                    params.put("course",course);
                    params.put("eng",Integer.toString(eng));
                    params.put("hin",Integer.toString(hin));
                    params.put("mat",Integer.toString(mat));
                    params.put("sci",Integer.toString(sci));
                    params.put("sst",Integer.toString(sst));
                    params.put("min",Integer.toString(min));
                    params.put("max",Integer.toString(max));
                    params.put("total",Integer.toString(sum));
                    params.put("sd",Integer.toString(sd));
                    params.put("avg",Integer.toString(avg));
                    params.put("grade",grade);
                    params.put("status",status);
                    
                    JasperReport jr=JasperCompileManager.compileReport("C:\\Users\\Admin\\Documents\\NetBeansProjects\\JasperDemo\\src\\reports\\slip.jrxml");
                    JasperPrint jp=JasperFillManager.fillReport(jr,params,con);
                    JasperViewer.viewReport(jp);
                    //jv.setVisible(true);
                } 
                catch (JRException ex) 
                {
                    Logger.getLogger(JasperDemo.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Exception : "+ex);
                }
            }
            stmt.close();
                    rs.close();
                    con.close();
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(JasperDemo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception : "+ex.getMessage());
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(JasperDemo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception : "+ex.getMessage());
        }
            
    }
    
}
