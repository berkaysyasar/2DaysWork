import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.berkay.a2dayswork.room.MyDataBase
import java.util.Calendar

class RoutineWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    private val applicationContext = context.applicationContext

    override suspend fun doWork(): Result {
        return try {
            val prefs: SharedPreferences = applicationContext.getSharedPreferences("RoutineWorkerPrefs", Context.MODE_PRIVATE)

            // SharedPreferences'ten işlemin daha önce yapılıp yapılmadığını kontrol et
            val isRoutineResetDone: Boolean = prefs.getBoolean("isRoutineResetDone", false)

            // Eğer daha önce işlem yapılmamışsa, işlemi gerçekleştir
            if (!isRoutineResetDone) {
                // Veritabanını oluştur veya al
                val database = Room.databaseBuilder(applicationContext, MyDataBase::class.java, "rotuinedb.db").createFromAsset("rotuinedb.db").build()

                // Tüm rutinleri al
                val routines = database.getroutineDao().loadRoutine()

                // Her rutin için isDone değerini sıfırla ve veritabanına kaydet
                for (routine in routines) {
                    routine.isDone = 0
                    database.getroutineDao().update(routine)
                    println("Worked")
                }

                // İşlem tamamlandığında SharedPreferences'teki değeri güncelle
                prefs.edit().putBoolean("isRoutineResetDone", true).apply()
            }

            // İşlem başarıyla tamamlandıysa Result.success() döndür
            Result.success()
        } catch (e: Exception) {
            // Hata durumunda Result.failure() döndür
            Result.failure()
        }
    }
}
