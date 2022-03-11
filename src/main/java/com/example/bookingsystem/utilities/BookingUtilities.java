package com.example.bookingsystem.utilities;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookingUtilities {

	public Boolean emailPhoneValid(String email, String phone) {
		if (Integer.parseInt(email) == 0 && Integer.parseInt(phone) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public int randomNumber(int length, int maxid) {
		String newuserid = null;
		int x = 0;
		Random random = new Random();
		if (length == 4) {

			x = random.nextInt(899) + 100;
			newuserid = String.valueOf(maxid + 1) + String.valueOf(x);

		} else {
			x = random.nextInt(8999) + 1000;
			newuserid = String.valueOf(maxid + 1) + String.valueOf(x);
		}
		return Integer.parseInt(newuserid);
	}

	public String splitPrice(String prices) {

		Map<String, Integer> priceMap = new HashMap<String, Integer>();
		String[] pairs = prices.split(",");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split(":");
			priceMap.put(keyValue[0], Integer.valueOf(keyValue[1]));
		}
		return priceMap.toString();
	}

	public List<String> createList(String flightList) {

		String replace = flightList.replace("[", "");
		String replace1 = replace.replace("]", "");
		List<String> newList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
		return newList;
	}

	public List<String> removeSeat(List<String> availableSeat, List<String> selectedSeat) {

		for (int i = 0; i < selectedSeat.size(); i++) {
			String deleteSeat = selectedSeat.get(i);
			availableSeat.remove(deleteSeat);
		}
		return availableSeat;

	}

	public List<String> addSeat(List<String> availableSeat, List<String> selectedSeat) {

		for (int i = 0; i < selectedSeat.size(); i++) {
			String addSeat = selectedSeat.get(i);
			availableSeat.add(addSeat);
		}
		return availableSeat;

	}

	public int price(List<String> selectedSeat, int price) {
		for (int i = 0; i < selectedSeat.size(); i++) {
			price = price * i;
			System.out.println(price);
		}
		return price;

	}

	public int sizeOfList(List<String> selectedSeat) {
		String size;
		int sizeInt = 0;
		for (int i = 0; i < selectedSeat.size(); i++) {

			size = selectedSeat.get(i);
			sizeInt = i + 1;
			if (size.equals("")) {
				System.out.println("checking 111  " + size);
				sizeInt = 0;

			}
			break;
		}

		return sizeInt;

	}

	public boolean search(List<String> searchList, List<String> selectedSeat) {
		boolean search = false;

		// for (String element : searchList) {
		// for (int i = 0; i < selectedSeat.size(); i++) {
		// if (element.contains(selectedSeat.get(i))) {
		// System.out.println(element);
		// search = true;
		// System.out.println(search);
		// }
		//
		// }
		// }

		Collection listOne = new ArrayList(searchList);
		Collection listTwo = new ArrayList(selectedSeat);

		listOne.retainAll(listTwo);
		System.out.println(listOne);
		if (listOne.equals(listTwo)) {
			System.out.println(true);
			search = true;
		} else {
			System.out.println(false);
			search = false;
		}

		System.out.println(search);
		return search;
	}

	public String dateDay(String Date) throws Exception {
		String sDate1 = Date;
		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		String dayWeekText = new SimpleDateFormat("EEEE").format(date1);
		return dayWeekText;

	}

	public static boolean validateJavaDate(String strDate) {
		/* Check if date is 'null' */
		String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(strDate);
		System.out.println(strDate + " : " + matcher.matches());
		if (matcher.matches() == true) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean datebetween(String from, String to, String selected) throws ParseException {

		boolean validDate = false;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		Date firstDate = simpleDateFormat.parse(from);
		Date secondDate = simpleDateFormat.parse(to);
		Date thirdDate = simpleDateFormat.parse(selected);
		long difference = thirdDate.getTime() - firstDate.getTime();
		long difference2 = secondDate.getTime() - thirdDate.getTime();
		long differenceDates = difference / (24 * 60 * 60 * 1000);
		long differenceDates2 = difference2 / (24 * 60 * 60 * 1000);

		// Convert long to String
		String dayDifference = Long.toString(differenceDates);
		String dayDifference2 = Long.toString(differenceDates2);
		System.out.println(dayDifference2 + "Day Differnec is " + dayDifference);
		long differncebetween = differenceDates * differenceDates2;
		if (differncebetween >= 0) {
			System.out.println(dayDifference2 + "Day Differnec is True " + dayDifference);
			validDate = true;
		} else {
			System.out.println(dayDifference2 + "Day Differnec is False" + dayDifference);
			validDate = false;
		}
		return validDate;
	}

	public static boolean validDay(String day) {
		boolean valid = false;
		String[] days = new DateFormatSymbols().getWeekdays();
		for (int i = 0; i < days.length; i++) {
			String weekday = days[i];
			if (weekday.equals(day)) {
				valid = true;
			}
		}

		return valid;

	}
}
