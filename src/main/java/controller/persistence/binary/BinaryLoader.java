package controller.persistence.binary;

import controller.persistence.LoaderFactory;
import model.Company;
import model.position.Employee;

import java.io.*;

public class BinaryLoader extends LoaderFactory {
	
	@Override
	public void download(String path) 
			throws IOException, ClassNotFoundException,
				   ClassCastException {
		ObjectInputStream in = new ObjectInputStream(
							   new BufferedInputStream(
							   new FileInputStream(path)));
		while(true) {
			try {
				Company.getInstance().addEmployee((Employee) in.readObject());
			} catch (EOFException ex) {
				break;
			}
		}
		in.close();
	}

	@Override
	public void upload(String path) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.write(Company.getInstance().getPayroll());
		out.flush();
		out.close();
	}
}
