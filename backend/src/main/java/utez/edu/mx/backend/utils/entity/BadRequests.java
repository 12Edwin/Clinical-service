package utez.edu.mx.backend.utils.entity;

public enum BadRequests {
    BAD_ENCODED("Bad encoded text"),
    MALFORMED_REQUEST("Malformed request"),
    MISSING_FIELDS("Missing fields"),
    CODE_ALREADY_EXISTS("code already exists"),
    INVALID_ROLE("Invalid role"),
    USER_NOT_FOUND("User not found"),
    USER_LOCKED("User locked"),
    NOT_FOUND("Not found"),
    TREATMENT_NOT_FOUND("Treatment not found"),
    EXPEDIENT_NOT_FOUND("Expedient not found"),
    SPACE_NOT_FOUND("Space not found"),
    UNAUTHORIZED_USER("Unauthorized user"),
    UNREGISTERED_USER("Unregistered user"),
    INVALID_BIRTHDAY("Invalid birthday"),
    INVALID_FORMAT_DATES("Invalid format dates"),
    MAXIMUM_HOURS("Maximum 5 hours"),
    MINIMUM_HOURS("Minimum 1 hour"),
    INVALID_SCHEDULE("Invalid schedule"),
    INVALID_AFTER_DATE("Cannot be more than one month from now"),
    BUSY_SPACE("The space is busy in this schedule"),
    CANNOT_UPDATE_APPOINT("Cannot update a canceled or completed appointment"),
    PHONE_ALREADY_REGISTERED("Phone already registered"),
    PERSON_NOT_FOUND("Person not found"),
    PERSON_NOT_UPDATED("Person not updated"),

    // Success messages
    REQUESTS_SUCCESS("Request successful");


    private final String message;

    BadRequests(String message) {
        this.message = message;
    }

    public String getText() {
        return this.message;
    }
}
