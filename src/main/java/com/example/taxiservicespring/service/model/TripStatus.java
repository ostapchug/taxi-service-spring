package com.example.taxiservicespring.service.model;

public enum TripStatus {
	NEW, ACCEPTED, COMPLETED, CANCELLED;

	public static String getName(int id) {

		return TripStatus.values()[id].name().toLowerCase();
	}

	public static int getId(String name) {
		int id = 0;

		for (int i = 0; i < TripStatus.values().length; i++) {
			if (TripStatus.values()[i].name().equalsIgnoreCase(name)) {
				id = i;
			}
		}

		return id;
	}

}
