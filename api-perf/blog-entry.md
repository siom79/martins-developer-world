API decisions and performance
=============================

When you design a new API you have to take a lot of decisions. These decisions are based on a number
of design principles. Joshua Bloch has summarized some of them in his presention ["How to Design a 
Good API and Why it Matters"](http://lcsd05.cs.tamu.edu/slides/keynote.pdf). The main principles
he mentions are:
* Easy to learn
* Easy to use
* Hard to misuse
* Easy to read and maintain code that uses it
• Sufficiently powerful to satisfy requirements
• Easy to extend
• Appropriate to audience
As we see from the list above, Joshua Bloch puts his emphasis on readability and usage. A point that
is completely missing from this listing is performance. But can performance impact your design decisions
at all?

To answer this query let's try to design a simple use case in form of an API and measure its performance.
Then we can have a look at the results and decide whether performance considerations have an impact on
the API or not. As an example we take the classic use case of loading a list of customers from some
service/storage. What we also want to consider is the fact that not all users are allowed to perform
this operation. Hence we will have to implement some kind of permission check. To implement this check
and to return this information back to the caller, we have multiple ways to do so. The first try would
look like this one:

	List<Customer> loadCustomersWithException() throws PermissionDeniedException
	
Here we model an explicit exception for the case the caller has not the right to retrieve the list
of customers. The method returns a list of Customer objects while we assume that the user can
be retrieved from some container or ThreadLocal implementation and has not to be passed to each method.

The method signature above is easy to use and hard to misuse. Code that uses this methods is also
easy to read:

	try {
		List<Customer> customerList = api.loadCustomersWithException();
		doSomething(customerList);
	} catch (PermissionDeniedException e) {
		handleException();
	}
	
The reader immediately sees that a list of Customers is loaded and that we perform some follow-up
action only in case we don't get a PermissionDeniedException. But in terms of performance exceptions
do cost some CPU time as the JVM has to stop the normal code execution and walk up the stack to find
the position where the execution has to be continued. This is also extremely hard if we consider the
architecture of modern processors with their eager execution of code sequences in pipelines. So would
it be better in terms of performance to introduce another way of informing the caller about the missing 
permission?

The first idea would be to create another method in order to check the permission before calling the 
method that eventually throws an exception. The caller code would then look like this:

	if(api.hasPermissionToLoadCustomers()) {
		List<Customer> customerList = api.loadCustomers();
		doSomething(customerList);
	}
	
The code is still readable, but we have introduced another method call that also costs runtime. But now
we are sure that the exception won't be thrown; hence we can omit the try/catch block. This code
now violates the principle "Easy to use", as we now have to invoke two methods for one use case instead
of one. You have to pay attention not to forget the additional call for each retrieval operation. With
regard to the whole project, your code will be cluttered with hundreds of permission checks.

Another idea to overcome the exception is to provide an empty list to the API call and let the implementation
fill it. The return value can then be a boolean value indicating if the user has had the permission
to execute the operation or if the list is empty because no customers have been found. As this sounds
like early C or C++ programming where the caller manages the memory of the structures that the callees
use, this approach costs the construction of an empty list even if you don't have a permission to
retrieve the list at all:

	List<Customer> customerList = new ArrayList<Customer>();
	boolean hasPermission = api.loadCustomersWithListAsParameter(customerList);
	if(hasPermission) {
		doSomething(customerList);
	}
	
One last approach to solve the problem to return two pieces of information to the caller would be
the introduction of a new class, that holds next to the returned list of Customers also a boolean
flag indicating if the user has had the permission to perform this operation:

	CustomerList customerList = api.loadCustomersWithReturnClass();
	if(customerList.isUserHadPermission()) {
		doSomething(customerList.getCustomerList());
	}
	
Again we have to create additional objects that cost memory and performance, and we also have to deal
with an additional class that has noting more to do than to serve as a simple data holder to provide
the two pieces of information. Although this approach is again easy to use and creates readable code,
it creates additional overhead in order to maintain the separate classes.

After having introduced these different approaches it is now time to measure their performance, one
time for the case the caller has the permission and one time for the case the caller does not have
the necessary permission. The results in the following table are shown for the first case with 
1.000.000 repetitions:

|Measurement | Time[ms] |
|------------|----------|
|testLoadCustomersWithExceptionWithPermission |	33 |
|testLoadCustomersWithExceptionAndCheckWithPermission |	34 |
|testLoadCustomersWithReturnClassWithPermission	 | 41 |
|testLoadCustomersWithListAsParameterWithPermission |	66 |

As we have expected before, the two approaches that introduce an additional class respectively
pass an empty list cost more performance than the the approaches that use an exception. Even the approach
that uses a dedicated method call to check for the permission is not much slower than the one without it.

The following table now shows the results for the case where the caller does not have the permission
to retrieve the list:

|Measurement | Time[ms] |
|------------|----------|
|testLoadCustomersWithExceptionNoPermission |	1187 |
|testLoadCustomersWithExceptionAndCheckNoPermission |	5 |
|testLoadCustomersWithReturnClassNoPermission |	4 |
|testLoadCustomersWithListAsParameterNoPermission |	5 |

Not suprisingly the approach where a dedicated exception is thrown is much slower than the other
approaches. The magnitude of this impact is much higher then one would expect before. But from the table
above we already know the solution for this case: Just introduce another method that can be used
to check for the permission ahead, in case you expect a lot of permission denied use cases. The huge
difference in runtime between the with and without permission use cases can be explained by the fact
that I have returned an ArrayList with one Customer object in case the caller was in possesion of the
permission; hence the loadCustomer() calls where a bit more expensive than in case the user did not
posses this permission.

Conclusion: When performance is a critical factor, you also have to consider it when designing a new
API. As we have seen from the measurements above, this may lead to solutions that violate common
prinicples of API design like "easy to use" and "hard to misuse".