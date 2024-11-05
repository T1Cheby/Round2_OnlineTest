package com.example.question11

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonParser
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var fromCurrencyTextView: TextView
    private lateinit var toCurrencyTextView: TextView
    private lateinit var outputRate: TextView
    private lateinit var searchCurrencyEditText: EditText
    private lateinit var convertedAmount: EditText
    private lateinit var currencyConversionButton: Button
    private lateinit var currencyListView: ListView
    private var convertFromValue: String? = null
    private var convertToValue: String? = null

    // List of currencies
    private val currencyList = arrayListOf(
        "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
        "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL",
        "BSD", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY",
        "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD",
        "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "FOK", "GBP", "GEL", "GGP",
        "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG",
        "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JMD", "JOD",
        "JPY", "KES", "KGS", "KHR", "KID", "KMF", "KRW", "KWD", "KYD", "KZT",
        "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD", "MDL", "MGA", "MKD",
        "MMK", "MNT", "MOP", "MRU", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN",
        "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK",
        "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR",
        "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP",
        "STN", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD",
        "TVD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VES", "VND",
        "VUV", "WST", "XAF", "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW", "ZWL"
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fromCurrencyTextView = findViewById(R.id.fromCurrencyDropdownMenu)
        toCurrencyTextView = findViewById(R.id.toCurrencyDropdownMenu)
        currencyConversionButton = findViewById(R.id.currencyConversionButton)
        outputRate = findViewById(R.id.output)
        convertedAmount = findViewById(R.id.amount)

        // Set click listeners for currency selection
        fromCurrencyTextView.setOnClickListener { displayCurrencyDialog(fromCurrencyTextView, false) }
        toCurrencyTextView.setOnClickListener { displayCurrencyDialog(toCurrencyTextView, true) }


        // Set click listener for the currency conversion button
        currencyConversionButton.setOnClickListener {
            // Error handling for the case of null values.
            if (convertFromValue == null || convertToValue == null || convertedAmount.text.isEmpty()) {
                outputRate.text = "Please select both currencies and the amount to convert"
                return@setOnClickListener
            }
            convertCurrency(convertFromValue!!, convertToValue!!, convertedAmount.text.toString().toDouble())
        }
    }

    // Display a dialog to choose currency
    private fun displayCurrencyDialog(currencyTextView: TextView, isToCurrency: Boolean) {
        val chooseCurrencyDialog = Dialog(this)
        chooseCurrencyDialog.setContentView(R.layout.spinner)

        currencyListView = chooseCurrencyDialog.findViewById(R.id.currencyListView)
        searchCurrencyEditText = chooseCurrencyDialog.findViewById(R.id.searchCurrencyEditText)

        val currencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyList)
        currencyListView.adapter = currencyAdapter

        searchCurrencyEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currencyAdapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        currencyListView.setOnItemClickListener { _, _, position, _ ->
            val selectedCurrency = currencyAdapter.getItem(position)!!
            currencyTextView.text = selectedCurrency
            chooseCurrencyDialog.dismiss()

            if (isToCurrency) {
                convertToValue = selectedCurrency
            } else {
                convertFromValue = selectedCurrency
            }
        }

        chooseCurrencyDialog.show()
    }

    @SuppressLint("StaticFieldLeak")
    private fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double) {
        // Asynchronous task to fetch conversion rates and calculate the converted amount
        object : AsyncTask<Void, Void, Double>() {
            @SuppressLint("SetTextI18n")
            override fun doInBackground(vararg params: Void?): Double? {
                return try {
                    val urlStr = "https://v6.exchangerate-api.com/v6/adb86d1e8da86b242b48ba39/latest/$fromCurrency"
                    val url = URL(urlStr)
                    val request = url.openConnection() as HttpURLConnection
                    request.connect()

                    val root = JsonParser.parseReader(InputStreamReader(request.inputStream)).asJsonObject
                    val conversionRates = root.getAsJsonObject("conversion_rates")
                    val rate = conversionRates[toCurrency].asDouble
                    amount * rate
                // Error handling for the case of error in network connection.
                } catch (e: java.net.SocketException) {
                    e.printStackTrace()
                    outputRate.text = "Error occurred in network connection process"
                    -1.0
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            @SuppressLint("DefaultLocale", "SetTextI18n")
            override fun onPostExecute(result: Double?) {
                if (result != null) {
                    outputRate.text = String.format("%.5f", result)
                } else {
                    outputRate.text = "Error occurred in currency conversion."
                }
            }
        }.execute()
    }
}
