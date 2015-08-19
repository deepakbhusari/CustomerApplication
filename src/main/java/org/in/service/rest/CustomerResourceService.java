package org.in.service.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.URI;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.in.app.AppConfig;
import org.in.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.inject.*;

@Path("/customers")
public class CustomerResourceService {
	private Logger l = LoggerFactory.getLogger(CustomerResourceService.class);

	@Inject
	public CustomerResourceService() {

	}
	// @GET
	// @Path("/hello/{parameter}")
	// public Response responseMsg(@PathParam("parameter") String parameter,
	//
	// @DefaultValue("Nothing to say") @QueryParam("value") String value) {
	// String output = "Hello from: " + parameter + " : " + value;
	//
	// return Response.status(200).entity(output).build();
	//
	// }

	@POST
	@Path("/add")
	// @Consumes("application/xml;charset=utf-8")
	public Response createCustomer(String is) {
		Customer customer;

		try {
			customer = AppConfig.getMapper().readValue(is, Customer.class);

			if (customer.getId() == 0) {

				// if (!AppConfig.isCustomerPresent(customer.getName())) {
				customer.setId(AppConfig.getIdcounter().incrementAndGet());
				AppConfig.getCustomerdb().add(customer);
				l.debug("customer {}", customer.getId());
			} else {
				// Update record
				boolean response = AppConfig.removeCustomer(customer.getId());// getCustomerdb().remove(customer);
				if (response) {
					AppConfig.getCustomerdb().add(customer);
				}
			}

			return Response.created(URI.create("/customers/all" + customer.getId())).build();
		} catch (IOException e) {
			l.error("{}", e.getMessage());
		}

		// Customer customer = readCustomer(is);
		// return Response.status(200).entity("customers/" +
		// customer.getId()).build();
		return null;
	}

	// @PUT
	// @Path("{id}")
	// @Consumes("application/xml")
	// public void updateCustomer(@PathParam("id") int id, String is) {
	// Customer update = readCustomer(is);
	// Customer current = AppConfig.getCustomerdb().get(id);
	// if (current == null)
	// throw new WebApplicationException(Response.Status.NOT_FOUND);
	//
	// current.setName(update.getName());
	// current.setCountry(update.getCountry());
	// }

	@GET
	@Path("/id/{id}")
	// @Produces("application/xml")
	public Response getCustomer(@PathParam("id") long id) {
		final Customer customer = AppConfig.fetchCustomer(id);
		if (customer == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
			// return "Customer not found " + id;//
			// Response.status(200).entity("Customer
			// not found " + id).build();
		} else {
			try {
				return Response.status(200)
						.entity(AppConfig.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(customer))
						.build();
			} catch (IOException e) {
				l.error("{}", e.getMessage());
			}
		}

		return null;
	}

	@GET
	@Path("/all")
	public String getAllCustomer() {
		List<Customer> db = AppConfig.getCustomerdb();
		// XStream xStream = new XStream();
		// xStream.alias("map", java.util.Map.class);
		// String output = xStream.toXML(db);
		l.debug("db size {}", db.size());

		try {

			return AppConfig.getMapper().writeValueAsString(db);
			// return
			// Response.status(200).entity(AppConfig.getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(db)).build();
		} catch (Exception e) {
			l.error("{}", e.getMessage());
		}

		return null;
	}

	protected Customer readCustomer(String is) {
		// protected Customer readCustomer(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(is)));
			Element root = doc.getDocumentElement();

			Customer cust = new Customer();

			NodeList nodes = root.getChildNodes();

			for (int i = 0; i < nodes.getLength(); ++i) {
				Element element = (Element) nodes.item(i);
				// if (element.getTagName().equals("id")) {
				// cust.setId(Long.valueOf(element.getTextContent()));
				// }
				if (element.getTagName().equals("name")) {
					cust.setName(element.getTextContent());
				}
				if (element.getTagName().equals("country")) {
					cust.setCountry(element.getTextContent());
				}
			}

			return cust;
		} catch (Exception e) {
			l.error("error {}", e.getMessage());
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	protected void outputCustomer(OutputStream os, Customer cust) throws IOException {
		PrintStream writer = new PrintStream(os);
		writer.println("<customer id=\"" + cust.getId() + "\">");
		writer.println("   <first-name>" + cust.getName() + "</first-name>");
		writer.println("   <country>" + cust.getCountry() + "</country>");
		writer.println("</customer>");
	}
}
