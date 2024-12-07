//template of a java class

// Unit define/declare all the attribute:
public class Unit
{

    private String unitCode;     // attribute myName
    private String unitDescription;
    private int creditPoints;

//constructor:
    public Unit()
    {
    unitCode = "";
    unitDescription = "";
    creditPoints = 0;

    }

//method:
    public Unit (String unitCode , String unitDescription, int creditPoints)
    {
    
    // when the parameter hava the same name, use "this.":  this.myName = myName;
    this.unitCode = unitCode;
    this.unitDescription = unitDescription;
    this.creditPoints = creditPoints;
    // "this" is for the whole class parameter
    }

// display method
    public void display()
    {
    System.out.println("unitCode: " + unitCode);
    // upper cap
    System.out.println("unitDescription: " + unitDescription);
    System.out.println("CreditPoints: " + creditPoints);
    }

    // accesor: get method; mutator: set method;(get always be the first)
    public String getUnitCode()  // no parameter needed
    {
        return this.unitCode;
    }

    public String getUnitDescription()  
    {
        return this.unitDescription;
    }

    public int getCreditPoints() 
    {
        return this.creditPoints;
    }


    public void setStudentAddress(String unitCode)
    {
        this.unitCode = unitCode;
    }

    public void setStudentPhoneNo(String unitDescription)
    {
        this.unitDescription = unitDescription;
    }
    public void setStudentEmail(int creditPoints)
    {
        this.creditPoints = creditPoints;
    }

    public String toString()
    {
        return "Unitcode : " + unitCode + "\n Unitdescription: " + unitDescription + "\n creditPoints:" + creditPoints;
    }
    // main

    public static void main(String[] args)
    {
        // declare datatype; 

        String name;
        String address;
        String email;
        String phoneNo;

        Unit objM1ke;
        objM1ke.setStudentAddress("address");
        objM1ke.setStudentEmail("119@gmail.com");
        objM1ke.setStudentName("M1ke");
        objM1ke.setStudentPhoneNo("12002222");

        objM1ke.display();
    }
}











