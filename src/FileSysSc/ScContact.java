package FileSysSc;

import javafx.beans.property.SimpleStringProperty;

public class ScContact {
	String FNAME;
	String LNAME;
	String EMAIL;
	String PHONE;

	private SimpleStringProperty f1, f2, f3, f4;
	 
    public String getF1() {
        return f1.get();
    }

    public String getF2() {
        return f2.get();
    }

    public String getF3() {
        return f3.get();
    }

    public String getF4() {
        return f4.get();
    }
 

    ScContact(String f1, String f2, String f3, String f4,String f5) {
        this.f1 = new SimpleStringProperty(f1);
        this.f2 = new SimpleStringProperty(f2);
        this.f3 = new SimpleStringProperty(f3);
        this.f4 = new SimpleStringProperty(f4);
        
    }
}
