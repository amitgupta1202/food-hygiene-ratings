package com.infinityworks.foodHygieneRaings.entities;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Subset representation Food Agency Authority
 */
public class Authority {

    private final int id;
    private final String code;
    private final String name;
    private final String region;

    public Authority(int id, String code, String name, String region) {
        checkArgument(id > 0, "Invalid id!");
        checkArgument(isNotBlank(code));
        checkArgument(isNotBlank(name));
        checkArgument(isNotBlank(region));

        this.id = id;
        this.code = code;
        this.name = name;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return id == authority.id &&
                Objects.equal(code, authority.code) &&
                Objects.equal(name, authority.name) &&
                Objects.equal(region, authority.region);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, code, name, region);
    }
}
