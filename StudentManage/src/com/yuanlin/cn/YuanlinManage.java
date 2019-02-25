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
	public static final int ADD_STU = 0;//���
	public static final int SEARCH_STU = 1;//��ѯ
	public static final int CHANGE_STU = 2;//�޸�
	public static final int DELETE_STU =3;//ɾ��
	public static final int EXIT_SYS = 4;//�˳�����
	public int yourChoose = 0;//���ѡ��
	private static int temp;//��ʾ������ʾ�����˶��ٸ�ѧ����Ϣ���ļ���
	private FileOutputStream fos_t;//�����	
	private FileInputStream fis_t;//������
	//����̨��������Scanner �Ӽ��̻�ȡ����
	public Scanner sc;
	//ѧ����������Ļ�������
	public Student stu;//ѧ���༯��
	public int number;//ѧ��
	public String name;//����
	public int age;//����
	public String grade;//�꼶
	public String myClass;//�༶
	public String school;//ѧУ
	//�ļ�����������Ͷ������������������
	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private static List<Student> listOfStudent;//������������



	/**
	*���캯����������ʼ����Ҫ�Ļ�����Ϣ��
	*����̨���������������������������ļ��д�����Ϣ�Ͱ���Ϣ���뵽�����У��������������
	*/
	public YuanlinManage()
	{
		try
		{
			sc = new Scanner(System.in);//�ڿ���̨������Ϣ
			listOfStudent = new ArrayList<Student>();//�������

			fis_t = new FileInputStream("student.txt");//�ļ�������
			
			temp = readTemp();
			if(temp < 1)//�ļ�Ϊ���򲻶�ȡ��������׳�EOFException �쳣
			{
				System.out.println("�ļ��ǿյģ�");
			}
			else//����ļ����գ��Ͱ��ļ��е����ݶ��뵽������
			{
				ois = new ObjectInputStream(fis_t);//����������
				for(int i = 0; i < temp; i++)//ѭ������
				{
					stu = (Student)ois.readObject();//��ȡ�ļ�����
					listOfStudent.add(i,stu);//��i��ѧ����stu��ӵ�listOfStudent���������   listOfStudent���������
				}
			}
		}
		catch(Exception e)  //�쳣����
		{
			e.printStackTrace();//����쳣���񣬲�����������ԭ��
		} 
	}
	/**��ӡ����*/
	public void print(Student stuS)
	{
		System.out.println("-------------------------------------------------------------------------");
			System.out.println("ѧ�ţ�"+stuS.getNumber()+"  ������"+stuS.getName()+
			"  ���䣺"+stuS.getAge()+" �꼶��"+stuS.getGrade()+"  �༶��"+
		stuS.getMyClass()+"  ѧУ��"+stuS.getSchool());
		
	}
	/**�ж��û�������Ƿ�Ϸ��ĺ���*/
	public boolean isOK(String str)
	{
		
		try//ͬ����Ҫ���쳣����  �쳣����ĸ�ʽΪ��try{....} catch{....}
			{
				yourChoose = Integer.parseInt(str);//��ȡ�������������
			}catch(NumberFormatException e)
			{
   				System.out.println("��������������������������Ĳ������֣�������������������");
   				return false;//���Ϸ� ����false
			}
			return true;//�Ϸ�����true
	}
	/**���˵�*/
	public void mainMenu()
	{
		while(true)
		{
			System.out.println("************************* ��ӭʹ��ѧ������ϵͳ**************************************");
			System.out.println("��ѡ����Ҫ���еĲ�����0����� 1����ѯ  3��ɾ�� 4���˳��� ");
			System.out.println("\r\n");
			
			str = sc.next();//�û��Ӽ������������ ��ֵ��str
			boolean bb = isOK(str);//����isOK�������ж��û�������Ƿ�Ϊ�Ϸ���ʽ
			if(!bb)//bb����true����false �������ĸ�ʽ����ȷ
				continue;//��������
			else//����
				yourChoose = Integer.parseInt(str);//�����Ӳ˵�
			switch(yourChoose)//�˵�ѡ��
			{
				case ADD_STU:
				//���ѧ����Ϣ�ķ���
					addSTU();
					break;
				case SEARCH_STU:
					searchSTU();//��ѯѧ���ķ���
					break;
				case DELETE_STU:
					deleteSTU();//ɾ��ѧ���ķ���
					break;
				case EXIT_SYS://����
					return;
				default:
					break;
			}
		}
	}

	/**�ж�ѧ���Ƿ��Ѵ���*/
	public boolean isExist(int number)//����Ĳ���Ϊѧ��
	{
		Student stuE = null;
		int count = 0;//���count=0˵��ѧ�Ų�����  ���count=1˵��ѧ���Ѵ���
		ite = listOfStudent.iterator();//���ļ��л�ȡ����
		while(ite.hasNext())//hasNext������������Ƿ���Ԫ��   ���þ��ǰ����е������ó���
		{
			stuE = (Student)ite.next();//ָ����һ���ڵ�
			if(stuE.getNumber() == number)//����������Ĳ������������е�ѧ�ţ��Ǿ�˵��ѧ�Ŵ���
			{
				count++;
				System.out.println("ѧ���Ѵ��ڣ����������룡");
			}
		}
		if(count == 0)//�ж�conut��ֵ  ���count=0����false  ���count=1����true
			return false;
		else
			return true;
	}
/**���һ��ѧ����Ϣ������*/
	public boolean addToList()
	{
		Student stuAdd = new Student();//ʵ����ѧ��������
		//�ж��Ƿ������ͬ��ѧ�ţ������������������ѧ��
		System.out.println("��ǰ����ѧ��" + temp + "��");
		System.out.println("������ѧ�ţ�");
		str = sc.next();
		boo = isOK(str);//�ж������Ƿ�Ϸ�
		if(!boo)
			return false;
		else
			number = Integer.parseInt(str);//��ȡ�¼��˵�
		if(isExist(number))
			return false;
		System.out.println("������������");
		name = sc.next();//��������
		System.out.println("���������䣺");
		str = sc.next();//��������
		boo = isOK(str);
		if(!boo)
			return false;
		else
			age = Integer.parseInt(str);
		System.out.println("�������꼶��");
		grade = sc.next();//�����꼶
		System.out.println("������༶��");
		myClass = sc.next();//����༶
		System.out.println("������ѧУ��");
		school = sc.next();//����ѧУ
		//����ӵ���Ϣ��ֵ��stuAdd
		stuAdd.setNumber(number);//��ѧ����ӵ�ѧ��������
		stuAdd.setName(name);//��������ӵ�ѧ��������
		stuAdd.setAge(age);//��������ӵ�ѧ��������
		stuAdd.setGrade(grade);//���꼶��ӵ�ѧ��������
		stuAdd.setMyClass(myClass);//�Ѱ༶��ӵ�ѧ��������
		stuAdd.setSchool(school);//��ѧ����ӵ�ѧ��������
		listOfStudent.add(temp,stuAdd);//��ѧ��������д�뵽�ļ���
		System.out.println("��ӳɹ���");
		return true;
	}
	/**���ѧ����Ϣ*/
	public boolean addSTU()
	{
		if((boo = addToList()))
			{
				sequenceList();//�������е����ݽ�������
				return true;
			}
		else return false;
	}
	/**��ȡtemp��ֵ���ж��ж��ٸ�ѧ��*/
	public int readTemp()
	{
		int temp = 0;
		try
		{
			FileInputStream fis_t = new FileInputStream("temp.txt");//temp.txt�����һ���ж�����ѧ��
			temp = fis_t.read();//��ȡ�ļ��е�����
			if(temp == -1)//���Ϊ-1˵��û��ѧ��
			{
				temp = 0;
				System.out.println("Ŀǰû��ѧ��");
			}
			else//�����˵����ѧ��
			{
				System.out.println("Ŀǰ����ѧ����"+ temp + " ��");
			}
			fis_t.close();//�ر��ļ���
			
		}
		catch(Exception e)
		{
			e.printStackTrace();//��������쳣�������쳣��ԭ��
		}
		return temp;
	}
	/**д��temp��ֵ�������ѧ�� ��Ҫ��temp.txt�д��ѧ���������иı�*/
	public void writeTemp(int temp)//����Ĳ�����ѧ��������
	{
		try
		{
			FileOutputStream fos_t = new FileOutputStream("temp.txt");//���ļ�
			fos_t.write(temp);//����֮ǰ�ģ�����д��
			fos_t.close();//�ر��ļ���
		}
		catch(Exception e)
		{
			e.printStackTrace();//��������쳣�������쳣��ԭ��
		}
	}
	/**�������е�һ������д���ļ���*/
	public void writeToTxt(Student stuW)
	{
		try
		{
			fos_t = new FileOutputStream("student.txt");//�ļ���
			oos = new ObjectOutputStream(fos_t);//���������
			for(int j = 0; j < listOfStudent.size(); j++)//ѭ��������listOfStudent.size()�������Ĵ�С��J<listOfStudent.size()��˵������������һ��Ԫ��
			{
				stuW = listOfStudent.get(j);//���
				oos.writeObject(stuW);//д��
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();//��������쳣�������쳣��ԭ��
		}
		finally
		{
			try{oos.close();}catch(Exception e1){e1.printStackTrace();}//�ر��ļ�������������쳣�������쳣��ԭ��
		}
	}
	/**��ѧ�Ų���ѧ����Ϣ*/
	public void searchWithNumber()
	{
		int count = 0;
		Student stuS = null;
		System.out.println("������ѧ�ţ�");
		str = sc.next();
		boo = isOK(str);//�ж������Ƿ�Ϸ�
		if(!boo)
			return;
		else
			number = Integer.parseInt(str);
		ite = listOfStudent.iterator();//ʹ�÷��� iterator()Ҫ����������һ�� Iterator����һ�ε���Iterator ��next()����ʱ�����������е��ǵ�һ��Ԫ��
		while(ite.hasNext())//��������
		{
			stuS = (Student)ite.next();//ָ��дһ��
			if(stuS.getNumber() == number)//������������ѧ�źʹ������Ĳ�����ȣ�˵���ҵ��˸�ѧ������ѧ�������ݼ���stuS����
			{
				count++;//�ҵ�һ��ѧ���ͼ�1
				print(stuS);
			}
		}
		if(count == 0)//û���ҵ�ѧ��
			System.out.println("�ҵ�0��ѧ����");
	}
	/**����������ѧ����Ϣ*/
	public void searchWithName()
	{
		int count = 0;
		Student stuS = null;
		System.out.println("������������");
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
			System.out.println("�ҵ�0��ѧ����");
	}
	
	/**��ѯ����*/
	public void searchAll()
	{
		Student stuS = null;
		System.out.println("����ѧ����Ϣ��");
		ite = listOfStudent.iterator();
		while(ite.hasNext())
		{
			stuS = (Student)ite.next();
			print(stuS);
		}
	}
	/**�μ��˵�����ѯѧ����Ϣ*/
	public void searchSTU()
	{
		System.out.println("*********************��Ҫ��ʲô����������ѧ����Ϣ��************************"+"\r\n");
		System.out.println("1��ѧ�� 2������ 3����ӡ����ѧ����Ϣ 4���������˵� "+"\r\n");
		str = sc.next();
		boo = isOK(str);
		if(!boo)
			return;
		else
			yourChoose = Integer.parseInt(str);
		switch(yourChoose)
		{
			case 1:
				searchWithNumber();//����ѧ��
				break;
			case 2:
				searchWithName();//��������
				break;
			case 3:
				searchAll();//��������
				break;
			case 4:
				return;
			default:
				System.out.println("��Ч����");
				break;
		}
	}
	
	/**�������е���������*******************************************************/
	public void sequenceList()
	{
		//����һ���Ƚ���
		Comparator<Student> comparator = new Comparator<Student>()
		{
			public int compare(Student s1, Student s2) 
			{
			//��ѧ������
				if(s1.getNumber()!=s2.getNumber())
					return s1.getNumber()-s2.getNumber();
				else
					return 0;
			}
		};
		Collections.sort(listOfStudent,comparator);//����
		writeAll();
	}
	//�������е���������********************************************************
	/**����ѧ����Ϣȫ��д�뵽�ļ���*/
	public void writeAll()
	{
		temp = listOfStudent.size();
		writeTemp(temp);//д���µ�temp��ֵ���ļ�temp.txt��
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
	/**ɾ��ѧ����Ϣ*/
	public void deleteSTU()
	{
		int count = 0;
		Student stuD = null;
		System.out.println("������Ҫɾ����ѧ����ѧ��:");
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
				ite.remove();//ע�������õ���Iterator���ɾ�������������listOfStudent.remove(stuD)�����һ���쳣
							//ConcurrentModificationException������쳣�����ڵ���������������ʾ�������Ȳ�һ�²����ģ�
							//ʹ��Iterator�е�remove()��������Ա�����������Ĳ�������Ϊ���remove()����ͳһ���������ȡ�
				print(stuD);
				System.out.println("��ѧ����Ϣ������ɾ����"+"\r\n");
			}
		}
		if(count == 0)
			{System.out.println("û�����ѧ����"+"\r\n");return;}
		sequenceList();
	}
	/**��ں���*/
	public static void main(String args[])
	{
		new YuanlinManage().mainMenu();
	}
}


