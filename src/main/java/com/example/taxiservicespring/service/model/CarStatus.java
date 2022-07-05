package com.example.taxiservicespring.service.model;

public enum CarStatus {
    READY, BUSY, INACTIVE;

    public static String getName(int id) {
	return CarStatus.values()[id].name().toLowerCase();
    }

    public static int getId(String name) {
	int id = 0;

	for (int i = 0; i < CarStatus.values().length; i++) {
	    if (TripStatus.values()[i].name().equalsIgnoreCase(name)) {
		id = i;
	    }
	}
	return id;
    }
}
