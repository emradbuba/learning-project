package com.gitlab.emradbuba.learning.learningproject.exceptions;

public enum LPServiceExceptionErrorCode {

    PERSON_ID_NOT_FOUND("person_id_not_found", "Given personId does not exist in database"),

    ID_CARD_ID_NOT_FOUND("id_card_id_not_found", "Person does not have IDCard with given id"),
    ID_CARD_ALREADY_DEFINED("id_card_already_defined", "Person already has an ID Card - cannot add next one"),

    INCORRECT_UUID_FORMAT("uuid_format_incorrect", "The UUID number has incorrect format and cannot be processed"),
    INCORRECT_PERSON_FIRST_NAME_IS_EMPTY("person_first_name_is_empty", "Cannot create a person without a name"),
    INCORRECT_PERSON_SURNAME_IS_EMPTY("person_surname_name_is_empty", "Cannot create a person without a surname"),
    INCORRECT_PERSON_BIRTH_DATE_IS_EMPTY("person_birth_date_name_is_empty", "Cannot create a person without a date of birth"),
    INCORRECT_PERSON_BIRTH_DATE_IS_TOO_YOUNG("person_birth_date_too_young", "Cannot create a person of age under 18"),
    INCORRECT_ID_CARD_DATE_EMPTY("id_card_validity_date_empty", "IDCard must have a validity date"),
    INCORRECT_ID_CARD_DATE_INVALID("id_card_validity_date_exceeded", "IDCard validity date is exceeded"),
    INCORRECT_ID_CARD_SERIAL_NUMBER_EMPTY("id_card_serial_number_empty", "IdCard must have a serial number"),
    INCORRECT_ID_CARD_PUBLISHER_EMPTY("id_card_publisher_empty", "IdCard must have a publisher name");



    private final String reasonCode;
    private final String description;

    LPServiceExceptionErrorCode(String reasonCode, String description) {
        this.reasonCode = reasonCode;
        this.description = description;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public String getDescription() {
        return description;
    }
}