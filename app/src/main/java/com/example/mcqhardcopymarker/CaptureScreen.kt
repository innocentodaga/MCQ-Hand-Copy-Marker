package com.example.mcqhardcopymarker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.mcqhardcopymarker.data.StudentAnswer
import com.example.mcqhardcopymarker.data.StudentAnswerViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CaptureScreen : AppCompatActivity() {

    private lateinit var captureImageBtn: Button
    private lateinit var copyTextBtn: Button
    private var currentPhotoPath: String? = null
    private lateinit var cameraImage: ImageView
    private lateinit var toolbar: MaterialToolbar

    private lateinit var studentAnswerViewModel: StudentAnswerViewModel

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var takePictureLauncher: ActivityResultLauncher<Uri>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_capture_screen)

        cameraImage = findViewById(R.id.cameraImage)
        captureImageBtn = findViewById(R.id.captureImgBtn)
        copyTextBtn = findViewById(R.id.copyTextBtn)
        toolbar = findViewById(R.id.toolbar)

        studentAnswerViewModel = ViewModelProvider(this)[StudentAnswerViewModel::class.java]

        toolbar.setOnClickListener {
            finish() // or navigate
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) captureImage()
            else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }

        takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                currentPhotoPath?.let {
                    val bitmap = BitmapFactory.decodeFile(it)
                    cameraImage.setImageBitmap(bitmap)
                    recognizeText(bitmap)
                }
            }
        }

        captureImageBtn.setOnClickListener {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun captureImage() {
        val photoFile: File? = try {
            createImageFile()
        } catch (ex: IOException) {
            Toast.makeText(this, "File creation failed", Toast.LENGTH_SHORT).show()
            null
        }

        photoFile?.also {
            val photoUri = FileProvider.getUriForFile(this, "${packageName}.provider", it)
            takePictureLauncher.launch(photoUri)
        }
    }

    private fun recognizeText(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                copyTextBtn.setOnClickListener {
                    val studentId = "student_001" // Replace with actual student ID

                    for (block in visionText.textBlocks) {
                        for (line in block.lines) {
                            val raw = line.text.trim()
                            val parts = raw.split(" ")

                            if (parts.size == 2) {
                                val question = parts[0].toIntOrNull()
                                val answer = parts[1].uppercase()

                                if (question != null && answer in listOf("A", "B", "C", "D")) {
                                    val record = StudentAnswer(
                                        studentId = studentId,
                                        questionNumber = question,
                                        selectedOption = answer
                                    )
                                    studentAnswerViewModel.insertAnswer(record)
                                }
                            }
                        }
                    }

                    Toast.makeText(this, "Answers saved to database", Toast.LENGTH_SHORT).show()
                }

                copyTextBtn.visibility = Button.VISIBLE
            }
            .addOnFailureListener {
                Toast.makeText(this, "OCR failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
