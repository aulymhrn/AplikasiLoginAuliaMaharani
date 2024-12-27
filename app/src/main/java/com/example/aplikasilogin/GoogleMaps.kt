package com.example.aplikasilogin

import android.app.Dialog
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.fragment.app.DialogFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMaps : DialogFragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_google_maps)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map)
                as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        val btnTutup = dialog.findViewById<Button>(R.id.btnTutup)
        btnTutup.setOnClickListener {
            dismiss()
        }
        return dialog
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val latitude =-6.9821286
        val longitude = 110.4477009
        val myLocation = LatLng(latitude,longitude)
        googleMap.addMarker(MarkerOptions()
            .position(myLocation)
            .title("USM"))
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
        googleMap.addMarker(MarkerOptions().position(myLocation))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))

    }
    override fun onDestroyView() {
        super.onDestroyView()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map)
                as? SupportMapFragment
        if (mapFragment != null) {
            childFragmentManager.beginTransaction()
                .remove(mapFragment)
                .commit()
        }
    }

}


