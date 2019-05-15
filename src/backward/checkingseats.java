package backward;

import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class checkingseats {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private int roomId;
	private List<SeatInfo> roomseat = new ArrayList<SeatInfo>();
	private List<String> finaOutput = new ArrayList<String>();
	private Date currentTime = new Date();
	private String lessontablefilepath = "课程总表.xls";
	private String seatfilepath = "座位分配表.xls";
	private List<Integer> schoolday1;
	
	
	public List<SeatInfo> getRoomseat() {
		return roomseat;
	}

	public void setRoomseat(List<SeatInfo> roomseat) {
		this.roomseat = roomseat;
	}

	public void seatinfochecking(int roomid,Date checkingDate) {
		roomseat = getseatInfo(roomid);
		int[] a1 = getstunum();
		
		for(int l = 0;l<a1.length;l++) {
			System.out.println(a1[l]);
		}
		for(int num = 0;num<roomseat.size();num++) {
			int classnum = getclassnum(checkingDate);
			if(a1[num]!=0) {
				schoolday1 = getweek(a1[num], classnum);
				int currrentweek = getweeknum(checkingDate);
				if(!schoolday1.contains(currrentweek)) {
					roomseat.get(num).setOccupied(true);
				}else {
					roomseat.get(num).setOccupied(false);
					int day_class_num = classnum%5;
					switch(day_class_num) {
					case 1:{
						Calendar cal = Calendar.getInstance();
						cal.setTime(checkingDate); 
						// 获得小时
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						// 获得分钟
						int minute = cal.get(Calendar.MINUTE);
						if(hour<9) {
							roomseat.get(num).setMinute(60-minute+40);
						}else if(hour == 9) {
							roomseat.get(num).setMinute(40-minute);
						}
					}
					case 2:{
						Calendar cal = Calendar.getInstance();
						cal.setTime(checkingDate); 
						// 获得小时
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						// 获得分钟
						int minute = cal.get(Calendar.MINUTE);
				
						if(hour<11) {
							roomseat.get(num).setMinute(60-minute+40);
						}else if(hour == 11) {
							roomseat.get(num).setMinute(40-minute);
						}
						
					}
					case 3:{
						Calendar cal = Calendar.getInstance();
						cal.setTime(checkingDate); 
						// 获得小时
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						// 获得分钟
						int minute = cal.get(Calendar.MINUTE);
				
						if(hour==13) {
							roomseat.get(num).setMinute(60-minute+70);
						}else if(hour == 14) {
							roomseat.get(num).setMinute(60-minute+10);
						}else if(hour == 15) {
							roomseat.get(num).setMinute(10-minute);
						}
						
					}
					case 4:{
						Calendar cal = Calendar.getInstance();
						cal.setTime(checkingDate); 
						// 获得小时
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						// 获得分钟
						int minute = cal.get(Calendar.MINUTE);
				
						if(hour==15) {
							roomseat.get(num).setMinute(60-minute+70);
						}else if(hour == 16) {
							roomseat.get(num).setMinute(60-minute+10);
						}else if(hour == 17) {
							roomseat.get(num).setMinute(10-minute);
						}
						
					}	
					case 5:{
						Calendar cal = Calendar.getInstance();
						cal.setTime(checkingDate); 
						// 获得小时
						int hour = cal.get(Calendar.HOUR_OF_DAY);
						// 获得分钟
						int minute = cal.get(Calendar.MINUTE);
				
						if(hour==18) {
							roomseat.get(num).setMinute(60-minute+70);
						}else if(hour == 19) {
							roomseat.get(num).setMinute(60-minute+10);
						}else if(hour == 20) {
							roomseat.get(num).setMinute(10-minute);
						}
						
					}
					default :System.out.println("出现错误");
						
					}
				}
			}
			
			
			
		}
	}
	
	
	public List<String> outputFinaInfo(int roomid,Date checkingDate){  //最终调用此函数，获得String列表。
		List<String> finaOutput = new ArrayList<String>();
		seatinfochecking(roomid,checkingDate);
		for(int i = 0;i<roomseat.size();i++) {
			String info = "";
			if(!roomseat.get(i).isDistributed()) {
				info = info+"此座位未被分配！";
			}else if(roomseat.get(i).isOccupied()) {
				info = info+"此座位已被"+roomseat.get(i).getStuInfo()+"的学生占用";
			}else if(!roomseat.get(i).isOccupied()) {
				info = info+"此座位暂未被占用，剩余时间"+roomseat.get(i).getMinute()+"分钟。";
			}
			finaOutput.add(info);
			//System.out.println(info);
			
		}
		System.out.println(finaOutput);
		return finaOutput;
	}
	public List<SeatInfo> getseatInfo(int room) {       //得到座位表中room房间号的座位信息
		FileInputStream is;
		List<SeatInfo> roomseatinfo = new ArrayList<SeatInfo>();
		String[] seatInfo = new String[4];
		try {
			is = new FileInputStream(seatfilepath);
			HSSFWorkbook excel=new HSSFWorkbook(is);
			HSSFSheet sheet0=excel.getSheetAt(0);
			for(int rowNum = 1; rowNum <= sheet0.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = sheet0.getRow(rowNum);
				HSSFCell cell = hssfRow.getCell(0);
				System.out.println(cell);
				if(cell.getNumericCellValue()==room+0.0) {
					for(int i = 1;i<=4;i++) {
						seatInfo[i-1] = hssfRow.getCell(i).getStringCellValue();
						SeatInfo si = new SeatInfo();
						si.setSeatId(i);
						if(seatInfo[i-1].equals("未分配")) {
							si.setDistributed(false);							
						}else {
							si.setDistributed(true);
							si.setStuInfo(seatInfo[i-1]);
						}
						roomseatinfo.add(si);
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return roomseatinfo;
	}
	
	
	
	
	public void completeSeatInfo(Date date) {
		
	}
	
	private List<Integer> getweek(int x,int y) {               //得到课程持续的星期
		List<Integer> schoolday = new ArrayList<Integer>();
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
				schoolday.add(0);
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
	
	public int getweeknum(Date date) {    //获取当前为第几周。
		int weeknum = 0;
		String date1 = "2019-03-04 00:00:00";
		try {
			Date date2 = sdf.parse(date1);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date2);
			long time1 = cal.getTimeInMillis();               
		    cal.setTime(date);  
		    long time2 = cal.getTimeInMillis();       
		    long between_days=(time2-time1)/(1000*3600*24);
		    weeknum = (int) ((between_days/7)+1);
		    System.out.println(between_days);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return weeknum;
		
	}
	
	public int getclassnum(Date date) {    //获取当前时间为周几的第几节课
		int classnum = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 获得小时
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		// 获得分钟
		int minute = cal.get(Calendar.MINUTE);
		if(day<=6&&day>=2) {
			
			if(hour==8||(hour==9&&minute>=0&&minute<=40)) {
				classnum = (day-2)*5+1;
			}else if(hour==10||(hour==11&&minute>=0&&minute<=40)) {
				classnum = (day-2)*5+2;
			}else if((hour==13&&minute>=30&&minute<=60)||(hour==15&&minute>=0&&minute<=10)||hour==14) {
				classnum = (day-2)*5+3;
			}else if((hour==15&&minute>=30&&minute<=60)||(hour==17&&minute>=0&&minute<=10)||hour==16) {
				classnum = (day-2)*5+4;
			}else if((hour==18&&minute>=30&&minute<=60)||(hour==20&&minute>=0&&minute<=10)||hour==19) {
				classnum = (day-2)*5+5;
			}
		}
		System.out.println(classnum);
		return classnum;
	}
	
	public int[] getstunum() {       //获取学生所在课表中的行号
		int[] stunum = new int[4];
		FileInputStream is;
		try {
			is = new FileInputStream(lessontablefilepath);
			HSSFWorkbook excel=new HSSFWorkbook(is);
			HSSFSheet sheet0=excel.getSheetAt(0);
			for(int rowNum = 1; rowNum <= sheet0.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = sheet0.getRow(rowNum);
				HSSFCell cell = hssfRow.getCell(0);
				if (cell == null) {
					continue;
				}
				for(int i = 0;i<roomseat.size();i++) {
					if(roomseat.get(i).isDistributed()) {
						if(roomseat.get(i).getStuInfo().equals(cell.getStringCellValue())) {
							stunum[i] = rowNum;
						}
					}else {
						stunum[i] = 0;
					}
				}
			}
			for(int e = 0;e < roomseat.size();e++) {
				System.out.println(roomseat.get(e).getStuInfo());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stunum;
	}
	
	public void test() {
		for(int i = 0;i<roomseat.size();i++) {
			System.out.println("座位号："+roomseat.get(i).getSeatId());
			System.out.println("学生信息："+roomseat.get(i).getStuInfo());
			System.out.println("是否分配："+roomseat.get(i).isOccupied());
			System.out.println("是否占用："+roomseat.get(i).isOccupied());
			System.out.println("剩下时间："+roomseat.get(i).getMinute());
		}
	}
	
	public static void main(String[] args) {
		String date1 = "2019-05-06 16:15:00";
		try {
			Date date2 = sdf.parse(date1);
			checkingseats c = new checkingseats();
			c.outputFinaInfo(5,date2);
			c.test();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
