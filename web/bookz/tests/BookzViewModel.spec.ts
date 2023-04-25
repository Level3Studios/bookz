import { describe, expect, it } from "vitest";
import { BookzViewModel } from "../src/viewmodels/BookzViewModel";
import type { Item, VolumeInfo } from "../src/network/BookzInterface";
import dayjs from "dayjs";

const invalidVolume: VolumeInfo = {
  title: "Hello",
  maturityRating: "ADULT",
};
const singleAuthorVolume: VolumeInfo = {
  title: "Guide to Gamma Rays",
  authors: ["Bruce Banner"],
  publishedDate: "2022-01-12",
  averageRating: 3.5,
  ratingsCount: 40,
  maturityRating: "SCIENCE_FICTION",
};

const multiAuthorVolume: VolumeInfo = {
  title: "Beginners guide to robotics",
  authors: ["Hank Pym", "Tony Stark"],
  publishedDate: "2021-04-05",
  averageRating: 5,
  ratingsCount: 2,
  maturityRating: "NOT_MATURE",
};

const bookInvalid: Item = {
  id: "0",
  volumeInfo: invalidVolume,
};

const bookSingle: Item = {
  id: "133234",
  volumeInfo: singleAuthorVolume,
};

const bookMulti: Item = {
  id: "556432",
  volumeInfo: multiAuthorVolume,
};

describe("test author formatting", function () {
  let viewModel = BookzViewModel.getInstance();
  let invalidAuthors = viewModel.getAuthors(bookInvalid);
  let singleAuthors = viewModel.getAuthors(bookSingle);
  let multiAuthors = viewModel.getAuthors(bookMulti);
  it("check author format on books", function () {
    expect(invalidAuthors).toBe("Unknown");
    expect(singleAuthors).toBe("Bruce Banner");
    expect(multiAuthors).toBe("Hank Pym, Tony Stark");
  });
});

describe("test published date formatting", function () {
  let viewModel = BookzViewModel.getInstance();
  let invalidDate = viewModel.getPublishedDate(bookInvalid);
  let currentDate = dayjs(new Date()).format("MM/D/YYYY");
  let bookDate = viewModel.getPublishedDate(bookSingle);
  it("check date format on books", function () {
    expect(invalidDate).toBe(currentDate);
    expect(bookDate).toBe("01/12/2022");
  });
});

describe("test rating formatting", function () {
  let viewModel = BookzViewModel.getInstance();
  let invalidRating = viewModel.getRatingValue(bookInvalid);
  let singleRating = viewModel.getRatingValue(bookSingle);
  let multiRating = viewModel.getRatingValue(bookMulti);
  it("check the rating format for book", function () {
    expect(invalidRating).toBe("0 (0)");
    expect(singleRating).toBe("3.5 (40)");
    expect(multiRating).toBe("5 (2)");
  });
});

describe("test maturity formatting", function () {
  let viewModel = BookzViewModel.getInstance();
  let invalidRating = viewModel.fixBookRating(bookInvalid);
  let singleRating = viewModel.fixBookRating(bookSingle);
  let multiRating = viewModel.fixBookRating(bookMulti);
  it("check the maturity rating format for book", function () {
    expect(invalidRating).toBe("Adult");
    expect(singleRating).toBe("Science Fiction");
    expect(multiRating).toBe("Not Mature");
  });
});
