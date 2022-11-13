package ar.edu.itba.getaway.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categories_categoryId_seq")
    @SequenceGenerator(sequenceName = "categories_categoryId_seq", name = "categories_categoryId_seq", allocationSize = 1)
    @Column(name = "categoryId")
    private long categoryId;
    @Column(name = "categoryName", nullable = false, unique = true)
    private String categoryName;

    /* default */
    protected CategoryModel() {
        // Just for Hibernate
    }

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }

    //Constructor used in testing
    public CategoryModel(long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }


    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryModel)) {
            return false;
        }
        CategoryModel other = (CategoryModel) o;
        return this.getCategoryId() == other.getCategoryId() && this.getCategoryName().equals(other.getCategoryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId);
    }
}
