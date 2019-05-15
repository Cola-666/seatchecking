package backward;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//此类进行了对课程表的读取并生产作为分配表
public class createSeatTable {
	private List<String> stu_list = new ArrayList<String>();
	private String infilepath = "课程总表.xls";
	private String outfilepath = "座位分配表.xls";
	
	
	public void inxls() {

		FileInputStream is;
		try {
			is = new FileInputStream(infilepath);
			HSSFWorkbook excel=new HSSFWorkbook(is);
			HSSFSheet sheet0=excel.getSheetAt(0);
			for(int rowNum = 4; rowNum <= sheet0.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = sheet0.getRow(rowNum);
				HSSFCell cell = hssfRow.getCell(0);
				if (cell == null) {
					continue;
				}
				stu_list.add(cell.getStringCellValue());
				//System.out.println(cell.getStringCellValue());
			}
			
//			for (int rowNum = 1; rowNum <= sheet0.getLastRowNum(); rowNum++) {
//				HSSFRow hssfRow = sheet0.getRow(rowNum);
//				int minColIx = hssfRow.getFirstCellNum();
//                int maxColIx = hssfRow.getLastCellNum();
//                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
//                    // HSSFCell 表示单元格
//                    HSSFCell cell = hssfRow.getCell(colIx);
//                    if (cell == null) {
//                        continue;
//                    }
//                    System.out.println(cell);
//                }
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	
	}
	
	
	public void outStudentsTable() {                        //生成座位分配表，按照学号顺序依次分配，并将第三个作为空出作为备用。
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			FileOutputStream out = new FileOutputStream(outfilepath);
			HSSFRow row ;
			row = sheet.createRow(0);
			HSSFCell cell;
			cell=row.createCell(0);
			cell.setCellValue("房间号");
			for(int i = 1;i<5;i++) {
				cell=row.createCell(i);
				cell.setCellValue("座位"+(i));
			}
			int count = stu_list.size()/3;
			int seat_num = 0;
			for(int k = 1;k<=count+1;k++) {
				row = sheet.createRow(k);
				for(int j = 0;j<5;j++) {
					cell=row.createCell(j);
					if(j==3) {
						cell.setCellValue("未分配");
						continue;
					}
					if(j==0) {
						cell.setCellValue(k);
						continue;
					}
					if(seat_num>=stu_list.size()) {
						break;
					}
					cell.setCellValue(stu_list.get(seat_num));
					System.out.println(stu_list.get(seat_num));
					seat_num = seat_num + 1;
				}
			}
			
			
			workbook.write(out);
			out.close();
			System.out.println("OK!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		createSeatTable cre = new createSeatTable();
		cre.inxls();
		cre.outStudentsTable();
	}

}
