package launcher.focux.datastore.lockedapp

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object LockedAppSerializer : Serializer<LockedAppModel> {
    override val defaultValue: LockedAppModel = LockedAppModel()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun readFrom(input: InputStream): LockedAppModel {
        try {
            return Json.decodeFromString(
                deserializer = LockedAppModel.serializer(),
                string = input.readAllBytes().decodeToString()
            )
        } catch (exception : SerializationException) {
            throw CorruptionException(exception.toString())
        }
    }

    override suspend fun writeTo(
        t: LockedAppModel,
        output: OutputStream
    ) {
        output.write(
            Json.encodeToString(
                t
            ).toByteArray()
        )
    }
}

val Context.lockedAppDatastore: DataStore<LockedAppModel> by dataStore(
    fileName = "locked_app.json",
    serializer = LockedAppSerializer
)