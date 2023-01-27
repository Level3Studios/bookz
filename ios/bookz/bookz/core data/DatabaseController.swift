//
//  DatabaseController.swift
//  bookz
//
//  Created by Aldana, Sal on 1/26/23.
//

import Foundation
import CoreData

class DatabaseController {
    
    static let shared = DatabaseController()
    static let containerName = "bookzcontainer"
    static let entityName = "SavedBooks"
    
    let container: NSPersistentContainer
    
    //MARK: - Preview -
    static var preview: DatabaseController = {
        let controller = DatabaseController(inMemory: true)
        let viewContext = controller.container.viewContext
        let libraryBook = SavedBooks(context: viewContext)
        libraryBook.id = BooksModel.sampleBook.id
        libraryBook.type = "Library"
        do {
            try viewContext.save()
        } catch {
            //TODO: Handle Error
        }
        return controller
    }()
    
    
    //MARK: - Init -
    init(inMemory: Bool = false) {
        container = NSPersistentContainer(name: DatabaseController.containerName)
        if inMemory {
            container.persistentStoreDescriptions.first!.url = URL(fileURLWithPath: "/dev/null")
        }
        container.loadPersistentStores() { (storeDescription, error) in
            //TODO: Handle error
        }
        container.viewContext.automaticallyMergesChangesFromParent = true
    }
}
