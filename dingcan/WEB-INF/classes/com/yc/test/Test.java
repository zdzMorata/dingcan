package com.yc.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

public class Test {

	@org.junit.Test
	public void test() {
		fail("Not yet implemented");
	}
	@org.junit.Test
	 public void test1() {
	        //字符串转list<String>
	        String str = "asdfghjkl";
	        List<String> lis = Arrays.asList(str.split(""));
	        for (String string : lis) {
	            System.out.println(string);
	        }
	        //list<String>转字符串
	        System.out.println(String.join("", lis));
	}
}
