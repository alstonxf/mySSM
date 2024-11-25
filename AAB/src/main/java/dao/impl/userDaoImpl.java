package dao.impl;

import dao.user;
import dao.userDao;
import dao.userMaper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component("userDaoImpl1")
public class userDaoImpl implements userDao {
    @Autowired
    SqlSession sqlSession;

    @Value("小明")
    String userName;
    @Value("6")
    int userAge;

    List<user> userList;
    Map<String,String> userMap;
    Set userSet;
    Properties userProperties;

    @Autowired
    public userDaoImpl(@Value("小明") String userName, @Value("16") int userAge){
        this.userName = userName;
        this.userAge = userAge;
    }

    @Override
    public void insert() {
        System.out.println("数据库user执行用户更新数据");
        System.out.println(this);
    }

    @Override
    public List<user> getUserList() {
        System.out.println("数据库user执行getUserList()"+userList);
        userMaper mapper = sqlSession.getMapper(userMaper.class);
        List<user> userList = mapper.getUsers();
        return userList;
    }

    @Override
    public Set getUserSet() {
        System.out.println("数据库user执行getUserSet()"+userSet);
        return userSet;
    }

    @Override
    public Map getUserMap() {
        try {
            System.out.println("数据库user执行getUserMap" + userMap.entrySet());
        }
        catch (Exception e){
            System.out.println("warn");

        }
        finally {
            System.out.println("finally");
        }


        return userMap;
    }

    @Override
    public Properties getUserPros() {
        System.out.println("数据库user执行getUserPros()"+userProperties);
        return userProperties;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    @Override
    public String toString() {
        return "userDaoImpl{" +
                "userName='" + userName + '\'' +
                ", userAge=" + userAge +
                '}';
    }

    public void setUserList(List<user> userList) {
        this.userList = userList;
    }

    public void setUserMap(Map<String, String> userMap) {
        this.userMap = userMap;
    }

    public void setUserSet(Set userSet) {
        this.userSet = userSet;
    }

    public void setUserProperties(Properties userProperties) {
        this.userProperties = userProperties;
    }
}
