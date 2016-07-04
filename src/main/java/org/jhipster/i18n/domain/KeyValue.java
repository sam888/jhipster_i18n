package org.jhipster.i18n.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KeyValue.
 */
@Entity
@Table(name = "key_value")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "keyvalue")
public class KeyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "key_value_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "property", nullable = false)
    private String property;

    @NotNull
    @Column(name = "property_value", nullable = false)
    private String propertyValue;

    @Column(name = "description")
    private String description;

    @ManyToOne
    private ResourceBundle resourceBundle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        if(keyValue.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, keyValue.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KeyValue{" +
            "id=" + id +
            ", property='" + property + "'" +
            ", propertyValue='" + propertyValue + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
