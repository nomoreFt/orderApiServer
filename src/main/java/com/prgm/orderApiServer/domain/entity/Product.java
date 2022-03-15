package com.prgm.orderApiServer.domain.entity;

import com.prgm.orderApiServer.domain.entity.base.JpaBaseTimeEntity;
import lombok.Builder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Entity
@Builder
public class Product extends JpaBaseTimeEntity {

    @GeneratedValue @Id
    @Column(name = "product_id")
    private Long seq;

    @NotNull
    private String name;

    private String details;

    @Column(name = "review_count")
    private int reviewCount;

    public Product(String name, String details) {
        this(name, details, 0);
    }

    public Product(String name, String details, int reviewCount) {
        checkArgument(isNotEmpty(name), "name must be provided");
        checkArgument(
                name.length() >= 1 && name.length() <= 50,
                "name length must be between 1 and 50 characters"
        );
        checkArgument(
                isEmpty(details) || details.length() <= 1000,
                "details length must be less than 1000 characters"
        );

        this.name = name;
        this.details = details;
        this.reviewCount = reviewCount;
    }

    public void setName(String name) {
        checkArgument(isNotEmpty(name), "name must be provided");
        checkArgument(
                name.length() >= 1 && name.length() <= 50,
                "name length must be between 1 and 50 characters"
        );

        this.name = name;
    }

    public void setDetails(String details) {
        checkArgument(
                isEmpty(details) || details.length() <= 1000,
                "details length must be less than 1000 characters"
        );

        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(seq, product.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("seq", seq)
                .append("name", name)
                .append("details", details)
                .append("reviewCount", reviewCount)
                .toString();
    }
}