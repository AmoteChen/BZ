package com.example.bz.bz.Black_list;

/**
 * Created by ChenSiyuan on 2018/9/3.
 */
//实体类，负责装载数据
public class BlackNumber {
    private long id;
    private String PhoneNumber;
    private String remark;
    //get和set方法
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }
    public String getRemark(){
        return remark;
    }
    public void setRemark(String remark){
        this.remark=remark;
    }
    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber){
        this.PhoneNumber=PhoneNumber;
    }
    public BlackNumber(){
    }
    public BlackNumber(long id,String remark,String PhoneNumber){
        this.id=id;
        this.remark=remark;
        this.PhoneNumber=PhoneNumber;
    }
    public BlackNumber(String remark,String PhoneNumber){
        this.remark=remark;
        this.PhoneNumber=PhoneNumber;
    }
//    public BlackNumber(String PhoneNumber){
//        this.PhoneNumber = PhoneNumber;
//    }
    @Override
    public String toString(){
        return "BlackNumber{" +
                "id=" + id +
                ", remarke='" + remark + '\''+
                ", PhoneNumber='" + PhoneNumber + '\''+
                "}";
    }
}
