package start;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Orders;
import model.Product;

public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException {


		ProductBLL productBLL = new ProductBLL();
		Product product = null;

		ClientBLL clientBLL = new ClientBLL();
		Client client = null;

		OrderBLL orderBLL = new OrderBLL();
		Orders order = null;

		try {
			//product = productBLL.findProductById(1);
			//client = clientBLL.findClientById(1);
			order = orderBLL.findOrderById(1);

		} catch (Exception ex) {
			LOGGER.log(Level.INFO, ex.getMessage());
		}

		// obtain field-value pairs for object through reflection
		ReflectionExample.retrieveProperties(order);

	}

}
