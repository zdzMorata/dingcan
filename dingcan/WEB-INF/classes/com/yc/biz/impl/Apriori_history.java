package com.yc.biz.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Apriori_history {

	public void ss(String pythonfilepath){
		try{
			String exe=System.getenv("ANCONDA_HOME")+File.separator+"python";
			
			String command = pythonfilepath+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"apriori_history.py";
			String [] cmdArr = new String[] {exe,command};
			Process process = Runtime.getRuntime().exec(cmdArr);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line=in.readLine())!=null){
				System.out.println("---"+line);
			}
			in.close();
			process.waitFor();
			System.out.println("end");
		}catch (IOException e) {
			e.printStackTrace();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
