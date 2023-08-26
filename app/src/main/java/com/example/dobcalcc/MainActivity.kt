package com.example.dobcalcc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // criando uma variável de exibição de texto para que eu possa defini-la como nula pq não a
    // inicializarei neste ponto
    private var tvSelectedDate : TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // criamos uma variável que vai permitir o acesso ao button e a partir disso podemos
        // tratar da forma que queremos
        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)

        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)

        btnDatePicker.setOnClickListener {
            clickDatePicker() //a função está sendo chamada e irá executar tudo que estiver dentro dela
        }
    }

    //função para executar o código do selecionador de dados
    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance() //obtendo o calendário java
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        // caixa de diálogo de seleção de data
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                Toast.makeText(this,
                    "Year was $selectedYear, month was ${selectedMonth+1}" +
                            ", day was $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                // se a data não estiver vazia, execute isso... (garantindo que o código não quebre)
                theDate?.let {
                    val selectedDayInMinutes = theDate.time / 60000

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    // se a data não estiver vazia, execute isso... (garantindo que o código não quebre)
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDayInMinutes
                        tvAgeInMinutes?.text = "$differenceInMinutes"
                    }
                }

            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()

    }
}