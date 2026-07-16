package com.api.automation.payload;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ContactPayload {

    private ContactPayload() {
    }

    public static Map<String, Object> createContact() {

        String uniqueId =
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("firstName", "Taterao");
        requestBody.put("lastName", "Gaikwad");
        requestBody.put("birthdate", "1995-05-05");
        requestBody.put(
                "email",
                "taterao." + uniqueId + "@gmail.com");

        requestBody.put("phone", "9876543210");
        requestBody.put("street1", "MG Road");
        requestBody.put("street2", "Near City Mall");
        requestBody.put("city", "Pune");
        requestBody.put("stateProvince", "Maharashtra");
        requestBody.put("postalCode", "411001");
        requestBody.put("country", "India");

        return requestBody;
    }

    public static Map<String, Object> updateContact() {

        Map<String, Object> requestBody = createContact();

        requestBody.put("firstName", "Suraj");
        requestBody.put("city", "Mumbai");
        requestBody.put("phone", "9999999999");

        return requestBody;
    }

    public static Map<String, Object> patchContact() {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("firstName", "Updated");
        requestBody.put("phone", "8888888888");

        return requestBody;
    }
}