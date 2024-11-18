package dameng.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import dameng.dao.ProductCategoryMapper;
import dameng.pojo.ProductCategory;

public class TestProduct {
	SqlSession sqlSession = null;
	ProductCategoryMapper productCategoryMapper = null;
	public void init() {
		try {
		//1. ���� sqlsession factory biulder ����
	    SqlSessionFactoryBuilder sfb = new SqlSessionFactoryBuilder();
	    //2. ���������ļ���Ϊһ��������
	    //����Resources ʹ�õİ��� ibatis ��
	    InputStream resourceAsStream;
			resourceAsStream = Resources.getResourceAsStream("mybatis-config.xml");
	    //3. ͨ���Ự���������� �����  �����ļ��� ����һ���Ự���칤��
	    SqlSessionFactory factory = sfb.build(resourceAsStream);
	    //4. ͨ�� sql �Ự���� //true ���� mybatis �����Զ��ύ
	    sqlSession = factory.openSession(true);
	  
	    productCategoryMapper = sqlSession.getMapper(ProductCategoryMapper.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//���Բ�����Ϣ
    @Test
    public void testInstert(){
		this.init();
		productCategoryMapper.insertProductCategory(new ProductCategory(6, "�ٶ� "));
    }
    //�����޸���Ϣ
    @Test
    public void testUpdate(){
    	ProductCategory productCategory = productCategoryMapper.selectProductCategoryById(4);
    	productCategory.setNAME("Ӣ��");
    	productCategoryMapper.updateProductCategory(productCategory);
    }
    //���Ը��� id ��ѯָ������Ϣ
    @Test
    public void testSelectPersonById(){
    	ProductCategory productCategory = productCategoryMapper.selectProductCategoryById(1);
        System.out.println(productCategory);
    }
    //����ȫ��
    @Test
    public void testSelectAll(){
        List<ProductCategory> list = productCategoryMapper.selectAll();
        for(ProductCategory p: list){
            System.out.println(p);
        }
    }
    //����ɾ��
    @Test
    public  void testDelete(){
    	productCategoryMapper.deleteById(5);
    }
	public static void main(String[] args) {
		TestProduct test = new TestProduct();
		test.init();
		test.testInstert();
		test.testDelete();
		test.testUpdate();
        test.testSelectPersonById();
        test.testSelectAll();
	}
}