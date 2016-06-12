package com.infinityworks.foodHygieneRaings.entities;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Subset representation of food agency establishment
 */
public class Establishment {

    private final int id;
    private final String name;
    private final String authorityCode;
    private final String ratingKey;
    private final String ratingValue;

    public Establishment(int id, String name, String authorityCode, String ratingKey, String ratingValue) {
        checkArgument(id > 0, "Invalid Argument");
        checkArgument(isNotBlank(name));
        checkArgument(isNotBlank(authorityCode));
        checkArgument(isNotBlank(ratingKey));
        checkArgument(isNotBlank(ratingValue));

        this.id = id;
        this.name = name;
        this.authorityCode = authorityCode;
        this.ratingKey = ratingKey;
        this.ratingValue = ratingValue;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthorityCode() {
        return authorityCode;
    }

    public String getRatingKey() {
        return ratingKey;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Establishment that = (Establishment) o;
        return id == that.id &&
                Objects.equal(name, that.name) &&
                Objects.equal(authorityCode, that.authorityCode) &&
                Objects.equal(ratingKey, that.ratingKey) &&
                Objects.equal(ratingValue, that.ratingValue);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, authorityCode, ratingKey, ratingValue);
    }
}
