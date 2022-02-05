package com.example.upstox

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.upstox.ViewState.*
import upstox.R
import upstox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  private val viewModel: MainActivityVM by lazy {
    ViewModelProvider(this, MyViewModelFactory())[MainActivityVM::class.java]
  }

  private val binding: ActivityMainBinding by lazy {
    DataBindingUtil.setContentView(this, R.layout.activity_main)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    viewModel.getStockHoldings()
    setListeners()
    addObservers()
  }

  private fun setListeners() {
    binding.bottomView.setOnClickListener {
      val bundle = bundleOf(
        BottomSheetDialog.TOTAL_INVESTMENT to viewModel.totalInvestment,
        BottomSheetDialog.TODAY_PROFIT_LOSS to viewModel.todayPNL,
        BottomSheetDialog.TOTAL_PROFIT_LOSS to viewModel.totalPNL,
        BottomSheetDialog.CURRENT_VALUE to viewModel.currentValue
      )
      Log.e("CURRENT_VALUE", viewModel.currentValue.toString())
      Log.e("TODAY_PROFIT_LOSS", viewModel.todayPNL.toString())
      Log.e("TOTAL_PROFIT_LOSS", viewModel.totalPNL.toString())
      Log.e("TOTAL_INVESTMENT", viewModel.totalInvestment.toString())


      val bottomSheetDialog = BottomSheetDialog.newInstance(bundle)
      bottomSheetDialog.show(supportFragmentManager, "bottom")
    }
  }

  private fun addObservers() {
    viewModel.stockHoldingsLiveData.observe(this) {
      binding.progressBar.isVisible = it is Loading
      binding.bottomView.isVisible = it is Success
      when (it) {
        is Failure -> Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
        NoInternet -> Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show()
        is Success -> {
          binding.apply {
            recyclerViewStocks.adapter = it.data.data?.let { it1 -> StocksAdapter(it1) }
            binding.txtProfitLoss.text = "₹"+viewModel.totalPNL.toString()
            setColours(binding.txtProfitLoss, viewModel.totalPNL)
          }
        }
        else -> {}
      }
    }
  }
  private fun setColours(textView: TextView,no: Int){
    if(no<0){
      textView.setTextColor(Color.parseColor("#B71C1C"))
    }else{
      textView.setTextColor(Color.parseColor("#1DE9B6"))
    }
  }
}