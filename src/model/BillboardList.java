package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BillboardList {
	
	private static final String SAVE_PATH_FILE = "data/billboards.acq";
	public final static String SPLITTER="\\|";

	private ArrayList<Billboard> billboards;
	
	public BillboardList() {
		billboards=new ArrayList<Billboard>();
		
	}

	public ArrayList<Billboard> getBillboards() {
		return billboards;
	}

	public void setBillboards(ArrayList<Billboard> billboards) {
		this.billboards = billboards;
		
	}

	public void addBillboard(double width, double height, boolean inUse, String brand) throws IOException {
		Billboard billboard= new Billboard(width, height, inUse, brand);
		billboards.add(billboard);
		saveBillboards() ;
	}
	
	public void saveBillboards() throws IOException{
	    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
	    oos.writeObject(billboards);
	    oos.close();
	  }
	
	@SuppressWarnings("unchecked")
	public boolean loadBillboards() throws IOException, ClassNotFoundException{
	    File f = new File(SAVE_PATH_FILE);
	    boolean loaded = false;
	    if(f.exists()){
	      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
	      billboards = (ArrayList<Billboard>)ois.readObject();
	      ois.close();
	      loaded = true;
	    }
	    return loaded;
	  }

	public void importData(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line!=null){
			String[] parts = line.split(SPLITTER);
			boolean inUse=false;

			if(parts[2].equals("true")) {
				inUse=true;
			}

			if(!parts[0].equals("width")) {
				double width =Double.parseDouble(parts[0]);
		    	double height =Double.parseDouble(parts[1]);
				addBillboard(width,height, inUse, parts[3]);
			}

			line = br.readLine();

		}
		br.close();
	}
	
	public void exportDangerousBillboardReport(String fileName) throws FileNotFoundException{
	    PrintWriter pw = new PrintWriter(fileName);
	    pw.println("===========================\n"+
		    		"DANGEROUS BILLBOARD REPORT\n"+
		    		"===========================\n"+
		    		"The dangerous billboard are:");

	    int j=0;
	    for(int i=0;i<billboards.size();i++){
	      Billboard billboard = billboards.get(i);
	     
	      if(billboard.calculateArea()>=160000) {
	    	  j++;
	    	  pw.println((j)+". Billboard "+billboard.getBrand()+" with area "+billboard.calculateArea());
	    
	      }
	    }

	    pw.close();
	  }

}
