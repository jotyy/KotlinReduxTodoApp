package top.jotyy.kotlinreduxtodo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.rekotlin.Store
import top.jotyy.kotlinreduxtodo.reducers.todoReducer
import top.jotyy.kotlinreduxtodo.ui.TodoViewModel

val appModule = module {
    viewModel {
        TodoViewModel()
    }
}

val store = Store(
    reducer = ::todoReducer,
    state = null,
    middleware = listOf(),
    automaticallySkipRepeats = true
)

class TodoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        startKoin {
            androidLogger()
            androidContext(this@TodoApplication)

            modules(appModule)
        }
    }
}
