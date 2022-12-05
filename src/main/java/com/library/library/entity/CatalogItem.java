package com.library.library.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="catalog_item")
public class CatalogItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="catalog_item_id", nullable=false)
    private Integer id;

    @NonNull
    @Column(name="catalog_item_source_id", nullable=false)
    private Integer sourceId;

    @NonNull
    @Column(name="catalog_item_source_filter", length=32, nullable=false)
    private String sourceFilter;

    @Transient
    private String sourceName;

    @Column(name="catalog_item_add_date", nullable=false, updatable=false, insertable=false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp addDate;

    @Column(name="catalog_item_status", length=12, nullable=false)
    private String status = "ACTIVE";

    @ManyToOne
    @JoinColumn(name="catalog_id")
    private Catalog catalog;

}
