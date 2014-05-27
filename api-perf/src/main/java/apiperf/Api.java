package apiperf;

import java.util.ArrayList;
import java.util.List;

public class Api {
	private boolean userHasPermission;

	public Api(boolean userHasPermission) {
		this.userHasPermission = userHasPermission;
	}

	public List<Customer> loadCustomersWithException() throws PermissionDeniedException {
		if(this.userHasPermission) {
			return internaLloadCustomers();
		} else {
			throw new PermissionDeniedException();
		}
	}
	
	public boolean hasPermissionToLoadCustomers() {
		return this.userHasPermission;
	}
	
	public CustomerList loadCustomersWithReturnClass() {
		if(this.userHasPermission) {
			return new CustomerList(internaLloadCustomers(), true);
		} 
		return new CustomerList(new ArrayList<Customer>(), false);
	}
	
	public boolean loadCustomersWithListAsParameter(List<Customer> customerList) {
		if(this.userHasPermission) {
			customerList.addAll(internaLloadCustomers());
			return true;
		}
		return false;
	}
	
	private List<Customer> internaLloadCustomers() {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		customerList.add(new Customer("name"));
		return customerList;
	}
}
