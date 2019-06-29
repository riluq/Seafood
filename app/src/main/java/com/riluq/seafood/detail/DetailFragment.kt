package com.riluq.seafood.detail


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.riluq.seafood.databinding.FragmentDetailBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = FragmentDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val meals = DetailFragmentArgs.fromBundle(arguments!!).selectedSeafood
        val barcodeResult = DetailFragmentArgs.fromBundle(arguments!!).scanResult
        val viewModelFactory = DetailViewModelFactory(meals, barcodeResult, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.navigateToYoutube.observe(this, Observer {
            if (it == true) {
                val url = viewModel.mealYoutube.value
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                viewModel.onYoutubeNavigated()
            }
        })
        viewModel.navigateToSource.observe(this, Observer {
            if (it == true) {
                val url = viewModel.mealSource.value
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
                viewModel.onSourceNavigated()
            }
        })
        viewModel.navigateToOverview.observe(this, Observer {
            if (it == true) {
                this.findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToOverviewFragment())
                Toast.makeText(context, "Data tidak ditemukan", Toast.LENGTH_LONG).show()
                viewModel.onOverviewNavigated()
            }
        })


        return binding.root
    }


}
