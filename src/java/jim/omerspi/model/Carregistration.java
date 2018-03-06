package jim.omerspi.model;
// Generated May 14, 2013 4:28:15 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Carregistration generated by hbm2java
 */
public class Carregistration  implements java.io.Serializable {


     private Integer carRegistrationId;
     private String plateNo;
     private String model;
     private long cost;
     private int manufacturedYear;
     private String country;
     private String status;
     private Date acquiredDate;
     private Date startDate;
     private String vendorName;
     private String carCondition;
     private String carServiceStatus;
     private Set<Carrequisitionservice> carrequisitionservices = new HashSet<Carrequisitionservice>(0);
     private Set<Carrequisitionservice> carrequisitionservices_1 = new HashSet<Carrequisitionservice>(0);

    public Carregistration() {
    }

	
    public Carregistration(String plateNo, long cost, int manufacturedYear, String country, Date acquiredDate, String vendorName, String carCondition, String carServiceStatus) {
        this.plateNo = plateNo;
        this.cost = cost;
        this.manufacturedYear = manufacturedYear;
        this.country = country;
        this.acquiredDate = acquiredDate;
        this.vendorName = vendorName;
        this.carCondition = carCondition;
        this.carServiceStatus = carServiceStatus;
    }
    public Carregistration(String plateNo, String model, long cost, int manufacturedYear, String country, String status, Date acquiredDate, Date startDate, String vendorName, String carCondition, String carServiceStatus, Set<Carrequisitionservice> carrequisitionservices, Set<Carrequisitionservice> carrequisitionservices_1) {
       this.plateNo = plateNo;
       this.model = model;
       this.cost = cost;
       this.manufacturedYear = manufacturedYear;
       this.country = country;
       this.status = status;
       this.acquiredDate = acquiredDate;
       this.startDate = startDate;
       this.vendorName = vendorName;
       this.carCondition = carCondition;
       this.carServiceStatus = carServiceStatus;
       this.carrequisitionservices = carrequisitionservices;
       this.carrequisitionservices_1 = carrequisitionservices_1;
    }
   
    public Integer getCarRegistrationId() {
        return this.carRegistrationId;
    }
    
    public void setCarRegistrationId(Integer carRegistrationId) {
        this.carRegistrationId = carRegistrationId;
    }
    public String getPlateNo() {
        return this.plateNo;
    }
    
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }
    public String getModel() {
        return this.model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    public long getCost() {
        return this.cost;
    }
    
    public void setCost(long cost) {
        this.cost = cost;
    }
    public int getManufacturedYear() {
        return this.manufacturedYear;
    }
    
    public void setManufacturedYear(int manufacturedYear) {
        this.manufacturedYear = manufacturedYear;
    }
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getAcquiredDate() {
        return this.acquiredDate;
    }
    
    public void setAcquiredDate(Date acquiredDate) {
        this.acquiredDate = acquiredDate;
    }
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public String getVendorName() {
        return this.vendorName;
    }
    
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
    public String getCarCondition() {
        return this.carCondition;
    }
    
    public void setCarCondition(String carCondition) {
        this.carCondition = carCondition;
    }
    public String getCarServiceStatus() {
        return this.carServiceStatus;
    }
    
    public void setCarServiceStatus(String carServiceStatus) {
        this.carServiceStatus = carServiceStatus;
    }
    public Set<Carrequisitionservice> getCarrequisitionservices() {
        return this.carrequisitionservices;
    }
    
    public void setCarrequisitionservices(Set<Carrequisitionservice> carrequisitionservices) {
        this.carrequisitionservices = carrequisitionservices;
    }
    public Set<Carrequisitionservice> getCarrequisitionservices_1() {
        return this.carrequisitionservices_1;
    }
    
    public void setCarrequisitionservices_1(Set<Carrequisitionservice> carrequisitionservices_1) {
        this.carrequisitionservices_1 = carrequisitionservices_1;
    }




}

