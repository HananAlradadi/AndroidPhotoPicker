package com.example.androidphotopicker

import android.app.Activity
import android.app.Notification.Action
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.androidphotopicker.ui.theme.AndroidPhotoPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPhotoPickerTheme {

                val uriImg = remember {
                    mutableStateOf<Uri?>(null)
                }
                val uriImgs = remember {
                    mutableStateOf<List<Uri?>>(emptyList())
                }
                val lFS = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickVisualMedia()
                ) {
                   uri: Uri? ->
                    uriImg.value = uri
                }
                val lFM = rememberLauncherForActivityResult(
                    ActivityResultContracts.PickMultipleVisualMedia()
                ) {
                    uriImgs.value = it
                }

                LazyColumn {
                    item{
                        Row(Modifier.padding(top = 50.dp)) {
                            Button(onClick = {
                                lFS.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }) {
                                Text(text = "Select Single image")
                            }
                            Button(onClick = {
                                lFM.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )}) {
                                Text(text = "Select Multiple image")
                            }
                        }
                    }
                    item{
                        AsyncImage(
model = uriImg.value ,
                            contentDescription = null ,
                            modifier =  Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    items(uriImgs.value) {
                        AsyncImage(
                            model = uriImgs.value ,
                            contentDescription = null ,
                            modifier =  Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

            }
        }
    }
  fun checkLocationPermission(){
      if(ContextCompat.checkSelfPermission(
          this,
              android.Manifest.permission.ACCESS_FINE_LOCATION ,
      ) != PackageManager.PERMISSION_GRANTED){
          ActivityCompat.requestPermissions(
              this ,
              arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION ),
              0
          )

      }
      else {
          Toast.makeText(this , "Permission granted" , Toast.LENGTH_SHORT).show()
      }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidPhotoPickerTheme {
        Greeting("Android")
    }
}