package edu.iua.calculator.business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import edu.iua.calculator.model.Addresses;
import edu.iua.calculator.model.HibernateUtil;
import edu.iua.calculator.model.Taxes;

/**
 * Taxes Calculator returns a calculated total amount based on taxes percentages
 */
public class TaxesCalculator {
	public static void main(String[] args) {
		System.out.print("Enter amount value: ");

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		try {
			float amount = Float.parseFloat(br.readLine());
			HashMap<String, Float> calculatedTax = calculate(amount);

			JSONObject jo = new JSONObject(calculatedTax);
			System.out.println(jo);

//            int addressId = saveAddress();

			int idUpdate = 2;
			int idDelete = 3;

			updateAddress(idUpdate);
			System.out.printf("Address with id = %d was updated succesfully.", idUpdate);

			deleteAddress(idDelete);
			System.out.printf("Address with id = %d was deleted succesfully.", idDelete);
		} catch (IOException e) {
			System.out.println("Sorry, an error has occurred. Please try again!");
		} catch (NumberFormatException e) {
			System.out.println("Invalid amount value. Please try again!");
		}

	}

	public static HashMap<String, Float> calculate(float amount) {
		HashMap<String, Float> calculatedTaxesAmount = new HashMap<String, Float>();

		Taxes taxes = new Taxes();
		HashMap<String, Float> taxesPercentages = getTaxesPercentage();

		float totalAmount = amount;
		for (String tax : taxesPercentages.keySet()) {
			float taxValue = taxesPercentages.get(tax);
			float applicableTax = amount * taxValue;
			calculatedTaxesAmount.put(tax, applicableTax);
			totalAmount = totalAmount + applicableTax;
		}

		return calculatedTaxesAmount;
	}

	private static HashMap<String, Float> getTaxesPercentage() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		HashMap<String, Float> taxesPercentage = new HashMap<String, Float>();

		try {
			tx = session.beginTransaction();
			List taxes = session.createQuery("FROM Taxes").list();

			for (Iterator iterator = taxes.iterator(); iterator.hasNext();) {
				Taxes tax = (Taxes) iterator.next();
				taxesPercentage.put(tax.getTaxName(), tax.getTaxPercentage());
			}

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

		return taxesPercentage;
	}

	private static int saveAddress() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		int id = -1;

		Addresses address = new Addresses();
		address.setStreet("Fuerza Aerea Argentina");
		address.setNumber(6500);
		address.setCity("Cordoba");
		address.setState("Cordoba");
		address.setCountry("Argentina");
		address.setZipCode(5000);

		try {
			tx = session.beginTransaction();
			id = (Integer) session.save(address);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}

		return id;
	}

	private static void updateAddress(int addressId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Addresses address = session.get(Addresses.class, addressId);

			if (address == null) {
				throw new RuntimeException();
			}

			address.setStreet("Rondeau");
			address.setNumber(150);

			session.update(address);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

	private static void deleteAddress(int addressId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			Addresses address = session.get(Addresses.class, addressId);

			if (address == null) {
				throw new RuntimeException();
			}

			session.delete(address);

			tx.commit();
		} catch (RuntimeException e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
	}

}