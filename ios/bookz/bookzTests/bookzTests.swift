//
//  bookzTests.swift
//  bookzTests
//
//  Created by Aldana, Sal on 1/13/23.
//

import XCTest
@testable import bookz

final class bookzTests: XCTestCase {

    let viewModel = NetworkViewModel()
    var sampleFantasyBook: BookResponse!
    
    override func setUp() {
        super.setUp()
        let sampleVolume = VolumeInfo(title: "Book Title",
                                    authors: ["Scott Lang"],
                                    publisher: "Marvel Press",
                                    publishedDate: "2023-01-18",
                                    description: "A sample book for testing",
                                    industryIdentifiers: nil,
                                    readingModes: nil,
                                    pageCount: 100,
                                    printType: "Book",
                                    categories: ["Adventure"],
                                    averageRating: 4.0,
                                    ratingsCount: 25,
                                    maturityRating: "NOT_MATURE",
                                    allowAnonLogging: nil,
                                    contentVersion: nil,
                                    panelizationSummary: nil,
                                    imageLinks: nil,
                                    language: "en",
                                    previewLink:  nil,
                                    infoLink: nil,
                                    canonicalVolumeLink: nil)
        sampleFantasyBook = BookResponse(kind: "books#volume",
                                         id: UUID().uuidString,
                                         etag: UUID().uuidString,
                                         selfLink: nil,
                                         volumeInfo: sampleVolume,
                                         saleInfo: nil,
                                         accessInfo: nil)
    }
    override func tearDown() {
        super.tearDown()
        self.sampleFantasyBook = nil
    }
    
    func testUpdateView() {
        XCTAssertTrue(viewModel.libraryBooks.count == 0)
        XCTAssertTrue(viewModel.wishlistBooks.count == 0)
        
        viewModel.updateView(view: .library, withBook: sampleFantasyBook)
        XCTAssertTrue(viewModel.libraryBooks.count == 1)
        viewModel.updateView(view: .library, withBook: sampleFantasyBook)
        XCTAssertTrue(viewModel.libraryBooks.count == 1)
    }

}
