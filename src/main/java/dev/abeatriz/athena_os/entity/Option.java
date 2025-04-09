package dev.abeatriz.athena_os.entity;

import dev.abeatriz.athena_os.entity.enums.OptionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private OptionType type;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionValue> values;

    public Option() { }

    public Option(String title, OptionType type) {
        this.title = title;
        this.type = type;

    }

    public void addOptionValue(OptionValue optionValue) {
        values.add(optionValue);
        optionValue.setOption(this);
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public OptionType getType() {
        return type;
    }

    public void setType(OptionType type) {
        this.type = type;
    }

    public List<OptionValue> getValues() {
        return values;
    }

    public void setValues(List<OptionValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(getOptionId(), option.getOptionId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOptionId());
    }

    @Override
    public String toString() {
        return "Option{" +
            "optionId=" + optionId +
            ", product=" + product +
            ", title='" + title + '\'' +
            ", type=" + type +
            ", values=" + values +
            '}';
    }
}

