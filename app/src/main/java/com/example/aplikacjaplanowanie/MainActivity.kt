package com.example.zadaniezchipami

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.RadioGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var shoppingItems: Map<String, List<String>>
    private var selectedItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingItems = mapOf(
            "Warzywa" to listOf("Kukurydza", "Brokuły", "Rzodkiewka"),
            "Mięsa Delikatesowe" to listOf("Bekon", "Salami", "Prosciutto", "Kurczak"),
            "Pieczywo" to listOf("Chleb razowy", "Chleb pełnoziarnisty", "Chleb pszenny")
        )


        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.addModeRadioButton -> showAddMode()
                R.id.checkOffModeRadioButton -> showCheckOffMode()
            }
        }


        val vegetablesCheckboxGroup = findViewById<ChipGroup>(R.id.vegetablesCheckboxGroup)
        val deliMeatsCheckboxGroup = findViewById<ChipGroup>(R.id.deliMeatsCheckboxGroup)
        val breadCheckboxGroup = findViewById<ChipGroup>(R.id.breadCheckboxGroup)

        vegetablesCheckboxGroup.removeAllViews()
        deliMeatsCheckboxGroup.removeAllViews()
        breadCheckboxGroup.removeAllViews()

        val shoppingItemList = shoppingItems.values.flatten()
        for (item in shoppingItemList) {

            val checkbox = CheckBox(this)
            checkbox.text = item
            checkbox.tag = shoppingItems.entries.firstOrNull { it.value.contains(item) }?.key

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                } else {
                    selectedItems.remove(item)
                }
            }

            when (checkbox.tag) {
                "Warzywa" -> {
                    checkbox.buttonTintList = resources.getColorStateList(R.color.vegetablesColor)
                    vegetablesCheckboxGroup.addView(checkbox)
                }
                "Mięsa Delikatesowe" -> {
                    checkbox.buttonTintList = resources.getColorStateList(R.color.deliMeatsColor)
                    deliMeatsCheckboxGroup.addView(checkbox)
                }
                "Pieczywo" -> {
                    checkbox.buttonTintList = resources.getColorStateList(R.color.breadColor)
                    breadCheckboxGroup.addView(checkbox)
                }
            }
        }



    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showAddMode() {
        val checkboxesLayout = findViewById<View>(R.id.checkboxesLayout)
        val chipsLayout = findViewById<View>(R.id.chipsLayout)
        checkboxesLayout.visibility = View.VISIBLE
        chipsLayout.visibility = View.GONE
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showCheckOffMode() {
        val checkboxesLayout = findViewById<View>(R.id.checkboxesLayout)
        val chipsLayout = findViewById<View>(R.id.chipsLayout)
        checkboxesLayout.visibility = View.GONE
        chipsLayout.visibility = View.VISIBLE

        val chipsGroup = findViewById<ChipGroup>(R.id.chipsGroup)
        chipsGroup.removeAllViews()

        for ((groupName, items) in shoppingItems) {
            for (item in items) {
                if (selectedItems.contains(item)) {
                    val chip = Chip(this)
                    chip.text = item
                    chip.isCloseIconVisible = true
                    chip.tag = groupName

                    when (groupName) {
                        "Warzywa" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.vegetablesColor)
                        }
                        "Mięsa Delikatesowe" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.deliMeatsColor)
                        }
                        "Pieczywo" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.breadColor)
                        }
                    }

                    chipsGroup.addView(chip)
                    chip.setOnCloseIconClickListener {
                        selectedItems.remove(item)
                        chipsGroup.removeView(chip)
                    }
                }
            }
        }
    }
}