package com.common;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.pojo.OptionInfo;


public class ExportExcelUtil {
	
	/**
	 * �����������ļ�·����ȡ��Ŀ�е��ļ�
	 * @param fileDir �ļ�·��
	 * @return
	 * @throws Exception
	 */
	public  File getExcelDemoFile(String fileDir , String realPath) throws Exception{
		String classDir = null;
		String fileBaseDir = null;
		File file = null;
		classDir = Thread.currentThread().getContextClassLoader().getResource("/").getPath();
		fileBaseDir = classDir.substring(0, classDir.lastIndexOf("classes"));
		
		file = new File(realPath+fileDir);
		if(!file.exists()){
			throw new Exception("模板文件不存在");
		}
		return file;
	}
	
	public  Workbook writeNewExcel(File file,String sheetName,List<OptionInfo> lis) throws Exception{
		Workbook wb = null;
		Row row = null; 
		Cell cell = null;
		
		FileInputStream fis = new FileInputStream(file);
		wb = new ImportExcelUtil().getWorkbook(fis, file.getName());	//��ȡ������
		Sheet sheet = wb.getSheet(sheetName);
		
		//ѭ����������
		int lastRow = sheet.getLastRowNum()+1;    //�������ݵ�����ROW
		CellStyle cs = setSimpleCellStyle(wb);    //Excel��Ԫ����ʽ
		for (int i = 0; i < lis.size(); i++) {
			row = sheet.createRow(lastRow+i); //�����µ�ROW���������ݲ���
			
			//����Ŀʵ�������ڸô����������ݲ��뵽Excel��
			OptionInfo vo  = lis.get(i);
			if(null==vo){
				break;
			}
			//Cell��ֵ��ʼ
			cell = row.createCell(0);
			cell.setCellValue(vo.getClassInfo().getExperName());
			cell.setCellStyle(cs);
			
			cell = row.createCell(1);
			cell.setCellValue(vo.getUser().getUsername());
			cell.setCellStyle(cs);
			
			cell = row.createCell(2);
			cell.setCellValue(vo.getClassInfo().getClassDate());
			cell.setCellStyle(cs);
			
			cell = row.createCell(3);
			cell.setCellValue(vo.getClassInfo().getDuration());
			cell.setCellStyle(cs);
			
			cell = row.createCell(4);
			String states = "";
			switch (vo.getStates()) {
			case "1":
				states = "已选课";
				break;
			case "0":
				states = "已取消";
				break;
			case "2":
				states = "已提交报告";
				break;
			case "3":
				states = "已评价";
				break;
			default:
				break;
			}
			cell.setCellValue(states);
			cell.setCellStyle(cs);
			
			
		}
		return wb;
	}
	
	/**
	 * ���������ü򵥵�Cell��ʽ
	 * @return
	 */
	public  CellStyle setSimpleCellStyle(Workbook wb){
		CellStyle cs = wb.createCellStyle();
		
		cs.setBorderBottom(CellStyle.BORDER_THIN); //�±߿�
		cs.setBorderLeft(CellStyle.BORDER_THIN);//��߿�
		cs.setBorderTop(CellStyle.BORDER_THIN);//�ϱ߿�
		cs.setBorderRight(CellStyle.BORDER_THIN);//�ұ߿�

		cs.setAlignment(CellStyle.ALIGN_CENTER); // ����
		
		return cs;
	}

}
