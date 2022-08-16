import com.lagou.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author hhc19
 * @date 2022/7/22 17:57
 * @description
 */
public class MybatisTest {

    /**
     * 快速入门测试方法
     */
    @Test
    public void mybatisQuickstart() throws IOException {

        // 1.加载核心配置文件
        // 底层是借助类加载器来加载 sqlMapConfig.xml
        // 加载核心配置类的同时也借助 mappers 标签把映射配置文件也加载了 这样就可以根据 user.findAll 定位到 sql 执行了
        /**
         * Resources 是一个工具类 getResourceAsStream 加载指定配置文件 sqlMapConfig.xml 配置文件路径
         * 将该配置文件加载成一个输入流
         * 借助工具类完成资源的加载
         */
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");

        // 2.获取sqlSessionFactory工厂对象
        /**
         * SqlSessionFactoryBuilder SqlSession 工厂的构建器 这个类就是用来构建 SqlSession工厂类的
         * build 通过加载mybatis核心配置文件的输入流 来构建出一个SqlSession工厂对象
         * SqlSessionFactory 就是用来生产 SqlSession 会话对象的 因为只有拿到 SqlSession会话对象，我们才能调用它的相关方法，完成对数据库的操作
         */
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        // 3.获取sqlSession会话对象
        /**
         * SqlSessionFactory 有多个个方法创建SqlSession 实例。常用的有如下两个：
         * openSession() 会默认开启一个事务，但事务不会自动提交，也就意味着需要手动提交该事务，更新操作数据才会持久化到数据库中
         * openSession(boolean autoCommit) 参数为是否自动提交，如果设置为true，那么不需要手动提交事务
         */
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.执行sql selectList 参数1：statementid: namespace.id组成   参数2：sql所需要的参数    参数3：做分页用的
        List<User> users = sqlSession.selectList("userMapper.findAll");

        // 5.遍历打印结果
        for (User user : users) {
            System.out.println(user);
        }

        // 6.释放资源
        sqlSession.close();
    }

    /**
     * 测试新增用户
     */
    @Test
    public void testSave() throws IOException {

        // 1.加载核心配置文件
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 2.获取SqlSessionFactory 工厂类对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 3.获取 SqlSession 会话对象
        SqlSession sqlSession = sqlSessionFactory.openSession(true); // 设置当前开启的事务是自动提交的


        // 调用 api 方法
        User user = new User();
        user.setUsername("自动提交事务");
        user.setBirthday(new Date());
        user.setSex("男");
        user.setAddress("北京海淀");

        sqlSession.insert("userMapper.saveUser", user);

        // 手动提交事务 新增之后未提交则会发生回滚，但是 id 会自增 之后再新增 就会跳过该 id
        // 增删改完成之后都需要提交一下事务
        // 插入操作涉及数据库数据变化，所以要使用sqlSession对象显示的提交事务，即 sqlSession.commit()
        // sqlSession.commit();

        // 关闭资源
        sqlSession.close();
    }

    /**
     * 测试更新用户
     */
    @Test
    public void testUpdate() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(4);
        user.setUsername("Lucy");
        user.setBirthday(new Date());
        user.setSex("女");
        user.setAddress("北京朝阳");
        sqlSession.update("userMapper.updateUser", user);

        // 手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDelete() throws IOException {

        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("userMapper.deleteUser", 4);

        // 手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }
}
