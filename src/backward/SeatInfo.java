package backward;

import java.util.Date;

public class SeatInfo {
	private int seatId;        //座位编号1、2、3、4
	private boolean isOccupied;    //是否被占用
	private boolean isDistributed;   //是否被分配
	private int minute;
	private String stuInfo;     //分配的学生信息
	public int getSeatId() {
		return seatId;
	}
	public String getStuInfo() {
		return stuInfo;
	}
	public void setStuInfo(String stuInfo) {
		this.stuInfo = stuInfo;
	}
	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}
	public boolean isOccupied() {
		return isOccupied;
	}
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	public boolean isDistributed() {
		return isDistributed;
	}
	public void setDistributed(boolean isDistributed) {
		this.isDistributed = isDistributed;
	}
	
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	

}
