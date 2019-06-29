package com.riluq.seafood.barcode


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.zxing.Result

import com.riluq.seafood.R
import com.riluq.seafood.cameraPermission
import com.riluq.seafood.network.Seafood
import com.riluq.seafood.overview.OverviewViewModel
import me.dm7.barcodescanner.zxing.ZXingScannerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class BarcodeScannerFragment : Fragment(), ZXingScannerView.ResultHandler  {


    private val viewModel: BarcodeScannerViewModel by lazy {
        ViewModelProviders.of(this).get(BarcodeScannerViewModel::class.java)
    }

    private var mScannerView: ZXingScannerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mScannerView = ZXingScannerView(activity)

        cameraPermission(context!!, activity!!)

        viewModel.navigateForResult.observe(this, Observer {
            if (it != null) {
                this.findNavController()
                    .navigate(BarcodeScannerFragmentDirections.actionBarcodeScannerFragmentToDetailFragment(
                    Seafood(), it))
            }
        })

        // Inflate the layout for this fragment
        return mScannerView
    }


    override fun onResume() {
        super.onResume()
        mScannerView?.setResultHandler(this)
        mScannerView?.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()
    }

    override fun handleResult(rawResult: Result) {
        viewModel.displayMealDetails(rawResult.text)

        Toast.makeText(context, "${rawResult.barcodeFormat}: ${rawResult.text}", Toast.LENGTH_SHORT).show()

        // If you would like to resume scanning, call this method below:
//        mScannerView?.resumeCameraPreview(this)
    }


}
