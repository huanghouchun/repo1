package com.lagou.domain;

import java.util.Date;

/**
 * @author hhc19
 * @date 2022/7/22 16:02
 * @description
 * int和Integer的区别
 *      1、Integer是int的包装类，int则是java的八种基本数据类型中的一种(byte,short,int,long,float,double,boolean,char)
 *      2、Integer变量必须实例化后才能使用，而int变量不需要
 *      3、Integer实际是对象的引用，当new一个Integer时，实际上是生成一个指针指向此对象；而int则是直接存储数据值
 *      4、Integer的默认值是null，int的默认值是0
 *
 * 做springboot项目中mapper.xml映射文件的时候，当你在做插入操作的时候变得尤为重要的两点知识
 *      a.定义属性值int类型的时候，在数据库中默认null，当插入操作的时候会把默认值变成0
 *      b.定义属性值Integer类型的时候，在数据库中默认也是null,但是当插入操作的的时候默认值会变成null。
 */
public class User {

    private Integer id;
    private String username;
    private  Date birthday;
    private String sex;
    private String address;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
