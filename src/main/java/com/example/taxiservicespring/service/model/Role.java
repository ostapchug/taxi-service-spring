package com.example.taxiservicespring.service.model;

/**
 * Role entity.
 */
public enum Role {
    CLIENT, ADMIN;

    public static String getName(int id) {
	return Role.values()[id].name().toLowerCase();
    }

    public static int getId(String name) {
	int id = 0;

	for (int i = 0; i < Role.values().length; i++) {
	    if (Role.values()[i].name().equalsIgnoreCase(name)) {
		id = i;
	    }
	}
	return id;
    }
}
