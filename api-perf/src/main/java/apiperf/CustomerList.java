package apiperf;

import java.util.List;

public class CustomerList {
	List<Customer> customerList;
	boolean userHadPermission;
	
	public CustomerList(List<Customer> customerList, boolean userHadPermission) {
		this.customerList = customerList;
		this.userHadPermission = userHadPermission;
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public boolean isUserHadPermission() {
		return userHadPermission;
	}
}
