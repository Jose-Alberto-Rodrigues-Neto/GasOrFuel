package app.gasorfuel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission(
    onPermissionGranted: () -> Unit
) {
    val permissionState = rememberPermissionState(
        permission = android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    when {
        permissionState.status.isGranted -> {
            onPermissionGranted()
        }
        permissionState.status.shouldShowRationale -> {
            Text(stringResource(R.string.permission_text))
        }
        else -> {
            Text(stringResource(R.string.permission_denied))
        }
    }
}

@SuppressLint("MissingPermission")
fun getUserLocation(
    context: Context,
    onLocationReceived: (Location?) -> Unit
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            onLocationReceived(location)
        }
        .addOnFailureListener { exception ->
            Log.e("Location", "Error getting location: ${exception.message}")
        }
}

fun startNavigationToLocation(context: Context, latitude: Double?, longitude: Double?, errorText: String) {
    if (latitude == null || longitude == null) {
        Log.e("Location", "There is no longitude or latitude")
        return
    }

    // Usando a URL de navegação do Google Maps
    val uri = Uri.parse("google.navigation:q=$latitude,$longitude")
    val intent = Intent(Intent.ACTION_VIEW, uri)

    context.startActivity(intent)

}
