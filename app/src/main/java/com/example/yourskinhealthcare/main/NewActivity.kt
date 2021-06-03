package com.example.yourskinhealthcare.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.yourskinhealthcare.R
import com.example.yourskinhealthcare.ml.*
import kotlinx.android.synthetic.main.activity_detect.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class NewActivity : AppCompatActivity() {

    lateinit var select_image_button: Button
    lateinit var make_prediction: Button
    lateinit var img_view: ImageView
    lateinit var text_view: TextView
    lateinit var bitmap: Bitmap
    lateinit var text_view2: TextView
    lateinit var btnmel: Button
    lateinit var btnsqu: Button
    lateinit var btnder: Button
    lateinit var btnpig: Button
    lateinit var btnvas: Button
    lateinit var btnseb: Button

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detect)

        select_image_button = findViewById(R.id.button)
        make_prediction = findViewById(R.id.button2)
        img_view = findViewById(R.id.imageView2)
        text_view = findViewById(R.id.textView)
        text_view2 = findViewById(R.id.textView2)
        btnmel = findViewById(R.id.buttonmel)
        btnseb = findViewById(R.id.buttonseb)
        btnsqu = findViewById(R.id.buttonsqu)
        btnder = findViewById(R.id.buttonder)
        btnvas = findViewById(R.id.buttonvas)
        btnpig = findViewById(R.id.buttonpig)

        btnsqu.visibility = View.GONE
        btnseb.visibility = View.GONE
        btnvas.visibility = View.GONE
        btnpig.visibility = View.GONE
        btnmel.visibility = View.GONE
        btnder.visibility = View.GONE

        val labels =
            application.assets.open("label.txt").bufferedReader().use { it.readText() }.split("\n")

        select_image_button.setOnClickListener(View.OnClickListener {
            Log.d("mssg", "button pressed")
            var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            startActivityForResult(intent, 100)
        })

        make_prediction.setOnClickListener(View.OnClickListener {
            var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = ModelSkin.newInstance(this)

            var tbuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffer.buffer
            val image = TensorImage.fromBitmap(bitmap)

            // Creates inputs for reference.

            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(image)

            val probability = outputs.probabilityAsCategoryList
            val max = probability.apply { sortByDescending { it.score } }.take(Int.MAX_VALUE)
            val foodCategory = when (max[0].label.toString()) {
                "dermatofibroma" -> "Dermatofibroma"
                "pigmented_benign_keratosis" -> "Pigmented Benign Keratosis"
                "seborrheic_keratosis" -> "Seborrheic Keratosis"
                "squamous_cell_carcinoma" -> "Squamous Cell Carcinoma"
                "vascular_lesion" -> "Vascular Lesion"
                "melanoma" -> "Melanoma"
                else -> { // Note the block
                    "Undefined"
                }
            }
            val foodScore = max[0].score * 100
            val number3digits: Double = String.format("%.2f", foodScore).toDouble()

            //var max = getMax(probability)
            //var new = probability.floatArray[max]
            var new = foodCategory
            var score = probability.component1()
            //var percentage = new/500*100
            textView2.text = new
            text_view.text = number3digits.toString() + "%"
            var shared = textView2.text.toString()


            if (shared == "Dermatofibroma") {
                btnsqu.visibility = View.GONE
                btnseb.visibility = View.GONE
                btnvas.visibility = View.GONE
                btnpig.visibility = View.GONE
                btnmel.visibility = View.GONE
                btnder.visibility = View.VISIBLE
            } else if (shared == "Melanoma") {
                btnsqu.visibility = View.GONE
                btnseb.visibility = View.GONE
                btnvas.visibility = View.GONE
                btnpig.visibility = View.GONE
                btnmel.visibility = View.VISIBLE
                btnder.visibility = View.GONE
            } else {
                btnsqu.visibility = View.GONE
                btnseb.visibility = View.GONE
                btnvas.visibility = View.GONE
                btnpig.visibility = View.GONE
                btnmel.visibility = View.GONE
                btnder.visibility = View.GONE
            }
            //textView2.setText((outputFeature0.floatArray[max].toString())+"%")


            // Releases model resources if no longer used.
            model.close()
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        img_view.setImageURI(data?.data)

        var uri: Uri? = data?.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
    }

    fun getMax(arr: FloatArray): Int {
        var ind = 0
        var min = 0.0f

        for (i in 0..5) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i
            }
        }
        return ind
    }
}