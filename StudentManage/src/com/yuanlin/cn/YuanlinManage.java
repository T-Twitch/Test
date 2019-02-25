package com.yuanlin.cn;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


public class YuanlinManage
{
	public Iterator ite;
	public static String str;
	public boolean boo;
	public static final int ADD_STU = 0;//添加
	public static final int SEARCH_STU = 1;//查询
	public static final int CHANGE_STU = 2;//修改
	public static final int DELETE_STU =3;//删除
	public static final int EXIT_SYS = 4;//退出程序
	public int yourChoose = 0;//你的选择
	private static int temp;//标示符，表示存入了多少个学生信息到文件中
	private FileOutputStream fos_t;//输出流	
	private FileInputStream fis_t;//输入流
	//控制台输入引用Scanner 从键盘获取数据
	public Scanner sc;
	//学生对象包含的基本属性
	public Student stu;//学生类集合
	public int number;//学号
	public String name;//姓名
	public int age;//年龄
	public String grade;//年级
	public String myClass;//班级
	public String school;//学校
	//文件输入输出流和对象输入输出流的引用
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private static List<Student> listOfStudent;//链表对象的引用



	/**
	*构造函数，用来初始化必要的基本信息，
	*控制台输入对象、链表、对象输入流，如果文件中存有信息就把信息读入到链表中，方便后续操作。
	*/
	public YuanlinManage()
	{
		try
		{
			sc = new Scanner(System.in);//在控制台输入信息
			listOfStudent = new ArrayList<Student>();//链表对象

			fis_t = new FileInputStream("student.txt");//文件输入流
			
			temp = readTemp();
			if(temp < 1)//文件为空则不读取，否则会抛出EOFException 异常
			{
				System.out.println("文件是空的！");
			}
			else//如果文件不空，就把文件中的内容读入到链表中
			{
				ois = new ObjectInputStream(fis_t);//对象输入流
				for(int i = 0; i < temp; i++)//循环遍历
				{
					stu = (Student)ois.readObject();//读取文件数据
					listOfStudent.add(i,stu);//第i个学生的stu添加到listOfStudent链表对象中   listOfStudent：链表对象
				}
			}
		}
		catch(Exception e)  //异常处理
		{
			e.printStackTrace();//如果异常捕获，并且输出错误的原因
		} 
	}
	/**打印函数*/
	public void print(Student stuS)
	{
		System.out.println("-------------------------------------------------------------------------");
			System.out.println("学号："+stuS.getNumber()+"  姓名："+stuS.getName()+
			"  年龄："+stuS.getAge()+" 年级："+stuS.getGrade()+"  班级："+
		stuS.getMyClass()+"  学校："+stuS.getSchool());
		
	}
	/**判断用户输入的是否合法的函数*/
	public boolean isOK(String str)
	{
		
		try//同样需要做异常捕获  异常捕获的格式为：try{....} catch{....}
			{
				yourChoose = Integer.parseInt(str);//获取键盘输入的数据
			}catch(NumberFormatException e)
			{
   				System.out.println("！！！！！！！！！！你输入的不是数字！！！！！！！！！！");
   				return false;//不合法 返回false
			}
			return true;//合法返回true
	}
	/**主菜单*/
	public void mainMenu()
	{
		while(true)
		{
			System.out.println("************************* 欢迎使用学生管理系统**************************************");
			System.out.println("请选择您要进行的操作：0、添加 1、查询  3、删除 4、退出！ ");
			System.out.println("\r\n");
			
			str = sc.next();//用户从键盘输入的数据 赋值给str
			boolean bb = isOK(str);//调用isOK函数来判断用户输入的是否为合法格式
			if(!bb)//bb返回true或者false 如果输入的格式不正确
				continue;//继续输入
			else//否则
				yourChoose = Integer.parseInt(str);//弹出子菜单
			switch(yourChoose)//菜单选项
			{
				case ADD_STU:
				//添加学生信息的方法
					addSTU();
					break;
				case SEARCH_STU:
					searchSTU();//查询学生的方法
					break;
				case DELETE_STU:
					deleteSTU();//删除学生的方法
					break;
				case EXIT_SYS://返回
					return;
				default:
					break;
			}
		}
	}

	/**判断学号是否已存在*/
	public boolean isExist(int number)//传入的参数为学号
	{
		Student stuE = null;
		int count = 0;//如果count=0说明学号不存在  如果count=1说明学号已存在
		ite = listOfStudent.iterator();//从文件中获取数据
		while(ite.hasNext())//hasNext：检查序列中是否还有元素   作用就是把所有的数据拿出来
		{
			stuE = (Student)ite.next();//指向下一个节点
			if(stuE.getNumber() == number)//如果传进来的参数等于序列中的学号，那就说明学号存在
			{
				count++;
				System.out.println("学号已存在，请重新输入！");
			}
		}
		if(count == 0)//判断conut的值  如果count=0返回false  如果count=1返回true
			return false;
		else
			return true;
	}
/**添加一个学生信息到链表*/
	public boolean addToList()
	{
		Student stuAdd = new Student();//实例化学生构造类
		//判断是否存在相同的学号，如果有则请重新输入学号
		System.out.println("当前共有学生" + temp + "名");
		System.out.println("请输入学号：");
		str = sc.next();
		boo = isOK(str);//判断输入是否合法
		if(!boo)
			return false;
		else
			number = Integer.parseInt(str);//获取下级菜单
		if(isExist(number))
			return false;
		System.out.println("请输入姓名：");
		name = sc.next();//输入姓名
		System.out.println("请输入年龄：");
		str = sc.next();//输入年龄
		boo = isOK(str);
		if(!boo)
			return false;
		else
			age = Integer.parseInt(str);
		System.out.println("请输入年级：");
		grade = sc.next();//输入年级
		System.out.println("请输入班级：");
		myClass = sc.next();//输入班级
		System.out.println("请输入学校：");
		school = sc.next();//输入学校
		//把添加的信息赋值给stuAdd
		stuAdd.setNumber(number);//把学号添加到学生构造类
		stuAdd.setName(name);//把姓名添加到学生构造类
		stuAdd.setAge(age);//把年龄添加到学生构造类
		stuAdd.setGrade(grade);//把年级添加到学生构造类
		stuAdd.setMyClass(myClass);//把班级添加到学生构造类
		stuAdd.setSchool(school);//把学号添加到学生构造类
		listOfStudent.add(temp,stuAdd);//把学生构造类写入到文件中
		System.out.println("添加成功！");
		return true;
	}
	/**添加学生信息*/
	public boolean addSTU()
	{
		if((boo = addToList()))
			{
				sequenceList();//对链表中的数据进行排序
				return true;
			}
		else return false;
	}
	/**读取temp的值，判断有多少个学生*/
	public int readTemp()
	{
		int temp = 0;
		try
		{
			FileInputStream fis_t = new FileInputStream("temp.txt");//temp.txt存的是一共有多少名学生
			temp = fis_t.read();//读取文件中的数据
			if(temp == -1)//如果为-1说明没有学生
			{
				temp = 0;
				System.out.println("目前没有学生");
			}
			else//否则就说明有学生
			{
				System.out.println("目前共有学生："+ temp + " 名");
			}
			fis_t.close();//关闭文件流
			
		}
		catch(Exception e)
		{
			e.printStackTrace();//如果捕获到异常就输入异常的原因
		}
		return temp;
	}
	/**写入temp的值，添加了学生 就要对temp.txt中存的学生数量进行改变*/
	public void writeTemp(int temp)//传入的参数的学生的数量
	{
		try
		{
			FileOutputStream fos_t = new FileOutputStream("temp.txt");//打开文件
			fos_t.write(temp);//覆盖之前的，重新写入
			fos_t.close();//关闭文件流
		}
		catch(Exception e)
		{
			e.printStackTrace();//如果捕获到异常就输入异常的原因
		}
	}
	/**把链表中的一个对象写入文件中*/
	public void writeToTxt(Student stuW)
	{
		try
		{
			fos_t = new FileOutputStream("student.txt");//文件流
			oos = new ObjectOutputStream(fos_t);//对象输出流
			for(int j = 0; j < listOfStudent.size(); j++)//循环遍历，listOfStudent.size()是这个表的大小。J<listOfStudent.size()就说明是链表的最后一个元素
			{
				stuW = listOfStudent.get(j);//添加
				oos.writeObject(stuW);//写入
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();//如果捕获到异常就输入异常的原因
		}
		finally
		{
			try{oos.close();}catch(Exception e1){e1.printStackTrace();}//关闭文件流。如果捕获到异常就输入异常的原因
		}
	}
	/**按学号查找学生信息*/
	public void searchWithNumber()
	{
		int count = 0;
		Student stuS = null;
		System.out.println("请输入学号：");
		str = sc.next();
		boo = isOK(str);//判断输入是否合法
		if(!boo)
			return;
		else
			number = Integer.parseInt(str);
		ite = listOfStudent.iterator();//使用方法 iterator()要求容器返回一个 Iterator。第一次调用Iterator 的next()方法时，它返回序列的是第一个元素
		while(ite.hasNext())//遍历所有
		{
			stuS = (Student)ite.next();//指向写一个
			if(stuS.getNumber() == number)//如果链表里面的学号和传进来的参数相等，说明找到了该学生，把学生的数据集合stuS返回
			{
				count++;//找到一个学生就加1
				print(stuS);
			}
		}
		if(count == 0)//没有找到学生
			System.out.println("找到0个学生！");
	}
	/**按姓名查找学生信息*/
	public void searchWithName()
	{
		int count = 0;
		Student stuS = null;
		System.out.println("请输入姓名：");
		String name = sc.next();
		ite = listOfStudent.iterator();
		while(ite.hasNext())
		{
			stuS = (Student)ite.next();
			
			if(stuS.getName().equals(name))
			{
				count++;
				print(stuS);
			}
		}
		if(count == 0)
			System.out.println("找到0个学生！");
	}
	
	/**查询所有*/
	public void searchAll()
	{
		Student stuS = null;
		System.out.println("所有学生信息：");
		ite = listOfStudent.iterator();
		while(ite.hasNext())
		{
			stuS = (Student)ite.next();
			print(stuS);
		}
	}
	/**次级菜单：查询学生信息*/
	public void searchSTU()
	{
		System.out.println("*********************你要按什么条件来查找学生信息？************************"+"\r\n");
		System.out.println("1、学号 2、姓名 3、打印所有学生信息 4、返回主菜单 "+"\r\n");
		str = sc.next();
		boo = isOK(str);
		if(!boo)
			return;
		else
			yourChoose = Integer.parseInt(str);
		switch(yourChoose)
		{
			case 1:
				searchWithNumber();//查找学号
				break;
			case 2:
				searchWithName();//查找姓名
				break;
			case 3:
				searchAll();//查找所有
				break;
			case 4:
				return;
			default:
				System.out.println("无效数字");
				break;
		}
	}
	
	/**对链表中的数据排序*******************************************************/
	public void sequenceList()
	{
		//生成一个比较器
		Comparator<Student> comparator = new Comparator<Student>()
		{
			public int compare(Student s1, Student s2) 
			{
			//按学号排序
				if(s1.getNumber()!=s2.getNumber())
					return s1.getNumber()-s2.getNumber();
				else
					return 0;
			}
		};
		Collections.sort(listOfStudent,comparator);//排序
		writeAll();
	}
	//对链表中的数据排序********************************************************
	/**所有学生信息全部写入到文件中*/
	public void writeAll()
	{
		temp = listOfStudent.size();
		writeTemp(temp);//写入新的temp的值到文件temp.txt中
		try
		{
			int i = 0;
			Student stuW = null;
			for(; i < listOfStudent.size(); i++)
			{
				stuW = listOfStudent.get(i);
				writeToTxt(stuW);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**删除学生信息*/
	public void deleteSTU()
	{
		int count = 0;
		Student stuD = null;
		System.out.println("请输入要删除的学生的学号:");
		str = sc.next();
		boo = isOK(str);
		if(!boo)
			return;
		else
			number = Integer.parseInt(str);
		ite = listOfStudent.iterator();
		while(ite.hasNext())
		{
			stuD = (Student)ite.next();
			if(stuD.getNumber() == number)
			{
				count++;
				ite.remove();//注意这里用的是Iterator类的删除方法，如果用listOfStudent.remove(stuD)会产生一个异常
							//ConcurrentModificationException，这个异常是由于迭代器和链表所表示的链表长度不一致产生的，
							//使用Iterator中的remove()方法则可以避免这种情况的产生，因为这个remove()方法统一了两个长度。
				print(stuD);
				System.out.println("此学生信息被永久删除！"+"\r\n");
			}
		}
		if(count == 0)
			{System.out.println("没有这个学生！"+"\r\n");return;}
		sequenceList();
	}
	/**入口函数*/
	public static void main(String args[])
	{
		new YuanlinManage().mainMenu();
	}
}


