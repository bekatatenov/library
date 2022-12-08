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
@Table(name="author")
public class Author implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="author_id", nullable=false)
  private Integer id;

  @NonNull
  @Column(name="author_name", length=320, nullable=false)
  private String name;

  @Column(name="author_add_date", nullable=false, updatable=false, insertable=false,
          columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private Timestamp addDate;

  @Column(name="author_status", length=12, nullable=false)
  private String status = "ACTIVE";

  @ManyToMany
  @JoinTable(
    name="book_author_map",
    joinColumns={ @JoinColumn(name="author_id") },
    inverseJoinColumns={ @JoinColumn(name="book_id") }
  )
  private List<Book> books;

}
