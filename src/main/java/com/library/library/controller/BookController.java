package com.library.library.controller;

import com.library.library.model.Book;
import com.library.library.service.AccountService;
import com.library.library.service.BookService;
import com.library.library.view.TemplateView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class BookController extends TemplateView {

  private final AccountService accountService;
  private final BookService bookService;

  @Autowired
  public BookController(AccountService accountService,
                        BookService bookService) {
    this.accountService = accountService;
    this.bookService = bookService;
  }

  @GetMapping("/books")
  public String getBooks() {
    return "redirect:/search";
  }

  @GetMapping("/books/{id}")
  public String getBook(Model model, @PathVariable Integer id) {
    Book book = bookService.getById(id);
    model.addAttribute("book", book);

    if (accountService.isLoggedIn()) {
      model.addAttribute("lists", accountService.getLoggedInAccount().getCatalogs());
    }

    return loadView(model, "books/book");
  }

  @GetMapping("/books/{id}/read")
  public String getBookReader(@RequestParam(required=false) String book) {
    return "books/bibi";
  }

}
