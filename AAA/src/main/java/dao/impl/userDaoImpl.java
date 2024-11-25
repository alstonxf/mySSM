package dao.impl;

import dao.userDao;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class userDaoImpl implements userDao {
    String userName;
    int userAge;
    
    List<String> userList;
    Map<String,String> userMap;
    Set userSet;
    Properties userProperties;
    
    public userDaoImpl(String userName, int userAge){
        this.userName = userName;
        this.userAge = userAge;
    }

    @Override
    public void insert() {
        System.out.println("数据库user执行用户更新数据");
        System.out.println(this);
    }

    @Override
    public List getUserList() {
        System.out.println("数据库user执行getUserList()"+userList);
        return userList;
    }

    @Override
    public Set getUserSet() {
        System.out.println("数据库user执行getUserSet()"+userSet);
        return userSet;
    }

    @Override
    public Map getUserMap() {
        System.out.println("数据库user执行getUserMap"+userMap.entrySet());
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

    public void setUserList(List<String> userList) {
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
