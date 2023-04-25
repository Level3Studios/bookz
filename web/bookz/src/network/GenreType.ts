// declare enum
enum GenreTypes {
  ADVENTURE = 0,
  FANTASY,
  SCIFI,
  ROMANCE,
}

// setup functions for properties
namespace GenreTypes {
  export function allItems(): GenreTypes[] {
    return new Array(
      GenreTypes.ADVENTURE,
      GenreTypes.FANTASY,
      GenreTypes.SCIFI,
      GenreTypes.ROMANCE
    );
  }

  export function getSearchString(item: GenreTypes): string {
    var search = "";
    switch (item) {
      case GenreTypes.ADVENTURE:
        search = "adventure";
        break;
      case GenreTypes.FANTASY:
        search = "fantasy";
        break;
      case GenreTypes.SCIFI:
        search = "science%20fiction";
        break;
      case GenreTypes.ROMANCE:
        search = "romance";
        break;
    }
    return `+subject:${search}`;
  }
}

//interface for export
interface GenreItem {
  id: number;
  type: GenreTypes;
  searchText: string;
}

//class for UI
export class GenreCard implements GenreItem {
  id: number;
  type: GenreTypes;
  searchText: string;

  constructor(public item: GenreTypes) {
    this.id = item.valueOf();
    this.type = item;
    this.searchText = GenreTypes.getSearchString(item);
  }
}

export const allGenreCards: GenreCard[] = GenreTypes.allItems().map(
  (item) => new GenreCard(item)
);
