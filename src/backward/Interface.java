package backward;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class Interface extends JFrame implements ActionListener{
	
	/**
	 * 
	 */

	
	String l1=" ";
	String l2=" ";
	String l3=" ";
	String l4=" ";
	JPanel jp1, jp2;
	JButton jb1, jb2, jb3, jb4;
	Label ltime, lyear, lmonth, lday, lhour, lminute, lplace;
	TextField tfyearnum;
	Choice cmonth, cday, chour, cminute, cfloor, cnum;
	JButton jsearch;
	public Interface(){
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		
		//下层作为显示
		jb1 = new JButton(l1);
		jb2 = new JButton(l2);
		jb3 = new JButton(l3);
		jb4 = new JButton(l4);
		jp2.setLayout(new GridLayout(2,2));
		jp2.add(jb1);
		jp2.add(jb2);
		jp2.add(jb3);
		jp2.add(jb4);
		
		//上层时间地点显示
		ltime = new Label("时间：");		
		tfyearnum = new TextField("2019",4);
		tfyearnum.setEditable(true);
		lyear= new Label("年");
		lmonth= new Label("月");
		lday= new Label("日");
		lhour= new Label("时");
		lminute= new Label("分");
		lplace= new Label("地点：");
		cmonth = new Choice();
		for (int i=1; i<=12; i++){
			cmonth.add(String.valueOf(i));
		}
		cday=new Choice();
		for (int i=1;i<=31;i++){
			cday.add(String.valueOf(i));
		}
		chour=new Choice();
		for (int i=0;i<=23;i++){
			chour.add(String.valueOf(i));
		}
		cminute=new Choice();
		for (int i=0;i<=59;i++){
			cminute.add(String.valueOf(i));
		}
		cfloor = new Choice();
		cfloor.add("一层");
		cfloor.add("二层");
		cfloor.add("三层");
		cfloor.add("四层");
		cfloor.add("五层");
		cfloor.add("六层");
		cfloor.add("七层");
		cfloor.add("八层");
		cnum = new Choice();
		for (char c='A'; c<='J';c++){
			cnum.add("图书空间"+c);
		}
		jsearch = new JButton("查询");
		jp1.setLayout(new FlowLayout());
		jp1.add(ltime);
		jp1.add(tfyearnum);
		jp1.add(lyear);
		jp1.add(cmonth);
		jp1.add(lmonth);
		jp1.add(cday);
		jp1.add(lday);
		jp1.add(chour);
		jp1.add(lhour);
		jp1.add(cminute);
		jp1.add(lminute);
		jp1.add(lplace);
		jp1.add(cfloor);
		jp1.add(cnum);
		jp1.add(jsearch);
		jsearch.addActionListener(this);
		this.add(jp1, BorderLayout.NORTH);
		this.add(jp2, BorderLayout.CENTER);
		this.setSize(800,800);
		this.setLocation(200,200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	static boolean check (String str) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");//括号内为日期格式，y代表年份，M代表年份中的月份（为避免与小时中的分钟数m冲突，此处用M），d代表月份中的天数
		try {
			sd.setLenient(false);//此处指定日期/时间解析是否不严格，在true是不严格，false时为严格
			sd.parse(str);//从给定字符串的开始解析文本，以生成一个日期
		}
		catch (Exception e) {
			return false;
		}
		return true;
	}
	static int cumputeNum(String s1, String s2){
		int result=0;
		int i=0;
		int j=0;
		if(s1.equals("一层")){
			i=0;
		}else if(s1.equals("二层")){
			i=1;
		}else if(s1.equals("三层")){
			i=2;
		}else if(s1.equals("四层")){
			i=3;
		}else if(s1.equals("五层")){
			i=4;
		}else if(s1.equals("六层")){
			i=5;
		}else if(s1.equals("七层")){
			i=6;
		}else if(s1.equals("八层")){
			i=7;
		}
		if(s2.equals("图书空间A")){
			j=1;
		}else if(s2.equals("图书空间B")){
			j=2;
		}else if(s2.equals("图书空间C")){
			j=3;
		}else if(s2.equals("图书空间D")){
			j=4;
		}else if(s2.equals("图书空间E")){
			j=5;
		}else if(s2.equals("图书空间F")){
			j=6;
		}else if(s2.equals("图书空间G")){
			j=7;
		}else if(s2.equals("图书空间H")){
			j=8;
		}else if(s2.equals("图书空间I")){
			j=9;
		}else if(s2.equals("图书空间J")){
			j=10;
		}
		result = i*10+j;
		return result;
	}
	
	public void freshen(){
		String year = tfyearnum.getText();
		String month = cmonth.getSelectedItem();
		String day = cday.getSelectedItem();
		String hour = chour.getSelectedItem();
		String minute = cminute.getSelectedItem();
		String floor = cfloor.getSelectedItem();
		String num = cnum.getSelectedItem();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String datetime = year+"-"+month+"-"+day+"-"+hour+"-"+minute+"-"+"00";
		if(check(datetime)){
			Date datet = null;
	        try {
	            datet = f.parse(datetime);
	        } catch (ParseException e2) {
	            e2.printStackTrace();
	        }
	        int inputplace = cumputeNum(floor,num);
	        checkingseats ch = new checkingseats();
	        List<String> finaOutput = new ArrayList<String>();
	        finaOutput = ch.outputFinaInfo(inputplace, datet);
			l1 = finaOutput.get(0);
			l2 = finaOutput.get(1);
			l3 = finaOutput.get(2);
			l4 = finaOutput.get(3);
			jb1.setText(l1);
			jb2.setText(l2);
			jb3.setText(l3);
			jb4.setText(l4);
		}else{
			System.out.println("Wrong Date Format!");
			JOptionPane.showMessageDialog(null, "日期【错误】", "格式输入错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		if(e.getSource()==jsearch){
			freshen();
		}
	}
	public static void main(String[] args){
		Interface d= new Interface();
	}

}

