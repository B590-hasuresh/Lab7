package com.example.lab7

import android.app.Dialog
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.lab7.databinding.FragmentZoomedImageBinding
import java.io.File

class ZoomedImageDialogFragment : DialogFragment() {

    private var _binding: FragmentZoomedImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        // Remove title bar for a full-screen look
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentZoomedImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the photo file path from arguments
        val photoFilePath = arguments?.getString(ARG_PHOTO_PATH)
        if (!photoFilePath.isNullOrEmpty()) {
            val photoFile = File(photoFilePath)
            if (photoFile.exists()) {
                // Decode and set the image
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                binding.zoomedImageView.setImageBitmap(bitmap)
            }
        }

        // Close the dialog when the user taps the zoomed-in image
        binding.zoomedImageView.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_PHOTO_PATH = "photo_path"

        fun newInstance(photoFilePath: String): ZoomedImageDialogFragment {
            val args = Bundle().apply {
                putString(ARG_PHOTO_PATH, photoFilePath)
            }
            return ZoomedImageDialogFragment().apply {
                arguments = args
            }
        }
    }
}