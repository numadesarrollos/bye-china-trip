import SwiftUI
import FirebaseCore
import Shared

@main
struct iOSApp: App {

    init() {
        // Firebase must be configured before any Kotlin code uses it
        FirebaseApp.configure()

        // Start Koin DI (must run before MainViewController is created)
        KoinHelperKt.initKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
