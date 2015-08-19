package org.in.app;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Singleton;

import org.in.model.Customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Singleton
public class AppConfig {
	private final static AtomicLong idCounter = new AtomicLong();
	private final static List<Customer> custDb = new CopyOnWriteArrayList<Customer>();

	private final static ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

	public static AtomicLong getIdcounter() {
		return idCounter;
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static List<Customer> getCustomerdb() {
		return custDb;
	}

	public static Customer fetchCustomer(long id) {
		for (Customer customer : custDb) {
			if (customer.getId() == id) {
				return customer;
			}
		}

		return null;
	}

	public static boolean isCustomerPresent(String name) {
		for (Customer customer : custDb) {
			if (customer.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

	public static boolean removeCustomer(long id) {
		for (Customer customer : custDb) {
			if (customer.getId() == id) {
				custDb.remove(customer);
				return true;
			}
		}

		return false;
	}
}
