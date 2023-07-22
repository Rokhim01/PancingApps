package com.example.pancingapps.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pancingapps.R
import com.example.pancingapps.application.PancingApps
import com.example.pancingapps.databinding.FragmentSecondBinding
import com.example.pancingapps.model.Pancing
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val PancingViewModel: PancingViewModel by viewModels {
        pancingViewModelFactory((applicationContext as PancingApps).repository)
    }
     private val args : SecondFragmentArgs by navArgs()
    private var pancing: Pancing? = null
    private lateinit var mMap: GoogleMap
    private var currentLatLang :LatLng?=null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pancing = args.pancing
        // kita cek jika ikan null maka tampilan deflaut nambah nama
        // jika ikan tidak nul tampilan sedikit berubah ada tombol hapus
        if (pancing != null) {
            binding.nameEditTextText.visibility = View.VISIBLE
            binding.saveButton.text = "simpan"
            binding.nameEditTextText.setText(pancing?.name)
            binding.BeratEditText.setText(pancing?.berat)
            binding.hargaEditText.setText(pancing?.address)
        }
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map)as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()

        val name = binding.nameEditTextText.text
        val address = binding.addressEditText.text
        val jumlah = binding.amountEditText.text
        val berat = binding.BeratEditText.text
        val harga = binding.hargaEditText.text
        binding.saveButton.setOnClickListener {
            // kita kasih kondisi jika data kosong tidak bisa menyimpan
            if (name.isEmpty()) {
                Toast.makeText(context, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (address.isEmpty()) {
                Toast.makeText(context, "Alamat tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (jumlah.isEmpty()) {
                Toast.makeText(context, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else if (berat.isEmpty()) {
                Toast.makeText(context, "berat tidak boleh kosong", Toast.LENGTH_SHORT).show()
                }else if (harga.isEmpty()) {
                Toast.makeText(context, "harga tidak boleh kosong", Toast.LENGTH_SHORT).show()
            } else {
                if (pancing == null){
                    val pancing = Pancing( 0, name.toString(), address.toString(), jumlah.toString(), berat.toString(), harga.toString(),currentLatLang?.latitude,currentLatLang?.longitude )
                    PancingViewModel.insert(pancing)
                }else {
                    val pancing = Pancing(pancing?.id!!, name.toString(), address.toString(), jumlah.toString(), berat.toString(), harga.toString(),currentLatLang?.latitude,currentLatLang?.longitude)
                    PancingViewModel.update(pancing)
                }
                findNavController().popBackStack() // untuk dismiss halaman ini
            }
        }

        binding.deleteButton.setOnClickListener {
            pancing?.let { PancingViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }
 override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        val sydney = LatLng(-34.0 , 151.0)
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {}

    override fun onMarkerDragEnd(marker: Marker) {
        val newPosition = marker.position
        currentLatLang = LatLng(newPosition.latitude, newPosition.longitude)
        Toast.makeText(context, currentLatLang.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }
    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
          applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        }else{
            Toast.makeText(applicationContext, "akses lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCurrentLocation(){
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        )!= PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null){
                    var latlang =LatLng(location.latitude,location.longitude)
                    currentLatLang = latlang
                    var title = "Marker"

                    if (pancing!=null){
                        title=pancing?.name.toString()
                        val newCurrentLocation = LatLng(pancing?.latitude!!,pancing?.longitude!!)
                        latlang=newCurrentLocation
                    }

                    val markerOption = MarkerOptions()
                        .position(latlang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_fishingrod))
                    mMap.addMarker(markerOption)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlang,15f))
                }
            }
    }
}

