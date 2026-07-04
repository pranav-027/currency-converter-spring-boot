package com.miniproject.currency_converter.enums;

import lombok.Getter;

@Getter
public enum CurrencyCode {

    EUR("Euro"),
    USD("US Dollar"),
    JPY("Japanese Yen"),
    BGN("Bulgarian Lev"),
    CZK("Czech Republic Koruna"),
    DKK("Danish Krone"),
    GBP("British Pound Sterling"),
    HUF("Hungarian Forint"),
    PLN("Polish Zloty"),
    RON("Romanian Leu"),
    SEK("Swedish Krona"),
    CHF("Swiss Franc"),
    ISK("Icelandic Króna"),
    NOK("Norwegian Krone"),
    HRK("Croatian Kuna"),
    RUB("Russian Ruble"),
    TRY("Turkish Lira"),
    AUD("Australian Dollar"),
    BRL("Brazilian Real"),
    CAD("Canadian Dollar"),
    CNY("Chinese Yuan"),
    HKD("Hong Kong Dollar"),
    IDR("Indonesian Rupiah"),
    ILS("Israeli New Sheqel"),
    INR("Indian Rupee"),
    KRW("South Korean Won"),
    MXN("Mexican Peso"),
    MYR("Malaysian Ringgit"),
    NZD("New Zealand Dollar"),
    PHP("Philippine Peso"),
    SGD("Singapore Dollar"),
    THB("Thai Baht"),
    ZAR("South African Rand");

	/**
	 * -- GETTER --
	 *  Returns the full currency name.
	 */
	private final String currencyName;

    CurrencyCode(String currencyName) {
        this.currencyName = currencyName;
    }

	/**
     * Returns the enum from the currency code.
     * Example:
     * CurrencyCode.fromCode("USD")
     */
    public static CurrencyCode fromCode(String code) {
        if (code == null) {
            return null;
        }
        try {
            return CurrencyCode.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     * Returns the currency name directly from the code.
     * Example:
     * CurrencyCode.getCurrencyName("USD") -> "US Dollar"
     */
    public static String getCurrencyName(String code) {
        return fromCode(code).getCurrencyName();
    }
}
