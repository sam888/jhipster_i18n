package org.jhipster.i18n.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import org.jhipster.i18n.domain.enumeration.ResourceBundleStatus;

/**
 * A ResourceBundle.
 */
@Entity
@Table(name = "resource_bundle", uniqueConstraints={
    @UniqueConstraint(columnNames = {"locale_id", "module_id" }),
    @UniqueConstraint(columnNames = { "resource_bundle_name" } )
})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "resourcebundle")
public class ResourceBundle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "resource_bundle_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "resource_bundle_name")
    private String resourceBundleName;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(name = "status") @NotNull
    private ResourceBundleStatus status;

    @ManyToOne( fetch=FetchType.EAGER  )
    @JoinColumn(name="locale_id", nullable=false) @NotNull
    private Locale locale;

    @ManyToOne( fetch=FetchType.EAGER )
    @JoinColumn(name="module_id", nullable=false) @NotNull
    private Module module;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceBundleName() {
        return resourceBundleName;
    }

    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResourceBundleStatus getStatus() {
        return status;
    }

    public void setStatus(ResourceBundleStatus status) {
        this.status = status;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResourceBundle resourceBundle = (ResourceBundle) o;
        if(resourceBundle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resourceBundle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ResourceBundle{" +
            "id=" + id +
            ", resourceBundleName='" + resourceBundleName + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
