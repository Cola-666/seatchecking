package backward;

import java.util.Date;

public class SeatInfo {
	private int seatId;        //��λ���1��2��3��4
	private boolean isOccupied;    //�Ƿ�ռ��
	private boolean isDistributed;   //�Ƿ񱻷���
	private int minute;
	private String stuInfo;     //�����ѧ����Ϣ
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
