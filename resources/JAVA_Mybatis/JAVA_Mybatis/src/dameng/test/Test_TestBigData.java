package dameng.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import dameng.dao.TestBigDataMapper;
import dameng.pojo.TestBigData;
public class Test_TestBigData {
	SqlSession sqlSession = null;
	TestBigDataMapper testBigDateMapper = null;
	public void init() {
		try {
		//1. ���� sqlsession factory biulder ����
	    SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
	    //2. ���������ļ���Ϊһ��������
	    //���� Resources ʹ�õİ��� ibatis ��
	    InputStream resourceAsStream;
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
	    //3. ͨ���Ự���������� �����  �����ļ� ��  ����һ���Ự���칤��
	    SqlSessionFactory factory = sfb.build(resourceAsStream);
	    //4. ͨ�� sql �Ự���� //true ���� mybatis �����Զ��ύ
	    sqlSession = factory.openSession(true);
	    testBigDateMapper = sqlSession.getMapper(TestBigDataMapper.class);
	    resourceAsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Test_TestBigData test = new Test_TestBigData();
		test.init();
		test.testInsert();
		test.testSelect();
	}
	//���Բ�����ֶα�
	private void testInsert() {
		try {
		String filePath = "D:\\mybatis_bigdata_test.jpg";
		File file = new File(filePath);
		String filePath2 = "D:\\mybatis_bigdata_test.txt";
		File file2 = new File(filePath2);
		InputStream in;
		in = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes1 = new byte[732160];//������Ҫ����
		byte[] bytes2 = new byte[732160];//������Ҫ����
		in.read(bytes1);
		InputStream in2 = new BufferedInputStream(new FileInputStream(file));
		in2.read(bytes2);
		BufferedReader reader = new BufferedReader(new InputStreamReader
				(new FileInputStream(file2),"UTF-8"));
		//char[] c = new char[10240];
		char[] chars = new char[4096];
		reader.read(chars);
		TestBigData bigDate = new TestBigData(null,bytes1,bytes2,new String(chars));
		testBigDateMapper.InsertIntoTestBigDate(bigDate);
		in.close();
		in2.close();
		reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//���Բ�ѯ���ֶα�
	private void testSelect() {
		List<TestBigData> list = testBigDateMapper.SelectFromTestBigDate();
		try {
			for(TestBigData big:list) {
				//��ӡ�� id
				System.out.println("id = "+big.getId());
				//�� photo ����Ϣ �����ָ��·��
				FileOutputStream fos = new FileOutputStream("D:/"+big.getId()+"_mybatis_ photo_test.jpg");
				fos.write(big.getPhoto());
				//�� describe ����Ϣ �����ָ��·��
				FileOutputStream fos2 = new FileOutputStream("D:/"+big.getId()+"_mybatis_describe_test.jpg");
				fos2.write(big.getDescribe());
				//�� photo ����Ϣ ���������̨
				System.out.println("txt="+big.getTxt());
				fos.close();
				fos2.close();
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}