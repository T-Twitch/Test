package com.yuanlin.cn;

/**
*—ß…˙¿‡
*
*/
import java.io.*;
public class Student implements Serializable
{

	private int number = 0;
	private String name = "noName";
	private int age = 0;
	private String grade = "noGrade";
	private String myClass = "noClass";
	private String school = "noSchool";
	public Student()
	{

	}
	public int getNumber()
	{
		return this.number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public int getAge()
	{
		return this.age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	public String getGrade()
	{
		return this.grade;
	}
	public void setGrade(String grade)
	{
		this.grade = grade;
	}
	public String getMyClass()
	{
		return this.myClass;
	}
	public void setMyClass(String myClass)
	{
		this.myClass = myClass;
	}

	public String getSchool()
	{
		return this.school;
	}
	public void setSchool(String school)
	{
		this.school = school;
	}
}
	
