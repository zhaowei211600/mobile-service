package com.third.enterprise.util;

public enum FileTypeEnum {

    /**
     * JEPG.
     */
    JPEG("FFD8FF"),

    /**
     * PNG.
     */
    PNG("89504E47"),

    /**
     * GIF.
     */
    GIF("47494638"),

    /**
     * Adobe Acrobat.
     */
    PDF("255044462D312E"),

    /**
     * MS Word/Excel.
     */
    DOC("D0CF11E0"),

    DOCX("504B0304140006000800"),


    WPS("d0cf11e0a1b11ae10000");

    private String value = "";

    private FileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
