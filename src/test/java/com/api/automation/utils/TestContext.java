package com.api.automation.utils;

public final class TestContext {

    private static String contactId;

    private TestContext() {
    }

    public static void setContactId(String id) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "Contact ID cannot be null or empty.");
        }

        contactId = id;
    }

    public static String getContactId() {

        if (contactId == null || contactId.isBlank()) {
            throw new IllegalStateException(
                    "Contact ID is not available. "
                    + "CreateContactTest must run first.");
        }

        return contactId;
    }
}