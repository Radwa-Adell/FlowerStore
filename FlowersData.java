/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flowerstore;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class FlowersData {
    private Integer flowerId;
    private String name;
     private String status;
     private Double price;
     private Date date;
     private String image;

     
     public FlowersData (Integer flowerId,String name,String status,Double price,String image,Date datث)
     {
          this.flowerId = flowerId;
        this.name = name;
        this.status = status;
        this.price = price;
        this.image = image;
        this.date = date;
     }
       public Integer getFlowerId(){
        return flowerId;
    }
    public String getName(){
        return name;
    }
    public String getStatus(){
        return status;
    }
    public Double getPrice(){
        return price;
    }
    public Date getDate(){
        return date;
    }
    public String getImage(){
        return image;
    }

    
    
}
