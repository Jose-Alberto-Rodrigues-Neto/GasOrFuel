package app.gasorfuel.manager

import android.content.Context
import android.content.SharedPreferences
import app.gasorfuel.model.GasStation
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val PREFS_NAME = "gas_stations_prefs"
private const val KEY_STATIONS = "gas_stations_list"

object GasStationManager {
    fun saveStation(context: Context, station: GasStation) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val stations = loadStations(context).toMutableList()

        stations.removeAll { it.name == station.name }

        stations.add(station)

        with(sharedPreferences.edit()) {
            putString(KEY_STATIONS, Json.encodeToString(stations))
            apply()
        }
    }

    fun loadStations(context: Context): List<GasStation> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val json = sharedPreferences.getString(KEY_STATIONS, "[]") ?: "[]"
        return Json.decodeFromString(json)
    }

    fun deleteStation(context: Context, id: Int) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val stations = loadStations(context).filterNot { it.id == id }

        with(sharedPreferences.edit()) {
            putString(KEY_STATIONS, Json.encodeToString(stations))
            apply()
        }
    }

    fun updateStation(context: Context, updatedStation: GasStation) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        val stations = loadStations(context).toMutableList()

        val index = stations.indexOfFirst { it.id == updatedStation.id }
        if (index != -1) {
            stations[index] = updatedStation
        }

        with(sharedPreferences.edit()) {
            putString(KEY_STATIONS, Json.encodeToString(stations))
            apply()
        }
    }
}