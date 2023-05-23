package bll.validators;

import model.Orders;

public interface Validator<T> {

	public void validate(T t);
}
