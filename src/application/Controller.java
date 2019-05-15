package application;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import backward.Interface;
import backward.checkingseats;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller{
	
    
	@FXML
	private Label info_seatA;
	@FXML
	private Label info_seatB;
	@FXML
	private Label info_seatC;
	@FXML
	private Label info_seatD;
	@FXML
	private Button button;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox ComboBox_hour;
	@FXML
	private ComboBox ComboBox_minute;
	@FXML
	private ComboBox ComboBox_Room;
	@FXML
	private Label label_room;
	
	
	Interface interf;

	
	
	
	@FXML
	private void onButtonClick1() {//获取时间、房间号信息
		try {
			//获取日期
			LocalDate localDate=datePicker.getValue();
			ZoneId zone = ZoneId.systemDefault();
		    Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		    java.util.Date date = Date.from(instant);
		    //获取小时
		    String hour=(String) ComboBox_hour.getValue();
		    int h= Integer.parseInt(hour);
		    //获取分钟
		    String minute=(String) ComboBox_minute.getValue();
		    int m=Integer.parseInt(minute);

		    date.setHours(h);
		    date.setMinutes(m);
		    
		    //System.out.print(date);
		    
		    //获取房间号
			String room=(String) ComboBox_Room.getValue();
			//显示房间号
			label_room.setText(room);
			int roomid=Integer.parseInt(room);
			
			//获取该房间座位信息列表
			checkingseats cs=new checkingseats();
			List<String> finaOutput=cs.outputFinaInfo(roomid, date);

			//显示4个座位信息
			info_seatA.setWrapText(true);
			info_seatB.setWrapText(true);
			info_seatC.setWrapText(true);
			info_seatD.setWrapText(true);
			info_seatA.setText(finaOutput.get(0));
			info_seatB.setText(finaOutput.get(1));
			info_seatC.setText(finaOutput.get(2));
			info_seatD.setText(finaOutput.get(3));			
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	




}
