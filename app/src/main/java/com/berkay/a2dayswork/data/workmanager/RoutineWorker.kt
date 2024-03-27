import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.berkay.a2dayswork.room.MyDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar

class RoutineWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val applicationContext = context.applicationContext

    override suspend fun doWork(): Result {
        return try {
            // Get the current date
            val currentDate = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyyMMdd")
            val currentDateStr = dateFormat.format(currentDate)

            // Get the shared preferences
            val prefs: SharedPreferences = applicationContext.getSharedPreferences("RoutineWorkerPrefs", Context.MODE_PRIVATE)

            // Get the date when the app was last opened
            val lastOpenedDateStr = prefs.getString("lastOpenedDate", null)

            // Check if the current date is different from the last opened date
            if (lastOpenedDateStr == null || lastOpenedDateStr != currentDateStr) {
                // Set the last opened date to the current date
                prefs.edit().putString("lastOpenedDate", currentDateStr).apply()

                // Set the isDone value of all routines to 0
                withContext(Dispatchers.IO) {
                    val database = Room.databaseBuilder(applicationContext, MyDataBase::class.java, "rotuinedb.db").createFromAsset("rotuinedb.db").build()
                    val routineDao = database.getroutineDao()
                    val routines = routineDao.loadRoutine()

                    routines.forEach { routine ->
                        routine.isDone = 0
                        routineDao.update(routine)
                    }
                }
            }

            // İşlem başarıyla tamamlandıysa Result.success() döndür
            Result.success()
        } catch (e: Exception) {
            // Hata durumunda Result.failure() döndür
            Result.failure()
        }
    }
}
