package com.yc.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yc.commons.FileCreat;

public class Deal {

	public String tuijian(String cids,String pythonfilepath){
		String result=null;
		try{
//			String exe=System.getenv("ANCONDA_HOME")+File.separator+"python";
			String exe="python";
			String command = pythonfilepath+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"apriori_tui.py";
			String [] cmdArr = new String[] {exe,command,cids};
			Process process = Runtime.getRuntime().exec(cmdArr);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line=in.readLine())!=null){
				System.out.println(line);
				result=line;
			}
			in.close();
			process.waitFor();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
}
