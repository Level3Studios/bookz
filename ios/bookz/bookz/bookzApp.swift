//
//  bookzApp.swift
//  bookz
//
//  Created by Aldana, Sal on 1/13/23.
//

import SwiftUI

@main
struct bookzApp: App {
    let dbController = DatabaseController.shared
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.managedObjectContext, dbController.container.viewContext)
        }
    }
}
