package com.example.upstox

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.StockHoldings.Stock
import com.example.upstox.StocksAdapter.StockVH
import upstox.databinding.ItemStockBinding

class StocksAdapter(private val stocks: List<Stock?>): RecyclerView.Adapter<StockVH>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockVH {
    return StockVH(ItemStockBinding.inflate(LayoutInflater.from(parent.context),parent,false))
  }

  override fun onBindViewHolder(holder: StockVH, position: Int) {
    holder.bind(stocks[position])
  }

  override fun getItemCount(): Int = stocks.size

  class StockVH(private val itemStockBinding: ItemStockBinding): RecyclerView.ViewHolder(itemStockBinding.root){
    fun bind(stock: Stock?){
      itemStockBinding.apply {
        textViewName.text = stock?.symbol
        textViewNetQty.text = stock?.quantity.toString()
        textViewLtpValue.text = "₹"+stock?.ltp.toString()
        val pslValue= (stock?.close?.minus(stock.ltp!!))?.times(stock.quantity!!)
        textViewPl.text = "₹"+(pslValue?.times(1000.0)?.let { Math.round(it) }?.div(1000.0)).toString()
        if (pslValue != null) {
          if(pslValue<0){
            textViewPl.setTextColor(Color.parseColor("#B71C1C"))
            }else{
            textViewPl.setTextColor(Color.parseColor("#1DE9B6"))
            }
        }
      }
    }


  }
}