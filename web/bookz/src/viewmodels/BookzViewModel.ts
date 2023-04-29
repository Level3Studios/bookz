import type { BooksModel, Item } from "../network/BookzInterface";
import type { BookModel } from "../network/BookzInterface";
import { allGenreCards } from "../network/GenreType";
import dayjs from "dayjs";
const baseURL: string = "https://www.googleapis.com/books/v1/volumes";
const APIKey: string = import.meta.env.VITE_GOOGLE_API_KEY;

export class BookzViewModel {
  private adventureBooks: Item[] = [];
  private fantasyBooks: Item[] = [];
  private scifiBooks: Item[] = [];
  private romanceBooks: Item[] = [];

  public storedLibraryBooks: Item[] = [];
  public storedWishlistBooks: Item[] = [];

  static instance: BookzViewModel;

  private constructor() {}

  public static getInstance(): BookzViewModel {
    if (!BookzViewModel.instance) {
      BookzViewModel.instance = new BookzViewModel();
    }
    return BookzViewModel.instance;
  }

  private buildQuery(
    searchTerm: string,
    language: string = "en",
    type: string = "books",
    maxResults: number
  ): string {
    var query = baseURL;
    query += `?q=${searchTerm}`;
    query += "&orderBy=newest";
    query += `&langRestrict=${language}`;
    query += `&printType=${type}`;
    query += `&maxResults=${maxResults.toString()}`;
    query += `&key=${APIKey}`;
    return query;
  }

  private async searchForBooks(
    searchString: string,
    maxResults: number
  ): Promise<Item[]> {
    let query = this.buildQuery(searchString, undefined, undefined, maxResults);
    let response = await fetch(query);
    let data = await response.json();
    let container = data as BooksModel;
    let books = container.items;
    return books;
  }

  public async fetchBook(bookId: string): Promise<Item> {
    var query = baseURL;
    query += `/${bookId}`;
    query += `?key=${APIKey}`;
    let response = await fetch(query);
    let data = await response.json();
    let book = data as BookModel;
    return book;
  }

  public didSelectGenreCard(id: number): Promise<Item[]> {
    let selectedType = allGenreCards.find((item) => item.id === id);
    let needsSearch = this.genreNeedsSearch(selectedType.id);
    if (needsSearch) {
      let search = this.searchForBooks(selectedType.searchText, 5);
      this.setResultsFromSearch(search, id);
      return search;
    } else {
      return this.getSavedSearch(id);
    }
  }

  private genreNeedsSearch(id: number): Boolean {
    switch (id) {
      case 0:
        return this.adventureBooks.length === 0 ? true : false;
      case 1:
        return this.fantasyBooks.length === 0 ? true : false;
      case 2:
        return this.scifiBooks.length === 0 ? true : false;
      case 3:
        return this.romanceBooks.length === 0 ? true : false;
      default:
        return false;
    }
  }

  private getSavedSearch(id: number): Promise<Item[]> {
    switch (id) {
      case 0:
        return Promise.resolve(this.adventureBooks);
      case 1:
        return Promise.resolve(this.fantasyBooks);
      case 2:
        return Promise.resolve(this.scifiBooks);
      case 3:
        return Promise.resolve(this.romanceBooks);
      default:
        break;
    }
  }

  private setResultsFromSearch(results: Promise<Item[]>, id: number) {
    switch (id) {
      case 0:
        results.then((items) => {
          this.adventureBooks = items;
        });
        break;
      case 1:
        results.then((items) => {
          this.fantasyBooks = items;
        });
        break;
      case 2:
        results.then((items) => {
          this.scifiBooks = items;
        });
        break;
      case 3:
        results.then((items) => {
          this.romanceBooks = items;
        });
        break;
      default:
        break;
    }
  }

  // MARK Storage helpers
  private isBookInLibrary(book: Item): boolean {
    return this.storedLibraryBooks.some((item) => item.id === book.id);
  }

  private isBookInWishlist(book: Item): boolean {
    return this.storedWishlistBooks.some((item) => item.id === book.id);
  }

  public saveBookToLibrary(book: Item) {
    if (!this.isBookInLibrary(book)) {
      this.storedLibraryBooks.push(book);
    }
  }

  public removeBookFromLibrary(book: Item) {
    if (this.isBookInLibrary(book)) {
      let filteredStore = this.storedLibraryBooks.filter(
        (item) => item.id !== book.id
      );
      this.storedLibraryBooks = filteredStore;
    }
  }

  public saveBookToWishlist(book: Item) {
    if (!this.isBookInWishlist(book)) {
      this.storedWishlistBooks.push(book);
    }
  }

  public removeBookFromWishlist(book: Item) {
    if (this.isBookInWishlist(book)) {
      let filteredStore = this.storedWishlistBooks.filter(
        (item) => item.id !== book.id
      );
      this.storedWishlistBooks = filteredStore;
    }
  }

  // MARK: Helpers for UI
  public getAuthors(book: Item): string {
    let authors = book.volumeInfo.authors;
    if (authors === undefined || authors.length === 0) {
      return "Unknown";
    } else {
      return authors.join(", ");
    }
  }

  public getRatingValue(book: Item): string {
    let rating = book.volumeInfo.averageRating;
    var ratingValue = rating === undefined ? 0 : rating;
    let count = book.volumeInfo.ratingsCount;
    let countValue = count === undefined ? 0 : count;
    return `${ratingValue} (${countValue})`;
  }

  public getPublishedDate(book: Item): string {
    let publishedDate = book.volumeInfo.publishedDate;
    let date = publishedDate === undefined ? new Date() : publishedDate;
    return dayjs(date).format("MM/D/YYYY");
  }

  public getCategories(book: Item): string {
    let categories = book.volumeInfo.categories;
    let categoryList =
      categories === undefined ? "Unknown" : categories.join(", ");
    return categoryList;
  }

  public fixBookRating(book: Item): string {
    let rating = book.volumeInfo.maturityRating;
    let fixedRating = rating.replace("_", " ");
    return this.firstLetterUpper(fixedRating);
  }

  private firstLetterUpper(string: string): string {
    var sentence = string.toLowerCase().split(" ");
    for (var i = 0; i < sentence.length; i++) {
      sentence[i] = sentence[i][0].toUpperCase() + sentence[i].slice(1);
    }
    return sentence.join(" ");
  }
}
