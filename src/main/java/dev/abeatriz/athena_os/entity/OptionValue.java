package dev.abeatriz.athena_os.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "options_values")
public class OptionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_value_id")
    private Long optionValueId;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;

    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OptionValue() {
    }

    public OptionValue(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }


    public Long getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(Long optionValueId) {
        this.optionValueId = optionValueId;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OptionValue that = (OptionValue) o;
        return Objects.equals(getOptionValueId(), that.getOptionValueId()) && Objects.equals(getOption(), that.getOption());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOptionValueId(), getOption());
    }

    @Override
    public String toString() {
        return "OptionValue{" +
            "optionValueId=" + optionValueId +
            ", option=" + option +
            ", name='" + name + '\'' +
            ", price=" + price +
            '}';
    }
}
