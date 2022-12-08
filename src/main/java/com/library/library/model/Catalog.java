package com.library.library.model;

import lombok.*;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="catalog")
public class Catalog implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="catalog_id", nullable=false)
  private Integer id;

  @NonNull
  @Column(name="catalog_name", length=320, nullable=false)
  private String name;

  @Column(name="catalog_privacy", length=12, nullable=false)
  private String privacy = "PRIVATE";

  @Column(name="catalog_last_update", nullable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp lastUpdate;

  @Column(name="catalog_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="catalog_status", length=12, nullable=false)
  private String status = "ACTIVE";

  @ManyToOne
  @JoinColumn(name="account_id")
  private Account account;

  @OneToMany(mappedBy="catalog")
  private List<CatalogItem> catalogItems;

  public CatalogItem addCatalogItem(CatalogItem catalogItem) {
    getCatalogItems().add(catalogItem);
    catalogItem.setCatalog(this);
    return catalogItem;
  }

  public CatalogItem removeCatalogItem(CatalogItem catalogItem) {
    getCatalogItems().remove(catalogItem);
    catalogItem.setCatalog(null);
    return catalogItem;
  }

}
