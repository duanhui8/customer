package org.jeecgframework.core.util;

public class RandomUtils
{
	private static String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
			"a","b","c","d"};
	public static String getUsername(int lenght)
	{
		StringBuffer str = new StringBuffer();
		str.append("2");
		for(int i = 0;i < lenght; i++)
		{
			Double number=Math.random()*(randomValues.length-1);
			str.append(randomValues[number.intValue()]);
		}
		
		return str.toString();
	}

	public static void main(String[] args)
	{
		System.out.println(RandomUtils.getUsername(8));
	}
}