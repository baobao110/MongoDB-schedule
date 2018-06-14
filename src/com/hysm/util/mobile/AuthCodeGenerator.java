package com.hysm.util.mobile;

public class AuthCodeGenerator
{
	
	public static  String randomAuthCode()
	{
		int r = (int)(Math.random()*10000000+100000)%1000000;
		if(r<100000)
		{
			r += 100000;
		}
		return String.valueOf(r);
	}
	
	
	public static void main(String[] args)
	{
		for(int i = 0;i<20;i++)
		{
			System.out.println(AuthCodeGenerator.randomAuthCode());
		}
	}

}
