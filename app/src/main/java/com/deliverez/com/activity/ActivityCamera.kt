package com.deliverez.com.activity

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.deliverez.com.R
import com.deliverez.com.imagePicker.imageModel.ImageModel
import com.deliverez.com.tools.DeliverezConstants.selctedImagesCamera
import io.fotoapparat.Fotoapparat
import io.fotoapparat.parameter.ScaleType
import io.fotoapparat.selector.back
import io.fotoapparat.view.CameraView

import java.io.File
import java.util.*


class ActivityCamera : AppCompatActivity() {

    private lateinit var cameraDoneButton: TextView
    private lateinit var takePic: ImageView
    private lateinit var cameraView: CameraView
    private lateinit var fotoapparat: Fotoapparat
    private val arrayList = ArrayList<ImageModel>()
    private lateinit var cameraCount: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        cameraView = findViewById(R.id.camera_view)
        cameraDoneButton = findViewById(R.id.cameraDoneButton)
        takePic = findViewById(R.id.takePic)
        cameraCount = findViewById(R.id.cameraCountValue)



        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView,
                scaleType = ScaleType.CenterCrop,
                lensPosition = back()
        )


        fotoapparat.start()

        takePic.setOnClickListener {
            val photoResult = fotoapparat.takePicture()
            val outputFile = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + "pic.png"
            val file = File(outputFile)
            photoResult.saveToFile(file)
            val imageModel = ImageModel(outputFile, file.absolutePath, file.absolutePath,"")
            arrayList.add(imageModel)

            cameraCount.text = arrayList.size.toString()
        }


        cameraDoneButton.setOnClickListener {
            if (arrayList.size > 0) {
                val intent = Intent()
                selctedImagesCamera = arrayList
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@ActivityCamera, "Please Select Photo", Toast.LENGTH_LONG).show()
            }
        }


    }


}





