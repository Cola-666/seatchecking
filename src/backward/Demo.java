package backward;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Demo {
	private String lessontablefilepath = "课程总表.xls";
	private List<Integer> schoolday = new ArrayList<Integer>();
	private List<Integer> getweek(int x,int y) {               //得到课程持续的星期
	int[] week = new int[2];
	FileInputStream is;
	try {
		is = new FileInputStream(lessontablefilepath);
		HSSFWorkbook excel=new HSSFWorkbook(is);
		HSSFSheet sheet0=excel.getSheetAt(0);
		HSSFRow hssfRow = sheet0.getRow(x);
		HSSFCell cell = hssfRow.getCell(y);
		String classInfo = cell.getStringCellValue();
		if(classInfo=="") {
			System.out.println("没课");
			week[0] = 0;
			week[1] = 0;
			return schoolday;
		}
		System.out.println(classInfo);
		String[] s = classInfo.split("\n");
		int len = s.length/5;
		for(int i = 1;i<=len;i++) {
			if(s[i*5-1].indexOf("单周")!=-1) {
				String[] s1 = s[i*5-1].split("周");
				int a = s1[0].indexOf("-");
				String[] s3 = new String[2];
				s3[0] = s1[0].substring(1, a);
				s3[1] = s1[0].substring(a+1);
				int n1 = Integer.parseInt(s3[0]);
				int n2 = Integer.parseInt(s3[1]);
				int x1;
				if(n1%2==1) {
					x1 = n1;
				}else {
					x1 = n1+1;
				}
				while(x1<=n2){
					schoolday.add(x1);
					x1 = x1 + 2;
				}
				System.out.println(s3[0]+" "+s3[1]);
			}else if(s[i*5-1].indexOf("双周")!=-1) {
				String[] s1 = s[i*5-1].split("周");
				int a = s1[0].indexOf("-");
				String[] s3 = new String[2];
				s3[0] = s1[0].substring(1, a);
				s3[1] = s1[0].substring(a+1);
				int n1 = Integer.parseInt(s3[0]);
				int n2 = Integer.parseInt(s3[1]);
				int x1;
				if(n1%2==0) {
					x1 = n1;
				}else {
					x1 = n1+1;
				}
				while(x1<=n2){
					schoolday.add(x1);
					x1 = x1 + 2;
				}
				System.out.println(s3[0]+" "+s3[1]);
			}else {
				String[] s1 = s[i*5-1].split("周");
				int a = s1[0].indexOf("-");
				String[] s3 = new String[2];
				s3[0] = s1[0].substring(1, a);
				s3[1] = s1[0].substring(a+1);
				int n1 = Integer.parseInt(s3[0]);
				int n2 = Integer.parseInt(s3[1]);
				int x1 = n1;
				while(x1<=n2){
					schoolday.add(x1);
					x1 = x1 + 1;
				}
				System.out.println(s3[0]+" "+s3[1]);
			
			}
		}
		
		
		//System.out.println(s.length);
		
		
		}catch (Exception e) {
		// TODO: handle exception
		}
	for(int i = 1;i<schoolday.size();i++) {
		System.out.println(schoolday.get(i));
	}
	return schoolday;
	}
	public static void main(String[] args) {
		new Demo().getweek(17, 4);
	}
}
