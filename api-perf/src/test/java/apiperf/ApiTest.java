package apiperf;

import java.util.ArrayList;
import java.util.List;

public class ApiTest {
	
	private interface TimingCallback {
		void execute();
	}
	
	private static class Timing {
		public static long measure(TimingCallback callback) {
			long startMillis = System.currentTimeMillis();
			for(int i=0; i<1000000; i++) {
				callback.execute();
			}
			return System.currentTimeMillis() - startMillis;
		}
	}

	public static void main(String args[]) {
		ApiTest apiTest = new ApiTest();
		for(int i=0; i<10; i++) {
		apiTest.testLoadCustomersWithExceptionNoPermission();
		apiTest.testLoadCustomersWithExceptionWithPermission();
		apiTest.testLoadCustomersWithExceptionAndCheckNoPermission();
		apiTest.testLoadCustomersWithExceptionAndCheckWithPermission();
		apiTest.testLoadCustomersWithReturnClassNoPermission();
		apiTest.testLoadCustomersWithReturnClassWithPermission();
		apiTest.testLoadCustomersWithListAsParameterNoPermission();
		apiTest.testLoadCustomersWithListAsParameterWithPermission();
		}
	}
	
	public void testLoadCustomersWithExceptionNoPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(false);
				try {
					List<Customer> customerList = api.loadCustomersWithException();
					doSomething(customerList);
				} catch (PermissionDeniedException e) {
				}
			}
		});
		System.out.println("testLoadCustomersWithException(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithExceptionWithPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(true);
				try {
					List<Customer> customerList = api.loadCustomersWithException();
					doSomething(customerList);
				} catch (PermissionDeniedException e) {
				}
			}
		});
		System.out.println("testLoadCustomersWithExceptionWithPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithExceptionAndCheckNoPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(false);
				try {
					if(api.hasPermissionToLoadCustomers()) {
						List<Customer> customerList = api.loadCustomersWithException();
						doSomething(customerList);
					}
				} catch (PermissionDeniedException e) {
				}
			}
		});
		System.out.println("testLoadCustomersWithExceptionAndCheckNoPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithExceptionAndCheckWithPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(true);
				try {
					if(api.hasPermissionToLoadCustomers()) {
						List<Customer> customerList = api.loadCustomersWithException();
						doSomething(customerList);
					}
				} catch (PermissionDeniedException e) {
				}
			}
		});
		System.out.println("testLoadCustomersWithExceptionAndCheckWithPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithReturnClassNoPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(false);
				CustomerList customerList = api.loadCustomersWithReturnClass();
				if(customerList.isUserHadPermission()) {
					doSomething(customerList.getCustomerList());
				}
			}
		});
		System.out.println("testLoadCustomersWithReturnClassNoPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithReturnClassWithPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(true);
				CustomerList customerList = api.loadCustomersWithReturnClass();
				if(customerList.isUserHadPermission()) {
					doSomething(customerList.getCustomerList());
				}
			}
		});
		System.out.println("testLoadCustomersWithReturnClassWithPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithListAsParameterNoPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(false);
				List<Customer> customerList = new ArrayList<Customer>();
				boolean hasPermission = api.loadCustomersWithListAsParameter(customerList);
				if(hasPermission) {
					doSomething(customerList);
				}
			}
		});
		System.out.println("testLoadCustomersWithListAsParameterNoPermission(): "+millis+" ms");
	}
	
	public void testLoadCustomersWithListAsParameterWithPermission() {
		long millis = Timing.measure(new TimingCallback() {
			public void execute() {
				Api api = new Api(true);
				List<Customer> customerList = new ArrayList<Customer>();
				boolean hasPermission = api.loadCustomersWithListAsParameter(customerList);
				if(hasPermission) {
					doSomething(customerList);
				}
			}
		});
		System.out.println("testLoadCustomersWithListAsParameterWithPermission(): "+millis+" ms");
	}
	
	private void doSomething(List<Customer> customerList) {
		for(Customer customer : customerList) {
			customer.getName();
		}
	}
}
